package wyf.sj;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class MySurfaceView extends GLSurfaceView{

	private SceneRenderer mRenderer;//场景渲染器
	float cx=0;//摄像机x位置
	float cy=10;//摄像机y位置
	float cz=10;//摄像机z位置
	
	float tx=0;////目标点x位置
	float ty=0;//目标点y位置
	float tz=0;//目标点z位置
	final float UNIT_Z=2.0f;//移动坐标系的位置

	final float UNIT_OFFSET_X=4.0f;//椎体X轴位置
	final float UNIT_OFFSET_Y=5.0f;//椎体Y轴位置
	final float UNIT_OFFSET_Z=0.5f;//椎体Z轴位置
	ArrayList<Control> al;//椎体控制列表
	GoThread gt;//运动线程
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        al=new ArrayList<Control>();//创建对象
	}

	
	private class SceneRenderer implements GLSurfaceView.Renderer
	{

		int ZTId;//椎体纹理ID
		int ZTId_T;//2椎体纹理ID
		
		ZhuiTi zhuiTi;//声明椎体
		ZhuiTi zhuiTi_T;//声明另一个椎体
		
		Control ct;//控制椎体运动
		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			//设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);

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
            gl.glTranslatef(0, 0, -UNIT_Z);
            for(int i=0;i<al.size();i++)
            {
            	al.get(i).drawSelf(gl);//绘制椎体
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
            //调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio*0.5f, ratio*0.5f, -0.5f, 0.5f, 1, 100);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			 //关闭抗抖动  
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0.8f,0.8f,0.8f,0.8f);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);            
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
    		 
    		ZTId=initTexture(gl,R.drawable.ground);//初始化纹理ID
    		ZTId_T=initTexture(gl,R.drawable.bottom);
    		
    		zhuiTi=new ZhuiTi(ZTId,1,2,1);//创建椎体
    		zhuiTi_T=new ZhuiTi(ZTId_T,1,1,1);//创建椎体
    		
    		
//    		ct=new Control(zhuiTi,0,0,-7*UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//Z轴上的障碍物
//    		al.add(ct);
    		ct=new Control(zhuiTi_T,UNIT_OFFSET_X,0,-UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//X轴上的障碍物
    		al.add(ct);
//    		ct=new Control(zhuiTi,0,UNIT_OFFSET_Y,-UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//Y轴上的障碍物
//    		al.add(ct);
    		ct=new Control(zhuiTi_T,0,0,-UNIT_OFFSET_Z,1.0f,0.0f,0.0f);//创建椎体
    		al.add(ct);//加入列表中 
    		ct=new Control(zhuiTi_T,-UNIT_OFFSET_X,0,-UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//X轴上的障碍物
    		al.add(ct);
//    		ct=new Control(zhuiTi,0,-UNIT_OFFSET_Y,-UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//Y轴上的障碍物
//    		al.add(ct);
//    		ct=new Control(zhuiTi,0,0,4*UNIT_OFFSET_Z,0.0f,0.0f,0.0f);//Z轴上的障碍物
//    		al.add(ct);//加入列表中
    		gt=new GoThread(al);//创建运动线程对象
    		gt.start();//开启线程

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
