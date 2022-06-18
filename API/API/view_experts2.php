<?php
include("connect.php");
$state = "wait";
$result = mysqli_query($connect,"SELECT * FROM `expert` where `accountStatus` = '$state'");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["users"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["userID"] = $row["expertID"];
        $app["userName"] = $row["expertName"];
		$app["userEmail"] = $row["expertEmail"];
		$app["userPhone"] = $row["expertPhone"];
		$app["userAddress"] = "Experience1: " . $row["experience1"] . "\n Experience2: " . $row["experience2"] . "\n Experience3: " . $row["experience3"];
        array_push($response["users"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No experts found";
    echo json_encode($response);
}
?>