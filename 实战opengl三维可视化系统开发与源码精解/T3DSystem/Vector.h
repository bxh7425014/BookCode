#ifndef _3DMATH_H
#define _3DMATH_H

#define PI 3.1415926535897932					

// 定义2D点类，用于保存模型的UV纹理坐标
struct CVector2 
{
public:
	float x, y;
};

 
// 定义3D点的类，用于保存模型中的顶点
struct CVector3
{
public:
	
	float x, y, z;	

	
	CVector3() 
	{
		x = 0;y = 0; z = 0;
	}

	
	CVector3(float X, float Y, float Z) 
	{ 
		x = X; y = Y; z = Z;
	}


	
	void Set(float X, float Y, float Z) 
	{ 
		x = X; y = Y; z = Z;
	}
	
	CVector3 operator+(CVector3 vVector)
	{
		
		return CVector3(vVector.x + x, vVector.y + y, vVector.z + z);
	}

	
	CVector3 operator-(CVector3 vVector)
	{
		
		return CVector3(x - vVector.x, y - vVector.y, z - vVector.z);
	}
	
	
	CVector3 operator*(float num)
	{
		
		return CVector3(x * num, y * num, z * num);
	}

	
	CVector3 operator/(float num)
	{
		
		return CVector3(x / num, y / num, z / num);
	}
					
};

typedef CVector3 Vertex;

 
float Absolute(float num);

 
CVector3 Vector(CVector3 vPoint1, CVector3 vPoint2);

 
CVector3 Cross(CVector3 vVector1, CVector3 vVector2);

 
float Magnitude(CVector3 vNormal);

 
CVector3 Normalize(CVector3 vNormal);

 
CVector3 Normal(CVector3 vPolygon[]);

 
float Distance(CVector3 vPoint1, CVector3 vPoint2);

 
CVector3 ClosestPointOnLine(CVector3 vA, CVector3 vB, CVector3 vPoint);

 
 
float PlaneDistance(CVector3 Normal, CVector3 Point);

 
bool IntersectedPlane(CVector3 vPoly[], CVector3 vLine[], CVector3 &vNormal, float &originDistance);

 
float Dot(CVector3 vVector1, CVector3 vVector2);

 
double AngleBetweenVectors(CVector3 Vector1, CVector3 Vector2);

 
CVector3 IntersectionPoint(CVector3 vNormal, CVector3 vLine[], double distance);

 
bool InsidePolygon(CVector3 vIntersection, CVector3 Poly[], long verticeCount);

 
bool IntersectedPolygon(CVector3 vPoly[], CVector3 vLine[], int verticeCount);

 
int ClassifySphere(CVector3 &vCenter, 
				   CVector3 &vNormal, CVector3 &vPoint, float radius, float &distance);

 
 
 
bool EdgeSphereCollision(CVector3 &vCenter, 
						 CVector3 vPolygon[], int vertexCount, float radius);

 
bool SpherePolygonCollision(CVector3 vPolygon[], 
							CVector3 &vCenter, int vertexCount, float radius);

 

 
CVector3 GetCollisionOffset(CVector3 &vNormal, float radius, float distance);

void ComputeNormal(CVector3* v1, CVector3* v2, CVector3* v3);

float DotProduct(const CVector3 &v1, const CVector3 &v2);

CVector3 CrossProduct(const CVector3 &v1, const CVector3 &v2);


float Magnitude(CVector3 vNormal);

CVector3 Normalize(CVector3 vVector);

bool PntFrontOrBack(const CVector3 &vPnt,const CVector3 &vPtStart,const CVector3 &vDrct);

bool PntFrontOrBack2(const CVector3 &vPnt,const CVector3 &vPtStart,const CVector3 &vDrct);

float Dist(const CVector3 &v1,const CVector3 &v2);

 

#endif 

