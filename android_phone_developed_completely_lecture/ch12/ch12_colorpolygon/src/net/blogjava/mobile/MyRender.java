package net.blogjava.mobile;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class MyRender implements Renderer
{

	/*
	 * private FloatBuffer quaterBuffer = FloatBuffer.wrap(new float[] { 0.5f,
	 * 0.5f, 0.0f, -0.5f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, -0.5f, -0.5f, 0.0f });
	 */

	private FloatBuffer triangleBuffer = FloatBuffer.wrap(new float[]
	{ -0.15f, 0.0f, 0.0f, 0.0f, 0.3f, 0.0f, 0.15f, 0.0f, 0.0f });

	// GL_TRIANGLE_STRIP（a,b,c b,c,d）
	private FloatBuffer quaterBuffer = FloatBuffer
			.wrap(new float[]
			{ -0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, 0.3f, -0.3f, 0.0f, -0.3f,
					-0.3f, 0 });

	// GL_TRIANGLE_FAN（a,b,c a,c,d）
	private FloatBuffer quaterBuffer1 = FloatBuffer
			.wrap(new float[]
			{ -0.3f, 0.3f, 0.0f, 0.3f, 0.3f, 0.0f, -0.3f, -0.3f, 0, 0.3f,
					-0.3f, 0.0f });

	/*
	 * private FloatBuffer triangleBuffer = FloatBuffer.wrap(new float[] {
	 * -0.65f, 0.5f, 0.0f, -0.5f, 0.8f, 0.0f, -0.35f, 0.5f, 0.0f });
	 */
	// 定义多边形的顶点
	private FloatBuffer polygonTriangleBuffer1 = FloatBuffer.wrap(new float[]
	{ -0.2f, 0.2f, 0.0f, 0.0f, 0.3f, 0.0f, 0.2f, 0.2f, 0.0f });
	private FloatBuffer polygonQuaterBuffer = FloatBuffer
			.wrap(new float[]
			{ -0.2f, 0.2f, 0.0f, 0.2f, 0.2f, 0.0f, -0.2f, 0.0f, 0.0f, 0.2f,
					0.0f, 0.0f });
	private FloatBuffer polygonTriangleBuffer2 = FloatBuffer.wrap(new float[]
	{ -0.2f, 0.0f, 0.0f, -0.4f, -0.2f, 0.0f, 0.0f, -0.3f, 0.0f });
	private FloatBuffer polygonTriangleBuffer3 = FloatBuffer.wrap(new float[]
	{ 0.2f, 0.0f, 0.0f, 0.4f, -0.2f, 0.0f, 0.0f, -0.3f, 0.0f });
	private FloatBuffer polygonTriangleBuffer4 = FloatBuffer.wrap(new float[]
	{ -0.2f, 0.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, -0.3f, 0.0f });
	
	
	 int one = 0x10000;		
	 private IntBuffer colorBuffer = IntBuffer.wrap(new int[]{
			 one,0,0,one,
			 0,one,0,one,
			 0,0,one,one,
	 });
	@Override
	public void onDrawFrame(GL10 gl)
	{
  
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		
		
		gl.glLoadIdentity();
		gl.glTranslatef(-0.8f, 0.6f, 0.0f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		// 由于给的顶点顺序错误，无法画出矩形
		gl.glLoadIdentity();
		gl.glTranslatef(-0.2f, 0.6f, 0.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quaterBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

		// 画矩形
		
		gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
		gl.glLoadIdentity();
		gl.glTranslatef(0.6f, 0.6f, 0.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quaterBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);

		// 画多边形
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, -0.4f, 0.0f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 画多边形中的第1个三角形
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, polygonTriangleBuffer1);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		// 画多边形中的矩形
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, polygonQuaterBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		// 画多边形中的第2个三角形
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, polygonTriangleBuffer2);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		// 画多边形中的第3个三角形
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, polygonTriangleBuffer3);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		// 画多边形中的第4个三角形
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, polygonTriangleBuffer4);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// GLU.gluLookAt(gl, 0.0f, 0.0f, 5f, 0.6f, 0.8f, 0.0f, 0.0f, -1f, 0.0f);
		gl.glViewport(0, 0, width, height);

		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{

	}

}
