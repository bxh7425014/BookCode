package wyf.tzz.gdl;

import java.io.IOException;

import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static wyf.tzz.gdl.Constant.*;

class MySurfaceView extends GLSurfaceView {
	
	GL_Demo activity;	//Activity
	
	int objectCount=0;	//得分
	
	int failCount=0;	//失败次数
	
	boolean isWin=false;//是否胜利标记
	
	boolean isFail=false;//是否失败标记
	
	boolean beginFlag=false;		//动画线程开始的标志，true为开始
		
	float targetPointY=(BASE_HEIGHT+LINE_OFF_BOX)*UNIT_SIZE;	//箱子坐标，目标点y坐标

    float cx=-8f;					//摄像机x坐标
    float cy=targetPointY+1f;		//摄像机y坐标
    float cz=18;					//摄像机z坐标
  
    float tx=0;						//观察目标点x坐标  
    float ty=targetPointY;			//观察目标点y坐标
    float tz=0;						//观察目标点z坐标   

    private SceneRenderer mRenderer;//场景渲染器
    
    float width;					//屏幕宽度
    float height;					//屏幕高度
    float ratio;					//屏幕宽高比
    
    Handler hd;
    
    ActionThread actionThread;		//动画线程，绳子摆动和箱子下落
   
    
	public MySurfaceView(Context context) {
        super(context);
        
        activity=(GL_Demo)context;
        
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        

        hd=new Handler()			//初始化消息处理器
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		
        		switch(msg.what)		
        		{
        		   case 0:     	//达到赢的目标数量
    			   
        			   isWin=true;			//将胜利标志置为false
    			   
	    			   beginFlag=false;		//让动画线程停止   
	                   	
	                   	if(activity.isSound)	//是否打开了声音  
	                   	{	                   	
	                   		activity.mpWin.start();//播放胜利音乐
	                   		
	                   		activity.mpBack.pause();//背景音乐停止
	                   	}
	                   	
	                   	activity.setWinView();  //切换到胜利的画面         
	                   	
        		   break; 
        		   
        		   case 1:
        			   isFail=true;
        			   
        			   beginFlag=false;		//让动画线程停止
        			   
        			   if(activity.isSound)	//是否打开了声音  
	                   	{	                   	
	                   		activity.mpFail.start();//播放胜利音乐
	                   		
	                   		activity.mpBack.pause();//背景音乐停止
	                   	}
        			   
        			   activity.setFailView();  //切换到失败的画面       
        			   
        			   
        		}
        	}
        };
        
   	
		new Thread()						//创建一个新的线程，检验是否胜利
		{
			@Override
			public void run()
			{
				while(!isWin&&!isFail)		//如果还没有胜利
				{
					//发消息加载游戏
					if(objectCount==GOAL_COUNT)
					{
						hd.sendEmptyMessage(0);//发送现在得分即为成功落下箱子数量
					}
					else if(failCount==GOAL_COUNT)
					{
						hd.sendEmptyMessage(1);//发送现在得分即为成功落下箱子数量
					}
					
					try {
						Thread.sleep(200);	//睡眠200ms
					} 
					catch (InterruptedException e) {	
						e.printStackTrace();
					}  	
				}
				
			}
		}.start();	//启动线程
		
    }
	
   @Override
   public boolean onKeyDown(int keyCode,KeyEvent event)		//为按键添加监听
   { 
		  switch(keyCode)
		  {  
			   case KeyEvent.KEYCODE_DPAD_CENTER:			//如果按下OK键
				
					   actionThread.beginDown=true;			//将下落标志箱子开始下落
					
					   return true;
					   
			   case KeyEvent.KEYCODE_BACK:			//如果按下返回键
					
				   beginFlag=false;		//让动画线程停止  
                 
				   activity.msv=new MySurfaceView(activity);
					
					activity.setMenuView();		//切换到主菜单界面
				
				   return true;
		  }
		  
	   return false;										//false，其他按键交给系统处理
	   
   }
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        
    	float y = e.getY();		//触点x坐标
        float x = e.getX();		//触点y坐标
   
        switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
        
        if(x>width-BUTTON_WIDTH*UNIT_SIZE*240&&x<width
    			&&y>height-BUTTON_HEIGHT*UNIT_SIZE*240&&y<height)		//如果落在虚拟OK键上
    		{
    			actionThread.beginDown=true;							//箱子开始下落
    			
    			return true;
    		};
    		break;
		}
        
        return super.onTouchEvent(e);				
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 		//创建场景渲染器类
    {   
  	
		Base base;							//基台
		
		Background bk;						//背景
		
		Floor floor;						//地面
		
		BoxGroup bg;						//管理箱子的箱组
		
		Line line;							//绳子
	
		Tree tree;							//树
		
		Score score;						//得分
		
		TextureRect mOKButton;				//虚拟OK键
	
		
		
        public void onDrawFrame(GL10 gl) {  
        	
        	
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色缓存于深度缓存
        	
            gl.glMatrixMode(GL10.GL_MODELVIEW);		//设置当前矩阵为模式矩阵
           
            gl.glLoadIdentity();  					//设置当前矩阵为单位矩阵
  
            ty=targetPointY;						//观察目标点坐标  Y
            
            cy=ty+1f;								//摄像机坐标
         
            GLU.gluLookAt
            ( 
            		gl, 
            		cx,   							//摄像机坐标x
            		cy, 							//摄像机坐标Y
            		cz,  							//摄像机坐标Z
            		tx, 							//观察目标点坐标  X
            		ty,   							//观察目标点坐标  Y
            		tz,   							//观察目标点坐标  Z
            		0, 
            		1, 								//摄像机朝向
            		0
            );   

            
    		 //画背景
    		gl.glPushMatrix();										//保护当前矩阵
    		
    		gl.glTranslatef(0,BACKGROUND_HEIGHT*UNIT_SIZE/2,0);   	//向Y正平移
    
    		gl.glTranslatef(0,0,-BASE_WIDTH/2-0.5f);				//向z负方向平移
    		
    		bk.drawSelf(gl);										//画背景
    		
    		gl.glPopMatrix();										//回复之前变换矩阵
    		
    		
    		//画地面
    		gl.glPushMatrix();										//保护当前矩阵
    		
    		gl.glTranslatef(0, 0,FLOOR_WIDTH*UNIT_SIZE/3);			//向z正方向平移
    	
    		floor.drawSelf(gl);										//画地板
    		
    		gl.glPopMatrix();										//回复之前变换矩阵
    	
    		//画基台
    		gl.glPushMatrix();										//保护当前矩阵
    		
    		gl.glTranslatef(0,BASE_HEIGHT*UNIT_SIZE/2,0);			//向x正方向平移
    		
    		base.drawSelf(gl);										//画基台
    		
    		gl.glPopMatrix();										//回复之前变换矩阵
    		
    		
    		//画线和箱子
    		gl.glPushMatrix();    									//保护当前矩阵
    		
    		bg.drawSelf(gl);										//画箱子
    		
    		line.drawSelf(gl);										//画绳子
    		
    		gl.glPopMatrix();										//回复之前变换矩阵
    		
    		
    		//画树
    		gl.glPushMatrix();										//保护当前矩阵
    		
    		gl.glTranslatef(1.5f, 0, 0f);							//向x正方向平移
    		
    		tree.drawSelf(gl);										//画树
    		
    		gl.glTranslatef(-3f, 0, 0f);							//向x负方向平移
    		
    		tree.drawSelf(gl);										//画树
    		
    		gl.glPopMatrix();										//回复之前变换矩阵
    	
    		
    		gl.glLoadIdentity();									//设置当前矩阵为单位矩阵
    		
    		gl.glEnable(GL10.GL_BLEND); 							//开启混合
           
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //设置源混合因子与目标混合因子
            
            gl.glPushMatrix();										//保护当前矩阵
            
            //平移到贴近摄像机位置
            gl.glTranslatef((ratio-BUTTON_WIDTH/2)*UNIT_SIZE, -(1-BUTTON_HEIGHT/2)*UNIT_SIZE,-NEAR);
            
            mOKButton.drawSelf(gl);									//画虚拟OK键
            
            gl.glPopMatrix();										//回复之前变换矩阵
            
            //平移到贴近摄像机位置
            gl.glTranslatef(-(ratio-ICON_WIDTH)*UNIT_SIZE,(1-ICON_HEIGHT)*UNIT_SIZE,-NEAR);
            
            score.drawSelf(gl); 									//画得分
           
            gl.glDisable(GL10.GL_BLEND); 							//禁止混合   
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
           
        	gl.glViewport(0, 0, width, height); 					//设置视窗大小及位置 
        	
            gl.glMatrixMode(GL10.GL_PROJECTION);					//设置当前矩阵为投影矩阵
           
            gl.glLoadIdentity(); 									//设置当前矩阵为单位矩阵
           
            float ratio = (float) width / height; 					//计算透视投影的比例，宽高比
            
            gl.glFrustumf(-ratio, ratio, -1, 1,NEAR, 100);			//设置投影矩阵
            
            
            //保存宽，高，宽高比
            MySurfaceView.this.width=width;
            
            MySurfaceView.this.height=height;
            
            MySurfaceView.this.ratio=ratio;       
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            
        	gl.glDisable(GL10.GL_DITHER);							//关闭抗抖动 
        	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式，这里为设置为使用快速模式
            
            gl.glClearColor(0,0,0,0);           					//设置屏幕背景色黑色RGBA 
           
            gl.glEnable(GL10.GL_DEPTH_TEST); 						//启用深度测试
           
            gl.glShadeModel(GL10.GL_SMOOTH);					 	//设置平滑
            
            //初始化图片纹理
            int baseId=initTexture(gl,R.drawable.base);				//基台纹理图片
            int boxId=initTexture(gl,R.drawable.box);				//箱子纹理图片
            int floorId=initTexture(gl,R.drawable.floor);			//地面纹理图片
            int backgroundId=initTexture(gl,R.drawable.background); //背景纹理图片
            int steelId=initTexture(gl,R.drawable.line);			//绳子纹理图片
            int leafId=initTexture(gl,R.drawable.leaf);				//树叶纹理图片
            int trunkId=initTexture(gl,R.drawable.trunk);				//树干纹理图片
            int numberTexId=initTexture(gl,R.drawable.number);		//得分数字纹理图片
            int buttonId=initTexture(gl,R.drawable.okbutton);		//OK键纹理图片
           
            
            bk=new Background(backgroundId);						//初始化背景
            
            floor=new Floor(floorId);								//初始化地面
            
            base=new Base(baseId);									//初始化基台
         
            bg=new BoxGroup(boxId);									//初始化箱子管理组
         
            line=new Line(LINE_LENGTH,LINE_RADIUS,steelId);			//初始化绳子
            
            tree=new Tree(0.3f,leafId,trunkId);								//初始化树
           
            actionThread=new ActionThread(bg,line,MySurfaceView.this); //初始化动画线程
            
            beginFlag=true;											//开始标志置为true
            
            actionThread.start();									//启动动画线程
    
            score=new Score(numberTexId,MySurfaceView.this);		//初始化得分
            
            mOKButton=new TextureRect								//初始化OK虚拟键
            (
            	 buttonId,											//OK键纹理图片
            	 BUTTON_WIDTH*UNIT_SIZE/2,							//OK键长度
            	 BUTTON_HEIGHT*UNIT_SIZE/2,							//OK键高度
           		 new float[]										//OK键纹理坐标数组
    	             {
            			 0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
    	             }
             ); 

        }
        
    	public int initTexture(GL10 gl ,int drawableId)
		{
			int[] textures=new int[1];								//存储纹理ID
			
			gl.glGenTextures(1, textures, 0);						//生成纹理ID
			
			int currTextureId=textures[0];
			
			gl.glBindTexture(GL10.GL_TEXTURE_2D,currTextureId);		//绑定纹理ID
			
			InputStream is=getResources().openRawResource(drawableId);//初始化图片
			
			//设置滤波方式
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
			
			Bitmap bitmapTmp;										//声明位图
			
			try
			{
				bitmapTmp=BitmapFactory.decodeStream(is);			//转换为位图
				
			}
			finally
			{
				try
				{
					is.close();										//关闭流
				}
				catch(IOException e)
				{
					e.printStackTrace();							//打印异常
				}
			}
			
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//转换为位图
			
			bitmapTmp.recycle();									//释放资源
			
			return currTextureId;									//返回图片纹理ID
		}
	}


}
