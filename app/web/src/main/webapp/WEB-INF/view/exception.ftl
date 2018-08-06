<#import "/spring.ftl"as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><@spring.message 'exception_message'/></title>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/4.1.1/css/bootstrap.min.css">
</head>
<body>
<div>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template">
                    <h1>
                        <@spring.message 'exception_message'/>
                    </h1>
                ${exception}
                    <a href="/"><@spring.message 'home'/></a>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>