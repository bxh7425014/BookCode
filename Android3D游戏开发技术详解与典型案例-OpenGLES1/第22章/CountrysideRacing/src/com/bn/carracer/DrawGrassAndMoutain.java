package com.bn.carracer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.carracer.Constant.*;

//代表陆地的类-包括山和草地
public class DrawGrassAndMoutain {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
    int vCount;//顶点数量
    int texId;//纹理Id
    
    public void setTexId(int texId)
    {
    	this.texId=texId;
    }
    
    public DrawGrassAndMoutain(short[][] yArray,int rows,int cols)
    {    	
    	//顶点坐标数据的初始化================begin============================
        
        ArrayList<Float> alf=new ArrayList<Float>();
        
        for(int j=0;j<rows;j++)//循环行
        {
        	for(int i=0;i<cols;i++)//循环列
        	{        		     
       		
        		float zsx=X_OFFSET+i*UNIT_SIZE;
        		float zsz=Z_OFFSET+j*UNIT_SIZE;     
        		
        		alf.add(zsx);//左上
        		alf.add(yArray[j][i]*LAND_HIGHEST/255.0f);
        		alf.add(zsz);
        		
        		alf.add(zsx);//左下
        		alf.add(yArray[j+1][i]*LAND_HIGHEST/255.0f);
        		alf.add(zsz+UNIT_SIZE);
        		
        		alf.add(zsx+UNIT_SIZE);//右上
        		alf.add(yArray[j][i+1]*LAND_HIGHEST/255.0f);
        		alf.add(zsz); 
        		 
        		alf.add(zsx+UNIT_SIZE);//右上
        		alf.add(yArray[j][i+1]*LAND_HIGHEST/255.0f);
        		alf.add(zsz);
        		
        		alf.add(zsx);//左下
        		alf.add(yArray[j+1][i]*LAND_HIGHEST/255.0f);
        		alf.add(zsz+UNIT_SIZE);
        		
        		alf.add(zsx+UNIT_SIZE);//右下
        		alf.add(yArray[j+1][i+1]*LAND_HIGHEST/255.0f);
        		alf.add(zsz+UNIT_SIZE);
        	}
        }
        
        vCount=alf.size()/3;//实际顶点数
       
        float vertices[]=new float[alf.size()];
        for(int i=0;i<alf.size();i++)
        {
        	vertices[i]=alf.get(i);
        }
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================
        
        //顶点纹理数据的初始化================begin============================
    	//自动生成纹理数组，20列15行
    	float textures[]=generateTexCoor(rows,cols,yArray);
        
        //创建顶点纹理数据缓冲
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTextureBuffer= tbb.asFloatBuffer();//转换为Float型缓冲
        mTextureBuffer.put(textures);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理数据的初始化================end============================
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		
        //开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理ST坐标缓冲
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays    
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0,
        		vCount 
        );
        
        //关闭纹理
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D); 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
   //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bh,int bw,short[][] yArray)
    {
    	ArrayList<Float> alf=new ArrayList<Float>();

    	float sizeh=T/bh;//行数
    	float sizew=S/bw;//列数 
    	
    	for(int i=0;i<bh;i++)//行循环
    	{
    		for(int j=0;j<bw;j++)//列循环
    		{
    				//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
        			float s=j*sizew;
        			float t=i*sizeh; 
        			   
        			alf.add(s);//左上
        			alf.add(t);
        			
        			alf.add(s);//左下
        			alf.add(t+sizeh);
        			
        			alf.add(s+sizew);//右上
        			alf.add(t);
        			
        			alf.add(s+sizew);//右上
        			alf.add(t);			
        			  
        			alf.add(s);//左下
        			alf.add(t+sizeh);
        			
        			alf.add(s+sizew);//右下
        			alf.add(t+sizeh); 
    		}
    	}
    	
    	float[] result=new float[alf.size()]; 
    	for(int i=0;i<alf.size();i++)
        {
    		result[i]=alf.get(i);
        }
    	return result;
    }
}
