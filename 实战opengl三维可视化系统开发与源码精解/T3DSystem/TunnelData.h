//{{AFX_INCLUDES()
#include "msflexgrid.h"
//}}AFX_INCLUDES
#if !defined(AFX_TUNNELDATA_H__1756FD1E_BDFF_4201_B800_3530AC842E3B__INCLUDED_)
#define AFX_TUNNELDATA_H__1756FD1E_BDFF_4201_B800_3530AC842E3B__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TunnelData.h : header file
 

 
// CTunnelData dialog

class CTunnelData : public CDialog
{
// Construction
public:
	BOOL m_bChageData;

	CTunnelData(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CTunnelData)
	enum { IDD = IDD_DIALOG_TUNNEL };
	double	m_endLC;
	float	m_length;
	CString	m_name;
	double	m_startLc;
	CMSFlexGrid	m_grid;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTunnelData)
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
	//{{AFX_MSG(CTunnelData)
	virtual BOOL OnInitDialog();
	afx_msg void OnButtonAdd();
	afx_msg void OnButtonExit();
	afx_msg void OnClickMsflexgrid();
	afx_msg void OnButtonEdit();
	afx_msg void OnButtonDelete();
	afx_msg void OnButtonOutput();
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

#endif // !defined(AFX_TUNNELDATA_H__1756FD1E_BDFF_4201_B800_3530AC842E3B__INCLUDED_)
