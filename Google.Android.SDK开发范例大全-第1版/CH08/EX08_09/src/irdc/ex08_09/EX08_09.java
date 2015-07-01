package irdc.ex08_09; 

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream; 
import java.net.URL; 
import java.net.URLConnection; 

import android.app.Activity; 
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle; 
import android.util.Log;
import android.view.View; 
import android.webkit.URLUtil;
import android.widget.Button; 
import android.widget.EditText;
import android.widget.ImageView; 
import android.widget.TextView; 
import android.widget.Toast;

public class EX08_09 extends Activity 
{ 
  protected static final String APP_TAG = "DOWNLOAD_RINGTONE";
  private Button mButton1; 
  private TextView mTextView1;
  private EditText mEditText1; 
  private String strURL = "";
  public static final int RINGTONE_PICKED = 0x108;
  private String currentFilePath = "";
  private String currentTempFilePath = "";
  private String fileEx="";
  private String fileNa="";
  private String strRingtoneFolder = "/sdcard/music/ringtones";
  
  
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    mButton1 =(Button) findViewById(R.id.myButton1); 
    mTextView1 = (TextView) findViewById(R.id.myTextView1); 
    mEditText1 = (EditText) findViewById(R.id.myEditText1);
    
    /*判斷是否有/sdcard/music/ringtones資料夾*/ 
    if(bIfExistRingtoneFolder(strRingtoneFolder))
    {
      Log.i(APP_TAG, "Ringtone Folder exists.");
    }
    
    mButton1.setOnClickListener(new Button.OnClickListener() 
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        strURL = mEditText1.getText().toString(); 
        
         Toast.makeText(EX08_09.this, getString(R.string.str_msg)
                  ,Toast.LENGTH_SHORT).show();         
       
        /*取得檔案名稱*/
        fileEx = strURL.substring(strURL.lastIndexOf(".")+1,strURL.
                     length()).toLowerCase();
        fileNa = strURL.substring(strURL.lastIndexOf("/")+1,strURL.
                     lastIndexOf("."));
        getFile(strURL);
      }
     });
  }       

  /* 判斷檔案MimeType的method */
  private String getMIMEType(File f) 
  { 
    String type="";
    String fName=f.getName();
    /* 取得副檔名 */
    String end=fName.substring(fName.lastIndexOf(".")+1,
                            fName.length()).toLowerCase(); 
    
    /* 依附檔名的類型決定MimeType */
    if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
        end.equals("xmf")||end.equals("ogg")||end.equals("wav"))
    {
      type = "audio"; 
    }
    else if(end.equals("3gp")||end.equals("mp4"))
    {
      type = "video";
    }
    else if(end.equals("jpg")||end.equals("gif")||
             end.equals("png")||end.equals("jpeg")||
              end.equals("bmp"))
    {
      type = "image";
    }
    else
    {
      type="*";
    }
    /*如果無法直接開啟，就跳出軟體清單給使用者選擇 */
    if(end.equals("image")) 
    { 
    } 
    else 
    { 
      type += "/*";  
    } 
    return type;  
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
            Log.e(APP_TAG, e.getMessage(), e); 
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
      mTextView1.setText("錯誤的URL"); 
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
      
      /*建立檔案位址*/ 
      File myTempFile = new File("/sdcard/music/ringtones/",
                        fileNa+"."+fileEx);
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
      
      /* 開啟RingtonManager進行鈴聲選擇 */
      String uri = null;
      if(bIfExistRingtoneFolder(strRingtoneFolder))
      {
        /*設定鈴聲*/
        Intent intent = new Intent( RingtoneManager.
                    ACTION_RINGTONE_PICKER);
        /*設定顯示鈴聲的資料夾*/
        intent.putExtra( RingtoneManager.EXTRA_RINGTONE_TYPE,
                    RingtoneManager.TYPE_RINGTONE);
        /*設定顯示鈴聲開頭*/
        intent.putExtra( RingtoneManager.EXTRA_RINGTONE_TITLE,
                    "設定鈴聲");
        if( uri != null)
        {
          intent.putExtra( RingtoneManager.
                EXTRA_RINGTONE_EXISTING_URI, Uri.parse( uri));
        }
        else
        {
          intent.putExtra( RingtoneManager.
                EXTRA_RINGTONE_EXISTING_URI, (Uri)null); 
        }
        startActivityForResult(intent, RINGTONE_PICKED);
      }
      
      try 
      { 
        is.close(); 
      } 
      catch (Exception ex) 
      { 
        Log.e(APP_TAG, "error: " + ex.getMessage(), ex); 
      } 
    }
  } 
  
  @Override
  protected void onActivityResult(int requestCode, 
                   int resultCode, Intent data)
  {
    // TODO Auto-generated method stub
    if (resultCode != RESULT_OK)
    {
      return;
    }
    switch (requestCode)
    { 
      case (RINGTONE_PICKED):
        try
        {
          Uri pickedUri = data.getParcelableExtra
          (RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
          if(pickedUri!=null)
          {
            RingtoneManager.setActualDefaultRingtoneUri
            (EX08_09.this,RingtoneManager.TYPE_RINGTONE,
                pickedUri);
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        break;
      default:
        break;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
  
  /*判斷是否/sdcard/music/ringtones資料夾*/
  private boolean bIfExistRingtoneFolder(String strFolder)
  {
    boolean bReturn = false;
    
    File f = new File(strFolder);
    if(!f.exists())
    {
      /*建立/sdcard/music/ringtones資料夾*/
      if(f.mkdirs())
      {
        bReturn = true;
      }
      else
      {
        bReturn = false;
      }
    }
    else
    {
      bReturn = true;
    }
    return bReturn;
  }
}       