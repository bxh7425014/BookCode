package net.blogjava.mobile;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class MyRender implements Renderer
{

	int one = 0x10000;
	// 三角形的3个顶点
	private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]
	{ 0, one, 0, // 上顶点
			-one, -one, 0, // 左下顶点
			one, -one, 0, }); // 右下顶点
	// 正方形的4个顶点
	private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]
	{ one, one, 0, // 右上角顶点
			-one, one, 0, // 左上角顶点
			one, -one, 0, // 右下角顶点
			-one, -one, 0 }); // 左下角顶点
	private IntBuffer quaterBuffer1 = IntBuffer.wrap(new int[]
	{ one, one, 0, // 右上角顶点
			-one, one, 0,-one, -one, 0, // 左上角顶点
			// 右下角顶点
			one, -one, 0 }); // 左下角顶点

	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 允许设置顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		// 左移 1.5 单位，并移入屏幕 6.0
		gl.glTranslatef(1.5f, 0.0f, -6.0f);

		// 设置三角形的顶点坐标
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
		// 绘制三角形
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();

		// 左移 2.0 单位，并移入屏幕 6.0
		gl.glTranslatef(-2.0f, 0.0f, -6.0f);
		// 设置正方形的顶点坐标
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
		// 绘制正方形
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		// gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		float ratio = (float) width / height;
		// 设置OpenGL场景的大小
		gl.glViewport(0, 0, width, height);
		// 设置投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// 重置投影矩阵
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
	}

}
