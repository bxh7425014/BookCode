 
 
 

#if !defined(AFX_GETDEMINSERTVALUE_H__AA5B373C_1FF2_4A51_B964_467EA20D8B49__INCLUDED_)
#define AFX_GETDEMINSERTVALUE_H__AA5B373C_1FF2_4A51_B964_467EA20D8B49__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CGetDemInsertValue  
{
public:
	float kriging(float dx,float dy,int mode, int item, float *Z_s, double *pos, float c0, float c1, float a, float *result);
	float GetHeightValue(double x, double y, int method_style);
	float InverseDistancetoaPower(int mStartrow,int mStartCol,double mx,double my, int mtotalPoins,float smoothParameter);
	float biLinearInterpolation(int mStartrow,int mStartCol,double mx,double my);
	float LinearInterpolation(int mStartrow,int mStartCol,double mx,double my);
	CGetDemInsertValue();
	virtual ~CGetDemInsertValue();

private:
	float trendFace(int mStartrow, int mStartCol, double mx, double my);
	float GetKrikingValue(int mStartrow,int mStartCol,double mx,double my);
	int agaus(float *a, float *b, int n);
	float GetDistence(double x1,double y1,double x2,double y2);
};

#endif // !defined(AFX_GETDEMINSERTVALUE_H__AA5B373C_1FF2_4A51_B964_467EA20D8B49__INCLUDED_)
