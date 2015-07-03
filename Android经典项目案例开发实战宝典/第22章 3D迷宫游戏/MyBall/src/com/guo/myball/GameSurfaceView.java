package com.guo.myball;
import static com.guo.myball.Constant.*;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
//游戏进行界面
class GameSurfaceView extends GLSurfaceView 
{		
	MapMasetActivity father;//声明Activity
	public static int guankaID;//关卡ID
    public static int[][]MAP;//对应关卡的地图数组
    public static int[][] MAP_OBJECT;//对应关卡的洞数组
    public static int STIME;//每一关对应的时间限制
    
    public static float yAngle=0f;//方位角
    public static float xAngle=90f;//仰角 
    public static float cx;//摄像机x坐标
    public static float cy;//摄像机y坐标
    public static float cz;//摄像机z坐标
    public static float tx=0;//观察目标点x坐标  
    public static float ty=0;//观察目标点y坐标
    public static float tz=0f;//观察目标点z坐标      
    public static float upX=0;
    public static float upY=1;
    public static float upZ=0;//up轴
    
    public static float ballX;//球的各个坐标
    public static float ballY;
    public static float ballZ;
    public static float ballGX=0f;//x方向上的加速度
    public static float ballGZ=0f;//y方向上的加速度
    
    public static int ballCsX;//初始格子
    public static int ballCsZ;
    public static int ballMbX;//目标格子
    public static int ballMbZ;
    
    public static float ballVX=0;//XZ方向上的速度
    public static float ballVZ=0;
   
    private SceneRenderer mRenderer;//场景渲染器	
    
    public static int floorId;//地板纹理ID
    public static int wallId;//墙纹理
    public static int yuankonId;//圆孔纹理Id
    public static int ballId;//球纹理ID
    public static int ballYZId;//球的影子纹理ID
    public static int numberId;//数字ID
    public static int time_DH_Id;//顿号ID
    public static int mbyuankonId;
    
	public RectWall yuankon;//圆孔矩形
	public Floor floor;//地板
	public static  Wall wall;//墙
	public BallTextureByVertex ball;//球
	public RectWall ballYZ;//球的影子矩形
	public Number number;//数字
	public TextureRect time_DH;//顿号，用于时间
	
	BallGDThread ballgdT;//球运动线程
	
