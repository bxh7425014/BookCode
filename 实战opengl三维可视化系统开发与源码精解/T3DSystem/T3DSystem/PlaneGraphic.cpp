// PlaneGraphic.cpp : implementation file
 
#include "stdafx.h"
#include "T3DSystem.h"
#include "PlaneGraphic.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define PAI 3.1415926
 
// CPlaneGraphic dialog

CPlaneGraphic::CPlaneGraphic(CWnd* pParent /*=NULL*/)
	: CDialog(CPlaneGraphic::IDD, pParent)
{
	//{{AFX_DATA_INIT(CPlaneGraphic)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CPlaneGraphic::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CPlaneGraphic)
		// NOTE: the ClassWizard will add DDX and DDV calls here
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CPlaneGraphic, CDialog)
	//{{AFX_MSG_MAP(CPlaneGraphic)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_VSCROLL()
	ON_WM_HSCROLL()
	ON_WM_ERASEBKGND()
	ON_WM_SIZE()
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_COMMAND(ID_GRAPH_MOVE2, OnGraphMove2)
	ON_WM_RBUTTONDOWN()
	ON_WM_MOUSEMOVE()
	ON_COMMAND(ID_ZOOMWINDOW, OnZoomwindow)
	ON_COMMAND(ID_ZOOMIN, OnZoomin)
	ON_COMMAND(ID_ZOOMOUT, OnZoomout)
	ON_WM_DESTROY()
	ON_COMMAND(ID_MENU_PLANE_FULLSCREEN, OnMenuPlaneFullscreen)
	ON_COMMAND(ID_MENU_PLANE_EXIT, OnMenuPlaneExit)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CPlaneGraphic message handlers

 

BOOL CPlaneGraphic::OnInitDialog()
{
	CDialog::OnInitDialog();

//	ShowWindow(SW_SHOWMAXIMIZED);
	
	
	

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			
	SetIcon(m_hIcon, FALSE);		
	
	m_brush.CreateSolidBrush(RGB( 255, 255, 255)); 
	
//	InitData();
		
//	CreateStatusBar();


	return TRUE;  // return TRUE  unless you set the focus to a control
}


// If you add a minimize button to your dialog, you will need the code below
 
 

void CPlaneGraphic::OnPaint() 
{

	if (IsIconic())
	{
		CPaintDC dc(this); 
		
		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);
		
		
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;
		
		
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
	
		DrawPlan();
	}




 
}

 
 
HCURSOR CPlaneGraphic::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

void CPlaneGraphic::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	SCROLLINFO scrollinfo;   
	GetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
	switch   (nSBCode)   
	{   
	case   SB_BOTTOM:   
		ScrollWindow(0,(scrollinfo.nPos-scrollinfo.nMax)*10);   
		scrollinfo.nPos = scrollinfo.nMax;   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		break;   
	case   SB_TOP:   
		ScrollWindow(0,(scrollinfo.nPos-scrollinfo.nMin)*10);   
		scrollinfo.nPos = scrollinfo.nMin;   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		break;   
	case   SB_LINEUP:   
		scrollinfo.nPos -= 1;   
		if   (scrollinfo.nPos<scrollinfo.nMin)   
		{   
			scrollinfo.nPos = scrollinfo.nMin;   
			break;   
		}   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		ScrollWindow(0,1);   
		break;   
	case   SB_LINEDOWN:   
		scrollinfo.nPos += 1;   
		if   (scrollinfo.nPos>scrollinfo.nMax)   
		{   
			scrollinfo.nPos = scrollinfo.nMax;   
			break;   
		}   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		ScrollWindow(0,-1);   
		break;   
	case   SB_PAGEUP:   
		scrollinfo.nPos -= 5;   
		if   (scrollinfo.nPos<scrollinfo.nMin)   
		{   
			scrollinfo.nPos = scrollinfo.nMin;   
			break;   
		}   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		ScrollWindow(0,1*5);   
		break;   
	case   SB_PAGEDOWN:   
		scrollinfo.nPos += 5;   
		if   (scrollinfo.nPos>scrollinfo.nMax)   
		{   
			scrollinfo.nPos = scrollinfo.nMax;   
			break;   
		}   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		ScrollWindow(0,-1*5);   
		break;   
	case   SB_THUMBTRACK:   
		ScrollWindow(0,(scrollinfo.nPos-nPos)*1);   
		scrollinfo.nPos = nPos;   
		SetScrollInfo(SB_VERT,&scrollinfo,SIF_ALL);   
		break;   
	}
	
	CRect rect;	
	GetClientRect(rect);
 
 
    this->RedrawWindow(NULL,NULL,RDW_INVALIDATE | RDW_UPDATENOW | RDW_ERASE );

	CDialog::OnVScroll(nSBCode, nPos, pScrollBar);
}

void CPlaneGraphic::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar) 
{
	SCROLLINFO scrollinfo;  
	GetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
	switch (nSBCode)  
	{  
	case SB_LEFT:  
		ScrollWindow((scrollinfo.nPos-scrollinfo.nMin)*10,0);  
		scrollinfo.nPos = scrollinfo.nMin;  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		break;  
	case SB_RIGHT:  
		ScrollWindow((scrollinfo.nPos-scrollinfo.nMax)*10,0);  
		scrollinfo.nPos = scrollinfo.nMax;  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		break;  
	case SB_LINELEFT:  
		scrollinfo.nPos -= 1;  
		if (scrollinfo.nPos<scrollinfo.nMin)
		{  
			scrollinfo.nPos = scrollinfo.nMin;  
			break;  
		}  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		ScrollWindow(10,0);  
		break;  
	case SB_LINERIGHT:  
		scrollinfo.nPos += 1;  
		if (scrollinfo.nPos>scrollinfo.nMax)  
		{  
			scrollinfo.nPos = scrollinfo.nMax;  
			break;  
		}  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		ScrollWindow(-10,0);  
		break;  
	case SB_PAGELEFT:  
		scrollinfo.nPos -= 5;  
		if (scrollinfo.nPos<scrollinfo.nMin)
		{  
			scrollinfo.nPos = scrollinfo.nMin;  
			break;  
		}  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		ScrollWindow(10*5,0);  
		break;  
	case SB_PAGERIGHT:  
		scrollinfo.nPos += 5;  
		if (scrollinfo.nPos>scrollinfo.nMax)  
		{  
			scrollinfo.nPos = scrollinfo.nMax;  
			break;  
		}  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		ScrollWindow(-10*5,0);  
		break;  
	case SB_THUMBPOSITION:  
		break;  
	case SB_THUMBTRACK:  
		ScrollWindow((scrollinfo.nPos-nPos)*10,0);  
		scrollinfo.nPos = nPos;  
		SetScrollInfo(SB_HORZ,&scrollinfo,SIF_ALL);  
		break;  
	case SB_ENDSCROLL:  
		break;  
	}  

	Invalidate(FALSE);   
	
	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}



BOOL CPlaneGraphic::OnEraseBkgnd(CDC* pDC) 
{
	// TODO: Add your message handler code here and/or call default
	return TRUE;
	return CDialog::OnEraseBkgnd(pDC);
}

void CPlaneGraphic::OnSize(UINT nType, int cx, int cy) 
{
	CDialog::OnSize(nType, cx, cy);
	
	m_wScreen=cx;
	m_hScreen=cy;
	
	if(m_bLoadData==1)
	{	
		
		float bl1=(Big_x-Small_x)/(m_wScreen-20) ;
		blc=(Big_y-Small_y)/(m_hScreen-20) ;
		if(bl1>blc) blc=bl1; 
		if(blc<1) blc=1; 
		this->Invalidate(FALSE);
	}

}



void CPlaneGraphic::VPtoDP(int x, int y, double *X, double *Y)
{	
	*X=m_xStart+x*blc;						
	if(m_MapMode==1)						
	*Y=m_yStart+blc*(m_hScreen-y);		
	else									
	*Y=m_yStart+blc*(y+m_hScreen);		


}

 
 
float CPlaneGraphic::VLtoDL(int L)
{
	return blc*L;	
	
}

 
 
int CPlaneGraphic::DLtoVL(float L)
{
	return (int)(L/blc);	
	
}

void CPlaneGraphic::DPtoVP(double x, double y, double *X, double *Y)
{
	*X=(double)((x-m_xStart)/blc);					
	if(m_MapMode==1)							
		*Y=m_hScreen-(double)((y-m_yStart)/blc);	
	else										
		*Y=(double)((y-m_yStart)/blc)-m_hScreen;	
	
}

 
void CPlaneGraphic::Get_Min_Max_XY(double x, double y)
{
	if(x<0 || y<0) return;
	if(Small_x>x) Small_x=x;
	if(Small_y>y) Small_y=y;
	if(Big_x<x) Big_x=x;
	if(Big_y<y) Big_y=y;
}



