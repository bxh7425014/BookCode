package wyf.tzz.lta;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Airscrew 
{
	private FloatBuffer mVertexBuffer;	//顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;	//顶点纹理数据缓冲
	
	private float mAngleZ;				//沿z轴旋转角度 
	
	int vCount=6;						//顶点数量
	final int angleSpan=8;				//每片螺旋桨叶片角度
	int texId;							//纹理ID
	float scale;						//尺寸
	float zSpan=0;						//螺旋桨在z轴上的偏离
	
	public Airscrew(float scale,int texId){
		this.scale=scale;	
		this.texId = texId;	
		zSpan=scale/12;		//螺旋桨在z轴上的偏离
		initVertex();		//初始化顶点坐标数据
		initTexture();		//初始化顶点纹理数据
	}
	
	 public void initVertex()
		{   //构建立方体
			float x=(float) (this.scale*Math.cos(Math.toRadians(angleSpan)));//构建三角形顶点的x坐标的变量
			
			float y=(float) (this.scale*Math.sin(Math.toRadians(angleSpan)));//构建三角形顶点的y坐标的变量
			
			float z=zSpan;														 //构建三角形顶点的z坐标的变量
			
			//顶点坐标缓冲数组初始化
			float[] vertices=
			{				
				//构成外侧表面三角形的坐标
				0,0,0,
				x,y,0,
				x,-y,-z,
				
				//构成内侧侧表面三角形的坐标
				0,0,0,
				x,-y,-z,
				x,y,0,								
			};
		   
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	 //创建顶点坐标数据缓冲，因一个Float四个字节
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
		}
	
	public void initTexture()
	{
		float[] textures=generateTextures();	//生成纹理坐标数组
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());		//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();		//转换为float型缓冲
		mTextureBuffer.put(textures);			//向缓冲区中放入顶点坐标数据
		mTextureBuffer.position(0);				//设置缓冲区起始位置
	}
	
	public float[] generateTextures(){
		float[] textures=new float[]{			//生成纹理坐标数组
				0,0,1,0,0,1,
				0,0,0,1,1,0,
	       };
		return textures;
	}
	

	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();										//保护当前矩阵
		gl.glRotatef(mAngleZ, 0, 0, 1);							//绕z轴旋转
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);			//开启定点坐标数组
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer); //为画笔指定顶点坐标数据
		
		gl.glEnable(GL10.GL_TEXTURE_2D);						//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);	//开启纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理数组
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);			//绑定纹理
		
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制
		gl.glRotatef(60, 0, 0, 1);								//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制
		gl.glRotatef(60, 0, 0, 1);								//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制
		gl.glRotatef(60, 0, 0, 1);								//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制
		gl.glRotatef(60, 0, 0, 1);								//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制
		gl.glRotatef(60, 0, 0, 1);								//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);			//绘制	
		gl.glPopMatrix();										//恢复之前保护的矩阵
		mAngleZ=mAngleZ+27;										//让z轴旋转角度自加，保持螺旋桨的旋转
	}
}