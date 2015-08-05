<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
 %>
<!DOCTYPE HTML>
<html>
  <head>  
    <title>计算两个日期相差的天数</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		table{
			border: 1px solid;
			border-color: green;
			color: green;
			font-size: 12px;
		}
	</style>
  </head>
  <body>
   	<%
   		String dateStr1 = request.getParameter("datestr1");
   		String dateStr2 = request.getParameter("datestr2");
  	 %>
  	 <!-- 使用useBean动作标签导入JavaBean对象 -->
  	<jsp:useBean id="strBean" class="com.wgh.bean.StringUtil"></jsp:useBean>
  	 <!-- 对StringUtil类的dateStr1属性赋值 -->
  	<jsp:setProperty property="dateStr1" name="strBean" value="<%=dateStr1 %>"/>
  	 <!-- 对StringUtil类的dateStr2属性赋值 -->
  	<jsp:setProperty property="dateStr2" name="strBean" value="<%=dateStr2 %>"/>
    <table>
    	<tr>
			<td align="right">第一个日期为：</td>
			<td>
				<!-- 从StringUtil对象中获得dateStr1的属性值 -->	
				<jsp:getProperty property="dateStr1" name="strBean"/>
			</td>
		</tr>
		<tr>
			<td align="right">第二个日期为：</td>
			<td>
				<!-- 从StringUtil对象中获得dateStr2的属性值 -->	
				<jsp:getProperty property="dateStr2" name="strBean"/>
			</td>
		</tr>
		<tr >
			<td align="right">两个日期相差的天数为：</td>
			<td >	
				<!-- 从StringUtil对象中获得minus的属性值 -->	
				<jsp:getProperty property="minus" name="strBean"/>
			</td>		
		</tr>
 	</table>	
  </body>
</html>
