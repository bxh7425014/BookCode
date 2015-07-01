package com.yarin.android.Examples_13_05;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;

public class GLRender implements Renderer
{
	int one = 0x10000;
	float step = 0.4f;
	boolean key;
	boolean light = true;
	
	GL10 mGl10;
	
	float xrot, yrot;	//x,y轴旋转
	float xspeed, yspeed;//旋转的速度
	float z = -5.0f;//深入屏幕的距离
	
	int fogMode[]= { GL10.GL_EXP, GL10.GL_EXP2, GL10.GL_LINEAR };		// 雾气的模式
	int fogfilter= 2;			
	// 使用哪一种雾气
	float fogColor[]= {0.5f, 0.5f, 0.5f, 1.0f};		// 雾的颜色设为白色

	
	//定义环境光(r,g,b,a)
	FloatBuffer lightAmbient = FloatBuffer.wrap(new float[]{0.5f,0.5f,0.5f,1.0f}); 
	//定义漫射光
	FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f,1.0f});
	//光源的位置
	FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{0.0f,0.0f,2.0f,1.0f}); 
	
	//过滤的类型
	int filter = 1;
	//纹理效果
	int [] texture;
	
	IntBuffer vertices = IntBuffer.wrap(new int[]{
			-one,-one,one,
			one,-one,one,
			one,one,one,
			-one,one,one,
			
			-one,-one,-one,
			-one,one,-one,
			one,one,-one,
			one,-one,-one,
			
			-one,one,-one,
			-one,one,one,
			one,one,one,
			one,one,-one,
			
			-one,-one,-one,
			one,-one,-one,
			one,-one,one,
			-one,-one,one,
			
			one,-one,-one,
			one,one,-one,
			one,one,one,
			one,-one,one,
			
			-one,-one,-one,
			-one,-one,one,
			-one,one,one,
			-one,one,-one,
			
	});
	
	IntBuffer normals = IntBuffer.wrap(new int[]{
			0,0,one,
			0,0,one,
			0,0,one,
			0,0,one,
			
			0,0,one,
			0,0,one,
			0,0,one,
			0,0,one,
			
			0,one,0,
			0,one,0,
			0,one,0,
			0,one,0,
			
			0,-one,0,
			0,-one,0,
			0,-one,0,
			0,-one,0,
			
			one,0,0,
			one,0,0,
			one,0,0,
			one,0,0,
			
			-one,0,0,
			-one,0,0,
			-one,0,0,
			-one,0,0,
	});
	
	IntBuffer texCoords = IntBuffer.wrap(new int[]{
		one,0,0,0,0,one,one,one,	
		0,0,0,one,one,one,one,0,
		one,one,one,0,0,0,0,one,
		0,one,one,one,one,0,0,0,
		0,0,0,one,one,one,one,0,
		one,0,0,0,0,one,one,one,
	});
	
	ByteBuffer indices = ByteBuffer.wrap(new byte[]{
			0,1,3,2,
			4,5,7,6,
			8,9,11,10,
			12,13,15,14,
			16,17,19,18,
			20,21,23,22,
	});
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		
		//如果不启用GL_LIGHTING光就什么都看不见
		gl.glEnable(GL10.GL_LIGHTING);
		
		
		gl.glTranslatef(0.0f, 0.0f, z);
		//设置旋转
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

		//选择使用的纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[filter]);
		
		gl.glNormalPointer(GL10.GL_FIXED, 0, normals);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);

		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	
		//绘制四边形
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24,  GL10.GL_UNSIGNED_BYTE, indices);

	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	    //旋转角度
	    if ( key )
		{
		    xrot+=xspeed; 
		    yrot+=yspeed; 
		}
	    
    	if (!light)				// 判断是否开始光源
		{
    		gl.glDisable(GL10.GL_LIGHT1);		// 禁用一号光源
		}
		else					// 否则
		{
			gl.glEnable(GL10.GL_LIGHT1);		// 启用一号光源
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		float ratio = (float) width / height;
		//设置OpenGL场景的大小
		gl.glViewport(0, 0, width, height);
		//设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// 选择模型观察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
		// 重置模型观察矩阵
		gl.glLoadIdentity();	
	}

	public void fogMode()
	{
		mGl10.glFogf(GL10.GL_FOG_MODE, fogMode[fogfilter]);		// 设置雾气的模式
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		mGl10 = gl;
		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 设置背景的颜色为雾气的颜色
		gl.glClearColor(0.5f,0.5f,0.5f,1.0f);	
		
		gl.glFogx(GL10.GL_FOG_MODE, fogMode[fogfilter]);		// 设置雾气的模式
		gl.glFogfv(GL10.GL_FOG_COLOR, fogColor,0);			// 设置雾的颜色
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.35f);			// 设置雾的密度
		gl.glHint(GL10.GL_FOG_HINT, GL10.GL_DONT_CARE);			// 设置系统如何计算雾气
		gl.glFogf(GL10.GL_FOG_START, 5.0f);				// 雾气的开始位置
		gl.glFogf(GL10.GL_FOG_END, 6.0f);				// 雾气的结束位置
		gl.glEnable(GL10.GL_FOG);					// 使用雾气
		
		
		gl.glEnable(GL10.GL_CULL_FACE);
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		
		IntBuffer textureBuffer = IntBuffer.allocate(3);
		// 创建纹理
		gl.glGenTextures(3, textureBuffer);
		texture = textureBuffer.array();
		
		// 创建 Nearest 滤波贴图
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_NEAREST); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST); 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		
		// 创建线性滤波纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR); 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR); 
		gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST); 
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		
		gl.glClearDepthf(1.0f);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		//设置环境光
	    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);

	    //设置漫射光
	    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);

	    //设置光源的位置
	    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);
	    
	    //启用一号光源
	    gl.glEnable(GL10.GL_LIGHT1);
	}
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
			case KeyEvent.KEYCODE_DPAD_UP:
				key = true;
				xspeed=-step;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				key = true;
				xspeed=step;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				key = true;
				yspeed=-step;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				key = true;
				yspeed=step;
				break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				fogfilter++;					// 变换雾气模式
				if (fogfilter>2)					// 模式是否大于2
				{
					fogfilter=0;				// 置零
				}
				fogMode();
				break;
		}
		return false;
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		key = false;
		return false;
	}
}

