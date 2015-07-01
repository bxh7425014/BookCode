package irdc.ex09_08; 

/* import相關class */
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

public class AlbumHandler extends DefaultHandler
{
  private String gphotoURI="";
  private String mediaURI="";
  private boolean in_entry = false;
  private boolean in_title = false;
  private boolean in_id = false;
  private List<String[]> li;
  private String[] s;
  private StringBuffer buf=new StringBuffer();

  /* 將轉換成List的XML資料回傳 */
  public List<String[]> getParsedData()
  { 
    return li; 
  }

  /* XML文件開始解析時呼叫此method */
  @Override 
  public void startDocument() throws SAXException
  { 
    li = new ArrayList<String[]>(); 
  }
  
  /* XML文件結束解析時呼叫此method */
  @Override 
  public void endDocument() throws SAXException
  {
  }
  
  /* 取得prefix的method */
  @Override 
  public void startPrefixMapping(String prefix,String uri)
  {
    if(prefix.equals("gphoto"))
    {
      gphotoURI=uri;
    }
    else if(prefix.equals("media"))
    {
      mediaURI=uri;
    }
  }
  
  /* 解析到Element的開頭時呼叫此method */
  @Override 
  public void startElement(String namespaceURI, String localName, 
               String qName, Attributes atts) throws SAXException
  { 
    if (localName.equals("entry"))
    { 
      this.in_entry = true;
      /* 解析到entry的開頭時new一個String[] */
      s=new String[3];
    }
    else if (localName.equals("title"))
    { 
      if(this.in_entry)
      {
        this.in_title = true;
      }
    }
    else if (localName.equals("id"))
    { 
      if(gphotoURI.equals(namespaceURI))
      {
        this.in_id = true;  
      }
    }
    else if (localName.equals("thumbnail"))
    { 
      if(mediaURI.equals(namespaceURI))
      {
        /* 相簿網址 */
        s[1]=atts.getValue("url");
      }
    }
  }
  
  /* 解析到Element的結尾時呼叫此method */
  @Override 
  public void endElement(String namespaceURI, String localName,
                         String qName) throws SAXException
  { 
    if (localName.equals("entry"))
    { 
      this.in_entry = false;
      /* 解析到item的結尾時將String[]寫入List */
      li.add(s);
    }
    else if (localName.equals("title"))
    { 
      if(this.in_entry)
      {
        /* 相簿名稱 */
        s[2]=buf.toString().trim();
        buf.setLength(0);
        this.in_title = false;
      }
    }
    else if (localName.equals("id"))
    { 
      if(gphotoURI.equals(namespaceURI))
      {
        /* 相簿ID */
        s[0]=buf.toString().trim();
        buf.setLength(0);
        this.in_id = false;
      }
    }
  }
  
  /* 取得Element的開頭結尾中間夾的字串 */
  @Override 
  public void characters(char ch[], int start, int length)
  { 
    if(this.in_title||this.in_id)
    {
      /* 將char[]加入StringBuffer */
      buf.append(ch,start,length);
    }
  } 
}