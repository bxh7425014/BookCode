// T3DSystemView.h : interface of the CT3DSystemView class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_T3DSYSTEMVIEW_H__3A8D7F07_F2EE_46F8_A484_C22327088E9C__INCLUDED_)
#define AFX_T3DSYSTEMVIEW_H__3A8D7F07_F2EE_46F8_A484_C22327088E9C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

enum { QUERY_COORDINATE,QUERY_DISTENCE,SELECTLINE,SELECTFLYPATH};
enum {GIS_VIEW_ORTHO,GIS_VIEW_PERSPECTIVE};
enum {ORTHO_ZOOMIN,ORTHO_ZOOMOUT,ORTHO_PAN,ORTHO_ZOOMORIGIN,ORTHO_ZOOMWINDOW};
enum {GIS_FLY_STATICHEIGHT,	GIS_FLY_PATHHEIGHT};

#define MAX_TEXTURENUM  500  //定义最多可同时加载的地形块数量

#include "AllocUnAlloc2D3D.h"
#include "Vector.h"	
#include "Texture.h"
#include "3DSModel.h"
#include "Delaunay.h"
#include "DXF.h"
#include "AVICapture.h"
#include "ClientCapture.h"	
#include "Snow.h"


#define SKYFRONT 0						// Give Front ID = 0
#define SKYBACK  1						// Give Back  ID = 1
#define SKYLEFT  2						// Give Left  ID = 2
#define SKYRIGHT 3						// Give Right ID = 3
#define SKYUP    4						// Give Up    ID = 4
#define SKYDOWN  5						// Give Down  ID = 5

//定义封闭区域线结构
typedef struct  
{
	int index;//封闭区索引号
	CArray<PCordinate,PCordinate> ReginPts;//线路封闭区域边界点集合
	CArray<int,int>GridID;//所属于分块ID号(分块标准按采用DEM分块大小)
	double minx;//最小x坐标
	double miny;//最小y坐标
	double maxx;//最大x坐标
	double maxy;//最大y坐标
	
}Regions,*PRegions;


typedef struct  
{
	BOOL bInRegion;//节点块是否在封闭区域(只有当节点4个角点个部不在封闭区域内才为真)
	
} BlockReginInfor,*PBlockReginInfor;

typedef struct
{
	int RegionNum;//该地形块内封闭区域总数
	CArray<int,int>GridID;//所属于分块ID号(分块标准按采用DEM分块大小)
	CArray<PCordinate,PCordinate> ReginPts;//线路封闭区域边界点集合
	double minx; //最小x坐标
	double miny;//最小y坐标
	double maxx;//最大x坐标
	double maxy;//最大y坐标
	
}BlockReginInforMe,*PBlockReginInforMe;

//定义地形块离散点集合
typedef struct
{
	CArray<PCordinate,PCordinate> ReginPts;//地形块线离散点集合(包括地形点和封闭区域点)
	int mDemBlockRow;//对应的DEM子块的行号
	int mDemBlockCol;//对应的DEM子块的列号
	
}BlockLSPts,*PBlockLSPts;


//三角形3个顶点信息
typedef struct
{
	Point Pt1;
	Point Pt2;
	Point Pt3;
}TriPt,*PTriPt;

//存储每个地形块内的三角形信息
typedef struct
{
	CArray<PTriPt,PTriPt>TriPts;
	int mDemBlockRow;//对应的DEM子块的行号
	int mDemBlockCol;//对应的DEM子块的列号
}BlockTriInfos,*PBlockTriInfos;




class CT3DSystemView : public CView
{
protected: // create from serialization only
	CT3DSystemView();
	DECLARE_DYNCREATE(CT3DSystemView)

// Attributes
public:
	CT3DSystemDoc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CT3DSystemView)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//}}AFX_VIRTUAL

