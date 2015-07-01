package wyf.jc;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SensorBallActivity extends Activity {
	
	//=========================sensor begin============================
	//SensorManager对象引用
	//SensorManager mySensorManager;	
	//使用SensorSimulator模拟时声明SensorSensorManager对象引用的方法
	SensorManagerSimulator mySensorManager;	
	//=========================sensor end==============================
	
	static int bx;
	static int by;
	
	MyGameView msv;
	//开发实现了SensorEventListener接口的传感器监听器
	private SensorListener mySensorListener = new SensorListener(){
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) 
		{	
		}
		@Override
		public void onSensorChanged(int sensor, float[] values) 
		{
			if(sensor == SensorManager.SENSOR_ORIENTATION)
			{//判断是否为加速度传感器变化产生的数据	
				int directionDotXY[]=RotateUtil.getDirectionDot
				(
						new double[]{values[0],values[1],values[2]}
			    );
				//标准化xy位移量
				double mLength=directionDotXY[0]*directionDotXY[0]+
				            directionDotXY[1]*directionDotXY[1];
				mLength=Math.sqrt(mLength);
				msv.dx=(int)((directionDotXY[0]/mLength)*msv.dLength);
				msv.dy=(int)((directionDotXY[1]/mLength)*msv.dLength);
			}	
		}		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		//=========================sensor begin============================
		//获得SensorManager对象
        //mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	        
        
        //使用SensorSimulator模拟时声明SensorSensorManager对象引用的方法
        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mySensorManager.connectSimulator();
        //=========================sensor end==============================
        
        msv = new MyGameView(this);
        this.setContentView(msv);       
        
    }
    
	@Override
	protected void onResume() {						//重写onResume方法
		mySensorManager.registerListener(			//注册监听器
				mySensorListener, 					//监听器对象
				SensorManager.SENSOR_ORIENTATION,	//传感器类型
				SensorManager.SENSOR_DELAY_UI		//传感器事件传递的频度
				);
		msv.gvdt.pauseFlag=false;
		msv.ballX=bx;
		msv.ballY=by;
		super.onResume();
	}
	
	@Override
	protected void onPause() {									//重写onPause方法
		mySensorManager.unregisterListener(mySensorListener);	//取消注册监听器
		msv.gvdt.pauseFlag=true;
		bx=msv.ballX;
		by=msv.ballY;
		super.onPause();
	}
}