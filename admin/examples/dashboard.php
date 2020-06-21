<?php
include('connection.php');
include('session.php');

?>


<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>Agro Admin Dashboard</title>
  <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
  <meta name="viewport" content="width=device-width" />
  <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
  <link href="../assets/css/material-dashboard5438.css?v=1.2.0" rel="stylesheet" />
  <link href="../assets/css/demo.css" rel="stylesheet" />
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="../assets/css/dataTableds.bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css" integrity="sha384-v2Tw72dyUXeU3y4aM2Y0tBJQkGfplr39mxZqlTBDUZAb9BGoC40+rdFCG0m10lXk" crossorigin="anonymous">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/fontawesome.css" integrity="sha384-q3jl8XQu1OpdLgGFvNRnPdj5VIlCvgsDQTQB6owSOHWlAurxul7f+JpUOVdAiJ5P" crossorigin="anonymous">
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script type="text/javascript">
    function myFunction(){
      var value = $('.dataTables_filter input').val();       
    }
  </script>
  <style>

  .topnav {
    overflow: hidden;
    background-color: #333;
  }

  .topnav a {
    float: left;
    display: block;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
  }


  .active {
    background-color: #4CAF50
    color: white;
  }

  .topnav .icon {
    display: none;
  }

  @media screen and (max-width: 991px) {
    .topnav a:not(:first-child) {display: none;}
    .topnav a.icon {
      float: right;
      display: block;
    }
  }

  @media screen and (max-width: 991px) {
    .topnav.responsive {position: relative;}
    .topnav.responsive .icon {
      position: absolute;
      right: 0;
      top: 0;
    }
    .topnav.responsive a {
      float: none;
      display: block;
      text-align: left;
    }
  }

  @media screen and (min-width: 991px){
    .topnav{
      display: none;
    }
  }
</style>
</head>

