package irdc.ex04_11;

/* import相關class */
import java.io.File;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EX04_11 extends Activity
{
  /*宣告物件變數*/
  private Button mButton;
  private EditText mKeyword;
  private TextView mResult;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 初始化物件 */
    mKeyword=(EditText)findViewById(R.id.mKeyword);
    mButton=(Button)findViewById(R.id.mButton);
    mResult=(TextView) findViewById(R.id.mResult);
    
    /* 將mButton加入onClickListener */
    mButton.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v)
      {
    	/*取得輸入的關鍵字*/
        String keyword = mKeyword.getText().toString();
        if(keyword.equals("")){
          mResult.setText("請勿輸入空白的關鍵字!!");
        }else{
      	  mResult.setText(searchFile(keyword));
      	}
      }
    });
  }
  
  /* 搜尋檔案的method */
  private String searchFile(String keyword)
  {
    String result="";
    File[] files=new File("/").listFiles();
    for( File f : files ){
	  if(f.getName().indexOf(keyword)>=0){	
	    result+=f.getPath()+"\n";
	  }
    }
    if(result.equals("")) result="找不到檔案!!";
    return result;
  }
}