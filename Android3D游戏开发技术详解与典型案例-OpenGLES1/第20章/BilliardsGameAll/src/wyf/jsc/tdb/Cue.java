package wyf.jsc.tdb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


import javax.microedition.khronos.opengles.GL10;

public class Cue{
	public FloatBuffer vertexBuffer;
	public FloatBuffer textureBuffer;
	public int vCount=0;
	public int texId;
	//public float angleX=60;
	public static float angleZ=0;
	//public float angleY=0;
	float yOffset;
	float vy;
	public Cue(float R,float r,float hight,int scale,int texId,float yOffset)
	{
		this.yOffset=yOffset;
		this.texId=texId;
		float FLOAT_SPAN=11.25f;
		float texture[]=generateTexCoor((int)(360/FLOAT_SPAN));
		int c=0;
		int tc=texture.length;
		List<Float>vertexList=new ArrayList<Float>();
		List<Float>textureList=new ArrayList<Float>();
		for(float angle=360;angle>0;angle-=FLOAT_SPAN)
		{
			float x1=(float)(scale*r*Math.cos(Math.toRadians(angle)));
			float y1=scale*hight;
			float z1=(float)(scale*r*Math.sin(Math.toRadians(angle)));
			
			float x2=(float)(scale*R*Math.cos(Math.toRadians(angle)));
			float y2=2;
			float z2=(float)(scale*R*Math.sin(Math.toRadians(angle)));
			
			float x3=(float)(scale*R*Math.cos(Math.toRadians(angle-FLOAT_SPAN)));
			float y3=2;
			float z3=(float)(scale*R*Math.sin(Math.toRadians(angle-FLOAT_SPAN)));
			
			float x4=(float)(scale*r*Math.cos(Math.toRadians(angle-FLOAT_SPAN)));
			float y4=scale*hight;
			float z4=(float)(scale*r*Math.sin(Math.toRadians(angle-FLOAT_SPAN)));
			
			vertexList.add(x1);vertexList.add(y1);vertexList.add(z1);
			vertexList.add(x2);vertexList.add(y2);vertexList.add(z2);
			vertexList.add(x4);vertexList.add(y4);vertexList.add(z4);
			
			vertexList.add(x4);vertexList.add(y4);vertexList.add(z4);
			vertexList.add(x2);vertexList.add(y2);vertexList.add(z2);
			vertexList.add(x3);vertexList.add(y3);vertexList.add(z3);
			
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);
			textureList.add(texture[c++%tc]);		
		}
		vCount=vertexList.size()/3;
		float []vertice=new float[vertexList.size()];
		for(int i=0;i<vertexList.size();i++)
		{
			vertice[i]=vertexList.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertice.length*4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer=vbb.asFloatBuffer();
		vertexBuffer.put(vertice);
		vertexBuffer.position(0);
		
		float[]textures=new float[textureList.size()];
		for(int i=0;i<textureList.size();i++)
		{
			textures[i]=textureList.get(i);
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());
		textureBuffer=tbb.asFloatBuffer();
		textureBuffer.put(textures);
		textureBuffer.position(0);
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(angleZ, 0,0 , 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
	}
	public float[] generateTexCoor(int share)
	{
		float[]result=new float[share*6*2];
		int c=0;
		float bs=1.0f/share;
		for(int i=0;i<share;i++)
		{
			float s=i*bs;
			result[c++]=s;
			result[c++]=0;
			
			result[c++]=s;
			result[c++]=1;
			
			result[c++]=s+bs;
			result[c++]=0;
			
			result[c++]=s+bs;
			result[c++]=0;
			
			result[c++]=s;
			result[c++]=1;
			
			result[c++]=s+bs;
			result[c++]=1;
			
		}
		return result;	
	}
	public void cueRun()
	{
		
	}
}


