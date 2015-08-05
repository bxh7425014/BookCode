<%@ page import="java.util.*" pageEncoding="UTF-8"%>
<%
application.setAttribute("number",0); 
Enumeration enema=application.getAttributeNames();//获取application范围内的全部属性
out.print("<pre>");
while(enema.hasMoreElements()){
	String name=(String)enema.nextElement();		//获取属性名
	Object value=application.getAttribute(name);	//获取属性值
	out.print(name+"：");						//输出属性名
	out.println(value);						//输出属性值
}
out.print("</pre>");
%>
