package wyf.zcl;
/*
 * 	该例子演示surfaceView中简单场景的绘制
 * 	MyActivity.java		为程序的主Activity
 * 	MySurfaceView.java	为程序的SurfaceView类
 * 	Constant.java		常量类，将常量全部写在该类中
 * 	OnDrawThread.java	该类的作用是时时刷新onDraw，进行画面的重绘
 * 	PicRunThread.java	该类是控制duke图片运动的类
 * */
import android.app.Activity;						//引入相关包
import android.content.pm.ActivityInfo;				//引入相关包
import android.os.Bundle;							//引入相关包
import android.view.Window;							//引入相关包
import android.view.WindowManager;					//引入相关包	
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private MySurfaceView msv;			//得到surfaceView的引用
    @Override
    public void onCreate(Bundle savedInstanceState) {	//Activity的生命周期函数，该函数是在程序创建时调用
        super.onCreate(savedInstanceState);
        msv=new MySurfaceView(MyActivity.this);			//实例化MySurfaceView的对象
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//设置屏幕显示没有title栏 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);	//设置全屏
		//设置只允许横屏
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(msv);							//设置Activity显示的内容为msv
    }
}