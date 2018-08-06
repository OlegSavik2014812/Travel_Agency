<#import "/spring.ftl"as spring>
<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
    <h5 class="my-0 mr-md-auto font-weight-normal">
        <a href="/"><@spring.message 'tour_agency'/></a>
    </h5>
    <h3>
    <#if user??>
        <@spring.message 'hello'/>, ${user.login}
    </#if>
    </h3>
    <nav class="my-2 my-md-0 mr-md-3">
        <a class="p-2 text-dark" href="?lang=en"><@spring.message 'localization.english'/></a>
        <a class="p-2 text-dark" href="?lang=ru"><@spring.message 'localization.russian'/></a>
        <#if user??&&user.role.name()=="MEMBER">
        <a class="p-2 text-dark" href="/user_tracks">favorites(${user.tourList?size})</a>
        </#if>
    </nav>

          <#if !user??>
             <a class="btn btn-outline-primary" href="/go_to_sign_in">
                 <@spring.message 'sign_in'/>
             </a>
             <a class="btn btn-outline-primary" href="/go_to_sign_up">
                 <@spring.message 'sign_up'/>
             </a>
          </#if>
    <#if user??>

        <a href="/sign_out"><@spring.message 'sign_out'/></a>
    </#if>
</div>