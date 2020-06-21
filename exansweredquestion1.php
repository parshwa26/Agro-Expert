<?php

include_once "connection1.php";
ob_start();
$lid = $_REQUEST['lid'];
$arr = array();
$lan1 = mysqli_query($con,"SELECT * FROM `registration` WHERE lid=$lid");
$language = mysqli_fetch_array($lan1);
$array1 = array();
if($language['lang_flag'] == "en"){
	$a = mysqli_query($con,"SELECT * FROM `question` WHERE expertid = $lid");
	while($row = mysqli_fetch_array($a))
	{
		if($row['expertid'] != 0)
		{
			
			$no = mysqli_query($con,"SELECT * FROM `like` where qid=".$row['qid']);
			$countlike = mysqli_num_rows($no);
			
		
			$answer = mysqli_query($con,"SELECT * FROM `answer` WHERE qid= ".$row['qid']." AND visible=1");
			$subcat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row['cid']);
			$row3 = mysqli_fetch_array($subcat);
				
			$cat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row3['pid']);
			$row4 = mysqli_fetch_array($cat);

			$nam= mysqli_query($con,"SELECT * FROM `registration` WHERE lid = ".$row['expertid']);
			$name= mysqli_fetch_array($nam);
		

			while($row1 = mysqli_fetch_array($answer))
			{
				$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],
				'eximage' => $row1['image'],'excontentflag' => $row1['contentflag'],'timestamp' => $row['timestamp'],
				'answer' => $row1['answer'],'category' => $row4['ecategory_flag'],'subcategory' => $row3['ecategory_flag'],
				'exname' => $name['fname']." ".$name['lname'],'totallikes' => $countlike);	
			}
		}
	}
	$array1 = array("status" => "success");
}
elseif($language['lang_flag'] == "gu"){
	$a = mysqli_query($con,"SELECT * FROM `question` WHERE expertid = $lid");
	while($row = mysqli_fetch_array($a))
	{
		if($row['expertid'] != 0)
		{
			
			$no = mysqli_query($con,"SELECT * FROM `like` where qid=".$row['qid']);
			$countlike = mysqli_num_rows($no);
			
		
			$answer = mysqli_query($con,"SELECT * FROM `answer` WHERE qid= ".$row['qid']." AND visible=1");
			$subcat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row['cid']);
			$row3 = mysqli_fetch_array($subcat);
				
			$cat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row3['pid']);
			$row4 = mysqli_fetch_array($cat);

			$nam= mysqli_query($con,"SELECT * FROM `registration` WHERE lid = ".$row['expertid']);
			$name= mysqli_fetch_array($nam);
		

			while($row1 = mysqli_fetch_array($answer))
			{
				$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],
				'eximage' => $row1['image'],'excontentflag' => $row1['contentflag'],'timestamp' => $row['timestamp'],
				'answer' => $row1['answer'],'category' => $row4['gcategory_flag'],'subcategory' => $row3['gcategory_flag'],
				'exname' => $name['fname']." ".$name['lname'],'totallikes' => $countlike);	
			}
		}
	}
	$array1 = array("status" => "success");

}
elseif($language['lang_flag'] == "hi"){
	$a = mysqli_query($con,"SELECT * FROM `question` WHERE expertid = $lid");
	while($row = mysqli_fetch_array($a))
	{
		if($row['expertid'] != 0)
		{
			
			$no = mysqli_query($con,"SELECT * FROM `like` where qid=".$row['qid']);
			$countlike = mysqli_num_rows($no);
			
		
			$answer = mysqli_query($con,"SELECT * FROM `answer` WHERE qid= ".$row['qid']." AND visible=1");
			$subcat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row['cid']);
			$row3 = mysqli_fetch_array($subcat);
				
			$cat = mysqli_query($con,"SELECT * FROM `category` WHERE cid= ".$row3['pid']);
			$row4 = mysqli_fetch_array($cat);

			$nam= mysqli_query($con,"SELECT * FROM `registration` WHERE lid = ".$row['expertid']);
			$name= mysqli_fetch_array($nam);
		

			while($row1 = mysqli_fetch_array($answer))
			{
				$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],
				'eximage' => $row1['image'],'excontentflag' => $row1['contentflag'],'timestamp' => $row['timestamp'],
				'answer' => $row1['answer'],'category' => $row4['hcategory_flag'],'subcategory' => $row3['hcategory_flag'],
				'exname' => $name['fname']." ".$name['lname'],'totallikes' => $countlike);	
			}
		}
	}
	$array1 = array("status" => "success");

}
else
{
	$array1 = array("status" => "unsuccess");

}
$arr1 = array("answerquestion" => $arr,"status" => $array1);

echo json_encode($arr1);

mysqli_close($con);

?>