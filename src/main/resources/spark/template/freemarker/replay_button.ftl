<div class="body">
    <p>Watch Replays</p>
    <#if replayList??>
        <#list replayList as replay>
            <a></a>
        </#list>
    </#if>
    <form action="./replay/game" method="GET">
        <button name="opponent" value=${"a"?replace("a", user.getName())} type="submit">Start A Game</button>
    </form>
</div>