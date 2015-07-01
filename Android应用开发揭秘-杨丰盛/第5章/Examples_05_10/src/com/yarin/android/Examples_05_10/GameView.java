package com.yarin.android.Examples_05_10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements Runnable
{
    int BACKWIDTH;

    int BACKHEIGHT;

    short[] buf2;

    short[] buf1;

    int[] Bitmap2;

    int[] Bitmap1;
    
	public GameView(Context context)
	{
		super(context);
		
		/* 装载图片 */
    	Bitmap 		image = BitmapFactory.decodeResource(this.getResources(),R.drawable.qq);
    	BACKWIDTH = image.getWidth();
    	BACKHEIGHT = image.getHeight();
    	
        buf2 = new short[BACKWIDTH * BACKHEIGHT];
        buf1 = new short[BACKWIDTH * BACKHEIGHT];
        Bitmap2 = new int[BACKWIDTH * BACKHEIGHT];
        Bitmap1 = new int[BACKWIDTH * BACKHEIGHT];

        /* 加载图片的像素到数组中 */
        image.getPixels(Bitmap1, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT);
		
		new Thread(this).start();
	}
	
	
    void DropStone(int x,// x坐标
				   int y,// y坐标
				   int stonesize,// 波源半径
				   int stoneweight)// 波源能量
	{
		for (int posx = x - stonesize; posx < x + stonesize; posx++)
			for (int posy = y - stonesize; posy < y + stonesize; posy++)
				if ((posx - x) * (posx - x) + (posy - y) * (posy - y) < stonesize * stonesize)
					buf1[BACKWIDTH * posy + posx] = (short) -stoneweight;
	}
    
    
    void RippleSpread()
	{
		for (int i = BACKWIDTH; i < BACKWIDTH * BACKHEIGHT - BACKWIDTH; i++)
		{
			// 波能扩散
			buf2[i] = (short) (((buf1[i - 1] + buf1[i + 1] + buf1[i - BACKWIDTH] + buf1[i + BACKWIDTH]) >> 1) - buf2[i]);
			// 波能衰减
			buf2[i] -= buf2[i] >> 5;
		}

		// 交换波能数据缓冲区
		short[] ptmp = buf1;
		buf1 = buf2;
		buf2 = ptmp;
	}

    /* 渲染你水纹效果 */
	void render()
	{
		int xoff, yoff;
		int k = BACKWIDTH;
		for (int i = 1; i < BACKHEIGHT - 1; i++)
		{
			for (int j = 0; j < BACKWIDTH; j++)
			{
				// 计算偏移量
				xoff = buf1[k - 1] - buf1[k + 1];
				yoff = buf1[k - BACKWIDTH] - buf1[k + BACKWIDTH];

				// 判断坐标是否在窗口范围内
				if ((i + yoff) < 0)
				{
					k++;
					continue;
				}
				if ((i + yoff) > BACKHEIGHT)
				{
					k++;
					continue;
				}
				if ((j + xoff) < 0)
				{
					k++;
					continue;
				}
				if ((j + xoff) > BACKWIDTH)
				{
					k++;
					continue;
				}

				// 计算出偏移象素和原始象素的内存地址偏移量
				int pos1, pos2;
				pos1 = BACKWIDTH * (i + yoff) + (j + xoff);
				pos2 = BACKWIDTH * i + j;
				Bitmap2[pos2++] = Bitmap1[pos1++];
				k++;
			}
		}
	}
    
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* 绘制经过处理的图片效果 */
		canvas.drawBitmap(Bitmap2, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT, false, null);
	}
	
	// 触笔事件
	public boolean onTouchEvent(MotionEvent event)
	{
		
		return true;
	}


	// 按键按下事件
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}


	// 按键弹起事件
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		DropStone(BACKWIDTH/2, BACKHEIGHT/2, 10, 30);
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
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
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
	        RippleSpread();
	        render();
			//使用postInvalidate可以直接在线程中更新界面
			postInvalidate();
		}
	}
}

