package wyf.swq;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Lines {
	private IntBuffer myVertexBuffer;			//顶点坐标数据缓冲
	private IntBuffer myColorBuffer;			//顶点着色数据缓冲
	private ByteBuffer myIndexBuffer;		//顶点构建的索引数据缓冲
	
	int vCount=0;						//声明顶点变量
	int iCount=0;						//声明索引变量

	public Lines(){
		vCount=4;						//顶点数量
		final int UNIT_SIZE=10000;			//缩放比例
		int []vertices=new int[]
	       {
				-2*UNIT_SIZE,3*UNIT_SIZE,0,
				1*UNIT_SIZE,1*UNIT_SIZE,0,
				-1*UNIT_SIZE,-2*UNIT_SIZE,0,
				2*UNIT_SIZE,-3*UNIT_SIZE,0
	       };
		//创建顶点坐标数据缓存，由于不同平台字节顺序不同，数据单元不是字节的（上面的事整型的缓存），一定要经过ByteBuffer转换，关键是通过ByteOrder设置nativeOrder()
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//一个整数四个字节，根据最新分配的内存块来创建一个有向的字节缓冲
		vbb.order(ByteOrder.nativeOrder());//设置这个字节缓冲的字节顺序为本地平台的字节顺序
		myVertexBuffer=vbb.asIntBuffer();//转换为int型缓冲
		myVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
		myVertexBuffer.position(0);//设置缓冲区的起始位置
		
		final int one=65535;//支持65535色色彩通道
		int []colors=new int[]//顶点颜色值数组，每个顶点4个色彩值RGBA
		{
			one,one,one,0,
			one,one,one,0,
			one,one,one,0,
			one,one,one,0
		};
		ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);
		cbb.order(ByteOrder.nativeOrder());
		myColorBuffer=cbb.asIntBuffer();
		myColorBuffer.put(colors);
		myColorBuffer.position(0);
		//为三角形构造索引数据初始化
		iCount=4;
		byte []indices=new byte[]
            {
				0,3,2,1
            };
		//创建三角形构造索引数据缓冲
		myIndexBuffer=ByteBuffer.allocateDirect(indices.length);
		myIndexBuffer.put(indices);
		myIndexBuffer.position(0);
	}
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组	
		gl.glVertexPointer(				//为画笔指定顶点坐标数据
				3,					//每个顶点的坐标组成数量
				GL10.GL_FIXED,		//顶点坐标值的类型
				0,					//连续顶点坐标数据之间的间隔
				myVertexBuffer		//顶点坐标数据
		);
		gl.glColorPointer(				//为画笔指定顶点 颜色数据
			4,							//每个顶点的颜色值组成数量
			GL10.GL_FIXED,			//顶点颜色值的类型
			0,						//连续颜色数据之间的间隔
			myColorBuffer			//顶点颜色数据
		);
		gl.glDrawElements( //索引法绘制图形
			GL10.GL_LINES,          //以线方式填充
			iCount,					//索引数量
			GL10.GL_UNSIGNED_BYTE,	//索引值的类型
			myIndexBuffer			//索引值数据
		);
	}}