// Implementation
public:
	CArray<PBlockTriInfos,PBlockTriInfos> m_BlockTriInfos;//存储每个地形块的三角形信息
	
	virtual ~CT3DSystemView();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	CArray<PCordinate,PCordinate> m_FlayPath;//存储进行飞行路径选择时所选择的一系列点坐标
	CArray<PCordinate,PCordinate> m_FlayPathTempPts;//存储临时进行飞行路径选择时所选择的一系列点坐标
	int m_flypathPtIndex; //飞行路径坐标索引号
	int m_flyPathCurrentPtIndex;//在飞行过程中记录当前路径点的索引号,用于暂停飞行器
	BOOL m_FlyPause;//是否暂停飞行

	CArray<PCordinate,PCordinate> m_TempPts;
	BOOL m_ifZoomonRoad; //标识是否沿线路方案漫游
	
	CArray<PCordinate,PCordinate> m_ReginPtsLeft;//线路封闭区域边界左侧线路点
	CArray<PCordinate,PCordinate> m_ReginPtsRight;//线路封闭区域边界右侧线路点
	CArray<PRegions,PRegions> mRegions;//线路封闭区域边界点
	
	CArray<PBlockReginInforMe,PBlockReginInforMe> *mBlockReginInforMe;//地形节点三角形是否位于封闭区域边界信息
	
	CArray<PBlockLSPts,PBlockLSPts> m_BlockLSPts;//
	
	//{{AFX_MSG(CT3DSystemView)
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnMenuOpenproject();
	afx_msg void OnDrawmodeLine();
	afx_msg void OnDrawmodeRender();
	afx_msg void OnDrawmodeTexture();
	afx_msg void OnSpacequerySet();
	afx_msg void OnQueryCoordinate();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnQueryDistence();
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags);
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnMenuStereo();
	afx_msg void OnUpdateMenuStereo(CCmdUI* pCmdUI);
	afx_msg void OnMenuProjecttionortho();
	afx_msg void OnUpdateMenuProjecttionortho(CCmdUI* pCmdUI);
	afx_msg void OnOrthoZoomIn();
	afx_msg void OnUpdateOnOrthoZoomIn(CCmdUI*  pCmdUI);   
	afx_msg void OnOrthoZoomOut();
	afx_msg void OnUpdateOnOrthoZoomOut(CCmdUI*  pCmdUI);   
	afx_msg void OnOrthoPan();
	afx_msg void OnUpdateOnOrthoPan(CCmdUI*  pCmdUI);   
	afx_msg void OnOrthoZoomOrigin();
	afx_msg void OnUpdateOnOrthoZoomOrigin(CCmdUI*  pCmdUI);   
	afx_msg void OnOrthoZoomWindow();
	afx_msg void OnUpdateOnOrthoZoomWindow(CCmdUI*  pCmdUI);   
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMenuLinedesign();
	afx_msg void OnUpdateMenuLinedesign(CCmdUI* pCmdUI);
	afx_msg void OnUpdateQueryCoordinate(CCmdUI* pCmdUI);
	afx_msg void OnUpdateQueryDistence(CCmdUI* pCmdUI);
	afx_msg void OnMenuLinesavescheme();
	afx_msg void OnUpdateMenuPerspective(CCmdUI* pCmdUI);
	afx_msg void OnMenuPerspective();
	afx_msg void OnMenuBuild3dlinemodle();
	afx_msg void OnFlyRoutinschemeline();
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg void OnFlyStop();
	afx_msg void OnMenuTunnelset();
	afx_msg void OnMenuBridgeset();
	afx_msg void OnMenuTexturebp();
	afx_msg void OnMenuTexturelj();
	afx_msg void OnMenuTextureqlhpm();
	afx_msg void OnMenuTexturetunnel();
	afx_msg void OnMenuTexturetunnelDm();
	afx_msg void OnPathManuinput();
	afx_msg void OnFlypathSave();
	afx_msg void OnFlppathInterpolation();
	afx_msg void OnFlyOpenpath();
	afx_msg void OnFlyOnoffpath();
	afx_msg void OnUpdateFlyOnoffpath(CCmdUI* pCmdUI);
	afx_msg void OnFlyStaticheight();
	afx_msg void OnUpdateFlyStaticheight(CCmdUI* pCmdUI);
	afx_msg void OnFlyRoutineheight();
	afx_msg void OnUpdateFlyRoutineheight(CCmdUI* pCmdUI);
	afx_msg void OnFlyPlaypause();
	afx_msg void OnUpdateFlyPlaypause(CCmdUI* pCmdUI);
	afx_msg void OnFlyOnestep();
	afx_msg void OnFlyViewEnlarge();
	afx_msg void OnFlyViewSmall();
	afx_msg void OnFlyHeightUp();
	afx_msg void OnFlyHeightDown();
	afx_msg void OnFlyViewDown();
	afx_msg void OnFlyViewUp();
	afx_msg void OnFlySpeedUp();
	afx_msg void OnFlySpeedDown();
	afx_msg void OnMenuModleqd();
	afx_msg void OnMenuOuptcad3dlinemodel();
	afx_msg void OnAviParameter();
	afx_msg void OnAviStart();
	afx_msg void OnAviPause();
	afx_msg void OnAviEnd();
	afx_msg void OnSavepictureSpeed();
	afx_msg void OnSavepictureContinue();
	afx_msg void OnFilePrintPreview();
	afx_msg void OnSavepictureStop();
	afx_msg void OnMenuSavescreentobmp();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
