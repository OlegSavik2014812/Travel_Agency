<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'userlist_name'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<#include "navigation/header.ftl">
<div class="row">
<#include "navigation/left_menu.ftl">
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <h1 class="display-4">Users</h1>
        <p class="lead"><@spring.message 'userlist_quantity'/>: ${userList?size}</p>
    </div>
<#include "navigation/right_menu.ftl">
</div>
<div class="container">
    <div class="row mt-4">
        <div class="col-12">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">
                        №
                    </th>
                    <th scope="col">
                        <@spring.message 'userlist_login'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'userlist_role'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'userlist_remove'/>
                    </th>

                </tr>
                </thead>
                <tbody>
                <#list userList as user>
                <tr>
                    <td>
                        ${user.id}
                    </td>
                    <td>
                        ${user.login}
                    </td>
                    <td>
                        ${user.role.name()}
                    </td>
                    <td>
                        <#if user.role.name()!="ADMIN">
                            <a href="/remove_user${user.id}">&#10006;</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>