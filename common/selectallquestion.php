<?php
//error_reporting(0);
include_once "connection.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$arr = array();
	$sql = "SELECT * FROM `question` WHERE status=1";
	$result = mysqli_query($con,$sql);

	while($row = mysqli_fetch_array($result)){

	    $arr[] = array('question' => $row['question'],'image' => $row['image'],'timestamp' => $row['timestamp']);
	}

	$arr1 = array("selectquestion" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid key";
}
?>