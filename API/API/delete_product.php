<?php
include("connect.php");
///// read the data that given from Android Application 
$prod = $_POST['prodID'];

$query_search = "delete from `product` where `productID` = '$prod'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Product deleted Successfully";
echo json_encode($response);
?>