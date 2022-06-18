<?php
include("connect.php");
$email = $_POST['email'];
$pass = $_POST['pass'];
$type = $_POST['type'];

if($type == "Buyer"){
	$query_search = "update `buyer` set `userPass` = '$pass' where `buyerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$response["success"] = 1;
	$response["message"] = "Password reset successfully";
	echo json_encode($response);
}else if($type == "Seller"){
	$query_search = "update `seller` set `userPass` = '$pass' where `sellerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$response["success"] = 1;
	$response["message"] = "Password reset successfully";
	echo json_encode($response);
}else if($type == "Expert"){
	$query_search = "update `expert` set `userPass` = '$pass' where `expertEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$response["success"] = 1;
	$response["message"] = "Password reset successfully";
	echo json_encode($response);
}else if($type == "Delivery Captain"){
	$query_search = "update `delivery_captain` set `userPass` = '$pass' where `captainEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$response["success"] = 1;
	$response["message"] = "Password reset successfully";
	echo json_encode($response);
}else if($type == "Manager"){
	$query_search = "update `administrator` set `userPass` = '$pass' where `adminEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$response["success"] = 1;
	$response["message"] = "Password reset successfully";
	echo json_encode($response);
}

?>