package irdc.ex04_22;

/* import相關class */
import java.io.File;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EX04_22 extends Activity
{
  /*宣告物件變數*/
  private ImageView mImageView;
  private Button mButton;
  private TextView mTextView;
  private String fileName="/data/data/irdc.ex04_22/ex04_22_2.png";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 取得Button物件，並加入onClickListener */
    mButton = (Button)findViewById(R.id.mButton);
    mButton.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v)
      {
    	/* 取得物件 */
        mImageView = (ImageView)findViewById(R.id.mImageView);
        mTextView=(TextView)findViewById(R.id.mTextView);
        /* 檢查檔案是否存在 */
        File f=new File(fileName);   
        if(f.exists()) 
        { 
        	/* 產生Bitmap物件，並放入mImageView中 */
          Bitmap bm = BitmapFactory.decodeFile(fileName);
          mImageView.setImageBitmap(bm);
          mTextView.setText(fileName); 
        } 
        else 
        {  
          mTextView.setText("檔案不存在"); 
        } 
      } 
    });
  }
}