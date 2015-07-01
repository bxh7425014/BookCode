package com.yarin.android.Examples_05_19;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View implements Runnable
{
	Context		mContext	= null;

	/* 声明GifFrame对象 */
	GifFrame	mGifFrame	= null;

	public GameView(Context context)
	{
		super(context);
		
		mContext = context;
		/* 解析GIF动画 */
		mGifFrame=GifFrame.CreateGifImage(fileConnect(this.getResources().openRawResource(R.drawable.gif1)));
		/* 开启线程 */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		/* 下一帧 */
		mGifFrame.nextFrame();
		/* 得到当前帧的图片 */
		Bitmap b=mGifFrame.getImage();
		
		/* 绘制当前帧的图片 */
		if(b!=null)
			canvas.drawBitmap(b,10,10,null);
	}
	
	
	/**
	 * 线程处理
	 */
	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			//使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
	
	/* 读取文件 */
	public byte[] fileConnect(InputStream is)
	{
		try
		{					    
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch = 0;
			while( (ch = is.read()) != -1) 
			{
				baos.write(ch);
			}			      
			byte[] datas = baos.toByteArray();
			baos.close(); 
			baos = null;
			is.close();
			is = null;
			return datas;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}

