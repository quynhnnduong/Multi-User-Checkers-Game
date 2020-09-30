<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>


  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />


  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->
    <#if isSignedIn??>
        <h1>Player Lobby</h1>
           <p>${player_name}</p>

    </#if>


  </div>

</div>
</body>

</html>
