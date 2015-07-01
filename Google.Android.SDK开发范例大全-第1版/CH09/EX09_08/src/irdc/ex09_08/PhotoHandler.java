package irdc.ex09_08; 

/* import相關class */
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

public class PhotoHandler extends DefaultHandler
{
  private int thumbnailNum=0;
  private List<String> list1;
  private List<String> list2;
  
  /* 回傳解析度72的相片資訊 */
  public List<String> getSmallPhoto()
  { 
    return list1;
  }
  /* 回傳解析度288的相片資訊 */
  public List<String> getBigPhoto()
  { 
    return list2;
  }

  /* XML文件開始解析時呼叫此method */
  @Override 
  public void startDocument() throws SAXException
  { 
    list1 = new ArrayList<String>();
    list2 = new ArrayList<String>();
  } 
  /* XML文件結束解析時呼叫此method */
  @Override 
  public void endDocument() throws SAXException
  {
  }

  /* 解析到Element的開頭時呼叫此method */
  @Override 
  public void startElement(String namespaceURI, String localName, 
               String qName, Attributes atts) throws SAXException
  { 
    if (localName.equals("thumbnail"))
    { 
      if(thumbnailNum==0)
      {
        /* 將第一筆url(解析度72的相片連結)寫入list1 */
        list1.add(atts.getValue("url"));
      }
      else if(thumbnailNum==2)
      {
        /* 將第三筆url(解析度288的相片連結)寫入list2 */
        list2.add(atts.getValue("url"));
      }
      thumbnailNum++;
    }
  }
  /* 解析到Element的結尾時呼叫此method */
  @Override 
  public void endElement(String namespaceURI, String localName,
                         String qName) throws SAXException
  { 
    if (localName.equals("group"))
    { 
      /* 解析到group的結尾時將thumbnailNum重設為0 */
      thumbnailNum=0;
    }
  } 
}