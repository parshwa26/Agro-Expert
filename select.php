<?php
include_once "connection1.php";
ob_start();
$sql = "SELECT * FROM `registration`";
$result = mysqli_query($con, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        echo "id: " . $row["lid"]." ".$row["fname"]." ".$row["lname"]." ".$row["lang_flag"]." ".$row["user_type"]."<br>";    }
} else {
    echo "0 results";
}


$sql = "SELECT * FROM `login`";
$result = mysqli_query($con, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        echo "id: " . $row["lid"]." ".$row["phoneno"]."<br>";    }
} else {
    echo "0 results";
}

?>