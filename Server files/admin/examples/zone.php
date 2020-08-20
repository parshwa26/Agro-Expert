<?php
include('connection.php');
include('session.php');

if(isset($_POST['edit'])){
	$mid = $_POST['zone'];
    $_SESSION['mid'] = $mid;
	
	$query1 = mysqli_query($con, "SELECT m.mid,z.zid, z.name, d.edistrict from zone z INNER JOIN `master` m ON z.zid = m.zid INNER JOIN district d ON m.did = d.did where m.mid = '$mid'");
	$row_query2=mysqli_fetch_array($query1);
	
	$zone1 = $row_query2['name'];
	$district1 = $row_query2['edistrict'];
	
	
}

if(isset($_POST['submit_edit'])){
	$m_id=$_SESSION['mid'];
	$zone2 = $_POST['zone'];
	
	$length1=sizeof($_POST['district']);
    foreach ($_POST['district'] as $district)
    {
    // $cat=$cat.$category.",";
          
             $dd_id1=$district;
                $length=$length-1;
				$update = mysqli_query($con ,"update `master` set did='$dd_id1' where mid = '$m_id'");
        
    }
	$row = mysqli_query($con, "SELECT zid FROM  `master` WHERE mid = '$m_id'");
	while($row_result=mysqli_fetch_array($row)){
				$update1 = mysqli_query($con, "update `zone` set name = '$zone2' where zid = '".$row_result['zid']."'");
			
		
		
		
		
	}
	//$update1 = mysqli_query($con, "update `zone` set name = '$zone2'")
	
}


