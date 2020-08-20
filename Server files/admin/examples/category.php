<?php
include('connection.php');
include('session.php');
$parent=0;
if(isset($_POST['edit']))
{

    $cid=$_POST['category'];
    $_SESSION['cid']=$cid;
    $sql=mysqli_query($con,"select * from category where cid=".$cid." ");
    if($row=mysqli_fetch_array($sql)){
        $parent=$row['pid'];
        $sql2=mysqli_query($con,"select * from category where cid=".$parent." ");
        if($row2=mysqli_fetch_array($sql2))
        {
            $parent_category=$row2['ecategory_flag'];
        }
        $ecategory=$row['ecategory_flag'];
        $hcategory=$row['hcategory_flag'];
        $gcategory=$row['gcategory_flag'];

    }
}

if(isset($_POST['delete']))
{
    $cid=$_POST['category'];
    $sql=mysqli_query($con,"update category set status='0' where cid='$cid'");
    if($sql)
    {
        $ss_msg="sucessful";
        //header("Location:faqs.php");
    }
}

if(isset($_POST['submit']))
{
    $cat1=$_POST['cat1'];
    $cat2=$_POST['cat2'];
    $cat3=$_POST['cat3'];
    $category=$_POST['cid'];    


    $select = mysqli_query($con,"select max(cid) as cid1 from category"); 
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['cid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }

    if($category!="root")
    {
        $select1= mysqli_query($con,"select * from category where cid=$category");
        if($data1=mysqli_fetch_array($select1))
        {
            $level=$data1['level'];
            $level=$level+1;
            $pid=$data1['cid'];
            $parent_name=$data1['ecategory_flag'];
        }
    }

    else
    {
        $level='1';
        $pid='0';
    }



    $sql=mysqli_query($con,"INSERT INTO `category` (`cid`,`ecategory_flag`,`gcategory_flag`,`hcategory_flag`,`pid`,`level`,`status`) VALUES ('$no',N'$cat1',N'$cat2',N'$cat3','$pid','$level','1')");
	echo "SET @para=".$no." CALL treestr(@para)";
	$sql_p=mysqli_query($con,"SET @para=".$no." ");
    $sql_proc=mysqli_query($con,"CALL treestr(@para)");
}

if(isset($_POST['submit_edit']))
{
    $cat1=$_POST['cat1'];
    $cat2=$_POST['cat2'];
    $cat3=$_POST['cat3'];
    $category=$_POST['cid'];
    $category_id=$_SESSION['cid'];

    $select = mysqli_query($con,"select max(cid) as cid1 from category"); 
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['cid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }

    if($category!="root")
    {
        $select1= mysqli_query($con,"select * from category where cid=$category");
        if($data1=mysqli_fetch_array($select1))
        {
            $level=$data1['level'];
            $level=$level+1;
            $pid=$data1['cid'];
        }
    }

    else
    {
        $level='1';
        $pid='0';
    }

    echo $cat1;

    $sql=mysqli_query($con,"update category set ecategory_flag=N'$cat1',hcategory_flag=N'$cat3',gcategory_flag=N'$cat2',pid='$pid',level='$level' where cid='$category_id'");
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
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="../assets/css/dataTables.bootstrap.min.css">
    <link rel="stylesheet" href="hierarchy-select-master/dist/hierarchy-select.min.css">
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
            <li class="active">
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
<div class="content" style="margin-top: 0px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header" data-background-color="purple">
                        <h4 class="title">Table</h4>
                    </div>
                    <?php

                    function category($cid)
                    {
                      global $con;

                      $sql1=mysqli_query($con,"select * from category where cid=$cid and status!='0'");

                      while ($result=mysqli_fetch_array($sql1)) 
                      {
                        ?>
                        <li data-value="<?php echo $result['cid'] ?>" data-level="<?php echo $result['level'] ?>"  >
                            <a href="#"><?php echo $result['ecategory_flag'] ?></a>
                        </li>
                        <?php

                        $level=$result['level']+1;
                        $sql2=mysqli_query($con,"select * from category where pid=$cid and level=$level and status!='0'");
                        if(mysqli_num_rows($sql2)>0)
                        {
                            while ($result2=mysqli_fetch_array($sql2))
                            {
                                category($result2['cid']);
                            }

                        }

                    }


                }

                ?>

                <div class="card-content table-responsive">
                    <form action="" method="post">
                        <div class="form-group">
                            <div class="btn-group hierarchy-select" data-resize="auto" id="your-expertise-hierarchy">
                                <button type="button" class="dropdown-toggle" data-toggle="dropdown" style="background-color: #fff; outline: none !important;" >
                                    <span class="selected-label pull-left"> </span>
                                    <span class="caret"></span>
                                    <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <div class="dropdown-menu open">
                                    <ul class="dropdown-menu inner" role="menu" name="cid" id="category">

                                        <li data-value="root" data-level="1"  >
                                            <a href="#">Add root</a>
                                        </li>
                                        <?php

                                        $sql=mysqli_query($con,"select * from category where pid='0' and status!='0'");

                                        while ($result1=mysqli_fetch_array($sql)) 
                                        {

                                            category($result1['cid']);

                                        }
                                        ?>


                                    </ul>
                                </div>
                                <input type="hidden" name="cid" id="cid">

                            </div>
                        </div>
                        
                        <div class="form-group label-floating"  style="padding: 10px;">
                            <label class="control-label">Add Category English</label>
                            <input type="text" value="<?php echo $ecategory?>" class="form-control" name="cat1">
                        </div>
                        <div class="form-group label-floating"  style="padding: 10px;">
                            <label class="control-label">Add Category Gujarati</label>
                            <input type="text" value="<?php echo $gcategory?>" class="form-control" name="cat2">
                        </div>
                        <div class="form-group label-floating"  style="padding: 10px;">
                            <label class="control-label">Add Category Hindi</label>
                            <input type="text" value="<?php echo $hcategory?>" class="form-control" name="cat3">
                        </div>
                        
                        <?php if(isset($_POST['edit'])){?>
                            <button type="submit" name="submit_edit"  class="btn btn-primary pull-right">Update</button>
                        <?php } else { ?>
                          <button type="submit" name="submit" class="btn btn-primary pull-right">Add category</button>
                      <?php }  ?>
                  </form>

              </div>
          </div>
      </div>
  </div>
  <form method="post" action="">
    <div class="card-content">
      <table class="table table-striped table-bordered table-hover" id="mydata">
        <input  name="category" type="hidden" id="hidden" />
        <thead>
            <th>English Category Name</th>
            <th>Gujarati Category Name</th>
            <th>Hindi Category Name</th>
            <th>Edit/Delete</th>
        </thead>
        <tbody>
            <?php

            include('connection.php');
            $result = mysqli_query($con,"SELECT * FROM `category` where status!='0'");
            while($reg=mysqli_fetch_array($result)){
                ?>

                <?php
                echo
                "<tr>
                <td>{$reg['ecategory_flag']}</td>
                <td>{$reg['gcategory_flag']}</td>
                <td>{$reg['hcategory_flag']}</td>";
                ?>

                <td>
                    <input type="submit" name="edit" value=" Edit " class="btn btn-primary" onclick="passvalue(<?php echo $reg['cid']?>)">
                    <input type="submit" name="delete" value="Delete" class="btn btn-primary" onclick="passvalue(<?php echo $reg['cid']?>)">


                </td>

                <?php
                echo "</tr>";
            }



            ?>

        </tbody>
    </table>
</div>
</form>

</div>
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
<script src="../assets/js/demo.js"></script>
<script src="../assets/js/jquery.dataTables.min.js"></script>
<script src="../assets/js/dataTables.bootstrap.min.js"></script>
<script src="hierarchy-select-master/dist/hierarchy-select-edit.js"></script>
<!-- <script src="hierarchy-select-master/dist/hierarchy-select.min.js"></script> -->
<!--<script type="text/javascript">
    $(document).ready(function() {
        $('#your-expertise-hierarchy').hierarchySelect({
            hierarchy: true,
            search: true,
            width: 250
        });
    });
</script>-->
<script>
   $('#mydata').dataTable();
</script>
<script type="text/javascript">

    $("#category").click(function(){

        $("#cid").val($('ul#category').find('li.active').data('value'));

    });

    $(document).ready(function() {
        $('#your-expertise-hierarchy').hierarchySelect({
            hierarchy: true,
            search: true,
            width: 250,
            value:<?php echo $parent ?>
            
        });
        $("#cid").val($('ul#category').find('li.active').data('value'));
    });
</script>
<script type="text/javascript">
    function passvalue(cid)
    {
        document.getElementById("hidden").value = cid;
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


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
</html>