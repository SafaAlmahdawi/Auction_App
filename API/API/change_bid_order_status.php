<?php
include("connect.php");
///// read the data that given from Android Application 
$bid = $_POST['bidID'];
$pay = $_POST['pay'];
$state = "Confirmed";

$query_search = "update `bidding` set `bidStatus` = '$state', `paymentInfo` = '$pay' where `bidID` = '$bid'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Winning Product Purchase Confirmed Successfully";
echo json_encode($response);
?>