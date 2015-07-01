package wyf.jsc.tdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static wyf.jsc.tdb.Constant.*;

class MySurfaceView extends GLSurfaceView {

	//boolean needCreateFlag=true;
	
	float mPreviousX;
	float mPreviousY;
	MyActivity activity=(MyActivity)this.getContext();
	//摄像机的位置
	
	float cx=0;//摄像机x位置
	float cy=50;//75;//摄像机y位置
	float cz=50;//摄像机z位置
	
	float tx=0;//BOTTOM_LENGTH/2;//目标点x位置
	float ty=0;//目标点y位置
	float tz=0;//BOTTOM_WIDE/2;//目标点z位置
	
	static float yAngle=0;		//摄像机绕Y轴旋转的角度
	static float xAngle=0;		//摄像机绕X轴旋转的角度
	static float angle=0;
	
	float direction=0.0f;  //设置按键后的摄像机
	final float MOVE_SPAN=0.8f;
	final float DISTANCE_CAMERA_YACHT=50.0f;
	final float DISTANCE_CAMERA_TARGET=2.0f;
	final float DEGREE_SPAN=(float)(5.0/180.0f*Math.PI);
	
    private SceneRenderer mRenderer;//场景渲染器
    BallForControl bfd;

    BallGoThread bgt;//桌球的运动线程
    ArrayList<BallForControl> ballAl;//桌球的列表
   
    public static boolean turnFlag=true;//视角旋转一周的标志位
    public static int ballGoOver=1;//辅助传送球运动结束消息
    
