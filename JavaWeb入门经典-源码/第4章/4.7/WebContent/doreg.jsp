<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="us" class="com.Bean.UserInfo" scope="request"/>
<jsp:setProperty name="us" property="*"/>
<%
   String name=us.getName();
   String job=us.getJob();
   
   if(name.equals("")||job.equals("")){
%>
   <jsp:forward page="/false.jsp"/>
<% }else{ %>
   <jsp:forward page="/success.jsp"/>
<% } %>