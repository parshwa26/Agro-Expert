<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	mysqli_query($con,'SET CHARACTER SET utf8');

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

	$lid = $_REQUEST['lid'];
	$qid = $_REQUEST['qid'];
	$arr = array();
	$array1 = array();
	

			$a = mysqli_query($con,"SELECT * FROM `question` where qid = $qid AND expertid!=0 AND status=1");
			//echo "SELECT * FROM `question` where cid IN ($rcid) AND expertid!=0 AND status=1";
			if(mysqli_num_rows($a)>0)
			{
				while($row = mysqli_fetch_array($a))
				{
					$like1 = mysqli_query($con,"SELECT lid FROM `like` where qid=".$row['qid']." AND status=1");
					$countlike = mysqli_num_rows($like1);
								
					$no1 = mysqli_query($con,"SELECT * FROM `like` where qid='".$row['qid']."' AND lid='".$lid."' AND status=1");
					$liked = mysqli_num_rows($no1);

					$no2 = mysqli_query($con,"SELECT * FROM `starred` where qid='".$row['qid']."' AND lid='".$lid."' AND status=1");
					if(mysqli_num_rows($no2)>0)
						$star = 1;
					else
						$star = 0;
				

					$answer = mysqli_query($con,"SELECT * FROM `answer` WHERE qid= ".$row['qid']." AND visible=1 AND status=1");
					$subcat = mysqli_query($con,"SELECT pid,$col as col,level,cid FROM `category` WHERE cid= ".$row['cid']." AND status=1");
					$row3 = mysqli_fetch_array($subcat);
				
						$category = "";
						$category = $row3['col'];
						$a1 = $row3['pid'];
						//echo $row3['level']."  a  ";
						for($i=0;$i<$row3['level'];$i++){
							$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
							//echo "SELECT $col as col,pid from category where cid='".$a1."'";
							if(mysqli_num_rows($cate)){
									$data1 = mysqli_fetch_array($cate);
									$category = $data1['col']." --> ".$category;	
									//echo $data1['col'];
									$a1=$data1['pid'];
							}
						}
						
					$nam= mysqli_query($con,"SELECT * FROM `registration` WHERE lid = ".$row['expertid']." AND status=1");
					$name= mysqli_fetch_array($nam);
					
					$nam1= mysqli_query($con,"SELECT phoneno FROM `registration` WHERE lid = ".$row['lid']." AND status=1");
					$name1= mysqli_fetch_array($nam1);

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
						$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],'phoneno' => $name1['phoneno'],
						'eximage' => $row1['image'],'excontentflag' => $row1['contentflag'],'timestamp' => $row['timestamp'],
						'answer' => $row1['answer'],'category' => $category,'exname' => $exname,'totallikes' => $countlike,'liked' => $liked,'stared' => $star,'status' => "success");	
					}
				}
			}
	$arr1 = array("detailsearchquestion" => $arr);

		echo json_encode($arr1);

		mysqli_close($con);
}
else
{
	echo "Invalid key";
}
?>