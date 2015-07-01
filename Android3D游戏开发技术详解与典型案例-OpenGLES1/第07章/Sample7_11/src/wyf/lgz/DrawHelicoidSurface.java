package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawHelicoidSurface
{
	private FloatBuffer myVertex;//顶点缓冲
	private FloatBuffer myTexture;//纹理缓冲
	private FloatBuffer myNormalBuffer;//法向量缓冲
	
	int vcount;
	int textureid;
	
	float circle_small_R;		//截面初始半径
	float Hedicoid_small_R;		//螺旋环初始半径
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	final static float MAX_ANGLE=360*3f;//螺旋角度
	final static float HEDICOID_ANGLE_SPAN=10f;//螺旋角度变化量
	final static float CIRCLE_ANGLE_SPAN=180f;//截面角度变化量
	final static float CIRCLE_R_SPAN=0f;//截面半径变化量0.01f
	final static float HEDICOID_R_SPAN=0f;//螺旋面半径变化量0.2f
	final static float LENGTH_SPAN=0.3f;//螺旋高度变化
	final static float CIECLE_ANGLE_BEGIN=0f;//截面开始绘制角度
	final static float CIECLE_ANGLE_OVER=180f;//截面结束绘制角度
	 
	public DrawHelicoidSurface
			(		float circle_small_R,	//截面半径
					float Hedicoid_small_R,	//螺旋面半径
					int textureid			//螺旋面角度变化单位，纹理
			)
	{     
		this.circle_small_R=circle_small_R;
		this.Hedicoid_small_R=Hedicoid_small_R;
		this.textureid=textureid;
		
		ArrayList<Float> val=new ArrayList<Float>();//顶点列表
		ArrayList<Float> ial=new ArrayList<Float>();//法向量存放列表
		
		for(float h_angle=0,c_r=circle_small_R,h_r=Hedicoid_small_R,length=0;h_angle<MAX_ANGLE;h_angle+=HEDICOID_ANGLE_SPAN,c_r+=CIRCLE_R_SPAN,h_r+=HEDICOID_R_SPAN,length+=LENGTH_SPAN)
		{
			for(float c_angle=CIECLE_ANGLE_BEGIN;c_angle<CIECLE_ANGLE_OVER;c_angle+=CIRCLE_ANGLE_SPAN)
			{
				float x1=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle)))*Math.cos(Math.toRadians(h_angle)));
				float y1=(float) (c_r*Math.sin(Math.toRadians(c_angle))+length);
				float z1=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle)))*Math.sin(Math.toRadians(h_angle)));
				
				float x2=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.cos(Math.toRadians(h_angle)));
				float y2=(float) (c_r*Math.sin(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN))+length);
				float z2=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.sin(Math.toRadians(h_angle)));
				
				float x3=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				float y3=(float) ((c_r+CIRCLE_R_SPAN)*Math.sin(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN))+(length+LENGTH_SPAN));
				float z3=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				
				float x4=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle)))*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				float y4=(float) ((c_r+CIRCLE_R_SPAN)*Math.sin(Math.toRadians(c_angle))+(length+LENGTH_SPAN));
				float z4=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle)))*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				
				val.add(x1);val.add(y1);val.add(z1);
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
							
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3); 
				val.add(x4);val.add(y4);val.add(z4); 
				
				//各个顶点圆截面中心的组成的圆环上的点的坐标
				
				float a1=(float) (x1-(h_r*Math.cos(Math.toRadians(h_angle))));
				float b1=y1-length;
				float c1=(float) (z1-(h_r*Math.sin(Math.toRadians(h_angle))));
				float l1=getVectorLength(a1, b1, c1);//模长
				a1=a1/l1;//法向量规格化
				b1=b1/l1;
				c1=c1/l1;
				
				float a2=(float) (x2-(h_r*Math.cos(Math.toRadians(h_angle))));
				float b2=y1-length;
				float c2=(float) (z2-(h_r*Math.sin(Math.toRadians(h_angle))));
				float l2=getVectorLength(a2, b2, c2);//模长
				a2=a2/l2;//法向量规格化
				b2=b2/l2;
				c2=c2/l2;
				
				float a3=(float) (x3-(h_r*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float b3=y1-(length+LENGTH_SPAN);
				float c3=(float) (z3-(h_r*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float l3=getVectorLength(a3, b3, c3);//模长
				a3=a3/l3;//法向量规格化
				b3=b3/l3;
				c3=c3/l3;
				
				float a4=(float) (x4-(h_r*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float b4=y1-(length+LENGTH_SPAN);
				float c4=(float) (z4-(h_r*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float l4=getVectorLength(a4, b4, c4);//模长
				a4=a4/l4;//法向量规格化
				b4=b4/l4;
				c4=c4/l4;
				
				ial.add(a1);ial.add(b1);ial.add(c1);//顶点对应的法向量
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a4);ial.add(b4);ial.add(c4);
				
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a3);ial.add(b3);ial.add(c3);
				ial.add(a4);ial.add(b4);ial.add(c4);
			}
		}
		vcount=val.size()/3;
		float[] vertexs=new float[vcount*3];
		for(int i=0;i<vcount*3;i++)
		{
			vertexs[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertex=vbb.asFloatBuffer();
		myVertex.put(vertexs);
		myVertex.position(0);
		
		//法向量
		float[] normals=new float[vcount*3];
		for(int i=0;i<vcount*3;i++)
		{
			normals[i]=ial.get(i);
		}
		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
		ibb.order(ByteOrder.nativeOrder());
		myNormalBuffer=ibb.asFloatBuffer();
		myNormalBuffer.put(normals);
		myNormalBuffer.position(0);
		
		//纹理
		
		int col=(int) (MAX_ANGLE/HEDICOID_ANGLE_SPAN);
		int row=(int) ((CIECLE_ANGLE_OVER-CIECLE_ANGLE_BEGIN)/CIRCLE_ANGLE_SPAN);
		float[] textures=generateTexCoor(row,col);
		
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
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//打开法向量缓冲
		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//指定法向量缓冲
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureid);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int hang,int lie)
    {
    	float[] result=new float[hang*lie*6*2]; 
    	float sizeh=1.0f/hang;//行大小单位
    	float sizel=1.0f/lie;//列大小单位
    	int c=0;
    	for(int i=0;i<lie;i++)
    	{
    		for(int j=0;j<hang;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float h=j*sizeh;
    			float l=i*sizel;
    			
    			result[c++]=h;//1
    			result[c++]=l;
    		
    			result[c++]=h+sizeh;//2
    			result[c++]=l;
    			
    			result[c++]=h;//4
    			result[c++]=l+sizel;
    			
    			result[c++]=h+sizeh;//2
    			result[c++]=l;
    			
    			result[c++]=h+sizeh;//3
    			result[c++]=l+sizel; 
    			
    			result[c++]=h;//4
    			result[c++]=l+sizel;    			
    		}
    	}
    	return result;
    }
    
	//法向量规格化，求模长度
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
    
}
