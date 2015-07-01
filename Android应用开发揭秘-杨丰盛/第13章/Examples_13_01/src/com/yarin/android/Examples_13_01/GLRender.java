package com.yarin.android.Examples_13_01;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;
import android.view.KeyEvent;

public class GLRender implements Renderer
{
	public static final int	num	= 50;					// 星星数目
	boolean					twinkle = true;					// 闪烁的星星
	boolean					key;
	public Star[]			star		= new Star[num];	// 存放星星的数组
	float					zoom		= -10.0f;				// 星星离观察者的距离
	float					tilt		= 90.0f;				// 星星的倾角
	float					spin;								// 闪烁星星的自转	
	int one = 0x10000;
	Random random = new Random();
	int						texture;							// 纹理
	
	IntBuffer coord = IntBuffer.wrap(new int[]{
			0,0,
			one,0,
			one,one,
			0,one,
	});
	IntBuffer vertexs = IntBuffer.wrap(new int[]{
			-one,-one,0,
			one,-one,0,
			one,one,0,
			-one,one,0,
	});
	ByteBuffer indices = ByteBuffer.wrap(new byte[]{
			1, 0, 2, 3
	});
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// 清除屏幕和深度缓存
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);		// 选择纹理

		for (int i=0; i<num; i++)				// 循环设置所有的星星
		{
			gl.glLoadIdentity();				// 绘制每颗星星之前，重置模型观察矩阵
			gl.glTranslatef(0.0f,0.0f,zoom);			// 深入屏幕里面
			gl.glRotatef(tilt,1.0f,0.0f,0.0f);			// 倾斜视角
			gl.glRotatef(star[i].angle,0.0f,1.0f,0.0f);	// 旋转至当前所画星星的角度
			gl.glTranslatef(star[i].dist,0.0f,0.0f);	// 沿X轴正向移动
			gl.glRotatef(-star[i].angle,0.0f,1.0f,0.0f);	// 取消当前星星的角度
			gl.glRotatef(-tilt,1.0f,0.0f,0.0f);		// 取消屏幕倾斜

			//设置定点数组
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			//设置颜色数组
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			if (twinkle)					// 启用闪烁效果
			{
				// 使用byte型数值指定一个颜色
				gl.glColor4f((float)star[(num-i)-1].r/255.0f,(float)star[(num-i)-1].g/255.0f,(float)star[(num-i)-1].b/255.0f,1.0f);
				gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexs);
				gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, coord);
				
				{
					coord.position(0);
					vertexs.position(0);
					indices.position(0);
					
					gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
				}
			    //绘制结束
			    gl.glFinish();
			}

			gl.glRotatef(spin,0.0f,0.0f,1.0f);			// 绕z轴旋转星星
			
			// 使用byte型数值指定一个颜色
			gl.glColor4f((float)star[(num-i)-1].r/255.0f,(float)star[(num-i)-1].g/255.0f,(float)star[(num-i)-1].b/255.0f,1.0f);
			gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexs);
			gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, coord);
			
			{
				coord.position(0);
				vertexs.position(0);
				indices.position(0);
				
				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
			}
		    //绘制正方形结束
		    gl.glFinish();
		    
		    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		    //取消顶点数组
		    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		    
			spin+=0.01f;					// 星星的公转
			star[i].angle+=(float)(i)/(float)num;		// 改变星星的自转角度
			star[i].dist-=0.01f;				// 改变星星离中心的距离

			if (star[i].dist<0.0f)			// 星星到达中心了么
			{
				star[i].dist+=5.0f;			// 往外移5个单位
				star[i].r=random.nextInt(256);		// 赋一个新红色分量
				star[i].g=random.nextInt(256);		// 赋一个新绿色分量
				star[i].b=random.nextInt(256);		// 赋一个新蓝色分量
			}

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

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glShadeModel(GL10.GL_SMOOTH);						// 启用阴影平滑
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);					// 黑色背景
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);			// 告诉系统对透视进行修正
		
		IntBuffer buffer = IntBuffer.allocate(1);
		// 创建一个纹理
		gl.glGenTextures(1, buffer);
		texture = buffer.get();
		// 创建一个线性滤波纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap, 0);
		
		
		gl.glEnable(GL10.GL_TEXTURE_2D);				// 启用纹理映射
		gl.glShadeModel(GL10.GL_SMOOTH);				// 启用阴影平滑
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);			// 黑色背景
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);	// 真正精细的透视修正
		gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE);			// 设置混色函数取得半透明效果
		gl.glEnable(GL10.GL_BLEND);					// 启用混色

		for (int i=0; i<num; i++)				// 创建循环设置全部星星
		{
			Star starTMP = new Star();
			starTMP.angle=0.0f;				// 所有星星都从零角度开始
			starTMP.dist=((float)(i)/(float)num)*5.0f;		// 计算星星离中心的距离
			starTMP.r=random.nextInt(256);			// 为star[loop]设置随机红色分量
			starTMP.g=random.nextInt(256);			// 为star[loop]设置随机红色分量
			starTMP.b=random.nextInt(256);			// 为star[loop]设置随机红色分量
			star[i] = starTMP;
		}
	}

	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		twinkle=!twinkle;
		return false;
	}
}


class Star
{
	int		r, g, b;	// 星星的颜色
	float	dist;		// 星星距离中心的距离
	float	angle	= 0.0f;// 当前星星所处的角度
}
