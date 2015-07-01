package irdc.ex05_08;

/* import相關class */
import android.app.Activity; 
import android.os.Bundle; 
import android.widget.Toast;

/* 當user點選Notification留言條時，會執行的Activity */ 
public class EX05_08_1 extends Activity 
{ 
  @Override 
  protected void onCreate(Bundle savedInstanceState)
  {  
    super.onCreate(savedInstanceState); 
    
    /* 發出Toast */
    Toast.makeText(EX05_08_1.this,
                   "這是模擬MSN切換登入狀態的程式",
                   Toast.LENGTH_SHORT
                  ).show();
    finish();
  } 
} 