void CPlaneGraphic::GetMinMax_XY()
{
	int mm=JDCurveElements.GetSize();

	
	for(long i=0;i<JDCurveElements.GetSize();i++)
	{
	
		Get_Min_Max_XY(JDCurveElements.GetAt(i)->x,JDCurveElements.GetAt(i)->y);
	}
	GetMaxXY=1;

	m_xStart=Small_x; 
	m_yStart=Small_y-200;
	float bl1=(Big_x-Small_x)/(m_wScreen-20) ;
	blc=(Big_y-Small_y)/(m_hScreen-20) ;
	if(bl1>blc) blc=bl1; 
	if(blc<1) blc=1; 

	m_xStart0=m_xStart;
	m_yStart0=m_yStart;
	blc0=blc;
	
	m_Screen[0].sx=m_xStart;
	m_Screen[0].sy=m_yStart;
	m_Screen[0].blc=blc;
	
}

 
void CPlaneGraphic::DrawBKgrid(/*CDC *pDC*/)
{

	CPaintDC dc(this); 
	
	dc.SetBkMode(TRANSPARENT );
	CRect rect;	
	GetClientRect(rect);
	dc.FillSolidRect(rect,RGB(255,255,255));  

	CPen pen(4,1,RGB(220,220,220));
	CPen* pOldPen=dc.SelectObject(&pen);
	int m_Grid_Point=0;
	int m_GridCols=12;
	int m_GridRows=9;
	int m_GridRows_Point=50;
	if(m_Grid_Point==0)
	{
		for(int i=1;i<m_GridCols;i++)
		{
			dc.MoveTo (i*m_wScreen/m_GridCols,0);
			dc.LineTo (i*m_wScreen/m_GridCols,m_hScreen);
		}
		
		for(i=1;i<m_GridRows;i++)
		{
			dc.MoveTo (0,i*m_hScreen/m_GridRows);
			dc.LineTo (m_wScreen,i*m_hScreen/m_GridRows);
		}
	}	
	dc.SelectObject(pOldPen);


	if(	GetMaxXY!=1)
	{
		return;
		
	}
		
	CPen m_inputPen;
	m_inputPen.CreatePen(PS_SOLID,2,RGB(255,0,255));
	CPen *poldPen=NULL;
	poldPen=dc.SelectObject(&m_inputPen);
	
	double x1,y1,x2,y2;
	CString tt;
	
	for(long i=0;i<PtS.GetSize()-1;i++)
	{
		DPtoVP(PtS.GetAt(i)->x,PtS.GetAt(i)->z,&x1,&y1);
		DPtoVP(PtS.GetAt(i+1)->x,PtS.GetAt(i+1)->z,&x2,&y2);
		
		dc.MoveTo(x1,y1); 
		dc.LineTo(x2,y2);
		tt=JDCurveElements.GetAt(i)->ID;
		dc.TextOut(x1,y1,tt);
	}
	tt=JDCurveElements.GetAt(i)->ID;
	dc.TextOut(x2,y2,tt);
	
	dc.SelectObject(poldPen);

	
}

void CPlaneGraphic::OnLButtonDown(UINT nFlags, CPoint point) 
{
	mCurrentPoint=point;
    CString tt1,tt2;
	
	double x1,y1,x2,y2;
	
	if(m_DrawCurrent==13 && m_moveStyle==3) 
	{
		double w,h;
		
		w=fabs(point.x-mCurrentPoint.x);
		h=fabs(point.y-mCurrentPoint.y);
		
		
		float w1=  (m_wScreen/2-mCurrentPoint.x);
		float h1= (m_hScreen/2-mCurrentPoint.y);
		m_xStart=m_xStart-blc*w1;  
		m_yStart=m_yStart+blc*h1;
		AddScreen (m_xStart,m_yStart,blc);
		this->Invalidate ();
	}
	
	
	else if(m_DrawCurrent==11 || m_DrawCurrent==12 || m_DrawCurrent==13)
	{
		if(PushNumb ==0) 
		{
			mPointOrign=point; 
			mPointOld=point;   
			PushNumb++;		   
			SetCapture();      
		}
		else if(PushNumb ==1) 
		{
			if(m_DrawCurrent==11) 
			{
				
				x1=min(mPointOrign.x,point.x);
				y1=max(mPointOrign.y,point.y);
				x2=max(mPointOrign.x,point.x);
				y2=min(mPointOrign.y,point.y);
				
				this->VPtoDP (x1,y1,&m_xStart,&m_yStart);
				float bl1=(float)this->m_wScreen/(float)(x2-x1); 
				float bl2=(float)this->m_hScreen/(float)(y1-y2);
				if(bl2<bl1) bl2=bl1;
				blc=blc/bl2;
			}
			else if(m_DrawCurrent==12) 
			{
				
				x1=min(mPointOrign.x,point.x);
				y1=max(mPointOrign.y,point.y);
				x2=max(mPointOrign.x,point.x);
				y2=min(mPointOrign.y,point.y);
				
				this->VPtoDP (x1,y1,&m_xStart,&m_yStart);
				float bl1=(float)this->m_wScreen/(float)(x2-x1); 
				float bl2=(float)this->m_hScreen/(float)(y1-y2);
				if(bl2<bl1) bl2=bl1;
				blc=blc*bl2;
			}
			else 
			{
				
				m_xStart=m_xStart-blc*(point.x-mPointOrign.x);
				m_yStart=m_yStart+blc*(point.y-mPointOrign.y);
			}
			PushNumb=0;
			AddScreen (m_xStart,m_yStart,blc);
			ReleaseCapture ();
			this->Invalidate(); 
		}

	}
	
	else if(m_DrawCurrent==10)		
		PushNumb=1;		   
	
	else if(m_DrawCurrent==9)
	{
		double x1,y1,w,h;
		
		
		mPointOrign.x=150;
		mPointOrign.y=m_hScreen*1.0/m_wScreen*mPointOrign.x;
		point.x=m_wScreen-mPointOrign.x;
		point.y=m_hScreen-mPointOrign.y;
		
		x1=min(mPointOrign.x,point.x);
		y1=max(mPointOrign.y,point.y);
		x2=max(mPointOrign.x,point.x);
		y2=min(mPointOrign.y,point.y);
		
		this->VPtoDP (x1,y1,&m_xStart,&m_yStart);
		float bl1=(float)this->m_wScreen/(float)(x2-x1); 
		float bl2=(float)this->m_hScreen/(float)(y1-y2);
		if(bl2<bl1) bl2=bl1;
		blc=blc/bl2;
		AddScreen (m_xStart,m_yStart,blc);
		this->Invalidate ();
		
	}
	
	else if(m_DrawCurrent==8)
	{
 
		
		
		mPointOrign.x=-50;
		mPointOrign.y=-m_hScreen*1.0/m_wScreen*mPointOrign.x;
		point.x=m_wScreen-mPointOrign.x;
		point.y=m_hScreen-mPointOrign.y;
		
		x1=min(mPointOrign.x,point.x);
		y1=max(mPointOrign.y,point.y);
		x2=max(mPointOrign.x,point.x);
		y2=min(mPointOrign.y,point.y);
		
		this->VPtoDP (x1,y1,&m_xStart,&m_yStart);
		float bl1=(float)this->m_wScreen/(float)(x2-x1); 
		float bl2=(float)this->m_hScreen/(float)(y1-y2);
		if(bl2>bl1) bl2=bl1;
		blc=blc/bl2;
		AddScreen (m_xStart,m_yStart,blc);
		this->Invalidate ();
		
	}

	
	CDialog::OnLButtonDown(nFlags, point);
}

void CPlaneGraphic::OnLButtonUp(UINT nFlags, CPoint point) 
{
	if(m_DrawCurrent==10) 
	{
		double x1,y1,w,h;
		
		
		w=fabs(point.x-mCurrentPoint.x);
		h=fabs(point.y-mCurrentPoint.y);
		if(w<1 && h<1) return;
		
		float w1=  (m_wScreen/2-mCurrentPoint.x);
		float h1= (m_hScreen/2-mCurrentPoint.y);
		
		m_xStart=m_xStart-blc*w1;  
		m_yStart=m_yStart+blc*h1;
		
		if(point.y<=mCurrentPoint.y)
		{
			float bl1=(float)this->m_wScreen/(float)(m_wScreen-w); 
			float bl2=(float)this->m_hScreen/(float)(m_hScreen-h);
			if(bl2<bl1) bl2=bl1;
			
			blc=blc/bl2;
			m_xStart=m_xStart+blc*w;
			m_yStart=m_yStart+blc*h;
		}
		else
		{
			float bl1=(float)this->m_wScreen/(float)(m_wScreen+w); 
			float bl2=(float)this->m_hScreen/(float)(m_hScreen+h);
			if(bl2>bl1) bl2=bl1;
			
			blc=blc/bl2;
			m_xStart=m_xStart-blc*w/2;
			m_yStart=m_yStart+blc*h/2;
		}
		AddScreen (m_xStart,m_yStart,blc);
		this->Invalidate ();
	}
	
	if(m_DrawCurrent==13 && m_moveStyle==2)		
	{
	
	
		
		m_xStart=m_xStart-blc*(point.x-mPointOrign.x);
		m_yStart=m_yStart+blc*(point.y-mPointOrign.y);
		
		PushNumb=0;
		AddScreen (m_xStart,m_yStart,blc);
		ReleaseCapture ();
		this->Invalidate(); 
	}
	
	CDialog::OnLButtonUp(nFlags, point);
}

 
 
void CPlaneGraphic::CreateStatusBar()
{
	m_dlgwndStatusBar.Create(this); 
	m_dlgwndStatusBar.SetIndicators(indicator_PLANE,6); 
	UINT Style;
	Style=m_dlgwndStatusBar.GetPaneStyle(0);
	Style&=~(SBPS_POPOUT|SBT_NOBORDERS);
	m_dlgwndStatusBar.SetPaneStyle(0,Style);
	CRect rect;
	GetClientRect(&rect);
	m_dlgwndStatusBar.SetPaneInfo (0,0,0,70);
	m_dlgwndStatusBar.SetPaneInfo (1,0,0,180);
	m_dlgwndStatusBar.SetPaneInfo (2,0,0,160);
	m_dlgwndStatusBar.SetPaneInfo (3,0,0,160);
	m_dlgwndStatusBar.SetPaneInfo (4,0,0,110);
	m_dlgwndStatusBar.SetPaneInfo (5,0,0,110);
	m_dlgwndStatusBar.SetPaneStyle (0,SBPS_POPOUT);
	
	RepositionBars(AFX_IDW_CONTROLBAR_FIRST,AFX_IDW_CONTROLBAR_LAST,ID_INDICATOR_MESSAGE);
	
	m_dlgwndStatusBar.GetClientRect(rect);
}

 


 
void CPlaneGraphic::OnGraphMove2() 
{
 
 
	m_moveStyle=2;
	m_DrawCurrent=13;
	PushNumb=0; 
	
}

 



