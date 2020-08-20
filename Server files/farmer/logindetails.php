<?php
//error_reporting(0);
include_once "connection.php";
ob_start();
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	$phno = $_REQUEST['phno'];
	$arr = array();

	$sql = "SELECT * FROM `login` WHERE phoneno= ".$phno." AND status=1";
	$result = mysqli_query($con,$sql);

	while($row = mysqli_fetch_array($result)){
	    $arr[] = array('lid' => $row['lid'],'phoneno' => $row['phoneno'],'district' => $row['district']);
	}
 
	$arr1 = array("login" => $arr);
	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid key";
}
?>