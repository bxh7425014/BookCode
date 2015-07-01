package wyf.jsc.rtb;

import static wyf.jsc.rtb.Constant.*;
public class RotateThread extends Thread
{
   int function;//旋转方式  0-小面顺时针 1-小面逆时针  2-大面顺时针 3-大面逆时针
   Cube cube;
   int afterState;
   float afterXOffset;
   float afterZOffset;   
   
   public RotateThread()
   {   
   }
   
   public RotateThread(int function,Cube cube,int afterState,float afterXOffset,float afterZOffset)
   {
	   this.function=function;
	   this.cube=cube;
	   this.afterState=afterState;
	   this.afterXOffset=afterXOffset;
	   this.afterZOffset=afterZOffset;		   
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
				  Thread.sleep(40);
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
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
	  }
	  
	  //解除扰动
	  cube.tempCenterX=0;
	  cube.tempCenterY=0;
	  cube.tempCenterZ=0;
	  cube.angleX=0;
	  cube.angleY=0;
	  cube.angleZ=0;
	  
	  
	  //动画完成后设置状态
	  cube.zOffset=this.afterZOffset;
	  cube.xOffset=this.afterXOffset;
	  cube.state=this.afterState;
	  
	  anmiFlag=false;
   }
}
