<?php  
//export.php  
include('connection.php');
$output = '';
if(isset($_POST["export"]))
{
 
 

 
   ?><table class="table" bordered="1">  
                    <tr>  
                         <th>Question</th>  
                         <th>Likes</th>  
                    
                    </tr>
                    <?php

$result1= mysqli_query($con,"select a.question as questions,COUNT(b.likeid) as likes from `like` b,question a where b.qid=a.qid group by(a.qid) order by(likes) ");

while($row1=mysqli_fetch_array($result1))
{   
   echo "<tr><td>".$row1['questions']."</td>";
   echo "<td>".$row1['likes']."</td>";
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
