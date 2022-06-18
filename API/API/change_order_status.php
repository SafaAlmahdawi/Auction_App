<?php
include("connect.php");
///// read the data that given from Android Application 
$order = $_POST['orderID'];
$pay = $_POST['pay'];
$state = "Confirmed";

$query_search = "update `purchaseOrders` set `orderStatus` = '$state', `paymentInfo` = '$pay' where `orderID` = '$order'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Order Confirmed Successfully";
echo json_encode($response);
?>