void CPlaneGraphic::OnRButtonDown(UINT nFlags, CPoint point) 
{
 
	m_DrawCurrent=-1;	
	
	CDialog::OnRButtonDown(nFlags, point);
}

void CPlaneGraphic::OnMouseMove(UINT nFlags, CPoint point) 
{
 
	CClientDC ddd(this);
	CPen pen(0,2,RGB(0,0,0));
	CPen* pOldPen=ddd.SelectObject(&pen);
	ddd.SetROP2(R2_NOT);	
	int r;
	BOOL pb;
	int Lb,DwgIndex,Index,pbh;
	
	double x1,y1;
	char p1[100];
 
	
	VPtoDP(point.x,point.y,&x1,&y1);
 
 
	
	x1=m_xStart+blc*point.x;               
	y1=m_yStart+blc*(m_hScreen-point.y);  
	float jl=blc*4;            
	


	
	
	
    if((m_DrawCurrent==11||m_DrawCurrent==12||m_DrawCurrent==13||m_DrawCurrent==50)&&PushNumb==1)
		
    {
		if(point!=mPointOld)		
		{
			if(m_DrawCurrent==11||m_DrawCurrent==12 || m_DrawCurrent==50)	
			{
				ddd.SelectStockObject(NULL_BRUSH); 
				
				ddd.Rectangle(mPointOrign.x,mPointOrign.y,mPointOld.x,mPointOld.y);
				
				ddd.Rectangle(mPointOrign.x,mPointOrign.y,point.x,point.y);
			}
			else 	
			{
				
				ddd.MoveTo(mPointOrign);
				if(m_moveStyle==1)  
					ddd.LineTo(mPointOld);
				
				ddd.MoveTo(mPointOrign);
				if(m_moveStyle==1) 
					ddd.LineTo(point);
			}
			mPointOld=point;  
		}
    }
	ddd.SelectObject(pOldPen); 
	

	
	CDialog::OnMouseMove(nFlags, point);
}




 
void CPlaneGraphic::OnZoomwindow() 
{
	m_DrawCurrent=11;
	PushNumb=0;
	
}

 


 
CString CPlaneGraphic::GetLC(double LC)
{
	CString tt,tt1,tt2;
	
	if(LC==0)
	{
		tt.Format("K%d+%.3f",0,0);
		return tt;		
	}
	
	int L1=int(LC/1000.0);
	float L2=LC-L1*1000;
	
	
	
	if(L2>99)
	{
		tt2.Format("%.3f",L2);
	}
	else if(L2>9)
	{
		tt2.Format("0%.3f",L2);
	}
	else
	{
		tt2.Format("00%.3f",L2);
	}
	tt1.Format("K%d+",L1);
	tt=tt1+tt2;
	
	return tt;
}


 
 
 
int CPlaneGraphic::GetCurveL0(int mR)
{
	if(mR==0)
		return 0;
	
	int mL0=0;
	int mspeed=160;
	if(mspeed==160)
	{
		switch(mR)
		{
		case 12000:mL0=40;break;
		case 10000:mL0=50;break;
		case 8000:mL0=60;break;
		case 7000:mL0=70;break;
		case 6000:mL0=70;break;
		case 5000:mL0=70;break;
		case 4500:mL0=70;break;
		case 4000:mL0=80;break;
		case 3500:mL0=90;break;
		case 3000:mL0=100;break;
		case 2800:mL0=110;break;
		case 2500:mL0=120;break;
		case 2000:mL0=150;break;
		case 1800:mL0=170;break;
		case 1600:mL0=190;break;			
		}
	}
	else if(mspeed==140)
	{
		switch(mR)
		{
		case 12000:mL0=40;break;
		case 10000:mL0=40;break;
		case 8000:mL0=40;break;
		case 7000:mL0=50;break;
		case 6000:mL0=50;break;
		case 5000:mL0=60;break;
		case 4500:mL0=60;break;
		case 4000:mL0=60;break;
		case 3500:mL0=70;break;
		case 3000:mL0=80;break;
		case 2800:mL0=90;break;
		case 2500:mL0=90;break;
		case 2000:mL0=100;break;
		case 1800:mL0=120;break;
		case 1600:mL0=130;break;			
		case 1400:mL0=150;break;
		case 1200:mL0=190;break;			
		}
	}
	else if(mspeed==120)
	{
		switch(mR)
		{
		case 12000:mL0=40;break;
		case 10000:mL0=40;break;
		case 8000:mL0=40;break;
		case 7000:mL0=40;break;
		case 6000:mL0=40;break;
		case 5000:mL0=40;break;
		case 4500:mL0=40;break;
		case 4000:mL0=50;break;
		case 3500:mL0=50;break;
		case 3000:mL0=50;break;
		case 2800:mL0=60;break;
		case 2500:mL0=60;break;
		case 2000:mL0=70;break;
		case 1800:mL0=80;break;
		case 1600:mL0=90;break;			
		case 1400:mL0=100;break;
		case 1200:mL0=120;break;			
		case 1000:mL0=140;break;
		case 800:mL0=180;break;			
		}
	}
	return mL0;
	
}

