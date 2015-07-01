	package wyf.tzz.lta;
	import java.nio.ByteBuffer;
	import java.nio.ByteOrder;
	import java.nio.FloatBuffer;
	import javax.microedition.khronos.opengles.GL10;
	
	//上面的机翼
	public class Plane_TopWing {
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
	    float mAngleX;
	    float mAngleY;
	    int vCount = 42;
	    int texId; 
	    
	    public Plane_TopWing(int texId,float width,float height,float length)
	    {
	    	this.texId=texId;
	    	
	    	//顶点坐标数据的初始化================begin============================
	        float vertices[]=new float[]
	        {
	        	
	        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
	        	-width,height,-length,//B
	        	-width,height,length,//C
	        	
	        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
	        	-width,height,length,//C
	        	width,height,length,//D
	        	
	        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
	        	width,height,length,//D
	        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
	        	
	        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
	        	width,height,length,//D
	        	width,height,-length,//E
	        	
	        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
	        	width,height,-length,//E
	        	-width,height,-length,//B
	        	
	        	5.0f/6.0f*width,4.0f/3.0f*height,0,//O
	        	-width,height,-length,//B
	        	-5.0f/6.0f*width,4.0f/3.0f*height,0,//A
	        	
	        	-width,height,length,//C
	        	-width,0,6.0f/5.0f*length,//F
	        	width,0,6.0f/5.0f*length,//G
	        	
	        	-width,height,length,//C
	        	width,0,6.0f/5.0f*length,//G
	        	width,height,length,//D
	        	
	        	width,height,length,//D
	        	width,0,6.0f/5.0f*length,//G
	        	width,0,-6.0f/5.0f*length,//H
	        	
	        	width,height,length,//D
	        	width,0,-6.0f/5.0f*length,//H
	        	width,height,-length,//E
	        	
	        	width,height,-length,//E
	        	width,0,-6.0f/5.0f*length,//H
	        	-width,0,-6.0f/5.0f*length,//I
	        	
	        	width,height,-length,//E
	        	-width,0,-6.0f/5.0f*length,//I
	        	-width,height,-length,//B
	        	
	        	-width,height,-length,//B
	        	-width,0,-6.0f/5.0f*length,//I
	        	-width,0,6.0f/5.0f*length,//F
	        	
	        	-width,height,-length,//B
	        	-width,0,6.0f/5.0f*length,//F
	        	-width,height,length,//C
	        };
			
	        //创建顶点坐标数据缓冲
	        //vertices.length*4是因为一个整数四个字节
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
	        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mVertexBuffer.position(0);//设置缓冲区起始位置
	        
	        float textures[]=new float[]
	        {
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        	0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,0.133f,	0.211f,0.242f,0.492f,0.555f,0.289f,
	        };

	        //创建顶点纹理数据缓冲
	        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
	        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mTextureBuffer= tbb.asFloatBuffer();//转换为Float型缓冲
	        mTextureBuffer.put(textures);//向缓冲区中放入顶点着色数据
	        mTextureBuffer.position(0);//设置缓冲区起始位置
	    }

	    public void drawSelf(GL10 gl)
	    {        
	    	gl.glRotatef(mAngleY, 0, 1, 0);
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
