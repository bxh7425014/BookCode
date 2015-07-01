package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CubeIndex {
	private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mNormalBuffer;//法向量数据缓冲
	private ByteBuffer mIndexBuffer;//索引数据缓冲
	public float mOffsetX;
	public float mOffsetY;//绕Y轴旋转
	float scale;						//立方体高度	
	int iCount;//索引的数量
	public CubeIndex(float scale,float length,float width)
	{
		this.scale=scale;
		float UNIT_SIZE=0.5f;
		float UNIT_HIGHT=0.5f;
		float[] verteices=
		{
				-UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,//0号点
				UNIT_SIZE*length,UNIT_HIGHT*scale,-UNIT_SIZE*width,//1号点
				-UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,//2号点
				UNIT_SIZE*length,UNIT_HIGHT*scale,UNIT_SIZE*width,//3号点
				
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,//4号点
				UNIT_SIZE*length,-UNIT_HIGHT*scale,-UNIT_SIZE*width,//5号点
				-UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width,//6号点
				UNIT_SIZE*length,-UNIT_HIGHT*scale,UNIT_SIZE*width//7号点
				
						
		};
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
		byte[] indices=
		{
				//后面
				4,0,5,
				5,0,1,
				//前面
				2,6,3,
				3,6,7,
				//顶面
				0,2,1,
				1,2,3,
				//底面
				6,4,7,
				7,4,5,
				//左面
				4,6,2,
				2,0,4,
				//右面
				1,3,7,
				7,5,1
		};
		//创建三角形构造索引数据缓冲
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);//向缓冲区中放入三角形构造索引数据
        mIndexBuffer.position(0);//设置缓冲区起始位置
        iCount=indices.length;
        float[] normals=
		{
				-1,1,-1,//		-1/3,1/3,-1/3,
				1,1,-1,//		1/3,1/3,-1/3,
				-1,1,1,//		-1/3,1/3,1/3,
				1,1,1,//		1/3,1/3,1/3,
				
				-1,-1,-1,//		-1/3,-1/3,-1/3,
				1,-1-1,//		1/3,-1/3,-1/3,
				-1,-1,1,//		-1/3,-1/3,1/3,
				1,-1,1//		1/3,-1/3,1/3
				
		};
		ByteBuffer nbb=ByteBuffer.allocateDirect(normals.length*4);//创建法向量坐标数据缓冲
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
        
  		gl.glDrawElements(GL10.GL_TRIANGLES, iCount, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);//索引法绘制图形	
	}

}
