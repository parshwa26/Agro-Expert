<?php

    include('connection.php');
     session_start();
     $id=$_REQUEST['cid'];
     echo $id;
     $sql=mysqli_query($con,"update category set status='0' where cid='$id'");
     ?>
	          <script> alert('category deleted sucessfully !!!!');
	          window.open('category.php','_self');
	          </script>
        	<?php 
?>