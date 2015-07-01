 
 
 

#if !defined(AFX_ALCULATEF_H__433DBB3B_6B47_41B5_A563_7136A3BDB89F__INCLUDED_)
#define AFX_ALCULATEF_H__433DBB3B_6B47_41B5_A563_7136A3BDB89F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

struct   Point   {     
	double   x,   y,	z;     
};     

struct   LineSegment   {     
	Point   pt1,   pt2;     
}; 

 
typedef struct  
{
	float   m_TArea;
	float	m_WArea;
	int	m_Style;
	double m_LC;
}SectionTW;


class CalCulateF 
{
public:
	double GetPointToLineDistence(double ptx, double pty, double Linex1, double Liney1, double Linex2, double Liney2);
	bool GetLinePolygonJD(Point polygon[], int n, Point p1,Point P2,Point *pts,int *m_JdNums);
	Point GetCrossPoint(Point p1, Point p2, Point q1, Point q2,bool *bCross);
	bool IsLineSegmentCross(Point pFirst1, Point pFirst2, Point pSecond1, Point pSecond2);
	void GetTWArea(double *mareaT,double  *mareaW);
	CString RadianToDegree(float RadianAngle);
	bool InPolygon(Point polygon[], int n, Point point);
	bool IsOnline(Point point, LineSegment line);
	double Multiply(Point p1, Point p2, Point p0);
	double MinValueXY(double x1, double x2);
	double MaxValueXY(double x1, double x2);
	double maxValueXYZ(double dx ,  double dy ,  double dz);
	CalCulateF();
	virtual ~CalCulateF();

	CArray<SectionTW,SectionTW> m_SectionTW;

private:
	int GetTXArea(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4, float *marea);
	void GetDMArea(int indexZH,float *mareaT,float *mareaW);
	bool Intersect(LineSegment L1, LineSegment L2);
};

#endif // !defined(AFX_ALCULATEF_H__433DBB3B_6B47_41B5_A563_7136A3BDB89F__INCLUDED_)
