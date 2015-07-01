package wyf.zs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
public class Diamond {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer  mColorBuffer;//颜色数据缓冲
    public float yAngle=0;
    public float xOffset;
    public float yOffset;
    public float zOffset;
    public int vCount=0;
    public Diamond()
    {
    	float UNIT_SIZE=0.5f;
    	vCount=6;
    	//定点数组
    	float vertices[]=new float[]
    	{ 		
    			0,4*UNIT_SIZE,0,
    			-4*UNIT_SIZE,0,0,
    			0,-4*UNIT_SIZE,0,
    			
    			0,-4*UNIT_SIZE,0,
    			4*UNIT_SIZE,0,0,
    			0,4*UNIT_SIZE,0			
    			
    	};
    	ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
    	vbb.order(ByteOrder.nativeOrder());
    	mVertexBuffer=vbb.asFloatBuffer();
    	mVertexBuffer.put(vertices);
    	mVertexBuffer.position(0);
    	
    	float one=65535;
    	//颜色数组
    	float colors[]=new float[]
    	{
    			one,one,one,0,
        		0,one,one,0,
        		0,0,one,0,
        		
        		one,one,one,0,
        		0,0,one,0,
        		one,0,0,0 
    	};
    	ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);
    	cbb.order(ByteOrder.nativeOrder());
    	mColorBuffer=cbb.asFloatBuffer();
    	mColorBuffer.put(colors);
    	mColorBuffer.position(0);
    	 
    }
    public void drawSelf(GL10 gl)
    {
    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
    	gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组
    	
    	gl.glRotatef(yAngle, 0, 1, 0);//沿y轴旋转
    	gl.glTranslatef(0, yOffset, 0);
    	gl.glTranslatef(xOffset, 0, 0);
    	
    	//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		
        //为画笔指定顶点着色数据
        gl.glColorPointer
        (
        		4, 				//设置颜色的组成成分，必须为4—RGBA
        		GL10.GL_FLOAT, 	//顶点颜色值的类型为 GL_FIXED
        		0, 				//连续顶点着色数据之间的间隔
        		mColorBuffer	//顶点着色数据
        );
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}

