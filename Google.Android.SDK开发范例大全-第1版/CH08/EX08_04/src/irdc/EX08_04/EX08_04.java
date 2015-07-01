package irdc.EX08_04;

import android.app.Activity; 
import android.content.Intent; 
import android.net.Uri; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView; 

public class EX08_04 extends Activity 
{
  /*宣告一個ListView,TextView物件變數
   * 一個String array變數儲存我的最愛清單
   * 與String變數來儲存網址*/
  private ListView mListView1; 
  private TextView mTextView1; 
  private String[] myFavor;
  private String  myUrl;
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /*透過findViewById建構子建立ListView與TextView物件*/ 
    mListView1 =(ListView) findViewById(R.id.myListView1); 
    mTextView1 = (TextView) findViewById(R.id.myTextView1); 
    mTextView1.setText(getResources().getString(R.string.hello));
    /*將我的最愛清單由string.xml中匯入*/
    myFavor = new String[] { 
                               getResources().getString(R.string.str_list_url1), 
                               getResources().getString(R.string.str_list_url2), 
                               getResources().getString(R.string.str_list_url3), 
                               getResources().getString(R.string.str_list_url4) 
                             }; 
    /*自訂一ArrayAdapter準備傳入ListView中,並將myFavor清單以參數傳入*/ 
    ArrayAdapter<String> adapter = new  
    ArrayAdapter<String> 
    (EX08_04.this, android.R.layout.simple_list_item_1, myFavor); 
    
    /*將自訂完成的ArrayAdapter傳入自訂的ListView中*/
    mListView1.setAdapter(adapter);
    /*將ListAdapter的可選(Focusable)選單選項打開*/
    mListView1.setItemsCanFocus(true);  
    /*設定ListView選單選項設為每次只能單一選項*/ 
    mListView1.setChoiceMode 
    (ListView.CHOICE_MODE_SINGLE); 
    /*設定ListView選項的nItemClickListener*/
    mListView1.setOnItemClickListener(new ListView.OnItemClickListener()
    { 

      @Override
      /*覆寫OnItemClick方法*/
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
          long arg3)
      {
        // TODO Auto-generated method stub
        /*若所選選單的文字與myFavor字串陣列第一個文字相同*/ 
        if(arg0.getAdapter().getItem(arg2).toString()==myFavor[0].toString())
        {
          /*取得網址並呼叫goToUrl()方法*/
          myUrl=getResources().getString(R.string.str_url1);
          goToUrl(myUrl);
        }
        /*若所選選單的文字與myFavor字串陣列第二個文字相同*/ 
        else if (arg0.getAdapter().getItem(arg2).toString()==myFavor[1].toString())
        {
          /*取得網址並呼叫goToUrl()方法*/
          myUrl=getResources().getString(R.string.str_url2);
          goToUrl(myUrl);
        } 
        /*若所選選單的文字與myFavor字串陣列第三個文字相同*/ 
        else if (arg0.getAdapter().getItem(arg2).toString()==myFavor[2].toString())
        {
          /*取得網址並呼叫goToUrl()方法*/
          myUrl=getResources().getString(R.string.str_url3);
          goToUrl(myUrl);
        } 
        /*若所選選單的文字與myFavor字串陣列第四個文字相同*/ 
        else if (arg0.getAdapter().getItem(arg2).toString()==myFavor[3].toString())
        {
          /*取得網址並呼叫goToUrl()方法*/
          myUrl=getResources().getString(R.string.str_url4);
          goToUrl(myUrl);
        } 
        /*以上皆非*/
        else
        {
          /*顯示錯誤訊息*/
          mTextView1.setText("Ooops!!出錯了");
        } 
      }
    }); 
  } 
    /*開啟網頁的方法*/
    private void goToUrl(String url)
    {
      Uri uri = Uri.parse(url); 
      Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
      startActivity(intent); 
    }
} 
