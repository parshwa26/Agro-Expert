 <?php


        include('connection.php');
        if(isset($_POST['submit'])){
        	$cat1=$_POST['cat1'];
            $cat2=$_POST['cat2'];
            $cat3=$_POST['cat3'];
            $category=$_POST['cid'];	

        	
        	$select = mysqli_query($con,"select max(cid) as cid1 from category"); 
            if($data=mysqli_fetch_array($select))
            {
                $no = $data['cid1'];
                $no=$no+1;
            }
            else
            {
                $no=1;
            }
            
            if($category!="root")
            {
                $select1= mysqli_query($con,"select * from category where cid=$category");
                if($data1=mysqli_fetch_array($select1))
                {
                    $level=$data1['level'];
                    $level=$level+1;
                    $pid=$data1['cid'];
                }
            }

            else
            {
                $level='1';
                $pid='0';
            }
            


            $sql=mysqli_query($con,"INSERT INTO `category` VALUES ('$no',N'$cat1',N'$cat2',N'$cat3','$pid','$level','1')");
            ?>
              <script> 
               window.open('category.php','_self');
               alert('category inserted sucessfully !!!!');
              </script>
            <?php 
         
	         

        }


 ?>