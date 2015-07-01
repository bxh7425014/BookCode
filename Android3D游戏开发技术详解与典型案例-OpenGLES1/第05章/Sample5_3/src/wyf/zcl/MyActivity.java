package wyf.zcl;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView msv;
	SeekBar sb;								//声明拖拉条引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msv=new MySurfaceView(this);
        setContentView(R.layout.main);
        sb=(SeekBar)findViewById(R.id.SeekBar01);
        msv.requestFocus();					//获取焦点
        msv.setFocusableInTouchMode(true);	//设置为可触控
        LinearLayout lla=(LinearLayout)findViewById(R.id.lla);
        lla.addView(msv);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {		//通过SeekBar来改变定向光的方向
					System.out.println(progress);
					if(progress<50){		//光线在左边
						msv.light0PositionX=-(progress/5);
					}else if(progress>=50){	//光线在右边
						msv.light0PositionX=((progress-50)/5);
	}}});}
	@Override
	protected void onPause() {
		super.onPause();
		msv.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		msv.onResume();
}}