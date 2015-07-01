package wyf.swq;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private MySurfaceView mSurfaceView;//声明MySurfaceView对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mSurfaceView=new MySurfaceView(this);//创建MySurfaceView对象
        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);//获得线性布局的引用
        ll.addView(mSurfaceView);//将MySurfaceView对象放入线性布局中
    } 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSurfaceView.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSurfaceView.onResume();
	}  
}