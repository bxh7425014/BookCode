package com.bn.Sample19_3;

import java.util.HashMap;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

public class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//场景渲染器
    //茶壶的偏移量
    static float xOffset=0;
    //茶壶转动的角度
    static float yAngle=0;
	HashMap<Integer,BNPoint> hm=new HashMap<Integer,BNPoint>();
	//模拟键盘监听线程
	KeyThread kt;
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//获取触控的动作编号
		int action=event.getAction()&MotionEvent.ACTION_MASK;
		//获取主、辅点id（down时主辅点id皆正确，up时辅点id正确，主点id要查询Map中剩下的一个点的id）
		int id=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;
		switch(action)
		{
			case MotionEvent.ACTION_DOWN: //主点down
			case MotionEvent.ACTION_POINTER_DOWN: //辅点down	
				hm.put(id, new BNPoint(event.getX(id),event.getY(id)));
				kt.keyPress(event.getX(id), event.getY(id));
			break;
			case MotionEvent.ACTION_POINTER_UP:
				float x=hm.get(id).x;
				float y=hm.get(id).y;
				hm.remove(id);
				kt.keyUp(x, y);
			break;
			case MotionEvent.ACTION_UP:
				hm.clear();
				kt.clearKeyState();
			break;
		}
		return true;
	}
	
	public MySurfaceView(Context context)
	{
        super(context);
        this.setEGLContextClientVersion(2); //设置使用OPENGL ES2.0
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染 
    }
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	//从指定的obj文件中加载对象
		LoadedObjectVertexNormal ch;
		LoadedObjectVertexNormal pm;
    	
        public void onDrawFrame(GL10 gl) 
        {
        	//清除深度缓冲与颜色缓冲
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            MatrixState.copyMVMatrix();
            MatrixState.pushMatrix();//保护现场
            //若加载的物体不为空则绘制物体
            if(pm!=null)
            {
            	pm.drawSelf();
            }
            MatrixState.popMatrix();
            MatrixState.pushMatrix();
            MatrixState.translate(xOffset, 0, 0);
            MatrixState.rotate(yAngle, 0, 1, 0);
            if(ch!=null)
            {
            	ch.drawSelf();
            }
            MatrixState.popMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置 
        	GLES20.glViewport(0, 0, width, height); 
        	//计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            MatrixState.setCamera(0,5,25,0,0,0,0,1,0);
            //初始化光源位置
        	MatrixState.setLightLocation(70, 30, 70);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
        	GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //打开背面剪裁   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
            //加载要绘制的物体
            ch=LoadUtil.loadFromFile("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            pm=LoadUtil.loadFromFile("pm.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            kt=new KeyThread();
            kt.start();
        }
    }
}