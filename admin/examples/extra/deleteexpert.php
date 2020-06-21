<?php

    include('connection.php');
     
     $id=$_REQUEST['lid'];

     
     $sql=mysqli_query($con,"update registration set status='0' where lid='$id'");
     $sql1=mysqli_query($con,"update expertaction set status='0' where lid='$id'");
     $sql=mysqli_query($con,"update login set status='0' where lid='$id'");
    
      ?>
	           <script> alert('expert deleted sucessfully !!!!');
	           window.open('expert.php','_self');
	           </script>
         	<?php 
?>