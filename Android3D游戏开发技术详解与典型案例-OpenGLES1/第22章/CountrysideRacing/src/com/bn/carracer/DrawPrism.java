package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawPrism extends BNShape
{		
	Prism prism;//棱柱
	Cylinder cylinder;//圆柱
	
	float a=1;//棱柱截面边长
	float height=2;//棱柱高度
	float cylinder_R=0.1f;//圆柱截面半径
	float cylinder_H=1;//圆柱高度
	
    public float mAngleX;//沿x轴旋转角度
    public float mAngleY;//沿y轴旋转角度 
    public float mAngleZ;//沿z轴旋转角度 

	public DrawPrism(float scale) {
		super(scale);
		prism=new Prism(scale);
		cylinder=new Cylinder(scale,1,0.1f,45f,1);
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {		
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z轴旋转    	
        gl.glRotatef(mAngleY, 0, 1, 0);//沿Y轴旋转
        gl.glRotatef(mAngleX, 1, 0, 0);//沿X轴旋转
        
        gl.glPushMatrix();
        gl.glTranslatef(0, scale*(cylinder_H+height/2), 0);
        
		gl.glPushMatrix();
		prism.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(0, -scale*(height/2+cylinder_H/2), 0); 
		gl.glRotatef(90, 0, 0, 1);
		cylinder.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	
	
	private class Prism
	{
		FloatBuffer vertexBuffer;
		FloatBuffer textureBuffer;
		
		int vCount;
		
		public Prism(float scale)
		{
			float b=(float) Math.sqrt(3);
			
			ArrayList<Float> alVertex=new ArrayList<Float>();//存放顶点坐标
			
			float x1=scale*(-a/2);
			float y1=scale*(height/2);
			float z1=scale*(a/2/b);
			
			float x2=scale*(a/2);
			float y2=scale*(height/2);
			float z2=scale*(a/2/b);
			
			float x3=0;
			float y3=scale*(height/2);
			float z3=scale*(-a/b);
			
			float x4=scale*(-a/2);
			float y4=scale*(-height/2);
			float z4=scale*(a/2/b);
			
			float x5=scale*(a/2);
			float y5=scale*(-height/2);
			float z5=scale*(a/2/b);
			
			float x6=0;
			float y6=scale*(-height/2);
			float z6=scale*(-a/b);
			
			alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
			alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
			alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
						
			alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
			alVertex.add(x5);alVertex.add(y5);alVertex.add(z5);
			alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
			
			alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
			alVertex.add(x5);alVertex.add(y5);alVertex.add(z5);
			alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
			
			alVertex.add(x5);alVertex.add(y5);alVertex.add(z5);
			alVertex.add(x6);alVertex.add(y6);alVertex.add(z6);
			alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
			
			alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
			alVertex.add(x6);alVertex.add(y6);alVertex.add(z6);
			alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
			
			alVertex.add(x6);alVertex.add(y6);alVertex.add(z6);
			alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
			alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
			
			vCount=alVertex.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
	    	
	        //将alVertix中的坐标值转存到一个int数组中
	        float[] vertices=new float[vCount*3];
	    	for(int i=0;i<alVertex.size();i++)
	    	{
	    		vertices[i]=alVertex.get(i);
	    	}
	        //创建顶点坐标数据缓冲
	        //vertices.length*4是因为一个整数四个字节
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        vertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
	        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        vertexBuffer.position(0);//设置缓冲区起始位置
	        
			float[] textures=new float[]{			
					1,0.5f,
					0,0.5f,
					1,1,
					
					0,0.5f,
					0,1,
					1,1,
					
					1,0.5f,
					0,0.5f,
					1,1,
					
					0,0.5f,
					0,1,
					1,1,
					
					1,0.5f,
					0,0.5f,
					1,1,
					
					0,0.5f,
					0,1,
					1,1
				};
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
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组	
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	}	
	
	//圆柱内部类
	private class Cylinder
	{
		private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
		private FloatBuffer myTexture;//纹理缓冲
		
		int vCount;//顶点数量
		
		public Cylinder(float scale,float length,float circle_radius,float degreespan,int col)
		{			
			float collength=(float)length*scale/col;//圆柱每块所占的长度
			int spannum=(int)(360.0f/degreespan);
			
			ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
			
			for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
			{
				for(int j=0;j<col;j++)//循环列
				{
					float x1 =(float)(j*collength-length/2*scale);
					float y1=(float) (circle_radius*scale*Math.sin(Math.toRadians(circle_degree)));
					float z1=(float) (circle_radius*scale*Math.cos(Math.toRadians(circle_degree)));		
					
					float x2 =(float)(j*collength-length/2*scale);
					float y2=(float) (circle_radius*scale*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z2=(float) (circle_radius*scale*Math.cos(Math.toRadians(circle_degree-degreespan)));					
							
					float x3 =(float)((j+1)*collength-length/2*scale);
					float y3=(float) (circle_radius*scale*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z3=(float) (circle_radius*scale*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x4 =(float)((j+1)*collength-length/2*scale);
					float y4=(float) (circle_radius*scale*Math.sin(Math.toRadians(circle_degree)));
					float z4=(float) (circle_radius*scale*Math.cos(Math.toRadians(circle_degree)));							
					
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
		
		public void drawSelf(GL10 gl,int texId)
		{			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
			
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
	    	float sizeh=0.5f/bh;//行数
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