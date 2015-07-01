// T3DSystemDoc.h : interface of the CT3DSystemDoc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_T3DSYSTEMDOC_H__A71069EF_3581_4C98_BE91_175CB993342C__INCLUDED_)
#define AFX_T3DSYSTEMDOC_H__A71069EF_3581_4C98_BE91_175CB993342C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CT3DSystemDoc : public CDocument
{
protected: // create from serialization only
	CT3DSystemDoc();
	DECLARE_DYNCREATE(CT3DSystemDoc)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CT3DSystemDoc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CT3DSystemDoc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CT3DSystemDoc)
	afx_msg void OnMenuDem();
	afx_msg void OnMenuTextureimage();
	afx_msg void OnMenuNewproject();
	afx_msg void OnMenuLinescheme();
	afx_msg void OnMenuPlaneGraph();
	afx_msg void OnMenuZdmdesign();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_T3DSYSTEMDOC_H__A71069EF_3581_4C98_BE91_175CB993342C__INCLUDED_)
