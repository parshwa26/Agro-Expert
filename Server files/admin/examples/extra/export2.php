<?php  
//export.php  
include('connection.php');
$output = '';
if(isset($_POST["export"]))
{
 
 
 $result = mysqli_query($con,"SELECT * FROM `registration` where user_type='ex' ");
 if(mysqli_num_rows($result) > 0)
 {
 
   ?><table class="table" bordered="1">  
                    <tr>  
                         <th>ID</th>  
                         <th>name</th>  
                         <th>mobile no</th>
                         <th>district</th>
                         <th>topic</th>  
    
                    </tr>
 <?php
  
                                 $count=1;
                                            while($reg=mysqli_fetch_array($result)){
                                               echo "<tr><td>".$count."</td>"; 
                                               echo   "<td>".$reg['fname'].$reg['lname']."</td>";
                                               
                                               $result1= mysqli_query($con,"select * from login where lid='".$reg['lid']."'");
                                               if($log=mysqli_fetch_array($result1)){

                                                 echo "<td>".$log['phoneno']."</td>";
                                               echo "<td>".$log['district']."</td>"; 

                                                }

                                               $result2=mysqli_query($con,"select * from expertaction where lid='".$log['lid']."'");
                                               if($log1=mysqli_fetch_array($result2)){

                                                 $result3=mysqli_query($con,"select * from category where cid='".$log1['cid']."'");
                                                 if($cat=mysqli_fetch_array($result3)){

                                                    if($cat['pid']==0)
                                                    {
                                                        echo "<td>".$cat['ecategory_flag']."</td>";
                                                    }

                                                    else{
                                                    $result4=mysqli_query($con,"select * from category where cid='".$cat['pid']."'");
                                                    if($cat1=mysqli_fetch_array($result4)){
                                                    echo "<td>".$cat1['ecategory_flag']."</td>";
                                                    }
                                                    }
                                               }
                                                $count++;
                                                echo "</tr>";
                                            }
                                        }
                                      }
                                        ?>
</table>

<?php
  header('Content-Type: application/xls');
  header('Content-Disposition: attachment; filename=download.xls');
  echo $output;
 }

?>
