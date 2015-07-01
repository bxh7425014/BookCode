#if !defined(AFX_ZDMDESIGN_H__53E81F07_373F_4324_97B5_A338059AB699__INCLUDED_)
#define AFX_ZDMDESIGN_H__53E81F07_373F_4324_97B5_A338059AB699__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ZdmDesign.h : header file
 

 
// CZdmDesign dialog
#include "MainToolBar.h"
#include "PlaneGraphic.h"

enum {GRAPH_BRIDGE,GRAPH_TUNNEL};


#define m_MaxScreen 1000

static UINT indicator_ZDM[] =
{
		ID_SEPARATOR,
		ID_INDICATOR_MESSAGE 
		
};

typedef struct      
{  
	PLineCurve zdmJd;
	float slopeLength;
	float slopeDegree;
	float slopeDifference;
}ZdmCordiPoint, *PZdmCordiPoint;    

typedef struct     
{
	float L;                       
	float pd;                      
	double Lc;                     
	float H;                        
	float R ;                       
	float E ;                     
	float T ;                       
	float pddsc;					
	double ZHLc;
	float ZH_H;
	double HZLc;
	float HZ_H;
	int curveStyle;

}ZdmSlope, *PZdmSlope;    


typedef struct     
{
	double Lc;                     
	float H;                        
	float Dm_H ;                     
	float TW ;                       
	CString style;					
	double JDLC;					
}ZdmDesignPts, *PZdmDesignPts;    


/* 3                     4
　┃  隧道长度=465.653　┃
  ┣━━━━━━━━━━┫
  ┃　　　　　　　　　　┃
  1                      2

*/
typedef struct     
{
	CString name;                     
	double startLC;                     
	double endLC ;                     
	float length ;                      
	double centerLC;
	float  centerH;
	float startH_Sjx;
	float endH_Sjx;
	float startH_Dmx;
	float endH_Dmx;
}TunnelBridge, *PTunnelBridge;  



class CZdmDesign : public CDialog
{
// Construction
public:
	BOOL m_bBZOption[7];
	CString m_bBZOptionText[7];
							/*;
	m_bBZOption[0]:连续里程
	m_bBZOption[1]:线路平面
	m_bBZOption[2]:百米标与加标
	m_bBZOption[3]:地面高程
	m_bBZOption[4]:设计坡度
	m_bBZOption[5]:路肩设计高程
	m_bBZOption[6]:工程地质特征
	*/
	int m_ItemYvaluet[7];
	int m_ItemHeight[7];
	
	LOGFONT pLogFont,pLogFont2; //字体
	
	int m_BeiginLeftX;
	int m_BeiginLeftY;
	int m_BeiginRightX;
	int m_BeiginRightY;
	int m_drwaReginHeight;
	int m_drwaReginWidth;
	int m_lmwidth;

	int m_eachx; //
	int m_eachy;
	float m_scaleX,m_oldscaleX;
	float m_scaleY;
	int m_KeduSpace;
	int m_Magnify;
	int minY,maxY;//
	int oldminY,oldmaxY;
	BOOL m_bHaveGetminMAxY;
	float m_minAltitude,m_maxAltitude;
	CArray<Point,Point> m_dmpoints;
	CArray<Point,Point> m_Ljpoints;

	CArray<PTunnelBridge,PTunnelBridge> m_Tunnelpoints;
	CArray<PTunnelBridge,PTunnelBridge> m_Bridgepoints;


	int m_KeduNums;

	long mR;

	float m_ZdmStartH;
	double  m_ZdmStartLC;
	double  m_ZdmEndLC;
	double m_oldZdmstartLC,m_oldZdmEndLC;

	long m_totalZDmJD;

