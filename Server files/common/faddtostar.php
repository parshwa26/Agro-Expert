<?php

include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{
	ob_start();
	$lid = $_REQUEST['lid'];
	$fid = $_REQUEST['fid'];
	$arr = array();
		
		$check = mysqli_query($con,"select * from `starred` where lid = $lid AND fid = $fid AND status=1");
		if(!($chk = mysqli_fetch_array($check)))
		{
			$select = mysqli_query($con,"select max(sid) as sid from `starred`");
			if($data=mysqli_fetch_array($select))
			{
				$no = $data['sid'];
				$no=$no+1;
			}
			else
			{
				$no=1;
			}
			$insert = mysqli_query($con,"insert into `starred` (sid,lid,fid,status) values($no,$lid,$fid,1)");
			if($insert)
				$arr[] = array('status' => "success");
			else
				$arr[] = array('status' => "unsuccess");
		}
		else
		{
			$select = mysqli_query($con,"delete from `starred` where lid = $lid AND qid = $qid ");
			if($select)
					$arr[] = array('status' => "unsuccess");

		}


	echo json_encode($arr);
}
else
{
	echo "Invalid key";
}
?>