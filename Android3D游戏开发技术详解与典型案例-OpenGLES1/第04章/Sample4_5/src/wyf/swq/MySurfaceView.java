package wyf.swq;
import android.opengl.GLSurfaceView;						//引入相关包
import android.view.MotionEvent;							//引入相关包
import javax.microedition.khronos.egl.EGLConfig;			//引入相关包
import javax.microedition.khronos.opengles.GL10;			//引入相关包
import android.content.Context;								//引入相关包
class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;	//角度缩放比例
    private SceneRenderer mRenderer;						//场景渲染器
	public boolean isPerspective=false;						//投影标志位
	private float mPreviousY;								//上次的触控位置Y坐标
    public float xAngle=0;									//整体绕x轴旋转的角度  
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();					//创建场景渲染器
        setRenderer(mRenderer);								//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//主动渲染   
    }
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        switch (e.getAction()) {							//获取动作
        case MotionEvent.ACTION_MOVE:						//判断是否是滑动
            float dy = y - mPreviousY;						//计算触控笔Y位移
            xAngle+= dy * TOUCH_SCALE_FACTOR;				//设置沿x轴旋转角度
            requestRender();								//重绘画面
        }
        mPreviousY = y;										//作为上一次触点的Y坐标
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer { 
		Hexagon[] ha=new Hexagon[]{							//六边形数组
			new Hexagon(0),
			new Hexagon(-2),
			new Hexagon(-4),
			new Hexagon(-6),
			new Hexagon(-8),
			new Hexagon(-10),
			new Hexagon(-12),
	    };
    	public SceneRenderer(){}						//渲染器构造类
    	@Override
        public void onDrawFrame(GL10 gl) {  
            gl.glMatrixMode(GL10.GL_PROJECTION);		//设置当前矩阵为投影矩阵
            gl.glLoadIdentity(); 						//设置当前矩阵为单位矩阵        
            float ratio = (float) 320/480;				//计算透视投影的比例
            if(isPerspective){
                gl.glFrustumf(-ratio, ratio, -1, 1, 1f, 10);//调用此方法计算产生透视投影矩阵
            }
            else{
            	gl.glOrthof(-ratio, ratio, -1, 1, 1, 10);//调用此方法计算产生正交投影矩阵
            }
			gl.glEnable(GL10.GL_CULL_FACE);				//设置为打开背面剪裁	
	        gl.glShadeModel(GL10.GL_SMOOTH);    		//设置着色模型为平滑着色       	
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除缓存
            gl.glMatrixMode(GL10.GL_MODELVIEW);			//设置当前矩阵为模式矩阵
            gl.glLoadIdentity();    			 		//设置当前矩阵为单位矩阵
            
            gl.glTranslatef(0, 0f, -1.4f); 				//沿z轴向远处推
            gl.glRotatef(xAngle, 1, 0, 0);				//绕x轴旋转制定角度
            
            for(Hexagon th:ha){
            	th.drawSelf(gl);				//循环绘制六边形数组中的每个六边形
            }
        }
    	@Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
        	gl.glViewport(0, 0, width, height); //设置视窗大小及位置              
        }
    	@Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {    
        	gl.glDisable(GL10.GL_DITHER);								//关闭抗抖动 
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置Hint模式
            gl.glClearColor(0,0,0,0);									//设置屏幕背景色黑色            
            gl.glEnable(GL10.GL_DEPTH_TEST);							//启用深度测试
        }}}
