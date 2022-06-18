<?php
include("connect.php");
$prodID = $_POST['prodID'];
$buyerID = $_POST['buyerID'];
$prodPrice = $_POST['prodPrice'];
$payment = $_POST['payment'];
$date = date('y-m-d');

$insert = mysqli_query($connect, "insert into `purchaseOrders` (`buyerID`, `productID`,`orderDate`,`paymentInfo`, `paymentValue`) values ('$buyerID', '$prodID', '$date', '$payment', '$prodPrice')");
$response["success"] = 1;
$response["message"] = "Buy Product Order submitted Successfully";
echo json_encode($response);
?>