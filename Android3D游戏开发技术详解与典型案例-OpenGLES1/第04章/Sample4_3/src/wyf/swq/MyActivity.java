package wyf.swq;
import android.app.Activity;															//引入相关包
import android.os.Bundle;																//引入相关包
import android.widget.CompoundButton;													//引入相关包
import android.widget.LinearLayout;														//引入相关包
import android.widget.ToggleButton;														//引入相关包
import android.widget.CompoundButton.OnCheckedChangeListener;							//引入相关包
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private MySurfaceView mSurfaceView;													//声明MySurfaceView对象
    @Override
    public void onCreate(Bundle savedInstanceState) {									
        super.onCreate(savedInstanceState);												//继承父类方法
        setContentView(R.layout.main);													//设置布局文件
        mSurfaceView=new MySurfaceView(this);											//创建MySurfaceView对象
        mSurfaceView.requestFocus();													//获取焦点
        mSurfaceView.setFocusableInTouchMode(true);										//设置为可触控
        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);				//获得线性布局的引用
        ll.addView(mSurfaceView);//
        ToggleButton tb01=(ToggleButton)this.findViewById(R.id.ToggleButton01);			//获得第一个开关按钮的引用
        tb01.setOnCheckedChangeListener(new FirstListener());							//为开关按钮注册监听器
        ToggleButton tb02=(ToggleButton)this.findViewById(R.id.ToggleButton02);			//获得第二个开关按钮的引用
        tb02.setOnCheckedChangeListener(new SecondListener());//
        ToggleButton tb03=(ToggleButton)this.findViewById(R.id.ToggleButton03);			//获得第三个开关按钮的引用
        tb03.setOnCheckedChangeListener(new ThirdListener());//
    }
    class FirstListener implements OnCheckedChangeListener{								//声明第一个按钮的监听器
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//重写方法
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setBackFlag(!mSurfaceView.isBackFlag());						//实现功能
		}  	
    }
    class SecondListener implements OnCheckedChangeListener{							//声明第二个按钮的监听器
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//重写方法
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setSmoothFlag(!mSurfaceView.isSmoothFlag());					//实现功能
		}
    }
	class ThirdListener implements OnCheckedChangeListener{								//声明第三个按钮的监听器
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//重写方法
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setSelfCulling(!mSurfaceView.isSelfCulling());					//实现功能
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();																//继承父类的onPause()方法
		mSurfaceView.onPause();															//调用onPause()方法
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();																//继承父类的onResume()方法
		mSurfaceView.onResume();														//调用onResume()方法
	}  
}