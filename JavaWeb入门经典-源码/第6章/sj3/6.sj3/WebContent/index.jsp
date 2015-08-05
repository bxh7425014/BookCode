<%@ page contentType="text/html;charset=gb2312"%>
<html>
  <head>
    <title>随机产生指定位数的验证码</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
  </head>  
  <body>
    <center>
      <form action="dorandomnum.jsp">
        <table style="margin-top:200" width="300" border="1" cellpadding="0" cellspacing="0" bordercolor="black" bordercolorlight="black" bordercolordark="white">
          <tr bgcolor="lightgrey" height="25">
            <td align="center">随机产生指定位数的验证码</td>
          </tr>
          <tr height="50">
            <td align="center">
              输入验证码位数：
              <input type="text" name="number">
              <input type="submit" name="logon" value="生成">
            </td>
          </tr>
        </table>
       </form> 
     </center>
  </body>
</html>
