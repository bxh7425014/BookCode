 
 
 

#if !defined(AFX_DESINGSCHEME_H__5B5184D2_8345_4FB9_A7DA_9D8B33E76043__INCLUDED_)
#define AFX_DESINGSCHEME_H__5B5184D2_8345_4FB9_A7DA_9D8B33E76043__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#define MAXSCHEMENUMS 25

//三维点坐标结构
typedef struct  
{
	double x;	//x坐标
	double y;	//y坐标
	double z;	//z坐标
}Cordinate3D, *PCordinate3D;


//排水沟总结构
typedef struct  
{
	Cordinate3D SuiGouL[6];//左侧水沟结构
	Cordinate3D SuiGouR[6];//左侧水沟结构
	BOOL bhaveSuiGou_L;//记录左侧是否有水沟 
	BOOL bhaveSuiGou_R;//记录右侧是否有水沟
	
}PaiSuiGou, *PPaiSuiGou;


//护坡结构
typedef struct  
{
	Cordinate3D Hp[3];//一阶护坡的话个点三维坐标
	float h; //
	float m;
	float b;
	int style;//护坡类型
}HuPo, *PHuPo;

//路基总结构
typedef struct  
{
	HuPo HuPo_L[3];//左侧护坡结构
	int Huponums_L;//左侧护坡级数
	HuPo HuPo_R[3];//右侧护坡结构
	int Huponums_R;//右侧护坡级数
	int TW_left;//TW_left=-1:路堑  0:填挖平衡   1:路堤 99:桥隧中间点
	int TW_right;//TW_right=-1:路堑  0:填挖平衡   1:路堤  99:桥隧中间点
	double Lc;//里程
	CString strJDStyle;// 
}LuQianHuPo,*PLuQianHuPo;

typedef struct 
{
	long InsertIndex;
	CArray<PLuQianHuPo,PLuQianHuPo> tempHuPo;
}LuQianHuPoTemp,*PLuQianHuPoTemp;

//线路结构
typedef struct  
{
	double x1; //左侧轨道点坐标
	double y1;
	double z1;
	
	double x2;//右侧轨道点坐标
	double y2;
	double z2;

	int TW_left; //左侧填挖类型
	int TW_right;//左侧填挖类型
	float mAngle;//当前旋转角度
	float QDHeight;//桥墩高度
}Railway3DCordinate, *PRailway3DCordinate;


//线路三维点综合结构
typedef struct  
{
	double x;//点的x坐标
	double y;//点的y坐标
	double z;//点的z坐标
	CString strJDStyle;
	float dmh; //点的地面高程
	float Derh;//点的高差
	double Lc;//点的里程
}Cordinate, *PCordinate;

//隧道结构
typedef struct  
{

	double x1;//隧道左侧边点的x坐标
	double y1;//隧道左侧边点的y坐标
	double z1;//隧道左侧边点的z坐标
	
	double x2;//隧道右侧边点的x坐标
	double y2;//隧道右侧边点的y坐标
	double z2;//隧道右侧边点的z坐标
	
	int Tunnnelindex;//隧道在总线路隧道中的索引号
	CString name;//隧道名称
	CArray<PCordinate,PCordinate> tunnelArc; //隧道内顶面圆弧点结构
	
}Railway3DTunnnel, *PRailway3DTunnnel;

//隧道断面结构 
typedef struct  
{
	float	height; //隧道高度
	float	Archeight; //隧道圆弧高度
	float	WallHeight; //隧道立墙高度
	float	ArcSegmentNumber;//隧道圆弧分段数
	float	width; //隧道宽度
	float	H;//隧道圆弧顶端再向上的高度
	float	L;//隧道洞门顶端左右两侧延伸的宽度 
	
}Tunnel;

//隧道、桥梁信息结构
typedef struct 
{
	double StartLC; //隧道、桥梁的起始里程
	double EndLC;//隧道、桥梁的终点里程
	float mStartangle; //隧道、桥梁进口端角度
	float mEndAngle;  //隧道、桥梁出口端角度
	int startIndex; //进口端索引号
	int endIndex;//出口端端索引号
	Cordinate StartLeftXYZ;//进口端左侧边点的x,y,z坐标
	Cordinate StartRightXYZ;//进口端右侧边点的x,y,z坐标
	Cordinate EndLeftXYZ;//出口端左侧边点的x,y,z坐标
	Cordinate EndRightXYZ;//出口端右侧边点的x,y,z坐标
	CArray<PCordinate,PCordinate> m_ReginPt_Start;	//隧道/桥梁封闭区域边界线路点(隧道进口)
	CArray<PCordinate,PCordinate> m_ReginPt_End;	//隧道/桥梁封闭区域边界线路点(隧道出口)
}TunnnelBridgeInformation, *PTunnnelBridgeInformation;

