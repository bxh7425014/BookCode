package wyf.jsc.rtb;



import static wyf.jsc.rtb.Constant.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Floor 
{
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mTextureBuffer;
	
	public float mAngleX;
	public float mAngleZ;
	int vCount=0;
	int textureId;
	int scale;
	public Floor(int scale,int textureId)
	{
		this.scale=scale;
		this.textureId=textureId;

		vCount=GV_UNIT_NUM;//定点数量
		
		float[] vertices=
		{
			0,0,0,
			0,UNIT_HIGHT*scale,0,
			UNIT_SIZE,UNIT_HIGHT*scale,0,
			
			0,0,0,
			UNIT_SIZE,UNIT_HIGHT*scale,0,
			UNIT_SIZE,0,0,
			
			0,UNIT_HIGHT*scale,0,
			0,UNIT_HIGHT*scale,UNIT_SIZE,
			UNIT_SIZE,UNIT_HIGHT*scale,UNIT_SIZE,
			
			0,UNIT_HIGHT*scale,0,
			UNIT_SIZE,UNIT_HIGHT*scale,UNIT_SIZE,
			UNIT_SIZE,UNIT_HIGHT*scale,0,
			
			0,UNIT_HIGHT*scale,UNIT_SIZE,
			0,0,UNIT_SIZE,
			UNIT_SIZE,0,UNIT_SIZE,
			
			0,UNIT_HIGHT*scale,UNIT_SIZE,
			UNIT_SIZE,0,UNIT_SIZE,
			UNIT_SIZE,UNIT_HIGHT*scale,UNIT_SIZE,
			//左面
			
			0,UNIT_HIGHT*scale,0,
			0,0,0,
			0,0,UNIT_SIZE,
			
			0,UNIT_HIGHT*scale,0,
			0,0,UNIT_SIZE,
			0,UNIT_HIGHT*scale,UNIT_SIZE,
			//右面
			UNIT_SIZE,UNIT_HIGHT*scale,UNIT_SIZE,
			UNIT_SIZE,0,UNIT_SIZE,
			UNIT_SIZE,0,0,
			
			UNIT_SIZE,UNIT_HIGHT*scale,UNIT_SIZE,
			UNIT_SIZE,0,0,
			UNIT_SIZE,UNIT_HIGHT*scale,0,
			//下面
			0,0,UNIT_SIZE,
			0,0,0,
			UNIT_SIZE,0,0,
					
			0,0,UNIT_SIZE,
			UNIT_SIZE,0,0,
			UNIT_SIZE,0,UNIT_SIZE
			
			
		};
	
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertices);
		mVertexBuffer.position(0);
		
		float[] textureCoors=new float[vCount*2];
		for(int i=0;i<vCount/6;i++)
		{
			textureCoors[i*12]=0;
			textureCoors[(i*12)+1]=0;
			
			textureCoors[(i*12)+2]=0;
			textureCoors[(i*12)+3]=1;
			
			textureCoors[(i*12)+4]=1;
			textureCoors[(i*12)+5]=1;
			
			textureCoors[(i*12)+6]=0;
			textureCoors[(i*12)+7]=0;
			
			textureCoors[(i*12)+8]=1;
			textureCoors[(i*12)+9]=1;
			
			textureCoors[(i*12)+10]=1;
			textureCoors[(i*12)+11]=0;
			
			
			
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer=tbb.asFloatBuffer();
		mTextureBuffer.put(textureCoors);
		mTextureBuffer.position(0);
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glRotatef(mAngleX, 1, 0, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		//gl.glTranslatef(Tx, Ty, Tz);
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,mVertexBuffer);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,mTextureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
	}
}

