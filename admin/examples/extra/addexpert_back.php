<?php


include('connection.php');
if(isset($_POST['submit'])){
	$fname=$_REQUEST['fname'];
	$lname=$_REQUEST['lname'];
	$mobile=$_REQUEST['co_no'];
	$language=$_REQUEST['language'];
	$unit=$_REQUEST['uid'];
	$district=$_REQUEST['district'];
	$email=$_REQUEST['email'];



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
			$sql4=mysqli_query($con,"select * from category where ecategory_flag='$category'");
			while($result4=mysqli_fetch_array($sql4)) {
				if($length>1)
				{
					$cid=$cid.$result4['cid'].",";
					$length=$length-1;
				}
				else
				{
					$cid=$cid.$result4['cid'];
					$length=$length-1;
				}
				
				
			}
		
	}


	$sql=mysqli_query($con,"select * from unit where uid=$unit");
	while($result=mysqli_fetch_assoc($sql))
	{
		$zid=$result['zid'];
	}

	$sql1=mysqli_query($con,"select * from district where edistrict='$district'");
	while($result1=mysqli_fetch_assoc($sql1))
	{
		$did=$result1['did'];
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


	$sql2=mysqli_query($con,"INSERT INTO `registration`(`lid`,`did`,`zid`, `fname`, `lname`, `email`, `phoneno`,`lang_flag`,`user_type`,`status`) VALUES ('$no','$did','$zid','$fname','$lname','$email','$mobile','$lang','2','1')"); 
	$sql3=mysqli_query($con,"INSERT INTO `expertaction`(`lid`, `cid`,`status`) VALUES ('$no','$cid','1')");


	 ?>
         <script> alert('expert added sucessfully !!!!');
               window.open('expert.php','_self');
               </script>
             <?php 
	

}

?>