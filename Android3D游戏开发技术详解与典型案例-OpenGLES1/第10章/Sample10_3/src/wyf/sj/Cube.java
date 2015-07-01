package wyf.sj;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
	private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;	//纹理坐标数据缓冲
	public float mOffsetX;
	public float mOffsetY;
	float scale;						//立方体大小
	int textureId;						//纹理ID
	int vCount;//顶点数量
	public Cube(int textureId,float scale,float length,float width)
	{
		this.scale=scale;
		this.textureId=textureId;
		vCount=36;
		float TABLE_UNIT_SIZE=0.5f;
		float TABLE_UNIT_HIGHT=0.5f;
		float[] verteices=
		{
				
				//顶面
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//后面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				//前面
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//下面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				//左面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//右面
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width
						
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
			textureCoors[(i*12)+3]=1;
			
			textureCoors[(i*12)+4]=1;
			textureCoors[(i*12)+5]=0;
			
			textureCoors[(i*12)+6]=1;
			textureCoors[(i*12)+7]=0;
			
			textureCoors[(i*12)+8]=0;
			textureCoors[(i*12)+9]=1;
			
			textureCoors[(i*12)+10]=1;
			textureCoors[(i*12)+11]=1;		

		}
		
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建顶点坐标数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
		mTextureBuffer.position(0);//设置缓冲区起始位置
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mOffsetX, 1, 0, 0);
		gl.glRotatef(mOffsetY, 0, 1, 0);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数组
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//绑定纹理
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
		
	}

}
