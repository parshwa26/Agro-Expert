<?php
include('connection.php');
session_start();
 if(!checksession() && basename($_SERVER['PHP_SELF'])!="index.php")
{ 
    header('Location:index.php'); 
}

function checklogin($user,$pass)
{
    if (($data = checkindb($user,$pass))!= false) {
        $l = true;
    } else {
        $l = false;
    }
    if ($l == true) {
        $_SESSION['lid'] = $data->lid;
        return true;
    } else {
        session_destroy();
        return false;
    }
}
function checkindb($user,$pass)
{
    global $con;
    $sql = "SELECT * FROM login WHERE email= '$user' and password='$pass'";

    $results = mysqli_query($con,$sql);

    if($results)
    {
        return mysqli_fetch_object($results);
    }
    else
    {
        false;    
    }
}

function checksession()
{
    if(isset($_SESSION['lid']))
    {    
            return true;   
    }
    return false;
}
?>