private:
	HBITMAP GetSrcBit(HDC hDC, DWORD BitWidth, DWORD BitHeight);
	BOOL SaveBmp(HBITMAP hBitmap, CString FileName);
	void RecordPictures();
	void SetPictureName();
	void recordEnd();
	void recordBegin();
	bool isRecording();
	void initMovieCapture(CAVICapture *mc);
	bool BeginMovieCapture(CString filename, int width, int height, float framerate);
	void DrawSuiGou_CAD(long index, int BPside,CStdioFile* Dxffile);
	void DrawBP_CAD(long index, int BPside,CStdioFile* Dxffile);
	void DisplayHelp();
	int FlyPathRead(char *pathfile);
	int FlyPathSave(char *pathfile);
	void DrawFlyPath();
	int GetBlockID(double x, double y);
	void GetRoadCloseRegion();
	void GetBlockEdgeJd(double block_minx, double block_miny, double block_maxx, double block_maxy, long RegionsIndex, long ReginPtsIndex, int *jdNus, Point edgePts[]);
	void WriteBlockLsPts(long Blcokrow, long Blcokcol);
	void GetBlockInregion(int blockRow, int blockCol);
	BOOL GetTriOutRegion(double x1, double y1, double x2, double y2, double x3, double y3, int blockIndex);
	void AddBlcokTri(int blockIndex, int DemblcokRow, int DemblcokCol);
	void DrawTerrainTIN(long blockIndex);
	void BuildTerrainRegionInfo();
	void GetRegionID(long index);
	void WriteRegionPts(int index, BOOL bAddorAppend,int mLeftorRight);
	void DrawTerrainDelaunay();
	void GetNorthPtangle();
	void PrintText(float x, float y, char *string);
	void DrawNorthPt();
	void makeClockList();
	void SetClockProjectionNavigate();
	void DrawClock();
	void ReLoadCenterPt();
	void DrawBridgeHL();
	void DrawCylinder(GLfloat radii, GLfloat Height, GLint segmentNum);
	void makeQLList();
	void BuildQDModelList();
	void BuildTunnelDMModelList();//创建隧道洞门显示列表
	void CalZoomSpeed(float mCurZooomLC);
	void GetCameraCorrdinate(double x1, double y1, double z1, double x2, double y2, double z2);
	void SetFLyTimer();
	void DrawTunnelDM(double x1, double y1, double z1, double x2, double y2, double z2, float H, float L, int index, int tunnelIndex, BOOL bstartDM,float mangle);
	void DrawTunnelEach(long mStartindex, long mEndIndex,int mTunnelIndex,float H,float L);
	void GetMinMaxXY_bridgeHPm(double *minx, double *miny, double *maxx, double *maxy);
	void DrawTunnel();
	void DrawSceneQD(double x1, double y1, double z1, double x2, double y2, double z2, float QDheight);
	void DrawQDHP(long Index, CString mCurrentPtstyle, float mHPangle, CString mNextPtstyle,int mBridgeIndex);
	void DrawSuiGou_Ortho(long index);
	void DrawSuiGou(long index);
	void DrawBP_Ortho(long index, int BPside);
	void DrawBP(long index, int BPside);
	void DrawRailwaythesme();
	void DrawCenterLine(long index,BOOL ifSelectLine,int SchemeIndex);
	void DrawRailwaythesme_Ortho();
	float GetOrthoX(double x);	//计算正射投影模式下x坐标
	float GetOrthoY(double y);	//计算正射投影模式下y坐标
	void ExporttotalDemToFIle(CString strFilename);
	void ReadHdata(CString strfilename);
	void GetOrthoColor(double x1,double y1,float *mColorR,float *mColorG);
	void DrawBlockOrtho(int DemBlockIndex);
	void DrawTerrain_ORTHO();
	void SetProjection();
	void mCamraUpdate();
	void CheckForMovement();
	void MoveCamera(float speed);
	void RotateView(float angle, float x, float y, float z);
	void SetViewByMouse();
	void StrafeCamera(float speed);
	void DrawSearchPoint();
	void ScreenToGL(CPoint point);
	void SetDrawMode();
	BOOL ExportBlobTextureToFIle(CString strFilename, int RowIndex, int ColIndex,int mID);
	void DrawScene();
	void SetTextureCoord(float x, float z,int mRowIndex,int mColIndex);
	int RenderQuad(int nXCenter, int nZCenter, int nSize,int mRowIndex,int mColIndex);
	void CracksPatchTop(int nXCenter, int nZCenter, int nSize,int mRowIndex,int mColIndex);
	void CracksPatchRight(int nXCenter, int nZCenter, int nSize,int mRowIndex,int mColIndex);
	void CracksPatchLeft(int nXCenter, int nZCenter, int nSize,int mRowIndex,int mColIndex);
	void CracksPatchBottom(int nXCenter, int nZCenter, int nSize,int mRowIndex,int mColIndex);
	void SetTextureCoordZoomRoad(double x, double z,int mRowIndex,int mColIndex);
	BOOL GetIfINView(int mBlockRow,int mBlockCol);
	float GetNodeError(int nXCenter, int nZCenter, int nSize, int mRowIndex, int mColIndex);
	float GetHeightValue(int X, int Y,int mRowIndex,int mColIndex);
	void UpdateQuad(int nXCenter, int nZCenter, int nSize, int nLevel,int mRowIndex,int mColIndex);
	void CalculateViewPortTriCord(double View_x, double View_z, double look_x, double look_z);
	BOOL bnTriangle(double cneterx, double cnetery, double x2, double y2, double x3, double y3, double x, double y);
	void DrawTerrainZoomonRoad();
	void InitList();
	void SetCamra(int mStyle);
	void CalcFPS();
	void DrawTerrain();
	void InitTerr();
	void Init_ArrayData();
	void getDemBlockTexture(int RowIndex, int ColIndex, int Index);
	void ReadDataFromFiles(CString strfiles, int Index);
	void ExportBlobDemToFIle(CString strFilename, int RowIndex, int ColIndex);
	void PositionCamera(double positionX, double positionY, double positionZ, double viewX, double viewY, double viewZ, double upVectorX, double upVectorY, double upVectorZ);
	void Init_data();
	CVector3 Strafe();
	CVector3 UpVector();
	CVector3 GetView();
	CVector3 GetPos();

	void SetSkyProjection(); //背景天空投影设置
	void SetSkyProjectionNavigate();//背景天空导航图投影设置
	void DrawBackground();////绘制背景天空
	void makeSkykList();
	void DrawSky();
	
	

	CDC*	    m_pDC;			
	HGLRC		m_hRC;	
	
	int m_SCREEN_WIDTH;		//屏幕宽度
	int m_SCREEN_HEIGHT;	//屏幕高度
	
	int WinViewX,WinViewY;	//存储所定义视口的宽度和高度
	BOOL bStereoAvailable;	//显卡是否支持立体显示
	
	GLfloat m_ViewWideNarrow;   //用来调整gluPerspective()函数定义平截头体的视野的角度(增大或减小)
	double m_near,m_far;	//gluPerspective()函数定义平截头体的近剪裁平面和远剪裁平面的距离
	float m_aspectRatio; //gluPerspective()函数定义的平截头体的纵横比
	float m_FrustumAngle;
	float m_viewdegree;	//观察点与视点之间的俯视角
	float m_es;
	
	//在正射投影模式下地形的x,y中心坐标和x,y方向的比例
	float m_ortho_CordinateOriginX;//在正射投影模式下地形的x中心坐标
	float m_ortho_CordinateOriginY;//在正射投影模式下地形的y中心坐标
	float m_ortho_CordinateXYScale;//在正射投影模式下地形的x,y方向的比例
	
	AllocUnAlloc2D3D m_AllocUnAlloc2D3D;//定义用于实现对一维、二维和三维数组分配和销毁内存的类变量。
	BOOL m_BhasInitOCI;//标识是否初始化OCI
	int m_nMapSize;//记录地形子块大小,渲染地形使用
	long m_Radius;//包围球半径,用来实现对地形剪裁(位于包围球半径以便的地形块不需绘制)
	float m_lodScreenError;//设定的屏幕误差

	//视景体剪裁半径
	long m_R;
	long m_r;
	
	
	//地形参数变量
	int m_loddem_StartRow;//存储调入的地形块的起始行
	int m_loddem_StartCol;//存储调入的地形块的起始列
	int m_loddem_EndRow;//存储调入的地形块的结束行
	int m_loddem_EndCol;//存储调入的地形块的结束列
	float **m_pHeight_My;//存储调入地形子块的高程点
	int m_LodDemblockNumber; //存储所加载的地形块数量
	double m_DemBlockCenterCoord[MAX_TEXTURENUM][2]; //存储所加载的地形块中心大地坐标
	int m_lodDemBlock[MAX_TEXTURENUM][4];//存储调入的地形块的行号,列号,调入的地形块数量
	bool *m_pbm_DemLod;//标识地形块是否被调入
	int **m_DemLod_My;//存储调入地形子块的行号,列号等信息
	int m_DemLodIndex[MAX_TEXTURENUM];//存储调入的地形子块的索引号
	int *m_tempDemLodIndex;//存储前一DEM子块的索引
	int *m_bsign;//标识所调入的地形块是否参与绘制(若值为1,表示调入的地形子块参与绘制,=0,表示未参与绘制,位于视景体外,被剔除掉了)
	bool **m_pbQuadMat;//标识地形子块的节点是否还需要继续分割
	float  m_maxHeight,m_minHeight;//DEM数据点的最大最小高程值
	float m_heighScale;//DEM数据点高程式缩放比例( <1:高程减小 =1:高程不变 >1:高程增大)
	bool m_bLoadInitDemData;//标识加载初始化地形和影像纹理是否成功
	
	//纹理参数变量
	double m_Texturexy[MAX_TEXTURENUM][4];//存储调入的影像纹理子块的左下和左上角x,y坐标
	int m_demTextureID[MAX_TEXTURENUM];//存储调入的影像纹理子块的纹理ID,手于绑定纹理
	int m_currebtPhramid; //存储当前影像纹理的LOD级别
	CTexture m_texturesName; //定义CTexture类的变量,用于实现影像纹理的加载 
	CTexture m_cTxtSky;//定义CTexture类的变量,用于实现背景天空纹理的加载 
	
	
	//相机参数变量 
	BOOL m_bCamraInit;//标识相机是否初始化 
	CVector3 m_vPosition;	//相机视点坐标
	CVector3 m_vView;		//相机观察点坐标
	CVector3 m_vUpVector;	//相机向中三维矢量
	CVector3 m_oldvPosition;//相机前一视点坐标
	float m_viewHeight;//视点高度
	float m_oldviewHeight;//前一视点高度
	float m_camraDistence;//相机距离 
	CVector3 m_originView;//相机初始视点坐标
	CVector3	m_originPosition;//相机初始观察点坐标
	
	
	//用于计算相机事参数的CVector3类型变量
	CVector3 m_vStrafe;		
	CVector3 View;		

	//刷新频率参数
	float mTimeElaps; //用于计算刷新频率的时间值
	int nCountFrame;//绘制的帧数
	
	//屏幕误差负反馈参数
	BOOL    m_checkt; //标识是否打开屏幕误差负反馈功能
	long	m_maxTrinum;//最大三角形数量
	long	m_minTrinum;//最小三角形数量
	float	m_k1; //放大参数
	float	m_k2;//缩小参数

	int m_Drawmode; //绘制模式(1://线框模式  2://渲晕模式	3://纹理模式)
 	BOOL m_stereo;  //是否立体模式(TRUE:立体 FALSE:非立体)
	BOOL m_bShowbreviary;//是否显示缩略视图
	
	//视景体参数
	float m_sectorStartAngle,m_sectorEndAngle;
	double m_triPtA[2],m_triPtB[2],m_triPtC[2];
	Point m_Viewpolygon[3];	 //存储视口三角扇形的三个坐标点
	//存储视口三角扇形的三个坐标点中的最大最小坐标
	double m_Viewpolygon_Minx,m_Viewpolygon_Miny,m_Viewpolygon_Maxx,m_Viewpolygon_Maxy;

	
	//渲染参数
	int  m_RenderDemblockNumber;//渲染的地形块数量
	int mCurrentTextID;//当前渲染地形块的纹理ID号
	int m_CurrentDemArrayIndex;//当前渲染地形块的数组索引

	//显示列表参数 
	GLuint m_clockList; //时钟指北针显示列表
	GLuint m_SkyList;//背景天空显示列表
	GLuint m_TerrainList;
	GLuint m_TerrainZoomroadList;
	GLuint m_NavigateList;
	GLuint m_HazardList;
	GLuint m_HazardList_Ortho;
	GLuint m_TunnelDMList;//隧道洞门显示列表

	GLuint m_FAHPList;
	GLuint m_Rail3DwayList; //线路三维模型显示列表(透视投影模式)
	GLuint m_Rail3DwayList_Ortho;//线路三维模型显示列表(正射投影模式)
	GLuint m_Rail3DwayList_Ortho_Multi;
	GLuint m_QDList;//桥梁桥墩显示列表
	GLuint m_QMList;
	GLuint m_QLanList;//桥梁栏杆显示列表
