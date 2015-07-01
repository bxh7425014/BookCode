package wyf.jsc.rtb;


import static wyf.jsc.rtb.Constant.*;
public class WinDrop extends Thread
{
	Cube cube;
	public WinDrop(Cube cube)
	{
		this.cube=cube;
	}
	public void run()
	{
		int iOver=0;
		try
		  {
			  Thread.sleep(100);
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		 while(bOver)
		  {

			 cube.tempCenterZ+=0.1;
			 iOver++;
			 try
			 {  
				 sleep(40);
			 }catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			 if(iOver>40)
			 {
				 bOver=false;
			 }
		  }
		//设置转换画面，将循环结束,重新绘制积木
		/* cube.tempCenterX=0;
		 cube.tempCenterY=0;
		 cube.tempCenterZ=0;
		 cube.angleX=0;
		 cube.angleY=0;
		 cube.angleZ=0;*/
	}
}

