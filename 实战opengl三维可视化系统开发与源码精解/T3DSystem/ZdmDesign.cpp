// ZdmDesign.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "ZdmDesign.h"
#include "ZdmGeoSegmentFeatures.h"
#include "ZdmSlopeData.h"
#include "ZdmJtLpPcPD.h"
#include "ZdmStartLC_H.h"
#include "TunnelData.h"
#include "BridgeData.h"
#include "DialogInputData.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CZdmDesign dialog


CZdmDesign::CZdmDesign(CWnd* pParent /*=NULL*/)
	: CDialog(CZdmDesign::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZdmDesign)
	m_lcCorInfo = _T("");
	//}}AFX_DATA_INIT
}


void CZdmDesign::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZdmDesign)
	DDX_Control(pDX, IDC_STATIC_LCCORINFO, m_STAICLCINfo);
	DDX_Text(pDX, IDC_STATIC_LCCORINFO, m_lcCorInfo);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CZdmDesign, CDialog)
	//{{AFX_MSG_MAP(CZdmDesign)
	ON_WM_PAINT()
	ON_COMMAND(ID_ZDM_GEOFEATURE, OnZdmGeofeature)
	ON_WM_LBUTTONDOWN()
	ON_WM_CTLCOLOR()
	ON_WM_MOUSEMOVE()
	ON_WM_HSCROLL()
	ON_COMMAND(ID_MENU_ZDM_EXIT, OnMenuZdmExit)
	ON_WM_VSCROLL()
	ON_COMMAND(ID_ZDM_DESIGN_ADDPT, OnZdmDesignAddpt)
	ON_COMMAND(ID_ZDM_MENU_SAVE, OnZdmMenuSave)
	ON_COMMAND(ID_MENU_ZDM_SLOPEDATA, OnMenuZdmSlopedata)
	ON_WM_RBUTTONDOWN()
	ON_WM_SETCURSOR()
	ON_COMMAND(ID_MENU_ZDM_JDLPPCPD, OnMenuZdmJdlppcpd)
	ON_COMMAND(ID_ZDM_STARTLC_H, OnZdmStartlcH)
	ON_COMMAND(ID_ZDM_TUNNEL, OnZdmTunnel)
	ON_COMMAND(ID_ZDM_BRIDGE, OnZdmBridge)
	ON_COMMAND(ID_ZDM_GRAPHTUNNEL, OnZdmGraphtunnel)
	ON_COMMAND(ID_ZDM_GRAPHBRIDGE, OnZdmGraphbridge)
	ON_WM_CANCELMODE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CZdmDesign message handlers

BOOL CZdmDesign::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	ShowWindow(SW_SHOWMAXIMIZED);
	m_brush.CreateSolidBrush(RGB( 255, 255, 255)); 
	m_hAddPtCursor =AfxGetApp()->LoadStandardCursor(IDC_CROSS);
	CreateToolbar();
	InitData();
	CreateFonts();
	AddSchemeName();
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

 
void CZdmDesign::CreateToolbar()
{
	RECT rect;
	int index ;

	m_wndToolBar.CreateEx(this,TBSTYLE_FLAT,WS_CHILD|WS_VISIBLE|CBRS_TOP|CBRS_TOOLTIPS ,CRect(0,0,00,00),AFX_IDW_TOOLBAR);
	m_wndToolBar.LoadToolBar(IDR_TOOLBAR_ZDM);
	RepositionBars(AFX_IDW_CONTROLBAR_FIRST, AFX_IDW_CONTROLBAR_LAST, 0);
		
	
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_SCHEME);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_SCHEME, TBBS_SEPARATOR, 120);
    m_wndToolBar.GetItemRect(index, &rect);
    
    rect.top+=2;
    rect.bottom += 100;
	
    
    if (!m_wndToolBar.m_wndScheme_ZDM.Create(WS_CHILD|WS_VISIBLE | CBS_AUTOHSCROLL | 
		CBS_DROPDOWNLIST | CBS_HASSTRINGS ,
		rect, &m_wndToolBar, ID_TOOL_ZDM_SCHEME))
    {
		TRACE0("Failed to create combo-box\n");
		return ;
    }

	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_YSCALETEXT);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_YSCALETEXT, TBBS_SEPARATOR, 60);
    m_wndToolBar.GetItemRect(index, &rect);
   	m_wndToolBar.m_wndZDM_YScaleTEXT=new CEdit();
	rect.top=4;
	rect.bottom =24;
	m_wndToolBar.m_wndZDM_YScaleTEXT->Create(WS_CHILD|WS_VISIBLE|WS_BORDER,rect,this,ID_TOOL_ZDM_YSCALETEXT);
	m_wndToolBar.m_wndZDM_YScaleTEXT->SetWindowText("400");

	
	
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_YSCALE);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_YSCALE, TBBS_SEPARATOR,20);
    m_wndToolBar.GetItemRect(index, &rect);
	
	rect.top=1;
 
	m_wndToolBar.m_wndZDM_YSpinButton=new CSpinButtonCtrl();
    
	if (!m_wndToolBar.m_wndZDM_YSpinButton->Create(UDS_ALIGNRIGHT|WS_CHILD|\
		UDS_SETBUDDYINT|UDS_AUTOBUDDY|WS_VISIBLE,\
		rect, &m_wndToolBar, ID_TOOL_ZDM_YSCALE))
	{
		TRACE0("Failed to create combo-box\n");
		return ;
    }
	m_wndToolBar.GetItemRect(index, &rect);
	m_wndToolBar.m_wndZDM_YSpinButton->MoveWindow(&rect,TRUE);
	m_wndToolBar.m_wndZDM_YSpinButton->SetRange(50, 5000);
	m_wndToolBar.m_wndZDM_YSpinButton->SetPos(400);
 
	m_wndToolBar.m_wndZDM_YSpinButton->ShowWindow(SW_HIDE);
	m_wndToolBar.m_wndZDM_YScaleTEXT->ShowWindow(SW_HIDE);
	
	

	
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_XSCALETEXT);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_XSCALETEXT, TBBS_SEPARATOR, 60);
    m_wndToolBar.GetItemRect(index, &rect);
   	m_wndToolBar.m_wndZDM_XScaleTEXT=new CEdit();
	rect.top=4;
	rect.bottom =24;
	m_wndToolBar.m_wndZDM_XScaleTEXT->Create(WS_CHILD|WS_VISIBLE|WS_BORDER,rect,this,ID_TOOL_ZDM_XSCALETEXT);
	
	
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_XSCALE);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_XSCALE, TBBS_SEPARATOR,20);
    m_wndToolBar.GetItemRect(index, &rect);
	
	rect.top=1;
	rect.bottom =23;
	m_wndToolBar.m_wndZDM_XSpinButton=new CSpinButtonCtrl();
    
	if (!m_wndToolBar.m_wndZDM_XSpinButton->Create(UDS_ALIGNRIGHT|WS_CHILD|\
		UDS_SETBUDDYINT|UDS_AUTOBUDDY|WS_VISIBLE,\
		rect, &m_wndToolBar, ID_TOOL_ZDM_XSCALE))
	{
		TRACE0("Failed to create SPIN\n");
		return ;
    }
	m_wndToolBar.m_wndZDM_XSpinButton->MoveWindow(&rect,TRUE);
	m_wndToolBar.m_wndZDM_XSpinButton->SetRange(1, 20); 
	m_wndToolBar.m_wndZDM_XSpinButton->SetPos(4);
	m_wndToolBar.m_wndZDM_XScaleTEXT->SetWindowText("4000");
	
 
	


	
	
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_LC_HINFO);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_LC_HINFO, TBBS_SEPARATOR,240);
    m_wndToolBar.GetItemRect(index, &rect);
   	m_wndToolBar.m_wndZDM_LCHTEXT=new CEdit();
	rect.top=4;
	rect.bottom =24;
	m_wndToolBar.m_wndZDM_LCHTEXT->Create(WS_CHILD|WS_VISIBLE|WS_BORDER,rect,this,ID_TOOL_ZDM_YSCALETEXT);
	m_wndToolBar.m_wndZDM_LCHTEXT->SetWindowText("");
	
	m_wndToolBar.m_progress=new CProgressCtrl();
	index=m_wndToolBar.CommandToIndex(ID_TOOL_ZDM_PROGRESS);
    
    m_wndToolBar.SetButtonInfo(index, ID_TOOL_ZDM_LC_HINFO, TBBS_SEPARATOR,250);
    m_wndToolBar.GetItemRect(index, &rect);
	
	rect.top=4;
	rect.left=rect.left +5;
	rect.bottom =24;
	m_wndToolBar.m_progress->Create(WS_CHILD|\
		WS_BORDER|WS_BORDER|PBS_SMOOTH,rect,this,ID_PROGRESS);
	m_wndToolBar.m_progress->SetRange(0,100);
	
}



 
void CZdmDesign::AddSchemeName()
{
	
	m_wndToolBar.m_wndScheme_ZDM.ResetContent();
	for(int i=0;i<m_SchemeNames.GetSize();i++)
	{
		m_wndToolBar.m_wndScheme_ZDM.AddString(m_SchemeNames.GetAt(i));
	}
	if(m_wndToolBar.m_wndScheme_ZDM.GetCount()>0)
	{
		if(m_currentSchemeIndexs>=0)
			m_wndToolBar.m_wndScheme_ZDM.SetCurSel(m_currentSchemeIndexs);
 
	}
}


 
void CZdmDesign::InitData()
{
	m_SelectPtNum=0;

	mR=10000;
	
	if(m_currentSchemeIndexs>=0)
	{
		if(myDesingScheme.SchemeDatass[m_currentSchemeIndexs].strDesigngrade!="Ⅲ级铁路")
			mR=10000;
		else		
			mR=5000;

		m_maxSlope=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].maxSlope;
		m_minSlopeLength=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].minSlopeLength;
		m_maxSlopePddsc=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].maxSlopePddsc;
	}
	else
	{
		m_maxSlope=15;
		m_minSlopeLength=400;
		m_maxSlopePddsc=10;
	}

	m_pRecordset.CreateInstance(_uuidof(Recordset));


	for(int i=0;i<7;i++)//纵断面图形标注选项
	{
		m_bBZOption[i]=TRUE;
	}

	
	m_bBZOptionText[0]="连续里程";
	m_ItemHeight[0]=20;
	
	m_bBZOptionText[1]="线路平面";
	m_ItemHeight[1]=30;

	m_bBZOptionText[2]="百米标与加标";
	m_ItemHeight[2]=20;
	
	m_bBZOptionText[3]="地面高程";
	m_ItemHeight[3]=45;
	
	m_bBZOptionText[4]="设计坡度";
	m_ItemHeight[4]=30;

	m_bBZOptionText[5]="路肩设计高程";
	m_ItemHeight[5]=45;
	
	m_bBZOptionText[6]="工程地质特征";
	m_ItemHeight[6]=20;
	

	
	m_GridSpace=20;
	m_drawGrid=TRUE;
	m_GridlineWidth=1;
	m_GridlineStyle=0;
	m_GridlineColor=RGB(220,220,210);

	m_KeduSpace=20;
	m_scaleX=4000;
	m_oldscaleX=m_scaleX;
	m_scaleY=400;
	m_Magnify=m_scaleY/m_scaleX;

	m_lmwidth=85;
	m_totalZDmJD=0;
	
	
	
	LoadDMXGC();//加载地面线数据
	LoadLJGC();//加载路基数据
	LoadZdmDesignData();//加载纵断面设计数据
	LoadTunnelData();//加载隧道数据
	LoadBridgeData();//加载桥梁数据

	//设置设计线直线部分画笔线宽,颜色
	m_penDesignLine_width=2;
	m_penDesignLine_color=RGB(255,0,0);

	//设置设计线曲线部分画笔线宽,颜色
	m_penDesignCurve_width=2;
	m_penDesignCurve_color=RGB(0,0,255);

	//设置设地面线画笔线宽,颜色
	m_penDesignDmx_width=1;
	m_penDesignDmx_color=RGB(0,0,0);
	
	//设置设隧道画笔线宽,颜色
	m_penTunnel_width=3;
	m_penTunnel_color=RGB(220,20,220);
	
	//设置设桥梁画笔线宽,颜色
	m_penBridge_width=2;
	m_penBridge_color=RGB(20,120,180);
	
	//创建各画笔
	m_penDesignDesignLine.CreatePen(PS_SOLID,m_penDesignLine_width,m_penDesignLine_color);
	m_penDesignDesignCurve.CreatePen(PS_SOLID,m_penDesignCurve_width,m_penDesignCurve_color);
	m_penDesignDmx.CreatePen(PS_SOLID,m_penDesignDmx_width,m_penDesignDmx_color);
	m_penTunnel.CreatePen(PS_SOLID,m_penTunnel_width,m_penTunnel_color);
	m_penBridge.CreatePen(PS_SOLID,m_penBridge_width,m_penBridge_color);
			
	GetMin_MaxAltitude();// //得到地面线最大和最小高程值
		
	m_bHaveSetHRScrool=FALSE;
	m_bHaveGetminMAxY=FALSE;

}

 //绘制背景网格
