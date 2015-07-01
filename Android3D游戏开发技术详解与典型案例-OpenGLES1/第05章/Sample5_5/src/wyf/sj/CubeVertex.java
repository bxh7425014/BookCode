package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CubeVertex {

	private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mNormalBuffer;//法向量数据缓冲
	public float mOffsetX;
	public float mOffsetY;//绕Y轴旋转
	float scale;						//立方体高度			
	int vCount;//顶点数量
	public CubeVertex(float scale,float length,float width)
	{
		this.scale=scale;
		vCount=36;
		float UNIT_SIZE=0.5f;
		float UNIT_HIGHT=0.5f;
		float[] verteices=
		{
				
				//顶面
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//后面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				//前面
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//下面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				//左面
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				
				//右面
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width
						
		};
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
	
		float[] normals=
		{
				//顶面顶点法向量
				0,1,0,
				0,1,0,
				0,1,0,
				
				0,1,0,
				0,1,0,
				0,1,0,
				//后面顶点法向量
				0,0,-1,
				0,0,-1,
				0,0,-1,
				
				0,0,-1,
				0,0,-1,
				0,0,-1,
				//前面顶点法向量
				0,0,1,
				0,0,1,
				0,0,1,
				
				0,0,1,
				0,0,1,
				0,0,1,
				//下面顶点法向量
				0,-1,0,
				0,-1,0,
				0,-1,0,
				
				0,-1,0,
				0,-1,0,
				0,-1,0,
				//左面顶点法向量
				-1,0,0,
				-1,0,0,
				-1,0,0,
				
				-1,0,0,
				-1,0,0,
				-1,0,0,
				//右面顶点法向量
				1,0,0,
				1,0,0,
				1,0,0,
				
				1,0,0,
				1,0,0,
				1,0,0
		};
		ByteBuffer nbb=ByteBuffer.allocateDirect(normals.length*4);//创建颜色坐标数据缓冲
		nbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mNormalBuffer=nbb.asFloatBuffer();//转换为float型缓冲
		mNormalBuffer.put(normals);//向缓冲区中放入顶点坐标数据
		mNormalBuffer.position(0);//设置缓冲区起始位置
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mOffsetX, 1, 0, 0);
		gl.glRotatef(mOffsetY, 0, 1, 0);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//允许使用顶点数组
		
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//允许使用法向量数组
		gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);//为画笔指定顶点法向量数据
        
  		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图形	
	}

}
