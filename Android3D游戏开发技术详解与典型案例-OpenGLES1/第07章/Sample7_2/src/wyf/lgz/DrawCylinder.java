package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCylinder
{
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
	
	int vCount;//顶点数量
	
	float length;//圆柱长度
	float circle_radius;//圆截环半径
	float degreespan;  //圆截环每一份的度数大小 
	int col;//圆柱块数
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawCylinder(float length,float circle_radius,float degreespan,int col)
	{
		this.circle_radius=circle_radius;
		this.length=length;
		this.col=col;
		this.degreespan=degreespan;
		
		float collength=(float)length/col;//圆柱每块所占的长度
		
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
				
				val.add(x1);val.add(y1);val.add(z1);//每条线两个顶点确定，有6条线，共12个顶点。
				val.add(x2);val.add(y2);val.add(z2);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x1);val.add(y1);val.add(z1);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x2);val.add(y2);val.add(z2);
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
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
		
		gl.glColor4f(0, 0, 0, 0);//设置绘制线为黑色
		gl.glDrawArrays(GL10.GL_LINES, 0, vCount);//绘制图像
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}