void CPlaneGraphic::DrawPlan()
{
	

	CPaintDC dc(this); 
	
	dc.SetBkMode(TRANSPARENT );
	CRect rect;	
	GetClientRect(rect);
	dc.FillSolidRect(rect,RGB(255,255,255));  

	
	CPen pen(4,1,RGB(220,220,220));
	CPen* pOldPen=dc.SelectObject(&pen);
	int m_Grid_Point=0;
	int m_GridCols=12;
	int m_GridRows=9;
	int m_GridRows_Point=50;
	if(m_Grid_Point==0)
	{
		for(int i=1;i<m_GridCols;i++)
		{
			dc.MoveTo (i*m_wScreen/m_GridCols,0);
			dc.LineTo (i*m_wScreen/m_GridCols,m_hScreen);
		}
		
		for(i=1;i<m_GridRows;i++)
		{
			dc.MoveTo (0,i*m_hScreen/m_GridRows);
			dc.LineTo (m_wScreen,i*m_hScreen/m_GridRows);
		}
	}	
	dc.SelectObject(pOldPen);
	
	if(JDCurveElements.GetSize()<=1)
		return;

	
	if(	GetMaxXY!=1)
	{
		return;
	}

	
	pOldPen=dc.SelectObject(&m_PenJDLX);
	
	double x0,y0,x1,y1,x2,y2;
	double xx1,yy1,xx2,yy2;
	double xx,yy;
	
	float fwj2;
	float  angg;
	int mroatateStyle;

	CFont* cjcbakf=NULL;

	CString tt;

	
	if(m_bLoadData!=1)
		return;

	
	if(m_bAddCADData==FALSE)
	{
		ployLineCAD.RemoveAll();
		CircleCAD.RemoveAll();
		TextCAD.RemoveAll();
	}
	
	PPolyLine mypolyline;
	PCordinate ppt;
	PCircle mcirlce;
	PCADText mText;
	
	
	pOldPen=dc.SelectObject(&m_PenJDLX);
 
 
	DPtoVP(JDCurveElements.GetAt(0)->x,JDCurveElements.GetAt(0)->y,&x1,&y1);
	DPtoVP(JDCurveElements.GetAt(1)->x,JDCurveElements.GetAt(1)->y,&x2,&y2);
	
	dc.MoveTo(x1,y1); 
	dc.LineTo(x2,y2);
	if(m_bAddCADData==FALSE)
	{
		mypolyline=new PolyLine;
		ppt = new Cordinate; 
		ppt->x=x1;ppt->y=y1;
		mypolyline->PtCAD.Add(ppt);
		PCordinate ppt = new Cordinate; 
		ppt->x=x2;ppt->y=y2;
		mypolyline->PtCAD.Add(ppt);
		mypolyline->lineWidth=m_penWidth[2]-1;
		mypolyline->mcolor=m_penColorIndex[2];
		mypolyline->Index=2;
		ployLineCAD.Add(mypolyline);
	}
	

	
	pOldPen=dc.SelectObject(&m_PenZX);
	DPtoVP(JDCurveElements.GetAt(0)->HZ_xy->x,JDCurveElements.GetAt(0)->HZ_xy->y,&x1,&y1);
	DPtoVP(JDCurveElements.GetAt(1)->ZH_xy->x,JDCurveElements.GetAt(1)->ZH_xy->y,&x2,&y2);
	
	dc.MoveTo(x1,y1); 
	dc.LineTo(x2,y2);


	if(m_bAddCADData==FALSE)
	{
		mypolyline=new PolyLine;
		ppt = new Cordinate; 
		ppt->x=x1;ppt->y=y1;
		mypolyline->PtCAD.Add(ppt);
		ppt = new Cordinate; 
		ppt->x=x2;ppt->y=y2;
		mypolyline->PtCAD.Add(ppt);
		mypolyline->lineWidth=m_penWidth[0]-1;
		mypolyline->mcolor=m_penColorIndex[0];
		mypolyline->Index=0;
		ployLineCAD.Add(mypolyline);
		
	}


	
	dc.SetTextAlign(TA_CENTER|TA_BOTTOM);
	dc.SetTextColor(m_fontColor[2]);	
	m_FontJDBH.DeleteObject();
	
	m_FontJDBH.CreateFont(DLtoVL(m_fontSize[2]*10.0),0,0,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[2]);
	
	cjcbakf=dc.SelectObject(&m_FontJDBH);
	
	double mpy;
	if(JDCurveElements.GetAt(1)->RoateStyle==-1)
	{
		dc.TextOut(x1,y1-8,"JD0");
		mpy=y1-8;
	}
	else if(JDCurveElements.GetAt(1)->RoateStyle==1)
	{
		dc.TextOut(x1,y1+8,"JD0");
		mpy=y1+8;
	}
	
	if(m_bAddCADData==FALSE)
	{
		mText=new CADText;
		mText->x=x1;
		mText->y=-mpy;
		mText->angle=0;
		mText->fonhHeight=m_fontSize[2];
		mText->fontsizeIndex=2;
		mText->strText="JD0";
		TextCAD.Add(mText);
	}

	pOldPen=dc.SelectObject(&m_PenJDLX);
	dc.Ellipse(x1-5,y1-5,x1+5,y1+5);
	if(m_bAddCADData==FALSE)
	{
		mcirlce=new Circle;
		mcirlce->Cneterx=x1;
		mcirlce->Cnetery=-y1;
		mcirlce->R=5;
		mcirlce->mcolor=m_penColorIndex[2];
		CircleCAD.Add(mcirlce);
	}

	int mLength=500;
	int mLength2=100;
	int mLength3=50;
	mLength=DLtoVL(mLength);
	mLength2=DLtoVL(mLength2);
	mLength3=DLtoVL(mLength3);
	
	int disH=100;
	disH=DLtoVL(disH);
	
	for(long i=1;i<JDCurveElements.GetSize()-1;i++) 
		{
		pOldPen=dc.SelectObject(&m_PenJDLX);
		
		DPtoVP(JDCurveElements.GetAt(i)->x,JDCurveElements.GetAt(i)->y,&x1,&y1);
		DPtoVP(JDCurveElements.GetAt(i+1)->x,JDCurveElements.GetAt(i+1)->y,&x2,&y2);
		
		dc.MoveTo(x1,y1); 
		dc.LineTo(x2,y2);
	
		if(m_bAddCADData==FALSE)
		{
			mypolyline=new PolyLine;
			ppt = new Cordinate; 
			ppt->x=x1;ppt->y=y1;
			mypolyline->PtCAD.Add(ppt);
			ppt = new Cordinate; 
			ppt->x=x2;ppt->y=y2;
			mypolyline->PtCAD.Add(ppt);
			mypolyline->lineWidth=m_penWidth[2]-1;
			mypolyline->mcolor=m_penColorIndex[2];
			mypolyline->Index=2;
			ployLineCAD.Add(mypolyline);
		}
	
		mroatateStyle=JDCurveElements.GetAt(i)->RoateStyle;

		
		dc.SetTextColor(m_fontColor[2]);	
		m_FontJDBH.DeleteObject();
		
		m_FontJDBH.CreateFont(DLtoVL(m_fontSize[2]*10.0),0,0,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[2]);
		
		cjcbakf=dc.SelectObject(&m_FontJDBH);
		
		tt=JDCurveElements.GetAt(i)->ID;
		if(mroatateStyle==-1)
		{
			dc.SetTextAlign(TA_CENTER|TA_TOP);
			dc.TextOut(x1,y1+10,tt);
			mpy=y1+10;
		}
		else if(mroatateStyle==1)
		{
			dc.SetTextAlign(TA_CENTER|TA_BOTTOM);
			dc.TextOut(x1,y1-10,tt);
			mpy=y1-10;
		}

		if(m_bAddCADData==FALSE)
		{
			mText=new CADText;
			mText->x=x1;
			mText->y=-mpy;
			mText->angle=0;
			mText->fonhHeight=m_fontSize[2];
			mText->fontsizeIndex=2;
			mText->strText=tt;
			TextCAD.Add(mText);
		}

		pOldPen=dc.SelectObject(&m_PenJDLX);
		dc.Ellipse(x1-5,y1-5,x1+5,y1+5);
		if(m_bAddCADData==FALSE)
		{
			mcirlce=new Circle;
			mcirlce->Cneterx=x1;
			mcirlce->Cnetery=-y1;
			mcirlce->R=5;
			mcirlce->mcolor=m_penColorIndex[2];
			CircleCAD.Add(mcirlce);
		}
		pOldPen=dc.SelectObject(&m_PenZX);
		
		DPtoVP(JDCurveElements.GetAt(i)->HZ_xy->x,JDCurveElements.GetAt(i)->HZ_xy->y,&x1,&y1);
		DPtoVP(JDCurveElements.GetAt(i+1)->ZH_xy->x,JDCurveElements.GetAt(i+1)->ZH_xy->y,&x2,&y2);
		
		dc.MoveTo(x1,y1); 
		dc.LineTo(x2,y2);
		if(m_bAddCADData==FALSE)
		{
			mypolyline=new PolyLine;
			ppt = new Cordinate; 
			ppt->x=x1;ppt->y=y1;
			mypolyline->PtCAD.Add(ppt);
			ppt = new Cordinate; 
			ppt->x=x2;ppt->y=y2;
			mypolyline->PtCAD.Add(ppt);
			mypolyline->lineWidth=m_penWidth[0]-1;
			mypolyline->mcolor=m_penColorIndex[0];
			mypolyline->Index=0;
			ployLineCAD.Add(mypolyline);
		}
	
		
		
		
		if(m_bShow_ZH==TRUE)
		{	
			dc.SetTextColor(m_fontColor[1]);

			pOldPen=dc.SelectObject(&m_PenLCBZ);
			xx=JDCurveElements.GetAt(i)->ZH_xy->x;
			yy=JDCurveElements.GetAt(i)->ZH_xy->y;
			
			DPtoVP(xx,yy,&x1,&y1);
		
			GetLineXY(x1,y1,&xx1,&yy1,&fwj2,JDCurveElements.GetAt(i)->RoateStyle,JDCurveElements.GetAt(i-1)->fwj2,mLength,99);		
			dc.MoveTo(x1,y1);
			dc.LineTo(xx1,yy1);
		
			if(m_bAddCADData==FALSE)
			{
				mypolyline=new PolyLine;
				ppt = new Cordinate; 
				ppt->x=x1;ppt->y=y1;
				mypolyline->PtCAD.Add(ppt);
				ppt = new Cordinate; 
				ppt->x=xx1;ppt->y=yy1;
				mypolyline->PtCAD.Add(ppt);
				mypolyline->lineWidth=m_penWidth[3]-1;
				mypolyline->mcolor=m_penColorIndex[3];
				mypolyline->Index=3;
				ployLineCAD.Add(mypolyline);
				
			} 
		
			
			
				angg=GetAngle2(fwj2,mroatateStyle);
				
				m_FontLCBZ.DeleteObject();
				
				m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
				
				cjcbakf=dc.SelectObject(&m_FontLCBZ);
				dc.SetBkMode(TRANSPARENT);          
				dc.SetTextAlign(TA_LEFT);
				
				
				tt="ZH "+GetLC(JDCurveElements.GetAt(i)->ZH);
				dc.TextOut(x1,y1,tt);
				if(m_bAddCADData==FALSE)
				{
					mText=new CADText;
					mText->x=x1;
					mText->y=-y1;
					mText->angle=GetCADZHHYYHHZAngle(x1,-y1,xx1,-yy1,JDCurveElements.GetAt(i)->RoateStyle);;
					mText->fonhHeight=m_fontSize[1];
					mText->fontsizeIndex=1;
					mText->strText=tt;
					TextCAD.Add(mText);
				}
		}

		if(m_bShow_HY==TRUE)
		{
			pOldPen=dc.SelectObject(&m_PenLCBZ);
			
			
			myDesingScheme.GetYQXXY(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,\
				JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
				0,JDCurveElements.GetAt(i)->Alfa,JDCurveElements.GetAt(i)->HY_xy->x,\
				JDCurveElements.GetAt(i)->HY_xy->y,\
				JDCurveElements.GetAt(i)->YH_xy->x,JDCurveElements.GetAt(i)->YH_xy->y,\
				&x1,&y1);
			DPtoVP(x1,y1,&xx,&yy);
			dc.MoveTo(xx,yy); 
			dc.Ellipse(xx-15,yy-15,xx+15,yy+15);
			DPtoVP(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,&x1,&y1);
			GetYQXBZ(xx,yy,x1,y1,mLength/2.0,&x2,&y2,0);
			dc.LineTo(x2,y2);
			
			if(m_bAddCADData==FALSE)
			{
				mypolyline=new PolyLine;
				ppt = new Cordinate; 
				ppt->x=xx;ppt->y=yy;
				mypolyline->PtCAD.Add(ppt);
				ppt = new Cordinate; 
				ppt->x=x2;ppt->y=y2;
				mypolyline->PtCAD.Add(ppt);
				mypolyline->lineWidth=m_penWidth[2]-1;
				mypolyline->mcolor=m_penColorIndex[2];
				mypolyline->Index=2;
				ployLineCAD.Add(mypolyline);
			}
		
		
			angg=GetAngle2(fwj2,mroatateStyle);
			
			m_FontLCBZ.DeleteObject();
			
			m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
			
			cjcbakf=dc.SelectObject(&m_FontLCBZ);
			dc.SetTextColor(m_fontColor[1]);
			dc.SetTextAlign(TA_LEFT);
			tt="HY "+GetLC(JDCurveElements.GetAt(i)->HY);
			dc.TextOut(xx,yy,tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=xx;
				mText->y=-yy;
				mText->angle=GetCADZHHYYHHZAngle(xx,-yy,x2,-y2,JDCurveElements.GetAt(i)->RoateStyle);;
				mText->fonhHeight=m_fontSize[1];
				mText->fontsizeIndex=1;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			dc.SelectObject(cjcbakf);
		}
			
		if(m_bShow_YH==TRUE)
		{
			pOldPen=dc.SelectObject(&m_PenLCBZ);
			xx=JDCurveElements.GetAt(i)->YH_xy->x;
			yy=JDCurveElements.GetAt(i)->YH_xy->y;
			DPtoVP(xx,yy,&x1,&y1);
 
			if(mroatateStyle==0)
				mroatateStyle=JDCurveElements.GetAt(i)->RoateStyle;
			
			GetLineXY(x1,y1,&xx1,&yy1,&fwj2,mroatateStyle,JDCurveElements.GetAt(i)->fwj2,mLength,99);		
			
			
			myDesingScheme.GetYQXXY(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,\
				JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
				JDCurveElements.GetAt(i)->YH-JDCurveElements.GetAt(i)->HY,\
				JDCurveElements.GetAt(i)->Alfa,JDCurveElements.GetAt(i)->HY_xy->x,\
				JDCurveElements.GetAt(i)->HY_xy->y,\
				JDCurveElements.GetAt(i)->YH_xy->x,JDCurveElements.GetAt(i)->YH_xy->y,\
				&x1,&y1);
			
		//	x1=2633.206;y1=4004.148;
			DPtoVP(x1,y1,&xx,&yy);
			dc.MoveTo(xx,yy); 
			dc.Ellipse(xx-15,yy-15,xx+15,yy+15);
			DPtoVP(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,&x1,&y1);
			GetYQXBZ(xx,yy,x1,y1,mLength/1.0,&x2,&y2,JDCurveElements.GetAt(i)->YH-JDCurveElements.GetAt(i)->HY);
			dc.LineTo(x2,y2);
			
			
			if(m_bAddCADData==FALSE)
			{
				mypolyline=new PolyLine;
				ppt = new Cordinate; 
				ppt->x=xx;ppt->y=yy;
				mypolyline->PtCAD.Add(ppt);
				ppt = new Cordinate; 
				ppt->x=x2;ppt->y=y2;
				mypolyline->PtCAD.Add(ppt);
				mypolyline->lineWidth=m_penWidth[3]-1;
				mypolyline->mcolor=m_penColorIndex[3];
				mypolyline->Index=3;
				ployLineCAD.Add(mypolyline);
				
			}
			angg=GetAngle2(fwj2,mroatateStyle);
			m_FontLCBZ.DeleteObject();
			
			m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
			
			cjcbakf=dc.SelectObject(&m_FontLCBZ);
			dc.SetTextAlign(TA_LEFT);
			
			dc.SetTextColor(m_fontColor[1]);
			tt="YH "+GetLC(JDCurveElements.GetAt(i)->YH);			
			dc.TextOut(xx,yy,tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=xx;
				mText->y=-yy;
				mText->angle=GetCADZHHYYHHZAngle(xx,-yy,x2,-y2,JDCurveElements.GetAt(i)->RoateStyle);;
				mText->fonhHeight=m_fontSize[1];
				mText->fontsizeIndex=1;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
		}
		
		if(m_bShow_HZ==TRUE)
		{
		
			pOldPen=dc.SelectObject(&m_PenLCBZ);
			xx=JDCurveElements.GetAt(i)->HZ_xy->x;
			yy=JDCurveElements.GetAt(i)->HZ_xy->y;
			DPtoVP(xx,yy,&x1,&y1);
 
			GetLineXY(x1,y1,&xx1,&yy1,&fwj2,mroatateStyle,JDCurveElements.GetAt(i)->fwj2,mLength,99);		
			dc.MoveTo(x1,y1);
			dc.LineTo(xx1,yy1);
			if(m_bAddCADData==FALSE)
			{
				mypolyline=new PolyLine;
				ppt = new Cordinate; 
				ppt->x=x1;ppt->y=y1;
				mypolyline->PtCAD.Add(ppt);
				ppt = new Cordinate; 
				ppt->x=xx1;ppt->y=yy1;
				mypolyline->PtCAD.Add(ppt);
				mypolyline->lineWidth=m_penWidth[3]-1;
				mypolyline->mcolor=m_penColorIndex[3];
				mypolyline->Index=3;
				ployLineCAD.Add(mypolyline);
				
			}
			angg=GetAngle2(fwj2,mroatateStyle);
			m_FontLCBZ.DeleteObject();
			
			m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
			
			cjcbakf=dc.SelectObject(&m_FontLCBZ);
	
			dc.SetTextAlign(TA_LEFT);
			dc.SetTextColor(m_fontColor[1]);
			tt="HZ "+GetLC(JDCurveElements.GetAt(i)->HZ);
			dc.TextOut(x1,y1,tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1;
				mText->y=-y1;
				mText->angle=GetCADZHHYYHHZAngle(x1,-y1,xx1,-yy1,JDCurveElements.GetAt(i)->RoateStyle);;
				mText->fonhHeight=m_fontSize[1];
				mText->fontsizeIndex=1;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			dc.SelectObject(cjcbakf);
		}
		
		if(m_bShow_CurveElements==TRUE)
		{
			float fangle;
			DPtoVP(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,&x1,&y1);
			dc.Ellipse(x1-3,y1-3,x1+3,y1+3);
			GetZHHZAngle(JDCurveElements.GetAt(i)->ZH_xy->x,JDCurveElements.GetAt(i)->ZH_xy->y,JDCurveElements.GetAt(i)->HZ_xy->x,JDCurveElements.GetAt(i)->HZ_xy->y,&fangle);
			m_FontQX.DeleteObject();
			
			m_FontQX.CreateFont(DLtoVL(m_fontSize[0]*10.0),0,fangle*10,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[0]);
			
			cjcbakf=dc.SelectObject(&m_FontQX);
			dc.SetBkMode(TRANSPARENT);          
			dc.SetTextAlign(TA_CENTER);
			dc.SetTextColor(m_fontColor[0]);
		
		

			int index=0;
			index++;
			tt=JDCurveElements.GetAt(i)->ID; 
			dc.TextOut(x1,y1+index*disH,tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1;
				mText->y=-(y1+index*disH);
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			float tangle;
			if(fangle>90)
			{
				tangle=fangle-270;
			}
			else if(fangle==90)
			{
				if(JDCurveElements.GetAt(i)->RoateStyle==-1)
					tangle=180;
				else
					tangle=0;
			}
			else
				tangle=90+fangle;

			tangle=tangle*PAI/180.0;

			index++;
			tt=RadianToDegree(JDCurveElements.GetAt(i)->Alfa);
			if(JDCurveElements.GetAt(i)->RoateStyle==-1)
				tt="az="+tt;
			else if(JDCurveElements.GetAt(i)->RoateStyle==1)
				tt="ay="+tt;			
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			index++;
			tt.Format("R=%d",JDCurveElements.GetAt(i)->R);
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			index++;
			tt.Format("L0=%d",JDCurveElements.GetAt(i)->L0);
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			index++;
			tt.Format("T=%.3f",JDCurveElements.GetAt(i)->T);
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			index++;
			tt.Format("L=%.3f",JDCurveElements.GetAt(i)->L);
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			index++;
			tt.Format("Ly=%.3f",JDCurveElements.GetAt(i)->Ly);
			dc.TextOut(x1-(index*disH)*cos(tangle),y1+(index*disH)*sin(tangle),tt);
			if(m_bAddCADData==FALSE)
			{
				mText=new CADText;
				mText->x=x1-(index*disH)*cos(tangle);
				mText->y=-(y1+(index*disH)*sin(tangle));
				mText->angle=fangle;
				mText->fonhHeight=m_fontSize[0];
				mText->fontsizeIndex=0;
				mText->strText=tt;
				TextCAD.Add(mText);
			}
			dc.SelectObject(cjcbakf);
		}

			dc.SelectObject(pOldPen);
			pOldPen=dc.SelectObject(&m_PenQX);
			float L0=JDCurveElements.GetAt(i)->L0;
		
			
			//前缓和曲线段			
			mypolyline=new PolyLine;
			float  k=-5;
		
			while(k<L0)
			{
				k+=5;
				if(k>=L0) 
					k=L0;
				myDesingScheme.GetQLXY(L0,	JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
				k,JDCurveElements.GetAt(i-1)->fwj,JDCurveElements.GetAt(i)->ZH_xy->x,\
				JDCurveElements.GetAt(i)->ZH_xy->y,0,0,&x1,&y1,1);
				DPtoVP(x1,y1,&xx,&yy);
				if(k==0)
					dc.MoveTo(xx,yy);
				else
					dc.LineTo(xx,yy);

				if(k==L0)
					dc.Ellipse(xx-15,yy-15,xx+15,yy+15); //x1,y1 为HY点的坐标
				
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
				}
			}

			
			//中间圆曲线段
			float Ly=JDCurveElements.GetAt(i)->Ly;
			k=-5;
		
			while(k<Ly)
			{
				k+=5;
				if(k>Ly)	k=Ly;  //
                 
				myDesingScheme.GetYQXXY(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,\
					JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
					k,JDCurveElements.GetAt(i)->Alfa,JDCurveElements.GetAt(i)->HY_xy->x,\
					JDCurveElements.GetAt(i)->HY_xy->y,\
					JDCurveElements.GetAt(i)->YH_xy->x,JDCurveElements.GetAt(i)->YH_xy->y,\
					&x1,&y1);
				DPtoVP(x1,y1,&xx,&yy);
				if(k==0)//第1个点
					dc.MoveTo(xx,yy);
				else
					dc.LineTo(xx,yy);

				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
				}
			
			}

			
			//后缓和曲线段
			k=L0+5;
			while(k>0 )
			{
				k-=5;
				if(k<=0) 
					k=0;
				myDesingScheme.GetQLXY(L0,	JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
					k,JDCurveElements.GetAt(i)->fwj,0,0,JDCurveElements.GetAt(i)->HZ_xy->x,\
					JDCurveElements.GetAt(i)->HZ_xy->y,&x1,&y1,2);
				DPtoVP(x1,y1,&xx,&yy);
				if(k==0)
					dc.MoveTo(xx,yy);
				else
					dc.LineTo(xx,yy);
				if(k==L0)
					dc.Ellipse(xx-15,yy-15,xx+15,yy+15); //x1,y1 为HY点的坐标
				
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
				}
				
			}
			mypolyline->lineWidth=m_penWidth[1]-1;
			mypolyline->mcolor=m_penColorIndex[1];
			mypolyline->Index=1;
			ployLineCAD.Add(mypolyline);
		
			pOldPen=dc.SelectObject(&m_PenLCBZ);
			dc.SetTextAlign(TA_BOTTOM);
		
			double L,L1,L2;
			float LL;
			L1=JDCurveElements.GetAt(i-1)->HZ;
			L2=JDCurveElements.GetAt(i)->ZH;
			L=CalCulateStartLC(L1);
			
			if(JDCurveElements.GetAt(i)->RoateStyle==-1) 
				dc.SetTextAlign(TA_BOTTOM|TA_CENTER);
			else if(JDCurveElements.GetAt(i)->RoateStyle==1) 
				dc.SetTextAlign(TA_TOP|TA_CENTER);
			dc.SetTextColor(m_fontColor[1]);
		
			//
			while(L<=L2)
			{
						
			
				LL=L-JDCurveElements.GetAt(i)->ZH-JDCurveElements.GetAt(i)->T;
				GetZXDXY(JDCurveElements.GetAt(i)->x,JDCurveElements.GetAt(i)->y,LL,JDCurveElements.GetAt(i-1)->fwj,&x1,&y1);
				DPtoVP(x1,y1,&xx,&yy);
				
				GetLineXY(xx,yy,&xx1,&yy1,&fwj2,JDCurveElements.GetAt(i)->RoateStyle,JDCurveElements.GetAt(i-1)->fwj2,mLength2,L);
				dc.MoveTo(xx,yy);
				dc.LineTo(xx1,yy1);
			
				mypolyline=new PolyLine;
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
					ppt = new Cordinate; 
					ppt->x=xx1;ppt->y=yy1;
					mypolyline->PtCAD.Add(ppt);
					mypolyline->lineWidth=m_penWidth[3]-1;
					mypolyline->mcolor=m_penColorIndex[3];
					mypolyline->Index=3;
					ployLineCAD.Add(mypolyline);
				}
				

				

				
				
				tt=GetBZText(L);
				GetZBAngle(xx,yy,xx1,yy1,JDCurveElements.GetAt(i)->RoateStyle,&angg);
				m_FontLCBZ.DeleteObject();
				
				m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
				
				cjcbakf=dc.SelectObject(&m_FontLCBZ);				
				dc.TextOut(xx1,yy1,tt);
				if(m_bAddCADData==FALSE)
				{
					mText=new CADText;
					mText->x=xx1;
					mText->y=-yy1;
					mText->angle=GetCADBZLCAngle(xx,-yy,xx1,-yy1,JDCurveElements.GetAt(i)->RoateStyle);
					mText->fonhHeight=m_fontSize[1];
					mText->fontsizeIndex=1;
					mText->strText=tt;
					TextCAD.Add(mText);
				}
				L+=100;
			}
			dc.SelectObject(cjcbakf);

			
			L1=JDCurveElements.GetAt(i)->ZH;
			L2=JDCurveElements.GetAt(i)->HY;
			L=CalCulateStartLC(L1);
			while(L<=L2)
			{
				
				
				myDesingScheme.GetQLXY(JDCurveElements.GetAt(i)->L0,JDCurveElements.GetAt(i)->R,\
					JDCurveElements.GetAt(i)->RoateStyle,\
					L-JDCurveElements.GetAt(i)->ZH,JDCurveElements.GetAt(i-1)->fwj,JDCurveElements.GetAt(i)->ZH_xy->x,\
					JDCurveElements.GetAt(i)->ZH_xy->y,0,0,&x1,&y1,1);
				DPtoVP(x1,y1,&xx,&yy);
				GetLineXY(xx,yy,&xx1,&yy1,&fwj2,JDCurveElements.GetAt(i)->RoateStyle,JDCurveElements.GetAt(i-1)->fwj2,mLength2,L);		
				dc.MoveTo(xx,yy);
				dc.LineTo(xx1,yy1);
				mypolyline=new PolyLine;
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
					ppt = new Cordinate; 
					ppt->x=xx1;ppt->y=yy1;
					mypolyline->PtCAD.Add(ppt);
					mypolyline->lineWidth=m_penWidth[3]-1;
					mypolyline->mcolor=m_penColorIndex[3];
					mypolyline->Index=3;
					ployLineCAD.Add(mypolyline);
				}
				
				tt=GetBZText(L);
				GetZBAngle(xx,yy,xx1,yy1,JDCurveElements.GetAt(i)->RoateStyle,&angg);
				m_FontLCBZ.DeleteObject();
				
				
				m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
				
				cjcbakf=dc.SelectObject(&m_FontLCBZ);				
				dc.TextOut(xx1,yy1,tt);
				if(m_bAddCADData==FALSE)
				{
					mText=new CADText;
					mText->x=xx1;
					mText->y=-yy1;
					mText->angle=GetCADBZLCAngle(xx,-yy,xx1,-yy1,JDCurveElements.GetAt(i)->RoateStyle);
					mText->fonhHeight=m_fontSize[1];
					mText->fontsizeIndex=1;
					mText->strText=tt;
					TextCAD.Add(mText);
				}
				L+=100;
			}
			
			L1=JDCurveElements.GetAt(i)->HY;
			L2=JDCurveElements.GetAt(i)->YH;
			L=CalCulateStartLC(L1);
			while(L<=L2)
			{
				
				myDesingScheme.GetYQXXY(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,\
					JDCurveElements.GetAt(i)->R,JDCurveElements.GetAt(i)->RoateStyle,\
					L-JDCurveElements.GetAt(i)->HY,JDCurveElements.GetAt(i)->Alfa,JDCurveElements.GetAt(i)->HY_xy->x,\
					JDCurveElements.GetAt(i)->HY_xy->y,\
					JDCurveElements.GetAt(i)->YH_xy->x,JDCurveElements.GetAt(i)->YH_xy->y,\
					&x1,&y1);
				DPtoVP(x1,y1,&xx,&yy);
				dc.MoveTo(xx,yy); 
				DPtoVP(JDCurveElements.GetAt(i)->Cneterx,JDCurveElements.GetAt(i)->Cnetery,&x1,&y1);
				GetYQXBZ(xx,yy,x1,y1,mLength2,&x2,&y2,L);
				dc.LineTo(x2,y2);
				mypolyline=new PolyLine;
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
					ppt = new Cordinate; 
					ppt->x=x2;ppt->y=y2;
					mypolyline->PtCAD.Add(ppt);
					mypolyline->lineWidth=m_penWidth[3]-1;
					mypolyline->mcolor=m_penColorIndex[3];
					mypolyline->Index=3;
					ployLineCAD.Add(mypolyline);
				}
				
				tt=GetBZText(L);
				GetZBAngle(xx,yy,x1,y1,JDCurveElements.GetAt(i)->RoateStyle,&angg);
				m_FontLCBZ.DeleteObject();
				
				
				m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
				
				cjcbakf=dc.SelectObject(&m_FontLCBZ);				
				dc.TextOut(x2,y2,tt);
				if(m_bAddCADData==FALSE)
				{
					mText=new CADText;
					mText->x=x2;
					mText->y=-y2;
					mText->angle=GetCADBZLCAngle(xx,-yy,x2,-y2,JDCurveElements.GetAt(i)->RoateStyle);;
					mText->fonhHeight=m_fontSize[1];
					mText->fontsizeIndex=1;
					mText->strText=tt;
					TextCAD.Add(mText);
				}
				L+=100;
			}

			
			L1=JDCurveElements.GetAt(i)->YH;
			L2=JDCurveElements.GetAt(i)->HZ;
			L=CalCulateStartLC(L1);
			while(L<=L2)
			{
				
				
				myDesingScheme.GetQLXY(JDCurveElements.GetAt(i)->L0,JDCurveElements.GetAt(i)->R,\
					JDCurveElements.GetAt(i)->RoateStyle,\
					JDCurveElements.GetAt(i)->HZ-L,JDCurveElements.GetAt(i)->fwj,0,\
					0,JDCurveElements.GetAt(i)->HZ_xy->x,\
					JDCurveElements.GetAt(i)->HZ_xy->y,&x1,&y1,2);
				DPtoVP(x1,y1,&xx,&yy);
				dc.MoveTo(xx,yy);
				GetLineXY(xx,yy,&xx1,&yy1,&fwj2,JDCurveElements.GetAt(i)->RoateStyle,JDCurveElements.GetAt(i)->fwj2,mLength2,L);		
				dc.LineTo(xx1,yy1);
				mypolyline=new PolyLine;
				if(m_bAddCADData==FALSE)
				{
					ppt = new Cordinate; 
					ppt->x=xx;ppt->y=yy;
					mypolyline->PtCAD.Add(ppt);
					ppt = new Cordinate; 
					ppt->x=xx1;ppt->y=yy1;
					mypolyline->PtCAD.Add(ppt);
					mypolyline->lineWidth=m_penWidth[3]-1;
					mypolyline->mcolor=m_penColorIndex[3];
					mypolyline->Index=3;
					ployLineCAD.Add(mypolyline);
				}
				tt=GetBZText(L);
				GetZBAngle(xx,yy,xx1,yy1,JDCurveElements.GetAt(i)->RoateStyle,&angg);
				m_FontLCBZ.DeleteObject();
				
				
				m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
				
				cjcbakf=dc.SelectObject(&m_FontLCBZ);				
			
				L+=100;
			}
					
	}
	
	
		x1=JDCurveElements.GetAt(i)->x;
		y1=JDCurveElements.GetAt(i)->y;
		DPtoVP(x1,y1,&xx,&yy);				
		dc.SetTextColor(m_fontColor[2]);	
		m_FontJDBH.DeleteObject();
		
		m_FontJDBH.CreateFont(DLtoVL(m_fontSize[2]*10.0),0,0,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[2]);
		
		cjcbakf=dc.SelectObject(&m_FontJDBH);
	
		tt=JDCurveElements.GetAt(i)->ID;
		dc.TextOut(xx,yy+5,tt);
		if(m_bAddCADData==FALSE)
		{
			mText=new CADText;
			mText->x=xx1;
			mText->y=-(yy+5);
			mText->angle=0;
			mText->fonhHeight=m_fontSize[2];
			mText->fontsizeIndex=2;
			mText->strText=tt;
			TextCAD.Add(mText);
		}
		pOldPen=dc.SelectObject(&m_PenJDLX);
		dc.Ellipse(xx-3,yy-3,xx+3,yy+3);
		if(m_bAddCADData==FALSE)
		{
			mcirlce=new Circle;
			mcirlce->Cneterx=xx;
			mcirlce->Cnetery=-yy;
			mcirlce->R=5;
			mcirlce->mcolor=m_penColorIndex[2];
			CircleCAD.Add(mcirlce);
		}

		double L1=JDCurveElements.GetAt(JDCurveElements.GetSize()-2)->HZ;
		double L2=JDCurveElements.GetAt(JDCurveElements.GetSize()-1)->JDLC;
		long L=CalCulateStartLC(L1);
		
		dc.SetTextColor(m_fontColor[1]);	
		m_FontLCBZ.DeleteObject();
		
		m_FontLCBZ.CreateFont(DLtoVL(m_fontSize[1]*10.0),0,angg,0,0,FALSE,FALSE,FALSE,DEFAULT_CHARSET,OUT_TT_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,FIXED_PITCH,m_fontName[1]);
		
		cjcbakf=dc.SelectObject(&m_FontLCBZ);
		
		while(L<=L2)
		{
		
		double LL=L-JDCurveElements.GetAt(JDCurveElements.GetSize()-1)->HZ+JDCurveElements.GetAt(JDCurveElements.GetSize()-1)->T;
		GetZXDXY(JDCurveElements.GetAt(JDCurveElements.GetSize()-1)->x,JDCurveElements.GetAt(JDCurveElements.GetSize()-1)->y,LL,JDCurveElements.GetAt(JDCurveElements.GetSize()-2)->fwj,&x1,&y1);
		DPtoVP(x1,y1,&xx,&yy);
		dc.MoveTo(xx,yy);
		GetLineXY(xx,yy,&xx1,&yy1,&fwj2,JDCurveElements.GetAt(JDCurveElements.GetSize()-2)->RoateStyle,JDCurveElements.GetAt(JDCurveElements.GetSize()-2)->fwj2,mLength2,L);		
		dc.LineTo(xx1,yy1);
		mypolyline=new PolyLine;
		if(m_bAddCADData==FALSE)
		{
			ppt = new Cordinate; 
			ppt->x=xx;ppt->y=yy;
			mypolyline->PtCAD.Add(ppt);
			ppt = new Cordinate; 
			ppt->x=xx1;ppt->y=yy1;
			mypolyline->PtCAD.Add(ppt);
		}
		mypolyline->lineWidth=m_penWidth[3]-1;
		mypolyline->mcolor=m_penColorIndex[3];
		mypolyline->Index=3;
		ployLineCAD.Add(mypolyline);
		tt=GetBZText(L);
		dc.TextOut(xx1,yy1,tt);
		if(m_bAddCADData==FALSE)
		{
			mText=new CADText;
			mText->x=xx1;
			mText->y=-yy1;
			mText->angle=0;
			mText->fonhHeight=m_fontSize[1];
			mText->fontsizeIndex=1;
			mText->strText=tt;
			TextCAD.Add(mText);
		}
		L+=100;
		}

	dc.SelectObject(pOldPen);
	m_bAddCADData=TRUE;
	
}

