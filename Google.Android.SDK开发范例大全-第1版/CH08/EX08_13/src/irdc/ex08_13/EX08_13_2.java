package irdc.ex08_13;

/* import相關class */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class EX08_13_2 extends Activity
{
  /* 變數宣告 */
  private TextView mTitle;
  private TextView mDesc;
  private TextView mLink;
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 設定layout為newscontent.xml */
    setContentView(R.layout.newscontent);
    /* 初始化物件 */
    mTitle=(TextView) findViewById(R.id.myTitle);
    mDesc=(TextView) findViewById(R.id.myDesc);
    mLink=(TextView) findViewById(R.id.myLink);

    /* 取得Intent中的Bundle物件 */
    Intent intent=this.getIntent();
    Bundle bunde = intent.getExtras();
    /* 取得Bundle物件中的資料 */
    mTitle.setText(bunde.getString("title"));
    mDesc.setText(bunde.getString("desc")+"....");
    mLink.setText(bunde.getString("link"));
    /* 設定mLink為網頁連結 */
    Linkify.addLinks(mLink,Linkify.WEB_URLS);
  }
}