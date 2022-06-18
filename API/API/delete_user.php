<?php
include("connect.php");
///// read the data that given from Android Application 
$userID = $_POST['userID'];
$type = $_POST['type'];
if($type == "Buyer"){
	$query_search = "delete from `buyer` where `buyerID` = '$userID'";
	$query_exec = mysqli_query($connect,$query_search);
	 $response["success"] = 1;
	 $response["message"] = "Buyer deleted Successfully";
}
else if($type == "Seller"){
	$query_search = "delete from `seller` where `sellerID` = '$userID'";
	$query_exec = mysqli_query($connect,$query_search);
	 $response["success"] = 1;
	 $response["message"] = "Seller deleted Successfully";
}
else if($type == "Expert"){
	$query_search = "delete from `expert` where `expertID` = '$userID'";
	$query_exec = mysqli_query($connect,$query_search);
	 $response["success"] = 1;
	 $response["message"] = "Expert deleted Successfully";
}
else if($type == "Delivery Captain"){
	$query_search = "delete from `delivery_captain` where `captainID` = '$userID'";
	$query_exec = mysqli_query($connect,$query_search);
	 $response["success"] = 1;
	 $response["message"] = "Captain deleted Successfully";
}
 echo json_encode($response);
?>