<?php
include("connect.php");
///// update auctions status /////////////
$state = "wait";
$new_state = "close";
$date = date('y-m-d');
$result = mysqli_query($connect,"SELECT *  FROM `bidding` Where `bidStatus` = '$state'");
while($record = mysqli_fetch_array($result)){
	$end_date = strtotime($record["bidEnd"]);
	$today = strtotime($date);
	if($end_date < $today){
		$bidID = $record['bidID'];
		$update_state = mysqli_query($connect, "update `bidding` set `bidStatus` = '$new_state' where `bidID` = '$bidID'");
	}
}

$username = $_POST['user_name'];
$pass = $_POST['pass'];

//// Check in buyer table 
$query_search = mysqli_query($connect, "select * from `buyer` where (`userName` = '$username' or `buyerEmail` = '$username')  AND `userPass` = '$pass'");
$rows = mysqli_num_rows($query_search);
//when buyer found $rows will not equal zero
 if($rows != 0) {
	 $found = mysqli_fetch_array($query_search);
	 $response["success"] = 1;
     $msg = $found['buyerID'] . "##" . $found['buyerName'] . "##" . $found['buyerEmail'] . "##" . $found['buyerPhone'] . "##" . $found['buyerAddress'] . "##" . $found['userName'] . "##" . $found['userPass'] . "##" . $found['payment_details'] ;   
	 $response["message"] = $msg;
	 echo json_encode($response);
 }else{
            //// check in seller table  
            $query_search = mysqli_query($connect, "select * from `seller` where (`userName` = '$username' or `sellerEmail` = '$username')  AND `userPass` = '$pass'");
            $rows = mysqli_num_rows($query_search);
            if($rows != 0) {
                $found = mysqli_fetch_array($query_search);
                $msg = $found['sellerID'] . "##" . $found['sellerName'] . "##" . $found['sellerEmail'] . "##" . $found['sellerPhone'] . "##" . $found['sellerAddress'] . "##" . $found['userName'] . "##" . $found['userPass'];   
	            $response["message"] = $msg;
                $response["success"] = 2;
                echo json_encode($response);
            }else{
                    //// check in expert table  
                    $query_search = mysqli_query($connect, "select * from `expert` where (`userName` = '$username' or `expertEmail` = '$username')  AND `userPass` = '$pass'");
                    $rows = mysqli_num_rows($query_search);
                    if($rows != 0) {
                        $found = mysqli_fetch_array($query_search);
                        $msg = $found['expertID'] . "##" . $found['expertName'] . "##" . $found['expertEmail'] . "##" . $found['expertPhone'] . "##" . $found['userName'] . "##" . $found['userPass'] . "##" . $found['experience1'] . "##" . $found['experience2'] . "##" . $found['experience3']  . "##" . $found['accountStatus'];   
	                    $response["message"] = $msg;
                        $response["success"] = 3;
                        echo json_encode($response);
                    }else{
                            //// check in delivery captain table  
                            $query_search = mysqli_query($connect, "select * from `delivery_captain` where (`userName` = '$username' or `captainEmail` = '$username')  AND `userPass` = '$pass'");
                            $rows = mysqli_num_rows($query_search);
                            if($rows != 0) {
                                $found = mysqli_fetch_array($query_search);
                                $msg = $found['captainID'] . "##" . $found['captainName'] . "##" . $found['captainEmail'] . "##" . $found['captainPhone'] . "##" . $found['userName'] . "##" . $found['userPass'] . "##" . $found['carModel'] . "##" . $found['carYear'] . "##" . $found['captainCity'] . "##" . $found['work_area']  . "##" . $found['accountStatus'];   
	                            $response["message"] = $msg;
                                $response["success"] = 4;
                                echo json_encode($response);
                        }else{
                                //// check in Admin table  
                                $query_search = mysqli_query($connect,"select * from `administrator` where (`userName` = '$username' or `adminEmail` = '$username')  AND `userPass` = '$pass'");
                                $rows = mysqli_num_rows($query_search);
                                if($rows != 0) {
                                    $found = mysqli_fetch_array($query_search);
                                    $msg = $found['adminID'] . "##" . $found['adminName'] . "##" . $found['adminEmail'] . "##" . $found['userName'] . "##" . $found['userPass'];
                                    $response["message"] = $msg;
                                    $response["success"] = 5;
                                    echo json_encode($response);
                                }else{
                                    $response["success"] = 0;
                                    $response["message"] = "No user found";
                                    echo json_encode($response);
                                }
                            }
	                    }
                }
        }
		
?>