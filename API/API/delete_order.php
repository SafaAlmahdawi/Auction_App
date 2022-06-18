<?php
include("connect.php");
///// read the data that given from Android Application 
$order = $_POST['orderID'];

$query_search = "delete from `purchaseOrders` where `orderID` = '$order'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Order deleted Successfully";
echo json_encode($response);
?>