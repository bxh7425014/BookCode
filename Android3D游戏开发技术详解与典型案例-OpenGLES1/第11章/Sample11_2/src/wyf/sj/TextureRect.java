package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TextureRect {

	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
    int vCount;//顶点数量
    int texId;//纹理Id
    
    public TextureRect(int texId,double startAngle)
    {
    	this.texId=texId;
    	
    	final int cols=20;//列数
    	final int rows=cols*3/4;//行数
    	final float UNIT_SIZE=1.5f/cols;//每格的单位长度
    	final double angleSpan=Math.PI*4/cols;//梅格的单位弧度
    	
    	
    	//顶点坐标数据的初始化================begin============================
        vCount=cols*rows*2*3;//每个格子两个三角形，每个三角形3个顶点        
        float vertices[]=new float[vCount*3];//每个顶点xyz三个坐标
        int count=0;//顶点计数器
        for(int j=0;j<rows;j++)
        {
        	for(int i=0;i<cols;i++)
        	{        		
        		//计算当前格子左上侧点坐标 
        		float zsx=-UNIT_SIZE*cols/2+i*UNIT_SIZE;
        		float zsy=UNIT_SIZE*rows/2-j*UNIT_SIZE;
        		float zsz=(float)Math.sin(startAngle+i*angleSpan)*0.1f;
        		//计算右边一列的z坐标
        		float zszp=(float)Math.sin(startAngle+(i+1)*angleSpan)*0.1f;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy;
        		vertices[count++]=zszp;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy;
        		vertices[count++]=zszp;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zsz;
        		        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zszp;   
        	}
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
    	float textures[]=generateTexCoor(cols,rows);
        
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
    }
    
   //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//列数
    	float sizeh=0.75f/bh;//行数
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}
