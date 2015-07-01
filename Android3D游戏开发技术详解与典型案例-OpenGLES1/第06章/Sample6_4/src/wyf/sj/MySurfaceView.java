package wyf.sj;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class MySurfaceView extends GLSurfaceView{
	private SceneRenderer mRenderer;//场景渲染器
		
		public MySurfaceView(Context context) {
	        super(context);
	        mRenderer = new SceneRenderer();	//创建场景渲染器
	        setRenderer(mRenderer);				//设置渲染器		
	        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	    }

		private class SceneRenderer implements GLSurfaceView.Renderer 
	    {   
			int testTexId;//纹理
			
			TextureRect trOneXOne;//1x1贴图矩形
			TextureRect trFourXFour;//4x4贴图矩形
			TextureRect trFourXTwo;//4x2贴图矩形
	    	
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
	            
	            //将场景推远一些
	            gl.glTranslatef(0, 0, -3);
	            //绘制1x1纹理矩形
	            gl.glPushMatrix();
	            gl.glTranslatef(-3.5f, 0, 0);
	            trOneXOne.drawSelf(gl);
	            gl.glPopMatrix();
	            //绘制4x4纹理矩形
	            gl.glPushMatrix();
	            gl.glTranslatef(0, 0, 0);
	            trFourXFour.drawSelf(gl);
	            gl.glPopMatrix();
	            //绘制4x4纹理矩形
	            gl.glPushMatrix();
	            gl.glTranslatef(3.5f, 0, 0);
	            trFourXTwo.drawSelf(gl);
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
	            gl.glClearColor(0,0,0,0);            
	            //启用深度测试
	            gl.glEnable(GL10.GL_DEPTH_TEST);  
	            //初始化纹理
	            testTexId=initTexture(gl,R.drawable.xwl); 
	            //创建纹理矩形
	            trOneXOne=new TextureRect(1.5f,1.5f,testTexId,1,1);//s-t  0-1
	            trFourXFour=new TextureRect(1.5f,1.5f,testTexId,4,4);//s-t 4-4
	            trFourXTwo=new TextureRect(1.5f,1.5f,testTexId,4,2);//s-t 4-2
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
	        
	        
//	      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);//拉大模式
//	      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
	        //设置纹理拉伸方式为重复  
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
	        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
	        bitmapTmp.recycle(); 
	        
	        return currTextureId;
		}
}
