package com.supermario.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class HelloAndroidActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String TAG="HelloAndroid";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(TAG,"onCreate!");
    }
}