//	GLuint m_TrainList;// 火车模型的
	GLuint m_SelectBridgeList;//
	

	CalCulateF mCalF;//视景体计算公共类变量 

	//存储最大、最小高程对应戏、绿、蓝三色的颜色值
	float minZ_color_R,minZ_color_G,minZ_color_B;
	float maxZ_color_R,maxZ_color_G,maxZ_color_B;
	
	//空间查询标志参数
	int		m_shizxLength;//查询标志的十字线长度
	int		m_shuzxHeight;//查询标志的竖直线长度
	int m_QueryLineWidth;//查询标志线的宽度
	int m_QueryColorR,m_QueryColorG,m_QueryColorB;//查询标志线的颜色(红,绿,蓝)
	
	//空间查询标志
	BOOL m_bSearchCorrdinate;
	BOOL m_bSearchDistence;
	BYTE m_QueryType; //标识空间查询类别
	int m_bSearchDistencePtNums;//查询时点取的空间点数
	double pt1[3],pt2[3];//存储查询的坐标
	long m_linePtnums;//当前线路方案设计交点总数
	long m_oldlinePtnums; //原有线路方案设计交点数
	
	
	bool m_bmouseView;	//是否起用鼠标控制相机
	POINT m_oldMousePos;//前一鼠标位置
	
	//相机旋转参数
	float	m_Step_X; //相机在X方向移动的步长(鼠标控制)
	float	m_Step_Z; //相机在Z方向移动的步长(鼠标控制)
	float Derx;	//相机视点在X方向上的变化量
	float Derz;	//相机视点在Z方向上的变化量

	float m_xTrans;	//在X方向上移动的距离(键盘控制)
	float m_zTrans;	//在Z方向上移动的距离(键盘控制)
	int m_keynumber;//标识键盘按键值

	BYTE m_ViewType;   // 标识投影方式类别
	float m_ViewXYscale;//存储视口高宽比例
	float m_OrthoViewSize;//正射投影模式下视景体大小
	float m_OrthotranslateX,m_OrthotranslateY;//正射投影模式下X轴方向和Y轴方向移动的距离值
	BYTE m_OrthoZommPan; //正射投影模式下场景控制模式
	//开窗缩放时记录矩形窗口的左下和右上x,y坐标
	int m_OrthoZoomWindowRect_x1,m_OrthoZoomWindowRect_y1,m_OrthoZoomWindowRect_x2,m_OrthoZoomWindowRect_y2;
	int m_preX,m_preY; //前一坐标
	int m_currentX,m_currentY;//当前坐标
	int m_pushNumb;//按键次数
	
	BOOL b_haveMadeRail3DwayList; //是否已经有三维线路显示列表(透视投影模式下)
	BOOL b_haveMadeRail3DwayList_Ortho;//是否已经有三维线路显示列表(正射投影模式下)
	BOOL b_haveTerrainZoomroadList;///线路三维建模后是否建立了地形TIN模型的显示列表
	
	Bridge m_Bridge; //桥梁
	Tunnel  m_Tunnel; //隧道
	Railway m_Railway; //线路路基结构
	
	
	
	C3DSModel *g_3dsModels; 
	BOOL bHaveLoadModel;
	double m_QD_minx,m_QD_miny,m_QD_maxx,m_QD_maxy,m_QD_minz,m_QD_maxz; 
	double m_QM_minx,m_QM_miny,m_QM_maxx,m_QM_maxy,m_QM_minz,m_QM_maxz;	
	double m_Train_minx,m_Train_miny,m_Train_maxx,m_Train_maxy,m_Train_minz,m_Train_maxz; 
	double m_TunnelDM_minx,m_TunnelDM_miny,m_TunnelDM_maxx,m_TunnelDM_maxy,m_TunnelDM_minz,m_TunnelDM_maxz; 
	

	CTexture m_txture;
	CTexture m_cTxtureDetail;
	CTexture m_txt;
	CTexture m_cTxtDetail;
	CTexture m_LitLodtextureName;
	
	CTexture m_cTxtureBP; //路基边坡纹理
	CTexture m_cTxtureLJ;//路肩纹理
	CTexture m_cTxtureGdToLJ;//道床边坡纹理
	CTexture m_cTxtureRailway;//轨道纹理
	CTexture m_cTxtureSuigou;//水沟纹理
	CTexture m_cBridgeHpm; ////桥头下方护坡面纹理
	CTexture m_cBridgeHL; //桥栏
	CTexture m_cTxturePT;//边坡平台纹理	
	CTexture m_cTunnel;//隧道内墙纹理
	CTexture m_cTunnel_dm;//隧道洞门纹理
	CTexture m_cTunnel_dmwcBp;//隧道洞门外侧边坡纹理
	

	//飞行路径参数
	GLfloat m_StaticHeight;		//固定飞行高度
	GLfloat m_ViewUpDown;		//视角上,下倾
	int m_flyspeed;//飞行时的计时器时间间隔 
	BOOL m_PathFlag;//是否输入飞行路径
	
	int m_bridgeColorR,m_bridgeColorG,m_bridgeColorB; //桥梁颜色	
	CArray<PCordinate,PCordinate> PtBridgeHPUp;
	CArray<PCordinate,PCordinate> PtBridgeHPDown;
	
	BOOL b_haveGetRegion;//标识是否建立线路封闭区域
	
	double m_PreZooomLC;//漫游时记录前一里程
	double m_CurZooomLC;//漫游时记录当前里程
	
	CString m_3DSfilename_QD; //桥墩模型文件名称
	CString m_3DSfilename_TunnelDM; //隧道洞门模型文件名称
	
	float m_NorthPtangle; //指北针初始指向角度
	
	CDXF mdxf; //定义DXF变量
	

	vertexSet m_Vertices; //存储顶点坐标
	triangleSet m_Triangles; //存储三角形数据

	BOOL m_bSelectFlyPath; //标识在进行飞行路径的选取 
	BOOL m_ShowFlyPath; //标识是否显示飞行路径

	BYTE m_FlyHeightType;//三维漫游类型(固定高度漫游,固定高差漫游)
	

	//录制AVI参数
	int m_MovieWidth;  //AVI的宽度
	int m_MovieHeight; //AVI的高度 
	int m_MoviemaxWidth;  //AVI的最大宽度
	int m_MoviemaxHeight; //AVI的最大高度 
	int m_AVIFrame;  //AVI捕捉帧率
	CString m_AviFilename; //AVI文件名
	BOOL m_Beginrecording;       //开始录制AVI视频动画的标志
	CAVICapture *m_movieCapture; //捕捉动画
	bool m_Recording; //标识是否正在捕捉视频动画
	

//	
	//录制图像序列参数
	CClientCapture CapturedImage;
	CString m_RecordPicturename; //录制图像名称
    int		m_PictureNumbers; //录制图像的数量
	int		m_recordPictSpeed;  //采集图像的频率，即每隔多少毫秒录制一幅图像
	BOOL	m_RecordPicture;     //录像图像序列的标志
	CRect m_rcPrintRect;  //记录图像客户区大小
	
	BOOL SetupPixelFormat();
	BOOL InitializeOpenGL(CDC *pDC);
	BOOL LoadInitDemData();

	Snow m_snows;
	CWinThread* m_lpThread;
	
};

#ifndef _DEBUG  // debug version in T3DSystemView.cpp
inline CT3DSystemDoc* CT3DSystemView::GetDocument()
   { return (CT3DSystemDoc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_T3DSYSTEMVIEW_H__3A8D7F07_F2EE_46F8_A484_C22327088E9C__INCLUDED_)
