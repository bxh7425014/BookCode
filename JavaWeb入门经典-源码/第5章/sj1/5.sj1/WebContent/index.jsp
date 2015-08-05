<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>用户注册</title>
    <meta charset="UTF-8">
    <style type="text/css">
    td{
    	padding:5px;
    	font-size:12px;
    }
    </style>
      <script type="text/JavaScript">
function fun(){
var a="abcdefghijklmnopqrstuvwyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
var b=Math.floor(Math.random()*100%61+1);
var c=Math.floor(Math.random()*100%61+1);
var d=Math.floor(Math.random()*100%61+1);
var e=Math.floor(Math.random()*100%61+1);
var f=Math.floor(Math.random()*100%61+1);
var g=Math.floor(Math.random()*100%61+1);
var real=a.charAt(b)+a.charAt(c)+a.charAt(d)+a.charAt(e)+a.charAt(f)+a.charAt(g);
document.form1.textfield5.value=real;
}
function fun1(){
  var real=document.form1.textfield5.value;
  var yonghuming=document.form1.textfield.value;
  var mima=document.form1.textfield2.value;
  var mima2=document.form1.textfield3.value;
  var youxiang=document.form1.textfield4.value
  var real2=document.form1.textfield6.value;
 if(yonghuming=="")
  {
    alert("用户名不能为空");
	return false;
  }
 if(yonghuming.indexOf("*")!=-1)
 {
   alert("用户名不能包含*");
   return false;
 }
 if(mima=="")
 {
   alert("密码不能为空");
   return false;
 }
 if((mima.length)<6)
 {
  alert("密码长度小于6");  
  return false;
 }
 if((mima.length)>15)
 {
  alert("密码长度大于15");  
  return false;
 }
 if(mima!=mima2)
 {
    alert("两次密码不一致");  
	return false;
 }
 if(youxiang.indexOf("@")==-1)
 {
   alert("邮箱格式不正确");  
	return false;
 }
 if(real!=real2)
 {
   alert("验证码不一致");
   return false;
 }
}
</script>
  </head>


<body onLoad="fun()">
<form name="form1" action="show.jsp"> 
<div align="center">
  <p class="style1">用户注册信息表</p>
  <table width="400" border="1" cellpadding="0" cellspacing="0" bordercolor="#FFFFFF" bordercolorlight="#333333" bordercolordark="#FFFFFF">
    <tr>
      <td width="117" align="right">用户名</td>
      <td width="267" align="left">
        <input type="text" name="textfield">
        <span class="style2">*</span></td>
    </tr>
    <tr>
      <td align="right">密码</td>
      <td align="left">
        <input type="password" name="textfield2">
        <span class="style2">*(6-15)位</span></td>
    </tr>
    <tr>
      <td align="right">确认密码</td>
      <td align="left">
        <input type="password" name="textfield3">
        <span class="style2">*</span></td>
    </tr>
    <tr>
      <td align="right">性别</td>
      <td align="left">
        <select name="select">
          <option>男</option>
          <option>女</option>
          <option selected>--</option>
        </select>
      </td>
    </tr>
    <tr>
      <td height="98" align="right">爱好</td>
      <td align="left">
            <p>
            <input type="checkbox" name="like" value="上网">
          上网
            <input type="checkbox" name="like" value="旅游">
            旅游
            <input type="checkbox" name="like" value="交友">
            交友
            <input type="checkbox" name="like" value="逛街">
          逛街            </p>
            <p>
              <input type="checkbox" name="like" value="看书">
              看书
              <input type="checkbox" name="like" value="书法">
              书法
              <input type="checkbox" name="like" value="游戏">
              游戏
              <input type="checkbox" name="like" value="球类">
球类</p>
      </td>
    </tr>
    <tr>
      <td align="right">邮箱</td>
      <td align="left"><input type="text" name="textfield4"></td>
    </tr>
    <tr>
      <td align="right">验证码</td>
      <td align="left"><input name="textfield5" type="text" value="GTR-400"></td>
    </tr>
    <tr>
      <td align="right">确认验证码</td>
      <td align="left"><input type="text" name="textfield6"></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="submit" name="Submit3" value="注册" onClick="fun1()">
        <input type="reset" name="Submit4" value="重置">
      </td>
      </tr>
  </table>
 </div>
</form>
  </body>
</html>
