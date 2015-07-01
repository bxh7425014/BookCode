package wyf.jsl.lb;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class PackageWaterTankBody {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTexBuffer;//顶点着色数据缓冲
    int vCount=0;//顶点数量    
    float yAngle=0;//绕y轴旋转的角度
    float xAngle=0;//绕z轴旋转的角度    
    int texId;//纹理ID
    
    float hWidth;
    float hLength;
    float lWidth;
    float lLength;
    float height;
	
	public PackageWaterTankBody(float hWidth,float hLength,float lWidth,float lLength,float height,int texId)
	{
		this.height=height;
		this.hLength=hLength;
		this.hWidth=hWidth;
		this.lLength=lLength;
		this.lWidth=lWidth;
		this.texId=texId;//hWidth/2   hLength/2      lWidth/2     lLength/2
		
		//顶点
		vCount=36;
		float vertices[]=new float[]{
			-hWidth/2,height/2,-hLength/2,
			lWidth/2,-height/2,-lLength/2,
			-lWidth/2,-height/2,-lLength/2,
			
			-hWidth/2,height/2,-hLength/2,
			hWidth/2,height/2,-hLength/2,
			lWidth/2,-height/2,-lLength/2,
			
			-lWidth/2,-height/2,-lLength/2,
			-lWidth/2,-height/2,lLength/2,
			-hWidth/2,height/2,hLength/2,
			
			-lWidth/2,-height/2,-lLength/2,
			-hWidth/2,height/2,hLength/2,
			-hWidth/2,height/2,-hLength/2,
			
			-hWidth/2,height/2,-hLength/2,
			-hWidth/2,height/2,hLength/2,
			hWidth/2,height/2,hLength/2,
			
			-hWidth/2,height/2,-hLength/2,
			hWidth/2,height/2,hLength/2,
			hWidth/2,height/2,-hLength/2,
			
			hWidth/2,height/2,-hLength/2,
			hWidth/2,height/2,hLength/2,
			lWidth/2,-height/2,lLength/2,
			
			hWidth/2,height/2,-hLength/2,
			lWidth/2,-height/2,lLength/2,
			lWidth/2,-height/2,-lLength/2,
			
			-hWidth/2,height/2,hLength/2,
			-lWidth/2,-height/2,lLength/2,
			lWidth/2,-height/2,lLength/2,
			
			-hWidth/2,height/2,hLength/2,
			lWidth/2,-height/2,lLength/2,
			hWidth/2,height/2,hLength/2,
			
			-lWidth/2,-height/2,lLength/2,
			-lWidth/2,-height/2,-lLength/2,
			lWidth/2,-height/2,lLength/2,
			
			-lWidth/2,-height/2,-lLength/2,
			lWidth/2,-height/2,-lLength/2,
			lWidth/2,-height/2,lLength/2
		};		
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置

        //船身纹理
        float[] texCoor=new float[]{
        	1,0,
        	0,1,
        	1,1,
        	
        	1,0,
        	0,0,
        	0,1,
        	
        	0,1,
        	1,1,
        	1,0,
        	
        	0,1,
        	1,0,
        	0,0,
        	
        	0,0,
        	0,1,
        	1,1,
        	
        	0,0,
        	1,1,
        	1,0,
        	
        	1,0,
        	0,0,
        	0,1,
        	
        	1,0,
        	0,1,
        	1,1,
        	
        	0,0,
        	0,1,
        	1,1,
        	
        	0,0,
        	1,1,
        	1,0,
        	
        	0,0,
        	0,1,
        	1,0,
        	
        	0,1,
        	1,1,
        	1,0
        };
        //创建顶点纹理数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexBuffer = cbb.asFloatBuffer();//转换为int型缓冲
        mTexBuffer.put(texCoor);//向缓冲区中放入顶点着色数据
        mTexBuffer.position(0);//设置缓冲区起始位置
	}
	
	public void drawSelf(GL10 gl)
	{		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组       		
        gl.glVertexPointer//为画笔指定顶点坐标数据
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		
        //开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理ST坐标缓冲
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );
	}
}
