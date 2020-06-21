<?php  

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$arr = array();

	$lid = $_REQUEST['lid'];

	$lang_flag = $_REQUEST['lang_flag'];
	if($lang_flag=="1"){
		$col = "gdistrict";
	}
	else if($lang_flag=="2"){
		$col = "hdistrict";
	}
	else if($lang_flag=="3"){
		$col = "edistrict";
	}

		$dis = mysqli_query($con,"select did,$col as col from district");

			if(mysqli_num_rows($dis) >0)
			{
				while($district = mysqli_fetch_array($dis))
				{
					$arr[] = array('did' => $district['did'],'district' => $district['col']);
				}
			}
			else
			{
				$arr = array('status' => "unsuccess");
			}
}
else
{
	$arr = array('status' => "Invalid key");
}

		$arr1 = array("districtlist" => $arr);
		echo json_encode($arr1);
?>