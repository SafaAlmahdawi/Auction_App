<?php
include("connect.php");
$state = "wait";
$result = mysqli_query($connect,"SELECT * FROM `delivery_captain` where `accountStatus` = '$state'");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["users"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["userID"] = $row["captainID"];
        $app["userName"] = $row["captainName"];
		$app["userEmail"] = $row["captainEmail"];
		$app["userPhone"] = $row["captainPhone"];
		$app["userAddress"] = "City: " . $row["captainCity"] . "\n Working Area: " . $row["work_area"];
        array_push($response["users"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No captains found";
    echo json_encode($response);
}
?>