package wyf.jazz;

import java.io.IOException;
import static wyf.jazz.Constant.*;
import java.io.InputStream;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
public class MySurfaceView extends GLSurfaceView
{
	float cx=0;//摄像机x位置
	float cy=10;//摄像机y位置
	float cz=30;//摄像机z位置
	
	float tx=0;////目标点x位置
	float ty=2;//目标点y位置
	float tz=0;//目标点z位置
	private SceneRenderer mRenderer;
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	}
	
	
	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		int ballTextureId;//球纹理ID
		int floorTextureId;//地面纹理
		BallForDraw ball;//球
		TextureRect floor;//地面
		Ball_Go_Thread bgt;//球运动线程
		ArrayList<LogicalBall> albfc=new ArrayList<LogicalBall>();
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
            gl.glTranslatef(0,-0.1f, 0);
            floor.drawSelf(gl);
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            for(LogicalBall bfcc:albfc)   
			{				
				bfcc.drawSelf(gl);
			}
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
            gl.glFrustumf(-ratio*0.5f, ratio*0.5f, -0.5f, 0.5f, 1, 100);
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
    		ball=new BallForDraw(11.25f,SCALE,ballTextureId);//创建球
    		floorTextureId=initTexture(gl,R.drawable.floor);
    		floor=new TextureRect//创建地板
    		(
    				30,0,30,floorTextureId,
    				new float[]
			          {
	        				0,0,0,1,1,1,
	        				1,1,1,0,0,0
			          }
    		);
    		albfc.add(new LogicalBall(ball,-STARTX,0.5f,0.5f));
    		albfc.add(new LogicalBall(ball,STARTX,-0.3f,0.7f));
    		bgt=new Ball_Go_Thread(albfc);
			bgt.start();
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
