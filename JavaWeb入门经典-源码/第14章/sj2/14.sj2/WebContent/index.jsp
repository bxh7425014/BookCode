<%@page contentType="text/html" import="com.core.*,com.model.CommodityForm,java.util.*"
        pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajax无刷新分页</title>
<link href="CSS/style.css" rel="stylesheet">

<script type="text/javascript" src="js/request.js"></script>	
<script type="text/javascript">
var request = false;	//保存 XMLHttpRequest对象的全局变量
//Ajax请求方法 
function getData(page){ 
		var url="GoodsServlet";										//服务器地址
		var param ="page="+page+"&order=0&nocache="+new Date().getTime();	//请求参数 
		request=httpRequest("post",url,true,callbackFunc,param);	//调用请求方法 	
	
}

//Ajax回调函数
function callbackFunc(){
	if(request.readyState == 4){
		if(request.status == 200){
		   var doc = request.responseXML;
		   var pageNum = doc.getElementsByTagName("pageNum")[0].firstChild.data;
		   var maxPage= doc.getElementsByTagName("maxPage")[0].firstChild.data;
		   var prePage = doc.getElementsByTagName("prePage")[0].firstChild.data;
		   var nextPage = doc.getElementsByTagName("nextPage")[0].firstChild.data;
		   var goodses = doc.getElementsByTagName("goods");
		   var innerHTML = "";
		   if((goodses!=null)&&(goodses.length!=0)) {
		       innerHTML+="<table width='96%' border='1' cellpadding='0' cellspacing='0' bordercolor='#FFFFFF' bordercolordark='#FFFFFF' bordercolorlight='CFCFCF'>";
		       innerHTML+="<tr align='center' height='30'>";
		       innerHTML+="<td><a href =\"javascript: orderById("+(parseInt(prePage)+1)+")\">商品编号</a></td>";
		       innerHTML+="<td>商品名称</td>";
		       innerHTML+="<td>简介</td>";
		       innerHTML+="<td>单价</td></tr>";
		       for(var i=0;i<goodses.length;i++) {
		           var goods = goodses[i];
		           var goodsId =  goods.childNodes[0].firstChild.data;
		           var goodsName =  goods.childNodes[1].firstChild.data;
		           var introduce =  goods.childNodes[2].firstChild.data;
		           var price =  goods.childNodes[3].firstChild.data;
		           innerHTML += "<tr height='30'>";
		           innerHTML += "<td align='left'>&nbsp;"+goodsId+"</td>";
		           innerHTML += "<td align='left'>&nbsp;"+goodsName+"</td>";
		           innerHTML += "<td align='left'>&nbsp;"+introduce+"</td>";
		           innerHTML += "<td align='left'>&nbsp;"+price+"</td>";
		           innerHTML += "</tr>";
		       }
		       innerHTML+="<tr height='30'><td align='center' colspan='7'>";
		       innerHTML +="["+ pageNum+"/"+maxPage+"]&nbsp;&nbsp;";							
		       innerHTML += "<a href='javascript:void(0)' onClick=\"getData(1)\">[首页]</a>&nbsp;";
		       innerHTML += "<a href='javascript:void(0)' onClick=\"getData("+prePage+")\">[上一页]</a>&nbsp";
		       innerHTML += "<a href='javascript:void(0)' onClick=\"getData("+nextPage+")\">[下一页]</a>&nbsp";
		       innerHTML +="<a href='javascript:void(0)' onClick=\"getData("+maxPage+")\">[尾页]</a>";
		       innerHTML+="</td></tr>\n";
		       innerHTML+="</table>\n";
		     
		   }else {
		       innerHTML += "暂时没有任何数据";
		   }
		   document.getElementById("goodsList").innerHTML = innerHTML;
		   request = false;//此处必须设置XMLHttpRequest对象的变量为初始状态
   		}
   }
}
function orderById(page){
	var flag = document.getElementById("flag").value;
	alert(page);
	var url="GoodsServlet";										//服务器地址
	var param ="page="+page+"&order="+flag+"&nocache="+new Date().getTime();	//请求参数 
	request=httpRequest("post",url,true,callbackFunc,param);	//调用请求方法 	
	
	if(flag=="0"){
		document.getElementById("flag").value="1";
		return;
	}
	else if(flag=="1"){
		document.getElementById("flag").value="0";
		return;
	}	
}
window.onload=function(){
	getData(1);
}
</script>
    </head>
    <body>
<jsp:include page="navigation.jsp"/>
<input type="hidden" name="flag" id="flag" value="0"/>
<table width="788" height="236"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="199" height="182" valign="top" bgcolor="#FFFFFF"><img src="images/left.gif" width="199" height="474"></td>
    <td valign="top" bgcolor="#FFFFFF"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="45" valign="top" background="images/sub_newgoods.gif">&nbsp;</td>
        </tr>
        <tr>
          <td height="10" valign="top">&nbsp;</td>
        </tr>
        <tr>
          <td height="94" valign="top" align="center">
          
        <span id="goodsList"></span>
		  </td>
        </tr>
    </table></td>
  </tr>
</table>
 <jsp:include page="copyright.jsp"/>
    </body>
</html>
