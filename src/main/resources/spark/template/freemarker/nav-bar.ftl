 <div class="navigation">
  <!-- #if currentUser?? removed this because it panics when there's no current user -->
  <!-- checks if theres a login, so we dont have to worry about it panicing when no one's there -->
 <#if loggedIn>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.getName()}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
