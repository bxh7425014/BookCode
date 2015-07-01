package irdc.ex05_24;

/* import相關class */
import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.os.Bundle; 
import android.telephony.TelephonyManager;

public class EX05_24 extends ListActivity 
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
    /* 取得手機電話號碼 */
    item.add(getResources().getText(R.string.str_list0).toString());
    if(telMgr.getLine1Number()!=null)
    {
      value.add(telMgr.getLine1Number());
    }
    else
    {
      value.add("無法取得");
    }
    
    /* 取得電信網路國別 */
    item.add(getResources().getText(R.string.str_list1).toString());
    if(telMgr.getNetworkCountryIso().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(""+telMgr.getNetworkCountryIso());
    }
    
    /* 取得電信公司代碼 */
    item.add(getResources().getText(R.string.str_list2).toString());
    if(telMgr.getNetworkOperator().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(telMgr.getNetworkOperator());
    }
    
    /* 取得電信公司名稱 */
    item.add(getResources().getText(R.string.str_list3).toString());
    if(telMgr.getNetworkOperatorName().equals(""))
    {
      value.add("無法取得");
    }
    else
    {
      value.add(telMgr.getNetworkOperatorName());
    }
    
    /* 取得行動通訊類型 */
    item.add(getResources().getText(R.string.str_list4).toString());
    if(telMgr.getPhoneType()==telMgr.PHONE_TYPE_GSM)
    {
      value.add("GSM");
    }
    else
    {
      value.add("未知");
    }
    
    /* 取得網路類型 */
    item.add(getResources().getText(R.string.str_list5).toString());
    if(telMgr.getNetworkType()==telMgr.NETWORK_TYPE_EDGE)
    {
      value.add("EDGE");
    }
    else if(telMgr.getNetworkType()==telMgr.NETWORK_TYPE_GPRS)
    {
      value.add("GPRS");
    }
    else if(telMgr.getNetworkType()==telMgr.NETWORK_TYPE_UMTS)
    {
      value.add("UMTS");
    }
    else if(telMgr.getNetworkType()==4)
    {
      value.add("HSDPA");
    }
    else
    {
      value.add("未知");
    }
    
    /* 取得漫遊狀態 */
    item.add(getResources().getText(R.string.str_list6).toString());
    if(telMgr.isNetworkRoaming())
    {
      value.add("漫遊中");
    }
    else{
      value.add("無漫遊");
    }
    
    /* 取得手機IMEI */
    item.add(getResources().getText(R.string.str_list7).toString());
    value.add(telMgr.getDeviceId());
    
    /* 取得IMEI SV */
    item.add(getResources().getText(R.string.str_list8).toString());
    if(telMgr.getDeviceSoftwareVersion()!=null)
    {
      value.add(telMgr.getDeviceSoftwareVersion());
    }
    else
    {
      value.add("無法取得");
    }
    
    /* 取得手機IMSI */
    item.add(getResources().getText(R.string.str_list9).toString());
    if(telMgr.getSubscriberId()!=null)
    {
      value.add(telMgr.getSubscriberId());
    }
    else
    {
      value.add("無法取得");
    }
    
    /* 取得ContentResolver */
    ContentResolver cv = EX05_24.this.getContentResolver();
    String tmpS="";
    
    /* 取得藍芽狀態 */
    item.add(getResources().getText(R.string.str_list10).toString());
    tmpS=android.provider.Settings.System.getString(cv,android.provider.Settings.System.BLUETOOTH_ON);
    if(tmpS.equals("1"))
    {
      value.add("已開啟");
    }
    else{
      value.add("未開啟");
    }
    
    /* 取得WIFI狀態 */
    item.add(getResources().getText(R.string.str_list11).toString());
    tmpS=android.provider.Settings.System.getString(cv,android.provider.Settings.System.WIFI_ON);
    if(tmpS.equals("1"))
    {
      value.add("已開啟");
    }
    else{
      value.add("未開啟");
    }
    
    /* 取得飛行模式是否開啟 */
    item.add(getResources().getText(R.string.str_list12).toString());
    tmpS=android.provider.Settings.System.getString(cv,android.provider.Settings.System.AIRPLANE_MODE_ON);
    if(tmpS.equals("1"))
    {
      value.add("開啟中");
    }
    else{
      value.add("未開啟");
    }
    
    /* 取得數據漫遊是否開啟 */
    item.add(getResources().getText(R.string.str_list13).toString());
    tmpS=android.provider.Settings.System.getString(cv,android.provider.Settings.System.DATA_ROAMING);
    if(tmpS.equals("1"))
    {
      value.add("開啟中");
    }
    else{
      value.add("未開啟");
    }
    
    /* 使用自定義的MyAdapter來將資料傳入ListActivity */
    setListAdapter(new MyAdapter(this,item,value));
  } 
} 
