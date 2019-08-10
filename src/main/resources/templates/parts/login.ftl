<#macro login path isRegisterForm>
    <form class="mt-3" action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User mame:</label>
            <div class="col-sm-3">
                <input class="form-control" type="text" name="username" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-3">
                <input class="form-control" type="password" name="password" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Email:</label>
                <div class="col-sm-3">
                    <input class="form-control" type="email" name="email" placeholder="Sample@email.some"/>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">
            <#if isRegisterForm>Reg
            <#else>Sign in
            </#if>
        </button>
        <#if !isRegisterForm>
            <a href="/registration">Or reg...</a>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">Sign out</button>
    </form>
</#macro>