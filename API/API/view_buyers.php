<?php
include("connect.php");
$result = mysqli_query($connect,"SELECT * FROM `buyer`");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["users"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["userID"] = $row["buyerID"];
        $app["userName"] = $row["buyerName"];
		$app["userEmail"] = $row["buyerEmail"];
		$app["userPhone"] = $row["buyerPhone"];
		$app["userAddress"] = $row["buyerAddress"];
        array_push($response["users"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No buyers found";
    echo json_encode($response);
}
?>