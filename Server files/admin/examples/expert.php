<?php 
include('connection.php');
include('session.php');
if(isset($_POST['edit']))
{
    $lid = $_POST['expert'];
    $_SESSION['lid'] = $lid;
    $select1 = mysqli_query($con, "SELECT e.display_name,r.lid, r.fname, r.lname, r.phoneno, r.email, u.name AS uname, z.name AS zname, r.lang_flag, c.ecategory_flag, e.cid FROM registration r INNER JOIN unit u ON r.lid = u.lid INNER JOIN expertaction e ON r.lid = e.lid INNER JOIN category c ON e.cid = c.cid INNER JOIN zone z ON r.zid = z.zid where r.lid = '$lid' ");
    $row_query=mysqli_fetch_array($select1);
    $fname = $row_query['fname'];
    $lname = $row_query['lname'];
    $phoneno = $row_query['phoneno'];
    $email = $row_query['email'];
    $unit1 = $row_query['uname'];
    $languages = $row_query['lang_flag'];
	$zone1 = $row_query['zname'];
	echo $yes1 = $row_query['display_name'];

    
    //$arrays = explode(',', $row_query['cid']);
    //$length1=sizeof($arrays);
  //  foreach ($arrays as $cid1) {
        //echo $cid1[0];
        //$arr=array()
       // $selects=mysqli_query($con,"select * from category where cid='$cid1'");
       // while($row_query1=mysqli_fetch_array($selects))
        /*{
          if($length>1)
          {
            echo $category1 = $row_query1['ecategory_flag'].",";
            $length=$length-1;
        }

        //else
        {
            echo $category1 = $row_query1['ecategory_flag'];
            $length=$length-1;
        }*/


    

//}

}

if(isset($_POST['submit_edit']))
{
    //session_start();
    //$_SESSION['lid']=41;
    $fname1=$_POST['fname'];
    $lname1=$_POST['lname'];
    $co_no1=$_POST['co_no'];
    $email2=$_POST['email1'];
    $unit1=$_POST['unit'];
	$zone2 = $_POST['zone'];
    $language2=$_POST['language'];
    $expert_id=$_SESSION['lid'];
    $length=sizeof($_POST['category']);
    foreach ($_POST['category'] as $category)
    {
    // $cat=$cat.$category.",";
            if($length>1)
            {
                $c_id1=$c_id1.$category.",";
                $length=$length-1;
            }
            else
            {
                $cc_id1=$c_id1.$category;
                $length=$length-1;
            }

        
    }
    // echo $expert_id;
    // echo $fname;
    // echo $lname;
    // echo $email;
    // echo $co_no;

    $select9 = mysqli_query($con,"select max(lid) as lid1 from registration");
    if($data2=mysqli_fetch_array($select9))
    {
        $no1= $data2['lid1'];
        $no1=$no1+1;
    }
    else
    {
        $no1=1;
    }

    if($language2=='English'){
        $lang1='3';
    }

    else if($language2=='Gujarati'){
        $lang1='1';
    }

    else if($language2=='Hindi'){
        $lang1='2';
    }

    // echo $zid1;
    // echo $did1;
    // echo $lang1;
    $select6=mysqli_query($con, "update registration set did='0', zid='$zone2', fname='$fname1', lname='$lname1',email='$email2', phoneno='$co_no1', lang_flag='$lang1', user_type='2', status='1' where lid='$expert_id'");
    $row_query2 = mysqli_query($con, "update expertaction set cid='$cc_id1', status='1' where lid='$expert_id' ");
	$row_query5 = mysqli_query($con, "update unit set name = '$unit1' where lid='$expert_id' ");
    //echo "update registration set did='$did1', zid='$zid1', fname='$fname', lname='$lname',email='$email', phoneno='$co_no', lang_flag='$lang1', user_type='2', status='1' where lid='$expert_id'";

    ?>
    <script> alert('expert update sucessfully !!!!');
    window.open('expert.php','_self');
</script>
<?php

}

