package com.bn.Sample19_3;

import static com.bn.Sample19_3.MySurfaceView.*;
import static com.bn.Sample19_3.Sample19_3Activity.*;

public class KeyThread extends Thread
{
	//键盘状态  1-up 2-down 4-left 8-right
    int keyState=0;
	boolean flag=true;
	@Override
	public void run()
	{
		while(flag)
		{
			if((keyState&0x1)!=0) 
			{//按下向前运动的键
				xOffset-=0.3f;
			}
			else if((keyState&0x2)!=0)
			{//按下向后运动的键
				xOffset+=0.3f;
			}
			if((keyState&0x4)!=0)
			{//按下向左运动的键
				yAngle+=2.5f;
			}
			else if((keyState&0x8)!=0)
			{//按下向右运动的键
				yAngle-=2.5f;
			}
			if(yAngle>=360||yAngle<=-360)
			{
				yAngle=0;
			}
			try
			{
				Thread.sleep(30);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	//检测按键，更改比特位
	public void keyPress(float x,float y)
	{
		if(x>=0&&x<screenWidth/2&&y>=0&&y<screenHeight/2)
		{//向前
			keyState=keyState|0x1;
		}
		else if(x>=screenWidth/2&&x<screenWidth&&y>=0&&y<screenHeight/2)
		{//向后
			keyState=keyState|0x2;
		}
		else if(x>=0&&x<screenWidth/2&&y>=screenHeight/2&&y<screenHeight)
		{//向左
			keyState=keyState|0x4;
		}
		else if(x>=screenWidth/2&&x<=screenWidth&&y>=screenHeight/2&&y<=screenHeight)
		{//向右
			keyState=keyState|0x8;
		}
	}
	//抬起按键时调用的方法
	public void keyUp(float x,float y)
	{
		if(x>=0&&x<screenWidth/2&&y>=0&&y<screenHeight/2)
		{//向前
			keyState=keyState&0xE;
		}
		else if(x>=screenWidth/2&&x<screenWidth&&y>=0&&y<screenHeight/2)
		{//向后
			keyState=keyState&0xD;
		}
		else if(x>=0&&x<screenWidth/2&&y>=screenHeight/2&&y<screenHeight)
		{//向左
			keyState=keyState&0xB;
		}
		else if(x>=screenWidth/2&&x<=screenWidth&&y>=screenHeight/2&&y<=screenHeight)
		{//向右
			keyState=keyState&0x7;
		}
	}
	//所有触控点均抬起时调用的方法，即将keyState值赋值为0
	public void clearKeyState()
	{
		keyState=keyState&0x0;
	}
}