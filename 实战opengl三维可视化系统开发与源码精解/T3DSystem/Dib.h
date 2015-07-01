// Dib.h: interface for the CDib class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_DIB_H__6C294E63_FE8B_11D0_9B6B_444553540000__INCLUDED_)
#define AFX_DIB_H__6C294E63_FE8B_11D0_9B6B_444553540000__INCLUDED_

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

class CDib : public CObject  
{
public:
	BYTE* GetDibBitsPtr();
	LPBITMAPINFO GetDIbInfoPtr();
	LPBITMAPINFOHEADER GetDibInfoHeaderPtr();
	UINT GetDibHeight();
	UINT GetDibWidth();
	DWORD GetDibSizeImage();
	CDib();
	CDib(const char* fileName);
	virtual ~CDib();
protected:

	LPBITMAPFILEHEADER	m_pBmFileHeader;
	LPBITMAPINFO		m_pBmInfo;
	LPBITMAPINFOHEADER	m_pBmInfoHeader;
	RGBQUAD*			m_pRGBTable;
	BYTE*				m_pDibBits;
	UINT				m_numColors;

protected:
	void LoadBitmapFile(const char* fileName);
	

};

#endif // !defined(AFX_DIB_H__6C294E63_FE8B_11D0_9B6B_444553540000__INCLUDED_)
