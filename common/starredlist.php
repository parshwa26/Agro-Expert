<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	
	ob_start();
	$lid = $_REQUEST['lid'];
	$arr = array();
	$lan1 = mysqli_query($con,"SELECT * FROM `registration` WHERE lid=$lid");
	$language = mysqli_fetch_array($lan1);
	$array1 = array();

	$lang_flag = $_REQUEST['lang_flag'];
	if($lang_flag=="1"){
		$col = "gcategory_flag";
		$colq = "gquestion";
		$cola = "ganswer";
	}
	else if($lang_flag=="2"){
		$col = "hcategory_flag";
		$colq = "hquestion";
		$cola = "hanswer";
	}
	else if($lang_flag=="3"){
		$col = "ecategory_flag";
		$colq = "hquestion";
		$cola = "hanswer";
	}


		$star = mysqli_query($con,"SELECT * FROM `starred` WHERE lid=$lid AND status=1");
		while($data = mysqli_fetch_array($star))
		{

			$a = mysqli_query($con,"SELECT * FROM `question` WHERE qid=".$data['qid']." AND status=1");
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
					for($i=0;$i<$row4['level'];$i++)
					{
						$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
						//echo "SELECT $col as col,pid from category where cid='".$a1."'";
						if(mysqli_num_rows($cate))
						{
								$data1 = mysqli_fetch_array($cate);
								$category = $data1['col']." --> ".$category;	
								//echo $data1['col'];
								$a1=$data1['pid'];
						}
					}
						$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'category' => $category,'status' => "success");	
				}
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