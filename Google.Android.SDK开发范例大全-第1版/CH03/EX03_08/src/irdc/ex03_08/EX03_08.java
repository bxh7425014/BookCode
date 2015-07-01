package irdc.ex03_08;

/* import相關class */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EX03_08 extends Activity 
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    /* 載入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 以findViewById()取得Button物件，並加入onClickListener */
    Button b1 = (Button) findViewById(R.id.button1);
    b1.setOnClickListener(new Button.OnClickListener()
    {
      public void onClick(View v)
      {
    	  jumpToLayout2();
      }
    });
  }
  
  /* method jumpToLayout2：將layout由main.xml切換成mylayout.xml */
  public void jumpToLayout2()
  {
	/* 將layout改成mylayout.xml */
	setContentView(R.layout.mylayout);
	
	/* 以findViewById()取得Button物件，並加入onClickListener */
    Button b2 = (Button) findViewById(R.id.button2);
    b2.setOnClickListener(new Button.OnClickListener()
    {
    public void onClick(View v)
    {
    	jumpToLayout1();
    }
    });
  }
  
  /* method jumpToLayout1：將layout由mylayout.xml切換成main.xml */
  public void jumpToLayout1()
  {
	/* 將layout改成main.xml */
	setContentView(R.layout.main);
    
    /* 以findViewById()取得Button物件，並加入onClickListener */
    Button b1 = (Button) findViewById(R.id.button1);
    b1.setOnClickListener(new Button.OnClickListener()
    {
    public void onClick(View v)
    {
    	jumpToLayout2();
    }
    });
  }
}