package wyf.lgz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCirque
{
	private FloatBuffer myVertex;//顶点缓冲
	
	int vcount;
	
	float ring_Span;
	float circle_Span;
	
	float ring_Radius;
	float circle_Radius;
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawCirque(float ring_Span,float circle_Span,float ring_Radius,float circle_Radius)
	{     
		//ring_Span表示环每一份多少度；circle_Span表示圆截环每一份多少度;ring_Radius表示环半径；circle_Radius圆截面半径。
		this.ring_Span=ring_Span;
		this.circle_Span=circle_Span;
		this.circle_Radius=circle_Radius;
		this.ring_Radius=ring_Radius;
		
		ArrayList<Float> val=new ArrayList<Float>();
		
		for(float circle_Degree=0f;circle_Degree<360f;circle_Degree+=circle_Span)
		{
			for(float ring_Degree=0f;ring_Degree<360f;ring_Degree+=ring_Span)
			{
				float x1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree)));
				float y1=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
				float z1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree)));
				
				float x2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
				float y2=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
				float z2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
				
				float x3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
				float y3=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
				float z3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
				
				float x4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree)));
				float y4=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
				float z4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree)));
				
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
	}
	
	public void drawSelf(GL10 gl)
	{	
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		gl.glColor4f(0, 0, 0, 0);
		gl.glDrawArrays(GL10.GL_LINES, 0, vcount);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
}
