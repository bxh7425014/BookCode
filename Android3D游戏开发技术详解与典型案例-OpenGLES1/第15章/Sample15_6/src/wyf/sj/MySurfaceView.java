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
import static wyf.sj.Constant.*;
public class MySurfaceView extends GLSurfaceView{
	SceneRenderer mRenderer;//声明渲染器
	MyActivity activity=(MyActivity)this.getContext();//获取Activity
	float cx=0;//摄像机x位置
	float cy=3;//摄像机y位置
	float cz=10;//摄像机z位置
	
	float tx=0;////目标点x位置
	float ty=0;//目标点y位置
	float tz=0;//目标点z位置
	ArrayList<BulletForControl> alBFC;//子弹控制列表
	BulletGoThread bgt;//子弹运动线程
	Cube cube;//声明立方体
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
        alBFC=new ArrayList<BulletForControl>();
	}

	private class SceneRenderer implements GLSurfaceView.Renderer
	{

		int taperId;//圆锥体纹理ID
		int cylinderId;//圆柱体纹理ID
		int cubeId;//立方体纹理ID
		int[] exploTextureId=new int[6];//爆炸纹理
		
		Bullet bullet;//声明子弹
		TextureRect[] trExplo=new TextureRect[6];

		BulletForControl bfc;
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
            for(int i=0;i<alBFC.size();i++)
            {
            	alBFC.get(i).drawSelf(gl);//绘制子弹
            }
            gl.glPopMatrix();
            
            gl.glPushMatrix();
            gl.glTranslatef(CUBE_OFFSET_X, CUBE_OFFSET_Y, CUBE_OFFSET_Z);
            cube.drawSelf(gl);//绘制立方体
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
            gl.glClearColor(0.8f,0.8f,0.8f,0);
            //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);            
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);  
         
    		
    		taperId=initTexture(gl,R.drawable.bullet);//初始化纹理ID
    		cylinderId=initTexture(gl,R.drawable.bullet);//初始化子弹纹理ID
    		cubeId=initTexture(gl,R.drawable.stone);//立方体纹理ID
    		//初始化爆炸纹理
    		exploTextureId[0]=initTexture(gl,R.drawable.explode1);
    		exploTextureId[1]=initTexture(gl,R.drawable.explode2);
    		exploTextureId[2]=initTexture(gl,R.drawable.explode3);
    		exploTextureId[3]=initTexture(gl,R.drawable.explode4);
    		exploTextureId[4]=initTexture(gl,R.drawable.explode5);
    		exploTextureId[5]=initTexture(gl,R.drawable.explode6);
    		
    		
    		//创建爆炸动画组成帧
            trExplo[0]=new TextureRect(exploTextureId[0],LONG_R*3f,LONG_R*3f);
            trExplo[1]=new TextureRect(exploTextureId[1],LONG_R*3f,LONG_R*3f);
            trExplo[2]=new TextureRect(exploTextureId[2],LONG_R*3f,LONG_R*3f);
            trExplo[3]=new TextureRect(exploTextureId[3],LONG_R*3f,LONG_R*3f);
            trExplo[4]=new TextureRect(exploTextureId[4],LONG_R*3f,LONG_R*3f);
            trExplo[5]=new TextureRect(exploTextureId[5],LONG_R*3f,LONG_R*3f);
    		bullet=new Bullet(taperId,cylinderId);//创建子弹对象
    		cube=new Cube(cubeId,2,0.5f,1);//创建立方体对象

    		bfc=new BulletForControl(MySurfaceView.this,bullet,trExplo,-BULLET_OFFSET_X,BULLET_OFFSET_Y,BULLET_OFFSET_Z,VX,0.0f,0.0f);
    		alBFC.add(bfc);//加入列表中
    		bgt=new BulletGoThread(alBFC);
    		bgt.start();//开启线程

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
