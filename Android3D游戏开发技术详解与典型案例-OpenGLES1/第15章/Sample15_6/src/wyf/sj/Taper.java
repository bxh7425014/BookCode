package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Taper {
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
	private FloatBuffer myTexture;//纹理缓冲
	
	int textureId;
	int vCount;//顶点数量
	
	float height;//圆柱长度
	float circle_radius;//圆锥底面半径
	float degreespan;  //圆截环每一份的度数大小
	int col;//圆锥平均切分的块数 
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public Taper(float height,float circle_radius,float degreespan,int col,int textureId)
	{
		this.height=height;
		this.circle_radius=circle_radius;
		this.degreespan=degreespan;	//表示多少度切割一份
		this.col=col;				//表示height分几份切割
		this.textureId=textureId;
		
		float spanHeight=(float)height/col;//圆锥每块所占的高度
		int spannum=(int)(360.0f/degreespan);
		float spanR=circle_radius/col;//半径单位长度
				
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		
		for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
		{
			for(int j=0;j<col;j++)//循环列
			{
				float currentR=j*spanR;//当前截面的圆半径
				float currentHeight=height-j*spanHeight;//当前截面的高度
				
				float x1=(float) (currentR*Math.cos(Math.toRadians(circle_degree)));
				float y1=currentHeight;
				float z1=(float) (currentR*Math.sin(Math.toRadians(circle_degree)));
				
				
				float x2=(float) ((currentR+spanR)*Math.cos(Math.toRadians(circle_degree)));
				float y2=currentHeight-spanHeight;
				float z2=(float) ((currentR+spanR)*Math.sin(Math.toRadians(circle_degree)));
				
				
				float x3=(float) ((currentR+spanR)*Math.cos(Math.toRadians(circle_degree-degreespan)));
				float y3=currentHeight-spanHeight;
				float z3=(float) ((currentR+spanR)*Math.sin(Math.toRadians(circle_degree-degreespan)));
				
				float x4=(float) ((currentR)*Math.cos(Math.toRadians(circle_degree-degreespan)));
				float y4=currentHeight;
				float z4=(float) ((currentR)*Math.sin(Math.toRadians(circle_degree-degreespan)));
				
				
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
		float[] textures=generateTexCoor(spannum,col);
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
			
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//列单位长度
    	float sizeh=1.0f/bh;//行单位长度
    	int c=0;
    	for(int j=0;j<bw;j++)
    	{
    		for(int i=0;i<bh;i++)
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
