<?php
	session_start();

	$myarray=array();
        include_once 'db_functions.php';
        $db = new DB_Functions();
        $users = $db->getAllUsers();
 
        $shop = $db->getLocation();

        if ($users != false)
            $no_of_users = mysql_num_rows($users);
        else
            $no_of_users = 0;
	
	$count=0;
	if ($no_of_users > 0) 
	{
		while ($row = mysql_fetch_array($users)) 
		{
			$myarray[]=$row["gcm_regid"];
		}
	}
	$row=mysql_fetch_array($shop);
	$lat=$row['lat'];
	$lon=$row['lon'];
	$addr=$row['address'];

if (isset($_GET["regId"]) &&isset($_GET["message"])) 
{
	if(isset($_GET["ddl"]))
		$radius=$_GET["ddl"];

	else
		$radius="1";

	$message = $_GET["message"];
	$regId = $_GET["regId"];


	include_once './GCM.php';
 
	$message="\n\n".$message."\nAddress: ".$addr;
	$gcm = new GCM();

	$registatoin_ids = array($regId);
	$col="lba";
	$message = array("price" => $message, "lati" => $lat, "longi" => $lon, "r"=> $radius);
	$result = $gcm->send_notification($myarray, $message);

	echo $result;
}
?>
