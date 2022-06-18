<?php
include("connect.php");
///// read the data that given from Android Application 
$req = $_POST['reqID'];
$start = $_POST['bidStart'];
$end = $_POST['bidEnd'];
$state = "Accepted";

$query_search = "update `request_bidding` set `requestState` = '$state' where `requestID` = '$req'";
$query_exec = mysqli_query($connect,$query_search);

///// read seller and product of the request ////////////////
$select = mysqli_query($connect, "select * from `request_bidding` where `requestID` = '$req'");
if($record = mysqli_fetch_array($select)){
	$sellerID = $record['sellerID'];
	$prodID = $record['productID'];
	///// check if the product is found in bidding table ////////////////////
	$bidState = "wait";
	$selectProd = mysqli_query($connect, "select * from `bidding` where `bidStatus` = '$bidState' and `productID` = '$prodID' ");
	$num_rows = mysqli_num_rows($selectProd);
	if($num_rows == 0){
		$insert_bid = mysqli_query($connect, "insert into `bidding` (`bidStart`, `bidEnd`, `sellerID`, `productID`) values ('$start', '$end', '$sellerID', '$prodID')");
		$response["success"] = 1;
		$response["message"] = "Selected Request accepted Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "Product Bidding is already accepted";
		echo json_encode($response);
	}
}
 
?>