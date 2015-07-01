package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ZhuiTi {

	private FloatBuffer mVertexBuffer; //顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;// 纹理坐标数据缓冲
	int textureId;//纹理ID
	int vCount;//顶点数量
	float mOffsetX;
	float mOffsetY;
	float[] tempVerteices;//声明顶点数组
	public ZhuiTi(int textureId,float length,float hight,float width)
	{
		this.textureId=textureId;
		float UNIT_SIZE=1.0f;
		float[] verteices=
		{
				//背面
				0,UNIT_SIZE*hight,0,
				UNIT_SIZE*length,0,-UNIT_SIZE*width,
				-UNIT_SIZE*length,0,-UNIT_SIZE*width,
				//左面
				0,UNIT_SIZE*hight,0,
				-UNIT_SIZE*length,0,-UNIT_SIZE*width,
				-UNIT_SIZE*length,0,UNIT_SIZE*width,
				//前面
				0,UNIT_SIZE*hight,0,
				-UNIT_SIZE*length,0,UNIT_SIZE*width,
				UNIT_SIZE*length,0,UNIT_SIZE*width,
				//右面
				0,UNIT_SIZE*hight,0,
				UNIT_SIZE*length,0,UNIT_SIZE*width,
				UNIT_SIZE*length,0,-UNIT_SIZE*width,
				//底面
				-UNIT_SIZE*length,0,-UNIT_SIZE*width,
				UNIT_SIZE*length,0,-UNIT_SIZE*width,
				-UNIT_SIZE*length,0,UNIT_SIZE*width,
				
				-UNIT_SIZE*length,0,UNIT_SIZE*width,
				UNIT_SIZE*length,0,-UNIT_SIZE*width,
				UNIT_SIZE*length,0,UNIT_SIZE*width
				
		};
		vCount=verteices.length/3;//获取定点数量
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
		tempVerteices=verteices;
		
		float[] textureCoors=new float[vCount*2];
		for(int i=0;i<vCount/3;i++)//分配纹理坐标
		{
			textureCoors[i*6]=0;
			textureCoors[i*6+1]=0;
			
			textureCoors[i*6+2]=0;
			textureCoors[i*6+3]=1;
			
			textureCoors[i*6+4]=1;
			textureCoors[i*6+5]=0;
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建纹理坐标数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标
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
		
		gl.glPushMatrix();
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
		gl.glPopMatrix();
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
		
	}
}
