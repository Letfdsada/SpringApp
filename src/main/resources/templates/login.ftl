<#import "parts/common.ftl" as common>
<#import "parts/login.ftl" as loginPage>
<@common.page>
    ${message!}
    <h1>Login</h1>
    <@loginPage.login "/login" false/>
</@common.page>