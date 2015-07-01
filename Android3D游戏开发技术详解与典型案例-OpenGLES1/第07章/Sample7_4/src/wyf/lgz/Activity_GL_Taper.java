package wyf.lgz;

import android.app.Activity;
import android.os.Bundle;

public class Activity_GL_Taper extends Activity {
	private MyGLSurfaceView mGLSurfaceView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        mGLSurfaceView = new MyGLSurfaceView(this);
        setContentView(mGLSurfaceView);
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        mGLSurfaceView.requestFocus();//获取焦点
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