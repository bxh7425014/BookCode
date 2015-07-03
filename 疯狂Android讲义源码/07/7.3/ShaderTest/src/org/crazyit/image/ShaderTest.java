package org.crazyit.image;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
public class ShaderTest extends Activity
	implements OnClickListener
{
	// 声明位图渲染对象
	private Shader[] shaders = new Shader[5];
	// 声明颜色数组
	private int[] colors;	
	MyView myView;
	// 自定义视图类
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myView = (MyView)findViewById(R.id.my_view);
		// 获得Bitmap实例
		Bitmap bm = BitmapFactory.decodeResource(getResources()
			, R.drawable.water);
		// 设置渐变的颜色组，也就是按红、绿、蓝的方式渐变
		colors = new int[] { Color.RED, Color.GREEN, Color.BLUE };
		// 实例化BitmapShader,x坐标方向重复图形，y坐标方向镜像图形
		shaders[0] = new BitmapShader(bm, TileMode.REPEAT,
			TileMode.MIRROR);
		// 实例化LinearGradient
		shaders[1] = new LinearGradient(0, 0, 100, 100
			, colors, null, TileMode.REPEAT);
		// 实例化RadialGradient
		shaders[2] = new RadialGradient(100, 100, 80, colors, null,
			TileMode.REPEAT);
		// 实例化SweepGradient
		shaders[3] = new SweepGradient(160, 160, colors, null);
		// 实例化ComposeShader
		shaders[4] = new ComposeShader(shaders[1], shaders[2],
			PorterDuff.Mode.DARKEN);
		Button bn1 = (Button)findViewById(R.id.bn1);
		Button bn2 = (Button)findViewById(R.id.bn2);
		Button bn3 = (Button)findViewById(R.id.bn3);
		Button bn4 = (Button)findViewById(R.id.bn4);
		Button bn5 = (Button)findViewById(R.id.bn5);
		bn1.setOnClickListener(this);
		bn2.setOnClickListener(this);
		bn3.setOnClickListener(this);
		bn4.setOnClickListener(this);
		bn5.setOnClickListener(this);
	}
	@Override
	public void onClick(View source)
	{
		switch(source.getId())
		{
			case R.id.bn1:
				myView.paint.setShader(shaders[0]);
				break;
			case R.id.bn2:
				myView.paint.setShader(shaders[1]);
				break;
			case R.id.bn3:
				myView.paint.setShader(shaders[2]);
				break;
			case R.id.bn4:
				myView.paint.setShader(shaders[3]);
				break;
			case R.id.bn5:
				myView.paint.setShader(shaders[4]);
				break;
		}
		// 重绘界面
		myView.invalidate();
	}
}