void CZdmDesign::DrawBKgrid(CPaintDC &dc)
{
	CString tt;
	if(b_HaveCreateFont==FALSE) //如果没有创建字体
		this->CreateFonts(); //创建字体
	
	dc.SetBkMode(TRANSPARENT ); //背景设置为透明
	CRect rect;	
	GetClientRect(rect);
	dc.FillSolidRect(rect,RGB(255,255,255));  //用画刷填充
	
	m_H=rect.bottom-20;
	m_W=rect.right;

	int x,y;
	dc.SetTextColor(RGB(0,0,255)); //设置文本颜色
	CFont* oldfont=dc.SelectObject(f); //选择字体f

	long i;
	int m_jianJu=20;
	int M=0;

 
	CPen pen(PS_SOLID,1,RGB(220,20,220)); //创建画笔
	CPen* pOldPen=dc.SelectObject(&pen);//选择画笔
	
	x=0;
	int m_hvalue=0;
	m_H=m_H+20;
	
	//绘制纵断面图形栏目标注选项
	for(i=0;i<7;i++)
	{
		y=m_H-m_hvalue-m_ItemHeight[i];
		if(m_bBZOption[i]==TRUE)
		{
			dc.TextOut(x+5,y+(m_ItemHeight[i]-pLogFont.lfHeight)/2,m_bBZOptionText[i]); 
			M++;
			dc.MoveTo(x,y);
			dc.LineTo(m_W,y);
			m_hvalue+=m_ItemHeight[i];
			m_ItemYvaluet[i]=y;
		}
		else
		{
			if(i>0)
				m_ItemYvaluet[i]=m_ItemYvaluet[i-1];
			else
				m_ItemYvaluet[i]=m_H-m_hvalue;
		}
	}
	
	//计算绘图区域
	if(m_bHaveGetminMAxY==FALSE)
	{
		m_BeiginLeftX=x+m_lmwidth+1;
		m_BeiginLeftY=m_H-m_hvalue;
		m_BeiginRightX=m_W-1;
		m_BeiginRightY=27;
		
		m_drwaReginHeight=fabs(m_BeiginLeftY-m_BeiginRightY);//绘图区域高度
		m_drwaReginWidth=fabs(m_BeiginRightX-m_BeiginLeftX);//绘图区域宽度
		
		minY=int(m_minAltitude)/10*10;
		maxY=(int(m_maxAltitude)/10+1)*10;
		oldminY=minY;
		oldmaxY=maxY;
		
		m_KeduNums=m_drwaReginHeight/m_KeduSpace+1; 
		
		
		m_eachy=m_scaleY/m_KeduSpace;
		
		if(m_eachy%10==0)
			m_eachy=m_eachy;
		else
			m_eachy=((m_eachy/10)+1)*10;
		
		m_eachx=m_scaleX/m_KeduSpace;
		m_eachx=6000.0/m_scaleX*10*m_KeduSpace;
		m_bHaveGetminMAxY=TRUE;
		
	}	
	
	dc.MoveTo(x+m_lmwidth,m_H-m_hvalue);
	dc.LineTo(x+m_lmwidth,m_H-1);
		
	
	CPen pen2(PS_SOLID,2,RGB(20,70,80));
	pOldPen=dc.SelectObject(&pen2);
	dc.MoveTo(x+1,m_H-1);
	dc.LineTo(m_W,m_H-1);
				
	dc.MoveTo(x+1,27);
	dc.LineTo(x+1,m_H-1);

	dc.MoveTo(m_W-1,m_H-1);
	dc.LineTo(m_W-1,27);
	
	dc.MoveTo(m_W-1,27);
	dc.LineTo(x+1,27);

	if(m_drawGrid==TRUE)  //如果绘制背景网格,绘制背景网格线
	{
		CPen pen3(m_GridlineStyle,m_GridlineWidth,m_GridlineColor);
		pOldPen=dc.SelectObject(&pen3);
		int mX=m_BeiginLeftX;
		int mRows=m_drwaReginHeight/m_GridSpace;
		int mCols=m_drwaReginWidth/m_GridSpace;
		int tempx;
		
			for(int j=1;j<=mCols;j++) 
			{
				tempx=m_BeiginLeftX+j*m_GridSpace;
				dc.MoveTo(tempx,m_BeiginLeftY);
				dc.LineTo(tempx,m_BeiginRightY);
			}

			for(i=1;i<=mRows;i++)
			{
				int tempy=m_BeiginLeftY-i*m_GridSpace;
				dc.MoveTo(m_BeiginLeftX,tempy);
				dc.LineTo(m_BeiginRightX,tempy);
			}
	}

	

	CPen pen4(PS_SOLID,1,RGB(0,0,0));
	pOldPen=dc.SelectObject(&pen4);
	
	dc.MoveTo(m_BeiginLeftX,m_BeiginLeftY);
	dc.LineTo(m_BeiginLeftX,m_BeiginRightY);
	
	dc.SetTextColor(RGB(0,0,0));
	dc.SelectObject(f);
	

	int m_KedulineLength=5;
	
	for(i=0;i<m_KeduNums;i++)
	{
		tt.Format("%.3f",i*m_eachy*1.0+minY); 
		int ty=m_BeiginLeftY-i*m_KeduSpace;
		dc.MoveTo(m_BeiginLeftX,ty);
		dc.SetTextAlign(TA_RIGHT|TA_BASELINE);
		dc.TextOut(m_BeiginLeftX-m_KedulineLength-2,ty,tt);
		dc.LineTo(m_BeiginLeftX-m_KedulineLength,ty);
		
	}
	
	//设置水平和垂直滚动条范围
	if(m_bHaveSetHRScrool==FALSE && m_oldZdmEndLC>m_oldZdmstartLC)
	{
		double DY=maxY-minY;
		if(DY<m_eachy*m_KeduNums)
			DY=4*m_eachy*m_KeduNums;
		else
			DY=4*DY;
		

		sfiV.fMask=SIF_ALL;   
		if(DY<=30000)
			sfiV.nMax=DY;  
		else
			sfiV.nMax=30000;   

		sfiV.nMin=0;   
	
		sfiV.nPage=4*m_KeduSpace;
		sfiV.nPos=0; 
		sfiV.nPos=(sfiV.nMax-sfiV.nMin)/2.0; 
		SetScrollInfo(SB_VERT,&sfiV);
		if(DY<m_eachy*m_KeduNums)
			m_sfiVPermeter=m_eachy;
		else
			m_sfiVPermeter=(maxY-minY)*1.0/(sfiV.nMax-sfiV.nMin);
		
		double DX=m_ZdmEndLC;
		sfiH.fMask=SIF_ALL;  
		if(DX<=30000)
			sfiH.nMax=DX;  
		else
			sfiH.nMax=30000;   
		sfiH.nMin=0;  
	
		float ty=DX/(m_drwaReginWidth);
		int nPages;
		if(ty==int(ty))
			nPages=int(ty);
		else
			nPages=int(ty)+1;
		
		sfiH.nPage=sfiH.nMax*1.0/nPages;
	
		sfiH.nPos=0;   
		SetScrollInfo(SB_HORZ,&sfiH);
		m_bHaveSetHRScrool=TRUE;
		m_sfiHPermeter=(m_oldZdmEndLC-m_oldZdmstartLC)*1.0/(sfiH.nMax-sfiH.nMin);
		m_sfiHPermeter=(m_ZdmEndLC-m_oldZdmstartLC)*1.0/(sfiH.nMax-sfiH.nMin);
		
	}

}

//绘图
void CZdmDesign::OnPaint() 
{
 
	
	ZdmDc= new CPaintDC(this);
	DrawZDM(*ZdmDc);

	CDialog::OnPaint(); 

}

 //得到地面线最大和最小高程值
void CZdmDesign::GetMin_MaxAltitude()
{
	m_minAltitude=29999;
	m_maxAltitude=-29999;
	for(long i=0;i<m_dmpoints.GetSize();i++)
	{
		double y=m_dmpoints.GetAt(i).y;
		if(m_maxAltitude<y) m_maxAltitude=y;
		if(m_minAltitude>y) m_minAltitude=y;
		
	}

}

//绘制纵断面图形 
void CZdmDesign::DrawZDM(CPaintDC &dc)
{
 
	DrawBKgrid(dc);
	DrawLXLC(dc);
	DrawBMBJB(dc);
	DrawGEOfeature(dc);
	DrawDMX_DMGC(dc);
	DrawLJGC(dc);	
	DrawDesignSlope(dc);
	DrawPlan(dc);
	DrawPD(dc);
	DrawTunnel(dc);
	DrawBridge(dc);

}

 
void CZdmDesign::DrawDMX_DMGC(CPaintDC &dc)
{
	CString tt;
	double x,y;

	int m_totalPoints=m_dmpoints.GetSize();
	if(m_totalPoints<1) 
		return;
	
	int screenx,screeny;
		
	dc.SetTextColor(RGB(0,0,0));
	
	int b_drawFirst=FALSE;
	for(long i=0;i<m_dmpoints.GetSize();i++)  
	{
		x=m_dmpoints.GetAt(i).x;
		y=m_dmpoints.GetAt(i).y;

		dc.SelectObject(m_penDesignDmx);

		
		x=m_dmpoints.GetAt(i).x;
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginRightX+m_eachx )
			break;
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginLeftX)
		{
			GetScreenXY(x,y,&screenx,&screeny);
			if(b_drawFirst==FALSE)
			{
				dc.MoveTo(screenx,screeny);
				b_drawFirst=TRUE;
			}
			else
				dc.LineTo(screenx,screeny);
			if(	m_bBZOption[3]==TRUE)
			{
				dc.SelectObject(f2);
				tt.Format("%.3f",y); 
				dc.MoveTo(screenx,m_ItemYvaluet[2]);
				dc.SetTextAlign(TA_LEFT);
				if(i%20==0 )
					dc.TextOut(screenx,m_ItemYvaluet[2],tt);
			
				dc.MoveTo(screenx,screeny);
				
			}
		}
	}
	
}



 
void CZdmDesign::DrawLXLC(CPaintDC &dc)
{

	CString tt;
	int Nums=(m_BeiginRightX-m_BeiginLeftX)*1.0/m_eachx;
	
	
	if(	m_bBZOption[0]==FALSE)
		return;

	int a1,a2,mx; 
//	if(m_ZdmStartLC!=(long)m_ZdmStartLC)
	{
		a1=m_ZdmStartLC/1000;
		a2=(m_ZdmStartLC-a1*1000)/100;
		long Dx=((m_ZdmStartLC/100)-(long)(m_ZdmStartLC/100))*m_eachx*0.1;
		mx=a2*(m_eachx*0.1)+Dx;
	}

	
	for(int i=1;i<=Nums;i++)
	{
		if(m_BeiginLeftX+(i-1)*m_eachx<=m_BeiginRightX)
		{
			int hx=m_BeiginLeftX+i*(m_eachx)-mx;
			
			
			tt.Format("k%d",a1+1); 
			a1++;

			dc.MoveTo(hx,m_ItemYvaluet[0]);
			dc.LineTo(hx,m_ItemYvaluet[0]+7);
			dc.SetTextAlign(TA_CENTER);
			dc.TextOut(hx,m_ItemYvaluet[0]+6,tt);
		}
	}	
	
}


//创建字体 
void CZdmDesign::CreateFonts()
{
	b_HaveCreateFont=TRUE;
	f = new CFont; 
	f->CreateFont(14, 
		0, 
		0, 
		0, 
		0, 
		FALSE, 
		FALSE, 
		0, 
		ANSI_CHARSET, 
		OUT_DEFAULT_PRECIS, 
		CLIP_DEFAULT_PRECIS, 
		DEFAULT_QUALITY, 
		DEFAULT_PITCH | FF_SWISS, 
		_T("Arial")); 

	
	f2 = new CFont; 
	f2->CreateFont(12, 
		0, 
		900, 
		900, 
		0, 
		FALSE, 
		FALSE, 
		0, 
		ANSI_CHARSET, 
		OUT_DEFAULT_PRECIS, 
		CLIP_DEFAULT_PRECIS, 
		DEFAULT_QUALITY, 
		DEFAULT_PITCH | FF_SWISS, 
		_T("Arial")); 

		f2->GetLogFont(&pLogFont2);
		f->GetLogFont(&pLogFont);
}


 
void CZdmDesign::DrawBMBJB(CPaintDC &dc)
{
	CString tt;
	int Nums=(m_BeiginRightX-m_BeiginLeftX)*1.0/m_eachx*10+10;
	
	
	if(	m_bBZOption[2]==FALSE)
		return;
	int a1,a2,mx;
//	if(m_ZdmStartLC!=(long)m_ZdmStartLC)
//	{
	 	a1=m_ZdmStartLC/1000;
		a2=(m_ZdmStartLC-a1*1000)/100;
		long Dx=((m_ZdmStartLC/100)-(long)(m_ZdmStartLC/100))*m_eachx*0.1;
		
		for(int j=a2+1;j<=9;j++)
		{
			int hx=m_BeiginLeftX+(j-a2)*(m_eachx/10)-Dx;
			tt.Format("%d",j); 
			dc.MoveTo(hx,m_ItemYvaluet[2]);
			dc.LineTo(hx,m_ItemYvaluet[2]+4);
			dc.SetTextAlign(TA_CENTER);
			dc.TextOut(hx,m_ItemYvaluet[2]+6,tt);
		}
		mx=(10-a2)*(m_eachx/10)-Dx;
//	}
//	else
//	{
//		mx=0;
//		a2=0;
//	}

	
	for(int i=1;i<Nums;i++)
	{
		if(m_BeiginLeftX+(i-1)*m_eachx<=m_BeiginRightX)
		{
			for(int j=1;j<=9;j++)
			{
				int hx=mx+m_BeiginLeftX+(i-1)*m_eachx+j*(m_eachx/10);
				float lc=(hx-m_BeiginLeftX)*(1000.0/m_eachx);
					tt.Format("%d",j); 
					dc.MoveTo(hx,m_ItemYvaluet[2]);
					dc.LineTo(hx,m_ItemYvaluet[2]+4);
					dc.SetTextAlign(TA_CENTER);
					dc.TextOut(hx,m_ItemYvaluet[2]+6,tt);
			}
		}
	}	


}


 
void CZdmDesign::OnZdmGeofeature() 
{
	CZdmGeoSegmentFeatures dlg;
	dlg.DoModal();
}

