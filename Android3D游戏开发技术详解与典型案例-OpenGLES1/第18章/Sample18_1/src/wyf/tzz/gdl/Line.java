package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//引入常量类里的对象
//引入需要的类
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Line
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	  
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
     
    private int vCount;//整形变量记录顶点数量
    
    int drawableId;//图片ID
    
    public float mAngleX;//绕X轴旋转的角度
    public float mAngleY;//绕Y轴旋转的角度
    public float mAngleZ;//绕Z轴旋转的角度
    
    float mOffsetX;//X方向的偏移量
    float mOffsetY;//Y方向的偏移量
    float mOffsetZ;//Z方向的偏移量
    
    private float radius;//圆柱横截面的圆柱
    private float heightSpan=0.1f;//切分的高度
    private float angleSpan=30;//切分的角度
	
    //背景圆柱的切分行数和列数    
    int col=(int) (360/angleSpan);    
    int row;
    
    public Line(float height,float radius,int drawableId)
    {
    	this.radius=radius;
    	this.drawableId=drawableId;
    	
    	row=(int) (height/heightSpan);//绳子切分的行数    	
    	
    	initVertex();//调用初始化顶点方法
   
    	initTexture();//调用初始化纹理方法
    }    
    
    public void initVertex()
	{   //初始化顶点 	
    	ArrayList<Float> alVertex=new ArrayList<Float>();	
    	
		for(int i=-row;i<=0;i++)
		{
			float y=i*heightSpan*UNIT_SIZE;//y坐标
			
			float hAngle=0;
			
			for(int j=0;j<=col;j++)
			{
				hAngle=j*angleSpan;//水平面上的角度				
				float x=(float) (radius*UNIT_SIZE*Math.cos(Math.toRadians(hAngle)));//第I行J列的X坐标
				float z=(float) (radius*UNIT_SIZE*Math.sin(Math.toRadians(hAngle)));//第I行J列的Y坐标
				//将计算好的X,Y,Z坐标添加到ArrayList中
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);
			}
		}
		
		
		//创建索引数组
		ArrayList<Integer> alIndex=new ArrayList<Integer>();
		
		int ncol=col+1;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				int k=i*ncol+j;
					//构成行列交叉点上面三角形的索引
					alIndex.add(k);
					alIndex.add(k+ncol);
					alIndex.add(k+1);
					
					//构成行列交叉点下面三角形的索引
					alIndex.add(k+ncol);
					alIndex.add(k+ncol+1);
					alIndex.add(k+1);
			}
		}		
  		vCount=alIndex.size();//顶点数量  		
  	
  		float vertices[]=new float[alIndex.size()*3];//创建一大小为alIndex.size()*3的浮点型数组存放各顶点坐标
  		
		for(int i=0;i<vCount;i++)
		{//将ArrayList中的值全赋值到数组中
			int k=alIndex.get(i);
			vertices[i*3]=alVertex.get(k*3);
			vertices[i*3+1]=alVertex.get(k*3+1);
			vertices[i*3+2]=alVertex.get(k*3+2);
		}
		//创建顶点坐标数据缓冲
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为FLOAT型缓冲
		mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//缓冲区的起始位置		
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
		gl.glPushMatrix();//保护现场
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//允许使用顶点数组
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//为画笔指定顶点坐标数据
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定图片坐标数据 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定图片

		gl.glTranslatef(mOffsetX, mOffsetY, mOffsetZ);//将坐标系移动到(mOffsetX, mOffsetY, mOffsetZ)处
	
		gl.glRotatef(mAngleX, 1, 0, 0);//绕X轴旋转mAngleX
 		gl.glRotatef(mAngleY, 0, 1, 0);//绕Y轴旋转mAngleY
 		gl.glRotatef(mAngleZ, 0, 0, 1);//绕Z轴旋转mAngleZ
 		
 
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图形
	
		gl.glPopMatrix();//恢复现场
	
	}
    
}