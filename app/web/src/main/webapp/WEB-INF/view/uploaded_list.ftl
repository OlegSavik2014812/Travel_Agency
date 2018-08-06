<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'upload_tour'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<#include "navigation/header.ftl">
<div class="container">
    <form class="needs-validation" enctype="multipart/form-data" novalidate="" action="/upload_tour_list"
          method="post">
        <input type="file" name="file" id="file">
        <button class="btn btn-primary btn-lg btn-block" type="submit"><@spring.message 'upload'/></button>
    </form>


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
                <#list tourList as tour>
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