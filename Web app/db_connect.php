<?php
 
class DB_Connect {
 
    function __construct() {
 
    }
 

    function __destruct() {
     
    }
 
    // Connecting to database
    public function connect() {
        require_once 'config.php';
        $con = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
     
        mysql_select_db(DB_DATABASE);
 
        // return database handler
        return $con;
    }
 
    // Closing database connection
    public function close() {
        mysql_close();
    }
 
} 
?>