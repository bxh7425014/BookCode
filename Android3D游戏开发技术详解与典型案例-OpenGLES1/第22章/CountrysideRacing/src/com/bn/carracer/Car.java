package com.bn.carracer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.carracer.Constant.*;

public class Car
{
	public final static float RADIUS=DrawMiniMap.MAP_LENGHT/MAP_LEVEL1[0].length/2;//球半径
	public final static float ANGLE_SPAN=18;//球切割尺寸
	
	int count=0;//计数
	float scale;//尺寸
	DrawBall car;//小车
	DrawBall carBig;//大车
	
	static boolean flag=true;//大小车控制线程标志位
	
	static LightTurn lt;//控制大小车绘制的线程
	public Car(float scale)
	{
		this.scale=scale;
		car=new DrawBall(1*scale);
		carBig=new DrawBall(2*scale);
		lt=new LightTurn();
	}
	public void drawSelf(GL10 gl)
	{
		//count能被2整除就绘制大车，否则绘制小车
		if(count%2==0) 
        {
        	gl.glPushMatrix();
    		float[] directionParams={0,35f,35f,0};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);
    		initMaterial(gl,1,0,0,1);//初始化材质
//    		gl.glTranslatef(-MiniMap.MAP_LENGHT+3*scale*MiniMap.WIDTH+scale*RADIUS, 
//    				MiniMap.MAP_HEIGHT-3*scale*MiniMap.HEIGHT-scale*RADIUS, 0);
    		initLight(gl,1,1,1,1);//开灯
    		car.drawSelf(gl);	
    		closeLight(gl);//关灯
    		gl.glPopMatrix();
        }
        else
        {
        	gl.glPushMatrix();
    		float[] directionParams={0,35f,35f,0};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);
    		initMaterial(gl,1,0,0,1);//初始化材质
//    		gl.glTranslatef(-MiniMap.MAP_LENGHT+3*scale*MiniMap.WIDTH+scale*RADIUS, 
//    				MiniMap.MAP_HEIGHT-3*scale*MiniMap.HEIGHT-scale*RADIUS, 0);
    		initLight(gl,1,1,1,1);//开灯
    		carBig.drawSelf(gl);	
    		closeLight(gl);//关灯
    		gl.glPopMatrix();
        }
	}
	
	//控制大小车绘制的线程
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
					count+=1;
					sleep(500);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	//初始化灯
	private void initLight(GL10 gl,float r,float g,float b,float a)
	{    
	    gl.glEnable(GL10.GL_LIGHTING);//允许光照    
	    gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
	    
	    //环境光设置
	    float[] ambientParams={0.2f*r,0.2f*g,0.2f*b,1.0f*a};//光参数 RGBA
	    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
	    
	    //散射光设置
	    float[] diffuseParams={1f*r,1f*g,1f*b,1.0f*a};//光参数 RGBA
	    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
	    
	    //反射光设置
	    float[] specularParams={1f*r,1f*g,1f*b,1.0f*a};//光参数 RGBA
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
	    float specularMaterial[] = {1*r, 1*g, 1*b, 1.0f*a};
	    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
	    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
	
	private class DrawBall
	{
		private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
	    private FloatBuffer mNormalBuffer;//顶点纹理数据缓冲
	    int vCount=0;//顶点数量
	    public DrawBall(float scale) 
	    {    	
	    	ArrayList<Float> alVertix=new ArrayList<Float>();//存放顶点坐标的ArrayList
	    	
	        for(float vAngle=90;vAngle>-90;vAngle=vAngle-ANGLE_SPAN)//垂直方向angleSpan度一份
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
}
