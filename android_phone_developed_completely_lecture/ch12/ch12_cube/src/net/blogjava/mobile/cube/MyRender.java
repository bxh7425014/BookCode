package net.blogjava.mobile.cube;

import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;

public class MyRender implements Renderer
{
	
	float rotateTri, rotateQuad;
	
	 int one = 0x10000;
		
		private IntBuffer colorBufferForQuad = IntBuffer.wrap(new int[]{
				
				one/2,one,0,one,
				one/2,one,0,one,
				one/2,one,0,one,
				one/2,one,0,one,
				 
				 one, one/2, 0, one,
				 one, one/2, 0, one,
				 one, one/2, 0, one,
				 one, one/2, 0, one,
				 one,one,0,one,
				 one,one,0,one,
				 one,one,0,one,
				 one,one,0,one,
				 one,0,0,one,
				 one,0,0,one,
				 one,0,0,one,
				 one,0,0,one,
				 
				 
				 0,0,one,one,
				 0,0,one,one,
				 0,0,one,one,
				 0,0,one,one,
				 
				 one,0,one,one,
				 one,0,one,one,
				 one,0,one,one,
				 one,0,one,one,
		});
		
		 private IntBuffer colorBuffer = IntBuffer.wrap(new int[]{				
				 0,one,0,one,
				 one,0,0,one,
				 0,0,one,one,	
				 
				 0,one,0,one,
				 one,0,0,one,
				 0,0,one,one,
				 
				 0,one,0,one,
				 one,0,0,one,
				 0,0,one,one,
				 
				 0,one,0,one,
				 one,0,0,one,
				 0,0,one,one,
		 });
		 
		 
		 private IntBuffer triggerBuffer = IntBuffer.wrap(new int[]{
				0,one,0,
				-one,-one,0,
				one,-one,one,
				
				0,one,0,
				one,-one,one,
				one,-one,-one,
				
				0,one,0,
				one,-one,-one,
				-one,-one,-one,
				
				0,one,0,
				-one,-one,-one,
				-one,-one,one
		 
		 });
		 private IntBuffer quaterBuffer = IntBuffer.wrap(new int[]{
					one,one,-one,
					-one,one,-one,
					one,one,one,
					-one,one,one,
					
					one,-one,one,
					-one,-one,one,
					one,-one,-one,
					-one,-one,-one,
					
					one,one,one,
					-one,one,one,
					one,-one,one,
					-one,-one,one,
					
					one,-one,-one,
					-one,-one,-one,
					one,one,-one,
					-one,one,-one,
					
					-one,one,one,
					-one,one,-one,
					-one,-one,one,
					-one,-one,-one,
					
					one, one, -one,
					one, one, one,
					one, -one, -one,
					one, -one, one,
		 });
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// 清除屏幕和深度缓存
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 重置当前的模型观察矩阵
		gl.glLoadIdentity();
		// 右移 2.0个 单位，并移入屏幕 6.0
		gl.glTranslatef(2.0f, 0.0f, -6.0f);
		 //旋转立方锥
		gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
		
	    // 允许设置顶点
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 允许颜色渲染
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
	
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
		//绘制四棱锥
	    for(int i=0; i<4; i++)
	    {
	    	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*3, 3);
	    }
		
		// 开始处理正方体 
	    
	    gl.glLoadIdentity();
	    
	    // 左移 2.0个 单位，并移入屏幕 6.0
	    gl.glTranslatef(-2.0f, 0.0f, -6.0f);
	    
	    //设置旋转
	    gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0.0f);
	    
	    
	    gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBufferForQuad);
	    gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);

	    //  开始绘制立方体
	    for(int i=0; i<6; i++)
	    {
	    	gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i*4, 4);
	    }
	    
	    //绘制正方形结束
	    gl.glFinish();

	    //取消顶点数组
	    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	    
	    //改变旋转的角度
	    rotateTri += 1.0f;
	    rotateQuad -= 1.0f;
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