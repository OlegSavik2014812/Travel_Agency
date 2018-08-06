<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><@spring.message 'tour_agency'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>

<#include "navigation/header.ftl">
<div class="container-fluid">
    <div class="row">
    <#include "navigation/left_menu.ftl">
        <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
            <h1 class="display-4"><@spring.message 'tour_agency'/></h1>
        <#if user??&&user.role.name()=="ADMIN">
            <p class="lead"><@spring.message 'tourlist_quantity'/>: ${tourList?size}</p>
        </#if>
        </div>
    <#include "navigation/right_menu.ftl">
    </div>
    <div class="container">
        <form class="needs-validation" novalidate="" action="/search">
            <div class="mb-3">
                <label for="countryName"><@spring.message 'country'/></label>
                <input type="text" class="form-control" id="countryName" name="countryName"
                       placeholder=<@spring.message 'enter_country'/> value=""
                       required="">
            </div>
            <div class="mb-3">
                <label for="hotelName"><@spring.message 'hotel'/></label>
                <input type="text" class="form-control" id="hotelName" name="hotelName"
                       placeholder=<@spring.message 'hotel'/>
                               value=""
                       required="">
            </div>
            <div class="row">
                <div class="col-md-5 mb-3">
                    <label for="hotelStars"><@spring.message 'hotel_stars'/></label>
                    <select class="custom-select d-block w-100" id="hotelStars" name="numberOfStars" required="">
                        <option value=""></option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                </div>
                <div class="col-md-5 mb-3">
                    <label for="lowerLimit"><@spring.message 'date'/></label>
                    <input type="date" class="form-control" id="lowerLimit" placeholder="" value="" name="date"
                           required="">
                </div>
            </div>
            <hr class="mb-4">
            <button class="btn btn-primary btn-lg btn-block" type="submit"><@spring.message 'look_for'/></button>
        </form>

        <div class="card-deck mb-3 text-center">
            <div class="row">
            <#if tourList??>
                <#list tourList as tour>
        <div class="card mb-4 box-shadow">
            <div class="card-header">
                <h4 class="my-0 font-weight-normal">${tour.type}</h4>
            </div>
            <div class="card-body">
                <h1 class="card-title pricing-card-title">${tour.cost}$
                </h1>
                <ul class="list-unstyled mt-3 mb-4">
                    <li>Country: ${tour.country.name}</li>
                    <li>Hotel: ${tour.hotel.name} </li>
                    <li>
                        <#list 1..tour.hotel.numberOfStars as list>
                            <p style="display: inline">&#9733;</p>
                        </#list>
                    </li>
                    <li>Duration: ${tour.duration} <@spring.message 'days'/></li>
                    <li>Date ${tour.date}</li>
                </ul>
                <a href="/order_tour${tour.id}"
                   class="btn btn-lg btn-block btn-outline-primary"><@spring.message 'register'/></a>
            </div>
        </div>
                </#list>
            </#if>
            </div>
        </div>
    </div>
</div>
</body>
</html>