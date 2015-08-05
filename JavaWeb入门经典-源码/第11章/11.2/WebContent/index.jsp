<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应用commons-fileUpload实现文件上传</title>
<style type="text/css">
ul{list-style: none;}
li{padding:5px;}
</style>
</head>
<body>
	<script type="text/javascript">
		function validate() {
			if (form1.file.value == "") {
				alert("请选择要上传的文件");
				return false;
			}
		}
	</script>
	<!-- 定义表单 -->
	<form action="UploadServlet" method="post"
		enctype="multipart/form-data" name="form1" id="form1"
		onsubmit="return validate()">
		<ul>
			<li>请选择要上传的附件：</li>
			<li>上传文件： <input type="file" name="file" /> <!-- 文件上传组件 --></li>
			<li><input type="submit" name="Submit" value="上传" /> <input
							type="reset" name="Submit2" value="重置" /></li>
		</ul>
		<%
			if (request.getAttribute("result") != null) { //判断保存在request范围内的对象是否为空
				out.println("<script >alert('" + request.getAttribute("result")
						+ "');</script>"); //页面显示提示信息    	  
			}
		%>
	</form>
</body>
</html>
