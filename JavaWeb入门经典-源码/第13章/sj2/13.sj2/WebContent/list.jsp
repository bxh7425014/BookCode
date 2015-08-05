<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>学生信息</title>
		<style type="text/css">
    	#div1{
    		font-size:12px;
    		width:300px;
    		padding: 3px;
    		border: solid 1px;
    		background-color:#EEEEEE;
    	}
    	ul{
    		list-style:none;
    	}
    </style>
	</head>

	<body>
		<div>
	    	<c:forEach items="${list}" var="s">
		    	<div id="div1">
		    		<ul>
		    			<li>学号：${s.id}</li>
		    			<li>姓名：${s.name}</li>
		    			<li>年龄：${s.age}</li>
		    			<li>性别：${s.sex}</li>
		    		</ul>
		    	</div>
		    </c:forEach>
	    </div>
	</body>
</html>
