<%@page contentType="text/html; charset=UTF-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="java.net.URL"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" type="text/css" rel="stylesheet">
<title>防止表单在网站外部提交</title>
</head>
<%
	String strOne = request.getHeader("referer"); //获取页面的请求地址
	String pathOne = ""; //定义空字符串
	if (strOne != null) { //判断当页面的请求地址为空时
		URL urlOne = new URL(strOne); //实例化URL方法
		pathOne = urlOne.getHost(); //获取请求页面的服务器主机
	}
	String strTwo = request.getRequestURL().toString(); //获取当前网页的地址
	String pathTwo = "";
	if (strTwo != null) {
		URL urlTwo = new URL(strTwo);
		pathTwo = urlTwo.getHost(); //获取当前网页的服务器主机
	}
%>
<body>
	<table width="366" height="250" border="0" align="center"
		cellpadding="0" cellspacing="0">
		<tr align="center">
			<td width="366" background="images/00.jpg">
				<%
					if (!pathOne.equals(pathTwo)) { //判断当前页面的主机与服务器的主机是否相同
						%> 禁止网站外部提交表单！！！ <%
					} else {
						String name = request.getParameter("name");
						String pass = request.getParameter("pass");
						out.println("用户名：" + name);
						out.println("<br>");
						out.println("密　码：" + pass);

					}
				%>
			</td>
		</tr>
	</table>
</body>
</html>
