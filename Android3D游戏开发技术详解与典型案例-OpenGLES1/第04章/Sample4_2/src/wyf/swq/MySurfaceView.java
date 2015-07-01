package wyf.swq;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;


public class MySurfaceView extends GLSurfaceView {
	private SceneRenderer myRenderer;//场景渲染器
	
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myRenderer=new SceneRenderer();//创建场景渲染器
		this.setRenderer(myRenderer);//设置渲染器
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
	}

	private class SceneRenderer  implements GLSurfaceView.Renderer{//内部类，实现Renderer接口，渲染器
		
		Points p=new Points();
		Lines l=new Lines();
		public SceneRenderer(){
			
		}
		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//清除颜色缓存和深度缓存
			gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵为模式矩阵
			gl.glLoadIdentity();//设置当前矩阵为单位矩阵
			
			gl.glTranslatef(0.5f, 0, -2.0f);//
			p.drawSelf(gl);//
			gl.glTranslatef(-1.0f,0,0);
			l.drawSelf(gl);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			 gl.glViewport(0, 0, width, height);
			 gl.glMatrixMode(GL10.GL_PROJECTION);
			 gl.glLoadIdentity();
			 float ratio=(float)width/height;
			 gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			gl.glDisable(GL10.GL_DITHER);//关闭抗抖动
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式，这里为设置使用快速模式
			gl.glClearColor(0, 0, 0, 0);//设置屏幕背景色为黑色
			gl.glEnable(GL10.GL_DEPTH_TEST);//启用深度检测	
		}
	}
}
