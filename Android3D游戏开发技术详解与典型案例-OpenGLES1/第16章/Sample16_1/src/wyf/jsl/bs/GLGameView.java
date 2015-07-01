package wyf.jsl.bs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import wyf.jsl.bs.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static wyf.jsl.bs.Constant.*;

class GLGameView extends GLSurfaceView {
	private SceneRenderer mRenderer;
	
	MenuView w;//菜单界面引用声明
	
	public int floorTexId;
	public int balltextureid;
	public int sidewallTexId1;
	public int sidewallTexId2;
	public int backwallTexId;
	public int roofTexId;
	public int lzjTextureid;
	public int lhTextureid;
	public int lbTextureid;
	public int scorebankid;
	public int numberid;
	
	public int shadowid;
	
	//摄像机位置
	public float cx=CAMERA_INI_X;
	public float cy=CAMERA_INI_Y;
	public float cz=CAMERA_INI_Z;
	//目标点位置
	public float tx=0;
	public float ty=CAMERA_INI_Y;//////////////////////////////
	public float tz=0;
	
	public int screenWidth;
	public int screenHeight;
	
	public float touchStartY;
	public float touchStartX;
	LogicalBall currentTouchBall;
	
	public GLGameView(Context context,MenuView w) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        
        this.w=w;
    }
	BasketballActivity activity=(BasketballActivity)this.getContext();
	
	//按键回调方法
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(e.getAction())
		{
		case KeyEvent.ACTION_DOWN:
			if(keyCode==4)
			{
				w.left1=LEFT;//还原菜单位置
				w.left2=LEFT;
				w.left3=LEFT;
				w.left4=LEFT;
				
				SOUND_FLAG=false;//关闭声音
				DEADTIME_FLAG=false;//关闭倒计时线程
				activity.hd.sendEmptyMessage(GAME_MENU);
			}
			break;
		}
		return true;
	}
	
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) 
        {
         case MotionEvent.ACTION_DOWN://触控笔按下
        	 touchStartY = y;//记录触控笔位置
             touchStartX = x;//记录触控笔位置
             //判断有否摸到球
             float x3d=WIDTH*x/screenWidth-0.5f*WIDTH;
             float y3d=HEIGHT*0.9f*(screenHeight-y)/screenHeight;/////////////////////////////////////////////
             
             for(LogicalBall bfcc:mRenderer.albfc)
 			{
 				if(bfcc.state==0&&
 				   x3d<bfcc.currentX+BALL_SCALE&&x3d>bfcc.currentX-BALL_SCALE&&
 				   y3d<bfcc.currentY+BALL_SCALE&&y3d>bfcc.currentY-BALL_SCALE)
 				{
 					currentTouchBall=bfcc;
 					break;
 				}
 			}
             
         break;        
         case MotionEvent.ACTION_UP://触控笔抬起
             float dx=x-touchStartX;
             float dy=y-touchStartY;
             
             if(currentTouchBall!=null)
             {
            	 float vx=dx*BALL_MAX_SPEED_X/screenWidth;
            	 float vy=-dy*BALL_MAX_SPEED_Y/screenHeight;
            	 float vz=dy*BALL_MAX_SPEED_Z/screenHeight;
            	 currentTouchBall.vx=vx*3;
            	 currentTouchBall.vy=vy*4;
            	 currentTouchBall.vz=vz*2;
            	 currentTouchBall.state=2;
            	             	 
            	 currentTouchBall=null;
             }
         break;
        }        
        return true;
    }
	

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		private Assemble basketball_stands;
		private Panel sb;
		private Score score;
		private Deadtime deadtime;
		
		private Floor shadow;
		
		private BallForDraw ball;
		private BackWall backwall;
		private Floor floor;
		private LeftWall leftwall;
		private RightWall rightwall;
		private Roof roof;
		Ball_Go_Thread bgt;
		List<LogicalBall> albfc=new ArrayList<LogicalBall>();
		
		public SceneRenderer()
		{
			
		}
			
        public void onDrawFrame(GL10 gl) {
            gl.glShadeModel(GL10.GL_SMOOTH);  
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();             
            
            //摄像机
			GLU.gluLookAt
			(
					gl, 
					cx, 
					cy, 
					cz, 
					tx, 
					ty, 
					tz,
					0, 
					1, 
					0
			); 
          
			
			gl.glPushMatrix();
			floor.drawSelf(gl);
			backwall.drawSelf(gl);			
			leftwall.drawSelf(gl);
			rightwall.drawSelf(gl);
			roof.drawSelf(gl);
			basketball_stands.drawSelf(gl);
			gl.glPopMatrix();
			
			for(LogicalBall bfcc:albfc)   
			{				
				bfcc.drawSelf(gl);
			}
			     
			
			
            //恢复成初始状态绘制晶体图标与数量
            //设置当前矩阵为模式矩阵 
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glPushMatrix();
			gl.glTranslatef(0, 1.2f, -3f);
			sb.drawSelf(gl);
			gl.glPopMatrix();  
			
			gl.glEnable(GL10.GL_BLEND);//开启混合
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			gl.glPushMatrix();
			gl.glTranslatef(-0.8f, 1.12f, -2.8f);
			score.drawSelf(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(0.55f, 1.12f, -2.8f);
			deadtime.drawSelf(gl);
			gl.glPopMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
			screenWidth=width;
			screenHeight=height;
        	gl.glViewport(0, 0, width, height);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            float ratio = (float) width / height;
            gl.glFrustumf(-ratio, ratio, -1.1f, 0.9f, 2, 100); 
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        	gl.glDisable(GL10.GL_DITHER);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            gl.glClearColor(0,0,0,0);           
            gl.glEnable(GL10.GL_DEPTH_TEST);                
			
			floorTexId=initTexture(gl,R.drawable.floor);
			sidewallTexId1=initTexture(gl,R.drawable.swall1);
			sidewallTexId2=initTexture(gl,R.drawable.swall3);
			backwallTexId=initTexture(gl,R.drawable.swall2);
			roofTexId=initTexture(gl,R.drawable.black);
			balltextureid=initTexture(gl,R.drawable.basketball);
			lzjTextureid=initTexture(gl,R.drawable.icon);
			lhTextureid=initTexture(gl, R.drawable.red);
			lbTextureid=initTexture(gl, R.drawable.lanban2);
			scorebankid=initTexture(gl,R.drawable.yibiaoban);
			numberid=initTexture(gl,R.drawable.number);
			
			shadowid=initTexture(gl,R.drawable.shadow);
			
			sb=new Panel
			(
					2.2f,
					0.3f,
					scorebankid,
					new float[]
					{
				        	0,0,0,1,1,0,
				        	1,0,0,1,1,1
					}
			);
			score=new Score(numberid,GLGameView.this);
			deadtime=new Deadtime(numberid,GLGameView.this);
			
			backwall=new BackWall(backwallTexId,WIDTH,HEIGHT,LENGTH);
			floor=new Floor(WIDTH,HEIGHT,LENGTH,floorTexId);
			leftwall=new LeftWall(sidewallTexId1,WIDTH,HEIGHT,LENGTH);
			rightwall=new RightWall(sidewallTexId2,WIDTH,HEIGHT,LENGTH);
			roof=new Roof(roofTexId,WIDTH,HEIGHT,LENGTH);
			
			ball=new BallForDraw(BALL_ANGLESPAN,BALL_SCALE,balltextureid);
			
			shadow=new Floor(SHADOW_X,0,SHADOW_Z,shadowid);
           
			//篮球架
			basketball_stands=new Assemble
			(
					BASKETBALL_STANDS_SPAN,
					BASKETBALL_STANDS_X,
					BASKETBALL_STANDS_Y,
					BASKETBALL_STANDS_Z,
					GLGameView.this,
					lzjTextureid,
					lhTextureid,
					lbTextureid
		     );
			ringCenter=basketball_stands.getRingCentre();
			ringR=basketball_stands.getRingReduis();
			
			albfc.add(new LogicalBall(ball,shadow,0,activity));
			albfc.add(new LogicalBall(ball,shadow,-2*BALL_SCALE,activity));
			albfc.add(new LogicalBall(ball,shadow,2*BALL_SCALE,activity));
			bgt=new Ball_Go_Thread(albfc);
			bgt.start();
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
