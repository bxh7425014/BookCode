<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML>
<html>
<head>
<title>通过Commons-FileUpload实现文件批量上传</title>

<style type="text/css">
.div {
	position: absolute;
	left: 410px;
	top: 100px;
}
</style>

<script type="text/javascript">
	function addMoreRow() {//添加表格的行
		var perent = event.srcElement.parentNode.parentNode; //获取触发事件的上两级元素 
		var oTable = perent.parentNode.parentNode; //获取表格元素
		oNewRow = oTable.insertRow(); //表格中添加一行
		for (i = 0; i < perent.cells.length; i++) { //循环添加触发事件上级元素拥有的子元素
			oNewRow.insertCell().innerHTML = perent.cells[i].innerHTML;
		}
		perent.all("delBtn").disabled = false; //将父级元素中的“删除”按钮设置为可选状态”
		oNewRow.all("delBtn").disabled = false; //将新添加的行中的“删除”按钮设置为可选状态
	}
	function deleteMoreRow() {//删除表格的行
		var perent = event.srcElement.parentNode.parentNode; //获取触发事件的上两级元素 
		var table = perent.parentNode.parentNode; //获取表格元素
		if (table.rows.length > 1) //如果当前表格的指定行数大于1
		{
			table.deleteRow(perent.rowIndex); //删除当前行
			if (table.rows.length == 1) //如果当前表格行数为1	
			{
				table.all("delBtn").disabled = true; //将删除按钮设置为不可选
			}
		}
	}
</script>
</head>
<body topmargin="10">
<div style="background-image:url(images/fileUpload.jpg);width:400px;height:325px;;padding-top:5px;padding-left:165px;">
		<div style="width: 350px; height: 200px; overflow: auto;margin-top:100px;margin-left:20px;">
					<form action="FileUploadServlet" name="form1" method="post"
						enctype="multipart/form-data">
						<table>
							<tr>
								<td nowrap="nowrap">文件</td>
								<td><input type="file" name="filePath" size="15"
									onKeyDown="DisabledKeyInput()" /></td>
								<td><input type="button" name="insertButton" value="添加"
									onclick="addMoreRow()"></td>
								<td nowrap="nowrap"><input type="button" name="delBtn"
									value="删除" disabled="disabled" onClick="deleteMoreRow()">
								</td>
							</tr>
						</table>
						<table>
							<tr align="center">
								<td align="center"><input type="submit" name="fileUpload"
									value="上传"> <input type="reset" name="cancel"
									value="取消"></td>
								<%
									String result = (String) request.getAttribute("result");
									if (result != null) {
										out.println("<script >alert('" + result + "');</script>");
									}
								%>
							</tr>
						</table>
					</form>
				</div>
              </div>
</body>
</html>