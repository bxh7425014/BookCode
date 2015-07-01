package wyf.sj;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CubeColor {

	private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mColorBuffer;	//纹理坐标数据缓冲
	public float mOffsetX;
	public float mOffsetY;
	float scale;						//立方体高度			
	int vCount;//顶点数量
	public CubeColor(float scale,float length,float width)
	{
		this.scale=scale;
		vCount=36;
		float TABLE_UNIT_SIZE=0.5f;
		float TABLE_UNIT_HIGHT=0.5f;
		float[] verteices=
		{
				
				//顶面
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//后面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				//前面
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//下面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				//左面
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				-TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				
				//右面
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,-TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width,
				TABLE_UNIT_SIZE*length,-TABLE_UNIT_HIGHT*scale,TABLE_UNIT_SIZE*width
						
		};
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
		vbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
		mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);//设置缓冲区起始位置
		
		float[] colors=new float[vCount*4];//设置颜色数组
		for(int i=0;i<vCount;i++)
		{
			colors[i*4]=(float)Math.random();
			colors[i*4+1]=(float)Math.random();
			colors[i*4+2]=(float)Math.random();
			colors[i*4+3]=(float)Math.random();
		}
		
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(colors.length*4); //创建颜色坐标数据缓冲
		tbb.order(ByteOrder.nativeOrder());//设置字节顺序
		mColorBuffer=tbb.asFloatBuffer();//转换为float型缓冲
		mColorBuffer.put(colors);//向缓冲区中放入顶点坐标数据
		mColorBuffer.position(0);//设置缓冲区起始位置
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mOffsetX, 1, 0, 0);
		gl.glRotatef(mOffsetY, 0, 1, 0);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		
        //为画笔指定顶点着色数据
        gl.glColorPointer
        (
        		4, 				//设置颜色的组成成分，必须为4—RGBA
        		GL10.GL_FLOAT, 	//顶点颜色值的类型为 GL_FIXED
        		0, 				//连续顶点着色数据之间的间隔
        		mColorBuffer	//顶点着色数据
        );
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图形
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//禁用顶点颜色数组		
	}

}
