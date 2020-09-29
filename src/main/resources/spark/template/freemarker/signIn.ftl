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

        <form>
            <input type="text" id=${textfield} name=${textfield}>
            <input type="button" id=${signIn} name=${signIn}>
        </form>

    </div>
</div>
</body>