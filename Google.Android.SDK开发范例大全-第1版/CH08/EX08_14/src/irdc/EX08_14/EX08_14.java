package irdc.EX08_14;

import android.app.Activity; 
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle; 
import android.util.Log; 
import android.view.View; 
import android.webkit.URLUtil; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/*必需引用java.io與java.net*/
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.InputStream; 
import java.net.URL; 
import java.net.URLConnection; 



public class EX08_14 extends Activity 
{ 
  private TextView mTextView01;
  private EditText mEditText01;
  private Button mButton01;
  private static final String TAG = "DOWNLOADAPK"; 
  private String currentFilePath = ""; 
  private String currentTempFilePath = ""; 
  private String strURL="";
  private String fileEx="";
  private String fileNa="";
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mButton01 = (Button)findViewById(R.id.myButton1);
    mEditText01 =(EditText)findViewById(R.id.myEditText1);
 
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v) 
      {
        /* 檔案會下載至local端 */ 
        mTextView01.setText("下載中...");
        strURL = mEditText01.getText().toString(); 
        /*取得欲安裝程式之檔案名稱*/
        fileEx = strURL.substring(strURL.lastIndexOf(".")+1,strURL.length()).toLowerCase();
        fileNa = strURL.substring(strURL.lastIndexOf("/")+1,strURL.lastIndexOf("."));
        getFile(strURL);
       }
     }
    );
    
    mEditText01.setOnClickListener(new EditText.OnClickListener()
    {

      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        mEditText01.setText("");
        mTextView01.setText("遠端安裝程式(請輸入URL)");
      }
      
    });
  }

  private void getFile(final String strPath) 
  { 
    try 
    { 
      if (strPath.equals(currentFilePath) ) 
      { 
          
       getDataSource(strPath);  
      }        
      currentFilePath = strPath;      
      Runnable r = new Runnable() 
      {   
        public void run() 
        {   
          try 
          { 
             getDataSource(strPath); 
          } 
          catch (Exception e) 
          { 
            Log.e(TAG, e.getMessage(), e); 
          } 
        } 
      };   
      new Thread(r).start(); 
    } 
    catch(Exception e) 
    { 
      e.printStackTrace(); 
    }
  } 
  
   /*取得遠端檔案*/ 
  private void getDataSource(String strPath) throws Exception 
  { 
    if (!URLUtil.isNetworkUrl(strPath)) 
    { 
      mTextView01.setText("錯誤的URL"); 
    } 
    else 
    { 
        /*取得URL*/
        URL myURL = new URL(strPath); 
        /*建立連線*/
        URLConnection conn = myURL.openConnection();   
        conn.connect();   
        /*InputStream 下載檔案*/
        InputStream is = conn.getInputStream();   
        if (is == null) 
        { 
          throw new RuntimeException("stream is null"); 
        } 
        /*建立暫存檔案*/ 
        File myTempFile = File.createTempFile(fileNa, "."+fileEx); 
        /*取得站存檔案路徑*/
        currentTempFilePath = myTempFile.getAbsolutePath(); 
        /*將檔案寫入暫存檔*/ 
        FileOutputStream fos = new FileOutputStream(myTempFile); 
        byte buf[] = new byte[128];   
        do 
        {   
          int numread = is.read(buf);   
          if (numread <= 0) 
          { 
            break; 
          } 
          fos.write(buf, 0, numread);   
        }while (true);  
        
        /*開啟檔案進行安裝*/
        openFile(myTempFile);
        //openFile(c);
        try 
        { 
          is.close(); 
        } 
        catch (Exception ex) 
        { 
          Log.e(TAG, "error: " + ex.getMessage(), ex); 
        } 
      }
    }  
   
  /* 在手機上開啟檔案的method */
  private void openFile(File f) 
  {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(android.content.Intent.ACTION_VIEW);
    
    /* 呼叫getMIMEType()來取得MimeType */
    String type = getMIMEType(f);
    /* 設定intent的file與MimeType */
    intent.setDataAndType(Uri.fromFile(f),type);
    startActivity(intent); 
  }

  /* 判斷檔案MimeType的method */
  private String getMIMEType(File f) 
  { 
    String type="";
    String fName=f.getName();
    /* 取得副檔名 */
    String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase(); 
    
    /* 依附檔名的類型決定MimeType */
    if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||end.equals("xmf")||end.equals("ogg")||end.equals("wav"))
    {
      type = "audio"; 
    }
    else if(end.equals("3gp")||end.equals("mp4"))
    {
      type = "video";
    }
    else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||end.equals("jpeg")||end.equals("bmp"))
    {
      type = "image";
    }
    else if(end.equals("apk")) 
    { 
      /* android.permission.INSTALL_PACKAGES */ 
      type = "application/vnd.android.package-archive"; 
    } 
    else
    {
      type="*";
    }
    /*如果無法直接開啟，就跳出軟體清單給使用者選擇 */
    if(end.equals("apk")) 
    { 
    } 
    else 
    { 
      type += "/*";  
    } 
    return type;  
  } 

  /*自訂刪除檔案方法*/
  private void delFile(String strFileName) 
  { 
    File myFile = new File(strFileName); 
    if(myFile.exists()) 
    { 
      myFile.delete(); 
    } 
  } 
  
  /*當Activity處於onPause狀態時,更改TextView文字狀態*/
  @Override 
  protected void onPause()
  {
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mTextView01.setText("下載成功");
    super.onPause();
  }

  /*當Activity處於onResume狀態時,刪除暫存檔案*/
  @Override 
  protected void onResume() 
  { 
    // TODO Auto-generated method stub   
    /* 刪除暫存檔案 */ 
    delFile(currentTempFilePath); 
    super.onResume(); 
  }
} 
