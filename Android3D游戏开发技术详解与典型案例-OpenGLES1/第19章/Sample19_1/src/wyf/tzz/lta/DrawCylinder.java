package wyf.tzz.lta;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCylinder
{
	private FloatBuffer myVertexBuffer;	//顶点坐标缓冲 
	private FloatBuffer myTexture;		//纹理缓冲
	
	int textureId;
	
	int vCount;							//顶点数量
	
	float length;			//圆柱长度
	float circle_radius;	//圆截环半径
	int row;				//截面切分块数 
	int col;				//圆柱块数 
	float[][] yArray;		//导入灰度所表示的高度数组
	
	public float mAngleX;
	
	public DrawCylinder(float length,float circle_radius,int row,int col,float[][] yArray,int textureId)//圆柱是横向放置，中心点是原点。
	{
		this.circle_radius=circle_radius;
		this.length=length;
		this.col=col;
		this.row=row;
		this.yArray=yArray;
		this.textureId=textureId;
		
		float collength=(float)length/col;			//圆柱每块所占的长度
		float rowSpan=(float)(360f/row);			//截面角度变化单位
		 
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		
		for(float circle_degree=360.0f,i=0;circle_degree>0.0f;circle_degree-=rowSpan,i++)//循环行
		{
			for(int j=0;j<col;j++)//循环列
			{
				float x1 =(float)(j*collength-length/2);
				float y1=(float) ((circle_radius+yArray[(int)(i)][j])*Math.sin(Math.toRadians(circle_degree)));
				float z1=(float) ((circle_radius+yArray[(int)(i)][j])*Math.cos(Math.toRadians(circle_degree)));
				
				float x2 =(float)(j*collength-length/2);
				float y2=(float) ((circle_radius+yArray[(int)(i+1)][j])*Math.sin(Math.toRadians(circle_degree-rowSpan)));
				float z2=(float) ((circle_radius+yArray[(int)(i+1)][j])*Math.cos(Math.toRadians(circle_degree-rowSpan)));
				
				float x3 =(float)((j+1)*collength-length/2);
				float y3=(float) ((circle_radius+yArray[(int)(i+1)][j+1])*Math.sin(Math.toRadians(circle_degree-rowSpan)));
				float z3=(float) ((circle_radius+yArray[(int)(i+1)][j+1])*Math.cos(Math.toRadians(circle_degree-rowSpan)));
				
				float x4 =(float)((j+1)*collength-length/2);
				float y4=(float) ((circle_radius+yArray[(int)(i)][j+1])*Math.sin(Math.toRadians(circle_degree)));
				float z4=(float) ((circle_radius+yArray[(int)(i)][j+1])*Math.cos(Math.toRadians(circle_degree)));
				
				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
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
		float[] textures=generateTexCoor(col,row);
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
    	float sizew=32.0f/bw;//列数
    	float sizeh=32.0f/bh;//行数
    	int c=0;  
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    		
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			   			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;   
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    		}
    	}
    	return result;
    }
}