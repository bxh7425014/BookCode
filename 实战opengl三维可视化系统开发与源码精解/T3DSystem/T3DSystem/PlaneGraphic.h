// PlaneGraphic.h : header file
 
#if !defined(AFX_PLANEGRAPHIC_H__5811701A_B39B_4E41_9BA4_0A46B97E3D27__INCLUDED_)
#define AFX_PLANEGRAPHIC_H__5811701A_B39B_4E41_9BA4_0A46B97E3D27__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#define m_MaxScreen 1000

static UINT indicator_PLANE[] =
{
	ID_SEPARATOR,
	ID_INDICATOR_MESSAGE 
		
};
 
// CPlaneGraphic dialog

typedef struct	
{
	float blc;	
	float sx;	
	float sy;	
}ScreenStruct;	

typedef struct  
{
	double Cneterx;
	double Cnetery;
	double R;
	int mcolor;
}Circle, *PCircle;

typedef struct  
{
	double x;
	double y;
	float fonhHeight;
	float angle;
	int mcolor;
	CString strText;
	int fontsizeIndex;
	
}CADText, *PCADText;

typedef struct  
{
	CArray<PCordinate,PCordinate> PtCAD;
	float lineWidth;
	int mcolor;
	int Index;
	
}PolyLine, *PPolyLine;


class CPlaneGraphic : public CDialog
{
// Construction
public:
	CPlaneGraphic(CWnd* pParent = NULL);	

// Dialog Data
	//{{AFX_DATA(CPlaneGraphic)
	enum { IDD = IDD_DIALOG_PLANE_GRAPH };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CPlaneGraphic)
	public:
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;
	CBrush   m_brush;  
	CArray<PCordinate,PCordinate> PtS;
	CArray<PLineCurve,PLineCurve> JDCurveElements;
	CArray<PPolyLine,PPolyLine> ployLineCAD;
	CArray<PCircle,PCircle> CircleCAD;
	CArray<PCADText,PCADText> TextCAD;
	
	// Generated message map functions
	//{{AFX_MSG(CPlaneGraphic)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnGraphMove2();
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnZoomwindow();
	afx_msg void OnZoomin();
	afx_msg void OnZoomout();
	afx_msg void OnDestroy();
	afx_msg void OnMenuPlaneFullscreen();
	afx_msg void OnMenuPlaneExit();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	BOOL GetRect(double *m_Xmin, double *m_Ymin, double *m_Xmax, double *m_Ymax);
	void AddScreen(double StartX,double StartY,float blc);
	int GetCADZHHYYHHZAngle(double x1, double y1, double x2, double y2, int mrotateStyle);
	int GetCADBZLCAngle(double x1,double y1,double x2,double y2,int mrotateStyle);
	float GetAngle2(float fjw2,int mrotateStyle);
	void InitData();
	void GetZBAngle(double x1,double y1,double x2,double y2,int RoateStyle,float *angle);
	void GetYQXBZ(double x1,double y1,double centerx,double centery,float length,double *x2,double *y2,long L);
	CString GetBZText(long L);
	void GetZXDXY(double JDx,double JDy,float LL,float fwj,double *x1,double *y1);
	long CalCulateStartLC(double mstartlc);
	CString RadianToDegree(float RadianAngle);
	void GetZHHZAngle(double x1,double y1,double x2,double y2,float *fangle);
	void GetLineXY(double x1, double y1, double *X, double *Y,float *fwj2, int mroatateStyle, float angle,int mLength,long L);
	void DrawPlan();
	int GetCurveL0(int mR);
	CString GetLC(double LC);
	void CreateStatusBar();
	void DrawBKgrid(/*CDC *pDC*/);
	void GetMinMax_XY();
	void Get_Min_Max_XY(double x, double y);
	void DPtoVP(double x, double y, double *X, double *Y);
	int DLtoVL(float L);
	float VLtoDL(int L);
	void VPtoDP(int x, int y, double *X, double *Y);
	
// 	CMainToolBar    m_wndToolBar;
	CStatusBar m_dlgwndStatusBar;

	double m_startLC;
	double m_EndLC;
	long m_BZLCStart ;
	
	ScreenStruct* m_Screen;		
	int m_CurrentScreen;		
	int m_TotalScreen;		
	
	double m_xStart,m_yStart;
	double m_xStart0,m_yStart0;

	double moldminX,moldminY;
	double moldmaxX,moldmaxY;
	
	float blc,blc0;
	int m_wScreen,m_hScreen;
	int m_MapMode;				
	
	
	
	double Small_x, Small_y,Big_x,Big_y;  
	int GetMaxXY;
	int m_bLoadData;
   
	CPoint mPointOrign,mPointOrign1,mPointOld;
	CPoint mCurrentPoint;  
	CPoint StartPoint;  
	int m_DrawCurrent;			
	int PushNumb;				
	
	int m_moveStyle;	
	
	BOOL	m_bShow_BMZ;
	BOOL	m_bShow_KMZ;
	BOOL	m_bShow_ZH;
	BOOL	m_bShow_HY;
	BOOL	m_bShow_YH;
	BOOL	m_bShow_HZ;
	BOOL	m_bShow_CurveElements;

	int m_penWidth[4];
	int m_penStyle[4];
	COLORREF m_penColor[4];
	int	m_penColorIndex[4];	
	
	CPen m_PenZX;
	CPen m_PenQX;
	CPen m_PenJDLX;
	CPen m_PenLCBZ;
	
	int m_fontSize[3];
	CString m_fontName[3];
	COLORREF m_fontColor[3];
	int	m_fontColorIndex[3];
	
	CFont m_FontQX; 
	CFont m_FontLCBZ;
	CFont m_FontJDBH;
	
	BOOL m_bAddCADData;

	CString m_Schemename,m_oldSchemename;
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_PLANEGRAPHIC_H__5811701A_B39B_4E41_9BA4_0A46B97E3D27__INCLUDED_)
