<?php

include_once '../Database/DB_functions.php';
include_once '../config.php';

if (isset($_POST["email"]) && isset($_POST["regId"]) && isset($_POST["salt"]) &&isset($_POST["password"]) )
    {
    //השמת ערכי שנשלחו במשתנים
    $email=$_POST["email"];
    $regId=$_POST["regId"];
    $salt=$_POST["salt"];
    $pass=$_POST["password"];
    //בדיקת salt
    if(SALT!=$salt)
        die("salt error");
    //מאתחל
    $db=new DB_functions();
    //מוחק משתמשים קודמים עם אותו אימייל אם יש
    $db->DeleteUser($email);
    //מכניס פרטים למסד
    $db->StoreNewUser($email, $regId,$pass);
    }
    else {
        echo 'error';    
    }
    ?>