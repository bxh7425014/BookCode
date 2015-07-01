package wyf.jazz;

import static wyf.jazz.Constant.*;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;
public class MySurfaceView extends GLSurfaceView
{
	float cx=0;//摄像机x位置
	float cy=0;//摄像机y位置
	float cz=0;//摄像机z位置
	
	float tx=0;////目标点x位置
	float ty=0;//目标点y位置
	float tz=-15;//目标点z位置
	
	float xOffset=0;//天空盒X向位置 
	float yOffset=0;//天空盒Y向位置
	float zOffset=0;//天空盒Z向位置
	float direction=0.0f;  //设置按键后的摄像机
	final float DISTANCE_CAMERA_YACHT=20.0f; 
	final float DISTANCE_CAMERA_TARGET=2.0f;
	final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);
	boolean turnFlag=true;
	private SceneRenderer mRenderer;
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		cx=CAMERA_INI_X;//摄像机x坐标
        cy=CAMERA_INI_Y;//摄像机y坐标
        cz=CAMERA_INI_Z;//摄像机z坐标
         
        tx=(float)(cx-Math.sin(direction)*DISTANCE);//观察目标点x坐标  
        ty=CAMERA_INI_Y-0.2f;//平视观察目标点y坐标
       // ty=CAMERA_INI_Y-2f;//鸟瞰观察目标点y坐标
        tz=(float)(cz-Math.cos(direction)*DISTANCE);//观察目标点z坐标   
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
	{
		if(keyCode==4)
		{
			System.exit(0);
			return false;
		}
    	if(keyCode==19||keyCode==20)
    	{//上下键表示前进或后退
    		float xOffset=0;//此步的X位移
    		float zOffset=0;//此步的Z位移
    		
    		switch(keyCode)
    		{
    		   case 19://向上键代表前进
    			  //前进时运动方向与当前方向相同
    			  xOffset=(float)-Math.sin(direction)*MOVE_SPAN;
    			  zOffset=(float)-Math.cos(direction)*MOVE_SPAN;
    		   break;
    		   case 20://向下键代表后退
    			   //后退时运动方向与当前方向相反
    			   xOffset=(float)Math.sin(direction)*MOVE_SPAN;
     			   zOffset=(float)Math.cos(direction)*MOVE_SPAN;
    		   break;
    		}
    		//计算运动后的XZ值
			cx=cx+xOffset;
			cz=cz+zOffset;
    	}
    	else
    	{
    		switch(keyCode)
    		{
	    		case 21:  //向左代表左转身
	    		  direction=direction+DEGREE_SPAN;	
	    		break;
	    		case 22:  //向右代表右转身
	    		  direction=direction-DEGREE_SPAN;  
	    		break;
    		}
    	}
    	//设置新的观察目标点XZ坐标
    	tx=(float)(cx-Math.sin(direction)*DISTANCE);//观察目标点x坐标 
        tz=(float)(cz-Math.cos(direction)*DISTANCE);//观察目标点z坐标     		
    	return true;
	}
	
	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		int cubeTextureId;
		Cube cube;
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
            
            gl.glPushMatrix();
            gl.glTranslatef(xOffset, yOffset, zOffset);
            cube.drawSelf(gl);
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
            gl.glFrustumf(-ratio*1.5f, ratio*1.5f, -1.5f, 1.5f, 1, 100);
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
    		
    		cubeTextureId=initTexture(gl,R.drawable.sky);
    		cube=new Cube(cubeTextureId,HEIGHT,LENGTH,WIDTH);
		}
		
	}
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);//提供未使用的纹理对象名称    
		int textureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//创建和使用纹理对象
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);//指定放大缩小过滤方法
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);

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
