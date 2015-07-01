package wyf.wpf;			//声明包语句
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class SensorSimpleActivity extends Activity 
{
	//SensorManager对象引用
	//SensorManager mySensorManager;	
	//使用SensorSimulator模拟时声明SensorSensorManager对象引用的方法
	SensorManagerSimulator mySensorManager;		
	
	TextView tvX;	//TextView对象引用	
	TextView tvY;	//TextView对象引用	
	TextView tvZ;	//TextView对象引用	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);		
        tvY = (TextView)findViewById(R.id.tvY);		
        tvZ = (TextView)findViewById(R.id.tvZ);		
        
        //获得SensorManager对象
        //mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	
        
        
        //使用SensorSimulator模拟时声明SensorSensorManager对象引用的方法
        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mySensorManager.connectSimulator();
    }
	@Override
	protected void onResume() {						//重写onResume方法
		mySensorManager.registerListener(			//注册监听器
				mySensorListener, 					//监听器对象
				SensorManager.SENSOR_ACCELEROMETER,	//传感器类型
				SensorManager.SENSOR_DELAY_UI		//传感器事件传递的频度
				);
		super.onResume();
	}	
	@Override
	protected void onPause() {									//重写onPause方法
		mySensorManager.unregisterListener(mySensorListener);	//取消注册监听器
		super.onPause();
	}
	//开发实现了SensorEventListener接口的传感器监听器
	private SensorListener mySensorListener = new SensorListener(){
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) 
		{	
		}
		@Override
		public void onSensorChanged(int sensor, float[] values) 
		{
			if(sensor == SensorManager.SENSOR_ACCELEROMETER)
			{//判断是否为加速度传感器变化产生的数据	
				//将提取的数据显示到TextView
				tvX.setText("x轴方向上的加速度为："+values[0]);		
				tvY.setText("y轴方向上的加速度为："+values[1]);		
				tvZ.setText("z轴方向上的加速度为："+values[2]);		
			}	
		}		
	};
}