<?php
include("connect.php");
$bidID = $_POST['bidID'];
$suggest_price = $_POST['price'];
$buyerID = $_POST['buyerID'];

$update_bid = mysqli_query($connect, "update `bidding` set `lastPrice` = '$suggest_price', `winnerID` = '$buyerID' where `bidID` = '$bidID' ");
$insert = mysqli_query($connect, "insert into `bidding_participate` (`buyerID`, `bidID`, `suggestPrice`) values ('$buyerID', '$bidID', '$suggest_price')");
$response["success"] = 1;
$response["message"] = "Your participation added Successfully";
echo json_encode($response);
?>