	public GameSurfaceView(Context context)
	{
        super(context);
        this.father=(MapMasetActivity)context;
        ballCsX=CAMERA_COL_ROW[guankaID][0];//初始行列
        ballCsZ=CAMERA_COL_ROW[guankaID][1];
        
        ballMbX=CAMERA_COL_ROW[guankaID][2];//目标行列
        ballMbZ=CAMERA_COL_ROW[guankaID][3];
        
        MAP=MAPP[guankaID];//地图数组
        MAP_OBJECT=MAP_OBJECTT[guankaID];//洞数组
        STIME=GD_TIME[guankaID];//限制时间
        
        ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;//初始化球位置
		ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
		ballY=ballR;
       
        tx=0;//摄像机目标位置
        ty=0;
        tz=0;
        ballgdT=new BallGDThread(this);
        //设置摄像机的位置
        cx=(float)(tx+Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//摄像机x坐标 
        cz=(float)(tz+Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//摄像机z坐标 
        cy=(float)(ty+Math.sin(Math.toRadians(xAngle))*DISTANCE);//摄像机y坐标 
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染      
       
    }	
	//场景渲染器
	private class SceneRenderer implements GLSurfaceView.Renderer 
	{
		//绘制帧
        public void onDrawFrame(GL10 gl) 
        {  
        	//采用平滑着色
            gl.glShadeModel(GL10.GL_SMOOTH);            
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        	
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();    
            //设置camera位置
            GLU.gluLookAt
            (gl, cx,cy,cz, tx,ty, tz,0,1, 0);   //设置摄像机
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点数组
            gl.glEnable(GL10.GL_TEXTURE_2D);//启用纹理
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
            
            gl.glEnable(GL10.GL_LIGHTING);//允许光照
	        gl.glEnable(GL10.GL_LIGHT0);//开0号灯  	        
	        //允许使用法向量数组
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY); 
            
            floor.drawSelf(gl, floorId);//绘制地板
            
            gl.glPushMatrix();//保护矩阵
            gl.glTranslatef(-MAP[0].length/2*UNIT_SIZE, 0, (-MAP.length/2)*UNIT_SIZE);
            wall.drawSelf(gl, wallId);//绘制墙           
            gl.glPopMatrix();//恢复矩阵
            
            gl.glDisable(GL10.GL_LIGHTING);//关闭光照
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//关闭法向量数组
            
            gl.glEnable(GL10.GL_BLEND);//开启混合

            gl.glBlendFunc(GL10.GL_SRC_ALPHA ,GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
            gl.glPushMatrix();//保护当前矩阵，
            gl.glTranslatef(ballMbX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2,
            		0.015f,
            		ballMbZ*UNIT_SIZE- MAP.length*UNIT_SIZE/2);
            gl.glRotatef(-90, 1, 0, 0);
			yuankon.drawSelf(gl, mbyuankonId);//绘制目标圆孔
			gl.glPopMatrix();
            drawYuanKong(gl);//绘制圆孔           
            gl.glPushMatrix();
        	gl.glTranslatef(ballX+ballR-0.2f, 0.01f, ballZ-ballR+0.2f);
        	gl.glRotatef(-90, 1, 0, 0);
        	gl.glRotatef(45, 0, 0, 1);
        	ballYZ.drawSelf(gl, ballYZId);//绘制影子
        	gl.glPopMatrix();            
            gl.glDisable(GL10.GL_BLEND);//关闭混合                    
            drawBall(gl);//绘制球            
            drawNumber(gl);//绘制当前剩余时间数字            
            
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);   //关闭顶点数组         
            gl.glDisable(GL10.GL_TEXTURE_2D);//关闭纹理
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);        
         }        
        
        public  void drawNumber(GL10 gl)//绘制剩余时间方法
        {
        	gl.glMatrixMode(GL10.GL_MODELVIEW);//模式矩阵   
	        gl.glLoadIdentity();   	     //设置当前矩阵为单位矩阵
	      
	        gl.glEnable(GL10.GL_BLEND);//开启混合
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
	        //绘制数字仪表盘    高度
	        gl.glPushMatrix();
	        gl.glTranslatef(2.0f,1.6f,-6);//设置仪表板的位置.不能再调节
	        number.y=0;//数字的Y坐标
	        number.NumberStr=Math.abs(GD_TIME[guankaID]-STIME)/60+"";//剩下的分钟数
	        number.drawSelf(gl,0,numberId);//绘制分钟
	        gl.glTranslatef(ICON_WIDTH*0.7f,0f,0);
	        time_DH.drawSelf(gl, time_DH_Id);//画顿号
	        gl.glTranslatef(ICON_WIDTH*0.7f,0f,0);
	        number.NumberStr=Math.abs(GD_TIME[guankaID]-STIME)%60+"";
	        number.drawSelf(gl,1,numberId);//画秒数
	        gl.glPopMatrix();//恢复矩阵
	        gl.glDisableClientState(GL10.GL_BLEND);//关闭混合       
        }
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 1000);   
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
    		 //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
    		//开启混合   
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);           
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);             
            floorId=initTexture(gl,R.drawable.floor);//地面ID
            wallId=initTexture(gl,R.drawable.wall);    //墙ID
            
            yuankonId=initTexture2(gl,R.drawable.yuankon);//圆孔ID
            ballId=initTexture2(gl,R.drawable.ball);//球ID
            ballYZId=initTexture2(gl,R.drawable.ballyingzi);//球的影子ID
            numberId=initTexture2(gl,R.drawable.number);//数字ID
            time_DH_Id=initTexture2(gl,R.drawable.dunhao);//顿号纹理
            mbyuankonId=initTexture2(gl,R.drawable.mbyuankon);//目标圆孔Id
            
            
            floor=new Floor(MAP[0].length,MAP.length);//地板
            wall=new Wall();//墙     
            yuankon=new RectWall(2f*ballR,2f*ballR);//圆孔
            ball=new BallTextureByVertex(ballR,15);//球
            ballYZ=new RectWall(3.6f*ballR,2.6f*ballR);//影子
            number=new Number(GameSurfaceView.this);//数字对象
            time_DH=new TextureRect(ICON_WIDTH*0.5f/2,//数字
            	 ICON_HEIGHT*0.5f/2,
           		 new float[]
		             {
		           	  0,0, 0,1, 1,0,
		           	  0,1, 1,1,  1,0
		             });//顿号
            ballgdT.start();
            
            initLight(gl);//初始化灯光
            float[] positionParamsGreen={-4,4,4,0};//最后的0表示是定向光
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
     
        }
        public void drawBall(GL10 gl)//画重力球
        {	
        	gl.glPushMatrix();
        	gl.glTranslatef(ballX, ballY, ballZ);     //移动相应的位置 	
        	ball.drawSelf(gl, ballId);   //绘制
        	gl.glPopMatrix();       	
        }
        public void drawYuanKong(GL10 gl)//绘制圆孔
        {
        	gl.glPushMatrix();
        	gl.glTranslatef(-MAP[0].length*UNIT_SIZE/2, 0.01f,- MAP.length*UNIT_SIZE/2);
        	for(int i=0;i<MAP_OBJECT.length;i++)
        	{
        		for(int j=0;j<MAP_OBJECT[0].length;j++)
        		{
        			if(MAP_OBJECT[i][j]==1)
        			{
        				if(i==ballMbX&&j==ballMbZ)//如果不是目标洞则绘制
        				{
        				   continue;
        				}
        				gl.glPushMatrix();
        				gl.glTranslatef((j)*UNIT_SIZE, 0.001f, (i)*UNIT_SIZE);
        				gl.glRotatef(-90, 1, 0, 0);
        				yuankon.drawSelf(gl, yuankonId);//绘制
        				gl.glPopMatrix();
        			}
        		}
        	}
        	gl.glPopMatrix();
        }
        private void initLight(GL10 gl)
    	{
    		//白色灯光
            gl.glEnable(GL10.GL_LIGHT0);//打开0号灯  
            //环境光设置
            float[] ambientParams={1f,1f,1f,1.0f};//光参数 RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
            //散射光设置
            float[] diffuseParams={1f,1f,1f,1.0f};//光参数 RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
            //反射光设置
            float[] specularParams={1f,1f,1f,1.0f};//光参数 RGBA
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
    	}
	 }
	//初始化纹理
	public int initTexture2(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		//在MIN_FILTER MAG_FILTER中使用MIPMAP纹理
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        //纹理拉伸
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
        //获得图片资源
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
        //生成纹理
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        //回收资源
        bitmapTmp.recycle();  
        return currTextureId;
	}

	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0]; 
		//绑定纹理
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		
		//在MIN_FILTER MAG_FILTER中使用MIPMAP纹理
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		// 生成Mipmap纹理
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP,GL10.GL_TRUE);
        //纹理平铺
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        //取得图片资源
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
        //生成纹理
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        //回收资源
        bitmapTmp.recycle(); 
        return currTextureId;
	}
}
