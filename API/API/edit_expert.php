<?php
include("connect.php");
$id = $_POST['id'];
$name = $_POST['name'];
$email = $_POST['email'];
$pass = $_POST['userPass'];
$phone = $_POST['phone'];
$exper1 = $_POST['exper1'];
$exper2 = $_POST['exper2'];
$exper3 = $_POST['exper3'];

//// check if the username or email is repeated in buyer table 
$query_search = "select * from `buyer` where `buyerEmail` = '$email'";
$query_exec = mysqli_query($connect,$query_search);
$rows = mysqli_num_rows($query_exec);
//echo $rows;
if($rows != 0){
	$response["success"] = 0;
	$response["message"] = "Email already used";
	echo json_encode($response);
}else{
	///// check if there a seller with the same username or email
	$query_search = "select * from `seller` where  `sellerEmail` = '$email'";
	$query_exec = mysqli_query($connect,$query_search);
	$rows = mysqli_num_rows($query_exec);
	//echo $rows;
	if($rows != 0){
		$response["success"] = 0;
		$response["message"] = "Email already used";
		echo json_encode($response);
	}else{
            ///// check if there is an expert with the same username or email
            $query_search = "select * from `expert` where  `expertEmail` = '$email' and `expertID` != '$id'";
            $query_exec = mysqli_query($connect,$query_search);
            $rows = mysqli_num_rows($query_exec);
            //echo $rows;
            if($rows != 0){
                $response["success"] = 0;
                $response["message"] = "Email already used";
                echo json_encode($response);
            }else{
                    ///// check if there is an delivery_captain with the same username or email
                    $query_search = "select * from `delivery_captain` where `captainEmail` = '$email'";
                    $query_exec = mysqli_query($connect,$query_search);
                    $rows = mysqli_num_rows($query_exec);
                    //echo $rows;
                    if($rows != 0){
                        $response["success"] = 0;
                        $response["message"] = "Email already used";
                        echo json_encode($response);
                    }else{
                            ///// check if there is an administrator with the same username or email
                            $query_search = "select * from `administrator` where `adminEmail` = '$email'";
                            $query_exec = mysqli_query($connect,$query_search);
                            $rows = mysqli_num_rows($query_exec);
                            //echo $rows;
                            if($rows != 0){
                                $response["success"] = 0;
                                $response["message"] = "Email already used";
                                echo json_encode($response);
                            }
                            else{
                                    /// add new user data
                                        $update = mysqli_query($connect,"update `expert` set `expertName` = '$name', `expertEmail` = '$email', `userPass` = '$pass',
                                        `expertPhone` = '$phone', `experience1` = '$exper1' , `experience2` = '$exper2' , `experience3` = '$exper3' Where `expertID` = '$id' ");
                                        $response["message"] = "Expert profile updated successfully";
                                        $response["success"] = 1;
                                        echo json_encode($response);                                    
                                }
	                    }               
                }
            }
        }
?>