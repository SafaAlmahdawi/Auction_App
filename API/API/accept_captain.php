<?php
include("connect.php");
///// read the data that given from Android Application 
$user = $_POST['userID'];
$state = "Accepted";

///// select the captain email and send the accepted notification to the captain ////
$select = mysqli_query($connect, "select * from `delivery_captain` where `captainID` = '$user'");
if($found = mysqli_fetch_array($select)){
	$email = $found['captainEmail'];
	ini_set('SMTP' , 'TPCENTER-PC');
	$subject = "Online Auction System Accept Account";
	$body = "Your Delivery Captain Account in Online Auction System is accepted \n Now you can access your account";
	$email_from = "tuttut1443@gmail.com";
	$headers = "From: " . $email_from;
	$mail_sent = @mail($email, $subject , $body , $headers);
}

$query_search = "update `delivery_captain` set `accountStatus` = '$state' where `captainID` = '$user'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Selected Delivery Captain accepted Successfully";
echo json_encode($response);
?>