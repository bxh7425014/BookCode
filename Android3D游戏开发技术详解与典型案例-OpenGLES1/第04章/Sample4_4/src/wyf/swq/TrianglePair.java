package wyf.swq;
import java.nio.ByteBuffer;								//引人相关包
import java.nio.ByteOrder;								//引人相关包
import java.nio.IntBuffer;								//引人相关包
import javax.microedition.khronos.opengles.GL10;		//引人相关包
public class TrianglePair {
	private IntBuffer myVertexBuffer;			//顶点坐标数据缓冲
	private IntBuffer myColorBuffer;			//顶点着色数据缓冲
	int vCount=0;								//顶点数量
	float yAngle=0;								//绕y轴旋转的角度
	float zAngle=0;								//绕z轴旋转的角度
	public TrianglePair(){
		vCount=6;								//一个三角形，3个顶点
		final int UNIT_SIZE=10000;				//缩放比例
		int []vertices=new int[]{
				-8*UNIT_SIZE,10*UNIT_SIZE,0,
	        	-2*UNIT_SIZE,2*UNIT_SIZE,0,
	        	-8*UNIT_SIZE,2*UNIT_SIZE,0,
	        	8*UNIT_SIZE,2*UNIT_SIZE,0,
	        	8*UNIT_SIZE,10*UNIT_SIZE,0,
	        	2*UNIT_SIZE,10*UNIT_SIZE,0
	       };
		//创建顶点坐标数据缓存，由于不同平台字节顺序不同，数据单元不是字节的（上面的事整型的缓存），一定要经过ByteBuffer转换，关键是通过ByteOrder设置nativeOrder()
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//分配的内存块
		vbb.order(ByteOrder.nativeOrder());//设置本地平台的字节顺序
		myVertexBuffer=vbb.asIntBuffer();//转换为int型缓冲
		myVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
		myVertexBuffer.position(0);//设置缓冲区的起始位置
		final int one=65535;//支持65535色色彩通道
		int []colors=new int[]{//顶点颜色值数组，每个顶点4个色彩值RGBA		
				one,one,one,0,
        		0,0,one,0,
        		0,0,one,0,
        		one,one,one,0,
        		one,0,0,0,
        		one,0,0,0 
		};
		ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);		//分配的内存块
		cbb.order(ByteOrder.nativeOrder());		//设置本地平台的字节顺序
		myColorBuffer=cbb.asIntBuffer();		//转换为int型缓冲
		myColorBuffer.put(colors);				//向缓冲区中放入顶点颜色数据
		myColorBuffer.position(0);				//设置缓冲区的起始位置
	}
	public void drawSelf(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组
		gl.glRotatef(yAngle,0,1,0);//根据yAngle的角度值，绕y轴旋转yAngle
		gl.glRotatef(zAngle,0,0,1);
		gl.glVertexPointer(			//为画笔指定顶点坐标数据
				3,					//每个顶点的坐标数量为3
				GL10.GL_FIXED,		//顶点坐标值的类型为GL_FIXED,整型
				0,					//连续顶点坐标数据之间的间隔
				myVertexBuffer		//顶点坐标数量
		);
		gl.glColorPointer(//为画笔指定顶点 颜色数据
			4,
			GL10.GL_FIXED,
			0,
			myColorBuffer
		);
		gl.glDrawArrays(//绘制图形
			GL10.GL_TRIANGLES,
			0,				//开始点编号
			vCount
		);
	}}
