package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;

//用于山地地形碰撞检测的工具类
public class CollectionUtil 
{
	//判断一个点是否在三角形内的方法
	//基本算法思想是首先求要被判断的点到三角形三个顶点的矢量1、2、3
	//然后三个矢量求叉积，若三个叉积同号则点位于三角形内，否则位于三角形外
	public static boolean isInTriangle
	(
			//三角形第一个点的XY坐标
			float x1,
			float y1,
			//三角形第二个点的XY坐标
			float x2,
			float y2,
			//三角形第三个点的XY坐标
			float x3,
			float y3,
			//被判断点的XY坐标
			float dx,
			float dy
	)
	{
		//被判断点到三角形第一个点的矢量
		float vector1x=dx-x1;
		float vector1y=dy-y1;
		
		//被判断点到三角形第二个点的矢量
		float vector2x=dx-x2;
		float vector2y=dy-y2;
		
		//被判断点到三角形第三个点的矢量
		float vector3x=dx-x3;
		float vector3y=dy-y3;
		
		//计算第1、2矢量个叉积
		float crossProduct1=vector1x*vector2y-vector1y*vector2x;
		
		//计算第2、3矢量个叉积
		float crossProduct2=vector2x*vector3y-vector2y*vector3x;
		
		//计算第3、1矢量个叉积
		float crossProduct3=vector3x*vector1y-vector3y*vector1x;
		
		if(crossProduct1<0&&crossProduct2<0&&crossProduct3<0)
		{//若三个叉积同号返回true
			return true;
		}
		
		if(crossProduct1>0&&crossProduct2>0&&crossProduct3>0)
		{//若三个叉积同号返回true
			return true;
		}
		
		return false;
	}
	
	//计算由三个点0、1、2确定的平面在指定XZ坐标处的高度
	//基本算法思想，首先求出0号点到1、2号点的矢量
	//然后这两个矢量求叉积得到三角形平面的法矢量{A,B,C}
	//接着通过法矢量和0号点坐标可以写出三角形平面的方程
	// A(x-x0)+B(y-y0)+c(z-z0)=0
	//然后可以推导出指定xz坐标处y的求值公式
	//y=(C(z0-z)+A(x0-x))/B+y0;
	//最后通过求值公式求出指定xz坐标处y的值
	public static float fromXZToY
	(
		float tx0,float ty0,float tz0,//确定平面的点0
		float tx1,float ty1,float tz1,//确定平面的点1
		float tx2,float ty2,float tz2,//确定平面的点2
		float ctx,float ctz//球心的XZ坐标
	)
	{
		//求出0号点到1号点的矢量
        float x1=tx1-tx0;
        float y1=ty1-ty0;
        float z1=tz1-tz0;
        //求出0号点到2号点的矢量
        float x2=tx2-tx0;
        float y2=ty2-ty0;
        float z2=tz2-tz0;
        //求出两个矢量叉积矢量在XYZ轴的分量ABC
        float A=y1*z2-y2*z1;
        float B=z1*x2-z2*x1;
        float C=x1*y2-x2*y1;
        //通过求值公式求指定xz处的y值
		float yResult=(C*(tz0-ctz)+A*(tx0-ctx))/B+ty0;
		//返回结果
		return yResult;
	}
	
