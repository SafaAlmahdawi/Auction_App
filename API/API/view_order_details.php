<?php
include("connect.php");

$orderID = $_POST['orderID'];
$buyerID = $_POST['buyerID'];
$prodID = $_POST['prodID'];
$orderType = $_POST['orderType'];
//$orderID = "11";
//$buyerID = "3";
//$prodID = "2";
//$orderType = "Bidding";

//// read the buyer data //////////////////
$select_buyer = mysqli_query($connect, "select * from `buyer` where `buyerID` = '$buyerID'");
while($record = mysqli_fetch_array($select_buyer)){
	$buyerName = $record['buyerName'];
	$buyerPhone = $record['buyerPhone'];
	$buyerAddress = $record['buyerAddress'];
}
///// read Seller data //////////
$select_seller = mysqli_query($connect, "select sellerName, sellerPhone, sellerAddress from `seller` s, `product` p where s.sellerID = p.sellerID and `productID` = '$prodID'");
while($record = mysqli_fetch_array($select_seller)){
	$sellerName = $record['sellerName'];
	$sellerPhone = $record['sellerPhone'];
	$sellerAddress = $record['sellerAddress'];
}
//// read Order details ////////////////////
if($orderType == "Purchase"){
	$result = mysqli_query($connect,"SELECT `productTitle`, `productPrice`, `paymentInfo` from `purchaseOrders` o, `product` p where p.productID = o.productID and `orderID` = '$orderID' ");
	while($record = mysqli_fetch_array($result))
	{
		$prodName = $record['productTitle'];
		$prodPrice = $record['productPrice'];
		$payment = $record['paymentInfo'];
	}
}else if($orderType == "Bidding"){
	$result = mysqli_query($connect,"SELECT `productTitle`, `lastPrice`, `paymentInfo` from `bidding` b, `product` p where p.productID = b.productID and `bidID` = '$orderID'");
	while($record = mysqli_fetch_array($result))
	{
		$prodName = $record['productTitle'];
		$prodPrice = $record['lastPrice'];
		$payment = $record['paymentInfo'];
	}
}
$response["success"] = 1;
$response["message"] = $buyerName . "##" . $buyerPhone . "##" . $buyerAddress . "##" . $sellerName . "##" . $sellerPhone . "##" . $sellerAddress . "##" . $prodName . "##" . $prodPrice . "##" . $payment ;
echo json_encode($response);
?>