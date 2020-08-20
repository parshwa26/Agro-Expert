<?php
include('connection.php');
include('session.php');
$parent=0;
if(isset($_POST['submit'])){
	
	$cid = $_POST['cid'];
	$text = $_POST['text'];
	$description = $_POST['description'];
	
	$select = mysqli_query($con,"select max(coid) as coid from content");
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['coid'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }

	if(empty($_FILES['image']['name'])){
			$image1 = "Not Uploaded";
	}
	else{
		$image = $_FILES['image']['name'];
		$ext= explode(".",$image);
		
		$image1 = $no."_IMAGE.".$ext[1];
		$target = "uploads/image/".$image1;
		
		if(move_uploaded_file($_FILES['image']['tmp_name'],$target)){
		echo "img upload";
		}
		else{
			echo "img not upload";
		}
	}
			
	
	
	if(empty($_FILES['pdf']['name'])){
		$pdf1 = "Not Uploaded";
	}
	else{
		$pdf = $_FILES['pdf']['name'];
		$ext1 = explode(".",$pdf);
		
		$pdf1 = $no."_PDF.".$ext1[1];
		$target1="uploads/pdf/".$pdf1;
		
		if(move_uploaded_file($_FILES['pdf']['tmp_name'],$target1)){
			echo "pdf upload";
		}
		else{
			echo "pdf not Uploaded";
		}
	}
	
	if(empty($_POST['aurl'])){
		$audio1 = "Not Uploaded";
	}
	else{
		$audio1 = $_POST['aurl'];
	}
	
	
	if(empty($_POST['vurl'])){
		$video1 = "Not Uploaded";
	}
	else{
		$video1 = $_POST['vurl'];
	}
	$sql = mysqli_query($con,"INSERT INTO `content` (`coid`,`cid`,`content_text`,`content_desc`,`image`,`pdf`,`audio`,`video`,`status`) VALUES ('$no','$cid','$text','$description','$image1','$pdf1','$audio1','$video1','1')");
    ?>
	<script>
		 alert('Information added sucessfully !!!!');
    window.open('information.php','_self');
	</script>
	<?php
}

if(isset($_POST['edit'])){
	
	$coid=$_POST['information'];
    $_SESSION['coid']=$coid;
	$sql5 = mysqli_query($con,"SELECT cat.ecategory_flag, c.coid, c.content_text, c.content_desc, c.image, c.pdf, c.audio, c.video FROM content c INNER JOIN category cat ON c.cid = cat.cid WHERE c.coid =  '$coid'");
	$row_query=mysqli_fetch_array($sql5);
	$category = $row_query['ecategory_flag'];
	$text = $row_query['content_text'];
	$desc = $row_query['content_desc'];
	$image = $row_query['image'];
	$pdf = $row_query['pdf'];
	$audio = $row_query['audio'];
	$video = $row_query['video'];
	
}

if(isset($_POST['submit_edit'])){
	$text = $_POST['text']; 
	$desc = $_POST['description'];
	$audio = $_POST['aurl'];
	$video = $_POST['vurl'];
	
	if(empty($_FILES['image']['name'])){
		$image2 = $_POST['image2'];
	}
	else{
		$image3 = $_FILES['image4']['name'];
		$ext4= explode(".",$image);
		
		echo $image2 = $coid."_IMAGE.".$ext4[1];
		$target4 = "uploads/image/".$image2;
		
		if(move_uploaded_file($_FILES['image4']['tmp_name'],$target4)){
		echo "img upload";
		}
		else{
			echo "img not upload";
		}
	}
	
	if(empty($_FILES['pdf']['name'])){
		$pdf2 = $_POST['pdf2'];
	}
	else{
		$pdf = $_FILES['pdf4']['name'];
		$ext5 = explode(".",$pdf);
		
		echo $pdf2 = $coid."_PDF.".$ext5[1];
		$target5="uploads/pdf/".$pdf2;
		
		/*if(move_uploaded_file($_FILES['pdf4']['tmp_name'],$target5)){
			echo "pdf upload";
		}
		else{
			echo "pdf not Uploaded";
		}*/
	}
}

