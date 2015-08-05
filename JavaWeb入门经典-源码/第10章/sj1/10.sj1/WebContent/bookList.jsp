<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mingrisoft.BookBean"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>显示图书列表</title>
<style type="text/css">
tr {
	height: 30px;
}
footer{
	padding:5px;
}
</style>
<script type="text/javascript">
	function CheckAll(elementsA, elementsB) {
		for (i = 0; i < elementsA.length; i++) {
			elementsA[i].checked = true;
		}
		if (elementsB.checked == false) {
			for (j = 0; j < elementsA.length; j++) {
				elementsA[j].checked = false;
			}
		}
	}
	//判断用户是否选择了要删除的记录，如果是，则提示“是否删除”；否则提示“请选择要删除的记录”
	function checkdel(delid, formname) {
		var flag = false;
		for (i = 0; i < delid.length; i++) {
			if (delid[i].checked) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			alert("请选择要删除的记录！");
			return false;
		} else {
			if (confirm("确定要删除吗？")) {
				formname.submit();
			}
		}
	}
</script>
</head>
<body>
	<div width="98%" align="center">
		<h2>所有图书信息</h2>
	</div>
	<form action="DelServlet" method="post" name="frm">
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#666666">
			<tr>
				<th bgcolor="#FFFFFF">ID</th>
				<th bgcolor="#FFFFFF">图书名称</th>
				<th bgcolor="#FFFFFF">价格</th>
				<th bgcolor="#FFFFFF">数量</th>
				<th bgcolor="#FFFFFF">作者</th>
				<th bgcolor="#FFFFFF">删除</th>
			</tr>
			<%
  // 获取图书信息集合
  List<BookBean> list = (List<BookBean>) request.getAttribute("list");
  // 判断集合是否有效
  if (list == null || list.size() < 1) {
	  out.print("<tr><td bgcolor='#FFFFFF' colspan='6'>没有任何图书信息！</td></tr>");
  } else {
	  // 遍历图书集合中的数据
	  for (BookBean book : list) {
  %>
			<tr align="center">
				<td bgcolor="#FFFFFF"><%=book.getId()%></td>
				<td bgcolor="#FFFFFF"><%=book.getName()%></td>
				<td bgcolor="#FFFFFF"><%=book.getPrice()%></td>
				<td bgcolor="#FFFFFF"><%=book.getBookCount()%></td>
				<td bgcolor="#FFFFFF"><%=book.getAuthor()%></td>
				<td bgcolor="#FFFFFF"><input name="delid" type="checkbox"
					class="noborder" value="<%=book.getId()%>"></td>
			</tr>
			<%
		}
	}
  %>
		</table>
		<footer>
			<input name="checkbox" type="checkbox" class="noborder"
				onClick="CheckAll(frm.delid,frm.checkbox)"> [全选/反选] [<a
				style="color:red;cursor:pointer;" onClick="checkdel(frm.delid,frm)">删除</a>]
			<div id="ch" style="display: none">
				<input name="delid" type="checkbox" value="0">
			</div>
			<!--层ch用于放置隐藏的checkbox控件，因为当表单中只是一个checkbox控件时，应用javascript获得其length属性值为undefine-->
		</footer>
	</form>
</body>
</html>