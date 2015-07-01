package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawParaboloid
{
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
//	private FloatBuffer myNormalBuffer;//法向量缓冲
	private FloatBuffer myTexture;//纹理缓冲
	
	int textureId;
	int vCount;//顶点数量
	
	float height;//抛物体高度
	float a;//截面椭圆轴参数
	float b;
	float col;//抛物体高度切割份数
	float angleSpan;//截面切分角度
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawParaboloid(float height,float a,float b,int col,float angleSpan,int textureId)
	{
		this.height=height;
		this.a=a;
		this.b=b;
		this.col=col;
		this.angleSpan=angleSpan;
		this.textureId=textureId;
		
		float heightSpan=height/col;//抛物体每块所占的高度
		int spannum=(int)(360.0f/angleSpan);//截面角度切分份数
		
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		//ArrayList<Float> ial=new ArrayList<Float>();//法向量存放列表
		
		for(float h=height;h>0;h-=heightSpan)//循环行
		{
			for(float angle=360;angle>0;angle-=angleSpan)//循环列
			{
				float x1=(float) (a*Math.sqrt(2*h)*Math.cos(Math.toRadians(angle)));
				float y1=h;
				float z1=(float) (b*Math.sqrt(2*h)*Math.sin(Math.toRadians(angle)));
				
				float x2=(float) (a*Math.sqrt(2*(h-heightSpan))*Math.cos(Math.toRadians(angle)));
				float y2=h-heightSpan;
				float z2=(float) (b*Math.sqrt(2*(h-heightSpan))*Math.sin(Math.toRadians(angle)));
				
				float x3=(float) (a*Math.sqrt(2*(h-heightSpan))*Math.cos(Math.toRadians(angle-angleSpan)));
				float y3=h-heightSpan;
				float z3=(float) (b*Math.sqrt(2*(h-heightSpan))*Math.sin(Math.toRadians(angle-angleSpan)));
				
				float x4=(float) (a*Math.sqrt(2*h)*Math.cos(Math.toRadians(angle-angleSpan)));
				float y4=h;
				float z4=(float) (b*Math.sqrt(2*h)*Math.sin(Math.toRadians(angle-angleSpan)));
				
				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
//				float a1=0;
//				float b1=y1;
//				float c1=z1;
//				float l1=getVectorLength(a1, b1, c1);//模长
//				a1=a1/l1;//法向量规格化
//				b1=b1/l1;
//				c1=c1/l1;
//				
//				float a2=0;
//				float b2=y2;
//				float c2=z2;
//				float l2=getVectorLength(a2, b2, c2);//模长
//				a2=a2/l2;//法向量规格化
//				b2=b2/l2;
//				c2=c2/l2;
//				
//				float a3=0;
//				float b3=y3;
//				float c3=z3;
//				float l3=getVectorLength(a3, b3, c3);//模长
//				a3=a3/l3;//法向量规格化
//				b3=b3/l3;
//				c3=c3/l3;
//				
//				float a4=0;
//				float b4=y4;
//				float c4=z4;
//				float l4=getVectorLength(a4, b4, c4);//模长
//				a4=a4/l4;//法向量规格化
//				b4=b4/l4;
//				c4=c4/l4;
//				
//				ial.add(a1);ial.add(b1);ial.add(c1);//顶点对应的法向量
//				ial.add(a2);ial.add(b2);ial.add(c2);
//				ial.add(a4);ial.add(b4);ial.add(c4);
//				
//				ial.add(a2);ial.add(b2);ial.add(c2);
//				ial.add(a3);ial.add(b3);ial.add(c3);
//				ial.add(a4);ial.add(b4);ial.add(c4);
			}
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
		
//		//法向量
//		float[] normals=new float[vCount*3];
//		for(int i=0;i<vCount*3;i++)
//		{
//			normals[i]=ial.get(i);
//		}
//		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
//		ibb.order(ByteOrder.nativeOrder());
//		myNormalBuffer=ibb.asFloatBuffer();
//		myNormalBuffer.put(normals);
//		myNormalBuffer.position(0);
//		
		//纹理
		float[] textures=generateTexCoor(col,spannum);
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
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);
		
//		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//打开法向量缓冲
//		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//指定法向量缓冲
//		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
		gl.glEnable(GL10.GL_TEXTURE_2D);
//		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	//法向量规格化，求模长度
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int hang,int lie)
    {
    	float[] result=new float[hang*lie*6*2]; 
    	float sizeh=1.0f/hang;//行大小单位
    	float sizel=1.0f/lie;//列大小单位
    	int c=0;
    	for(int i=0;i<hang;i++)
    	{
    		for(int j=0;j<lie;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float h=i*sizeh;
    			float l=j*sizel;
    			 
    			result[c++]=l;//1
    			result[c++]=h; 
    		
    			result[c++]=l;//2
    			result[c++]=h+sizeh; 
    			
    			result[c++]=l+sizel;//4
    			result[c++]=h;
    			
    			result[c++]=l;//2
    			result[c++]=h+sizeh;
    			
    			result[c++]=l+sizel;//3
    			result[c++]=h+sizeh; 
    			
    			result[c++]=l+sizel;//4
    			result[c++]=h;    			
    		}
    	}
    	return result;
    }
}