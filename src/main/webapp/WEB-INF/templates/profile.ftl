<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<#global user=model["user"]>
<script>
    function triggerInput() {
        document.getElementById('inputFile').click();
    }
    function inputImage(event) {
        if (event.target.files[0]) {
            document.getElementById('image').src=URL.createObjectURL(event.target.files[0]);
            document.getElementById("add-icon").style.display = "none";
            document.getElementById("uploadbtn").disabled = false;
        }
    }
</script>
<head>
  <title><@spring.message "profile"/></title>
</head>
<style>
    body {
        height: 100vh;
        font-family: Verdana, sans-serif;
    }
    .container {
        padding: 15px;
        width: 700px;
        height: 100%;
        display: flex;
        flex-direction: column;
        margin: auto auto 50px;
    }
    .avatar-user-info {
        width: 100%;
        display: flex;
        justify-content: left;
    }
    .avatar-info {
        display: flex;
        flex-direction: column;
    }
    .user-info {
        font-size: 13pt;
        margin-top: 50px;
        margin-left: 30px;
        display: flex;
        flex-direction: row;
        overflow-x: auto;
        scrollbar-width: thin;
    }
    .logout {
        display: flex;
        flex-direction: row;
        margin-left: auto;
        justify-content: center;
        align-items: start;
    }
    .logoutbtn {
        background-color: #5237d5;
        color: white;
        border: none;
        cursor: pointer;
        width: 60px;
        height: 30px;
        opacity: 0.9;
        font-size: 10pt;
    }
    .logoutbtn:hover {
        opacity:1;
        cursor: pointer;
    }
    .upload-form {
        width: 198px;
    }
    .uploadbtn {
        background-color: #5237d5;
        color: white;
        padding: 7px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        height: 30px;
        width: 200px;
        opacity: 0.9;
    }
    .uploadbtn:hover {
        opacity:1;
        cursor: pointer;
    }
    .image-preview {
        height:175px;
        width: 194px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 2px dashed #5237d5;
        border-radius: 10px;
    }
    .image {
        max-width: 194px;
        max-height: 175px;
        border-radius: inherit;
    }
    .add-icon {
        font-size: 40pt;
        color: #5237d5;
    }
    .add-icon:hover {
        font-size: 60pt;
    }
    .info-key {
        margin-right: 10px;
        min-width: 100px;
    }
    .label {
        color: #a4a4a4;
        margin: 2px;
        font-size: 13pt;
    }
    .table-label {
        color: #5237d5;
        margin: 2px;
        font-size: 13pt;
        text-align: left;
    }
    .value {
        margin: 2px;
    }
    .sessions-list {
        margin-top: 40px;
        display: flex;
        flex-direction: column;
    }
    .images-list {
        margin-top: 30px;
        display: flex;
        flex-direction: column;
    }
    .table {
        max-height: 500px;
        overflow-y: auto;
    }
    table {
        width: 100%;
        font-size: 10pt;
        border-collapse: collapse;
    }
    hr {
        height: 2px;
        background-color: #5237d5;
        border: none;
        width: 100%;
        padding: 0;
        margin: 0 0 15px;
    }
    td, th {
        border: 1px solid #dddddd;
        text-align: center;
        padding: 4px;
    }
    th {
        background-color: #dddddd;
    }
</style>
<body>
<div class="container">
  <p class="global-error">
      <#if RequestParameters.error??>
          ${RequestParameters.error}
      </#if>
  </p>
  <div class="avatar-user-info">
    <div class="avatar-info">
      <div id="fileSelect" onclick="triggerInput()" class="image-preview">
        <i id="add-icon" class="add-icon">+</i>
        <img id="image" class="image" alt="" src="">
      </div>
      <form class="upload-form" action="/user/avatar" method="POST" enctype="multipart/form-data">
        <input id="inputFile" style="display: none" type="file" name="image" onchange="inputImage(event)" accept="image/*">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <input id="uploadbtn" type="submit" class="uploadbtn" value="<@spring.message "upload"/> disabled>
      </form>
    </div>
    <div class="user-info">
      <div class="info-key">
        <p class="label"><@spring.message "firstName"/></p>
        <p class="label">Last name</p>
        <p class="label">Email</p>
        <p class="label">Phone</p>
      </div>
      <div class="info-value">
        <p class="value">${user.firstName}</p>
        <p class="value">${user.lastName}</p>
        <p class="value">${user.email}</p>
        <p class="value">${user.phone}</p>
      </div>
    </div>
    <div class="logout">
      <form action="/logout" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="logoutbtn" value="/admin/panel/halls">Logout</button>
      </form>
    </div>
  </div>
  <div class="sessions-list">
    <p class="table-label">Sessions</p>
    <hr>
    <div class="table">
      <table>
        <tr>
          <th>Date</th>
          <th>IP</th>
        </tr>
          <#list user.sessions as s>
            <tr>
              <td>${s.dateTimeCreate.format('HH:mm dd.MM.yyyy')}</td>
              <td>${s.ip}</td>
            </tr>
          </#list>
      </table>
    </div>
  </div>
  <div class="images-list">
    <p class="table-label">Images</p>
    <hr>
    <div class="table">
      <table>
        <tr>
          <th>File name</th>
          <th>Size</th>
          <th>MIME</th>
        </tr>
          <#list user.avatars as a>
            <tr>
              <td><a href="${a.url}">${a.name}</a></td>
              <td>${a.size}</td>
              <td>${a.type}</td>
            </tr>
          </#list>
      </table>
    </div>
  </div>
</div>
</body>
</html>