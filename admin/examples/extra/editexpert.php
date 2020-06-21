



<!doctype html>
<html lang="en">


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/user.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
<head>
    <meta charset="utf-8" />
    <link rel="apple-touch-icon" sizes="76x76">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Agro</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
    <meta name="viewport" content="width=device-width" />
    <!-- Canonical SEO -->
    
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
    <!--  Material Dashboard CSS    -->
    <link href="../assets/css/material-dashboard5438.css?v=1.2.0" rel="stylesheet" />
    <!--  CSS for Demo Purpose, don't include it in your project     -->
    <link href="../assets/css/demo.css" rel="stylesheet" />
    <!--     Fonts and icons     -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>

                                                <script language="javascript" type="text/javascript">

                        function showCity(str)

                        {

                        if (str=="")

                          {

                          document.getElementById("drpcity").innerHTML="";

                          return;

                          } 

                        if (window.XMLHttpRequest)

                          {// code for IE7+, Firefox, Chrome, Opera, Safari

                          xmlhttp=new XMLHttpRequest();

                          }

                        else

                          {// code for IE6, IE5

                          xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");

                          }

                        xmlhttp.onreadystatechange=function()

                          {

                          if (xmlhttp.readyState==4 && xmlhttp.status==200)

                            {

                            document.getElementById("drpcity").innerHTML=xmlhttp.responseText;

                            }

                          }

                        xmlhttp.open("GET","ajex1.php?q="+str,true);

                        xmlhttp.send();

                        }

                        </script>

                        <script language="javascript" type="text/javascript">

                        function validate()

                        {

                            var isvalid=true;;

                            if(document.City.txtFirst.value == "")

                            {

                                alert("enter First name");

                                isvalid = false;

                            }

                            else if(document.City.txtLast.value == "")

                            {

                                alert("enter last name");

                                isvalid = false;

                            }

                            else if(document.City.txtcity.value == "")

                            {

                                alert("enter city name");

                                isvalid = false;

                            }

                            else if(document.City.cmbstate.value == "")

                            {

                                alert("enter state name");

                                isvalid = false;

                            }

                        return isvalid;

                        }

                        </script>



</head>

</head>

