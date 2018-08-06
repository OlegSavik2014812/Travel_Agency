<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'sign_up'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<#include "../navigation/header.ftl">
<div class="container">
    <form class="form-signin" name="form" action="/sign_up" method="post">
        <h2 class="form-signin-heading">
                <@spring.message 'sign_up'/>
        </h2>
        <label for="inputLogin" class="sr-only"><@spring.message 'login'/></label>
        <input type="text" id="inputLogin" name="login" class="form-control"
               placeholder="<@spring.message 'enter_login'/>" required=""
               autofocus="">
        <label for="inputPassword" class="sr-only"><@spring.message 'password'/></label>
        <input type="password" id="inputPassword" name="password" class="form-control"
               placeholder="<@spring.message 'enter_password'/>"
               required="">
        <div class="form-group">
            <label for="confirmPassword" class="sr-only">
                <@spring.message 'confirm_password'/>
            </label>
            <input type="password" name="confirmPassword" id="confirmPassword" class="form-control"
                   placeholder="<@spring.message 'confirm_password'/>" required=""/>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me" name="rememberMe"> <@spring.message 'remember'/>
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><@spring.message 'sign_up'/></button>
    </form>
</div>
</body>
</html>