<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{	
	ob_start();
	$arr = array();

	$qid = $_REQUEST['qid'];
	$no = mysqli_query($con,"SELECT * FROM `like` where qid=$qid");
	$data = mysqli_num_rows($no);

	$arr[] =array('count' => $data);

	echo json_encode($arr);
}
else
{
	echo "Invalid key";
}
?>