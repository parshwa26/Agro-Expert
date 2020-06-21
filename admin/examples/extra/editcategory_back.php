<?php
        include('connection.php');
        session_start();
        $id=$_SESSION['cid'];
        $newcat1=$_REQUEST['newcat1'];
        $newcat2=$_REQUEST['newcat2'];
        $newcat3=$_REQUEST['newcat3'];

       
        $sql=mysqli_query($con,"update category set ecategory_flag=N'$newcat1',hcategory_flag=N'$newcat3',gcategory_flag=N'$newcat2' where cid=N'$id'");
        ?>
        <script> alert('category updated sucessfully !!!!');
	          window.open('category.php','_self');
	          </script>
        	<?php 
?>