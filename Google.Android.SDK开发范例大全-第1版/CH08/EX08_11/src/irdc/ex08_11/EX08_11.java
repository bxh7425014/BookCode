package irdc.ex08_11;

/* import相關class */
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EX08_11 extends Activity
{
  /* 變數宣告
   * newName：上傳後在伺服器上的檔案名稱
   * uploadFile：要上傳的檔案路徑
   * actionUrl：伺服器上對應的程式路徑 */
  private String newName="image.jpg";
  private String uploadFile="/data/data/irdc.ex08_11/image.jpg";
  private String actionUrl="http://10.10.100.33/upload/upload.jsp";
  private TextView mText1;
  private TextView mText2;
  private Button mButton;
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mText1 = (TextView) findViewById(R.id.myText2);
    mText1.setText("檔案路徑：\n"+uploadFile);
    mText2 = (TextView) findViewById(R.id.myText3);
    mText2.setText("上傳網址：\n"+actionUrl);
    /* 設定mButton的onClick事件處理 */    
    mButton = (Button) findViewById(R.id.myButton);
    mButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        uploadFile();
      }
    });
  }
  
  /* 上傳檔案至Server的method */
  private void uploadFile()
  {
    String end = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    try
    {
      URL url =new URL(actionUrl);
      HttpURLConnection con=(HttpURLConnection)url.openConnection();
      /* 允許Input、Output，不使用Cache */
      con.setDoInput(true);
      con.setDoOutput(true);
      con.setUseCaches(false);
      /* 設定傳送的method=POST */
      con.setRequestMethod("POST");
      /* setRequestProperty */
      con.setRequestProperty("Connection", "Keep-Alive");
      con.setRequestProperty("Charset", "UTF-8");
      con.setRequestProperty("Content-Type",
                         "multipart/form-data;boundary="+boundary);
      /* 設定DataOutputStream */
      DataOutputStream ds = 
        new DataOutputStream(con.getOutputStream());
      ds.writeBytes(twoHyphens + boundary + end);
      ds.writeBytes("Content-Disposition: form-data; " +
                    "name=\"file1\";filename=\"" +
                    newName +"\"" + end);
      ds.writeBytes(end);   

      /* 取得檔案的FileInputStream */
      FileInputStream fStream = new FileInputStream(uploadFile);
      /* 設定每次寫入1024bytes */
      int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];

      int length = -1;
      /* 從檔案讀取資料至緩衝區 */
      while((length = fStream.read(buffer)) != -1)
      {
        /* 將資料寫入DataOutputStream中 */
        ds.write(buffer, 0, length);
      }
      ds.writeBytes(end);
      ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

      /* close streams */
      fStream.close();
      ds.flush();
      
      /* 取得Response內容 */
      InputStream is = con.getInputStream();
      int ch;
      StringBuffer b =new StringBuffer();
      while( ( ch = is.read() ) != -1 )
      {
        b.append( (char)ch );
      }
      /* 將Response顯示於Dialog */
      showDialog(b.toString().trim());
      /* 關閉DataOutputStream */
      ds.close();
    }
    catch(Exception e)
    {
      showDialog(""+e);
    }
  }
  
  /* 顯示Dialog的method */
  private void showDialog(String mess)
  {
    new AlertDialog.Builder(EX08_11.this).setTitle("Message")
     .setMessage(mess)
     .setNegativeButton("確定",new DialogInterface.OnClickListener()
     {
       public void onClick(DialogInterface dialog, int which)
       {          
       }
     })
     .show();
  }
}