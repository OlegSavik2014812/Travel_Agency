<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'hotellist_name'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<#include "navigation/header.ftl">
<div class="row">
<#include "navigation/left_menu.ftl">
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <h1 class="display-4">Hotels</h1>
        <p class="lead"><@spring.message 'hotellist_quantity'/>: ${hotelList?size}</p>
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
                        <@spring.message 'hotellist_name'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'hotellist_stars'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'hotellist_phone'/>
                    </th>
                    <th scope="col">
                        <@spring.message 'hotellist_country'/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list hotelList as hotel>
                <tr>
                    <td>
                        ${hotel.id}
                    </td>
                    <td>
                        ${hotel.name}
                    </td>
                    <td>
                        <#list 1..hotel.numberOfStars as list>
                            <p style="display: inline">&#9733;</p>
                        </#list>
                    </td>
                    <td>
                        ${hotel.phoneNumber}
                    </td>
                    <td>
                        ${hotel.country.name}
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