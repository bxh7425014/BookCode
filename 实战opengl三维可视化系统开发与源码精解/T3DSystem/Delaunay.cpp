// Delaunay.cpp: implementation of the CDelaunay class.
 
 

#include "stdafx.h"
#include "Delaunay.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

 
// Construction/Destruction
 
class drawVertex
{
public:
	drawVertex(CDC& dc, REAL hScale, REAL vScale) : m_DC(dc), m_hScale(hScale), m_vScale(vScale)	{}
	void operator()(const vertex& v) const
	{
		const int halfSize = 2;
		
		CRect rc;
		rc.SetRectEmpty();
		rc.InflateRect(halfSize, halfSize);
		rc.OffsetRect(int(m_hScale * v.GetX()), Int(m_vScale * v.GetY()));
		m_DC.FillSolidRect(& rc, colVertex);
	}
protected:
	CDC& m_DC;
	REAL m_hScale;
	REAL m_vScale;
};

class drawEdge
{
public:
	drawEdge(CDC& dc, REAL hScale, REAL vScale) : m_DC(dc), m_hScale(hScale), m_vScale(vScale)	{}
	void operator()(const edge& e) const
	{
		m_DC.MoveTo(Int(m_hScale * e.m_pV0->GetX()), Int(m_vScale * e.m_pV0->GetY()));
		m_DC.LineTo(Int(m_hScale * e.m_pV1->GetX()), Int(m_vScale * e.m_pV1->GetY()));
	}
protected:
	CDC& m_DC;
	REAL m_hScale;
	REAL m_vScale;
};


class drawTriangle
{
public:
	drawTriangle(CDC& dc, REAL hScale, REAL vScale) : m_DC(dc), m_hScale(hScale), m_vScale(vScale)	{}
	void operator()(const triangle& tri) const
	{
		const vertex * v0 = tri.GetVertex(0);
		m_DC.MoveTo(Int(m_hScale * v0->GetX()), Int(m_vScale * v0->GetY()));
		const vertex * v1 = tri.GetVertex(1);
		m_DC.LineTo(Int(m_hScale * v1->GetX()), Int(m_vScale * v1->GetY()));
		const vertex * v2 = tri.GetVertex(2);
		m_DC.LineTo(Int(m_hScale * v2->GetX()), Int(m_vScale * v2->GetY()));
		m_DC.LineTo(Int(m_hScale * v0->GetX()), Int(m_vScale * v0->GetY()));
	}
protected:
	CDC& m_DC;
	REAL m_hScale;
	REAL m_vScale;
};

const REAL sqrt3 = 1.732050808F;
void triangle::SetCircumCircle()
{
	REAL x0 = m_Vertices[0]->GetX();
	REAL y0 = m_Vertices[0]->GetY();

	REAL x1 = m_Vertices[1]->GetX();
	REAL y1 = m_Vertices[1]->GetY();

	REAL x2 = m_Vertices[2]->GetX();
	REAL y2 = m_Vertices[2]->GetY();

	REAL y10 = y1 - y0;
	REAL y21 = y2 - y1;

	bool b21zero = y21 > -REAL_EPSILON && y21 < REAL_EPSILON;

	if (y10 > -REAL_EPSILON && y10 < REAL_EPSILON)
	{
		if (b21zero)	
		{
			if (x1 > x0)
			{
				if (x2 > x1) x1 = x2;
			}
			else
			{
				if (x2 < x0) x0 = x2;
			}
			m_Center.X = (x0 + x1) * .5F;
			m_Center.Y = y0;
		}
		else	
		{
			REAL m1 = - (x2 - x1) / y21;

			REAL mx1 = (x1 + x2) * .5F;
			REAL my1 = (y1 + y2) * .5F;

			m_Center.X = (x0 + x1) * .5F;
			m_Center.Y = m1 * (m_Center.X - mx1) + my1;
		}
	}
	else if (b21zero)	
	{
		REAL m0 = - (x1 - x0) / y10;

		REAL mx0 = (x0 + x1) * .5F;
		REAL my0 = (y0 + y1) * .5F;

		m_Center.X = (x1 + x2) * .5F;
		m_Center.Y = m0 * (m_Center.X - mx0) + my0;
	}
	else	
	{
		REAL m0 = - (x1 - x0) / y10;
		REAL m1 = - (x2 - x1) / y21;

		REAL mx0 = (x0 + x1) * .5F;
		REAL my0 = (y0 + y1) * .5F;

		REAL mx1 = (x1 + x2) * .5F;
		REAL my1 = (y1 + y2) * .5F;

		m_Center.X = (m0 * mx0 - m1 * mx1 + my1 - my0) / (m0 - m1);
		m_Center.Y = m0 * (m_Center.X - mx0) + my0;
	}

	REAL dx = x0 - m_Center.X;
	REAL dy = y0 - m_Center.Y;

	m_R2 = dx * dx + dy * dy;	
	m_R = (REAL) sqrt(m_R2);	
	m_R2 *= 1.000001f;  
}

 
class triangleHasVertex
{
public:
	triangleHasVertex(const vertex SuperTriangle[3]) : m_pSuperTriangle(SuperTriangle)	{}
	bool operator()(const triangle& tri) const
	{
		for (int i = 0; i < 3; i++)
		{
			const vertex * p = tri.GetVertex(i);
			if (p >= m_pSuperTriangle && p < (m_pSuperTriangle + 3)) return true;
		}
		return false;
	}
protected:
	const vertex * m_pSuperTriangle;
};

 
 
