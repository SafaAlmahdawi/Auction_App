<?php
include("connect.php");

$name = $_POST['name'];
$email = $_POST['email'];
$user = $_POST['userName'];
$pass = $_POST['userPass'];
$type = $_POST['userType'];


//// check if the username or email is repeated in buyer table 
$query_search = "select * from `buyer` where  `userName` = '$user' OR `buyerEmail` = '$email'";
$query_exec = mysqli_query($connect,$query_search);
$rows = mysqli_num_rows($query_exec);
//echo $rows;
if($rows != 0){
	$response["success"] = 0;
	$response["message"] = "Username or Email already used";
	echo json_encode($response);
}else{
	///// check if there a seller with the same username or email
	$query_search = "select * from `seller` where  `userName` = '$user' OR `sellerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		$response["success"] = 0;
		$response["message"] = "Username or Email already used";
		echo json_encode($response);
	}else{
            ///// check if there is an expert with the same username or email
            $query_search = "select * from `expert` where  `userName` = '$user' OR `expertEmail` = '$email'";
            $query_exec = mysqli_query($connect,$query_search);
            $rows = mysqli_num_rows($query_exec);
            //echo $rows;
            if($rows != 0){
                $response["success"] = 0;
                $response["message"] = "Username or Email already used";
                echo json_encode($response);
            }else{
                    ///// check if there is an delivery_captain with the same username or email
                    $query_search = "select * from `delivery_captain` where  `userName` = '$user' OR `captainEmail` = '$email'";
                    $query_exec = mysqli_query($connect,$query_search);
                    $rows = mysqli_num_rows($query_exec);
                    //echo $rows;
                    if($rows != 0){
                        $response["success"] = 0;
                        $response["message"] = "Username or Email already used";
                        echo json_encode($response);
                    }else{
                            ///// check if there is an administrator with the same username or email
                            $query_search = "select * from `administrator` where  `userName` = '$user' OR `adminEmail` = '$email'";
                            $query_exec = mysqli_query($connect,$query_search);
                            $rows = mysqli_num_rows($query_exec);
                            //echo $rows;
                            if($rows != 0){
                                $response["success"] = 0;
                                $response["message"] = "Username or Email already used";
                                echo json_encode($response);
                            }
                            else{
                                    /// add new user to the system
                                    if($type == "Buyer"){
                                        $insert = mysqli_query($connect,"insert into `buyer` (`buyerName`, `buyerEmail`, `userName`, `userPass`) 
                                        values ('$name' , '$email' , '$user', '$pass')");
                                        $response["message"] = "New Buyer added successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);
                                    }else if($type == "Seller"){
                                        $insert = mysqli_query($connect,"insert into `seller` (`sellerName`, `sellerEmail`, `userName`, `userPass`) 
                                        values ('$name' , '$email' , '$user', '$pass')");
                                        $response["message"] = "New Seller added successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);
                                    }else if($type == "Expert"){
                                        $insert = mysqli_query($connect,"insert into `expert` (`expertName`, `expertEmail`, `userName`, `userPass`) 
                                        values ('$name' , '$email' , '$user', '$pass')");
                                        $response["message"] = "New Expert Request added successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);
                                    }else if($type == "Delivery Captain"){
                                        $insert = mysqli_query($connect,"insert into `delivery_captain` (`captainName`, `captainEmail`, `userName`, `userPass`) 
                                        values ('$name' , '$email' , '$user', '$pass')");
                                        $response["message"] = "New Delivery Captain Request added successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);
                                    }
                                    
                                }
	                    }               
                }
            }
        }
?>