<div class="body">
    <p>Watch Replays</p>
    <#if replayList??>
        <#list replayList as replay>
            <a>${replay.getRed().getName()} vs ${replay.getWhite().getName()}</a>
            <form action="./replay/game" method="GET">
                <button name="gameID" value=${"a"?replace("a", replay.getGameId())} type="submit">Watch Replay</button>
            </form>
        </#list>
    </#if>

</div>