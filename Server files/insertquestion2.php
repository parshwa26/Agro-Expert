<?php
//error_reporting(0);
include_once "connection.php";
ob_start();
function base64_to_jpeg($base64_string, $output_file) {
    // open the output file for writing
    $ifp = fopen( $output_file, 'wb' ); 

    // split the string on commas
    // $data[ 0 ] == "data:image/png;base64"
    // $data[ 1 ] == <actual base64 string>
    $data = explode( ',', $base64_string );

    // we could add validation here with ensuring count( $data ) > 1
    fwrite( $ifp, base64_decode( $data[ 1 ] ) );

    // clean up the file resource
    fclose( $ifp ); 

    return $output_file; 
}



$image = "";
$lid = $_REQUEST['lid'];
$question = $_REQUEST['question'];
$image = $_REQUEST['image'];
$cid = $_REQUEST['pid'];
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
	define('UPLOAD_DIR', 'images/');
    $image_parts = explode(";base64,", $image);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type = $image_type_aux[1];
    $image_base64 = base64_decode($image_parts[1]);
    $file = UPLOAD_DIR . uniqid() . '.jpg';
    file_put_contents($file, $image_base64);
base64_to_jpeg($image,$file);

$arr = array();
date_default_timezone_set('Asia/Kolkata');
$date = date('Y-m-d H:i:s');  
$sql = "insert into question values($no,$lid,$cid,'".$question."','".$file."','0','".$date."','0')";
$result = mysqli_query($con,$sql);
if($result)
	$arr[] = array("status" => "Question posted");
else
	$arr[] = array("status" => "Error in posting");

$arr1 = array("insertquestion" => $arr);
echo json_encode($arr1);

mysqli_close($con);
?>