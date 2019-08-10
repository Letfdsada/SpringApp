<#import "parts/common.ftl" as common>

<@common.page>
    User editor
    <form method="post", action="/user">
        <input type="text" value="${user.username}" name="username">
        <#list roles as role>
            <div>
                <label>
                    <#--Проверка, на наличие роли в списке-->
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                    ${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>


</@common.page>