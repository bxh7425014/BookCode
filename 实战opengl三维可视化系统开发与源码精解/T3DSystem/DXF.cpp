// DXF.cpp: implementation of the CDXF class.
 
 

#include "stdafx.h"
#include "DXF.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

 
// Construction/Destruction
 

CDXF::CDXF()
{

}

CDXF::~CDXF()
{

}

//开始绘制实体
void CDXF::DxfBeginDrawEnties(CStdioFile* Dxffile)
{
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("SECTION\n"); //表明这是一个段的开始
	Dxffile->WriteString ("2\n");
	Dxffile->WriteString ("ENTITIES\n"); 
}

//绘制直线
void CDXF::DxfDraw_Line(CStdioFile* Dxffile,CString Layername,int color,double x1, double y1, double z1, double x2, double y2, double z2)
{
	CString tt1[3],tt2[3],ttcolor;

	//将(x1,y1,z1)坐标转换为字符型
	tt1[0].Format ("%.3f",x1);
	tt1[1].Format ("%.3f",y1);
	tt1[2].Format ("%.3f",z1);

	//将(x2,y2,z2)坐标转换为字符型
	tt2[0].Format ("%.3f",x2);
	tt2[1].Format ("%.3f",y2);
	tt2[2].Format ("%.3f",z2);
	
	ttcolor.Format("%d",color);//将颜色值转移为字符型

	Dxffile->WriteString ("0\n"); 
	Dxffile->WriteString ("LINE\n"); //标识直线开始
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString (Layername+"\n"); //层名
	Dxffile->WriteString ("62\n");
	Dxffile->WriteString (ttcolor+"\n"); //颜色
	Dxffile->WriteString ("6\n");
	Dxffile->WriteString ("CONTINUOUS\n"); //线型
	Dxffile->WriteString ("10\n");  
	Dxffile->WriteString (tt1[0]+"\n"); //x1坐标
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString (tt1[1]+"\n"); //y1坐标
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString (tt1[2]+"\n"); //z1坐标
	
	Dxffile->WriteString ("11\n");
	Dxffile->WriteString (tt2[0]+"\n"); //x2坐标
	Dxffile->WriteString ("21\n");
	Dxffile->WriteString (tt2[1]+"\n"); //y2坐标
	Dxffile->WriteString ("31\n");
	Dxffile->WriteString (tt2[2]+"\n"); //z2坐标
}

//绘制点
void CDXF::DxfDraw_Point(CStdioFile* Dxffile,CString Layername, int color,double x, double y, double z)
{
	CString tt2[3],ttcolor;;

	//将(x,y,z)坐标转换为字符型
	tt2[0].Format ("%.3f",x);
	tt2[1].Format ("%.3f",y);
	tt2[2].Format ("%.3f",z);

	ttcolor.Format("%d",color);//将颜色值转移为字符型
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("POINT\n");//标识点开始
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString (Layername+"\n"); //层名
	Dxffile->WriteString ("62\n");
	Dxffile->WriteString (ttcolor+"\n"); //颜色
	Dxffile->WriteString ("6\n");
	Dxffile->WriteString ("CONTINUOUS\n"); //线型
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString (tt2[0]+"\n"); //点的x坐标
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString (tt2[1]+"\n"); //点的x坐标
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString (tt2[2]+"\n"); //点的z坐标
}

//绘制多段线
void CDXF::DxfDraw_PolyLine(CStdioFile* Dxffile,CString Layername, double x, double y, double z)
{
	CString tt1[3];
	
	//将(x,y,z)坐标转换为字符型
	tt1[0].Format ("%.3f",x);
	tt1[1].Format ("%.3f",-y); 
	tt1[2].Format ("%.3f",z);


	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("VERTEX\n");//标识顶点开始
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString (Layername+"\n"); //层名
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString (tt1[0]+"\n"); //点的x坐标
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString (tt1[1]+"\n"); //点的y坐标
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString (tt1[2]+"\n"); //点的z坐标
}

