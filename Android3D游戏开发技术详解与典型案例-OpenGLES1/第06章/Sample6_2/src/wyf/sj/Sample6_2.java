package wyf.sj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Sample6_2 extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView mGLSurfaceView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); 
        ll.addView(mGLSurfaceView);
        
        ToggleButton tb=(ToggleButton)this.findViewById(R.id.ToggleButton01);
        tb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
	 			@Override
	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
	 			{
	 				mGLSurfaceView.setSmoothFlag(!mGLSurfaceView.isSmoothFlag());
	 			}        	   
            }        
        );
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