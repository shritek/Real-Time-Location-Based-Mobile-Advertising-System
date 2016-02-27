<?php
session_start();
class DB_Functions {

    private $db;

    function __construct() {
        include_once './db_connect.php';
   
        $this->db = new DB_Connect();
        $this->db->connect();
    }

 
    function __destruct() {
        
    }

    public function storeUser($name, $email, $gcm_regid) {
        // insert user into database
        $result = mysql_query("INSERT INTO gcm_users(name, email, gcm_regid, created_at) VALUES('$name', '$email', '$gcm_regid', NOW())");
        // check for successful store
        if ($result) {
           
            $id = mysql_insert_id(); // last inserted id
            $result = mysql_query("SELECT * FROM gcm_users WHERE id = $id") or die(mysql_error());
            // return user details
            if (mysql_num_rows($result) > 0) {
                return mysql_fetch_array($result);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

 
    // Getting all users
    
    public function getAllUsers() {
        $result = mysql_query("select * FROM gcm_users");
        return $result;
    }

public function getLocation() {
	$username1 = $_SESSION['usr'];
	$result = mysql_query("SELECT lat,lon,address FROM login_data WHERE user_id like '$username1'");
	return $result;
     }

}

?>