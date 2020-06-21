<?php

    include('connection.php');
     session_start();
     $id=$_REQUEST['fid'];
     echo $id;
     $sql=mysqli_query($con,"update faq set status='0' where fid='$id'");
     ?>
	          <script> alert('faq deleted sucessfully !!!!');
	          window.open('faqs.php','_self');
	          </script>
        	<?php 
?>