package wyf.sj;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import static wyf.sj.Constant.*;

public class Sky_Ball
{
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	
	//球自转
	public int zAngle;

	
	int vcount;//顶点数量	
	int textureID;//纹理ID
	float scale;

		
	public Sky_Ball(float scale,int textureID)
	{		
		this.textureID=textureID;
		this.scale=scale;

		//顶点
		ArrayList<Float> val=new ArrayList<Float>();
		for(float i=90;i>-90;i-=ANGLE_SPAN)
		{
			for(float j=180;j>0;j-=ANGLE_SPAN)
			{
				float x1=(float) (scale*Math.cos(Math.toRadians(i))*Math.cos(Math.toRadians(j)));
				float z1=(float) (scale*Math.sin(Math.toRadians(i)));
				float y1=(float) (scale*Math.cos(Math.toRadians(i))*Math.sin(Math.toRadians(j)));
				
				float x2=(float) (scale*Math.cos(Math.toRadians(i-ANGLE_SPAN))*Math.cos(Math.toRadians(j)));
				float z2=(float) (scale*Math.sin(Math.toRadians(i-ANGLE_SPAN)));
				float y2=(float) (scale*Math.cos(Math.toRadians(i-ANGLE_SPAN))*Math.sin(Math.toRadians(j)));
				
				float x3=(float) (scale*Math.cos(Math.toRadians(i-ANGLE_SPAN))*Math.cos(Math.toRadians(j-ANGLE_SPAN)));
				float z3=(float) (scale*Math.sin(Math.toRadians(i-ANGLE_SPAN)));
				float y3=(float) (scale*Math.cos(Math.toRadians(i-ANGLE_SPAN))*Math.sin(Math.toRadians(j-ANGLE_SPAN)));
				
				float x4=(float) (scale*Math.cos(Math.toRadians(i))*Math.cos(Math.toRadians(j-ANGLE_SPAN)));
				float z4=(float) (scale*Math.sin(Math.toRadians(i)));
				float y4=(float) (scale*Math.cos(Math.toRadians(i))*Math.sin(Math.toRadians(j-ANGLE_SPAN)));
				
				val.add(x1);val.add(y1);val.add(z1);				
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x2);val.add(y2);val.add(z2);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x3);val.add(y3);val.add(z3);
			}
		}       
        vcount=val.size()/3;
		float[] ver=new float[vcount*3];
		for(int i=0;i<vcount*3;i++)
		{
			ver[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(ver.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertex=vbb.asFloatBuffer();
		myVertex.put(ver);
		myVertex.position(0);	
	
		
		//纹理
		ArrayList<Float> tal=new ArrayList<Float>();
		int trow=(int) (180/ANGLE_SPAN);//纵向块数
		int tcol=(int) (180/ANGLE_SPAN);//横向块数
		float rowUnitSize=(float)1.0f/trow;//纵向块长度
		float colUnitSize=(float)1.0f/tcol;//横向块长度
		for(int i=0;i<trow;i++)
		{
			for(int j=0;j<tcol;j++)
			{
				//第一个三角形
                tal.add(j*colUnitSize);//1
                tal.add(i*rowUnitSize);
                
                tal.add(j*colUnitSize+colUnitSize);//4
                tal.add(i*rowUnitSize);
                
                tal.add(j*colUnitSize);//2
                tal.add(i*rowUnitSize+rowUnitSize);
                
               
                
                //第二个三角形
                
                tal.add(j*colUnitSize);//2
                tal.add(i*rowUnitSize+rowUnitSize);
                
                tal.add(j*colUnitSize+colUnitSize);//4
                tal.add(i*rowUnitSize);
 
                
                tal.add(j*colUnitSize+colUnitSize);//3
                tal.add(i*rowUnitSize+rowUnitSize);
			}
		}
		float[] textures=new float[trow*tcol*6*2];
		for(int i=0;i<textures.length;i++)
		{
			textures[i]=tal.get(i);
		}
		
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        myTexture = tbb.asFloatBuffer();//转换为int型缓冲
        myTexture.put(textures);//向缓冲区中放入顶点着色数据
        myTexture.position(0);//设置缓冲区起始位置		
	}
	
	public void drawSelf(GL10 gl)
	{
		//顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		//gl.glRotatef((float)-(MySurfaceView.direction*180/Math.PI), 0, 0, 1);
		//纹理
        //开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理ST坐标缓冲
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
        
		//绘图
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
}

