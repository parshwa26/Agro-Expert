<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{	
	ob_start();
	$lid = $_REQUEST['lid'];
	$district = $_REQUEST['district'];
	$arr = array();
	$sql = "update registration set did = N'".$district."' where lid = '".$lid."'";
	//echo "update login set district = $district where lid = $lid";
	$result = mysqli_query($con,$sql);

	if($result)
		$arr[] = array("status" => "District Updated");
	else
		$arr[] = array("status" => "Error in updating district");
	$arr1 = array("updatedistrict" => $arr);
	echo json_encode($arr1);
}
else
{
	echo "Invalid key";
}
?>