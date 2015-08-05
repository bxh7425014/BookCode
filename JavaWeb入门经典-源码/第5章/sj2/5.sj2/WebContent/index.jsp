<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/style.css" type="text/css" rel="stylesheet">
<title>防止表单在网站外部提交</title>
</head>

<body>
<form name="form1" action="dealwith.jsp" method="post">
  <div align="center">
    <table width="362" height="252" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="156" valign="top" background="images/00.jpg">
		<table width="323" height="171" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="43" colspan="2">&nbsp;</td>
          </tr>
          <tr>
            <td width="86" height="77" valign="bottom">　　 用户名：</td>
            <td width="237" valign="bottom"><div align="left">
              <input type="text" name="name">
            </div></td>
          </tr>
          <tr>
            <td height="23">　　 密　码：</td>
            <td height="23"><div align="left">
              <input type="password" name="pass">
            </div></td>
          </tr>
          <tr>
            <td height="27" colspan="2">              　　　　　　
              <div align="center">
                  <input type="submit" name="action2" value="提交">　
                　
                  <input type="reset" name="Submit" value="重置">
              </div></td>
          </tr>
        </table></td>
      </tr>
    </table>
  </div>
</form>
</body>
</html>
