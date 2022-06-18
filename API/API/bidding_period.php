<?php
include("connect.php");
///// read the data that given from Android Application 
$prod = $_POST['prodID'];
$seller = $_POST['sellerID'];
$startDate = $_POST['startDate'];
$endDate = $_POST['endDate'];
$date = date('y-m-d');

$query_search = "insert into `request_bidding` (sellerID, productID, `requestDate`, `bidStart`, `bidEnd`) values ('$seller', '$prod', '$date', '$startDate', '$endDate')";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Product Bidding Request Submit Successfully";
echo json_encode($response);

?>