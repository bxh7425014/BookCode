package wyf.sj;
import java.io.IOException;
import java.io.InputStream;
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

private SceneRenderer mRenderer;//场景渲染器
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		int lqcTexId;//篮球场纹理
		//int lqcTMTexId;//篮球场透明纹理  ，用来 绘制比较真实的效果
		int basketballTexId;//篮球纹理
		
		//TextureRect tr;//半透明反射面，用来 绘制比较真实的效果
		TextureRect trL;//普通反射面
    	BallTextureByVertex btbv;//用于绘制的球
    	BallForControl bfd;//用于控制的球
    	
        public void onDrawFrame(GL10 gl) {
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
            		0.0f,   //人眼位置的X
            		7.0f, 	//人眼位置的Y
            		7.0f,   //人眼位置的Z
            		0, 	//人眼球看的点X
            		0f,   //人眼球看的点Y
            		0,   //人眼球看的点Z
            		0, 
            		1, 
            		0
            );   
            gl.glTranslatef(0, -2, 0);
            trL.drawSelf(gl);//绘制反射面
            bfd.drawSelfMirror(gl);//绘制镜像体
            //tr.drawSelf(gl);//绘制半透明反射面，用来 绘制比较真实的效果
            bfd.drawSelf(gl);//绘制实际物体 
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
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 100);           
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);            
            //启用深度测试
            //gl.glEnable(GL10.GL_DEPTH_TEST);
            //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            
            //初始化纹理
            lqcTexId=initTexture(gl,R.drawable.mdb);
            basketballTexId=initTexture(gl,R.drawable.basketball);
            //lqcTMTexId=initTexture(gl,R.drawable.mdbtm);//用来 绘制比较真实的效果
            //创建半透明篮球 板
            //tr=new TextureRect(4,2.568f,lqcTMTexId);//用来 绘制比较真实的效果
            //创建普通不透明篮球板
            trL=new TextureRect(4,2.568f,lqcTexId);
            //创建用于绘制的球
            btbv=new BallTextureByVertex(BALL_SCALE,basketballTexId);
            //创建用于控制的球
            bfd=new BallForControl(btbv,3f);
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
