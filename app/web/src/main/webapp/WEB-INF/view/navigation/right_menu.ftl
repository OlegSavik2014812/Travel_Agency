<#import "/spring.ftl"as spring>
<#if user??&&user.role.name()=="ADMIN">
<nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
    <ul class="nav nav-pills flex-column">
        <li class="nav-item">
            <a class="nav-link" href="/upload">
                <@spring.message 'upload_tour'/>
            </a>
        </li>
    </ul>
</nav>
</#if>