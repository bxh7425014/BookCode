 

#ifndef __IMAGELOAD_H__
#define __IMAGELOAD_H__

#include <windows.h>

#ifndef POPULARITY_PALETTE
#define POPULARITY_PALETTE 0
#define MEDIAN_CUT_PALETTE 1
#define FIXED_PALETTE 2
#endif

#define GETRGB555( a, b, c, d ) { WORD *wData = (WORD *) d; a = (unsigned char) ( ( (*wData) & 0x7c00 ) >> 7 ); b = (unsigned char) ( ( (*wData) & 0x03e0 ) >> 2 ); c = (unsigned char) ( ( (*wData) & 0x001f ) << 3 ); }
#define GETRGB565( a, b, c, d ) { WORD *wData = (WORD *) d; a = (unsigned char) ( ( (*wData) & 0xf800 ) >> 8 ); b = (unsigned char) ( ( (*wData) & 0x07e0 ) >> 3 ); c = (unsigned char) ( ( (*wData) & 0x001f ) << 3 ); }
#define GETRGB888( a, b, c, d ) { DWORD *dwData = (DWORD *) d; a = (unsigned char) ( (*dwData) >> 16 ); b = (unsigned char) ( ( (*dwData) & 0x0000ff00 ) >> 8 ); c = (unsigned char) ( (*dwData) & 0x000000ff ); }
#define PUTRGB555( a, b, c, d ) { WORD *wData = (WORD *) d; *wData = ( ( ( (WORD) a & 0x00f8 ) << 7 ) | ( ( (WORD) b & 0x00f8 ) << 2 ) | ( (WORD) c >> 3 ) ); }
#define PUTRGB565( a, b, c, d ) { WORD *wData = (WORD *) d; *wData = ( ( ( (WORD) a & 0x00f8 ) << 8 ) | ( ( (WORD) b & 0x00fc ) << 3 ) | ( (WORD) c >> 3 ) ); }
#define PUTRGB888( a, b, c, d ) { DWORD *dwData = (DWORD *) d; *dwData = ( (DWORD) a << 16 ) | ( (DWORD) b << 8 ) | (DWORD) c; }

#define _RGB(r,g,b) (WORD)(((b)&~7)<<7)|(((g)&~7)<<2)|((r)>>3)

enum{ IMAGELIB_CREATEFILE, IMAGELIB_APPENDTOFILE, IMAGELIB_DELETEPAGE, IMAGELIB_OVERWRITEPAGE, IMAGELIB_INSERTPAGE };

#ifndef IMAGELIB_SUCCESS
#define IMAGELIB_SUCCESS 0

 
#define IMAGELIB_FILE_OPEN_ERROR -1
#define IMAGELIB_FILE_CREATION_ERROR -2
#define IMAGELIB_FILE_WRITE_ERROR -3
#define IMAGELIB_FILE_READ_ERROR -4

 
#define IMAGELIB_MEMORY_ALLOCATION_ERROR -50
#define IMAGELIB_MEMORY_LOCK_ERROR -51

#define IMAGELIB_PAGE_OUT_OF_RANGE -60

 
#define IMAGELIB_NODIB -100

#define IMAGELIB_UNSUPPORTED_FILETYPE -200
#define IMAGELIB_HDIB_NULL -201
#define IMAGELIB_LOGICAL_PALETTE_CREATION_ERROR -202
#define IMAGELIB_NO_PALETTE_FOR_HIGH_COLOR -203
#define IMAGELIB_STRETCHDIBITS_ERROR -204
#define IMAGELIB_PALETTE_QUANTIZE_ERROR -205
#define IMAGELIB_ATTEMPT_CHANGE_TO_SAME 206
#define IMAGELIB_ROTATION_VALUE_ERROR -207
#define IMAGELIB_ROTATE_ERROR -208
#define IMAGELIB_HDIB_LOCKERROR -209

#define IMAGELIB_BAD_ARGUMENT -300



