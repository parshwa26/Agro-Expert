<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	mysqli_query($con,'SET CHARACTER SET utf8');

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

	$arr = array();

		$array1 = array();
		$getcid1 = mysqli_query($con,"select cid from expertaction where lid = $lid");
		$getcid = mysqli_fetch_array($getcid1);
		$cid1 = $getcid['cid'];

		$chk = mysqli_query($con,"select rcid from category c INNER JOIN expertaction e ON c.cid IN ($cid1) where e.lid = $lid");
		//echo "select rcid from category c INNER JOIN expertaction e ON c.cid IN (1,2,3) where e.lid = $lid";
	//		echo "select * from expertaction where lid = $lid";
		if($chk){	
			while($check = mysqli_fetch_array($chk))
			{
				$abcd = $check['rcid'];
				$a = mysqli_query($con,"SELECT * FROM question WHERE cid IN ($abcd) AND expertid = 0 ORDER BY qid DESC");

				//echo "SELECT * FROM question WHERE cid IN ($abcd) AND expertid = 0 ORDER BY qid DESC";

				while($row = mysqli_fetch_array($a))
				{

					//echo "SELECT $col as col,level,pid FROM category WHERE cid = ".$row['cid'];
					$cat =  mysqli_query($con,"SELECT $col as col,level,pid FROM category WHERE cid =". $row['cid']);

					$cat1 = mysqli_fetch_array($cat);
					$category = "";
					$category = $cat1['col'];
					$a1 = $cat1['pid'];
			//		echo $row['level']."  a  ";
					for($i=0;$i<$cat1['level'];$i++){
						$cate = mysqli_query($con,"SELECT $col as col,pid from category where cid='".$a1."'");
			//			echo "SELECT $col as col,pid from category where cid='".$a1."'";
						if(mysqli_num_rows($cate)){
								$data1 = mysqli_fetch_array($cate);
								$category = $data1['col']." --> ".$category;	
								//echo $data1['col']."<br>";
								$a1=$data1['pid'];
						}
					}
			
					$arr[] = array('qid' => $row['qid'],'question' => $row['question'],'image' => $row['image'],'contentflag' => $row['contentflag'],'timestamp' => $row['timestamp'],'category' => $category,'status' => "success");	

				}
			}
		}
		else
		{
			$arr = array('status' => "unsuccess");

		}
	$arr1 = array("unanswerquestion" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid Key";
}
?>