package irdc.ex08_13; 

/* import相關class */
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes; 
import org.xml.sax.SAXException; 
import org.xml.sax.helpers.DefaultHandler; 

public class MyHandler extends DefaultHandler
{ 
  /* 變數宣告 */
  private boolean in_item = false;
  private boolean in_title = false;
  private boolean in_link = false;
  private boolean in_desc = false;
  private boolean in_date = false;
  private boolean in_mainTitle = false;
  private List<News> li;
  private News news;
  private String title="";
  private StringBuffer buf=new StringBuffer();

  /* 將轉換成List<News>的XML資料回傳 */
  public List<News> getParsedData()
  { 
    return li; 
  }
  /* 將解析出的RSS title回傳 */
  public String getRssTitle()
  { 
    return title; 
  }
  /* XML文件開始解析時呼叫此method */
  @Override 
  public void startDocument() throws SAXException
  { 
    li = new ArrayList<News>(); 
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
    if (localName.equals("item"))
    { 
      this.in_item = true;
      /* 解析到item的開頭時new一個News物件 */
      news=new News();
    }
    else if (localName.equals("title"))
    { 
      if(this.in_item)
      {
        this.in_title = true;
      }
      else
      {
        this.in_mainTitle = true;
      }
    }
    else if (localName.equals("link"))
    { 
      if(this.in_item)
      {
        this.in_link = true;
      }
    }
    else if (localName.equals("description"))
    { 
      if(this.in_item)
      {
        this.in_desc = true;
      }
    }
    else if (localName.equals("pubDate"))
    { 
      if(this.in_item)
      {
        this.in_date = true;
      }
    } 
  }
  /* 解析到Element的結尾時呼叫此method */
  @Override 
  public void endElement(String namespaceURI, String localName,
                         String qName) throws SAXException
  { 
    if (localName.equals("item"))
    { 
      this.in_item = false;
      /* 解析到item的結尾時將News物件寫入List中 */
      li.add(news);
    }
    else if (localName.equals("title"))
    { 
      if(this.in_item)
      {
        /* 設定News物件的title */
        news.setTitle(buf.toString().trim());
        buf.setLength(0);
        this.in_title = false;
      }
      else
      {
        /* 設定RSS的title */
        title=buf.toString().trim();
        buf.setLength(0);
        this.in_mainTitle = false;
      }
    }
    else if (localName.equals("link"))
    { 
      if(this.in_item)
      {
        /* 設定News物件的link */
        news.setLink(buf.toString().trim());
        buf.setLength(0);
        this.in_link = false;
      }
    }
    else if (localName.equals("description"))
    { 
      if(in_item)
      {
        /* 設定News物件的description */
        news.setDesc(buf.toString().trim());
        buf.setLength(0);
        this.in_desc = false;
      }
    }
    else if (localName.equals("pubDate"))
    { 
      if(in_item)
      {
        /* 設定News物件的pubDate */
        news.setDate(buf.toString().trim());
        buf.setLength(0);
        this.in_date = false;
      }
    } 
  } 
  /* 取得Element的開頭結尾中間夾的字串 */
  @Override 
  public void characters(char ch[], int start, int length)
  { 
    if(this.in_item||this.in_mainTitle)
    {
      /* 將char[]加入StringBuffer */
      buf.append(ch,start,length);
    }
  } 
}