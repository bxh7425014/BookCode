package wyf.tzz.gdl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static wyf.tzz.gdl.Constant.*;

public class Base
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
    private int vCount=36;//定点坐标数
    int drawableId;//纹理ID
    public float mAngleX;//绕x轴旋转角度
    public float mAngleY;//绕y轴旋转角度
    public float mAngleZ;//绕z轴旋转角度
    public Base(int drawableId)
    {
    	this.drawableId=drawableId;
    	//初始化顶点
    	initVertex();
    	//初始化纹理
    	initTexture();
    }
    //棱台
    public void initVertex()
	{    	
		float xup=BASE_LENGTH*UNIT_SIZE/2;//上表面最大x值
		float xdw=BASE_LENGTH*UNIT_SIZE*DOWN_UP_RATIO/2;//下表面最大x值,DOWN_UP_RATIO为下表面与上表面的宽度比
		float y=BASE_HEIGHT*UNIT_SIZE/2;//最大y值
		float zup=BASE_WIDTH*UNIT_SIZE/2;//上表面最大z值
		float zdw=BASE_WIDTH*UNIT_SIZE*DOWN_UP_RATIO/2;//下表面最大z值，DOWN_UP_RATIO为下表面与上表面的宽度比
		float[] vertices=
		{
			//上
			-xup,y,-zup,
			-xup,y,zup,
			xup,y,-zup,

			-xup,y,zup,
			xup,y,zup,
			xup,y,-zup,
			
			//下
			-xdw,-y,zdw,
			-xdw,-y,-zdw,
			xdw,-y,zdw,
			
			-xdw,-y,-zdw,
			xdw,-y,-zdw,
			xdw,-y,zdw,
			
			//左
			-xup,y,-zup,
			-xdw,-y,-zdw,
			-xup,y,zup,
			
			-xdw,-y,-zdw,
			-xdw,-y,zdw,
			-xup,y,zup,
			
			//右
			xup,y,zup,
			xdw,-y,zdw,
			xup,y,-zup,
			
			xdw,-y,zdw,
			xdw,-y,-zdw,
			xup,y,-zup,
			
			//前
			-xup,y,zup,
			-xdw,-y,zdw,
			xup,y,zup,
			
			-xdw,-y,zdw,
			xdw,-y,zdw,
			xup,y,zup,
			
			//后
			xup,y,-zup,
			xdw,-y,-zdw,
			-xup,y,-zup,
			
			xdw,-y,-zdw,
			-xdw,-y,-zdw,
			-xup,y,-zup
			
		};
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//一个float型是4个比特
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(vertices);//向缓冲中放入定点坐标数组
		mVertexBuffer.position(0);//设置缓冲区起始位置
	}
    public void initTexture()
	{
		float[] textures=new float[vCount*2];
		for(int i=0,temp=0;i<6;i++)
		{
			//左上
			textures[temp++]=0;
			textures[temp++]=0;
			
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
			
			
			//右下
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//一个float型是4个比特
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float缓冲
		mTextureBuffer.put(textures);//放入纹理坐标数组
		mTextureBuffer.position(0);//设置缓冲区起始位置
	}
    
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用定点数组
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		gl.glEnable(GL10.GL_TEXTURE_2D);//启用纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//启用纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理定点坐标数据
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定纹理

		gl.glRotatef(mAngleX, 1, 0, 0);//沿x轴旋转
 		gl.glRotatef(mAngleY, 0, 1, 0);//沿y轴旋转
 		gl.glRotatef(mAngleZ, 0, 0, 1);//沿z轴旋转
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//定点法绘制
	}
}