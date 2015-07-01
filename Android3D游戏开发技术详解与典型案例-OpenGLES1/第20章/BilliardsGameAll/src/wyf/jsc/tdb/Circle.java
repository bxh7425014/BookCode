package wyf.jsc.tdb;

import static wyf.jsc.tdb.Constant.TABLE_UNIT_SIZE;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Circle {


	FloatBuffer mVertexBuffer;
	FloatBuffer mTextureBuffer;	
	int vCount;
	int textureId;
	public Circle(float scaleX,float scaleZ,int textureId)
	{
		this.textureId=textureId;	
		float SPAN=18;
		ArrayList<Float> alVertex=new ArrayList<Float>();
		for(int i=0;i<360/SPAN;i++)
		{
			float x1=0.0f;float y1=0.0f;float z1=0.0f;//坐标轴原点
			//根据单位角度计算第二个点坐标
			float x2=(float)Math.cos(Math.toRadians(i*SPAN))*scaleX*TABLE_UNIT_SIZE;
			float y2=0.0f;
			float z2=(float)Math.sin(Math.toRadians(i*SPAN))*scaleZ*TABLE_UNIT_SIZE;
			//计算第三个点坐标
			float x3=(float)Math.cos(Math.toRadians((i+1)*SPAN))*scaleX*TABLE_UNIT_SIZE;
			float y3=0.0f;
			float z3=(float)Math.sin(Math.toRadians((i+1)*SPAN))*scaleZ*TABLE_UNIT_SIZE;
			//按逆时针方向摆放顶点，组成三角形
			alVertex.add(x1);
			alVertex.add(y1);
			alVertex.add(z1);	
			alVertex.add(x3);
			alVertex.add(y3);
			alVertex.add(z3);		
			alVertex.add(x2);
			alVertex.add(y2);
			alVertex.add(z2);		
		}
		vCount=alVertex.size()/3;
		float[] verteices=new float[vCount*3];
		for(int i=0;i<vCount*3;i++)
		{
			verteices[i]=alVertex.get(i);
		}	
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4);//将顶点坐标放入数据缓冲
		vbb.order(ByteOrder.nativeOrder());	//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置 
				
		float[] textureCoors=new float[vCount*2];//分配纹理坐标
		for(int i=0;i<vCount/3;i++)
		{
			textureCoors[i*6]=0;
			textureCoors[i*6+1]=0;
			
			textureCoors[i*6+2]=0;
			textureCoors[i*6+3]=1;
			
			textureCoors[i*6+4]=1;
			textureCoors[i*6+5]=0;
		}
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//将纹理坐标坐标放入数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
		mTextureBuffer.position(0);//设置缓冲区起始位置
	}
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//允许使用顶点数组
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//为画笔指定顶点坐标数据
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理坐标数据
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//绑定纹理ID
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//以三角形方式填充
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
		gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
	}
}
