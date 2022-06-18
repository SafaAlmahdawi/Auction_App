<?php
include("connect.php");
$result = mysqli_query($connect,"SELECT * FROM `seller`");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["users"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["userID"] = $row["sellerID"];
        $app["userName"] = $row["sellerName"];
		$app["userEmail"] = $row["sellerEmail"];
		$app["userPhone"] = $row["sellerPhone"];
		$app["userAddress"] = $row["sellerAddress"];
        array_push($response["users"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No sellers found";
    echo json_encode($response);
}
?>