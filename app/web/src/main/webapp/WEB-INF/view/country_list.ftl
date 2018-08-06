<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'countrylist_name'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<#include "navigation/header.ftl">
<div class="row">
<#include "navigation/left_menu.ftl">
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
        <h1 class="display-4">Countries</h1>
        <p class="lead"><@spring.message 'countrylist_quantity'/>: ${countryList?size}</p>
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
                        <@spring.message 'countrylist_country'/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list countryList as country>
                <tr>
                    <td>
                        ${country.id}
                    </td>
                    <td>
                        ${country.name}
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