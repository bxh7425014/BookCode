package wyf.tzz.path;

import java.util.*;

public class Path {

	static float xBoundary = 3f;		//屏幕横向边界
	static float yBoundary = 2f;		//屏幕纵向边界
	
	static int totalPath=10;			//需要的路线的总条数
	static int totalPoint=4;			//每条路线的点数
	
	static float lastValue=0;

	static float [][] myPath=new float[2][totalPath*totalPoint];		//存储路线的数组


    public Path() {
    }
    
    public static float getInnerPoint(float boudary,float span)		//获得屏幕内的点的方法
    {
		float result=0;
		while(true)
		{
			result=(int)((boudary*2*Math.random()-boudary)*1000)/1000.0f;	//根据坐标计算出屏幕内的点坐标
			if(Math.abs(lastValue-result)>span)					//与上一次得到的点比较，如果间距太小，重新计算
			{
				lastValue=result;								
				break;
			}
		}
		
		return result;
    }
    
   
    
    static public float getOuterPoint(float boudary)			//获得屏幕外的点的方法
    {	
    	float result=0;
    	result=(int)(Math.signum((Math.random()-0.5f))*1.05f*boudary*1000)/1000.0f;	//根据坐标计算出屏幕外的点坐标
    	return  result;
    }
    
    public static void main(String[] args)
    {
    
    	int index=0;	//路线数组索引
    	
    	
    	for(int j=0;j<totalPath;j++)//获得路线所有点的x坐标
		{
    		myPath[0][index++]=getOuterPoint(xBoundary*2);//第一个点在屏幕外
    		myPath[0][index++]=getInnerPoint(xBoundary,1.5f);//第二个点在屏幕外
    		myPath[0][index++]=getInnerPoint(xBoundary,1.5f);//第三个点在屏幕外
    		myPath[0][index++]=getOuterPoint(xBoundary*2);   //第四个点在屏幕外 	
    	}
    
    	for(int i=0;i<myPath[0].length;i++)		//获得路线所有点的x坐标
    	{
    			myPath[1][i]=getInnerPoint(yBoundary,2f);//路线所有点的y坐标在屏幕内
    	
    	}
	
    	
    	System.out.println("public final static float[][] path={");
    	for(int j=0;j<myPath.length;j++)
    	{
    		System.out.print("{");
    		for(int i=0;i<myPath[j].length;i++)
	    	{ 
	    		System.out.print(myPath[j][i]+"f,");	//打印路线数组
	    	}
	    	System.out.println("},");
    	}
    	System.out.print("};");
    }
    
}