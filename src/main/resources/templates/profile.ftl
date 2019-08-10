<#import "parts/common.ftl" as common>
<@common.page>
    <h4>${username}</h4>
    ${message!}
    <form class="mt-3" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-3">
                <input class="form-control" type="password" name="password" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-3">
                <input class="form-control" type="email" name="email" placeholder="Sample@email.some" value="${email!""}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="btn btn-primary" type="submit">
            Save
        </button>
    </form>
</@common.page>