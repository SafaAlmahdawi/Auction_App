<?php
include("connect.php");
$state = "wait";
$date = date('y-m-d');
$result = mysqli_query($connect,"SELECT bidID, b.productID, productTitle, lastPrice, bidEnd, bidStart, productType, productModel, image1  FROM `product` p , `bidding` b Where b.productID = p.productID and `bidStatus` = '$state'");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["bidding"] = array();
    while ($row = mysqli_fetch_array($result)) { 
		$start_date = strtotime($row["bidStart"]);
		$end_date = strtotime($row["bidEnd"]);
		$today = strtotime($date);
		if($start_date <= $today && $end_date >= $today){
		$lastDate = $end_date * 1000;
				$app = array();
				$app["bidID"] = $row["bidID"];
				$app["prodID"] = $row["productID"];
				$app["prodTitle"] = $row["productTitle"];
				$app["prodCat"] = $row["productType"];
				$app["prodPrice"] = $row["lastPrice"];
				$app["prodModel"] = $row["productModel"];
				$app["lastDate"] = $lastDate;
				$app["prodImg"] = $row["image1"];
				array_push($response["bidding"], $app);
			}
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No bidding found";
    echo json_encode($response);
}
?>