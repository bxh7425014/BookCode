<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>显示注册信息</title>
		<meta charset="UTF-8">
            <style type="text/css">
    td{
    	padding:5px;
    	font-size:12px;
    }
    </style>
	</head>
	<body>
		<form name="form1">
			<div align="center">
				<p class="style1">
					显示用户注册信息
				</p>
				<table width="400" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolorlight="#333333" bordercolordark="#FFFFFF">
					<tr>
						<td width="117">
							<div align="right">
								用户名
							</div>
						</td>
						<td width="267">
							<div align="left">
								<input type="text" name="textfield"
									value="<%=new String(request.getParameter("textfield").getBytes("ISO8859-1"),"UTF-8")%>">
								<span class="style2">*</span>
							</div>
						</td>
					</tr>					
					<tr>
						<td>
							<div align="right">
								性别
							</div>
						</td>
						<td>
							<div align="left">
								<input type="text" name="select"
									value="<%=new String(request.getParameter("select").getBytes("ISO8859-1"),"UTF-8")%>">
							</div>
						</td>
					</tr>
					<tr>
						<td height="98">
							<div align="right">
								爱好
							</div>
						</td>
						<td>
							<div align="left">
								<%
									String[] loves = request.getParameterValues("like");
									for (int i = 0; i < loves.length; i++) {
								%>
								<%=new String(loves[i].getBytes("ISO8859_1"), "UTF-8")
									+ "&nbsp;&nbsp;"%>
								<%}%>
						<p>
					</tr>
					<tr>
						<td>
							<div align="right">
								邮箱
							</div>
						</td>
						<td>
							<div align="left">
								<input type="text" name="textfield4"
									value="<%=new String(request.getParameter("textfield4").getBytes("ISO8859-1"),"UTF-8")%>">
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
								<a href="index.jsp">返回</a>								
							</div>
						</td>
					</tr>
				</table>
				<p align="left" class="style1">&nbsp;
					
				</p>
			</div>
		</form>
	</body>
</html>
