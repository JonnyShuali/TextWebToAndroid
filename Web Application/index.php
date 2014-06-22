<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" dir="rtl" lang="he">
        <title>SMS Test Website</title>
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
    $("form").submit(function(event){
        event.preventDefault();
   var formData = $("form").serializeArray();
var URL = $("form").attr("action");

$.post(URL,
	formData,
	function(data, textStatus, jqXHR)
	{
            alert(data);
	}).fail(function(jqXHR, textStatus, errorThrown) 
	{
            alert(data);
	}); 
});
});
</script>
    </head>
    <body dir="rtl">
        <h1>שלח אס אמ אס</h1>
        <form action="Gcm/send_message.php" method="POST" >
            <label for="id">אימייל: </label><input type="email" id="email" name="email" required="required"/>
            <br>
            <label for="id">סיסמא: </label><input type="password" id="password" name="password" required="required"/>
            <br>
            <label for="phone_number"> טלפון למשלוח: </label> <input id="phone_number" name="phone_number" type="tel" required="required" pattern="^05[0|2|3|4|5|7|8]\d{7}$"/>
            <br>
            <label for="msg">תוכן ההודעה: </label> <textarea id="msg" name="msg" maxlength="160" required="required" ></textarea>
            <br>
            <input type="submit">
        </form>
    </body>
</html>
