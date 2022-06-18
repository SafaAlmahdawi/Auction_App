<?php
include("connect.php");
$state = "close";
$result = mysqli_query($connect,"SELECT productTitle, bidEnd, bidID, winnerID, b.productID, lastPrice from `bidding` b, `product` p where p.productID = b.productID and `bidStatus` = '$state' order by `bidID` desc");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["wins"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["prodName"] = $row["productTitle"];
        $app["orderDate"] = $row["bidEnd"];
		$app["bidID"] = $row["bidID"];
		$app["buyerID"] = $row["winnerID"];
		$app["prodID"] = $row["productID"];
		$app["prodPrice"] = $row["lastPrice"];
        array_push($response["wins"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No Bids found";
    echo json_encode($response);
}
?>