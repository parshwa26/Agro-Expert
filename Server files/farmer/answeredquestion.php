<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$lid = $_REQUEST['lid'];
	$arr = array();
	$array1 = array();
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

		$a = mysqli_query($con,"SELECT * FROM `question` WHERE lid=$lid AND status='1' AND expertid!='0'");
		while($row = mysqli_fetch_array($a))
		{

			if($row['expertid'] != 0)
			{
					
				$cat = mysqli_query($con,"SELECT cid,$col as col,pid,level FROM `category` WHERE cid= ".$row['cid']);
				$row4 = mysqli_fetch_array($cat);

				//echo "SELECT cid,$col as col,pid,level FROM `category` WHERE cid= ".$row['cid'];

					$category = "";
					$category = $row4['col'];
					$a1 = $row4['pid'];
					//echo $row4['level']."  1a  ";
					for($i=0;$i<$row4['level'];$i++){
						$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
						//echo "SELECT $col as col,pid from category where cid='".$a1."'";
						if(mysqli_num_rows($cate)){
								$data1 = mysqli_fetch_array($cate);
								$category = $data1['col']." --> ".$category;	
								//echo $data1['col'];
								$a1=$data1['pid'];
						}
					}

					$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'category' => $category,"status" => "success");	
			}
		}
	$arr1 = array("answerquestion" => $arr);

	echo json_encode($arr1);
	mysqli_close($con);
}

else
{
	echo "Invalid key";
}
?>