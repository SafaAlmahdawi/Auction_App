<?php
include("connect.php");
$prodID = $_POST['prodID'];
$buyerID = $_POST['buyerID'];
$review = $_POST['review'];
$date = date('y-m-d');

$addReview = mysqli_query($connect, "insert into `comment` (`buyerID`, `productID`,`commentBody`,`commentDate`) values ('$buyerID', '$prodID', '$review', '$date')");
$response["success"] = 1;
$response["message"] = "Product Review saved Successfully";
echo json_encode($response);
?>