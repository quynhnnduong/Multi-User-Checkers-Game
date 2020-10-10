 <div class="navigation">
  <!-- #if currentUser?? removed this because it panics when there's no current user -->
  <!-- checks if there's a login, so we don't have to worry about it panicking when no one's there -->

 <#if loggedIn>
    <a href="/">my home</a> |
    <form id="signout" action="./signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.getName()}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
