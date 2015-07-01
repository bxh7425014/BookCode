package wyf.tzz.gdl;
import static wyf.tzz.gdl.Constant.*;
public class ActionThread extends Thread{
	MySurfaceView msv;			 //主界面
	public boolean beginDown=false;//标志箱子是否已经开始下落
	BoxGroup bg;				//箱子管理组
	Line line;					//绳子
	SingleBox singleBox;		//绳子下面的箱子	
	SingleBox singleBox2;		//大楼上最上面的箱子
	float lineHeight=BASE_HEIGHT+LINE_LENGTH+LINE_OFF_BOX;	//线上端点初始的高度
	float downSpan=0.01f;		//箱子每次下降高度
	boolean rotateLeft=false;	//箱子是否左转
	boolean rotateRight=false;	//箱子是否右转
	float rotateAngle=0;		//箱子旋转角度
	float angleSpan=downSpan/BOX_SIZE*180;			//箱子每次旋转角度
	float xSpan=downSpan/BOX_SIZE*BASE_LENGTH/2;	//箱子旋转时每次在x轴方向平移的距离
	public ActionThread(BoxGroup bg,Line line,MySurfaceView msv){
		this.bg=bg;
		this.line=line;	
		this.msv=msv;
		line.mOffsetY=lineHeight;		//将线向上平移
	}
	@Override
	public void run(){
		boolean flag=true;				//flag 标志绳子向左还是向右摆动
		
		while(msv.beginFlag){			//beginFlag是否为true，是否开始
			singleBox=bg.al.get(bg.al.size()-1);//找到al中最上面的SingleBox，就是随着绳子转动的箱子			
			if(!beginDown){				//箱子还没有开始下落时，跟随绳子摆动
				//箱子随着绳子一起绕着Z轴转动,单摆运动		
				if(flag)//向右摆动
				{
					line.mAngleZ+=1;	//角度加一
					
					if(line.mAngleZ>=LINE_ANGLE)	//如果角度大于LINE_ANGLE开始左摆
					{
						flag=false;
					}
				}
				else					//向左摆动
				{
					line.mAngleZ-=1;	//角度减一
					
					if(line.mAngleZ<=-LINE_ANGLE)	//如果角度小于-LINE_ANGLE开始右摆
					{
						flag=true;
					}
				}
				calculateBoxXYZ();		//计算箱子中心店的坐标
				try
				{
					Thread.sleep(100);	//睡眠100ms
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else						//beginDown为false；箱子已经开始下落后
			{
				//箱子开始下落
				singleBox.y-=downSpan;
				collisionTest();		//碰撞检测，是否成功降落在大楼上
				if(rotateLeft)			//箱子左转
				{
					boxRotateLeft();
				}
				if(rotateRight)			//箱子右转
				{
					boxRotateRight();
				}
				msv.objectCount=bg.al.size()-1;		//更新分数				
				try
				{
					Thread.sleep(5);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public void calculateBoxXYZ()		//计算箱子中心的XYZ坐标
	{
		//单摆运动
		//箱子x坐标
		singleBox.x=(float)(LINE_LENGTH*Math.sin(Math.toRadians(line.mAngleZ)));
		
		//箱子y坐标
		singleBox.y=(float)(line.mOffsetY-(LINE_LENGTH*Math.cos(Math.toRadians(line.mAngleZ)))-BOX_SIZE/2);
	}
	public void collisionTest()			//碰撞检测
	{
		if(bg.al.size()>1)				//singleBox数量多于一个时
		{
			singleBox2=bg.al.get(bg.al.size()-2);				//取出大楼上最上面的箱子
			if(	singleBox.x>=singleBox2.x-BOX_SIZE&&			
					singleBox.x<=singleBox2.x+BOX_SIZE)			//箱子中心落在最上面的箱子上即可成功
			{
				if(singleBox.x>=singleBox2.x-BOX_SIZE/2&&
						singleBox.x<=singleBox2.x+BOX_SIZE/2)	//能够安全落在大楼上，降落成功
				{	
					if((singleBox.y-singleBox2.y-BOX_SIZE)<=0f&&
							(singleBox.y-singleBox2.y-BOX_SIZE)>-0.01f)		//箱子碰到最上面的箱子时
						{
							bg.al.add(new SingleBox(bg));		//成功之后，重新生成一个新的箱子加入箱子管理组
							beginDown=false;					//下一轮，绳子开始摆动，箱子停止下落
							line.mOffsetY+=BOX_SIZE;			//绳子的y坐标上升
							msv.targetPointY+=BOX_SIZE;			//目标点坐标上升，摄像机y坐标上升
							msv.activity.playSound(1, 0);		//播放成功降落的音乐
							return;
						}
				}
				else if((singleBox.y-singleBox2.y-BOX_SIZE)<=0f&&
							(singleBox.y-singleBox2.y)>=0f)		//碰到但没有成功落下时
					{
						if(singleBox.x<singleBox2.x)			//箱子落在偏左边
						{
							rotateLeft=true;
							return;
						}
						if(singleBox.x>singleBox2.x)			//箱子落在偏右边
						{
							rotateRight=true;
							return;
						}
					}
			}
			//箱子触地
			if(singleBox.y<=BOX_SIZE/2||singleBox.y<line.mOffsetY-LINE_LENGTH-TEST_LENGTH)	
			{				
				beginDown=false;							//下一轮，绳子摆动				
				singleBox.mAngleZ=0;						//箱子绕z轴旋转角度置为0
				rotateAngle=0;								//箱子旋转角度坐角度置为0
				singleBox.x=0;								//箱子沿x轴平移坐标置为0
				msv.failCount++;
				return;
			}
		}
		else												//singleBox数量等于一个时								
		{
			if(singleBox.y<=BASE_HEIGHT+BOX_SIZE/2&&
					singleBox.x>=-BASE_LENGTH/2&&
					singleBox.x<=BASE_LENGTH/2)				//箱子碰到基台
				{				
					bg.al.add(new SingleBox(bg));			//成功之后，重新生成一个新的箱子加入箱子管理组
					beginDown=false;						//下一轮，绳子摆动	
					line.mOffsetY+=BOX_SIZE;				//绳子的y坐标上升
					msv.targetPointY+=BOX_SIZE;				//目标点坐标上升，摄像机y坐标上升
					msv.activity.playSound(1, 0);			//播放成功降落的音乐
					return;
				}
		}
			if(singleBox.y<=BOX_SIZE/2)						//触地
			{				
				beginDown=false;							//下一轮，绳子摆动	//return true;		
				msv.failCount++;
				return;
			}
	}
	public void boxRotateLeft()
	{
		if(singleBox.y<=singleBox2.y)						//当箱子y坐标小于最上面箱子y之后
		{
			rotateLeft=false;								//停止向左旋转
			msv.activity.playSound(2, 0);					//播放失败音乐
			return;
		}
		singleBox.x-=xSpan;					//箱子向左平移
		rotateAngle+=angleSpan;				//箱子沿z轴旋转角度加上angleSpan
		singleBox.mAngleZ=rotateAngle;		//箱子沿z轴旋转角度
	}
	public void boxRotateRight()			//箱子向左旋转
	{
		if(singleBox.y<=singleBox2.y)		//当箱子y坐标小于最上面箱子y之后
		{
			rotateRight=false;				//停止向右旋转
			msv.activity.playSound(2, 0);	//播放失败音乐
			return;
		}
		singleBox.x+=xSpan;					//箱子向右平移
		rotateAngle-=angleSpan;				//箱子沿z轴旋转角度减去angleSpan
		singleBox.mAngleZ=rotateAngle;		//箱子沿z轴旋转角度
	}
}