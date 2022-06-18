<?php
include("connect.php");
$state = "Wait";
$result = mysqli_query($connect,"SELECT `orderID`, `orderDate`, `buyerID`, o.productID, `productTitle`, `productPrice`, `quantity` from `purchaseOrders` o, `product` p where p.productID = o.productID and `orderStatus` = '$state' order by `orderID` desc");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["prods"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["prodName"] = $row["productTitle"];
        $app["orderDate"] = $row["orderDate"];
		$app["orderID"] = $row["orderID"];
		$app["buyerID"] = $row["buyerID"];
		$app["prodID"] = $row["productID"];
		$app["prodPrice"] = $row["productPrice"];
		$app["prodQty"] = $row["quantity"];
        array_push($response["prods"], $app);
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