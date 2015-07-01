
package wyf.tzz.lta;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//纹理矩形
public class Plane_BackWing {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
    int vCount ;
    int texId;
    float mAngleX ;
    
    public Plane_BackWing(int texId,float width,float height,float length)
    {
    	this.texId=texId;
    	
    	//顶点坐标数据的初始化================begin============================
        float vertices[]=new float[]
        {
        		//上表面
        		0,height,length,-width,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,length,//OAB        		
        		
        		0,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,length,-width,-height,length,//OBC
        		
        		0,height,length,-width,-height,length,width,-height,length,//OCD
        		
        		0,height,length,width,-height,length,13.0f/10.0f*width,2.0f/5.0f*height,length,//ODE

        		0,height,length,13.0f/10.0f*width,2.0f/5.0f*height,length,width,height,length,//OEF
        		
        		//下表面
        		0,height,-length,-width,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,//OAB        		
        		
        		0,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-width,-height,-length,//OBC
        		
        		0,height,-length,-width,-height,-length,width,-height,-length,//OCD
        		
        		0,height,-length,width,-height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,//ODE

        		0,height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,width,height,-length,//OEF
     
        		-width,height,length,-width,height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,
        		0-width,height,length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-13.0f/10.0f*width,2.0f/5.0f*height,length,
        		-13.0f/10.0f*width,2.0f/5.0f*height,length,-13.0f/10.0f*width,2.0f/5.0f*height,-length,-width,-height,-length,
        		-13.0f/10.0f*width,2.0f/5.0f*height,length,-width,-height,-length,-width,-height,length,
        		-width,-height,length,-width,-height,-length,width,-height,-length,
        		-width,-height,length,width,-height,-length,width,-height,length,
        		width,-height,length,width,-height,-length,13.0f/10.0f*width,2.0f/5.0f*height,-length,
        		width,-height,length,13.0f/10.0f*width,2.0f/5.0f*height,-length,13.0f/10.0f*width,2.0f/5.0f*height,length,
        		13.0f/10.0f*width,2.0f/5.0f*height,length,13.0f/10.0f*width,2.0f/5.0f*height,-length,width,height,-length,
        		13.0f/10.0f*width,2.0f/5.0f*height,length,width,height,-length,width,height,length,
        		width,height,length,width,height,-length,-width,height,-length,
        		width,height,length,width,height,-length,0,height,-length,
        		width,height,length,0,height,-length,0,height,length,
        		0,height,length,0,height,-length,-width,height,-length,
        		0,height,length, -width,height,-length,	-width,height,length,	
        };
        
        vCount=vertices.length/3;
		
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
        float textures[]=new float[]
        {
        	
        	//上表面
        		0.18f,0.0f,0.027f,0.0080f,0.0f,0.027f,
        		0.18f,0.0f,0.0f,0.027f,0.0f,0.09f,
        		0.18f,0.0f,0.0f,0.09f,0.035f,0.109f,
        		0.18f,0.0f,0.035f,0.109f,0.145f,0.109f,
        		0.18f,0.0f,0.145f,0.109f,0.168f,0.074f,
        		0.18f,0.0f,0.168f,0.074f,0.211f,0.07f,
        		
        	//下表面
        		0.18f,0.0f,0.027f,0.0080f,0.0f,0.027f,
        		0.18f,0.0f,0.0f,0.027f,0.0f,0.09f,
        		0.18f,0.0f,0.0f,0.09f,0.035f,0.109f,
        		0.18f,0.0f,0.035f,0.109f,0.145f,0.109f,
        		0.18f,0.0f,0.145f,0.109f,0.168f,0.074f,
        		0.18f,0.0f,0.168f,0.074f,0.211f,0.07f,
        	//侧面
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,   		
        		0.168f,0.043f,0.152f,0.09f,0.223f,0.074f,0.168f,0.043f,0.152f,0.09f,0.223f,0.074f, 
        };

        
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
    	
    	gl.glRotatef(mAngleX, 1, 0, 0);
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
        
       
    }
}
