package org.lxh.xml.jdom ;
import java.io.* ;
import java.util.* ;
import org.jdom.* ;
import org.jdom.input.* ;
public class ReadXML {
	public static void main(String args[]) throws Exception {
		SAXBuilder builder = new SAXBuilder() ;
		Document read_doc = builder.build(new File("D:" + File.separator + "address.xml")) ;
		Element root = read_doc.getRootElement() ;	// 取得根
		List list = root.getChildren("linkman") ;	// 得到所有的linkman
		for(int x=0;x<list.size();x++){
			Element e = (Element) list.get(x) ;
			String name = e.getChildText("name") ;	// 得到name子节点的内容
			String id = e.getChild("name").getAttribute("id").getValue() ;
			String email = e.getChildText("email") ;
			System.out.println("-------------- 联系人 -------------") ;
			System.out.println("姓名：" + name + "，编号：" + id) ;
			System.out.println("EMAIL：" + email) ;
			System.out.println("-----------------------------------") ;
			System.out.println() ;
		}
	}
}