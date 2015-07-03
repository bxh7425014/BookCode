package com.guo.myball;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import static com.guo.myball.Constant.*;
//表示地板的类
public class Floor {
	//顶点坐标数据缓冲
	private FloatBuffer   mVertexBuffer;
	//顶点纹理数据缓冲
    private FloatBuffer mTextureBuffer;
    //顶点法向量缓冲类
    private FloatBuffer mNormalBuffer;
    //顶点数量
    int vCount=0;
    //地板横向width个单位
    int width;
    //地板纵向height个单位
    int height;
    //构造函数
    public Floor(int width,int height)
    {
    	this.width=width;
    	this.height=height;
    	//顶点坐标数据的初始化-------begin
    	//每个地板块6个顶点
        vCount=6;
    	float []vertices=new float[]
    	{
    			-width*UNIT_SIZE/2,0,-height*UNIT_SIZE/2,
    			-width*UNIT_SIZE/2,0,height*UNIT_SIZE/2,
    			width*UNIT_SIZE/2,0,height*UNIT_SIZE/2,
    		
    			width*UNIT_SIZE/2,0,height*UNIT_SIZE/2,
    			width*UNIT_SIZE/2,0,-height*UNIT_SIZE/2,
    			-width*UNIT_SIZE/2,0,-height*UNIT_SIZE/2
    	};  
    	//分配空间
    	ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
    	//设置字节顺序
        vbb.order(ByteOrder.nativeOrder());
        //转换为float型缓冲
        mVertexBuffer = vbb.asFloatBuffer();
        //向缓冲区中放入顶点坐标数据
        mVertexBuffer.put(vertices);
        //设置缓冲区起始位置    
        mVertexBuffer.position(0);   
        float textures[]=new float[]
        {
        		0,0,
        		0,2,
        		2,2,
        		
        		2,2,
        		2,0,
        		0,0
        };
        //顶点数据的初始化------end
        //顶点纹理数据的初始化-------begin
        //分配空间
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        //设置字节顺序
        tbb.order(ByteOrder.nativeOrder());
        //转换为Float型缓冲
        mTextureBuffer= tbb.asFloatBuffer();
        //向缓冲区中放入顶点纹理数据
        mTextureBuffer.put(textures);
        //设置缓冲区起始位置
        mTextureBuffer.position(0);
        //由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理数据的初始化--------end
        
        //顶点的初始化------begin
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount;i++)
        {
        	normals[i*3]=0;
        	normals[i*3+1]=1;
        	normals[i*3+2]=0;
        }
        //分配空间
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        //设置字节顺序
        nbb.order(ByteOrder.nativeOrder());
        //转换为float型缓冲
        mNormalBuffer = nbb.asFloatBuffer();
        //向缓冲区中放入顶点法向量数据
        mNormalBuffer.put(normals);
        //设置缓冲区起始位置
        mNormalBuffer.position(0);
        //顶点法向量数据的初始化-------------end              
    }
    //绘制地板
    public void drawSelf(GL10 gl,int texId)
    {              
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3， xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FLOAT
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );      
        
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
        //设置法向量
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );
    }
}