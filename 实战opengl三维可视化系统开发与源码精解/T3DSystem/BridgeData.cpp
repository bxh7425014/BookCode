// BridgeData.cpp : implementation file
 

#include "stdafx.h"
#include "T3DSystem.h"
#include "BridgeData.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CBridgeData dialog


CBridgeData::CBridgeData(CWnd* pParent /*=NULL*/)
	: CDialog(CBridgeData::IDD, pParent)
{
	//{{AFX_DATA_INIT(CBridgeData)
	m_endLC = 0.0;
	m_length = 0.0;
	m_name = _T("");
	m_startLc = 0.0;
	//}}AFX_DATA_INIT
}


void CBridgeData::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CBridgeData)
	DDX_Text(pDX, IDC_EDIT_ENDLC, m_endLC);
	DDX_Text(pDX, IDC_EDIT_LENGTH, m_length);
	DDX_Text(pDX, IDC_EDIT_NAME, m_name);
	DDX_Text(pDX, IDC_EDIT_STARTLC, m_startLc);
	DDX_Control(pDX, IDC_MSFLEXGRID, m_grid);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CBridgeData, CDialog)
	//{{AFX_MSG_MAP(CBridgeData)
	ON_BN_CLICKED(IDC_BUTTON_ADD, OnButtonAdd)
	ON_BN_CLICKED(IDC_BUTTON_EXIT, OnButtonExit)
	ON_BN_CLICKED(IDC_BUTTON_EDIT, OnButtonEdit)
	ON_BN_CLICKED(IDC_BUTTON_DELETE, OnButtonDelete)
	ON_BN_CLICKED(IDC_BUTTON_OUTPUT, OnButtonOutput)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CBridgeData message handlers


BOOL CBridgeData::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	
	m_pRecordset.CreateInstance(_uuidof(Recordset));
	
	m_bChageData=FALSE;
	InitGrid();
	LoadData();
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}
void CBridgeData::InitGrid()
{
	m_grid.SetCols(5);
	m_grid.SetColWidth(0,400);
	m_grid.SetColWidth(1,1000);
	m_grid.SetColWidth(2,1300);
	m_grid.SetColWidth(3,1300);
	m_grid.SetColWidth(4,1200);
	
	m_grid.SetTextMatrix(0,0,"序号");
	m_grid.SetTextMatrix(0,1,"桥梁名称");
	m_grid.SetTextMatrix(0,2,"起点里程");
	m_grid.SetTextMatrix(0,3,"终点里程");
	m_grid.SetTextMatrix(0,4,"桥梁长度");
	
}

void CBridgeData::LoadData()
{
	
 
 
	
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
	
	m_totalNums=0;
	while (!m_pRecordset->adoEOF)
	{
		m_totalNums++;
		m_grid.SetRows(m_totalNums+1);
		tt.Format("%d",m_totalNums);
		m_grid.SetTextMatrix(m_totalNums,0,tt);
		
		Thevalue = m_pRecordset->GetCollect("桥梁名称"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			m_name=Thevalue.bstrVal;
			m_grid.SetTextMatrix(m_totalNums,1,m_name);
		}
		
		
		Thevalue = m_pRecordset->GetCollect("桥梁起点里程"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalNums,2,tt);
		
		Thevalue = m_pRecordset->GetCollect("桥梁终点里程"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalNums,3,tt);
		
		Thevalue = m_pRecordset->GetCollect("桥梁长度"); 
		tt.Format("%.3f",(double)Thevalue);
		m_grid.SetTextMatrix(m_totalNums,4,tt);
		
		
		m_pRecordset->MoveNext();
	}
	m_grid.SetRows(m_totalNums+1);
	
	m_pRecordset->Close();
}

