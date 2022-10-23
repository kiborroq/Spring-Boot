<html>
<head>
  <title>SignIn</title>
</head>
<style>
  hr {
    border: 1px solid #f1f1f1;
    margin: 15px 0;
  }
  a {
    text-align: center;
    font-weight: 100;
  }
</style>
<body>
<div class="container">
  <h3>Hello ${lastName} ${firstName},</h3>
  <p>Thank you for joining <b>Cinema App.</b></p>
  <p>We'd like to confirm that your account was created successfully. To access <b>Cinema App</b> click the link below.</p>

  <div class="confirm-form">
    <a href="http://localhost:8080/confirm/${token}" target="_blank" class="confirmbtn">
      Click to confirm
    </a>
  </div>

  <hr>

  <p>Regards, <b>Cinema App</b> team</p>
</div>
</body>
</html>