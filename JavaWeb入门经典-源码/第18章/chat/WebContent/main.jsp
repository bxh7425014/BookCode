<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" %>
<%@ include file="safe.jsp"%>
<% request.setCharacterEncoding("UTF-8"); 
int maxTime=50*60*1000;		//设置5分钟不说话，将其踢出聊天室
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>聊天室</title>
<link href="CSS/style.css" rel="stylesheet">
<script language="javascript" src="JS/AjaxRequest.js"></script>
<script language="javascript">
window.setInterval("showContent();",1000); 
window.setInterval("showOnline();",10000); 
var sysBBS="<span style='font-size:14px; line-height:30px;'>欢迎光临碧蓝晴空聊天室，请遵守聊天室规则，不要使用不文明用语。</span><br><span style='line-height:22px;'>";
//此处需要加?nocache="+new Date().getTime()，否则将出现在线人员列表不更新的情况
function showOnline(){
	var loader=new net.AjaxRequest("online.jsp?nocache="+new Date().getTime(),deal_online,onerror,"GET");
}
function showContent(){
	var loader1=new net.AjaxRequest("MessagesAction?action=getMessages&nocache="+new Date().getTime(),deal_content,onerror,"GET");
}
function onerror(){
	alert("很抱歉，服务器出现错误，当前窗口将关闭！");
	window.opener=null;
	window.close();
}
function deal_online(){
	online.innerHTML=this.req.responseText;
}
function deal_content(){
	var returnValue=this.req.responseText;		//获取Ajax处理页的返回值
	var h=returnValue.replace(/\s/g,"");	//去除字符串中的Unicode空白符
	if(h=="error"){
		//alert("您的账户已经过期，请重新登录！");
		Exit();
	}else{
		content.innerHTML=sysBBS+returnValue+"</span>";
	}
}
//控制是否滚屏
function checkScrollScreen(){
	if(!form1.scrollScreen.checked){
		document.getElementById("content").style.overflow='scroll';
		//document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;
		//setTimeout('checkScrollScreen()',500);
	}else{
		document.getElementById("content").style.overflow='hidden';
		document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;	//当聊天信息超过一屏时，设置最先发送的聊天信息不显示
	}
	setTimeout('checkScrollScreen()',500);
}
window.onload=function(){
	checkScrollScreen();				//当页面载入后控制是否滚屏
	showContent();						//当页面载入后显示聊天内容
	showOnline();						//当页面载入后显示在线人员列表
}
</script>
<script language="javascript">
<!--
	function send(){	//验证聊天信息并发送
		if(form1.to.value==""){
			alert("请选择聊天对象！");return false;
		}
		if(form1.content1.value==""){
			alert("发送信息不可以为空！");form1.content1.focus();return false;
		}
		if(form1.isPrivate.checked){
			isPrivate="true";
		}else{
			isPrivate="false";
		}
		var param="from="+form1.from.value+"&face="+form1.face.value+"&color="+form1.color.value+"&to="+form1.to.value+"&content="+	form1.content1.value+"&isPrivate="+isPrivate; 
		var loader=new net.AjaxRequest("MessagesAction?action=sendMessage",deal_send,onerror,"POST",param);

	}
function deal_send(){
content.innerHTML=sysBBS+this.req.responseText+"</span>";
	if(form1.scrollScreen.checked){
		document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;	//当聊天信息超过一屏时，设置最先发送的聊天信息不显示
	}
	//重新计时
	clearTimeout(timer);
	timer = window.setTimeout("Exit()",<%=maxTime%>); 
	form1.content1.value="";
}	
	function Exit(){
		window.location.href="MessagesAction?action=exitRoom";
		alert("欢迎您下次光临！");
	}
-->
</script>
<script language="javascript">
function set(selectPerson){	//自动添加聊天对象
	if(selectPerson!="<%=session.getAttribute("username")%>"){
		if(form1.isPrivate.checked && selectPerson=="所有人"){
			alert("请选择私聊对象！");
		}else{
			form1.to.value=selectPerson;
		}
	}else{
		alert("请重新选择聊天对象！");
	}
}
function checkIsPrivate(){
	if(form1.isPrivate.checked){
		if(form1.to.value=="所有人"){	
			alert("请选择私聊对象！");
			form1.to.value="";
		}
		
	}	
}
</script>
<script language="jscript"> 
timer = window.setTimeout("Exit()",<%=maxTime%>); 		//用于当用户长时间不说话时，将其踢出聊天室
window.onbeforeunload=function(){   //当用户单击浏览器中的“关闭”按钮时，执行退出操作
	ver = navigator.appVersion; //浏览器的版本
	bType =navigator.appName;//浏览器的类型
	vNumber=parseFloat(ver.substring(ver.indexOf("MSIE")+5,ver.lastIndexOf("Windows")));
	if(bType=="Microsoft Internet Explorer"){
		if(vNumber>6){//IE6以上浏览器
			vOffset=document.body.offsetWidth-document.body.scrollWidth;
			if(event.clientY<0 && event.clientX>document.body.scrollWidth-vOffset-20){  
				 	Exit();		//执行退出操作
			}else{//IE6及以下浏览器
				if(event.clientY<0 && event.clientX>document.body.scrollWidth){  
				 	Exit();		//执行退出操作
				}   
			}
		}
	}
}     
</script> 
</head>
<body>
<table width="778" height="152" border="0" align="center" cellpadding="0" cellspacing="0" background="images/top.jpg">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="778" height="276" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="165" valign="top" bgcolor="#F7FDED" id="online" style="padding:5px">在线人员列表</td>
    <td width="613"  height="200px" valign="top" bgcolor="#FFFFFF" style="padding:5px; ">
	<div style="height:290px; overflow:hidden" id="content">聊天内容</div>
	</td>

  </tr>
