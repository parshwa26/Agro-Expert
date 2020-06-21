<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
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

	$arr = array();
		  $name = mysqli_query($con, "SELECT r.lid, r.fname, r.lname, r.phoneno, r.email, u.name AS uname, z.name AS zname, r.lang_flag, c.ecategory_flag, e.cid FROM registration r INNER JOIN unit u ON r.lid = u.lid INNER JOIN expertaction e ON r.lid = e.lid INNER JOIN category c ON e.cid = c.cid INNER JOIN zone z ON r.zid = z.zid where r.lid = '$lid' ");
    			//echo "SELECT r.lid, r.fname, r.lname, r.phoneno, r.email, u.name AS uname, z.name AS zname, r.lang_flag, c.ecategory_flag, e.cid FROM registration r INNER JOIN unit u ON r.lid = u.lid INNER JOIN expertaction e ON r.lid = e.lid INNER JOIN category c ON e.cid = c.cid INNER JOIN zone z ON r.zid = z.zid where r.lid = '$lid' "//;
		if($data = mysqli_fetch_array($name))
		{
			$arr[] = array('name' => $data['fname']." ".$data['lname'],'email' => $data['email'], 'phoneno' => $data['phoneno'], 'zone' => $data['zname'], 'unit' => $data['uname'],'status' => "success");	
		}
		else
		{
			$arr[] = array('status' => "unsuccess");
		}
		$arr1 = array("profile" => $arr);
		echo json_encode($arr1);
}
else
{ 
	echo "Invalid key";
}
?>