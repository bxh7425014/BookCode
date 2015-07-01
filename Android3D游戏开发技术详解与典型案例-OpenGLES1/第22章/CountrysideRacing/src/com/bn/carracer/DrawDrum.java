package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawDrum extends BNShape
{	
	DrawTaper taper;//绘制圆锥
	DrawCirque cirque;//圆环
	
	public DrawDrum(float scale)
	{
		super(scale);
		taper=new DrawTaper(scale);
		cirque=new DrawCirque(scale);
	} 

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0.2f*scale, 0);
		
		gl.glPushMatrix();
		taper.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		cirque.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	
	
	//绘制圆锥内部类
	private class DrawTaper
	{
		private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
		private FloatBuffer myTexture;//纹理缓冲
		
		int vCount;//顶点数量
				
		public DrawTaper(float scale)
		{			
			float height=5*scale;
			float circle_radius=1.5f*scale;
			float degreespan=36f;
			int col=1;
			
			float spanHeight=(float)height/col;//圆锥每块所占的高度
			int spannum=(int)(360.0f/degreespan);
			float spanR=circle_radius/col;//半径单位长度
					
			ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
			
			for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
			{
				for(int j=0;j<col;j++)//循环列
				{
					float currentR=j*spanR;//当前截面的圆半径
					float currentHeight=height-j*spanHeight;//当前截面的高度
					
					float x1=(float) (currentR*Math.cos(Math.toRadians(circle_degree)));
					float y1=currentHeight;
					float z1=(float) (currentR*Math.sin(Math.toRadians(circle_degree)));
					
					float x2=(float) ((currentR+spanR)*Math.cos(Math.toRadians(circle_degree)));
					float y2=currentHeight-spanHeight;
					float z2=(float) ((currentR+spanR)*Math.sin(Math.toRadians(circle_degree)));
					
					float x3=(float) ((currentR+spanR)*Math.cos(Math.toRadians(circle_degree-degreespan)));
					float y3=currentHeight-spanHeight;
					float z3=(float) ((currentR+spanR)*Math.sin(Math.toRadians(circle_degree-degreespan)));
					
					float x4=(float) ((currentR)*Math.cos(Math.toRadians(circle_degree-degreespan)));
					float y4=currentHeight;
					float z4=(float) ((currentR)*Math.sin(Math.toRadians(circle_degree-degreespan)));
										
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
			float[] textures=generateTexCoor(spannum,col);
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
	    	float sizew=1.0f/bw;//列单位长度
	    	float sizeh=1.0f/bh;//行单位长度
	    	int c=0;
	    	for(int j=0;j<bw;j++)
	    	{
	    		for(int i=0;i<bh;i++)
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
	
	
	
	//绘制圆环内部类
	private class DrawCirque
	{
		private FloatBuffer myVertex;//顶点缓冲
		private FloatBuffer myTexture;//纹理缓冲
		
		int vcount;
		 
		public DrawCirque(float scale)
		{     			
			float ring_Span=36;
			float circle_Span=45;
			float ring_Radius=1.5f*scale;
			float circle_Radius=0.2f*scale;
			
			ArrayList<Float> val=new ArrayList<Float>();
			
			for(float circle_Degree=0f;circle_Degree<360f;circle_Degree+=circle_Span)
			{
				for(float ring_Degree=0f;ring_Degree<360f;ring_Degree+=ring_Span)
				{
					float x1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree)));
					float y1=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
					float z1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree)));
					
					float x2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
					float y2=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
					float z2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
					
					float x3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
					float y3=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
					float z3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
					
					float x4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree)));
					float y4=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
					float z4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree)));
					
					val.add(x1);val.add(y1);val.add(z1);
					val.add(x4);val.add(y4);val.add(z4);
					val.add(x2);val.add(y2);val.add(z2);
								
					val.add(x2);val.add(y2);val.add(z2);
					val.add(x4);val.add(y4);val.add(z4);
					val.add(x3);val.add(y3);val.add(z3); 
				}
			}
			vcount=val.size()/3; 
			float[] vertexs=new float[vcount*3];
			for(int i=0;i<vcount*3;i++)
			{
				vertexs[i]=val.get(i);
			}
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
			vbb.order(ByteOrder.nativeOrder());
			myVertex=vbb.asFloatBuffer();
			myVertex.put(vertexs);
			myVertex.position(0);
			
			//纹理			
			int row=(int) (360.0f/circle_Span);
			int col=(int) (360.0f/ring_Span);
			float[] textures=generateTexCoor(row,col);
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			myTexture=tbb.asFloatBuffer();
			myTexture.put(textures);
			myTexture.position(0);
		}
		
		public void drawSelf(GL10 gl,int texId)
		{				
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		
	    //自动切分纹理产生纹理数组的方法
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizew=1.0f/bw;//列数
	    	float sizeh=1.0f/bh;//行数
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
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    			   			
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;     			
	    		}
	    	}
	    	return result;
	    }	    
	}
}