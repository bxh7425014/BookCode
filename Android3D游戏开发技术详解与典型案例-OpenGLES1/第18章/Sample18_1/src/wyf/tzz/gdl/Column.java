package wyf.tzz.gdl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Column
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	  
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
     
    private int vCount;
    
    int drawableId;
    
    public float mAngleX;//绕x轴旋转角度
    public float mAngleY;//绕y轴旋转角度
    public float mAngleZ;//绕z轴旋转角度
    
    float mOffsetX;//沿x轴平移距离
    float mOffsetY;//沿y轴平移距离
    float mOffsetZ;//沿z轴平移距离
    
    float height;//设置高
    float radius;//设置半径
    private float heightSpan=0.1f;//高度切分距离
    private float angleSpan=30;//角度切分大小
	
    //背景圆柱的切分行数和列数
    int col=(int) (360/angleSpan);//列
    int row;//行
    public Column(float height,float radius,int drawableId)
    {
    	this.height=height;
    	this.radius=radius;
    	this.drawableId=drawableId;
    	row=(int) (height/heightSpan);
    	initVertex();
    	initTexture();
    }
    //圆柱
    public void initVertex()
	{    	
    	ArrayList<Float> alVertex=new ArrayList<Float>();//存放顶点
    	
		for(int i=0;i<=row;i++)
		{
			float y=(i-row/2.0f)*heightSpan;//y坐标
			
			float hAngle=0;
			
			for(int j=0;j<=col;j++)
			{
				hAngle=j*angleSpan;//水平面上的角度
				
				float x=(float) (radius*Math.cos(Math.toRadians(hAngle)));
				float z=(float) (radius*Math.sin(Math.toRadians(hAngle)));
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);
			}
		}
		
		
		//创建索引
		ArrayList<Integer> alIndex=new ArrayList<Integer>();
		
		int ncol=col+1;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				int k=i*ncol+j;
					//上三角形
					alIndex.add(k);
					alIndex.add(k+ncol);
					alIndex.add(k+1);
					
					//下三角形
					alIndex.add(k+ncol);
					alIndex.add(k+ncol+1);
					alIndex.add(k+1);
			}
		}
  		vCount=alIndex.size();
  		float vertices[]=new float[alIndex.size()*3];
  		
		for(int i=0;i<vCount;i++)
		{
			int k=alIndex.get(i);
			vertices[i*3]=alVertex.get(k*3);
			vertices[i*3+1]=alVertex.get(k*3+1);
			vertices[i*3+2]=alVertex.get(k*3+2);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//一个float型4个byte
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//装换为float缓冲
		mVertexBuffer.put(vertices);//放入顶点数组
		mVertexBuffer.position(0);//设置起始位置
	}
    public void initTexture()
	{
		////row*col个矩形，一个矩形2个三角形，一个三角形3个顶点，一个顶点2个纹理坐标值
		int tCount=row*col*2*3*2;
		float[] textures=new float[tCount];
		
		float sizew=1.0f/col;
		float sizeh=1.0f/row;
		
		for(int i=0,temp=0;i<row;i++)
		{
			
			float t=i*sizeh;
			for(int j=0;j<col;j++)
			{
				float s=j*sizew;
				//左上
				textures[temp++]=s;
				textures[temp++]=t;
				
				textures[temp++]=s;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t;
				
				//左下
				textures[temp++]=s;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t;
			}
		
		}
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为FLOAT型缓冲
		mTextureBuffer.put(textures);//向缓冲区中放入顶点坐标数据
		mTextureBuffer.position(0);//缓冲区的起始位置
			
		
	}
	
    
  
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//保护当前矩阵
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);			//开启定点坐标数组
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//为画笔指定顶点坐标数据
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理数组
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定纹理
		gl.glTranslatef(mOffsetX, mOffsetY, mOffsetZ);//平移

		gl.glRotatef(mAngleX, 1, 0, 0);//绕x轴旋转
 		gl.glRotatef(mAngleY, 0, 1, 0);//绕z轴旋转
 		gl.glRotatef(mAngleZ, 0, 0, 1);//绕y轴旋转
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
	
		gl.glPopMatrix();//恢复之前保护的矩阵
	
	}
    
}