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
        <h1 class="display-4">Favorites</h1>
        <#if userRole??&&userRole=="ADMIN"&& userList??>
        <p class="lead"><@spring.message 'userlist_quantity'/>: ${userList?size}</p>
        </#if>
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
                        <@spring.message 'country'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'hotel'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'date'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'duration'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'description'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'cost'/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list userTours as tour>
                <tr>
                    <td>
                        ${tour.country.name}
                    </td>
                    <td>
                        ${tour.hotel.name}
                    </td>
                    <td>
                        ${tour.date}
                    </td>
                    <td>
                        ${tour.duration}
                    </td>
                    <td>
                        ${tour.description}
                    </td>
                    <td>
                        ${tour.cost}
                    </td>
                    <td>
                        <#if user.role.name()=="MEMBER">
                            <a href="/remove_tour${userTours?seq_index_of(tour)}">&#10006;</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <a href="/"><@spring.message 'home'/></a>
</div>
</body>
</html>