<?php
//error_reporting(0);
include_once "connection.php";
ob_start();
$image = "";
$lid = $_REQUEST['lid'];
$question = $_REQUEST['question'];
$image = $_REQUEST['image'];
$flag = $_REQUEST['flag'];
$cid = $_REQUEST['cid'];
$select = mysqli_query($con,"select max(qid) as qid1 from question");
	if($data=mysqli_fetch_array($select))
	{
		$no = $data['qid1'];
		$no=$no+1;
	}
	else
	{
		$no=1;
	}
//echo $no;

$arr = array();
$sql = "insert into question values($no,$lid,$cid,'".$question."','".$image."','0','".$flag."')";
$result = mysqli_query($con,$sql);
if($result)
	$arr = array("status" => "Question posted");
else
	$arr = array("status" => "Error in posting");

$arr1 = array("insertquestion" => $arr);
echo json_encode($arr1);

mysqli_close($con);
?>