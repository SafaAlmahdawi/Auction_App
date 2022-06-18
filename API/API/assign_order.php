<?php
include("connect.php");
$deliverID = $_POST['deliverID'];
$orderID = $_POST['orderID'];
$buyerID = $_POST['buyerID'];
$prodID = $_POST['prodID'];
$orderType = $_POST['orderType'];
$state ="Assigned";
if($orderType == "Purchase"){
	$update = mysqli_query($connect, "update `purchaseOrders` set `orderStatus` = '$state' where `orderID` = '$orderID'");
}else if($orderType == "Bidding"){
	$update = mysqli_query($connect, "update `bidding` set `bidStatus` = '$state' where `bidID` = '$orderID'");
}

$insert = mysqli_query($connect, "insert into `deliver_orders` (`orderType`, `orderID`, `deliverID`, `buyerID`, `prodID`) values ('$orderType', '$orderID', '$deliverID', '$buyerID', '$prodID')");
$response["success"] = 1;
$response["message"] = "Assign Order Success";
echo json_encode($response);
?>