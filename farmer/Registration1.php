<?php

include_once "connection1.php";

ob_start();

/*$edt_phone_number = $_REQUEST['edt_phone_number'];

$otp = $_REQUEST['otp'];



$a= "qwew";

$otp="1256";

$edt_phone_number="9157578750";

header("location:otpverify.php?otp=$otp&edt_phone_number=$edt_phone_number");

$update = mysqli_query($con,"update login set district='".$edt_phone_number."' where phoneno = $edt_phone_number");

*/

$key = $_REQUEST['key'];

if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")

{



	$edt_district= "";

	$edt_district = $_REQUEST['edt_district'];

	$edt_phone_number = $_REQUEST['edt_phone_number'];

	$lang_flag = $_REQUEST['lang_flag'];

	//$otp = $_REQUEST['otp'];

	$edt_fname="";

	$edt_lname="";

	$edt_pincode="";

	$arr = array();

	$arra = array();

	$checkno= mysqli_query($con,"select * from registration where phoneno = $edt_phone_number AND status=1");

	if (mysqli_num_rows($checkno) > 0) {



		$data = mysqli_fetch_array($checkno);

		$id = $data["lid"];

		//$update = mysqli_query($con,"update otp set otp=$otp where lid = $id");

		$update1 = mysqli_query($con,"update registration set lang_flag = '$lang_flag' where lid = $id");

		

		$arra[] = array('lid' => $id,'phoneno' => $edt_phone_number,'district' => $data["did"]);

		$arr[] = array('registered' => "Already Registered", 'detail' => $arra);

		$arr1 = array("Registration" => $arr);

		

	//	header("location:otpverify.php?otp=$otp&edt_phone_number=$edt_phone_number");

		echo json_encode($arr1);

	//exit();

	}

	else

	{

		$select = mysqli_query($con,"select max(lid) as lid1 from registration");

		if($data=mysqli_fetch_array($select))

		{

			$no = $data['lid1'];

			$no=$no+1;

		}

		else

		{

			$no=1;

		}

		

		$c = mysqli_query($con,"insert into registration(lid,did,zid,fname,lname,email,phoneno,lang_flag,user_type,status) values('$no','$edt_district','','$edt_fname','$edt_lname','','$edt_phone_number','$lang_flag','1','1')");



		//$otp1 = mysqli_query($con,"insert into otp values('$no','$otp','1')");

		if($c)

		{

			$array[] = array('lid' => $no,'phoneno' => $edt_phone_number);

			$arr[] = array('registered' => "Registration Successful",'detail' => $array);

			$arr1 = array("Registration" => $arr);

		}

		else

		{

			$arr1 = array('registered' => "Registration Unsuccessful");

		}

		

	//	header("location:otpverify.php?otp=$otp&edt_phone_number=$edt_phone_number");

		echo json_encode($arr1);

	}

}

else

{

	echo "Invalid key";

}

?>

