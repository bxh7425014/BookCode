package org.crazyit.io;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class AddGesture extends Activity
{
	EditText editText;
	GestureOverlayView gestureView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取文本编辑框
		editText = (EditText) findViewById(R.id.gesture_name);
		// 获取手势编辑视图
		gestureView = (GestureOverlayView) findViewById(R.id.gesture);
		// 设置手势的绘制颜色
		gestureView.setGestureColor(Color.RED);
		// 设置手势的绘制宽度
		gestureView.setGestureStrokeWidth(4);
		// 为gesture的手势完成事件绑定事件监听器
		gestureView.addOnGesturePerformedListener(
			new OnGesturePerformedListener()
			{
				@Override
				public void onGesturePerformed(GestureOverlayView overlay,
					final Gesture gesture)
				{
					//加载save.xml界面布局代表的视图
					View saveDialog = getLayoutInflater().inflate(
						R.layout.save, null);
					// 获取saveDialog里的show组件
					ImageView imageView = (ImageView) saveDialog
						.findViewById(R.id.show);
					// 获取saveDialog里的gesture_name组件
					final EditText gestureName = (EditText) saveDialog
						.findViewById(R.id.gesture_name);
					// 根据Gesture包含的手势创建一个位图
					Bitmap bitmap = gesture.toBitmap(128, 128, 10, 0xFFFF0000);
					imageView.setImageBitmap(bitmap);
					//使用对话框显示saveDialog组件
					new AlertDialog.Builder(AddGesture.this)
						.setView(saveDialog)
						.setPositiveButton("保存", new OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
								int which)
							{
								// 获取指定文件对应的手势库
								GestureLibrary gestureLib = GestureLibraries
									.fromFile("/sdcard/mygestures");
								// 添加手势
								gestureLib.addGesture(gestureName.getText().toString(),
									gesture);
								// 保存手势库
								gestureLib.save();
							}
						})
						.setNegativeButton("取消", null)
						.show();
					}
			});
	}
}