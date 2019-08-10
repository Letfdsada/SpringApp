<#import "parts/common.ftl" as common>
<@common.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/messages" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Tag">
                <button type="submit" class="btn btn-primary ml-2">Search!</button>
            </form>
        </div>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>

    <!--Форма для отправки сообщения на сервер и добавления его в бд-->
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-2">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input class="form-control" type="text" name="text" placeholder="Message">
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="tag" placeholder="Tag">
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <div class="form-group">
                    <button class="btn btn-primary" type="submit">Send!</button>
                </div>
            </form>
        </div>
    </div>
    <!--Форма для фильтрации сообщений по Tag-->
    <!--Вывод сообщений по Key=="messages"-->
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-2">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No messages
        </#list>
    </div>
</@common.page>