    int size;//记录球的个数
    final float UNIT_D=-0.2f;//球与球的距离微控
    float tempAngleZ;//临时记录转动的角度
    static int id;//1 代表玩家1    2 代表玩家2
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        ballAl=new ArrayList<BallForControl>();//桌球的列表
    }
	
	@Override
    public boolean onTouchEvent(MotionEvent e) {
		float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
        	float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔Y位移
            float r=(float)(Math.sqrt(dx*dx+dy*dy));
            if(dy>0&&dx!=0)
			{
				float tempA=(float)Math.toDegrees(Math.acos(dx/r));
				if(tempA>=0&&tempA<45)
				{//right
					direction=direction+DEGREE_SPAN;
		            cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
					cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				}
				if(tempA>=45&&tempA<135)
				{//down
					if(cy>=20)					
					{
						cy-=0.5f;
					}
				}
				if(tempA>=135&&tempA<180)
				{//left
					direction=direction-DEGREE_SPAN;
				    //设置新的摄像机XZ坐标
			    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
			        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				}
			}
			if(dy<0&&dx!=0)
			{
				float tempB=(float)Math.toDegrees(Math.asin(dy/r));
				if(dx>0)
				{
					if(tempB>=-45&&tempB<0)
					{//right
						direction=direction+DEGREE_SPAN;
					    //设置新的摄像机XZ坐标
				    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
				        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
					}
					if(tempB>=-90&&tempB<-45)
					{//up
						if(cy<=65)
						{
							cy+=0.5f;
						}
					}
				}
				if(dx<0)
				{
					tempB=tempB-90;
					if(tempB>=-135&&tempB<-90)
					{//left
						direction=direction-DEGREE_SPAN;
					    //设置新的摄像机XZ坐标
				    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
				        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
					}
					if(tempB>=-180&&tempB<-135)
					{//up
						if(cy<=65)
						{
							cy+=0.5f;
						}
					}
				}
			}
			if(dx==0)
			{
				if(dy>0){//down
					if(cy>=20){
						cy-=0.5f;
					}
				}
				if(dy<0){//up
					if(cy<=65){
						cy+=0.5f;
					}
				}
			}
			if(dy==0)
			{
				if(dx>0)
				{//right
					direction=direction+DEGREE_SPAN;
				    //设置新的摄像机XZ坐标
			    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
			        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				}
				if(dx<0)
				{//left
					direction=direction-DEGREE_SPAN;
				    //设置新的摄像机XZ坐标
			    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
			        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				}
			}
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
	{
		if(keyCode==4)
		{
			cueFlag=true;//重绘球杆的标志位
			overFlag=false;//打球结束的标志位
			hitFlag=false;//控制是否打球
			hitSound=false;//打球声音标志位
			score=0;//记录得分
			scoreOne=0;//first player score
			scoreTwo=0;//second player score
			scoreNODFlag=1;//用来判断给那位玩家进球加分  1  表示玩家1    2 表示玩家2
			scoreNOD=0;//用来标志是网络版还是单机版     0  表示单机版   1 表示网络版
			scoreTip=1;//用来标志下一次谁有击球权
			sendFlag=false;//控制客户端发送一次消息。
			winLoseFlag=0;
			bgt.flag=false;
			Cue.angleZ=0;//初始化球杆角度
			if(activity.netFlag)
			{
				try {
					activity.ct.dout.writeUTF("<#EXIT_MAN#>");//发送退出消息
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			activity.toAnotherView(ENTER_MENU);//进入菜单界面
			return true;
		}
		switch(keyCode)
		{
		case 19:
			direction=direction+DEGREE_SPAN;			    
			    //设置新的摄像机XZ坐标
		    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
		        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				break;
		case 20:
			direction=direction-DEGREE_SPAN;
			    
			    //设置新的摄像机XZ坐标
		    	cx=(float)(tx+Math.sin(direction)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
		        cz=(float)(tz+Math.cos(direction)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
				break;
			case 21:
					if(hitFlag&&cueFlag)
					{
						if(activity.netFlag)//网络
						{
							tempAngleZ=Cue.angleZ+CUE_ROTATE_DEGREE_SPAN;
							try
							{
								activity.ct.dout.writeUTF("<#CUE_ANGLE#>"+tempAngleZ);//发送球杆转动角度
							}catch(Exception ea)
							{
								ea.printStackTrace();
							}
						}
						else//单机
						{
							Cue.angleZ=Cue.angleZ+CUE_ROTATE_DEGREE_SPAN;//设置球杆转动角度
						}
					}
					break;
			case 22:
					if(hitFlag&&cueFlag)
					{
						if(activity.netFlag)//网络
						{
							tempAngleZ=Cue.angleZ-CUE_ROTATE_DEGREE_SPAN;
							try
							{
								activity.ct.dout.writeUTF("<#CUE_ANGLE#>"+tempAngleZ);//发送球杆转动角度
							}catch(Exception ea)
							{
								ea.printStackTrace();
							}	
						}
						else//单机
						{
							Cue.angleZ=Cue.angleZ-CUE_ROTATE_DEGREE_SPAN;//设置球杆转动角度
						}
					}
					break;
			case 62:
					if(hitFlag&&cueFlag)
					{
						mRenderer.cue.yOffset+=4;//球杆初始位置
						vBall=(float)(Math.random()+1.5)*5;
						if(activity.netFlag)
						{
							try {								
								activity.ct.dout.writeUTF("<#BALL_HIT#>"+vBall+"|"+Cue.angleZ);//发送击球信息 速度+角度
								activity.ct.dout.flush();//刷新消息
							} catch (IOException e1) {								
								e1.printStackTrace();
							}
							hitFlag=false;//将球权设为false
						}else
						{
							ballAl.get(0).vx=(float)(vBall*Math.sin(Math.toRadians(Cue.angleZ)));//分解母球速度
							ballAl.get(0).vz=(float)(-1*vBall*Math.cos(Math.toRadians(Cue.angleZ)));
							cueFlag=false;//打球标志位设为false
						}		
						activity.playSound(1, 0);//播放开球声音
						mRenderer.cue.yOffset=0;//球杆位移设0
					}					
					break;				
		}		
		return true;
	}
	

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		Cue cue;
		int cueId;
		int[] ballTexId=new int[16];//桌球纹理id数组
		public int groundId;//地面的ID
		public int wallId;//墙面Id
		int tableBottomId;//桌底纹理ID		
		int tableAreaId;//桌面纹理ID
		int tableRoundUDId;//桌子上下边缘ID
		int tableRoundLRId;//桌子左右边缘ID
		int circleId;//洞ID
		int baffleId;//挡板ID
		
		public int iconId;//移标版Id
		public int iconIdFirst;//一号玩家的纹理ID
		public int iconIdSecond;//二号玩家的纹理ID
		public int numberId;//分数Id
		public int tipIdOne;//提示一号玩家的纹理Id
		public int tipIdTwo;//提示二号问价的纹理Id
		public int numberIdFirst;//一号玩家的得分纹理ID
		public int numberIdSecond;//二号玩家的得分纹理ID
		public int vsId;//vsId
		public int tipId;//提示ID

		
		
		TextureRect floor;//地板
		Wall wall;//墙面
    	BallTextureByVertex btbv;//用于绘制的球    	
  
    	DrawTable drawTable;//桌子
    	TextureRect icon;//仪表版
    	TextureRect iconFirst;//一号玩家的仪表版
    	TextureRect iconSecond;//二号玩家的仪表版
    	TextureRect tipOne;//一号玩家提示
    	TextureRect tipTwo;//二号玩家的提示
    	TextureRect vs;//VS图片
    	Score score;//计分
    	Score scoreFirst;//一号玩家得分
    	Score scoreSecond;//二号玩家得分
    	TextureRect tipO;//指示玩家球权
        public void onDrawFrame(GL10 gl) {
    		//设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
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
            		0, 	//up位置
            		1, 
            		0
            ); 
            //绘制场景
            gl.glPushMatrix();
            wall.drawSelf(gl);//绘制墙面
            gl.glPopMatrix();
            
            gl.glPushMatrix();//地板
            gl.glTranslatef(-1.5f*WALL_SIZE, BOTTOM_Y, -1.8f*WALL_SIZE);
            for(int i=0;i<FLOOR_I;i++)
            {
            	gl.glPushMatrix();
            	gl.glTranslatef(0, BOTTOM_Y, i*TABLE_AREA_WIDTH/4);
            	for(int j=0;j<FLOOR_J;j++)
            	{
            		gl.glPushMatrix();
            		gl.glTranslatef(j*TABLE_AREA_LENGTH/4, BOTTOM_Y,0);
            		floor.drawSelf(gl);//绘制地板
            		gl.glPopMatrix();
            	}
            	gl.glPopMatrix();
            }
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            drawTable.drawSelf(gl);//绘制桌子
            gl.glPopMatrix();
            
            gl.glRotatef(-90, 0, 1, 0);
            gl.glEnable(GL10.GL_LIGHTING);//允许光照==光照效果需要调节一下，用真机测试                
            size=ballAl.size();//获得球的个数
           
            
            try 
            {
            	for(int i=1;i<size;i++)
                {
                	ballAl.get(i).drawSelf(gl);//绘制其它球
                }
    	    	for(int i=0;i<1;i++)
    	    	{
    	    		 ballAl.get(i).drawSelf(gl); //绘制母球
    	    		 
    	    		 if(cueFlag)
    	    		 { 
    	    			//禁用深度测试
    	    	        gl.glDisable(GL10.GL_DEPTH_TEST);
    	    			gl.glTranslatef(ballAl.get(i).xOffset, BALL_Y, ballAl.get(i).zOffset);
    	    			gl.glRotatef(90, 1, 0, 0);
    	    			cue.drawSelf(gl);//绘制球杆
    	    			gl.glEnable(GL10.GL_DEPTH_TEST);
    	    		 }
    	    		 
    	    		 if(overFlag)
    	   			 {
    	   				 if(soundFlag)
    	   				 {
    	   					 activity.mpBack.pause();//暂停背景音乐
    	   				 }	 
    	   				 backMenu();//调用方法
    	   				 overFlag=false;//标志位设反
    	   			 }
    	    	}
            }catch(Exception e)
            {
            	e.printStackTrace();
            }   
            gl.glDisable(GL10.GL_LIGHTING);//关闭光照   
            //恢复成初始状态绘制晶体图标与数量
            //设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();  
            gl.glDisable(GL10.GL_LIGHTING);//禁止光照
            gl.glEnable(GL10.GL_BLEND);//开启混合   
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置源混合因子与目标混合因子
            switch(scoreNOD)
            {
	            case 0://单机
	            		gl.glTranslatef(-2.5f, 2, -9f);
			            icon.drawSelf(gl);//绘制练习图标
			            gl.glTranslatef(-1.6f, 2.05f, -9);
			            score.drawSelf(gl);//绘制得分
			            break;
	            case 1://网络
	            		gl.glPushMatrix();
	            		gl.glTranslatef(-2.7f, 2,-9);
	            		iconFirst.drawSelf(gl);//绘制玩家1图标
	            		gl.glTranslatef(-0.5f, 2, -9);
	            		scoreFirst.drawSelfNet(gl,1);//绘制玩家1得分
	            		gl.glPopMatrix();
	            		 
	            		//玩家标志绘制
	            		if(id==1)
	            		{
	            			gl.glPushMatrix();
	            			gl.glTranslatef(-2.7f, -1.95f, -9);
	            			tipOne.drawSelf(gl);//绘制玩家1提示
	            			gl.glPopMatrix();
	            		}
	            		
	            		if(id==2)
	            		{
	            			gl.glPushMatrix();
	            			gl.glTranslatef(-2.7f, -1.95f, -9);
	            			tipTwo.drawSelf(gl);//绘制玩家2提示
	            			gl.glPopMatrix();
	            		}
	            		
	            		
	            		gl.glPushMatrix();
	            		gl.glTranslatef(0, 2, -9);
	            		vs.drawSelf(gl);//绘制VS
	            		gl.glPopMatrix();

	            		gl.glPushMatrix();
	            		gl.glTranslatef(2.7f, 2, -9);
	            		iconSecond.drawSelf(gl);//绘制玩家2图标
	            		gl.glTranslatef(0.5f, 2, -9);
	            		scoreSecond.drawSelfNet(gl,2);//绘制玩家2得分
	            		gl.glPopMatrix();
	            		
	            		//球权绘制
	            		if(scoreTip==1)
	            		{
	            			gl.glPushMatrix();
		            		gl.glTranslatef(-2.2f, 1.5f, -8);
		            		tipO.drawSelf(gl);//绘制球权提示
		            		gl.glPopMatrix();
	            		}
	            		if(scoreTip==2)
	            		{
	            			gl.glPushMatrix();//提示二号玩家
		            		gl.glTranslatef(2.2f, 1.5f, -8);
		            		tipO.drawSelf(gl);//绘制球权提示
		            		gl.glPopMatrix();
	            		}
	            		break;
            }
            gl.glDisable(GL10.GL_BLEND);//关闭混合
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
            gl.glFrustumf(-ratio, ratio, -1, 1, 4, 200);  
            
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        	if(needCreateFlag)
//    		{
//    			needCreateFlag=false;
//    		}
//    		else
//    		{
//    			return;
//    		}
        	
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);            
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);  
           
            cueId=initTexture(gl,R.drawable.snooker14);//初始化球杆ID
            cue=new Cue(0.05f,0.05f,5f,3,cueId,0);//创建球杆对象
            
            //初始化纹理            
            ballTexId[0]=initTexture(gl,R.drawable.snooker0);
            ballTexId[1]=initTexture(gl,R.drawable.snooker1);
            ballTexId[2]=initTexture(gl,R.drawable.snooker2);
            ballTexId[3]=initTexture(gl,R.drawable.snooker3);
            ballTexId[4]=initTexture(gl,R.drawable.snooker4);
            ballTexId[5]=initTexture(gl,R.drawable.snooker5);
            ballTexId[6]=initTexture(gl,R.drawable.snooker6);
            ballTexId[7]=initTexture(gl,R.drawable.snooker7);
            ballTexId[8]=initTexture(gl,R.drawable.snooker8);
            ballTexId[9]=initTexture(gl,R.drawable.snooker9);
            ballTexId[10]=initTexture(gl,R.drawable.snooker10);
            ballTexId[11]=initTexture(gl,R.drawable.snooker11);
            ballTexId[12]=initTexture(gl,R.drawable.snooker12);
            ballTexId[13]=initTexture(gl,R.drawable.snooker13);
            ballTexId[14]=initTexture(gl,R.drawable.snooker14);
            ballTexId[15]=initTexture(gl,R.drawable.snooker15);
           
            //初始化桌子 房间的纹理ID
			wallId=initTexture(gl,R.drawable.wall);
			groundId=initTexture(gl,R.drawable.ground3); 
            tableBottomId=initTexture(gl,R.drawable.bottom);//桌底纹理ID
            tableAreaId=initTexture(gl,R.drawable.area);//桌面纹理ID
            tableRoundUDId=initTexture(gl,R.drawable.round);//桌子上下边缘ID
            tableRoundLRId=initTexture(gl,R.drawable.roundlr);//桌子左右边缘ID
            circleId=initTexture(gl,R.drawable.circle);//球洞ID
            baffleId=initTexture(gl,R.drawable.baffle);
			 
			iconId=initTexture(gl,R.drawable.icon);
			iconIdFirst=initTexture(gl,R.drawable.play1);
			iconIdSecond=initTexture(gl,R.drawable.play2);
			numberId=initTexture(gl,R.drawable.number);
			numberIdFirst=initTexture(gl,R.drawable.number);
			numberIdSecond=initTexture(gl,R.drawable.number);
			tipIdOne=initTexture(gl,R.drawable.tip1);
			tipIdTwo=initTexture(gl,R.drawable.tip2);
			vsId=initTexture(gl,R.drawable.vs);
			tipId=initTexture(gl,R.drawable.tip);
            //初始化光照材质
            initSunLight(gl);
            initMaterial(gl);
            
            drawTable=new DrawTable(tableBottomId,tableAreaId,tableRoundUDId,tableRoundLRId,circleId,baffleId);//创建桌子对象
            wall=new Wall(wallId);
            floor=new TextureRect//创建地板
            (
            		TABLE_AREA_LENGTH/4,0,TABLE_AREA_WIDTH/4,groundId,
            		new float[]
			          {
            				0,0,0,1,1,1,
            				1,1,1,0,0,0
			          }
            );
            
            
            icon=new TextureRect//练习 图标对象
            (
            		0.3f,0.3f,0,iconId,
            		new float[] 
			        {
			        	0,0,0,1,1,1,
			        	1,1,1,0,0,0
			        }
            );
            iconFirst=new TextureRect//一号玩家的仪表版
            (
            		1.2f,0.6f,0,iconIdFirst,
            		new float[] 
			        {
			        	0,0,0,1,1,1,
			        	1,1,1,0,0,0
			        }	
            );
            iconSecond=new TextureRect//二号玩家的仪表版
            (
            		1.2f,0.6f,0,iconIdSecond,
            		new float[] 
			        {
			        	0,0,0,1,1,1,
			        	1,1,1,0,0,0
			        }	
            );
            tipOne=new TextureRect//一号玩家提示
            (
            	1.2f,0.48f,0,tipIdOne,
            	new float[]
		          {
            			0,0,0,0.5f,0.625f,0.5f,
			        	0.625f,0.5f,0.625f,0,0,0
		          }
            );
            tipTwo=new TextureRect//二号玩家提示
            (
            	1.2f,0.48f,0,tipIdTwo,
            	new float[]
		          {
            			0,0,0,0.5f,0.625f,0.5f,
			        	0.625f,0.5f,0.625f,0,0,0
		          }
            );
            vs=new TextureRect//vs图片
            (
            		0.8f,0.4f,0,vsId, 
                	new float[]
    		          {
                			0,0,0,1,1,1,
    			        	1,1,1,0,0,0
    		          }	
            );
            
            
            tipO=new TextureRect//提示球权图片
            (
            	0.6f,0.3f,0,tipId,
            	new float[]
	          {
				0,0,0,1,1,1,
	        	1,1,1,0,0,0
	          }
            );
                        
            score=new Score(numberId,MySurfaceView.this);//得分
            scoreFirst=new Score(numberIdFirst,MySurfaceView.this);//玩家1得分
            scoreSecond=new Score(numberIdSecond,MySurfaceView.this);//玩家2得分
    
            //创建用于绘制的球
            btbv=new BallTextureByVertex(BALL_SCALE);
            
            //创建用于控制的球
            //0号球--母球
            bfd=new BallForControl(btbv,0,9,ballTexId[0]);
            ballAl.add(bfd);
            //1-15号球,全部有效
            bfd=new BallForControl(btbv,0,1-BALL_DISTANCE,ballTexId[1]);
            ballAl.add(bfd);
          	bfd=new BallForControl(btbv,-BALL_R-0.1f,0-BALL_DISTANCE+UNIT_D,ballTexId[2]);
            ballAl.add(bfd);  
            bfd=new BallForControl(btbv,BALL_R+0.1f,0-BALL_DISTANCE+UNIT_D,ballTexId[3]);
            ballAl.add(bfd);            
            bfd=new BallForControl(btbv,-2*BALL_R-0.1f,-1-BALL_DISTANCE+2*UNIT_D,ballTexId[4]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,0,-1-BALL_DISTANCE+2*UNIT_D,ballTexId[5]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,2*BALL_R+0.1f,-1-BALL_DISTANCE+2*UNIT_D,ballTexId[6]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,-3*BALL_R-0.1f,-2-BALL_DISTANCE+3*UNIT_D,ballTexId[7]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,-1f*BALL_R,-2-BALL_DISTANCE+3*UNIT_D,ballTexId[8]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,1*BALL_R+0.1f,-2-BALL_DISTANCE+3*UNIT_D,ballTexId[9]);
            ballAl.add(bfd);            
            bfd=new BallForControl(btbv,3*BALL_R+0.2f,-2-BALL_DISTANCE+3*UNIT_D,ballTexId[10]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,-4*BALL_R-0.2f,-3-BALL_DISTANCE+4*UNIT_D,ballTexId[11]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,-2*BALL_R-0.1f,-3-BALL_DISTANCE+4*UNIT_D,ballTexId[12]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,0,-3-BALL_DISTANCE+4*UNIT_D,ballTexId[13]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,2*BALL_R+0.1f,-3-BALL_DISTANCE+4*UNIT_D,ballTexId[14]);
            ballAl.add(bfd);
            bfd=new BallForControl(btbv,4*BALL_R+0.2f,-3-BALL_DISTANCE+4*UNIT_D,ballTexId[15]);
            ballAl.add(bfd);   /*   */         
            tempBallAl=ballAl;
            //启动桌球的运动线程
            bgt=new BallGoThread(ballAl,MySurfaceView.this);
            bgt.start();
          
            new Thread()//转动摄像机
			{
				public void run()
				{
					try
					{
						sleep(100);
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					if(turnFlag)
					{
						while(angle<6.3f)
						{
							cx=(float)(tx+Math.sin(angle)*DISTANCE_CAMERA_YACHT);//摄像机x坐标
			  		        cz=(float)(tz+Math.cos(angle)*DISTANCE_CAMERA_YACHT);//摄像机z坐标
							angle=direction+angle+DEGREE_SPAN;
							try{
								sleep(50);
							}catch(Exception e)
							{
								e.printStackTrace();
							}
						}
						turnFlag=false;
						angle=0.0f;
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
		gl.glGenTextures(1, textures, 0);    //提供未使用的纹理对象名称
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);//创建和使用纹理对象
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);//指定放大缩小过滤方法
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
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
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//定义二维纹理
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}
	
	//初始化光源
	private void initSunLight(GL10 gl)
	{
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
        
        //环境光设置
        float[] ambientParams={0.85f,0.85f,0.85f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        
        //散射光设置
        float[] diffuseParams={0.05f,0.05f,0.05f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //反射光设置
        float[] specularParams={0f,0f,0.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);  
        
        //设定光源的位置
    	float[] positionParamsGreen={0,15f,0f,1};//最后的1表示使用定位光
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
	}

	
	//初始化材质
	private void initMaterial(GL10 gl)
	{//材质为白色时什么颜色的光照在上面就将体现出什么颜色
        //环境光为白色材质
        float ambientMaterial[] = {0.9f, 0.9f, 0.9f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光为白色材质
        float diffuseMaterial[] = {0.01f, 0.01f, 0.01f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质为白色
        float specularMaterial[] = {0.0f, 0.0f, 0.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);  
        //高光反射区域,数越大高亮区域越小越暗
        float shininessMaterial[] = {5.5f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	public void backMenu()
	{
		if(activity.netFlag) //对战模式下
		{
				switch(winLoseFlag)
				{
					case 1:		
						activity.toAnotherView(ENTER_WIN);	//进入获胜界面
						break;
		
					case 2:		
						activity.toAnotherView(ENTER_LOSE);//进入失败界面
						break;		
				}			
			cueFlag=true;//转动球杆标志位设为true
		}
		else//练习模式
		{
			activity.toAnotherView(ENTER_OVER);//进图结束界面
			cueFlag=true;
		}
		bgt.flag=false;//线程标志位设false
		try
		{
			ballAl.clear();//清空球列表
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void ballhit()//球球碰撞的声音
    {
		activity.playSound(2, 0);
    }
}