//结束绘制多段线
void CDXF::DxfEnd_polyline(CStdioFile* Dxffile)
{
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("SEQEND\n"); //标识结束,表明该实体的数据已经全部记录完了
}

//绘制多边形
void CDXF::DxfDraw_Polygon(CStdioFile* Dxffile,CString Layername, double x, double y, double z)
{
	CString tt,tt1[3];

	//将(x,y,z)坐标转换为字符型
	tt1[0].Format ("%.3f",x);
	tt1[1].Format ("%.3f",y);
	tt1[2].Format ("%.3f",z);
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("VERTEX\n");//标识顶点开始
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString (Layername+"\n"); //层名
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString (tt1[0]+"\n");//点的x坐标
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString (tt1[1]+"\n");//点的y坐标
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString (tt1[2]+"\n");//点的z坐标
}

//结束绘制多边形
void CDXF::DxfEnd_polygon(CStdioFile* Dxffile)
{
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("SEQEND\n");//标识结束,表明该实体的数据已经全部记录完了
}

//绘制DXF文件头
void CDXF::DxfHeader(CStdioFile* Dxffile)
{
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("SECTION\n"); //表明这是一个段的开始
	Dxffile->WriteString ("2\n");
	Dxffile->WriteString ("HEADER\n"); //标识标题段开始
	Dxffile->WriteString ("9\n");

	Dxffile->WriteString ("$LIMMIN\n");//储当前空间的左下方图形界限
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString ("0\n");
	
	Dxffile->WriteString ("9\n");
	Dxffile->WriteString ("$LIMMAX\n");//储当前空间的右上方图形界限
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString ("5000\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString ("420\n");
	
	Dxffile->WriteString ("9\n");
	Dxffile->WriteString ("$VIEWCTR\n");//存储当前视口中视图的中心点
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString ("430\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString ("210\n");
	
	Dxffile->WriteString ("9\n");
	Dxffile->WriteString ("$VIEWSIZE\n"); //视口大小
	Dxffile->WriteString ("40\n");
	Dxffile->WriteString ("5000\n");
	
	Dxffile->WriteString ("9\n");
	Dxffile->WriteString ("$TEXTSTYLE\n"); //方案类型
	Dxffile->WriteString ("7\n");
	Dxffile->WriteString ("HZ\n");
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("ENDSEC\n"); //表明这一段结束了
	
}

//定义直线线型
void CDXF::DxfLineType(CStdioFile* Dxffile)
{
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("SECTION\n"); //表明这是一个段的开始
	Dxffile->WriteString ("2\n");
	Dxffile->WriteString ("TABLES\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("TABLE\n");
	Dxffile->WriteString ("2\n");
	Dxffile->WriteString ("LTYPE\n");
	
	Dxffile->WriteString ("70\n");
	Dxffile->WriteString ("1\n");
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("LTYPE\n");
	
	Dxffile->WriteString ("2\n");
	Dxffile->WriteString ("CONTINUOUS\n");
	Dxffile->WriteString ("70\n");
	Dxffile->WriteString ("64\n");
	Dxffile->WriteString ("3\n");
	Dxffile->WriteString ("Solid line\n");
	Dxffile->WriteString ("72\n");
	Dxffile->WriteString ("65\n");
	Dxffile->WriteString ("73\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("40\n");
	Dxffile->WriteString ("0.0\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("ENDTAB\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("ENDSEC\n");//表明这一段结束了
}

//DXF文件结束
void CDXF::DxfSectionEnd(CStdioFile* Dxffile)
{
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("ENDSEC\n");//表明这一段结束了
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("EOF\n"); //表明这个DXF 文件结束了
	Dxffile->Close ();
}

//开始绘制多段线标志
void CDXF::DxfStart_polyline(CStdioFile* Dxffile,CString Layername,double elevation,float startWidth,float endWidth,int mcolor)
{
	CString tt;
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("POLYLINE\n"); //标识为多段线 POLYLINE
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString (Layername+"\n"); //层名
	Dxffile->WriteString ("66\n");
	Dxffile->WriteString ("1\n");
	Dxffile->WriteString ("62\n");//颜色
	tt.Format("%d\n",mcolor);
	Dxffile->WriteString (tt);  
	Dxffile->WriteString ("70\n");
	Dxffile->WriteString ("0\n");   //是否封闭(=0:不封闭 =1:封闭)
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("30\n");
	tt.Format ("%.3f",elevation);
	Dxffile->WriteString (tt+"\n");
	Dxffile->WriteString ("40\n"); //标高
	tt.Format("%.2f\n",startWidth); //起始线段宽度
	Dxffile->WriteString (tt);
	Dxffile->WriteString ("41\n");
	tt.Format("%.2f\n",endWidth);//终止线段宽度
	Dxffile->WriteString (tt);
}

//开始绘制多边形标志
void CDXF::DxfStart_polygon(CStdioFile* Dxffile,double elevation)
{
	CString tt;
	tt.Format ("%.3f",elevation); //标高
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("POLYLINE\n");
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString ("poline_layer\n");
	Dxffile->WriteString ("66\n");
	Dxffile->WriteString ("1\n");
	Dxffile->WriteString ("62\n");
	Dxffile->WriteString ("4\n"); //颜色
	Dxffile->WriteString ("70\n");
	Dxffile->WriteString ("1\n"); //是否封闭(=0:不封闭 =1:封闭)
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString (tt+"\n");//标高
	Dxffile->WriteString ("40\n");
	Dxffile->WriteString ("0\n");//起始线段宽度
	Dxffile->WriteString ("41\n");
	Dxffile->WriteString ("0\n");//终止线段宽度
}


void CDXF::DxfDraw_Circle(CStdioFile *Dxffile, double centerx, double centery, float R, int mcolor)
{
	CString tt;
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("CIRCLE\n");
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString ("Circle_layer\n");
	Dxffile->WriteString ("62\n");
	tt.Format("%d\n",mcolor);
	Dxffile->WriteString (tt);  
	Dxffile->WriteString ("10\n");
	tt.Format("%f\n",centerx);
	Dxffile->WriteString (tt);
	Dxffile->WriteString ("20\n");
	tt.Format("%f\n",centery);
	Dxffile->WriteString (tt);
	Dxffile->WriteString ("30\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("40\n");   
	tt.Format("%f\n",R);
	Dxffile->WriteString (tt);
}

void CDXF::DxfDraw_Text(CStdioFile *Dxffile, CString strlayername, double x, double y, float HW_Scale, int fonhHeight, float InAngle, CString strText, int style)
{
	CString tt,tt1[3];
	
	tt1[0].Format ("%.3f",x);
	tt1[1].Format ("%.3f",y);
	
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("text\n");
	Dxffile->WriteString ("8\n");
	Dxffile->WriteString ("layrtname\n");
	Dxffile->WriteString ("62\n");
	Dxffile->WriteString ("2\n"); 
	Dxffile->WriteString ("10\n");
	Dxffile->WriteString (tt1[0]+"\n");
	Dxffile->WriteString ("20\n");
	Dxffile->WriteString (tt1[1]+"\n");
	Dxffile->WriteString ("40\n");
	tt.Format("%d\n",fonhHeight);
	Dxffile->WriteString (tt);
	Dxffile->WriteString ("1\n");
	Dxffile->WriteString (strText+"\n");
	Dxffile->WriteString ("41\n");
	tt.Format("%f\n",HW_Scale);
	Dxffile->WriteString (tt);
	Dxffile->WriteString ("71\n");
	Dxffile->WriteString ("0\n");
	Dxffile->WriteString ("50\n");
	tt.Format("%f\n",InAngle);
	Dxffile->WriteString (tt);
	
	
	
	Dxffile->WriteString ("7\n");
	tt.Format("%d\n",style);
	Dxffile->WriteString (tt);
}
