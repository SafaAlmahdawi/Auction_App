<?php
include("connect.php");
///// read the data that given from Android Application 
$req = $_POST['reqID'];
$state = "Rejected";

$query_search = "update `request_bidding` set `requestState` = '$state' where `requestID` = '$req'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Selected Request rejected Successfully";
echo json_encode($response);
?>