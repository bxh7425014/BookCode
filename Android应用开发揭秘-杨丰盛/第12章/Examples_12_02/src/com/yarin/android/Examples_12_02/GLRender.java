package com.yarin.android.Examples_12_02;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	 int one = 0x10000;
		
	 //三角形三个顶点
	 private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]{
			0,one,0,		//上顶点
			-one,-one,0,    //坐下点
			one,-one,0,});  //右下点
	 //正方形的4个顶点
	 private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]{
				one,one,0,
				-one,one,0,
				one,-one,0,
				-one,-one,0});
	 
	 //三角形的顶点颜色值(r,g,b,a)
	 private IntBuffer colorBuffer = IntBuffer.wrap(new int[]{
			 one,0,0,one,
			 0,one,0,one,
			 0,0,one,one,
	 });
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		
		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		
		//设置定点数组
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//设置颜色数组
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		// 设置三角形顶点
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
		//绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
		/* 渲染正方形 */
		// 设置当前色为蓝色
	    gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
	    
		// 重置当前的模型观察矩阵
	    gl.glLoadIdentity();
	    
	    // 左移 1.5 单位，并移入屏幕 6.0
	    gl.glTranslatef(1.5f, 0.0f, -6.0f);
	    //设置和绘制正方形
	    gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
	    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

	    //取消顶点数组
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// TODO Auto-generated method stub
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

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// 启用阴影平滑
		gl.glShadeModel(GL10.GL_SMOOTH);
		
		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);
		
		// 设置深度缓存
		gl.glClearDepthf(1.0f);							
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);						
		// 所作深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);							
		
		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	}
}

