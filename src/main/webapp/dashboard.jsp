<%@ page import="java.util.*" %>
<h2> Log Dashboard </h2>
<h3> Logs By Level </h3>
<% Map<String, Integer> levels = (Map<String,Integer>)request.getAttribute("levels");
    for(String key : levels.keySet()){
    %>
    <p> <%=key%> : <%=levels.get(key)%> </p>
<%
}
%>

<a href="search.jsp">search Logs </a>