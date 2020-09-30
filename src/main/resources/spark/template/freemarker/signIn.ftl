<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>Sign-in | ${title}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="page">
    <h1>Web Checkers | ${title}</h1>

    <div class="body">

        <!-- Provide a message to the user, if supplied. -->
        <#include "message.ftl" />

        <form action="./signin" method="POST">
            <input id=${textfield} name=${textfield}>
            <button type="submit">Sign-In</button>
        </form>

    </div>
</div>
</body>