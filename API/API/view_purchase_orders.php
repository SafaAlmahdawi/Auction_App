<?php
include("connect.php");
$state = "Confirmed";
$result = mysqli_query($connect,"SELECT `orderID`, `buyerID`, `productTitle`, `productPrice`, `paymentInfo`, p.productID from `purchaseOrders` o, `product` p where p.productID = o.productID and `orderStatus` = '$state' order by `orderID` desc");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["delivers"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["prodName"] = $row["productTitle"];
		$app["orderID"] = $row["orderID"];
		$app["buyerID"] = $row["buyerID"];
		$app["prodPrice"] = $row["productPrice"];
		$app["payment"] = $row["paymentInfo"];
		$app["prodID"] = $row["productID"];
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