if(isset($_POST['submit'])){
    $fname=$_REQUEST['fname'];
    $lname=$_REQUEST['lname'];
    $mobile=$_REQUEST['co_no'];
    $language=$_REQUEST['language'];
    $email1=$_REQUEST['email1'];
	$unit = $_POST['unit'];
	$zone = $_POST['zone'];
	echo $yes = $_POST['yes'];



    if($language=='English'){
        $lang='3';
    }

    else if($language=='Gujarati'){
        $lang='1';
    }

    else if($language=='Hindi'){
        $lang='2';
    }


    $length=sizeof($_POST['category']);
    foreach ($_POST['category'] as $category)
    {
    // $cat=$cat.$category.",";
            if($length>1)
            {
                $c_id=$c_id.$category.",";
                $length=$length-1;
            }
            else
            {
                $cc_id=$c_id.$category;
                $length=$length-1;
            }

        
    }


    
    $select = mysqli_query($con,"select max(lid) as rid1 from registration");
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['rid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }

    $sql9=mysqli_query($con,"INSERT INTO `registration`(`lid`,`did`,`zid`, `fname`, `lname`, `email`, `phoneno`,`lang_flag`,`user_type`,`status`) VALUES ('$no','0','$zone','$fname','$lname','$email1','$mobile','$lang','2','1')"); 
    $sql91=mysqli_query($con,"INSERT INTO `expertaction`(`lid`, `cid`,`display_name`,`status`) VALUES ('$no','$cc_id','$yes','1')");
	$query = mysqli_query($con,"INSERT INTO `unit`(`lid`,`name`,`status`) VALUES ('$no','$unit','1') ");


    ?>
    <!--<script> alert('expert added sucessfully !!!!');
    window.open('expert.php','_self');
</script>-->
    
<?php 


}

if(isset($_POST['delete']))
{
    $lid=$_POST['expert'];
    $row_query3=mysqli_query($con,"update registration set status='0' where lid='$lid'");
     $row_query4=mysqli_query($con,"update expertaction set status='0' where lid='$lid'");
	 $row_query7=mysqli_query($con, "update unit set status='0' where lid = '$lid'")
     ?>
               <script> alert('expert deleted sucessfully !!!!');
               window.open('expert.php','_self');
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
    <link href='http://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="../assets/css/select2.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/select2.min.css">
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

<body onload="document.registration.fname.focus();">
    <?php

    include('connection.php');

    function category($cid)
    {
      global $con;

      $sql1=mysqli_query($con,"select * from category where cid=$cid and status!='0'");

      while ($result=mysqli_fetch_array($sql1)) 
      {
        ?>
        <li data-value="" data-level="<?php echo $result['level'] ?>" class="level-1">
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
<div class="wrapper">
    <div class="sidebar" data-color="purple" data-image="../assets/img/sidebar-1.jpg">

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
            <li  class="active">
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
<div class="content" style="margin-top: 0px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header" data-background-color="purple">
                        <h4 class="title">Table</h4>
                    </div>
                    <div class="card-content table-responsive">
                        <form action="" method="post" name='registration' onSubmit="return formValidation();">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">First Names</label>
                                        <input type="text" class="form-control" name="fname" value="<?php echo $fname ?>" required>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Last Name</label>
                                        <input type="text" class="form-control" name="lname" value="<?php echo $lname ?>" required>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Contact Number</label>
                                        <input type="text" class="form-control" name="co_no" value="<?php echo $phoneno ?>">
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Email id</label>
                                        <input type="text" class="form-control" name="email1" value="<?php echo $email ?>">
                                    </div>
                                </div>
								<div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Unit Name</label>
                                        <input type="text" class="form-control" name="unit" value="<?php echo $unit1 ?>">
                                    </div>
                                </div>
								<div class="col-md-12">
                                    <div class="">
									<label>Want to show Your name in Given Answered By</label>
									<?php 
									
									if(isset($_POST['edit'])){							?>
										<input type="radio" name="yes" value="1" <?php if($yes1==1){echo "checked";}?> > Yes
										<input type="radio" name="yes" value="2" <?php if($yes1==2){echo "checked";}?>>NO
										
										<?php 
										
									}
									
									?>
                                        
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group label-floating">
                                        <div class="">
                                         <table>
                                             <tr>
                                                 <td>
                                                 Zone:</td>
                                                 <td>
                                                    <select class="btn btn-primary dropdown-toggle" name="zone" type="button" data-toggle="dropdown" style="height: auto; max-height: 100px; background: #fff; color: #000; border: 2px;">
                                                        <span class="caret"></span>

                                                        <?php 

                                                        $sql3=mysqli_query($con,"select * from zone");
                                                        while($row3=mysqli_fetch_array($sql3))
                                                        {
                                                            ?>
                                                            <option value="<?php echo $row3['zid'] ?>"<?php
                                                            if($row3['name']==$zone1){
                                                               echo "selected=";?>"selected" 
                                                               <?php }?>><?php  echo $row3['name'] ?></option>
                                                               <?php
                                                           }
                                                           ?>  

                                                       </select>
                                                   </td>
                                               </tr>
											   
                                                           
                                               <!--<tr>
                                                <td>
                                                District:</td>
                                                <td>
                                                    <select class="btn btn-primary dropdown-toggle" name="district" type="button" data-toggle="dropdown" style="height: auto; max-height: 100px; background: #fff; color: #000; border: 2px;">
                                                        <span class="caret"></span>

                                                        <?php 

                                                        $sql2=mysqli_query($con,"select * from district");
                                                        while($row=mysqli_fetch_array($sql2))
                                                        {
                                                            ?>
                                                            <option value="<?php echo $row['edistrict'] ?>"<?php
                                                            if($row['edistrict']==$district){
                                                               echo "selected=";?>"selected" 
                                                               <?php }?>><?php  echo $row['edistrict'] ?></option>
                                                               <?php
                                                           }
                                                           ?>  

                                                       </select></td>
                                                   </tr>-->
                                                   <br>
                                                   <tr>
                                                    <td>
                                                    Language:</td>
                                                    <td>
                                                        <select class="btn btn-primary dropdown-toggle" name="language" value="<?php echo $languages ?>" type="button" data-toggle="dropdown" style="height: auto; max-height: 100px; background: #fff; color: #000; border: 2px;">
                                                            <option <?php
                                                            if($languages=='3'){
                                                               echo "selected=";?>"selected" 
                                                               <?php }?>>English</option>
                                                               <option <?php
                                                               if($languages=='1'){
                                                                   echo "selected=";?>"selected" 
                                                                   <?php }?>>Gujarati</option>
                                                                   <option <?php
                                                                   if($languages=='2'){
                                                                       echo "selected=";?>"selected" 
                                                                       <?php }?>>Hindi</option>
                                                                   </select>
                                                               </td>
                                                           </tr>
                                                           <br>
                                                           <tr><td>
                                                           catrgory:</td>
                                                           <td>
                                                           <?php
                                                            ?>
                                                            <select class="js-example-basic-multiple" data-toggle="dropdown" name="category[]" multiple="multiple" required>


                                                                <?php
                                                                $arrays = explode(',', $row_query['cid']);
                                                                $length1=sizeof($arrays);
                                                                foreach ($arrays as $cid1) {
                                                                $sql=mysqli_query($con,"select * from category");
                                                                while($row=mysqli_fetch_array($sql))
                                                                {
                                                                    ?>
                                                                    <option value="<?php echo $row['cid'] ?>" <?php
                                                                    if($row['cid']==$cid1){
                                                                       echo "selected=";?>"selected" 
                                                                       <?php }?>><?php  echo $row['ecategory_flag'] ?></option>
                                                                       <?php

                                                                   }
                                                                }
                                                                   ?>  

                                                               </select></td></tr>
                                                           </table>
                                                       </div>
                                                   </div>
                                                   <center>
                                                      <?php if(isset($_POST['edit'])){?>
                                                      <button type="submit" name="submit_edit"  class="btn btn-primary pull-right">Update</button>
                                                      <?php } else { ?>
                                                      <button type="submit" name="submit" class="btn btn-primary pull-right">Add Expert</button>
                                                      <?php }  ?>
                                                  </center>
                                              </div>
                                              <!--  </form> -->
                                          </div>

                                      </form>
                                      <form method="post" action="">
                                          <table class="table table-striped table-bordered table-hover" id="mydata">
                                            <input  name="expert" type="hidden" id="hidden" />
                                            <thead>
                                               <tr>
                                                <th>First Name</th>
                                                <th>Last Name</th>
                                                <th>Email</th>
                                                <th>Langauge</th>
                                                <th>Contact Number</th>
                                                <!--<th>District</th>-->
                                                <th>Topic</th>
                                                <th>Edit/Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <?php
                                            include('connection.php');
                                            $result = mysqli_query($con,"SELECT * FROM `registration` where user_type='2' and status!='0'");


                                            while($reg=mysqli_fetch_array($result)){

                                                ?>

                                                <?php
                                                if($reg['lang_flag']=='3'){
                                                    $language='English';
                                                }
                                                else if($reg['lang_flag']=='1'){
                                                    $language='Gujarati';
                                                } 

                                                else if($reg['lang_flag']=='2'){
                                                    $language='Hindi';
                                                } 
                                                echo "<tr>";
                                                echo   "<td>".$reg['fname']."</td>";
                                                echo   "<td>".$reg['lname']."</td>";
                                                echo   "<td>".$reg['email']."</td>";
                                                echo   "<td>".$language."</td>";
                                                echo   "<td>".$reg['phoneno']."</td>";

                                                $result1= mysqli_query($con,"select * from district where did='".$reg['did']."'");
                                                if($log=mysqli_fetch_array($result1)){
                                                  // echo "<td>".$log['edistrict']."</td>"; 

                                               }

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
                                        }

                                        ?>



                                        <td><input type="submit" name="edit" value=" Edit " class="btn btn-primary" onclick="passvalue(<?php echo $reg['lid']?>)">
                                            <input type="submit" name="delete" value="Delete" class="btn btn-primary" onclick="passvalue(<?php echo $reg['lid']?>)">

                                            <?php
                                            echo "</tr>";
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
<script src="../assets/js/demo.js"></script>
<script src="../assets/js/select2.js"></script>
<script src="../assets/js/select2.min.js"></script>
<script src="../assets/js/jquery.dataTables.min.js"></script>
<script src="../assets/js/dataTables.bootstrap.min.js"></script>
<script src="hierarchy-select-master/dist/hierarchy-select.min.js"></script>
<script>
    $('#mydata').dataTable();
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.js-example-basic-multiple').select2();
    });
</script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#your-expertise-hierarchy').hierarchySelect({
            hierarchy: true,
            search: true,
            width: 250
        });
    });
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
    function formValidation() {
        var uemail = document.registration.email;
        var cno = document.registration.co_no;

        if(ValidateEmail(uemail))
        {
           if(Validatecno(cno)){
           }
       }
       return false;
   }
//    function Validatecno(cno)
//    {
//     var coformat = /^\d{10}$/;
//     if(cno.value.match(coformat))
//     {
//         return true;
//     }
//     else
//     {
//         alert("You have entered an invalid Contact Number!");
//         return false;
//     }
// }

   function ValidateEmail(uemail)
   {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(uemail.value.match(mailformat))
    {
        return true;
    }
    else
    {
        alert("You have entered an invalid email address!");
        uemail.focus();
        return false;
    }
}

</script>   
<script type="text/javascript">
    function passvalue(cid)
    {
        document.getElementById("hidden").value = cid;
        //alert(document.getElementById("hidden").value); 
    }
</script>



<!-- Mirrored from demos.creative-tim.com/material-dashboard/examples/table.html by HTTrack Website Copier/3.x [XR&CO'2014], Tue, 20 Mar 2018 03:27:17 GMT -->
</html>