#define JMSG_NOMESSAGE -900
#define JERR_ARITH_NOTIMPL -901
#define JERR_BAD_ALIGN_TYPE -902
#define JERR_BAD_ALLOC_CHUNK -903
#define JERR_BAD_BUFFER_MODE -904
#define JERR_BAD_COMPONENT_ID -905
#define JERR_BAD_DCTSIZE -906
#define JERR_BAD_IN_COLORSPACE -907
#define JERR_BAD_J_COLORSPACE -908
#define JERR_BAD_LENGTH -909
#define JERR_BAD_MCU_SIZE -910
#define JERR_BAD_POOL_ID -911
#define JERR_BAD_PRECISION -912
#define JERR_BAD_PROGRESSION -913
#define JERR_BAD_PROG_SCRIPT -914
#define JERR_BAD_SAMPLING -915
#define JERR_BAD_SCAN_SCRIPT -916
#define JERR_BAD_STATE -917
#define JERR_BAD_VIRTUAL_ACCESS -918
#define JERR_BUFFER_SIZE -919
#define JERR_CANT_SUSPEND -920
#define JERR_CCIR601_NOTIMPL -921
#define JERR_COMPONENT_COUNT -922
#define JERR_CONVERSION_NOTIMPL -923
#define JERR_DAC_INDEX -924
#define JERR_DAC_VALUE -925
#define JERR_DHT_COUNTS -926
#define JERR_DHT_INDEX -927
#define JERR_DQT_INDEX -928
#define JERR_EMPTY_IMAGE -929
#define JERR_EMS_READ -930
#define JERR_EMS_WRITE -931
#define JERR_EOI_EXPECTED -932
#define JERR_FILE_READ -933
#define JERR_FILE_WRITE -934
#define JERR_FRACT_SAMPLE_NOTIMPL -935
#define JERR_HUFF_CLEN_OVERFLOW -936
#define JERR_HUFF_MISSING_CODE -937
#define JERR_IMAGE_TOO_BIG -938
#define JERR_INPUT_EMPTY -939
#define JERR_INPUT_EOF -940
#define JERR_MISMATCHED_QUANT_TABLE -941
#define JERR_MISSING_DATA -942
#define JERR_MODE_CHANGE -943
#define JERR_NOTIMPL -944
#define JERR_NOT_COMPILED -945
#define JERR_NO_BACKING_STORE -946
#define JERR_NO_HUFF_TABLE -947
#define JERR_NO_IMAGE -948
#define JERR_NO_QUANT_TABLE -949
#define JERR_NO_SOI -950
#define JERR_OUT_OF_MEMORY -951
#define JERR_QUANT_COMPONENTS -952
#define JERR_QUANT_FEW_COLORS -953
#define JERR_QUANT_MANY_COLORS -954
#define JERR_SOF_DUPLICATE -955
#define JERR_SOF_NO_SOS -956
#define JERR_SOF_UNSUPPORTED -957
#define JERR_SOI_DUPLICATE -958
#define JERR_SOS_NO_SOF -959
#define JERR_TFILE_CREATE -960
#define JERR_TFILE_READ -961
#define JERR_TFILE_SEEK -962
#define JERR_TFILE_WRITE -963
#define JERR_TOO_LITTLE_DATA -964
#define JERR_UNKNOWN_MARKER -965
#define JERR_VIRTUAL_BUG -966
#define JERR_WIDTH_OVERFLOW -967
#define JERR_XMS_READ -968
#define JERR_XMS_WRITE -969
#define JMSG_COPYRIGHT -970
#define JMSG_VERSION -971
#define JTRC_16BIT_TABLES -972
#define JTRC_ADOBE -973
#define JTRC_APP0 -974
#define JTRC_APP14 -975
#define JTRC_DAC -976
#define JTRC_DHT -977
#define JTRC_DQT -978
#define JTRC_DRI -979
#define JTRC_EMS_CLOSE -980
#define JTRC_EMS_OPEN -981
#define JTRC_EOI -982
#define JTRC_HUFFBITS -983
#define JTRC_JFIF -984
#define JTRC_JFIF_BADTHUMBNAILSIZE -985
#define JTRC_JFIF_MINOR -986
#define JTRC_JFIF_THUMBNAIL -987
#define JTRC_MISC_MARKER -988
#define JTRC_PARMLESS_MARKER -989
#define JTRC_QUANTVALS -990
#define JTRC_QUANT_3_NCOLORS -991
#define JTRC_QUANT_NCOLORS -992
#define JTRC_QUANT_SELECTED -993
#define JTRC_RECOVERY_ACTION -994
#define JTRC_RST -995
#define JTRC_SMOOTH_NOTIMPL -996
#define JTRC_SOF -997
#define JTRC_SOF_COMPONENT -998
#define JTRC_SOI -999
#define JTRC_SOS -1000
#define JTRC_SOS_COMPONENT -1001
#define JTRC_SOS_PARAMS -1002
#define JTRC_TFILE_CLOSE -1003
#define JTRC_TFILE_OPEN -1004
#define JTRC_UNKNOWN_IDS -1005
#define JTRC_XMS_CLOSE -1006
#define JTRC_XMS_OPEN -1007

