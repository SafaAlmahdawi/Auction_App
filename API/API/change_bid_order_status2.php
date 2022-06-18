<?php
include("connect.php");
///// read the data that given from Android Application 
$bid = $_POST['bidID'];
$pay = $_POST['pay'];
$card = $_POST['card'];
$expire_month = $_POST['expire_month'];
$expire_year = $_POST['expire_year'];
$verify_code = $_POST['verify_code'];
$state = "Confirmed";
$paymentInfo = $pay . " Card Type: " . $card . " Expiration: " . $expire_month . "-" . $expire_year . " Verification Code: " . $verify_code;

$query_search = "update `bidding` set `bidStatus` = '$state', `paymentInfo` = '$paymentInfo' where `bidID` = '$bid'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Winning Product Purchase Confirmed Successfully";
echo json_encode($response);
?>