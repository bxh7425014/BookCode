#include "stdafx.h"
#include <math.h>
#include "vector.h"

 
 
#define Mag(Normal) (sqrt(Normal.x*Normal.x + Normal.y*Normal.y + Normal.z*Normal.z))

 
CVector3 Vector(CVector3 vPoint1, CVector3 vPoint2)
{
	CVector3 vVector;							

	vVector.x = vPoint1.x - vPoint2.x;			
	vVector.y = vPoint1.y - vPoint2.y;			
	vVector.z = vPoint1.z - vPoint2.z;			

	return vVector;								
}
 
CVector3 Normalize(CVector3 vNormal)
{
	double Magnitude;							

	Magnitude = Mag(vNormal);					

	vNormal.x /= (float)Magnitude;				
	vNormal.y /= (float)Magnitude;				
	vNormal.z /= (float)Magnitude;				

	return vNormal;								
}
 
float Absolute(float num)
{
	return num>=0?num:-num;
}

 
 
 
 
 

float Magnitude(CVector3 vNormal)
{
	
	
	return (float)sqrt( (vNormal.x * vNormal.x) + (vNormal.y * vNormal.y) + (vNormal.z * vNormal.z) );
}
 
CVector3 Cross(CVector3 vVector1, CVector3 vVector2)
{
	CVector3 vCross;								
												
	vCross.x = ((vVector1.y * vVector2.z) - (vVector1.z * vVector2.y));
												
	vCross.y = ((vVector1.z * vVector2.x) - (vVector1.x * vVector2.z));
												
	vCross.z = ((vVector1.x * vVector2.y) - (vVector1.y * vVector2.x));

	return vCross;								
}


 
 
 
 
 
 
 
float DotProduct(const CVector3 &v1, const CVector3 &v2)//点积计算
{
	return (v1.x*v2.x)+(v1.y*v2.y)+(v1.z*v2.z);
 
};

 
CVector3 CrossProduct(const CVector3 &v1, const CVector3 &v2)//叉积计算
{
	CVector3 result(0.0f, 0.0f, 0.0f);

	result.x= (v1.y*v2.z) - (v1.z*v2.y);
	result.y= (v1.z*v2.x) - (v1.x*v2.z);
	result.z= (v1.x*v2.y) - (v1.y*v2.x);

	return result;
};



 
 
 
 
 

/*
float Magnitude(CVector3 vNormal)
{
	
	return (float)sqrt( (vNormal.x * vNormal.x) + 
						(vNormal.y * vNormal.y) + 
						(vNormal.z * vNormal.z) );
}
*/


 
 
 
 
 

/*
CVector3 Normalize(CVector3 vVector)
{
	
	float magnitude = Magnitude(vVector);				

	
	
	vVector = vVector / magnitude;		
	
	
	return vVector;										
}*/

bool SameSign(float f1,float f2)
{
	return f1<0&&f2<0||f1>0&&f2>0||f1==0&&f2==0?true:false;
}
 
bool PntFrontOrBack(const CVector3 &vPnt,const CVector3 &vPtStart,const CVector3 &vDrct)
{
	return SameSign(vPnt.x-vPtStart.x,vDrct.x)&&
		SameSign(vPnt.y-vPtStart.y,vDrct.y)&&
		SameSign(vPnt.z-vPtStart.z,vDrct.z)?true:false;
}
bool PntFrontOrBack2(const CVector3 &vPnt,const CVector3 &vPtStart,const CVector3 &vDrct)
{
	return SameSign(vPnt.x-vPtStart.x,vDrct.x)&&
	
		SameSign(vPnt.z-vPtStart.z,vDrct.z)?true:false;
}
float Dist(const CVector3 &v1,const CVector3 &v2)
{
	return float(sqrt((v1.x-v2.x)*(v1.x-v2.x)+(v1.y-v2.y)*(v1.y-v2.y)+(v1.z-v2.z)*(v1.z-v2.z)));
}
