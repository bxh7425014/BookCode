package wyf.jsc.tdb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static wyf.jsc.tdb.Constant.*;

public class Wall {

	private FloatBuffer mVertexBuffer;
	private FloatBuffer mTextureBuffer;  //顶点纹理数据缓冲
	int vCount=0;
	float xOffset;    
    int textureId;	
	public Wall(int textureId)
	{
		this.textureId=textureId;
		//final float UNIT_SIZE=8f;
		float vertices[]=new float[]
		{
			-WALL_SIZE,WALL_SIZE,-WALL_SIZE,
			-WALL_SIZE,0,-WALL_SIZE,
			WALL_SIZE,0,-WALL_SIZE,
			
			-WALL_SIZE,WALL_SIZE,-WALL_SIZE,
			WALL_SIZE,0,-WALL_SIZE,
			WALL_SIZE,WALL_SIZE,-WALL_SIZE,//后面
			
			-WALL_SIZE,0,WALL_SIZE,
			-WALL_SIZE,WALL_SIZE,WALL_SIZE,
			WALL_SIZE,WALL_SIZE,WALL_SIZE,
			
			-WALL_SIZE,0,WALL_SIZE,
			WALL_SIZE,WALL_SIZE,WALL_SIZE,
			WALL_SIZE,0,WALL_SIZE,//前面
			
			-WALL_SIZE,WALL_SIZE,-WALL_SIZE,
			-WALL_SIZE,WALL_SIZE,WALL_SIZE,
			-WALL_SIZE,0,WALL_SIZE,
			
			-WALL_SIZE,WALL_SIZE,-WALL_SIZE,
			-WALL_SIZE,0,WALL_SIZE,
			-WALL_SIZE,0,-WALL_SIZE,//左面
			
			WALL_SIZE,0,-WALL_SIZE,
			WALL_SIZE,0,WALL_SIZE,
			WALL_SIZE,WALL_SIZE,WALL_SIZE,
			
			WALL_SIZE,0,-WALL_SIZE,
			WALL_SIZE,WALL_SIZE,WALL_SIZE,
			WALL_SIZE,WALL_SIZE,-WALL_SIZE
			
			
				
				
		
		};
		 //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
		vCount=vertices.length/3;
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        
  
         float textureCoors[]=new float[vCount*2];
         for(int i=0;i<vCount/6;i++)
         {
        	 textureCoors[i*12]=0;
        	 textureCoors[i*12+1]=0;
        	 
        	 textureCoors[i*12+2]=0;
        	 textureCoors[i*12+3]=1;
        	 
        	 textureCoors[i*12+4]=1;
        	 textureCoors[i*12+5]=1;
        	 
        	 textureCoors[i*12+6]=0;
        	 textureCoors[i*12+7]=0;
        	 
        	 textureCoors[i*12+8]=1;
        	 textureCoors[i*12+9]=1;
        	 
        	 textureCoors[i*12+10]=1;
        	 textureCoors[i*12+11]=0;
        	 
         }
          //创建顶点纹理数据缓冲
	        //textureCoors.length×4是因为一个float型整数四个字节
	        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
	        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mTextureBuffer = cbb.asFloatBuffer();//转换为int型缓冲
	        mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点着色数据
	        mTextureBuffer.position(0);//设置缓冲区起始位置
	        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
	        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
	        //顶点纹理数据的初始化================end============================*/        
       
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xOffset, 1, 0, 0);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
		gl.glVertexPointer//为画笔指定顶点坐标数据
		(
				3, 
				GL10.GL_FLOAT,
				0,
				mVertexBuffer
		);			
		gl.glEnable(GL10.GL_TEXTURE_2D); //开启纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);	//允许使用纹理ST坐标缓冲	
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId); //绑定当前纹理
		gl.glTexCoordPointer//为画笔指定纹理ST坐标缓冲
		(
				2, 
				GL10.GL_FLOAT, 
				0,
				mTextureBuffer
		);                                       
		gl.glDrawArrays//绘制图形
		(
				GL10.GL_TRIANGLES, 
				0, 
				vCount
		);	
	}
}

