package irdc.ex04_21;

/* import相關class */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class EX04_21 extends ListActivity
{
  /* 物件宣告 
     items：存放顯示的名稱
     paths：存放檔案路徑
     rootPath：起始目錄
  */
  private List<String> items=null;
  private List<String> paths=null;
  private String rootPath="/";
  private TextView mPath;
  
  /** Called when the activity is first created. */
  @Override
  protected void onCreate(Bundle icicle)
  {
    super.onCreate(icicle);
    
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
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
      items.add("Back to "+rootPath);
      paths.add(rootPath);
      /* 第二筆設定為[回上層] */
      items.add("Back to ../");
      paths.add(f.getParent());
    }
    /* 將所有檔案加入ArrayList中 */
    for(int i=0;i<files.length;i++)
    {
      File file=files[i];
      items.add(file.getName());
      paths.add(file.getPath());
    }
    
    /* 宣告一ArrayAdapter，使用file_row這個Layout，並將Adapter設定給此ListActivity */
    ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_row, items);
    setListAdapter(fileList);
  }
  
  /* 設定ListItem被按下時要做的動作 */
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id)
  {
    File file = new File(paths.get(position));
    if (file.isDirectory())
    {
      /* 如果是資料夾就再進去撈一次 */
      getFileDir(paths.get(position));
    }
    else
    {
      /* 如果是檔案，則跳出AlertDialog */
    	new AlertDialog.Builder(this).setIcon(R.drawable.icon)
                       .setTitle("["+file.getName()+"] is File!")
                       .setPositiveButton("OK",
                       new DialogInterface.OnClickListener()
                       {
                         public void onClick(DialogInterface dialog,int whichButton)
                         {
                         }
                       }).show();         
    }
  }
}