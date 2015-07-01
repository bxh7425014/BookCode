package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.media.MediaPlayer;

public class DrawCountdown extends BNShape
{
	//倒计时板的高和宽
	private static float countdown_Height=20;
	private static float countdown_Width=20;
	
	static float Countdown_Z_Offset=-20;//倒计时牌起始位置
	
	static int PictureFlag=3;//绘制倒计时板的标志位
	static boolean flag=true;//线程控制标志位，默认为true
	
	Countdown daojishi;//倒计时板
	static ThreadGo go;//运动绘制线程
	
	public DrawCountdown(float scale)
	{
		super(scale);
		daojishi=new Countdown(scale);//倒计时牌
		go=new ThreadGo();
	}
	
	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
    	gl.glPushMatrix();
    	
        if(PictureFlag==3)
        {
            gl.glPushMatrix();//绘制3
            gl.glTranslatef(0, 0, Countdown_Z_Offset);
            daojishi.drawSelf(gl, texId, 2);
            gl.glPopMatrix();
        }
        else if(PictureFlag==2)
        {
            gl.glPushMatrix();//绘制2
            gl.glTranslatef(0, 0, Countdown_Z_Offset);
            daojishi.drawSelf(gl, texId, 1);
            gl.glPopMatrix();
        }
        else if(PictureFlag==1)
        {
            gl.glPushMatrix();//绘制1
            gl.glTranslatef(0, 0, Countdown_Z_Offset);
            daojishi.drawSelf(gl, texId, 0);
            gl.glPopMatrix();
        }
        else if(PictureFlag==0)
        {
            gl.glPushMatrix();//绘制GO
            gl.glTranslatef(0, 0, Countdown_Z_Offset);
            daojishi.drawSelf(gl, texId, 3);
            gl.glPopMatrix();
        }
        
        gl.glPopMatrix();
	}
	

	//绘制倒计时板内部类
	//点内部类
	private class Countdown
	{ 
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer[] textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Countdown(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-countdown_Width/2*scale,countdown_Height/2*scale,0,
										-countdown_Width/2*scale,-countdown_Height/2*scale,0,
										countdown_Width/2*scale,countdown_Height/2*scale,0,
										
										-countdown_Width/2*scale,-countdown_Height/2*scale,0,
										countdown_Width/2*scale,-countdown_Height/2*scale,0,
										countdown_Width/2*scale,countdown_Height/2*scale,0									
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[][]textures=new float[][]
			                              {
					{
					 0,0,
					 0,1,
					 0.25f,0,
					 
					 0,1,
					 0.25f,1,
					 0.25f,0	
					},
					{
					 0.25f,0,
					 0.25f,1,
					 0.5f,0,
					 
					 0.25f,1,
					 0.5f,1,
					 0.5f,0
					},
					{
					 0.5f,0,
					 0.5f,1,
					 0.75f,0,
					 
					 0.5f,1,
					 0.75f,1,
					 0.75f,0
					},
					{
					 0.75f,0,
					 0.75f,1,
					 1,0,
					 
					 0.75f,1,
					 1,1,
					 1,0
					}
			                              };
			textureBuffer=new FloatBuffer[4];
			for(int i=0;i<textures.length;i++)
			{
				ByteBuffer tbb=ByteBuffer.allocateDirect(textures[i].length*4);
				tbb.order(ByteOrder.nativeOrder());
				textureBuffer[i]=tbb.asFloatBuffer();
				textureBuffer[i].put(textures[i]);
				textureBuffer[i].position(0);
			}		
		}
		public void drawSelf(GL10 gl,int texId,int number) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);		
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer[number]);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭顶点数组
		}
	}
	
	
	//倒计时牌播放线程
	class ThreadGo extends Thread
	{ 
		public void run()
		{			
			while(flag)
			{
				if(Activity_GL_Racing.soundFlag==true&&PictureFlag==3&&Countdown_Z_Offset==-20)//播放交通灯声音
				{					
					MyGLSurfaceView.activity.playSound(8, 0);//交通灯声音
				}
				
				Countdown_Z_Offset=Countdown_Z_Offset+0.3f;
				if(Countdown_Z_Offset>-11f)
				{
					Countdown_Z_Offset=-20;//位置还原 
					PictureFlag=PictureFlag-1;//换下一张图，绘制20
					
					if(Activity_GL_Racing.soundFlag==true&&PictureFlag>0)//播放交通灯声音
					{					
						MyGLSurfaceView.activity.playSound(8, 0);//交通灯声音
					}
					else if(Activity_GL_Racing.soundFlag==true&&PictureFlag==0)
					{
						MyGLSurfaceView.activity.playSound(1, 0);//交通灯声音
					}
				}
				if(PictureFlag<0)//当倒计时完后，结束线程
				{
					MyGLSurfaceView.daojishiFlag=false;//不绘制倒计时
					flag=false;
					
					MyGLSurfaceView.timeFlag=true;//开始计时
					MyGLSurfaceView.gameStartTime=System.currentTimeMillis();//记录游戏开始时间
					MyGLSurfaceView.benquanStartTime=MyGLSurfaceView.gameStartTime;//记录游戏本圈开始时间	
										
					Activity_GL_Racing.inGame=true;//表示当前状态为在游戏中
					Activity_GL_Racing.keyFlag=true;//设置场景可控	
					
					if(Activity_GL_Racing.soundFlag==true&&Activity_GL_Racing.inGame==true)//当在游戏中，并且音效为可开启状态时，开启声音
					{						
						Activity_GL_Racing.mpBack.stop();
						Activity_GL_Racing.mpBack=MediaPlayer.create(MyGLSurfaceView.activity,R.raw.backsound); 
						Activity_GL_Racing.mpBack.setLooping(true);//循环播放
						Activity_GL_Racing.mpBack.start();//背景音播放 
					}
				}
				try
				{  
					Thread.sleep(60);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}





