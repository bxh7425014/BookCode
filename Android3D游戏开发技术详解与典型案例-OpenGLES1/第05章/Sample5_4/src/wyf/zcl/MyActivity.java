package wyf.zcl;
import android.app.Activity;				//引入相关包
import android.os.Bundle;					//引入相关包
public class MyActivity extends Activity {
	MySurfaceView msv;						//MySurfaceView引用
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);			
        msv=new MySurfaceView(this);		//实例化MySurfaceView对象
        setContentView(msv);				//设置Activity内容
    }
}