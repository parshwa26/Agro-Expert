<?php
	ob_start();
	include_once "connection1.php";
	$otp = $_REQUEST['otp'];
	$phone_number = $_REQUEST['edt_phone_number'];
	//header("location:http://2factor.in/API/V1/3a26e3b8-2c07-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");
	//1900486c-25e0-11e8-a895-0200cd936042
//	header("location:http://2factor.in/API/V1/7d0c1059-2dc4-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");
//	header("location:http://2factor.in/API/V1/1900486c-25e0-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");

//header("location:http://2factor.in/API/V1/200d010d-5843-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");
	//header("location:http://2factor.in/API/V1/8cf4fb35-2fd7-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");
	//header("location:http://2factor.in/API/V1/a6b8df98-7845-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");



//	header("location:http://2factor.in/API/V1/1900486c-25e0-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");

	

		header("location:http://2factor.in/API/V1/3f6eff5a-549d-11e9-a6e1-0200cd936042/SMS/+91$phone_number/$otp");

//header("location:http://2factor.in/API/V1/b4bbb14e-cc8f-11e8-a895-0200cd936042/SMS/+91$phone_number/$otp");
//	file_get_contents("http://10.152.72.242:2015/SMSG/index.php?mobile=".$phone_number."&message=Your+OTP+is+".$otp."&sid=JAUITC&itkey=46b7ce99b5ed5cdb54e0bc84a76a7504");

	
?>
