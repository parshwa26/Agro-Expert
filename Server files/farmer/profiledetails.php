<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$lid = $_REQUEST['lid'];
	$edit = $_REQUEST['edit'];

	$display = "display";
	$update = "update";

	$arr = array();
	if($edit == $display){

		$name = mysqli_query($con,"select * from registration where lid = $lid AND status=1 AND user_type=1");
		if($data = mysqli_fetch_array($name))
		{
			$arr[] = array('fname' => $data['fname'],'lname' => $data['lname'],'lang_flag' => $data['lang_flag'],'phoneno' => $data['phoneno'],'district' => $data['did'],'status' => "success");
		}
		else
		{
			$arr[] = array('status' => "unsuccess");
		}
	}
	elseif($edit == $update)
	{

		$fname = addslashes($_REQUEST['fname']);
		$lname = addslashes($_REQUEST['lname']);
		$lang_flag = $_REQUEST['lang_flag'];
		$district = $_REQUEST['district'];	
		$phoneno = $_REQUEST['phoneno'];
		$update = mysqli_query($con,"UPDATE `registration` SET `fname`='$fname',`lname`='$lname',`lang_flag`='$lang_flag',`did`='$district',`phoneno` = '$phoneno' where lid=$lid AND status=1 AND status = 1");
		
		if($update)
		{
			$arr[] = array('status' => "success");	
		}
		else
		{
			$arr[] = array('status' => " unsuccess");
		}
	}
		$arr1 = array("profile" => $arr);
		echo json_encode($arr1);
}
else
{ 
	echo "Invalid key";
}
?>