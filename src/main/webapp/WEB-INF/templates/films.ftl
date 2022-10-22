<!DOCTYPE html>
<html lang="en">
<script>
    function triggerInput(filmId) {
        document.getElementById('inputFile' + filmId).click();
    }
    function inputImage(event, filmId) {
        if (event.target.files[0]) {
            document.getElementById("uploadbtn" + filmId).click();
        }
    }
</script>
<head>
    <title>Movies</title>
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
    .add-film-form {
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
    textarea {
        width: calc(100% - 10px);
        resize: none;
        padding: 5px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }
    textarea:focus {
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
    .films-list {
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
</style>
<body>
<div class="container">
    <div style="display: flex; flex-direction: row" class="container-head">
        <a href="/admin/panel/halls" class="container-label">Halls</a>
        <a href="/admin/panel/films" class="container-label current-container-label">Movies</a>
        <a href="/admin/panel/sessions" class="container-label">Sessions</a>
    </div>
    <hr>
    <div class="container-content">
        <div class="add-film-form">
            <form action="/admin/panel/films" method="post">
                <label for="title"><b style="font-size: 10pt">Title</b></label>
                <input autocomplete="off" type="text" placeholder="Enter title" name="title" id="title" maxlength="100" required>
                <label for="description"><b style="font-size: 10pt">Description</b></label>
                <textarea autocomplete="off" placeholder="Enter description" name="description" id="description" maxlength="1000" rows="3"></textarea>
                <label for="duration"><b style="font-size: 10pt">Duration, min</b></label>
                <input autocomplete="off" type="text" placeholder="Enter duration" name="duration" id="duration" required>
                <label for="yearOfRelease"><b style="font-size: 10pt">Year of release</b></label>
                <input autocomplete="off" type="text" min="0" placeholder="Enter year" name="yearOfRelease" id="yearOfRelease" required>
                <label for="ageRestrictions"><b style="font-size: 10pt">Age restriction</b></label>
                <input autocomplete="off" type="text" min="1" placeholder="Enter age" name="ageRestrictions" id="ageRestrictions" required>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit" class="createbtn" value="/admin/panel/films">Create film</button>
            </form>
        </div>
        <div class="films-list">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Poster</th>
                    <th>Description</th>
                    <th>Duration</th>
                    <th>Release</th>
                    <th>Age restriction</th>
                </tr>
                <#list model["films"] as film>
                    <tr>
                        <td>${film.id}</td>
                        <td>${film.title}</td>
                        <td>
                            <img style="width: 50px; height: 50px; cursor: pointer" src="${film.posterUrl}" onclick="triggerInput(${film.id})">
                            <form class="upload-form"
                                  action="/admin/panel/film/${film.id}/poster"
                                  method="POST"
                                  enctype="multipart/form-data">
                                <input id="inputFile${film.id}"
                                       style="display: none"
                                       type="file"
                                       name="image"
                                       onchange="inputImage(event, ${film.id})"
                                       accept="image/*">
                                <input id="uploadbtn${film.id}"
                                       style="display: none"
                                       type="submit"
                                       value="Submit">
                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                            </form>
                        </td>
                        <td>
                            <#if film.description?has_content>
                                ${film.description}
                            <#else>
                                -
                            </#if>
                        </td>
                        <td>${film.duration}</td>
                        <td>${film.yearOfRelease}</td>
                        <td>${film.ageRestrictions}+</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>
</body>
</html>