package org.lxh.xml.dom4j ;
import java.io.* ;
import org.dom4j.* ;
import org.dom4j.io.* ;
public class DOM4JWriter {
	public static void main(String args[]) throws Exception {
		Document doc = DocumentHelper.createDocument() ;
		Element addresslist = doc.addElement("addresslist") ;	// 现在定义一个根节点
		Element linkman = addresslist.addElement("linkman") ;
		Element name = linkman.addElement("name") ;
		Element email = linkman.addElement("email") ;
		name.setText("李兴华") ;
		email.setText("email") ;
		OutputFormat format = OutputFormat.createPrettyPrint() ;
		format.setEncoding("GBK") ;

		XMLWriter writer = new XMLWriter(new FileOutputStream(new File("d:" + File.separator + "output.xml")),format) ;
		writer.write(doc) ;	// 进行输出
		writer.close() ;
	}
}