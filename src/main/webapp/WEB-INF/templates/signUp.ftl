<html>
<head>
  <title>SignUp</title>
</head>
<#global errors = model["errors"]>
<#global fields = model["fields"]>
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
    }
    .global-error {
        width: 100%;
        margin: 0 0 15px;
        font-size: 14pt;
        color: #c41515;
        text-align: center;
    }
</style>
<body>
<form action="/signUp" method="post">
  <div class="container">
    <h1 style="text-align: center">Registration</h1>
    <p class="global-error">
        <#if RequestParameters.error??>
            ${RequestParameters.error}
        </#if>
    </p>
    <hr>

    <div class="field">
      <label for="firstName"><b>First name</b></label>
      <input autocomplete="false" type="text" placeholder="Enter first name" name="firstName" id="firstName" required
             value="<#if fields["firstName"]??>${fields["firstName"]}<#else></#if>">
      <p class="error"><#if errors["firstName"]??>${errors["firstName"]}<#else></#if></p>
    </div>

    <div class="field">
      <label for="lastName"><b>Last name</b></label>
      <input autocomplete="false" type="text" placeholder="Enter last name" name="lastName" id="lastName" required
             value="<#if fields["lastName"]??>${fields["lastName"]}<#else></#if>">
      <p class="error"><#if errors["lastName"]??>${errors["lastName"]}<#else></#if></p>
    </div>

    <div class="field">
      <label for="phone"><b>Phone</b></label>
      <input autocomplete="false" type="text" placeholder="Enter Phone" name="phone" id="email" required
             value="<#if fields["phone"]??>${fields["phone"]}<#else></#if>">
      <p class="error"><#if errors["phone"]??>${errors["phone"]}<#else></#if></p>
    </div>

    <div class="field">
      <label for="email"><b>Email</b></label>
      <input autocomplete="false" type="text" placeholder="Enter Email" name="email" id="email" required
             value="<#if fields["email"]??>${fields["email"]}<#else></#if>">
      <p class="error"><#if errors["email"]??>${errors["email"]}<#else></#if></p>
    </div>

    <div class="field">
      <label for="password"><b>Password</b></label>
      <input autocomplete="false" type="password" placeholder="Enter Password" name="password" id="password" required
             value="<#if fields["password"]??>${fields["password"]}<#else></#if>">
      <p class="error"><#if errors["password"]??>${errors["password"]}<#else></#if></p>
    </div>

    <button type="submit" class="registerbtn" value="/sighUp">Register</button>

    <div style="display: flex; justify-content: center; align-items: center; margin-top: 5px">
      <a href="/signIn">Already have account</a>
    </div>
  </div>
</form>
</body>
</html>