package wyf.jsl.bs;

public class CollisionUtil
{
				//求两个向量的点积
				public static float dotProduct(float[] vec1,float[] vec2)//向量有x,y,z方向的三个分量, 将其存放在一个float数组中。
				{
					return
						vec1[0]*vec2[0]+
						vec1[1]*vec2[1]+
						vec1[2]*vec2[2];
					
				}   
				
				//求向量的模
				public static float mould(float[] vec)
				{
					return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
				}
				
				//求两个向量的夹角
				public static float angle(float[] vec1,float[] vec2)
				{
					//先求点积
					float dp=dotProduct(vec1,vec2);
					//再求两个向量的模
					float m1=mould(vec1);
					float m2=mould(vec2);
					
					return (float)Math.acos(dp/(m1*m2));
				}
			
	
	
	
			//求球在某个点以一定速度撞击后的反弹速度，不考虑能量损失
			public static float[] ballBreak
			(
				float[] vBefore, //撞击前的速度
				float[] ballCenter, //撞击时的球心
				float[] point//撞击点
			)
			{
				//求由球心指向撞击点的向量
				float[] vecOtoB=
				{
				    point[0]-ballCenter[0],
				    point[1]-ballCenter[1],
				    point[2]-ballCenter[2],	
				};
				
				
				//先求撞击前速度与撞击法向量(由球心指向撞击点的向量)的夹角
				float alpha=angle(vecOtoB,vBefore);
				//求撞击前速度的模
				float vMould=mould(vBefore);	
				//求在撞击法向量方向上速度分量的模
				float vNormalMould=vMould*(float)Math.cos(alpha);
				//求撞击法向量的模
				float normalMould=mould(vecOtoB);	
				//求在撞击法向量方向上的速度分量
				float[] vNormal=
				{
					vecOtoB[0]*vNormalMould/normalMould,
					vecOtoB[1]*vNormalMould/normalMould,
					vecOtoB[2]*vNormalMould/normalMould,
				};		
				
				//求垂直于撞击法向量方向上的速度分量
				float[] vONormal=
				{
					vBefore[0]-vNormal[0],
					vBefore[1]-vNormal[1],
					vBefore[2]-vNormal[2]
				};
				
				//求撞击后撞击法向量方向的速度分量，撞击后该方向上的速度分量向量方向置反。
				float[] vNormalAfter=
				{
					-vNormal[0],
					-vNormal[1],
					-vNormal[2]
				};
				
				//求撞击后的速度，该速度为置反的撞击法向量方向的速度分量与垂直于撞击法向量方向上的速度分量的和向量。
				float[] vAfter=
				{
					vONormal[0]+vNormalAfter[0],
					vONormal[1]+vNormalAfter[1],
					vONormal[2]+vNormalAfter[2],
				};
				
				return vAfter;		
			}
	
	//已知球的位置，半径，篮圈的位置，半径
	//求撞击点
	public static float[] breakPoint
	(
			float[] ballCenter, //球心
			float ballR,//球半径
			float[] ringCenter,//篮圈中心点
			float ringR//篮圈半径
	)
	{
			//首先判断篮圈的高度范围是否在篮球高度范围内，这样才有碰撞篮圈的可能
			if(ringCenter[1]<ballCenter[1]+ballR&&ringCenter[1]>ballCenter[1]-ballR)//ringCenter[1]为环的高度，ballCenter[1]为球心的高度
			{
				
				//计算碰撞圆的半径
				float ballRForBreak=(float)Math.sqrt
				(
						ballR*ballR-
						(ringCenter[1]-ballCenter[1])*(ringCenter[1]-ballCenter[1])//ringCenter[1]-ballCenter[1]为高度差
				);
				
				//计算碰撞圆圆心与篮圈圆心的距离
				float distance=(float)Math.sqrt
				(
					(ballCenter[0]-ringCenter[0])*(ballCenter[0]-ringCenter[0])+
					(ballCenter[2]-ringCenter[2])*(ballCenter[2]-ringCenter[2])
				);
				
				//判断圆心距离是否小于两圆半径的和且大于两个半径的差，若满足则发生了碰撞
				if((distance<ballRForBreak+ringR)&&(distance>(ringR-ballRForBreak)))
				{
					//圆心的Z差
					float zDiff=Math.abs(ringCenter[2]-ballCenter[2]);
					double jdAngle=Math.asin(zDiff/distance);
					
					float xAdjust=0;
					float zAdjust=0;
					
					if(ballCenter[0]>=ringCenter[0])
					{
						xAdjust=-1;
					}
					else
					{
						xAdjust=+1;
					}
					if(ballCenter[2]>=ringCenter[2])
					{
						zAdjust=-1;
					}
					else
					{
						zAdjust=1;
					}
					
					//求碰撞点
					float[] point=
					{
						ballCenter[0]+xAdjust*ballRForBreak*(float)Math.cos(jdAngle),
						ringCenter[1],
						ballCenter[2]+zAdjust*ballRForBreak*(float)Math.sin(jdAngle),
					};					
					
					return point;
				}
				else
				{
					return null;
				}
				
			}

			return null;		
	}

	
	public static void main(String args[])
	{
		float[] vAfter=CollisionUtil.ballBreak
		(
			new float[]{2,-2,0}, //撞击前的速度
			new float[]{4,4,0}, //撞击时的球心
			new float[]{6.828427f,1.1715729f,0}//撞击点			
		);
		
		System.out.println(vAfter[0]+", "+vAfter[1]+", "+vAfter[2]);
		
		
	}
}