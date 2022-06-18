<?php
include("connect.php");
$qty = 0;
$cat = $_GET['cat'];
//$cat = "1";
if($cat == "1"){
	$type = "Laptops";
	$sql = "select * from `product` where `productQuantity` > '$qty' and  `productType` = '$type' ";
}else if($cat == "2"){
	$type = "Mobiles";
	$sql = "select * from `product` where `productQuantity` > '$qty' and  `productType` = '$type' ";
}else if($cat == "3"){
	$type = "Antiques";
	$sql = "select * from `product` where `productQuantity` > '$qty' and  `productType` = '$type' ";
}else if($cat == "4"){
	$type = "Jewelry";
	$sql = "select * from `product` where `productQuantity` > '$qty' and  `productType` = '$type' ";
}
	
	$res = mysqli_query($connect,$sql);
	$result = array();	
	while($row = mysqli_fetch_array($res)){
    $image_url = "https://tuttut1443.000webhostapp.com/" . $row['image1']; 
	array_push($result,array('url'=>$image_url, 'prodName'=>$row['productTitle'], 'prodPrice'=>$row['productPrice'], 'prodID'=>$row['productID'], 'prodModel'=>$row['productModel'], 'prodStatus'=>$row['productStatus'], 'prodImg2'=>$row['image2'], 'prodImg3'=>$row['image3'], 'prodImg4'=>$row['image4'], 'prodQty'=>$row['productQuantity']));
	}
	echo json_encode(array("result"=>$result));
	mysqli_close($connect);
?>
