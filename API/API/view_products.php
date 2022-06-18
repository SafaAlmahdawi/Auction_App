<?php
include("connect.php");
$state = "wait";
$result = mysqli_query($connect,"SELECT * FROM `product`");
// check for empty result
if (mysqli_num_rows($result) > 0) {
    $response["products"] = array();
    while ($row = mysqli_fetch_array($result)) { 
        $app = array();
		$app["prodID"] = $row["productID"];
        $app["prodTitle"] = $row["productTitle"];
		$app["prodQty"] = $row["productQuantity"];
		$app["prodCat"] = $row["productType"];
		$app["prodPrice"] = $row["productPrice"];
		$app["prodModel"] = $row["productModel"];
		$app["prodStatus"] = $row["productStatus"];
		$app["prodSeller"] = $row["sellerID"];
		$app["prodImg1"] = $row["image1"];
		$app["prodImg2"] = $row["image2"];
		$app["prodImg3"] = $row["image3"];
		$app["prodImg4"] = $row["image4"];
        array_push($response["products"], $app);
    }
    // success read list of malls  
    $response["success"] = 1;
    // echoing JSON response
    echo json_encode($response);
} else {
    // no malls found
    $response["success"] = 0;
    $response["message"] = "No products found";
    echo json_encode($response);
}
?>