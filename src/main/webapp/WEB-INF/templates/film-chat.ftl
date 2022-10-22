<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://snipp.ru/cdn/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.6.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script>
        var userId = ${model['chat'].user.id};
        var socket = null;
        var stompClient = null;
        var headers = { "${_csrf.headerName}" : "${_csrf.token}" }

        $(document).ready(function () {
            connect();
            var msgContainer = document.getElementById("msg-container");
            msgContainer.scrollTop = msgContainer.scrollHeight;
        });
        $(function () {
            $("#msger-input").keypress(function (event) {
                if (event.keyCode === 13) {
                    send();
                }
            });
            $("#msger-send-btn").click(function () {
                send();
            });
        });
        function connect() {
            socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect(headers, function() {
                console.log('Web Socket is connected ...');
                stompClient.subscribe('/films/${model['chat'].film.id}/chat/messages', function(data) {
                    var message = JSON.parse(data.body);
                    var iam = message.author.id == userId;
                    var msgClass = iam ? "right-msg" : "left-right";
                    var author = iam ? "you" : message.author.email;
                    var showAdmin = !iam && message.author.isAdmin ? "<i title=\"admin\" class=\"material-icons\">star</i>" : "";
                    var msg =
                            "<div class=\"msg " + msgClass + "\">" +
                            "<div class=\"msg-bubble\">" +
                            "<div class=\"msg-info\">" +
                            "<div class=\"msg-info-name\">" + author + showAdmin + "</div>" +
                            "<div class=\"msg-info-time\">" + moment(message.dateTimeCreate).format('HH:MM DD.MM.YYYY') + "</div>" +
                            "</div>" +
                            "<div class=\"msg-text\">" + message.text + "</div>" +
                            "</div>" +
                            "</div>";
                    var msgContainer = document.getElementById("msg-container");
                    msgContainer.innerHTML += msg;
                    if (iam) {
                        msgContainer.scrollTop = msgContainer.scrollHeight;
                    }
                });
            });
        }
        function send() {
            var message = document.getElementById('msger-input').value;
            if (message && message.length > 0) {
                stompClient.send("/app/film/${model['chat'].film.id}/chat/messages/send", {}, JSON.stringify({text: message, authorId: userId}));
                document.getElementById('msger-input').value = "";
            }
        }
        function getCookie(name) {
            var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
            if (match) {
                return match[2];
            }
            return "";
        }
    </script>
</head>
<style>
    :root {
        --body-bg: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
        --msger-bg: #fff;
        --border: 2px solid #ddd;
        --left-msg-bg: #ececec;
        --right-msg-bg: #725bec;
    }
    html {
        box-sizing: border-box;
    }
    *,
    *:before,
    *:after {
        margin: 0;
        padding: 0;
        box-sizing: inherit;
    }
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background-image: var(--body-bg);
        font-family: Helvetica, sans-serif;
    }
    .chat {
        display: flex;
        flex-flow: column wrap;
        justify-content: space-between;
        width: 100%;
        max-width: 867px;
        margin: 25px 10px;
        height: calc(100% - 50px);
        border: var(--border);
        border-radius: 5px;
        background: var(--msger-bg);
        box-shadow: 0 15px 15px -5px rgba(0, 0, 0, 0.2);
    }
    .chat-header {
        display: flex;
        justify-content: space-between;
        padding: 10px;
        border-bottom: var(--border);
        background: #eee;
        color: #666;
    }
    .msg-container {
        flex: 1;
        overflow-y: auto;
        padding: 10px;
    }
    .msg-container::-webkit-scrollbar {
        width: 6px;
    }
    .msg-container::-webkit-scrollbar-track {
        background: #ddd;
    }
    .msg-container::-webkit-scrollbar-thumb {
        background: #bdbdbd;
    }
    .msg {
        display: flex;
        align-items: flex-end;
        margin-bottom: 10px;
    }
    .msg:last-of-type {
        margin: 0;
    }
    .msg-bubble {
        max-width: 450px;
        padding: 15px;
        border-radius: 15px;
        background: var(--left-msg-bg);
    }
    .msg-info {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
    }
    .msg-info-name {
        margin-right: 10px;
        font-weight: bold;
    }
    .msg-info-time {
        font-size: 0.85em;
    }
    .left-msg .msg-bubble {
        border-bottom-left-radius: 0;
    }
    .right-msg {
        flex-direction: row-reverse;
    }
    .right-msg .msg-bubble {
        background: var(--right-msg-bg);
        color: #fff;
        border-bottom-right-radius: 0;
    }
    .msg-inputarea {
        display: flex;
        padding: 10px;
        border-top: var(--border);
        background: #eee;
    }
    .msg-inputarea * {
        padding: 10px;
        border: none;
        border-radius: 3px;
        font-size: 1em;
    }
    .msger-input {
        flex: 1;
        background: #ddd;
    }
    .msger-send-btn {
        margin-left: 10px;
        background: rgb(82, 55, 213);
        color: #fff;
        cursor: pointer;
        transition: background 0.23s;
    }
    .msg-container {
        background-color: #fcfcfe;
    }
    .material-icons {
        font-size: 12pt;
        color: #5237d5;
    }
</style>
<body>
<section class="chat">
    <header class="chat-header">
        <div class="chat-header-title">
            ${model["chat"].film.title}
        </div>
    </header>

    <main class="msg-container" id="msg-container">
        <#list model["chat"].messages as msg>
            <#if model["chat"].user.id == msg.author.id>
                <#assign msgClass="right-msg">
                <#assign authorName="you">
                <#assign isAdmin=false>
            <#else>
                <#assign msgClass="left-msg">
                <#assign authorName=msg.author.email>
                <#assign isAdmin=msg.author.isAdmin>
            </#if>
            <div class="msg ${msgClass}">
                <div class="msg-bubble">
                    <div class="msg-info">
                        <div class="msg-info-name">${authorName}
                            <#if isAdmin>
                                <i title="admin" class="material-icons">star</i>
                            </#if>
                        </div>
                        <div class="msg-info-time">${(msg.dateTimeCreate).format('HH:mm dd.MM.yyyy')}</div>
                    </div>
                    <div class="msg-text">${msg.text}</div>
                </div>
            </div>
        </#list>
    </main>
    <div class="msg-inputarea">
        <input type="text" class="msger-input" placeholder="Enter your message..." id="msger-input">
        <button class="msger-send-btn" id="msger-send-btn">Send</button>
    </div>
</section>
</body>
</html>