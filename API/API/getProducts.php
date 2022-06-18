<?php
include("connect.php");
 
 $sql = "select * from photos";
 
 $res = mysqli_query($connect,$sql);
 
 $result = array();
 
 while($row = mysqli_fetch_array($res)){
 array_push($result,array('url'=>$row['image']));
 }
 
 echo json_encode(array("result"=>$result));
 
 mysqli_close($con);
?>