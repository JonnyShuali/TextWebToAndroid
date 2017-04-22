<?php

/**
 * DB connection class
  */
class DB_Connect extends mysqli{
    public function __construct() {
        $this->connect(); // Connecting here
    } 

    public function connect() {
        require_once '../config.php';
        parent::connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DBNAME);
    }
}

?>
