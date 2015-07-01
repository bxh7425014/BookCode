package wyf.jsc.rtb;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static wyf.jsc.rtb.Constant.*;

public class MainMenu extends SurfaceView implements SurfaceHolder.Callback {
	MainActivity activity;
	Paint paint;
	Bitmap background;
	Bitmap td;
	Bitmap startgame;
	Bitmap setting;
	Bitmap help;
	Bitmap about;
	Bitmap exit;
	MenuThread menuThread;
	ThreeDThread threedthread;
	public float Dx=0;
	public float Dy=-200;//²âÊÔ¶¯Ì¬3d£¡£¡£¡
	public static float choserX0=0;
	public static float choserY0=240;
	public static float choserX1=-20;
	public static float choserY1=270;
	public static float choserX2=-30;
	public static float choserY2=300;
	public static float choserX3=-40;
	public static float choserY3=330;
	public  static float choserX4=-50;
	public static float choserY4=360;
	public float YY0;
	public float YY1;
	public float YY2;
	public float YY3;
	public float YY4;
	public float x_X;
	public float plY=16;
	public int widths;
	public int heights;
	public MainMenu(MainActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		this.getHolder().addCallback(this);
		
		background=BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
		td=BitmapFactory.decodeResource(this.getResources(), R.drawable.d);
		startgame=BitmapFactory.decodeResource(this.getResources(), R.drawable.startgame);
		setting=BitmapFactory.decodeResource(this.getResources(), R.drawable.setting);
		about=BitmapFactory.decodeResource(this.getResources(), R.drawable.about);
		help=BitmapFactory.decodeResource(this.getResources(), R.drawable.help);
		exit=BitmapFactory.decodeResource(this.getResources(), R.drawable.exit);
		paint=new Paint();
		paint.setAntiAlias(true);
		menuThread=new MenuThread(this);
		threedthread=new ThreeDThread(this);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		
		
		switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			int x=(int)e.getX();
			int y=(int)e.getY();	
			
			if(x>0&&x<x_X&&y>YY0&&y<YY0+plY)
			{
				status=0;
			}
			else if(x>0&&x<x_X&&y>YY1&&y<YY1+plY)
			{
				status=1;
			}
			else if(x>0&&x<x_X&&y>YY2&&y<YY2+plY)
			{
				status=2;
			}
			else if(x>0&&x<x_X&&y>YY3&&y<YY3+plY)
			{
				status=3;
			}
			else if(x>0&&x<x_X&&y>YY4&&y<YY4+plY)
			{
				status=4;
			}
			switch(status)
			{
				case 0:choserX0=0;choserX1=-20;choserX2=-30;choserX3=-40;choserX4=-50;keyStatus=status;
						activity.toAnotherView(START_GAME);	break;
				case 1:choserX0=-40;choserX1=0;choserX2=-30;choserX3=-40;choserX4=-50;keyStatus=status;
						activity.toAnotherView(ENTER_SETTING);break;
				case 2:choserX0=-40;choserX1=-20;choserX2=0;choserX3=-40;choserX4=-50;keyStatus=status;
						surfaceId=0;activity.toAnotherView(ENTER_HELP);
						break;
				case 3:choserX0=-40;choserX1=-20;choserX2=-30;choserX3=0;choserX4=-50;keyStatus=status;
						surfaceId=1;activity.toAnotherView(ENTER_HELP);break;
				case 4:choserX0=-40;choserX1=-20;choserX2=-30;choserX3=-40;choserX4=0;keyStatus=status;
							System.exit(0);break;
			}
									break;
		case MotionEvent.ACTION_UP:
								
									break;
		}

		return true;
		
	}
	@Override
	public boolean onKeyUp(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			return false;
		}
		if(keyCode==19||keyCode==20||keyCode==23)
		{
			switch(keyCode)
			{
				case 19:
						switch(keyStatus)
						{
						case 0:keyStatus=4;choserX0=-40;choserX4=0;break;
						case 1:keyStatus=0;choserX1=-20;choserX0=0;break;
						case 2:keyStatus=1;choserX2=-30;choserX1=0;break;
						case 3:keyStatus=2;choserX3=-40;choserX2=0;break;
						case 4:keyStatus=3;choserX4=-50;choserX3=0;break;
						}
					break;
				case 20:
						switch(keyStatus)
						{
						case 0:keyStatus=1;choserX0=-40;choserX1=0;break;
						case 1:keyStatus=2;choserX1=-20;choserX2=0;break;
						case 2:keyStatus=3;choserX2=-30;choserX3=0;break;
						case 3:keyStatus=4;choserX3=-40;choserX4=0;break;
						case 4:keyStatus=0;choserX4=-50;choserX0=0;break;
						}
					break;
				case 23:
						switch(keyStatus)
						{
						case 0:break;
						case 1:break;
						case 2:surfaceId=0;break;
						case 3:surfaceId=1;break;
						case 4:System.exit(0);break;
						}
			}
		}
		return true;
		}
	
	@Override
	public void onDraw(Canvas canvas)
	{	
			canvas.drawBitmap(background,0, 0, paint);
			canvas.drawBitmap(td,Dx, Dy,paint);
			canvas.drawBitmap(startgame,choserX0 , choserY0,paint);
			canvas.drawBitmap(setting,choserX1 , choserY1,paint);
			canvas.drawBitmap(help,choserX2 , choserY2,paint);
			canvas.drawBitmap(about,choserX3 , choserY3,paint);
			canvas.drawBitmap(exit,choserX4 , choserY4,paint);	
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		widths=width;
		heights=height;
		 YY0=240;
		 YY1=280;
		 YY2=310;
		 YY3=340;
		 YY4=370;
		 x_X=100;
		 Canvas canvas=holder.lockCanvas();
			try
			{
				synchronized(holder)
				{
					onDraw(canvas);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(canvas!=null)
				{
					holder.unlockCanvasAndPost(canvas);
				}
			}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {		
		threedthread.start();
		menuThread.start();	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		threadFlag=false;
		
	}

}
