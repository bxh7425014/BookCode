package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class DrawBuilding extends BNShape
{
	//立方体长、宽、高
	public static float LENGTH=15f;
	public static float WIDTH=10f;
	public static float HEIGHT=12f;
	//圆柱高、半径、切割度数、块数
	public static float CYLINDER_HEIGHT=50f;
	public static float CIRCLE_RADIUS=20f;
	public static float DEGREESPAN=18f;
	public static int COL=1;
	
	private Cube cubeBuilding;
	private DrawCylinder cylinderBuilding;
	private DrawCylinderTop buildingRoof;
	//各个坐标轴的转动角度
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawBuilding(float scale) {
		super(scale);
		cubeBuilding = new Cube(scale);
		cylinderBuilding = new DrawCylinder(
				scale*CYLINDER_HEIGHT,
				scale*CIRCLE_RADIUS,
				scale*DEGREESPAN,
				COL);
		buildingRoof = new DrawCylinderTop(
				scale*CYLINDER_HEIGHT*0.2f,
				scale*CIRCLE_RADIUS*0.8f,
				scale*DEGREESPAN,
				COL);
	}
	
	@Override
	public void drawSelf(GL10 gl,int texId,int number)
	{
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glPushMatrix();
		gl.glTranslatef(-scale*CIRCLE_RADIUS, scale*CYLINDER_HEIGHT/2, 0);
		gl.glRotatef(90, 0, 0, 1);
		cylinderBuilding.drawSelf(gl,texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(-scale*CIRCLE_RADIUS, scale*CYLINDER_HEIGHT+scale*CYLINDER_HEIGHT*0.2f/2, 0);
		gl.glRotatef(90, 0, 0, 1);
		buildingRoof.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0.8f*scale*LENGTH, scale*HEIGHT, 0);
		cubeBuilding.drawSelf(gl,texId);
		gl.glPopMatrix();
	}
	
	
	private class Cube {
		private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
		private FloatBuffer mTextureBuffer;	//纹理坐标数据缓冲
		public float mOffsetX;
		public float mOffsetY;	//立方体大小
		int vCount;//顶点数量
		public Cube(float scale)
		{
			vCount=36;
			float[] verteices=
			{
					
					//顶面
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					
					//后面
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					
					//前面
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					//下面
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					
					//左面
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					//右面
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH
							
			};
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float[] textureCoors=new float[vCount*2];
			for(int i=0;i<vCount/6;i++)//个顶点纹理坐标
			{
				textureCoors[i*12]=0;
				textureCoors[(i*12)+1]=0;
				
				textureCoors[(i*12)+2]=0;
				textureCoors[(i*12)+3]=0.125f;
				
				textureCoors[(i*12)+4]=0.125f;
				textureCoors[(i*12)+5]=0.125f;
				
				textureCoors[(i*12)+6]=0;
				textureCoors[(i*12)+7]=0;
				
				textureCoors[(i*12)+8]=0.125f;
				textureCoors[(i*12)+9]=0.125f;
				
				textureCoors[(i*12)+10]=0.125f;
				textureCoors[(i*12)+11]=0;

			}
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建顶点坐标数据缓冲
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
			mTextureBuffer.position(0);//设置缓冲区起始位置
			
			
		}
		
		public void drawSelf(GL10 gl,int texId)
		{
			gl.glRotatef(mOffsetX, 1, 0, 0);
			gl.glRotatef(mOffsetY, 0, 1, 0);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数组
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			
		}
	}
	
	private class DrawCylinder
	{
		private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
		private FloatBuffer myTexture;//纹理缓冲
		int vCount;//顶点数量
		
		public float mAngleX;
		public float mAngleY;
		public float mAngleZ;
		
		public DrawCylinder(float length,float circle_radius,float degreespan,int col)
		{			
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
					
					float x3 =(float)((j+1)*collength-length/2);
					float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x4 =(float)((j+1)*collength-length/2);
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
			myVertexBuffer=vbb.asFloatBuffer();
			myVertexBuffer.put(vertexs);
			myVertexBuffer.position(0);		

			//纹理
			float[] textures=generateTexCoor(col,spannum);
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			myTexture=tbb.asFloatBuffer();
			myTexture.put(textures);
			myTexture.position(0);
		}
		
		public void drawSelf(GL10 gl,int textureId)
		{
			gl.glRotatef(mAngleX, 1, 0, 0);//旋转
			gl.glRotatef(mAngleY, 0, 1, 0);
			gl.glRotatef(mAngleZ, 0, 0, 1);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		
	    //自动切分纹理产生纹理数组的方法
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	int c=0;
	    	for(int i=0;i<bh;i++)
	    	{
	    		for(int j=0;j<bw;j++)
	    		{
	    			result[c++]=0;
	    			result[c++]=0;
	    			
	    			result[c++]=0;
	    			result[c++]=0.125f;
	    			
	    			result[c++]=1f;
	    			result[c++]=0;
	    			
	    			result[c++]=0;
	    			result[c++]=0.125f;
	    			
	    			result[c++]=1f;
	    			result[c++]=0.125f;
	    			
	    			result[c++]=1f;
	    			result[c++]=0;
	    		}
	    	}
	    	return result;
	    }
	}
	
	private class DrawCylinderTop
	{
		private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
		//private FloatBuffer myNormalBuffer;//法向量缓冲
		private FloatBuffer myTexture;//纹理缓冲
		int vCount;//顶点数量
		
		public float mAngleX;
		public float mAngleY;
		public float mAngleZ;
		
		public DrawCylinderTop(float length,float circle_radius,float degreespan,int col)
		{			
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
					
					float x3 =(float)((j+1)*collength-length/2);
					float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x4 =(float)((j+1)*collength-length/2);
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
			myVertexBuffer=vbb.asFloatBuffer();
			myVertexBuffer.put(vertexs);
			myVertexBuffer.position(0);
			
			//纹理
			float[] textures=generateTexCoor(col,spannum);
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			myTexture=tbb.asFloatBuffer();
			myTexture.put(textures);
			myTexture.position(0);
		}
		
		public void drawSelf(GL10 gl,int textureId)
		{
			gl.glRotatef(mAngleX, 1, 0, 0);//旋转
			gl.glRotatef(mAngleY, 0, 1, 0);
			gl.glRotatef(mAngleZ, 0, 0, 1);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		
		//自动切分纹理产生纹理数组的方法
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizeh=0.5f/bh;//行数
	    	float sizew=0.125f/bw;//列数
	    	int c=0;
	    	for(int i=0;i<bh;i++)
	    	{
	    		for(int j=0;j<bw;j++)
	    		{
	    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
	    			float t=0.125f+i*sizeh;
	    			float s=j*sizew;    	
	    			
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




