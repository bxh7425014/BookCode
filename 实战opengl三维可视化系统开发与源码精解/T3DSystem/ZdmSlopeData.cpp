// ZdmSlopeData.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "ZdmSlopeData.h"
#include "ZdmDesign.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CZdmSlopeData dialog


CZdmSlopeData::CZdmSlopeData(CWnd* pParent /*=NULL*/)
	: CDialog(CZdmSlopeData::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZdmSlopeData)
	m_E = 0.0f;
	m_H = 0.0f;
	m_L = 0.0f;
	m_LC = 0.0;
	m_pd = 0.0f;
	m_R = 0;
	m_T = 0.0f;
	m_pddsc = 0.0f;
	//}}AFX_DATA_INIT
}


void CZdmSlopeData::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZdmSlopeData)
	DDX_Control(pDX, IDC_MSFLEXGRID, m_grid);
	DDX_Text(pDX, IDC_EDIT_E, m_E);
	DDX_Text(pDX, IDC_EDIT_H, m_H);
	DDX_Text(pDX, IDC_EDIT_L, m_L);
	DDX_Text(pDX, IDC_EDIT_LC, m_LC);
	DDX_Text(pDX, IDC_EDIT_PD, m_pd);
	DDX_Text(pDX, IDC_EDIT_R, m_R);
	DDX_Text(pDX, IDC_EDIT_T, m_T);
	DDX_Text(pDX, IDC_EDIT_PDDSC, m_pddsc);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CZdmSlopeData, CDialog)
	//{{AFX_MSG_MAP(CZdmSlopeData)
	ON_BN_CLICKED(IDC_BUTTON_EXIT, OnButtonExit)
	ON_BN_CLICKED(IDC_BUTTON_OUTPUT, OnButtonOutput)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CZdmSlopeData message handlers

BEGIN_EVENTSINK_MAP(CZdmSlopeData, CDialog)
    //{{AFX_EVENTSINK_MAP(CZdmSlopeData)
	ON_EVENT(CZdmSlopeData, IDC_MSFLEXGRID, -600 /* Click */, OnClickMsflexgrid, VTS_NONE)
	//}}AFX_EVENTSINK_MAP
END_EVENTSINK_MAP()

void CZdmSlopeData::OnClickMsflexgrid() 
{

	if(m_totalZDmJD<=0) 
		return;

	int m_curRow=m_grid.GetRow();
	if(m_curRow<=0) 
		return;

 
 
 
 
	
	m_L=atof(m_grid.GetTextMatrix(m_curRow,1));
	m_pd=atof(m_grid.GetTextMatrix(m_curRow,2));
	m_LC=atof(m_grid.GetTextMatrix(m_curRow,3));
	m_H=atof(m_grid.GetTextMatrix(m_curRow,4));
	m_R=atof(m_grid.GetTextMatrix(m_curRow,5));
	m_E=atof(m_grid.GetTextMatrix(m_curRow,6));
	m_T=atof(m_grid.GetTextMatrix(m_curRow,7));
	m_pddsc=atof(m_grid.GetTextMatrix(m_curRow,8));
	
	this->UpdateData(FALSE);
	

		
}

