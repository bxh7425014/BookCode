package wyf.zcl;
import android.content.Context;					//引入相关包
import android.graphics.Bitmap;					//引入相关包
import android.graphics.BitmapFactory;			//引入相关包
import android.graphics.Canvas;					//引入相关包
import android.graphics.Color;					//引入相关包
import android.graphics.Paint;					//引入相关包
import android.view.Display;					//引入相关包
import android.view.SurfaceHolder;				//引入相关包
import android.view.SurfaceView;				//引入相关包
public class MySurfaceView extends SurfaceView
implements SurfaceHolder.Callback{			
	//此处实现SurfaceHolder.Callback接口，为surfaceView添加生命周期回调函数
	int dy=Display.DEFAULT_DISPLAY;
	MyActivity ma;							//得到MyActivity的引用
	Paint paint;							//画笔的引用
	OnDrawThread odt;						//OnDrawThread类引用
	PicRunThread prt;						//图片运动的Thread类引用
	private float picX=0;						//图片x坐标
	private float picY=0;						//图片y坐标
	boolean picAlphaFlag=false;					//图片变暗效果的标记，false为不显示，true为显示。
	int picAlphaNum=0;							//图片变暗效果中画笔的alpha值
	public MySurfaceView(Context context) {
		super(context);
		this.ma=(MyActivity) context;			
		//将ma的引用指向调用了该Surfaceview类构造器方法的对象，本例为MyActivity
		this.getHolder().addCallback(this);		//注册回调接口
		paint=new Paint();						//实例化画笔
		odt=new OnDrawThread(this);				//实例化OnDrawThread类
		prt=new PicRunThread(this);				//实例化PicRunThread类
		prt.start();
	}
	public void setPicX(float picX) {			//图片x坐标的设置器
		this.picX = picX;
	}
	public void setPicY(float picY) {			//图片y坐标的设置器
		this.picY = picY;
	}
	public void setPicAlphaNum(int picAlphaNum) {//图片变暗效果alpha参数设置器
		this.picAlphaNum = picAlphaNum;
	}
	@Override
	protected void onDraw(Canvas canvas) {	//onDraw方法，此方法用于绘制图像，图形等
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);		//设置画笔为白色
		canvas.drawRect(0, 0, Constant.SCREENWIDTH, Constant.SCREENHEIGHT, paint);
		//此处画了一个白色的全屏幕的矩形，目的是设置背景为白色，同时每次重绘时清除背景
		//进行平面贴图
		Bitmap bitmapDuke=BitmapFactory.decodeResource(ma.getResources(), R.drawable.duke);
		canvas.drawBitmap(bitmapDuke, picX, picY, paint);
		//图片渐暗效果
		if(picAlphaFlag){
			Bitmap bitmapBG=BitmapFactory.decodeResource(ma.getResources(), R.drawable.jpg1);
			paint.setAlpha(picAlphaNum);
			canvas.drawBitmap(bitmapBG, 0,0, paint);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {			//此方法为当surfaceView改变时调用，如屏幕大小改变。
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//此方法为在surfaceView创建时调用
		odt.start();				//启动onDraw的绘制线程
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//此方法为在surfaceView销毁前调用
	}
}
