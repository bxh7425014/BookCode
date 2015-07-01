package wyf.tzz.lta;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class DrawSpheroid {
	private FloatBuffer  mVertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer mTextureBuffer;//纹理缓冲
    public float mAngleX;//沿x轴旋转角度
    public float mAngleY;//沿y轴旋转角度 
    public float mAngleZ;//沿z轴旋转角度 
    int vCount=0;
    int textureId;
    
    float a;
    float b;		//三轴半径长
    float c;
    float angleSpan;//将球进行单位切分的角度
    float hAngleBegin;//经度绘制起始角度
    float hAngleOver;//经度绘制结束角度
    float vAngleBegin;//纬度绘制起始角度
    float vAngleOver;//纬度绘制结束角度
    
    //hAngle表示经度，vAngle表示纬度。
    public DrawSpheroid(float a,float b,float c,float angleSpan,
    					float hAngleBegin,float hAngleOver,float vAngleBegin,float vAngleOver,int textureId)
    {	
    	this.a=a;
    	this.b=b;
    	this.c=c;
    	this.hAngleBegin=hAngleBegin;
    	this.hAngleOver=hAngleOver;
    	this.vAngleBegin=vAngleBegin;
    	this.vAngleOver=vAngleOver;
    	this.textureId=textureId;

    	ArrayList<Float> alVertix=new ArrayList<Float>();//存放顶点坐标
    	
        for(float vAngle=vAngleBegin;vAngle<vAngleOver;vAngle=vAngle+angleSpan)//垂直方向angleSpan度一份
        {
        	for(float hAngle=hAngleBegin;hAngle<hAngleOver;hAngle=hAngle+angleSpan)//水平方向angleSpan度一份
        	{//纵向横向各到一个角度后计算对应的此点在球面上的坐标    		
        		float x1=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle)));                          
        		float y1=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle)));
        		float z1=(float)(c*Math.sin(Math.toRadians(vAngle)));
        		
        		float x2=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle)));
        		float y2=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle)));
        		float z2=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
        		
        		float x3=(float)(a*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.cos(Math.toRadians(hAngle+angleSpan)));
        		float y3=(float)(b*Math.cos(Math.toRadians(vAngle+angleSpan))*Math.sin(Math.toRadians(hAngle+angleSpan)));
        		float z3=(float)(c*Math.sin(Math.toRadians(vAngle+angleSpan)));
        		
        		float x4=(float)(a*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle+angleSpan)));
        		float y4=(float)(b*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle+angleSpan)));
        		float z4=(float)(c*Math.sin(Math.toRadians(vAngle)));
        		
        		//将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        	}
        } 	
        vCount=alVertix.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
    	
        //将alVertix中的坐标值转存到一个int数组中
        float[] vertices=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置

		//纹理
						
    	//获取切分整图的纹理数组
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(180/angleSpan), //纹理图切分的列数
    			 (int)(180/angleSpan)  //纹理图切分的行数 
    	);
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(texCoorArray.length*4);
		tbb.order(ByteOrder.nativeOrder());
		mTextureBuffer=tbb.asFloatBuffer();
		mTextureBuffer.put(texCoorArray);
		mTextureBuffer.position(0);
    }

    public void drawSelf(GL10 gl)
    {    	
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z轴旋转    	
        gl.glRotatef(mAngleY, 0, 1, 0);//沿Y轴旋转
        gl.glRotatef(mAngleX, 1, 0, 0);//沿X轴旋转
        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3   
        		GL10.GL_FLOAT,	//顶点坐标值的类型
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
        
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//启用深度测试
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
    }
    
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//列数
    	float sizeh=1.0f/bh;//行数
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }  
    

}
