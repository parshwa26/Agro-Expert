<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$lid = $_REQUEST['lid'];
	$arr = array();

	$lid = $_REQUEST['lid'];
	
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
	
		$a = mysqli_query($con,"SELECT q.question,q.cid,c.level,c.pid,q.qid,c.$col as col FROM `question` q,category c WHERE q.cid = c.cid AND q.expertid = $lid ORDER BY timestamp DESC");

		//echo "SELECT q.question,q.cid,c.level,c.pid,q.qid,c.$col as col FROM `question` q,category c WHERE q.cid = c.cid AND q.expertid = $lid ORDER BY timestamp DESC";
		if($a){
			while($row = mysqli_fetch_array($a))
			{

				$category = "";
				$category = $row['col'];
				$a1 = $row['pid'];
		//		echo $row['level']."  a  ";
				for($i=0;$i<$row['level'];$i++){
					$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
		//			echo "SELECT $col as col,pid from category where cid='".$a1."'";
					if(mysqli_num_rows($cate)){
							$data1 = mysqli_fetch_array($cate);
							$category = $data1['col']." --> ".$category;	
							//echo $data1['col']."<br>";
							$a1=$data1['pid'];
					}
				}

					$subcat = mysqli_query($con,"SELECT pid,$col as col FROM `category` WHERE cid= ".$row['cid']);
					$row3 = mysqli_fetch_array($subcat);
						
					$cat = mysqli_query($con,"SELECT $col as col FROM `category` WHERE cid= ".$row3['pid']);
					$row4 = mysqli_fetch_array($cat);

				
						$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'category' => $category,'status' => "success");	
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