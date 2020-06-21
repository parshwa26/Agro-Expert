<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$lid = $_REQUEST['lid'];
	$arr = array();
	$lan1 = mysqli_query($con,"SELECT * FROM `registration` WHERE lid=$lid AND status=1");
	$language = mysqli_fetch_array($lan1);
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
	if($language['lang_flag'] == "3"){
		$a = mysqli_query($con,"SELECT * FROM `faq` where status=1");
		while($row = mysqli_fetch_array($a))
		{
				$no = mysqli_query($con,"SELECT * FROM `like` where qid=".$row['fid']." AND status=1");
				$countlike = mysqli_num_rows($no);
				
				$no1 = mysqli_query($con,"SELECT * FROM `like` where qid='".$row['fid']."' AND lid='".$lid."' AND status=1");
				$d = mysqli_num_rows($no1);
				$liked = $d;		

				$no2 = mysqli_query($con,"SELECT * FROM `starred` where qid='".$row['fid']."' AND lid='".$lid."' AND status=1");
				$d1 = mysqli_num_rows($no2);
				$star = $d1;
				

				$subcat = mysqli_query($con,"select $col as col,pid FROM `category` WHERE cid= ".$row['cid']." AND status=1");
				//echo "select $col as col,pid FROM `category` WHERE cid= ".$row['cid']." AND status=1	";
				$row3 = mysqli_fetch_array($subcat);
					
				$cat = mysqli_query($con,"SELECT $col as col FROM `category` WHERE cid= ".$row3['pid']." AND status=1");
				$row4 = mysqli_fetch_array($cat);
			
				$arr[] = array('fid' => $row['fid'],'question' => $row['equestion'],'answer' => $row['eanswer'],'category' => $row4['col'],
						 'subcategory' => $row3['col'],'totallikes' => $countlike,'liked' => $liked,'stared' => $star);	

		}
		$array1 = array("status" => "success");
	
	}

	$arr1 = array("faq" => $arr,"status" => $array1);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid Key";	
}
?>