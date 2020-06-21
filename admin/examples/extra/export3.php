<?php  
//export.php  
include('connection.php');
$output = '';
if(isset($_POST["export"]))
{
 
 

 
   ?><table class="table" bordered="1">  
                    <tr>  
                         <th>District</th>  
                         <th>No. of Question</th>  
                           
    
                    </tr>
 <?php
  
                                 

                                          $result = mysqli_query($con,"SELECT * FROM `login` ");
                                          while( $row = mysqli_fetch_assoc( $result ))
                                          {

                                             echo   "<tr><td>".$row['district']."</td>";
                                             $result1= mysqli_query($con,"select COUNT(qid) from question where lid='".$row['lid']."' ");

                                            if($row1=mysqli_fetch_array($result1))
                                            {
                                                echo "<td>".$row1['COUNT(qid)']."</td>";
                                            }
                                            echo "</tr>";
                                          }
                                        
                                        ?>
</table>

<?php
  header('Content-Type: application/xls');
  header('Content-Disposition: attachment; filename=download.xls');
  echo $output;
 }

?>
