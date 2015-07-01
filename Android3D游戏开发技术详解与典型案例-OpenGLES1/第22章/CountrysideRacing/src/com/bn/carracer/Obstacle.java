package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Obstacle extends BNShape{

	Rect rect;//声明纹理矩形
	Cylinder cylinder;//圆柱体
	static float yOffset;//矩形Y轴移动距离
	static float xOffset;//圆柱X轴移动距离
	static float zOffset;//圆柱Z轴的移动距离
	static float tempYOffset;//将物体移动到xoz平面上面
	static float yAngle;//绕Y轴转动的角度
	static float OffsetY;//障碍物在Y轴上的位移
	static float OffsetZ;//障碍物在Z轴上的位移 
	public Obstacle(float scale) {
		super(scale);
		// TODO Auto-generated constructor stub
		rect=new Rect(scale);
		cylinder=new Cylinder(scale);
		yOffset=(float) (rect.width*scale+cylinder.length/2*Math.sin(Math.toRadians(60)));
		xOffset=rect.length*scale-cylinder.circle_radius;
		zOffset=(float) (cylinder.length/2*Math.cos(Math.toRadians(60)));
		tempYOffset=(float) (cylinder.length/2*Math.sin(Math.toRadians(60)));
		Log.d("yOffset", yOffset+"");
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
		// TODO Auto-generated method stub
		gl.glPushMatrix();
		
		gl.glTranslatef(0, -yOffset, 0);//移动中心点
		gl.glPushMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, yOffset+tempYOffset, 0);
		rect.drawSelf(gl, texId);//绘制纹理矩形
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, yOffset+tempYOffset, 0);
		gl.glRotatef(180, 0, 1, 0);
		rect.drawSelf(gl, texId);//绘制纹理矩形
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(xOffset, tempYOffset, zOffset);//右侧的圆柱体
		gl.glRotatef(90, 0, 0, 1);
		gl.glRotatef(30, 0, 1, 0);
		cylinder.drawSelf(gl, texId);//绘制圆柱体
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(xOffset, tempYOffset, -zOffset);
		gl.glRotatef(90, 0, 0, 1);
		gl.glRotatef(-30, 0, 1, 0);
		cylinder.drawSelf(gl, texId);//绘制圆柱体
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(-xOffset, tempYOffset, zOffset);//左侧的圆柱体
		gl.glRotatef(90, 0, 0, 1);
		gl.glRotatef(30, 0, 1, 0);
		cylinder.drawSelf(gl, texId);//绘制圆柱体
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(-xOffset, tempYOffset, -zOffset);
		gl.glRotatef(90, 0, 0, 1);
		gl.glRotatef(-30, 0, 1, 0);
		cylinder.drawSelf(gl, texId);//绘制圆柱体
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		gl.glPopMatrix();
		
	}
	
	private class Rect
	{
		private FloatBuffer mVertexBuffer;//顶点数组数据缓冲
		private FloatBuffer mTextureBuffer;//纹理数组数据缓冲
		float width;//宽度
		float length;//长度
		int vCount;//顶点数量
		public Rect(float scale)
		{
			vCount=6;
			
			width=1f;//宽度
			length=2.0f;//长度
			float verteices[]=
			{
					-length*scale,width*scale,0,
					-length*scale,-width*scale,0,
					length*scale,-width*scale,0,
					
					length*scale,-width*scale,0,
					length*scale,width*scale,0,
					-length*scale,width*scale,0
			};
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float textureCoors[]=
			{
					0,0,0,1,1,1,
					1,1,1,0,0,0
			};
			ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建纹理坐标数据缓冲
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标
			mTextureBuffer.position(0);//设置缓冲区起始位置
		}
		
		public void drawSelf(GL10 gl,int textureId)
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//允许使用顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//指定顶点数据缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许使用纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理组数
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数据缓冲
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//绑定纹理坐标
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//以三角形方式绘制矩形
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭顶点数组
		}
	}
	
	private class Cylinder
	{
		private FloatBuffer mVertexBuffer;//顶点坐标缓冲 
		private FloatBuffer mTextureBuffer;//纹理缓冲	
		int vCount;//顶点数量
		float length;//圆柱长度
		float circle_radius;//圆截环半径
		float degreespan;  //圆截环每一份的度数大小
		int col;//圆柱块数
		public Cylinder(float scale)
		{
			length=2.0f*scale;
			circle_radius=0.5f;
			degreespan=18f;
			col=1;
			float collength=(float)length/col;//圆柱每块所占的长度
			int spannum=(int)(360.0f/degreespan);
			
			ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
			
			for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
			{
				for(int j=0;j<col;j++)//循环列
				{
					float x1 =(float)(j*collength-length/2);
					float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
					float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
					
					float x2 =(float)(j*collength-length/2);
					float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
									
					float x3=(float)((j+1)*collength-length/2);
					float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x4=(float)((j+1)*collength-length/2);
					float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
					float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
									
					val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
					val.add(x2);val.add(y2);val.add(z2);
					val.add(x4);val.add(y4);val.add(z4);
					
					val.add(x2);val.add(y2);val.add(z2);
					val.add(x3);val.add(y3);val.add(z3);
					val.add(x4);val.add(y4);val.add(z4);
					
				}
			}
			 
			vCount=val.size()/3;//确定顶点数量
			//顶点
			float[] vertexs=new float[vCount*3];
			for(int i=0;i<vCount*3;i++)
			{
				vertexs[i]=val.get(i);
			}
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBuffer=vbb.asFloatBuffer();
			mVertexBuffer.put(vertexs);
			mVertexBuffer.position(0);
			
			//纹理
			float[] textures=generateTexCoor(col,spannum);
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTextureBuffer=tbb.asFloatBuffer();
			mTextureBuffer.put(textures);
			mTextureBuffer.position(0);
		}
		
		public void drawSelf(GL10 gl,int textureId)
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//指定顶点缓冲
					
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		//自动切分纹理产生纹理数组的方法
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizew=1f/bw;//列数
	    	float sizeh=0.03f/bh;//行数
	    	int c=0;
	    	for(int i=0;i<bh;i++)
	    	{
	    		for(int j=0;j<bw;j++)
	    		{
	    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
	    			float s=j*sizew;
	    			float t=i*sizeh;
	    			
	    			result[c++]=s;
	    			result[c++]=t;
	    		
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    			   			
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;   
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    		}
	    	}
	    	return result;
	    }
	}
}
