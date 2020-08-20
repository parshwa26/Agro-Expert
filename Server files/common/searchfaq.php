<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$lid = $_REQUEST['lid'];
	$cid = $_REQUEST['cid'];
	$lang_flag = $_REQUEST['lang_flag'];
	$arr = array();
	$array1 = array();

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
		$colq = "equestion";
		$cola = "eanswer";
	}

		$cat = mysqli_query($con,"SELECT cid,rcid FROM `category` where cid = $cid AND status=1");
			//echo "SELECT cid,rcid FROM `category` where cid = $cid AND status=1";
	
				$catres= mysqli_fetch_array($cat);

				if(mysqli_num_rows($cat)>0)
				{

					if($catres['rcid']!="")
					{
						$rcid = $catres['rcid'];
						$a = mysqli_query($con,"SELECT fid,cid,$colq as colq,$cola as cola FROM `faq` where cid IN ($rcid) AND status=1");	
						//echo "SELECT fid,cid,$colq as colq,$cola as cola FROM `faq` where cid IN ($rcid) AND status=1";
					}
					else
					{
						$rcid = $catres['cid'];
						$a = mysqli_query($con,"SELECT fid,cid,$colq as colq,$cola as cola FROM `faq` where cid IN ($rcid) AND status=1");	
						//echo "11 SELECT fid,cid,$colq as colq,$cola as cola FROM `faq` where cid IN ($rcid) AND status=1";
					}
				while($row = mysqli_fetch_array($a))
				{
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
					
						$arr[] = array('fid' => $row['fid'],'question' => $row['colq'],'answer' => $row['cola'],'category' => $category,'status' => "success");	
				}
			}
			else{
				//$arr[] = array('status' => "unsuccess");
			}
			
	$arr1 = array("searchfaq" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid Key";	
}
?>