if(isset($_POST['submit'])){
	$zone = $_POST['zone'];
	
	$select = mysqli_query($con,"select max(zid) as zid1 from zone");
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['zid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }
	
	$length=sizeof($_POST['district']);
    foreach ($_POST['district'] as $district)
    {
    // $cat=$cat.$category.",";
          
             $dd_id=$district;
                $length=$length-1;
				$inser = mysqli_query($con ,"INSERT INTO `master` (zid, did) VALUES ('$no','$dd_id')");
			//	echo "INSERT INTO `master` (zid, did) VALUES ('$no','$dd_id')";
            

        
    }
	
	$row_query = mysqli_query($con,"INSERT INTO `zone` (zid,name) VALUES ('$no','$zone')");
}

/*if(isset($_POST['edit'])){
	$mid = $_POST['zone'];
    $_SESSION['mid'] = $mid;
	$sql_query1 = mysqli_query($con, "SELECT m.mid, z.name as zname, d.edistrict, u.name FROM `master` m INNER JOIN district d ON m.did = d.did INNER JOIN zone z ON m.zid = z.zid INNER JOIN unit u ON m.uid = u.uid where mid= '$mid'");
	$row_query1=mysqli_fetch_array($sql_query1);
	$zname = $row_query1['zname'];
	$district1 = $row_query1['edistrict'];
	$uname = $row_query1['name'];
}

if(isset($_POST['submit_edit'])){
	$z_id=$_SESSION['mid'];
	$zname1 = $_POST['zid'];
	$district2 = $_POST['district'];
	$uname2 = $_POST['uid'];
	
	$select6=mysqli_query($con, "update master set zid = '$zname1', did='$district2', uid='$uname2' where mid = '$z_id'");
	?>
	<script> alert(' update sucessfully !!!!');
    window.open('zone.php','_self');
</script>
	<?php
}*/

?>

<!doctype html>
<html lang="en">


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Agro Admin Dashboard</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    <!-- Bootstrap core CSS     -->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
    <!--  Material Dashboard CSS    -->
    <link href="../assets/css/material-dashboard5438.css?v=1.2.0" rel="stylesheet" />
    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="../assets/css/demo.css" rel="stylesheet" />
    <!--     Fonts and icons     -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css" integrity="sha384-v2Tw72dyUXeU3y4aM2Y0tBJQkGfplr39mxZqlTBDUZAb9BGoC40+rdFCG0m10lXk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/fontawesome.css" integrity="sha384-q3jl8XQu1OpdLgGFvNRnPdj5VIlCvgsDQTQB6owSOHWlAurxul7f+JpUOVdAiJ5P" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="../assets/css/dataTables.bootstrap.min.css">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="../assets/css/select2.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/select2.min.css">
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

    <div class="wrapper">
        <div class="sidebar" data-color="purple" data-image="../assets/img/sidebar-1.jpg">
            <!--
        Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

        Tip 2: you can also add an image using data-image tag
    -->
    <div class="logo">
        <center>
            <img src="../assets/img/logo.png" style="height: 50px;">
        </center>
    </div>
    <div class="sidebar-wrapper">
        <ul class="nav">
            <li>
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
            <li class="">
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
			<li class="active">
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
<div class="content" style="margin-top: 0px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header" data-background-color="purple">
                        <h4 class="title">Zone</h4>
                    </div>
                    <div class="card-content table-responsive">
					<form action="" method="post">
                        <div class="col-md-12">
                                <div class="form-group label-floating">
                                    <label class="control-label">Zone</label>
                                    <input type="text" class="form-control" name="zone" value="<?php echo $zone1 ?>">
                                </div>
                            </div>
							
							<div class="col-md-12">
                                <div class="form-group label-floating">
                                    District:
                                                           
                                                            <select class="js-example-basic-multiple" data-toggle="dropdown" name="district[]" multiple="multiple">


                                                                <?php
                                                                $sql6=mysqli_query($con,"select * from district");
                                                                while($row6=mysqli_fetch_array($sql6))
                                                                {
                                                                    ?>
                                                                    <option value="<?php echo $row6['did'] ?>" <?php
                                                                    if($row6['edistrict']==$district1){
                                                                       echo "selected=";?>"selected" 
                                                                       <?php }?>><?php  echo $row6['edistrict'] ?></option>
                                                                       <?php

                                                                   }
                                                                
                                                                   ?>  

                                                               </select>
                                </div>
                            </div>
							
							<?php if(isset($_POST['edit'])){?>
                            <button type="submit" name="submit_edit"  class="btn btn-primary pull-right">Update</button>
                            <?php } else { ?>
                            <button type="submit" name="submit" class="btn btn-primary pull-right">Add Zone</button>
                            <?php }  ?>
							</form>
                    
            </div>
        </div>
    </div>
</div>
<form action="" method="post">
<table class="table table-striped table-bordered table-hover" id="mydata">
<input  name="zone" type="hidden" id="hidden" />
						<thead>
							<tr>
								<th>Zone</th>
								<th>District</th>
								<th>Edit/Delete</th>
							</tr>
						</thead>
						<tbody>
						
						<?php 
						
						$query = mysqli_query($con, "SELECT m.mid,z.zid, z.name, d.edistrict from zone z INNER JOIN `master` m ON z.zid = m.zid INNER JOIN district d ON m.did = d.did");
						while($row_query1 = mysqli_fetch_array($query)){
						
						echo "<tr>";
						echo "<td>".$row_query1['name']."</td>";
						echo "<td>".$row_query1['edistrict']."</td>";
						?>
						
 							<td><input type="submit" name="edit" value=" Edit " class="btn btn-primary" onclick="passvalue(<?php echo $row_query1['mid']?>)">
                                    <input type="submit" name="delete" value="Delete" class="btn btn-primary" onclick="passvalue(<?php echo $row_query1['mid']?>)">
							</td>
						<?php
							echo "</tr>";
						}
						?>
							
						</tbody>
					</table>
					</form>
</div>
</div>
<footer class="footer">

</footer>
<div class="fixed-plugin">
</div>
</div>
</div>
</body>
<!--   Core JS Files   -->
<script src="../assets/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/js/material.min.js" type="text/javascript"></script>
<!--  Charts Plugin -->
<script src="../assets/js/chartist.min.js"></script>
<!--  Dynamic Elements plugin -->
<script src="../assets/js/arrive.min.js"></script>
<!--  Sharrre Plugin -->
<script src="../assets/js/jquery.sharrre.js"></script>
<!--  PerfectScrollbar Library -->
<script src="../assets/js/perfect-scrollbar.jquery.min.js"></script>
<!--  Notifications Plugin    -->
<script src="../assets/js/bootstrap-notify.js"></script>
<!--  Google Maps Plugin    -->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBSaSxv01RBLnlu5EyBHLs57s-IquPaows"></script>
<!-- Material Dashboard javascript methods -->
<script src="../assets/js/material-dashboard5438.js?v=1.2.0"></script>
<!-- Material Dashboard DEMO methods, don't include it in your project! -->
<script src="../assets/js/select2.js"></script>
<script src="../assets/js/select2.min.js"></script>
<script src="../assets/js/jquery.dataTables.min.js"></script>
<script src="../assets/js/dataTables.bootstrap.min.js"></script>
<script src="../assets/js/demo.js"></script>
<script>
  $('#mydata').dataTable();
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.js-example-basic-multiple').select2();
    });
</script>
<script type="text/javascript">
    function passvalue(mid)
    {
        document.getElementById("hidden").value = mid;
        //alert(document.getElementById("hidden").value); 
    }
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
<script type="text/javascript">
    function passvalue(mid)
    {
        document.getElementById("hidden").value = mid;
        //alert(document.getElementById("hidden").value); 
    }
</script>


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
</html>