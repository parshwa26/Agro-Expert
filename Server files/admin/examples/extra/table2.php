<!doctype html>
<html lang="en">


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
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
    <link href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css" integrity="sha384-v2Tw72dyUXeU3y4aM2Y0tBJQkGfplr39mxZqlTBDUZAb9BGoC40+rdFCG0m10lXk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/fontawesome.css" integrity="sha384-q3jl8XQu1OpdLgGFvNRnPdj5VIlCvgsDQTQB6owSOHWlAurxul7f+JpUOVdAiJ5P" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
</head>

<body>


    <div class="wrapper">
        <div class="sidebar" data-color="purple" data-image="../assets/img/sidebar-1.jpg">
           <div class="logo">
                <center>
                    <img src="../assets/img/logo.png" style="height: 50px;">
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
                        <a href="addexpert.php">
                            <i class="fa fa-plus-square"></i>
                            <p>Add Expert</p>
                        </a>
                    </li>
                    <li>
                        <a href="category.php">
                            <i class="material-icons">person</i>
                            <p>Category</p>
                        </a>
                    </li>
                    <li>
                        <a href="addcategory.php">
                            <i class="fa fa-plus-square"></i>
                            <p>Add category</p>
                        </a>
                    </li>
                    <li>
                        <a href="subcategory.php">
                            <i class="material-icons">person</i>
                            <p>Sub Category</p>
                        </a>
                    </li>
                    <li>
                        <a href="addsubcategory.php">
                            <i class="fa fa-plus-square"></i>
                            <p>Add Sub category</p>
                        </a>
                    </li>
                    <li>
                        <a href="faqs.php">
                            <i class="material-icons">person</i>
                            <p>FAQs</p>
                        </a>
                    </li>
                    <li>
                        <a href="addfaqs.php">
                            <i class="fa fa-plus-square"></i>
                            <p>Add FAQs</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="main-panel">
            <h3 style="padding: 10px 0px 0px 20px;">No. of Expert</h3>
            <a href="index.php"><button class="btn btn-primary" style="float: right; margin-top: -50px;">Log Out</button></a>
            <div class="content" style="margin-top: 0px;">
                                    <table class="table table-striped table-bordered table-hover" id="mydata">
                                        <thead>
                                            <th><b>ID</b></th>
                                            <th><b>Name</b></th>
                                            <th><b>Mobile Number</b></th>
                                            <th><b>District</b></th>
                                            <th><b>Topic</b></th>
                                        </thead>
                                        <tbody>
                                        <?php
                                            include('connection.php');
                                            $result = mysqli_query($con,"SELECT * FROM `registration` where user_type='ex' ");
                                            
                                            $count=1;
                                            while($reg=mysqli_fetch_array($result)){
                                               echo "<tr><td>".$count."</td>"; 
                                               echo   "<td>".$reg['fname']." ".$reg['lname']."</td>";
                                               
                                               $result1= mysqli_query($con,"select * from login where lid='".$reg['lid']."'");
                                               if($log=mysqli_fetch_array($result1)){

                                                 echo "<td>".$log['phoneno']."</td>";
                                               echo "<td>".$log['district']."</td>"; 

                                                }

                                               $result2=mysqli_query($con,"select * from expertaction where lid='".$log['lid']."'");
                                               if($log1=mysqli_fetch_array($result2)){

                                                 $result3=mysqli_query($con,"select * from category where cid='".$log1['cid']."'");
                                                 if($cat=mysqli_fetch_array($result3)){

                                                    if($cat['pid']==0)
                                                    {
                                                        echo "<td>".$cat['ecategory_flag']."</td>";
                                                    }

                                                    else{
                                                    $result4=mysqli_query($con,"select * from category where cid='".$cat['pid']."'");
                                                    if($cat1=mysqli_fetch_array($result4)){
                                                    echo "<td>".$cat1['ecategory_flag']."</td>";
                                                    }
                                                    }
                                               }
                                                $count++;
                                                echo "</tr>";
                                            }
                                        }
                                        ?>
                                        </tbody>
                                    </table>
                                </div>
                                <form action="export2.php" method='post'><button class="btn btn-primary" name="export">Download Excel</button></form>
                        
                        
                    
                
            </div>
            <footer class="footer">
                
            </footer>
            <div class="fixed-plugin">
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
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
<script>
	$('#mydata').dataTable();
</script>
</html>