	//判断当前物体（如子弹，炮管头等）所在位置的Y坐标是否高于当前物体对应的目标物体（如山地，海水等）的Y坐标
	public static boolean isBulletCollectionsWithLand
	(
			float tempbx,//当前物体X坐标
			float tempby,//当前物体Y坐标
			float tempbz//当前物体Z坐标
	)
	{		
	    //拷贝炮弹的XZ坐标
	    float ctx=tempbx;
	    float ctz=tempbz;
	    //计算炮弹对应的陆地格子的行、列
	    int tempCol=(int)((ctx+(UNIT_SIZE*COLS/2))/UNIT_SIZE);
	    int tempRow=(int)((ctz+(UNIT_SIZE*ROWS/2))/UNIT_SIZE);
	    if(tempCol<0||tempCol>128||tempRow<0||tempRow>100)//炮弹出了陆地
	    {
	    	return false;
	    }
	    //计算炮弹对应的陆地格子的四个点的坐标
	    float x0=-UNIT_SIZE*COLS/2+tempCol*UNIT_SIZE;
	    float z0=-UNIT_SIZE*ROWS/2+tempRow*UNIT_SIZE; 
	    float y0=yArray[tempRow][tempCol];
	    
	    float x1=x0+UNIT_SIZE;
	    float z1=z0;
	    float y1=yArray[tempRow][tempCol+1];
	    
	    float x2=x0+UNIT_SIZE;
        float z2=z0+UNIT_SIZE;
        float y2=yArray[tempRow+1][tempCol+1];
    
	    float x3=x0;
	    float z3=z0+UNIT_SIZE;
	    float y3=yArray[tempRow+1][tempCol];
    
	    //炮弹处的陆地高度
	    float cty=0;
	    
	    if(isInTriangle(x0,z0,x1,z1,x3,z3,ctx,ctz))
	    {//判断炮弹是否位于0-1-3三角形
	    	//求0-1-3面在炮弹处陆地的高度
	    	cty=fromXZToY
		    (
			    	x0,y0,z0,
			    	x1,y1,z1,
			    	x3,y3,z3,
			    	ctx,ctz
			 );
	    }
	    else
	    {
	    	//求1-2-3面在炮弹处陆地的高度
	    	cty=fromXZToY
		    (
			    	x1,y1,z1,
			    	x2,y2,z2,
			    	x3,y3,z3,
			    	ctx,ctz
			);
	    }	    
	    if(cty>tempby)
	    {//若炮弹处的陆地高于炮弹则返回true
	    	return true;
	    }	    
	    return false;//若当前物体对应的陆地高度低于水面高度，返回FALSE
	}
	
	//返回当前物体对应陆地的高度
	public static float getLandHeight(float tempbx,float tempbz)
	{
		//拷贝炮弹的XZ坐标
	    float ctx=tempbx;
	    float ctz=tempbz;
	    //计算炮弹对应的陆地格子的行、列
	    int tempCol=(int)((ctx+(UNIT_SIZE*COLS/2))/UNIT_SIZE);
	    int tempRow=(int)((ctz+(UNIT_SIZE*ROWS/2))/UNIT_SIZE);
	  //计算炮弹对应的陆地格子的四个点的坐标
	    float x0=-UNIT_SIZE*COLS/2+tempCol*UNIT_SIZE;
	    float z0=-UNIT_SIZE*ROWS/2+tempRow*UNIT_SIZE; 
	    float y0=yArray[tempRow][tempCol];
	    
	    float x1=x0+UNIT_SIZE;
	    float z1=z0;
	    float y1=yArray[tempRow][tempCol+1];
	    
	    float x2=x0+UNIT_SIZE;
        float z2=z0+UNIT_SIZE;
        float y2=yArray[tempRow+1][tempCol+1];
    
	    float x3=x0;
	    float z3=z0+UNIT_SIZE;
	    float y3=yArray[tempRow+1][tempCol];
    
	    //炮弹处的陆地高度
	    float cty=0;
	    
	    if(isInTriangle(x0,z0,x1,z1,x3,z3,ctx,ctz))
	    {//判断炮弹是否位于0-1-3三角形
	    	//求0-1-3面在炮弹处陆地的高度
	    	cty=fromXZToY
		    (
			    	x0,y0,z0,
			    	x1,y1,z1,
			    	x3,y3,z3,
			    	ctx,ctz
			 );
	    }
	    else
	    {
	    	//求1-2-3面在炮弹处陆地的高度
	    	cty=fromXZToY
		    (
			    	x1,y1,z1,
			    	x2,y2,z2,
			    	x3,y3,z3,
			    	ctx,ctz
			);
	    }
	    return cty;
	}
	
	//法向量规格化，求长度
	public static float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
	
}
