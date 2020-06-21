<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$lid = $_REQUEST['lid'];
	$lang_flag = $_REQUEST['lang_flag'];

	$arr = array();
	$array1 = array();
	if($lang_flag=="1"){
		$colq = "gquestion";
		$cola = "ganswer";
	}
	else if($lang_flag=="2"){
		$colq = "hquestion";
		$cola = "hanswer";
	}
	else if($lang_flag=="3"){
		$colq = "equestion";
		$cola = "eanswer";
	}
		$a = mysqli_query($con,"SELECT $colq as colq,iid FROM `instruction` where status=1");
		//echo "SELECT $calq as colq FROM `instruction` where status=1";
		while($row = mysqli_fetch_array($a))
		{
			$arr[] = array('iid' => $row['iid'],'title' => $row['colq'],'status' => "success");	

		}
	$arr1 = array("instruction" => $arr);

	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid Key";	
}
?>