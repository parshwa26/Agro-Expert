<?php
//error_reporting(0);
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$image = "";
	$lid = $_REQUEST['lid'];
	$question = addslashes($_REQUEST['question']);
	$image = $_REQUEST['image'];
	$cid = $_REQUEST['cid'];
	$flag = $_REQUEST['flag'];
	$file = $_REQUEST['file'];

	$select = mysqli_query($con,"select max(qid) as qid1 from question");
		if($data=mysqli_fetch_array($select))
		{
			$no = $data['qid1'];
			$no=$no+1;
		}
		else
		{
			$no=1;
		}
	//echo $no;

	$arr = array();
	date_default_timezone_set('Asia/Kolkata');
	$date = date('Y-m-d H:i:s');  
	$sql = "insert into question values($no,$lid,$cid,'0',N'".$question."','".$image."','".$date."','".$flag."','1','0')";
	//file_put_contents("abc.txt", $sql);

	$result = mysqli_query($con,$sql);
	if($result)
		$arr[] = array("status" => "Question posted");
	else
		$arr[] = array("status" => "Error in posting");

	$arr1 = array("insertquestion" => $arr);
	echo json_encode($arr1);

	mysqli_close($con);
}
else
{
	echo "Invalid key";
}
?>