//桥梁结构
typedef struct  
{
	double x1; //桥梁起点x坐标
	double y1;//桥梁起点y坐标
	double z1;//桥梁起点z坐标
	double Lc1; //桥梁起点里程
	double x2;//桥梁终点x坐标
	double y2;//桥梁终点y坐标
	double z2;//桥梁终点z坐标
	double Lc2;//桥梁终点里程
	CString name;//桥梁名称
}CordinateBridge, *PCordinateBridge;

//桥梁模型结构
typedef struct  
{
	float	m_Bridge_HLSpace;	//桥梁栏杆间距
	float	m_Bridge_HLWidth;	//桥梁栏杆宽度
	float	m_Bridge_HLHeight;	//桥梁栏杆高度
	float	m_Bridge_QDSpace;	//桥墩间距
	int     m_Bridge_HPangle;  //桥头护坡倾角

}Bridge;

//设计方案信息结构
typedef struct 
{
	CString	Schemename;		//方案名称			
	CString strDesigngrade;	//设计等级			
	CString strDraughtstyle;//牵引类型			
	CString strBlocomotiveStyle;//闭塞类型		
	CString strCBbsStyle;				
	CString strTerrainstyle;	//地形类别		
	CString strEngineeringcondition;	//工程条件
	int		designspeed;			//设计速度	
	int		minRadius;				//最小曲线半径	
	int		dxfLength;				//到发线最小长度	
	float	minSlopeLength;			//最小坡长	
	float	maxSlope;				//最大坡度	
	float	maxSlopePddsc;			//最大坡度代数差
	double	StartLC;				//起始里程		
	double	EndLC;					//终止里程	
	int Index;						//方案索引	
}SchemeData, *PSchemeData;

//线路设计交点结构
typedef struct  
{
	CString  ID;	//交点ID号
	double  JDLC;	//交点里程
	float Alfa;		//交点转角
	float fwj;		//交点偏角1
	float fwj2;		//交点偏角2
	float T;		//切线长
	int L0;			//缓和曲线长
	float L;		//曲线长
	float Ly;		//
	float Jzxc;		//夹直线长
	double HZ;		//缓直点里程
	double ZH;		//直缓点里程
	double HY;		//缓圆点里程
	double YH;		//圆缓点里程
	long R;			//曲线半径
	float E;		//外矢距
	float P;		//
	float Dist;		//
	int  RoateStyle;//旋转类型(左转,右转)
	double x; //交点x坐标
	double y;//交点y坐标
	double z;//交点z坐标
	
	PCordinate ZH_xy;//直缓点坐标
	PCordinate HZ_xy;//缓直点坐标
	PCordinate YH_xy;//圆缓点坐标
	PCordinate HY_xy;//缓圆点坐标
	
	double Cneterx; //曲线圆心x坐标
	double Cnetery;//曲线圆心y坐标
	
}LineCurve, *PLineCurve;

//路基断面结构
typedef struct  
{
	float   m_Railway_width; //路基断面总宽度
	float	m_Lj_width;	//路肩宽度
	float	m_Lj_Dh;	//碴肩至碴脚的高度
	float   m_GuiMianToLujianWidth;//铁轨到碴肩的距离
	float	m_TieGui_width;	//铁轨间距
	
}Railway;


typedef struct  
{
	long boreLayerID;
	BOOL bDraw;
}BoreLayerDraw, *PBoreLayerDraw;


typedef struct  
{
	double StartPt[3];
	double EndPt[3];
	int mlayerID;
}BoreSectionLine, *PBoreSectionLine;


class CDesingScheme 
{
public:
	int GetBlockID(double x,double y);
	void GetTunnelcordinate(float TotalHeight,float Archeight,float WallHeight,float Width,int  ArcSegmentNumber);
	void GetTieGuicordinate(float Railwaywidth, float m_GDwidth);
	void GetZhenMucordinate(float ZhenMuJJ,float Railwaywidth);
	void drawBitmapFormFile(CString BitmapFilePath, CDC *pDC, CRect rect);
	float GetDistenceXYZ(double x1, double y1, double z1, double x2, double y2,double z2);
	void GetBirdgeQDcordinate(float BridgeQDJJ);
	void GetHs(double x0,double y0,double z0,float alfa,float mangle,int Style,double *px,double *py,double *pz);
	void GetHpD(double x,double y,double z,float mAngle,float mHpangle,double *ax,double *ay,double *az);
	float GetANgle(double x1,double z1,double x2,double z2);
	void GetBirdgeLGcordinate(float BridgeLGJJ);
	void Get3DLineModelLast(double x1, double y1, double z1,double x2, double y2, double z2, float fRailwayWidth, float LjWidth, float h_FromGmToLj,float mWidthGuiMianToLujian,float mAngleLujinaToBianPo,CString strJDstyle,CString strJDstyleNext,long index,double mLC);
	void Get3DLineModel(double x1,double y1,double z1,double x2,double y2,double z2,float fRailwayWidth,float LjWidth,float h_FromGmToLj,float mWidthGuiMianToLujian,float mAngleLujinaToBianPo,CString strJDstyle,CString strJDstyleNext,long index,double mLC);
	void InertHuPoPts();
	double GetDistenceXY(double x1,double y1,double x2,double y2);
	void GetYQXXY(double centerx,double centery, long R, int RoateStyle, float LL,float alfa,double HY_xy_x,double HY_xy_y,double YH_xy_x,double YH_xy_y, double *xc, double *yc);
	void GetQLXY(float L0,long R,int RoateStyle,float LL,float fwj,double ZH_xy_x,double ZH_xy_y,double HZ_xy_x,double HZ_xy_y,double *xc,double *yc,int Q_H_L);
	void LoadData(CString strSchemeName);
	int GetMinRfromCriterion(int mspeed,int EngineeringConditionStyle);
	int GetMinL0(int mRindex);
	CString GetLC(double LC);
	void SavePlaneCurveData();
	
