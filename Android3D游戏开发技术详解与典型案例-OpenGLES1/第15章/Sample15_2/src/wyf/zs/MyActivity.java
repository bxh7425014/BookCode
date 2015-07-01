package wyf.zs;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView mySurfaceView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mySurfaceView=new MySurfaceView(this);
        mySurfaceView.requestFocus();
        mySurfaceView.setFocusableInTouchMode(true);
        setContentView(mySurfaceView);
    }
    @Override
    public void onResume()
    {
    	super.onResume();
    	mySurfaceView.onResume();
    }
    @Override
    public void onPause()
    {
    	super.onPause();
    	mySurfaceView.onPause();
    }
}