void CPlaneGraphic::GetLineXY(double x1, double y1, double *X, double *Y,float *fwj2, int mroatateStyle, float angle,int mLength,long L)
{

	if(L%1000==0)
		mLength=2*mLength;

	if(angle<(PAI/2.0)) 
	{
		*fwj2=PAI/2.0+angle;
		if(mroatateStyle==-1) 
		{
			*X=x1+mLength*cos(*fwj2);
			*Y=y1-mLength*sin(*fwj2);	
		}
		else if(mroatateStyle==1) 
		{
			*X=x1-mLength*cos(*fwj2);
			*Y=y1+mLength*sin(*fwj2);	
		}
	}
	else if(angle<PAI)	
	{
		*fwj2=angle-PAI/2.0;
		if(mroatateStyle==-1) 
		{
			*X=x1-mLength*cos(*fwj2);
			*Y=y1+mLength*sin(*fwj2);	
		}
		else if(mroatateStyle==1) 
		{
			*X=x1+mLength*cos(*fwj2);
			*Y=y1-mLength*sin(*fwj2);	
		}
	}
	else if(angle<(PAI*3/2.0))	
	{
		*fwj2=angle-PAI/2.0;
		if(mroatateStyle==-1) 
		{
			*X=x1-mLength*cos(*fwj2);
			*Y=y1-mLength*sin(*fwj2);	
		}
		else if(mroatateStyle==1) 
		{
			*X=x1+mLength*cos(*fwj2);
			*Y=y1+mLength*sin(*fwj2);	
		}
	}
	else if(angle>(PAI*3/2.0))	
	{
		*fwj2=angle-PAI*3.0/2.0;
		if(mroatateStyle==-1) 
		{
			*X=x1+mLength*cos(*fwj2);
			*Y=y1-mLength*sin(*fwj2);	
		}
		else if(mroatateStyle==1) 
		{
			*X=x1-mLength*cos(*fwj2);
			*Y=y1+mLength*sin(*fwj2);	
		}
	}
}






















