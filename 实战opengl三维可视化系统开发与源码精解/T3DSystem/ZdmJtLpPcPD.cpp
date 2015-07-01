// ZdmJtLpPcPD.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "ZdmJtLpPcPD.h"
#include "ZdmDesign.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CZdmJtLpPcPD dialog


CZdmJtLpPcPD::CZdmJtLpPcPD(CWnd* pParent /*=NULL*/)
	: CDialog(CZdmJtLpPcPD::IDD, pParent)
{
	//{{AFX_DATA_INIT(CZdmJtLpPcPD)
	m_H = 0.0f;
	m_L = 0.0f;
	m_LC = 0.0;
	m_pd = 0.0f;
	//}}AFX_DATA_INIT
}


void CZdmJtLpPcPD::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CZdmJtLpPcPD)
	DDX_Control(pDX, IDC_MSFLEXGRID, m_grid);
	DDX_Text(pDX, IDC_EDIT_H, m_H);
	DDX_Text(pDX, IDC_EDIT_L, m_L);
	DDX_Text(pDX, IDC_EDIT_LC, m_LC);
	DDX_Text(pDX, IDC_EDIT_PD, m_pd);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CZdmJtLpPcPD, CDialog)
	//{{AFX_MSG_MAP(CZdmJtLpPcPD)
	ON_BN_CLICKED(IDC_BUTTON_EDIT, OnButtonEdit)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_BN_CLICKED(IDC_BUTTON_EXIT, OnButtonExit)
	ON_BN_CLICKED(IDC_BUTTON_OUTPUT, OnButtonOutput)
	ON_BN_CLICKED(IDC_BUTTON_SAVE, OnButtonSave)
	ON_BN_CLICKED(IDC_RADIO_PCPD, OnRadioPcpd)
	ON_BN_CLICKED(IDC_RADIO_ZHBG, OnRadioZhbg)
	ON_BN_CLICKED(IDC_BUTTON_CHECK, OnButtonCheck)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CZdmJtLpPcPD message handlers

BEGIN_EVENTSINK_MAP(CZdmJtLpPcPD, CDialog)
    //{{AFX_EVENTSINK_MAP(CZdmJtLpPcPD)
	ON_EVENT(CZdmJtLpPcPD, IDC_MSFLEXGRID, -600 /* Click */, OnClickMsflexgrid, VTS_NONE)
	//}}AFX_EVENTSINK_MAP
END_EVENTSINK_MAP()

void CZdmJtLpPcPD::OnClickMsflexgrid() 
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
	
	this->UpdateData(FALSE);
	
}

void CZdmJtLpPcPD::InitGrid()
{
	m_grid.SetCols(6);
	m_grid.SetColWidth(0,400);
	m_grid.SetColWidth(1,1000);
	m_grid.SetColWidth(2,1000);
	m_grid.SetColWidth(3,1600);
	m_grid.SetColWidth(4,1300);
	m_grid.SetColWidth(5,1500);
	
	m_grid.SetTextMatrix(0,0,"序号");
	m_grid.SetTextMatrix(0,1,"坡长");
	m_grid.SetTextMatrix(0,2,"坡率");
	m_grid.SetTextMatrix(0,3,"变坡点桩号");
	m_grid.SetTextMatrix(0,4,"设计标高");
	m_grid.SetTextMatrix(0,5,"坡度代数差");
	
	
}


 
void CZdmJtLpPcPD::LoadZdmDesignData()
{
 
 
	
	CString tt;
	
	
	m_pRecordset.CreateInstance(_uuidof(Recordset));
	
	tt.Format("SELECT *  FROM  ZDmJDCurve WHERE 方案名称='%s' ORDER BY 变坡点桩号",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
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
	
		Thevalue = m_pRecordset->GetCollect("坡度代数差"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalZDmJD,5,tt);
		
		
		m_pRecordset->MoveNext();
	}
	m_grid.SetRows(m_totalZDmJD+1);
	
	m_pRecordset->Close();
}

