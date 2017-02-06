package org.lxh.xml.jdom ;
import java.io.* ;
import org.jdom.* ;
import org.jdom.output.* ;
public class WriteXML {
	public static void main(String args[]) throws Exception {
		Element addresslist = new Element("addresslist") ;
		Element linkman = new Element("linkman") ;
		Element name = new Element("name") ;
		Element email = new Element("email") ;
		Attribute id = new Attribute("id","lxh") ;
		Document doc = new Document(addresslist) ;	// 定义Document对象
		name.setText("李兴华") ;
		name.setAttribute(id) ;	// 将属性设置到元素之中
		email.setText("mldnqa@163.com") ;
		linkman.addContent(name) ;	// 设置关系
		linkman.addContent(email) ;
		addresslist.addContent(linkman) ;
		XMLOutputter out = new XMLOutputter() ;
		out.setFormat(out.getFormat().setEncoding("GBK")) ;	// 表示的是设置编码
		out.output(doc,new FileOutputStream(new File("D:" + File.separator + "address.xml"))) ;
	}
}