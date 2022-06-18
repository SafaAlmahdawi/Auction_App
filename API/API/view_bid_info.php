<?php
include("connect.php");
$prodID = $_POST['prodID'];
$start_date = "-";
$end_date = "-";
$startPrice = "0";
$bid = mysqli_query($connect, "select * from `bidding` where `productID` = '$prodID' Order by `productID` desc");
if($bid != null){
	$found = mysqli_fetch_array($bid); 
	$start_date = $found['bidStart'];
	$end_date = $found['bidEnd'];
	$startPrice = $found['lastPrice'];
	$response["success"] = 1;
	$response["message"] = $start_date . "###" . $end_date . "###" . $startPrice;
}else{
	$response["success"] = 0;
	$response["message"] = $start_date . "###" . $end_date . "###" . $startPrice;
}
echo json_encode($response);
?>