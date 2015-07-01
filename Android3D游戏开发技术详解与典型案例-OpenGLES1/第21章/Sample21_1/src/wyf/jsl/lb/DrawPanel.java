package wyf.jsl.lb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class DrawPanel
{
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	
	float width;
	float height;
	
	int vcount;
	int textureid;
	
	public DrawPanel(float width,float height,int textureid,float[] textures)
	{
		this.width=width;
		this.height=height;
		this.textureid=textureid;
		
		vcount=6;
		float[] vertexs=new float[]
		{
			-width/2,height/2,0,
			-width/2,-height/2,0,
			width/2,height/2,0,
			
			width/2,height/2,0,
			-width/2,-height/2,0,
			width/2,-height/2,0
		};
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexs.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        myVertex = vbb.asFloatBuffer();//转换为int型缓冲
        myVertex.put(vertexs);//向缓冲区中放入顶点坐标数据
        myVertex.position(0);//设置缓冲区起始位置

        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        myTexture = tbb.asFloatBuffer();//转换为int型缓冲
        myTexture.put(textures);//向缓冲区中放入顶点坐标数据
        myTexture.position(0);//设置缓冲区起始位置
	
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
        //关闭纹理
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}