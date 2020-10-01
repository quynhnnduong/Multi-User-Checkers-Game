<#if message??>
  <div id="message" class="${message.type}">${message.text}</div>
<#--  ${playersMessage}-->
<#else>
  <div id="message" class="INFO" style="display:none">
  </div>
</#if>
