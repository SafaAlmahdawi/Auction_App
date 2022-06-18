<?php
include("connect.php");
///// read the data that given from Android Application 
$revID = $_POST['revID'];

$query_search = "delete from `comment` where `commentID` = '$revID'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Comment deleted Successfully";
echo json_encode($response);
?>