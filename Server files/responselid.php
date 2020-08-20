<?php
include_once "connection.php";
ob_start();
$number= $_POST['phno'];
//$number = "123456";
$checkno= mysqli_query($con,"select * from login where phoneno = $number");
//$checkno= mysqli_query($con,"update login set phoneno = '1111' where phoneno = $number");
if(mysqli_num_rows($checkno) > 0)
{
	$data = mysqli_fetch_array($checkno);
	$id = $data["lid"];
	echo $id;
}
?>