package irdc.ex05_11;

/* import相關class */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class EX05_11 extends ListActivity
{
  /* 變數宣告 
     items：存放顯示的名稱
     paths：存放檔案路徑
     rootPath：起始目錄         */
  private List<String> items=null;
  private List<String> paths=null;
  private String rootPath="/";
  private TextView mPath;
  
  @Override
  protected void onCreate(Bundle icicle)
  {
    super.onCreate(icicle);
    
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    /* 初始化mPath，用以顯示目前路徑 */
    mPath=(TextView)findViewById(R.id.mPath);
    getFileDir(rootPath);
  }

  /* 取得檔案架構的method */
  private void getFileDir(String filePath)
  {
    /* 設定目前所在路徑 */
    mPath.setText(filePath);
    items=new ArrayList<String>();
    paths=new ArrayList<String>();
    File f=new File(filePath);  
    File[] files=f.listFiles();
    
    if(!filePath.equals(rootPath))
    {
      /* 第一筆設定為[回到根目錄] */
      items.add("b1");
      paths.add(rootPath);
      /* 第二筆設定為[回到上一層] */
      items.add("b2");
      paths.add(f.getParent());
    }
    /* 將所有檔案加入ArrayList中 */
    for(int i=0;i<files.length;i++)
    {
      File file=files[i];
      items.add(file.getName());
      paths.add(file.getPath());
    }
    
    /* 使用自定義的MyAdapter來將資料傳入ListActivity */
    setListAdapter(new MyAdapter(this,items,paths));
  }
  
  /* 設定ListItem被按下時要做的動作 */
  @Override
  protected void onListItemClick(ListView l,View v,int position,long id)
  {
    File file=new File(paths.get(position));
    if (file.isDirectory())
    {
      /* 如果是資料夾就再執行getFileDir() */
      getFileDir(paths.get(position));
    }
    else
    {
      /* 如果是檔案就執行openFile() */
      openFile(file);
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
    else
    {
      type="*";
    }
    /* 如果無法直接開啟，就跳出軟體清單給使用者選擇 */
    type += "/*"; 
    return type; 
  }
}