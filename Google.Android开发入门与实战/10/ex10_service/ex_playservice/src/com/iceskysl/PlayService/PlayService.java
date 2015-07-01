package com.iceskysl.PlayService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayService extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button1 = (Button)findViewById(R.id.start);
        button1.setOnClickListener(startIt);
        Button button2 = (Button)findViewById(R.id.stop);
        button2.setOnClickListener(stopIt);
    }
    
    private OnClickListener startIt = new OnClickListener()
    {
        public void onClick(View v)
        {           
            startService(new Intent("com.iceskysl.PlayService.START_AUDIO_SERVICE"));
        }
    };
   
    private OnClickListener stopIt = new OnClickListener()
    {
        public void onClick(View v)
        {
            stopService(new Intent("com.iceskysl.PlayService.START_AUDIO_SERVICE"));
            finish();           
        }
    };
}