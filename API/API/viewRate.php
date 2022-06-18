<?php
include("connect.php");
$prodID = $_POST['prodID'];
$rate_value = "0"; 

$Rate = mysqli_query($connect, "select avg(rateValue) as `rateVal` from `productRate` where `productID` = '$prodID' Group by `productID`");
if($Rate != null){
	$found = mysqli_fetch_array($Rate); 
	$rate_value = $found['rateVal'];
	$response["success"] = 1;
	$response["message"] = $rate_value;
}else{
	$response["success"] = 0;
	$response["message"] = $rate_value;
}
echo json_encode($response);
?>