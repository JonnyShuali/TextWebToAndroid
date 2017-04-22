<?php

class gcm {

    public function __construct() {
        ;
    }
/**
 * Sends notification from server
 * @param type $registerId Google ID
 * @param type $msg the msg
 */
    function send2phone($registerId, $msg) {
        include_once '../config.php';
        $fields = array( // post the required info
        'registration_ids' => array($registerId),
        'data' => $msg,
        );
        $headers = array( // Google Auth for GCM connection
            'Authorization: key=' . GOOGLE_API_KEY,
            'Content-Type: application/json'
        );
        // Using CURL for sending POST
        $ch=  curl_init();
        // CURL settings
        curl_setopt($ch, CURLOPT_URL, GOOGLE_API_SERVER);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        
        // Sending the request, die on error
        $result=curl_exec($ch);
        if($result==false)
            die("send failed");
        curl_close($ch);
    }

}

?>
