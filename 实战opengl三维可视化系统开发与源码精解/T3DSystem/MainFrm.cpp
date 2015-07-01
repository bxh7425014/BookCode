// MainFrm.cpp : implementation of the CMainFrame class
//

#include "stdafx.h"
#include "T3DSystem.h"

#include "MainFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CMainFrame

IMPLEMENT_DYNCREATE(CMainFrame, CFrameWnd)

BEGIN_MESSAGE_MAP(CMainFrame, CFrameWnd)
	//{{AFX_MSG_MAP(CMainFrame)
	ON_WM_CREATE()
	ON_WM_PAINT()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

static UINT indicators[] =
{
		ID_INDICATOR_LOADRENDERDEM,//【内存/渲染块数】
		ID_INDICATOR_FPS,//刷新频率
		ID_INDICATOR_TRINUMBER,//三角形数
		ID_INDICATOR_FSANGLE,//【俯视角】=
		ID_INDICATOR_VIEWPOS,//视点坐标
		ID_INDICATOR_PROGRESSBAR,//进度条
		
};

/////////////////////////////////////////////////////////////////////////////
// CMainFrame construction/destruction

CMainFrame::CMainFrame()
{
  //   skinppLoadSkin(_T("skin\\AlphaOS.ssk")); //加载皮肤
	
}

CMainFrame::~CMainFrame()
{
//	skinppExitSkin(); //退出界面库，做清理工作。
}

int CMainFrame::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CFrameWnd::OnCreate(lpCreateStruct) == -1)
		return -1;
	

	if (!m_wndToolBar.CreateEx(this, TBSTYLE_FLAT, WS_CHILD | WS_VISIBLE | CBRS_TOP
		| CBRS_GRIPPER | CBRS_TOOLTIPS | CBRS_FLYBY | CBRS_SIZE_DYNAMIC) ||
		!m_wndToolBar.LoadToolBar(IDR_MAINFRAME))
	{
		TRACE0("Failed to create toolbar\n");
		return -1;      
	}

	//创建正射投影模式下的工具栏
	if (!m_wndOrthoToolBar.CreateEx(this, TBSTYLE_FLAT, WS_CHILD  | CBRS_TOP
		| CBRS_GRIPPER | CBRS_TOOLTIPS | CBRS_FLYBY | CBRS_SIZE_DYNAMIC) ||
		!m_wndOrthoToolBar.LoadToolBar(IDR_TOOLBAR_ORTHO))
	{
		TRACE0("Failed to create toolbar\n");
		return -1;      
	}

	if (!m_wndStatusBar.Create(this) ||
		!m_wndStatusBar.SetIndicators(indicators,
		  sizeof(indicators)/sizeof(UINT)))
	{
		TRACE0("Failed to create status bar\n");
		return -1;      // fail to create
	}

	//设置各指示器的索引、宽度等信息
	m_wndStatusBar.SetPaneInfo(0, ID_INDICATOR_LOADRENDERDEM,SBPS_NORMAL,155);
	m_wndStatusBar.SetPaneInfo(1, ID_INDICATOR_FPS,SBPS_NORMAL,85);
	m_wndStatusBar.SetPaneInfo(2, ID_INDICATOR_TRINUMBER,SBPS_NORMAL,90);
	m_wndStatusBar.SetPaneInfo(3, ID_INDICATOR_FSANGLE,SBPS_NORMAL,120);
	m_wndStatusBar.SetPaneInfo(4, ID_INDICATOR_VIEWPOS,SBPS_NORMAL,350);
	m_wndStatusBar.SetPaneInfo(5, ID_INDICATOR_PROGRESSBAR,SBPS_STRETCH,100);


	RECT rect;
	int index=m_wndToolBar.CommandToIndex(ID_TOOL_SCHEME);
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_SCHEME, TBBS_SEPARATOR, 120);
    m_wndToolBar.GetItemRect(index, &rect);
    rect.top+=2;
    rect.bottom += 200;

	if (!m_wndToolBar.m_wndScheme.Create(WS_CHILD|WS_VISIBLE | CBS_AUTOHSCROLL | 
		CBS_DROPDOWNLIST | CBS_HASSTRINGS ,
		rect, &m_wndToolBar, ID_TOOL_SCHEME))
    {
		TRACE0("Failed to create combo-box\n");
		return FALSE;
    }
    m_wndToolBar.m_wndScheme.ShowWindow(SW_SHOW);

	
	m_wndToolBar.EnableDocking(CBRS_ALIGN_ANY);
	EnableDocking(CBRS_ALIGN_ANY);//	工具栏
	DockControlBar(&m_wndToolBar);
	
	m_wndOrthoToolBar.EnableDocking(CBRS_ALIGN_ANY);
	EnableDocking(CBRS_ALIGN_ANY);//工具栏
	DockControlBar(&m_wndOrthoToolBar);
	m_wndOrthoToolBar.ShowWindow(SW_SHOW);	//工具栏可见
	
	return 0;
}

