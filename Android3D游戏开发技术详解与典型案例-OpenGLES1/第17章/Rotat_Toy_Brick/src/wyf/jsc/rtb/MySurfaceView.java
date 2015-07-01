package wyf.jsc.rtb;
import static wyf.jsc.rtb.Constant.*;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MySurfaceView extends GLSurfaceView {

    private SceneRenderer mRenderer;//场景渲染器
    //Cube cubeKong;
    float tX;//目标点的初始坐标。
    float tZ;
    float tY;
    float cX;
    float cY;
    float cZ;
    int times;
    MainActivity gkd=(MainActivity)this.getContext();
    boolean turnleft=true;
	boolean turnright=false;
	public MySurfaceView(Context context) {
        super(context);
        tX=-tempFlag+(INIT_I+1)*UNIT_SIZE-0.5f;
        tZ=-tempFlag+(INIT_J+1)*UNIT_SIZE-1.0f;
        cY=9.5f;
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        
    }
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			previousX=e.getX();
			previousY=e.getY();
			break;
		case MotionEvent.ACTION_UP:
			laterX=e.getX();
			laterY=e.getY();
			float dx=laterX-previousX;
			float dy=laterY-previousY;
			float r=(float)(Math.sqrt(dx*dx+dy*dy));
			if(dy>0&&dx!=0)
			{
				float tempA=(float)Math.toDegrees(Math.acos(dx/r));
				if(tempA>=0&&tempA<45)
				{//right
					mRenderer.cube.keyPress(Constant.RIGHT_KEY);
	    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f; 	    			
	    			requestRender();
	    			soundAndTimes();
				}
				if(tempA>=45&&tempA<135)
				{//down
					mRenderer.cube.keyPress(Constant.DOWN_KEY);
	    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
	    			requestRender();
	    			soundAndTimes();
				}
				if(tempA>=135&&tempA<180)
				{//left
					mRenderer.cube.keyPress(Constant.LEFT_KEY);
	    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f;
	    			requestRender();
	    			soundAndTimes();
				}
			}
			if(dy<0&&dx!=0)
			{
				float tempB=(float)Math.toDegrees(Math.asin(dy/r));
				if(dx>0)
				{
					if(tempB>=-45&&tempB<0)
					{//right
						mRenderer.cube.keyPress(Constant.RIGHT_KEY);
		    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f; 	    			
		    			requestRender();
		    			soundAndTimes();
					}
					if(tempB>=-90&&tempB<-45)
					{//up
						mRenderer.cube.keyPress(Constant.UP_KEY);
		    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
		    			requestRender();
		    			soundAndTimes();
					}
				}
				if(dx<0)
				{
					tempB=tempB-90;
					if(tempB>=-135&&tempB<-90)
					{//left
						mRenderer.cube.keyPress(Constant.LEFT_KEY);
		    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f;
		    			requestRender();
		    			soundAndTimes();
					}
					if(tempB>=-180&&tempB<-135)
					{//up
						mRenderer.cube.keyPress(Constant.UP_KEY);
		    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
		    			requestRender();
		    			soundAndTimes();
					}
				}
			}
			if(dx==0)
			{
				if(dy>0)
				{//down
					mRenderer.cube.keyPress(Constant.DOWN_KEY);
	    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
	    			requestRender();
	    			soundAndTimes();
				}
				if(dy<0)
				{//up
					mRenderer.cube.keyPress(Constant.UP_KEY);
	    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
	    			requestRender();
	    			soundAndTimes();
				}
			}
			if(dy==0)
			{
				if(dx>0)
				{//right
					mRenderer.cube.keyPress(Constant.RIGHT_KEY);
	    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f; 	    			
	    			requestRender();
	    			soundAndTimes();
				}
				if(dx<0)
				{//left
					mRenderer.cube.keyPress(Constant.LEFT_KEY);
	    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f;
	    			requestRender();
	    			soundAndTimes();
				}
			}
			break;		
		}
		if(winFlag||loseFlag)
		{
			gkd.toAnotherView(ENTER_WINVIEW);
			winFlag=false;
			loseFlag=false;
		}
			
		return true;
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent e)
	{//键抬起事件回调方法
		if(keyCode==4){
			return false;//抬起return键
		}		
		if(anmiFlag==true)
		{
			return true;
		}
		anmiFlag=true;
		
		switch(keyCode)
    	{
			
    		case 19://up
    			mRenderer.cube.keyPress(Constant.UP_KEY);
    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
    		break;
    		case 20://down
    			mRenderer.cube.keyPress(Constant.DOWN_KEY);
    			tZ=-tempFlag+(targetZ+1)*UNIT_SIZE-1.0f;
        	break;
    		case 21://left
    			mRenderer.cube.keyPress(Constant.LEFT_KEY);
    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f;
    	    break;
    		case 22://right
    			mRenderer.cube.keyPress(Constant.RIGHT_KEY);
    			tX=-tempFlag+(targetX+1)*UNIT_SIZE-0.5f;    			
	        break;
    	} 	
		gkd.playSound(1, 0);//播放翻木块的声音
		closeSound();
		dropSound();
		times++;
		if(winFlag||loseFlag)
		{
			gkd.toAnotherView(ENTER_WINVIEW);
			winFlag=false;
			loseFlag=false;
		}
		return true;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		//各种纹理Id
		int cubeSmallTexId;//长方体小面纹理Id
		int cubeBigTexId;//长方体大面纹理Id
		int floorId;//地板纹理Id
		int iconId;//图标的纹理ID
		int headId;//头纹理Id
		int numberId;//数字Id
		int cloudId;//浮云的Id
		int backId;//背景图Id
		int levelId;//关数Id
		
		//各种图像
		Cube cube;
		FloorGroup floorGroup;//绘制地板的对象
    	TextureRect icon;//图标对象
		TextureRect head;//头图像
		TextureRect backGround;
		TextureRect level;//关卡的图片
		BallCloud cloud;//云
		//TextureRect cloud;
    	Number number;//数目图象
    	
		 
    	public SceneRenderer()
    	{
    			
    	}
    	
        public void onDrawFrame(GL10 gl) {
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
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -25);
            backGround.drawSelf(gl);
            gl.glPopMatrix();
            GLU.gluLookAt
			(
					gl,
					-tempFlag+(targetX+1)*UNIT_SIZE+1.5f,
					9f, //观察点坐标
					10,
					tX,//目标点坐标
					UNIT_HIGHT*6,
					tZ,
					0,
					1,
					0
			);
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, -2);
            floorGroup.drawSelf(gl);
            gl.glTranslatef(-tempFlag+(INIT_I+1)*UNIT_SIZE-0.5f, UNIT_HIGHT*6,-tempFlag/2+(INIT_J+1)*UNIT_SIZE-0.5f);
            cube.drawSelf(gl);
            gl.glPopMatrix();  
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
            //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);          
            gl.glTranslatef(0,8.475f*ratio, -15*ratio);
            head.drawSelf(gl);//绘制头
            gl.glTranslatef(-4.5f*ratio, -0.15f*ratio, 0.015f*ratio);
            icon.drawSelf(gl);//绘制晶体图标
            gl.glTranslatef(1.5f*ratio, 0.0f, 0.015f*ratio);
            number.drawSelf(gl);//绘制转动次数
            gl.glTranslatef(6.75f*ratio, 0, 0);
            level.drawSelf(gl);//绘制L
            //禁止混合   
            gl.glDisable(GL10.GL_BLEND);
            gl.glPushMatrix();
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); 
            gl.glTranslatef(-1.2f, -5.5f, -10.0f);
            cloud.drawSelf(gl);//绘制云层  
            gl.glDisable(GL10.GL_BLEND);
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
            ratio = (float) width / height;
            backWidth=width;
            backHeight=height;
            Log.d("backWidth"+backWidth, "backHeight"+backHeight);
            //调用此方法计算产生透视投影矩阵
           gl.glFrustumf(-ratio, ratio, -1, 1, 1.67f, 100);
           number=new Number(numberId,MySurfaceView.this);
           icon=new TextureRect
           (
        		ratio*0.45f,ratio*0.45f,
           		iconId,
           		new float[]
                   {
           				0,0,0,1f,1,1f,
           				1,1f,1,0,0,0	
           		}
           );
           head=new TextureRect
           (
           		ratio*12,ratio*0.75f,
           		headId,
           		new float[]
                   {
           				1,0,0,0,0,1,
           				0,1,1,1,1,0
           		   }
           );
           backGround=new TextureRect
           (
        		ratio*15,ratio*24,
           		backId,
           		new float[]
           	    {
           				0.625f,0,0,0,0,0.93f,
                   		0,0.93f,0.625f,0.93f,0.625f,0 
           	    }
           );
           level=new TextureRect
           (
        		ratio*0.75f,ratio*0.75f,
        		levelId,
           		new float[]
           		{
           				1,0,0,0,0,1,
               		    0,1,1,1,1,0
           		}
           );
           cloud=new BallCloud(15000*ratio,cloudId);
         //转动云层
           new Thread()
           {
           	public void run()
           	{
           		try
           		{
           			sleep(40);
           		}catch(Exception e)
           		{
           			e.printStackTrace();
           		}
           		while(true)
           		{
           			
           			cloud.mAngleY+=0.1f;
           			try
           			{
           			sleep(40);	
           			}catch(Exception e)
           			{
           				e.printStackTrace();
           			}
           		}
           	}
           }.start();
          
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);
        	//设置为打开背面剪裁
            gl.glEnable(GL10.GL_CULL_FACE);
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);               
            //初始化纹理
            cubeSmallTexId=initTexture(gl,R.drawable.cubesmall); 
            cubeBigTexId=initTexture(gl,R.drawable.cubebig); 
            floorId=initTexture(gl,R.drawable.floor);
            iconId=initTexture(gl,R.drawable.cubesmall);
            headId=initTexture(gl,R.drawable.head);
            numberId=initTexture(gl,R.drawable.number);
            cloudId=initTexture(gl,R.drawable.cloud);
            backId=initTexture(gl,R.drawable.back);
            levelId=initTexture(gl,R.drawable.l);            
            cube=new Cube
            (
            		0.5f,cubeSmallTexId,cubeBigTexId,
            		new float[]
            			{
            				0,0,0,1f,1,1f,
            				1,1f,1,0,0,0
            	        }
            );
            floorGroup=new FloorGroup(1,floorId);
            Log.d("backWidthC"+backWidth, "backHeightC"+backHeight);           
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
	
	public void closeSound()
	{
		if(winSound)
		{
			gkd.mpBack.stop();
			gkd.playSound(3, 0);
			winSound=false;
		}
	}
	public void dropSound()
	{
		if(dropFlag)
		{
			gkd.mpBack.stop();
			gkd.playSound(2, 0);
			dropFlag=false;
		}
	}
	public void soundAndTimes()
	{
		gkd.playSound(1, 0);//播放翻木块的声音
	    closeSound();
	    dropSound();
	    times++;
	}
}

