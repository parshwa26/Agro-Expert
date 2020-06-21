<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$cat = array();

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
	$select = mysqli_query($con,"SELECT cid,$col as col from category where pid = 0 AND status=1");
	
		while($row = mysqli_fetch_array($select))
		{
		    $cat[] = array('cid' => $row['cid'],'category_flag' => $row['col']);
		}
	$arr1 = array("category" => $cat);
	echo json_encode($arr1);
}
else
{
	echo "Invalid key";
}
?>