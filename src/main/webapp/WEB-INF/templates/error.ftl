<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Error</title>
</head>
<style>
    * {
        margin: 0;
        padding: 0;
        border: 0;
        vertical-align: baseline;
        box-sizing: border-box;
        color: inherit;
    }

    body {
        background-color: #5237d5;
        height: 100vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    h1 {
        font-size: 20vw;
        text-align: center;
        width: 100vw;
        color: white;
        font-family: "Montserrat", monospace;
    }

    p {
        font-size: 5vw;
        text-align: center;
        width: 100vw;
        color: white;
        font-family: "Montserrat", monospace;
    }
</style>
<body style="height: 100vh">
<h1>${status}</h1>
<p>${error}</p>
</body>
</html>