package wyf.lgz;

import java.io.IOException;
import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
	private int lightAngle=90;//灯的当前角度
	
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
            mRenderer.cylinder.mAngleY += dx * TOUCH_SCALE_FACTOR;//设置沿z轴旋转角度
            requestRender();//重绘画面
        } 
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		int textureId;//纹理名称ID
    	DrawTaper cylinder;//创建圆锥体
		
    	public SceneRenderer()
    	{

    	}
    	
        public void onDrawFrame(GL10 gl) {                	
        	//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();     
                  
            gl.glPushMatrix();//保护变换矩阵现场
            
        	float lx=0; //设定光源的位置
            float ly=(float)(7*Math.cos(Math.toRadians(lightAngle)));
        	float lz=(float)(7*Math.sin(Math.toRadians(lightAngle)));
            float[] positionParamsRed={lx,ly,lz,0};
            gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);    
            
            initMaterial(gl);//初始化纹理
            gl.glTranslatef(0, -3f, -5f);//平移
            initLight(gl);//开灯
            cylinder.drawSelf(gl);//绘制 
            closeLight(gl);//关灯
            
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
            gl.glClearColor(0,0,0,0);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试 
            gl.glEnable(GL10.GL_DEPTH_TEST);
             
            textureId=initTexture(gl,R.drawable.jiaotong);//纹理ID
            cylinder=new DrawTaper(5f,1.5f,12f,10,textureId);//创建圆锥体
            
//            //开启一个线程自动旋转光源
//            new Thread()
//            {
//          	  public void run()
//          	  {
//          		  while(true)
//          		  {
//          			lightAngle+=5;//转动灯
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
	
	//初始化白色灯
	private void initLight(GL10 gl)
	{    
        gl.glEnable(GL10.GL_LIGHTING);//允许光照    
        gl.glEnable(GL10.GL_LIGHT1);//打开1号灯  
        
        //环境光设置
        float[] ambientParams={0.2f,0.2f,0.2f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置
        float[] diffuseParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0); 
	}
	
	//关闭灯
	private void closeLight(GL10 gl)
	{
		gl.glDisable(GL10.GL_LIGHT1);
		gl.glDisable(GL10.GL_LIGHTING);
	}
	
	//初始化材质
	private void initMaterial(GL10 gl)
	{
        //环境光
        float ambientMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
	
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR_MIPMAP_LINEAR);
        ((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
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
