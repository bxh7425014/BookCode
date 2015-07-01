// AllocUnAlloc2D3D.cpp: implementation of the AllocUnAlloc2D3D class.
 
 

#include "stdafx.h"
#include "AllocUnAlloc2D3D.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

 
// Construction/Destruction
 

AllocUnAlloc2D3D::AllocUnAlloc2D3D()
{

}

AllocUnAlloc2D3D::~AllocUnAlloc2D3D()
{

}

 
void AllocUnAlloc2D3D::Alloc3D_double(double ***&p, long r1, long r2, long r3)
{
	long   i,j;   
	p   =   new   double  **[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  double  *[r2];   
		for(j=0;   j<r2;   j++)   
		{   
			p[i][j]=new   double[r3];   
		}   
	}   
	
}

 
void AllocUnAlloc2D3D::Alloc2D_double(double **&p, long r1, long r2)
{
	long   i;   
	p   =   new   double  *[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  double  [r2];   
	
	}   
}

void AllocUnAlloc2D3D::Unalloc2D_double(double **&p, long r1, long r2)
{
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   

}

void AllocUnAlloc2D3D::Unalloc3D_double(double ***&p, long r1, long r2)
{
	long   i,j;   
	for(i=0;   i<r1;   i++)   
	{   
		for(j=0;   j<r2;   j++)   
		{   
			delete[]   p[i][j];   
		}   
		delete[]   p[i];   
	}   
	delete[]   p;   
	
}

 
void AllocUnAlloc2D3D::Alloc2D_BOOL(BOOL **&p, long r1, long r2)
{
		long   i;   
		p   =   new   BOOL  *[r1];   
		for(i=0;   i<r1;   i++)   
		{   
			p[i]   =   new  BOOL  [r2];   
			
		}   
	
}

void AllocUnAlloc2D3D::Unalloc2D_BOOL(BOOL **&p, long r1, long r2)
{
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   
}

 
void AllocUnAlloc2D3D::Alloc2D_float(float **&p, long r1, long r2)
{
	long   i;   
	p   =   new   float  *[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  float  [r2];   
		
	}   
}

void AllocUnAlloc2D3D::Unalloc2D_float(float **&p, long r1, long r2)
{
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   
	
}

void AllocUnAlloc2D3D::Alloc2D_bool(bool **&p, long r1, long r2)
{
	long   i;   
	p   =   new   bool  *[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  bool  [r2];   
		
	}   
}

void AllocUnAlloc2D3D::Unalloc2D_bool(bool **&p, long r1, long r2)
{
	
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   
}

 
void AllocUnAlloc2D3D::Alloc2D_int(int **&p, long r1, long r2)
{
	long   i;   
	p   =   new   int  *[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  int  [r2];   
		
	}   
}

void AllocUnAlloc2D3D::Unalloc2D_int(int **&p, long r1, long r2)
{
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   
	
}


void AllocUnAlloc2D3D::Alloc1D_bool(bool *&p, long r1)
{
	p   =   new   bool  [r1];   

}

void AllocUnAlloc2D3D::Unalloc1D_bool(bool *&p, long r1)
{
	delete[]   p;  
}

void AllocUnAlloc2D3D::Alloc1D_int(int *&p, long r1)
{
	p   =   new   int  [r1];   
	
}

void AllocUnAlloc2D3D::Unalloc1D_int(int *&p, long r1)
{
	delete[]   p;  
}

void AllocUnAlloc2D3D::Alloc2D_String(CString **&p, long r1, long r2)
{
	long   i;   
	p   =   new   CString  *[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  CString  [r2];   
		
	}   
}

void AllocUnAlloc2D3D::Unalloc2D_String(CString **&p, long r1, long r2)
{
	long   i;   
	for(i=0;   i<r1;   i++)   
	{   
		delete[]   p[i];   
	}   
	delete[]   p;   
}

 
void AllocUnAlloc2D3D::Alloc3D_int(int ***&p, long r1, long r2, long r3)
{
	long   i,j;   
	p   =   new   int  **[r1];   
	for(i=0;   i<r1;   i++)   
	{   
		p[i]   =   new  int  *[r2];   
		for(j=0;   j<r2;   j++)   
		{   
			p[i][j]=new   int[r3];   
		}   
	}   
}

void AllocUnAlloc2D3D::Unalloc3D_int(int ***&p, long r1, long r2)
{
	
	long   i,j;   
	for(i=0;   i<r1;   i++)   
	{   
		for(j=0;   j<r2;   j++)   
		{   
			delete[]   p[i][j];   
		}   
		delete[]   p[i];   
	}   
	delete[]   p;   
}
