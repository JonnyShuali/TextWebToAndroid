<?php

include_once '../Database/DB_functions.php';
include_once '../config.php';

if (isset($_POST["email"]) && isset($_POST["regId"]) && isset($_POST["salt"]) &&isset($_POST["password"]) )
    {
    // Var declaration 
    $email=$_POST["email"];
    $regId=$_POST["regId"];
    $salt=$_POST["salt"];
    $pass=$_POST["password"];

    // Salt checking
    if(SALT!=$salt)
        die("salt error");

    // Init DB function
    $db=new DB_functions();

    // Delete previousy used user with the same email
    $db->DeleteUser($email);

    // Adding the new user
    $db->StoreNewUser($email, $regId,$pass);
    }
    else {
        die('error');
    }
    ?>