	SchemeData SchemeDatass[MAXSCHEMENUMS];
	
	
	CArray<PLineCurve,PLineCurve> JDCurveElements;
	CArray<PLineCurve,PLineCurve> JDCurveElementss[MAXSCHEMENUMS];
	CArray<PCordinate,PCordinate> PtS_JD;
	CArray<PCordinate,PCordinate> PtS_3DLineZX[MAXSCHEMENUMS];;

	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_Railway3D[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_RailwayLj3D[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_RailwayLjToBP3D[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_RailwayBP3D[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_BridgeLG3D[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_BridgeQD3D[MAXSCHEMENUMS];
	CArray<PRailway3DTunnnel,PRailway3DTunnnel> PtS_Tunnel3D[MAXSCHEMENUMS];
	CArray<PTunnnelBridgeInformation,PTunnnelBridgeInformation> TunnelInfor[MAXSCHEMENUMS];
	CArray<PTunnnelBridgeInformation,PTunnnelBridgeInformation> BridgeInfor[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_RailwayZhenMu[MAXSCHEMENUMS];
	CArray<PRailway3DCordinate,PRailway3DCordinate> PtS_RailwayTieGui[MAXSCHEMENUMS];

	CArray<PPaiSuiGou,PPaiSuiGou> PtS_PaiSuiGou[MAXSCHEMENUMS];

	CArray<PLuQianHuPo,PLuQianHuPo> PtS_HuPo[MAXSCHEMENUMS];
	CArray<PLuQianHuPoTemp,PLuQianHuPoTemp> PtSHuPoTemp;
	
	
	CArray<long,long>m_borelayerID;
	CArray<PBoreLayerDraw,PBoreLayerDraw>m_BorelayerDraw;
	CArray<PBoreSectionLine,PBoreSectionLine> P_BoreSectionLine[3];
	BOOL bDrawTerrSurface;
	
	int m_minR160_L0[15][2];
	int m_minR140_L0[17][2];
	int m_minR120_L0[19][2];
	int m_minR100_L0[22][2];
	int m_minR80_L0[23][2];
	
	int m_minPriorityR160_L0[15];
	int m_minPriorityR140_L0[17];
	int m_minPriorityR120_L0[19];
	
	int  m_TotalSchemeNums;
	
	BOOL m_bHaveSaveScheme;
	
	CDesingScheme();
   	virtual ~CDesingScheme();

private:
	void GetTunnelArcCordinate(float TotalHeight, float Archeight, float WallHeight, float Width, int ArcSegmentNumber,double x1,double y1,double z1,double x2,double y2,double z2,float *mAngle);
	void GetBpJD(float H0, float Afla, float mangle, double x0, double z0,int bsignTW,int mLeftRight,double *tx,double *ty,double *tz);
	void GetDMJD(double x1, double y1, double z1,double x2, double y2, double z2,float L, float h0,double x0,double z0,int TW,int LeftRight,\
		double tx0,double ty0,double tz0,double tx1,double ty1,double tz1,double mLC,CString strJDstyle);
	float GetHBridgeHP(float L,float alfa,float mangle,double x0,double z0,int Style,double *xx,double *zz);
	int GetTW(double x,double z,float H);
	float GetH(float L, float Afla, float mAngle,double x0,double z0,int mLeftRight,double *xx,double *zz);
	void Get3DCorrdinate(double x1, double y1, double z1,double x2, double y2, double z2,float dx,float dz, float L,double *x11,double *y11,double *z11,double *x12,double *y12,double *z12,double *x21,double *y21,double *z21,double *x22,double *y22,double *z22 ,float *angle);
	void Save3DlineZXCorrdinateToDB();
	void Save3DlineZX();
	void SavePlaneCurveDataToDB();
	void InitMinR();
	void NeiChaDian(float ZHLength, double x1, double y1, double z1, double x2, double y2, double z2,double lc);
	float GetAlfa(int mTW);
		
	_RecordsetPtr m_pRecordset;
	HRESULT hr;
	_variant_t  Thevalue;  
	_variant_t RecordsAffected;
	
};

#endif // !defined(AFX_DESINGSCHEME_H__5B5184D2_8345_4FB9_A7DA_9D8B33E76043__INCLUDED_)





















