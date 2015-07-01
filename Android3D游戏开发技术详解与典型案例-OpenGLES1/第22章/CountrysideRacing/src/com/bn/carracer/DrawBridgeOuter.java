package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
public class DrawBridgeOuter extends BNShape{

	
	DrawBridge bridge;//桥顶面
	public DrawBridgeOuter(float scale) {
		super(scale);
		// TODO Auto-generated constructor stub
		bridge=new DrawBridge(scale);//创建矩形对象
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) {
		// TODO Auto-generated method stub
		bridge.drawSelf(gl, texId, number);
		
	}
	
	
	private class DrawBridge
	{
		private FloatBuffer mVertexBuffer;//创建顶点数据缓冲
		private FloatBuffer mTextureBuffer;//创建纹理坐标数据缓冲
		
		int vCount;//顶点数量
		float lengthOut;//总长
		float lengthMid;//洞长
		float lengthIn;//桥面长
		float height;//总高
		float heightIn;//洞高
		float width;//宽
		
		public DrawBridge(float scale)
		{	
			lengthOut=8.0f*scale;//总长
			lengthMid=6.0f*scale;//洞长
			lengthIn=4.0f*scale;//桥面长
			height=3.0f*scale;//总高
			heightIn=2.0f*scale;//洞高
			width=1.5f*scale;//宽
			float[] verteices=
			{
				//前面
					//1号面
				-lengthOut,0,width,
				-lengthMid,0,width,
				-lengthIn,height,width,
					//2号面
				-lengthIn,height,width,
				-lengthMid,0,width,
				-lengthIn,heightIn,width,
				//3号面
				-lengthIn,height,width,
				-lengthIn,heightIn,width,
				lengthIn,height,width,
				//4号面
				lengthIn,height,width,
				-lengthIn,heightIn,width,
				lengthIn,heightIn,width,
				//5号面
				lengthIn,heightIn,width,
				lengthOut,0,width,
				lengthIn,height,width,
				//6号面
				lengthIn,heightIn,width,
				lengthMid,0,width,
				lengthOut,0,width,
				
				//后面
				//7号面
				-lengthOut,0,-width,
				-lengthIn,height,-width,
				-lengthMid,0,-width,
				//8号面
				-lengthMid,0,-width,
				-lengthIn,height,-width,
				-lengthIn,heightIn,-width,
				//9号
				-lengthIn,height,-width,
				lengthIn,height,-width,
				-lengthIn,heightIn,-width,
				
				//10号
				lengthIn,height,-width,
				lengthIn,heightIn,-width,
				-lengthIn,heightIn,-width,
				//11
				lengthIn,height,-width,
				lengthOut,0,-width,
				lengthIn,heightIn,-width,
				//12
				lengthIn,heightIn,-width,
				lengthOut,0,-width,
				lengthMid,0,-width,
				//上面
				//13
				-lengthOut,0,-width,
				-lengthOut,0,width,
				-lengthIn,height,width,
				//14
				-lengthIn,height,width,
				-lengthIn,height,-width,
				-lengthOut,0,-width,
				//15
				-lengthIn,height,-width,
				-lengthIn,height,width,
				lengthIn,height,width,
				//16
				lengthIn,height,width,
				lengthIn,height,-width,
				-lengthIn,height,-width,
				//17
				lengthIn,height,-width,
				lengthIn,height,width,
				lengthOut,0,width,
				//18
				lengthOut,0,width,
				lengthOut,0,-width,
				lengthIn,height,-width,
				
				//下面
				//19
				-lengthMid,0,-width,				
				-lengthIn,heightIn,width,
				-lengthMid,0,width,
				//20
				-lengthIn,heightIn,width,				
				-lengthMid,0,-width,
				-lengthIn,heightIn,-width,
				//21
				-lengthIn,heightIn,-width,				
				lengthIn,heightIn,width,
				-lengthIn,heightIn,width,
				//22
				lengthIn,heightIn,width,				
				-lengthIn,heightIn,-width,
				lengthIn,heightIn,-width,
				//23
				lengthIn,heightIn,-width,				
				lengthMid,0,width,
				lengthIn,heightIn,width,
				//24
				lengthMid,0,width,				
				lengthIn,heightIn,-width,
				lengthMid,0,-width
				
				
			};
			vCount=verteices.length/3;
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float[] textureCoors=
			{
					//1
					0,0.5f,0.17f,0.5f,0.34f,0,
					//2
					0.34f,0,0.17f,0.5f,0.34f,0.25f,
					//3
					0.34f,0,0.34f,0.25f,0.68f,0,
					//4
					0.68f,0,0.34f,0.25f,0.68f,0.25f,
					//5
					0.68f,0.25f,1,0.5f,0.68f,0,
					//6
					0.68f,0.25f,0.85f,0.5f,1,0.5f,
					//7
					0,0.5f,0.34f,0,0.17f,0.5f,
					//8
					0.17f,0.5f,0.34f,0,0.34f,0.25f,
					//9
					0.34f,0,0.68f,0,0.34f,0.25f,
					//10
					0.68f,0,0.68f,0.25f,0.34f,0.25f,
					//11
					0.68f,0,1,0.5f,0.68f,0.25f,
					//12
					0.68f,0.25f,1,0.5f,0.85f,0.5f,
					//13
					0,0.5f,0,1,0.34f,1,
					//14
					0.34f,1,0.34f,0.5f,0,0.5f,
					//15
					0.34f,0.5f,0.34f,1,0.68f,1,
					//16
					0.68f,1,0.68f,0.5f,0.34f,0.5f,
					//17
					0.68f,0.5f,0.68f,1,1,1,
					//18
					1,1,1,0.5f,0.68f,0.5f,
					//19
					0,0.5f,0.34f,1,0,1,
					//20
					0.34f,1,0,0.5f,0.34f,0.5f,
					//21
					0.34f,0.5f,0.68f,1,0.34f,1,
					//22
					0.68f,1,0.34f,0.5f,0.68f,0.5f,
					//23
					0.68f,0.5f,1,1,0.68f,1,
					//24
					1,1,0.68f,0.5f,1,0.5f
					
					
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
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//指点顶点数据缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//允许使用纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理坐标数据缓冲
			
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数据缓冲
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//以三角形方式绘制矩形
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//禁止使用纹理坐标数据
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//禁止使用顶点数据
			
		}
	}
}
