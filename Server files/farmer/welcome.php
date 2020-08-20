<?php
include_once "connection1.php";
$key = $_REQUEST['key'];
if($key == "4336f0f199bd35b3275e7b77e6471da3985f963b")
{

	ob_start();
	$arr = array();
	
	$lang_flag = $_REQUEST['lang_flag'];
	if($lang_flag == "1"){
		$arr[] =  array('message' => "અંતમાં વાવેતરની શરત 25,000-30000 એસટીપીમાં ઉગાડવામાં આવે છે. એક હેકટર જમીનમાં 75 સે.મી. પંક્તિ અંતર અને 60 સે.મી. પ્લાન્ટને જાળવી રાખવામાં આવે છે, જેમાં જમીનનું સ્તર 5 સે.મી. ઉપરથી દૂર રાખવામાં આવે છે.",'status' => "success");
	}
	else if($lang_flag == "2"){
		$arr[] =  array('message' => "देर से रोपण की स्थिति 25,000-30000 एसटीपी में उगाई जाती है। एक हेक्टेयर ग्राउंड में 75 सेमी पंक्ति रिक्ति और 60 सेमी संयंत्र बनाए रखा जाता है, जिसमें जमीन का स्तर 5 सेंटीमीटर होता है ऊपर से दूर रखा जाता है",'status' => "success");
	}
	else if($lang_flag == "3"){
		$arr[] =  array('message' => "A Under late planting condition 25000-30000 STP raised settlings are transplanted in one hectare land maintaining 75 cm row spacing   and 60 cm plant to plant spacing leaving 5 cm of shoot above ground level followed by irrigation.",'status' => "success");
	}
	$arr1 = array("welcome" => $arr);
	echo json_encode($arr1);
}
else
{ 
	echo "Invalid key";
}
?>