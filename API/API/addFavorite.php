<?php
include("connect.php");
$prodID = $_POST['prodID'];
$buyerID = $_POST['buyerID'];
$select = mysqli_query($connect, "select * from `favorite` where `buyerID` = '$buyerID' and `productID` = '$prodID'");
if($select != null){
	$rows = mysqli_num_rows($select);
	if($rows != 0){
		$response["success"] = 0;
		$response["message"] = "Product already in your Favorite List";
	}else{
		$insert = mysqli_query($connect, "insert into `favorite` (`buyerID`, `productID`) values ('$buyerID', '$prodID')");
		$response["success"] = 1;
		$response["message"] = "Product added to your Favorite Successfully";
	}
}else{
	$insert = mysqli_query($connect, "insert into `favorite` (`buyerID`, `productID`) values ('$buyerID', '$prodID')");
	$response["success"] = 1;
	$response["message"] = "Product added to your Favorite Successfully";
}
echo json_encode($response);
?>