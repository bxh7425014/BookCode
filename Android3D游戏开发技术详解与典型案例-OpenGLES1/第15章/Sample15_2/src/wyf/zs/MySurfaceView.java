package wyf.zs;

import java.io.IOException;
import static wyf.zs.Constant.*;

import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.KeyEvent;
public class MySurfaceView extends GLSurfaceView
{
	static float cx=0;//摄像机x位置
	static float cy=10;//摄像机y位置
	static float cz=20;//摄像机z位置
	
	static float tx=0;////目标点x位置
	static float ty=2;//目标点y位置
	static float tz=0;//目标点z位置
	
	float xAngle=0;
	float ballx=0;//球的初始位置
	float ballz=0;
	int keyState=0;//键盘状态  1-up 2-down 4-left 8-right
	KeyThread keyThread;
	public SceneRenderer mRenderer;
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        keyThread=new KeyThread(this);
        keyThread.start();      
	}
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(keyCode)
		{
		case 19:
			keyState=keyState|0x1;
			break;
		case 20:
			keyState=keyState|0x2;
			break;
		case 21:
			keyState=keyState|0x4;
			break;
		case 22:
			keyState=keyState|0x8;
			break;
		}
		return true;
	}
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent e)
	{
		switch(keyCode)
		{		
		case 19:
		case 20:
		case 21:
		case 22:
			direction=0;
			keyState=0;
			break;
		}
		return true;
	}
	
	public class SceneRenderer implements GLSurfaceView.Renderer
	{
		int ballTextureId;//球纹理ID
		int floorTextureId;//地面纹理
		BallForDraw ball;//球
		TextureRect floor;//地面

		//@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			//采用平滑着色
            gl.glShadeModel(GL10.GL_SMOOTH);
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);            
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();  
            
            
          //设置camera位置
            GLU.gluLookAt
            (
            		gl, 
            		cx,   //人眼位置的X
            		cy, 	//人眼位置的Y
            		cz,   //人眼位置的Z
            		tx, 	//人眼球看的点X
            		ty,   //人眼球看的点Y
            		tz,   //人眼球看的点Z
            		0, 
            		1, 
            		0
            ); 
            gl.glPushMatrix();//获取坐标轴
            gl.glTranslatef(0, 2*STARTY+HEIGHT+SCALE, 0);//移动坐标轴
            gl.glPopMatrix();//释放坐标轴
            
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            floor.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            
            gl.glTranslatef(ballx, SCALE+0.05f, ballz);
            gl.glRotatef(xAngle, 1, 0, 0);
            ball.drawSelf(gl);
            gl.glPopMatrix();
		} 

		//@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			 //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1f, 1f, 1, 100);
		}

		//@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			 //关闭抗抖动  
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);            
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
    		
    		
    		ballTextureId=initTexture(gl,R.drawable.basketball);//初始化球纹理
    		ball=new BallForDraw(ballx,ballz,11.25f,SCALE,ballTextureId);//创建球
    		floorTextureId=initTexture(gl,R.drawable.floor);
    		floor=new TextureRect//创建地板
    		(
    				20,0,30,floorTextureId,
    				new float[]
			          {
	        				0,0,0,1,1,1,
	        				1,1,1,0,0,0
			          }
    		);
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					xAngle+=3f;
					try {
						sleep(30);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	//初始化纹理
	
 }
	public int initTexture(GL10 gl,int drawableId)
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);//提供未使用的纹理对象名称    
		int textureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//创建和使用纹理对象
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);//指定放大缩小过滤方法
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        { 
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//定义二维纹理
        bitmapTmp.recycle(); 
        
        return textureId;
	}
}