#endif


 

#define IMAGETYPE_NONE 0
#define IMAGETYPE_BMP 1
#define IMAGETYPE_GIF 2
#define IMAGETYPE_PCX 3
#define IMAGETYPE_TGA 4
#define IMAGETYPE_JPG 5
#define IMAGETYPE_TIF 6

#define IMAGETYPE_FIRSTTYPE IMAGETYPE_BMP
#define IMAGETYPE_LASTTYPE IMAGETYPE_TIF

#define	    TIFCOMPRESSION_NONE		1	/* dump mode */
#define	    TIFCOMPRESSION_CCITTRLE	2	/* CCITT modified Huffman RLE */
#define	    TIFCOMPRESSION_CCITTFAX3	3	/* CCITT Group 3 fax encoding */
#define	    TIFCOMPRESSION_CCITTFAX4	4	/* CCITT Group 4 fax encoding */
#define	    TIFCOMPRESSION_LZW		5	/* Lempel-Ziv  & Welch */
#define	    TIFCOMPRESSION_OJPEG		6	/* !6.0 JPEG */
#define	    TIFCOMPRESSION_JPEG		7	/* %JPEG DCT compression */
#define	    TIFCOMPRESSION_NEXT		32766	/* NeXT 2-bit RLE */
#define	    TIFCOMPRESSION_CCITTRLEW	32771	/* #1 w/ word alignment */
#define	    TIFCOMPRESSION_PACKBITS	32773	/* Macintosh RLE */
#define	    COMPRESSION_THUNDERSCAN	32809	/* ThunderScan RLE */
/* codes 32895-32898 are reserved for ANSI IT8 TIFF/IT <dkelly@etsinc.com) */
#define	    TIFCOMPRESSION_IT8CTPAD	32895   /* IT8 CT w/padding */
#define	    TIFCOMPRESSION_IT8LW		32896   /* IT8 Linework RLE */
#define	    TIFCOMPRESSION_IT8MP		32897   /* IT8 Monochrome picture */
#define	    TIFCOMPRESSION_IT8BL		32898   /* IT8 Binary line art */
/* compression codes 32908-32911 are reserved for Pixar */
#define     TIFCOMPRESSION_PIXARFILM	32908   /* Pixar companded 10bit LZW */
#define	    TIFCOMPRESSION_PIXARLOG	32909   /* Pixar companded 11bit ZIP */
#define	    TIFCOMPRESSION_DEFLATE		32946	/* Deflate compression */
/* compression code 32947 is reserved for Oceana Matrix <dev@oceana.com> */
#define     TIFCOMPRESSION_DCS             32947   /* Kodak DCS encoding */
#define	    TIFCOMPRESSION_JBIG		34661	/* ISO JBIG */

#if 0
#define TIF_NO_COMPRESSION 1
#define TIF_CCITT_COMPRESSION 2
#define TIF_PACKBITS_COMPRESSION 32773
#define TIF_TIF_LZW_COMPRESSION 5
#define TIF_G3_COMPRESSION 3
#define TIF_G4_COMPRESSION 4
#endif

#define TWAINLIB_SUCCESS 0

#define TWAINLIB_PROCADDRNULL -6000
#define TWAINLIB_LOADLIBRARYERROR -6002
#define TWAINLIB_HINSTANCENULL -6003
#define TWAINLIB_MEMALLOCATIONERROR -6004
#define TWAINLIB_DSMOPENFAILURE -6005
#define TWAINLIB_DSOPENFAILURE -6006

#define TWAINLIB_OUTOFPAPER -7000

