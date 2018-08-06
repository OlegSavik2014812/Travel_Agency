<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'sign_in'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body class="text-center">
<#include "../navigation/header.ftl">
<div class="container">
    <form class="form-signin" action="/sign_in" method="post">
        <h2 class="form-signin-heading"><@spring.message 'sign_in'/></h2>
        <label for="inputLogin" class="sr-only"><@spring.message 'login'/></label>
        <input type="text" id="inputLogin" name="login" class="form-control"
               placeholder="<@spring.message 'enter_login'/>"
               required autofocus>
        <label for="inputPassword" class="sr-only"><@spring.message 'password'/></label>
        <input type="password" id="inputPassword" name="password" class="form-control"
               placeholder="<@spring.message 'enter_password'/>"
               required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="true" name="remember-me"> <@spring.message 'remember'/>
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><@spring.message 'login'/></button>
    </form>
</div>
</body>
</html>