<?php
include("connect.php");
$state = "Assigned";
$type = "Purchase";
$result = mysqli_query($connect,"SELECT o.orderID, d.buyerID, prodID, deliverID from `purchaseOrders` o, `deliver_orders` d where d.orderID = o.orderID and o.orderStatus = '$state' and `orderType` = '$type'");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["delivers"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["orderID"] = $row["orderID"];
		$app["buyerID"] = $row["buyerID"];
		$app["prodID"] = $row["prodID"];
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
    $response["message"] = "No orders found";
    echo json_encode($response);
}
?>