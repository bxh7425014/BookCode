package com.guo.myball;
import javax.microedition.khronos.opengles.GL10;

import static com.guo.myball.Constant.*;
//绘制数字的类
public class Number 
{
	GameSurfaceView mv;
	TextureRect[] numbers=new TextureRect[10];
	String NumberStr;
	public float y;
	public Number(GameSurfaceView mv)
	{
		this.mv=mv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            	 ICON_WIDTH*0.7f/2,
            	 ICON_HEIGHT*0.7f/2,
           		 new float[]
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*i,1, 0.1f*(i+1),1,  0.1f*(i+1),0
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl,int flag,int texId)
	{	
		for(int i=0;i<NumberStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=NumberStr.charAt(flag==1?i:NumberStr.length()-i-1);
			//保存当前状态
			gl.glPushMatrix();
			gl.glTranslatef(flag==1?i*ICON_WIDTH*0.7f:-i*ICON_WIDTH*0.7f,y, 0);
			numbers[c-'0'].drawSelf(gl,texId);
			//恢复成原来的状态
			gl.glPopMatrix();
		}
	}
}