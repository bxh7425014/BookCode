package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;

public class ThreadMenu extends Thread {
	
	SurfaceViewMenu mv;
	int afterCurrentIndex;//动画播放完后的当前菜单图片数组索引
	
	public ThreadMenu(SurfaceViewMenu mv,int afterCurrentIndex)
	{
		this.mv=mv;
		this.afterCurrentIndex=afterCurrentIndex;
	}
	
	@Override
	public void run()
	{
		for(int i=0;i<=TOTAL_STEPS;i++)//循环指定的步数完成动画
		{
			mv.changePercent=PERCENT_STEP*i;//计算此步的占动画的百分比
			mv.initMenu();//初始化各个位置的值
			
			if(mv.anmiState==1)//如果状态是1，则向右移
			{
				//根据当前百分比计算当前菜单的位置，大小
				mv.currentSelectX=mv.currentSelectX+(BIGWIDTH+MENU_SPAN)*mv.changePercent;
				mv.currentSelectY=mv.currentSelectY+(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
				mv.currentSelectWidth=BIGWIDTH-(BIGWIDTH-SMALLWIDTH)*mv.changePercent;
				mv.currentSelectHeight=BIGHEIGHT-(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
				//向右移之后，紧邻当前菜单的左侧菜单项要变大，下面要计算
				mv.leftWidth=SMALLWIDTH+(BIGWIDTH-SMALLWIDTH)*mv.changePercent;
				mv.leftHeight=SMALLHEIGHT+(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
				
				
			}
			else if(mv.anmiState==2)//如果状态是2，则向左移
			{
				//根据当前百分比计算当前菜单的位置，大小
				mv.currentSelectX=mv.currentSelectX-(SMALLWIDTH+MENU_SPAN)*mv.changePercent;
				mv.currentSelectY=mv.currentSelectY+(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
				mv.currentSelectWidth=BIGWIDTH-(BIGWIDTH-SMALLWIDTH)*mv.changePercent;
				mv.currentSelectHeight=BIGHEIGHT-(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
				//向左移之后，紧邻当前菜单的右侧菜单项要变大，下面要计算
				mv.rightWidth=SMALLWIDTH+(BIGWIDTH-SMALLWIDTH)*mv.changePercent;
				mv.rightHeight=SMALLHEIGHT+(BIGHEIGHT-SMALLHEIGHT)*mv.changePercent;
			}
			//计算出紧邻左侧的菜单的位置
			mv.leftX=mv.currentSelectX-MENU_SPAN-mv.leftWidth;
			mv.leftY=mv.currentSelectY+(mv.currentSelectHeight-mv.leftHeight);//犯过错
			//计算出紧邻右侧的菜单的位置
			mv.rightX=mv.currentSelectX+mv.currentSelectWidth+MENU_SPAN;
		    mv.rightY=mv.currentSelectY+(mv.currentSelectHeight-mv.rightHeight);//犯过错
		    
		    mv.repaint();
		    try
		    {
		    	Thread.sleep(ANMI_TIMESPAN);
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		}//for结束
		mv.anmiState=0;
		mv.currentIndex=afterCurrentIndex;
		mv.initMenu();
		mv.repaint();
	}//run结束
}
