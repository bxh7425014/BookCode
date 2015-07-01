 
#if !defined(AFX_Delaunay_H__D4420155_363F_4C25_8825_1822C8272B09__INCLUDED_)
#define AFX_Delaunay_H__D4420155_363F_4C25_8825_1822C8272B09__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include <set>
#include <algorithm>
#include <math.h>

using namespace std;
#include "Resource.h"       
#ifndef _GDIPLUS_H
typedef double REAL;

const int range = 1000;
#define colVertex	(RGB(255, 128, 0))

#if(WINVER >= 0x0400)
#define FONT	DEFAULT_GUI_FONT
#else
#define FONT	SYSTEM_FONT
#endif

inline int Int(REAL r)	{ return (int) floor(r + 0.5f);} 

/*
struct PointF
{
	PointF() : X(0), Y(0), Z(0)	{}
	PointF(const PointF& p) : X(p.X), Y(p.Y)	{}
	PointF(REAL x, REAL y, REAL z) : X(x), Y(y), Z(z)	{}
 
	PointF operator+(const PointF& p) const	{ return PointF(X + p.X, Y + p.Y,0); }
	PointF operator-(const PointF& p) const	{ return PointF(X - p.X, Y - p.Y,0); }
	REAL X;
	REAL Y;
	REAL Z;
 
 
};
const REAL REAL_EPSILON = 1.192092896e-07F;	

#endif
 

class vertex
{
public:
	vertex()					: m_Pnt(0.0F, 0.0F, 0.0F)			{}
	vertex(const vertex& v)		: m_Pnt(v.m_Pnt)			{}
	vertex(const PointF& pnt)	: m_Pnt(pnt)				{}
	vertex(REAL x, REAL y, REAL z)		: m_Pnt(x, y,z)				{}
	vertex(int x, int y, int z)		: m_Pnt((REAL) x, (REAL) y, (REAL) z)	{}
 
	
	bool operator<(const vertex& v) const
	{
		if (m_Pnt.X == v.m_Pnt.X) return m_Pnt.Y < v.m_Pnt.Y;
		return m_Pnt.X < v.m_Pnt.X;
	}

	bool operator==(const vertex& v) const
	{
		return m_Pnt.X == v.m_Pnt.X && m_Pnt.Y == v.m_Pnt.Y && m_Pnt.Z == v.m_Pnt.Z;
		
	}
	
	REAL GetX()	const	{ return m_Pnt.X; }
	REAL GetY() const	{ return m_Pnt.Y; }
	REAL GetZ() const	{ return m_Pnt.Z; }
	
 
	
	void SetX(REAL x)		{ m_Pnt.X = x; }
	void SetY(REAL y)		{ m_Pnt.Y = y; }
	void SetZ(REAL z)		{ m_Pnt.Z = z; }
	
	
	const PointF& GetPoint() const		{ return m_Pnt; }
protected:
	PointF	m_Pnt;
};

typedef set<vertex> vertexSet;
typedef set<vertex>::iterator vIterator;
typedef set<vertex>::const_iterator cvIterator;

 
 

class triangle
{
public:
	triangle(const triangle& tri): m_Center(tri.m_Center), m_R(tri.m_R), m_R2(tri.m_R2)
	{
		for (int i = 0; i < 3; i++) m_Vertices[i] = tri.m_Vertices[i];
	}
	triangle(const vertex * p0, const vertex * p1, const vertex * p2)
	{
		m_Vertices[0] = p0;
		m_Vertices[1] = p1;
		m_Vertices[2] = p2;
		SetCircumCircle();
	}
	triangle(const vertex * pV)
	{
		for (int i = 0; i < 3; i++) m_Vertices[i] = pV++;
		SetCircumCircle();
	}

	bool operator<(const triangle& tri) const
	{
		if (m_Center.X == tri.m_Center.X) return m_Center.Y < tri.m_Center.Y;
		return m_Center.X < tri.m_Center.X;
	}

	const vertex * GetVertex(int i) const
	{
		ASSERT(i >= 0);
		ASSERT(i < 3);
		return m_Vertices[i];
		
	}

	bool IsLeftOf(cvIterator itVertex) const
	{
		
		return itVertex->GetPoint().X > (m_Center.X + m_R);
	}

	bool CCEncompasses(cvIterator itVertex) const
	{
		
		PointF dist = itVertex->GetPoint() - m_Center;		
		REAL dist2 = dist.X * dist.X + dist.Y * dist.Y;		
		return dist2 <= m_R2;								
	}
protected:
	const vertex * m_Vertices[3];	
	PointF m_Center;				
	REAL m_R;		
	REAL m_R2;			
	
	void SetCircumCircle();
};

typedef multiset<triangle> triangleSet;
typedef multiset<triangle>::iterator tIterator;
typedef multiset<triangle>::const_iterator ctIterator;

 
 

class edge
{
public:
	edge(const edge& e)	: m_pV0(e.m_pV0), m_pV1(e.m_pV1)	{}
	edge(const vertex * pV0, const vertex * pV1)
		: m_pV0(pV0), m_pV1(pV1)
	{
	}

	bool operator<(const edge& e) const
	{
		if (m_pV0 == e.m_pV0) return * m_pV1 < * e.m_pV1;
		return * m_pV0 < * e.m_pV0;
	}

	const vertex * m_pV0;
	const vertex * m_pV1;
};

typedef set<edge> edgeSet;
typedef set<edge>::iterator edgeIterator;
typedef set<edge>::const_iterator cedgeIterator;

class CDelaunay  
{
public:
	void HandleEdge(const vertex * p0, const vertex * p1, edgeSet& edges);
	void TrianglesToEdges(const triangleSet& triangles, edgeSet& edges);
	void Triangulate(const vertexSet& vertices, triangleSet& output);
	CDelaunay();
	virtual ~CDelaunay();

};*/

