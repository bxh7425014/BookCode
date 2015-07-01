package com.bn.carracer;

import java.nio.ByteBuffer;   
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.carracer.Constant.*;

class DrawPanel extends BNShape {

	private final float  PANELLENGTH=0.6f;//仪表盘的长度
	private final float  PANELHEIGHT=0.5f;//仪表盘的高度
	private final float  POINTER=0.2f;//指针的长度
	private final float  POINTER_H=0.011f;//指针的高度
	
	Panel panel;
	Pointer pointer;
	float angle=60;//指针的初始位置 
	float zAngle=60;//指针角度
	public DrawPanel(float scale) {    
		super(scale); 
		panel=new Panel(scale);
		pointer=new Pointer(scale);
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
		gl.glPushMatrix();//绘制盘
		panel.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制指针
		gl.glTranslatef(0f, 0f, 0.05f);
		gl.glRotatef(zAngle, 0, 0, 1);
		pointer.drawSelf(gl,texId);
		gl.glPopMatrix();
		
		
	}
	public void changepointer(float v)//指针角度转的方法
	{
		//***********每增加0.6km/h，zAngle增加1度**************
		float vSpan=CAR_MAX_SPEED*1.3f/267;//每一仪表盘指针角度所表示的车速
		if(v<=CAR_MAX_SPEED*1.3f&&v>=0)
		{
			float v_Angle=v/vSpan;
			zAngle=angle-v_Angle;
		}
		
	}

	private class Panel
	{
		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Panel(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
					-PANELLENGTH/2*scale,PANELHEIGHT/2*scale,0,
					-PANELLENGTH/2*scale,-PANELHEIGHT/2*scale,0,
					PANELLENGTH/2*scale,PANELHEIGHT/2*scale,0,
					
					PANELLENGTH/2*scale,PANELHEIGHT/2*scale,0,
					-PANELLENGTH/2*scale,-PANELHEIGHT/2*scale,0,
					PANELLENGTH/2*scale,-PANELHEIGHT/2*scale,0,
										
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
			float[]textures=new float[]{			
					0.07f,0.14f,
					0.07f,1,
					0.93f,0.14f,
					
					0.93f,0.14f,
					0.07f,1,
					0.93f,1
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
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		
		}
	}
	private class Pointer//绘制指针的内部类
	{
		

		private FloatBuffer vertexBuffer;//顶点Buffer
		private FloatBuffer textureBuffer;//纹理坐标Buffer
		private int vCount=0;//顶点数
		public Pointer(float scale) {
			float[]vertice=new float[]{//存放顶点坐标的数组
										-POINTER*scale,POINTER_H/2*scale,0,
										-POINTER*scale,-POINTER_H/2*scale,0,
										0,POINTER_H/2*scale,0,
										
										0,POINTER_H/2*scale,0,
										-POINTER*scale,-POINTER_H/2*scale,0,
										0,-POINTER_H/2*scale,0
										};
			vCount=vertice.length/3;//顶点数量

			ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
			vbb.order(ByteOrder.nativeOrder());
			vertexBuffer=vbb.asFloatBuffer();
			vertexBuffer.put(vertice);
			vertexBuffer.position(0);
		
			float textures[]=new float[]
			                           {
										0,0,
										0,0.125f,
										0.5f,0,
										
										0.5f,0,
										0,0.125f,
										0.5f,0.125f
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
			
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		}
	}
}
