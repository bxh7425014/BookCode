package org.lxh.xml.dom ;
import java.io.* ;
import org.w3c.dom.* ;
import javax.xml.parsers.* ;
import javax.xml.transform.* ;
import javax.xml.transform.dom.* ;
import javax.xml.transform.stream.* ;

public class DOMDemo03 {
	public static void main(String args[]) throws Exception {
		// 取得DocumentBuilderFactory类的对象
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
		// 取得DocumentBuilder类的对象
		DocumentBuilder build = factory.newDocumentBuilder()  ;
		Document doc = build.newDocument() ;	// 创建一个新的XML文档
		Element addresslist = doc.createElement("addresslist") ;
		Element linkman = doc.createElement("linkman") ;
		Element name = doc.createElement("name") ;
		Element email = doc.createElement("email") ;
		// 设置节点内容
		name.appendChild(doc.createTextNode("李兴华")) ;
		email.appendChild(doc.createTextNode("mldnqa@163.com")) ;
		// 该设置各个节点的关系
		linkman.appendChild(name) ;	// name是linkeman的子节点
		linkman.appendChild(email) ;	// email是linkman的子节点
		addresslist.appendChild(linkman) ;
		doc.appendChild(addresslist) ;
		TransformerFactory tf = TransformerFactory.newInstance() ;
		Transformer t = tf.newTransformer() ;
		t.setOutputProperty(OutputKeys.ENCODING, "GBK")  ;	// 处理中文的
		DOMSource source = new DOMSource(doc) ;	// 准备输出文档
		StreamResult result = new StreamResult(new File("d:"+File.separator+"output.xml")) ;
		t.transform(source,result) ;
	}
}