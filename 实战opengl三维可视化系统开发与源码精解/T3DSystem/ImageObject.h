 

#ifndef __IMAGEOBJECT_H__
#define __IMAGEOBJECT_H__

#include "stdafx.h"
#include "Errors.h"
#include "ImageLoad.h"

#define LPTODP( a, b, c ) { SIZE Size; Size.cx = b; Size.cy = c; a->LPtoDP( &Size ); b = Size.cx; c = Size.cy; }

#define POPULARITY_PALETTE 0
#define MEDIAN_CUT_PALETTE 1
#define FIXED_PALETTE 2

#define MASK_ANDTHEMASK 1
#define MASK_ORTHEIMAGE 2

enum{ BLIT_REPLACE, BLIT_MASK, BLIT_BLEND, BLIT_SHADOW };

#define GETRGB555( a, b, c, d ) { WORD *wData = (WORD *) d; a = (unsigned char) ( ( (*wData) & 0x7c00 ) >> 7 ); b = (unsigned char) ( ( (*wData) & 0x03e0 ) >> 2 ); c = (unsigned char) ( ( (*wData) & 0x001f ) << 3 ); }
#define GETRGB565( a, b, c, d ) { WORD *wData = (WORD *) d; a = (unsigned char) ( ( (*wData) & 0xf800 ) >> 8 ); b = (unsigned char) ( ( (*wData) & 0x07e0 ) >> 3 ); c = (unsigned char) ( ( (*wData) & 0x001f ) << 3 ); }
#define GETRGB888( a, b, c, d ) { DWORD *dwData = (DWORD *) d; a = (unsigned char) ( (*dwData) >> 16 ); b = (unsigned char) ( ( (*dwData) & 0x0000ff00 ) >> 8 ); c = (unsigned char) ( (*dwData) & 0x000000ff ); }
#define PUTRGB555( a, b, c, d ) { WORD *wData = (WORD *) d; *wData = ( ( ( (WORD) a & 0x00f8 ) << 7 ) | ( ( (WORD) b & 0x00f8 ) << 2 ) | ( (WORD) c >> 3 ) ); }
#define PUTRGB565( a, b, c, d ) { WORD *wData = (WORD *) d; *wData = ( ( ( (WORD) a & 0x00f8 ) << 8 ) | ( ( (WORD) b & 0x00fc ) << 3 ) | ( (WORD) c >> 3 ) ); }
#define PUTRGB888( a, b, c, d ) { DWORD *dwData = (DWORD *) d; *dwData = ( (DWORD) a << 16 ) | ( (DWORD) b << 8 ) | (DWORD) c; }

#define _RGB(r,g,b) (WORD)(((b)&~7)<<7)|(((g)&~7)<<2)|((r)>>3)

class CImageObject
{

public:
	CImageObject();
	CImageObject( CDC *, int, int, int, int );
	CImageObject( const char *, CDC *pDC = NULL, int nX = -1, int nY = -1 );
	CImageObject( HBITMAP, CDC *pDC = NULL );
	CImageObject( int, int, int, int, COLORREF = RGB( 255, 255, 255 ) );
	CImageObject( HGLOBAL );
	CImageObject( HGLOBAL, int, int, int, int, RGBQUAD *pPalette = NULL, int nNumColors = 0 );
	~CImageObject();

	BOOL Load( const char *, CDC *pDC = NULL, int nX = -1, int nY = -1 );
	int GetLastError( void );
	BOOL Save( const char *, int nType = -1 );
	BOOL Save( void );
	int GetWidth( void );
	int GetHeight( void );
	int GetNumBits( void );
	int GetNumPlanes( void );
	int GetNumColors( void );
	BOOL GetPaletteData( RGBQUAD * );
	RGBQUAD *GetPaletteData( void );
	int GetImageType( const char * );
	int GetImageType( void );
	BOOL GetImageInfo( const char *, int *pnWidth = NULL,
		int *pnHeight = NULL, int *pnPlanes = NULL,
		int *pnBitsPerPixel = NULL, int *pnNumColors = NULL );
	BOOL Draw( CDC *, int x = -60000, int y = -60000, int nWidth = -1, int nHeight = -1, int nSrcX = 0, int nSrcY = 0 );
	BOOL SetPalette( CDC * );
	void SetPaletteCreationType( int );
	int GetPaletteCreationType( void );

