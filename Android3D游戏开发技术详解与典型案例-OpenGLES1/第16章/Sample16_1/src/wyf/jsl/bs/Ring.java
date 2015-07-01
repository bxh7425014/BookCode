package wyf.jsl.bs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ring
{
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	
	int vcount;
	int textureid;
	
	float ring_Span;
	float circle_Span;
	
	float ring_Radius;
	float circle_Radius;
	
	public Ring(int ring_Span,int circle_Span,float ring_Radius,float circle_Radius,int textureid)
	{     
		//ring_Span表示环每一份多少度；circle_Span表示圆截环每一份多少度;ring_Radius表示环半径；circle_Radius圆截面半径。
		this.ring_Span=ring_Span;
		this.circle_Span=circle_Span;
		this.circle_Radius=circle_Radius;
		this.ring_Radius=ring_Radius;
		this.textureid=textureid;
		
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
				
				val.add(x1);val.add(y1);val.add(z1);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x2);val.add(y2);val.add(z2);
							
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x3);val.add(y3);val.add(z3); 
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
		
		//纹理
		
		int row=(int) (360.0f/circle_Span);
		int col=(int) (360.0f/ring_Span);
		float[] textures=generateTexCoor(row,col);
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());
		myTexture=tbb.asFloatBuffer();
		myTexture.put(textures);
		myTexture.position(0);
	}
	
	public void drawSelf(GL10 gl)
	{	
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureid);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
	}
	
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//列数
    	float sizeh=1.0f/bh;//行数
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
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			   			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;     			
    		}
    	}
    	return result;
    }
    
}
