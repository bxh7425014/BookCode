package com.xmobileapp.DroidGPS;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DroidGPS extends Activity {
	private static final String TIME_INTER = "TimeInter";
	private LinearLayout llmain ;
	private Button btn_new_track,btn_list,btn_status;
	private final static int wrap = LinearLayout.LayoutParams.WRAP_CONTENT;
	private final static int fill = LinearLayout.LayoutParams.FILL_PARENT;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        llmain = new LinearLayout(this);
        llmain.setBackgroundResource(R.drawable.backg);
        llmain.setOrientation(LinearLayout.VERTICAL);
        
        int iWidth = 160;
        int iHeight = 50 ;
        
        btn_new_track = new Button(this);
        btn_new_track.setText("New Track");
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(iWidth,iHeight);
        /*���mButton����m*/
        lp1.leftMargin=80;
        lp1.topMargin=100;
        llmain.addView(btn_new_track, lp1);
        
        btn_list = new Button(this);
        btn_list.setText("Track List");
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(iWidth,iHeight);
        lp2.leftMargin=80;
        lp2.topMargin=40;
        llmain.addView(btn_list, lp2);
        
        btn_status = new Button(this);
        btn_status.setText("GPS Status");
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(iWidth,iHeight);
        lp4.leftMargin=80;
        lp4.topMargin=40;

        llmain.addView(btn_status, lp4);
        
        setContentView(llmain);
        		
        btn_new_track.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				showDialog();
			}      	
        });
        btn_list.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
				Intent i = new Intent(DroidGPS.this,TrackList.class);
				startActivity(i);
			}     	
        });
        btn_status.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
				Intent i = new Intent(DroidGPS.this,GPSStatus.class);
				startActivity(i);
			}
        });
    }
    private void showDialog()
    {
    	new AlertDialog.Builder(this)
    	.setIcon(R.drawable.ic_menu_info_details)
    	.setTitle(getResources().getString(R.string.str_dialog_title))
    	.setItems(getResources().getStringArray(R.array.category), new DialogInterface.OnClickListener()
    	{
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(DroidGPS.this,mapactivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt(TIME_INTER, which);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}  		
    	})
    	.setNegativeButton(getResources().getString(R.string.str_cancel),new DialogInterface.OnClickListener()
    	{
			public void onClick(DialogInterface dialog, int which) {
			}
    	})
    	.setCancelable(true)
    	.create()
    	.show()
    	;
    }
}