#define TWAINLIB_BUMMER          1 /* Failure due to unknown causes             */
#define TWAINLIB_LOWMEMORY       2 /* Not enough memory to perform operation    */
#define TWAINLIB_NODS            3 /* No Data Source                            */
#define TWAINLIB_MAXCONNECTIONS  4 /* DS is connected to max possible apps      */
#define TWAINLIB_OPERATIONERROR  5 /* DS or DSM reported error, app shouldn't   */
#define TWAINLIB_BADCAP          6 /* Unknown capability                        */
#define TWAINLIB_BADPROTOCOL     9 /* Unrecognized MSG DG DAT combination       */
#define TWAINLIB_BADVALUE       10 /* Data parameter out of range               */
#define TWAINLIB_SEQERROR       11 /* DG DAT MSG out of expected sequence       */
#define TWAINLIB_BADDEST        12 /* Unknown destination App/Src in DSM_Entry  */
#define TWAINLIB_CAPUNSUPPORTED  13 /* Capability not supported by source            */
#define TWAINLIB_CAPBADOPERATION 14 /* Operation not supported by capability         */
#define TWAINLIB_CAPSEQERROR     15 /* Capability has dependancy on other capability */

#define TWAINLIB_CAPSSUPPORTED 200
#define TWAINLIB_CAPSDEFAULTS 201
#define TWAINLIB_CAPSCURRENT 202
#define TWAINLIB_ONECAPSUPPORTED 203
#define TWAINLIB_ONECAPDEFAULT 204
#define TWAINLIB_ONECAPCURRENT 205
#define TWAINLIB_CAPSIZE 206

#define TWAINLIB_SETONECAP 300
#define TWAINLIB_SETALLCAPS 301

/* all data sources are REQUIRED to support these caps */
#define CAP_XFERCOUNT          0x0001


/* image data sources are REQUIRED to support these caps */
#define ICAP_COMPRESSION       0x0100
#define ICAP_PIXELTYPE         0x0101
#define ICAP_UNITS             0x0102 /* default is TWUN_INCHES */
#define ICAP_XFERMECH          0x0103


/* all data sources MAY support these caps */
#define CAP_AUTHOR             0x1000
#define CAP_CAPTION            0x1001
#define CAP_FEEDERENABLED      0x1002
#define CAP_FEEDERLOADED       0x1003
#define CAP_TIMEDATE           0x1004
#define CAP_SUPPORTEDCAPS      0x1005
#define CAP_EXTENDEDCAPS       0x1006
#define CAP_AUTOFEED           0x1007
#define CAP_CLEARPAGE          0x1008
#define CAP_FEEDPAGE           0x1009
#define CAP_REWINDPAGE         0x100a
#define CAP_INDICATORS         0x100b           	/* Added 1.1 */
#define CAP_SUPPORTEDCAPSEXT   0x100c			/* Added 1.6 */
#define CAP_PAPERDETECTABLE    0x100d			/* Added 1.6 */
#define CAP_UICONTROLLABLE     0x100e			/* Added 1.6 */
#define CAP_DEVICEONLINE	   0x100f			/* Added 1.6 */
	
/* image data sources MAY support these caps */
#define ICAP_AUTOBRIGHT        0x1100
#define ICAP_BRIGHTNESS        0x1101
#define ICAP_CONTRAST          0x1103
#define ICAP_CUSTHALFTONE      0x1104
#define ICAP_EXPOSURETIME      0x1105
#define ICAP_FILTER            0x1106
#define ICAP_FLASHUSED         0x1107
#define ICAP_GAMMA             0x1108
#define ICAP_HALFTONES         0x1109
#define ICAP_HIGHLIGHT         0x110a
#define ICAP_IMAGEFILEFORMAT   0x110c
#define ICAP_LAMPSTATE         0x110d
#define ICAP_LIGHTSOURCE       0x110e
#define ICAP_ORIENTATION       0x1110
#define ICAP_PHYSICALWIDTH     0x1111
#define ICAP_PHYSICALHEIGHT    0x1112
#define ICAP_SHADOW            0x1113
#define ICAP_FRAMES            0x1114
#define ICAP_XNATIVERESOLUTION 0x1116
#define ICAP_YNATIVERESOLUTION 0x1117
#define ICAP_XRESOLUTION       0x1118
#define ICAP_YRESOLUTION       0x1119
#define ICAP_MAXFRAMES         0x111a
#define ICAP_TILES             0x111b
#define ICAP_BITORDER          0x111c
#define ICAP_CCITTKFACTOR      0x111d
#define ICAP_LIGHTPATH         0x111e
#define ICAP_PIXELFLAVOR       0x111f
#define ICAP_PLANARCHUNKY      0x1120
#define ICAP_ROTATION          0x1121
#define ICAP_SUPPORTEDSIZES    0x1122
#define ICAP_THRESHOLD         0x1123
#define ICAP_XSCALING          0x1124
#define ICAP_YSCALING          0x1125
#define ICAP_BITORDERCODES     0x1126
#define ICAP_PIXELFLAVORCODES  0x1127
#define ICAP_JPEGPIXELTYPE     0x1128
#define ICAP_TIMEFILL          0x112a
#define ICAP_BITDEPTH          0x112b
#define ICAP_BITDEPTHREDUCTION 0x112c           /* Added 1.5 */
#define ICAP_UNDEFINEDIMAGESIZE 0X112d		/* Added 1.6 */

