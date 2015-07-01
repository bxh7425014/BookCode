package wyf.jsl.bs;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

import static wyf.jsl.bs.Constant.*;

public class Roof {
	private FloatBuffer   mVertexBuffer;
    private FloatBuffer   mTextureBuffer;
    int vCount;
    int texId;
    
    public Roof(int texId,float width,float height,float length)
    {
    	this.texId=texId;
    	
    	//顶点坐标数据的初始化================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        	-width/2,height,length/2,
        	-width/2,height,-length/2,
        	width/2,height,-length/2,
        	
        	-width/2,height,length/2,
        	width/2,height,-length/2,
        	width/2,height,length/2
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        
        ByteBuffer tbb = ByteBuffer.allocateDirect(HALL_TEXTURES.length*4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer= tbb.asFloatBuffer();
        mTextureBuffer.put(HALL_TEXTURES);
        mTextureBuffer.position(0);
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer
        (
        		3,				
        		GL10.GL_FLOAT,	
        		0, 				
        		mVertexBuffer	
        );
		
        gl.glEnable(GL10.GL_TEXTURE_2D); 
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		
        		0,
        		vCount
        );
        
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D); 
    }
}