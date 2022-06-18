<?php
include("connect.php");
///// read the data that given from Android Application 
$user = $_POST['userID'];
$state = "Accepted";

///// select the expert email and send the accepted notification to the expert ////
$select = mysqli_query($connect, "select * from `expert` where `expertID` = '$user'");
if($found = mysqli_fetch_array($select)){
	$email = $found['expertEmail'];
	ini_set('SMTP' , 'TPCENTER-PC');
	$subject = "Online Auction System Accept Account";
	$body = "Your Expert Account in Online Auction System is accepted \n Now you can access your account";
	$email_from = "tuttut1443@gmail.com";
	$headers = "From: " . $email_from;
	$mail_sent = @mail($email, $subject , $body , $headers);
}

$query_search = "update `expert` set `accountStatus` = '$state' where `expertID` = '$user'";
$query_exec = mysqli_query($connect,$query_search);
 $response["success"] = 1;
 $response["message"] = "Selected Expert accepted Successfully";
echo json_encode($response);
?>