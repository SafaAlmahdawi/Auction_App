<?php
include("connect.php");

$prodID = $_POST['prodID'];
$image = $_POST['image'];

	 $id = "prod$prodID-1.png"; 
	 $path = "products/$id";
	 $imagePath = "https://tuttut1443.000webhostapp.com/" . $path;

	 $update = mysqli_query($connect,"update `product` set `image1` = '$path' where `productID` = '$prodID'");
	 file_put_contents($path, base64_decode($image));
	
	 $response["success"] = 1;
	 $response["message"] = "Image is added successfully";
	 echo json_encode($response);

?>