BOOL CMainFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	if( !CFrameWnd::PreCreateWindow(cs) )
		return FALSE;
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CMainFrame diagnostics

#ifdef _DEBUG
void CMainFrame::AssertValid() const
{
	CFrameWnd::AssertValid();
}

void CMainFrame::Dump(CDumpContext& dc) const
{
	CFrameWnd::Dump(dc);
}

#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMainFrame message handlers

//根据索引在状态栏上显示相关信息
void CMainFrame::Set_BarText(int index, CString strText, int nPos)
{
	switch(index)
	{
	case 0://【内存/渲染块数】
		m_wndStatusBar.SetPaneText (index,strText);
		break;
	case 1://刷新频率
		m_wndStatusBar.SetPaneText (index,strText);
		break;
	case 2: //三角形数
		m_wndStatusBar.SetPaneText (index,strText);
		break;
	case 3: //【俯视角】
		m_wndStatusBar.SetPaneText (index,strText);
		break;
	case 4: //视点坐标
		m_wndStatusBar.SetPaneText (index,strText);
		break;
	case 5: //进度条

		if(nPos<=0)
		{
			m_progress.ShowWindow(SW_HIDE);//隐藏进度条
			m_wndStatusBar.SetPaneText (index,strText);
		}
		else
		{	
	//		m_progress.ShowWindow(SW_SHOW);//显示进度条
	//		m_progress.SetPos(nPos);
		}

		break;
		
	}
}

//刷新状态栏指示器上的进度条
void CMainFrame::OnPaint() 
{
	CPaintDC dc(this); // device context for painting
	
	CRect rect;
	//得到状态栏指示器上存放进度条的客户区大小
	m_wndStatusBar.GetItemRect(m_wndStatusBar.CommandToIndex(ID_INDICATOR_PROGRESSBAR),&rect);
	if (!m_progress.m_hWnd)  //如果没有创建进度条控件，则创建                     
	{
		m_progress.Create(WS_CHILD|PBS_SMOOTH , rect, &m_wndStatusBar, 123);
	}
	else
	{
		m_progress.MoveWindow(rect);    //将进度条移动到状态栏指示器上对应的位置   
	}
}



void CMainFrame::AddSchemeName()
{
	m_wndToolBar.m_wndScheme.ResetContent();
	for(int i=0;i<m_SchemeNames.GetSize();i++)
	{
		m_wndToolBar.m_wndScheme.AddString(m_SchemeNames.GetAt(i));
		
	}
	if(m_wndToolBar.m_wndScheme.GetCount()>0)
	{
		m_wndToolBar.m_wndScheme.SetCurSel(0);
		OnSelectScheme();
	}
	myDesingScheme.m_TotalSchemeNums=m_wndToolBar.m_wndScheme.GetCount();
	if(myDesingScheme.m_TotalSchemeNums<0)
		myDesingScheme.m_TotalSchemeNums=0;
}

void CMainFrame::OnSelectScheme()
{
	CString strSchemeName;
	m_wndToolBar.m_wndScheme.GetWindowText(strSchemeName);
	m_currentSchemeIndexs=m_wndToolBar.m_wndScheme.GetCurSel();
	m_currentSchemeNames=strSchemeName; 
	myDesingScheme.LoadData(strSchemeName);	
	LoadSchemeData();
	myDesingScheme.JDCurveElements.RemoveAll();
}

