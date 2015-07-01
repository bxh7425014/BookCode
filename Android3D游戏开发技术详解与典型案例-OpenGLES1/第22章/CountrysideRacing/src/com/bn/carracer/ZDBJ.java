package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class ZDBJ 
{
    int roadTextureId;//路面纹理id
    int lubiaoTexId;//路标纹理id
    
    float partSize;//部件尺寸
    float roadWidth;//路宽
    
    Road road;//公路对象
    DrawCylinder lubiao;//路标对象
    
    float Cylinder_R=9.6f;//路标圆柱的半径
    float start=130f;//路标圆柱绘制的起始角度
    float over=50f;//路标圆柱绘制的结束角度
    float cylinder_offsetY;//路标圆柱Y方向偏移量  
    float cylinder_offsetZ;//路标圆柱Z方向偏移量
    
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
    
	public void setTexId(int roadTextureId,int lubiaoTexId)
	{
		this.roadTextureId=roadTextureId;
		this.lubiaoTexId=lubiaoTexId;
	}
	
    public ZDBJ 
    (
    		float partSize,//部件尺寸
    		float roadWidth//路宽
    )
    { 
    	this.partSize=partSize;
    	this.roadWidth=roadWidth;
    	
    	road=new Road(partSize,roadWidth,200);
    	lubiao=new DrawCylinder(partSize,Cylinder_R,1.8f,start,over);
    	
    	cylinder_offsetY=(float) (-Cylinder_R*Math.sin(Math.toRadians(over)));//路标圆柱Y方向偏移量
    	cylinder_offsetZ=(float) (roadWidth/2+Cylinder_R*Math.cos(Math.toRadians(over)));//路标圆柱Z方向偏移量
    }
    
    //绘制直道部件
    public void drawSelf(GL10 gl)
    {
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
    	gl.glPushMatrix();//公路
    	road.drawSelf(gl);
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();//路标上侧
    	gl.glTranslatef(0, cylinder_offsetY, cylinder_offsetZ);
    	lubiao.drawSelf(gl);
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();//路标下侧
    	gl.glTranslatef(0, cylinder_offsetY, -cylinder_offsetZ);
    	lubiao.drawSelf(gl);
    	gl.glPopMatrix();
    }
    
    
    
    
    
	private class Road
	{
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
	    int vCount=0;
	    
	    public Road(float L,float W,int col)
	    {		    	
	    	float width=L/2;//直路部件半长
	    	float height=W/2;//直路部件半宽  
	    	float lengthSpan=L/col;//直路部件长变化单位
	    	
	    	float REPEAT=10f;
	    	float texSpan=REPEAT/col;
	    	
	    	ArrayList<Float> val=new ArrayList<Float>();//顶点列表
	    	ArrayList<Float> tal=new ArrayList<Float>();//纹理列表
	    	
	    	for(int i=0;i<col;i++)
	    	{
	    		float s=i*texSpan;
	    		
	    		float x1=-width+lengthSpan*i;//左上
	    		float y1=height;
	    		float z1=0;
	    		
	    		float s1=s;
	    		float t1=0;
	    		
	    		float x2=-width+lengthSpan*i;//左下
	    		float y2=-height;
	    		float z2=0;
	    		
	    		float s2=s;
	    		float t2=1;
	    		
	    		float x3=-width+lengthSpan*(i+1);//右下
	    		float y3=-height;
	    		float z3=0;
	    		
	    		float s3=s+texSpan;
	    		float t3=1;
	    		
	    		float x4=-width+lengthSpan*(i+1);//右上
	    		float y4=height;
	    		float z4=0;
	    		
	    		float s4=s+texSpan;
	    		float t4=0;
	    		
	    		val.add(x1);val.add(y1);val.add(z1);
	    		val.add(x2);val.add(y2);val.add(z2);
	    		val.add(x4);val.add(y4);val.add(z4);
	    		
	    		
	    		val.add(x2);val.add(y2);val.add(z2);
	    		val.add(x3);val.add(y3);val.add(z3);
	    		val.add(x4);val.add(y4);val.add(z4);
	    		
	    		tal.add(s1);tal.add(t1);
	    		tal.add(s2);tal.add(t2);
	    		tal.add(s4);tal.add(t4);
	    		
	    		tal.add(s2);tal.add(t2);
	    		tal.add(s3);tal.add(t3);
	    		tal.add(s4);tal.add(t4);
	    	}
	        
	    	vCount=val.size()/3;
	    	
			float[] vertices=new float[vCount*3];
			for(int i=0;i<vCount*3;i++)
			{
				vertices[i]=val.get(i);
			}
	        
	        //创建顶点坐标数据缓冲
	        //vertices.length*4是因为一个整数四个字节
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
	        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mVertexBuffer.position(0);//设置缓冲区起始位置
	        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
	        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
	        //顶点坐标数据的初始化================end============================
	        
	        //纹理 坐标数据初始化

			float[] texST=new float[vCount*2];
			for(int i=0;i<vCount*2;i++)
			{
				texST[i]=tal.get(i);
			}
	        
	        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
	        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mTextureBuffer = tbb.asFloatBuffer();//转换为int型缓冲
	        mTextureBuffer.put(texST);//向缓冲区中放入顶点着色数据
	        mTextureBuffer.position(0);//设置缓冲区起始位置        
	    }
	    
	    public void drawSelf(GL10 gl)
	    {   
	    	gl.glRotatef(-90, 1, 0, 0);//使直道部件旋转到XOZ平面上
	    	
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组

	        
			//为画笔指定顶点坐标数据
	        gl.glVertexPointer
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
	        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
	        //绑定当前纹理
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, roadTextureId);
			
	        //绘制图形
	        gl.glDrawArrays
	        (
	        		GL10.GL_TRIANGLES, 		//以三角形方式填充
	        		0, 			 			//开始点编号
	        		vCount					//顶点的数量
	        );
	        
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        gl.glDisable(GL10.GL_TEXTURE_2D);
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    }
	}
	
    
    //绘制圆柱的内部类
    private class DrawCylinder
    {
    	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
    	private FloatBuffer myTexture;//纹理缓冲
    	
    	int vCount;//顶点数量
    	
    	public DrawCylinder(float length,float circle_radius,float degreespan,float startAngle,float overAngle)
    	{
    		int spannum=(int)(360.0f/degreespan);
    		
    		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
    		
    		for(float circle_degree=startAngle;circle_degree>overAngle;circle_degree-=degreespan)//循环行
    		{
    				float x1 =(float)(-length/2);
    				float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
    				float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
    				
    				float x2 =(float)(-length/2);
    				float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
    				float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
    				
    				float x3 =(float)(length/2);
    				float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
    				float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
    				
    				float x4 =(float)(length/2);
    				float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
    				float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
    				
    				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
    				val.add(x2);val.add(y2);val.add(z2);
    				val.add(x4);val.add(y4);val.add(z4);
    				
    				val.add(x2);val.add(y2);val.add(z2);
    				val.add(x3);val.add(y3);val.add(z3);
    				val.add(x4);val.add(y4);val.add(z4);
    		}
    		 
    		vCount=val.size()/3;//确定顶点数量
    		
    		//顶点
    		float[] vertexs=new float[vCount*3];
    		for(int i=0;i<vCount*3;i++)
    		{
    			vertexs[i]=val.get(i);
    		}
    		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
    		vbb.order(ByteOrder.nativeOrder());
    		myVertexBuffer=vbb.asFloatBuffer();
    		myVertexBuffer.put(vertexs);
    		myVertexBuffer.position(0);
    		
    		//纹理
    		float[] textures=generateTexCoor(spannum);
    		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
    		tbb.order(ByteOrder.nativeOrder());
    		myTexture=tbb.asFloatBuffer();
    		myTexture.put(textures);
    		myTexture.position(0);
    	}
    	
    	public void drawSelf(GL10 gl)
    	{	
    		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
    		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
    		
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
    		gl.glBindTexture(GL10.GL_TEXTURE_2D, lubiaoTexId);
    		
    		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
    		
    		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    	}
    	
        //自动切分纹理产生纹理数组的方法
        public float[] generateTexCoor(int bh)
        {
        	float[] result=new float[bh*6*2]; 
        	float sizeh=1.0f/bh;//行数
        	float REPEAT=10;
        	int c=0;
        	for(int i=0;i<bh;i++)
        	{
        			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
        			float t=i*sizeh;
        			
        			result[c++]=0;
        			result[c++]=t;
        		
        			result[c++]=0;
        			result[c++]=t+sizeh;
        			 
        			result[c++]=REPEAT;
        			result[c++]=t;
        			   			
        			result[c++]=0;
        			result[c++]=t+sizeh;
        			
        			result[c++]=REPEAT;
        			result[c++]=t+sizeh;   
        			
        			result[c++]=REPEAT;
        			result[c++]=t;
        	}
        	return result;
        }
    }
}