void CBridgeData::OnButtonAdd() 
{
	
	this->UpdateData();
	if(CheckValid(TRUE)==FALSE)
		return;

	m_length=m_endLC-m_startLc;
	this->UpdateData(FALSE);

	CString tt;

	tt.Format("INSERT INTO Bridge VALUES(\
		  '%s',%.3f,%.3f,%.3f,'%s')",\
		  m_name,
		  m_startLc,
		  m_endLC,
		  m_length,
		  myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename
		  );
	try
	{
		hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		if(!SUCCEEDED(hr))
		{
			AfxMessageBox("增加数据!",MB_ICONINFORMATION);
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

	LoadData();

	
	MessageBox("增加数据成功","增加数据",MB_ICONINFORMATION);
	m_bChageData=TRUE;
	
		
}

BOOL CBridgeData::CheckValid(BOOL ADDorEDit)
{
	this->UpdateData();

	if(m_startLc>=m_endLC)
	{
		MessageBox("起点里程不能<=终点里程","数据检查",MB_ICONSTOP);
		return FALSE;
	}
	
	if(m_name.IsEmpty())
	{
		MessageBox("名称不能为空","数据检查",MB_ICONSTOP);
		return FALSE;
		
	}
	
	CString tt;
	
	if(ADDorEDit==TRUE) 
		tt.Format("SELECT *  FROM  Bridge WHERE 方案名称='%s' AND \
		桥梁名称='%s'",myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,m_name);
	else
		tt.Format("SELECT *  FROM  Bridge WHERE 方案名称='%s' AND \
		  桥梁名称='%s' AND 桥梁名称!='%s'" ,myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename,m_name,m_oldname);

	try
	{
		m_pRecordset->Open((_bstr_t)tt,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);    
	}
	
	catch(_com_error& e)	
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return FALSE;
	} 
	
	if (!m_pRecordset->adoEOF)
	{
		tt.Format("桥梁名称[%s]已经存在!",m_name);
		MessageBox(tt,"增加数据",MB_ICONSTOP);
		m_pRecordset->Close();
		return FALSE;
	}
	m_pRecordset->Close();

	 return TRUE;

}

void CBridgeData::OnButtonExit() 
{
	EndDialog(IDOK);	
}


void CBridgeData::OnClickMsflexgrid() 
{

	if(m_totalNums<=0) 
		return;
	
	int m_curRow=m_grid.GetRow();
	if(m_curRow<=0) 
		return;
	

	m_name=m_grid.GetTextMatrix(m_curRow,1);
	m_startLc=atof(m_grid.GetTextMatrix(m_curRow,2));
	m_endLC=atof(m_grid.GetTextMatrix(m_curRow,3));
	m_length=atof(m_grid.GetTextMatrix(m_curRow,4));

	m_oldname=m_name;
	
	this->UpdateData(FALSE);
	
	
}

void CBridgeData::OnButtonEdit() 
{

	this->UpdateData();

	int m_curRow=m_grid.GetRow();
	if(m_curRow<=0) 
		return;

	if(CheckValid(FALSE)==FALSE)
		return;
	
	

	m_length=m_endLC-m_startLc;
	this->UpdateData(FALSE);
	
	CString tt;
	

	tt.Format("DELETE FROM  Bridge WHERE 桥梁名称='%s' AND 方案名称='%s'",\
		m_oldname,myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);

	hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
	
	tt.Format("INSERT INTO Bridge VALUES(\
		'%s',%.3f,%.3f,%.3f,'%s')",\
		m_name,
		m_startLc,
		m_endLC,
		m_length,
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename
		);
	
	hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
	if(!SUCCEEDED(hr))
	{
		AfxMessageBox("增加数据!",MB_ICONINFORMATION);
		return;
	}

	
	LoadData();
	
	MessageBox("修改数据成功","修改数据",MB_ICONINFORMATION);
	m_bChageData=TRUE;
	
}

void CBridgeData::OnButtonDelete() 
{
	int m_curRow=m_grid.GetRow();
	if(m_curRow<=0) 
		return;
	

	m_length=m_endLC-m_startLc;

	this->UpdateData(FALSE);
	
	CString tt;
	
	tt.Format("DELETE FROM  Bridge WHERE 桥梁名称='%s' AND 方案名称='%s'",\
		m_oldname,myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	try
	{
		hr=theApp.m_pConnection->Execute((_bstr_t)tt,&RecordsAffected,adCmdText); 
		if(!SUCCEEDED(hr))
		{
			AfxMessageBox("删除数据!",MB_ICONINFORMATION);
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

	LoadData();

	MessageBox("删除数据成功","删除数据",MB_ICONINFORMATION);
	m_bChageData=TRUE;
	
}

void CBridgeData::OnButtonOutput() 
{
	WriteToExcel();
	
}

void CBridgeData::WriteToExcel()
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
			
			
		sSql = "CREATE TABLE "+sTableFile+" (桥梁名称 TEXT,桥梁起点里程  NUMBER,\
			桥梁终点里程 NUMBER,桥梁长度 NUMBER)";
		
		database.ExecuteSQL(sSql);
			
			for(int i=0;i<m_grid.GetRows ()-1;i++)
			{
			sSql.Format("INSERT INTO "+sTableFile+"	VALUES('%s',%.3f,%.3f,%.3f)",\
				m_grid.GetTextMatrix (i+1,1),\
				atof(m_grid.GetTextMatrix (i+1,2)),\
				atof(m_grid.GetTextMatrix (i+1,3)),\
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

BEGIN_EVENTSINK_MAP(CBridgeData, CDialog)
    //{{AFX_EVENTSINK_MAP(CBridgeData)
	ON_EVENT(CBridgeData, IDC_MSFLEXGRID, -600 /* Click */, OnClickMsflexgrid, VTS_NONE)
	//}}AFX_EVENTSINK_MAP
END_EVENTSINK_MAP()
