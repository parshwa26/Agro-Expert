<?php
//echo "time is".date("d/m/Y");
ob_start();
$timezone = date_default_timezone_get();
echo "The current server timezone is: " . $timezone."<br>";

date_default_timezone_set('Asia/Kolkata');
  echo date('Y-m-d H:i:s');  

?>