<body>
  <?php
  include('connection.php');
       // $result =mysqli_query($con,"SELECT * FROM `registration` WHERE user_type='fr'");
  $result4 = mysqli_query($con,"SELECT * FROM `registration` where user_type='fr'");
  $result1 =mysqli_query($con,"SELECT * FROM `registration` WHERE user_type='ex'");
  $result2=mysqli_query($con,"SELECT * FROM `question` ");
  $result3=mysqli_query($con,"SELECT * FROM `question` WHERE expertid!='0'");
  ?>



  <div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="../assets/img/sidebar-1.jpg">
      <div class="logo">
        <center>
          <img class="img img-responsive" src="../assets/img/logo.png" style="height: 50px;">
        </center>

      </div>
      <div class="sidebar-wrapper">
        <ul class="nav">
          <li class="active">
            <a href="dashboard.php">
              <i class="material-icons">dashboard</i>
              <p>Dashboard</p>
            </a>
          </li>
          <li>
            <a href="expert.php">
              <i class="material-icons">person</i>
              <p>Expert</p>
            </a>
          </li>
          <li>
            <a href="category.php">
              <i class="material-icons">person</i>
              <p>Category</p>
            </a>
          </li>

          <li>
            <a href="faqs.php">
              <i class="material-icons">person</i>
              <p>FAQs</p>
            </a>
          </li>
	  <li class="">
                <a href="information.php">
                    <i class="material-icons">person</i>
                    <p>Information</p>
                </a>
            </li>
			<li class="">
                <a href="zone.php">
                    <i class="material-icons">person</i>
                    <p>Zone</p>
                </a>
            </li>
			<li class="">
                <a href="instruction.php">
                    <i class="material-icons">person</i>
                    <p>Instruction</p>
                </a>
            </li>
        </ul>
      </div>
    </div>
    <div class="main-panel">
      <div class="topnav" id="myTopnav">
        <a href="dashboard.php">Dashboard</a>
        <a href="expert.php">Expert</a>
        <a href="category.php">Catetgory</a>
        <a href="faqs.php">FAQs</a>
		<a href="information.php">Information</a>
		<a href="zone.php">Zone</a>
		<a href="instruction.php">Instruction</a>
        <a href="javascript:void(0);" class="icon" onclick="myFunction()">
          <i class="fa fa-bars"></i>
        </a>
      </div>
      <h3 style="padding: 10px 0px 0px 20px;">Admin Dashboard</h3>
      <a href="logout.php"><button class="btn btn-primary" style="float: right; margin-top: -50px;">Log Out</button></a>
      <div class="container">
        <h3>No. of Expert</h3>
        <table class="table table-striped table-bordered table-hover" id="mydata">
          <thead>
            <th><b>ID</b></th>
            <th><b>Name</b></th>
            <th><b>Mobile Number</b></th>
            <th><b>Topic</b></th>
          </thead>
          <tbody>
            <?php
            include('connection.php');
            $result = mysqli_query($con,"SELECT * FROM `registration` where user_type='2' AND status=1");

            $count=1;
            while($reg=mysqli_fetch_array($result)){
             echo "<tr><td>".$count."</td>"; 
             echo   "<td>".$reg['fname']." ".$reg['lname']."</td>";
             echo "<td>".$reg['phoneno']."</td>";

             

             $result2=mysqli_query($con,"select * from expertaction where lid='".$reg['lid']."'");
             if($log1=mysqli_fetch_array($result2)){

              echo "<td>";
              $array = explode(',', $log1['cid']);
              $length=sizeof($array);
              foreach ($array as $cid) {
                $result3=mysqli_query($con,"select * from category where cid='$cid'");
                while($log1=mysqli_fetch_array($result3))
                {
                  if($length>1)
                  {
                    echo $log1['ecategory_flag'].",";
                    $length=$length-1;
                  }

                  else
                  {
                    echo $log1['ecategory_flag'];
                    $length=$length-1;
                  }

                  
                }

              }

              echo "</td>";

              //  $result3=mysqli_query($con,"select * from category where cid='".$log1['cid']."'");
              //  if($cat=mysqli_fetch_array($result3)){

              //   if($cat['pid']==0)
              //   {
              //     echo "<td>".$cat['ecategory_flag']."</td>";
              //   }

              //   else{
              //     $result4=mysqli_query($con,"select * from category where cid='".$cat['pid']."'");
              //     if($cat1=mysqli_fetch_array($result4)){
              //       echo "<td>".$cat1['ecategory_flag']."</td>";
              //     }
              //   }
              // }
              $count++;
              echo "</tr>";
            }
          }
          ?>
        </tbody>
      </table>
      

      <h3>No. of User</h3>
      <table class="table table-striped table-bordered table-hover" id="mydata1">
        <thead class="text-primary">
          <th>ID</th>
          <th>Mobile Number</th>
          <th>District</th>
        </thead>
        <tbody>
          <?php
          $count=1;
          $ty="fr";
          $sql = mysqli_query($con,"select * from registration where user_type='1'");
          while($reg=mysqli_fetch_array($sql)){

           echo "<tr><td>".$count."</td>"; 
           echo "<td>".$reg['phoneno']."</td>";

           $sql1=mysqli_query($con,"select * from district where did='".$reg['did']."'");
           while($reg1=mysqli_fetch_array($sql1)){
             echo "<td>".$reg1['edistrict']."</td></tr>";
           }
           
           $count++;


         }

                                        
         ?>
       </tbody>
     </table>

   </div>
   
  <div class="container">
    <h3>Question Ask By Category</h3>
    <table class="table table table-striped table-bordered table-hover" id="mydata3">
      <thead class="text-primary">
        <th>Topic</th>
        <th>No. of Question</th>
      </thead>
      <tbody>
        <?php

                                        //   $result = mysqli_query($con,"SELECT * FROM `category` where pid='0' and status!='0' ");
                                        //   while( $row = mysqli_fetch_assoc( $result ))
                                        //   {

                                        //      echo   "<tr><td>".$row['ecategory_flag']."</td>";
        $result7= mysqli_query($con,"select a.ecategory_flag as category,COUNT(b.qid) as questions from category a,question b where a.cid=b.cid  group by a.ecategory_flag ");

        while($row1=mysqli_fetch_array($result7))
        {
          echo "<tr><td>".$row1['category']."</td>";
          echo "<td>".$row1['questions']."</td>";
          echo "</tr>";
        }


        ?>
        <tbody>
        </table>
      </div>


    </div>
  </div>
</body>

<script src="../assets/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/js/material.min.js" type="text/javascript"></script>

<script src="../assets/js/chartist.min.js"></script>

<script src="../assets/js/arrive.min.js"></script>

<script src="../assets/js/jquery.sharrre.js"></script>

<script src="../assets/js/perfect-scrollbar.jquery.min.js"></script>

<script src="../assets/js/bootstrap-notify.js"></script>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSaSxv01RBLnlu5EyBHLs57s-IquPaows"></script>
<script src="../assets/js/material-dashboard5438.js?v=1.2.0"></script>
<script src="../assets/js/demo.js"></script>
<script type="text/javascript">
  $(document).ready(function() {


    demo.initDashboardPageCharts();

  });
</script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="../assets/js/jquery.dataTables.min.js"></script>
<script src="../assets/js/dataTables.bootstrap.min.js"></script>
<script>
	$('#mydata').dataTable();
</script>
<script>
	$('#mydata2').dataTable();
</script>
<script>
	$('#mydata1').dataTable();
</script>
<script>
	$('#mydata3').dataTable();
</script>
<script>
  function myFunction() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
      x.className += " responsive";
    } else {
      x.className = "topnav";
    }
  }
</script>
</html>