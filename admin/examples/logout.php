<?php
session_start();
session_destroy();
header("Location:http://online.jau.in:2015/agriex/admin/examples/index.php");
?>