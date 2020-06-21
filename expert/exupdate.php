<?php
include_once "connection1.php";

$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$lang_flag = $_REQUEST['lang_flag'];
	$expert = "2";
	$arr = array();

	$checkno1 = mysqli_query($con,"update registration set lang_flag = $lang_flag where lid = $lid AND user_type = '$expert' ");
	if($checkno1)
	{
		$arra[] = array('status' => "success");
		$arr1 = array('update' => $arra);				
	}
	else
	{
		$arra[] = array('status' => "unsuccess");
		$arr1 = array('update' => $arra);
	}
	echo json_encode($arr1);
}
else
{
	echo "Invalid code";
}
?>