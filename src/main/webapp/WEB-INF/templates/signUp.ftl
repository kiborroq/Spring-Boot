<#import "/spring.ftl" as spring />
<html>
<head>
  <title><@spring.message "label.validation.signUp.registration"/></title>
</head>
<style>
    form {width: 400px; margin: auto}
    input[type=text], input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 0 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }
    .field {
        margin-bottom: 20px;
        height: 80px;
    }
    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }
    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }
    .registerbtn {
        background-color: #5237d5;
        color: white;
        padding: 16px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        width: 400px;
        opacity: 0.9;
    }
    .registerbtn:hover {
        opacity:1;
        cursor: pointer;
    }
    .error {
        margin: 2px 0 0;
        font-size: 10pt;
        color: #c41515;
        font-weight: 100;
    }
    .global-error {
        width: 100%;
        margin: 0 0 15px;
        font-size: 14pt;
        color: #c41515;
        text-align: center;
    }
    .langbtn {
      background-color: #ddd;
      color: white;
      padding: 16px 20px;
      margin: 8px 0;
      border: darkblue;
      cursor: pointer;
      width: 80px;
      opacity: 0.9;
    }
    .langbtn:hover {
      opacity:1;
      cursor: pointer;
      color: white;
      background-color: #5237d5;
    }
</style>
<body>
<form action="/signUp" method="post">
  <div class="container">
    <h1 style="text-align: center"><@spring.message "label.validation.signUp.registration"/></h1>
    <p class="global-error">
        <#if RequestParameters.error??>
            ${RequestParameters.error}
        </#if>
    </p>
    <hr>

    <div class="field">
      <label for="firstName"><b><@spring.message "label.validation.common.firstName"/></b></label>
      <input autocomplete="false" type="text" placeholder="<@spring.message "label.validation.signUp.enterFirstName"/>" name="firstName" id="firstName"
             value="<#if user?? && user.firstName??>${user.firstName}<#else></#if>">
      <p class="error">
          <@spring.bind "user.firstName"/>
          <@spring.showErrors "" ""/>
      </p>
    </div>

    <div class="field">
      <label for="lastName"><b><@spring.message "label.validation.common.lastName"/></b></label>
      <input autocomplete="false" type="text" placeholder="<@spring.message "label.validation.signUp.enterLastName"/>" name="lastName" id="lastName"
             value="<#if user?? && user.lastName??>${user.lastName}<#else></#if>">
      <p class="error">
          <@spring.bind "user.lastName"/>
          <@spring.showErrors "" ""/>
      </p>
    </div>

    <div class="field">
      <label for="phone"><b><@spring.message "label.validation.signUp.phone"/></b></label>
      <input autocomplete="false" type="text" placeholder="<@spring.message "label.validation.signUp.enterPhone"/>" name="phone" id="email"
             value="<#if user?? && user.phone??>${user.phone}<#else></#if>">
      <p class="error">
          <@spring.bind "user.phone"/>
          <@spring.showErrors "" ""/>
      </p>
    </div>

    <div class="field">
      <label for="email"><b><@spring.message "label.validation.common.email"/></b></label>
      <input autocomplete="false" type="text" placeholder="<@spring.message "label.validation.common.enterEmail"/>" name="email" id="email"
             value="<#if user?? && user.email??>${user.email}<#else></#if>">
      <p class="error">
          <@spring.bind "user.email"/>
          <@spring.showErrors "" ""/>
      </p>
    </div>

    <div class="field">
      <label for="password"><b><@spring.message "label.validation.common.password"/></b></label>
      <input autocomplete="false" type="password" placeholder="<@spring.message "label.validation.common.enterPassword"/>" name="password" id="password" value="">
      <p class="error">
          <@spring.bind "user.password"/>
          <@spring.showErrors "" ""/>
      </p>
    </div>

    <button type="submit" class="registerbtn" value="/sighUp"><@spring.message "label.validation.signUp.register"/></button>

    <div style="display: flex; justify-content: center; align-items: center; margin-top: 5px">
      <a href="/signIn"><@spring.message "label.validation.signUp.accountExists"/></a>
    </div>
  </div>
</form>
</body>
<footer>
  <div style="display: flex; justify-content: center; align-items: center; margin-top: 5px">
    <a href="/signIn?lang=en" class="langbtn"> <@spring.message "lang.en"/></a>
    <a href="/signIn?lang=ru" class="langbtn"> <@spring.message "lang.ru"/></a>
  </div>
</footer>
</html>