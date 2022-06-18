<?php
include("connect.php");
///// read the data that given from Android Application 
$prod = $_POST['prodID'];
$seller = $_POST['sellerID'];
$date = date('y-m-d');

$query_search = "insert into `request_bidding` (sellerID, productID, `requestDate`) values ('$seller', '$prod', '$date')";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Product Bidding Request Submit Successfully";
echo json_encode($response);
?>