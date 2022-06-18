<?php
include("connect.php");
//// read post data from Android ///////////////
$email = $_POST['email'];
$resetPass = $_POST['code'];
$type = $_POST['type'];

if($type == "Buyer"){
	$query_search = "select * from `buyer` where `buyerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		ini_set('SMTP' , 'TPCENTER-PC');
		$subject = "Online Auction System Reset Password";
		$body = " Your Reset Passsword: " . $resetPass;
		$email_from = "tuttut1443@gmail.com";
		$headers = "From: " . $email_from;

		$mail_sent = @mail($email, $subject , $body , $headers);
	    $response["success"] = 1;
		$response["message"] = "Reset Password is sent Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No email found";
		echo json_encode($response);
	}
} else if($type == "Seller"){
	$query_search = "select * from `seller` where `sellerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		ini_set('SMTP' , 'TPCENTER-PC');
		$subject = "Online Auction System Reset Password";
		$body = " Your Reset Passsword: " . $resetPass;
		$email_from = "tuttut1443@gmail.com";
		$headers = "From: " . $email_from;

		$mail_sent = @mail($email, $subject , $body , $headers);
	    $response["success"] = 1;
		$response["message"] = "Reset Password is sent Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No email found";
		echo json_encode($response);
	}
} else if($type == "Expert"){
	$query_search = "select * from `expert` where `expertEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		ini_set('SMTP' , 'TPCENTER-PC');
		$subject = "Online Auction System Reset Password";
		$body = " Your Reset Passsword: " . $resetPass;
		$email_from = "tuttut1443@gmail.com";
		$headers = "From: " . $email_from;

		$mail_sent = @mail($email, $subject , $body , $headers);
	    $response["success"] = 1;
		$response["message"] = "Reset Password is sent Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No email found";
		echo json_encode($response);
	}
} else if($type == "Delivery Captain"){
	$query_search = "select * from `delivery_captain` where `captainEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		ini_set('SMTP' , 'TPCENTER-PC');
		$subject = "Online Auction System Reset Password";
		$body = " Your Reset Passsword: " . $resetPass;
		$email_from = "tuttut1443@gmail.com";
		$headers = "From: " . $email_from;

		$mail_sent = @mail($email, $subject , $body , $headers);
	    $response["success"] = 1;
		$response["message"] = "Reset Password is sent Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No email found";
		echo json_encode($response);
	}
} else if($type == "Manager"){
	$query_search = "select * from `administrator` where `adminEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		ini_set('SMTP' , 'TPCENTER-PC');
		$subject = "Online Auction System Reset Password";
		$body = " Your Reset Passsword: " . $resetPass;
		$email_from = "tuttut1443@gmail.com";
		$headers = "From: " . $email_from;

		$mail_sent = @mail($email, $subject , $body , $headers);
	    $response["success"] = 1;
		$response["message"] = "Reset Password is sent Successfully";
		echo json_encode($response);
	}else{
		$response["success"] = 0;
		$response["message"] = "No email found";
		echo json_encode($response);
	}
}

?>