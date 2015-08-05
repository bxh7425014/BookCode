<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.jfree.chart.servlet.ServletUtilities,com.wgh.util.ChartUtil"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>Java图书销量统计</title>
  </head>
  <body>
    <%
    	String fileName = ServletUtilities.saveChartAsJPEG(ChartUtil.createChart(),500,300,session);
    	String graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
    %>
    <img src="<%=graphURL%>">
  </body>
</html>
