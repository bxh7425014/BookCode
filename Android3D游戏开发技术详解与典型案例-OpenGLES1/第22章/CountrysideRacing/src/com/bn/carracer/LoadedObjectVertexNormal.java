package com.bn.carracer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
public class LoadedObjectVertexNormal 
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer   mNormalBuffer;//顶点法向量数据缓冲
    int vCount=0; 
      
    public LoadedObjectVertexNormal(float[] vertices,float[] normals) 
    { 
    	//顶点坐标数据的初始化================begin============================
        vCount=vertices.length/3;    
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        
        ByteBuffer vbn = ByteBuffer.allocateDirect(normals.length*4);
        vbn.order(ByteOrder.nativeOrder());//设置字节顺序
        mNormalBuffer = vbn.asFloatBuffer();//转换为Float型缓冲
        mNormalBuffer.put(normals);//向缓冲区中放入顶点坐标数据
        mNormalBuffer.position(0);//设置缓冲区起始位置 
    }

    public void drawSelf(GL10 gl,float r,float g,float b)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//启用顶点法向量数组

        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        ); 
        
        //为画笔指定顶点法向量数据
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        
        setMaterial(gl,r,g,b);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );          
        
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//禁用顶点法向量数组
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
    }
    
    public void setMaterial(GL10 gl,float r,float g,float b)
    {
    	float ambientMaterial[] = {0.4f*r, 0.4f*g, 0.4f*b, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光为白色材质
        float diffuseMaterial[] = {0.5f*r, 0.5f*g, 0.5f*b, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质为白色
        float specularMaterial[] = {0.8f*r, 0.8f*g, 0.8f*b, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        //高光反射区域,数越大高亮区域越小越暗
        float shininessMaterial[] = {1.5f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
    }
}
