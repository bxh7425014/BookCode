package com.yarin.android.HelloNDK;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
public class HelloNDK extends Activity
{
	//装载动态库“libHello.so”
    static 
    {
        System.loadLibrary("HelloNDK");
    }
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Jni jni = new Jni();
		TextView textView = new TextView(this);
		//调用原生方法
		textView.setText(jni.getCString()+Integer.toString(jni.getCInt()));
		setContentView(textView);
	}
}