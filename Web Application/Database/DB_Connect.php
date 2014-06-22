<?php

/**
 * אחראי על חיבור למסד
 */
class DB_Connect extends mysqli{
    //**פעולות חיבור למסד**/
    public function __construct() {
        $this->connect();
    } 

    public function connect() {
        require_once '../config.php';
        parent::connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DBNAME);
    }
}

?>
