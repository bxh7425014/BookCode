<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
  <head>
    <title>多级级联下拉列表</title>
<meta charset="UTF-8"> 
<script type="text/javascript" src="JS/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="CSS/style.css">
<script language="javascript">
//获取省份和直辖市
function getProvince(){
	$.ajax({
		url:"ZoneServlet?action=getProvince&nocache="+new Date().getTime(),
		//设置请求成功时执行的回调函数
		success : function(data) {
			provinceArr=data.split(",");	//将获取的省份名称字符串分隔为数组
			for(i=0;i<provinceArr.length;i++){		//通过循环将数组中的省份名称添加到下拉列表中
				$("#province").append("<option value='"+provinceArr[i]+"'>"+provinceArr[i]+"</option>");
			}
			if(provinceArr[0]!=""){
				getCity(provinceArr[0]);	//获取地级市
			}			
		}
	});
}
$(document).ready(function(){
	 getProvince();		//获取省份和直辖市
});
/*************************************************************************************************************/
//获取地级市
function getCity(selProvince){
	$.ajax({
		url:"ZoneServlet?action=getCity&parProvince="+encodeURIComponent(selProvince)+"&nocache="+new Date().getTime(),
		//设置请求成功时执行的回调函数
		success : function(data) {
			cityArr=data.split(",");	//将获取的市县名称字符串分隔为数组
			$("#city").empty();	//清空下拉列表
			for(i=0;i<cityArr.length;i++){		//通过循环将数组中的地级市名称添加到下拉列表中
				$("#city").append("<option value='"+cityArr[i]+"'>"+cityArr[i]+"</option>");
			}
			if(cityArr[0]!=""){
				getArea($("#province").val(),cityArr[0]);	//获取县/县级市/区
			}
		}
	});
}
/****************************************************************************/
//获取县、县级市或区
function getArea(selProvince,selCity){
	$.ajax({
		url:"ZoneServlet?action=getArea&parProvince="+encodeURIComponent(selProvince)+"&parCity="+encodeURIComponent(selCity)+"&nocache="+new Date().getTime(),
		//设置请求成功时执行的回调函数
		success : function(data) {
			areaArr=data.split(",");	//将获取的市县名称字符串分隔为数组
			$("#area").empty();	//清空下拉列表
			for(i=0;i<areaArr.length;i++){		//通过循环将数组中的县、县级市和区名称添加到下拉列表中
				$("#area").append("<option value='"+areaArr[i]+"'>"+areaArr[i]+"</option>");
			}
		}
	});
}
</script>
  </head>
  
<body style="background-color:#eef2eb;">
<div id="box">
	<div id="top"></div>
	<div id="center" style="border:1px solid #91D564;width:730px; height:350px; margin-left:50px; margin-bottom:15px;">
		<div id="title" style="height:37px;background-image:url(images/zhuce_05.jpg); background-repeat:repeat-x"><img src="images/zhuce_04.jpg" width="124" height="37"></div>
		<div id="content" style="margin-left:130px;">
		  <form method="post" name="form1" id="form1">
		  <ul>
			<li>用 &nbsp;户 &nbsp;名：
			  <input name="username" type="text" id="username">
			*</li>
			<li>密　　码：
			  <input name="pwd" type="password" id="pwd">
			*</li>
			<li>确认密码：
			  <input name="repwd" type="password" id="repwd">
			*</li>
			<li>E-mail地址：
			  <input name="email" type="text" id="email" size="35">
			*</li>
			<li>
		  居 &nbsp;住 &nbsp;地：
			  <select name="province" id="province" onChange="getCity(this.value)">
			  </select>
			  -
			  <select name="city" id="city"  onChange="getArea($('#province').val(),this.value)">
			  </select>
			  -
			  <select name="area" id="area">
			  </select>
		    </li>
			  <li style="margin-top:20px;">以下两个选项，只要有任何一个没有输入，将不可以通过答案问题重新设置密码。</li>
			  <li>密码提示问题：
				<input name="question" type="text" id="question" size="35">
			  </li>	
			  <li>提示问题答案：
				<input name="answer" type="text" id="answer" size="35">
			  </li>	
			  <li style="padding-left:30px;">
			    <input type="image" style="border:none;" name="imageField" src="images/zhuce_09.jpg">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="image" style="border:none;" name="imageField2" src="images/zhuce_11.jpg" onClick="this.form.reset();return false;">
&nbsp;			  </li>  
			</ul>	  
			  
	      </form>
	  </div>
  	</div>
	<div id="bottom"></div>
</div>
</body>
</html>
