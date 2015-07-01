package wyf.zs;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
class MySurfaceView extends GLSurfaceView {
    private SceneRenderer mRenderer;//场景渲染器
    public float change;
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Spheroid ball=new Spheroid(3);
    	
    	public SceneRenderer()
    	{   		
           new Thread()
            {
          	  public void run()
          	  {
          		
          		  while(true)
          		  {
          			ball.scaleX+=change;
      				ball.scaleY+=change;
          			 if(ball.scaleX>1.5)
          			 {
          				change=-0.02f;
          			 }
          			 if(ball.scaleX<0.5)
          			 {
          				 change+=0.02f;
          			 }
          			try
                    {
                  	  Thread.sleep(50);//休息50ms再重绘
                    }
                    catch(Exception e)
                    {
                  	  e.printStackTrace();
                    } 
          		  } 
          	  }
            }.start();
    	} 	
        public void onDrawFrame(GL10 gl) {            
        	  	
        	//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();  
            gl.glTranslatef(0, 0, -5);
            ball.drawSelf(gl);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            gl.glShadeModel(GL10.GL_SMOOTH);     
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
    }
}

