package net.blogjava.mobile.light;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;


public class MyRender implements Renderer
{
	int one = 0x10000;	
	boolean key;
	public boolean light = true;

	float xrot, yrot; // x,y轴旋转
	float xspeed = 0.6f, yspeed = 0.6f;// 旋转的速度
	float z = -3.0f;// 深入屏幕的距离

	// 定义环境光(R、G、B、A)
	FloatBuffer lightAmbient = FloatBuffer.wrap(new float[]
	{ 0.5f, 0.5f, 0.5f, 1.0f });
	// 定义漫射光
	FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]
	{ 1.0f, 1.0f, 1.0f, 1.0f });
	// 光源的位置
	FloatBuffer lightPosition = FloatBuffer.wrap(new float[]
	{ 0.3f, 0.0f, 2.0f, 1.0f });

	// 过滤的类型
	int filter = 1;
	// 纹理效果
	int[] texture;

	IntBuffer vertices = IntBuffer.wrap(new int[]
	{ -one, -one, one, one, -one, one, one, one, one, -one, one, one,

	-one, -one, -one, -one, one, -one, one, one, -one, one, -one, -one,

	-one, one, -one, -one, one, one, one, one, one, one, one, -one,

	-one, -one, -one, one, -one, -one, one, -one, one, -one, -one, one,

	one, -one, -one, one, one, -one, one, one, one, one, -one, one,

	-one, -one, -one, -one, -one, one, -one, one, one, -one, one, -one,

	});

	IntBuffer normals = IntBuffer.wrap(new int[]
	{ 0, 0, one, 0, 0, one, 0, 0, one, 0, 0, one,

	0, 0, one, 0, 0, one, 0, 0, one, 0, 0, one,

	0, one, 0, 0, one, 0, 0, one, 0, 0, one, 0,

	0, -one, 0, 0, -one, 0, 0, -one, 0, 0, -one, 0,

	one, 0, 0, one, 0, 0, one, 0, 0, one, 0, 0,

	-one, 0, 0, -one, 0, 0, -one, 0, 0, -one, 0, 0, });

	IntBuffer texCoords = IntBuffer.wrap(new int[]
	{ one, 0, 0, 0, 0, one, one, one, 0, 0, 0, one, one, one, one, 0, one, one,
			one, 0, 0, 0, 0, one, 0, one, one, one, one, 0, 0, 0, 0, 0, 0, one,
			one, one, one, 0, one, 0, 0, 0, 0, one, one, one, });

	ByteBuffer indices = ByteBuffer.wrap(new byte[]
	{ 0, 1, 3, 2, 4, 5, 7, 6, 8, 9, 11, 10, 12, 13, 15, 14, 16, 17, 19, 18, 20,
			21, 23, 22, });

	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();

		// 必须启动GL_LIGHTING光，否则什么都看不见
		gl.glEnable(GL10.GL_LIGHTING);

		gl.glTranslatef(0.0f, 0.0f, z);
		// 设置旋转
		gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

		// 选择使用的纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[filter]);

		gl.glNormalPointer(GL10.GL_FIXED, 0, normals);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertices);
		gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);

		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// 绘制四边形
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24, GL10.GL_UNSIGNED_BYTE,
				indices);

		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		// 旋转角度

		xrot += xspeed;
		yrot += yspeed;
		// 是否打开光源
		if (!light) 
		{
			gl.glDisable(GL10.GL_LIGHT1); // 关闭一号光源
		}
		else
		{
			gl.glEnable(GL10.GL_LIGHT1); // 启用一号光源
		}

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
		// 告诉系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// 黑色背景
		gl.glClearColor(0, 0, 0, 0);

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
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST); // ( NEW )
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST); // ( NEW )
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);

		// 创建线性滤波纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR); // ( NEW )
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR); // ( NEW )
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST); // ( NEW )
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR); // ( NEW )
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);

		gl.glClearDepthf(1.0f);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		// 设置环境光
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);

		// 设置漫射光
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);

		// 设置光源的位置
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);

		// 启用一号光源
		gl.glEnable(GL10.GL_LIGHT1);
		
	}
    
}
