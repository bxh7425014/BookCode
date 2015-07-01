package net.blogjava.mobile.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class MyRender implements Renderer
{
	float xrot, yrot, zrot;
	int texture = -1;
	int one = 0x10000;
	
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
		
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		
		//设置3个方向的旋转
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		//纹理和四边形对应的顶点
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);

		//绘制
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24,  GL10.GL_UNSIGNED_BYTE, indices);

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
		
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		gl.glLoadIdentity();
		
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	
		gl.glMatrixMode(GL10.GL_MODELVIEW);	
	
		gl.glLoadIdentity();	
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(0, 0, 0, 0);
		
		gl.glEnable(GL10.GL_CULL_FACE);
	
		gl.glShadeModel(GL10.GL_SMOOTH);
	
		gl.glEnable(GL10.GL_DEPTH_TEST);
	
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
		
		// 线形滤波
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
	}

}

