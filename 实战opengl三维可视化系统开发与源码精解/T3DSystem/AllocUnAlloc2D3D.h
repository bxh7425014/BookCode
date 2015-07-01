 
 
 

#if !defined(AFX_ALLOCUNALLOC2D3D_H__E0CF2E6B_3F1D_4C0F_9451_CADB34917004__INCLUDED_)
#define AFX_ALLOCUNALLOC2D3D_H__E0CF2E6B_3F1D_4C0F_9451_CADB34917004__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class AllocUnAlloc2D3D  
{
public:
	void Unalloc3D_int(int ***&p, long r1, long r2);
	void Alloc3D_int(int ***&p, long r1, long r2, long r3);
	void Unalloc2D_String(CString **&p, long r1, long r2);
	void Alloc2D_String(CString **&p, long r1, long r2);
	void Unalloc1D_bool(bool *&p, long r1);
	void Alloc1D_bool(bool *&p, long r1);
	void Unalloc1D_int(int *&p, long r1);
	void Alloc1D_int(int *&p, long r1);
	
	void Unalloc2D_int(int **&p, long r1, long r2);
	void Alloc2D_int(int **&p, long r1, long r2);
	void Unalloc2D_bool(bool **&p, long r1, long r2);
	void Alloc2D_bool(bool **&p, long r1, long r2);
	void Unalloc2D_float(float **&p, long r1, long r2);
	void Alloc2D_float(float **&p, long r1, long r2);
	void Unalloc2D_BOOL(BOOL**   &p,   long   r1,   long   r2);
	void Alloc2D_BOOL(BOOL **&p, long r1, long r2);
	void Unalloc2D_double(double**   &p,   long   r1,   long   r2);
	void Alloc2D_double(double **&p, long r1, long r2);
	void Alloc3D_double(double  ***   &p,   long   r1,   long   r2,   long   r3);
	void Unalloc3D_double(double***   &p,   long   r1,   long   r2);

	AllocUnAlloc2D3D();
	virtual ~AllocUnAlloc2D3D();
private:
	
};

#endif // !defined(AFX_ALLOCUNALLOC2D3D_H__E0CF2E6B_3F1D_4C0F_9451_CADB34917004__INCLUDED_)
