package wyf.zs;

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
import android.view.MotionEvent;
public class MySurfaceView extends GLSurfaceView{
	
	private SceneRenderer mRender; 
	public MySurfaceView(Context context) {
		super(context);
		mRender=new SceneRenderer();//创建场景渲染器
		setRenderer(mRender);//设置渲染器
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置为自动渲染
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x=e.getX();
		float y=e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if(x>12&&x<48&&y>371&&y<404)
			{
				mRender.diamond.yOffset+=0.5f;
			}
			if(x>268&&x<305&&y>371&&y<404)
			{
				mRender.diamond.yOffset-=0.5f;
			}
			break;
		}
		return true;	
	}
	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		Diamond diamond;
		TextureRect upButton;
		TextureRect downButton;		
		int upTextureId;
		int downTextureId;
		public SceneRenderer()
		{}

		@Override
		public void onDrawFrame(GL10 gl) {
		
    		//启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
    		//设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);        	
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();   
			
			
			GLU.gluLookAt(gl, 0, 2, 0, 0, 0, -5, 0, 1, 0);//摄像机位置
			gl.glPushMatrix();
			gl.glTranslatef(0,0, -9);
			diamond.drawSelf(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glMatrixMode(GL10.GL_MODELVIEW);
	        //设置当前矩阵为单位矩阵
	        gl.glLoadIdentity();   
			gl.glPushMatrix();
			gl.glTranslatef(-3,-4,-5);
			upButton.drawSelf(gl);
			gl.glPopMatrix();			
			gl.glPushMatrix();
			gl.glTranslatef(3,-4, -5);
			downButton.drawSelf(gl);
			gl.glPopMatrix();
			gl.glPopMatrix();	
			
		}
		@Override
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

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {			
			
			//关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);            
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glShadeModel(GL10.GL_SMOOTH);
            
            upTextureId=initTexture(gl,R.drawable.up); 
            downTextureId=initTexture(gl,R.drawable.down);
            upButton=new TextureRect(upTextureId);
            downButton=new TextureRect(downTextureId);
            diamond=new Diamond();
                     
           new Thread()
           {
        	   public void run()
        	   {
        		   while(true)
        		   {
        			   diamond.yAngle+=5f; 
        			   try
            		   {
            			   sleep(20);
            		   }catch(Exception e)
            		   {
            			   e.printStackTrace();
            		   }
        		   }
        		   
        	   }	   
           }.start();
		}	
	}
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
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
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}
}
