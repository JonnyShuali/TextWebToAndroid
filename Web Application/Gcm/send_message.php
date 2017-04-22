<?php
include_once '../Database/DB_functions.php';
include_once '../Gcm/gcm.php';
require_once '../config.php';

if (isset($_POST["phone_number"]) && isset($_POST["email"]) && isset($_POST["msg"])&& isset($_POST["password"])) {
    $email = $_POST["email"];
    $pass=$_POST["password"];
    $phone_number = $_POST["phone_number"];
    $msg = $_POST["msg"];
    
    // Checking phone number length
    if (strlen($phone_number) != 10)
    {
        echo'יש שגיאה בטלפון, כמה ספרות הקלדת?';
    die();
    }
    // Checking prefix
    if(substr($phone_number, 0, 2)!="05")
    {
        echo "בעיה בקידומת? ".$phone_number."  ".substr($phone_number, 0, 2);
        die();
    }
    // Checking password
    $db=new DB_functions();
    if(($regId=$db->GetUser($email, md5($pass.SALT)))===false)
    {
        echo "שגיאה באימייל או בסיסמא";
        die();
    }
    // Checking msg len
    if(strlen($msg)>160)
    {
        echo "הודעה ארוכה מידי";
        die();
    }
    $gcm=new gcm();
    $gcm->send2phone($regId, array("num"=>$phone_number,"msg"=>$msg));
    echo "נשלח בהצלחה";
} else {
    echo "שגיאה. האם מילאת את כל הפרטים?";
}
?>
