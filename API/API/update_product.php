<?php
include("connect.php");

$title = $_POST['title'];
$quantity = $_POST['quantity'];
$category = $_POST['category'];
$status = $_POST['status'];
$price = $_POST['price'];
$model = $_POST['model'];
$prodID = $_POST['id'];
	 $response["success"] = 1;
	 $insert = mysqli_query($connect,"update `product` set `productTitle` = '$title', `productType` = '$category', `productPrice` = '$price', `productQuantity` = '$quantity',
	  `productStatus` = '$status', `productModel` = '$model' where `productID` = '$prodID' ");
			$response["message"] = "Product updated successfully";
		 	echo json_encode($response);
?>