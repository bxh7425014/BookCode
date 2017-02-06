package org.lxh.xml.dom ;
import java.io.* ;
import org.w3c.dom.* ;
import javax.xml.parsers.* ;
public class DOMDemo {
	public static void main(String args[]) throws Exception {
		// 取得DocumentBuilderFactory类的对象
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
		// 取得DocumentBuilder类的对象
		DocumentBuilder build = factory.newDocumentBuilder()  ;
		Document doc = build.parse(new File("D:" + File.separator + "dom_demo_02.xml")) ;
		NodeList nl = doc.getElementsByTagName("name") ;
		// 输出节点
		System.out.println("姓名：" + nl.item(0).getFirstChild().getNodeValue()) ;
	}
}