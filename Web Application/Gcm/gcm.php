<?php

class gcm {

    public function __construct() {
        ;
    }
/**
 * הפעולה שאחראית את שליחת המידע למכשיר מהשרת
 * @param type $registerId מזהה עבור גוגל
 * @param type $msg ההודעה שתשלח למכשיר
 */
    function send2phone($registerId, $msg) {
        include_once '../config.php';
        $fields = array( //יכלול את המידע שצריך לשלוח בpost
        'registration_ids' => array($registerId),
        'data' => $msg,
        );
        $headers = array( //יכלול את הכותרת והאימות שנדרשים על פי גוגל
            'Authorization: key=' . GOOGLE_API_KEY,
            'Content-Type: application/json'
        );
        //פקודות שליחה לשרת של גוגל באמצעות CURL
        $ch=  curl_init();
        //הגדרות
        curl_setopt($ch, CURLOPT_URL, GOOGLE_API_SERVER);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        //שליחת הבקשה
        $result=curl_exec($ch);
        if($result==false)
            die("send failed");
        curl_close($ch);
    }

}

?>
