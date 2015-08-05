<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>简单投票器</title>
  </head>
  <body>
    <s:form action="piao">
  	<s:label label="候选人信息" value="mr、mrsoft、mrkj"></s:label>
  	<s:textfield name="name" label="您选择的候选人"></s:textfield>
  	<s:submit value="提交"></s:submit>
  </s:form>
  </body>
</html>
