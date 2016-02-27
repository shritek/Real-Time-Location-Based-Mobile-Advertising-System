<?php
session_start();
function goto_page($url)
{
	header ("Location: $url");
	exit;
}

$username="root";
$password="";
$database="lba";
$s="localhost";

$username1=$_POST['user'];
$password1=$_POST['pwd'];

$c=mysql_connect("$s","$username","$password");
@mysql_select_db($database,$c) or die(mysql_error());
if(strlen($username1)>0)
{
	$quer="SELECT * FROM login_data where user_id like '$username1'";
	$result=mysql_query($quer) or die(mysql_error());
	$num_rows=mysql_num_rows($result);
	$row=mysql_fetch_array($result);
	
	$_SESSION['usr'] = $username1;
	if($num_rows==1)
	{
		if($row['password']==$password1)
		{
			
			goto_page("det1.php");
		}
	}
}

	goto_page("index.html");


?>

