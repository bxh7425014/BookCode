package wyf.swq;
import javax.microedition.khronos.egl.EGLConfig;					//引入相关包
import javax.microedition.khronos.opengles.GL10;					//引入相关包
import android.content.Context;										//引入相关包
import android.opengl.GLSurfaceView;								//引入相关包
import android.view.MotionEvent;									//引入相关包
public class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR=180.0f/320;		//角度缩放比例
	private SceneRenderer myRenderer;						//场景渲染器
	private boolean backFlag=false;							//是否打开背面剪裁的标志位
	private boolean smoothFlag=false;						//是否打开平面着色的标志位
	private boolean selfCulling=false;						//是否采用自定义卷绕顺序的标志位
	private float myPreviousY;								//上次屏幕上的触控位置的Y坐标
	private float myPreviousX;								//上次屏幕上的触控位置的X坐标
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myRenderer=new SceneRenderer();						//创建场景渲染器
		this.setRenderer(myRenderer);						//设置渲染器
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
	}
	public void setBackFlag(boolean flag){					//
		this.backFlag=flag;
	}
	public boolean isBackFlag(){							//
		return backFlag;
	}
	public void setSmoothFlag(boolean flag){				//
		this.smoothFlag=flag;
	}
	public boolean isSmoothFlag(){							//
		return smoothFlag;
	}
	public void setSelfCulling(boolean flag){				//
		this.selfCulling=flag;
	}
	public boolean isSelfCulling(){							//
		return selfCulling;
	}
	//触摸事件回调方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float y=event.getY();								//获得当前触点的Y坐标
		float x=event.getX();								//获得当前触点的X坐标
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			float dy=y-myPreviousY;							//滑动距离在y轴方向上的垂直距离
			float dx=x-myPreviousX;							//活动距离在x轴方向上的垂直距离
			myRenderer.tp.yAngle+=dx*TOUCH_SCALE_FACTOR;	//设置沿y轴旋转角度
			myRenderer.tp.zAngle+=dy*TOUCH_SCALE_FACTOR;	//设置沿z轴旋转角度
			requestRender();								//渲染画面
		}
		myPreviousY=y;										//
		myPreviousX=x;										//
		return true;
	}
	private class SceneRenderer  implements GLSurfaceView.Renderer{
		TrianglePair tp=new TrianglePair();
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			if(backFlag){
				gl.glEnable(GL10.GL_CULL_FACE);				//设置为打开背面剪裁	
			}
			else{
				gl.glDisable(GL10.GL_CULL_FACE);			//设置为关闭背面剪裁
			}
			
			if(smoothFlag){
				gl.glShadeModel(GL10.GL_SMOOTH);			//设置着色模型为平滑着色
			}
			else{
				gl.glShadeModel(GL10.GL_FLAT);				//设置着色模型为不平滑着色
			}
			
			if(selfCulling){
				gl.glFrontFace(GL10.GL_CW);				//设置自定义卷绕顺序为顺时针为正面
			}
			else{
				gl.glFrontFace(GL10.GL_CCW);			//设置自定义卷绕顺序为逆时针为正面
			}
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//清除缓存
			gl.glMatrixMode(GL10.GL_MODELVIEW);						//设置当前矩阵为模式矩阵
			gl.glLoadIdentity();									//设置当前矩阵为单位矩阵
			gl.glTranslatef(0, 0, -2.0f);							//
			tp.drawSelf(gl);										//
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			 gl.glViewport(0, 0, width, height);					//
			 gl.glMatrixMode(GL10.GL_PROJECTION);					//
			 gl.glLoadIdentity();									//
			 float ratio=(float)width/height;						//
			 gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);			//
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			gl.glDisable(GL10.GL_DITHER);									//关闭抗抖动
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式
			gl.glClearColor(0, 0, 0, 0);									//设置屏幕背景色为黑色
			gl.glEnable(GL10.GL_DEPTH_TEST);								//启用深度检测	
		}}}
