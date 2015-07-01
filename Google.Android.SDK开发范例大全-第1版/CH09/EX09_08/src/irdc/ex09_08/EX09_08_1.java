package irdc.ex09_08;

/* import相關class */
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class EX09_08_1 extends Activity
{
  private GridView gView;
  private String userId="";
  private List<String[]> li=new ArrayList<String[]>();
  
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 設定layout為albumlist.xml */
    setContentView(R.layout.albumlist);
    
    /* 取得Bundle中的userId */
    Intent intent=this.getIntent();
    Bundle bunde = intent.getExtras();
    userId = bunde.getString("userId");

    /* 呼叫getAlbumList()取得相簿資訊 */
    li=this.getAlbumList(userId);
    /* 設定gView的Adapter為自定義的AlbumAdapter */
    gView=(GridView) findViewById(R.id.myGrid);
    gView.setAdapter(new AlbumAdapter(this,li));
    
    /* 設定GridView的onItemClick事件 */
    gView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() 
    { 
      @Override
      public void onItemClick(AdapterView<?> arg0,View arg1,
                              int arg2,long arg3)
      {
        /* 把帳號、相簿ID、相簿名稱放入Bundle傳給下一個Activity */
        Intent intent = new Intent();
        intent.setClass(EX09_08_1.this,EX09_08_2.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId",userId);
        bundle.putString("albumId",li.get(arg2)[0]);
        bundle.putString("title",li.get(arg2)[2]);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
      } 
    }); 
  }
  
  /* 剖析XML取得相簿資訊的method */
  private List<String[]> getAlbumList(String id)
  {
    List<String[]> data=new ArrayList<String[]>();
    URL url = null;
    String path="http://picasaweb.google.com/data/feed/api/user/"
                +id.trim();
    try
    {
      url = new URL(path);
      /* 以自訂的AlbumHandler作為解析XML的Handler */
      AlbumHandler alHandler = new AlbumHandler(); 
      Xml.parse(url.openConnection().getInputStream(),
                Xml.Encoding.UTF_8,alHandler);
      
      /* 取得相簿資訊 */
      data =alHandler.getParsedData(); 
    }
    catch (Exception e)
    { 
      /* 發生錯誤時回傳result回上一個activity */
      Intent intent=new Intent();
      Bundle bundle = new Bundle();
      bundle.putString("error",""+e);
      intent.putExtras(bundle);
      /* 錯誤的回傳值設定為99 */
      EX09_08_1.this.setResult(99, intent);
      EX09_08_1.this.finish();
    }
    return data;
  }
  
  /* 覆寫 onActivityResult()*/
  @Override
  protected void onActivityResult(int requestCode,int resultCode,
                                  Intent data)
  {
    switch (resultCode)
    { 
      case 99:
        /* 回傳錯誤時以Dialog顯示 */
        Bundle bunde = data.getExtras();
        String error = bunde.getString("error");
        showDialog(error);
        break;     
      default: 
        break; 
    } 
  } 
  
  /* 顯示Dialog的method */
  private void showDialog(String mess){
    new AlertDialog.Builder(EX09_08_1.this).setTitle("Message")
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