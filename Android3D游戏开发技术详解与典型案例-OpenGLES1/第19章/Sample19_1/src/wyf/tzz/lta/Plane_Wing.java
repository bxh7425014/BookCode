package wyf.tzz.lta;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//纹理矩形
public class Plane_Wing {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
    int vCount;
    int texId;
    float mAngleX ;
    
    public Plane_Wing(int texId,float width,float height,float length)
    {
    	this.texId=texId;
    	
    	//顶点坐标数据的初始化================begin============================
        vCount=144;
        float vertices[]=new float[]
        {
        		
        		//上表面
        		0,height,length,-width,height,length,-17.0f/15.0f*width,3.0f/5.0f*height,length,//OAB        		
        		
        		0,height,length,-17.0f/15.0f*width,3.0f/5.0f*height,length,-17.0f/15.0f*width,-3.0f/5.0f*height,length,//OBC
        		
        		0,height,length,-17.0f/15.0f*width,-3.0f/5.0f*height,length,-width,-height,length,//OCD
        		
        		0,height,length,-width,-height,length,-1.0f/3.0f*width,-height,length,//ODE

        		0,height,length,-1.0f/3.0f*width,-height,length,-2.0f/15.0f*width,-2.0f/5.0f*height,length,//OEF
        		
        		0,height,length,-2.0f/15.0f*width,-2.0f/5.0f*height,length,2.0f/15.0f*width,-2.0f/5.0f*height,length,//OFG
        		
        		0,height,length,2.0f/15.0f*width,-2.0f/5.0f*height,length,1.0f/3.0f*width,-height,length,//OGH
        		
        		0,height,length,1.0f/3.0f*width,-height,length,width,-height,length,//OHI        		
        		
        		0,height,length,width,-height,length,17.0f/15.0f*width,-3.0f/5.0f*height,length,//OIJ
        		
        		0,height,length,17.0f/15.0f*width,-3.0f/5.0f*height,length,17.0f/15.0f*width,3.0f/5.0f*height,length,//OJK
        		
        		0,height,length,17.0f/15.0f*width,3.0f/5.0f*height,length,width,height,length,//OKL
        		
        		//下表面
        		0,height,-length,-width,height,-length,-17.0f/15.0f*width,3.0f/5.0f*height,-length,//OAB        		
        		
        		0,height,-length,-17.0f/15.0f*width,3.0f/5.0f*height,-length,-17.0f/15.0f*width,-3.0f/5.0f*height,-length,//OBC
        		
        		0,height,-length,-17.0f/15.0f*width,-3.0f/5.0f*height,-length,-width,-height,-length,//OCD
        		
        		0,height,-length,-width,-height,-length,-1.0f/3.0f*width,-height,-length,//ODE

        		0,height,-length,-1.0f/3.0f*width,-height,-length,-2.0f/15.0f*width,-2.0f/5.0f*height,-length,//OEF
        		
        		0,height,-length,-2.0f/15.0f*width,-2.0f/5.0f*height,-length,2.0f/15.0f*width,-2.0f/5.0f*height,-length,//OFG
        		
        		0,height,-length,2.0f/15.0f*width,-2.0f/5.0f*height,-length,1.0f/3.0f*width,-height,-length,//OGH
        		
        		0,height,-length,1.0f/3.0f*width,-height,-length,width,-height,-length,//OHI        		
        		
        		0,height,-length,width,-height,-length,17.0f/15.0f*width,-3.0f/5.0f*height,-length,//OIJ
        		
        		0,height,-length,17.0f/15.0f*width,-3.0f/5.0f*height,-length,17.0f/15.0f*width,3.0f/5.0f*height,-length,//OJK
        		
        		0,height,-length,17.0f/15.0f*width,3.0f/5.0f*height,-length,width,height,-length,//OKL
        		
        		//侧面
        		-width,height,length,-width,height,-length,-17.0f/15.0f*width,3.0f/5.0f*height,-length,//A//A1//B1        		
        		
        		-width,height,length,-17.0f/15.0f*width,3.0f/5.0f*height,-length,-17.0f/15.0f*width,3.0f/5.0f*height,length,//A//B1//B        		
        		
        		-17.0f/15.0f*width,3.0f/5.0f*height,length,-17.0f/15.0f*width,3.0f/5.0f*height,-length,-17.0f/15.0f*width,-3.0f/5.0f*height,-length,//B//B1//C1
        		
        		-17.0f/15.0f*width,3.0f/5.0f*height,length,-17.0f/15.0f*width,-3.0f/5.0f*height,-length,-17.0f/15.0f*width,-3.0f/5.0f*height,length,//B//C1//C
        		
        		-17.0f/15.0f*width,-3.0f/5.0f*height,length,-17.0f/15.0f*width,-3.0f/5.0f*height,-length,-width,-height,-length,//CC1D1
        		
        		-17.0f/15.0f*width,-3.0f/5.0f*height,length,-width,-height,-length,-width,-height,length,//C//D1//D
        		
        		-width,-height,length,-width,-height,-length,-1.0f/3.0f*width,-height,-length,//D//D1//E1        		
        		
        		-width,-height,length,-1.0f/3.0f*width,-height,-length,-1.0f/3.0f*width,-height,length,//D//E1//E\\		
        		
        		-1.0f/3.0f*width,-height,length,-1.0f/3.0f*width,-height,-length,-2.0f/15.0f*width,-2.0f/5.0f*height,-length,//E\\//E1//F1
        		
        		-1.0f/3.0f*width,-height,length,-2.0f/15.0f*width,-2.0f/5.0f*height,-length,-2.0f/15.0f*width,-2.0f/5.0f*height,length,//E\\//F1//F
        		
        		-2.0f/15.0f*width,-2.0f/5.0f*height,length,-2.0f/15.0f*width,-2.0f/5.0f*height,-length,2.0f/15.0f*width,-2.0f/5.0f*height,-length,//F//F1//G1
        		
        		-2.0f/15.0f*width,-2.0f/5.0f*height,length,2.0f/15.0f*width,-2.0f/5.0f*height,-length,2.0f/15.0f*width,-2.0f/5.0f*height,length,//F//G1//G
        		
        		2.0f/15.0f*width,-2.0f/5.0f*height,length,2.0f/15.0f*width,-2.0f/5.0f*height,-length,1.0f/3.0f*width,-height,-length,//G//G1//H1
        		
        		2.0f/15.0f*width,-2.0f/5.0f*height,length,1.0f/3.0f*width,-height,-length,1.0f/3.0f*width,-height,length,//G//H1//H

        		1.0f/3.0f*width,-height,length,1.0f/3.0f*width,-height,-length,width,-height,-length,//H//H1//I1        		
        		
        		1.0f/3.0f*width,-height,length,width,-height,-length,width,-height,length,//H//I1//I
        	
        		width,-height,length,width,-height,-length,17.0f/15.0f*width,-3.0f/5.0f*height,-length,//I//I1//J1	
        		
        		width,-height,length,17.0f/15.0f*width,-3.0f/5.0f*height,-length,17.0f/15.0f*width,-3.0f/5.0f*height,length,//I//J1//J

        		17.0f/15.0f*width,-3.0f/5.0f*height,length,17.0f/15.0f*width,-3.0f/5.0f*height,-length,17.0f/15.0f*width,3.0f/5.0f*height,-length,//J//J1//K1
	
        		17.0f/15.0f*width,-3.0f/5.0f*height,length,17.0f/15.0f*width,3.0f/5.0f*height,-length,17.0f/15.0f*width,3.0f/5.0f*height,length,//J//K1//K

        		17.0f/15.0f*width,3.0f/5.0f*height,length,17.0f/15.0f*width,3.0f/5.0f*height,-length,width,height,-length,//K//K1//L1
  		
        		17.0f/15.0f*width,3.0f/5.0f*height,length,width,height,-length,width,height,length,//K//L1//L
  
        		width,height,length,width,height,-length,0,height,-length,//L//L1//O1
      		
        		width,height,length,0,height,-length,0,height,length,//L//o1//O 
        		
        		0,height,length,0,height,-length,0,height,-length,//oo1a1
        		
        		0,height,length,0,height,-length,0,height,length,//oo1a
        		
        };
		
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
        		0.488f,0.0040f,0.051f,0.0080f,0.0f,0.051f,
        		0.488f,0.0040f,0.0f,0.051f,0.0f,0.203f,
        		0.488f,0.0040f,0.0f,0.203f,0.055f,0.23f,
        		0.488f,0.0040f,0.055f,0.23f,0.344f,0.223f,
        		0.488f,0.0040f,0.344f,0.223f,0.391f,0.168f,
        		0.488f,0.0040f,0.391f,0.168f,0.582f,0.164f,
        		0.488f,0.0040f,0.582f,0.164f,0.664f,0.211f,
        		0.488f,0.0040f,0.664f,0.211f,0.938f,0.203f,
        		0.488f,0.0040f,0.938f,0.203f,0.98f,0.164f,
        		0.488f,0.0040f,0.98f,0.164f,0.984f,0.059f,
        		0.488f,0.0040f,0.984f,0.059f,0.914f,0.0040f,
        		
        	//下表面
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		
        	//侧面
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,
        		0.0040f,0.277f,0.0040f,0.391f,0.137f,0.352f,  
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
