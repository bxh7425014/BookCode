package wyf.tzz.lta;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCylinderSky
{
	private FloatBuffer myVertexBuffer;	//顶点坐标缓冲 
	private FloatBuffer myTexture;		//纹理缓冲
	
	int textureId;						//纹理ID
	int vCount;							//顶点数量
	float length;						//圆柱长度
	float circle_radius;				//圆截环半径
	float degreespan;  					//圆截环每一份的度数大小
	int col;							//圆柱块数 
	
	public float mAngleX;

	public DrawCylinderSky(float length,float circle_radius,float degreespan,int col,int textureId)
	{
		this.circle_radius=circle_radius;
		this.length=length;
		this.col=col;
		this.degreespan=degreespan;
		this.textureId=textureId;
		
		float collength=(float)length/col;			//圆柱每块所占的长度
		int spannum=(int)(360.0f/degreespan);
		
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		
		for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
		{
			for(int j=0;j<col;j++)//循环列
			{
				float x1 =(float)(j*collength-length/2);
				float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float x2 =(float)(j*collength-length/2);
				float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float x3 =(float)((j+1)*collength-length/2);
				float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float x4 =(float)((j+1)*collength-length/2);
				float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x2);val.add(y2);val.add(z2);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x3);val.add(y3);val.add(z3);
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
		gl.glPushMatrix();
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
		gl.glPopMatrix();
	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
      	float sizew=1.0f/bw;	//列数
    	float sizeh=1.0f*4/bh;	//行数
    	int c=0;
    	int k=0;
    	
    	for(int i=0;i<bh/4;i++)	//行
    	{
    		for(int j=0;j<bw;j++)//列
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float s=j*sizew;//列
    			float t=i*sizeh;//行
    			
    			result[c++]=s;//1
    			result[c++]=t;
    		
    			result[c++]=s+sizew;//4
    			result[c++]=t;
    			
    			result[c++]=s;//2
    			result[c++]=t+sizeh;
    			   			
    			result[c++]=s;//2
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;//4
    			result[c++]=t;
    			 
    			result[c++]=s+sizew;//3
    			result[c++]=t+sizeh;   
    		}
    	}
    	
    	int totalPoint=bw*bh*6*2;
    	int readyPoint=bw*bh*6*2/4;
    	for(int i=readyPoint;i<totalPoint;i++)
    	{
    		result[c++]=result[k%readyPoint];
    		k++;
    	}
    	return result;
    }
}