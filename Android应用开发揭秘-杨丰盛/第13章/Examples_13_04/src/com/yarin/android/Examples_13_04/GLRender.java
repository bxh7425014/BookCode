package com.yarin.android.Examples_13_04;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer
{
	int texture = -1;
				
	int	xloop;						// X轴循环变量
	int	yloop;						// Y轴循环变量

	float xrot, yrot, zrot;
	
	// 保存盒子的显示列表
	FloatBuffer boxVertices = FloatBuffer.allocate(60);
	FloatBuffer boxTexCoords= FloatBuffer.allocate(40);
	
	// 保存盒子顶部的显示列表
	FloatBuffer topVertices = FloatBuffer.allocate(12);
	FloatBuffer topTexCoords= FloatBuffer.allocate(8);
	
	float[][] boxcol = {
			{1.0f, 0.0f, 0.0f},
			{1.0f, 0.5f, 0.0f},
			{1.0f, 1.0f, 0.0f},
			{0.0f, 1.0f, 0.0f},
			{0.0f, 1.0f, 1.0f},
	};
	
	float[][] topcol= {
			{0.5f, 0.0f, 0.0f},
			{0.5f, 0.25f, 0.0f},
			{0.5f, 0.5f, 0.0f},
			{0.0f, 0.5f, 0.0f},
			{0.0f, 0.5f, 0.5f},
	};
	
	public void BuildLists(GL10 gl)
	{
		boxTexCoords.put(new float[]{1.0f, 1.0f,0.0f, 1.0f,0.0f, 0.0f,1.0f,0.0f});
		boxVertices.put(new float[]{-1.0f, -1.0f, -1.0f,1.0f, -1.0f, -1.0f,1.0f, -1.0f,  1.0f,-1.0f, -1.0f,  1.0f});
		
		boxTexCoords.put(new float[]{0.0f, 0.0f,1.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f});
		boxVertices.put(new float[]{-1.0f, -1.0f,  1.0f,1.0f, -1.0f,  1.0f,1.0f,  1.0f,  1.0f,-1.0f,  1.0f,  1.0f});
		
		boxTexCoords.put(new float[]{1.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,0.0f, 0.0f});
		boxVertices.put(new float[]{-1.0f, -1.0f, -1.0f,-1.0f,  1.0f, -1.0f,1.0f,  1.0f, -1.0f,1.0f, -1.0f, -1.0f});
		
		boxTexCoords.put(new float[]{1.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,0.0f, 0.0f});
		boxVertices.put(new float[]{1.0f, -1.0f, -1.0f,1.0f,  1.0f, -1.0f,1.0f,  1.0f,  1.0f,1.0f, -1.0f,  1.0f});
		
		boxTexCoords.put(new float[]{0.0f, 0.0f,1.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f});
		boxVertices.put(new float[]{-1.0f, -1.0f, -1.0f,-1.0f, -1.0f,  1.0f,-1.0f,  1.0f,  1.0f,-1.0f,  1.0f, -1.0f});
		
		topTexCoords.put(new float[]{0.0f, 1.0f,0.0f, 0.0f,1.0f, 0.0f,1.0f, 1.0f});
		topVertices.put(new float[]{-1.0f,  1.0f, -1.0f,-1.0f,  1.0f,  1.0f,1.0f,  1.0f,  1.0f,1.0f,  1.0f, -1.0f});
	}
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);		
		
		// 绑定纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		for (yloop=1;yloop<6;yloop++)
		{
			for (xloop=0;xloop<yloop;xloop++)
			{
				gl.glLoadIdentity();			// 重置模型变化矩阵
				
				// 设置盒子的位置
				gl.glTranslatef(1.4f+((float)(xloop)*2.8f)-((float)(yloop)*1.4f),((6.0f-(float)(yloop))*2.4f)-7.0f,-20.0f);
				
				gl.glRotatef(45.0f-(2.0f*yloop)+xrot,1.0f,0.0f,0.0f);
				
				gl.glRotatef(45.0f+yrot,0.0f,1.0f,0.0f);
				
				gl.glColor4f(boxcol[yloop-1][0], boxcol[yloop-1][1], boxcol[yloop-1][2], 1.0f);
				
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, boxVertices);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, boxTexCoords);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 4, 4);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 8, 4);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 12, 4);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 16, 4);

	            /* Select The Top Color */
				gl.glColor4f(topcol[yloop-1][0], topcol[yloop-1][1], topcol[yloop-1][2], 1.0f);
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, topVertices);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, topTexCoords);
				gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			}
		}
		
	    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    
	    xrot+=0.5f;
	    yrot+=0.6f; 
	    zrot+=0.3f; 
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
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 30);
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
		
		gl.glEnable(GL10.GL_CULL_FACE);
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
		
		IntBuffer intBuffer = IntBuffer.allocate(1);
		// 创建纹理
		gl.glGenTextures(1, intBuffer);
		texture = intBuffer.get();
		// 设置要使用的纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
		//生成纹理
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		// 创建显示列表
		BuildLists(gl);		
		
		gl.glEnable(GL10.GL_LIGHT0);					// 使用默认的0号灯
		//gl.glEnable(GL10.GL_LIGHTING);					// 使用灯光
		gl.glEnable(GL10.GL_COLOR_MATERIAL);				// 使用颜色材质

	}
}

