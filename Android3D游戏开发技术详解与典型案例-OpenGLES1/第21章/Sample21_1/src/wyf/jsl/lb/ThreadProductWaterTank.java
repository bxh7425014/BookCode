package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;

public class ThreadProductWaterTank extends Thread
{
	GLGameView gl;
	boolean flag=true;
	
	public ThreadProductWaterTank(GLGameView gl)
	{
		this.gl=gl;
	}
	
	public void run()
	{
		while(flag)
		{
			if(gl.waterTankList.size()<=WATERTANK_COUNT)
			{
				float angle=(float) (120*Math.random()+120);//-60~60
				try{
					gl.waterTankList.add			//添加水上坦克
					(new LogicalWaterTank
							(
									(float)(PRODUCT_WATERTANK_RADIUS*Math.sin(Math.toRadians(angle))),//-90
									(float)(PRODUCT_WATERTANK_RADIUS*Math.cos(Math.toRadians(angle))),//-90
									angle,
									gl.mRenderer.wt,
									gl
							)
					);
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}