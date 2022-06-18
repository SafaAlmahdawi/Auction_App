<?php
include("connect.php");
$prodID = $_POST['prodID'];
$value = $_POST['rateValue'];

$addRate = mysqli_query($connect, "insert into `productRate` (`rateValue`, `productID`) values ('$value', '$prodID')");
$response["success"] = 1;
$response["message"] = "Product Rate saved Successfully";
echo json_encode($response);
?>