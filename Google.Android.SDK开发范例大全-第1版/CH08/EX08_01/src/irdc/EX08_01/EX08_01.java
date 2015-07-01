package irdc.EX08_01;


/*必需引用apache.http相關類別來建立HTTP連線*/
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.ClientProtocolException; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.protocol.HTTP; 
import org.apache.http.util.EntityUtils; 
/*必需引用java.io 與java.util相關類別來讀寫檔案*/
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.TextView; 

public class EX08_01 extends Activity 
{ 
  /*宣告兩個Button物件,與一個TextView物件*/
  private Button mButton1,mButton2; 
  private TextView mTextView1; 
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /*透過findViewById建構子建立TextView與Button物件*/ 
    mButton1 =(Button) findViewById(R.id.myButton1); 
    mButton2 =(Button) findViewById(R.id.myButton2);
    mTextView1 = (TextView) findViewById(R.id.myTextView1); 
     
    /*設定OnClickListener來聆聽OnClick事件*/
    mButton1.setOnClickListener(new Button.OnClickListener() 
    { 
      /*覆寫onClick事件*/
      @Override 
      public void onClick(View v) 
      { 
        /*宣告網址字串*/
        String uriAPI = "http://www.dubblogs.cc:8751/Android/Test/API/Post/index.php";
        /*建立HTTP Post連線*/
        HttpPost httpRequest = new HttpPost(uriAPI); 
        /*
         * Post運作傳送變數必須用NameValuePair[]陣列儲存
        */
        List <NameValuePair> params = new ArrayList <NameValuePair>(); 
        params.add(new BasicNameValuePair("str", "I am Post String")); 
        try 
        { 
          /*發出HTTP request*/
          httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
          /*取得HTTP response*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*若狀態碼為200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*取出回應字串*/
            String strResult = EntityUtils.toString(httpResponse.getEntity()); 
            mTextView1.setText(strResult); 
          } 
          else 
          { 
            mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (IOException e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (Exception e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace();  
        }  
         
      } 
    }); 
    mButton2.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View v) 
      { 
        // TODO Auto-generated method stub 
        /*宣告網址字串*/
        String uriAPI = "http://www.dubblogs.cc:8751/Android/Test/API/Get/index.php?str=I+am+Get+String"; 
        /*建立HTTP Get連線*/
        HttpGet httpRequest = new HttpGet(uriAPI); 
        try 
        { 
          /*發出HTTP request*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*若狀態碼為200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*取出回應字串*/
            String strResult = EntityUtils.toString(httpResponse.getEntity());
            /*刪除多餘字元*/
            strResult = eregi_replace("(\r\n|\r|\n|\n\r)","",strResult);
            mTextView1.setText(strResult); 
          } 
          else 
          { 
            mTextView1.setText("Error Response: "+httpResponse.getStatusLine().toString()); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (IOException e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (Exception e) 
        {  
          mTextView1.setText(e.getMessage().toString()); 
          e.printStackTrace();  
        }  
      } 
    }); 
  }
    /* 自訂字串取代函數 */
    public String eregi_replace(String strFrom, String strTo, String strTarget)
    {
      String strPattern = "(?i)"+strFrom;
      Pattern p = Pattern.compile(strPattern);
      Matcher m = p.matcher(strTarget);
      if(m.find())
      {
        return strTarget.replaceAll(strFrom, strTo);
      }
      else
      {
        return strTarget;
      }
    }
} 
