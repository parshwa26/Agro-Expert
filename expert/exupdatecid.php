<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{	
	ob_start();
	$arr = array();
	$cid = $_REQUEST['cid'];
	$qid = $_REQUEST['qid'];

	$update = mysqli_query($con,"update question set cid = $cid where qid=$qid");
	if($update)
		$arr[] = array('status' => "success");
	else
		$arr[] = array('status' => "unsuccess");

	$arr1 = array("updatecid" => $arr);
	echo json_encode($arr1);
}
else
{
	echo "Invalid key";
}
?>