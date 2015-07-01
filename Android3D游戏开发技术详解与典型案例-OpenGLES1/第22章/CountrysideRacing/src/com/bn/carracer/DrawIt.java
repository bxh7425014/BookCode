package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder; 
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import static com.bn.carracer.Constant.*;

class DrawIt extends BNShape{

	//***************各个部件尺寸声明********************
	public static final float TOTALWIDTH=1.044f;//总长度
	public static final float NUMBERHEIGTH=0.06F;//数字的高度
	public static final float NUMBERWIDTH=0.047f;//数字的宽度
	
	public static final float PICTUREHEIGHT=0.06f;//图片的高度
	public static final float PICTUREWIDTH=0.047f;//图片的宽度
	
	public static final float POINTWIDTH=0.015f;//点的宽度
	//**************声明结束****************************
	public long timeTotal[]=new long[3]; 
	public long timeBenQuan[]=new long[3];
	TimeTotal timeTotals;
	TimeBenQuan timeBen;
	Point point;
	Names names;
	QuanShu quan;
	ZongQuan zongQuan;
	XieGang xieGang;
	public DrawIt(float scale) {
		super(scale);
		timeTotals=new TimeTotal(scale);
		timeBen=new TimeBenQuan(scale);
		point=new Point(scale);
		names=new Names(scale);
		quan=new QuanShu(scale);
		zongQuan=new ZongQuan(scale);
		xieGang=new XieGang(scale);
	}
	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
		//开启混合   
        gl.glEnable(GL10.GL_BLEND); 
        //设置源混合因子与目标混合因子
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPushMatrix();//总时 
		gl.glTranslatef(-TOTALWIDTH/2*scale, 0, 0);
		names.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//分
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH/2-NUMBERWIDTH/2-0.01f)*scale, 0, 0);
		timeTotals.drawSelf(gl, texId,2);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//冒号
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH/2-NUMBERWIDTH*2-POINTWIDTH/2-0.01f)*scale,0,0);
		point.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//秒
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH/2-NUMBERWIDTH*5/2-POINTWIDTH-0.01f)*scale,0,0);
		timeTotals.drawSelf(gl,texId,1);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//小数点
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH/2-NUMBERWIDTH*4-POINTWIDTH*3/2-0.01f)*scale,0,0);
		point.drawSelf(gl, texId, 1);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//（毫）秒
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH/2-NUMBERWIDTH*9/2-POINTWIDTH*2-0.01f)*scale,0,0);
		timeTotals.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//本圈时
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH-NUMBERWIDTH*6-POINTWIDTH*2-0.03f)*scale,0,0);
		names.drawSelf(gl, texId, 1);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//分
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*3/2-NUMBERWIDTH*13/2-POINTWIDTH*2-0.04f)*scale, 0, 0);
		timeBen.drawSelf(gl, texId,2);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//冒号
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*3/2-NUMBERWIDTH*8-POINTWIDTH*5/2-0.04f)*scale,0,0);
		point.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//秒
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*3/2-NUMBERWIDTH*17/2-POINTWIDTH*3-0.04f)*scale,0,0);
		timeBen.drawSelf(gl,texId,1);
		gl.glPopMatrix();
		 
		gl.glPushMatrix();//小数点
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*3/2-NUMBERWIDTH*10-POINTWIDTH*7/2-0.04f)*scale,0,0);
		point.drawSelf(gl, texId, 1);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//（毫）秒
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*3/2-NUMBERWIDTH*21/2-POINTWIDTH*4-0.04f)*scale,0,0);
		timeBen.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//lap
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*2-NUMBERWIDTH*12-POINTWIDTH*4-0.06f)*scale,0,0);
		names.drawSelf(gl, texId, 2);
		gl.glPopMatrix();
		
		//***********
		gl.glPushMatrix();//跑的圈数
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*5/2-NUMBERWIDTH*25/2-POINTWIDTH*4-0.07f)*scale,0,0);
		quan.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//斜杠
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*5/2-NUMBERWIDTH*27/2-POINTWIDTH*4-0.07f)*scale,0,0);
		xieGang.drawSelf(gl, texId, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//总圈数
		gl.glTranslatef(-(TOTALWIDTH/2-PICTUREWIDTH*5/2-NUMBERWIDTH*29/2-POINTWIDTH*4-0.07f)*scale,0,0);
		zongQuan.drawSelf(gl, texId);
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_BLEND);
	}
	public void toBenQuanTime(long ms)//算本圈时间的算法
	{		
		timeBenQuan[0]=(long) Math.floor((ms%1000)/10);
		timeBenQuan[1]=(long) Math.floor((ms%60000)/1000);
		timeBenQuan[2]=(long) Math.floor((ms/60000));
	}
	public void toTotalTime(long ms)//算总时间的方法
	{		
		timeTotal[0]=(long) Math.floor((ms%1000)/10);
		timeTotal[1]=(long) Math.floor((ms%60000)/1000);
		timeTotal[2]=(long) Math.floor((ms/60000));
	}
	
	
	//时间数字内部类
	private class First
	{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public First(float scale,float texture[]) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-NUMBERWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										-NUMBERWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0,
										NUMBERWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										
										NUMBERWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										-NUMBERWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0,
										NUMBERWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[]textures=new float[vCount*2];
			textures=texture;
				ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
				tbb.order(ByteOrder.nativeOrder());
				textureBuffer=tbb.asFloatBuffer();
				textureBuffer.put(textures);
				textureBuffer.position(0);
		}
		public void drawSelf(GL10 gl,int texId) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);		
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}
	}
	
	
	//本圈时间显示内部类
	private class TimeBenQuan
	{
		First[] numbers=new First[10];
		
		public TimeBenQuan(float scale)
		{
			//生成0-9十个数字的纹理矩形
			for(int i=0;i<10;i++)
			{
				numbers[i]=new First(
					scale,
					new float[]
					          {
							0.099f*i,0f, 
							0.099f*i,0.277f, 
							0.099f*(i+1),0f,
							0.099f*(i+1),0f, 
							0.099f*i,0.277f,  
			           	  	0.099f*(i+1),0.277f							
					          }
				);
			}
		}	
	
		public void drawSelf(GL10 gl,int texId,int number)
		{	
			String scoreStr;
				if(timeBenQuan[number]<10)
				{
					scoreStr="0"+timeBenQuan[number]+"";
				}
				else
				{
					scoreStr=timeBenQuan[number]+"";
				}
			for(int i=0;i<scoreStr.length();i++)
			{//将得分中的每个数字字符绘制
				char c=scoreStr.charAt(i);
				gl.glPushMatrix();
				gl.glTranslatef(i*NUMBERWIDTH*scale, 0, 0);
				numbers[c-'0'].drawSelf(gl,texId);
				gl.glPopMatrix();
			}
		}
	}
	
	
	//总时间的内部类
	private class TimeTotal
	{
		First[] numbers=new First[10];
		
		public TimeTotal(float scale)
		{
			//生成0-9十个数字的纹理矩形
			for(int i=0;i<10;i++)
			{
				numbers[i]=new First(
					scale,
					new float[]
					          {
							0.099f*i,0f, 
							0.099f*i,0.277f, 
							0.099f*(i+1),0f,
							0.099f*(i+1),0f, 
							0.099f*i,0.277f,  
			           	  	0.099f*(i+1),0.277f
							
					          }
				);
			}
		}	
	
		public void drawSelf(GL10 gl,int texId,int number)
		{	
			String scoreStr;
				if(timeTotal[number]<10)
				{
					scoreStr="0"+timeTotal[number]+"";
				}
				else
				{
					scoreStr=timeTotal[number]+"";
				}
			for(int i=0;i<scoreStr.length();i++)
			{//将得分中的每个数字字符绘制
				char c=scoreStr.charAt(i);
				gl.glPushMatrix();
				gl.glTranslatef(i*NUMBERWIDTH*scale, 0, 0);
				numbers[c-'0'].drawSelf(gl,texId);
				gl.glPopMatrix();
			}
		}
	}
	
	
	
	//斜杠内部类
	private class XieGang
	{
		First xie;
		
		public XieGang(float scale)
		{
			//生成0-9十个数字的纹理矩形
			for(int i=0;i<10;i++)
			{
				xie=new First(
					scale,
					new float[]
					          {
								0.613f,0.441f,
								0.613f,0.754f,
								0.695f,0.441f,
								
								0.695f,0.441f,
								0.613f,0.754f,
								0.695f,0.754f						
					          }
				);
			}
		}
		public void drawSelf(GL10 gl,int texId,int number)
		{
				gl.glPushMatrix();
				xie.drawSelf(gl,texId);
				gl.glPopMatrix();
		}
	}

	
	
	//点内部类
	private class Point
	{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer[] textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Point(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-POINTWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										-POINTWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0,
										POINTWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										
										POINTWIDTH/2*scale,NUMBERHEIGTH/2*scale,0,
										-POINTWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0,
										POINTWIDTH/2*scale,-NUMBERHEIGTH/2*scale,0
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
					 0.73f,0.441f,
					 0.73f,0.754f,
					 0.781f,0.441f,
					 
					 0.781f,0.441f,
					 0.73f,0.754f,
					 0.781f,0.754f	
					},
					{
					 0.805f,0.441f,
					 0.805f,0.754f,
					 0.855f,0.441f,
						 
					 0.855f,0.441f,
					 0.805f,0.754f,
					 0.855f,0.754f
					}
			                              };
			textureBuffer=new FloatBuffer[3];
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
		}
	}
	
	
	
	//图片内部类
	private class Word
	{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Word(float scale,float texture[]) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-PICTUREWIDTH/2*scale,PICTUREHEIGHT/2*scale,0,
										-PICTUREWIDTH/2*scale,-PICTUREHEIGHT/2*scale,0,
										PICTUREWIDTH/2*scale,PICTUREHEIGHT/2*scale,0,
										
										PICTUREWIDTH/2*scale,PICTUREHEIGHT/2*scale,0,
										-PICTUREWIDTH/2*scale,-PICTUREHEIGHT/2*scale,0,
										PICTUREWIDTH/2*scale,-PICTUREHEIGHT/2*scale,0
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[]textures=new float[vCount*2];
			textures=texture;
				ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
				tbb.order(ByteOrder.nativeOrder());
				textureBuffer=tbb.asFloatBuffer();
				textureBuffer.put(textures);
				textureBuffer.position(0);
		}
		public void drawSelf(GL10 gl,int texId) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0,vertexBuffer);		
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
			gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}
	}
	
	
	
	private class Names
	{
		Word[] name=new Word[3];
		public Names(float scale)
		{
			for(int i=0;i<3;i++)
			{
				name[i]=new Word( 
					scale,
					new float[]
					          {
							0.193f*i,0.32f, 
							0.193f*i,0.805f, 
							0.193f*(i+1),0.32f,
							0.193f*(i+1),0.32f, 
							0.193f*i,0.805f,  
			           	  	0.193f*(i+1),0.805f
					          }
				); 
			}
		}
		public void drawSelf(GL10 gl,int texId,int number)
		{	
			gl.glPushMatrix();
			name[number].drawSelf(gl, texId);
			gl.glPopMatrix();
		}
	}
	private class QuanShu
	{
		First[] quanShu=new First[4];
		public QuanShu(float scale)
		{
			for(int i=0;i<4;i++)
			{
				quanShu[i]=new First(
					scale,
					new float[]
					          {
							0.099f*i,0f, 
							0.099f*i,0.277f, 
							0.099f*(i+1),0f,
							0.099f*(i+1),0f, 
							0.099f*i,0.277f,  
			           	  	0.099f*(i+1),0.277f
					          }
				);
			}                          
		}
		public void drawSelf(GL10 gl,int texId,int number)
		{
			String scoreStr=MyGLSurfaceView.quanshu+"";
			for(int i=0;i<scoreStr.length();i++)
			{//将得分中的每个数字字符绘制
				char c=scoreStr.charAt(i);
				gl.glPushMatrix();
				quanShu[c-'0'].drawSelf(gl,texId);
				gl.glPopMatrix();
			}
		}
	}
	private class ZongQuan//总圈数
	{ 
		First zongQuan;
		public ZongQuan(float scale)
		{
			zongQuan=new First(scale,
					new float[]{
					0.099f*MAX_QUANSHU,0f, 
					0.099f*MAX_QUANSHU,0.277f, 
					0.099f*(MAX_QUANSHU+1),0f,
					0.099f*(MAX_QUANSHU+1),0f, 
					0.099f*MAX_QUANSHU,0.277f,  
	           	  	0.099f*(MAX_QUANSHU+1),0.277f
							}
								);
		}
		public void drawSelf(GL10 gl,int texId)
		{
			gl.glPushMatrix();
			zongQuan.drawSelf(gl, texId);
			gl.glPopMatrix();
		}
	}
}
