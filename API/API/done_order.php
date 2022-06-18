<?php
include("connect.php");
$deliverID = $_POST['deliverID'];
$orderID = $_POST['orderID'];
$orderType = $_POST['orderType'];
$state ="Done";
if($orderType == "Purchase"){
	$update = mysqli_query($connect, "update `purchaseOrders` set `orderStatus` = '$state' where `orderID` = '$orderID'");
}else if($orderType == "Bidding"){
	$update = mysqli_query($connect, "update `bidding` set `bidStatus` = '$state' where `bidID` = '$orderID'");
}

$update = mysqli_query($connect, "update `deliver_orders` set `orderStatus` = '$state' where `orderType` = '$orderType' and `orderID` = '$orderID' and `deliverID` = '$deliverID'");
$response["success"] = 1;
$response["message"] = "Order delivered Successfully";
echo json_encode($response);
?>