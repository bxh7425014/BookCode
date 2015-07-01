package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class DrawBegin extends BNShape{
	
	DrawBeginLine line;
	public DrawBegin(float scale) {
		super(scale);
		// TODO Auto-generated constructor stub
		line=new DrawBeginLine(scale);
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
		// TODO Auto-generated method stub
		gl.glPushMatrix();
		line.drawSelf(gl, texId, number);
		gl.glPopMatrix();
	}

	
	private class DrawBeginLine
	{
		private FloatBuffer mVertexBuffer;//创建顶点数据缓冲
		private FloatBuffer mTextureBuffer;//创建纹理坐标数据缓冲
		
		int vCount;//顶点数量
		public DrawBeginLine(float scale)
		{
			vCount=6;
			
			float width=0.2f;//宽度
			float length=1.0f;//长度
			float verteices[]=
			{
					-length*scale,0,-width*scale,
					-length*scale,0,width*scale,
					length*scale,0,-width*scale,
					
					length*scale,0,-width*scale,
					-length*scale,0,width*scale,
					length*scale,0,width*scale
			};
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float s=5;//纹理坐标s轴最大值
			float t=1;//纹理坐标t轴最大值
			
			float textureCoors[]=
			{
				0,0,
				0,t,
				s,0,
				
				s,0,
				0,t,
				s,t
			};
			ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建纹理坐标数据缓冲
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标
			mTextureBuffer.position(0);//设置缓冲区起始位置
		}
		
		
		public void drawSelf(GL10 gl,int texId,int number)
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//允许使用顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//指定顶点数据
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许使用纹理坐标
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理坐标数据
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数据
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//以三角形方式填充
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisable(GL10.GL_TEXTURE_2D);//禁止使用纹理
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭顶点数组
		}
	}
}
