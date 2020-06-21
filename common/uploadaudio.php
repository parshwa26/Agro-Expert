<?php
include_once "connection1.php";
ob_start();
 if($_SERVER['REQUEST_METHOD']=='POST'){
 $file_name = $_FILES['myFile']['name'];
 $file_size = $_FILES['myFile']['size'];
 $file_type = $_FILES['myFile']['type'];
 $temp_name = $_FILES['myFile']['tmp_name'];
 
 $location = "../audio/";
 
 move_uploaded_file($temp_name, $location.$file_name);
 echo "audio/".$file_name;

 move_uploaded_file($temp_name, $location.$file_name);
 }else{
 echo "Error";
 }
?>