<!DOCTYPE html>
<html lang="en">
<head>
    <title>Movie sessions</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://snipp.ru/cdn/jquery/2.1.1/jquery.min.js"></script>
    <script>
        var prevValue = ''
        var tableHeader = "<th>ID</th>" +
                "<th>Hall</th>" +
                "<th>Movie</th>" +
                "<th>Session date, from</th>" +
                "<th>Session date, to</th>" +
                "<th>Ticket cost, RUB</th>"
        function search(title) {
            $.ajax({
                type: "GET",
                url: "/sessions/search?filmName=" + title
                // headers: {"X-XSRF-TOKEN" : getCookie("XSRF-TOKEN")}
            }).done(function (data) {
                reDrawTable(data);
            });
            // $.get("/admin/panel/sessions/search?filmName=" + title, "", );
        }
        function onClick() {
            search(document.getElementById('search').value);
        }
        function openSession(sessionId) {
            window.open("/sessions/" + sessionId, "_self");
        }
        function reDrawTable(sessions) {
            table = document.getElementById('sessions-table');
            $("#sessions-table tr").remove();
            if (sessions.length > 0) {
                var headerTr = table.insertRow(-1);
                headerTr.innerHTML = tableHeader;
                for (let i = 1; i < sessions.length + 1; ++i) {
                    var session = sessions[i - 1];
                    var rowTr = table.insertRow(i);
                    rowTr.setAttribute('class', 'select-session');
                    rowTr.setAttribute('id', 'select-session');
                    rowTr.setAttribute('onclick', 'openSession(' + session.id + ')');
                    var idTd = rowTr.insertCell(0);
                    idTd.innerHTML = session.id;
                    var hallTd = rowTr.insertCell(1);
                    hallTd.innerHTML = session.hall.id;
                    var movieTd = rowTr.insertCell(2);
                    movieTd.innerHTML = '<div class="film-container">' +
                            '<img class="film-poster" src="' + session.film.posterUrl + '">' +
                            '<p class="film-title">' + session.film.title + ' (' + session.film.ageRestrictions + '+)</p>' +
                            '</div>';
                    var fromTd = rowTr.insertCell(3);
                    fromTd.innerHTML = session.sessionDateTimeFrom;
                    var toTd = rowTr.insertCell(4);
                    toTd.innerHTML = session.sessionDateTimeTo;
                    var costTd = rowTr.insertCell(5);
                    costTd.innerHTML = session.ticketCost;
                }
            }
        }
        function getCookie(name) {
            var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
            if (match) {
                return match[2];
            }
            return "";
        }
        $(document).ready(function () {
            $('#search').on('keyup', function () {
                var title = $(this).val();
                if (prevValue !== title) {
                    search(title);
                    prevValue = title;
                }
            });
        });
    </script>
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
    input, select {
        height: 25px;
        width: calc(100% - 10px);
        padding: 5px;
        margin: 5px 0 22px 0;
        display: inline-block;
        border: none;
        background: #f1f1f1;
    }
    input:focus {
        background-color: #ddd;
        outline: none;
    }
    select {
        height: 35px;
        max-height: 100px;
        width: 100%;
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
    .session-search {
        width: calc(100% - 300px);
        height: 100%;
        display: flex;
        flex-direction: column;
    }
    .search-form {
        width: 100%;
        margin-bottom: 20px;
        position: relative;
        height: 34px;
    }
    .sessions-list {
        width: 100%;
        height: 100%;
        overflow-y: auto;
    }
    .film-poster {
        width: 50px;
        height: 50px;
    }
    .film-title {
        margin: 0 0 0 5px;
    }
    .film-container {
        display: flex;
        justify-content: left;
        align-items: center;
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
        padding: 7px;
        height: 50px;
    }
    th {
        background-color: #dddddd;
        height: 20px;
    }
    a {
        text-decoration: none;
    }
    .search-input, .search-input:focus {
        display: block;
        width: calc(100% - 4px);
        height: 34px;
        line-height: 34px;
        padding: 0;
        margin: 0;
        border: 2px solid #5237d5;
        outline: none;
        overflow: hidden;
        border-radius: 18px;
        background-color: rgb(255, 255, 255);
        text-indent: 15px;
        font-size: 14px;
        color: #222;
    }
    .search-input:hover, .search-input:focus {
        border: 3px solid #5237d5;
        width: calc(100% - 6px);
        height: 32px;
        line-height: 32px;
    }
    .searchbtn {
        border: 0;
        outline: 0;
        position: absolute;
        top: 5px;
        right: 15px;
        background-color: white;
        cursor: pointer;
        color: #5237d5;
    }
    .select-session:hover {
        cursor: pointer;
        background-color: #eeeeee;
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
    <div class="container-content">
        <div class="session-search">
            <div class="search-form">
                <input autocomplete="off" class="search-input" type="text" name="filmName" id="search" placeholder="Enter movie title">
                <button class="searchbtn" onclick="onClick()"><i class="material-icons">search</i></button>
            </div>
            <div class="sessions-list">
                <table class="session-table" id="sessions-table">
                    <tr>
                        <th>ID</th>
                        <th>Hall</th>
                        <th>Movie</th>
                        <th>Session date, from</th>
                        <th>Session date, to</th>
                        <th>Ticket cost, RUB</th>
                    </tr>
                    <#list model["sessions"] as session>
                        <tr class="select-session" id="select-session" onclick="openSession(${session.id})">
                            <td>${session.id}</td>
                            <td>${session.hall.id}</td>
                            <td>
                                <div class="film-container">
                                    <img class="film-poster" src="${session.film.posterUrl}">
                                    <p class="film-title">${session.film.title} (${session.film.ageRestrictions}+)</p>
                                </div>
                            </td>
                            <td>${(session.sessionDateTimeFrom).format('HH:mm dd.MM.yyyy')}</td>
                            <td>${(session.sessionDateTimeTo).format('HH:mm dd.MM.yyyy')}</td>
                            <td>${session.ticketCost}</td>
                        </tr>
                    </#list>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>