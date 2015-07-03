package com.guo.alertdialog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlertDialogReflactionActivity extends Activity {
	//对话框
	 AlertDialog alertDialog = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //新建一个对话框
        Builder mBuilder=new AlertDialog.Builder(this); 
        mBuilder.setMessage("Hello!");
        mBuilder.setTitle("提示！");
        mBuilder.setNegativeButton("关闭", new myListener());
        mBuilder.setPositiveButton("确定", new myListener());
        alertDialog= mBuilder.create();
        try {
        	//获得mAlert私有变量
			Field mfield=alertDialog.getClass().getDeclaredField("mAlert");
			mfield.setAccessible(true);
			//获得mAlert变量在alertDialog的值
			Object obj=mfield.get(alertDialog);
			mfield=obj.getClass().getDeclaredField("mHandler");
			//设置是否检查使用权限，true表示不检查
			mfield.setAccessible(true);
			//设置特定的obj中mfield的值
			mfield.set(obj, new MyHandler(alertDialog));
		} catch (Exception e) {
			e.printStackTrace();
		}
		alertDialog.show();
    }
    class myListener implements DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch(which)
			{
			//确定按钮
				case DialogInterface.BUTTON_POSITIVE:
					dialog.dismiss();
					break;
			//取消按钮
				case DialogInterface.BUTTON_NEGATIVE:
					break;
			}
		}   	
    }
    //自定义的处理器
    private class MyHandler extends Handler{
    private static final int MSG_DISMISS_DIALOG = 1;
    private WeakReference<DialogInterface> mDialog;
    public MyHandler(DialogInterface dialog){
    	mDialog=new WeakReference<DialogInterface>(dialog);
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case DialogInterface.BUTTON_POSITIVE:
            case DialogInterface.BUTTON_NEGATIVE:
            case DialogInterface.BUTTON_NEUTRAL:
            	((DialogInterface.OnClickListener) msg.obj).onClick(mDialog.get(), msg.what);
                break;
                //此处去掉了隐藏对话框的操作
            case MSG_DISMISS_DIALOG:    
            	break;
        	}
    	} 
    };
}