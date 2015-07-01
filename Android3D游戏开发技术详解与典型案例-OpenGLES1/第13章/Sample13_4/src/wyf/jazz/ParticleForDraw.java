package wyf.jazz;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

//用于绘制单个粒子的类
public class ParticleForDraw {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	private IntBuffer   mColorBuffer;//顶点着色数据缓冲
    float scale;//粒子尺寸
    
    public ParticleForDraw(float scale,float r,float g,float b,float a)
    {
    	this.scale=scale;
    	
    	//顶点坐标数据的初始化================begin=======================================       
        float vertices[]=new float[]//顶点坐标值数组
        {
        	0f,0f,0f	
        };
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个Float四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================ 
        
        //顶点着色数据的初始化================begin============================
        final int one = 65535;
        int colors[]=new int[]//顶点颜色值数组，每个顶点4个色彩值RGBA
        {
             (int)(one*r),
             (int)(one*g),
             (int)(one*b),
             (int)(one*a),
        };
        
        //创建顶点着色数据缓冲
        //vertices.length*4是因为一个int型整数四个字节
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mColorBuffer = cbb.asIntBuffer();//转换为int型缓冲
        mColorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        mColorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组

        gl.glPointSize(scale);//设置焰火粒子尺寸
        
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
        		GL10.GL_FIXED, 	//顶点颜色值的类型为 GL_FIXED
        		0, 				//连续顶点着色数据之间的间隔
        		mColorBuffer	//顶点着色数据
        );
		
        //绘制 点
        gl.glDrawArrays
        (
        		GL10.GL_POINTS, 		//以点方式填充
        		0, 			 			//开始点编号
        		1					//顶点的数量
        );
        
        gl.glPointSize(1);//恢复像素尺寸
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//禁用顶点颜色数组
    }
}