void CPlaneGraphic::GetZHHZAngle(double x1, double y1, double x2, double y2, float *fangle)
{
	double dx=x1-x2;
	double dy=y1-y2;
	if(fabs(dx)<=0.000001)
		*fangle=90;
	else
	{
		float fvalue=dy/dx;
		if(dy/dx>0)
			*fangle=atan(dy/dx)*180.0/PAI;
		else
			*fangle=360-fabs(atan(dy/dx))*180.0/PAI;

	}
    
	
	
}

 
CString CPlaneGraphic::RadianToDegree(float RadianAngle)
{
	CString tt;
	if(RadianAngle==0)
	{
		tt.Format("%2d°%2d′%2d″",0,0,0);
		return tt;
	}
	
	float mangle=RadianAngle*180.0/PAI;
	int m_D=int(mangle);
	mangle= mangle-m_D;
	float mangle2=mangle*60;
	int m_F=int(mangle2);
	mangle2=mangle2-m_F;
	int m_M=int(mangle2*60); 
	
	CString tt1,tt2,tt3;
	if(m_D>9)
		tt1.Format("%d°",m_D);
	else
		tt1.Format("0%d°",m_D);
	
	if(m_F>9)
		tt2.Format("%d′",m_F);
	else
		tt2.Format("0%d′",m_F);
	
	if(m_M>9)
		tt3.Format("%d″",m_M);
	else
		tt3.Format("0%d″",m_M);
	
	
	tt=tt1+tt2+tt3;
	return tt;
}


