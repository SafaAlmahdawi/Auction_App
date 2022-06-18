<?php
include("connect.php");

$result = mysqli_query($connect,"SELECT bidID, p.buyerID, suggestPrice, buyerName from `bidding_participate` p, `buyer` b where b.buyerID = p.buyerID order by `partID` desc");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["bidders"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["bidID"] = $row["bidID"];
        $app["buyerID"] = $row["buyerID"];
		$app["buyerName"] = $row["buyerName"];
		$app["suggestPrice"] = $row["suggestPrice"];
        array_push($response["bidders"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No bidders found";
    echo json_encode($response);
}
?>