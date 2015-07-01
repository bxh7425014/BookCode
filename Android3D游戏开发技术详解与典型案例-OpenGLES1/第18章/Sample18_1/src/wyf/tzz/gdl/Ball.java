package wyf.tzz.gdl;

import java.nio.ByteBuffer;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball 
{
	private FloatBuffer mVertexBuffer;//顶点
	private FloatBuffer mTextureBuffer;//纹理
	int vCount;//定点个数
	int drawableId;//纹理id
	float radius;//半径
	public Ball(float radius,int drawableId)
	{
		this.radius=radius;
		this.drawableId	= drawableId;
		final int angleSpan=50;
		ArrayList<Float> alVertex=new ArrayList<Float>();//存放顶点
		
		for(int vAngle=90;vAngle>=-90;vAngle-=angleSpan)
		{
			float y=(float) (radius*Math.sin(Math.toRadians(vAngle)));//球的y坐标
			double temp=radius*Math.cos(Math.toRadians(vAngle));
			for(int hAngle=360;hAngle>=0;hAngle-=angleSpan)
			{
				float x=(float) (temp*Math.cos(Math.toRadians(hAngle)));//球的x坐标
				float z=(float) (temp*Math.sin(Math.toRadians(hAngle)));//球的z坐标
				alVertex.add(x);//将定点坐标加入alVertex
				alVertex.add(y);//将定点坐标加入alVertex
				alVertex.add(z);//将定点坐标加入alVertex
			}
		}
		ArrayList<Integer> alIndex=new ArrayList<Integer>();
		
		int row=180/angleSpan;
		int col=360/angleSpan;
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
					
					//上三角形
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
		
		//创建顶点缓冲
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//一个float四个byte
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer(); //转换为float缓冲
		mVertexBuffer.put(vertices);//加入定点坐标数组
		mVertexBuffer.position(0);//设置起始位置
		
	
	
		float[] textures=generateTextures(row,col);
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//一个float四个byte
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mTextureBuffer=tbb.asFloatBuffer();//转换为float缓冲
		mTextureBuffer.put(textures);//加入定点坐标数组
		mTextureBuffer.position(0);//设置起始位置
	}
	
	
	public float[] generateTextures(int row,int col)
	{//自动生成纹理的方法
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
			
		return textures;
	}
	
	public void drawSelf(GL10 gl)
	{
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//开启定点数组		
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,mVertexBuffer);//为画笔指定定点坐标数据
		gl.glEnable(GL10.GL_TEXTURE_2D);//启用纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//开启纹理数组
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理坐标数据
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//绑定纹理
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);//绘制图形
		
	}
}