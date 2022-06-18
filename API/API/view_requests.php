<?php
include("connect.php");
$state = "wait";
$result = mysqli_query($connect,"SELECT requestID, requestDate, productTitle, productPrice, productStatus, bidStart, bidEnd FROM `product` p, `request_bidding` r where p.productID = r.productID and requestState = '$state'");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["reqs"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["reqID"] = $row["requestID"];
        $app["reqDate"] = $row["requestDate"];
		$app["reqProdName"] = $row["productTitle"];
		$app["reqProdStatus"] = $row["productStatus"];
		$app["reqProdPrice"] = $row["productPrice"];
		$app["reqStart"] = $row["bidStart"];
		$app["reqEnd"] = $row["bidEnd"];
        array_push($response["reqs"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No requests found";
    echo json_encode($response);
}
?>