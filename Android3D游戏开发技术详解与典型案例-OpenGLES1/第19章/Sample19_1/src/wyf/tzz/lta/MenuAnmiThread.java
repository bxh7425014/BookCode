package wyf.tzz.lta;

import static wyf.tzz.lta.MenuSurfaceView.*;
//菜单动画线程
public class MenuAnmiThread extends Thread{
	MenuSurfaceView mv;					//MenuSurfaceView的引用
	int afterCurrentIndex;				//动画播放完成后的当前菜单编号
	public MenuAnmiThread(MenuSurfaceView mv,int afterCurrentIndex){
		this.mv=mv;
		this.afterCurrentIndex=afterCurrentIndex;
	}
	
	public void run(){
		for(int i=0;i<=totalSteps;i++){		//循环指定的次数完成动画
			mv.changePercent=percentStep*i;	//计算此步的百分比
			mv.init();						//初始化各个位置值			
			if(mv.anmiState==1)
			{	//向右的动画
				//根据百分比计算当前菜单项位置、大小
				mv.currentSelectX=mv.currentSelectX+(bigWidth+span)*mv.changePercent;
				mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
				mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
				mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
				//由于动画向右，左侧菜单项会变大，因此计算左侧菜单项大小
				mv.leftWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
				mv.leftHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);				
			}
			else if(mv.anmiState==2)
			{	//向左的动画
				//根据百分比计算当前菜单项位置、大小
				mv.currentSelectX=mv.currentSelectX-(smallWidth+span)*mv.changePercent;
				mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
				mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
				mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
				//由于动画向左，右侧菜单项会变大，因此计算右侧菜单项大小
				mv.rightWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
				mv.rightHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);					
			}
			
			//计算出紧邻左侧的菜单项位置
			mv.tempxLeft=mv.currentSelectX-(span+mv.leftWidth);			
			mv.tempyLeft=mv.currentSelectY+(mv.currentSelectHeight-mv.leftHeight);	
			//计算出紧邻右侧的菜单项位置
			mv.tempxRight=mv.currentSelectX+(span+mv.currentSelectWidth);
			mv.tempyRight=mv.currentSelectY+(mv.currentSelectHeight-mv.rightHeight);
		
			mv.repaint();				//重绘画面
			try{Thread.sleep(timeSpan);
			}catch(Exception e){e.printStackTrace();}
		}
		
		mv.anmiState=0;						//动画完成后设置动画状态为0-无动画		
		mv.currentIndex=afterCurrentIndex; 	//动画完成后更新当前的菜单项编号
		mv.init();							//初始化各个位置值
		mv.repaint();						//重绘画面
	}
}
