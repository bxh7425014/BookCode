package com.iceskysl.TestServiceHolder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestServiceHolder extends Activity {
	private boolean _isBound;
	private TestService _boundService;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("Service Test");

		initButtons();

	}
	
    private ServiceConnection _connection = new ServiceConnection() {  
        public void onServiceConnected(ComponentName className, IBinder service) {           
            _boundService = ((TestService.LocalBinder)service).getService();  
              
            Toast.makeText(TestServiceHolder.this, "Service connected",  
                    Toast.LENGTH_SHORT).show();  
        }  
  
        public void onServiceDisconnected(ComponentName className) {  
            // unexpectedly disconnected,we should never see this happen.  
            _boundService = null;  
            Toast.makeText(TestServiceHolder.this, "Service connected",  
                    Toast.LENGTH_SHORT).show();  
        }  
    };  
    
    private void initButtons() {  
        Button buttonStart = (Button) findViewById(R.id.start_service);  
        buttonStart.setOnClickListener(new OnClickListener() {  
            public void onClick(View arg0) {  
                startService();  
            }  
        });  
  
        Button buttonStop = (Button) findViewById(R.id.stop_service);  
        buttonStop.setOnClickListener(new OnClickListener() {  
            public void onClick(View arg0) {  
                stopService();  
            }  
        });  
  
        Button buttonBind = (Button) findViewById(R.id.bind_service);  
        buttonBind.setOnClickListener(new OnClickListener() {  
            public void onClick(View arg0) {  
                bindService();  
            }  
        });  
  
        Button buttonUnbind = (Button) findViewById(R.id.unbind_service);  
        buttonUnbind.setOnClickListener(new OnClickListener() {  
            public void onClick(View arg0) {  
                unbindService();  
            }  
        });  
    }  
  
    private void startService() {  
        Intent i = new Intent(this, TestService.class);  
        this.startService(i);  
    }  
    
    private void stopService() {  
        Intent i = new Intent(this, TestService.class);  
        this.stopService(i);  
    }  
  
    private void bindService() {  
        Intent i = new Intent(this, TestService.class);  
         bindService(i, _connection, Context.BIND_AUTO_CREATE);  
         _isBound = true;  
    }  
  
    private void unbindService() {  
        if (_isBound) {  
            unbindService(_connection);  
            _isBound = false;  
        }  
    }  
}