class triangleIsCompleted
{
public:
	triangleIsCompleted(cvIterator itVertex, triangleSet& output, const vertex SuperTriangle[3])
		: m_itVertex(itVertex)
		, m_Output(output)
		, m_pSuperTriangle(SuperTriangle)
	{}
	bool operator()(const triangle& tri) const
	{
		bool b = tri.IsLeftOf(m_itVertex); 

		if (b)
		{
			triangleHasVertex thv(m_pSuperTriangle);
			if (! thv(tri)) m_Output.insert(tri);
		}
		return b;
	}

protected:
	cvIterator m_itVertex;
	triangleSet& m_Output;
	const vertex * m_pSuperTriangle;
};

 
class vertexIsInCircumCircle
{
public:
	vertexIsInCircumCircle(cvIterator itVertex, edgeSet& edges) : m_itVertex(itVertex), m_Edges(edges)	{}
	bool operator()(const triangle& tri) const
	{
		bool b = tri.CCEncompasses(m_itVertex);

		if (b)
		{
			HandleEdge(tri.GetVertex(0), tri.GetVertex(1));
			HandleEdge(tri.GetVertex(1), tri.GetVertex(2));
			HandleEdge(tri.GetVertex(2), tri.GetVertex(0));
		}
		return b;
	}
protected:
	void HandleEdge(const vertex * p0, const vertex * p1) const
	{
		const vertex * pVertex0=NULL;
		const vertex * pVertex1=NULL;

		if (* p0 < * p1)
		{
			pVertex0 = p0;
			pVertex1 = p1;
		}
		else
		{
			pVertex0 = p1;
			pVertex1 = p0;
		}

		edge e(pVertex0, pVertex1);

		
		edgeIterator found = m_Edges.find(e);

		if (found == m_Edges.end()) m_Edges.insert(e);		
		else m_Edges.erase(found);							
	}

	cvIterator m_itVertex;
	edgeSet& m_Edges;
};


CDelaunay::CDelaunay()
{

}

CDelaunay::~CDelaunay()
{

}

 
void CDelaunay::Triangulate(const vertexSet &vertices, triangleSet &output)
{
	if (vertices.size() < 3) return;	
	
	
	cvIterator itVertex = vertices.begin();
	
	REAL xMin = itVertex->GetX();
	REAL yMin = itVertex->GetY();
	REAL xMax = xMin;
	REAL yMax = yMin;
	
	
	++itVertex;		
	for (; itVertex != vertices.end(); itVertex++)
	{
		xMax = itVertex->GetX();	
		REAL y = itVertex->GetY();
		if (y < yMin) yMin = y;
		if (y > yMax) yMax = y;
	}
	
	REAL dx = xMax - xMin;
	REAL dy = yMax - yMin;
	
	
	REAL ddx = dx * 0.01F;
	REAL ddy = dy * 0.01F;
	
	xMin -= ddx;
	xMax += ddx;
	dx += 2 * ddx;
	
	yMin -= ddy;
	yMax += ddy;
	dy += 2 * ddy;
	
	
	vertex vSuper[3];
	
	vSuper[0] = vertex(xMin - dy * sqrt3 / 3.0F, yMin,0.0);	
	vSuper[1] = vertex(xMax + dy * sqrt3 / 3.0F, yMin,0.0);
	vSuper[2] = vertex((xMin + xMax) * 0.5F, yMax + dx * sqrt3 * 0.5F,0.0);
	
	triangleSet workset;
	workset.insert(triangle(vSuper));
	long mTrnums=0;
	for (itVertex = vertices.begin(); itVertex != vertices.end(); itVertex++)
	{
		
		tIterator itEnd = remove_if(workset.begin(), workset.end(), triangleIsCompleted(itVertex, output, vSuper));
		edgeSet edges;
		
		
		itEnd = remove_if(workset.begin(), itEnd, vertexIsInCircumCircle(itVertex, edges));
		workset.erase(itEnd, workset.end());	
		
		
		for (edgeIterator it = edges.begin(); it != edges.end(); it++)
		{
			workset.insert(triangle(it->m_pV0, it->m_pV1, & (* itVertex)));
		}
	}
	
	mTrnums=0;
	for(tIterator Tri=workset.begin();Tri != workset.end(); Tri++)
	{
		mTrnums++;
	}
	
	
	tIterator where = output.begin();
	remove_copy_if(workset.begin(), workset.end(), inserter(output, where), triangleHasVertex(vSuper));
	
	
	
	
	
	
	
	
	
	long count=mTrnums;
}

void CDelaunay::TrianglesToEdges(const triangleSet &triangles, edgeSet &edges)
{
	m_triNums=0;
	for (ctIterator it = triangles.begin(); it != triangles.end(); ++it)
	{
		HandleEdge(it->GetVertex(0), it->GetVertex(1), edges);
		HandleEdge(it->GetVertex(1), it->GetVertex(2), edges);
		HandleEdge(it->GetVertex(2), it->GetVertex(0), edges);
		m_triNums++;
	}
}

void CDelaunay::HandleEdge(const vertex *p0, const vertex *p1, edgeSet &edges)
{
	const vertex * pV0=NULL;
	const vertex * pV1=NULL;
	
	if (* p0 < * p1)
	{
		pV0 = p0;
		pV1 = p1;
	}
	else
	{
		pV0 = p1;
		pV1 = p0;
	}
	edges.insert(edge(pV0, pV1));
}
