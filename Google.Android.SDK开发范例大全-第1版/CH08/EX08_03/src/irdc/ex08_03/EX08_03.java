package irdc.ex08_03;

import android.app.Activity;
import android.os.Bundle;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EX08_03 extends Activity 
{
  private WebView mWebView1; 
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    String strIFrame = 
      "<iframe name=\"Hippo_ImageNote_frame\"" +
      "width=\"320\" height=\"480\"" +
      "frameborder=\"0\" src=" +
      "\"http://www.dubblogs.cc:8751/Android/Test/API/TestAjaxForm/\""+
      "marginwidth=\"0\" marginheight=\"0\" vspace=\"0\" " +
      "hspace=\"0\" allowtransparency=\"true\"" +
      "scrolling=\"no\"></iframe>";
    
    /*設定WebView的物件*/
    mWebView1 = (WebView) findViewById(R.id.myWebView1);
    WebSettings webSettings = mWebView1.getSettings(); 
    webSettings.setJavaScriptEnabled(true); 
    //webSettings.setSaveFormData(false); 
    //webSettings.setSavePassword(false); 
    //webSettings.setSupportZoom(false); 
     
    //mWebView1.setWebChromeClient(new MyWebChromeClient());
    /*mWebView1.
    loadData(
        "<iframe id=test name=test src=http://www.google.com/" +
        " width=320 height=240 ALIGN=left VALIGN=top " +
        "MARGINWIDTH=0 MARGINHEIGHT=0 FRAMEBORDER=0 " +
        "SCROLLING=NO></iframe>", 
        "text/html", "utf-8");*/
    
    /*自行設定WebView要呈現的網頁內容*/
    mWebView1.
      loadData(
      "<html><body>"+strIFrame+"</body></html>", "text/html", "utf-8");
    }
  /*
  final class MyWebChromeClient extends WebChromeClient 
  {     
  }
  */
}