package irdc.ex05_15;

/* import相關class */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class EX05_15 extends ListActivity
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
  private View myView;
  private EditText myEditText;
  
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
      /* 第二筆設定為[回上層] */
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
    File file = new File(paths.get(position));
  
    if(file.isDirectory())
    {
      /* 如果是資料夾就再執行getFileDir() */
      getFileDir(paths.get(position));
    }
    else
    {
      /* 如果是檔案呼叫fileHandle() */
      fileHandle(file);
    }
  }
  
  /* 處理檔案的method */
  private void fileHandle(final File file){
    /* 按下檔案時的OnClickListener */
    OnClickListener listener1=new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog,int which)
      {
        if(which==0)
        {
          /* 選擇的item為開啟檔案 */
          openFile(file);
        }
        else if(which==1)
        {
          /* 選擇的item為更改檔名 */
          LayoutInflater factory = LayoutInflater.from(EX05_15.this);
          /* 初始化myChoiceView，使用rename_alert_dialog為layout */
          myView=factory.inflate(R.layout.rename_alert_dialog,null);
          myEditText=(EditText)myView.findViewById(R.id.mEdit);
          /* 將原始檔名先放入EditText中 */
          myEditText.setText(file.getName());
            
          /* new一個更改檔名的Dialog的確定按鈕的listener */
          OnClickListener listener2=new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
              /* 取得修改後的檔案路徑 */
              String modName=myEditText.getText().toString();
              final String pFile=file.getParentFile().getPath()+"/";
              final String newPath=pFile+modName;
              
              /* 判斷檔名是否已存在 */
              if(new File(newPath).exists())
              {
                /* 排除修改檔名時沒修改直接送出的狀況 */
                if(!modName.equals(file.getName()))
                {
                  /* 跳出Alert警告檔名重覆，並確認是否修改 */
                  new AlertDialog.Builder(EX05_15.this)
                      .setTitle("注意!")
                      .setMessage("檔名已經存在，是否要覆蓋?")
                      .setPositiveButton("確定",new DialogInterface.OnClickListener()
                      {
                        public void onClick(DialogInterface dialog,int which)
                        {          
                          /* 檔名重覆仍然修改會覆改掉已存在的檔案 */
                          file.renameTo(new File(newPath));
                          /* 重新產生檔案列表的ListView */
                          getFileDir(pFile);
                        }
                      })
                      .setNegativeButton("取消",new DialogInterface.OnClickListener()
                      {
                        public void onClick(DialogInterface dialog,int which)
                        {
                        }
                      }).show();
                }
              }
              else
              {
                /* 檔名不存在，直接做修改動作 */
                file.renameTo(new File(newPath));
                /* 重新產生檔案列表的ListView */
                getFileDir(pFile);
              }
            }
          };

          /* create更改檔名時跳出的Dialog */
          AlertDialog renameDialog=new AlertDialog.Builder(EX05_15.this).create();
          renameDialog.setView(myView);
          
          /* 設定更改檔名按下確認後的Listener */
          renameDialog.setButton("確定",listener2);
          renameDialog.setButton2("取消",new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface dialog, int which)
            {
            }
          });
          renameDialog.show();
        }
        else
        {
          /* 選擇的item為刪除檔案 */
          new AlertDialog.Builder(EX05_15.this).setTitle("注意!")
              .setMessage("確定要刪除檔案嗎?")
              .setPositiveButton("確定", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface dialog, int which)
                {          
                  /* 刪除檔案 */
                  file.delete();
                  getFileDir(file.getParent());
                }
              })
              .setNegativeButton("取消", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface dialog, int which)
                {
                }
              }).show();
        }
      }
    };
        
    /* 選擇一個檔案時，跳出要如何處理檔案的ListDialog */
    String[] menu={"開啟檔案","更改檔名","刪除檔案"};
    new AlertDialog.Builder(EX05_15.this)
        .setTitle("你要做甚麼?")
        .setItems(menu,listener1)
        .setPositiveButton("取消", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int which)
          {
          }
        })
        .show();
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