long CPlaneGraphic::CalCulateStartLC(double mstartlc)
{
	
	
	
	
		long mBZLCStart;
 
	int m=(long)(mstartlc/100);
	float lc1=mstartlc-m*100;
	if(lc1==0) 
		mBZLCStart=(long)mstartlc;
	else
	{
		m=(long)(mstartlc/1000);
		lc1=mstartlc-m*1000;	
		int k=(int)(lc1/100)+1;
		mBZLCStart=m*1000+k*100;
	}
	return mBZLCStart;
     
}

 
 
void CPlaneGraphic::GetZXDXY(double JDx, double JDy, float LL, float fwj, double *x1, double *y1)
{
	*x1=JDx+LL*cos(PAI/2.0-fwj);
	*y1=JDy+LL*sin(PAI/2.0-fwj);
}

 
CString CPlaneGraphic::GetBZText(long L)
{
	CString tt;
	if(L%1000==0)
	{
		if(m_bShow_KMZ==TRUE)//是否显示公米标
			tt.Format("K%d",L/1000);
		else
			tt="";
	}
	else
	{
		if(m_bShow_BMZ==TRUE)//是否显示百米标
			tt.Format("%d",(L-L/1000*1000)/100);
		else
			tt="";
	}

	return tt;
}

void CPlaneGraphic::GetYQXBZ(double x1, double y1, double centerx, double centery,float length, double *x2, double *y2,long L)
{
	
	if(L%1000==0)
		length=2*length;
	
	float  dx=centerx-x1;
	float dy=centery-y1;
	if(dx==0)
	{
		if(centery>y1)
		{
			*x2=x1;
			*y2=y1+length;
		}
		else
		{
			*x2=x1;
			*y2=y1-length;
		}
	}
	else
	{
		float k=dy/dx;
		
		
		if(centery>y1)//则*y2必>y1()
		{
			*y2=y1+fabs(k)*length/(sqrt(1+k*k));
			*x2=x1+(*y2-y1)/k;
		}
		else   
		{
			*y2=y1-fabs(k)*length/(sqrt(1+k*k));
			*x2=x1+(*y2-y1)/k;
		}


	
	}
}

 
void CPlaneGraphic::GetZBAngle(double x1,double y1,double x2,double y2,int RoateStyle,float *angle)
{
	float dx=x2-x1;
	float dy=y1-y2;
	if(dx==0)
	{
		*angle=0;
	}
	else
	{
		float k=dy/dx;
		
		if(k>0)
			*angle=(3*PAI/2.0+atan(k))*180.0/PAI;
		else
			*angle=(PAI/2.0-atan(fabs(k)))*180.0/PAI;
	}

	*angle=*angle*10;

		
}


 
void CPlaneGraphic::InitData()
{
	m_bAddCADData=FALSE;
	
	m_Screen=new ScreenStruct[m_MaxScreen];
	
	m_CurrentScreen=0;
	m_TotalScreen=0;
	
	m_xStart=m_yStart=0;
	blc=1.0;
	m_MapMode=1;            
	
	Small_x=Small_y=99999999; 
	Big_x=Big_y=-99999999;
	
	blc=20;  
	GetMaxXY=0;
	m_bLoadData=0;
	PushNumb=0;
	m_moveStyle=2;	

	m_bShow_BMZ=TRUE;
	m_bShow_KMZ=TRUE;
	m_bShow_ZH=TRUE;
	m_bShow_HY=TRUE;
	m_bShow_YH=TRUE;
	m_bShow_HZ=TRUE;
	m_bShow_CurveElements=TRUE; 
	
	
	m_penWidth[0]=2;
	m_penStyle[0]=0;
	m_penColorIndex[0]=1;
	m_penColor[0]=20;

	
	m_penWidth[1]=2;
	m_penStyle[1]=0;
	m_penColorIndex[1]=5;
	m_penColor[1]=150;
	
	
	m_penWidth[2]=1;
	m_penStyle[2]=0;
	m_penColorIndex[2]=155;
	m_penColor[2]=200;
	
	
	m_penWidth[3]=1;
	m_penStyle[3]=0;
	m_penColorIndex[3]=205;
	m_penColor[3]=245;
	

 
 
 
 
	m_PenZX.CreatePen(PS_SOLID,m_penWidth[0],m_penColor[0]);
	m_PenQX.CreatePen(PS_SOLID,m_penWidth[1],m_penColor[1]);
	m_PenJDLX.CreatePen(PS_SOLID,m_penWidth[2],m_penColor[2]);
	m_PenLCBZ.CreatePen(PS_SOLID,m_penWidth[3],m_penColor[3]);
	
	
 
 
 
	
 
 
 
	m_fontSize[0]=10;
	m_fontSize[1]=9;
	m_fontSize[2]=10;

	m_fontName[0]=m_fontName[1]=m_fontName[2]="Times New Roman";
	m_fontColorIndex[0]=205;
	m_fontColor[0]=250;

	m_fontColorIndex[1]=105;
	m_fontColor[1]=100;
	
	m_fontColorIndex[2]=105;
	m_fontColor[2]=40;
	

				

}

 
float CPlaneGraphic::GetAngle2(float fjw2, int mrotateStyle)
{
	float angg;

	if(mrotateStyle==-1) 
		angg=fjw2*180.0/PAI*10.0;
	else if(mrotateStyle==1) 
		angg=(fjw2+PAI)*180.0/PAI*10.0;
	return angg;

}



 