<body>

    <?php
        include('connection.php');
        session_start();
        $id=$_GET['lid'];
        $_SESSION['id']=$id;
        $sql1=mysqli_query($con,"select * from registration where lid='$id'");
        $sql2=mysqli_query($con,"select * from login where lid='$id'");
        $sql3=mysqli_query($con,"select * from expertaction where lid='$id'");
        if($row=mysqli_fetch_array($sql3)){
            $sql4=mysqli_query($con,"select * from category where cid='".$row['cid']."'");
            if($reg=mysqli_fetch_array($sql4)){
                $subcategory=$reg['ecategory_flag'];
                $sql5=mysqli_query($con,"select * from category where cid='".$reg['pid']."'");
                if($reg1=mysqli_fetch_array($sql5)){
                    echo $subcategory;
                    echo $reg1['ecategory_flag'];
                    $category=$reg1['ecategory_flag'];
                }
                
            }
        }

         if($row2=mysqli_fetch_array($sql1)){
            $fname=$row2['fname'];
            $lname=$row2['lname'];
            $pincode=$row2['pincode'];
            $lang=$row2['lang_flag'];
            // if($lang=='en'){
            //     $lang='English';
            //     }

            //     else if($lang=='gu'){
            //         $lang='Gujarati';
            //     }

            //     else if($lang=='hi'){
            //         $lang='Hindi';
            //     }


         }

         if($row3=mysqli_fetch_array($sql2)){
            $phoneno=$row3['phoneno'];
            $district=$row3['district'];
         }



        
    ?>


    <div class="wrapper">
        <div class="sidebar" data-color="purple" data-image="../assets/img/sidebar-1.jpg">
            
            <div class="logo">
                <center>
                    <img src="../assets/img/logo.png" style="height: 100px;">
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
                    <li class="active">
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
                   <!--  <li>
                        <a href="table.html">
                            <i class="material-icons">content_paste</i>
                            <p>Table List</p>
                        </a>
                    </li> 
                    <li>
                        <a href="typography.html">
                            <i class="material-icons">library_books</i>
                            <p>Typography</p>
                        </a>
                    </li>
                    <li>
                        <a href="icons.html">
                            <i class="material-icons">bubble_chart</i>
                            <p>Icons</p>
                        </a>
                    </li>
                    <li>
                        <a href="maps.html">
                            <i class="material-icons">location_on</i>
                            <p>Maps</p>
                        </a>
                    </li>
                    <li>
                        <a href="notifications.html">
                            <i class="material-icons text-gray">notifications</i>
                            <p>Notifications</p>
                        </a>
                    </li>
                    <li class="active-pro">
                        <a href="upgrade.html">
                            <i class="material-icons">unarchive</i>
                            <p>Upgrade to PRO</p>
                        </a>
                    </li>-->
                </ul>
            </div>
        </div>
        <div class="main-panel">
            <h3 style="padding: 10px 0px 0px 20px;">Admin Dashboard</h3>
            <a href="index.php"><button class="btn btn-primary" style="float: right; margin-top: -50px;">Log Out</button></a>
            <div class="content" style="margin-top: 0px;">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="card">
                                <div class="card-header" data-background-color="purple">
                                    <h4 class="title">Edit Expert</h4>
                                </div>
                                <div class="card-content">
                                    <form action="editexpert_back.php">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">First Name</label>
                                                    <input type="text" class="form-control" value="<?php echo $fname ?>">
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Last Name</label>
                                                    <input type="text" class="form-control" value="<?php echo $lname ?>">
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">Contact Number</label>
                                                    <input type="email" class="form-control" value="<?php echo $phoneno ?>">
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group label-floating">
                                                    <label class="control-label">pincode</label>
                                                    <input type="number" class="form-control" value="<?php echo $pincode ?>">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group label-floating">
                                                    <div class="">
                                                        <select  class="btn btn-primary dropdown-toggle"  type="button" data-toggle="dropdown" style="height: auto; max-height: 100px; background: #fff; color: #000; border: 2px;">Select District
                                                        <span class="caret"></span>
                                                          
                                                         
                                                                <?php 
 
                                                                $sql2=mysqli_query($con,"select * from district ");
                                                                while($row=mysqli_fetch_array($sql2))
                                                                {
                                                                ?>
                                                                <option value="<?php echo $district ?>" <?php
                                                                if($row['edistrict']==$district){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?> > <?php  echo $row['edistrict'] ?></option>
                                                                <?php
                                                                }
                                                                 ?>
                                                    </select>
                                                    <select type="button" value="<?php echo $lang ?>" class="btn btn-primary" style="background: #fff; color: #000;"> 
                                                        
                                                        <option <option <?php
                                                                if($lang=='en'){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?>>English</option>
                                                        <option <option <?php
                                                                if($lang=='gu'){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?>>Gujarati</option>
                                                        <option <option <?php
                                                                if($lang=='hi'){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?>>Hindi</option>
                                                    </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <form action="#" method="post" name="City">
                                                    <table>
                                                        <tr>
                                                            <td style="font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;">Select Category</td>
                                                            <td><select name="cmbstate" onChange="showCity(this.value)" value="" >
                                                                <option value="">--<Select--</option>
                                                                <?php $sql2=mysqli_query($con,"select * from category where pid='0' and status!='0'");
                                                                while($row=mysqli_fetch_array($sql2))
                                                                {
                                                               ?>
                                                                <option value="<?php echo $category ?>" <?php
                                                                if($row['ecategory_flag']==$category){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?> > <?php echo $row['ecategory_flag']; ?></option>
                                                                <?php
                                                                }
                                                                 ?>
                                                            </select></td>
                                                        
                                                        <td style="font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;">Select sub Category</td>
                                                            <td><select name="cmbstate" onChange="showCity(this.value)">
                                                                <option value="">--<Select--</option>
                                                                <?php $sql2=mysqli_query($con,"select * from category where pid='0' and status!='0'");
                                                                while($row=mysqli_fetch_array($sql2))
                                                                {
                                                                ?>
                                                                <option value="<?php echo $subcategory ?>" <?php
                                                                if($row['subcategory']==$category){
                                                                 echo "selected=";?>"selected" 
                                                                 <?php }?> > <?php echo $row['ecategory_flag']; ?></option>
                                                                <?php
                                                                }
                                                                 ?>
                                                            </select></td>
                                                    </tr>
                                                    </table>
                                                </form>
                                            </div>
                                        </div>
                                        <a href="editexpert_back.php"><button type="submit" class="btn btn-primary pull-right">Edit Expert</button></a>
                                        <div class="clearfix"></div>
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
<script src="../assets/js/demo.js"></script>


<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/user.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
</html>