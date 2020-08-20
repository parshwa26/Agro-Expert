<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$lid = $_REQUEST['lid'];
	$arr = array();

	$qid = $_REQUEST['qid'];

	$lang_flag = $_REQUEST['lang_flag'];
	if($lang_flag=="1"){
		$col = "gcategory_flag";
	}
	else if($lang_flag=="2"){
		$col = "hcategory_flag";
	}
	else if($lang_flag=="3"){
		$col = "ecategory_flag";
	}

	$array1 = array();
	

		$a = mysqli_query($con,"SELECT q.expertid,q.timestamp,q.question,q.cid,c.level,c.pid,q.qid,q.lid,c.$col as col FROM `question` q,category c WHERE q.cid = c.cid AND q.qid =$qid AND q.expertid = $lid ORDER BY timestamp DESC");
		//echo "SELECT q.question,q.cid,c.level,c.pid,q.qid,c.$col as col FROM `question` q,category c WHERE q.cid = c.cid AND q.qid =$qid AND q.expertid = $lid ORDER BY timestamp DESC";
		if($a){
			while($row = mysqli_fetch_array($a))
			{

					$category = "";
					$category = $row['col'];
					$a1 = $row['pid'];
			//		echo $row['level']."  a  ";
					for($i=0;$i<$row['level'];$i++){
						$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
					//echo "SELECT $col as col,pid from category where cid='".$a1."'";
						if(mysqli_num_rows($cate)){
								$data1 = mysqli_fetch_array($cate);
								$category = $data1['col']." --> ".$category;	
								//echo $data1['col']."<br>";
								$a1=$data1['pid'];
						}
					}			
				
					$answer = mysqli_query($con,"SELECT * FROM `answer` WHERE qid= ".$row['qid']." AND visible=1");

					$no2 = mysqli_query($con,"SELECT * FROM `starred` where qid='".$row['qid']."' AND lid='".$lid."' AND status=1");
					if(mysqli_num_rows($no2)>0)
						$star = 1;
					else
						$star = 0;
			

					$nam= mysqli_query($con,"SELECT * FROM `registration` WHERE lid = ".$row['expertid']);
					$name= mysqli_fetch_array($nam);
					
					$getphno1 = mysqli_query($con,"select phoneno from registration where lid = '".$row['lid']."'");
					$getphno = mysqli_fetch_array($getphno1);

					$display = mysqli_query($con,"SELECT display_name from expertaction where lid = '".$name['lid']."' AND status=1 AND display_name=2");
					if(mysqli_num_rows($display) > 0){
						$zon = mysqli_query($con,"SELECT name from unit where lid = '".$name['lid']."' AND status=1 AND status=1");
						$zone = mysqli_fetch_array($zon);
						$exname=$zone['name']." Unit Expert ";
					}
					else{
						$exname=$name['fname']." ".$name['lname'];	
					}
					

					while($row1 = mysqli_fetch_array($answer))
					{
						$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],'phoneno' => $getphno['phoneno'],
						'eximage' => $row1['image'],'excontentflag' => $row1['contentflag'],'timestamp' => $row['timestamp'],
						'answer' => $row1['answer'],'category' => $category,'stared' => $star,
						'exname' => $exname,'status' => "success");	
					}
			}
	}
	else
	{
		$arr[] = array('status' => "unsuccess");

	}
	$arr1 = array("answerquestion" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid code";
}
?>