void CPlaneGraphic::OnZoomin() 
{
	m_DrawCurrent=9;	
	this->Invalidate();
	
}

void CPlaneGraphic::OnZoomout() 
{
	m_DrawCurrent=8;	
	this->Invalidate();
}

void CPlaneGraphic::OnDestroy() 
{
	CDialog::OnDestroy();
	
	delete m_Screen;
	
	
}

 

 
int CPlaneGraphic::GetCADBZLCAngle(double x1, double y1, double x2, double y2, int mrotateStyle)
{
	int mCADAngle;

	float dx=x2-x1;
	float dy=y2-y1;
	if(dx==0)
	{
		mCADAngle=0;
		return mCADAngle;
	}
    float ang;
	if(dy/dx>0)
		ang=atan(dy/dx)*180/PAI;
	else
		ang=180-atan(-dy/dx)*180/PAI;
	

	if(mrotateStyle==-1)//左转
	{
		if(ang<90)//锐角
		{
			if(y2>y1)
			
				mCADAngle=-(90-ang);
			else
				mCADAngle=270+ang;
		}
		else
		{
			mCADAngle=ang-90;
		}
	}
	else if(mrotateStyle==1)//右转
	{
		if(y2>y1)
		{
		
			mCADAngle=ang-90;
		}
		else
		{
			mCADAngle=ang-90;
		}
	}

	return mCADAngle;

}

int CPlaneGraphic::GetCADZHHYYHHZAngle(double x1, double y1, double x2, double y2, int mrotateStyle)
{
	int mCADAngle;
	
	float dx=x2-x1;
	float dy=y2-y1;
	if(dx==0)
	{
		mCADAngle=0;
		return mCADAngle;
	}
    float ang;
	if(dy/dx>0)
		ang=atan(dy/dx)*180/PAI;
	else
		ang=180-atan(-dy/dx)*180/PAI;
	
	
	if(mrotateStyle==-1)//左转
	{

		if(y2>y1)
		{
			mCADAngle=ang;
		}

		else
		{
		
				mCADAngle=ang-180;
		
		
		}
	
	}
	else if(mrotateStyle==1)//右转
	{
		
		if(y2>y1)
		{
			mCADAngle=ang;
		}
		
		else
		{
			mCADAngle=ang-180;
		}
		
	}
	return mCADAngle;
	
}

 
void CPlaneGraphic::AddScreen(double StartX, double StartY, float blc)
{
	int i;
	if(m_CurrentScreen==m_MaxScreen)//如果当前屏幕在50屏上，即没有数组空间再存信息
	{
		
		for(i=1;i<m_MaxScreen-1;i++)
			m_Screen[i]=m_Screen[i+1];
	}
	else    
		m_CurrentScreen++;
	
	m_Screen[m_CurrentScreen].sx=StartX;
	m_Screen[m_CurrentScreen].sy=StartY;
	m_Screen[m_CurrentScreen].blc=blc;
	m_TotalScreen++;
}

 




 





BOOL CPlaneGraphic::GetRect(double *m_Xmin, double *m_Ymin, double *m_Xmax, double *m_Ymax)
{
	double mminX,mminY,mmaxX,mmaxY;
	mminX=mminY=9999999999;
	mmaxX=mmaxY=-9999999999;
	double x,y;

	BOOL pb=FALSE;

	for(int i=0;i<ployLineCAD.GetSize();i++)
	{
		for(int j=0;j<ployLineCAD.GetAt(i)->PtCAD.GetSize();j++)
		{
			x=ployLineCAD.GetAt(i)->PtCAD.GetAt(j)->x;
			y=fabs(ployLineCAD.GetAt(i)->PtCAD.GetAt(j)->y);
			if(mminX>x) mminX=x;
			if(mminY>y) mminY=y;
			if(mmaxX<x) mmaxX=x;
			if(mmaxY<y) mmaxY=y;
		}
	}
	
	for(i=0;i<CircleCAD.GetSize();i++)
	{
		x=CircleCAD.GetAt(i)->Cneterx-CircleCAD.GetAt(i)->R;
		y=fabs(CircleCAD.GetAt(i)->Cnetery)-CircleCAD.GetAt(i)->R;
		if(mminX>x) mminX=x;
		if(mminY>y) mminY=y;
		if(mmaxX<x) mmaxX=x;
		if(mmaxY<y) mmaxY=y;
	
		x=CircleCAD.GetAt(i)->Cneterx+CircleCAD.GetAt(i)->R;
		y=fabs(CircleCAD.GetAt(i)->Cnetery)+CircleCAD.GetAt(i)->R;
		if(mminX>x) mminX=x;
		if(mminY>y) mminY=y;
		if(mmaxX<x) mmaxX=x;
		if(mmaxY<y) mmaxY=y;
		
	}
	
	for(i=0;i<TextCAD.GetSize();i++)
	{
		x=TextCAD.GetAt(i)->x-TextCAD.GetAt(i)->strText.GetLength();
		y=fabs(TextCAD.GetAt(i)->y)-TextCAD.GetAt(i)->fonhHeight;
		if(mminX>x) mminX=x;
		if(mminY>y) mminY=y;
		if(mmaxX<x) mmaxX=x;
		if(mmaxY<y) mmaxY=y;
	
		x=fabs(TextCAD.GetAt(i)->x)+TextCAD.GetAt(i)->strText.GetLength();
		y=fabs(TextCAD.GetAt(i)->y)+TextCAD.GetAt(i)->fonhHeight;
		if(mminX>x) mminX=x;
		if(mminY>y) mminY=y;
		if(mmaxX<x) mmaxX=x;
		if(mmaxY<y) mmaxY=y;
	}

	*m_Xmin=mminX*blc0+m_xStart0;
	*m_Xmax=mmaxX*blc0+m_xStart0;
	*m_Ymin=(m_hScreen-mmaxY)*blc0+m_yStart0;
	*m_Ymax=(m_hScreen-mminY)*blc0+m_yStart0;

	return pb;
}

void CPlaneGraphic::OnMenuPlaneFullscreen() 
{
	double minx,miny,maxx,maxy,bl1;
 
	
	
	minx=(float)1E20;miny=(float)1E20;maxx=-(float)1E20;maxy=-(float)1E20;
	SetCapture();
	SetCursor(LoadCursor(NULL,IDC_WAIT));
	BOOL pb=GetRect(&minx,&miny,&maxx,&maxy);
	
    SetCursor(LoadCursor(NULL,IDC_ARROW));
    ReleaseCapture();
 
 
	bl1=(maxx-minx)/(m_wScreen-20);
	blc=(maxy-miny)/(m_hScreen-20);
	if(bl1>blc)blc=bl1;  
						 /*以下确定屏幕左下角的实际坐标，即在左侧和下侧都留了10点阵的区域
	（minx,miny)在屏幕的左下角向右10点阵同时向上10点阵处。*/
	m_xStart=minx-10*blc;
	m_yStart=miny-10*blc;
	
	blc=blc0;
	m_Screen[0].sx=m_xStart;
	m_Screen[0].sy=m_yStart;
	m_Screen[0].blc=blc;
	m_CurrentScreen=0;
	Invalidate();		
}

 
void CPlaneGraphic::OnMenuPlaneExit() 
{
	EndDialog(IDCANCEL);	
}

 


 






















