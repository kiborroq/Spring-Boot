<html>
<head>
  <title>SignIn</title>
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
</style>
<body>
<form action="/signIn" method="post">
  <div class="container">
    <h1 style="text-align: center">Authentication</h1>
    <p class="error">
        <#if RequestParameters.formError??>
          Email or password incorrect
        <#elseif RequestParameters.sessionError??>
          An error has occurred
        </#if>
    </p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input autocomplete="false" type="text" placeholder="Enter Email" name="email" id="email" required/>

    <label for="password"><b>Password</b></label>
    <input autocomplete="false" type="password" placeholder="Enter Password" name="password" id="password" required>

    <div style="display: flex; align-items: center; width: 100%; margin-bottom: 20px">
      <input autocomplete="false" type="checkbox" name="remember-me" id="remember-me">
      <label for="remember-me" class="remember-me-label"><b>Remember me</b></label>
    </div>

    <button type="submit" class="loginbtn" value="submit">Login</button>

    <div style="display: flex; justify-content: center; align-items: center; margin-top: 5px">
      <a href="/signUp">Don't have account</a>
    </div>

  </div>
</form>
</body>
</html>