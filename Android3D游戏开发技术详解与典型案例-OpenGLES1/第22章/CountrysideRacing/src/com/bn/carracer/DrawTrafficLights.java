package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class DrawTrafficLights extends BNShape
{
	//立方体长、宽、高
	private static float LENGTH=12f;
	private static float WIDTH=2f;
	private static float HEIGHT=4f;
	
	//球半径、切割角度
	private final float RADIUS=4f;
    private final float ANGLE_SPAN=18;
    
    //圆柱高、半径、切割度数、块数
	private static float CYLINDER_HEIGHT=40f;
	private static float CIRCLE_RADIUS=2f;
	private static float DEGREESPAN=18f;
	private static int COL=1;
    
	static boolean flag=true;//线程启动标志位	
	Cube lightBoard;
	DrawBall light;
	Texture face;
	DrawCylinder pole;
	DrawCylinder poleh;
	
	static LightTurn lt;
	
	static float count=0;
	float mAngleX=0;
	float mAngleY=0;
	
	public DrawTrafficLights(float scale) 
	{
		super(scale);
		lightBoard=new Cube(scale);
		light=new DrawBall(scale);
		face=new Texture(scale);
		pole = new DrawCylinder(
				scale*CYLINDER_HEIGHT,
				scale*CIRCLE_RADIUS,
				scale*DEGREESPAN,
				COL);
		poleh=new DrawCylinder(
				scale*CYLINDER_HEIGHT*0.2f,
				scale*CIRCLE_RADIUS*0.2f,
				scale*DEGREESPAN,
				COL);
		
	}
	
	//初始化交通灯线程
	public void initLightTurn()
	{
		lt=new LightTurn();
	}

	@Override
	public void drawSelf(GL10 gl, int texId, int number) 
	{
		gl.glRotatef(mAngleX, 1, 0, 0);
		gl.glRotatef(mAngleY, 0, 1, 0);
		
		gl.glPushMatrix();
		gl.glTranslatef(0, scale*CYLINDER_HEIGHT/2,0);
		gl.glRotatef(90, 0, 0, 1);
		pole.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(scale*RADIUS, scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.06f, 0);
		poleh.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(scale*RADIUS, scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.12f, 0);
		poleh.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(scale*RADIUS+scale*CYLINDER_HEIGHT*0.2f/2+scale*LENGTH, 
				scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.1f, 
				0);
		lightBoard.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslatef(scale*RADIUS+scale*CYLINDER_HEIGHT*0.2f/2+scale*LENGTH, 
				scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.1f, 
				scale*WIDTH);
		face.drawSelf(gl, texId);
		gl.glPopMatrix();
		
		if(count%3==2)//绿灯
		{
			gl.glPushMatrix();
			float[] directionParams={0,0,35f,0};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);
			initMaterial(gl,0,1,0,1);//初始化材质
			gl.glTranslatef(scale*RADIUS+scale*CYLINDER_HEIGHT*0.2f/2+5*RADIUS*scale, 
					scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.1f, 
					scale*WIDTH+0.1f-RADIUS*scale/2);
			gl.glRotatef(90, 1, 0, 0);
            initLight(gl,1,1,1,1);//开灯
            light.drawSelf(gl);//绘制
            closeLight(gl);//关灯
			gl.glPopMatrix();
		}
		
		if(count%3==0)//红灯
		{
			gl.glPushMatrix();
			float[] directionParams={0,0,35f,0};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);
			initMaterial(gl,1,0,0,1);//初始化材质
			gl.glTranslatef(scale*RADIUS+scale*CYLINDER_HEIGHT*0.2f/2+RADIUS*scale, 
					scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.1f, 
					scale*WIDTH+0.1f-RADIUS*scale/2);
			gl.glRotatef(90, 1, 0, 0);
            initLight(gl,1,1,1,1);//开灯
            light.drawSelf(gl);//绘制
            closeLight(gl);//关灯
			gl.glPopMatrix();
		}
		
		if(count%3==1)//黄灯
		{
			gl.glPushMatrix();
			float[] directionParams={0,0,35f,0};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);
			initMaterial(gl,1,1,0,1);//初始化材质
			gl.glTranslatef(scale*RADIUS+scale*CYLINDER_HEIGHT*0.2f/2+3*RADIUS*scale, 
					scale*CYLINDER_HEIGHT-scale*CYLINDER_HEIGHT*0.1f, 
					scale*WIDTH+0.1f-RADIUS*scale/2);
			gl.glRotatef(90, 1, 0, 0);
            initLight(gl,1,1,1,1);//开灯
            light.drawSelf(gl);//绘制
            closeLight(gl);//关灯
			gl.glPopMatrix();
		}
	}
	
	private class DrawBall
	{
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	    private FloatBuffer mNormalBuffer;//顶点纹理数据缓冲
	    int vCount=0;//顶点数量
	    public DrawBall(float scale) 
	    {    	
	    	ArrayList<Float> alVertix=new ArrayList<Float>();//存放顶点坐标的ArrayList
	    	
	        for(float vAngle=90;vAngle>30;vAngle=vAngle-ANGLE_SPAN)//垂直方向angleSpan度一份
	        {
	        	for(float hAngle=360;hAngle>0;hAngle=hAngle-ANGLE_SPAN)//水平方向angleSpan度一份
	        	{
	        		//纵向横向各到一个角度后计算对应的此点在球面上的四边形顶点坐标
	        		//并构建两个组成四边形的三角形
	        		
	        		double xozLength=scale*RADIUS*Math.cos(Math.toRadians(vAngle));
	        		float x1=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
	        		float z1=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
	        		float y1=(float)(scale*RADIUS*Math.sin(Math.toRadians(vAngle)));
	        		
	        		xozLength=scale*RADIUS*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
	        		float x2=(float)(xozLength*Math.cos(Math.toRadians(hAngle)));
	        		float z2=(float)(xozLength*Math.sin(Math.toRadians(hAngle)));
	        		float y2=(float)(scale*RADIUS*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
	        		
	        		xozLength=scale*RADIUS*Math.cos(Math.toRadians(vAngle-ANGLE_SPAN));
	        		float x3=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
	        		float z3=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
	        		float y3=(float)(scale*RADIUS*Math.sin(Math.toRadians(vAngle-ANGLE_SPAN)));
	        		
	        		xozLength=scale*RADIUS*Math.cos(Math.toRadians(vAngle));
	        		float x4=(float)(xozLength*Math.cos(Math.toRadians(hAngle-ANGLE_SPAN)));
	        		float z4=(float)(xozLength*Math.sin(Math.toRadians(hAngle-ANGLE_SPAN)));
	        		float y4=(float)(scale*RADIUS*Math.sin(Math.toRadians(vAngle)));   
	        		
	        		//构建第一三角形
	        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
	        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
	        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);        		
	        		//构建第二三角形
	        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
	        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
	        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
	        		      		
	        	}
	        } 	
	        
	        vCount=alVertix.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
	    	
	        //将alVertix中的坐标值转存到一个int数组中
	        float vertices[]=new float[vCount*3];
	    	for(int i=0;i<alVertix.size();i++)
	    	{
	    		vertices[i]=alVertix.get(i);
	    	}
	        
	        //创建绘制顶点数据缓冲
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
	        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mVertexBuffer.position(0);//设置缓冲区起始位置     
	                
	        //创建顶点法向量数据缓冲
	        //vertices.length*4是因为一个float四个字节
	        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
	        nbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mNormalBuffer = vbb.asFloatBuffer();//转换为int型缓冲
	        mNormalBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mNormalBuffer.position(0);//设置缓冲区起始位置	        
	    }

	    public void drawSelf(GL10 gl)
	    {	        
	    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//指定顶点缓冲
			
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//打开法向量缓冲
			gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);//指定法向量缓冲
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//关闭缓冲
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	    }
	}
	
	private class Cube 
	{
		private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
		private FloatBuffer mTextureBuffer;	//纹理坐标数据缓冲
		public float mOffsetX;
		public float mOffsetY;	//立方体大小
		int vCount;//顶点数量
		public Cube(float scale)
		{
			vCount=30;
			float[] verteices=
			{
					
					//顶面
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					
					//后面
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					
					//前面
//					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
//					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
//					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
//					
//					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
//					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
//					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					//下面
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					
					//左面
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					
					-scale*LENGTH,HEIGHT*scale,-scale*WIDTH,
					-scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					-scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					
					//右面
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					
					scale*LENGTH,HEIGHT*scale,scale*WIDTH,
					scale*LENGTH,-HEIGHT*scale,-scale*WIDTH,
					scale*LENGTH,HEIGHT*scale,-scale*WIDTH
							
			};
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float[] textureCoors=new float[vCount*2];
			for(int i=0;i<vCount/6;i++)//个顶点纹理坐标
			{
				textureCoors[i*12]=0.5f;
				textureCoors[(i*12)+1]=0;
				
				textureCoors[(i*12)+2]=0.5f;
				textureCoors[(i*12)+3]=0.75f;
				
				textureCoors[(i*12)+4]=1;
				textureCoors[(i*12)+5]=0.75f;
				
				textureCoors[(i*12)+6]=0.5f;
				textureCoors[(i*12)+7]=0;
				
				textureCoors[(i*12)+8]=1;
				textureCoors[(i*12)+9]=0.75f;
				
				textureCoors[(i*12)+10]=1;
				textureCoors[(i*12)+11]=0f;

			}
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建顶点坐标数据缓冲
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
			mTextureBuffer.position(0);//设置缓冲区起始位置		
		}
		
		public void drawSelf(GL10 gl,int texId)
		{
			gl.glRotatef(mOffsetX, 1, 0, 0);
			gl.glRotatef(mOffsetY, 0, 1, 0);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数组
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			
		}
	}
	
	private class Texture
	{
		private FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
		private FloatBuffer mTextureBuffer;	//纹理坐标数据缓冲
		public float mOffsetX;
		public float mOffsetY;	
		int vCount;//顶点数量	
		
		public Texture(float scale)
		{
			vCount=6;
			float [] verteices={
				-scale*LENGTH,scale*HEIGHT,0,
				-scale*LENGTH,-scale*HEIGHT,0,
				scale*LENGTH,-scale*HEIGHT,0,
				
				-scale*LENGTH,scale*HEIGHT,0,
				scale*LENGTH,-scale*HEIGHT,0,
				scale*LENGTH,scale*HEIGHT,0
			};
			
			ByteBuffer vbb=ByteBuffer.allocateDirect(verteices.length*4); //创建顶点坐标数据缓冲
			vbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mVertexBuffer=vbb.asFloatBuffer();//转换为float型缓冲
			mVertexBuffer.put(verteices);//向缓冲区中放入顶点坐标数据
			mVertexBuffer.position(0);//设置缓冲区起始位置
			
			float[] textureCoors={
				0,0.75f,0,1,0.75f,1,
				0,0.75f,0.75f,1,0.75f,0.75f
			};
			
			ByteBuffer tbb=ByteBuffer.allocateDirect(textureCoors.length*4);//创建顶点坐标数据缓冲
			tbb.order(ByteOrder.nativeOrder());//设置字节顺序
			mTextureBuffer=tbb.asFloatBuffer();//转换为float型缓冲
			mTextureBuffer.put(textureCoors);//向缓冲区中放入顶点坐标数据
			mTextureBuffer.position(0);//设置缓冲区起始位置
			
		}
		
		public void drawSelf(GL10 gl,int texId)
		{
			gl.glRotatef(mOffsetX, 1, 0, 0);
			gl.glRotatef(mOffsetY, 0, 1, 0);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//允许使用纹理数组
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//指定纹理数组
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);//绑定纹理
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭纹理数组
			gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
			
		}
	}
	
	private class DrawCylinder
	{
		private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
		private FloatBuffer myTexture;//纹理缓冲
		int vCount;//顶点数量
		
		public float mAngleX;
		public float mAngleY;
		public float mAngleZ;
		
		public DrawCylinder(float length,float circle_radius,float degreespan,int col)
		{			
			float collength=(float)length/col;//圆柱每块所占的长度
			int spannum=(int)(360.0f/degreespan);
			
			ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
			
			for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//循环行
			{
				for(int j=0;j<col;j++)//循环列
				{
					float x1 =(float)(j*collength-length/2);
					float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
					float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));

					float x2 =(float)(j*collength-length/2);
					float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x3 =(float)((j+1)*collength-length/2);
					float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
					float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
					
					float x4 =(float)((j+1)*collength-length/2);
					float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
					float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
					
					val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
					val.add(x2);val.add(y2);val.add(z2);
					val.add(x4);val.add(y4);val.add(z4);
					
					val.add(x2);val.add(y2);val.add(z2);
					val.add(x3);val.add(y3);val.add(z3);
					val.add(x4);val.add(y4);val.add(z4);
				}
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
			float[] textures=generateTexCoor(col,spannum);
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
			tbb.order(ByteOrder.nativeOrder());
			myTexture=tbb.asFloatBuffer();
			myTexture.put(textures);
			myTexture.position(0);
		}
		
		public void drawSelf(GL10 gl,int textureId)
		{
			gl.glRotatef(mAngleX, 1, 0, 0);//旋转
			gl.glRotatef(mAngleY, 0, 1, 0);
			gl.glRotatef(mAngleZ, 0, 0, 1);
			
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
			
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		
		
		//自动切分纹理产生纹理数组的方法
	    public float[] generateTexCoor(int bw,int bh)
	    {
	    	float[] result=new float[bw*bh*6*2]; 
	    	float sizeh=0.5f/bh;//行数
	    	float sizew=0.125f/bw;//列数
	    	int c=0;
	    	for(int i=0;i<bh;i++)
	    	{
	    		for(int j=0;j<bw;j++)
	    		{
	    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
	    			float t=i*sizeh;
	    			float s=j*sizew;    	
	    			
	    			result[c++]=s;
	    			result[c++]=t;
	    		 
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    			   			 
	    			result[c++]=s;
	    			result[c++]=t+sizeh;
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t+sizeh;   
	    			
	    			result[c++]=s+sizew;
	    			result[c++]=t;
	    		}
	    	}
	    	return result;
	    }
	}
	
	//初始化灯
	private void initLight(GL10 gl,float r,float g,float b,float a)
	{    
        gl.glEnable(GL10.GL_LIGHTING);//允许光照    
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={1f*r,1f*g,1f*b,1.0f*a};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置
        float[] diffuseParams={1f*r,1f*g,1f*b,1.0f*a};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={0f*r,0f*g,0f*b,1.0f*a};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0); 
	}
	
	//关闭灯
	private void closeLight(GL10 gl)
	{
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glDisable(GL10.GL_LIGHTING);
	}
	
	//初始化材质
	private void initMaterial(GL10 gl,float r,float g,float b,float a)
	{
        //环境光
        float ambientMaterial[] = {1*r, 1*g, 1*b, 1.0f*a};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {1*r, 1*g, 1*b, 1.0f*a};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {0.5f*r, 0.5f*g, 0.5f*b, 1.0f*a};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
	
	class LightTurn extends Thread
	{
		public LightTurn()
		{		 
		}
		
		@Override
		public void run()  
		{ 
			while(flag) 
			{ 
				try{
					sleep(2400);
					count+=1;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				if(count>1)
				{					
					count=2;
					flag=false;//交通灯停止播放
				}
				
			}
		}
	}
}
