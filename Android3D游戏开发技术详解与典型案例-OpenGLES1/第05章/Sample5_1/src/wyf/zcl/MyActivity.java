package wyf.zcl;
import android.app.Activity;					//引入相关包
import android.os.Bundle;					//引入相关包
import android.widget.CompoundButton;					//引入相关包
import android.widget.LinearLayout;					//引入相关包
import android.widget.ToggleButton;					//引入相关包
import android.widget.CompoundButton.OnCheckedChangeListener;					//引入相关包
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView msv;
	ToggleButton tb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msv=new MySurfaceView(this);				//实例化MySurfaceView
        setContentView(R.layout.main);				//设置Acitivity的内容
        msv.requestFocus();							//获取焦点
        msv.setFocusableInTouchMode(true);			//设置为可触控
        LinearLayout lla=(LinearLayout)findViewById(R.id.lla);
        lla.addView(msv);							//将SurfaceView加入LinearLayout中
     tb=(ToggleButton)findViewById(R.id.ToggleButton01);//添加监听器
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				msv.openLightFlag=!msv.openLightFlag;
		}});}
	@Override
	protected void onPause() {					//当另一个Acitvity遮挡着当前的Activity时调用
		super.onPause();
		msv.onPause();
	}
	@Override
	protected void onResume() {					//当Acitvity获得用户焦点时调用
		super.onResume();
		msv.onResume();
	}
}