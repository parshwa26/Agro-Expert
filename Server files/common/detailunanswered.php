<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	mysqli_query($con,'SET CHARACTER SET utf8');

	$lid = $_REQUEST['lid'];
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

	$arr = array();

		$a = mysqli_query($con,"SELECT q.question,q.image,q.contentflag,q.timestamp,q.cid,c.level,q.qid,c.pid,c.$col as col FROM question q,category c WHERE q.lid=$lid AND q.cid=c.cid AND q.qid = $qid ORDER BY q.qid DESC");

		//echo "SELECT q.question,q.image,q.contentflag,q.timestamp,q.cid,c.level,c.pid,c.$col as col FROM question q,category c WHERE q.lid=$lid AND q.cid=c.cid ORDER BY q.qid DESC";
		//echo "SELECT * FROM `question` WHERE lid=$lid ORDER BY qid DESC";
		if(mysqli_num_rows($a)>0)
		{

			
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
							//echo $data1['pid'];
							$a1=$data1['pid'];
					}
				}

					$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],
					'timestamp' => $row['timestamp'],'category' => $category,'status' => "success");	
			}
		}
		else
		{
			$arr[] = array('status' => "unsuccess");
		}
	$arr1 = array("unanswerquestion" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid key";
}
?>