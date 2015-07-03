<%--
网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
author  yeeku.H.lee kongyeeku@163.com
version  1.0
Copyright (C), 2001-2010, yeeku.H.Lee
This program is protected by copyright laws.
Program Name:
Date: 
--%>

<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>浏览拍卖中的物品</title>
</head>
<body>
<table width="780" align="center" cellspacing="0"
	background="images/bodybg.jpg">
<tr>
<td>
<br />
<s:actionerror/>
<table width="80%" align="center" cellpadding="0"
	cellspacing="1" style="border:1px solid black">
<tr bgcolor="#e1e1e1" >
	<td colspan="5"><div class="mytitle">当前种类是：<s:property value="kind"/></div></td> 
</tr>
  <tr height="30">
    <th>物品名</th>
    <th>起拍时间</th>
    <th>最高价格</th>
    <th>所有者</th>
    <th>物品备注</th>
  </tr>
<s:iterator id="item" value="items" status="st">
<tr height="24" <s:if test="#st.odd">
	style="background-color:#dddddd"</s:if>>
<td><a href='viewDetail.action?itemId=<s:property value="id"/>'>
<s:property value="name"/></a></td>
<td><s:property value="addTime"/></td>
<td><s:property value="maxPrice"/></td>
<td><s:property value="owner"/></td>
<td><s:property value="remark"/></td>
</tr>
</s:iterator>
</table>
</td>
</tr>
</table>
</body>
</html>