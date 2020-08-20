<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{	
	ob_start();
	$cid = $_REQUEST['cid'];

	$informative = array();
	$select = mysqli_query($con,"select content_text,content_desc,image,pdf,audio,video from content where cid = $cid AND status=1");
	if(mysqli_num_rows($select)>0){
		while($row = mysqli_fetch_array($select))
			{
		 		$informative[] = array('status' => 'success','content_title' => $row['content_text'],'content_desc' => $row['content_desc'],'image' => $row['image'],'pdf' => $row['pdf'],'audio' => $row['audio'],'video' => $row['video']);
			}
			$arr1 = array("informative" => $informative);
		}
		else
		{
			$informative[] = array('status' => 'unsuccess');
			$arr1 = array("informative" => $informative);
		}
	echo json_encode($arr1);
}
else
{
	echo "Invalid key";
}
?>