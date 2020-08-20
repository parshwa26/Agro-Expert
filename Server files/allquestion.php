<?php
include_once "connection.php";
ob_start();
$sql = "SELECT * FROM question";
$result = mysqli_query($con, $sql);
//$arr = array();
if (mysqli_num_rows($result) > 0) {

    while($row = mysqli_fetch_array($result)) {
	echo $row['question']."\n";
/*        $args['cid'] = $row['cid'];
	 $args['question'] = $row['question'];
	$args['image'] = $row['image'];
	$args['authorityid'] = $row['authorityid'];
	array_push($arr,$args);*/
    }
}
//	$arr1 = array('allquestion' => $arr);
//	echo json_encode($arr1);
}
?>
