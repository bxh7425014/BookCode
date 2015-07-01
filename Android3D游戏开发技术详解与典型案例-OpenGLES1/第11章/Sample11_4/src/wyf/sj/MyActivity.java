package wyf.sj;
import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        
        setContentView(mGLSurfaceView);        
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }    
}