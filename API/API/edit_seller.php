<?php
include("connect.php");
$id = $_POST['id'];
$name = $_POST['name'];
$email = $_POST['email'];
$user = $_POST['userName'];
$pass = $_POST['userPass'];
$phone = $_POST['phone'];
$address = $_POST['address'];

//// check if the username or email is repeated in buyer table 
$query_search = "select * from `buyer` where `userName` = '$user' OR `buyerEmail` = '$email'";
$query_exec = mysqli_query($connect,$query_search);
$rows = mysqli_num_rows($query_exec);
//echo $rows;
if($rows != 0){
	$response["success"] = 0;
	$response["message"] = "Username or Email already used";
	echo json_encode($response);
}else{
	///// check if there a seller with the same username or email
	$query_search = "select * from `seller` where  (`userName` = '$user' OR `sellerEmail` = '$email') and `sellerID` != '$id'";
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
                                    /// add new user data
                                        $update = mysqli_query($connect,"update `seller` set `sellerName` = '$name', `sellerEmail` = '$email', `userName` = '$user', `userPass` = '$pass',
                                        `sellerPhone` = '$phone', `sellerAddress` = '$address' Where `sellerID` = '$id' ");
                                        $response["message"] = "Seller profile updated successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);                                    
                                }
	                    }               
                }
            }
        }
?>