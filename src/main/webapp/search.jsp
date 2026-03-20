<%@ page import="java.util.*,com.logtool.model.LogEntry" %>
<form action="search">
    <input type="text" name="q" placeholder="Search logs" />
    <button type="submit"> search </button>
</form>

<% List<LogEntry> logs = (List<LogEntry>)request.getAttribute("logs");
if(logs != null){
    for(LogEntry log : logs){
    %>

<p>
    <%=log.getTimestamp()%>
    <%=log.getLevel()%>
    <%=log.getService()%>
    <%=log.getMessage()%>
</p>
<%
}
}
%>