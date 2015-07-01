package wyf.sj;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import static wyf.sj.Constant.*;
public class MySurfaceView extends GLSurfaceView{

	SceneRenderer mRenderer;//声明渲染器
	float cx=0;//摄像机x位置
	float cy=1;//摄像机y位置
	float cz=0;//摄像机z位置
	
	float tx;////目标点x位置
	float ty;//目标点y位置
	float tz;//目标点z位置
	float left;//视角的值
	float right;
	float top;
	float bottom;
	float near;
	float far;
	float cameraAlpha=70;//摄像机角度
	
	{
		tx=(float)(cx-CAMERA_DISTANCE*Math.sin(Math.toRadians(cameraAlpha)));
		ty=cy;
		tz=(float)(cz-CAMERA_DISTANCE*Math.cos(Math.toRadians(cameraAlpha)));
	}
	
	
	ArrayList<BNShape> alBN;//物体列表
	ArrayList<Float> alDistance;//离摄像机的距离
	
	
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        alBN=new ArrayList<BNShape>();
        alDistance=new ArrayList<Float>();
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float y = e.getY();
        float x = e.getX();
        switch(e.getAction())
        {
        	case MotionEvent.ACTION_DOWN:
        		alDistance.clear();//清空列表
        		float bodyDistance=Float.POSITIVE_INFINITY;//记录物体距离
        		int bodyFlag=0;//记录物体的标志位
        		        		
        		for(int i=0;i<alBN.size();i++)
        		{ 
        			alBN.get(i).setHilight(false);
        			float minMax[]=alBN.get(i).findMinMax();//获得长宽高
        			float mid[]=alBN.get(i).findMid();//获取中心点位置
        			float[] bPosition=RotateUtil.calculateABPosition
        			(
        					x, //触屏X坐标
        					y, //触屏Y坐标
        					MyActivity.screenWidth, //屏幕宽度
        					MyActivity.screenHeight, //屏幕长度
        					left, //视角left值
        					top, //视角top值
        					near, //视角near值
        				    far,
        				    cameraAlpha,//摄像机角度
        				    cx,
        				    cy,
        				    cz
        			);
        			float tempDistance=IsIntersectantUtil.isIntersectant
        			(
        					mid[0], mid[1], mid[2], //物体中心点位置
        					minMax[0]/2, minMax[1]/2, minMax[2]/2, //长 宽 高
        					bPosition[0],bPosition[1],bPosition[2],//A点位置
        					bPosition[3],bPosition[4],bPosition[5]//B点位置   
        			);
        			alDistance.add(tempDistance);        			
        		}
        		for(int i=0;i<alDistance.size();i++)
        		{
        			float tempA=alDistance.get(i);
        			if(tempA<bodyDistance)
        			{
        					bodyDistance=tempA;//记录最小值
        					bodyFlag=i;//记录最小值索引                					
        			}
        		}
        		
        		Log.d("bodyDistance",bodyDistance+""); 
        		
        		if(!Float.isInfinite(bodyDistance))
        		{
        			alBN.get(bodyFlag).setHilight(true);//调用调整大小的方法
        			Log.d("hi",bodyFlag+"|"+alBN.get(bodyFlag).getClass().toString());
        		}
        		break;
        		
        }
		
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
	{
		if(keyCode==4)
		{
			return false;
		}
		switch(keyCode)
		{
		case 21://按左键
			
			cameraAlpha+=5f;
			tx=(float)(cx-CAMERA_DISTANCE*Math.sin(Math.toRadians(cameraAlpha)));
			tz=(float)(cz-CAMERA_DISTANCE*Math.cos(Math.toRadians(cameraAlpha)));
			break;
		case 22://按右键
			cameraAlpha-=5f;
			tx=(float)(cx-CAMERA_DISTANCE*Math.sin(Math.toRadians(cameraAlpha)));
			tz=(float)(cz-CAMERA_DISTANCE*Math.cos(Math.toRadians(cameraAlpha)));
			break;
			
		}
		
		return true;
	}
	private class SceneRenderer implements GLSurfaceView.Renderer
	{

		int cubeId;//声明立方体ID
		int textureId;//声明
		int textureZId;//晶体纹理ID
		BNShape bnShape;//声明形状
		//Cube cube;//声明对象
		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			//清除颜色缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();   
            //设置camera位置
            GLU.gluLookAt
            (
            		gl, 
            		cx,   //摄像机的X位置
            		cy, 	//摄像机的Y位置
            		cz,   //摄像机的Z位置
            		tx, 	//目标点X
            		ty,   //目标点Y
            		tz,   //目标点Z
            		0, //UP位置
            		1, 
            		0
            );
            
            
            gl.glPushMatrix();
            for(int i=0;i<alBN.size();i++)
            {
            	gl.glPushMatrix();
            	alBN.get(i).drawSelf(gl);
            	gl.glPopMatrix();
            }
           
            gl.glPopMatrix();
  		}

		@Override
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
            left=right=ratio*0.5f;
            top=bottom=0.5f;
            near=1;
            far=100;
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-left,right,-bottom,top, near, far);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			 //关闭抗抖动  
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0.8f,0.8f,0.8f,0);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);            
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);  
    		
    		cubeId=initTexture(gl,R.drawable.stone);//初始化立方体纹理ID
    		textureId=initTexture(gl,R.drawable.basketball);//初始化纹理ID
    		textureZId=initTexture(gl,R.drawable.robot);
    		
    		bnShape=new Cube(cubeId,CUBE_X,CUBE_Y,CUBE_Z,-10f,0f,-4.0f);//创建立方体对象
    		alBN.add(bnShape);
    		bnShape=new Ball(BALL_R,textureId,-5f,0,1.0f);//创建球
    		alBN.add(bnShape);
    		bnShape=new ZhuiTi(textureZId,ZHUITI_X,ZHUITI_Y,ZHUITI_Z,-10f,0f,1.0f);//创建晶体
    		alBN.add(bnShape);
    		bnShape=new Cube(cubeId,CUBE_X,CUBE_Y,CUBE_Z,-10f,0f,4.0f);
    		//new Cylinder(cubeId,CYLINDER_LENGTH,CYLINDER_CIRCLE_RADIUS,CYLINDER_DEGREE_SPAN,CYLINDER_COL,-10f,0f,4.0f);//创建圆柱体
    		alBN.add(bnShape);
    		bnShape=new ZhuiTi(textureZId,ZHUITI_X,ZHUITI_Y,ZHUITI_Z,10f,0f,1.0f);//创建晶体
    		alBN.add(bnShape);
    		bnShape=new Cube(cubeId,CUBE_X,CUBE_Y,CUBE_Z,10f,0f,-4.0f);//创建立方体对象
    		alBN.add(bnShape);
    		bnShape=new Ball(BALL_R,textureId,5f,0,1.0f);//创建球
    		alBN.add(bnShape);
    		
    		bnShape=new Ball(BALL_R,textureId,-5f,0,1.0f);//创建球
    		alBN.add(bnShape);
    		bnShape=new ZhuiTi(textureZId,ZHUITI_X,ZHUITI_Y,ZHUITI_Z,8f,0f,1.0f);//创建晶体
    		alBN.add(bnShape);
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
