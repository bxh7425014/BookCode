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

public class MySurfaceView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
	private boolean smoothFlag=true;//是否进行平滑着色
	private int lightAngleGreen=0;//绿光灯的当前角度
	private int lightAngleRed=90;//红光灯的当前角度
	
	int textureId;//纹理名称ID

public MySurfaceView(Context context) {
	super(context);
	mRenderer = new SceneRenderer();	//创建场景渲染器
    setRenderer(mRenderer);				//设置渲染器		
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
}

 @Override 
 public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔Y位移
            mRenderer.ball.mAngleX += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            mRenderer.ball.mAngleZ += dx * TOUCH_SCALE_FACTOR;//设置沿z轴旋转角度
            requestRender();//重绘画面
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    public void setSmoothFlag(boolean smoothFlag) {
		this.smoothFlag = smoothFlag;
	}

	public boolean isSmoothFlag() {
		return smoothFlag;
	}


private class SceneRenderer implements GLSurfaceView.Renderer 
{   
	Ball ball;
	
	public SceneRenderer()
	{
        //开启一个线程自动旋转球体
        new Thread()
        {
      	  public void run()
      	  {
      		try
            {
          	  Thread.sleep(1000);//休息1000ms再开始绘制
            }
            catch(Exception e)
            {
          	  e.printStackTrace();
            } 
      		  while(true)
      		  {
      			lightAngleGreen+=5;//转动绿灯
      			lightAngleRed+=5;//转动红灯
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
	}
	
	
    public void onDrawFrame(GL10 gl) {            
    	if(smoothFlag)
    	{//进行平滑着色
    		gl.glShadeModel(GL10.GL_SMOOTH);
    	}
    	else
    	{//不进行平滑着色
    		gl.glShadeModel(GL10.GL_FLAT);
    	}
    	
    	//设定绿色光源的位置
    	float lxGreen=(float)(7*Math.cos(Math.toRadians(lightAngleGreen)));
    	float lzGreen=(float)(7*Math.sin(Math.toRadians(lightAngleGreen)));
    	float[] positionParamsGreen={lxGreen,0,lzGreen,1};//最后的1表示使用定位光
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
        
        //设定红色光源的位置
        float lyRed=(float)(7*Math.cos(Math.toRadians(lightAngleRed)));
    	float lzRed=(float)(7*Math.sin(Math.toRadians(lightAngleRed)));
        float[] positionParamsRed={0,lyRed,lzRed,1};//最后的1表示使用定位光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, positionParamsRed,0);        	
    	
    	//清除颜色缓存
    	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
    	//设置当前矩阵为模式矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //设置当前矩阵为单位矩阵
        gl.glLoadIdentity();     
        
        gl.glTranslatef(0, 0f, -1.8f);  
        
        gl.glPushMatrix();//保护变换矩阵现场
        ball.drawSelf(gl);//绘制球
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
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭抗抖动 
    	gl.glDisable(GL10.GL_DITHER);
    	//设置特定Hint项目的模式，这里为设置为使用快速模式
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        //设置屏幕背景色黑色RGBA
        gl.glClearColor(0,0,0,0);
        //设置着色模型为平滑着色   
        gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        
        gl.glEnable(GL10.GL_LIGHTING);//允许光照    
        
        initGreenLight(gl);//初始化绿色灯
        initRedLight(gl);//初始化红色灯
        initMaterial(gl);//初始化材质
        
        textureId=initTexture(gl,R.drawable.duke);//初始化纹理
        ball=new Ball(4,textureId);
    }
}

//初始化绿色灯
private void initGreenLight(GL10 gl)
{
    gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
    
    //环境光设置
    float[] ambientParams={0.1f,0.1f,0.1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
    
    //散射光设置
    float[] diffuseParams={0f,1f,0f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //反射光设置
    float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
}



//初始化红色灯
private void initRedLight(GL10 gl)
{    
    gl.glEnable(GL10.GL_LIGHT1);//打开1号灯  
    
    //环境光设置
    float[] ambientParams={0.2f,0.2f,0.2f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientParams,0);            
    
    //散射光设置
    float[] diffuseParams={1f,0f,0f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseParams,0); 
    
    //反射光设置
    float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
    gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, specularParams,0); 
}

//初始化材质
private void initMaterial(GL10 gl)
{//材质为白色时什么颜色的光照在上面就将体现出什么颜色
    //环境光为白色材质
    float ambientMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
    //散射光为白色材质
    float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
    //高光材质为白色
    float specularMaterial[] = {1f, 1f, 1f, 1.0f};
    gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
    gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
}
public int initTexture(GL10 gl,int textureId)//textureId
{
	int[] textures = new int[1];
	gl.glGenTextures(1, textures, 0);    
	int currTextureId=textures[0];    
	gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
    
    InputStream is = this.getResources().openRawResource(textureId);
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
