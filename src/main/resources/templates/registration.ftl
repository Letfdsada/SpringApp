<#import "parts/common.ftl" as common>
<#import  "parts/login.ftl" as loginPage>

<@common.page>
<div class="mb-1">
        <h1>Registration</h1>
</div>
        ${message!}
<@loginPage.login "/registration" true/>
</@common.page>