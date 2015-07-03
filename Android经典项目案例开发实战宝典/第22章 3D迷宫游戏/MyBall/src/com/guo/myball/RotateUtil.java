package com.guo.myball;
/*
 * 该类为静态工具类，提供静态方法来计算
 * 小球应该的运动方向
 */
public class RotateUtil
{
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=
		{
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		  
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}		
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	public static int[] getDirectionDot(double[] values)
	{
		double yawAngle=-Math.toRadians(values[0]);
		double pitchAngle=-Math.toRadians(values[1]);
		double rollAngle=-Math.toRadians(values[2]);
		
		double[] gVector={0,0,-10,1};
		gVector=RotateUtil.yawRotate(yawAngle,gVector);
		gVector=RotateUtil.pitchRotate(pitchAngle,gVector);	
		gVector=RotateUtil.rollRotate(rollAngle,gVector);
		double mapX=gVector[0];
		double mapY=gVector[1];		
		int[] result={(int)mapX,(int)mapY};
		return result;
	}	
}