void CZdmDesign::OnLButtonDown(UINT nFlags, CPoint point) 
{

	int x=point.x;
	int y=point.y;
	float dx=x-m_BeiginLeftX;
	float dy=m_BeiginLeftY-y;
	dx=m_ZdmStartLC+dx*(1000.0/m_eachx); //当前里程
	dy=minY+dy*m_eachy*1.0/m_KeduSpace;
	if(graph_DesignType==GRAPH_BRIDGE || graph_DesignType==GRAPH_TUNNEL )
	{
		m_SelectPtNum++;
		if(m_SelectPtNum==1)
			m_startLC=dx;
		else
		{
			m_SelectPtNum=0;
			m_EndLC=dx;
			CDialogInputData dlg;
			if(graph_DesignType==GRAPH_TUNNEL)
				dlg.m_strTitle="请输入隧道名称";
			else if(graph_DesignType==GRAPH_BRIDGE)
				dlg.m_strTitle="请输入桥梁名称";
			
			if(dlg.DoModal()==IDOK && !dlg.m_name.IsEmpty())
				writeDesignData(graph_DesignType,dlg.m_name,"",m_startLC,m_EndLC);
		}
	}


	CString tt;
	tt.Format("x=%d  y=%d",point.x,point.y);
 
	
	m_wndToolBar.m_wndZDM_LCHTEXT->SetWindowText(tt);



	BOOL bOK=FALSE;

	PZdmSlope zdmSlope; 
	switch(m_drawStyle)
	{
	case 1:

		if(point.x<m_BeiginLeftX || point.y>m_BeiginLeftY)
			return;

		
		zdmSlope=new ZdmSlope;
		if(dx>m_ZdmEndLC)
			dx=m_ZdmEndLC;
		zdmSlope->Lc=dx;
		zdmSlope->R=mR;
		zdmSlope->H=dy;
		zdmSlope->L=dx-ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->Lc;
		zdmSlope->pd=(dy-ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->H)*1.0/zdmSlope->L*1000;
		zdmSlope->T=0;
		zdmSlope->E=0;
		zdmSlope->pddsc=0;
		zdmSlope->curveStyle=0;
	
		int manswer;
		if(zdmSlope->L<m_minSlopeLength)
		{
			tt.Format("坡长[%.3f]小于最小坡长【%.3f】,是否取最小坡长?",zdmSlope->L,m_minSlopeLength);
			manswer=MessageBox(tt,"坡长检查",MB_ICONQUESTION|MB_YESNO);
			if(manswer==7)
				return;
			else
			{
				zdmSlope->L=m_minSlopeLength;
				zdmSlope->Lc=zdmSlope->L+ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->Lc;
				zdmSlope->pd=(dy-ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->H)*1.0/zdmSlope->L*1000;
				
			}
		}
		
		if(fabs(zdmSlope->pd)>m_maxSlope)
		{
			tt.Format("坡度[%.3f]大于最大坡度【%.3f】,是否取最大坡度",zdmSlope->pd,m_maxSlope);
			manswer=MessageBox(tt,"最大坡度检查",MB_ICONQUESTION|MB_YESNO);
			if(manswer==7)
				return;
			else
			{
				if(zdmSlope->pd<0)
					zdmSlope->pd=-m_maxSlope;
				else
					zdmSlope->pd=m_maxSlope;
				//标高
				zdmSlope->H=ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->H+zdmSlope->L*zdmSlope->pd*0.001;
				zdmSlope->pddsc=zdmSlope->pd-ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->pd;
			//	zdmSlope->L=
			}
		}
		
		if(fabs(zdmSlope->pddsc)>m_maxSlopePddsc)
		{
			tt.Format("坡度差[%.3f] 大于最大坡度差%.3f】,是否取最大坡度差",zdmSlope->pddsc,m_maxSlopePddsc);
			manswer=MessageBox(tt,"最大坡度检查",MB_ICONQUESTION|MB_YESNO);
			if(manswer==7)
				return;
			else
			{
				if(zdmSlope->pddsc>0)
					zdmSlope->pd=m_maxSlopePddsc-fabs(ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->pd);
				else
					zdmSlope->pd=-(m_maxSlopePddsc-ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->pd);
				zdmSlope->H=ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->H+zdmSlope->L*zdmSlope->pd*0.001;
			}
			
		}

		//检查最小坡长
	//	bOK=CHeckDataValid(zdmSlope->L,zdmSlope->pd,zdmSlope->pddsc);
	//	if(bOK==FALSE)
	//		return;

		m_totalZDmJD++;

		zdmSlope->ZHLc=zdmSlope->L;
		zdmSlope->ZH_H=zdmSlope->H;
		zdmSlope->HZLc=zdmSlope->L;
		zdmSlope->HZ_H=zdmSlope->H;
		
		if(m_totalZDmJD>1)
		{
			long index=ZDmJDCurveElements.GetSize()-1;
			float pddsc=ZDmJDCurveElements.GetAt(index)->pd-zdmSlope->pd;
			ZDmJDCurveElements.GetAt(index)->pddsc=fabs(pddsc);
		
			//检查坡度代数差和最大坡度
			bOK=CHeckDataValid(ZDmJDCurveElements.GetAt(index)->L,ZDmJDCurveElements.GetAt(index)->pd,pddsc);
			if(bOK==FALSE)
			{
				m_totalZDmJD--;
				return;
			}
			
			if(pddsc>0) 	
				ZDmJDCurveElements.GetAt(index)->curveStyle=1;
			else  if(pddsc<0)
				ZDmJDCurveElements.GetAt(index)->curveStyle=-1;
			else
				ZDmJDCurveElements.GetAt(index)->curveStyle=0;
			
			ZDmJDCurveElements.GetAt(index)->pddsc=fabs(pddsc);
			
			ZDmJDCurveElements.GetAt(index)->T=ZDmJDCurveElements.GetAt(index)->R*ZDmJDCurveElements.GetAt(index)->pddsc/2000;
			ZDmJDCurveElements.GetAt(index)->E=ZDmJDCurveElements.GetAt(index)->T*ZDmJDCurveElements.GetAt(index)->T/(2*ZDmJDCurveElements.GetAt(index)->R);
			
			float h1,h2,h0,h3,L,T;
			T=ZDmJDCurveElements.GetAt(index)->T;
			h1=ZDmJDCurveElements.GetAt(index-1)->H;
			h2=ZDmJDCurveElements.GetAt(index)->H;
			L=ZDmJDCurveElements.GetAt(index)->L;
			h0=h1-(h1-h2)*(L-T)/L;
			
			ZDmJDCurveElements.GetAt(index)->ZH_H=h0;
			ZDmJDCurveElements.GetAt(index)->ZHLc=ZDmJDCurveElements.GetAt(index)->Lc-T;
		
			h3=zdmSlope->H;
			L=zdmSlope->L;
		
			h0=h2-T/L*(h2-h3);
			ZDmJDCurveElements.GetAt(index)->HZLc=ZDmJDCurveElements.GetAt(index)->Lc+T;
			ZDmJDCurveElements.GetAt(index)->HZ_H=h0;
			
		}
		
	
		
		ZDmJDCurveElements.Add(zdmSlope);
		
		SaveDesignPts();
		LoadLJGC();
		this->Invalidate();
		break;

	default:
		break;
	}
	
	CDialog::OnLButtonDown(nFlags, point);
	
}

HBRUSH CZdmDesign::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor) 
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	
	if(nCtlColor   ==   CTLCOLOR_STATIC)   
	{   
		pDC->SetBkMode(   TRANSPARENT   );   
		return   (HBRUSH)::GetStockObject(NULL_BRUSH);   
	}   
	return hbr;
}

void CZdmDesign::OnMouseMove(UINT nFlags, CPoint point) 
{
	
	CString tt;
	
	int x=point.x;
	int y=point.y;
	float dx=x-m_BeiginLeftX;
	float dy=m_BeiginLeftY-y;
	dx=m_ZdmStartLC+dx*(1000.0/m_eachx);
	dy=minY+dy*m_eachy*1.0/m_KeduSpace;
	
	tt.Format("里程=%.3f  高程=%.3f",dx,dy);
	m_wndToolBar.m_wndZDM_LCHTEXT->SetWindowText(tt);
	

	CDialog::OnMouseMove(nFlags, point);
}

void CZdmDesign::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	
	int nCurPos;   
	int nPrevPos;   
	CRect rect;   
    
	GetClientRect(&rect);   
	nPrevPos   =   GetScrollPos(SB_HORZ);   
	nCurPos   =   nPrevPos;   
	switch(nSBCode)   
	{   
	case   SB_LEFT:   
		SetScrollPos(SB_HORZ,   0);   
	
		break;   
	case   SB_RIGHT:   
		SetScrollPos(SB_HORZ,   sfiH.nMax);   
	
		break;   
	case   SB_PAGELEFT:   
		nCurPos   = nPrevPos - sfiH.nPage;   
		if(nCurPos   <   0)   
			nCurPos   =   0;   
		SetScrollPos(SB_HORZ,   nCurPos);   
		break;   
	case   SB_PAGERIGHT:     
		nCurPos   =   nPrevPos   +   sfiH.nPage;   
		if(nCurPos   >   sfiH.nMax)   
			nCurPos   =   sfiH.nMax;   
		SetScrollPos(SB_HORZ,   nCurPos);   
		break;   
	case   SB_THUMBPOSITION:   
		SetScrollPos(SB_HORZ,   nPos);   
		break;   
	case   SB_THUMBTRACK:   
		break;   
	case   SB_LINELEFT:   
		nCurPos   =   nPrevPos   -   sfiH.nPage*0.1; 
	
		if(nCurPos   <   0)   
			nCurPos   =   0;   
		SetScrollPos(SB_HORZ,   nCurPos);   
		break;   
	case   SB_LINERIGHT:   
		nCurPos   =   nPrevPos   +    sfiH.nPage*0.1;   
	
		if(nCurPos   >   sfiH.nMax)   
			nCurPos   =   sfiH.nMax;   
		SetScrollPos(SB_HORZ,   nCurPos);   
		break;   
	case   SB_ENDSCROLL:   
		break;   
	}   
	nCurPos   =   GetScrollPos(SB_HORZ);   
 
	if(nSBCode!=SB_ENDSCROLL)
	{
		float dx=nCurPos;
		if(dx<0) dx=0;
		m_ZdmStartLC=m_oldZdmstartLC+dx*m_sfiHPermeter;
		
		
		this->Invalidate(TRUE);
	}
	
	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}

 
void CZdmDesign::OnMenuZdmExit() 
{
	EndDialog(IDOK);	
}

