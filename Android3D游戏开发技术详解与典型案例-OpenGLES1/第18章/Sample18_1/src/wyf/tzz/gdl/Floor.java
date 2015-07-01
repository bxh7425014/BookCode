package wyf.tzz.gdl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static wyf.tzz.gdl.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class Floor
{
	FloatBuffer mVertexBuffer;//定点缓冲
	FloatBuffer mTextureBuffer;//纹理缓冲
	int drawableId;//纹理id
	int vCount=6;//定点个数
	public Floor(int drawableId)
	{
		this.drawableId=drawableId;
		initVertex();//初始化定点
		initTexture();//初始化纹理
	}
	public void initVertex()
	{
		float x=UNIT_SIZE*FLOOR_LENGTH/2;//最大x坐标值
		float y=0;//y坐标值
		float z=UNIT_SIZE*FLOOR_WIDTH/2;//最大z坐标值
		float[] vertices=
		{
				-x,y,-z,
				-x,y,z,
				x,y,-z,
				
				-x,y,z,
				x,y,z,
				x,y,-z
		};
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//每个float4个byte
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float缓冲
		mVertexBuffer.put(vertices);//加入顶点数组
		mVertexBuffer.position(0);//设置起始位置
	}
	public void initTexture()
	{
		float[] textures=new float[vCount*2];
		int temp=0;
		//左上三角形
		textures[temp++]=0;
		textures[temp++]=0;
		
		textures[temp++]=0;
		textures[temp++]=1;
		 
		textures[temp++]=1;
		textures[temp++]=0;
		
		
		//右下三角形
		textures[temp++]=0;
		textures[temp++]=1;
		
		textures[temp++]=1;
		textures[temp++]=1;
		
		textures[temp++]=1;
		textures[temp++]=0;
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//每个float4个byte
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float缓冲
		mTextureBuffer.put(textures);//加入定点数组
		mTextureBuffer.position(0);//设置起始位置
	}
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启顶点数组
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//为画笔指定顶点数据
		gl.glEnable(GL10.GL_TEXTURE_2D);//启用纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);	//为画笔指定纹理数据
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定纹理
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭定点数组
	}
}