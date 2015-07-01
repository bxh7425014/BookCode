#if !defined(AFX_TEXTUREQLHPM_H__F4A767CF_C6E2_428F_B262_7BCCD7C04580__INCLUDED_)
#define AFX_TEXTUREQLHPM_H__F4A767CF_C6E2_428F_B262_7BCCD7C04580__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TextureQLHpm.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CTextureQLHpm dialog

class CTextureQLHpm : public CDialog
{
// Construction
public:
	CString m_HQMtextureName;
	CTextureQLHpm(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CTextureQLHpm)
	enum { IDD = IDD_DIALOG_TEXTURE_BRIDGEHPM };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CTextureQLHpm)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CTextureQLHpm)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeList();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	void InitTexture();
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TEXTUREQLHPM_H__F4A767CF_C6E2_428F_B262_7BCCD7C04580__INCLUDED_)
