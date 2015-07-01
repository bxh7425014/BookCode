#if !defined(AFX_TEXTURETUNNELDM_H__C19A1F81_27AF_4ABD_920F_EF0097EF3E72__INCLUDED_)
#define AFX_TEXTURETUNNELDM_H__C19A1F81_27AF_4ABD_920F_EF0097EF3E72__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureTunnelDm.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CTextureTunnelDm dialog

class CTextureTunnelDm : public CDialog
{
// Construction
public:
	CString m_TunnelDmtextureName;
	int m_TextureIndex;
	CTextureTunnelDm(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CTextureTunnelDm)
	enum { IDD = IDD_DIALOG_TEXTURE_TUNNELDM };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureTunnelDm)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTextureTunnelDm)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitTexture();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTURETUNNELDM_H__C19A1F81_27AF_4ABD_920F_EF0097EF3E72__INCLUDED_)
