#if !defined(AFX_NEWSCHEME_H__928EFF23_94FC_428E_85A8_4A6EFA7FEC37__INCLUDED_)
#define AFX_NEWSCHEME_H__928EFF23_94FC_428E_85A8_4A6EFA7FEC37__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// NewScheme.h : header file
 

 
// CNewScheme dialog

class CNewScheme : public CDialog
{
// Construction
public:
	CNewScheme(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CNewScheme)
	enum { IDD = IDD_DIALOG_SCHEME };
	CComboBox	m_CBbsStyle;
	CComboBox	m_CBlocomotiveStyle;
	CComboBox	m_CBdfxefficientLength;
	CComboBox	m_CBterrainStyle;
	CComboBox	m_CBengineeringCondition;
	CComboBox	m_CBdesignSpeed;
	CComboBox	m_CBschemeName;
	CComboBox	m_CBrailwayGrade;
	CComboBox	m_CBdraughtStyle;
	long	m_minRadius;
	float	m_restrictSlope;
	int		m_criterionMinR;
	double	m_startLC;
	float	m_maxSlopePd;
	float	m_maxSlopePddsc;
	float	m_minSLopePc;
	int		m_criterionminSlopePc;
	float	m_criterionmaxSlopePD;
	float	m_criterionmaxSlopePddsc;
	double	m_endLC;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CNewScheme)
	public:
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	virtual BOOL DestroyWindow();
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CNewScheme)
	virtual BOOL OnInitDialog();
	afx_msg void OnButtonSave();
	afx_msg void OnSelchangeComboSchemename();
	afx_msg void OnButtonEdit();
	afx_msg void OnButtonExit();
	afx_msg void OnSelchangeComboGrade();
	afx_msg void OnSelchangeComboDesignspeed();
	afx_msg void OnSelchangeComboEngineeringcondition();
	afx_msg void OnButtonDelete();
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnSelchangeComboDraughtstyle();
	afx_msg void OnSelchangeComboDfxefficientlength();
	afx_msg void OnSelchangeComboTerrainstyle();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void DeleteSchemeTableData();
	void GetMaxSlopePdRules();
	void GetMaxPddscRules();
	void Add_Edit_Data(BOOL bAddorEdit);
	int GetMinRfromCriterion();
	void LoadData(CString strschemeName);
	void LoadSchemeNmae(CString mstrschemename);
	BOOL CheckData(BOOL bAdd);
	
// 	CToolTipCtrl m_tooltip;

	CString m_schemename,m_oldschemename;
	
	_RecordsetPtr   m_pRecordset;
	_variant_t Thevalue;  
	variant_t RecordsAffected;
	HRESULT hr;

// 	CMyEditClass m_EditClass[3];
	
	void InitData();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_NEWSCHEME_H__928EFF23_94FC_428E_85A8_4A6EFA7FEC37__INCLUDED_)
