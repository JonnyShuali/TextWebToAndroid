CREATE DATABASE sms;

CREATE TABLE `sms`.`users` (`id` INT(11) NULL AUTO_INCREMENT PRIMARY KEY , `gcm_regid` TEXT NULL , `name` VARCHAR(50) NULL , `pass` TEXT NULL, `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP) ENGINE = MYISAM ;

 );