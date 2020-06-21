<?php  
//export.php  
include('connection.php');
$output = '';
if(isset($_POST["export"]))
{
 
 
 $result = mysqli_query($con,"SELECT * FROM `registration` where user_type='fr'");
 if(mysqli_num_rows($result) > 0)
 {
 
   ?><table class="table" bordered="1">  
                    <tr>  
                         <th>ID</th>  
                         <th>contace no</th>  
                         <th>district</th>  
    
                    </tr>
 <?php
  
                                 $count=1;
                                            while($reg=mysqli_fetch_array($result)){

                                                echo "<tr><td>".$count."</td>"; 
                                                $result1 = mysqli_query($con,"SELECT * FROM `login` where lid='".$reg['lid']."'");
                                                 while($reg1=mysqli_fetch_array($result1)){

                                                    echo
                                                        "
                                                          <td>{$reg1['phoneno']}</td>
                                                          <td>{$reg1['district']}</td> 
                                                        </tr>\n";
                                                     }
                                                     $count++;


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
