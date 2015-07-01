package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class DrawLicensePlate {

	private FloatBuffer mVertexBuffer;//顶点数组数据缓冲 
	private FloatBuffer mTextureBuffer;//纹理数组数据缓冲
	int vCount;//顶点数量
	public DrawLicensePlate(float scale)
	{
		vCount=6;//顶点数量
		float width=0.38f;//宽度
		float length=1.0f;//长度
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
				0,0.200f,0,0.719f,1,0.719f,
				1,0.719f,1,0.200f,0,0.200f
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
