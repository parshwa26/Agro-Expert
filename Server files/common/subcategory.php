<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{	
	ob_start();
	$cat = $_REQUEST['cid'];
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

	$subcat = array();
	$select = mysqli_query($con,"select $col as col,cid from category where pid = $cat AND status=1");
	//$row=mysqli_fetch_array($select);
 //echo "select * from category where pid =".$row['cid']." ";

	//$row_nxt=mysqli_fetch_array($select);

	//echo "select $col,cid from category where pid = $cat AND status=1";
	

		while($row = mysqli_fetch_array($select))
			{
					$select_nxt = mysqli_query($con,"select * from category where pid =".$row['cid']." ");
					$cnt=mysqli_num_rows($select_nxt);
					if($cnt <= 0)
					{
						$msg_nxt="fail";
					}
					else
					{
						$msg_nxt="success";
					}
			 		$subcat[] = array('status' => 'success','status_next' => $msg_nxt,'pid' => $row['cid'],'category_flag' => $row['col']);
			}
			
			$arr1 = array("subcategory" => $subcat);
			
	echo json_encode($arr1);
}
else
{
	echo "Invalid key";
}
?>