	BOOL IsLoaded( void );
	int ExtensionIndex( const char * );

	BOOL Crop( int, int, int, int );
	BOOL Stretch( int, int );
	BOOL Rotate( int );
	BOOL Invert( void );
	BOOL Reverse( void );
	BOOL RotateIt( int );
	void Rotate90( unsigned char *, unsigned char * );
	void Rotate270( unsigned char *, unsigned char * );
	BOOL ChangeFormat( int );
	BOOL MapToPalette( RGBQUAD *, int );
	BOOL MapToBrowserPalette( void );

	BOOL Blit( CImageObject *, int, int, int nFlag = BLIT_REPLACE, int nOption = -1, int nSrcX = 0, int nSrcY = 0, int nWidth = -1, int nHeight = -1 );

	void GetFilename( char * );

	int GetPaletteBytes( void );
	HGLOBAL GetDib( void );
	CPalette *GetPalette( void );

	void operator= (const CImageObject &ImageObject);
	void NormalizeCoordinates( int *, int *, int *, int *, BOOL *bCompleteImage = NULL, BOOL *bLessThanHalf = NULL );
	void *GetDIBPointer( int *nWidthBytes = NULL, int nNewBits = 0, int *nNewWidthBytes = NULL, int nNewWidth = -1 );
	LOGPALETTE *GetLogPal( void );
	LOGPALETTE *CreateLogPalette( RGBQUAD *Palette, int NumColors );
	int GetNearestIndex( unsigned char, unsigned char, unsigned char, RGBQUAD *, int );

	void SetQuality( int nQual ){ m_nQuality = nQual; }
	void SetTIFCompression( int nCompression ){ m_nTIFCompression = nCompression; }

	void CreateMask( int, int, int, int );
	unsigned char *m_pucMask;
	unsigned char *m_pMaskImage;
	int m_nMaskEntryCount;
	int m_nMaskSize;
	void DeleteMask( void );
	void MaskImage( void );
	BOOL InMaskArray( unsigned char, unsigned char, unsigned char );
	void AddExclusion( int, int, int, int );
	void SetMaskOperation( int );
	void PerformSmartExclusion( int nKernelWidth = 10, int nKernelHeight = 10 );

	static char *szExtensions[7];
	int m_nLastError;
	int m_nScreenPlanes, m_nScreenBits, m_nPaletteBytes;
	void ProcessPalette( void );
	int WidthBytes( int, int );
	void SetDib( HGLOBAL hDib ){ m_hDib = hDib; }
	void SetLogPal( LOGPALETTE *pLogPal ){ m_pLogPal = pLogPal; }

	void AttachPalette( CImageObject * );

	int GetPixelIndex( int, int );
	int AssignPixelIndex( int, int );
	COLORREF GetPixel( int, int );

	char *m_pcRGB;
	int *m_pnIndex;

	int m_nTransparentIndex;

	HGLOBAL DetachDib( void );

	void SetPixel( int, int, COLORREF );

	int m_nX, m_nY;

protected:
	void InitVars( BOOL bFlag = FALSE );
	void ProcessImageHeader( void );
	void KillImage( void );
	void CreatePaletteFromDIB( RGBQUAD *, int );
	LOGPALETTE *CreatePaletteFromBitmap( int, unsigned char *, int, int, int );

	void CreateFromBitmap( HBITMAP, CDC * );

	RGBQUAD *MakePopularityPalette( int, unsigned char *, int, int, int );
	RGBQUAD *MakeMedianCutPalette( int, unsigned char *, int, int, int );
	RGBQUAD *MakeFixedPalette( int );

	int m_nWidth, m_nHeight, m_nPlanes, m_nBits, m_nColors, m_nImageType;
	int m_nQuality;
	int m_nTIFCompression;
	int m_nPaletteCreationType;
	CPalette m_Palette;
	HGLOBAL m_hDib;
	char *m_pszFilename;
	LOGPALETTE *m_pLogPal;

	int m_nMaskOperation;

	void ImprintTrialVersion( void );

};

#endif

