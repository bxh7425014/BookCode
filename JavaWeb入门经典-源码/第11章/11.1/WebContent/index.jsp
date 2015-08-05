<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>应用Servlet实现文件上传</title>
</head>
<body>
<form action="UploadServlet" enctype="multipart/form-data" method ="post" >
    选择文件<input type="file" name="file1" id= "file1"/>   
    <input type="submit" name="upload" value="上传" />
 </form>
</body>
</html>