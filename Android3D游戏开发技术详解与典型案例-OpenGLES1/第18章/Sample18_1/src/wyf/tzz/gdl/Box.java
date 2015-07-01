package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//引入静态类里的成员

//引入用到的类
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Box
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	  
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
	    
    private int vCount=36;//总的三角形的顶点数量
    
    int drawableId;//纹理图片的ID
    
    public float mAngleX;//绕x轴旋转的角度
    public float mAngleY;//绕y轴旋转的角度
    public float mAngleZ;//绕z轴旋转的角度
    
    float mOffsetX;
    float mOffsetY;
    float mOffsetZ;
	
    
    public Box(int drawableId)
    {
    	this.drawableId=drawableId;
    	
    	initVertex(); //调用初始化顶点的方法
    	
    	initTexture();//调用初始化纹理的方法
    }
    

    public void initVertex()
	{   //构建立方体
		float x=BOX_SIZE*UNIT_SIZE/2;//构建三角形顶点的x坐标的变量
		
		float y=x;					//构建三角形顶点的y坐标的变量
		
		float z=x;					//构建三角形顶点的z坐标的变量
		
		//顶点坐标缓冲数组初始化
		float[] vertices=
		{				
			//构成上表面两个三角形的坐标
			-x,y,-z,
			-x,y,z,
			x,y,-z,
			
			-x,y,z,
			x,y,z,
			x,y,-z,
			
			//构成下表面两个三角形的坐标
			-x,-y,z,
			-x,-y,-z,
			x,-y,z,
			
			-x,-y,-z,
			x,-y,-z,
			x,-y,z,
			
			//构成左表面两个三角形的坐标
			-x,y,-z,
			-x,-y,-z,
			-x,y,z,
			
			-x,-y,-z,
			-x,-y,z,
			-x,y,z,
			
			//构成右表面两个三角形的坐标
			x,y,z,
			x,-y,z,
			x,y,-z,
			
			x,-y,z,
			x,-y,-z,
			x,y,-z,
			
			//构成前表面两个三角形的坐标
			-x,y,z,
			-x,-y,z,
			x,y,z,
			
			-x,-y,z,
			x,-y,z,
			x,y,z,
			
			//构成后表面两个三角形的坐标
			x,y,-z,
			x,-y,-z,
			-x,y,-z,
			
			x,-y,-z,
			-x,-y,-z,
			-x,y,-z,
			
		};
	    //创建顶点坐标数据缓冲
		//vertices.length*4是因为一个Float四个字节
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
	}
    
    //纹理 坐标数据初始化
    public void initTexture()
	{
    	//创建各个面对应的纹理坐标，总共有36个顶点，也就是36对纹理坐标
    	float textures[] = new float[]
            {
			0.02f,0.2f,		0.02f,0.4f,		0.05f,0.4f,		0.02f,0.2f,		0.05f,0.4f,		0.05f,0.2f,
			
			0.02f,0.2f,		0.02f,0.4f,		0.05f,0.4f,		0.02f,0.2f,		0.05f,0.4f ,	0.05f,0.2f,
			
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0
			
			};
    	//创建顶点坐标数据缓冲
		//textures.length*4是因为一个Float四个字节
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mTextureBuffer.put(textures);//向缓冲区中放入顶点坐标数据
		mTextureBuffer.position(0);//设置缓冲区起始位置
	}
	
    
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//保护现场
				
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点数组
		//为画笔指定顶点坐标数据
		gl.glVertexPointer
		(
				3, //每个顶点的坐标数量为3  xyz 
				GL10.GL_FLOAT, //顶点坐标值的类型为 GL_FLOAT型
				0, //连续顶点之间的间隔
				mVertexBuffer//顶点坐标数据
		);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理坐标缓冲
		
		//为画笔指定纹理ST坐标缓冲
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,mTextureBuffer);
		//绑定当前纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);
		//把坐标系移动到mOffsetX, mOffsetY, mOffsetZ位置 
		gl.glTranslatef(mOffsetX, mOffsetY, mOffsetZ);
		
		gl.glRotatef(mAngleX, 1, 0, 0);//绕 x轴旋转mAngleX度
 		gl.glRotatef(mAngleY, 0, 1, 0);//绕y轴旋转mAngleY度
 		gl.glRotatef(mAngleZ, 0, 0, 1);//绕z轴旋转mAngleZ度
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图形
		
		gl.glPopMatrix();//恢复现场
	
	}
    
}