BOOL CZdmSlopeData::OnInitDialog() 
{
	CDialog::OnInitDialog();

	InitGrid();
	
	LoadZdmDesignData();
	
		
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CZdmSlopeData::InitGrid()
{
	m_grid.SetCols(9);
	m_grid.SetColWidth(0,400);
	m_grid.SetColWidth(1,1000);
	m_grid.SetColWidth(2,1000);
	m_grid.SetColWidth(3,1600);
	m_grid.SetColWidth(4,1300);
	m_grid.SetColWidth(5,1500);
	m_grid.SetColWidth(6,1100);
	m_grid.SetColWidth(7,1100);
	m_grid.SetColWidth(8,1800);
	
	m_grid.SetTextMatrix(0,0,"序号");
	m_grid.SetTextMatrix(0,1,"坡长");
	m_grid.SetTextMatrix(0,2,"坡率");
	m_grid.SetTextMatrix(0,3,"变坡点桩号");
	m_grid.SetTextMatrix(0,4,"设计标高");
	m_grid.SetTextMatrix(0,5,"竖曲线半经");
	m_grid.SetTextMatrix(0,6,"外矢距");
	m_grid.SetTextMatrix(0,7,"切线长");
	m_grid.SetTextMatrix(0,8,"坡度代数差");
	
	
}

 
void CZdmSlopeData::LoadZdmDesignData()
{
 
 
 

	CString tt;

	
	m_pRecordset.CreateInstance(_uuidof(Recordset));
	
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
	
	
	m_totalZDmJD=0;
	while (!m_pRecordset->adoEOF)
	{
		m_totalZDmJD++;
		m_grid.SetRows(m_totalZDmJD+1);
		tt.Format("%d",m_totalZDmJD);
		m_grid.SetTextMatrix(m_totalZDmJD,0,tt);

		Thevalue = m_pRecordset->GetCollect("序号"); 
		int ID=(long)Thevalue;
	
		
		Thevalue = m_pRecordset->GetCollect("坡长"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,1,tt);

		Thevalue = m_pRecordset->GetCollect("坡率"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,2,tt);
		
		Thevalue = m_pRecordset->GetCollect("变坡点桩号"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,3,tt);
		
		
		Thevalue = m_pRecordset->GetCollect("标高"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,4,tt);
		
		Thevalue = m_pRecordset->GetCollect("竖曲线半径"); 
		tt.Format("%.f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,5,tt);
		
		Thevalue = m_pRecordset->GetCollect("外矢距"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,6,tt);
		
		Thevalue = m_pRecordset->GetCollect("切线长"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,7,tt);
		
		Thevalue = m_pRecordset->GetCollect("坡度代数差"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,8,tt);
		
		m_pRecordset->MoveNext();
	}
	m_grid.SetRows(m_totalZDmJD+1);

	m_pRecordset->Close();
}



void CZdmSlopeData::OnButtonExit() 
{
	EndDialog(IDOK);
	
}



 
void CZdmSlopeData::OnButtonOutput() 
{
	WriteToExcel();
}

void CZdmSlopeData::WriteToExcel()
{
	
	CString sExcelFile,sTableFile,sSql,sDriver;
	CDatabase database;
	
	sDriver = "MICROSOFT EXCEL DRIVER (*.XLS)"; 
	CFileDialog dlg(FALSE,"Excel文件",NULL,OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,"Excel文件(*.xls)|*.xls||",NULL);
	dlg.m_ofn.lpstrTitle="输出到Excel文件";
	
	if(dlg.DoModal()==IDCANCEL)		return;
	
	sExcelFile=dlg.GetPathName();
	
	
	
	DeleteFile(sExcelFile);
	sTableFile=dlg.GetFileTitle();	
	
	try
	{
		
		sSql.Format("DRIVER={%s};DSN='';FIRSTROWHASNAMES=1;READONLY=FALSE;CREATE_DB=\"%s\";DBQ=%s",sDriver, sExcelFile, sExcelFile);
		
		
		if( database.OpenEx(sSql,CDatabase::noOdbcDialog) )
		{
			
			
			sSql = "CREATE TABLE "+sTableFile+" (坡长 NUMBER,坡率  NUMBER,\
				变坡点桩号 NUMBER,设计标高 NUMBER,竖曲线半经 NUMBER,\
				外矢距 NUMBER,切线长 NUMBER,坡度代数差 NUMBER)";
			database.ExecuteSQL(sSql);
			
			for(int i=0;i<m_grid.GetRows ()-1;i++)
			{
				
			sSql.Format("INSERT INTO "+sTableFile+"	VALUES(%.3f,%.3f,%.3f,%.3f,\
				%.3f,%.3f,%.2f,%.3f)",atof(m_grid.GetTextMatrix (i+1,1)),\
				atof(m_grid.GetTextMatrix (i+1,2)),atof(m_grid.GetTextMatrix (i+1,3)),\
				atof(m_grid.GetTextMatrix (i+1,4)),atof(m_grid.GetTextMatrix (i+1,5)),\
				atof(m_grid.GetTextMatrix (i+1,6)),atof(m_grid.GetTextMatrix (i+1,7)),\
				atof(m_grid.GetTextMatrix (i+1,8)));
			database.ExecuteSQL(sSql);
			}
		} 
		
		database.Close();		
		this->MessageBox("Excel文件 "+sExcelFile+" 已输出成功!","输出到Excel文件",MB_ICONINFORMATION);
	}
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("错误信息:%s",e.ErrorMessage());
		AfxMessageBox(errormessage);
	} 
}
