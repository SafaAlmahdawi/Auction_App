<?php
include("connect.php");
$qty = 0;
$s = $_GET['s'];
$sql = "select * from `product` where `productTitle` like '%$s%' OR `productModel` like '%$s%' OR `productStatus` like '%$s%'";
	$res = mysqli_query($connect,$sql);
	$result = array();	
	while($row = mysqli_fetch_array($res)){
		$image_url = "https://tuttut1443.000webhostapp.com/" . $row['image1']; 
		array_push($result,array('url'=>$image_url, 'prodName'=>$row['productTitle'], 'prodPrice'=>$row['productPrice'], 'prodID'=>$row['productID'], 'prodModel'=>$row['productModel'], 'prodStatus'=>$row['productStatus'], 'prodImg2'=>$row['image2'], 'prodImg3'=>$row['image3'], 'prodImg4'=>$row['image4'], 'prodQty'=>$row['productQuantity']));
	}
	echo json_encode(array("result"=>$result));
	mysqli_close($connect);
?>
