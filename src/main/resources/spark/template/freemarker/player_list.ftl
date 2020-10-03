<div class="body">
    <!-- If there is a currentUser, we can show them all the names in the player lobby -->
        <!-- Iterate through the playerLobby's set of players -->
            <!-- if the player in the list is not the current user, print the player as a button with a link to /game -->

    <#if playerList??>
        <!--Display everyone in the player  lobby except the current user-->
        <#list playerList as user>
            <#if user != currentUser>
                <a>${user.getName()}</a>
                <form action="./game" method="GET">
                    <button type="submit">Start A Game</button>
                </form>
            </#if>
        </#list>
    </#if>
</div>