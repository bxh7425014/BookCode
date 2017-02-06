package org.lxh.xml.dom ;
import java.io.* ;
import org.w3c.dom.* ;
import javax.xml.parsers.* ;
public class DOMDemo02 {
	public static void main(String args[]) throws Exception {
		// 取得DocumentBuilderFactory类的对象
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
		// 取得DocumentBuilder类的对象
		DocumentBuilder build = factory.newDocumentBuilder()  ;
		Document doc = build.parse(new File("D:" + File.separator + "dom_demo_01.xml")) ;
		// 得到所有的linkman节点
		NodeList nl = doc.getElementsByTagName("linkman") ;
		for(int x=0;x<nl.getLength();x++){
			Element e = (Element) nl.item(x) ;	// 取出每一个元素
			System.out.println("姓名：" + e.getElementsByTagName("name").item(0).getFirstChild().getNodeValue()) ;
			System.out.println("邮箱：" + e.getElementsByTagName("email").item(0).getFirstChild().getNodeValue()) ;
		}
	}
}