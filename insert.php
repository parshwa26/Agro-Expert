<?php
include_once "connection.php";
ob_start();

$a = $_POST['user'];
$b = $_POST['pass'];

$c = mysqli_query($con,"insert into login values('','".$a."','".$b."')");

if($c)
	echo "data inserted";
else
	echo "Problem in inserting data";
?>