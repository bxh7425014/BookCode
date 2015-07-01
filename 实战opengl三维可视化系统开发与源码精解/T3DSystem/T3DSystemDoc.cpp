// T3DSystemDoc.cpp : implementation of the CT3DSystemDoc class
//

#include "stdafx.h"
#include "T3DSystem.h"

#include "T3DSystemDoc.h"
#include "DemLoad.H"
#include "TextureLoad.h"
#include "NewProject.h"
#include "NewScheme.h"
#include "PlaneGraphic.h"
#include "ZdmDesign.h"


#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemDoc

IMPLEMENT_DYNCREATE(CT3DSystemDoc, CDocument)

BEGIN_MESSAGE_MAP(CT3DSystemDoc, CDocument)
	//{{AFX_MSG_MAP(CT3DSystemDoc)
	ON_COMMAND(ID_MENU_DEM, OnMenuDem)
	ON_COMMAND(ID_MENU_TEXTUREIMAGE, OnMenuTextureimage)
	ON_COMMAND(ID_MENU_NEWPROJECT, OnMenuNewproject)
	ON_COMMAND(ID_MENU_LINESCHEME, OnMenuLinescheme)
	ON_COMMAND(ID_MENU_PLANE_GRAPH, OnMenuPlaneGraph)
	ON_COMMAND(ID_MENU_ZDMDESIGN, OnMenuZdmdesign)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemDoc construction/destruction

CT3DSystemDoc::CT3DSystemDoc()
{
	// TODO: add one-time construction code here

}

CT3DSystemDoc::~CT3DSystemDoc()
{
}

BOOL CT3DSystemDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}



/////////////////////////////////////////////////////////////////////////////
// CT3DSystemDoc serialization

void CT3DSystemDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemDoc diagnostics

#ifdef _DEBUG
void CT3DSystemDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CT3DSystemDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemDoc commands

//数模读取与分块处理	
void CT3DSystemDoc::OnMenuDem() 
{
	CDemLoad dlg;
	dlg.DoModal();

}

//纹理影像入库
void CT3DSystemDoc::OnMenuTextureimage() 
{
		
	CTextureLoad dlg;
	dlg.DoModal();
}

void CT3DSystemDoc::OnMenuNewproject() 
{
	CNewProject dlg;
	dlg.DoModal();
	
}

//项目方案控制参数
void CT3DSystemDoc::OnMenuLinescheme() 
{
	CNewScheme dlg;
	dlg.DoModal();	
}



void CT3DSystemDoc::OnMenuPlaneGraph() 
{
		CPlaneGraphic dlg;
		if(dlg.DoModal()==IDOK)
		{
			Sleep(0);
		}

		
	
}

//纵断面设计
void CT3DSystemDoc::OnMenuZdmdesign() 
{
	CZdmDesign dlg;
	dlg.DoModal();
	
}
