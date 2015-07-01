package wyf.jsl.lb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class DrawCube
{
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	
	int vcount; 
	int textureid;

	float length;
	float width;
	float height;
	
	public DrawCube(float length,float width,float height,int textureid)
	{
		this.length=length;
		this.width=width;
		this.height=height;
		this.textureid=textureid;
		
		float l=length/2;
		float w=width/2;
		float h=height/2;
		
		vcount=36;
		
		float[] vertexs=new float[]
		{	
			0-w,0-h,length-l,	
			0-w,0-h,0-l,
			0-w,height-h,0-l,
			
			0-w,0-h,length-l,
			0-w,height-h,0-l,
			0-w,height-h,length-l,
			
			width-w,0-h,0-l,
			0-w,0-h,0-l,
			0-w,height-h,0-l,
			
			width-w,0-h,0-l,
			0-w,height-h,0-l,
			width-w,height-h,0-l,
			
			width-w,height-h,length-l,
			width-w,height-h,0-l,
			width-w,0-h,0-l,
			
			width-w,height-h,length-l,
			width-w,0-h,0-l,
			width-w,0-h,length-l,
			
			width-w,height-h,0-l,
			0-w,height-h,0-l,
			0-w,height-h,length-l,
			
			width-w,height-h,0-l,
			0-w,height-h,length-l,
			width-w,height-h,length-l,
			
			width-w,height-h,length-l,
			0-w,height-h,length-l,
			0-w,0-h,length-l,
			
			width-w,height-h,length-l,
			0-w,0-h,length-l,
			width-w,0-h,length-l,
			
			width-w,0-h,length-l,
			0-w,0-h,length-l,
			0-w,0-h,0-l,
			
			width-w,0-h,length-l,
			0-w,0-h,0-l,
			width-w,0-h,0-l,
		};
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertex=vbb.asFloatBuffer();
		myVertex.put(vertexs);
		myVertex.position(0);
		float[] textures=new float[] 
		{						
	            1,1,1,0,0,0,
	            1,1,0,0,0,1,
	            0,1,1,1,1,0,
	            0,1,1,0,0,0,
	            1,1,1,0,0,0,
	            1,1,0,0,0,1,
	            1,0,0,0,0,1,
	            1,0,0,1,1,1,
	            0,1,1,1,1,0,
	            0,1,1,0,0,0,
	            0,1,1,1,1,0,
	            0,1,1,0,0,0
	           
		};
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
        //πÿ±’Œ∆¿Ì
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
}