void CMainFrame::LoadSchemeData()
{	
	CString tt,strsql;
	
 
	strsql.Format("select * FROM  Scheme_plane_CureveData WHERE 方案名称='%s' ORDER BY 交点序号 ASC",m_currentSchemeNames);	
	try
	{
		m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		AfxMessageBox(errormessage);
		m_Recordset->Close();
		return;
	} 

 
	myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_JD.RemoveAll();
	PCordinate ppt;
	PLineCurve pTempCurveElements;
	while(!m_Recordset->adoEOF)
	{
		pTempCurveElements=new LineCurve;
		Thevalue = m_Recordset->GetCollect("交点编号"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			pTempCurveElements->ID=tt;
		}
	
		Thevalue = m_Recordset->GetCollect("转角"); 
		pTempCurveElements->Alfa=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("方位角1"); 
		pTempCurveElements->fwj=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("方位角2"); 
		pTempCurveElements->fwj2=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("转向"); 
		pTempCurveElements->RoateStyle=(short)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("缓和曲线长"); 
		pTempCurveElements->L0=(short)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("切线长"); 
		pTempCurveElements->T=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("曲线长"); 
		pTempCurveElements->L=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("圆曲线长"); 
		pTempCurveElements->Ly=(double)Thevalue;

		Thevalue = m_Recordset->GetCollect("直缓点里程"); 
		pTempCurveElements->ZH=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("缓圆点里程"); 
		pTempCurveElements->HY=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("圆缓点里程"); 
		pTempCurveElements->YH=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("缓直点里程"); 
		pTempCurveElements->HZ=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("外矢距"); 
		pTempCurveElements->E=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("内移距"); 
		pTempCurveElements->P=(double)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("曲线半径"); 
		pTempCurveElements->R=(long)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("交点里程"); 
		pTempCurveElements->JDLC=(long)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("交点x坐标"); 
		pTempCurveElements->x=(double)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("交点y坐标"); 
		pTempCurveElements->y=(double)Thevalue;
	
		Thevalue = m_Recordset->GetCollect("交点z坐标"); 
		pTempCurveElements->z=(double)Thevalue;
		
 
		myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].Add(pTempCurveElements);
	
		ppt=new Cordinate;
		ppt->x=pTempCurveElements->x;ppt->y=pTempCurveElements->z;ppt->z=-pTempCurveElements->y;
		myDesingScheme.PtS_JD.Add(ppt);
		
		m_Recordset->MoveNext();
	}
	m_Recordset->Close();


	
	
	strsql.Format("select * FROM  Scheme_plane_CureveData_XY WHERE 方案名称='%s' ORDER BY 交点序号 ASC",m_currentSchemeNames);	
	try
	{
		m_Recordset->Open(_bstr_t(strsql),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s",e.ErrorMessage());
		AfxMessageBox(errormessage);
		m_Recordset->Close();
		return;
	} 

	long n=0;
	while(!m_Recordset->adoEOF)
	{
		pTempCurveElements=myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].GetAt(n);
	
 
 
 
 
 
		
		Thevalue = m_Recordset->GetCollect("圆心x坐标"); 
		pTempCurveElements->Cneterx=(double)Thevalue;
		
		Thevalue = m_Recordset->GetCollect("圆心y坐标"); 
		pTempCurveElements->Cnetery=(double)Thevalue;
		
		pTempCurveElements->ZH_xy=new  Cordinate;		
		Thevalue = m_Recordset->GetCollect("直缓x坐标"); 
		pTempCurveElements->ZH_xy->x=(double)Thevalue;
		Thevalue = m_Recordset->GetCollect("直缓y坐标"); 
		pTempCurveElements->ZH_xy->y=(double)Thevalue;
	
		pTempCurveElements->HY_xy=new  Cordinate;		
		Thevalue = m_Recordset->GetCollect("缓圆x坐标"); 
		pTempCurveElements->HY_xy->x=(double)Thevalue;
		Thevalue = m_Recordset->GetCollect("缓圆y坐标"); 
		pTempCurveElements->HY_xy->y=(double)Thevalue;

		pTempCurveElements->YH_xy=new  Cordinate;		
		Thevalue = m_Recordset->GetCollect("圆缓x坐标"); 
		pTempCurveElements->YH_xy->x=(double)Thevalue;
		Thevalue = m_Recordset->GetCollect("圆缓y坐标"); 
		pTempCurveElements->YH_xy->y=(double)Thevalue;
		
		pTempCurveElements->HZ_xy=new  Cordinate;		
		Thevalue = m_Recordset->GetCollect("缓直x坐标"); 
		pTempCurveElements->HZ_xy->x=(double)Thevalue;
		Thevalue = m_Recordset->GetCollect("缓直y坐标"); 
		pTempCurveElements->HZ_xy->y=(double)Thevalue;
		
		n++;
		m_Recordset->MoveNext();
	}
	m_Recordset->Close();
   	


}
