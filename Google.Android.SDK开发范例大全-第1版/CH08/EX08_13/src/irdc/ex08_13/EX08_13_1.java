package irdc.ex08_13;

/* import相關class */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class EX08_13_1 extends ListActivity
{
  /* 變數宣告 */
  private TextView mText;
  private String title="";
  private List<News> li=new ArrayList<News>();
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /* 設定layout為newslist.xml */
    setContentView(R.layout.newslist);
    
    mText=(TextView) findViewById(R.id.myText);
    /* 取得Intent中的Bundle物件 */
    Intent intent=this.getIntent();
    Bundle bunde = intent.getExtras();
    /* 取得Bundle物件中的資料 */
    String path = bunde.getString("path");
    /* 呼叫getRss()取得解析後的List */
    li=getRss(path);
    mText.setText(title);
    /* 設定自定義的MyAdapter */
    setListAdapter(new MyAdapter(this,li));
  }
  
  /* 設定ListItem被按下時要做的動作 */
  @Override
  protected void onListItemClick(ListView l,View v,int position,
                                 long id)
  {
    /* 取得News物件 */
    News ns=(News)li.get(position);
    /* new一個Intent物件，並指定class */
    Intent intent = new Intent();
    intent.setClass(EX08_13_1.this,EX08_13_2.class);
    /* new一個Bundle物件，並將要傳遞的資料傳入 */
    Bundle bundle = new Bundle();
    bundle.putString("title",ns.getTitle());
    bundle.putString("desc",ns.getDesc());
    bundle.putString("link",ns.getLink());
    /* 將Bundle物件assign給Intent */
    intent.putExtras(bundle);
    /* 呼叫Activity EX08_13_2 */
    startActivity(intent);
  }
  
  /* 剖析XML的method */
  private List<News> getRss(String path)
  {
    List<News> data=new ArrayList<News>();
    URL url = null;     
    try
    {  
      url = new URL(path);
      /* 產生SAXParser物件 */ 
      SAXParserFactory spf = SAXParserFactory.newInstance(); 
      SAXParser sp = spf.newSAXParser(); 
      /* 產生XMLReader物件 */ 
      XMLReader xr = sp.getXMLReader(); 
      /* 設定自定義的MyHandler給XMLReader */ 
      MyHandler myExampleHandler = new MyHandler(); 
      xr.setContentHandler(myExampleHandler);     
      /* 解析XML */
      xr.parse(new InputSource(url.openStream()));
      /* 取得RSS標題與內容列表 */
      data =myExampleHandler.getParsedData(); 
      title=myExampleHandler.getRssTitle();
    }
    catch (Exception e)
    { 
      /* 發生錯誤時回傳result回上一個activity */
      Intent intent=new Intent();
      Bundle bundle = new Bundle();
      bundle.putString("error",""+e);
      intent.putExtras(bundle);
      /* 錯誤的回傳值設定為99 */
      EX08_13_1.this.setResult(99, intent);
      EX08_13_1.this.finish();
    }
    return data;
  }
}