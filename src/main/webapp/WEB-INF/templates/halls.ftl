<!DOCTYPE html>
<html lang="en">
<head>
    <title>Halls</title>
</head>
<style>
    body {
        height: 100%;
        width: 100%;
        font-family: Verdana, sans-serif;
        margin: 0;
    }
    .container {
        width: 1200px;
        height: calc(100% - 100px);
        display: flex;
        flex-direction: column;
        margin: 0 auto;
        padding: 50px 0;
    }
    .container-content {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: center;
    }
    .add-hall-form {
        width: 250px;
        margin-right: 50px;
        display: flex;
        justify-content: left;
    }
    input[type=text], input[type=password] {
        height: 25px;
        width: calc(100% - 10px);
        padding: 5px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }
    input[type=text]:focus, input[type=password]:focus {
        background-color: #ddd;
        outline: none;
    }
    .createbtn {
        background-color: #5237d5;
        color: white;
        border: none;
        cursor: pointer;
        width: 100%;
        height: 35px;
        opacity: 0.9;
        font-size: 10pt;
    }
    .createbtn:hover {
        opacity:1;
        cursor: pointer;
    }
    .halls-list {
        width: calc(100% - 300px);
        display: flex;
        flex-direction: column;
    }
    .container-label {
        color: #5237d5;
        padding: 5px 10px;
        margin: 0;
        font-size: 13pt;
        text-align: left;
        cursor: pointer;
    }
    .current-container-label {
        color: #ffffff;
        background-color: #5237d5;
    }
    table {
        width: 100%;
        overflow-y: auto;
        font-size: 10pt;
        border-collapse: collapse;
    }
    hr {
        height: 3px;
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
    a {
        text-decoration: none;
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
<div class="container">
    <p class="global-error">
        <#if RequestParameters.error??>
            ${RequestParameters.error}
        </#if>
    </p>
    <div style="display: flex; flex-direction: row" class="container-head">
        <a href="/admin/panel/halls" class="container-label current-container-label">Halls</a>
        <a href="/admin/panel/films" class="container-label">Movies</a>
        <a href="/admin/panel/sessions" class="container-label">Sessions</a>
    </div>
    <hr>
    <div class="container-content">
        <div class="add-hall-form">
            <form action="/admin/panel/halls" method="post">
                <label for="seatsCount"><b style="font-size: 10pt">Seats count</b></label>
                <input autocomplete="off" type="text" min="1" placeholder="Enter count" name="seatsCount" id="seatsCount" required>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit" class="createbtn" value="/admin/panel/halls">Create hall</button>
            </form>
        </div>
        <div class="halls-list">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Seats count</th>
                </tr>
                <#list model["halls"] as hall>
                    <tr>
                        <td>${hall.id}</td>
                        <td>${hall.seatsCount}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>
</body>
</html>