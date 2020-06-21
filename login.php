<?php
include_once "connection.php";
ob_start();
$edt_district = $_POST['edt_district'];
$edt_phone_number = $_POST['edt_phone_number'];
$lang_flag = $_POST['lang_flag'];
$otp = $_POST['one'];

$edt_fname=" ";
//$otp="123";
$edt_lname=" ";
$edt_district= " ";
$edt_pincode=" ";
//$lang_flag =" ";
//$edt_district=" ";
//$edt_phone_number ="12345678";
$checkno= mysqli_query($con,"select * from login where phoneno = $edt_phone_number");
if (mysqli_num_rows($checkno) > 0) {

	$data = mysqli_fetch_array($checkno);
	$id = $data["lid"];
	$update = mysqli_query($con,"update otp set otp=$otp where lid = $id");
}
else
{
	$select = mysqli_query($con,"select max(rid) as rid1 from registration");
	if($data=mysqli_fetch_array($select))
	{
		$no = $data['rid1'];
		$no=$no+1;
	}
	else
	{
		$no=1;
	}
$select = mysqli_query($con,"select max(lid) as rid1 from login");
	if($data=mysqli_fetch_array($select))
	{
		$no1 = $data['rid1'];
		$no1=$no1+1;
	}
	else
	{
		$no1=1;
	}
	$c = mysqli_query($con,"insert into registration values('$no','$edt_fname','$edt_lname','$edt_pincode','$lang_flag','fr')");
	$b = mysqli_query($con,"insert into login values('$no1','$no','$edt_phone_number','$edt_district')");
	$otp = mysqli_query($con,"insert into otp values('$no1','$otp')");
	echo $otp;
	if($c || $b || $otp)
		echo "data inserted";
	else
		echo "Problem in inserting data";
}
//echo $no;	
?>