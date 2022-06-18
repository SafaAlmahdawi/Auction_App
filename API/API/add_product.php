<?php
include("connect.php");

$title = $_POST['title'];
$quantity = $_POST['quantity'];
$category = $_POST['category'];
$status = $_POST['status'];
$price = $_POST['price'];
$model = $_POST['model'];
$seller = $_POST['id'];
	 $response["success"] = 1;
	 $insert = mysqli_query($connect,"insert into `product` (`productTitle`, `sellerID`, `productType`, `productPrice`, `productQuantity`, `productStatus`, `productModel`) 
		 values ('$title', '$seller' , '$category', '$price', '$quantity', '$status', '$model')");
		 ///// read the ID of the saved product ////////////////
		 $select = mysqli_query($connect, "select * from `product` order by productID desc");
		 if($found = mysqli_fetch_array($select)){
		 	$prodID = $found['productID'];
			$response["message"] = $prodID;
		 	echo json_encode($response);
		 }
?>