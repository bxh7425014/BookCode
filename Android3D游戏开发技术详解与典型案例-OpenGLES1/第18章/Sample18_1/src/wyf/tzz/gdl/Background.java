package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//引入静态类里的成员
//引入用到的类
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Background
{	
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
		  
	    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
	
	    private int vCount;//整形变量用来记录顶点数量
	    
	    int drawableId;//图片的ID 
	    
	    public float mAngleX;//绕X轴旋转的角度
	    public float mAngleY;//绕Y轴旋转的角度
	    public float mAngleZ;//绕Z轴旋转的角度		
	    
	    public Background(int drawableId)
	    {
	    	this.drawableId=drawableId;
	    	
	    	//初始化顶点坐标缓冲
	    	initVertex();
	
	    	//初始化纹理坐标缓冲
	    	initTexture();
	    }
	    
	   
	    public void initVertex()
		{
			float x=BACKGROUND_LENGTH*UNIT_SIZE/2;//记录顶点X坐标值的变量
			
			float y=BACKGROUND_HEIGHT*UNIT_SIZE/2;//记录顶点Y坐标值的变量
			
			float z=0;//记录顶点Z坐标值的变量

			float[] vertices=
			{//背景的顶点坐标初始化，背景由两个三角形组成
				-x,y,z,
				-x,-y,z,
				x,y,z,
				
				-x,-y,z,
				x,-y,z,
				x,y,z,
		
			};
			
			vCount=vertices.length/3;//顶点数量
			//创建顶点坐标数据缓冲
			//vertices.length*4是因为一个Float四个字节
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置			
			
		}
	   
	
	    public void initTexture()
		{//图片坐标初始化
			float[] textures=new float[vCount*2];//图片坐标数量为顶点数量的2倍
			
			int temp=0;
			
			//左上角三角形的纹理坐标
			textures[temp++]=0;
			textures[temp++]=0;
			
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
			
			
			//右下角三角形的纹理坐标
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
		
			//创建顶点坐标数据缓冲
			//vertices.length*4是因为一个Float四个字节
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);	
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textures);//向缓冲区中放入顶点坐标数据
			mTextureBuffer.position(0);//设置缓冲区起始位置	
		}
		
	    
		public void drawSelf(GL10 gl)
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点数组
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//为画笔指定顶点坐标数据
	
			gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//启用纹理数据
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定图片纹理数据
			gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定图片
		
			gl.glRotatef(mAngleX, 1, 0, 0);//绕X轴旋转mAngleX度
	 		gl.glRotatef(mAngleY, 0, 1, 0);//绕Y轴旋转mAngleY度
	 		gl.glRotatef(mAngleZ, 0, 0, 1);//绕Z轴旋转mAngleZ度
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制背景
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	    
	}