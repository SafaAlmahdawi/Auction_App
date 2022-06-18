<?php
include("connect.php");
$state = "Assigned";
$type = "Bidding";
$result = mysqli_query($connect,"SELECT `bidID`, `winnerID`, productID, deliverID  from `bidding` b, `deliver_orders` d where b.bidID = d.orderID and `bidStatus` = '$state' and `orderType` = '$type' order by `bidID` desc");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["delivers"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["orderID"] = $row["bidID"];
		$app["buyerID"] = $row["winnerID"];
		$app["prodID"] = $row["productID"];
		$app["captainID"] = $row["deliverID"];
        array_push($response["delivers"], $app);
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