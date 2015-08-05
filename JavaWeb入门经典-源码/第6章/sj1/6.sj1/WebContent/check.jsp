<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
 %>
<!DOCTYPE HTML>
<html>
  <head>  
    <title>检查</title>
    
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
   		String name = request.getParameter("name");
  	 %>
  	 <!-- 使用useBean动作标签导入JavaBean对象 -->
  	<jsp:useBean id="strBean" class="com.wgh.bean.StringUtil"></jsp:useBean>
  	 <!-- 对StringUtil类的str属性赋值 -->
  	<jsp:setProperty property="str" name="strBean" value="<%=name %>"/>
    <table>
    	<tr>
			<td align="right">输入的用户名为：</td>
			<td>
				<!-- 从StringUtil对象中获得dateStr的属性值 -->	
				<jsp:getProperty property="str" name="strBean"/>
			</td>
		</tr>
		<tr>
			<td align="right">是否有效：</td>
			<td>	
				<!-- 从StringUtil对象中获得valid的属性值 -->	
				<jsp:getProperty property="valid" name="strBean"/>
			</td>		
		</tr>
		<tr>
			<td align="right">提示信息：</td>
			<td>	
				<!-- 从StringUtil对象中获得cue的属性值 -->	
				<jsp:getProperty property="cue" name="strBean"/>
			</td>
		</tr>
 	</table>	
  </body>
</html>
