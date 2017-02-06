package org.lxh.xml.dom4j ;
import java.io.* ;
import java.util.* ;
import org.dom4j.* ;
import org.dom4j.io.* ;
public class DOM4JReader {
	public static void main(String args[]) throws Exception {
		File file = new File("d:" + File.separator + "output.xml") ;
		SAXReader reader = new SAXReader() ;
		Document doc = reader.read(file) ;	// 读取XML文件
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement() ;	// 取得根节点
		// 现在应该根据根节点找到全部的子节点，linkman
		Iterator iter = root.elementIterator() ;
		while(iter.hasNext()){
			Element linkman = (Element) iter.next() ;
			System.out.println("姓名：" + linkman.elementText("name")) ;
			System.out.println("邮件：" + linkman.elementText("email")) ;
		}
	}
}