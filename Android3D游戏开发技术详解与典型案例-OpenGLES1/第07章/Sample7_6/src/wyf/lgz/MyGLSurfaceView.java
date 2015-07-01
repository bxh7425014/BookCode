package wyf.lgz;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
	
	public MyGLSurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }   
   
    //触摸事件回调方法 
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔Y位移
            mRenderer.cylinder.mAngleX += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            mRenderer.cylinder.mAngleZ += dx * TOUCH_SCALE_FACTOR;//设置沿z轴旋转角度
            requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	DrawCirque cylinder;//创建圆环
    	
        public void onDrawFrame(GL10 gl) {                	
        	//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();     
                  
            gl.glPushMatrix();//保护变换矩阵现场   
            gl.glTranslatef(0, 0, -15f);//平移
            cylinder.drawSelf(gl);//绘制 
            gl.glPopMatrix();//恢复变换矩阵现场
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(1,1,1,1);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH); 
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
           
            cylinder=new DrawCirque(20f,45f,10f,2f);//创建圆柱体
            
//            //开启一个线程自动旋转光源
//            new Thread()
//            {
//          	  public void run()
//          	  {
//          		  while(true)
//          		  {
//          			mRenderer.cylinder.mAngleY+=2*TOUCH_SCALE_FACTOR;//球沿Y轴转动
//                    requestRender();//重绘画面
//                    try
//                    {
//                  	  Thread.sleep(50);//休息10ms再重绘
//                    }
//                    catch(Exception e)
//                    {
//                  	  e.printStackTrace();
//                    }        			  
//          		  }
//          	  }
//            }.start();
        }
    }
}
