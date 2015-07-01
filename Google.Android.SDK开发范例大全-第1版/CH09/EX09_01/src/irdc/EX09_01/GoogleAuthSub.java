package irdc.EX09_01;

import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.UnsupportedEncodingException; 
import java.util.ArrayList; 
import java.util.List; 
/*必需引用apache.http相關類別進行Http連線*/
import org.apache.http.Header; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.ClientProtocolException; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicHeader; 
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.protocol.HTTP; 
import android.util.Log; 

public class GoogleAuthSub 
{ 
  /*宣告變數*/
  private DefaultHttpClient httpclient; 
  private HttpPost httpost; 
  private HttpResponse response; 
  private String strGoogleAccount; 
  private String strGooglePassword; 
  private String TAG = "IRDC_DEBUG"; 
  
  /*GoogleAuthSub物件的建構子*/
  public GoogleAuthSub(String strUID, String strPWD) 
  { 
    this.strGoogleAccount = strUID; 
    this.strGooglePassword = strPWD; 
    httpclient = new DefaultHttpClient(); 
    httpost = new HttpPost("https://www.google.com/accounts/ClientLogin"); 
  } 
  
  /*取得Google Token方法*/
  public String getAuthSubToken() 
  { 
    /*建立Name Value Pair字串*/
    List <NameValuePair> nvps = new ArrayList <NameValuePair>(); 
    nvps.add(new BasicNameValuePair("Email", this.strGoogleAccount)); 
    nvps.add(new BasicNameValuePair("Passwd", this.strGooglePassword)); 
    nvps.add(new BasicNameValuePair("source", "MyApiV1")); 
    nvps.add(new BasicNameValuePair("service", "cl")); 
    String GoogleLoginAuth=""; 
    try 
    { 
      /*建立Http Post連線*/
      httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.DEFAULT_CONTENT_CHARSET)); 
      response = httpclient.execute(httpost); 
      if( response.getStatusLine().getStatusCode()!=200 ) 
      { 
        return ""; 
      } 
      /*取回Google Token*/
      InputStream is = response.getEntity().getContent(); 
      GoogleLoginAuth = getAuth(is); 

      
      
      /*模擬HTTP Header*/ 
      Header[] headers = new BasicHeader[6]; 
      headers[0] = new BasicHeader("Content-type", "application/x-www-form-urlencoded");  
      headers[1] = new BasicHeader("Authorization", "GoogleLogin auth=\""+GoogleLoginAuth+"\""); 
      headers[2] = new BasicHeader("User-Agent", "Java/1.5.0_06"); 
      headers[3] = new BasicHeader("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"); 
      headers[4] = new BasicHeader("Connection", "keep-alive"); 
      /*發出Http Get請求登入Google Calendar服務作範例*/ 
      HttpGet httpget; 
      String feedUrl2 = "http://www.google.com/calendar/feeds/default/allcalendars/full"; 
      httpget = new HttpGet(feedUrl2); 
      httpget.addHeader(headers[0]); 
      httpget.addHeader(headers[1]); 
      httpget.addHeader(headers[2]); 
      httpget.addHeader(headers[3]); 
      httpget.addHeader(headers[4]); 
      /*取得Google Calendar服務回應*/
      response = httpclient.execute(httpget); 
      String strTemp01 = convertStreamToString(response.getEntity().getContent()); 
      Log.i(TAG, strTemp01); 
      /*指定暫存檔位置*/ 
      String strEarthLog = "/sdcard/googleauth.log"; 
      BufferedWriter bw; 
      bw = new BufferedWriter (new FileWriter(strEarthLog)); 
      /*將取回檔案寫入暫存檔中*/
      bw.write( strTemp01, 0, strTemp01.length() ); 
      bw.flush(); 
       
    } 
    catch (UnsupportedEncodingException e) 
    { 
      e.printStackTrace(); 
    } 
    catch (ClientProtocolException e) 
    { 
      e.printStackTrace(); 
    } 
    catch (IOException e) 
    { 
      e.printStackTrace(); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    } 
    return GoogleLoginAuth; 
  } 
   /*自訂讀取token內容的方法*/
  public String getAuth(InputStream is) 
  { 
    BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
    String line = null; 
    String strAuth=""; 
    try 
    { 
      while ((line = reader.readLine()) != null) 
      { 
        Log.d(TAG, ": "+line); 
        if( line.startsWith("Auth=")) 
        { 
          strAuth=line.substring(5); 
          Log.i("auth",": "+strAuth); 
        } 
      } 
    } 
    catch (IOException e) 
    { 
      e.printStackTrace(); 
    } 
    finally 
    { 
      try 
      { 
        is.close(); 
      } 
      catch (IOException e) 
      { 
        e.printStackTrace(); 
      } 
    } 
    return strAuth; 
  } 
   /*將資料轉為字串方法*/
  public String convertStreamToString(InputStream is) 
  { 
    BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
    StringBuilder sb = new StringBuilder(); 
    String line = null; 
    try 
    { 
      while ((line = reader.readLine()) != null) 
      { 
        sb.append(line); 
      } 
    } 
    catch (IOException e) 
    { 
      e.printStackTrace(); 
    } 
    finally 
    { 
      try 
      { 
        is.close(); 
      } 
      catch (IOException e) 
      { 
        e.printStackTrace(); 
      } 
    } 
    return sb.toString(); 
  } 
} 

