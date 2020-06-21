<?php
        include('connection.php');
        session_start();
        $id=$_SESSION['fid'];
        $equestion=$_REQUEST['equestion'];
        $eanswer=$_REQUEST['eanswer'];
        $hquestion=$_REQUEST['hquestion'];
        $hanswer=$_REQUEST['hanswer'];
        $gquestion=$_REQUEST['gquestion'];
        $ganswer=$_REQUEST['ganswer'];


        
        
        $sql=mysqli_query($con,"update faq set equestion='$equestion',eanswer= '$eanswer',gquestion=N'$gquestion',ganswer=N'$ganswer',hquestion=N'$hquestion',hanswer=N'$hanswer' where fid='$id'");
       
       ?>
                  <script> alert('faq updated sucessfully !!!!');
                  window.open('faqs.php','_self');
                  </script>
                <?php 
?>