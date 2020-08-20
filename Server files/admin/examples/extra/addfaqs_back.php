<?php
include('connection.php');
session_start();
$id=$_SESSION['fid'];
if(isset($_POST['submit'])){
    $cid=$_REQUEST['cid'];
    $equestion=$_REQUEST['equestion'];
    $eanswer=$_REQUEST['eanswer'];
    $hquestion=$_REQUEST['hquestion'];
    $hanswer=$_REQUEST['hanswer'];
    $gquestion=$_REQUEST['gquestion'];
    $ganswer=$_REQUEST['ganswer'];
    

    $select = mysqli_query($con,"select max(fid) as fid1 from faq");
    if($data=mysqli_fetch_array($select))
    {
        $no = $data['fid1'];
        $no=$no+1;
    }
    else
    {
        $no=1;
    }
    
    $sql=mysqli_query($con,"insert into faq values ('$no','$cid','$equestion','$eanswer',N'$gquestion',N'$ganswer',N'$hquestion',N'$hanswer','0','1')");

    ?>
    <script> alert('category updated sucessfully !!!!');
    window.open('faqs.php','_self');
</script>
<?php 


}

?>