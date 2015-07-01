package wyf.jsc.tdb;

import static wyf.jsc.tdb.Constant.*;


public class MenuAnmiThread extends Thread
{
	MenuView mv;
	int afterCurrentIndex;//播放完动画后的当前菜单项
	
	public MenuAnmiThread(MenuView mv,int afterCurrentIndex)
	{
		this.mv=mv;
		this.afterCurrentIndex=afterCurrentIndex;
	}
	public void run()
	{
		//循环指定的次数完成动画
		for(int i=0;i<=totalSteps;i++)//动画的总步数
		{
			//计算此步的百分比·
			mv.changePercent=percentStep*i;
			
			mv.init();
			
			//动画总的思想就是根据进度百分比计算出当前菜单项的位置、大小			
			//并根据当前菜单项的位置大小计算出其左右侧紧邻菜单项的位置、大小
			//非紧邻项的大小是固定的，只需要计算位置
			if(mv.anmiState==1)   //anmiState=  0-没有动画  1-向右走  2-向左走	
			{//向右的动画
				//根据百分比计算当前菜单项位置、大小
				mv.currentSelectX=mv.currentSelectX+(bigWidth+span)*mv.changePercent;              		//
				mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
				mv.menuWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
				mv.menuHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
				//由于动画向右，左侧菜单项会变大，因此计算左侧菜单项大小
				mv.leftWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
				mv.leftHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);						
			}
			else if(mv.anmiState==2)     //anmiState=  0-没有动画  1-向右走  2-向左走	
			{//向左的动画
				//根据百分比计算当前菜单项位置、大小
				mv.currentSelectX=mv.currentSelectX-(smallWidth+span)*mv.changePercent;
				mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
				mv.menuWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
				mv.menuHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
				//由于动画向左，右侧菜单项会变大，因此计算右侧菜单项大小
				mv.rightWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
				mv.rightHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);					
			}
			//计算出紧邻左侧的菜单项位置
			mv.leftmenuX=mv.currentSelectX-(span+mv.leftWidth);			
			mv.leftmenuY=mv.currentSelectY+(mv.menuHeight-mv.leftHeight);	
			//计算出紧邻右侧的菜单项位置
			mv.rightmenuX=mv.currentSelectX+(span+mv.menuWidth);
			mv.rightmenuY=mv.currentSelectY+(mv.menuHeight-mv.rightHeight);
			
			//重新绘画
			mv.repaint();
			try
			{
				Thread.sleep(timeSpan);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
		}
		//动画完成后设置动画状态为0-无动画
		mv.anmiState=0;
		//动画完成后更新当前的菜单项编号
		mv.currentIndex=afterCurrentIndex;
		//初始化各个位置值
		mv.init();		
		//重绘画面
		mv.repaint();
	}
}
















