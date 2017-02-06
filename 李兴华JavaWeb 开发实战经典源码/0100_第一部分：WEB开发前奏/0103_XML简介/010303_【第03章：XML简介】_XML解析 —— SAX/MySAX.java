package org.lxh.xml.sax ;
import org.xml.sax.* ;
import org.xml.sax.helpers.* ;
public class MySAX extends DefaultHandler {
	public void startDocument()
                   throws SAXException{
		System.out.println("<?xml version=\"1.0\" encoding=\"GBK\">") ;
	}
	public void startElement(String uri,
                         String localName,
                         String qName,
                         Attributes attributes)
                  throws SAXException{
		System.out.print("<") ;
		System.out.print(qName) ;
		if(attributes != null){	// 如果存在了属性
			for(int x=0;x<attributes.getLength();x++){
				System.out.print(" " + attributes.getQName(x) + "=\"" + attributes.getValue(x) + "\"") ;
			}
		}
		System.out.print(">") ;
	}
	public void endElement(String uri,
                       String localName,
                       String qName)
                throws SAXException{
		System.out.print("<") ;
		System.out.print(qName) ;
		System.out.print(">") ;
	}
	public void characters(char[] ch,
                       int start,
                       int length)
                throws SAXException{
		System.out.print(new String(ch,start,length)) ;
	}
	public void endDocument()
                 throws SAXException{
		System.out.println("文档结束。。。") ;
	}
}