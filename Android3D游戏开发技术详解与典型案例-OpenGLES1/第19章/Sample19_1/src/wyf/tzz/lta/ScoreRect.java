package wyf.tzz.lta;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

// 表示单个纹理矩形的类
public class ScoreRect {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点着色数据缓冲
    int vCount;							//顶点数量
    int texId;							//纹理ID
    
    public ScoreRect(int texId,float X_UNIT_SIZE,float Y_UNIT_SIZE,float[] textures){
    	this.texId=texId;
        vCount=6;								//顶点数量
        float vertices[]=new float[]			//顶点坐标数组
        {
        	-1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0,
        	-1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0,
        	
        	-1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);	//因为一个整数四个字节
        vbb.order(ByteOrder.nativeOrder());			//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();		//转换为int型缓冲
        mVertexBuffer.put(vertices);				//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);					//设置缓冲区起始位置
       
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());			//设置字节顺序
        mTextureBuffer= tbb.asFloatBuffer();		//转换为Float型缓冲
        mTextureBuffer.put(textures);				//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);					//设置缓冲区起始位置
    }

    public void drawSelf(GL10 gl) {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);			//启用顶点坐标数组
        gl.glVertexPointer(3,GL10.GL_FLOAT,	0, mVertexBuffer);	//指定顶点坐标数据
        gl.glEnable(GL10.GL_TEXTURE_2D);    					//开启纹理
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);	//允许使用纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//为画笔指定纹理ST坐标缓冲
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId); 			//绑定当前纹理
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0,vCount); 			//绘制图形
    }
}
