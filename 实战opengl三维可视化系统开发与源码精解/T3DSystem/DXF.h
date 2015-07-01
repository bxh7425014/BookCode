#if !defined(AFX_DXF_H__3F79BDB0_6B6D_4513_8CA3_01FDE9D25EF3__INCLUDED_)
#define AFX_DXF_H__3F79BDB0_6B6D_4513_8CA3_01FDE9D25EF3__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CDXF  
{
public:
	void DxfDraw_Text(CStdioFile *Dxffile, CString strlayername, double x, double y, float HW_Scale, int fonhHeight, float InAngle, CString strText, int style);
	void DxfDraw_Circle(CStdioFile *Dxffile, double centerx, double centery, float R, int mcolor);
	void DxfStart_polyline(CStdioFile* Dxffile,CString Layername,double elevation,float startWidth,float endWidth,int mcolor);
	void DxfStart_polygon(CStdioFile* Dxffile,double elevation);
	void DxfSectionEnd(CStdioFile* Dxffile);
	void DxfLineType(CStdioFile* Dxffile);
	void DxfHeader(CStdioFile* Dxffile);
	void DxfEnd_polyline(CStdioFile* Dxffile);
	void DxfEnd_polygon(CStdioFile* Dxffile);
	void DxfDraw_PolyLine(CStdioFile* Dxffile,CString Layername, double x, double y, double z);
	void DxfDraw_Polygon(CStdioFile* Dxffile,CString Layername, double x, double y, double z);
	void DxfDraw_Point(CStdioFile* Dxffile,CString Layername, int color, double x, double y, double z);
	void DxfDraw_Line(CStdioFile* Dxffile,CString Layername, int color,double x1, double y1, double z1, double x2, double y2, double z2);
	void DxfBeginDrawEnties(CStdioFile* Dxffile);
	CDXF();
	virtual ~CDXF();

};

#endif // !defined(AFX_DXF_H__3F79BDB0_6B6D_4513_8CA3_01FDE9D25EF3__INCLUDED_)
