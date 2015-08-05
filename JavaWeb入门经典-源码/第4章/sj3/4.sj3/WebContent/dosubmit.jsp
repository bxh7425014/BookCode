<%@ page language="java" contentType="text/html; charset=gb2312"%>

<jsp:useBean id="logonimg" class="com.javabean.Image" scope="request"/>
<jsp:setProperty name="logonimg" property="x" param="logon.x"/>
<jsp:setProperty name="logonimg" property="y" param="logon.y"/>

<jsp:useBean id="regimg" class="com.javabean.Image" scope="request"/>
<jsp:setProperty name="regimg" property="x" param="reg.x"/>
<jsp:setProperty name="regimg" property="y" param="reg.y"/>

<% if(logonimg.getSelected()){ %>
<jsp:forward page="logon.jsp"/>
<% } else if(regimg.getSelected()){ %>
<jsp:forward page="reg.jsp"/>
<% } else{  %>
<jsp:forward page="error.jsp"/>
<% } %>
