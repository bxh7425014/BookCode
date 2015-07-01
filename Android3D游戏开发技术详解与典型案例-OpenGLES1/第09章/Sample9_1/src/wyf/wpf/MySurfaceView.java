package wyf.wpf;

import java.io.IOException;
import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class MySurfaceView extends GLSurfaceView {

    static int wallaId;//墙面纹理a的ID
    static int wallbId;//墙面纹理b的ID
    static int wallcId;//墙面纹理c的ID
    static int desertId;//沙漠纹理ID
	
	private SceneRenderer mRenderer;//场景渲染器	
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	Pyramid[] pArray;//金字塔数组
    	Desert desert;//沙漠
    	Celestial celestialSmall;//小星星星空半球
    	Celestial celestialBig;//大星星星空半球
    	
    	double lightAngle=120.0;//阳光的角度
    	
    	public SceneRenderer()
    	{
                 new Thread()
                 {//定时旋转阳光的线程
                	 public void run()
                	 {
                		 while(true)
                		 {
                			 lightAngle+=0.5;
                			 if(lightAngle>=360)
                			 {
                				 lightAngle=0;
                			 }
                			 try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
                		 }
                	 }
                 }.start();
    	}
    	
        public void onDrawFrame(GL10 gl) {            
            //使用平滑着色
        	gl.glShadeModel(GL10.GL_SMOOTH);    
        	//设定Sun光源的位置
        	float lxSun=(float)(1*Math.cos(Math.toRadians(lightAngle)));
        	float lySun=(float)(1*Math.sin(Math.toRadians(lightAngle)));
        	float[] positionParamsGreen={lxSun,lySun,0.6f,0};//最后的0表示使用定向光
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
        	
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            
            //绘制金字塔
            for(Pyramid tp:pArray)
            {
            	tp.drawSelf(gl);
            }        
            //绘制沙漠
            desert.drawSelf(gl);
            //绘制星空
            celestialSmall.drawSelf(gl);
            celestialBig.drawSelf(gl);
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
            gl.glFrustumf(-ratio, ratio, -0.5f, 1.5f, 1, 100);   
            
            //设置camera位置
            GLU.gluLookAt
            (
            		gl, 
            		-1.0f,   //人眼位置的X
            		0.6f, 	//人眼位置的Y
            		3.0f,   //人眼位置的Z
            		0, 	//人眼球看的点X
            		0.2f,   //人眼球看的点Y
            		0,   //人眼球看的点Z
            		0, 
            		1, 
            		0
            );   
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
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
            
            //初始化纹理
            wallaId=initTexture(gl,R.drawable.walla);
            wallbId=initTexture(gl,R.drawable.wallb);
            wallcId=initTexture(gl,R.drawable.wallc);
            desertId=initTexture(gl,R.drawable.desert);
            
            //创建金字塔
            pArray=new Pyramid[]
	        {
	    			new Pyramid(-2,-2,2.0f,30,wallaId),
	    			new Pyramid(3,-7,2.0f,0,wallbId),
	    			new Pyramid(6,-2,2.0f,0,wallcId),
	        };
            //创建沙漠
            desert=new Desert(-20,-20,4,0,desertId,40,40);
            //创建星空
            celestialSmall=new Celestial(0,0,1,0,250);
            celestialBig=new Celestial(0,0,2,0,50);
            
            gl.glEnable(GL10.GL_LIGHTING);//允许光照        
            initSunLight(gl);//初始化阳光光源
            initMaterial(gl);//初始化材质
            
            gl.glEnable(GL10.GL_FOG);//允许雾
            initFog(gl);//初始化雾
            
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
	
	private void initMaterial(GL10 gl)
	{//材质为白色时什么颜色的光照在上面就将体现出什么颜色
        //环境光为白色材质
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光为白色材质
        float diffuseMaterial[] = {0.8f, 0.8f, 0.8f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质为白色
        float specularMaterial[] = {0.6f, 0.6f, 0.6f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        //高光反射区域,数越大高亮区域越小越暗
        float shininessMaterial[] = {1.5f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	
	private void initSunLight(GL10 gl)
	{
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={0.2f,0.2f,0.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            

        //散射光设置
        float[] diffuseParams={1f,1f,0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={1.0f,1.0f,0.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);          
	}
	
	//初始化雾
	public void initFog(GL10 gl)
	{
		float[] fogColor={1,0.91765f,0.66667f,0};//雾的颜色
		gl.glFogfv(GL10.GL_FOG_COLOR, fogColor, 0);//设置雾的颜色，RGBA模式
		gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP2);//设置雾的模式，选择不同的雾因子GL_EXP(默认)、GL_EXP2或GL_LINEAR
		gl.glFogf(GL10.GL_FOG_DENSITY, 1);//设置雾的浓度，浓度范围为0~1之间，0表示最低浓度，1表示最高浓度。
		gl.glFogf(GL10.GL_FOG_START, 0.5f);//设置雾的开始距离，距离摄像机的最近距离。
		gl.glFogf(GL10.GL_FOG_END, 100.0f);//设置雾的结束距离，距离摄像机的最远距离。
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
