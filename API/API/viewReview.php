<?php
include("connect.php");

$result = mysqli_query($connect,"SELECT `commentID`, `commentBody`, `commentDate`, `productID`, `buyerName` FROM `comment` c, `buyer` b where b.buyerID = c.buyerID");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["reviews"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["reviewProd"] = $row["productID"];
        $app["reviewID"] = $row["commentID"];
		$app["reviewBody"] = $row["commentBody"];
		$app["reviewDate"] = $row["commentDate"];
		$app["buyerName"] = $row["buyerName"];
        array_push($response["reviews"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No reviews found";
    echo json_encode($response);
}
?>