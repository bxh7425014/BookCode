<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>插入用户登录日志信息</title> 
</head>

<body>

<form name="form1" method="post" action="Insert_Data">

  <table width="454" height="339" border="0" align="center" cellpadding="0" cellspacing="0" background="images/1.gif">
    <tr>
      <td height="233" colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td width="148" height="85" rowspan="4">&nbsp;</td>
      <td width="83" height="19"><div align="left">用户名：</div></td>
      <td width="201"><input name="tname" type="text" id="tname" style="width:180px"></td>
    </tr>
    <tr>
      <td height="19"><div align="left">密　码： </div></td>
      <td height="19"><input name="tpassword" type="password" id="tpassword" style="width:180px"></td>
    </tr>
    <tr>
      <td height="19" colspan="2"><div align="center">

  <input type="submit" name="Submit" value="登录">
&nbsp;&nbsp;  <input type="reset" name="Submit2" value="重置">
      </div></td>
    </tr>
    <tr>
      <td height="27" colspan="2">&nbsp;</td>
    </tr>
  </table>
</form>
</body>
</html>