struct PointF
{
	PointF() : X(0), Y(0),Z(0)	{}
	PointF(const PointF& p) : X(p.X), Y(p.Y), Z(p.Z)	{}
	PointF(REAL x, REAL y,REAL z) : X(x), Y(y),Z(z)	{}
	PointF operator+(const PointF& p) const	{ return PointF(X + p.X, Y + p.Y,Z + p.Z); }
	PointF operator-(const PointF& p) const	{ return PointF(X - p.X, Y - p.Y,Z - p.Z); }
	REAL X;
	REAL Y;
	REAL Z;
};
const REAL REAL_EPSILON = 1.192092896e-07F;	// = 2^-23; I've no idea why this is a good value, but GDI+ has it.

#endif
// vertex

class vertex
{
public:
	vertex()					: m_Pnt(0.0F, 0.0F, 0.0F)			{}
	vertex(const vertex& v)		: m_Pnt(v.m_Pnt)			{}
	vertex(const PointF& pnt)	: m_Pnt(pnt)				{}
	vertex(REAL x, REAL y,REAL z)		: m_Pnt(x, y,z)				{}
	vertex(int x, int y,int z)		: m_Pnt((REAL) x, (REAL) y, (REAL) z)	{}

	bool operator<(const vertex& v) const
	{
		if (m_Pnt.X == v.m_Pnt.X) return m_Pnt.Y < v.m_Pnt.Y;
		return m_Pnt.X < v.m_Pnt.X;
	}

	bool operator==(const vertex& v) const
	{
		return m_Pnt.X == v.m_Pnt.X && m_Pnt.Y == v.m_Pnt.Y;
	}
	
	REAL GetX()	const	{ return m_Pnt.X; }
	REAL GetY() const	{ return m_Pnt.Y; }
	REAL GetZ() const	{ return m_Pnt.Z; }
	
	void SetX(REAL x)		{ m_Pnt.X = x; }
	void SetY(REAL y)		{ m_Pnt.Y = y; }
	void SetZ(REAL z)		{ m_Pnt.Y = z; }
	
	const PointF& GetPoint() const		{ return m_Pnt; }
protected:
	PointF	m_Pnt;
};

typedef set<vertex> vertexSet;
typedef set<vertex>::iterator vIterator;
typedef set<vertex>::const_iterator cvIterator;

///////////////////
// triangle

class triangle
{
public:
	triangle(const triangle& tri): m_Center(tri.m_Center), m_R(tri.m_R), m_R2(tri.m_R2)
	{
		for (int i = 0; i < 3; i++) m_Vertices[i] = tri.m_Vertices[i];
	}
	triangle(const vertex * p0, const vertex * p1, const vertex * p2)
	{
		m_Vertices[0] = p0;
		m_Vertices[1] = p1;
		m_Vertices[2] = p2;
		SetCircumCircle();
	}
	triangle(const vertex * pV)
	{
		for (int i = 0; i < 3; i++) m_Vertices[i] = pV++;
		SetCircumCircle();
	}

	bool operator<(const triangle& tri) const
	{
		if (m_Center.X == tri.m_Center.X) return m_Center.Y < tri.m_Center.Y;
		return m_Center.X < tri.m_Center.X;
	}

	const vertex * GetVertex(int i) const
	{
		ASSERT(i >= 0);//If the result is 0, the macro prints a diagnostic message and aborts the program. If the condition is nonzero, it does nothing.This function is available only in the Debug version of MFC.
		ASSERT(i < 3);
		return m_Vertices[i];
		
	}

	bool IsLeftOf(cvIterator itVertex) const
	{
		// returns true if * itVertex is to the right of the triangle's circumcircle
		return itVertex->GetPoint().X > (m_Center.X + m_R);
	}

	bool CCEncompasses(cvIterator itVertex) const
	{
		//若点在三角形外接圆内则返回true
		PointF dist = itVertex->GetPoint() - m_Center;		
		DOUBLE dist2 = dist.X * dist.X + dist.Y * dist.Y;		
		return dist2 <= m_R2;								
	}
protected:
	const vertex * m_Vertices[3];	
	PointF m_Center;				
	DOUBLE m_R;		
	DOUBLE m_R2;			

	void SetCircumCircle();
};

typedef multiset<triangle> triangleSet;
typedef multiset<triangle>::iterator tIterator;
typedef multiset<triangle>::const_iterator ctIterator;

///////////////////
// edge

class edge
{
public:
	edge(const edge& e)	: m_pV0(e.m_pV0), m_pV1(e.m_pV1)	{}
	edge(const vertex * pV0, const vertex * pV1)
		: m_pV0(pV0), m_pV1(pV1)
	{
	}

	bool operator<(const edge& e) const
	{
		if (m_pV0 == e.m_pV0) return * m_pV1 < * e.m_pV1;
		return * m_pV0 < * e.m_pV0;
	}

	const vertex * m_pV0;
	const vertex * m_pV1;
};

typedef set<edge> edgeSet;
typedef set<edge>::iterator edgeIterator;
typedef set<edge>::const_iterator cedgeIterator;

///////////////////
// Delaunay

class CDelaunay  
{
public:
	long m_triNums;
	void HandleEdge(const vertex * p0, const vertex * p1, edgeSet& edges);
	void TrianglesToEdges(const triangleSet& triangles, edgeSet& edges);
	void Triangulate(const vertexSet& vertices, triangleSet& output);
	CDelaunay();
	virtual ~CDelaunay();

};
#endif // !defined(AFX_Delaunay_H__D4420155_363F_4C25_8825_1822C8272B09__INCLUDED_)
