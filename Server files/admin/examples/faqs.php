<?php
include('connection.php');
include('session.php');
if(isset($_POST['edit']))
{
    $fid = $_POST['faq'];
    $_SESSION['fid'] = $fid;
    $select1= mysqli_query($con, "SELECT f.fid, c.ecategory_flag, f.equestion, f.eanswer, f.gquestion, f.ganswer, f.hquestion, f.hanswer FROM faq f INNER JOIN category c ON f.cid = c.cid where f.fid = '$fid'");
    $row_query=mysqli_fetch_array($select1);
       echo  $category=$row_query['ecategory_flag'];
        $hquestion=$row_query['hquestion'];
        $gquestion=$row_query['gquestion'];
        $equestion=$row_query['equestion'];
        $hanswer=$row_query['hanswer'];
        $ganswer=$row_query['ganswer'];
        $eanswer=$row_query['eanswer'];

    
}
if(isset($_POST['delete']))
{
    $faq_id=$_SESSION['fid'];
    $sql=mysqli_query($con,"update faq set status='0' where fid='$faq_id'");
    ?>
    <script>
        alert("faq deletet sucessful!!");
        window.open('faqs.php','_self')
    </script>
<?php
}

if(isset($_POST['submit'])){
    echo $cid=$_POST['category'];
    echo $equestion=$_POST['editordata'];
    echo $eanswer=$_POST['editordata1'];
    $hquestion=$_POST['editordata2'];
    $hanswer=$_POST['editordata3'];
    $gquestion=$_POST['editordata4'];
    $ganswer=$_POST['editordata5'];
    

    $select = mysqli_query($con,"select max(fid) as fid1 from faq");
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['fid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }
    
    $sql=mysqli_query($con,"insert into faq (`fid`,`cid`,`equestion`,`eanswer`,`gquestion`,`ganswer`,`hquestion`,`hanswer`,`pid`,`status`) values ('$no','$cid','$equestion','$eanswer',N'$gquestion',N'$ganswer',N'$hquestion',N'$hanswer','0','1')");
    if($sql)
	{
		$ss_msg="Inserted Success";
	}
	else
	{
		$er_msg=mysqli_error();
	}

}
if(isset($_POST['submit_edit'])){
    $faq_id=$_SESSION['fid'];
    $cid=$_POST['category'];
    $equestion=$_POST['editordata'];
    $eanswer=$_POST['editordata1'];
    $hquestion=$_POST['editordata2'];
    $hanswer=$_POST['editordata3'];
    $gquestion=$_POST['editordata4'];
    $ganswer=$_POST['editordata5'];
    // echo $cid;
    // echo $equestion;
    // echo $eanswer;
    // echo $gquestion;
    // echo $ganswer;
    // echo $hquestion;
    // echo $hanswer;
    $sql=mysqli_query($con,"update faq set cid='$cid',equestion='$equestion',eanswer= '$eanswer',gquestion=N'$gquestion',ganswer=N'$ganswer',hquestion=N'$hquestion',hanswer=N'$hanswer' where fid='$faq_id'");
    ?>
    <script>
        alert("faq update sucessful!!");
        window.open('faqs.php','_self');
    </script>
    <?php


}
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
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">

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
            <li class="active">
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
<h3 style="color=green;"><?php if(isset($ss_msg)){ echo $ss_msg;} ?></h3>
<h3 style="color=red;"><?php if(isset($er_msg)){ echo $er_msg;} ?></h3>
<a href="logout.php"><button class="btn btn-primary" style="float: right; margin-top: -50px;">Log Out</button></a>
<div class="content" style="margin-top: 0px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header" data-background-color="purple">
                        <h4 class="title">Table</h4>
                    </div>
                    <div class="card-content table-responsive">
                        <form action="" method="post">

                            <div class="col-md-12">
                                <select  class="btn btn-primary dropdown-toggle" name="category" type="button" data-toggle="dropdown" style="height: auto; max-height: 100px; background: #fff; color: #000; border: 2px;">
                                    <?php 

                                    $sql2=mysqli_query($con,"select * from category ");
                                    while($row=mysqli_fetch_array($sql2))
                                    {
                                        ?>
                                        <option value="<?php echo $row['cid'] ?>"<?php
                                        if($row['ecategory_flag']==$category){
                                           echo "selected=";?>"selected" 
                                           <?php }?>><?php  echo $row['ecategory_flag'] ?></option>
                                           <?php
                                       }
                                       ?>  
                                   </select>
                               </div>
                               <div class="col-md-12">
                                <div class="form-group label-floating">
									<h4>English Question</h4>
                                    <textarea id="summernote" name="editordata"><?php echo $equestion; ?></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group label-floating">
								<h4>English Answer</h4>
                                    <textarea id="summernote1" name="editordata1"><?php echo $eanswer; ?></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group label-floating">
								<h4>Gujarati Question</h4>
                                    <textarea id="summernote2" name="editordata2"><?php echo $gquestion; ?></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group label-floating">
								<h4>Gujarati Answer</h4>
                                    <textarea id="summernote3" name="editordata3"><?php echo $ganswer; ?></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group label-floating">
								<h4>Hindi Question</h4>
                                    <textarea id="summernote4" name="editordata4"><?php echo $hquestion; ?></textarea>
                                </div>
                            </div>
							<div class="col-md-12">
                                <div class="form-group label-floating">
								<h4>Hindi Answer</h4>
                                    <textarea id="summernote5" name="editordata5"><?php echo $hanswer; ?></textarea>
                                </div>
                            </div>
							
                            

                        </div>
                        <?php if(isset($_POST['edit'])){?>
                        <button type="submit" name="submit_edit" class="btn btn-primary pull-right">Update</button>
                        <?php } else { ?>
                        <button type="submit" name="submit" class="btn btn-primary pull-right">Add FAQs</button>
                        <?php }  ?>
                        <div class="clearfix"></div>


                    </form>
                    <form action="" method="post">
                      <table class="table table-striped table-bordered table-hover" id="mydata">
                        <input type="hidden" name="faq" id="hidden">
                        <thead>
                            <tr>
                                <th>ID</th> 
                                <th>Category</th>
                                <th>English Question</th>
                                <th>English Answer</th>
                                <th>Gujarati Question</th>
                                <th>Gujarati Answer</th>
                                <th>Hindi Question</th>
                                <th>Hindi Answer</th>
                                <th>Edit/Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php

                            include('connection.php');
                            $sql1 = mysqli_query($con,"SELECT * FROM faq where status='1'");
                            $count=1;

                            while($reg=mysqli_fetch_array($sql1)){

                                echo
                                "
                                <tr><td>$count</td>";
                                $sql3 = mysqli_query($con,"SELECT * FROM category where cid='".$reg['cid']."'");
                                while($reg1=mysqli_fetch_array($sql3)){
                                    echo"<td>{$reg1['ecategory_flag']}</td>";
                                }
                                echo"
                                <td>{$reg['equestion']}</td>
                                <td>{$reg['eanswer']}</td>
                                <td>{$reg['gquestion']}</td>
                                <td>{$reg['ganswer']}</td>
                                <td>{$reg['hquestion']}</td>
                                <td>{$reg['hanswer']}</td> 
                                ";

                                ?>
                                <td>

                                    <input type="submit" name="edit" value=" Edit " class="btn btn-primary" onclick="passvalue(<?php echo $reg['fid']?>)">
                                    <input type="submit" name="delete" value="Delete" class="btn btn-primary" onclick="passvalue(<?php echo $reg['fid']?>)">

                                </td>
                                <?php
                                echo "</tr>";
                                $count++;
                            }
                            ?>


                        </tbody>
                    </table> 
                </form>
            </div>
        </div>
    </div>
</div>
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
<script src="../assets/js/jquery.dataTables.min.js"></script>
<script src="../assets/js/dataTables.bootstrap.min.js"></script>
<script src="../assets/js/demo.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
<script>
  $('#mydata').dataTable();
</script>
<script type="text/javascript">
    function passvalue(fid)
    {
        document.getElementById("hidden").value = fid;
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
<script>
        $(document).ready(function() {
  $('#summernote').summernote();
});
    </script>
<script>
        $(document).ready(function() {
  $('#summernote1').summernote();
});
    </script>
<script>
        $(document).ready(function() {
  $('#summernote2').summernote();
});
    </script>
<script>
        $(document).ready(function() {
  $('#summernote3').summernote();
});
    </script>
<script>
        $(document).ready(function() {
  $('#summernote4').summernote();
});
    </script>
<script>
        $(document).ready(function() {
  $('#summernote5').summernote();
});
    </script>


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
</html>