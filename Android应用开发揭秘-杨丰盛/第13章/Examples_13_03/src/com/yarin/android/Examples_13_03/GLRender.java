package com.yarin.android.Examples_13_03;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	float vertex[][][]=new float[45][45][3];	// Points网格顶点数组(45,45,3)
	int wiggle_count = 0;						// 指定旗形波浪的运动速度
	float hold;							// 临时变量
	float xrot, yrot, zrot;
	int texture = -1;
	FloatBuffer texCoord = FloatBuffer.allocate(8);
	FloatBuffer points = FloatBuffer.allocate(12);
	@Override
	public void onDrawFrame(GL10 gl)
	{
		int x, y;						// 循环变量
		float float_x, float_y, float_xb, float_yb;		// 用来将旗形的波浪分割成很小的四边形

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	// 清除屏幕和深度缓冲
		gl.glLoadIdentity();					// 重置当前的模型观察矩阵

		gl.glTranslatef(0.0f,0.0f,-12.0f);				// 移入屏幕12个单位

		gl.glRotatef(xrot,1.0f,0.0f,0.0f);				// 绕 X 轴旋转
		gl.glRotatef(yrot,0.0f,1.0f,0.0f);				// 绕 Y 轴旋转
		gl.glRotatef(zrot,0.0f,0.0f,1.0f);				// 绕 Z 轴旋转

	    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, points);
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoord);
	    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);		// 选择纹理
		
		for( x = 0; x < 44; x++ ){
			for( y = 0; y < 44; y++) {
				 float_x = (float)(x)/44.0f;		// 生成X浮点值
				 float_y = (float)(y)/44.0f;		// 生成Y浮点值
				 float_xb = (float)(x+1)/44.0f;		// X浮点值+0.0227f
				 float_yb = (float)(y+1)/44.0f;		// Y浮点值+0.0227f
		            
				texCoord.clear();
				texCoord.put(float_x);
				texCoord.put(float_y);
				texCoord.put(float_x);
				texCoord.put(float_yb);
				texCoord.put(float_xb);
				texCoord.put(float_yb);
				texCoord.put(float_xb);
				texCoord.put(float_y);
				
				points.clear();
				points.put(vertex[x][y][0]);
				points.put(vertex[x][y][1]);
				points.put(vertex[x][y][2]);
				
				points.put(vertex[x][y+1][0]);
				points.put(vertex[x][y+1][1]);
				points.put(vertex[x][y+1][2]);
				
				points.put(vertex[x+1][y+1][0]);
				points.put(vertex[x+1][y+1][1]);
				points.put(vertex[x+1][y+1][2]);
				
				points.put(vertex[x+1][y][0]);
				points.put(vertex[x+1][y][1]);
				points.put(vertex[x+1][y][2]);
				
				///////////////
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			}
		}
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if( wiggle_count == 2 )					// 用来降低波浪速度(每隔2帧一次)
		{
//			for( y = 0; y < 45; y++ )			// 沿Y平面循环
//			{
//				hold=vertex[0][y][2];			// 存储当前左侧波浪值
//				for( x = 0; x < 44; x++)		// 沿X平面循环
//				{
//					// 当前波浪值等于其右侧的波浪值
//					vertex[x][y][2] = vertex[x+1][y][2];
//				}
//				vertex[44][y][2]=hold;			// 刚才的值成为最左侧的波浪值
//			}
			for( x = 0; x < 45; x++ )			// 沿Y平面循环
			{
				hold=vertex[x][0][2];			// 存储当前左侧波浪值
				for( y = 0; y < 44; y++)		// 沿X平面循环
				{
					// 当前波浪值等于其右侧的波浪值
					vertex[x][y][2] = vertex[x][y+1][2];
				}
				vertex[x][44][2]=hold;			// 刚才的值成为最左侧的波浪值
			}
			wiggle_count = 0;				// 计数器清零
		}
		wiggle_count++;						// 计数器加一
		
//		xrot+=0.3f;						// X 轴旋转
//		yrot+=0.2f;						// Y 轴旋转
//		zrot+=0.4f;						// Z 轴旋转
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		float ratio = (float) width / (float)height;
		//设置OpenGL场景的大小
		gl.glViewport(0, 0, width, height);
		//设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//重置投影矩阵
		gl.glLoadIdentity();
		// 设置视口的大小
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 15);
		// 选择模型观察矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
		// 重置模型观察矩阵
		gl.glLoadIdentity();	
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);
		
		
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		
		//启用纹理映射
		gl.glClearDepthf(1.0f);
		//深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);
		//精细的透视修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		//允许2D贴图,纹理
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glEnable(GL10.GL_LEQUAL);
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		// 创建纹理
		gl.glGenTextures(1, intBuffer);
		texture = intBuffer.get();
		// 设置要使用的纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
		//生成纹理
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		// 线形滤波
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		// 沿X平面循环
		for(int x=0; x<45; x++)
		{
			// 沿Y平面循环
			for(int y=0; y<45; y++)
			{
//				// 向表面添加波浪效果
//				vertex[x][y][0]=((float)x/9.0f)-2.5f;
//				vertex[x][y][1]=(((float)y/9.0f)-2.5f);
//				vertex[x][y][2]=(float)(Math.sin(((((float)x/9.0f)*72.0f)/360.0f)*3.141592654*2.0f));
				
				// 向表面添加波浪效果
				vertex[x][y][0]=((float)x/5.0f)-4.5f;
				vertex[x][y][1]=(((float)y/5.0f)-4.5f);
				vertex[x][y][2]=(float)(Math.sin(((((float)y/5.0f)*40.0f)/360.0f)*3.141592654*2.0f));
			}
		}

	}

}

