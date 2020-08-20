<?php

include_once "connection1.php";
ob_start();
$lid = $_REQUEST['lid'];
$qid = $_REQUEST['qid'];
$answer = addslashes($_REQUEST['answer']);
$subanswer = $_REQUEST['subanswer'];
$file = $_REQUEST['file'];
$flag = $_REQUEST['flag'];


// subanswer to check whether answer is sub or not
//where subanswer = answer means main answer and answer = subanswer 
$arr = array();
$a = "answer";
if($subanswer == $a)
{

	$check = mysqli_query($con,"select * from `question` where expertid=$lid AND qid=$qid AND status=1");
//	echo "select * from `question` where expertid=$lid AND qid=$qid AND status=1";
	if(!($data1=mysqli_fetch_array($check)))
	{
		$select = mysqli_query($con,"select max(aid) as aid from `answer`");
		if($data=mysqli_fetch_array($select))
		{
			$aid = $data['aid'];
			$aid=$aid+1;
		}
		else
		{
			$aid=1;
		}
		$insert = mysqli_query($con,"insert into `answer` values ($aid,$qid,$lid,N'".$answer."','1','".$file."','".$flag."','1','0')");
		$update = mysqli_query($con,"update `question` set expertid=$lid where qid='".$qid."'");
		if($insert && $update)
			$arr[] = array('status' => "success");
		else
			$arr[] = array('status' => "unsuccess");
	}
	else
	{
		$insert = mysqli_query($con,"update `answer` set answer=N'".$answer."' where qid=$qid");
		$update = mysqli_query($con,"update `question` set expertid=$lid where qid=$qid");
		if($insert && $update)
			$arr[] = array('status' => "success");
		else
			$arr[] = array('status' => "unsuccess");	
	}
}
else
{
	$check = mysqli_query($con,"select * from `answer` where  lid=$lid AND qid=$qid AND status=1");
	if(!($data1=mysqli_fetch_array($check)))
	{
		$select = mysqli_query($con,"select max(aid) as aid from `answer`");
		if($data=mysqli_fetch_array($select))
		{
			$aid = $data['aid'];
			$aid=$aid+1;
		}
		else
		{
			$aid=1;
		}		
		$insert = mysqli_query($con,"insert into `answer` values ($aid,$qid,$lid,N'".$answer."','0','".$file."','".$flag."','1',$qid)");
		if($insert)
			$arr[] = array('status' => "success");
		else
			$arr[] = array('status' => "unsuccess");
	}
	else
	{
		$insert = mysqli_query($con,"update `answer` set answer=N'".$answer."' where qid=$qid AND lid=$lid AND status=1");
		if($insert)
			$arr[] = array('status' => "success");
		else
			$arr[] = array('status' => "unsuccess");
				
	}
}
echo json_encode($arr);
?>