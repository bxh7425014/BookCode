package wyf.sj;
import javax.microedition.khronos.opengles.GL10;
import static wyf.sj.Constant.*;

public class BallForControl {


	public static final float TIME_SPAN=0.05f;//单位时间间隔
	public static final float G=0.8f;//重力加速度
	
	BallTextureByVertex btv;//用于绘制的篮球
	float high;//最高点位置
	float timeLive=0;//此周期存活时长
	float currentY=0;//当前Y位置
	float vyMax=0;//最大速度
	float currentVy;//当前速度	
	boolean isDown=true;//是否为下降阶段 
	
	public BallForControl(BallTextureByVertex btv,float highl)
	{
		this.btv=btv;
		this.high=highl;		
		currentY=highl;
		new Thread()
		{//开启一个线程运动篮球
			public void run()
			{
				while(true)
				{
					if(isDown)
					{//下降阶段
						if(currentY>0)
						{//若没有到地板则继续下降
							currentY=high-0.5f*G*timeLive*timeLive;
							timeLive+=TIME_SPAN;
						}
						else
						{//若到地板则反弹
							vyMax=G*timeLive;//计算反弹速度
							isDown=false;//下降标志位置反
							timeLive=0;//下降时间置0
							currentVy=vyMax;//当前速度
							vyMax=vyMax*0.8f;//当前最大速度
						}
					}
					else
					{//上升阶段
						if(vyMax<0.35f)
						{//若速度小于阈值则停止运动
							currentY=0;//球位移置0
							break;
						}
						
						if(currentVy>0)
						{//若速度大于零则继续上升
							currentY=vyMax*timeLive-0.5f*G*timeLive*timeLive;//上升位移
							timeLive+=TIME_SPAN;//运动时间
							currentVy=vyMax-G*timeLive;//当前速度
						}
						else
						{//若速度小于等于0则进入下降周期
							timeLive=0;//时间置0
							isDown=true;//下降标志位设true
							high=currentY;//当前最高点
						}
					}
					
					try
					{
						Thread.sleep(20);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void drawSelf(GL10 gl)
	{//绘制物体自己		
		gl.glPushMatrix();
		gl.glTranslatef(0, UNIT_SIZE*BALL_SCALE+currentY, 0);
		btv.drawSelf(gl);		
		gl.glPopMatrix();
	}
	
	public void drawSelfMirror(GL10 gl)
	{//绘制 镜像体
		gl.glPushMatrix();
		gl.glTranslatef(0, -UNIT_SIZE*BALL_SCALE-currentY, 0);
		btv.drawSelf(gl);		
		gl.glPopMatrix();
	}
}
