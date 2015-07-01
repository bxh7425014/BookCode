package irdc.ex04_16;

/* import相關class */
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class EX04_16 extends Activity
{
  /*宣告物件變數*/
  private ImageView mImageView01;
  private ImageView mImageView02;
  private ImageView mImageView03;
  private Button mButton;
  private TextView mText;
  /*宣告長度為3的int陣列，並將三張牌的id放入
    R.drawable.p01：紅心A
    R.drawable.p02：黑桃2
    R.drawable.p03：梅花3
    R.drawable.p04：撲克牌背面*/
  private static int[] s1=new int[]{R.drawable.p01,R.drawable.p02,R.drawable.p03};
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    /* 取得相關物件 */
    mText=(TextView)findViewById(R.id.mText);
    mImageView01=(ImageView)findViewById(R.id.mImage01);
    mImageView02=(ImageView)findViewById(R.id.mImage02);
    mImageView03=(ImageView)findViewById(R.id.mImage03);
    mButton=(Button)findViewById(R.id.mButton);
    /* 執行洗牌程式 */
    randon();
    /* 替mImageView01加入onClickListener*/
    mImageView01.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 三張牌同時翻面，並將未選擇的兩張牌變透明 */
        mImageView01.setImageDrawable(getResources().getDrawable(s1[0]));
        mImageView02.setImageDrawable(getResources().getDrawable(s1[1]));
        mImageView03.setImageDrawable(getResources().getDrawable(s1[2]));
        mImageView02.setAlpha(100);
        mImageView03.setAlpha(100);
        /* 依有沒有猜對來決定TextView要顯示的訊息 */
        if(s1[0]==R.drawable.p01){
          mText.setText("哇!你猜對了喔!!拍拍手!");
        }
        else
        {
          mText.setText("你猜錯了喔!!要不要再試一次?");
        }
      }
    });
    
    /* 替mImageView02加入onClickListener*/
    mImageView02.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 三張牌同時翻面，並將未選擇的兩張牌變透明 */
        mImageView01.setImageDrawable(getResources().getDrawable(s1[0]));
        mImageView02.setImageDrawable(getResources().getDrawable(s1[1]));
        mImageView03.setImageDrawable(getResources().getDrawable(s1[2]));
        mImageView01.setAlpha(100);
        mImageView03.setAlpha(100);
        /* 依有沒有猜對來決定TextView要顯示的訊息 */
        if(s1[1]==R.drawable.p01){
          mText.setText("哇!你猜對了喔!!拍拍手!");
        }
        else
        {
          mText.setText("你猜錯了喔!!要不要再試一次?");
        }
      }
    });
    
    /* 替mImageView03加入onClickListener*/
    mImageView03.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 三張牌同時翻面，並將未選擇的兩張牌變透明 */
        mImageView01.setImageDrawable(getResources().getDrawable(s1[0]));
        mImageView02.setImageDrawable(getResources().getDrawable(s1[1]));
        mImageView03.setImageDrawable(getResources().getDrawable(s1[2]));
        mImageView01.setAlpha(100);
        mImageView02.setAlpha(100);
        /* 依有沒有猜對來決定TextView要顯示的訊息 */
        if(s1[2]==R.drawable.p01){
          mText.setText("哇!你猜對了喔!!拍拍手!");
        }
        else
        {
          mText.setText("你猜錯了喔!!要不要再試一次?");
        }
      }
    });
    
    /* 替mButton加入onClickListener，使其按下後三張牌都翻為背面且重新洗牌*/   
    mButton.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v)
      {
        mText.setText("猜猜看紅心A是哪一張?");
        mImageView01.setImageDrawable(getResources()
        		    .getDrawable(R.drawable.p04));
        mImageView02.setImageDrawable(getResources()
        		    .getDrawable(R.drawable.p04));
        mImageView03.setImageDrawable(getResources()
        		    .getDrawable(R.drawable.p04));
        mImageView01.setAlpha(255);
        mImageView02.setAlpha(255);
        mImageView03.setAlpha(255);
        randon();
      }
    });    
  }
  /*重新洗牌的程式*/
  private void randon()
  {
    for(int i=0;i<3;i++)
    {
      int tmp=s1[i];
      int s=(int)(Math.random()*2);
      s1[i]=s1[s];
      s1[s]=tmp;
    }        
  }
}