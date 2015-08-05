<%@ page contentType="text/html;charset=gb2312"%>
<jsp:useBean id="myrandom" class="com.wgh.random.RanDom"/>
<%
  String strnum=request.getParameter("number");
  if(strnum==null)
	  strnum="0";
  int num=0;
  try{
	  num=Integer.parseInt(strnum);
  }catch(Exception e){num=0;}
  myrandom.setNumber(num);
  myrandom.makeChecknum();
%>
<html>
  <head>
    <title>随机产生指定位数的验证码</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
  </head>
  <body>
   <center>
       <table style="margin-top:200" width="250" border="1" cellpadding="0" cellspacing="0" bordercolor="black" bordercolorlight="black" bordercolordark="white">
         <tr bgcolor="lightgrey" height="30">
            <td align="center">生成的验证码</td>
         </tr>
         <tr height="50">
            <td align="center">
              验证码的位数：<%=myrandom.getNumber() %>
              <br>
              生成的验证码：<%=myrandom.getChecknum()%>
            </td>
         </tr>
       </table>
       <a href="index.jsp">[返回]</a>
   </center>
  </body>
</html>