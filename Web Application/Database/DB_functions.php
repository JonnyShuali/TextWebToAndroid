<?php

/**
 * DB function
 **/
require 'DB_Connect.php';
class DB_functions {

    private $db;

    public function __construct() {
        
        $this->db = new DB_Connect;
    }

/**
 * Insert new user to db
 * Returns true on sucsess or false on error
 */
    public function StoreNewUser($email, $reg_id,$pass) {
        $stmt = $this->db->prepare("INSERT INTO users(name, gcm_regid, pass) VALUES (? , ?, ?)");
        $stmt->bind_param("sss", $email, $reg_id, $pass);
        $stmt->execute();
        $stmt->close();
        if($this->GetUser($email,$pass)===false)
            return false;
        return true;
    }

    /**
     * Lookup the user in db by name
     * Return user datails or sucsess or false on exception
     */
    public function GetUser($name,$pass) {
        $stmt = $this->db->prepare("SELECT gcm_regid FROM users WHERE name=? AND pass=?");
        $stmt -> bind_param("ss", $name, $pass);
        $stmt->execute();
         $stmt -> bind_result($result);
        $stmt -> fetch();
        $stmt->close();
        if(isset($result))
            return $result;
        return false;
    }

        /**
         * Deletes user from the db
         */ 
    public function DeleteUser($name) {
        $stmt = $this->db->prepare("DELETE FROM users WHERE name=?");
        $stmt -> bind_param("s", $name);
        $stmt->execute();
        $stmt->close();
        return true;
    }
}

?>
