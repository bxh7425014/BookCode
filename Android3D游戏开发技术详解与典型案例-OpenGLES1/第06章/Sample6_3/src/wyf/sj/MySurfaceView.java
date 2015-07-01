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
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView{

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
	
	public int earthTextureId;//纹理名称ID 
	public int moonTextureId;//纹理名称ID
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }

    //触摸事件回调方法
    @Override public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔Y位移
            mRenderer.earth.mAngleX += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            mRenderer.earth.mAngleZ += dx * TOUCH_SCALE_FACTOR;//设置沿z轴旋转角度
            requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }   

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		Ball earth;
		Ball moon;
    	Celestial celestialSmall;//小星星星空半球
    	Celestial celestialBig;//大星星星空半球
    	
            public void onDrawFrame(GL10 gl) {  
        	//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();     
            
            gl.glTranslatef(0, 0f, -3.6f);  
            
            gl.glEnable(GL10.GL_LIGHTING);//允许光照      
            gl.glPushMatrix();//保护变换矩阵现场
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 3.5f);
            earth.drawSelf(gl);//绘制地球
            gl.glTranslatef(0, 0f, 1.5f);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 1.0f);
            moon.drawSelf(gl);//绘制月球
            gl.glPopMatrix();//恢复变换矩阵现场           
            gl.glDisable(GL10.GL_LIGHTING);//不允许光照      
            
            //绘制星空
            gl.glPushMatrix();//保护变换矩阵现场
            gl.glTranslatef(0, -8.0f, 0.0f);  
            celestialSmall.drawSelf(gl);
            celestialBig.drawSelf(gl);
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
            gl.glFrustumf(-ratio*0.5f, ratio*0.5f, -0.5f, 0.5f, 1, 100);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
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
    		
            gl.glEnable(GL10.GL_LIGHTING);//允许光照            
            initSunLight(gl);//初始化太阳光源
            initMaterial(gl);//初始化材质
            
            //初始化纹理
            earthTextureId=initTexture(gl,R.drawable.earth); 
            moonTextureId=initTexture(gl,R.drawable.moon);
            
            earth=new Ball(6,earthTextureId);
            moon=new Ball(2,moonTextureId);
             
            //创建星空
            celestialSmall=new Celestial(0,0,1,0,750);
            celestialBig=new Celestial(0,0,2,0,200);
            
           //开启一个线程自动旋转地球与月球
            new Thread()
            {
          	  public void run()
          	  {
          		  while(true)
          		  {
          			mRenderer.earth.mAngleY+=2*TOUCH_SCALE_FACTOR;//球沿Y轴转动
          			mRenderer.moon.mAngleY+=2*TOUCH_SCALE_FACTOR;//球沿Y轴转动
                    requestRender();//重绘画面
                    try
                    {
                  	  Thread.sleep(50);//休息10ms再重绘
                    }
                    catch(Exception e)
                    {
                  	  e.printStackTrace();
                    }        			  
          		  }
          	  }
            }.start();
            
            new Thread()
            {//定时转动星空的线程
           	 public void run()
           	 {
           		 while(true)
           		 {
           			celestialSmall.yAngle+=0.5;
           			if(celestialSmall.yAngle>=360)
           			{
           				celestialSmall.yAngle=0;
           			}
           			celestialBig.yAngle+=0.5;
          			if(celestialBig.yAngle>=360)
          			{
          				celestialBig.yAngle=0;
          			}
           			 try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
           		 }
           	 }
            }.start();
        }
    }
	
	//初始化太阳光源
	private void initSunLight(GL10 gl)
	{
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={0.05f,0.05f,0.025f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置
        float[] diffuseParams={1f,1f,0.5f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={1f,1f,0.5f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);  
        
        //设定光源的位置
    	float[] positionParamsGreen={-14.14f,8.28f,6f,0};//最后的0表示使用定向光
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
	}
	
	
	//初始化材质
	private void initMaterial(GL10 gl)
	{//材质为白色时什么颜色的光照在上面就将体现出什么颜色
        //环境光为白色材质
        float ambientMaterial[] = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光为白色材质
        float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质为白色
        float specularMaterial[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);        
	}
	
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int textureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
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
        
        return textureId;
	}
}
