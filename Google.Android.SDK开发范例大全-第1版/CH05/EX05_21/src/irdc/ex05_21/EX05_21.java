package irdc.ex05_21;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EX05_21 extends Activity
{
  private Button mButton01;
  private ListView mListView01;
  private ArrayAdapter<String> aryAdapter1;
  private ArrayList<String> arylistTask;
  
  /* 類別成員設定取回最多幾筆的Task數量 */
  private int intGetTastCounter=30;
  
  /* 類別成員ActivityManager物件 */
  private ActivityManager mActivityManager;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    mListView01 = (ListView)findViewById(R.id.myListView1);
    
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        try
        {
          /* ActivityManager物件向系統取得ACTIVITY_SERVICE */
          mActivityManager = (ActivityManager)EX05_21.this.getSystemService(ACTIVITY_SERVICE);
          
          arylistTask = new ArrayList<String>();
          
          /* 以getRunningTasks方法取回正在執行中的程式TaskInfo */
          List<ActivityManager.RunningTaskInfo> mRunningTasks = mActivityManager.getRunningTasks(intGetTastCounter);
          //List<ActivityManager.RunningServiceInfo> mRunningTasks = mActivityManager.getRunningServices(intGetTastCounter);
          
          int i = 1;
          /* 以迴圈及baseActivity方式取得工作名稱與ID */ 
          for (ActivityManager.RunningTaskInfo amTask : mRunningTasks)
          //for (ActivityManager.RunningServiceInfo amTask : mRunningTasks)
          {
            /* baseActivity.getClassName取出執行工作名稱 */
            arylistTask.add("" + (i++) + ": "+ amTask.baseActivity.getClassName() + "(ID=" + amTask.id +")"); 
            //arylistTask.add("" + (i++) + ": "+ amTask.process + "(ID=" + amTask.pid +")");
          }
          aryAdapter1 = new ArrayAdapter<String>(EX05_21.this, R.layout.simple_list_item_1, arylistTask);
          if(aryAdapter1.getCount()==0)
          {
            /* 當沒有任何執行的工作，則提示訊息 */
            mMakeTextToast
            (
              getResources().getText(R.string.str_err_no_running_task).toString(),
              //getResources().getText(R.string.str_err_no_running_service).toString(),
              true
            );
          }
          else
          {
            /* 發現背景執行的工作程式，以ListView Widget條列呈現 */
            mListView01.setAdapter(aryAdapter1);
          }
        }
        catch(SecurityException e)
        {
          /* 當無GET_TASKS權限時(SecurityException例外)提示訊息 */
          mMakeTextToast
          (
            getResources().getText(R.string.str_err_permission).toString(),
            true
          );
        }
      }
    });
    
    /* 當User在執行工作選擇時的事件處理 */
    mListView01.setOnItemSelectedListener(new ListView.OnItemSelectedListener()
    {
      @Override
      public void onItemSelected(AdapterView<?> parent, View v, int id, long arg3)
      {
        // TODO Auto-generated method stub
        /* 由於將執行工作以陣列存放，故以id取出陣列元素名稱 */
        mMakeTextToast(arylistTask.get(id).toString(),false);
      }

      @Override
      public void onNothingSelected(AdapterView<?> arg0)
      {
        // TODO Auto-generated method stub
        
      }
    });
    
    /* 當User在執行工作上點擊時的事件處理 */
    mListView01.setOnItemClickListener(new ListView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> parent, View v, int id,  long arg3)
      {
        // TODO Auto-generated method stub
        /* 由於將執行工作以陣列存放，故以id取出陣列元素名稱 */
        mMakeTextToast(arylistTask.get(id).toString(), false);
      }
    });
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(EX05_21.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(EX05_21.this, str, Toast.LENGTH_SHORT).show();
    }
  }
}