enum{ TWAINLIB_AUTOACQUIRE, TWAINLIB_FLATACQUIRE, TWAINLIB_ADFACQUIRE };

#ifdef __cplusplus
extern "C" {
#endif

typedef struct{
	int nFileType;
	int nTransparentIndex;
	int nQuality;
	int nCompressionType;
	int nWidth, nHeight;
	int nBits, nPlanes, nColors;
	int nPages;
	int nSaveMode;
} _IMAGE_INFO;


 
int __declspec (dllexport) GetLastPicLibError( void );

 
int __declspec (dllexport) FileType( const char * );


 

 
HGLOBAL __declspec (dllexport) LoadBMP( const char * );

 
BOOL __declspec (dllexport) GetBMPInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SaveBMP( const char *, HGLOBAL );


 

 
HGLOBAL __declspec (dllexport) LoadGIF( const char *, int * );

 
BOOL __declspec (dllexport) GetGIFInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SaveGIF( const char *, HGLOBAL, int );


 

 
HGLOBAL __declspec (dllexport) LoadPCX( const char * );

 
BOOL __declspec (dllexport) GetPCXInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SavePCX( const char *, HGLOBAL );


 

 
HGLOBAL __declspec (dllexport) LoadTGA( const char * );

 
BOOL __declspec (dllexport) GetTGAInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SaveTGA( const char *, HGLOBAL );


 

 
HGLOBAL __declspec (dllexport) LoadTIF( const char * );

 
BOOL __declspec (dllexport) GetTIFInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SaveTIF( const char *, HGLOBAL, int );


 

 
HGLOBAL __declspec (dllexport) LoadJPG( const char * );

 
BOOL __declspec (dllexport) GetJPGInfo( const char *, int *, int *, int *, int *, int * );

 
BOOL __declspec (dllexport) SaveJPG( const char *, HGLOBAL, int );


 

 
int __declspec (dllexport) LoadImg( const char *, HGLOBAL *, int, _IMAGE_INFO * );

 
int __declspec (dllexport) GetImgInfo( const char *, int, _IMAGE_INFO * );

 
int __declspec (dllexport) SaveImg( const char *, HGLOBAL, int, _IMAGE_INFO * );


 

 
WORD __declspec (dllexport) MedianCut( WORD Hist[], BYTE ColMap[][3], int );

WORD __declspec (dllexport) Popularity( unsigned char *pBits, int nBits, int nWidth, int nHeight, BYTE ColorMap[][3] );


 

int __declspec (dllexport) GetLastTwainError( void );
	
int __declspec (dllexport) SelectDataSource( HWND );

int __declspec (dllexport) InitTwain( HWND );

int __declspec (dllexport) UninitTwain( void );

int __declspec (dllexport) AcquireImage( HGLOBAL *, BOOL, int );

int __declspec (dllexport) GetTwainCaps( HWND, void *, int, int );

int __declspec (dllexport) SetTwainCaps( HWND, void *, int, int );

int __declspec (dllexport) TwainCapValueFromIndex( int );

int __declspec (dllexport) TwainCapIndexFromValue( int );

HGLOBAL __declspec (dllexport) ChangeFormat( HGLOBAL, int );

 

#ifdef __cplusplus
}
#endif

#endif
