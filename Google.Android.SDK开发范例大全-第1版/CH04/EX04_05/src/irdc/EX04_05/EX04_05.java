package irdc.EX04_05;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class EX04_05 extends Activity 
{
  /*宣告物件變數*/
  private TextView mTextView1;
  private CheckBox mCheckBox1;
  private CheckBox mCheckBox2;
  private CheckBox mCheckBox3;
  
  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      /*透過findViewById取得TextView物件並調整文字內容*/
      mTextView1 = (TextView) findViewById(R.id.myTextView1); 
      mTextView1.setText("你所選擇的項目有: "); 
      
      /*透過findViewById取得三個CheckBox物件*/
      mCheckBox1=(CheckBox)findViewById(R.id.myCheckBox1);
      mCheckBox2=(CheckBox)findViewById(R.id.myCheckBox2);
      mCheckBox3=(CheckBox)findViewById(R.id.myCheckBox3);
      
      /*設定OnCheckedChangeListener給三個CheckBox物件*/
      mCheckBox1.setOnCheckedChangeListener(mCheckBoxChanged);
      mCheckBox2.setOnCheckedChangeListener(mCheckBoxChanged);
      mCheckBox3.setOnCheckedChangeListener(mCheckBoxChanged);
      } 
      
      /*宣告並建構onCheckedChangeListener物件*/ 
      private CheckBox.OnCheckedChangeListener mCheckBoxChanged 
      = new CheckBox.OnCheckedChangeListener() 
      { 
        /*implement onCheckedChanged方法*/
        @Override 
        public void onCheckedChanged( CompoundButton buttonView,
            boolean isChecked) 
        { 
          // TODO Auto-generated method stub 
          /*透過getString()取得CheckBox的文字字串*/
          String str0="所選的項目為: ";
          String str1=getString(R.string.str_checkbox1);
          String str2=getString(R.string.str_checkbox2);
          String str3=getString(R.string.str_checkbox3);
          String plus=";";
          String result="但是超過預算囉!!";
          String result2="還可以再多買幾本喔!!";
          
          /*任一CheckBox被勾選後,該CheckBox的文字會改變TextView的文字內容
           * 三個物件總共八種情境*/
          if(mCheckBox1.isChecked()==true & mCheckBox2.isChecked()==true
              & mCheckBox3.isChecked()==true) 
          { 
            mTextView1.setText(str0+str1+plus+str2+plus+str3+result);
          } 
          else if(mCheckBox1.isChecked()==false & mCheckBox2.isChecked()==true
              & mCheckBox3.isChecked()==true) 
          { 
            mTextView1.setText(str0+str2+plus+str3+result);
          } 
          else if(mCheckBox1.isChecked()==true & mCheckBox2.isChecked()==false
              & mCheckBox3.isChecked()==true) 
          { 
            mTextView1.setText(str0+str1+plus+str3+result);
          } 
          else if(mCheckBox1.isChecked()==true & mCheckBox2.isChecked()==true
              & mCheckBox3.isChecked()==false) 
          { 
            mTextView1.setText(str0+str1+plus+str2+result);
          } 
          else if(mCheckBox1.isChecked()==false & mCheckBox2.isChecked()==false
              & mCheckBox3.isChecked()==true) 
          { 
            mTextView1.setText(str0+str3+plus+result2);
          } 
          else if(mCheckBox1.isChecked()==false & mCheckBox2.isChecked()==true
              & mCheckBox3.isChecked()==false) 
          { 
            mTextView1.setText(str0+str2);
          } 
          else if(mCheckBox1.isChecked()==true & mCheckBox2.isChecked()==false
              & mCheckBox3.isChecked()==false) 
          { 
            mTextView1.setText(str0+str1);
          } 
          else if(mCheckBox1.isChecked()==false & mCheckBox2.isChecked()==false
              & mCheckBox3.isChecked()==false) 
          { 
            mTextView1.setText(str0);
          } 
        }
       }; 
} 


