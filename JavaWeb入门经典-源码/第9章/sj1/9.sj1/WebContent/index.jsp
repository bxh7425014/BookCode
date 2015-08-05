<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>利用JSTL实现用户注册协议</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  
  <body><div align="center">用户注册协议</div>
  		<table align="center" border="2">
  			<tr>
  				<td>&nbsp;</td>
  			</tr>
  			<tr>
  				<td><textarea rows="15" cols="80">
  				<c:import url="agreement.txt" charEncoding="UTF-8"/>
  			</textarea></td>
  			</tr>
  			<tr>
  			<td align="center" colspan="2"><input type="submit" value="我同意"/>
  			<input type="submit" value="我不同意"/></td>
  			</tr>
  		</table>

  </body>
</html>