</table>
<table width="778" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#D6D3CE" bgcolor="#D1E9AA">
<form action="" name="form1" method="post" >
  <tr>
    <td height="30" align="left">&nbsp;</td>
    <td height="37" align="left"><input name="from" type="hidden" value="<%=session.getAttribute("username")%>">[<%=session.getAttribute("username")%> ]对
      <input name="to" type="text" value="" size="35" readonly="readonly">
表情
<select name="face" class="wenbenkuang">
  <option  value="无表情的">无表情的</option>
  <option value="微笑着" selected>微笑着</option>
  <option value="笑呵呵地">笑呵呵地</option>
  <option value="热情的">热情的</option>
  <option value="温柔的">温柔的</option>
  <option value="红着脸">红着脸</option>
  <option value="幸福的">幸福的</option>
  <option value="嘟着嘴">嘟着嘴</option>
  <option value="热泪盈眶的">热泪盈眶的</option>
  <option value="依依不舍的">依依不舍的</option>
  <option value="得意的">得意的</option>
  <option value="神秘兮兮的">神秘兮兮的</option>
  <option value="恶狠狠的">恶狠狠的</option>
  <option value="大声的">大声的</option>
  <option value="生气的">生气的</option>
  <option value="幸灾乐祸的">幸灾乐祸的</option>
  <option value="同情的">同情的</option>
  <option value="遗憾的">遗憾的</option>
  <option value="正义凛然的">正义凛然的</option>
  <option value="严肃的">严肃的</option>
  <option value="慢条斯理的">慢条斯理的</option>
  <option value="无精打采的">无精打采的</option>
</select>
说： 悄悄话
<input name="isPrivate" type="checkbox" class="noborder" id="isPrivate" value="true" onClick="checkIsPrivate()">
滚屏
<input name="scrollScreen" type="checkbox" class="noborder" id="scrollScreen" onClick="checkScrollScreen()" value="1" checked></td>
    <td width="163" align="left"> &nbsp;&nbsp;字体颜色：
      <select name="color" size="1" class="wenbenkuang" id="select">
        <option selected>默认颜色</option>
        <option style="color:#FF0000" value="FF0000">红色热情</option>
        <option style="color:#0000FF" value="0000ff">蓝色开朗</option>
        <option style="color:#ff00ff" value="ff00ff">桃色浪漫</option>
        <option style="color:#009900" value="009900">绿色青春</option>
        <option style="color:#009999" value="009999">青色清爽</option>
        <option style="color:#990099" value="990099">紫色拘谨</option>
        <option style="color:#990000" value="990000">暗夜兴奋</option>
        <option style="color:#000099" value="000099">深蓝忧郁</option>
        <option style="color:#999900" value="999900">卡其制服</option>
        <option style="color:#ff9900" value="ff9900">镏金岁月</option>
        <option style="color:#0099ff" value="0099ff">湖波荡漾</option>
        <option style="color:#9900ff" value="9900ff">发亮蓝紫</option>
        <option style="color:#ff0099" value="ff0099">爱的暗示</option>
        <option style="color:#006600" value="006600">墨绿深沉</option>
        <option style="color:#999999" value="999999">烟雨蒙蒙</option>
      </select></td>
    <td width="19" align="left">&nbsp;</td>
  </tr>
  <tr>
    <td width="21" height="30" align="left">&nbsp;</td>
    <td width="575" align="left"><input name="content1" type="text" size="70" onKeyDown="if(event.keyCode==13 && event.ctrlKey){send();}">
      <input name="Submit2" type="button" class="btn_blank" value="发送" onClick="send()"></td>
    <td align="right"><input name="button_exit" type="button" class="btn_orange" value="退出聊天室" onClick="Exit()"></td>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td height="30" align="left">&nbsp;</td>
    <td colspan="2" align="center" class="word_dark">&nbsp;All CopyRights &copy; reserved 2012 吉林省明日科技有限公司</td>
    <td align="center">&nbsp;</td>
  </tr>
</form>
</table>
</body>
</html>
