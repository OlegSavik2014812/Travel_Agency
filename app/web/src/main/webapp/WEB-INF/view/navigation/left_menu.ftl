<#import "/spring.ftl"as spring>
<#if user??&&user.role.name()=="ADMIN">
<nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
    <ul class="nav nav-pills flex-column">
        <li class="nav-item">
            <a class="nav-link" href="/get_users">
                <@spring.message 'userlist_name'/>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link" href="/get_countries">
                <@spring.message 'countrylist_name'/>
            </a>
        </li>
    </ul>
    <ul class="nav nav-pills flex-column">
        <li class="nav-item">
            <a class="nav-link" href="/get_reviews">
                <@spring.message 'reviewlist_name'/>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/get_hotels">
                <@spring.message 'hotellist_name'/>
            </a>
        </li>
    </ul>
</nav>
</#if>