package wyf.swq;
import wyf.swq.MySurfaceView;				//引入相关包
import android.app.Activity;				//引入相关包
import android.os.Bundle;					//引入相关包
import android.widget.CompoundButton;		//引入相关包
import android.widget.LinearLayout;			//引入相关包
import android.widget.ToggleButton;			//引入相关包
import android.widget.CompoundButton.OnCheckedChangeListener;	//引入相关包
public class MyActivity extends Activity {
	private MySurfaceView mSurfaceView;			//声明MySurfaceView对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.main);			//设置布局
        mSurfaceView = new MySurfaceView(this);	//创建
        mSurfaceView.requestFocus();//获取焦点	//MySurfaceView对象
        mSurfaceView.setFocusableInTouchMode(true);//设置为可触控  
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); //获得布局引用
        ll.addView(mSurfaceView);//在布局中添加MySurfaceView对象
        //控制是否打开背面剪裁的ToggleButton
        ToggleButton tb=(ToggleButton)this.findViewById(R.id.ToggleButton01);//获得按钮引用
        tb.setOnCheckedChangeListener(new MyListener());        //为按钮设置监听器
    }
    class MyListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
				mSurfaceView.isPerspective=!mSurfaceView.isPerspective;//在正交投影与透视投影之间切换
				mSurfaceView.requestRender();//重新绘制
		}
    }
    @Override
    protected void onResume() {	//
        super.onResume();		//
        mSurfaceView.onResume();//
    }
    @Override
    protected void onPause() {	//
        super.onPause();		//
        mSurfaceView.onPause();	//
    }    
}