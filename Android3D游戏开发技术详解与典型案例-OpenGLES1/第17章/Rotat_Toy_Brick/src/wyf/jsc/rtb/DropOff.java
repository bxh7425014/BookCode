package wyf.jsc.rtb;


import static wyf.jsc.rtb.Constant.*;
public class DropOff extends Thread
{
	Cube cube;
	int function;
	
	public DropOff(int function,Cube cube)
	{
		this.function=function;
		this.cube=cube;
	}
	 
	public void run()
	{
		if(function==0)
		  {//0-0顺时针旋转
			   float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterX=(float)(-Math.cos(Math.toRadians(angle+45))*cube.rSmall)+cube.unitLocalSize;
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+45))*cube.rSmall)-cube.unitLocalSize;
			      cube.angleZ=-angle;
				  angle+=10f; 
				  try
				  {
					  sleep(40);
				  }catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   } 
			   if(MAP[cube.j1][cube.i2]==1)
			   {
				   OneToTwoN();
			   }
			   else if(MAP[cube.j2][cube.i2]==1)
			   {
				   OneToTwoS();
			   }
			   else
			   {
				   while(true)
					  {
						  cube.tempCenterY-=0.8;
						  cube.angleZ-=15;
						  try
						  {
							  Thread.sleep(40);
						  }
						  catch(Exception e)
						  {
							  e.printStackTrace();
						  }
						//设置转换画面，将循环结束
					  }
			   }
			   
		  }
		  else if(function==1)
		  {//0-0逆时针旋转
			   float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterX=(float)(Math.cos(Math.toRadians(angle+45))*cube.rSmall)-cube.unitLocalSize;
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+45))*cube.rSmall)-cube.unitLocalSize;
			      cube.angleZ=angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   if(MAP[cube.j1][cube.i2]==1)
			   {
				   OneToTwoN();
			   }
			   else if(MAP[cube.j2][cube.i2]==1)
			   {
				   OneToTwoS();
			   }
			   else
			   {
				   while(true)
					  {
						  cube.tempCenterY-=0.8;
						  cube.angleZ+=15;
						  try
						  {
							  Thread.sleep(40);
						  }
						  catch(Exception e)
						  {
							  e.printStackTrace();
						  }
						//设置转换画面，将循环结束
					  } 
			   }
			  
		  }
		  else if(function==2)
		  {//0-1顺时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterZ=(float)(Math.cos(Math.toRadians(angle+26.565))*cube.rBig)-2*cube.unitLocalSize;
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+26.565))*cube.rBig)-cube.unitLocalSize;
			      cube.angleX=-angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterY-=0.8;
					  cube.angleX-=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==3)
		  {//0-1逆时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterZ=(float)(-Math.cos(Math.toRadians(angle+26.565))*cube.rBig)+2*cube.unitLocalSize;
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+26.565))*cube.rBig)-cube.unitLocalSize;
			      cube.angleX=angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterY-=0.8;
					  cube.angleX+=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==4)
		  {//1-2顺时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterX=(float)(-Math.cos(Math.toRadians(angle+63.435))*cube.rBig)+cube.unitLocalSize;
				  cube.tempCenterZ=(float)(-Math.sin(Math.toRadians(angle+63.435))*cube.rBig)+2*cube.unitLocalSize;
			      cube.angleY=-angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterZ+=0.8;
					  cube.angleY-=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==5)
		  {//1-2逆时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterX=(float)(Math.cos(Math.toRadians(angle+63.435))*cube.rBig)-cube.unitLocalSize;
				  cube.tempCenterZ=(float)(-Math.sin(Math.toRadians(angle+63.435))*cube.rBig)+2*cube.unitLocalSize;
			      cube.angleY=angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterZ+=0.8;
					  cube.angleY+=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==6)
		  {//1-0顺时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterY=(float)(Math.cos(Math.toRadians(angle+63.435))*cube.rBig)-cube.unitLocalSize;
				  cube.tempCenterZ=(float)(-Math.sin(Math.toRadians(angle+63.435))*cube.rBig)+2*cube.unitLocalSize;
			      cube.angleX=-angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterZ+=0.8;
					  cube.angleX-=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==7)
		  {//1-0逆时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterY=(float)(-Math.cos(Math.toRadians(angle+63.435))*cube.rBig)+cube.unitLocalSize;
				  cube.tempCenterZ=(float)(-Math.sin(Math.toRadians(angle+63.435))*cube.rBig)+2*cube.unitLocalSize;
			      cube.angleX=angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterZ+=0.8;
					  cube.angleX+=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==8)
		  {//2-1顺时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+26.565))*cube.rBig)-cube.unitLocalSize;
				  cube.tempCenterZ=(float)(-Math.cos(Math.toRadians(angle+26.565))*cube.rBig)+2*cube.unitLocalSize;
			      cube.angleX=angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterY-=0.8;
					  cube.angleX+=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					//设置转换画面，将循环结束
				  }
		  }
		  else if(function==9)
		  {//2-1 逆时针
			  float angle=0;
			   while(angle<90)
			   {
				  cube.tempCenterY=(float)(Math.sin(Math.toRadians(angle+26.565))*cube.rBig)-cube.unitLocalSize;
				  cube.tempCenterZ=(float)(Math.cos(Math.toRadians(angle+26.565))*cube.rBig)-2*cube.unitLocalSize;
			      cube.angleX=-angle;
				  angle+=10f;
				  
				  try
				  {
					  Thread.sleep(40);
				  }
				  catch(Exception e)
				  {
					  e.printStackTrace();
				  }
			   }
			   while(true)
				  {
					  cube.tempCenterY-=0.8;
					  cube.angleX-=15;
					  try
					  {
						  Thread.sleep(40);
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
					  //设置转换画面，将循环结束
				  }
		  }
	}
	public void OneToTwoN()//1-2逆时针
	{
		while(true)
		  {
			  //cube.tempCenterX+=2;
			  cube.tempCenterY-=0.8;
			  cube.angleX-=15;
			  try
			  {
				  Thread.sleep(40);
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
			  }
			//设置转换画面，将循环结束
		  }
	}
	public void OneToTwoS()//1-2顺时针
	{
		while(true)
		{
			  cube.tempCenterY-=0.8;
			  cube.angleX+=15;
			  try
			  {
				  Thread.sleep(40);
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
			  }
		}
		
	}
}