void CZdmDesign::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	int nCurPos;   
	int nPrevPos;   
	CRect rect;   
    
	this->UpdateData();
	m_wndToolBar.m_wndZDM_YSpinButton->SetBuddy(GetDlgItem(ID_TOOL_ZDM_YSCALE));
	long g=m_wndToolBar.m_wndZDM_YSpinButton->GetPos();
	long g2=m_wndToolBar.m_wndZDM_XSpinButton->GetPos();
	CString tt;
 
	tt.Format("%d",g2);
	m_wndToolBar.m_wndZDM_XScaleTEXT->SetWindowText(tt);
	tt.Format("%ld",g);
	m_wndToolBar.m_wndZDM_YScaleTEXT->SetWindowText(tt);

	nPrevPos   =   GetScrollPos(SB_VERT);   
	nCurPos   =   nPrevPos;   
	switch(nSBCode)   
	{   
	case   SB_TOP:   
		SetScrollPos(SB_VERT,   0);   
		
		break;   
	case   SB_BOTTOM:   
		SetScrollPos(SB_VERT,   sfiV.nMax);   
		
		break;   
	case   SB_PAGEDOWN:   
		nCurPos   = nPrevPos + sfiV.nPage;   
		if(nCurPos   <   0)   
			nCurPos   =   0;   
		SetScrollPos(SB_VERT,   nCurPos);   
		break;   
	case   SB_PAGEUP:     
		nCurPos   =   nPrevPos   -   sfiV.nPage;   
		if(nCurPos   >   sfiV.nMax)   
			nCurPos   =   sfiV.nMax;   
		SetScrollPos(SB_VERT,   nCurPos);   
		break;   
	case   SB_THUMBPOSITION:   
		SetScrollPos(SB_VERT,   nPos);   
		break;   
	case   SB_THUMBTRACK:   
		break;   
	case   SB_LINEDOWN:   
		nCurPos   =   nPrevPos   +   m_KeduSpace;
		if(nCurPos   >   sfiV.nMax)   
			nCurPos   =   sfiV.nMax;   
		
		SetScrollPos(SB_VERT,   nCurPos);   
		break;   
	case   SB_LINEUP:   
		nCurPos   =   nPrevPos   -   m_KeduSpace;
		if(nCurPos   <   0)   
			nCurPos   =   0;   
		SetScrollPos(SB_VERT,   nCurPos);   
		break;   
	case   SB_ENDSCROLL:   
		break;   
	}   
	nCurPos   =   GetScrollPos(SB_VERT);   

	if(nSBCode!=SB_ENDSCROLL)
	{
		float dy=(nCurPos-nPrevPos);
		dy=nCurPos-( sfiV.nMax- sfiV.nMin)/2.0;
	
	
	
		dy=(int)dy/m_KeduSpace*m_KeduSpace;
		minY=oldminY-(dy*1.0/m_KeduSpace)*m_eachy;
		this->Invalidate(TRUE);
	}
	
	CDialog::OnVScroll(nSBCode, nPos, pScrollBar);
}





 
void CZdmDesign::DrawGEOfeature(CPaintDC &dc)
{

	if(m_currentSchemeIndexs<0)
		return;
	
	
	CString tt,m_geoFeature;	
	int x1, int x2;
	dc.SelectObject(&f);
	dc.SetTextAlign(TA_CENTER|TA_BASELINE);

	tt="扼要填写沿线各路段重大不良地质现象、主要地层构造等情况";
	tt.Format("select * from zdmSegmentGeoFeature WHERE 方案名称='%s' \
		ORDER BY 分段起始里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	m_pRecordset->Open(_bstr_t(tt),(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	while(!m_pRecordset->adoEOF)
	{
		
		Thevalue=m_pRecordset->GetCollect("分段起始里程"); 
		double m_SegmentstartLC=(double)Thevalue;
	
		Thevalue=m_pRecordset->GetCollect("分段终点里程"); 
		double m_SegmentendLC=(double)Thevalue;

		Thevalue=m_pRecordset->GetCollect("工程地质特征"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			m_geoFeature=Thevalue.bstrVal;
		}

		
		GetScreenX(m_SegmentstartLC, &x1);
		GetScreenX(m_SegmentendLC, &x2);
		
	
		if(x1>m_BeiginLeftX && x1<m_BeiginRightX ||x2>m_BeiginLeftX && x2<m_BeiginRightX)
		{
			if(x1>=m_BeiginLeftX)
			{
				if((x1+x2)/2.0<=m_BeiginRightX)
					dc.TextOut((x1+x2)/2.0,m_ItemYvaluet[5]-(m_ItemHeight[6]-pLogFont2.lfHeight)/2.0,m_geoFeature);
				else
					dc.TextOut(x1+2,m_ItemYvaluet[5]-(m_ItemHeight[6]-pLogFont2.lfHeight)/2.0,m_geoFeature);
				
			}

		}

		if(x2<m_drwaReginWidth)
		{
			dc.MoveTo(x2,m_ItemYvaluet[6]);
			dc.LineTo(x2,m_ItemYvaluet[5]);
			int L1=x2-x1;
			int nlength=m_geoFeature.GetLength();
		}
		if(x1>m_BeiginRightX)
			break;

		m_pRecordset->MoveNext();
		
	}
	m_pRecordset->Close();
	


	
}

 
void CZdmDesign::GetScreenX(double x, int *screenx)
{
	double dx=x-m_ZdmStartLC;
	float emx=m_BeiginLeftX+dx/(1000.0/m_eachx);
	*screenx=int(emx);
}

 
void CZdmDesign::GetScreenY(double y, int *screeny)
{
	float dy=y-minY;
	float emy=(dy*1.0/m_eachy)*m_KeduSpace;
	emy=m_BeiginLeftY-emy;
	
	*screeny=int(emy);
	if(*screeny>m_BeiginLeftY) 
		*screeny=m_BeiginLeftY;
	
}

 
void CZdmDesign::GetScreenXY(double x, double y, int *screenx, int *screeny)
{
	double dx=x-m_ZdmStartLC;
	float dy=y-minY;
	float emy=(dy*1.0/m_eachy)*m_KeduSpace;
	
	emy=m_BeiginLeftY-emy;
	
	float emx=m_BeiginLeftX+dx/(1000.0/m_eachx);
	
	*screenx=int(emx);
	*screeny=int(emy);
	if(*screeny>m_BeiginLeftY) 
		*screeny=m_BeiginLeftY;
	
}

 
 
void CZdmDesign::LoadDMXGC()
{
	CString strSql;

	if(m_currentSchemeIndexs<0)
		return;

	m_pRecordset.CreateInstance(_uuidof(Recordset));



	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 
	
	Point pt;
	double x,z;
	m_dmpoints.RemoveAll();
	while (!m_pRecordset->adoEOF)
	{
		
		Thevalue = m_pRecordset->GetCollect("里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		pt.x=atof(strSql);
		
		Thevalue = m_pRecordset->GetCollect("x坐标"); 
		x=(double)Thevalue;
		Thevalue = m_pRecordset->GetCollect("z坐标"); 
		z=-(double)Thevalue;
		float y=m_demInsert.GetHeightValue(x+theApp.m_DemLeftDown_x,z+theApp.m_DemLeftDown_y,2);
		strSql.Format("%.3f",y);
		pt.y=atof(strSql);
	
		m_dmpoints.Add(pt);
		
		m_pRecordset->MoveNext();

	}
	m_pRecordset->Close();

	CString tt;
	
	if(m_dmpoints.GetSize()>0)
	{


		tt.Format("SELECT *  FROM  T3DLineZxCorrdinateZDM WHERE 方案名称='%s' ORDER BY 里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
		m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
		if(!m_pRecordset->adoEOF)
		{
			Thevalue = m_pRecordset->GetCollect("里程"); 
			m_ZdmStartLC=(double)Thevalue;
			Thevalue = m_pRecordset->GetCollect("Y坐标"); 
			m_ZdmStartH=(double)Thevalue;
		
	
	
			
			m_pRecordset->MoveLast();
			Thevalue = m_pRecordset->GetCollect("里程"); 
			m_ZdmEndLC=(double)Thevalue;
			
			
			m_pRecordset->Close();
			
		}
		else
		{
			
			m_ZdmStartH=m_dmpoints.GetAt(0).y;
			m_ZdmStartLC=m_dmpoints.GetAt(0).x;
			m_ZdmEndLC=m_dmpoints.GetAt(m_dmpoints.GetSize()-1).x;
			
		}
	
	}
	else
	{
		m_ZdmStartLC=m_ZdmEndLC=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC;		
		m_ZdmStartH=0;
	
	}
	m_oldZdmstartLC=m_ZdmStartLC;
	m_oldZdmEndLC=m_ZdmEndLC;
}

 
void CZdmDesign::OnZdmDesignAddpt() 
{
	m_drawStyle=1;
 
	
}

 
void CZdmDesign::LoadZdmDesignData()
{
	if(m_currentSchemeIndexs<0)
		return;

	CString tt;

	
	m_totalZDmJD=0;

	ZDmDesignPtsElements.RemoveAll();
	tt.Format("SELECT *  FROM  ZDmJDCurve WHERE 方案名称='%s' ORDER BY 变坡点桩号 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	ZDmJDCurveElements.RemoveAll();

	PZdmSlope zdmSlope=new ZdmSlope; 
 
 
		zdmSlope->Lc =m_oldZdmstartLC;
		zdmSlope->H=m_ZdmStartH;
		zdmSlope->E=0;
		zdmSlope->R =0;
		zdmSlope->L=0;	
		zdmSlope->pd=0;
		zdmSlope->T=0; 
		zdmSlope->pddsc=0;
		zdmSlope->ZHLc=zdmSlope->L;
		zdmSlope->ZH_H=zdmSlope->H;
		zdmSlope->HZLc=zdmSlope->L;
		zdmSlope->HZ_H=zdmSlope->H;
		
		ZDmJDCurveElements.Add(zdmSlope);
 
	
 	m_totalZDmJD=0;
	while (!m_pRecordset->adoEOF)
	{
		zdmSlope=new ZdmSlope ;

		Thevalue = m_pRecordset->GetCollect("坡长"); 
		zdmSlope->L=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("坡率"); 
		zdmSlope->pd=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("变坡点桩号"); 
		zdmSlope->Lc=(double)Thevalue;
		
		
		Thevalue = m_pRecordset->GetCollect("标高"); 
		zdmSlope->H=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("竖曲线半径"); 
		zdmSlope->R=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("外矢距"); 
		zdmSlope->E=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("切线长"); 
		zdmSlope->T=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("转向类型"); 
		zdmSlope->curveStyle=(long)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("坡度代数差"); 
		zdmSlope->pddsc=(double)Thevalue;

		Thevalue = m_pRecordset->GetCollect("直缓点里程"); 
		zdmSlope->ZHLc=(double)Thevalue;
	
		Thevalue = m_pRecordset->GetCollect("直缓点标高"); 
		zdmSlope->ZH_H=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("缓直点里程"); 
		zdmSlope->HZLc=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("缓直点标高"); 
		zdmSlope->HZ_H=(double)Thevalue;
					
	
		ZDmJDCurveElements.Add(zdmSlope);
		m_pRecordset->MoveNext();

		m_totalZDmJD++;
	}

	m_pRecordset->Close();
	
	if(m_totalZDmJD>0)
		SaveDesignPts();
	else
	{
		if(m_dmpoints.GetSize()>0)
			m_ZdmEndLC=m_dmpoints.GetAt(m_dmpoints.GetSize()-1).x; //说明没有进行纵断面设计,则初始纵断面终点里程为地面线最大里程
		else
			m_ZdmEndLC=0;//表示没有地面点
	}
	this->Invalidate();

	
}

 
void CZdmDesign::DrawDesignSlope(CPaintDC &dc)
{
	int x,y;
	int x2,y2;

	double tx,ty;
	double tx2,ty2;

	int m_circleR=5;
	CString tt;


	CPen* pOldPen=dc.SelectObject(&m_penDesignDesignLine);
	CPen pent(PS_SOLID,1,RGB(120,20,220));
	
	for(long i=0;i<ZDmJDCurveElements.GetSize();i++)
		{
		
		tx=ZDmJDCurveElements.GetAt(i)->Lc;
		ty=ZDmJDCurveElements.GetAt(i)->H;
		
		GetScreenXY(tx,ty,&x, &y);
	
		if(	i<ZDmJDCurveElements.GetSize()-2)
		{
			 tx2=ZDmJDCurveElements.GetAt(i+1)->Lc;
			 ty2=ZDmJDCurveElements.GetAt(i+1)->H;
			 GetScreenXY(tx2,ty2,&x2, &y2);

		}
		
 
 
 
 
			if(x<m_BeiginLeftX)
			{
				x=m_BeiginLeftX;
				GetYFromLC(m_ZdmStartLC,&y2);
				y=y2;
			}

			if(i==0)
			{
				dc.MoveTo(x,y);
			}
			else
			{
				if(x2<=m_BeiginLeftX && x<=m_BeiginLeftX)
					Sleep(0);
				else
				{
					
					dc.SelectObject(&m_penDesignDesignLine);
					dc.LineTo(x,y);
					dc.SelectObject(&pent);
					dc.Ellipse(x-m_circleR,y+m_circleR,x+m_circleR,y-m_circleR);

				}
				

			}
	}


 
	for(i=0;i<ZDmDesignPtsElements.GetSize();i++)
	{
		tx=ZDmDesignPtsElements.GetAt(i)->Lc;
		ty=ZDmDesignPtsElements.GetAt(i)->H;
		
		GetScreenXY(tx,ty,&x, &y);
		
		if(	i<ZDmDesignPtsElements.GetSize()-2)
		{
			tx2=ZDmDesignPtsElements.GetAt(i+1)->Lc;
			ty2=ZDmDesignPtsElements.GetAt(i+1)->H;
			GetScreenXY(tx2,ty2,&x2, &y2);
			
		}
		if(ZDmDesignPtsElements.GetAt(i)->style=="曲线段坐标点")
			dc.SelectObject(&m_penDesignDesignCurve);
		else
			dc.SelectObject(&m_penDesignDesignLine);
			
		if(x<m_BeiginLeftX)
		{
			x=m_BeiginLeftX;
			GetYFromLC(m_ZdmStartLC,&y2);
			y=y2;
		}
		
		if(i==0)
		{
			dc.MoveTo(x,y);
		}
		else
		{
			if(x2<=m_BeiginLeftX && x<=m_BeiginLeftX)
				Sleep(0);
			else
			{
		
				if(ZDmDesignPtsElements.GetAt(i)->style=="曲线段坐标点")
					dc.LineTo(x,y);
				else
					dc.MoveTo(x,y);	
			}
			
			
		}
		
	}
	
	dc.SelectObject(pOldPen);
		
}


 
void CZdmDesign::OnZdmMenuSave() 
{


	CString tt;


	BeginWaitCursor();

	tt.Format("DELETE FROM ZDmJDCurve WHERE 方案名称='%s'",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
	
 	for(long i=1;i<ZDmJDCurveElements.GetSize();i++)
	{
	
		if(i==0)
			ZDmJDCurveElements.GetAt(i)->curveStyle=0;
		tt.Format("INSERT INTO ZDmJDCurve VALUES(\
			'%s',%.3f,%.3f,%.3f,%.3f,\
			%.3f,%.3f,%.3f,%.3f,%d,%d,%.3f,%.3f,%.3f,%.3f)",\
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
			ZDmJDCurveElements.GetAt(i)->L,
			ZDmJDCurveElements.GetAt(i)->pd,
			ZDmJDCurveElements.GetAt(i)->Lc,
			ZDmJDCurveElements.GetAt(i)->H,
			ZDmJDCurveElements.GetAt(i)->R,
			ZDmJDCurveElements.GetAt(i)->E,
			ZDmJDCurveElements.GetAt(i)->T,
			ZDmJDCurveElements.GetAt(i)->pddsc,
			i+1,								
			ZDmJDCurveElements.GetAt(i)->curveStyle, 
			ZDmJDCurveElements.GetAt(i)->ZHLc,	
			ZDmJDCurveElements.GetAt(i)->ZH_H,	
			ZDmJDCurveElements.GetAt(i)->HZLc,	
			ZDmJDCurveElements.GetAt(i)->HZ_H	
			);
		try
		{
			hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
			if(!SUCCEEDED(hr))
			{
				MessageBox("方案保存失败!","保存方案",MB_ICONINFORMATION);
				return;
			}
		}
		
		catch(_com_error& e)	
		{
			CString errormessage;
			errormessage.Format("错误信息:%s",e.ErrorMessage());
			MessageBox(errormessage,"保存方案",MB_ICONINFORMATION);
			return;
		} 
	}

	myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC=m_oldZdmstartLC;
	myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC=ZDmJDCurveElements.GetAt(ZDmJDCurveElements.GetSize()-1)->Lc;


	double MLC;
	tt.Format("SELECT *  FROM  SCHEME_PLANE_CUREVEDATA WHERE 方案名称='%s' \
		ORDER BY 交点里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	if(!m_pRecordset->adoEOF)
	{
		m_pRecordset->MoveLast();
		Thevalue = m_pRecordset->GetCollect("缓直点里程"); 
		MLC=(double)Thevalue;
	}	
	else
	{	
		long M=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;
		MLC=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(M)->Lc;
	}
	m_pRecordset->Close();
	
		
	if(myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC>MLC) 
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC=MLC;

	
	tt.Format("UPDATE Scheme SET 起点里程=%.3f,终点里程=%.3f\
		WHERE 方案名称='%s'",\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC,\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC,\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		if(!SUCCEEDED(hr))
		{
			MessageBox("方案保存失败!","保存方案",MB_ICONINFORMATION);
			return;
		}
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		MessageBox(errormessage,"保存方案",MB_ICONINFORMATION);
		return;
	} 

		
	SaveDesignPts();	
	
	ModifyPlanDesignData();	
	ModifyPlanDesignData_Tunnel(1);
	ModifyPlanDesignData_Tunnel(2);
	ModifyPlanDesignDataID(TRUE);
//	Insert3DlineZX();
	ReLoadNewDisginData();//

	BeginWaitCursor();

	MessageBox("方案保存成功!","保存方案",MB_ICONINFORMATION);
	
	

}

 
void CZdmDesign::GetYFromLC(double LC, int *y)
{
	double L1,L2;
	int y2;
	float H1,H2;
	for(long i=0;i<ZDmJDCurveElements.GetSize()-1;i++)
	{
		L1=ZDmJDCurveElements.GetAt(i)->Lc;
		H1=ZDmJDCurveElements.GetAt(i)->H;
		L2=ZDmJDCurveElements.GetAt(i+1)->Lc;
		H2=ZDmJDCurveElements.GetAt(i+1)->H;
		if(LC>=L1 && LC<=L2)
		{
			float Derh=(LC-L1)*1.0/(L2-L1)*(H2-H1);
			float y1=Derh*1.0/m_eachy;
			GetScreenY(H1,&y2);
			*y=y2-Derh;
			break;
		}
	}
}	

 
void CZdmDesign::DrawPlan(CPaintDC &dc)
{
	CString tt;
	CString strSql;
	if(m_currentSchemeIndexs<0)
		return;

	
	strSql.Format("SELECT *  FROM  Scheme_plane_CureveData  \
		 WHERE 方案名称='%s' ORDER BY 直缓点里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}

	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	CPen pen(PS_SOLID,1,RGB(20,120,220));
	CPen* pOldPen=dc.SelectObject(&pen);

	CFont* poldfont=dc.SelectObject(f);
	
	double ZHLc,HZLc,HYLc,YHLc;
	CString T,R,E,L,L0;
	int m_oritation;
	float mangle;
	 
	
	int x1,x2,x3,x4,oldx2,oldx3;
	int mDerH=7;
	long Nums=0;
	float  YY=m_ItemYvaluet[0]-m_ItemHeight[1]/2.0;
	while (!m_pRecordset->adoEOF)
	{
		
		Thevalue = m_pRecordset->GetCollect("直缓点里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		ZHLc=atof(strSql);
		
		Thevalue = m_pRecordset->GetCollect("缓圆点里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		HYLc=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("圆缓点里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		YHLc=(double)Thevalue;
	
		Thevalue = m_pRecordset->GetCollect("缓直点里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		HZLc=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("切线长"); 
		T.Format("%.3f",(double)Thevalue);

		Thevalue = m_pRecordset->GetCollect("曲线半径"); 
		R.Format("%.f",(double)Thevalue);
		
		Thevalue = m_pRecordset->GetCollect("转角"); 
		strSql.Format("%.3f",(double)Thevalue);
		mangle=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("外矢距"); 
		E.Format("%.3f",(double)Thevalue);
	
 
 
		
		Thevalue = m_pRecordset->GetCollect("曲线长"); 
		L.Format("%.3f",(double)Thevalue);
		

		Thevalue = m_pRecordset->GetCollect("转向"); 
		m_oritation=(long)Thevalue;
		
		GetScreenX(ZHLc,&x1);
		GetScreenX(HYLc,&x2);
		GetScreenX(YHLc,&x3);
		GetScreenX(HZLc,&x4);
		
		if(ZHLc>=HZLc)
		{
			Sleep(0);
		
		
		}
		if(HYLc>=YHLc)
		{
			Sleep(0);
		
		
		}
		
		oldx2=x2;oldx3=x3;
		
		if(x1<m_BeiginLeftX) x1=m_BeiginLeftX;
		if(x2<m_BeiginLeftX) 
			x2=m_BeiginLeftX;
		
		if(x3<m_BeiginLeftX) 
			x3=m_BeiginLeftX;
		if(x4<m_BeiginLeftX) x4=m_BeiginLeftX;
			
		if(Nums==0)
		{
			dc.MoveTo(x1,YY);
		}
		else
		{	
				if(m_oritation==1)
					dc.SetTextAlign(TA_TOP|TA_CENTER);
				else if(m_oritation==-1)
					dc.SetTextAlign(TA_BOTTOM|TA_CENTER);	
				
				dc.LineTo(x1,YY);
				dc.LineTo(x2,YY-mDerH*m_oritation);
				dc.LineTo(x3,YY-mDerH*m_oritation);
				dc.LineTo(x4,YY);
				tt=mCalF.RadianToDegree(mangle);
				if(oldx2>=m_BeiginLeftX)
				{
					dc.TextOut((x2+x3)/2.0,YY-mDerH*m_oritation,"a="+tt+"   R="+R); 
					dc.TextOut((x2+x3)/2.0,m_ItemYvaluet[0]-(pLogFont.lfHeight-1)*m_oritation,"T="+T+"   L="+L); 
				}
				
		}

		m_pRecordset->MoveNext();
		Nums++;
	}
	m_pRecordset->Close();

	pOldPen=dc.SelectObject(pOldPen);
	poldfont=dc.SelectObject(poldfont);
	
}

 
void CZdmDesign::OnMenuZdmSlopedata() 
{
	CZdmSlopeData dlg;
	dlg.DoModal();

}

 
void CZdmDesign::DrawPD(CPaintDC &dc)
{
	CString  tt;                       
	float L,pd;                      
	double Lc,Lc2;                     
	int x1,x2,y1,y2,ty;
	int oldx1;
	CPen pen(PS_SOLID,1,RGB(20,120,120));
	CPen* pOldPen=dc.SelectObject(&pen);
	CFont* oldfont=dc.SelectObject(f);
	for(long i=1; i<ZDmJDCurveElements.GetSize();i++)
	{
		Lc =ZDmJDCurveElements.GetAt(i-1)->Lc; 
		Lc2 =ZDmJDCurveElements.GetAt(i)->Lc; 

		L=ZDmJDCurveElements.GetAt(i)->L;	
		pd=ZDmJDCurveElements.GetAt(i)->pd;
		
		GetScreenX(Lc,&x1);
		GetScreenX(Lc2,&x2);
		oldx1=x1;
		if(pd>0)//正坡
		{
			y1=m_ItemYvaluet[4]+m_ItemHeight[4];
			y2=m_ItemYvaluet[4];
		}
		else if(pd<0)//负坡
		{
			y2=m_ItemYvaluet[4]+m_ItemHeight[4];
			y1=m_ItemYvaluet[4];
		}
		else if(pd==0)//零坡
		{
			y1=m_ItemYvaluet[4]+m_ItemHeight[4]/2.0;
			y2=y1;
		}
	
		if(x2>m_BeiginLeftX)
		{
			if(x1<m_BeiginLeftX)
			{
				if(fabs(y1-y2)<=0.00001)
					ty=y1;
				else
					ty=y1-(m_BeiginLeftX-x1)*1.0/(x2-x1)*(y1-y2);

				x1=m_BeiginLeftX;
				y1=ty;
			}
			
			dc.MoveTo(x1,y1);
			dc.LineTo(x2,y2);
			dc.MoveTo(x2,m_ItemYvaluet[4]);
			dc.LineTo(x2,m_ItemYvaluet[4]+m_ItemHeight[4]);
			if((oldx1+x2)/2.0>m_BeiginLeftX)
			{
				tt.Format("%.3f",pd);
				dc.SetTextAlign(TA_BOTTOM|TA_CENTER);
				dc.TextOut((x1+x2)/2.0,(y1+y2)/2.0,tt);
				tt.Format("%.3f",L);
				dc.SetTextAlign(TA_TOP|TA_CENTER);
				dc.TextOut((x1+x2)/2.0,(y1+y2)/2.0,tt);
			}
		}
	}
	dc.SelectObject(oldfont);
	dc.SelectObject(pOldPen);
}

void CZdmDesign::OnRButtonDown(UINT nFlags, CPoint point) 
{
	m_drawStyle=-1;
	m_SelectPtNum=0;
	graph_DesignType=-1;
	SetCursor(AfxGetApp()->LoadStandardCursor(IDC_ARROW));
	this->SetWindowText("纵断面设计");
	
	CDialog::OnRButtonDown(nFlags, point);
}

BOOL CZdmDesign::PreTranslateMessage(MSG* pMsg) 
{
 
	CString tt;
	long npos=m_wndToolBar.m_wndZDM_XSpinButton->GetPos();
	if(npos*1000!=m_oldscaleX)
	{
		
		m_scaleX=npos*1000;
		m_oldscaleX=m_scaleX;
		tt.Format("%d",npos*1000);
		m_wndToolBar.m_wndZDM_XScaleTEXT->SetWindowText(tt);
		m_bHaveGetminMAxY=FALSE;
		this->Invalidate();
	}

	return CDialog::PreTranslateMessage(pMsg);
}

BOOL CZdmDesign::OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message) 
{

   	if(graph_DesignType==GRAPH_BRIDGE)
	{
		SetCursor(AfxGetApp()->LoadStandardCursor(IDC_UPARROW));
		return TRUE;
	}
	else if(graph_DesignType==GRAPH_TUNNEL)
	{
		SetCursor(AfxGetApp()->LoadStandardCursor(IDC_IBEAM));
		return TRUE;
	}

	else
	{
		SetCursor(AfxGetApp()->LoadStandardCursor(IDC_ARROW));
		return TRUE;
	}
	
	
	return CDialog::OnSetCursor(pWnd, nHitTest, message);
}


 
float CZdmDesign::GetDmxGC(double lc)
{
	double mlc,mlc2,h1,h2,h;
	BOOL m_Bfind=FALSE;

	for(long i=0;i<m_dmpoints.GetSize()-1;i++)
	{
		mlc=m_dmpoints.GetAt(i).x;
		h1=m_dmpoints.GetAt(i).y;
		
		mlc2=m_dmpoints.GetAt(i+1).x;
		h2=m_dmpoints.GetAt(i+1).y;
		
		if(lc>=mlc && lc<=mlc2)
		{
			if(fabs(h2-h1)<=0.00001)
				h=h1;
			else
				h=h1+(lc-mlc)/(mlc2-mlc)*(h2-h1);
			m_Bfind=TRUE;
		}
		
	}
	
	if(m_Bfind==TRUE)
		return h;
	else
		return -9999;
}

 
float CZdmDesign::GetSjxGC(double lc)
{
	double mlc,mlc2,h1,h2,h;
	
	for(long i=0;i<ZDmDesignPtsElements.GetSize()-1;i++)
	{
		mlc=ZDmDesignPtsElements.GetAt(i)->Lc;
		h1=ZDmDesignPtsElements.GetAt(i)->H;
		
		mlc2=ZDmDesignPtsElements.GetAt(i+1)->Lc;
		h2=ZDmDesignPtsElements.GetAt(i+1)->H;
		
		if(lc>=mlc && lc<=mlc2)
		{
			if(fabs(h2-h1)<=0.00001)
				h=h1;
			else
				h=h1+(lc-mlc)/(mlc2-mlc)*(h2-h1);
		}
		
	}
	
	return h;
}

 
void CZdmDesign::SaveDesignPts()
{

	PZdmDesignPts pt;
	ZDmDesignPtsElements.RemoveAll();
 
	for(long i=0;i<ZDmJDCurveElements.GetSize();i++)
	{
	
		pt=new ZdmDesignPts;

		if(i==0 || i==ZDmJDCurveElements.GetSize()-1)//起点
		{
			pt->Lc=ZDmJDCurveElements.GetAt(i)->Lc;
			pt->H=ZDmJDCurveElements.GetAt(i)->H;
			
			
			pt->Dm_H=GetDmxGC(pt->Lc);
			pt->TW=	pt->H-pt->Dm_H;
			pt->style="起终点坐标";
			pt->JDLC=ZDmJDCurveElements.GetAt(i)->Lc;
			ZDmDesignPtsElements.Add(pt);
		}
		else  
		{
			GetCurveJiaMiD(ZDmJDCurveElements.GetAt(i)->ZHLc,ZDmJDCurveElements.GetAt(i)->ZH_H,\
				ZDmJDCurveElements.GetAt(i)->HZLc,ZDmJDCurveElements.GetAt(i)->HZ_H,\
				mR,ZDmJDCurveElements.GetAt(i)->curveStyle,ZDmJDCurveElements.GetAt(i)->Lc);
		}
	}
	

	CString tt;
	
 
	tt.Format("DELETE FROM T3DLineZxCorrdinateZDM WHERE 方案名称='%s'",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	
	hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
	
	double PreLc,CurenLC;
	long index=0;
	for(i=0;i<ZDmDesignPtsElements.GetSize();i++)
	{
		CurenLC=ZDmDesignPtsElements.GetAt(i)->Lc;
		if(fabs(PreLc-CurenLC)>=0.1)		
		{	
			index++;
			tt.Format("INSERT INTO T3DLineZxCorrdinateZDM VALUES(\
				   '%s',%d,%.3f,%.3f,%.3f,'%s',\
				   %.3f,%.3f,%.3f,%.3f)",\
				   myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
				   index,							
				   ZDmDesignPtsElements.GetAt(i)->Lc,
				   ZDmDesignPtsElements.GetAt(i)->H,
				   ZDmDesignPtsElements.GetAt(i)->H,
				   ZDmDesignPtsElements.GetAt(i)->style,
				   ZDmDesignPtsElements.GetAt(i)->Dm_H,
				   ZDmDesignPtsElements.GetAt(i)->TW,
				   ZDmDesignPtsElements.GetAt(i)->Lc,
				   ZDmDesignPtsElements.GetAt(i)->JDLC
				   
				   );
			try
			{
				hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
				if(!SUCCEEDED(hr))
				{
					MessageBox("方案保存失败!","保存方案",MB_ICONINFORMATION);
					return;
				}
			}
			
			catch(_com_error& e)	
			{
				CString errormessage;
				errormessage.Format("错误信息:%s",e.ErrorMessage());
				MessageBox(errormessage,"保存方案",MB_ICONINFORMATION);
				return;
			} 
		}
		else
		{
			Sleep(0);
		}
			PreLc=CurenLC;
	}
	

}


void CZdmDesign::GetCurveJiaMiD(double x1, float y1, double x2, float y2,  double R, int CurveStyle,double jdLC)
{

	float k1,k2,mangle;
	float m_startAngle,m_endAngle; 
	double ux,uy,centerx,centery;
	
	float PAI=3.1415926;
	
	if(CurveStyle==-1) 
		R=-R;

	GetCenteXY(x1,y1,x2,y2,R,&centerx,&centery);

	PZdmDesignPts pt;
	pt=new ZdmDesignPts;
		
	pt->Lc=x1;
	pt->H=y1;
	
	
	pt->Dm_H=GetDmxGC(pt->Lc);
	pt->TW=	pt->H-pt->Dm_H;
	pt->style="直缓点坐标点";
	pt->JDLC=jdLC;

	ZDmDesignPtsElements.Add(pt);
		
	k1=(centery-y1)/(centerx-x1);
	k2=(centery-y2)/(centerx-x2);

	float mang1=atan(fabs(k1))*180.0/PAI;
	float mang2=atan(fabs(k2))*180.0/PAI;
	

	if(CurveStyle==1)
	{
		if(k1>=0) 
		{
			m_startAngle=mang1;
			if(k2>=0)
				m_endAngle=mang2;
			else
				m_endAngle=-mang2;
		}
		else 
		{
			m_startAngle=180-mang1;
			if(k2<0)
				m_endAngle=180-mang2;
			else
				m_endAngle=mang2;
			
		}
 
 
	}
	else if(CurveStyle==-1)
	{
		if(k1>=0) 
		{
			m_startAngle=180+mang1;
			if(k2>=0)
				m_endAngle=180+mang2;
			else
				m_endAngle=360-mang2;
		}
		else 
		{
			m_startAngle=360-mang1;
			if(k2<0)
				m_endAngle=360-mang2;
			else
				m_endAngle=360+mang2;
			
		}
	}
	
	if(CurveStyle==-1) 
	{
		mangle=m_startAngle;
		while(mangle<=m_endAngle)
		{
			pt=new ZdmDesignPts;

			ux=centerx+fabs(R)*cos(mangle*PAI/180.0);
			uy=centery+fabs(R)*sin(mangle*PAI/180.0);
	
			pt->Lc=ux;
			pt->H=uy;
			
			
			pt->Dm_H=GetDmxGC(pt->Lc);
			pt->TW=	pt->H-pt->Dm_H;
			pt->style="曲线段坐标点";
			pt->JDLC=jdLC;
			if(pt->Dm_H!=-9999)
				ZDmDesignPtsElements.Add(pt);
			mangle+=0.1;
		}
		
	}
	else if(CurveStyle==1) 
	{
		mangle=m_startAngle; 
		while(mangle>=m_endAngle)
		{
			pt=new ZdmDesignPts;
			ux=centerx+fabs(R)*cos(mangle*PAI/180.0);
			uy=centery+fabs(R)*sin(mangle*PAI/180.0);
			pt->Lc=ux;
			pt->H=uy;
			
			
			pt->Dm_H=GetDmxGC(pt->Lc);
			pt->TW=	pt->H-pt->Dm_H;
			pt->style="曲线段坐标点";
			pt->JDLC=jdLC;
			if(pt->Dm_H!=-9999)
				ZDmDesignPtsElements.Add(pt);
			mangle-=0.1;
		}
	}
	
	pt->Lc=x2;
	pt->H=y2;
	
	
	pt->Dm_H=GetDmxGC(pt->Lc);
	pt->TW=	pt->H-pt->Dm_H;
	pt->style="缓直点坐标点";
	pt->JDLC=jdLC;
	if(pt->Dm_H!=-9999)
		ZDmDesignPtsElements.Add(pt);
	
}

 
void CZdmDesign::GetCenteXY(double x1, double y1, double x2, double y2, double R, double *centerx, double *centery)
{
	double x, y;
	double a,b,c,d,h;
	
	a = (x2 - x1)/2; b = (y2 - y1)/2; 
	c = b/sqrt(a*a + b*b); d = -a/sqrt(a*a + b*b);    
	h = R * R -a*a - b*b;    
	if(h<0)    
	{    
		x = x1 + a; y = y1 + b; 
		R = sqrt(a*a + b*b);
	}    
	else    
	{    
		h = sqrt(h); c = h * c; d = h * d;    
		if(R > 0)    
		{    
			x = x1 + c + a; y = y1 + d + b;    
		}    
		else if(R < 0)
		{    
			c = -c; d = -d;    
			x = x1 + c + a; y = y1 + d + b;    
		}    
		else
		{    
			x = x1 + a; y = y1 + b;    
			R = sqrt(a*a + b*b);
		}    
	}
	
	*centerx = x; *centery=y;
}

 


void CZdmDesign::OnMenuZdmJdlppcpd() 
{
	CZdmJtLpPcPD dlg;
	dlg.m_ZdmStartH=m_ZdmStartH;
	dlg.m_ZdmStartLC=m_oldZdmstartLC;
	
	dlg.m_maxSlope=m_maxSlope;
	dlg.m_minSlopeLength=m_minSlopeLength;
	dlg.m_maxSlopePddsc=m_maxSlopePddsc;

	if(dlg.DoModal()==IDOK && dlg.m_bEditData==TRUE)
	{

		LoadZdmDesignData();
		
	
		
		this->Invalidate();
	}
	
	
}

 
void CZdmDesign::OnZdmStartlcH() 
{
	CZdmStartLC_H dlg;
	
	dlg.m_ZdmStartH=m_ZdmStartH;
	dlg.m_ZdmStartLC=m_oldZdmstartLC;
	if(dlg.DoModal()==IDOK)
	{
		m_ZdmStartH=dlg.m_ZdmStartH;
	
		m_oldZdmstartLC=dlg.m_ZdmStartLC;
	

		ZDmJDCurveElements.GetAt(0)->Lc=m_oldZdmstartLC;
		ZDmJDCurveElements.GetAt(0)->H=m_ZdmStartH; 
		this->Invalidate();
		
	}
}

 
void CZdmDesign::DrawLJGC(CPaintDC &dc)
{
	CString tt;
	double x,y;
	
	int m_totalPoints=m_Ljpoints.GetSize();
	if(m_totalPoints<1) 
		return;
	
	
	int screenx,screeny;
	
	dc.SetTextColor(RGB(0,0,0));
	
	int b_drawFirst=FALSE;
	for(long i=0;i<m_Ljpoints.GetSize();i++)  
	{
		x=m_Ljpoints.GetAt(i).x;
		y=m_Ljpoints.GetAt(i).y;
		
		dc.SelectObject(m_penDesignDmx);
		
		
		x=m_Ljpoints.GetAt(i).x;
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginRightX+m_eachx )
			break;
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginLeftX)
		{
			GetScreenXY(x,y,&screenx,&screeny);
	
			if(	m_bBZOption[5]==TRUE)
			{
				tt.Format("%.3f",y); 
				dc.SetTextAlign(TA_LEFT);
				dc.TextOut(screenx,m_ItemYvaluet[4],tt);
				
			}
		}
	}
}

 
 
void CZdmDesign::LoadLJGC()
{
	CString strSql;
	
	if(m_currentSchemeIndexs<0)
		return;
	
	m_pRecordset.CreateInstance(_uuidof(Recordset));
	
	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinateZDM WHERE 方案名称='%s' ORDER BY 里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 
	
	Point pt;
 
	CString style;

	int m_curvePtsNums=0;
	float LcCh=0;
	m_Ljpoints.RemoveAll();
	while (!m_pRecordset->adoEOF)
	{
		
		Thevalue = m_pRecordset->GetCollect("里程"); 
		strSql.Format("%.3f",(double)Thevalue);
		pt.x=atof(strSql);
		
		Thevalue = m_pRecordset->GetCollect("y坐标"); 
		pt.y=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("坐标类型"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			style=Thevalue.bstrVal;
		}

		if(m_Ljpoints.GetSize()>0)
		{
			double L1=m_Ljpoints.GetAt(m_Ljpoints.GetSize()-1).x;
			double L2=pt.x;
			float h1=m_Ljpoints.GetAt(m_Ljpoints.GetSize()-1).y;
			double h2=pt.y;
		

			double Ltm=L1;
			if(L2-L1>200)
			{
				while(Ltm<L2-100)
				{
					Ltm+=100;
					float hh=h1+Ltm/(L2-L1)*(h2-h1);
					pt.x=Ltm;
					pt.y=hh;	
					m_Ljpoints.Add(pt);
				}
			}
		}

		if(style!="曲线段坐标点")
			m_Ljpoints.Add(pt);
		else
		{
			m_curvePtsNums++;
			LcCh+=pt.x-m_Ljpoints.GetAt(m_Ljpoints.GetSize()-1).x;
			if(LcCh>=200)
			{
				m_Ljpoints.Add(pt);
				LcCh=0;
			}
		}
		m_pRecordset->MoveNext();
		
	}
	m_pRecordset->Close();
	
}

 
BOOL CZdmDesign::CHeckDataValid(double SlopeL, float SlopePd, float SlopePddsc)
{
	CString tt;
	if(SlopeL<m_minSlopeLength)
	{
		tt.Format("坡长[%.3f]小于最小坡长【%.3f】",SlopeL,m_minSlopeLength);
		MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
		return FALSE;
	}
	if(fabs(SlopePd)>m_maxSlope)
	{
		tt.Format("坡度[%.3f]大于最大坡度【%.3f】",SlopePd,m_maxSlope);
		MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
		return FALSE;
	}
	if(SlopePddsc>m_maxSlopePddsc)
	{
		tt.Format("坡度差[%.3f]大于最大坡度差【%.3f】",SlopePddsc,m_maxSlopePddsc);
		MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
		return FALSE;
	}
	return TRUE;
	
}

void CZdmDesign::OnBtnSPin()
{
	int m=0;
}

 


void CZdmDesign::OnZdmTunnel() 
{
	CTunnelData dlg;
	if(dlg.DoModal()==IDOK && dlg.m_bChageData==TRUE)
	{
		LoadTunnelData();
		this->Invalidate();
	}

	
}

void CZdmDesign::OnZdmBridge() 
{
	CBridgeData dlg;
	if(dlg.DoModal()==IDOK && dlg.m_bChageData==TRUE)
	{
		LoadBridgeData();
		this->Invalidate();
	}
}


 
void CZdmDesign::DrawTunnel(CPaintDC &dc)
{
	CString tt;
	int x1,x2,x3,x4;
	int y1,y2,y3,y4;

	double x;

	int m_totalPoints=m_Tunnelpoints.GetSize();
	if(m_totalPoints<1) 
		return;
	
	
	dc.SelectObject(f);
	dc.SetTextColor(RGB(0,0,0));
 
	CPen* pOldPen=dc.SelectObject(&m_penTunnel);
	


	for(long i=0;i<m_Tunnelpoints.GetSize();i++)  
	{

		GetScreenX(m_Tunnelpoints.GetAt(i)->startLC,&x1);
		GetScreenY(m_Tunnelpoints.GetAt(i)->startH_Sjx,&y1);	
		
		x3=x1;
		GetScreenY(m_Tunnelpoints.GetAt(i)->startH_Dmx,&y3);	
		
		GetScreenX(m_Tunnelpoints.GetAt(i)->endLC,&x2);
		GetScreenY(m_Tunnelpoints.GetAt(i)->endH_Sjx,&y2);	
		
		x4=x2;
		GetScreenY(m_Tunnelpoints.GetAt(i)->endH_Dmx,&y4);	
		
		
		x=m_Tunnelpoints.GetAt(i)->startLC;
	
		//超出图形右边界不绘出
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginRightX+m_eachx )
			break;
		
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginLeftX)
		{

			dc.MoveTo(x1,y1);
			dc.LineTo(x3,y3);
			
			dc.MoveTo(x2,y2);
			dc.LineTo(x4,y4);
			
			dc.MoveTo(x1,(y1+y3)/2.0);
			dc.LineTo(x2,(y2+y4)/2.0);
			
			tt.Format("隧道长度=%.3f",m_Tunnelpoints.GetAt(i)->length); 
			
			dc.SetTextAlign(TA_CENTER|TA_BOTTOM); 
			dc.TextOut((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2-1,tt);
			
		}
		else
		{	
			
			if(m_Tunnelpoints.GetAt(i)->endLC>m_ZdmStartLC)
			{			
				float dx=m_ZdmStartLC-x;
				GetScreenY(m_Tunnelpoints.GetAt(i)->startH_Sjx,&y1);	
				x1=m_BeiginLeftX;
				x3=x1;
				GetScreenY(m_Tunnelpoints.GetAt(i)->startH_Dmx,&y3);	
				
				GetScreenX(m_Tunnelpoints.GetAt(i)->endLC,&x2);
				GetScreenY(m_Tunnelpoints.GetAt(i)->endH_Sjx,&y2);	
				
				x4=x2;
				GetScreenY(m_Tunnelpoints.GetAt(i)->endH_Dmx,&y4);	
				
				
				y1=y1+(y2-y1)*(dx/(m_Tunnelpoints.GetAt(i)->endLC-m_Tunnelpoints.GetAt(i)->startLC));
				y3=y3+(y4-y3)*(dx/(m_Tunnelpoints.GetAt(i)->endLC-m_Tunnelpoints.GetAt(i)->startLC));
				
				
				dc.MoveTo(x1,(y1+y3)/2.0);
				dc.LineTo(x2,(y2+y4)/2.0);
				
				if((m_Tunnelpoints.GetAt(i)->endLC-m_ZdmStartLC)*(m_eachx/300.0)>=340)
				{
					tt.Format("隧道长度=%.3f",m_Tunnelpoints.GetAt(i)->length); 
					dc.SetTextAlign(TA_CENTER|TA_BOTTOM); 
					dc.TextOut((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2-1,tt);
				}
				
			}
			
		}

	}
	
	pOldPen=dc.SelectObject(pOldPen);
				
}



void CZdmDesign::LoadTunnelData()
{

	if(m_currentSchemeIndexs<0)
		return;
	
	CString tt;
	
	
	tt.Format("SELECT *  FROM  Tunnel WHERE 方案名称='%s' ORDER BY  隧道起点里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	
	m_Tunnelpoints.RemoveAll();

	PTunnelBridge pt;

	while (!m_pRecordset->adoEOF)
	{
		pt=new TunnelBridge;

		Thevalue = m_pRecordset->GetCollect("隧道名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			pt->name=Thevalue.bstrVal;
		}
		
		
		Thevalue = m_pRecordset->GetCollect("隧道起点里程"); 
		pt->startLC=(double)Thevalue;
		
		pt->startH_Sjx=GetSjxGC(pt->startLC);
		pt->startH_Dmx=GetDmxGC(pt->startLC);
		

		Thevalue = m_pRecordset->GetCollect("隧道终点里程"); 
		pt->endLC=(double)Thevalue;
		
		pt->endH_Sjx=GetSjxGC(pt->endLC);
		pt->endH_Dmx=GetDmxGC(pt->endLC);

		
		Thevalue = m_pRecordset->GetCollect("隧道长度"); 
		pt->length=(double)Thevalue;
		
		pt->centerLC=(pt->startLC+pt->endLC)/2;
		pt->centerH=GetSjxGC(pt->centerLC);
		

		m_Tunnelpoints.Add(pt);
		
		m_pRecordset->MoveNext();
	}
	
	m_pRecordset->Close();

	
}


 
void CZdmDesign::DrawBridge(CPaintDC &dc)
{
	CString tt;
	int x1,x2,x3,x4;
	int y1,y2,y3,y4;

	double x;

	int m_totalPoints=m_Bridgepoints.GetSize();
	if(m_totalPoints<1) 
		return;
	
	
 
	dc.SetTextColor(RGB(0,0,0));

	CPen* pOldPen=dc.SelectObject(&m_penBridge);
	


	for(long i=0;i<m_Bridgepoints.GetSize();i++)  
	{

		GetScreenX(m_Bridgepoints.GetAt(i)->startLC,&x1);
		GetScreenY(m_Bridgepoints.GetAt(i)->startH_Sjx,&y1);	
		
		x3=x1;
		GetScreenY(m_Bridgepoints.GetAt(i)->startH_Dmx,&y3);	
		
		GetScreenX(m_Bridgepoints.GetAt(i)->endLC,&x2);
		GetScreenY(m_Bridgepoints.GetAt(i)->endH_Sjx,&y2);	
		
		x4=x2;
		GetScreenY(m_Bridgepoints.GetAt(i)->endH_Dmx,&y4);	
		
		
		x=m_Bridgepoints.GetAt(i)->startLC;
		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginRightX+m_eachx )
			break;

		if(m_BeiginLeftX+(x-m_ZdmStartLC)*0.001*m_eachx>=m_BeiginLeftX)
		{
			dc.SelectObject(f);
			dc.MoveTo(x1,y1);
			dc.LineTo(x3,y3);
			
			dc.MoveTo(x2,y2);
			dc.LineTo(x4,y4);
			
			dc.MoveTo(x1,(y1+y3)/2.0);
			dc.LineTo(x2,(y2+y4)/2.0);
			
			tt.Format("桥梁长度=%.3f",m_Bridgepoints.GetAt(i)->length); 
			
			dc.SetTextAlign(TA_CENTER|TA_TOP); 
			dc.TextOut((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2+1,tt);
			dc.MoveTo((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2);
			dc.LineTo((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2-70);
			
			dc.SelectObject(f2);
			dc.SetTextAlign(TA_LEFT|TA_BOTTOM); 
			dc.TextOut((x1+x2)/2-2,((y1+y3)/2.0+(y2+y4)/2)/2-1,m_Bridgepoints.GetAt(i)->name);
			dc.SetTextAlign(TA_LEFT|TA_BOTTOM); 
			tt.Format("%.3f",m_Bridgepoints.GetAt(i)->startLC);
			dc.TextOut((x1+x2)/2+15,((y1+y3)/2.0+(y2+y4)/2)/2-1,tt);
		}
		else
		{	
			
			if(m_Bridgepoints.GetAt(i)->endLC>m_ZdmStartLC)
			{			
				float dx=m_ZdmStartLC-x;
				GetScreenY(m_Bridgepoints.GetAt(i)->startH_Sjx,&y1);	
				x1=m_BeiginLeftX;
				x3=x1;
				GetScreenY(m_Bridgepoints.GetAt(i)->startH_Dmx,&y3);	
				
				GetScreenX(m_Bridgepoints.GetAt(i)->endLC,&x2);
				GetScreenY(m_Bridgepoints.GetAt(i)->endH_Sjx,&y2);	
				
				x4=x2;
				GetScreenY(m_Bridgepoints.GetAt(i)->endH_Dmx,&y4);	
				
				
				y1=y1+(y2-y1)*(dx/(m_Bridgepoints.GetAt(i)->endLC-m_Bridgepoints.GetAt(i)->startLC));
				y3=y3+(y4-y3)*(dx/(m_Bridgepoints.GetAt(i)->endLC-m_Bridgepoints.GetAt(i)->startLC));
				
				
				dc.MoveTo(x1,(y1+y3)/2.0);
				dc.LineTo(x2,(y2+y4)/2.0);
				
				if((m_Bridgepoints.GetAt(i)->endLC-m_ZdmStartLC)*(m_eachx/300.0)>=340)
				{
					tt.Format("桥梁长度=%.3f",m_Bridgepoints.GetAt(i)->length); 
					
					dc.SetTextAlign(TA_CENTER|TA_TOP); 
					dc.TextOut((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2+1,tt);
					dc.MoveTo((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2);
					dc.LineTo((x1+x2)/2,((y1+y3)/2.0+(y2+y4)/2)/2-70);
					
					dc.SelectObject(f2);
					dc.SetTextAlign(TA_LEFT|TA_BOTTOM); 
					dc.TextOut((x1+x2)/2-2,((y1+y3)/2.0+(y2+y4)/2)/2-1,m_Bridgepoints.GetAt(i)->name);
					dc.SetTextAlign(TA_LEFT|TA_BOTTOM); 
					tt.Format("%.3f",m_Bridgepoints.GetAt(i)->startLC);
					dc.TextOut((x1+x2)/2+15,((y1+y3)/2.0+(y2+y4)/2)/2-1,tt);
				}
				
			}
			
		}
		
	}
	
	pOldPen=dc.SelectObject(pOldPen);
				
}


 
void CZdmDesign::LoadBridgeData()
{

	if(m_currentSchemeIndexs<0)
		return;
	
	CString tt;
	
	
	tt.Format("SELECT *  FROM  Bridge WHERE 方案名称='%s' ORDER BY  桥梁起点里程 ASC",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	
	m_Bridgepoints.RemoveAll();

	PTunnelBridge pt;

	while (!m_pRecordset->adoEOF)
	{
		pt=new TunnelBridge;

		Thevalue = m_pRecordset->GetCollect("桥梁名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			pt->name=Thevalue.bstrVal;
		}
		
		
		Thevalue = m_pRecordset->GetCollect("桥梁起点里程"); 
		pt->startLC=(double)Thevalue;
		
		pt->startH_Sjx=GetSjxGC(pt->startLC);
		pt->startH_Dmx=GetDmxGC(pt->startLC);
		

		Thevalue = m_pRecordset->GetCollect("桥梁终点里程"); 
		pt->endLC=(double)Thevalue;
		
		pt->endH_Sjx=GetSjxGC(pt->endLC);
		pt->endH_Dmx=GetDmxGC(pt->endLC);

		
		Thevalue = m_pRecordset->GetCollect("桥梁长度"); 
		pt->length=(double)Thevalue;
		
		pt->centerLC=(pt->startLC+pt->endLC)/2;
		pt->centerH=GetSjxGC(pt->centerLC);
		

		m_Bridgepoints.Add(pt);
		
		m_pRecordset->MoveNext();
	}
	
	m_pRecordset->Close();

	
}


 
void CZdmDesign::ModifyPlanDesignData()
{
	CString strSql,strSql2,tt;
	_RecordsetPtr m_pRecordsetPm,m_pRecordsetZdm; 
	m_pRecordsetPm.CreateInstance(_uuidof(Recordset));
	m_pRecordsetZdm.CreateInstance(_uuidof(Recordset));
	
	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 序号,里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordsetPm->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		m_pRecordsetPm->Close();
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	
	strSql2.Format("SELECT *  FROM  T3DLineZxCorrdinateZDM WHERE 方案名称='%s' ORDER BY 里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordsetZdm->Open((_bstr_t)strSql2,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		m_pRecordsetZdm->Close();
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	double Lc1,H1,Lc2,H2,L,H,dmh;
	while(!m_pRecordsetZdm->adoEOF)
	{

		
		Thevalue=m_pRecordsetZdm->GetCollect("里程"); 
		Lc1=(double)Thevalue;
		
		Thevalue=m_pRecordsetZdm->GetCollect("Y坐标"); 
		H1=(double)Thevalue;

		Thevalue=m_pRecordsetZdm->GetCollect("地面高程"); 
		dmh=(double)Thevalue;
		
		if(!m_pRecordsetZdm->adoEOF)
		{
			m_pRecordsetZdm->MoveNext();
			if(m_pRecordsetZdm->adoEOF)
			{
			
				if(!m_pRecordsetPm->adoEOF)
				{
					double x1,z1,PL1,x2,z2,PL2,x,z;
					CString strJdStyle1,strJdStyle2,strJdStyle;
					long pmID1, pmID;
					 
					m_pRecordsetPm->MovePrevious();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					pmID1=(long)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
					if(Thevalue.vt!=VT_NULL) 
					{
						strJdStyle1=Thevalue.bstrVal;
						
					}
					
					m_pRecordsetPm->MoveNext();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
					if(Thevalue.vt!=VT_NULL) 
					{
						strJdStyle2=Thevalue.bstrVal;
						
					}
				
					if((PL2-PL1)>0 && (Lc1-PL1)>0)
					{		
						x=x1+(Lc1-PL1)/(PL2-PL1)*(x2-x1);
						z=z1+(Lc1-PL1)/(PL2-PL1)*(z2-z1);
						pmID=pmID1+1;
						if((Lc1-PL1)<(PL2-Lc1)) 
							strJdStyle=strJdStyle1;
						else
							strJdStyle=strJdStyle2;
						

					   tt.Format("INSERT INTO T3DLineZxCorrdinate VALUES(\
						   '%s',%ld,%.3f,%.3f,%.3f,'%s',%.3f,%.3f,%.3f)",\
						   myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
						   pmID,
						   x,
						   H1,
						   z,
						   strJdStyle,
						   dmh,
						   H1-dmh,
						   Lc1
						   );
					   try
					   {
						   hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
						   if(!SUCCEEDED(hr))
						   {
							   AfxMessageBox("保存失败!",MB_ICONINFORMATION);
							   return;
						   }
					   }
					   catch(_com_error& e)	
					   {
						   CString errormessage;
						   errormessage.Format("错误信息:%s",e.ErrorMessage());
						   AfxMessageBox(errormessage,MB_ICONINFORMATION);
						   return;
					   } 

					}
				
				}
				break;
			}
			
			Thevalue=m_pRecordsetZdm->GetCollect("里程"); 
			Lc2=(double)Thevalue;
			
			Thevalue=m_pRecordsetZdm->GetCollect("Y坐标"); 
			H2=(double)Thevalue;
		}

		
		
		Thevalue=m_pRecordsetPm->GetCollect("里程"); 
		L=(double)Thevalue;

		while(L>=Lc1 && L<=Lc2 && m_pRecordsetPm->adoEOF==FALSE)
		{

			Thevalue=m_pRecordsetPm->GetCollect("序号");
			long ID=(long)Thevalue;
			
			Thevalue=m_pRecordsetPm->GetCollect("地面高程");
			double m_dmh=(double)Thevalue;
			
			H=H1+(L-Lc1)/(Lc2-Lc1)*(H2-H1);
			tt.Format("UPDATE T3DLineZxCorrdinate SET Y坐标=%.3f,填挖高=%.3f\
				WHERE 方案名称='%s' AND 序号=%d",H,H-m_dmh,\
				myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,ID);
			hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
			
			m_pRecordsetPm->MoveNext();
			if(m_pRecordsetPm->adoEOF)
			{
				break;
			}
			
			if(!m_pRecordsetPm->adoEOF)
			{
				Thevalue=m_pRecordsetPm->GetCollect("里程"); 
				L=(double)Thevalue;	
			}
			else
			{
				break;
				return;
			}
			
			
		}
	}

	m_pRecordsetPm->Close();
	m_pRecordsetZdm->Close();

	

	//修改坐标类型
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 序号,里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	m_pRecordsetPm->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	
	CString strJdStyle0,strJdStyle1,strJdStyle2;

	while(!m_pRecordsetPm->adoEOF)
	{
		
		Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
		strJdStyle1=Thevalue.bstrVal;
		if(strJdStyle1!="隧道起点" && strJdStyle1!="隧道终点" && strJdStyle1!="隧道中间点" &&\
			strJdStyle1!="桥梁起点" && strJdStyle1!="桥梁终点" && strJdStyle1!="桥梁中间点") 
		{
			strJdStyle0=strJdStyle1;
		}
		else
		{
			strJdStyle1=strJdStyle0;
		}

		Thevalue=m_pRecordsetPm->GetCollect("里程"); 
		L=(double)Thevalue;	

		if(!m_pRecordsetPm->adoEOF)
		{
			m_pRecordsetPm->MoveNext();//
			if(m_pRecordsetPm->adoEOF)
			{
				break;
			}
			
			Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
			strJdStyle2=Thevalue.bstrVal;
			
			if(strJdStyle2=="隧道起点" || strJdStyle2=="隧道终点" || strJdStyle2=="隧道中间点" ||\
				strJdStyle2=="桥梁起点" || strJdStyle2=="桥梁终点" || strJdStyle2=="桥梁中间点") 
			{
				Thevalue=m_pRecordsetPm->GetCollect("序号");
				long ID=(long)Thevalue;
			

				tt.Format("UPDATE T3DLineZxCorrdinate SET 坐标类型='%s'\
					WHERE 方案名称='%s' AND 序号=%d",strJdStyle1,\
					myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,ID);
				hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
			}
			
		}
	}
	m_pRecordsetPm->Close();

}


void CZdmDesign::ModifyPlanDesignData_Tunnel(int mTunnelBridge)
{
	CString strSql,strSql2,tt,mTitle,m_tableName;
	
	if(mTunnelBridge==1)
	{
		mTitle="隧道";
		m_tableName="Tunnel";
	}
	if(mTunnelBridge==2)
	{
		mTitle="桥梁";
		m_tableName="Bridge";
	}

	_RecordsetPtr m_pRecordsetPm,m_pRecordsetTunnel; 
	m_pRecordsetPm.CreateInstance(_uuidof(Recordset));
	m_pRecordsetTunnel.CreateInstance(_uuidof(Recordset));
	
	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' \
		ORDER BY 里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordsetPm->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		m_pRecordsetPm->Close();
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	tt=mTitle+"起点里程";
	
 
	strSql2.Format("SELECT *  FROM  %s WHERE 方案名称='%s' \
		ORDER BY %s ",m_tableName,\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,tt);
	try
	{
		m_pRecordsetTunnel->Open((_bstr_t)strSql2,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		m_pRecordsetTunnel->Close();
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 

	CString strJdStyle;
	double Lc1,Lc2,L;
	BOOL m_bFirst,m_bEnd;

	double m_CurrentLC;
	while(!m_pRecordsetTunnel->adoEOF)
	{

		tt=mTitle+"起点里程";
		
		Thevalue=m_pRecordsetTunnel->GetCollect(_variant_t(tt)); 
		Lc1=(double)Thevalue;
		
		tt=mTitle+"终点里程";
		Thevalue=m_pRecordsetTunnel->GetCollect(_variant_t(tt)); 
		Lc2=(double)Thevalue;


		m_bFirst=FALSE;
		m_bEnd=FALSE;

		
		while(!m_pRecordsetPm->adoEOF)
		{
			
			Thevalue=m_pRecordsetPm->GetCollect("里程"); 
			L=(double)Thevalue;
            
			m_CurrentLC=L;
			if(L>Lc2 && m_bEnd==FALSE)
			{
					m_bEnd=TRUE;
					double x1,y1,z1,PL1,x2,y2,z2,PL2,x,z,y;
					double DmH,DmH1,DmH2;
					CString strJdStyle1,strJdStyle2,strJdStyle;
					long pmID1, pmID;
					
					m_pRecordsetPm->MovePrevious();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Y坐标"); 
					y1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					pmID1=(long)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("地面高程");
					DmH1=(double)Thevalue;		
					
					m_pRecordsetPm->MoveNext();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Y坐标"); 
					y2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					Thevalue=m_pRecordsetPm->GetCollect("地面高程");
					DmH2=(double)Thevalue;			
					
					
					x=x1+(Lc2-PL1)/(PL2-PL1)*(x2-x1);
					z=z1+(Lc2-PL1)/(PL2-PL1)*(z2-z1);
					y=y1+(Lc2-PL1)/(PL2-PL1)*(y2-y1);
					DmH=DmH1+(Lc2-PL1)/(PL2-PL1)*(DmH2-DmH1);
					
					pmID=pmID1+1;
					strJdStyle=mTitle+"终点";
					
					tt.Format("INSERT INTO T3DLineZxCorrdinate VALUES(\
						'%s',%ld,%.3f,%.3f,%.3f,'%s',%.3f,%.3f,%.3f)",\
						myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
						pmID,
						x,
						y,
						z,
						strJdStyle,
						DmH,
						y-DmH,
						Lc2
						);
					try
					{
						hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
						if(!SUCCEEDED(hr))
						{
							AfxMessageBox("保存失败!",MB_ICONINFORMATION);
							return;
						}
					}
					catch(_com_error& e)	
					{
						CString errormessage;
						errormessage.Format("错误信息:%s",e.ErrorMessage());
						AfxMessageBox(errormessage,MB_ICONINFORMATION);
						return;
					} 
				break;
			}

			if(L>=Lc1 && L<=Lc2)
			{
				
				if(L>Lc1 && m_bFirst==FALSE)
				{                    
					m_bFirst=TRUE;
					double x1,y1,z1,PL1,x2,y2,z2,PL2,x,z,y;
					double DmH,DmH1,DmH2;
					CString strJdStyle1,strJdStyle2,strJdStyle;
					long pmID1, pmID;
					
					m_pRecordsetPm->MovePrevious();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Y坐标"); 
					y1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z1=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					pmID1=(long)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("地面高程");
					DmH1=(double)Thevalue;		
					
					m_pRecordsetPm->MoveNext();
					Thevalue=m_pRecordsetPm->GetCollect("里程");
					PL2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("X坐标"); 
					x2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Y坐标"); 
					y2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("Z坐标"); 
					z2=(double)Thevalue;
					Thevalue=m_pRecordsetPm->GetCollect("序号"); 
					Thevalue=m_pRecordsetPm->GetCollect("地面高程");
					DmH2=(double)Thevalue;			
					
				
					x=x1+(Lc1-PL1)/(PL2-PL1)*(x2-x1);
					z=z1+(Lc1-PL1)/(PL2-PL1)*(z2-z1);
					y=y1+(Lc1-PL1)/(PL2-PL1)*(y2-y1);
					DmH=DmH1+(Lc1-PL1)/(PL2-PL1)*(DmH2-DmH1);

					pmID=pmID1+1;
					strJdStyle=mTitle+"起点";
					
					tt.Format("INSERT INTO T3DLineZxCorrdinate VALUES(\
						'%s',%ld,%.3f,%.3f,%.3f,'%s',%.3f,%.3f,%.3f)",\
						myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
						pmID,
						x,
						y,
						z,
						strJdStyle,
						DmH,
						y-DmH,
						Lc1
						);
					try
					{
						hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
						if(!SUCCEEDED(hr))
						{
							AfxMessageBox("保存失败!",MB_ICONINFORMATION);
							return;
						}
					}
					catch(_com_error& e)	
					{
						CString errormessage;
						errormessage.Format("错误信息:%s",e.ErrorMessage());
						AfxMessageBox(errormessage,MB_ICONINFORMATION);
						return;
					} 
				}

				else
				{
					Thevalue=m_pRecordsetPm->GetCollect("序号");
					long ID=(long)Thevalue;
					
					if(L==Lc1)
					{
						strJdStyle=mTitle+"起点";
						m_bFirst=TRUE;
					}
					else if(L==Lc2)
					{
						strJdStyle=mTitle+"终点";
						m_bEnd=TRUE;

					}
					else
						strJdStyle=mTitle+"中间点";
					

					tt.Format("UPDATE T3DLineZxCorrdinate SET 坐标类型='%s'\
						WHERE 方案名称='%s' AND 里程=%.3f",strJdStyle,\
						myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,L);
					hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
					if(m_bEnd==TRUE) 
						break;
				}

			}
			
			m_pRecordsetPm->MoveNext();
			if(m_pRecordsetPm->adoEOF)
			{
				break;
			}
		}

		m_pRecordsetTunnel->MoveNext();

	}

	m_pRecordsetPm->Close();
	m_pRecordsetTunnel->Close();


}

 
void CZdmDesign::ModifyPlanDesignDataID(BOOL bModifyAttribute)
{
	CString tt,strSql;
	
	_RecordsetPtr m_pRecordsetPm; 
	m_pRecordsetPm.CreateInstance(_uuidof(Recordset));
	
	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		m_pRecordsetPm->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		m_pRecordsetPm->Close();
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 


	long Nums=0;
	while(!m_pRecordsetPm->adoEOF)
	{
		
		Nums++;
	
		Thevalue=m_pRecordsetPm->GetCollect("里程");
		double Lc=(double)Thevalue;
		
		tt.Format("UPDATE T3DLineZxCorrdinate SET 序号=%ld\
			WHERE 方案名称='%s' AND 里程=%.3f",Nums,\
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,Lc);
		hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		m_pRecordsetPm->MoveNext();
	}

	m_pRecordsetPm->Close();
	
	if(bModifyAttribute==TRUE)
		ModifyJDStyle();

	
}
void CZdmDesign::ModifyJDStyle()
{
	CString tt,strSql;
	
	_RecordsetPtr m_pRecordsetPm; 
	m_pRecordsetPm.CreateInstance(_uuidof(Recordset));
	
	///////////////////////////////////////////////////
	CString strJdStyle1,strJdStyle2;
	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 序号,里程",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	m_pRecordsetPm->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	
	while(!m_pRecordsetPm->adoEOF)
	{
		
		Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
		strJdStyle1=Thevalue.bstrVal;
		if(!m_pRecordsetPm->adoEOF)
		{
			m_pRecordsetPm->MoveNext();//
			if(m_pRecordsetPm->adoEOF)
			{
				break;
			}
			
			Thevalue=m_pRecordsetPm->GetCollect("坐标类型");
			strJdStyle2=Thevalue.bstrVal;
			if(strJdStyle1=="桥梁起点")
			{
				if(strJdStyle2!="桥梁中间点")
				{	
					strJdStyle2="桥梁中间点";
					
					Thevalue=m_pRecordsetPm->GetCollect("序号");
					long ID=(long)Thevalue;
					tt.Format("UPDATE T3DLineZxCorrdinate SET 坐标类型='%s'\
						WHERE 方案名称='%s' AND 序号=%d",strJdStyle2,\
						myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,ID);
					hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
				}
				
			}				
			else if(strJdStyle1=="隧道起点")
			{
				if(strJdStyle2!="隧道中间点")
				{
					strJdStyle2="隧道中间点";
					Thevalue=m_pRecordsetPm->GetCollect("序号");
					long ID=(long)Thevalue;
					tt.Format("UPDATE T3DLineZxCorrdinate SET 坐标类型='%s'\
						WHERE 方案名称='%s' AND 序号=%d",strJdStyle2,\
						myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,ID);
					hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
				}
			}
		}	
	}
	m_pRecordsetPm->Close();
}


void CZdmDesign::ReLoadNewDisginData()
{
	CString tt,strSql;



	PCordinate ppt;
	myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].RemoveAll();


	_RecordsetPtr m_pRecordset;
	_variant_t  Thevalue;  
		
	m_pRecordset.CreateInstance(_uuidof(Recordset));
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' \
		ORDER BY 里程 ASC",\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename\
		);
	
	m_pRecordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    

	while (!m_pRecordset->adoEOF)
	{
		ppt=new Cordinate;
		
		Thevalue = m_pRecordset->GetCollect("x坐标"); 
		ppt->x=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("y坐标"); 
		ppt->y=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("z坐标"); 
		ppt->z=(double)Thevalue;
		
		
		Thevalue = m_pRecordset->GetCollect("坐标类型"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			ppt->strJDStyle=tt;
		}
		
		Thevalue = m_pRecordset->GetCollect("地面高程"); 
		ppt->dmh=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("填挖高"); 
		ppt->Derh=(double)Thevalue;
		
		Thevalue = m_pRecordset->GetCollect("里程"); 
		ppt->Lc=(double)Thevalue;
		
		myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].Add(ppt);
		m_pRecordset->MoveNext();
		
	}
	m_pRecordset->Close();
	
	
	
}

void CZdmDesign::Insert3DlineZX()
{
	
	float L;
	PCordinate ppt;
	CString strsql;

	for(long i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		float h1=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->Derh;
		float h2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->Derh;
		double L1=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->Lc;
		double L2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->Lc;
		
		if(h1*h2<0)//不相同类型,内插处理
		{
			ppt=new Cordinate;
			
			float t=fabs((h1/h2));
			
			L=(L1+t*L2)/(1+t);
			ppt->Lc=L;
			ppt->Derh=0;
			
			t=(L-L1)/(L2-L1);
			ppt->x=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x+t*(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->x-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x);
			ppt->y=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y+t*(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->y-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y);
			ppt->z=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z+t*(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->z-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z);
			
			ppt->dmh=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->dmh+t*(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->dmh-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->dmh);
			ppt->Derh=0.0;
			ppt->strJDStyle=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
			
			
			strsql.Format("INSERT INTO T3DLineZxCorrdinate VALUES(\
			   '%s',%d,%.3f,%.3f,%.3f,'%s',\
			   %.3f,%.3f,%.3f)",\
			   myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
			   -1,
			   ppt->x,
			   ppt->y,
			   ppt->z,
			   ppt->strJDStyle,
			   ppt->dmh,
			   ppt->Derh,
			   ppt->Lc	
			   );

				hr=theApp.m_pConnection->Execute((_bstr_t)strsql,&RecordsAffected,adCmdText); 
				if(!SUCCEEDED(hr))
				{
					MessageBox("方案保存失败!","保存方案",MB_ICONINFORMATION);
					return;
				}
			}
		}
	
		ModifyPlanDesignDataID(FALSE);
}

//隧道
void CZdmDesign::OnZdmGraphtunnel() 
{
	graph_DesignType=GRAPH_TUNNEL;
	m_SelectPtNum=0;
	HCURSOR hCursor = AfxGetApp()->LoadStandardCursor(IDC_CROSS);
	this->SetWindowText("纵断面设计→隧道设计【图形选择方式】");
}

//桥梁
void CZdmDesign::OnZdmGraphbridge() 
{
	graph_DesignType=GRAPH_BRIDGE;
	m_SelectPtNum=0;
	this->SetWindowText("纵断面设计→桥梁设计【图形选择方式】");
	
}


void CZdmDesign::writeDesignData(BYTE graphDesignType,CString name,CString style,double startLc,double endLc)
{

	CString tt;

	if(graphDesignType==GRAPH_BRIDGE)
	{
		tt.Format("INSERT INTO Bridge VALUES(\
			'%s',%.3f,%.3f,%.3f,'%s')",\
			name,startLc,endLc,	endLc-startLc,
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename
			);
		theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		LoadBridgeData();
		this->Invalidate();
	}
	else if	(graphDesignType==GRAPH_TUNNEL)
	{

		tt.Format("INSERT INTO Tunnel VALUES(\
			'%s',%.3f,%.3f,%.3f,'%s')",\
			name,startLc,endLc,	endLc-startLc,
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename
			);
		theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		LoadTunnelData();
		this->Invalidate();
	}

	
}

void CZdmDesign::OnCancelMode() 
{
	CDialog::OnCancelMode();
	
}


