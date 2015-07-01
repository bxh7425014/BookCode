package wyf.sj;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器	

    private float mPreviousX;//上次的触控位置X坐标
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dx = x - mPreviousX;//计算触控笔X位移
            mRenderer.angle += dx * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            
            requestRender();//重绘画面
        }   
        mPreviousX = x;//记录触控笔位置
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Cube cube=new Cube();//立方体
    	float angle=45;//总旋转角度
    	
        public void onDrawFrame(GL10 gl) {
    		//设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);

    		//设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
        	
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();    
            
            //设置camera位置=======================
//            GLU.gluLookAt//可能变形的视角——大视角
//            (
//            		gl, 
//            		0f,   //人眼位置的X
//            		0f, 	//人眼位置的Y
//            		2.0f,   //人眼位置的Z
//            		0, 	//人眼球看的点X
//            		0f,   //人眼球看的点Y
//            		0,   //人眼球看的点Z
//            		0, 
//            		1, 
//            		0
//            );   
            
            GLU.gluLookAt//不太可能变形的视角——小视角
            (
            		gl, 
            		0f,   //人眼位置的X
            		10f, 	//人眼位置的Y
            		15.0f,   //人眼位置的Z
            		0, 	//人眼球看的点X
            		0f,   //人眼球看的点Y
            		0,   //人眼球看的点Z
            		0, 
            		1, 
            		0
            );  
            
            //旋转总坐标系
            gl.glRotatef(angle, 0, 1, 0);
            
            //绘制左立方体
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -2);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            
            //绘制右立方体
            gl.glPushMatrix();
            gl.glTranslatef(2, 0, 0);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) height/width ;
            //调用此方法计算产生透视投影矩阵
            //gl.glFrustumf( -1, 1,-ratio, ratio, 1, 100);   //可能变形的视角——大视角  
            gl.glFrustumf( -1, 1,-ratio, ratio, 8f, 100);     //不太可能变形的视角——小视角
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);            
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
    }

}
