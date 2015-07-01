package wyf.sj;

import android.util.Log;

/*
 * 该类为静态工具类，提供静态方法来计算
 * 小球应该的运动方向
 */
public class RotateUtil
{
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] xRotate(double angle,double[] gVector)
	{
		double[][] matrix=//绕x轴旋转变换矩阵
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
	
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] yRotate(double angle,double[] gVector)
	{
		double[][] matrix=//绕y轴旋转变换矩阵
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
	
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] zRotate(double angle,double[] gVector)
	{
		double[][] matrix=//绕z轴旋转变换矩阵
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
	
	public static float[] calculateABPosition
	(
		float tx,//触屏X坐标
		float ty,//触屏Y坐标
		float w,// 屏幕宽度
		float h,// 屏幕高度
		float left,//视角left值
		float top,//视角top值
		float near,//视角near值
		float far,//视角far值
		float alpha,//		 摄像机角度
		float cx,//摄像机x坐标
		float cy,//摄像机y坐标
		float cz//摄像机z坐标
	)
	{
		Log.d("tx,ty",tx+"|"+ty);		
		
		float k=tx-w/2;//计算手机屏幕平面上触控点距中心点距离X
		float p=h/2-ty;//计算手机屏幕平面上触控点距中心点距离Y
		
		float kNear=2*k*left/w;//计算near平面A点距坐标原点距离X
		float pNear=2*p*top/h;//计算near平面A点距坐标原点距离Y
		
		Log.d("kNear,pNear",kNear+"|"+pNear);
		
		float ratio=far/near;
		float kFar=ratio*kNear;//计算far平面B点坐标x
		float pFar=ratio*pNear;//计算far平面B点坐标y
		
		Log.d("kFar,pFar",kFar+"|"+pFar);

        float beforeNX=0+kNear;//移动摄像机前A的坐标
        float beforeNY=0+pNear;
        float beforeNZ=-near;
        
        float beforeFX=0+kFar;//移动摄像机前B的坐标
        float beforeFY=0+pFar;
        float beforeFZ=-far;
        
        double[] beforeN=new double[]{beforeNX,beforeNY,beforeNZ,1};       
        double[] afterN=yRotate(Math.toRadians(alpha),beforeN);//计算转动摄像机后的A点坐标
        
        double[] beforeF=new double[]{beforeFX,beforeFY,beforeFZ,1};       
        double[] afterF=yRotate(Math.toRadians(alpha),beforeF);//计算转动摄像机后的B点坐标
		
		return new float[]//返回最终的AB两点坐标
		{
				(float)afterN[0]+cx,(float)afterN[1]+cy,(float)afterN[2]+cz,
				(float)afterF[0]+cx,(float)afterF[1]+cy,(float)afterF[2]+cz
		};
	} 

}