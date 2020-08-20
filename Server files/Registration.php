<?php
include_once "connection.php";
ob_start();
$edt_fname = $_POST['edt_fname'];
$edt_lname = $_POST['edt_lname'];
$edt_district = $_POST['edt_district'];
$edt_pincode = $_POST['edt_pincode'];
$edt_phone_number = $_POST['edt_phone_number'];
$edt_password = $_POST['edt_password'];
$lang_flag = $_POST['lang_flag'];
/*$lang_flag = "en";
$edt_fname = "parshwa";
$edt_lname= "shah";
$edt_district= "Anand";
$edt_pincode= "380013";
$edt_phone_number= "9157578750";
$edt_password= "123456";
$edt_confirm_password= "123456";*/

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

//echo $no;
$c = mysqli_query($con,"insert into registration values('$no','$edt_fname','$edt_lname','$edt_district','$edt_pincode','$lang_flag')");
$b = mysqli_query($con,"insert into login values('$no1','$no','$edt_phone_number','$edt_password')");
echo $b;
if($c && $b)
	echo "data inserted";
else
	echo "Problem in inserting data";
?>