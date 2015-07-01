package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCylinder
{
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
	private FloatBuffer myNormalBuffer;//法向量缓冲
	private FloatBuffer myTexture;//纹理缓冲
	
	int textureId;
	
	int vCount;//顶点数量
	
	float length;//圆柱长度
	float circle_radius;//圆截环半径
	float degreespan;  //圆截环每一份的度数大小
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawCylinder(float length,float circle_radius,float degreespan,int textureId)
	{
		this.circle_radius=circle_radius;
		this.length=length;
		this.degreespan=degreespan;
		this.textureId=textureId;
		
		float collength=(float)length;//圆柱每块所占的长度
		int spannum=(int)(360.0f/degreespan);
		
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		ArrayList<Float> ial=new ArrayList<Float>();//法向量存放列表
		
		for(float circle_degree=180.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
		{
				float x1 =(float)(-length/2);
				float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a1=0;
				float b1=y1;
				float c1=z1;
				float l1=getVectorLength(a1, b1, c1);//模长
				a1=a1/l1;//法向量规格化
				b1=b1/l1;
				c1=c1/l1;
				
				float x2 =(float)(-length/2);
				float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a2=0;
				float b2=y2;
				float c2=z2;
				float l2=getVectorLength(a2, b2, c2);//模长
				a2=a2/l2;//法向量规格化
				b2=b2/l2;
				c2=c2/l2;
				
				float x3 =(float)(length/2);
				float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a3=0;
				float b3=y3;
				float c3=z3;
				float l3=getVectorLength(a3, b3, c3);//模长
				a3=a3/l3;//法向量规格化
				b3=b3/l3;
				c3=c3/l3;
				
				float x4 =(float)(length/2);
				float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a4=0;
				float b4=y4;
				float c4=z4;
				float l4=getVectorLength(a4, b4, c4);//模长
				a4=a4/l4;//法向量规格化
				b4=b4/l4;
				c4=c4/l4;
				
				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
				ial.add(a1);ial.add(b1);ial.add(c1);//顶点对应的法向量
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a4);ial.add(b4);ial.add(c4);
				
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a3);ial.add(b3);ial.add(c3);
				ial.add(a4);ial.add(b4);ial.add(c4);
		}
		 
		vCount=val.size()/3;//确定顶点数量
		
		//顶点
		float[] vertexs=new float[vCount*3];
		for(int i=0;i<vCount*3;i++)
		{
			vertexs[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertexBuffer=vbb.asFloatBuffer();
		myVertexBuffer.put(vertexs);
		myVertexBuffer.position(0);
		
		//法向量
		float[] normals=new float[vCount*3];
		for(int i=0;i<vCount*3;i++)
		{
			normals[i]=ial.get(i);
		}
		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
		ibb.order(ByteOrder.nativeOrder());
		myNormalBuffer=ibb.asFloatBuffer();
		myNormalBuffer.put(normals);
		myNormalBuffer.position(0);
		
		//纹理
		float[] textures=generateTexCoor(spannum);
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());
		myTexture=tbb.asFloatBuffer();
		myTexture.put(textures);
		myTexture.position(0);
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
		
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//打开法向量缓冲
		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//指定法向量缓冲
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
	
	//法向量规格化，求模长度
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bh)
    {
    	float[] result=new float[bh*6*2]; 
    	float REPEAT=2;
    	float sizeh=1.0f/bh;//行数
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float t=i*sizeh;
    			
    			result[c++]=0;
    			result[c++]=t;
    		
    			result[c++]=0;
    			result[c++]=t+sizeh; 
    			
    			result[c++]=REPEAT;
    			result[c++]=t;
    			   			
    			result[c++]=0;
    			result[c++]=t+sizeh;
    			
    			result[c++]=REPEAT;
    			result[c++]=t+sizeh;   
    			
    			result[c++]=REPEAT;
    			result[c++]=t;
    	}
    	return result;
    }
}