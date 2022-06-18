<?php
include("connect.php");
$prodID = $_POST['prodID'];
$max_price = $_POST['max_price'];
$min_price = $_POST['min_price'];

$update_bid = mysqli_query($connect, "update `bidding` set `minPrice` = '$min_price', `maxPrice` = '$max_price', `lastPrice` = '$min_price' where `productID` = '$prodID' ");
$response["success"] = 1;
$response["message"] = "Estimate prices saved Successfully";
echo json_encode($response);
?>