	CZdmDesign(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CZdmDesign)
	enum { IDD = IDD_DIALOG_ZDMDESIGN };
	CStatic	m_STAICLCINfo;
	CString	m_lcCorInfo;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CZdmDesign)
	public:
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;
	HCURSOR m_hAddPtCursor;

	CPaintDC *ZdmDc;
	CalCulateF mCalF;
	int m_drawStyle;

	CArray<PZdmSlope,PZdmSlope> ZDmJDCurveElements;
	CArray<PZdmDesignPts,PZdmDesignPts> ZDmDesignPtsElements;
	
	// Generated message map functions
	//{{AFX_MSG(CZdmDesign)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg void OnZdmGeofeature();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnMenuZdmExit();
	afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnZdmDesignAddpt();
	afx_msg void OnZdmMenuSave();
	afx_msg void OnMenuZdmSlopedata();
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
	afx_msg void OnMenuZdmJdlppcpd();
	afx_msg void OnZdmStartlcH();
	afx_msg void OnZdmTunnel();
	afx_msg void OnZdmBridge();
	afx_msg void OnZdmGraphtunnel();
	afx_msg void OnZdmGraphbridge();
	afx_msg void OnCancelMode();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void writeDesignData(BYTE graphDesignType,CString name,CString style,double startLc,double endLc);
	void ModifyJDStyle();
	void Insert3DlineZX();
	void ReLoadNewDisginData();
	void ModifyPlanDesignDataID(BOOL bModifyAttribute);
	void ModifyPlanDesignData_Tunnel(int mTunnelBridge);
	void ModifyPlanDesignData();
	void LoadTunnelData();
	void DrawTunnel(CPaintDC &dc);
	void LoadBridgeData();
	void DrawBridge(CPaintDC &dc);
	BOOL CHeckDataValid(double SlopeL,float SlopePd,float SlopePddsc);
	void LoadLJGC();
	void DrawLJGC(CPaintDC &dc);
	void GetCenteXY(double x1,double y1,double x2,double y2,double R,double *centerx,double *centery);
	void GetCurveJiaMiD(double x1, float y1, double x2, float y2,double R,int CurveStyle,double jdLC);
	float GetDmxGC(double lc);
	float GetSjxGC(double lc);
	void SaveDesignPts();
	void DrawPD(CPaintDC &dc);
	void DrawPlan(CPaintDC &dc);
	void GetScreenY(double y,  int *screeny);
	void GetYFromLC(double LC,int *y);
	void DrawDesignSlope(CPaintDC &dc);
	void LoadZdmDesignData();
	void LoadDMXGC();
	void GetScreenX(double x,  int *screenx);
	void DrawGEOfeature(CPaintDC &dc);
	void CreateFonts();
	void DrawZDM(CPaintDC &dc);
	void DrawLXLC(CPaintDC &dc);
	void DrawBMBJB(CPaintDC &dc);
	void GetScreenXY(double x,double y,int *screenx,int *screeny);
	void DrawDMX_DMGC(CPaintDC &dc);
	void DrawBKgrid(CPaintDC &dc);
	void CreateToolbar();
	void GetMin_MaxAltitude();
	void OnBtnSPin();
	void AddSchemeName();
	void InitData();
	

	BYTE graph_DesignType;
	int m_SelectPtNum;
	double m_startLC,m_EndLC;

	_RecordsetPtr m_pRecordset; 
	HRESULT hr;

	float m_maxSlope;
	float m_minSlopeLength;
	float m_maxSlopePddsc;
	
	int m_H;
	int m_W;
	

	
	int		m_GridSpace;
	BOOL	m_drawGrid;
	int m_GridlineWidth;
	int m_GridlineStyle;
	COLORREF m_GridlineColor;
	
	CFont *f,*f2,*f3; 
	BOOL b_HaveCreateFont;

	
	BOOL m_bHaveSetHRScrool;
	SCROLLINFO   sfiV,sfiH; 
	float m_sfiHPermeter;
	float m_sfiVPermeter;


	CBrush   m_brush;  
 
	long m_penDesignLine_width,m_penDesignLine_color;
	long m_penDesignCurve_width,m_penDesignCurve_color;
	long m_penDesignDmx_width,m_penDesignDmx_color;
	long m_penTunnel_width,m_penTunnel_color;
	long m_penBridge_width,m_penBridge_color;
	
	CPen m_penDesignDesignLine;
	CPen m_penDesignDesignCurve;
	CPen m_penDesignDmx;
	CPen m_penTunnel;
	CPen m_penBridge;
	
	
	CArray<PCordinate,PCordinate> PtS;
	CArray<PLineCurve,PLineCurve> JDCurveElements;
	CArray<PPolyLine,PPolyLine> ployLineCAD;
	CArray<PCircle,PCircle> CircleCAD;
	CArray<PCADText,PCADText> TextCAD;
	



	CMainToolBar    m_wndToolBar;
 
	

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

#endif // !defined(AFX_ZDMDESIGN_H__53E81F07_373F_4324_97B5_A338059AB699__INCLUDED_)





















