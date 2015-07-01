//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_BRIDGEDATA_H__C8D062AB_BE34_457A_81BD_B60A555607C6__INCLUDED_)
#define AFX_BRIDGEDATA_H__C8D062AB_BE34_457A_81BD_B60A555607C6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// BridgeData.h : header file
 

 
// CBridgeData dialog

class CBridgeData : public CDialog
{
// Construction
public:
	BOOL m_bChageData;
	CBridgeData(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CBridgeData)
	enum { IDD = IDD_DIALOG_BRIDGE };
	double	m_endLC;
	double	m_length;
	CString	m_name;
	double	m_startLc;
	CMSFlexGrid	m_grid;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CBridgeData)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:
	_variant_t  Thevalue;  
	_RecordsetPtr   m_pRecordset;
	variant_t RecordsAffected;
	HRESULT hr;
	
	int m_totalNums;
	CString	m_oldname;
	
	// Generated message map functions
	//{{AFX_MSG(CBridgeData)
	afx_msg void OnButtonAdd();
	afx_msg void OnButtonExit();
	afx_msg void OnClickMsflexgrid();
	afx_msg void OnButtonEdit();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonOutput();
	virtual BOOL OnInitDialog();
	DECLARE_EVENTSINK_MAP()
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void WriteToExcel();
	BOOL CheckValid(BOOL ADDorEDit);
	void LoadData();
	void InitGrid();
	
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_BRIDGEDATA_H__C8D062AB_BE34_457A_81BD_B60A555607C6__INCLUDED_)
