#if !defined(AFX_DEMLOAD_H__D85A05F1_541B_4567_8F13_CB6933127398__INCLUDED_)
#define AFX_DEMLOAD_H__D85A05F1_541B_4567_8F13_CB6933127398__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// DemLoad.h : header file
 

 
// CDemLoad dialog

class CDemLoad : public CDialog
{
// Construction
public:
	CDemLoad(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CDemLoad)
	enum { IDD = IDD_DIALOG_DEMSEPERATE };
	CComboBox	m_CombolblockSize;
	CComboBox	m_subCombolblockSize;
	CProgressCtrl	m_progress;
	CString	m_DemFileName;
	CString	m_strblockinfo;
	CString	m_strsubblockinfo;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CDemLoad)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CDemLoad)
	afx_msg void OnButtonBrowse();
	afx_msg void OnButtonSeperate();
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeComboSubblocksize();
	//}}AFX_MSG
private:
	void WriteTotalDemToBlob(CString  strfile);
	float HH(int mRows,int mCols);
	void ReadDemDataFromFiles(CString strfiles, int Index);
	int GetBlcokSize(int currentSel);
	void SeperateDem(CString strfilename, int mBlockSize);
	void Init_BlockSize();
	void AddDemBlockDataToDB(int RowIndex, int ColIndex, CString strfilenaem, long ID);
		
	float *m_pHeight;//存储DEM的所有点的高程
	float **m_subBlockHeight;//存储每一子块的高程
	int m_subBlockSize;//DEM子块大小
	CString tempDemDirctory;//存储DEM分块文件的临时文件夹

	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_DEMLOAD_H__D85A05F1_541B_4567_8F13_CB6933127398__INCLUDED_)










































