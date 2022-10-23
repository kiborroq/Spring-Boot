<#import "/spring.ftl" as spring />


<html>
<head>
  <title><@spring.message "label.validation.common.authentication"/></title>
</head>
<style>
    form {width: 400px; margin: auto}
    input[type=text], input[type=password] {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }
    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }
    input[type=checkbox] {
        margin: 0 5px 0 0;
        width: 15px;
        height: 15px;
    }
    hr {
        border: 1px solid #f1f1f1;
        margin-bottom: 25px;
    }
    .remember-me-label {
        font-weight: 100;
        font-size: 12pt;
    }
    .loginbtn {
        background-color: #5237d5;
        color: white;
        padding: 16px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        width: 400px;
        opacity: 0.9;
    }
    .loginbtn:hover {
        opacity:1;
        cursor: pointer;
    }
    .error {
        margin: 0;
        font-size: 14pt;
        color: #c41515;
        text-align: center;
    }
    .info {
        margin: 0;
        font-size: 14pt;
        color: #17a342;
        text-align: center;
    }
</style>
<body>
<form action="/signIn" method="post">
  <div class="container">
    <h1 style="text-align: center" lang="En"><@spring.message "label.validation.common.authentication"/></h1>
    <p class="error">
        <#if RequestParameters.emailPasswordError??>
            <@spring.message "error.signIn.email"/>
        <#elseif RequestParameters.confirmedError??>
            <@spring.message "error.signIn.confirm"/>
        <#elseif RequestParameters.error??>
          RequestParameters.error
        </#if>
    </p>
    <p class="info">
        <#if RequestParameters.confirmEmail??>
            <@spring.message "info.signIn.confirm"/>
        </#if>
    </p>
    <hr>

    <label for="email"><b><@spring.message "label.validation.common.email"/></b></label>
    <input autocomplete="false" type="text" placeholder="<@spring.message "label.validation.common.enterEmail"/>" name="email" id="email" required/>

    <label for="password"><b><@spring.message "label.validation.common.password"/></b></label>
    <input autocomplete="false" type="password" placeholder="<@spring.message "label.validation.common.enterPassword"/>" name="password" id="password" required>

    <div style="display: flex; align-items: center; width: 100%; margin-bottom: 20px">
      <input autocomplete="false" type="checkbox" name="remember-me" id="remember-me">
      <label for="remember-me" class="remember-me-label"><b><@spring.message "label.validation.common.RememberMe"/></b></label>
    </div>

    <button type="submit" class="loginbtn" value="submit"><@spring.message "label.validation.common.logIn"/></button>


    <div style="display: flex; justify-content: center; align-items: center; margin-top: 5px">
      <a href="/signUp"><@spring.message "label.validation.signIn.noAccount"/></a>
    </div>

  </div>
</form>
</body>
<footer>
  <div style="display: flex; flex-direction: row; justify-content: center; align-items: center; margin-top: 25px; margin-bottom: 25px">
      <a href="/signIn?lang=en" style="margin-right: 15px"> <@spring.message "lang.en"/></a>
      <a href="/signIn?lang=ru"> <@spring.message "lang.ru"/></a>
  </div>
</footer>
</html>