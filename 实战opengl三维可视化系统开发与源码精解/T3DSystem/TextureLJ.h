#if !defined(AFX_TEXTURELJ_H__843831CE_A248_4726_83B1_ED92CEF38C3E__INCLUDED_)
#define AFX_TEXTURELJ_H__843831CE_A248_4726_83B1_ED92CEF38C3E__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureLJ.h : header file
 

 
// CTextureLJ dialog

class CTextureLJ : public CDialog
{
// Construction
public:
	CString m_LJtextureName;
	CTextureLJ(CWnd* pParent = NULL);   

// Dialog Data
	//{{AFX_DATA(CTextureLJ)
	enum { IDD = IDD_DIALOG_TEXTURE_LJ };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureLJ)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTextureLJ)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitTexture();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTURELJ_H__843831CE_A248_4726_83B1_ED92CEF38C3E__INCLUDED_)
