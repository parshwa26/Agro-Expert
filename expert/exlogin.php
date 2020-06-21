<?php
include_once "connection1.php";
ob_start();
$edt_phone_number = $_REQUEST['edt_phone_number'];
$lang_flag = $_REQUEST['lang_flag'];
$otp = $_REQUEST['otp'];
$expert = "2";
$arr = array();
	$checkno= mysqli_query($con,"select * from registration where phoneno = $edt_phone_number AND user_type = '$expert'");
	if (mysqli_num_rows($checkno) > 0) 
	{
		$data = mysqli_fetch_array($checkno);
		$lid = $data['lid'];

			$checkno1 = mysqli_query($con,"update registration set lang_flag = $lang_flag where phoneno = $edt_phone_number AND user_type = '$expert' ");

		$arra[] = array('status' => "success",'lid' => $lid,'phoneno' => $edt_phone_number);
		$arr1 = array('Registration' => $arra);
			
	}
	else
	{
		$arra[] = array('status' => "unsuccess");
		$arr1 = array('Registration' => $arra);
	}
echo json_encode($arr1);
?>