<?php

include_once "connection.php";
ob_start();
$lid = $_post['lid'];
$question = $_post['question'];
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
	
?>