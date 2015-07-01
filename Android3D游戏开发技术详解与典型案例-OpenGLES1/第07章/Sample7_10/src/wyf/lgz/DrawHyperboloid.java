package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawHyperboloid
{
	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
	
	int vCount;//顶点数量
	
	float height;//抛物体高度
	float a;//截面椭圆轴参数
	float b;
	float c;
	float col;//抛物体高度切割份数
	float angleSpan;//截面切分角度
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	float R;//双曲线体顶端截面半径
	
	public DrawHyperboloid(float height,float a,float b,float c,int col,float angleSpan)
	{
		this.height=height;
		this.a=a;
		this.b=b;
		this.c=c;
		this.col=col;
		this.angleSpan=angleSpan;
		
		R=(float) (a*Math.sqrt((1+(0.5f*height)*(0.5f*height)/(c*c))));
		
		float heightSpan=height/col;//抛物体每块所占的高度
		
		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
		
		for(float h=0.5f*height;h>-0.5f*height;h-=heightSpan)//循环行
		{
			for(float angle=360;angle>0;angle-=angleSpan)//循环列
			{
				float mid=1+(h*h)/(c*c);//中间量
				float midNext=1+((h-heightSpan)*(h-heightSpan))/(c*c);//下一步的中间量
				
				float x1=(float) (a*Math.sqrt(mid)*Math.cos(Math.toRadians(angle)));
				float y1=h;
				float z1=(float) (b*Math.sqrt(mid)*Math.sin(Math.toRadians(angle)));
				
				float x2=(float) (a*Math.sqrt(midNext)*Math.cos(Math.toRadians(angle)));
				float y2=h-heightSpan;
				float z2=(float) (b*Math.sqrt(midNext)*Math.sin(Math.toRadians(angle)));
				
				float x3=(float) (a*Math.sqrt(midNext)*Math.cos(Math.toRadians(angle-angleSpan)));
				float y3=h-heightSpan;
				float z3=(float) (b*Math.sqrt(midNext)*Math.sin(Math.toRadians(angle-angleSpan)));
				
				float x4=(float) (a*Math.sqrt(mid)*Math.cos(Math.toRadians(angle-angleSpan)));
				float y4=h;
				float z4=(float) (b*Math.sqrt(mid)*Math.sin(Math.toRadians(angle-angleSpan)));
				
				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
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
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);
		
		gl.glColor4f(0, 0, 0, 0);
		gl.glDrawArrays(GL10.GL_LINES, 0, vCount);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}