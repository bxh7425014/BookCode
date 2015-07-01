package irdc.ex05_18;

/* import相關class */
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.os.Bundle; 
import android.telephony.TelephonyManager;

public class EX05_18 extends ListActivity 
{ 
  private TelephonyManager telMgr;
  private List<String> item=new ArrayList<String>();
  private List<String> value=new ArrayList<String>();
   
  @SuppressWarnings("static-access")
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    /* 載入main.xml Layout */
    setContentView(R.layout.main); 
    telMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
    
    /* 將取得的資訊寫入List中 */
    /* 取得SIM卡狀態 */
    item.add(getResources().getText(R.string.str_list0).toString());
    if(telMgr.getSimState()==telMgr.SIM_STATE_READY)
    {
      value.add("良好");
    }
    else if(telMgr.getSimState()==telMgr.SIM_STATE_ABSENT)
    {
      value.add("無SIM卡");
    }
    else
    {
      value.add("SIM卡被鎖定或未知的狀態");
    }
    
    /* 取得SIM卡卡號 */
    item.add(getResources().getText(R.string.str_list1).toString());
    if(telMgr.getSimSerialNumber()!=null)
    {
      value.add(telMgr.getSimSerialNumber());
    }
    else
    {
      value.add("無法取得");
    }
    
    /* 取得SIM卡供應商代碼 */
    item.add(getResources().getText(R.string.str_list2).toString());
    if(telMgr.getSimOperator().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(telMgr.getSimOperator());
    }
    
    /* 取得SIM卡供應商名稱 */
    item.add(getResources().getText(R.string.str_list3).toString());
    if(telMgr.getSimOperatorName().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(telMgr.getSimOperatorName());
    }
    
    /* 取得SIM卡國別 */
    item.add(getResources().getText(R.string.str_list4).toString());
    if(telMgr.getSimCountryIso().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(telMgr.getSimCountryIso());
    }
    
    /* 使用自定義的MyAdapter來將資料傳入ListActivity */
    setListAdapter(new MyAdapter(this,item,value));
  } 
}