if(isset($_POST['delete'])){
	$coid = $_POST['information'];
	$row_query3=mysqli_query($con,"update content set status='0' where coid='$coid'");
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
			<li class="active">
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
                        <h4 class="title">Information</h4>
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
					<form action="" method="post" enctype="multipart/form-data">
					<div class="row">
					<div class="col-md-12">
					<div class="btn-group hierarchy-select" data-resize="auto" id="your-expertise-hierarchy">
                                <button type="button" class="dropdown-toggle" data-toggle="dropdown" style="background-color: #fff; outline: none !important;" >
                                    <span class="selected-label pull-left"> </span>
                                    <span class="caret"></span>
                                    <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <div class="dropdown-menu open">
                                    <ul class="dropdown-menu inner" role="menu" name="cid" id="category">

                                        
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
					<div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Text</label>
                                        <input type="text" class="form-control" name="text" value="<?php echo $text; ?>" required>
                                    </div>
                                </div>
								<div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Add Description</label>
										<textarea rows="4" cols="140" name="description"><?php echo $desc; ?></textarea>
                                    </div>
                                </div>
								<div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Audio URL</label>
                                        <input type="text" class="form-control" name="aurl" value="<?php echo $audio; ?>">
                                    </div>
                                </div>
								<div class="col-md-12">
                                    <div class="form-group label-floating">
                                        <label class="control-label">Video URL</label>
                                        <input type="url" class="form-control" name="vurl" value="<?php echo $video; ?>">
                                    </div>
                                </div>
								<?php if(isset($_POST['edit'])) { 
								$sql6 = mysqli_query($con, "select * from content where coid='$coid'");
								while($result3=mysqli_fetch_array($sql6)){
								echo "<a href='uploads/image/".$result3['image']."'  target='_blank'><img src='uploads/image/".$result3['image']."' style='width:50px; text-align: left;'></a>";
								
								?>
								<input type="hidden" name="image2" value="<?php echo $result3['image']; ?>">
								<div class="col-md-12">
                                        <label>Upload Image</label>
										<input type="file" name="image">
                                </div>
								
								<div class="col-md-12">
								<?php echo "<a href='uploads/pdf/".$result3['pdf']."'  target='_blank'>".$result3['pdf']."</a>"; ?>
								<input type="hidden" name="pdf2" value="<?php echo $result3[pdf]; ?>"
                                        <br><label>Upload PDF</label>
										<input type="file" name="pdf">
                                </div>
								<?php }} else { ?>
								<div class="col-md-12">
                                        <label>Upload Image</label>
										<input type="file" name="image">
                                </div>
								<div class="col-md-12">
                                        <label>Upload PDF</label>
										<input type="file" name="pdf">
                                </div>
								<?php } ?>
								
								<center>
								<?php if(isset($_POST['edit'])){?>
                            <button type="submit" name="submit_edit"  class="btn btn-primary pull-right">Update</button>
                        <?php } else { ?>
                          <button type="submit" name="submit" class="btn btn-primary pull-right">Add Information</button>
                      <?php }  ?>
								</center>
					</div>
                        
                </form>
            </div>
        </div>
    </div>
</div>
<form method="post" action="">
    <div class="card-content">
      <table class="table table-striped table-bordered table-hover" id="mydata">
        <input  name="information" type="hidden" id="hidden" />
        <thead>
            <th>Category Name</th>
			<th>Text</th>
			<th>Descriptioin</th>
			<th>Image</th>
			<th>PDF</th>
			<th>Audio</th>
			<th>Video</th>
			<th>Edit/Delete</th>
        </thead>
        <tbody>
			<?php
			
			$result = mysqli_query($con,"select * from content c INNER JOIN category cat ON c.cid = cat.cid where c.status!='0'");
			while($row=mysqli_fetch_array($result)){
				echo "<tr>";
				echo"<td>".$row['ecategory_flag']."</td>";
				echo "<td>".$row['content_text']."</td>";
				echo "<td>".$row['content_desc']."</td>";
				if($row['image']=='Not Uploaded'){
					echo "<td>".$row['image']."</td>";
				}else{
					echo "<td><img src='uploads/image/".$row['image']."' width='50px'></td>";
				}
				if($row['pdf']=='Not Uploaded'){
					echo "<td>".$row['pdf']."</td>";
				}else{
					echo "<td><a href='uploads/pdf/".$row['pdf']."'  target='_blank'>".$row['pdf']."</a></td>";
				}
				if($row['audio']=='Not Uploaded'){
					echo "<td>".$row['audio']."</td>";
				}else{
					echo "<td><a href='".$row['audio']."'  target='_blank'>".$row['audio']."</a></td>";
				}
				if($row['video']=='Not Uploaded'){
					echo "<td>".$row['video']."</td>";
				}else{
					echo "<td><a href='".$row['video']."'  target='_blank'>".$row['video']."</a></td>";
				}
				//echo "<td> <a href='uploads/pdf/".$row['pdf']."'  target='_blank'>".$row['pdf']."</a></td>";
				//echo "<td> <a href='".$row['url']."'  target='_blank'>".$row['audio']."</a></td>";
				//echo "<td> <a href='".$row['url']."'  target='_blank'>".$row['video']."</a></td>";
				?>

                <td>
                    <input type="submit" name="edit" value=" Edit " class="btn btn-primary" onclick="passvalue(<?php echo $row['coid']?>)">
                    <input type="submit" name="delete" value="Delete" class="btn btn-primary" onclick="passvalue(<?php echo $row['coid']?>)">


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
<script src="hierarchy-select-master/dist/hierarchy-select-edit.js"></script>
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
    function passvalue(coid)
    {
        document.getElementById("hidden").value = coid;
		
        //alert(document.getElementById("hidden").value); 
    }
</script>
<script>
  $('#mydata').dataTable();
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