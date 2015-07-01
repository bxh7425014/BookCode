package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.carracer.Constant.*;

public class DrawAirship extends BNShape
{	
	static float feitingX;//飞艇所在运行轨迹中心点位置
	static float feitingY;
	static float feitingZ;
	 
	static float A=7*X_SPAN;//飞艇运动椭球的a半径————x方向
	static float B=3*X_SPAN;//飞艇运动椭球的b半径————z方向
	
	static float angle=360;//飞艇当前角度
	
	static float angle_Rotate=0;//飞艇扰动角度
	
	static float height=10;//飞艇上下扰动高差
	static float angle_Y=270;//正玄曲线当前帧角度 
	
	DrawSpheroid bodyback;
	DrawSpheroid bodyhead;
	DrawSpheroid cabin;
	DrawWing wing;
	static goThread thread;//运动线程
	
	final static float BODYBACK_A=6f;
	final static float BODYBACK_B=1f;
	final static float BODYBACK_C=1f;
	
	final static float BODYHEAD_A=2f;
	final static float BODYHEAD_B=1f;
	final static float BODYHEAD_C=1f;
	
	final static float CABIN_A=0.4f;
	final static float CABIN_B=0.2f;
	final static float CABIN_C=0.2f;
	
    public float mAngleX;//沿x轴旋转角度
    public float mAngleY;//沿y轴旋转角度 
    public float mAngleZ;//沿z轴旋转角度 
	
	public DrawAirship(float scale)
	{
		super(scale);
		
		bodyback=new DrawSpheroid(BODYBACK_A*scale,BODYBACK_B*scale,BODYBACK_C*scale,18,-90,90,-90,90);
		bodyhead=new DrawSpheroid(BODYHEAD_A*scale,BODYHEAD_B*scale,BODYHEAD_C*scale,18,-90,90,-90,90);
		cabin=new DrawSpheroid(CABIN_A*scale,CABIN_B*scale,CABIN_C*scale,18,0,360,-90,90);
		wing=new DrawWing(scale*BODYBACK_A/6,scale*BODYBACK_B/6,scale*BODYBACK_A/8,scale*BODYBACK_B/16,scale*BODYHEAD_C/1.5f);
		thread=new goThread();
//		thread.start();
	}
	
	public void drawSelf(GL10 gl, int texId, int number)
	{
		feitingX=(float) (A*Math.cos(Math.toRadians(angle)));
		feitingY=(float) (height*Math.sin(Math.toRadians(angle_Y)));
		feitingZ=(float) (B*Math.sin(Math.toRadians(angle)));
		
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z轴旋转    	
        gl.glRotatef(mAngleY, 0, 1, 0);//沿Y轴旋转
        gl.glRotatef(mAngleX, 1, 0, 0);//沿X轴旋转
        
        gl.glPushMatrix();
        
        gl.glTranslatef
        (
        		feitingX, 
        		feitingY, 
        		feitingZ
        );
        gl.glRotatef(angle_Rotate-90, 0, 1, 0);
        
        gl.glPushMatrix();
        gl.glTranslatef(scale*BODYBACK_A*10/12, 0, scale*BODYHEAD_C/1.7f);
        gl.glRotatef(-90, 1, 0, 0);
        gl.glRotatef(15, 0, 0, 1);
        wing.drawSelf(gl,texId);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(scale*BODYBACK_A*10/12, 0, -scale*BODYHEAD_C/1.7f);
        gl.glRotatef(90, 1, 0, 0);
        gl.glRotatef(15, 0, 0, 1);
        wing.drawSelf(gl,texId);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(scale*BODYBACK_A*10/12, -scale*BODYHEAD_C/1.7f, 0);
        gl.glRotatef(15, 0, 0, 1);
        wing.drawSelf(gl,texId);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(scale*BODYBACK_A*10/12, scale*BODYHEAD_C/1.7f, 0);
        gl.glRotatef(165, 0, 0, 1);
        wing.drawSelf(gl,texId);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotatef(180, 1, 0, 0);
        bodyback.drawSelf(gl,texId);
        gl.glPopMatrix();
         
        gl.glPushMatrix();
        gl.glRotatef(180, 0, 1, 0);
        bodyhead.drawSelf(gl,texId);
        gl.glPopMatrix(); 
        
        gl.glPushMatrix();
        gl.glTranslatef(scale*BODYBACK_A/4, -scale*BODYBACK_B, 0);
        gl.glRotatef(180, 1, 0, 0);
        cabin.drawSelf(gl,texId); 
        gl.glPopMatrix();
        
        gl.glPopMatrix();
	}
	
	
	//绘制椭球内部类
	private class DrawSpheroid {
		private FloatBuffer  mVertexBuffer;//顶点坐标数据缓冲
		private FloatBuffer mTextureBuffer;//纹理缓冲
	    public float mAngleX;//沿x轴旋转角度
	    public float mAngleY;//沿y轴旋转角度 
	    public float mAngleZ;//沿z轴旋转角度 
	    int vCount=0;
	    
//	    float a;//三轴半径长
//	    float b;
//	    float c;
//	    float angleSpan;//将球进行单位切分的角度
//	    float hAngleBegin;//经度绘制起始角度
//	    float hAngleOver;//经度绘制结束角度
//	    float vAngleBegin;//纬度绘制起始角度
//	    float vAngleOver;//纬度绘制结束角度
	    //hAngle表示经度，vAngle表示纬度。
	    public DrawSpheroid(float a,float b,float c,float angleSpan,
	    					float hAngleBegin,float hAngleOver,float vAngleBegin,float vAngleOver)
	    {	
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
	    			 (int)((hAngleOver-hAngleBegin)/angleSpan), //纹理图切分的列数
	    			 (int)((vAngleOver-vAngleBegin)/angleSpan)  //纹理图切分的行数 
	    	);
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(texCoorArray.length*4);
			tbb.order(ByteOrder.nativeOrder());
			mTextureBuffer=tbb.asFloatBuffer();
			mTextureBuffer.put(texCoorArray);
			mTextureBuffer.position(0);
	    }

	    public void drawSelf(GL10 gl,int textureId)
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
			
	        //绘制图形
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像 
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
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
	
	
	
	//绘制机翼内部类
	private class DrawWing {
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	    private FloatBuffer   mTexBuffer;//顶点着色数据缓冲
	    int vCount=0;//顶点数量    
		
		public DrawWing(float hWidth,float hLength,float lWidth,float lLength,float height)
		{			
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
		
		public void drawSelf(GL10 gl,int texId)
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
	        
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        gl.glDisable(GL10.GL_TEXTURE_2D);
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	}
	
	//飞艇运动线程
	public class goThread extends Thread 
	{
		boolean flag=true;//飞艇运动标志位
		
		public void run()
		{
			while(flag)
			{					 
				angle=angle-0.2f;
				angle_Y=angle_Y+30;
				angle_Rotate=angle_Rotate+0.2f;
				if(angle<=0)
				{
					angle=360;
				}
				if(angle_Y>=360)
				{
					angle_Y=0;
				}
				if(angle_Rotate>=360)
				{
					angle_Rotate=0;
				}
				try
				{
					Thread.sleep(100);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}