BOOL CZdmJtLpPcPD::OnInitDialog() 
{
	CDialog::OnInitDialog();

	CButton *pbutton=(CButton*)GetDlgItem(IDC_RADIO_PCPD);
	pbutton->SetCheck(TRUE);

	m_bEditData=FALSE;
	m_editStyle=1;
	InitGrid();
	LoadZdmDesignData();
	 

	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CZdmJtLpPcPD::OnButtonEdit() 
{
	this->UpdateData();
	
	int m_curRow=m_grid.GetRow();
	if(m_curRow<0) 
		return;
	
	
	

	m_bEditData=TRUE;
	
 
	
	double m_PreLc,m_preH;

	if(m_curRow==1)
	{
		m_PreLc=this->m_ZdmStartLC;
		m_preH=this->m_ZdmStartH;
	}
	else
	{
		m_PreLc=atof(m_grid.GetTextMatrix(m_curRow-1,3));
		m_preH=atof(m_grid.GetTextMatrix(m_curRow-1,4));
		
	}
	switch(m_editStyle)
	{
	case 1:
		
		m_LC=m_PreLc+m_L;
		m_H=m_preH+m_pd/1000*m_L;
		break;
	case 2:
		m_L=m_LC-m_PreLc;
		m_pd=(m_H-m_preH)/m_L*1000;
		break;
	
		
	}

	this->UpdateData(FALSE);
	CString tt;

	tt.Format("%.3f",m_L);
	m_grid.SetTextMatrix(m_curRow,1,tt);
	
	tt.Format("%.3f",m_pd);
	m_grid.SetTextMatrix(m_curRow,2,tt);
	
	tt.Format("%.3f",m_LC);
	m_grid.SetTextMatrix(m_curRow,3,tt);
	
	
	tt.Format("%.3f",m_H);
	m_grid.SetTextMatrix(m_curRow,4,tt);

	float m_prePd;
	if(m_curRow>1 && m_curRow<m_totalZDmJD)
	{
		m_prePd=atof(m_grid.GetTextMatrix(m_curRow-1,2));
		tt.Format("%.3f",m_prePd-m_pd);
		m_grid.SetTextMatrix(m_curRow-1,5,tt);
		m_prePd=atof(m_grid.GetTextMatrix(m_curRow+1,2));
		tt.Format("%.3f",m_pd-m_prePd);
		m_grid.SetTextMatrix(m_curRow,5,tt);
		
		
	}

	
}

void CZdmJtLpPcPD::OnButtonDelete() 
{
	CString  sql;
	double lc;
	long mEndRow=m_grid.GetRowSel();
	
	if(m_totalZDmJD<=0) return;
	int m_curRow=m_grid.GetRow();
	if(m_curRow<=0) 	
	{
		MessageBox("请选择要删除的记录!","删除数据",MB_ICONINFORMATION);
		return;
	}

	m_bEditData=TRUE;

	for(long i=m_curRow;i<=mEndRow;i++)
	{
		
		lc=atof(m_grid.GetTextMatrix(i,3));
		
		sql.Format("DELETE from ZDmJDCurve WHERE 变坡点桩号=%.3f AND 方案名称='%s'",lc,myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	
		try
		{
			hr=theApp.m_pConnection->Execute((_bstr_t)sql,&RecordsAffected,adCmdText); 
		}
		
		catch(_com_error& e)	
		{
			CString errormessage;
			errormessage.Format("错误信息:%s",e.ErrorMessage());
			MessageBox(errormessage,"保存实例数据到数据库",MB_ICONSTOP);
			return;
		} 	
 
		sql.Format("DELETE FROM T3DLineZxCorrdinateZDM WHERE 方案名称='%s' AND 交点里程=%.3f",\
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,lc);
		
		hr=theApp.m_pConnection->Execute((_bstr_t)sql,&RecordsAffected,adCmdText); 
		
		

	}

	for(i=mEndRow;i<m_totalZDmJD;i++)
	{
		for(int j=0;j<=4;j++)
		{
			m_grid.SetTextMatrix(i,j,m_grid.GetTextMatrix(i+1,j));
		}
	}
	m_totalZDmJD=m_totalZDmJD-(mEndRow-m_curRow+1);
	m_grid.SetRows(m_totalZDmJD+1);

	ReCalCurve();
	
 
	MessageBox("删除成功","删除数据",MB_ICONINFORMATION);
	
}



void CZdmJtLpPcPD::OnButtonExit() 
{
	EndDialog(IDOK);
	
}


 
void CZdmJtLpPcPD::ReCalCurve()
{
	double Lc1,Lc2;
	float h1,h2;
	float Pc,Pd;
	CString tt;

	for(int i=1;i<m_grid.GetRows();i++)
	{
		if(i==1)
		{
			Lc1=m_ZdmStartLC;
			h1=m_ZdmStartH;
		}
		else
		{
			Lc1=atof(m_grid.GetTextMatrix(i-1,3));
			h1=atof(m_grid.GetTextMatrix(i-1,4));
		}

		Lc2=atof(m_grid.GetTextMatrix(i,3));
		h2=atof(m_grid.GetTextMatrix(i,4));
		
		Pc=Lc2-Lc1;
		if(Pc<0)
			Sleep(0);

	
		Pd=(h2-h1)/Pc*1000;
		tt.Format("%.3f",Pc);
		m_grid.SetTextMatrix(i,1,tt);
		tt.Format("%.3f",Pd);
		m_grid.SetTextMatrix(i,2,tt);

	}
	
}

void CZdmJtLpPcPD::SaveData()
{
	CString tt;


	double ZH_H,ZH_LC,HZ_H,HZ_LC;

	tt.Format("DELETE FROM ZDmJDCurve WHERE 方案名称='%s'",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	
	hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
	double R=10000;
	float E,pddsc;
	float h1,h2,h0,h3,L,T;

	int curveStyle;
	m_totalZDmJD=m_grid.GetRows()-1;
	for(long i=1;i<=m_totalZDmJD;i++)
	{
		m_L=atof(m_grid.GetTextMatrix(i,1));
		m_pd=atof(m_grid.GetTextMatrix(i,2));
		m_LC=atof(m_grid.GetTextMatrix(i,3));
		m_H=atof(m_grid.GetTextMatrix(i,4));
		
		if(/*i==1 || */i==m_totalZDmJD)
		{
			curveStyle=0;
			pddsc=0.0;
			T=E=0.0;
			ZH_LC=m_LC;	
			ZH_H=m_H;	
			HZ_LC=m_LC;	
			HZ_H=m_H;	
		}
		else if(i<m_totalZDmJD)
		{	
			
			pddsc=m_pd-atof(m_grid.GetTextMatrix(i+1,2));
			
			
			if(pddsc>0) 	
				curveStyle=1;
			else  if(pddsc<0)
				curveStyle=-1;
			else
				curveStyle=0;

			pddsc=fabs(pddsc);
			T=(R/2000.0)*pddsc;
			E=T*(T/(2*R));
			
			
			if(i==1)
			{
			
				h1=m_ZdmStartH;
			}
			else
				h1=atof(m_grid.GetTextMatrix(i-1,4));

			h2=atof(m_grid.GetTextMatrix(i,4));
			L=atof(m_grid.GetTextMatrix(i,1));
			h0=h1-(h1-h2)*(L-T)/L;
			ZH_H=h0;
			ZH_LC=m_LC-T;
			  
			h3=atof(m_grid.GetTextMatrix(i+1,4));
			L=atof(m_grid.GetTextMatrix(i+1,1));
		
			h0=h2-T/L*(h2-h3);
			 HZ_H=h0;
			 HZ_LC=m_LC+T;
			
		}
	
		tt.Format("INSERT INTO ZDmJDCurve VALUES(\
			'%s',%.3f,%.3f,%.3f,%.3f,\
			%.3f,%.3f,%.3f,%.3f,%d,%d,%.3f,%.3f,%.3f,%.3f)",\
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,
			m_L,
			m_pd,
			m_LC,
			m_H,
			R,
			E,
			T,
			pddsc,
			i,								
			curveStyle, 
			ZH_LC,	
			ZH_H,	
			HZ_LC,	
			HZ_H	
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
			MessageBox(errormessage,"保存数据",MB_ICONSTOP);
			return;
		} 
		
	}
	MessageBox("保存完成!","保存数据",MB_ICONINFORMATION);
	
}

 
void CZdmJtLpPcPD::OnButtonOutput() 
{
	WriteToExcel();
}

void CZdmJtLpPcPD::WriteToExcel()
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
				变坡点桩号 NUMBER,设计标高 NUMBER)";
			database.ExecuteSQL(sSql);
			
			for(int i=0;i<m_grid.GetRows ()-1;i++)
			{
				
			sSql.Format("INSERT INTO "+sTableFile+"	VALUES(%.3f,%.3f,%.3f,%.3f)",atof(m_grid.GetTextMatrix (i+1,1)),\
				atof(m_grid.GetTextMatrix (i+1,2)),atof(m_grid.GetTextMatrix (i+1,3)),\
				atof(m_grid.GetTextMatrix (i+1,4)));
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

void CZdmJtLpPcPD::OnButtonSave() 
{
	ReCalCurve();
	SaveData();
	
	
}

 
void CZdmJtLpPcPD::OnRadioPcpd() 
{
	m_editStyle=1;
	
	GetDlgItem(IDC_EDIT_L)->EnableWindow(TRUE);
	GetDlgItem(IDC_EDIT_PD)->EnableWindow(TRUE);
	GetDlgItem(IDC_EDIT_LC)->EnableWindow(FALSE);
	GetDlgItem(IDC_EDIT_H)->EnableWindow(FALSE);
}

 
void CZdmJtLpPcPD::OnRadioZhbg() 
{
	m_editStyle=2;	

	GetDlgItem(IDC_EDIT_L)->EnableWindow(FALSE);
	GetDlgItem(IDC_EDIT_PD)->EnableWindow(FALSE);
	GetDlgItem(IDC_EDIT_LC)->EnableWindow(TRUE);
	GetDlgItem(IDC_EDIT_H)->EnableWindow(TRUE);
	
}

 
void CZdmJtLpPcPD::OnButtonCheck() 
{

	CString tt;

	if(m_totalZDmJD<=0) 
		return;
	float m_pddsc;

	int *mIRow;
	mIRow=new int[m_totalZDmJD+1];
	for(int i=1;i<=m_totalZDmJD;i++)
	{
		mIRow[i]=0;	
	}

	for(i=1;i<=m_totalZDmJD;i++)
	{
		m_L=atof(m_grid.GetTextMatrix(i,1));
		m_pd=fabs(atof(m_grid.GetTextMatrix(i,2)));
		m_LC=atof(m_grid.GetTextMatrix(i,3));
		m_H=atof(m_grid.GetTextMatrix(i,4));
		m_pddsc=atof(m_grid.GetTextMatrix(i,5));
		if(m_L<m_minSlopeLength)
		{
			tt.Format("第%d行坡长[%.3f]小于最小坡长【%.3f】",i,m_L,m_minSlopeLength);
			MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
			mIRow[i]=1;
		}
		if(m_pd>m_maxSlope)
		{
			tt.Format("第%d行坡度[%.3f]大于最大坡度【%.3f】",i,m_pd,m_maxSlope);
			MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
			mIRow[i]=1;
		}
		if(m_pddsc>m_maxSlopePddsc)
		{
			tt.Format("第%d行坡度差[%.3f]大于最大坡度差【%.3f】",i,m_pddsc,m_maxSlopePddsc);
			MessageBox(tt,"坡度合法性检查数据",MB_ICONSTOP);
			mIRow[i]=1;
		}	
	}
	
	for(i=1;i<=m_totalZDmJD;i++)
	{
		m_grid.SetRow(i);
		if(mIRow[i]==1)
		{
			for(int j=1;j<m_grid.GetCols();j++)
			{
				m_grid.SetCol(j);
				m_grid.SetCellBackColor(RGB(255,0,0));
			}
		}
		else
		{
			for(int j=1;j<m_grid.GetCols();j++)
			{
				m_grid.SetCol(j);
				m_grid.SetCellBackColor(RGB(255,255,255));
			}
		}	
	}

}
