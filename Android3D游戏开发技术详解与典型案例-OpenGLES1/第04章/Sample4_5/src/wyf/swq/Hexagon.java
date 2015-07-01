package wyf.swq;
import java.nio.ByteBuffer;					//引入相关包
import java.nio.ByteOrder;					//引入相关包
import java.nio.IntBuffer;					//引入相关包
import javax.microedition.khronos.opengles.GL10;
public class Hexagon {
	private IntBuffer   mVertexBuffer;		//顶点坐标数据缓冲
    private IntBuffer   mColorBuffer;		//顶点着色数据缓冲
    private ByteBuffer  mIndexBuffer;		//顶点构建索引数据缓冲
    int vCount=0;							//图形顶点数量
    int iCount=0;							//索引顶点数量
    public Hexagon(int zOffset){
    	//顶点坐标数据的初始化
        vCount=7;
        final int UNIT_SIZE=10000;
        int vertices[]=new int[]{
        	0*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	2*UNIT_SIZE,3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	4*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	2*UNIT_SIZE,-3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-2*UNIT_SIZE,-3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-4*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-2*UNIT_SIZE,3*UNIT_SIZE,zOffset*UNIT_SIZE
        };
        //创建顶点坐标数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asIntBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //顶点着色数据的初始化
        final int one = 65535;
        int colors[]=new int[]//顶点颜色值数组，每个顶点4个色彩值RGBA
        {
        		0,0,one,0,
        		0,one,0,0,
        		0,one,one,0,
        		
        		one,0,0,0,
        		one,0,one,0,
        		one,one,0,0,
        		one,one,one,0
        };
        //创建顶点着色数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mColorBuffer = cbb.asIntBuffer();//转换为int型缓冲
        mColorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        mColorBuffer.position(0);//设置缓冲区起始位置
        //三角形构造索引数据初始化
        iCount=18;
        byte indices[]=new byte[]{
        	0,2,1,
        	0,3,2,
        	0,4,3,
        	0,5,4,
        	0,6,5,
        	0,1,6
        };
        //创建三角形构造索引数据缓冲
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);//向缓冲区中放入三角形构造索引数据
        mIndexBuffer.position(0);//设置缓冲区起始位置
    }
    public void drawSelf(GL10 gl){        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组
        gl.glVertexPointer(//为画笔指定顶点坐标数据     
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FIXED,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        gl.glColorPointer(//为画笔指定顶点着色数据     
        		4, 				//设置颜色的组成成分，必须为4—RGBA
        		GL10.GL_FIXED, 	//顶点颜色值的类型为 GL_FIXED
        		0, 				//连续顶点着色数据之间的间隔
        		mColorBuffer	//顶点着色数据
        );
        gl.glDrawElements(//索引法绘制图形
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		iCount, 			 	//一共icount/3个三角形，iCount个顶点
        		GL10.GL_UNSIGNED_BYTE, 	//索引值的尺寸
        		mIndexBuffer			//索引值数据
        ); 
    }}

