<?php
include("connect.php");
///// read the data that given from Android Application 
$order = $_POST['orderID'];
$pay = $_POST['pay'];
$card = $_POST['card'];
$expire_month = $_POST['expire_month'];
$expire_year = $_POST['expire_year'];
$verify_code = $_POST['verify_code'];
$state = "Confirmed";
$paymentInfo = $pay . " Card Type: " . $card . " Expiration: " . $expire_month . "-" . $expire_year . " Verification Code: " . $verify_code;

$query_search = "update `purchaseOrders` set `orderStatus` = '$state', `paymentInfo` = '$paymentInfo' where `orderID` = '$order'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Order Confirmed Successfully";
echo json_encode($response);
?>