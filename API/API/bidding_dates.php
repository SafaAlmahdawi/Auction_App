<?php
include("connect.php");
$prodID = $_POST['prodID'];

$select_bid = mysqli_query($connect, "select * from `bidding` where `productID` = '$prodID' ");
if(mysqli_num_rows($select_bid) != 0){
	$found = mysqli_fetch_array($select_bid);
	$bidStart = $found['bidStart'];
	$bidEnd = $found['bidEnd'];
	$response["success"] = 1;
	$response["message"] = $bidStart . "###" . $bidEnd;
}else{
	$bidStart = "No Bid";
	$bidEnd = "No Bid";
	$response["success"] = 0;
	$response["message"] = $bidStart . "###" . $bidEnd;
}
echo json_encode($response);
?>