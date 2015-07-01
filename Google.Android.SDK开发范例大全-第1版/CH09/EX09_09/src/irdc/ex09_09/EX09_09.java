package irdc.ex09_09;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

public class EX09_09 extends Activity
{
  private EditText myEditText1;
  private WebView myWebView1;
  private Handler mHandler01 = new Handler();
  private static final String LOG_TAG = "DEBUG";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    myEditText1 = (EditText) findViewById(R.id.myEditText1);
    myWebView1 = (WebView) findViewById(R.id.myWebView1);

    /* 取得WebSettings */
    WebSettings webSettings = myWebView1.getSettings();
    /* 設定可執行JavaScript */
    webSettings.setJavaScriptEnabled(true);
    webSettings.setSaveFormData(false);
    webSettings.setSavePassword(false);
    webSettings.setSupportZoom(false);
    myWebView1.setWebChromeClient(new MyWebChromeClient());
    /* 設定給html呼叫的物件及名稱 */
    myWebView1.addJavascriptInterface(new runJavaScript(), "irdc");
    /* 將assets/google_translate.html載入 */
    String url = "file:///android_asset/google_translate.html";
    myWebView1.loadUrl(url);
  }

  final class runJavaScript
  {
    public void runOnAndroidJavaScript()
    {
      mHandler01.post(new Runnable()
      {
        public void run()
        {
          if (myEditText1.getText().toString() != "")
          {
            /* 呼叫google_translate.html裡的javascript */
            myWebView1.loadUrl("javascript:translate('"
                + myEditText1.getText().toString() + "')");
          }
        }
      });
    }
  }

  /**
   * 捕捉網頁裡的alert javascript作為.js除錯之用，並輸出至LogCat
   */
  final class MyWebChromeClient extends WebChromeClient
  {
    @Override
    public boolean onJsAlert(WebView view, String url,
        String message, JsResult result)
    {
      // TODO Auto-generated method stub
      Log.d(LOG_TAG, message);
      // result.confirm();
      return super.onJsAlert(view, url, message, result);
    }
  }
}