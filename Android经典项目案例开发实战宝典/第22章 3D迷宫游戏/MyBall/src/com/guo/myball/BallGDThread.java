package com.guo.myball;
import static com.guo.myball.Constant.*;
import static com.guo.myball.GameSurfaceView.*;
//小球运动线程
public class BallGDThread extends Thread
{
	//surfaceView的引用
	GameSurfaceView gameSurface;
	//线程标志位
	public Boolean flag=true;
	//每次走的时间
	public static float t=0.1f;
	//总计时间，每一关的，从开始到完成开始计时
	public static int ZJS_Time;
	public static float ballRD;
	//加速度
	public float ballGX;
	public float ballGZ;	
	
	int ballXx=0;//此格所在的行列
	int ballZz=0;
	public static Boolean flagSY=true;//判断是否掉进洞标志      
	public BallGDThread(GameSurfaceView gameSurface)
	{
		this.gameSurface=gameSurface;
		ballRD=ballR/2;
	}
	@Override   
	public void run()
	{
		while(flag)
		{
			ballGX=GameSurfaceView.ballGX;//拷贝加速度
			ballGZ=GameSurfaceView.ballGZ;
			try{
			//判断碰撞情况方法
			PDPZ(ballX,ballZ,ballVX*t+ballGX*t*t/2,ballVZ*t+ballGZ*t*t/2);
			}catch (Exception tt) {
				tt.printStackTrace();
			}
			ballVX+=ballGX*t;
			ballVZ+=ballGZ*t;//最终速度
			
			ballX=ballX+ballVX*t+ballGX*t*t/2;//VT+1/2A*T*T
			ballZ=ballZ+ballVZ*t+ballGZ*t*t/2;//最终位置		
			gameSurface.ball.mAngleX+=(float)Math.toDegrees(((ballVZ*t+ballGZ*t*t/2))/ballR);
			gameSurface.ball.mAngleZ-=(float)Math.toDegrees((ballVX*t+ballGX*t*t/2)/ballR);//旋转的角度
			if(Math.abs((ballVZ*t+ballGZ*t*t/2))<0.005f)//如果当前前进值小于调整值，则相应的转动方向角归零
			{
				gameSurface.ball.mAngleX=0;
			}
			if(Math.abs(ballVX*t+ballGX*t*t/2)<0.005f)
			{
				gameSurface.ball.mAngleZ=0;
			}
			//判断进洞函数，及相应的操作
			PDJD();			
			if(!flagSY)//如果掉进洞里了
			{
				flagSY=true;//初始到开始
				if(ballXx==ballMbX&&ballZz==ballMbZ)//如果是赢了
				{
					try 
					{
						Thread.sleep(1000);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					flag=false;
					gameSurface.father.curr_grade=GD_TIME[guankaID]-STIME;
					gameSurface.father.hd.sendEmptyMessage(1);//进入赢的界面
				}
				else//否则是进洞了
				{
					try
			        {
						Thread.sleep(1000);//停顿1秒
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=ballR;
				}
				ballVX=0;
				ballVZ=0;//最终速度都减为零
			}		
			ballVX*=V_TENUATION;
			ballVZ*=V_TENUATION;//衰减
			if(Math.abs(ballVX)<0.04)//当速度小于某个调整值时
			{
				ballVX=0;//速度归零
				gameSurface.ball.mAngleZ=0;//将绕轴旋转的值置为零
			}
			if(Math.abs(ballVZ)<0.04)
			{
				ballVZ=0;
				gameSurface.ball.mAngleX=0;
			}			
			//每局的总时间
			ZJS_Time+=50;
			//走过的时间秒数
			STIME=(ZJS_Time/1000);
			//如果时间减到零，说明没有通过
			if(GD_TIME[guankaID]-STIME<=0)
			{
				flag=false;
				gameSurface.father.hd.sendEmptyMessage(2);
			}
			try //休息50毫秒，进入下一次循环
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	//初始化地图方法
	public static void initDiTu()
	{
		guankaID%=MAPP.length;//防止数组越界
        ballY=0;
        MAP=MAPP[guankaID];//地图数组
        
        Wall walll=new Wall();//新建地图
        try
        {
			Thread.sleep(1000);//停顿1秒
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}		
		wall=walll;//将新建地图付给当前场景绘制地图
		ballCsX=CAMERA_COL_ROW[guankaID][0];//初始球所在的初始行列
        ballCsZ=CAMERA_COL_ROW[guankaID][1];
        
        ballMbX=CAMERA_COL_ROW[guankaID][2];//小球目标行列
        ballMbZ=CAMERA_COL_ROW[guankaID][3];
		MAP_OBJECT=MAP_OBJECTT[guankaID];//该地图的洞数组
		//将球画到初始位置
        ballX=ballCsX*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
		ballZ=ballCsZ*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
		STIME=GD_TIME[guankaID];//限制时间，还原
		ZJS_Time=0;//总时间归零
	}
	//判断碰撞
	public Boolean PDPZ(float ballX,float ballZ,float BX,float BZ)
	{
		Boolean flag=false;
		ballX=MAP[0].length*UNIT_SIZE/2+ballX;//将地图移到XZ都大于零的象限
		ballZ=MAP.length*UNIT_SIZE/2+ballZ;
		if(BZ>0)//如果向Z轴正方向运动
		{
			for(int i=(int)((ballZ+ballR)/UNIT_SIZE);i<=(int)((ballZ+ballR+BZ)/UNIT_SIZE);i++)
				//循环，假如它一下穿过几个格子，那么从第一个格子开始判断
			{
				if(MAP[i][(int)(ballX/UNIT_SIZE)]==BKTG&&MAP[i-1][(int)(ballX/UNIT_SIZE)]==KTG){//判断是否碰墙壁了
					ballVZ=-ballVZ*VZ_TENUATION;//将速度置反，并调整
					//如果速度调反后还是会穿墙，那么将加速度归零，并将球画在和墙壁紧挨着的地方
					if((GameSurfaceView.ballZ+ballVZ*t+ballGZ*t*t/2)>=(i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2))
					{
						GameSurfaceView.ballZ=(i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2);
						ballVZ=0;
						ballGZ=0;
					}
					else{					
					gameSurface.father.playSound(1, 0);//播放移动声音
					}
					flag=true;//标志位置为true
				}
				else if(BX<=0&&((int)((ballX-ballR)/UNIT_SIZE)>=0)&&(MAP[i][(int)((ballX-ballR)/UNIT_SIZE)]==BKTG)
						&&MAP[i-1][(int)((ballX-ballR)/UNIT_SIZE)]==KTG)//如果是球的Z负方向边碰角，可能会碰到角
				{
					float sina=(ballX-((int)((ballX-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//得到碰角时的角度相关值
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballGX=0;
					ballGZ=0;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){//如果碰撞很大，则播放声音
					gameSurface.father.playSound(1, 0);//播放移动声音
					}else if(Math.abs(ballVZ)<SD_TZZ)//如果速度小于调整值，则不弹起，而且速度值为零
					{
						GameSurfaceView.ballZ=i*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}else if(Math.abs(ballVX)<SD_TZZ)//如果速度小于调整值，则不弹起，而且速度值为零
					{
						GameSurfaceView.ballX=(1+i)*UNIT_SIZE-ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}
					flag=true;
				}
				else if(BX>=0&&((int)((ballX+ballR)/UNIT_SIZE)>=0)&&MAP[i][(int)((ballX+ballR)/UNIT_SIZE)]==BKTG
						&&MAP[i-1][(int)((ballX+ballR)/UNIT_SIZE)]==KTG){//如果是Z正方向运动时碰角了
					float sina=(ballX-((int)((ballX+ballR)/UNIT_SIZE)*UNIT_SIZE))/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){
						gameSurface.father.playSound(1, 0);//播放移动声音
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
			}	
		}
		
		if(BX>0)//如果向X轴正方向走
		{
			for(int i=(int)((ballX+ballR)/UNIT_SIZE);i<=(int)((ballX+ballR+BX)/UNIT_SIZE);i++)
			{//循环，假如它一下穿过几个格子，那么从第一个格子开始判断
				if(MAP[(int)(ballZ/UNIT_SIZE)][i]==BKTG&&MAP[(int)(ballZ/UNIT_SIZE)][i-1]==KTG){//如果碰壁了			
					
					ballVX=-ballVX*VZ_TENUATION;//速度置反，并调整
					if((GameSurfaceView.ballX+ballVX*t+ballGX*t*t/2)>
					((i)*UNIT_SIZE-ballR-MAP[0].length*UNIT_SIZE/2))//如果已经紧贴墙壁了，那么速度为零
					{
						GameSurfaceView.ballX=(i)*UNIT_SIZE-ballR-MAP[0].length*UNIT_SIZE/2;
						ballGX=0;//加速度和速度设置为零
						ballVX=0;
					}
					else
					{						
					gameSurface.father.playSound(1, 0);//播放移动声音
					}
					if(ballGX>0)//速度小于调整值则归零
					{
						ballGX=0;
					}
					 flag=true;
					 
				}
				else if(BZ<=0&&((int)((ballZ-ballR)/UNIT_SIZE)>=0)&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i]==BKTG)
						&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i-1]==KTG))//球的左边撞角
				{
					float sina=(ballZ-((int)((ballZ-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//得到相关角度的正弦值和余弦值
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ){//速度达到一定大后才播放声音和震动
						gameSurface.father.playSound(1, 0);//播放移动声音
						}else{
							ballGX=0;//速度归零
							ballGZ=0;
						}
					flag=true;
				}
				else if(BZ>=0&&((int)((ballZ+ballR)/UNIT_SIZE)>=0)&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i]==BKTG
						&&(MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i-1]==KTG)){//如果右边碰角
					float sina=-(ballZ-((int)((ballZ+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;//得到相关值
					float cosa=(float)Math.sqrt(1-sina*sina);
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)
					{
						gameSurface.father.playSound(1, 0);//播放移动声音
						}else{
							ballGX=0;
							ballGZ=0;//否则速度归零
						}
					flag=true;
				}
			}		
		}
		 if(BX<0)
		{
			for(int i=(int)((ballX-ballR)/UNIT_SIZE);i>=(int)((ballX-ballR+BX)/UNIT_SIZE);i--)
			{//循环判断是否碰壁
				if(MAP[(int)(ballZ/UNIT_SIZE)][i]==BKTG&&MAP[(int)(ballZ/UNIT_SIZE)][i+1]==KTG)
				{//如果碰壁
					
						ballVX=-ballVX*VZ_TENUATION;//速度置反并调整，
						if((GameSurfaceView.ballX+ballVX*t+ballGX*t*t/2)<
						((1+i)*UNIT_SIZE+ballR-MAP[0].length*UNIT_SIZE/2))//如果已经紧贴墙壁了，那么速度归零
						{
							GameSurfaceView.ballX=(1+i)*UNIT_SIZE+ballR-MAP[0].length*UNIT_SIZE/2;
							ballGX=0;//加速度和速度设置为零
							ballVX=0;
						}
						else
						{						
						gameSurface.father.playSound(1, 0);//播放移动声音
						}
					
					if(ballVX<0)
					{
						ballVX=-ballVX;
					}
					flag=true;
				}
				else if(BZ<=0&&((int)((ballZ-ballR)/UNIT_SIZE)>=0)&&(MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i]==BKTG)
						&&MAP[(int)((ballZ-ballR)/UNIT_SIZE)][i+1]==KTG)//球左边撞角
				{
					float sina=(ballZ-((int)((ballZ-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//得到相关的值
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//看是否要播放声音
					{
						gameSurface.father.playSound(1, 0);//播放移动声音
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
				else if(BZ>=0&&((int)((ballZ+ballR)/UNIT_SIZE)>=0)&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i]==BKTG
						&&MAP[(int)((ballZ+ballR)/UNIT_SIZE)][i+1]==KTG){//如果右边碰角
					float sina=-(ballZ-((int)((ballZ+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//得到相关值
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=-jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//判断是否要播放声音
					{
						gameSurface.father.playSound(1, 0);//播放移动声音
						}else{
							ballGX=0;
							ballGZ=0;
						}
					flag=true;
				}
			}
		}
		
		if(BZ<0)//向Z轴负方向上运动时
		{
			for(int i=(int)((ballZ-ballR)/UNIT_SIZE);i>=(int)((ballZ-ballR+BZ)/UNIT_SIZE);i--)
			{//循环看是否碰壁了
				if(MAP[i][(int)(ballX/UNIT_SIZE)]==BKTG&&MAP[i+1][(int)(ballX/UNIT_SIZE)]==KTG){
					ballVZ=-ballVZ*VZ_TENUATION;//将速度置反，并调整
					if((GameSurfaceView.ballZ+ballVZ*t+ballGZ*t*t/2)<=((1+i)*UNIT_SIZE+ballR-MAP.length*UNIT_SIZE/2))
					{//看调整后的速度下，还是否会穿墙
						GameSurfaceView.ballZ=(1+i)*UNIT_SIZE+ballR-MAP.length*UNIT_SIZE/2;
						ballVZ=0;
						ballGZ=0;
					}
					else
					{					
					gameSurface.father.playSound(1, 0);//播放移动声音
					}					
					if(ballVZ<0)//如果速度还小于零，则置反
					{
						ballVZ=-ballVZ;
					}					
					flag=true;
				}
				else if(BX<=0&&((int)((ballX-ballR)/UNIT_SIZE)>=0)&&(MAP[i][(int)((ballX-ballR)/UNIT_SIZE)]==BKTG)
						&&MAP[i+1][(int)((ballX-ballR)/UNIT_SIZE)]==KTG)//左边碰角
				{
					float sina=(ballX-((int)((ballX-ballR)/UNIT_SIZE)+1)*UNIT_SIZE)/ballR;//得到角度相关值
					float cosa=(float)Math.sqrt(1-sina*sina);					
					ballVX=jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//判断是否要播放声音
					{
						gameSurface.father.playSound(1, 0);//播放移动声音
					}
					else
					{
							ballGX=0;
							ballGZ=0;
					}
					flag=true;
				}
				else if(BX>=0&&((int)((ballX+ballR)/UNIT_SIZE)<MAP[0].length)&&MAP[i][(int)((ballX+ballR)/UNIT_SIZE)]==BKTG
						&&MAP[i+1][(int)((ballX+ballR)/UNIT_SIZE)]==KTG){//右边碰角
					float sina=-(ballX-((int)((ballX+ballR)/UNIT_SIZE))*UNIT_SIZE)/ballR;
					float cosa=(float)Math.sqrt(1-sina*sina);//得到相关值
					ballVX=-jsSDX(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;//得到碰角后的速度
					ballVZ=jsSDZ(ballVX,ballVZ,cosa,sina)*VZ_TENUATION;
					if(Math.abs(ballVX)>SD_TZZ||Math.abs(ballVZ)>SD_TZZ)//看是否要播放声音
					{
						gameSurface.father.playSound(1, 0);//播放移动声音
					}
					else
					{
							ballGX=0;
							ballGZ=0;
					}
					flag=true;
				}
				
			}	
		}
		return flag;
	}
	//判断是否进洞
	public void PDJD()
	{
		//此格所在的对应的数组行列
		ballXx=(int)((MAP[0].length*UNIT_SIZE/2+ballX)/UNIT_SIZE);
		ballZz=(int)((MAP.length*UNIT_SIZE/2+ballZ)/UNIT_SIZE);
		//左上的格子是洞
		if(MAP_OBJECT[ballZz][ballXx]==1){
				if((float)Math.sqrt(
				(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
				+(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
				<ballR+ballRD//则判断球心是否在洞内
				){//掉进洞里了
					flagSY=false;//标志位
					ballX=ballXx*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;//将球画到洞里
					ballZ=ballZz*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
					ballY=0;
					
		}}
		else if(MAP_OBJECT[ballZz][ballXx+1]==1)//左下的格子是洞
		{
			if((float)Math.sqrt(
					(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-ballZz*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//则判断球心是否在洞内
					){
				flagSY=false;//掉进洞里了
				ballX=(1+ballXx)*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=ballZz*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=0;
				ballXx=ballXx+1;
			}
		}
		else if(MAP_OBJECT[ballZz+1][ballXx+1]==1){//右下的格子是洞
			if((float)Math.sqrt(
					(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-(1+ballXx)*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//则判断球心是否在洞内
					){
				flagSY=false;//掉进洞里了
				ballX=(1+ballXx)*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
				ballZ=(ballZz+1)*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
				ballY=0;
				ballXx=ballXx+1;
				ballZz=ballZz+1;
			}
		}
		else if(MAP_OBJECT[ballZz+1][ballXx]==1){//右上的格子是洞
			if((float)Math.sqrt(
					(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)*(ballX-ballXx*UNIT_SIZE+MAP[0].length*UNIT_SIZE/2)
					+(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2)*(ballZ-(1+ballZz)*UNIT_SIZE+MAP.length*UNIT_SIZE/2))
					<ballR+ballRD//则判断球心是否在洞内
					){
						flagSY=false;//掉进洞里了
						ballX=ballXx*UNIT_SIZE-MAP[0].length*UNIT_SIZE/2;
						ballZ=(ballZz+1)*UNIT_SIZE-MAP.length*UNIT_SIZE/2;
						ballY=0;
						ballZz=ballZz+1;
			}
		}		
	}
	public float jsSDX(float vx,float vz,float cosa,float sina)//遇到角时计算X方向的速度，此两个方法为从轴负方向前进时
	{
	float vvx;
	vvx=-2*vz*sina*cosa+vx*cosa*cosa-vx*sina*sina;//计算此时X轴方向的速度
		return Math.abs(vvx);//返回值
	}
	public float jsSDZ(float vx,float vz,float cosa,float sina)//遇到角时计算Z方向的速度。此两个方法为从轴负方向前进时
	{
	float vvz;	
	vvz=vz*sina*sina-vz*cosa*cosa+2*vx*cosa*sina;//计算此时的Z方向上的速度
		return Math.abs(vvz);
	}
}
