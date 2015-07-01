// T3DSystemView.cpp : implementation of the CT3DSystemView class
//

#include "stdafx.h"
#include "T3DSystem.h"

#include "T3DSystemDoc.h"
#include "T3DSystemView.h"
#include "MainFrm.h"
#include "OpenProject.h"
#include "SpaceSearchSet.h"
#include "PLaneRL0.h"
#include "TunncelPropertySet.h"
#include "BridgeSet.h"
#include "TextureBP.h"
#include "TextureLJ.h"
#include "TextureQLHpm.h"
#include "TextureTunnel.h"
#include "TextureTunnelDm.h"
#include "ModelMangerQD.h"
#include "DXF.h"
#include "AviParameter.h"
#include "RecordPictureSpeed.h"


#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define PAI 3.1415926       //定义π常量
#define HDANGLE  52.706     //定义1弧度等于多少度(1弧度=180/PAI)
#define PAI_D180  PAI/180   //定义1度等于多少弧度(1度=PAI/180弧度)

char g_sMediaPath[512];
UINT g_Texture[100] = {0};

GLfloat LightAmbient  [] = { 0.5f, 0.5f, 0.5f, 1.0f };
GLfloat LightDiffuse  [] = { 1.0f, 1.0f, 1.0f, 1.0f };
GLfloat LightPosition [] = { 0.0f, 0.0f, 2.0f, 1.0f };

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView

IMPLEMENT_DYNCREATE(CT3DSystemView ,  CView)

BEGIN_MESSAGE_MAP(CT3DSystemView ,  CView)
	//{{AFX_MSG_MAP(CT3DSystemView)
	ON_WM_CREATE()
	ON_WM_SIZE()
	ON_WM_ERASEBKGND()
	ON_COMMAND(ID_MENU_OPENPROJECT ,  OnMenuOpenproject)
	ON_COMMAND(ID_DRAWMODE_LINE ,  OnDrawmodeLine)
	ON_COMMAND(ID_DRAWMODE_RENDER ,  OnDrawmodeRender)
	ON_COMMAND(ID_DRAWMODE_TEXTURE ,  OnDrawmodeTexture)
	ON_COMMAND(ID_SPACEQUERY_SET ,  OnSpacequerySet)
	ON_COMMAND(ID_QUERY_COORDINATE ,  OnQueryCoordinate)
	ON_WM_LBUTTONDOWN()
	ON_COMMAND(ID_QUERY_DISTENCE ,  OnQueryDistence)
	ON_WM_MOUSEMOVE()
	ON_WM_KEYDOWN()
	ON_WM_RBUTTONDOWN()
	ON_COMMAND(ID_MENU_STEREO, OnMenuStereo)
	ON_UPDATE_COMMAND_UI(ID_MENU_STEREO, OnUpdateMenuStereo)
	ON_COMMAND(ID_MENU_PROJECTTIONORTHO, OnMenuProjecttionortho)
	ON_UPDATE_COMMAND_UI(ID_MENU_PROJECTTIONORTHO, OnUpdateMenuProjecttionortho)
	ON_COMMAND(ID_ORTHO_ZOOMIN, OnOrthoZoomIn)
	ON_UPDATE_COMMAND_UI(ID_ORTHO_ZOOMIN, OnUpdateOnOrthoZoomIn)
	ON_COMMAND(ID_ORTHO_ZOOMOUT, OnOrthoZoomOut)
	ON_UPDATE_COMMAND_UI(ID_ORTHO_ZOOMOUT, OnUpdateOnOrthoZoomOut)
	ON_COMMAND(ID_ORTHO_PAN, OnOrthoPan)
	ON_UPDATE_COMMAND_UI(ID_ORTHO_PAN, OnUpdateOnOrthoPan)
	ON_COMMAND(ID_ORTHO_ZOOMORIGIN, OnOrthoZoomOrigin)
	ON_UPDATE_COMMAND_UI(ID_ORTHO_ZOOMORIGIN, OnUpdateOnOrthoZoomOrigin)
	ON_COMMAND(ID_ORTHO_ZOOWINDOW, OnOrthoZoomWindow)
	ON_UPDATE_COMMAND_UI(ID_ORTHO_ZOOWINDOW, OnUpdateOnOrthoZoomWindow)
	ON_WM_LBUTTONUP()
	ON_COMMAND(ID_MENU_LINEDESIGN, OnMenuLinedesign)
	ON_UPDATE_COMMAND_UI(ID_MENU_LINEDESIGN, OnUpdateMenuLinedesign)
	ON_UPDATE_COMMAND_UI(ID_QUERY_COORDINATE, OnUpdateQueryCoordinate)
	ON_UPDATE_COMMAND_UI(ID_QUERY_DISTENCE, OnUpdateQueryDistence)
	ON_COMMAND(ID_MENU_LINESAVESCHEME, OnMenuLinesavescheme)
	ON_UPDATE_COMMAND_UI(ID_MENU_PERSPECTIVE, OnUpdateMenuPerspective)
	ON_COMMAND(ID_MENU_PERSPECTIVE, OnMenuPerspective)
	ON_COMMAND(ID_MENU_BUILD3DLINEMODLE, OnMenuBuild3dlinemodle)
	ON_COMMAND(ID_FLY_ROUTINSCHEMELINE, OnFlyRoutinschemeline)
	ON_WM_TIMER()
	ON_COMMAND(ID_FLY_STOP, OnFlyStop)
	ON_COMMAND(ID_MENU_TUNNELSET, OnMenuTunnelset)
	ON_COMMAND(ID_MENU_BRIDGESET, OnMenuBridgeset)
	ON_COMMAND(ID_MENU_TEXTUREBP, OnMenuTexturebp)
	ON_COMMAND(ID_MENU_TEXTURELJ, OnMenuTexturelj)
	ON_COMMAND(ID_MENU_TEXTUREQLHPM, OnMenuTextureqlhpm)
	ON_COMMAND(ID_MENU_TEXTURETUNNEL, OnMenuTexturetunnel)
	ON_COMMAND(ID_MENU_TEXTURETUNNEL_DM, OnMenuTexturetunnelDm)
	ON_COMMAND(ID_PATH_MANUINPUT, OnPathManuinput)
	ON_COMMAND(ID_FLYPATH_SAVE, OnFlypathSave)
	ON_COMMAND(ID_FLPPATH_INTERPOLATION, OnFlppathInterpolation)
	ON_COMMAND(ID_FLY_OPENPATH, OnFlyOpenpath)
	ON_COMMAND(ID_FLY_ONOFFPATH, OnFlyOnoffpath)
	ON_UPDATE_COMMAND_UI(ID_FLY_ONOFFPATH, OnUpdateFlyOnoffpath)
	ON_COMMAND(ID_FLY_STATICHEIGHT, OnFlyStaticheight)
	ON_UPDATE_COMMAND_UI(ID_FLY_STATICHEIGHT, OnUpdateFlyStaticheight)
	ON_COMMAND(ID_FLY_ROUTINEHEIGHT, OnFlyRoutineheight)
	ON_UPDATE_COMMAND_UI(ID_FLY_ROUTINEHEIGHT, OnUpdateFlyRoutineheight)
	ON_COMMAND(ID_FLY_PLAYPAUSE, OnFlyPlaypause)
	ON_UPDATE_COMMAND_UI(ID_FLY_PLAYPAUSE, OnUpdateFlyPlaypause)
	ON_COMMAND(ID_FLY_ONESTEP, OnFlyOnestep)
	ON_COMMAND(ID_FLY_VIEW_ENLARGE, OnFlyViewEnlarge)
	ON_COMMAND(ID_FLY_VIEW_SMALL, OnFlyViewSmall)
	ON_COMMAND(ID_FLY_HEIGHT_UP, OnFlyHeightUp)
	ON_COMMAND(ID_FLY_HEIGHT_DOWN, OnFlyHeightDown)
	ON_COMMAND(ID_FLY_VIEW_DOWN, OnFlyViewDown)
	ON_COMMAND(ID_FLY_VIEW_UP, OnFlyViewUp)
	ON_COMMAND(ID_FLY_SPEED_UP, OnFlySpeedUp)
	ON_COMMAND(ID_FLY_SPEED_DOWN, OnFlySpeedDown)
	ON_COMMAND(ID_MENU_MODLEQD, OnMenuModleqd)
	ON_COMMAND(ID_MENU_OUPTCAD3DLINEMODEL, OnMenuOuptcad3dlinemodel)
	ON_COMMAND(ID_AVI_PARAMETER, OnAviParameter)
	ON_COMMAND(ID_AVI_START, OnAviStart)
	ON_COMMAND(ID_AVI_PAUSE, OnAviPause)
	ON_COMMAND(ID_AVI_END, OnAviEnd)
	ON_COMMAND(ID_SAVEPICTURE_SPEED, OnSavepictureSpeed)
	ON_COMMAND(ID_SAVEPICTURE_CONTINUE, OnSavepictureContinue)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, OnFilePrintPreview)
	ON_COMMAND(ID_SAVEPICTURE_STOP, OnSavepictureStop)
	ON_COMMAND(ID_MENU_SAVESCREENTOBMP, OnMenuSavescreentobmp)
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT ,  CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT ,  CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW ,  CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView construction/destruction

PBlockTriInfos tempBlockTriInfos;

class GetBlockTriangles
{
public:
	GetBlockTriangles(long blockIndex) : MyblockIndex(blockIndex){}
	void operator()(const triangle& tri) const
	{
		
		const vertex * v0 = tri.GetVertex(0);
		const vertex * v1 = tri.GetVertex(1);
		const vertex * v2 = tri.GetVertex(2);
		
		
		PTriPt tempptript=new TriPt;
		tempptript->Pt1.x=v0->GetX(); tempptript->Pt1.y=v0->GetY();	tempptript->Pt1.z=v0->GetZ();
		tempptript->Pt2.x=v1->GetX(); tempptript->Pt2.y=v1->GetY();	tempptript->Pt2.z=v1->GetZ();
		tempptript->Pt3.x=v2->GetX(); tempptript->Pt3.y=v2->GetY();	tempptript->Pt3.z=v2->GetZ();
		tempBlockTriInfos->TriPts.Add(tempptript);
		
		
		
		
	}
protected:
	long MyblockIndex;
};


CT3DSystemView::CT3DSystemView()
{
	Init_data() ; // 初始化相关变量
	m_lpThread  = NULL;
	
}

CT3DSystemView::~CT3DSystemView()
{
}

BOOL CT3DSystemView::PreCreateWindow(CREATESTRUCT& cs)
{
	//设置窗口类型
	cs.style|=WS_CLIPCHILDREN|WS_CLIPSIBLINGS;
	return CView::PreCreateWindow(cs);
	
}

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView drawing

//绘制三维场景
void CT3DSystemView::OnDraw(CDC* pDC)
{
	CT3DSystemDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	
//	m_snows.DrawSnow ();
//	return;

	if (pDC->IsPrinting()) 
	{
		CRect rcDIB;
		GetClientRect(&rcDIB);
		
		m_rcPrintRect=rcDIB;
		
		pDC->DPtoLP(&rcDIB);
		
		rcDIB.right = rcDIB.Width();
		rcDIB.bottom = rcDIB.Height();
		
		// 获得打印机页面尺寸(像素)
		int cxPage = pDC->GetDeviceCaps(HORZRES);
		int cyPage = pDC->GetDeviceCaps(VERTRES);
		// 获得打印机每英寸上的像素个数
		int cxInch = pDC->GetDeviceCaps(LOGPIXELSX);
		int cyInch = pDC->GetDeviceCaps(LOGPIXELSY);
		
		CRect rcDest;
		rcDest.top = rcDest.left = 0;
		rcDest.bottom = (int)(((double)rcDIB.Height() * cxPage * cyInch)
			/ ((double)rcDIB.Width() * cxInch));
		rcDest.right = cxPage;
		CapturedImage.OnDraw(pDC->m_hDC, &rcDest, &rcDIB);
	}
	else 
	{
		wglMakeCurrent( pDC->m_hDC, m_hRC );// 使 RC 与当前 DC 相关联
		DrawScene() ; // 场景绘制
		if (m_movieCapture != NULL && m_Recording)
		{
			m_movieCapture->captureFrame();
		}
		glFlush();	
		::SwapBuffers(m_pDC->GetSafeHdc());	//交换缓冲区	 
	}
	

}

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView printing

BOOL CT3DSystemView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CT3DSystemView::OnBeginPrinting(CDC* /*pDC*/ ,  CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CT3DSystemView::OnEndPrinting(CDC* /*pDC*/ ,  CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView diagnostics

#ifdef _DEBUG
void CT3DSystemView::AssertValid() const
{
	CView::AssertValid();
}

void CT3DSystemView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CT3DSystemDoc* CT3DSystemView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CT3DSystemDoc)));
	return (CT3DSystemDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CT3DSystemView message handlers

int CT3DSystemView::OnCreate(LPCREATESTRUCT lpCreateStruct) 
{
	if (CView::OnCreate(lpCreateStruct) == -1)
		return -1;
	
	//获取客户区的设备描述表
	m_pDC=new CClientDC(this); 

	//初始化OpenGL
	InitializeOpenGL(m_pDC); 
	InitList() ; // 初始化显示列表	
	
	return 0; 

}

BOOL CT3DSystemView::InitializeOpenGL(CDC *pDC)
{
	//进行opengl的初始化工作
	m_pDC=pDC; 
	//首先把DC的象素格式调整为指定的格式，以便后面对DC的使用
	SetupPixelFormat(); 
	//根据DC来创建RC
	m_hRC=::wglCreateContext(m_pDC->GetSafeHdc()); 
	//设置当前的RC，以后的画图操作都画在m_pDC指向的DC上
	::wglMakeCurrent(m_pDC->GetSafeHdc() , m_hRC); 
	

	//判断当前系统的OpenGL版本是否支持多重纹理扩展  判断显卡是否支持该扩展
	glActiveTextureARB		= (PFNGLACTIVETEXTUREARBPROC)		wglGetProcAddress("glActiveTextureARB");
	glMultiTexCoord2fARB	= (PFNGLMULTITEXCOORD2FARBPROC)		wglGetProcAddress("glMultiTexCoord2fARB");
	
	if(!glActiveTextureARB || !glMultiTexCoord2fARB)
	{
		MessageBox("当前OpenGL版本较低，不支持多重纹理\n扩展功能，请下载安装新的版本！" ,  "多重纹理扩展错误" ,  MB_ICONSTOP);
		return FALSE;
	}

	m_cTxtSky.LoadGLTextures("Sky.bmp");	//加载背景天空的纹理影像

	m_cTxtureBP.LoadGLTextures("纹理\\边坡防护\\拱形护坡1.bmp");
	m_cTxtureLJ.LoadGLTextures("纹理\\路肩\\1.bmp");
	m_cTxtureGdToLJ.LoadGLTextures("纹理\\路肩\\10.bmp");
	m_cTxtureSuigou.LoadGLTextures("纹理\\水沟\\水沟1.bmp");
	m_cTxturePT.LoadGLTextures("纹理\\边坡平台\\边坡平台1.bmp");
	m_cTxtureRailway.LoadGLTextures("纹理\\铁路\\铁路1.bmp");
	m_cBridgeHpm.LoadGLTextures("纹理\\桥下方护坡面\\1.bmp");//桥头下方护坡面纹理
	m_cTunnel.LoadGLTextures("纹理\\隧道内墙\\1.bmp"); //隧道内墙纹理
	m_cTunnel_dm.LoadGLTextures("纹理\\隧道洞门\\2.bmp");//隧道洞门纹理
	m_cTunnel_dmwcBp.LoadGLTextures("纹理\\隧道洞门外侧边坡\\2.bmp");//隧道洞门外侧边坡*/

	
	return TRUE;

	

}

BOOL CT3DSystemView::SetupPixelFormat()
{
	//初始化象素格式以及选取合适的格式来创建RC
	PIXELFORMATDESCRIPTOR pfd = { 
		sizeof(PIXELFORMATDESCRIPTOR) ,  // pfd结构的大小 
			1 ,  // 版本号
			PFD_DRAW_TO_WINDOW | // 支持在窗口中绘图 
			PFD_SUPPORT_OPENGL | // 支持 OpenGL 
			PFD_DOUBLEBUFFER| // 双缓存模式
			PFD_STEREO |  //支持立体模式
			PFD_TYPE_RGBA ,  // RGBA 颜色模式 
			24 ,  // 24 位颜色深度
			0 ,  0 ,  0 ,  0 ,  0 ,  0 ,  // 忽略颜色位 
			0 ,  // 没有非透明度缓存 
			0 ,  // 忽略移位位 
			0 ,  // 无累加缓存 
			0 ,  0 ,  0 ,  0 ,  // 忽略累加位 
			32 ,  // 32 位深度缓存 
			0 ,  // 无模板缓存 
			0 ,  // 无辅助缓存 
			PFD_MAIN_PLANE ,  // 主层 
			0 ,  // 保留 
			0 ,  0 ,  0 // 忽略层 , 可见性和损毁掩模 
	}; 
	//在DC中选择合适的象素格式并返回索引号
	int pixelformat;
	pixelformat=::ChoosePixelFormat(m_pDC->GetSafeHdc() , &pfd);
	if (pixelformat==0)
	{
		MessageBox("选择像素格式失败!" , "设置像素格式" , MB_ICONERROR);
		return FALSE;
	}
	//设置指定象素格式
	if (::SetPixelFormat(m_pDC->GetSafeHdc() , pixelformat , &pfd)==FALSE)
	{
		MessageBox("设置像素格式失败!" , "设置像素格式" , MB_ICONERROR);
		return FALSE;
	}

	

	//测试当前设置和硬件显卡是否支持立体模式
	unsigned char ucTest;
    glGetBooleanv (GL_STEREO ,  &ucTest);  
    if (!ucTest) 
	{
		return 1;
	}
	
	
	if((pfd.dwFlags & PFD_STEREO)==0)
		bStereoAvailable=FALSE ; // 显卡不支持立体模式
	else
		bStereoAvailable=TRUE;
	
	CString stt[5];
	if(bStereoAvailable==FALSE) //如果显卡不支持立体模式，给出可能的错误原因
	{
		stt[0]="①.图形卡不支持立体缓冲;\n";
		stt[1]="②.图形卡驱动程序不支持立体缓冲;\n";
		stt[2]="③.只有在特定的解析度或刷新率设置下 , 才可以支持立体缓冲;\n";
		stt[3]="④.立体缓冲需要特定的驱动配置以激活;";
		stt[4].Format("立体模式未被接受.可能有以下原因:\n%s%s%s%s" , stt[0] , stt[1] , stt[2] , stt[3]);
		MessageBox(stt[4] , "立体模式设置" , MB_ICONINFORMATION);
	}
	
	
}

void CT3DSystemView::OnSize(UINT nType ,  int cx ,  int cy) 
{
	CView::OnSize(nType ,  cx ,  cy);


	if(cy>0)
	{
		WinViewX = cx ; // 视口宽度
		WinViewY = cy ; // 视口高度
		glViewport(0 , 0 , cx , cy); //设置视口大小
        m_aspectRatio = (float)cx/(float)cy ; // 视口的横纵比例
		glMatrixMode(GL_PROJECTION); //将当前矩阵设置为投影矩阵,指明当前矩阵为GL_PROJECTION 
		glLoadIdentity();    //将当前矩阵置换为单位阵        
		gluPerspective(50.0 + m_ViewWideNarrow , m_aspectRatio , m_near , m_far);		//:fovy=50.0 + m_ViewWideNarrow , 是视野角度
		m_FrustumAngle=2*asin(0.5*m_aspectRatio*tan((50.0 + m_ViewWideNarrow)*PAI_D180))*HDANGLE;
		m_es=m_SCREEN_HEIGHT*cos(m_viewdegree*PAI_D180)/(2*tan(m_FrustumAngle/2.0*PAI_D180));
		glMatrixMode(GL_MODELVIEW);	//将当前矩阵设置为模型矩阵		
		glLoadIdentity();	//将当前矩阵置换为单位阵 
	}			
}

//清除背景
BOOL CT3DSystemView::OnEraseBkgnd(CDC* pDC) 
{
	return TRUE;	
}

//初始化相关变量
void CT3DSystemView::Init_data()
{
	m_ViewWideNarrow = 0.0f;	//初始飞行视口宽窄变量
	CWindowDC dc=NULL;
	m_SCREEN_WIDTH = ::GetDeviceCaps(dc , HORZRES);	//屏幕宽度
	m_SCREEN_HEIGHT = ::GetDeviceCaps(dc , VERTRES);	//屏幕高度
	m_near=1;	//观察点与近侧剪裁平面的距离
	m_far=5000 ; // 观察点与远侧剪裁平面的距离

	m_currebtPhramid=0 ; // 当前影像纹理的LOD级别
	mTimeElaps=0 ; // 用于计算帧率的时间值
	
	m_stereo=FALSE; //关闭立体模式
	m_Drawmode=3 ; // 绘制模式(1://线框模式  2://渲晕模式	3://纹理模式)
	
	
	m_bCamraInit=FALSE ; // 相机未初始化
    m_bLoadInitDemData=FALSE ; // 地形和影像数据未调入 
	m_LodDemblockNumber=0 ; // 加载地形块数量初始为0
	m_RenderDemblockNumber=0 ; // 渲染地形块数量初始为0
	
	//相机参数向上矢量
	m_vUpVector.x=0; 
	m_vUpVector.y=1;
	m_vUpVector.z=0;
	
	
	m_viewdegree=0 ; // 初始视角增量 
	m_viewHeight=m_oldviewHeight=10 ; // 相机初始高度
	m_camraDistence=80 ; // 双目立体模式下的左右相机初始间距
	m_heighScale=1.0 ; // 高程缩放系数

	m_Radius=50000 ; // 包围球半径
	m_R=5500 ; // 
	m_r=3500 ; // 

	m_BhasInitOCI=FALSE;	//初始OCI未初始化
	mTimeElaps=0 ; // 用于计算帧率的时间值
	
	m_maxHeight=-9999 ; // 初始最大高程
	m_minHeight=9999 ; // 初始最小高程

	//DE财形最大高程和最小高程对应的颜色初始值
	minZ_color_R=0;minZ_color_G=1;minZ_color_B=0 ; // 绿色
	maxZ_color_R=1;maxZ_color_G=0;maxZ_color_B=0 ; // 红色
	
// 	m_bShowbreviary=TRUE ; // 是否显示缩略图

	m_shizxLength=20 ; // 查询标志十字线长度
	m_shuzxHeight=45 ; // 查询标志竖直线高度
	m_QueryLineWidth=1 ; // 查询标志线的宽度
	m_QueryColorR=255 ; // 查询标志线的颜色(红)
	m_QueryColorG=255 ; // 查询标志线的颜色(绿)
	m_QueryColorB=0 ; // 查询标志线的颜色(蓝)
	m_bSearchDistencePtNums=0;

	Derx=0;	//相机在X方向上的初始变化量
	Derz=0;	//相机在Z方向上的初始变化量
	m_Step_X=5.0;	//相机在X方向移动的步长初始值(鼠标控制)
	m_Step_Z=5.0;	//相机在Z方向移动的步长初始值(鼠标控制)
	m_xTrans=0;	//在X方向上移动的步长(键盘控制)
	m_zTrans=0;	//在Z方向上移动的步长(键盘控制)

	m_OrthoViewSize=0.3;//正射投影模式下视景体大小
	m_OrthotranslateX=m_OrthotranslateY=0;//正射投影X轴方向和Y轴方向移动的距离初值

	m_ViewType=GIS_VIEW_PERSPECTIVE;

	b_haveMadeRail3DwayList=FALSE; //是否已经有三维线路显示列表(透视投影模式下)
	b_haveMadeRail3DwayList_Ortho=FALSE;//是否已经有三维线路显示列表(正射投影模式下)
	b_haveTerrainZoomroadList=FALSE; //线路三维建模后是否建立了地形TIN模型的显示列表

	//初始飞行固定高度变量
	m_StaticHeight = 80.0f;
	
	//初始飞行视口上下倾变量
	m_ViewUpDown = 0.0f;
	
	//初始飞行视口宽窄变量
	m_ViewWideNarrow = 0.0f;
	
	m_flyspeed=10;//飞行速度
	
	
	m_PreZooomLC=m_CurZooomLC=0;//漫游时前一里程和当前里程

	b_haveGetRegion=FALSE;//标识是否建立线路封闭区域
	
	
	m_bridgeColorR=0; //桥梁栏杆颜色
	m_bridgeColorG=0;//桥梁栏杆颜
	m_bridgeColorB=255;//桥梁栏杆颜
	m_Bridge.m_Bridge_HLHeight=2.0; //桥梁栏杆高
	m_Bridge.m_Bridge_HLSpace=10;//桥梁栏杆间距
	m_Bridge.m_Bridge_HLWidth=3.0;//桥梁栏杆宽度
    m_Bridge.m_Bridge_QDSpace=40;//桥墩间距
	m_Bridge.m_Bridge_HPangle=45;//桥头护坡倾角
	m_3DSfilename_QD=".\\模型\\桥梁墩台\\刚架式柔性墩\\模型文件.3DS";
	
	
	m_Railway.m_Railway_width=2.5;//路基断面总宽度
	m_Railway.m_Lj_width=0.8;//路肩宽度
	m_Railway.m_GuiMianToLujianWidth=0.6;//碴肩至碴脚的高度
	m_Railway.m_Lj_Dh=m_Railway.m_GuiMianToLujianWidth*(1/1.75);//铁轨到碴肩的距离
	m_Railway.m_TieGui_width=1.435;//铁轨间距
	
	m_Tunnel.height=6.55; //隧道总高度
	m_Tunnel.Archeight=2;//隧道圆弧高度
	m_Tunnel.WallHeight=m_Tunnel.height-m_Tunnel.Archeight;////隧道立墙高度
	m_Tunnel.ArcSegmentNumber=20;//隧道圆弧分段数
	m_Tunnel.H=1.5;//隧道圆弧顶端再向上的高度(m_Tunnel.height+m_Tunnel.H=隧道洞门总高度)
	m_Tunnel.L=4.0;//隧道洞门顶端左右两侧延伸的宽度 
	m_Tunnel.width=m_Railway.m_Railway_width+2*(m_Railway.m_Lj_width+m_Railway.m_GuiMianToLujianWidth);
	
	
	m_shizxLength=20;
	m_shuzxHeight=45;

	m_NorthPtangle=90;//指北针初始指向角度(90度,即表示正北方向,在三维空间中则指向Z员负方向,即指向屏幕里面)
	
	m_bShowbreviary=TRUE;//显示缩略视图

	m_PathFlag=FALSE;//是否成功打开飞行路径
	m_FlyPause=FALSE;//标识是否暂停飞行
	
	
	m_3DSfilename_QD=".\\模型\\桥梁墩台\\刚架式柔性墩\\模型文件.3DS";
	
	
	DeleteFile("c:\\TEMP.AVI"); //删除c:\\TEMP.AVI文件
    m_AviFilename="c:\\TEMP.AVI"; //AVI动画初始文件名
	m_AVIFrame=15; //AVI频率
	m_Recording=false; //标识是否正在捕捉视频动画
	
	m_RecordPicture=FALSE;     //录像图像序列的标志	
    m_recordPictSpeed=100;	//录制图像的帧率
	m_PictureNumbers=0; //录制图像数量
	
}

//地形子块和影像子块的调入
BOOL CT3DSystemView::LoadInitDemData()
{
	if(theApp.bLoadImage==FALSE)//如果影像加载失败 , 返回 
        return FALSE;

	if(theApp.bLoginSucceed==FALSE) //如果数据库连接失败 , 返回
		return FALSE;
	if(m_bLoadInitDemData==TRUE)	//如果初始地形和影像子块已调入成功 , 返回
		return TRUE;

	if(theApp.m_BlockCols<=0 || theApp.m_BlockRows<=0) //如果地形块的总行数或总列数<=0 , 返回
		return FALSE;
	else  //重新定义二维数组m_DemLod_My的大小 , 并为其分配内存
		m_AllocUnAlloc2D3D.Alloc2D_int(m_DemLod_My , theApp.m_BlockCols*theApp.m_BlockRows+1 , 3);
	
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	
	//在正射投影模式下地形的x , y中心坐标和x , y方向的比例
	m_ortho_CordinateOriginX=0.5;
	m_ortho_CordinateXYScale=(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);
	m_ortho_CordinateOriginY=m_ortho_CordinateOriginX*m_ortho_CordinateXYScale;
	
	double m_Sphere_x , m_Sphere_y;
	m_Sphere_x=(theApp.m_DemRightUp_x+theApp.m_DemLeftDown_x)/2.0 ; // 包围球中心x坐标
	m_Sphere_y=(theApp.m_DemRightUp_y+theApp.m_DemLeftDown_y)/2.0 ; // 包围球中心y坐标
	
	
	CString strsql;
	int mRowId , mColID;

	//打开数据库表dem_block
	strsql="select *  from  dem_block order by 行号 , 列号";
	if(m_Recordset->State)	//如果m_Recordset已打开
		m_Recordset->Close() ; // 关闭
	try
	{
		m_Recordset->Open(_bstr_t(strsql) , (IDispatch*)(theApp.m_pConnection) , adOpenDynamic , adLockOptimistic , adCmdText);    
	}
	catch(_com_error& e)	//错误处理		
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s" , e.ErrorMessage());
		AfxMessageBox(errormessage , MB_ICONSTOP , 0);
		m_Recordset->Close() ; // 关闭记录集
		return FALSE ; // 返回
	} 
	
	long mcount;
    m_LodDemblockNumber=0 ; // 加载地形块数量
	
	while(!m_Recordset->adoEOF)//如果没有到数据库尾
	{
		
		Thevalue = m_Recordset->GetCollect("中心坐标X"); 
		double m_demblock_centerx=(double)Thevalue ;	//得到DEM子块中心x坐标
		
		Thevalue = m_Recordset->GetCollect("中心坐标Y"); 
		double m_demblock_centery=(double)Thevalue;	//得到DEM子块中心y坐标
		
		//计算DEM子块中心与包围球中心距离
		double distence=sqrt((m_Sphere_x-m_demblock_centerx)*(m_Sphere_x-m_demblock_centerx)+
			(m_Sphere_y-m_demblock_centery)*(m_Sphere_y-m_demblock_centery));

		if(distence<m_Radius)//如果DEM子块中心与包围球中心距离<设置的包围球半径 , 则加载该地形子块
		{
			Thevalue = m_Recordset->GetCollect("行号"); 
			mRowId=(long)Thevalue;
			
			Thevalue = m_Recordset->GetCollect("列号"); 
			mColID=(long)Thevalue;
			//存储所加载的地形块中心大地x , y坐标
			m_DemBlockCenterCoord[m_LodDemblockNumber][0]=m_demblock_centerx-theApp.m_DemLeftDown_x;
			m_DemBlockCenterCoord[m_LodDemblockNumber][1]=-(m_demblock_centery-theApp.m_DemLeftDown_y);
			
			m_lodDemBlock[m_LodDemblockNumber][0]=mRowId ; // 调入的地形块的行号
			m_lodDemBlock[m_LodDemblockNumber][1]=mColID ; // 调入的地形块的列号
			m_lodDemBlock[m_LodDemblockNumber][2]=m_LodDemblockNumber ; // 调入的地形块数量
			
			mcount=(mRowId-1)*theApp.m_BlockCols+mColID ; // 计算调入的地形块在所有DEM数据块中的索引
			m_DemLodIndex[m_LodDemblockNumber]=mcount ; // 记录调入的第m_LodDemblockNumber地形子块的索引 
			m_DemLod_My[mcount][0]=mRowId;	//调入的地形块的行号
			m_DemLod_My[mcount][1]=mColID;	//调入的地形块的列号
			m_DemLod_My[mcount][2]=m_LodDemblockNumber;	//调入的地形块数量
			m_pbm_DemLod[mcount]=true;
			if(m_LodDemblockNumber==0)//如果是第一次调入
			{
				m_loddem_StartRow=mRowId ; // 存储调入的地形块的起始行号
				m_loddem_StartCol=mColID ; // 存储调入的地形块的起始列号
			}
			m_LodDemblockNumber++; //调入地形块数量+1
		}

		if(mRowId>(theApp.m_BlockRows/2.0+m_Radius/theApp.m_Dem_BlockWidth) && mColID>(theApp.m_BlockCols/2.0+m_Radius/theApp.m_Dem_BlockWidth) && distence>m_Radius)
			break;
			m_Recordset->MoveNext() ; // 
	}
	m_Recordset->Close() ; // 关闭数据库表

	m_loddem_EndRow=mRowId ; // 存储调入的地形块的结束行号
	m_loddem_EndCol=mColID ; // 存储调入的地形块的结束列号

    if(m_LodDemblockNumber<=0) //如果加载地形块数量<=0 , 表示调入失败 , 返回
		return FALSE ; // 返回
	

    m_tempDemLodIndex=new int[m_LodDemblockNumber];
	//重新定义bsign数组大小(若值为1,表示调入的地形子块参与绘制,=0,表示未参与绘制,被剔除掉了)
	m_bsign=new int[m_LodDemblockNumber];
	
	CString strtempfile="c:\\tempdem.txt" ; // 临时ASCII文件 , 用于存储从数据库中读取的DEM子块数据
	ExporttotalDemToFIle(strtempfile);//从数据库中读取所有高程数据到数组中,是为了用来内插高程时用的	
	ReadHdata(strtempfile);//读取DEM所有高种点到全局数组theApp.m_DemHeight[]中

	float iPer=100.0/m_LodDemblockNumber;
	for( int i=0;i<m_LodDemblockNumber;i++)
	{
		//将第m_lodDemBlock[i][0]行和第m_lodDemBlock[i][1]列的大二进制(BLOB)
		//DEM数据从数据库中读取并写入临时ASCII文件中
		ExportBlobDemToFIle(strtempfile , m_lodDemBlock[i][0] , m_lodDemBlock[i][1]);
		ReadDataFromFiles(strtempfile , i) ; // 从临时的ASCII文件读取DEM数据点到高程
	 	getDemBlockTexture(m_lodDemBlock[i][0] , m_lodDemBlock[i][1] , i) ; // 读取并加载对应DEM地形子块的影像纹理
		strsql.Format("正在加载地形与影像纹理数据 , 请稍候... , 已完成%.2f%s" , (i+1)*iPer , "%");
		pMainFrame->Set_BarText(4 , strsql , 0); 
	}
	pMainFrame->Set_BarText(4 , "加载地形与影像纹理数据完成!" , 0); 
	pMainFrame->Set_BarText(5 , " " , 60) ; // 加载完成，清空进度信息，并隐藏进度条控件
	m_bLoadInitDemData=TRUE ; // 数据加载成功

	double	mCx=(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x)/2.0 ; // 初始相机视点X坐标(位于地形的中心)
	double	mCz=-800 ; // 初始相机视点Z坐标

	if(m_bCamraInit==FALSE)//如果相机没有初始化
	{
		m_bCamraInit=TRUE ; // 标识相机初始化
		float mDis=100 ; // 视点与观察点初始距离

		//初始化相机 , 并记录其各参数
		PositionCamera(
			mCx , 
			m_viewHeight+m_maxHeight*m_heighScale*1.0 , 
			mCz , 
			mCx , 
			m_viewHeight+m_maxHeight*m_heighScale*1.0-mDis*tan(m_viewdegree*PAI_D180) , 
			mCz-mDis , 
			0 , 1 , 0);
		
		//相机初始视点坐标
		m_originPosition=CVector3(mCx ,  m_viewHeight+m_maxHeight*m_heighScale*1.0 ,  mCz);
		//相机初始观察点坐标
		m_originView=CVector3(mCx , m_viewHeight+m_maxHeight*m_heighScale*1.0-mDis*tan(m_viewdegree*PAI_D180) , mCz-mDis);
			
	}
	
	return TRUE;
}

//读取DEM所有高种点到全局数组theApp.m_DemHeight[]中
void CT3DSystemView::ReadHdata(CString strfilename)
{
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	long i,j;
	CString tt;
	float hh;	
	FILE *fp=fopen(strfilename,"r");//打开DEM文件

	//读取DEM文件头信息
	fscanf(fp,"%s",tt);
    fscanf(fp,"%s",tt);
    fscanf(fp,"%s",tt);
    fscanf(fp,"%s",tt);
    fscanf(fp,"%s",tt);
    fscanf(fp,"%s",tt);
    fscanf(fp,"%s\n",tt);

	//重新定义全局数组theApp.m_DemHeight[]的大小,并分配内存
	theApp.m_DemHeight= new float[theApp.m_Dem_Rows*theApp.m_Dem_cols];
	float mPer=100.0/(theApp.m_Dem_Rows*theApp.m_Dem_cols);
	
	for (i=0;i<theApp.m_Dem_Rows;i++)
	{
		for (j=0;j<theApp.m_Dem_cols;j++)
		{
			fscanf(fp,"%f ",&hh);  //读取高程
			theApp.m_DemHeight[i*theApp.m_Dem_cols+j]=hh;
		}
		fscanf(fp,"\n");  
	}
	
	fclose(fp); //关闭文件
	DeleteFile(strfilename); //删除临时文件
}

//从数据库中读取所有高程数据到数组中,是为了用来内插高程时用的
void CT3DSystemView::ExporttotalDemToFIle(CString strFilename)
{
	CString tt;
	int idcol=1;	
	tt.Format("SELECT DEM数据 FROM DEM_INFO WHERE 编号= :%d FOR UPDATE",1);
	myOci.ReadBOLBDataFromDB(strFilename,tt,1);//从数据库中读取DEM数据
}

//初始化相机 , 并记录其各参数
void CT3DSystemView::PositionCamera(double positionX ,  double positionY ,  double positionZ ,  double viewX ,  double viewY ,  double viewZ ,  double upVectorX ,  double upVectorY ,  double upVectorZ)
{
	CVector3 vPosition	= CVector3(positionX ,  positionY ,  positionZ);
	CVector3 vView		= CVector3(viewX ,  viewY ,  viewZ);
	CVector3 vUpVector	= CVector3(upVectorX ,  upVectorY ,  upVectorZ);
	
	
	m_vPosition = vPosition;	//视点坐标				
	m_vView     = vView;		//观察点坐标		
	m_vUpVector = vUpVector;	//向上矢量坐标			
	m_oldvPosition=m_vPosition; //保存当前视点坐标
}

//从DEM数据表中读取的BLOB二进制的DEM数据，并写入临时ASCII格式的文件中，实现DEM数据读取
void CT3DSystemView::ExportBlobDemToFIle(CString strFilename ,  int RowIndex ,  int ColIndex)
{
	CString tt;
	int idcol=(RowIndex-1)*theApp.m_BlockCols+ColIndex;	
	tt.Format("SELECT DEM数据 FROM dem_block WHERE 行号=%d AND 列号=%d AND 编号= :%d FOR UPDATE" , RowIndex , ColIndex , idcol);
	myOci.ReadBOLBDataFromDB(strFilename , tt , idcol) ; // 从数据库中读取BLOB数据
}

//从ASCII格式的文件中读取DEM高程点数据 , 并写入高程数组中
void CT3DSystemView::ReadDataFromFiles(CString strfiles ,  int Index)
{
	float hh; 
	int i , j;
    int mCount=theApp.m_Dem_BlockSize ; // 地形子块大小(如分块大小为33×33 , 则地形子块大小=33 , 依次类推)
    FILE *fp=fopen(strfiles , "r") ; // 打开文件
	//循环读取DEM数据
	for( i=0;i<mCount;i++)
	{
		for( j=0;j<mCount;j++)
		{
			fscanf(fp , "%f " , &hh);  
			
			m_pHeight_My[Index][i*mCount+j]=hh ; // 将DEM高程值写入数组
			if(m_maxHeight<hh) m_maxHeight=hh; //计算最大高程值
			if(m_minHeight>hh && hh!=-9999) m_minHeight=hh ; // 计算最小高程值
			
		}
	}
	fclose(fp) ; // 关闭文件
	DeleteFile(strfiles) ; // 删除临时ASCII文件
}

//读取并加载对应DEM地形子块的影像纹理
void CT3DSystemView::getDemBlockTexture(int RowIndex ,  int ColIndex ,  int Index)
{
	CString strsql;
	int mID;
	_RecordsetPtr mRst;
	mRst.CreateInstance(_uuidof(Recordset));
		
	strsql.Format("select *  from TEXTURE WHERE 行号=%d AND 列号=%d  AND 纹理金子塔层号=%d" , RowIndex , ColIndex , m_currebtPhramid);
	try
	{
		//打开数据库表
		mRst->Open(_bstr_t(strsql) , (IDispatch*)(theApp.m_pConnection) , adOpenDynamic , adLockOptimistic , adCmdText);    
	}
	
	catch(_com_error& e)	//出错处理
	{
		CString errormessage;
		errormessage.Format("打开数据库表失败!\r\n错误信息:%s" , e.ErrorMessage());
		MessageBox(errormessage , "读取影像纹理" , MB_ICONSTOP);
		mRst->Close() ; // 关闭数据库表
		return;
	} 
	
	if(mRst->adoEOF)	
	{
		mRst->Close() ; // 关闭数据库表
		return;
	}
	
	//得到第Index影像纹理块的左下和右上x , y坐标
	Thevalue = mRst->GetCollect("左下角坐标X"); 
	m_Texturexy[Index][0]=(double)Thevalue;
	
	Thevalue = mRst->GetCollect("左下角坐标Y"); 
	m_Texturexy[Index][1]=(double)Thevalue;
	
	Thevalue = mRst->GetCollect("右上角坐标X"); 
	m_Texturexy[Index][2]=(double)Thevalue ;
	
	Thevalue = mRst->GetCollect("右上角坐标Y"); 
	m_Texturexy[Index][3]=(double)Thevalue;
	
	Thevalue = mRst->GetCollect("编号"); 
	mID=(long)Thevalue;
	
	mRst->Close() ; // 关闭数据库表
	strsql.Format("C:\\%d_%d.bmp" , RowIndex , ColIndex) ; // 设置临时影像纹理文件名
	if(ExportBlobTextureToFIle(strsql , RowIndex , ColIndex , mID)==TRUE)//从oracle数据库表中读取BLOB类型的影像纹理数据并写入临时影像纹理文件
		m_demTextureID[Index]=m_texturesName.LoadGLTextures(_bstr_t(strsql)) ; // 存储调入的影像纹理子块的纹理ID , 手于绑定纹理
	DeleteFile(strsql) ; // 删除临时文件

}

//初始化数组和OCI
void CT3DSystemView::Init_ArrayData()
{
	if(theApp.bLoadImage==FALSE) //如果影像纹理加载失败，返回
        return ;
	
	if(theApp.bLoginSucceed==TRUE && m_BhasInitOCI==FALSE)//如果数据加载成功，但OCI未初始化
	{
		m_nMapSize=theApp.m_Dem_BlockSize-1 ; // 
		int blocks=m_nMapSize/2;
		//分配数组内存
		m_AllocUnAlloc2D3D.Alloc2D_float(m_pHeight_My , blocks*blocks , (m_nMapSize+1)*(m_nMapSize+1)) ; // 存储调入地形子块的高程点
		m_AllocUnAlloc2D3D.Alloc2D_bool(m_pbQuadMat , blocks*blocks , (m_nMapSize+1)*(m_nMapSize+1)) ; // 标识地形子块的节点是否还需要继续分割
		m_AllocUnAlloc2D3D.Alloc1D_bool(m_pbm_DemLod , theApp.m_BlockCols *theApp.m_BlockRows) ; // 标识地形块是否被调入
		m_AllocUnAlloc2D3D.Alloc2D_int(m_DemLod_My , theApp.m_BlockCols*theApp.m_BlockRows+1 , 3) ; // 存储调入地形子块的行号 , 列号等信息
		if(m_BhasInitOCI==FALSE)//如果OCI还未被初始化
		{
			myOci.Init_OCI(); //初始化OCI
			m_BhasInitOCI=TRUE ; // 标识为TRUE  
		}
	}
}

//信息初始化和加载地形 , 影像数据
void CT3DSystemView::InitTerr()
{
	Init_ArrayData() ; // 初始化数组和OCI
	if(LoadInitDemData()==FALSE) //如果数据加载失败 , 返回
		return ; // 返回
}


//三维地形绘制(三维地形渲染)
void CT3DSystemView::DrawTerrain()
{
	if(theApp.bLoginSucceed==FALSE || m_bLoadInitDemData==FALSE)
		return;
	
	//glShadeModel函数用来设置阴影的效果，主要有GL_SMOOTH和GL_FLAT两种效果，
	//其中GL_SMOOTH为默认值，表示平滑阴影效果；
	glShadeModel(GL_SMOOTH);

	glDisable(GL_TEXTURE_2D) ; // 关闭2D纹理映射功能
	glActiveTextureARB(GL_TEXTURE0_ARB) ; // 选择TEXTURE0为设置目标
	glEnable(GL_TEXTURE_2D) ; // 激活TEXTURE0单元

	glActiveTextureARB(GL_TEXTURE1_ARB) ; // 选择TEXTURE1为设置目标
	glEnable(GL_TEXTURE_2D) ; // 激活TEXTURE1单元 , 启用2D纹理映射
	glTexEnvi(GL_TEXTURE_ENV ,  GL_TEXTURE_ENV_MODE ,  GL_COMBINE_ARB);
	glTexEnvi(GL_TEXTURE_ENV ,  GL_RGB_SCALE_ARB ,  2);
	glMatrixMode(GL_TEXTURE); //定义矩阵为模型纹理矩阵
	glLoadIdentity() ; // 将当前矩阵置换为单位矩阵
	glDisable(GL_TEXTURE_2D) ; // 关闭纹理功能
	glActiveTextureARB(GL_TEXTURE0_ARB) ; // 选择TEXTURE0为设置目标
	SetDrawMode() ; // 
	
	
	glMatrixMode(GL_PROJECTION); 
	glLoadIdentity();            
	gluPerspective(50.0 + m_ViewWideNarrow , (float)WinViewX/(float)WinViewY , m_near , m_far);		
	
	glMatrixMode(GL_MODELVIEW); //定义矩阵为模型模型矩阵
	glLoadIdentity(); //将当前矩阵置换为单位矩阵       

/*
	glClearDepth函数设置深度缓冲区的，它的含义就在OpenGL窗口绘制的图形深入到屏幕中的程度，
	深度的意义就是在三维空间中的z坐标的数值，z取0时表示在平面上，你就看不到窗口中的图形了，
	  所以负值越小，越远离窗口平面向里，说明窗口中的图形离我们观察者的距离变远了；
*/
	glClearDepth(1.0f); //设置初始化深度缓存值
	glEnable(GL_DEPTH_TEST); // 启用深度测试
	glDepthFunc(GL_LESS); //在调用glEnable(GL_DEPTH_TEST); 开启这个功能以后，当深度变化小于当前深度值时，更新深度值。

	
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		
	
	int nCount=0;


	if(this->m_stereo==TRUE) //如果是双目立体模式
	{
		SetCamra(1); //设置相机
	}
	else	//非立体
	{
		SetCamra(0) ; // 设置相机
	}

	glViewport(0 ,  0 ,  WinViewX ,  WinViewY); //设置视口大小和位置
	
	if(b_haveGetRegion==TRUE && m_ifZoomonRoad==TRUE)//如果沿线路方案漫游
	{
		DrawTerrainZoomonRoad() ; // 绘制地形与三维线路模型
	}
	else //仅绘制地形
	{
		if(theApp.bLoginSucceed==TRUE && m_bLoadInitDemData==TRUE)
		{
			//计算帧率
			float currentTime1 = timeGetTime() * 0.001f;
			nCountFrame++;
			CalculateViewPortTriCord(m_vPosition.x , m_vPosition.z , m_vView.x , m_vView.z);
			
			//为数组m_pbQuadMat分配内存(LOD节点分割的标志)
			for(int j=0;j<m_LodDemblockNumber;j++)
			{
				memset(m_pbQuadMat[j] , 0 , m_nMapSize*m_nMapSize);
			}
		
			CalcFPS() ; // 计算帧率

			//创建地形显示列表
			glNewList(m_TerrainList , GL_COMPILE_AND_EXECUTE);
				m_RenderDemblockNumber=0 ; // 渲染的地形块数量
				View=m_vView-m_vPosition;
				for(int i=0;i<m_LodDemblockNumber;i++)
				{
					mCurrentTextID=i;
					m_CurrentDemArrayIndex=i;
					glBindTexture(GL_TEXTURE_2D ,  m_demTextureID[i]) ; // 绑定第i地表子块的纹理
					m_lodDemBlock[i][3]=0 ; // 初始值为未参与渲染
					//如果当前地形块不在视景体内
					if(bnTriangle(m_triPtA[0] , m_triPtA[1] , m_triPtB[0] , m_triPtB[1] , m_triPtC[0] , m_triPtC[1] , m_DemBlockCenterCoord[i][0] , m_DemBlockCenterCoord[i][1])==FALSE)
						continue;
					m_RenderDemblockNumber++ ; // 渲染的地形块数量+1
					m_lodDemBlock[i][3]=1 ; // 当前地形块参与渲染
                    //对当前地形块进行LOD四叉树分割
					UpdateQuad(m_nMapSize/2 , m_nMapSize/2 , m_nMapSize/2 , 1 , m_lodDemBlock[i][0] , m_lodDemBlock[i][1]);
					//渲染LOD四叉树分割后的当前地形块 , 并计算出当前所绘制的三角形总数量
					nCount+= RenderQuad(m_nMapSize/2 , m_nMapSize/2 , m_nMapSize/2 , m_lodDemBlock[i][0] , m_lodDemBlock[i][1]);	
				}

				if(m_checkt==TRUE)//如果进行错误负反馈控制
				{
					if(nCount>=m_maxTrinum) //如果三角形数量超过最大三角形总数
						m_lodScreenError=m_lodScreenError*m_k1; //屏幕误差τ增大
					if(nCount<=m_minTrinum) //如果三角形数量小于最小三角形总数
					m_lodScreenError=m_lodScreenError/m_k2 ; // 屏幕误差τ减小
				}

			glEndList(); //结束显示列表
			
			CString	strText;
			strText.Format("【内存/渲染块数】=%d/%d" , m_LodDemblockNumber , m_RenderDemblockNumber);
	  		pMainFrame->Set_BarText(0 , strText , 0); //显示内存中和渲染的地形块数量
			
			if(m_bShowbreviary==TRUE)//显示缩略视图
			{
				glViewport(WinViewX*5/6 ,  WinViewY*5/6 , WinViewX/6 ,  WinViewY/6);
				glCallList(m_TerrainList) ; // 调用地形显示列表 , 在新的视口绘制三维地形
				glViewport(0 ,  0 ,  WinViewX ,  WinViewY) ; // 重新设置视口大小
				
			}

			
			float currentTime2 = timeGetTime() * 0.001f;
			mTimeElaps+=currentTime2-currentTime1;
			if(mTimeElaps>=1.0)//如果两次时间间隔>=1秒
			{
				strText.Format("频率 %d FPS" , nCountFrame);
				pMainFrame->Set_BarText(1 , strText , 0); //在状态栏指示器上显示绘制帧率
				mTimeElaps=nCountFrame=0;
				
			}
		

			strText.Format("三角形 %d " , nCount);
			pMainFrame->Set_BarText(2 , strText , 0); //在状态栏指示器上显示当前帧所绘制的地形三角形总数
		}
	}


	glActiveTextureARB(GL_TEXTURE1_ARB) ; // 选择TEXTURE1为设置目标
	glDisable(GL_TEXTURE_2D) ; // 关闭TEXTURE1纹理功能
	glActiveTextureARB(GL_TEXTURE0_ARB) ; // 选择TEXTURE0为设置目标		
	glDisable(GL_TEXTURE_2D) ; // 关闭TEXTURE0纹理功能
	glDisable(GL_DEPTH_TEST) ; // 关闭深度缓冲区测试功能
}

//计算帧率
void CT3DSystemView::CalcFPS()
{
	static DWORD dwStart = 0;
	static nCount = 0;
	nCount++;
	DWORD dwNow = ::GetTickCount();//返回从程序启动到现在所经过的毫秒数
	
	if(dwNow-dwStart>=1000)//每1秒计算一次帧率
	{
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		CString strText;
		strText.Format("频率 %d FPS " , nCount , 0);
		pMainFrame->Set_BarText(1 , strText , 0); //在状态栏上指示器是显示帧率值
		dwStart = dwNow;
		nCount = 0;
	}
}

//设置相机
void CT3DSystemView::SetCamra(int mStyle)
{
	
		switch(mStyle)
		{
		case 0://非双目立体模式
			gluLookAt(m_vPosition.x ,  m_vPosition.y ,  m_vPosition.z , 	
				m_vView.x , 	 m_vView.y ,      m_vView.z , 	
				m_vUpVector.x ,  m_vUpVector.y ,  m_vUpVector.z);
			break;
		case 1://双目立体模式 , 左相机
			gluLookAt(m_vPosition.x-m_camraDistence/2.0 ,  m_vPosition.y ,  m_vPosition.z , 	
				m_vView.x , 	 m_vView.y ,      m_vView.z , 	
				m_vUpVector.x ,  m_vUpVector.y ,  m_vUpVector.z);
			break;
		case 2://双目立体模式 , 右相机
			gluLookAt(m_vPosition.x+m_camraDistence/2.0 ,  m_vPosition.y ,  m_vPosition.z , 	
				m_vView.x , 	 m_vView.y ,      m_vView.z , 	
				m_vUpVector.x ,  m_vUpVector.y ,  m_vUpVector.z);
		}
		
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		CString strText;
		float dy=m_vPosition.y-m_vView.y;
		float dz=fabs(m_vPosition.z-m_vView.z);
		if(theApp.bLoginSucceed==TRUE) 
		{
			if(fabs(dz)<=0.000001)
				m_viewdegree=0;
			else
				m_viewdegree=HDANGLE*atan(dy/dz);
		}
		
		//在状态栏指示器上显示相关信息
		strText.Format("【俯视角】A=%.2f" , m_viewdegree);
		pMainFrame->Set_BarText(3 , strText , 0); 
		
		if(m_ifZoomonRoad==FALSE)//非沿线路方案漫游 , 否则 , 显示当前里程
		{
			strText.Format("视点坐标:x=%.3f , y=%.3f , z=%.3f" , m_vPosition.x  , m_vPosition.y , fabs(m_vPosition.z));
			pMainFrame->Set_BarText(4 , strText , 0); 
		}
		
		if(m_ifZoomonRoad==FALSE)//非沿线路方案漫游 , 否则 , 显示当前里程
		{
			strText.Format("观察点坐标:x=%.3f , y=%.3f , z=%.3f" , m_vView.x  , m_vView.y , fabs(m_vView.z));
			pMainFrame->Set_BarText(5 , strText , 0); 
		}
		
}

//初始化显示列表
void CT3DSystemView::InitList()
{
	m_TerrainList=glGenLists(20);
	m_SkyList=m_TerrainList+1 ; // 背景天空显示列表
	m_QLanList=m_TerrainList+2; //桥梁栏杆显示列表
	m_QDList=m_TerrainList+3;//桥梁桥墩显示列表
	m_Rail3DwayList=m_TerrainList+4; //线路三维模型显示列表(透视投影模式)
	m_HazardList_Ortho=m_TerrainList+5;//线路三维模型显示列表(正射投影模式)
	m_clockList=m_TerrainList+6;//时钟指北针显示列表
	m_NavigateList=m_TerrainList+7;
	m_Rail3DwayList_Ortho=m_TerrainList+8;
	m_TerrainZoomroadList=m_TerrainList+9;
	m_TunnelDMList=m_TerrainList+10; //隧道洞门显示列表


	makeSkykList() ; // 生成绘制背景天空显示列表
	BuildQDModelList();//生成桥墩显示列表
	makeQLList();//生成桥梁栏杆//显示列表
	makeClockList();//创建时钟指北针显示列表
	BuildTunnelDMModelList();//创建隧道洞门显示列表
		
	
}

//沿线路方案漫游时绘制被线路模型剪切后的三维地形
void CT3DSystemView::DrawTerrainZoomonRoad()
{

	double x,y,z;
	if(theApp.bLoginSucceed==TRUE && m_bLoadInitDemData==TRUE && m_BlockTriInfos.GetSize()>0)
	{
		m_bShowbreviary=FALSE;
		glViewport(0, 0, WinViewX, WinViewY);
		
		
		float dz=m_maxHeight-m_minHeight;
		glLineWidth(2);
		
		float currentTime1 = timeGetTime() * 0.001f;
		nCountFrame++;
		CalculateViewPortTriCord(m_vPosition.x,m_vPosition.z,m_vView.x,m_vView.z);
		CalcFPS();
		
		for( long i=0;i<m_BlockTriInfos.GetSize();i++)
		{
			
			BOOL bt=GetIfINView(m_BlockTriInfos.GetAt(i)->mDemBlockRow,m_BlockTriInfos.GetAt(i)->mDemBlockCol); 
			if(bt==FALSE)
				continue;
			
			for(long j=0;j<m_BlockTriInfos.GetAt(i)->TriPts.GetSize();j++)
			{
				
				int mtextureID=(m_BlockTriInfos.GetAt(i)->mDemBlockRow-1)*theApp.m_BlockCols+(m_BlockTriInfos.GetAt(i)->mDemBlockCol-1);
				
				glBindTexture(GL_TEXTURE_2D, m_demTextureID[mtextureID]); //绑定纹理
				
				if(m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt1.x!=-9999)
				{
					
					{
						glBegin( GL_TRIANGLES);
						
						x=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt1.x;
						y=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt1.y;
						z=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt1.z;
						
						SetTextureCoordZoomRoad(x,y,m_BlockTriInfos.GetAt(i)->mDemBlockRow,m_BlockTriInfos.GetAt(i)->mDemBlockCol);
						glVertex3f(x,z,-y);
						
						x=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt2.x;
						y=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt2.y;
						z=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt2.z;
						SetTextureCoordZoomRoad(x,y,m_BlockTriInfos.GetAt(i)->mDemBlockRow,m_BlockTriInfos.GetAt(i)->mDemBlockCol);
						glVertex3f(x,z,-y);
						
						x=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt3.x;
						y=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt3.y;
						z=m_BlockTriInfos.GetAt(i)->TriPts.GetAt(j)->Pt3.z;
						SetTextureCoordZoomRoad(x,y,m_BlockTriInfos.GetAt(i)->mDemBlockRow,m_BlockTriInfos.GetAt(i)->mDemBlockCol);
						glVertex3f(x,z,-y);
						glEnd();
					}
				}
			}
		}
		
		
		float currentTime2 = timeGetTime() * 0.001f;
		mTimeElaps+=currentTime2-currentTime1;
		
		if(mTimeElaps>=1.0)
		{
			CString strText;
			strText.Format("频率 %d FPS",nCountFrame);
			
			mTimeElaps=nCountFrame=0;
			
		}
	}
}

//判断地形节点是否在简化扇形视景体内
BOOL CT3DSystemView::bnTriangle(double cneterx ,  double cnetery ,  double x2 ,  double y2 ,  double x3 ,  double y3 ,  double x ,  double y)
{
	double m_dx , m_dz;
	float remianAngle;
	
	
	double d1 , d2;
	double mDis=(cneterx-x)*(cneterx-x)+(cnetery-y)*(cnetery-y);
	mDis=sqrt(mDis);
	if(mDis>=m_R)
		return FALSE;
	else if(mDis<=1000)
		return TRUE;
	
		
	m_dx=cneterx-x;
	m_dz=cnetery-y;
	
    if(m_dx<=0 && m_dz>=0) 
	{
		if(m_dx==0)	
			remianAngle=90;
		else
			remianAngle=atan(fabs(m_dz/m_dx))*HDANGLE;
		
	}
    else if(m_dx>=0 && m_dz>=0) 
	{
		if(m_dx==0)	
			remianAngle=90;
		else
			remianAngle=180-atan(fabs(m_dz/m_dx))*HDANGLE;
		
	}
    else if(m_dx>=0 && m_dz<=0) 
	{
		if(m_dx==0)	
			remianAngle=270;
		else
			remianAngle=180+atan(fabs(m_dz/m_dx))*HDANGLE;
		
	}
    else if(m_dx<=0 && m_dz<=0) 
	{
		if(m_dx==0)	
			remianAngle=270;
		else
			remianAngle=360-atan(fabs(m_dz/m_dx))*HDANGLE;
		
	}
	

	if(mDis<=m_R)
	{  
		if(remianAngle>=m_sectorStartAngle && remianAngle<=m_sectorEndAngle)
			return TRUE;
		else
		{
			d1=mCalF.GetPointToLineDistence(x , y , cneterx , cnetery , x2 , y2) ; // 计算点到直线的距离
			d2=mCalF.GetPointToLineDistence(x , y , cneterx , cnetery , x3 , y3) ; // 计算点到直线的距离
            if(d1<=m_r || d2<=m_r)
				return TRUE;
		}
	}
    else
	{
		if(remianAngle>=m_sectorStartAngle && remianAngle<=m_sectorEndAngle)
		{
			if(mDis<=m_R+m_r)
				return TRUE;
		}
		
	}
	
	return FALSE;
}

//计算简化扇形视景体内的三个顶点坐标
void CT3DSystemView::CalculateViewPortTriCord(double View_x ,  double View_z ,  double look_x ,  double look_z)
{
	double m_derx=look_x-View_x;
	double m_derz=look_z-View_z;
	float angle_arefa;
	
	m_triPtA[0]=View_x;m_triPtA[1]=View_z;
	
    if(m_derx<=0 && m_derz>0) 
	{
		if(m_derx==0)	
			angle_arefa=270;
		else
			angle_arefa=180+atan(fabs(m_derz/m_derx))*HDANGLE;
		
	}
    else if(m_derx>=0 && m_derz>0) 
	{
		if(m_derx==0)	
			angle_arefa=0;
		else
			angle_arefa=360-atan(fabs(m_derz/m_derx))*HDANGLE;
		
	}
    else if(m_derx>=0 && m_derz<0) 
	{
		if(m_derx==0)	
			angle_arefa=0;
		else
			angle_arefa=atan(fabs(m_derz/m_derx))*HDANGLE;
		
	}
    else if(m_derx<=0 && m_derz<0) 
	{
		if(m_derx==0)	
			angle_arefa=90;
		else
			angle_arefa=180-atan(fabs(m_derz/m_derx))*HDANGLE;
		
	}
	
	m_sectorStartAngle=angle_arefa-m_FrustumAngle/2;
	m_triPtB[0]=m_R*cos(m_sectorStartAngle*PAI_D180)+m_triPtA[0];
	m_triPtB[1]=-m_R*sin(m_sectorStartAngle*PAI_D180)+m_triPtA[1];
	
	m_sectorEndAngle=angle_arefa+m_FrustumAngle/2;
	m_triPtC[0]=m_R*cos(m_sectorEndAngle*PAI_D180)+m_triPtA[0];
	m_triPtC[1]=-m_R*sin(m_sectorEndAngle*PAI_D180)+m_triPtA[1];
	
	
	m_Viewpolygon[0].x=m_triPtA[0]; 	m_Viewpolygon[0].y=-m_triPtA[1];
	m_Viewpolygon[1].x=m_triPtB[0]; 	m_Viewpolygon[1].y=-m_triPtB[1];
	m_Viewpolygon[2].x=m_triPtC[0]; 	m_Viewpolygon[2].y=-m_triPtC[1];
	
	////存储视口三角扇形的三个坐标点中的最大最小坐标
	m_Viewpolygon_Minx=m_Viewpolygon[0].x;
	m_Viewpolygon_Miny=m_Viewpolygon[0].y;
	m_Viewpolygon_Maxx=m_Viewpolygon[0].x;
	m_Viewpolygon_Maxy=m_Viewpolygon[0].y;
	
	if(m_Viewpolygon_Minx>m_Viewpolygon[1].x) m_Viewpolygon_Minx=m_Viewpolygon[1].x;
	if(m_Viewpolygon_Maxx<m_Viewpolygon[1].x) m_Viewpolygon_Maxx=m_Viewpolygon[1].x;
	if(m_Viewpolygon_Miny>m_Viewpolygon[1].y) m_Viewpolygon_Miny=m_Viewpolygon[1].y;
	if(m_Viewpolygon_Maxy<m_Viewpolygon[1].y) m_Viewpolygon_Maxy=m_Viewpolygon[1].y;
	
	if(m_Viewpolygon_Minx>m_Viewpolygon[2].x) m_Viewpolygon_Minx=m_Viewpolygon[2].x;
	if(m_Viewpolygon_Maxx<m_Viewpolygon[2].x) m_Viewpolygon_Maxx=m_Viewpolygon[2].x;
	if(m_Viewpolygon_Miny>m_Viewpolygon[2].y) m_Viewpolygon_Miny=m_Viewpolygon[2].y;
	if(m_Viewpolygon_Maxy<m_Viewpolygon[2].y) m_Viewpolygon_Maxy=m_Viewpolygon[2].y;
}

//对地形块进行四叉树LOD分割
void CT3DSystemView::UpdateQuad(int nXCenter ,  int nZCenter ,  int nSize ,  int nLevel ,  int mRowIndex ,  int mColIndex)
{
	double mx=(mColIndex-1)*theApp.m_Dem_BlockWidth;
	double mz=(mRowIndex-1)*theApp.m_Dem_BlockWidth;
	//如果地形节点不在视景体内 , 返回
	if(bnTriangle(m_triPtA[0] , m_triPtA[1] , m_triPtB[0] , m_triPtB[1] , m_triPtC[0] , m_triPtC[1] , nXCenter*theApp.m_Cell_xwidth+mx , -nZCenter*theApp.m_Cell_ywidth-mz)==FALSE)
		return ;
	
	
	if(m_ifZoomonRoad==FALSE) //是否沿线路方案漫游
	{
		CVector3 vPos=GetPos();
		
		CVector3 vDst(nXCenter*theApp.m_Cell_xwidth+(mColIndex-1)*theApp.m_Dem_BlockWidth , GetHeightValue(nXCenter , nZCenter ,  mRowIndex ,  mColIndex) , -nZCenter*theApp.m_Cell_ywidth-(mRowIndex-1)*theApp.m_Dem_BlockWidth);
		float nDist=mCalF.maxValueXYZ(fabs(vPos.x-vDst.x) , fabs(vPos.y-vDst.y) , fabs(vPos.z-vDst.z));
		float es , em;
		
		em=GetNodeError(nXCenter ,  nZCenter ,  nSize , mRowIndex ,  mColIndex) ; // 计算节点误差
		es=m_es*(em/nDist);
		if(es<m_lodScreenError) //如果误差小于屏幕误差τ , 不绘制
			return;
	}
		
	if(nSize>1) //表示地形块节点还需要继续分割
	{	
		m_pbQuadMat[m_CurrentDemArrayIndex][nXCenter+nZCenter*m_nMapSize]=true;		
		UpdateQuad(nXCenter-nSize/2 , nZCenter-nSize/2 , nSize/2 , nLevel+1 , mRowIndex ,  mColIndex) ; // 分割左下子节点
		UpdateQuad(nXCenter+nSize/2 , nZCenter-nSize/2 , nSize/2 , nLevel+1 , mRowIndex ,  mColIndex) ; // 分割右下子节点
		UpdateQuad(nXCenter+nSize/2 , nZCenter+nSize/2 , nSize/2 , nLevel+1 , mRowIndex ,  mColIndex) ; // 分割右上子节点
		UpdateQuad(nXCenter-nSize/2 , nZCenter+nSize/2 , nSize/2 , nLevel+1 , mRowIndex ,  mColIndex) ; // 分割左上子节点
	}
}

//根据节点的X , Y和地形子块的行号和列号从高程数组中得到对应的节点高程值
float CT3DSystemView::GetHeightValue(int X ,  int Y ,  int mRowIndex ,  int mColIndex)
{
	return m_pHeight_My[m_CurrentDemArrayIndex][X+Y*(m_nMapSize+1)]*m_heighScale;
	
}

//得到相机观察点三维坐标
CVector3 CT3DSystemView::GetPos()
{
	return m_vPosition;
}

//得到相机视点三维坐标
CVector3 CT3DSystemView::GetView()
{
	return m_vView;
}

//得到相机向上矢量三维坐标
CVector3 CT3DSystemView::UpVector()
{
	return m_vUpVector;
}

//得到相机事参数的CVector3类型变量
CVector3 CT3DSystemView::Strafe()
{
	return m_vStrafe;
}


//节点误差计算
float CT3DSystemView::GetNodeError(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	float nMax = GetHeightValue(nXCenter , nZCenter ,  mRowIndex ,  mColIndex);
	float nMin = nMax;
	float nH1 = GetHeightValue(nXCenter-nSize , nZCenter-nSize , mRowIndex ,  mColIndex);
	float nH2 = GetHeightValue(nXCenter+nSize , nZCenter-nSize , mRowIndex ,  mColIndex);
	float nH3 = GetHeightValue(nXCenter+nSize , nZCenter+nSize , mRowIndex ,  mColIndex);
	float nH4 = GetHeightValue(nXCenter-nSize , nZCenter+nSize , mRowIndex ,  mColIndex);
	if(nMax<nH1)nMax = nH1;
	if(nMax<nH2)nMax = nH2;
	if(nMax<nH3)nMax = nH3;
	if(nMax<nH4)nMax = nH4;
	if(nMin>nH1)nMin = nH1;
	if(nMin>nH2)nMin = nH2;
	if(nMin>nH3)nMin = nH3;
	if(nMin>nH4)nMin = nH4;
	return nMax-nMin;
}

//根据地形块的行号和列号判断该项地形块是否视口三角形内
BOOL CT3DSystemView::GetIfINView(int mBlockRow ,  int mBlockCol)
{
	Point *polygonPs;	
	polygonPs=new Point[4];

	polygonPs[0].x=(mBlockCol-1)*theApp.m_Dem_BlockWidth;
	polygonPs[0].y=(mBlockRow-1)*theApp.m_Dem_BlockWidth;
	
	polygonPs[1].x=polygonPs[0].x+theApp.m_Dem_BlockWidth;
	polygonPs[1].y=polygonPs[0].y;

	polygonPs[2].x=polygonPs[0].x;
	polygonPs[2].y=polygonPs[0].y+theApp.m_Dem_BlockWidth;

	polygonPs[3].x=polygonPs[1].x;
	polygonPs[3].y=polygonPs[2].y;



	BOOL Bt=FALSE;
	bool Fin=false;
	
   //视口三角形(扇形的简化)与地形子块(矩形)判断规则如下:
/*
	以下三种情况任何一种都认为该地形子块需要绘制
	(1)视口三角形完全位于或部分视口三角形顶点位于地形子块(矩形)内
	(2)地形子块(矩形)完全位于视口三角形内
	(3)地形子块(矩形)与视口三角形内相交
*/
	////存储视口三角扇形的三个坐标点中的最大最小坐标
	//如果①地形子块的最小x , y坐标都大于视口三角形的最大x , y坐标
	//    ②地形子块的最大x , y坐标都小于视口三角形的最小x , y坐标
	//则该地形子块一定不位于视口三角形内 , 不需再进行判断
	if(polygonPs[0].x>=m_Viewpolygon_Maxx || polygonPs[0].y>=m_Viewpolygon_Maxy)
		return FALSE;
	if(polygonPs[3].x<=m_Viewpolygon_Minx || polygonPs[3].y<=m_Viewpolygon_Miny)
		return FALSE;
	

		
	//(1)只要视口三角形有一个顶点位于地形子块(矩形)内就算
	if(m_triPtA[0]>polygonPs[0].x && m_triPtA[0]<polygonPs[3].x && m_triPtA[1]<-polygonPs[0].y && m_triPtA[1]>-polygonPs[3].y) //点在矩形里
		Bt=TRUE;
	if(Bt==FALSE && m_triPtB[0]>polygonPs[0].x && m_triPtB[0]<polygonPs[3].x && m_triPtB[1]<-polygonPs[0].y && m_triPtB[1]>-polygonPs[3].y) //点在矩形里
		Bt=TRUE;
	if(Bt==FALSE && m_triPtC[0]>polygonPs[0].x && m_triPtC[0]<polygonPs[3].x && m_triPtC[1]<-polygonPs[0].y && m_triPtC[1]>-polygonPs[3].y) //点在矩形里
		Bt=TRUE;
	if(Bt==FALSE)
	{
		Bt=mCalF.InPolygon(m_Viewpolygon , 3 , polygonPs[0]);
		if(Bt==FALSE)
			Bt=mCalF.InPolygon(m_Viewpolygon , 3 , polygonPs[1]);
		if(Bt==FALSE)
			Bt=mCalF.InPolygon(m_Viewpolygon , 3 , polygonPs[2]);
		if(Bt==FALSE)
			Bt=mCalF.InPolygon(m_Viewpolygon , 3 , polygonPs[3]);
	}
	
	//还有可能穿越地形子块而
	//(1)三角形的三条边01 , 12 , 02
	//(2)矫形的4条边 01 , 23 , 02 , 13
		
	//01边
	if(Bt==FALSE)
	{
		//判断两条线段是否相交(有交点)
		Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[1] , polygonPs[0] , polygonPs[1]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[1] , polygonPs[0] , polygonPs[2]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[1] , polygonPs[2] , polygonPs[3]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[1] , polygonPs[1] , polygonPs[3]);
	}

	//12边
	if(Bt==FALSE)
	{
		//判断两条线段是否相交(有交点)
		Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[1] ,  m_Viewpolygon[2] , polygonPs[0] , polygonPs[1]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[1] ,  m_Viewpolygon[2] , polygonPs[0] , polygonPs[2]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[1] ,  m_Viewpolygon[2] , polygonPs[2] , polygonPs[3]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[1] ,  m_Viewpolygon[2] , polygonPs[1] , polygonPs[3]);
	}

	//02边
	if(Bt==FALSE)
	{
		//判断两条线段是否相交(有交点)
		Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[2] , polygonPs[0] , polygonPs[1]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[2] , polygonPs[0] , polygonPs[2]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[2] , polygonPs[2] , polygonPs[3]);
		if(Bt==FALSE)
			Bt=mCalF.IsLineSegmentCross(m_Viewpolygon[0] ,  m_Viewpolygon[2] , polygonPs[1] , polygonPs[3]);
	}
	return Bt;
}

//设置线路漫游时地形块纹理坐标
void CT3DSystemView::SetTextureCoordZoomRoad(double x ,  double z ,  int mRowIndex ,  int mColIndex)
{
	//对应地形块的最小x , y坐标
	double mminx=(mColIndex-1)*theApp.m_Dem_BlockWidth;
	double mminy=(mRowIndex-1)*theApp.m_Dem_BlockWidth;
	
	float u=(x-mminx)/theApp.m_Dem_BlockWidth;
	float v=(z-mminy)/theApp.m_Dem_BlockWidth;
	
	glMultiTexCoord2fARB(GL_TEXTURE0_ARB ,  u ,  v);
	glMultiTexCoord2fARB(GL_TEXTURE1_ARB ,  u ,  v);
}

//渲染地形节点
int CT3DSystemView::RenderQuad(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	float hjh;
	int kk;
	CVector3 pos , VPos;

	
	int nH0 , nH1 , nH2 , nH3 , nH4;
	int nCount = 0;
	if(nSize>=1.0f)//地形块节点尺寸>=1.0，表示该节点还有子节点
	{
		//节点中心点和4个角点的高程
		nH0 = GetHeightValue(nXCenter , nZCenter ,  mRowIndex ,  mColIndex);
		nH1 = GetHeightValue(nXCenter-nSize , nZCenter-nSize ,  mRowIndex ,  mColIndex);
		nH2 = GetHeightValue(nXCenter+nSize , nZCenter-nSize ,  mRowIndex ,  mColIndex);
		nH3 = GetHeightValue(nXCenter+nSize , nZCenter+nSize ,  mRowIndex ,  mColIndex);
		nH4 = GetHeightValue(nXCenter-nSize , nZCenter+nSize ,  mRowIndex ,  mColIndex);
	}

	int mnum=0;

	double mx=(mColIndex-1)*theApp.m_Dem_BlockWidth;
	double mz=(mRowIndex-1)*theApp.m_Dem_BlockWidth;
	

	//如果地形节点不在视景体内 , 返回
	if(bnTriangle(m_triPtA[0] , m_triPtA[1] , m_triPtB[0] , m_triPtB[1] , m_triPtC[0] , m_triPtC[1] , nXCenter*theApp.m_Cell_xwidth+mx , -nZCenter*theApp.m_Cell_ywidth-mz)==FALSE)
		return 0;

	float dz=m_maxHeight-m_minHeight;

	double x , z;
	double xcenter , zcenter;


	/*
	格网节点组成示意图
	nH4
	  |----------------|nH3
	  |                |
	  |                |
	  |                |
	  |       nH0      |
	  |    节点中心    |
	  |                |
	  |                |
	  |----------------|
      nH1              nH2 
	
	  其中都是以节点中心(nH0)开始进行绘制 , 
	  
	顺序为nH0--nH1--nH2--nH3--nH4--nH1
	其中在4条边上(nH1--nH2 , nH2--nH3 , nH3--nH4 , nH4--nH1)可能会补充上相应点
	以消除与相临节点分辨率不一致造成的空洞 , 也就是说一个地形节点至少会有4个三角形

    即使补充办界点 , 其结果仍然是以节点中心nH0开始 , 绘制一系列三
	角形(以GL_TRIANGLE_FAN方式绘制) , 以提高绘制速度


	*/
	
	if(m_pbQuadMat[m_CurrentDemArrayIndex][nXCenter+nZCenter*m_nMapSize]==true /*&& nSize>1.0/nLodScale*/)			
			
	{
		nCount += RenderQuad(nXCenter-nSize/2 , nZCenter-nSize/2 , nSize/2 ,  mRowIndex ,  mColIndex);
		nCount += RenderQuad(nXCenter+nSize/2 , nZCenter-nSize/2 , nSize/2 ,  mRowIndex ,  mColIndex);
		nCount += RenderQuad(nXCenter+nSize/2 , nZCenter+nSize/2 , nSize/2 ,  mRowIndex ,  mColIndex);
		nCount += RenderQuad(nXCenter-nSize/2 , nZCenter+nSize/2 , nSize/2 ,  mRowIndex ,  mColIndex);
	}
	else
	{
			//以扇形绘制地形块节点
			glBegin( GL_TRIANGLE_FAN );
		
				x=nXCenter*theApp.m_Cell_xwidth+mx;z=-nZCenter*theApp.m_Cell_ywidth-mz;
				VPos.x=x-m_vPosition.x;VPos.y=nH0-m_vPosition.y;VPos.z=z-m_vPosition.z;
				xcenter=x;
				zcenter=-z;
				if(DotProduct(View , VPos)>0) //如果在视点范围内
				{	
					if(nH0!=theApp.m_DEM_IvalidValue)	//如果高程有效
					{
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH0-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH0-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH0-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x , nH0 ,  z);  //设置法线
						SetTextureCoord(nXCenter , nZCenter ,  mRowIndex ,  mColIndex);  //设置纹理坐标
						glVertex3i(x ,  nH0 ,  z) ; // 写入中心点坐标
					}
					
				}
				x=(nXCenter-nSize)*theApp.m_Cell_xwidth+mx;z= -(nZCenter-nSize)*theApp.m_Cell_ywidth-mz;
				VPos.x=x-m_vPosition.x;VPos.y=nH1-m_vPosition.y;VPos.z=z-m_vPosition.z;
				if(DotProduct(View , VPos)>0)
				{	

					if(nH1!=theApp.m_DEM_IvalidValue )	//如果高程有效
					{
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH1-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH1-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH1-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x , nH1  ,  x);  //设置法线
						SetTextureCoord(nXCenter-nSize , nZCenter-nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
						glVertex3i(x , nH1  ,  z) ; // 左下角点
						CracksPatchTop(nXCenter , nZCenter , nSize , mRowIndex , mColIndex) ; // 节点裂缝修补
						nCount++;
					}
				}
				if(nZCenter-nSize==0) 
				{
					for(kk=1;kk<=2*nSize-1;kk++)
					{
						hjh = GetHeightValue(nXCenter-nSize+kk , nZCenter-nSize ,  mRowIndex ,  mColIndex);
						x=(nXCenter-nSize+kk)*theApp.m_Cell_xwidth+mx;z=-(nZCenter-nSize)*theApp.m_Cell_ywidth-mz;
						VPos.x=x-m_vPosition.x;VPos.y=hjh-m_vPosition.y;VPos.z=z-m_vPosition.z;
						if(DotProduct(View , VPos)>0)//如果节点在在视景体内
						{	
							if(hjh!=theApp.m_DEM_IvalidValue)	//如果高程有效	
							{
								//设置颜色
								glColor3f(
									(maxZ_color_R-minZ_color_R)*(hjh-m_minHeight)/dz+minZ_color_R , 
									(maxZ_color_G-minZ_color_G)*(hjh-m_minHeight)/dz+minZ_color_G , 
									(maxZ_color_B-minZ_color_B)*(hjh-m_minHeight)/dz+minZ_color_B);
								glNormal3f(x ,  hjh ,  z);  //设置法线
								SetTextureCoord(nXCenter-nSize+kk , nZCenter-nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
								glVertex3i(x ,  hjh ,  z);
								
								mnum++;
							}
						}
						
					}
				
				}

					
				x=(nXCenter+nSize)*theApp.m_Cell_xwidth+mx;z=-(nZCenter-nSize)*theApp.m_Cell_ywidth-mz;
				
				VPos.x=x-m_vPosition.x;VPos.y=nH2-m_vPosition.y;VPos.z=z-m_vPosition.z;
				if(DotProduct(View , VPos)>0)//如果节点在在视景体内
				{	
					if(nH2!=theApp.m_DEM_IvalidValue)	//如果高程有效
					{
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH2-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH2-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH2-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x ,  nH2 ,  z) ; // 设置法线
						SetTextureCoord(nXCenter+nSize , nZCenter-nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
						glVertex3i(x ,  nH2 ,  z) ; // 左上角点
						CracksPatchRight(nXCenter , nZCenter , nSize , mRowIndex , mColIndex) ; // 节点裂缝修补
						nCount++;
					}
				}


				if(nXCenter+nSize>=m_nMapSize)
				{
					for(kk=1;kk<=2*nSize-1;kk++)
					{
						hjh = GetHeightValue(nXCenter+nSize , kk+(nZCenter-nSize) ,  mRowIndex ,  mColIndex);
						x=(nXCenter+nSize)*theApp.m_Cell_xwidth+mx;z=-(kk+(nZCenter-nSize))*theApp.m_Cell_ywidth-mz;
						
						VPos.x=x-m_vPosition.x;VPos.y=hjh-m_vPosition.y;VPos.z=z-m_vPosition.z;
						if(DotProduct(View , VPos)>0)//如果节点在在视景体内
						{	
							if(hjh!=theApp.m_DEM_IvalidValue)	//如果高程有效	
							{
								//设置颜色
								glColor3f(
									(maxZ_color_R-minZ_color_R)*(hjh-m_minHeight)/dz+minZ_color_R , 
									(maxZ_color_G-minZ_color_G)*(hjh-m_minHeight)/dz+minZ_color_G , 
									(maxZ_color_B-minZ_color_B)*(hjh-m_minHeight)/dz+minZ_color_B);
								glNormal3f(x ,  hjh ,  z) ; // 设置法线
								SetTextureCoord(nXCenter+nSize , kk+(nZCenter-nSize) ,  mRowIndex ,  mColIndex); //设置纹理坐标
								glVertex3i(x ,  hjh ,  z);
								mnum++;
							}
						}
					}
				
				}


				x=(nXCenter+nSize)*theApp.m_Cell_xwidth+mx;z=-(nZCenter+nSize)*theApp.m_Cell_ywidth-mz;
				
				VPos.x=x-m_vPosition.x;VPos.y=nH3-m_vPosition.y;VPos.z=z-m_vPosition.z;
				if(DotProduct(View , VPos)>0)//如果节点在在视景体内
				{	
					if(nH3!=theApp.m_DEM_IvalidValue)		//如果高程有效
					{
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH3-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH3-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH3-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x ,  nH3 ,  z) ; // 设置法线
						SetTextureCoord(nXCenter+nSize , nZCenter+nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
						glVertex3i(x ,  nH3 ,  z) ; // 右上角点
						CracksPatchBottom(nXCenter , nZCenter , nSize , mRowIndex , mColIndex) ; // 节点裂缝修补
						nCount++;
					}
				}

				if(nZCenter+nSize>=m_nMapSize) 
				{
					for(kk=1;kk<=2*nSize-1;kk++)
					{
						hjh = GetHeightValue(nXCenter+nSize-kk , nZCenter+nSize ,  mRowIndex ,  mColIndex);
						x=(nXCenter+nSize-kk)*theApp.m_Cell_xwidth+mx;z=-(nZCenter+nSize)*theApp.m_Cell_ywidth-mz;
						
						VPos.x=x-m_vPosition.x;VPos.y=hjh-m_vPosition.y;VPos.z=z-m_vPosition.z;
						if(DotProduct(View , VPos)>0)//如果节点在在视景体内
						{	
							if(hjh!=theApp.m_DEM_IvalidValue)	//如果高程有效	
							{
								//设置颜色
								glColor3f(
									(maxZ_color_R-minZ_color_R)*(hjh-m_minHeight)/dz+minZ_color_R , 
									(maxZ_color_G-minZ_color_G)*(hjh-m_minHeight)/dz+minZ_color_G , 
									(maxZ_color_B-minZ_color_B)*(hjh-m_minHeight)/dz+minZ_color_B);
								glNormal3f(x ,  hjh ,  z) ; // 设置法线
								SetTextureCoord(nXCenter+nSize-kk , nZCenter+nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
								glVertex3i(x ,  hjh ,  z);
								mnum++;
							}
						}
						
					}
				}

				
				x=(nXCenter-nSize)*theApp.m_Cell_xwidth+mx;z=-(nZCenter+nSize)*theApp.m_Cell_ywidth-mz;
				
				VPos.x=x-m_vPosition.x;VPos.y=nH4-m_vPosition.y;VPos.z=z-m_vPosition.z;
				if(DotProduct(View , VPos)>0)//如果节点在在视景体内
				{	
					if(nH4!=theApp.m_DEM_IvalidValue)	//如果高程有效	
					{	
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH4-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH4-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH4-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x ,  nH4 ,  z) ; // 设置法线
						SetTextureCoord(nXCenter-nSize , nZCenter+nSize ,  mRowIndex ,  mColIndex) ; // 设置纹理坐标
						glVertex3i(x ,  nH4 ,  z) ; // 左上角点
						CracksPatchLeft(nXCenter , nZCenter , nSize , mRowIndex , mColIndex) ; // 节点裂缝修补
						nCount++;
					}
				}
			
				if(nXCenter-nSize<=0) 
				{
					for(kk=2*nSize-1;kk>0;kk--)
					{
						hjh = GetHeightValue(nXCenter-nSize , kk+(nZCenter-nSize) ,  mRowIndex ,  mColIndex);
						x=(nXCenter-nSize)*theApp.m_Cell_xwidth+mx;z=-(kk+(nZCenter-nSize))*theApp.m_Cell_ywidth-mz;
						
						VPos.x=x-m_vPosition.x;VPos.y=hjh-m_vPosition.y;VPos.z=z-m_vPosition.z;
						if(DotProduct(View , VPos)>0)//如果节点在在视景体内
						{	
							if(hjh!=theApp.m_DEM_IvalidValue)	//如果高程有效	
							{							
								//设置颜色
								glColor3f(
									(maxZ_color_R-minZ_color_R)*(hjh-m_minHeight)/dz+minZ_color_R , 
									(maxZ_color_G-minZ_color_G)*(hjh-m_minHeight)/dz+minZ_color_G , 
									(maxZ_color_B-minZ_color_B)*(hjh-m_minHeight)/dz+minZ_color_B);
								glNormal3f(x ,  hjh ,  z) ; // 设置法线
								SetTextureCoord(nXCenter-nSize , kk+(nZCenter-nSize) ,  mRowIndex ,  mColIndex); //设置纹理坐标
								glVertex3i(mx ,  hjh ,  z);
								mnum++;
							}
						}
					}
					
				}
				

				x=(nXCenter-nSize)*theApp.m_Cell_xwidth+mx;z=-(nZCenter-nSize)*theApp.m_Cell_ywidth-mz;
				VPos.x=x-m_vPosition.x;VPos.y=nH1-m_vPosition.y;VPos.z=z-m_vPosition.z;
				if(DotProduct(View , VPos)>0)//如果节点在在视景体内
				{	
						
					if(nH1!=theApp.m_DEM_IvalidValue)	//如果高程有效	
					{
						//设置颜色
						glColor3f(
							(maxZ_color_R-minZ_color_R)*(nH1-m_minHeight)/dz+minZ_color_R , 
							(maxZ_color_G-minZ_color_G)*(nH1-m_minHeight)/dz+minZ_color_G , 
							(maxZ_color_B-minZ_color_B)*(nH1-m_minHeight)/dz+minZ_color_B);
						glNormal3f(x ,  nH1 ,  z) ; // 设置法线
						SetTextureCoord(nXCenter-nSize , nZCenter-nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
						glVertex3i(x ,  nH1 , z) ; // 重写左下角点
						nCount++;
					}
				}

				
			glEnd();
			
			nCount=nCount+mnum ; // 三角形数量
			mnum=0;
		}
	return nCount ; // 返回三角形数量
}

//设置纹理坐标
void CT3DSystemView::SetTextureCoord(float x ,  float z ,  int mRowIndex ,  int mColIndex)
{
	double X=x*theApp.m_Cell_xwidth;
	double Y=-z*theApp.m_Cell_xwidth;
	
	float u=(X)/(m_Texturexy[mCurrentTextID][2]-m_Texturexy[mCurrentTextID][0]);
	float v=-(Y)/(m_Texturexy[mCurrentTextID][3]-m_Texturexy[mCurrentTextID][1]);	
	glMultiTexCoord2fARB(GL_TEXTURE0_ARB ,  u ,  v) ; // 指定多重纹理单元TEXTURE0的纹理坐标
	glMultiTexCoord2fARB(GL_TEXTURE1_ARB ,  u ,  v) ; // 指定多重纹理单元TEXTURE1的纹理坐标
}

//节点裂缝修补(底部)
void CT3DSystemView::CracksPatchBottom(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	if(nSize<=0) return;
	if(m_ifZoomonRoad==FALSE) return;
	
	if(nZCenter+2*nSize<m_nMapSize)
	{
		if(!m_pbQuadMat[m_CurrentDemArrayIndex][nXCenter+(nZCenter+2*nSize)*m_nMapSize])
			return;
	}
	else
		return;
	CracksPatchBottom(nXCenter+nSize/2 , nZCenter+nSize/2 , nSize/2 , mRowIndex ,  mColIndex) ; // 节点裂缝修补
	SetTextureCoord(nXCenter , nZCenter+nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
	glVertex3i(nXCenter*theApp.m_Cell_xwidth+(mColIndex-1)*theApp.m_Dem_BlockWidth , GetHeightValue(nXCenter , nZCenter+nSize , mRowIndex ,  mColIndex) , -(nZCenter+nSize)*theApp.m_Cell_ywidth-((mRowIndex-1)*theApp.m_Dem_BlockWidth));
	CracksPatchBottom(nXCenter-nSize/2 , nZCenter+nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
}

//节点裂缝修补(左侧)
void CT3DSystemView::CracksPatchLeft(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	
	if(nSize<=0) return;
	if(m_ifZoomonRoad==FALSE) return;
	
	if(nXCenter-2*nSize>=0)
	{
		if(!m_pbQuadMat[m_CurrentDemArrayIndex][(nXCenter-2*nSize)+nZCenter*m_nMapSize])
			return;
	}
	else
		return;
	CracksPatchLeft(nXCenter-nSize/2 , nZCenter+nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
	SetTextureCoord(nXCenter-nSize , nZCenter ,  mRowIndex ,  mColIndex); //设置纹理坐标
	glVertex3i((nXCenter-nSize)*theApp.m_Cell_xwidth+(mColIndex-1)*theApp.m_Dem_BlockWidth , GetHeightValue(nXCenter-nSize , nZCenter , mRowIndex ,  mColIndex) , -nZCenter*theApp.m_Cell_ywidth-((mRowIndex-1)*theApp.m_Dem_BlockWidth));
	
	CracksPatchLeft(nXCenter-nSize/2 , nZCenter-nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
	
}
//节点裂缝修补(右侧)
void CT3DSystemView::CracksPatchRight(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	
	if(nSize<=0) return;
	if(m_ifZoomonRoad==FALSE) return;
	
	if(nXCenter+2*nSize<m_nMapSize)
	{
		if(!m_pbQuadMat[m_CurrentDemArrayIndex][(nXCenter+2*nSize)+nZCenter*m_nMapSize])
			return;
	}
	else
		return;
	CracksPatchRight(nXCenter+nSize/2 , nZCenter-nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
	SetTextureCoord(nXCenter+nSize , nZCenter ,  mRowIndex ,  mColIndex); //设置纹理坐标
	glVertex3i((nXCenter+nSize)*theApp.m_Cell_xwidth+(mColIndex-1)*theApp.m_Dem_BlockWidth , GetHeightValue(nXCenter+nSize , nZCenter , mRowIndex ,  mColIndex) , -nZCenter*theApp.m_Cell_ywidth-((mRowIndex-1)*theApp.m_Dem_BlockWidth));
	
	CracksPatchRight(nXCenter+nSize/2 , nZCenter+nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
}

//节点裂缝修补(顶部)
void CT3DSystemView::CracksPatchTop(int nXCenter ,  int nZCenter ,  int nSize ,  int mRowIndex ,  int mColIndex)
{
	if(nSize<=0) return;
	if(m_ifZoomonRoad==FALSE) return;
	
	if(nZCenter-2*nSize>=0)
	{
		if(!m_pbQuadMat[m_CurrentDemArrayIndex][nXCenter+(nZCenter-2*nSize)*m_nMapSize])
			return;
	}
	else
		return;
	CracksPatchTop(nXCenter-nSize/2 , nZCenter-nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
	SetTextureCoord(nXCenter , nZCenter-nSize ,  mRowIndex ,  mColIndex); //设置纹理坐标
	glVertex3i(nXCenter*theApp.m_Cell_xwidth+(mColIndex-1)*theApp.m_Dem_BlockWidth , GetHeightValue(nXCenter , nZCenter-nSize , mRowIndex ,  mColIndex) , -(nZCenter-nSize)*theApp.m_Cell_ywidth-((mRowIndex-1)*theApp.m_Dem_BlockWidth));
	CracksPatchTop(nXCenter+nSize/2 , nZCenter-nSize/2 , nSize/2 , mRowIndex ,  mColIndex);
}

//场景绘制
void CT3DSystemView::DrawScene()
{
	InitTerr();
    SetDrawMode() ; // 设置绘图模式


	if(this->m_stereo==TRUE)
	{
		SetCamra(1);
		glDrawBuffer(GL_BACK_LEFT);                      
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		DrawSky();
		DrawClock();		
		DrawTerrain();
//		DrawRangeNavigate();

		glDrawBuffer(GL_BACK_RIGHT);                      
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		SetCamra(2);
		DrawSky();
		DrawClock();
		glCallList(m_TerrainList);
//		glCallList(m_NavigateList);

	}
	else
	{
	//	SetCamra(0) ; // 设置相机
		glDrawBuffer(GL_BACK);                     
		glClearColor(1.0f , 1.0f , 1.0f , 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		DrawSky();
		if(m_ViewType==GIS_VIEW_ORTHO)		
		{
			DrawTerrain_ORTHO();
		}
		else if(m_ViewType==GIS_VIEW_PERSPECTIVE)
		{
			DrawClock();//绘制时钟指北针
			DrawTerrain() ; // 三维地形绘制
			
		}
		
		if(m_QueryType==QUERY_COORDINATE || m_QueryType==QUERY_DISTENCE || m_QueryType==SELECTLINE)
			DrawSearchPoint() ; // 绘制空间查询标志
		

		if(m_ViewType==GIS_VIEW_ORTHO)	
		{
			DrawRailwaythesme_Ortho();
		}
		else if(m_ViewType==GIS_VIEW_PERSPECTIVE) 
		{
			DrawRailwaythesme();
		}
		DrawTerrainDelaunay();	//绘制经过线路建三维模后的三维Delaunay地形
		DrawFlyPath();//绘制飞行路径

	}

}

//打开项目
void CT3DSystemView::OnMenuOpenproject() 
{
	COpenProject dlg;
	BeginWaitCursor() ; // 调用本函数显示沙漏光标 , 告诉用户系统忙
	if(dlg.DoModal()==IDOK)
	{
		theApp.bLoadImage=TRUE ; // 影像加载成功
		OnDraw (GetDC()) ; // 打开成功 , 绘制三维场景
	}
	EndWaitCursor();	//将光标切换为默认光标 , 结束等待
}

//从oracle数据库表中读取BLOB类型的影像纹理数据并写入临时影像纹理文件
BOOL CT3DSystemView::ExportBlobTextureToFIle(CString strFilename ,  int RowIndex ,  int ColIndex ,  int mID)
{
	CString tt;
	
	tt.Format("SELECT 纹理数据 FROM Texture WHERE 行号=%d AND 列号=%d AND 纹理金子塔层号=%d AND 编号= :%d FOR UPDATE" , RowIndex , ColIndex , m_currebtPhramid , mID);
	BOOL bt=myOci.ReadBOLBDataFromDB(strFilename , tt , mID) ; // 从数据库中读取BLOB数据
	return bt;
}

//设置绘图模式
void CT3DSystemView::SetDrawMode()
{
	switch(m_Drawmode)//绘制模式 
	{
	case 1://线框模式
		glDisable(GL_TEXTURE_2D) ; // 关闭纹理功能
		glPolygonMode(GL_FRONT_AND_BACK , GL_LINE);
		break;
	case 2://渲晕模式
		glDisable(GL_TEXTURE_2D) ; // 开启纹理功能
		glPolygonMode(GL_FRONT_AND_BACK , GL_FILL);
		break;
	case 3://纹理模式
		glEnable(GL_TEXTURE_2D) ; // 开启纹理功能
		glPolygonMode(GL_FRONT_AND_BACK , GL_FILL);
		break;
	}

	
}


//线框模式
void CT3DSystemView::OnDrawmodeLine() 
{

	if(m_Drawmode!=1) 
	{
		b_haveMadeRail3DwayList=FALSE;
		b_haveMadeRail3DwayList_Ortho=FALSE;
	}
	
	m_Drawmode =1;
	OnDraw (GetDC()) ; // 打开成功 , 绘制三维场景
	
}

//渲晕模式
void CT3DSystemView::OnDrawmodeRender() 
{
	if(m_Drawmode!=2) 
	{
		b_haveMadeRail3DwayList=FALSE;
		b_haveMadeRail3DwayList_Ortho=FALSE;
	}
	m_Drawmode =2;
	OnDraw (GetDC()) ; // 打开成功 , 绘制三维场景
}

//纹理模式
void CT3DSystemView::OnDrawmodeTexture() 
{
	if(m_Drawmode!=3) 
	{
		b_haveMadeRail3DwayList=FALSE;
		b_haveMadeRail3DwayList_Ortho=FALSE;
	}
	m_Drawmode =3;
	OnDraw (GetDC()) ; // 打开成功 , 绘制三维场景
}

//绘制天空背景
void CT3DSystemView::DrawSky()
{
	
	glPushMatrix();
	SetSkyProjection() ; // 设置背景天空投影
	glCallList(m_SkyList);
	if(m_bShowbreviary==TRUE)  //如果显示导航图
	{
		SetSkyProjectionNavigate() ; // 设置导航图的背景天空投影
		glCallList(m_SkyList); ; // 调用背景天空显示列表
	}
	glPopMatrix();
	glViewport(0 ,  0 ,  WinViewX , WinViewY) ; // 重新设置视口大小
	
}

//背景天空投影设置
void CT3DSystemView::SetSkyProjection()
{
	glViewport(0 ,  0 ,  WinViewX , WinViewY) ; // 设置视口大小
	
    glMatrixMode( GL_PROJECTION ); //将当前矩阵设置为投影矩阵
    glLoadIdentity() ; // 将当前矩阵置换为单位阵  
	
    //设置正射投影视景体
	if (WinViewX <= WinViewY) 
		glOrtho (0.0f ,  1.0f ,  0.0f , 1.0f*(GLfloat)WinViewX/(GLfloat)WinViewY ,  -1.0f , 1.0f);
    else 
		glOrtho (0.0f , 1.0f*(GLfloat)WinViewY/(GLfloat)WinViewX ,  0.0f ,  1.0f ,  -1.0f ,  1.0f);
	
	glMatrixMode( GL_MODELVIEW ) ; // 将当前矩阵设置为模型矩阵	
	glLoadIdentity ();	//将当前矩阵置换为单位阵  
}

//背景天空导航图投影设置
void CT3DSystemView::SetSkyProjectionNavigate()
{
	glViewport(WinViewX*5/6 ,  WinViewY*5/6 , WinViewX/6 ,  WinViewY/6) ; // 设置视口大小
	
    glMatrixMode( GL_PROJECTION ); //将当前矩阵设置为投影矩阵
    glLoadIdentity() ; // 将当前矩阵置换为单位阵  
	
	//设置正射投影视景体
    if (WinViewX <= WinViewY) 
		glOrtho (0.0f ,  1.0f ,  0.0f , 1.0f*(GLfloat)WinViewX/(GLfloat)WinViewY ,  -1.0f , 1.0f);
    else 
		glOrtho (0.0f , 1.0f*(GLfloat)WinViewY/(GLfloat)WinViewX ,  0.0f ,  1.0f ,  -1.0f ,  1.0f);
	
	glMatrixMode( GL_MODELVIEW ) ; // 将当前矩阵设置为模型矩阵	
	glLoadIdentity ();	//将当前矩阵置换为单位阵  
}

//生成绘制背景天空显示列表
void CT3DSystemView::makeSkykList()
{
	glNewList(m_SkyList , GL_COMPILE);
		DrawBackground(); //绘制背景天空         
	glEndList();
}

//绘制背景天空    
void CT3DSystemView::DrawBackground()
{
	glPushMatrix();	
	glDisable(GL_DEPTH_TEST);
	glDisable(GL_LIGHTING);
	glShadeModel(GL_SMOOTH);
	
	glPolygonMode(GL_FRONT_AND_BACK , GL_FILL); 
	glEnable(GL_TEXTURE_2D);	
	glBindTexture(GL_TEXTURE_2D ,  m_cTxtSky.GetTxtID());
	
	glBegin(GL_QUADS);
	{			
		glTexCoord2f((float)0.0 ,  (float)1.0);	glVertex2f(0.0 , 1.0);
		glTexCoord2f((float)1.0 ,  (float)1.0);	glVertex2i(1.0 , 1.0);
		
		glTexCoord2f((float)1.0 ,  (float)0.0); glVertex2f(1.0f , 0.0f);
		glTexCoord2f((float)0.0 ,  (float)0.0); glVertex2f(0.0f , 0.0f);
	}
	glEnd();
	glPolygonMode(GL_FRONT_AND_BACK , GL_LINE); 
	glDisable(GL_TEXTURE_2D);	
	glEnable(GL_DEPTH_TEST);	
	glPopMatrix();
	
}

//双目立体显示打开/关闭
void CT3DSystemView::OnMenuStereo() 
{
	if(m_stereo==TRUE)//如果是立体模式
		m_stereo=FALSE;//标志为关闭
	else ////如果不是立体模式
	{
		m_stereo=TRUE;//标志为打开
		
	}
	OnDraw (GetDC());//刷新三维场景
}

//设置"双目立体模式"子菜单选中标志
void CT3DSystemView::OnUpdateMenuStereo(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable(bStereoAvailable); //如果显示不支持立体模式
	pCmdUI->SetCheck(m_stereo);//根据m_stereo值设置设置"双目立体模式"子菜单选中标志
	
}

//空间查询标志设置
void CT3DSystemView::OnSpacequerySet() 
{
	CSpaceSearchSet dlg;
	
	dlg.m_shizxLength=m_shizxLength ; // 查询标志十字线长度
	dlg.m_shuzxHeight=m_shuzxHeight ; // 查询标志竖直线高度
	dlg.m_QueryLineWidth=m_QueryLineWidth ; // 查询标志线的宽度
	dlg.m_QueryColorR=m_QueryColorR ; // 查询标志线的颜色(红)
	dlg.m_QueryColorG=m_QueryColorG ; // 查询标志线的颜色(绿)
	dlg.m_QueryColorB=m_QueryColorB ; // 查询标志线的颜色(蓝)
	if(dlg.DoModal()==IDOK) //如果对话框打开成功
	{
		m_shizxLength=dlg.m_shizxLength ; // 得到新设置的查询标志十字线长度
		m_shuzxHeight=dlg.m_shuzxHeight ; // 得到新设置的查询标志竖直线高度
		m_QueryLineWidth=dlg.m_QueryLineWidth ; // 得到新设置的查询标志线的宽度
		m_QueryColorR=dlg.m_QueryColorR ; // 得到新设置的查询标志线的颜色(红)
		m_QueryColorG=dlg.m_QueryColorG ; // 得到新设置的查询标志线的颜色(绿)
		m_QueryColorB=dlg.m_QueryColorB ; // 得到新设置的//查询标志线的颜色(蓝)
	}	
}


void CT3DSystemView::OnLButtonDown(UINT nFlags ,  CPoint point) 
{
	/**/
	if(m_ViewType==GIS_VIEW_ORTHO)	//如果是正射投影模式	
	{
		if(m_OrthoZommPan==ORTHO_ZOOMIN) //放大影像模式
			OnOrthoZoomIn(); 
		else if(m_OrthoZommPan==ORTHO_ZOOMOUT)//缩小影像模式
			OnOrthoZoomOut();
		else if(m_OrthoZommPan==ORTHO_PAN)//平移影像模式
		{
			m_preX=point.x;
			m_preY=point.y;
			OnOrthoPan();
		}
		else if(m_OrthoZommPan==ORTHO_ZOOMWINDOW) //开窗放大模式
		{
			if(m_pushNumb<=0) //
			{
				m_preX=point.x;//记录鼠标第1点坐标
				m_preY=point.y;
				
				m_pushNumb=1;
				SetCapture();
			}
			else if(m_pushNumb==1)
			{
				m_currentX=point.x;//记录鼠标第2点坐标
				m_currentY=point.y;
				
				m_OrthoZoomWindowRect_x1=mCalF.MinValueXY(m_preX,m_currentX);
				m_OrthoZoomWindowRect_y1=mCalF.MinValueXY(m_preY,m_currentY);
				m_OrthoZoomWindowRect_x2=mCalF.MaxValueXY(m_preX,m_currentX);
				m_OrthoZoomWindowRect_y2=mCalF.MaxValueXY(m_preY,m_currentY);
				
				float bl1=WinViewX/(m_OrthoZoomWindowRect_x2-m_OrthoZoomWindowRect_x1);
				float bl2=WinViewY/(m_OrthoZoomWindowRect_y2-m_OrthoZoomWindowRect_y1);
				float bl=bl1;
				if(bl>bl2)	
					bl=bl2;
				
				m_OrthoViewSize=m_OrthoViewSize/(1+bl/5);
				OnDraw (GetDC());
				m_pushNumb=0;
				ReleaseCapture();
				
			}
		}
		
	}


	if(m_QueryType==QUERY_COORDINATE)//空间三维坐标查询
	{
		m_bmouseView=false;
		m_oldMousePos=point;
		ScreenToGL(point);
	}
	else if(m_QueryType==QUERY_DISTENCE)//空间距离查询
	{
		m_bmouseView=false; // 关闭鼠标控相机旋转
		m_oldMousePos=point;
		ScreenToGL(point);
	}	
	else if(m_QueryType==SELECTLINE)
	{
		m_bmouseView=false; // 关闭鼠标控相机旋转
		m_oldMousePos=point;
		ScreenToGL(point); 
	}
	else if(m_QueryType==SELECTFLYPATH)//进行飞行路径选择
	{
		m_bmouseView=false;
		m_oldMousePos=point;
		ScreenToGL(point);
	}
	else
	{
		m_bmouseView=true;
		m_oldMousePos=point;
	}

	CView::OnLButtonDown(nFlags ,  point);
}

//查询三维坐标
void CT3DSystemView::OnQueryCoordinate() 
{
	m_OrthoZommPan=-1;
	if(m_QueryType==QUERY_COORDINATE) //如果当前已经是查询三维坐标状态，则关闭
		m_QueryType=-1;
	else //如果当前已经不是查询三维坐标状态，则打开
		m_QueryType=QUERY_COORDINATE;
}

//设置是否选中状态
void CT3DSystemView::OnUpdateQueryCoordinate(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_QueryType==QUERY_COORDINATE);
	
}

//查询空间距离
void CT3DSystemView::OnQueryDistence() 
{
	m_OrthoZommPan=-1;
	
	if(m_QueryType==QUERY_DISTENCE) //如果当前已经是查询空间距离状态，则关闭
		m_QueryType=-1;
	else // 如果当前不是查询空间距离状态，则关闭，则打开
		m_QueryType=QUERY_DISTENCE;
}

//设置是否选中状态
void CT3DSystemView::OnUpdateQueryDistence(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_QueryType==QUERY_DISTENCE);
	
}

//
void CT3DSystemView::ScreenToGL(CPoint point)
{
	int mouse_x=point.x;
	int mouse_y=point.y;
	
	GLint viewport[4];
	GLdouble modelview[16] , projection[16];
	GLdouble wx , wy , wz;
	float winX , winY , winZ;
	
	glPushMatrix();
	glGetDoublev(GL_MODELVIEW_MATRIX ,  modelview);
	glGetDoublev(GL_PROJECTION_MATRIX ,  projection);
	glGetIntegerv(GL_VIEWPORT ,  viewport);
	glPopMatrix();
	
	winX=(float)mouse_x;
	winY=(float)viewport[3]-(float)mouse_y-1;
	glReadPixels(mouse_x , 
		int(winY) , 
		1 , 1 , 
		GL_DEPTH_COMPONENT , 
		GL_FLOAT , 
		&winZ);
	gluUnProject((GLdouble)winX , (GLdouble)winY , (GLdouble)
		winZ , modelview , projection , viewport , &wx , &wy , &wz);
	
	CString tt;
	

	
	
	if(winZ>=0 && winZ<1.0) 
	{
		if(m_QueryType==QUERY_COORDINATE)//查询三维坐标
		{
			if(m_ViewType==GIS_VIEW_ORTHO) //如果是正射投影模式
			{
				double mx=wx+m_ortho_CordinateOriginX; //计算x坐标
				double my=wy+m_ortho_CordinateOriginY; //计算y坐标
				mx=theApp.m_DemLeftDown_x+mx*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);//转换为大地坐标
				my=theApp.m_DemLeftDown_y+my*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/m_ortho_CordinateXYScale;//转换为大地坐标
				wz=m_demInsert.GetHeightValue(mx,my,2);//从DEM中内插出对应的高程
				tt.Format("当前坐标(x,y,z)=(%.3f,%.3f,%.3f)",mx,my,wz);
			}
			else  if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果是透视投影模式
			{
				tt.Format("当前坐标(x,y,z)=(%.3f,%.3f,%.3f)",wx+theApp.m_DemLeftDown_x,-wz+theApp.m_DemLeftDown_y,wy);	
				
			}
			pt1[0]=wx;pt1[1]=wy;pt1[2]=wz; //查询获得的三维大地坐标
			OnDraw(GetWindowDC()); //刷新三维场景
			MessageBox(tt,"三维坐标查询",MB_ICONINFORMATION); //给出坐标查询信息
		}	
		else if(m_QueryType==QUERY_DISTENCE) // 查询空间距离
		{
			if(	m_bSearchDistencePtNums>=2) // 如果选择点数2个，归零
				m_bSearchDistencePtNums=0;
			
			m_bSearchDistencePtNums++ ; //  选择点数 + 1
			
			if(m_bSearchDistencePtNums==1) // 如果只选择了1个点
			{
				pt1[0]=wx;pt1[1]=wy;pt1[2]=wz;	// 将三维点坐标存储到数组 pt1[] 里面
			}
			else	//选择了两个点，则表示可以计算空间距离了		 		
			{
				pt2[0]=wx;pt2[1]=wy;pt2[2]=wz; // 将三维点坐标存储到数组 pt2[] 里面

				double mdistence;
				
				if(m_ViewType==GIS_VIEW_ORTHO)	 //如果是正射投影模式
				{
					double nx1,ny1,nz1,nx2,ny2,nz2;
					nx1=pt1[0]+m_ortho_CordinateOriginX;//计算P1点x坐标
					nx1=theApp.m_DemLeftDown_x+nx1*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);//将P1点x坐标转换为大地坐标
					ny1=pt1[1]+m_ortho_CordinateOriginY;//计算P1点y坐标
					ny1=theApp.m_DemLeftDown_y+ny1*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/m_ortho_CordinateXYScale;//将P1点y坐标转换为大地坐标
					nz1	=m_demInsert.GetHeightValue(nx1,ny1,2);//从DEM中内插出P1点对应的高程
					pt1[2]=nz1;
					
					nx2=pt2[0]+m_ortho_CordinateOriginX;//计算P2点x坐标
					nx2=theApp.m_DemLeftDown_x+nx2*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);//将P2点x坐标转换为大地坐标
					ny2=pt2[1]+m_ortho_CordinateOriginY;//计算P2点y坐标
					ny2=theApp.m_DemLeftDown_y+ny2*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/m_ortho_CordinateXYScale;//将P2点y坐标转换为大地坐标
					nz2	=m_demInsert.GetHeightValue(nx2,ny2,2);//从DEM中内插出P2点对应的高程
					pt2[2]=nz2;
					
					//计算距离
					mdistence=sqrt((nx2-nx1)*(nx2-nx1)+(ny2-ny1)*(ny2-ny1)+(nz2-nz1)*(nz2-nz1));
					tt.Format("两点为:\n(x1,y1,z1)=(%.3f,%.3f,%.3f)\n(x2,y2,z2)=(%.3f,%.3f,%.3f)\n距离Dis=%.3f",\
						nx1,ny1,nz1,nx2,ny2,nz2,mdistence);
					
				}
				else  if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果是透视投影模式
				{
					//计算距离
					mdistence=sqrt((pt2[0]-pt1[0])*(pt2[0]-pt1[0])+(pt2[1]-pt1[1])*(pt2[1]-pt1[1])+(pt2[2]-pt1[2])*(pt2[2]-pt1[2]));
					tt.Format("两点为:\n(x1,y1,z1)=(%.3f,%.3f,%.3f)\n(x2,y2,z2)=(%.3f,%.3f,%.3f)\n距离Dis=%.3f",pt1[0]+theApp.m_DemLeftDown_x,-pt1[2]+theApp.m_DemLeftDown_y,pt1[1],pt2[0]+theApp.m_DemLeftDown_x,-pt2[2]+theApp.m_DemLeftDown_y,pt2[1],mdistence);
				}
				
				OnDraw(GetWindowDC());//刷新三维场景
				MessageBox(tt,"三维距离查询",MB_ICONINFORMATION);//给出距离查询信息

			}
		}
		else if(m_QueryType==SELECTLINE) //如果是三维选线设计
		{
			
			PCordinate ppt = new Cordinate;  //定义
			if( ppt == NULL )      //如果失败 
			{  
				AfxMessageBox( "Failed to add a new ppt"); 
				return ;        
			}
			
			if(m_ViewType==GIS_VIEW_ORTHO)	//如果是正射投影模式	
			{
				double mx=wx+m_ortho_CordinateOriginX; //计算x坐标
				double my=wy+m_ortho_CordinateOriginY; //计算y坐标
				mx=theApp.m_DemLeftDown_x+mx*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);//转换为大地坐标
				my=theApp.m_DemLeftDown_y+my*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/m_ortho_CordinateXYScale;//转换为大地坐标
				wz=m_demInsert.GetHeightValue(mx,my,2);//从DEM中内插出对应的高程
				mx-=theApp.m_DemLeftDown_x;
				my-=theApp.m_DemLeftDown_y;
				ppt->x=mx;ppt->y=wz;ppt->z=-my; //记录转换后的设计交点三维坐标				
			}
			else  if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果是透视投影模式
			{
				ppt->x=wx;ppt->y=wy;ppt->z=wz;  //记录设计交点三维坐标	
			}
			
			
			m_oldlinePtnums=myDesingScheme.PtS_JD.GetSize();//当前线路方案原有设计交点数

			if(m_oldlinePtnums==0) //如果当前线路方案没有设计交点,即还没有进行该方案的设计
			{
				myDesingScheme.PtS_JD.Add(ppt); //加入设计交点到PtS_JD数组
				m_linePtnums=myDesingScheme.PtS_JD.GetSize();//当前线路方案原有设计交点数
				
				PLineCurve pTempCurveElements = new LineCurve; //定义新的交点变量
				//第一个设计交点
				
				//直缓里程=方案的起点里程   缓直里程=方案的起点里程
				pTempCurveElements->ZH=pTempCurveElements->HZ=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC;

				//缓圆里程=方案的起点里程   圆缓里程=方案的起点里程
				pTempCurveElements->HY=pTempCurveElements->YH=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC;
				
				//转向角、切线长和曲线长=0
				pTempCurveElements->Alfa=pTempCurveElements->T=pTempCurveElements->L=0;
			
				//曲线半径、旋转类型、外矢量距=0
				pTempCurveElements->R=pTempCurveElements->RoateStyle=pTempCurveElements->E=0;
				
				//坡长、缓和曲线长、圆曲线长、夹直线长=0
				pTempCurveElements->P=pTempCurveElements->L0=pTempCurveElements->Ly=pTempCurveElements->Jzxc=0;

				//交点里程=方案的起点里程
				pTempCurveElements->JDLC=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC;

				//交点ID="JD0";
				pTempCurveElements->ID="JD0";

				pTempCurveElements->x=myDesingScheme.PtS_JD.GetAt(0)->x;			//交点的x坐标				
				pTempCurveElements->y=fabs(myDesingScheme.PtS_JD.GetAt(0)->z);		//交点的yx坐标				
				pTempCurveElements->z=-myDesingScheme.PtS_JD.GetAt(0)->y;			//交点的z坐标

				//加入交点元素到DCurveElementss模板数组中
				myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].Add(pTempCurveElements); 
			}
			else  //如果当前线路方案设计交点数>0,表示已经进行该方案的设计
			{
				CPLaneRL0 dlg; 
				if(m_linePtnums<=0)
					m_linePtnums=myDesingScheme.PtS_JD.GetSize();
				tt.Format("JD%d",m_linePtnums); //交点ID自动增加
				
				dlg.m_ID=tt; //交点ID
				if(dlg.DoModal()==IDOK) //如果交点信息设计成功
				{
					PLineCurve pTempCurveElements = new LineCurve; 
					
					pTempCurveElements->R=dlg.R;
					pTempCurveElements->L0=dlg.m_L0;
					pTempCurveElements->ID=dlg.m_ID;
					pTempCurveElements->P=(pTempCurveElements->L0*pTempCurveElements->L0)/(pTempCurveElements->R*24.0); 
					
					
					pTempCurveElements->x=ppt->x;
					pTempCurveElements->y=-ppt->z;
					pTempCurveElements->z=ppt->y;
					myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].Add(pTempCurveElements);
					myDesingScheme.PtS_JD.Add(ppt);
					
					
					m_linePtnums=myDesingScheme.PtS_JD.GetSize();
					
					if(myDesingScheme.PtS_JD.GetSize()>1)
						OnDraw(GetWindowDC());
					
				}
			}
			
		}
		else if(m_QueryType==SELECTFLYPATH) // 如果是设置飞行路径
		{
			PCordinate ppt = new Cordinate;  
			if(m_ViewType==GIS_VIEW_ORTHO)	//如果是正射投影模式	
			{
				double mx=wx+m_ortho_CordinateOriginX;//计算x坐标
				double my=wy+m_ortho_CordinateOriginY;//计算y坐标
				mx=theApp.m_DemLeftDown_x+mx*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);//转换为大地坐标
				my=theApp.m_DemLeftDown_y+my*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)/m_ortho_CordinateXYScale;//转换为大地坐标
				wz=m_demInsert.GetHeightValue(mx,my,2);//从DEM中内插出对应的高程
				mx-=theApp.m_DemLeftDown_x; //x坐标变换
				my-=theApp.m_DemLeftDown_y;//y坐标变换
				ppt->x=mx;ppt->y=wz;ppt->z=-my; //记录飞行路径的三维坐标
			}
			else  if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果是透视投影模式
			{
				ppt->x=wx;ppt->y=wy;ppt->z=wz;//记录飞行路径的三维坐标
			}
			
			m_FlayPath.Add(ppt); //将飞行路径的三维坐标存储到数组m_FlayPath
			OnDraw(GetDC()); //刷新三维场景,用来显示绘制的飞行路径
		}
	}
	else
	{
		MessageBox("鼠标选择点不够精确 , 请精确选择点!");
		m_bSearchDistencePtNums=0;
	}
}

//绘制空间查询标志
void CT3DSystemView::DrawSearchPoint()
{
 
    glViewport(0 ,  0 ,  WinViewX ,  WinViewY) ; // 重新设置视口大小
	if(m_QueryType==QUERY_COORDINATE) //三维空间坐标查询
	{
		if(pt1[0]!=-99999)
		{
			glLineWidth(m_QueryLineWidth) ; // 设置查询标志线宽度
			glColor3f(m_QueryColorR/255.0 , m_QueryColorG/255.0 , m_QueryColorB/255.0) ; // 设置查询标志线颜色
			//绘制十字型查询标志线
			glBegin(GL_LINES);
				glVertex3f(pt1[0]-m_shizxLength , pt1[1] , pt1[2]);
				glVertex3f(pt1[0]+m_shizxLength , pt1[1] , pt1[2]);
				glEnd();
				
				glBegin(GL_LINES);
				glVertex3f(pt1[0] , pt1[1] , pt1[2]-m_shizxLength);
				glVertex3f(pt1[0] , pt1[1] , pt1[2]+m_shizxLength);
				glEnd();
				
				glBegin(GL_LINES);
				glVertex3f(pt1[0] , pt1[1] , pt1[2]);
				glVertex3f(pt1[0] , pt1[1]+m_shuzxHeight , pt1[2]);
			glEnd();
		}

	}
	else if(m_QueryType==QUERY_DISTENCE && m_bSearchDistencePtNums==2)//三维空间距离查询(在选择两个空间点之后才能够计算空间距离)
	{
		glLineWidth(m_QueryLineWidth) ; // 设置查询标志线宽度
		glColor3f(m_QueryColorR/255.0 , m_QueryColorG/255.0 , m_QueryColorB/255.0) ; // 设置查询标志线颜色
		
		//绘制十字型查询标志线
		glBegin(GL_LINES);
		glVertex3f(pt1[0]-m_shizxLength , pt1[1] , pt1[2]);
		glVertex3f(pt1[0]+m_shizxLength , pt1[1] , pt1[2]);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(pt1[0] , pt1[1] , pt1[2]-m_shizxLength);
		glVertex3f(pt1[0] , pt1[1] , pt1[2]+m_shizxLength);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(pt1[0] , pt1[1] , pt1[2]);
		glVertex3f(pt1[0] , pt1[1]+m_shuzxHeight , pt1[2]);
		glEnd();
		
		
		glBegin(GL_LINES);
		glVertex3f(pt2[0]-m_shizxLength , pt2[1] , pt2[2]);
		glVertex3f(pt2[0]+m_shizxLength , pt2[1] , pt2[2]);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f (pt2[0] , pt2[1] , pt2[2]-m_shizxLength);
		glVertex3f (pt2[0] , pt2[1] , pt2[2]+m_shizxLength);
		glEnd();
		
		glBegin(GL_LINES);
		glVertex3f(pt2[0] , pt2[1] , pt2[2]);
		glVertex3f(pt2[0] , pt2[1]+m_shuzxHeight , pt2[2]);
		glEnd();
		
		
		glBegin(GL_LINES);
		glVertex3f(pt1[0] , pt1[1] , pt1[2]);
		glVertex3f(pt2[0] , pt2[1] , pt2[2]);
		glEnd();

		glLineWidth(1.0);

	}
	else if(m_QueryType==SELECTLINE ||myDesingScheme.PtS_JD.GetSize()>0 )//如果当前已经三维选线状态
	{
		//绘制选结过程中的设计交点连线
		m_oldlinePtnums=m_linePtnums;
		glColor3f(0,0,1);
		glLineWidth(2.0);
	
		
		if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果是透视投影模式
		{
			for (int i=0;i<myDesingScheme.PtS_JD.GetSize()-1;i++)
			{
				DrawCenterLine(i,TRUE,m_currentSchemeIndexs); //绘制线路中心线
			}
		}
		else if(m_ViewType==GIS_VIEW_ORTHO)		//如果是正射投影模式
		{
			
			for (int i=0;i<myDesingScheme.PtS_JD.GetSize()-1;i++)
			{
				DrawCenterLine(i,TRUE,m_currentSchemeIndexs);//绘制线路中心线
			}
		}
	}

}



//响应鼠标移到函数
void CT3DSystemView::OnMouseMove(UINT nFlags, CPoint point) 
{
	if(theApp.bLoginSucceed==FALSE) //保证未打开项目的情况下,不需要响应鼠标移到消息
		return ; //返回
	
	if(m_bmouseView==TRUE) //如果鼠标控制场景打开时
	{
		mCamraUpdate(); //根据鼠标和键盘实现三维场景相机的移动和旋转控制
		OnDraw (GetDC()); //刷新三维场景
		
	}
	CView::OnMouseMove(nFlags, point);
}

//根据鼠标和键盘实现三维场景相机的移动和旋转控制
void CT3DSystemView::mCamraUpdate()
{
	CVector3 vCross = CrossProduct(m_vView - m_vPosition, m_vUpVector); //叉积计算
	m_vStrafe = Normalize(vCross); //vCross归一化
	SetViewByMouse(); //通过鼠标实现相机控制
	CheckForMovement();//通过键盘实现相机控制
	m_vPosition.y+=(m_viewHeight-m_oldviewHeight); //新的相机视点y坐标
	m_oldviewHeight=m_viewHeight; //记录当前相机视点高度
}

//通过鼠标实现相机控制
void CT3DSystemView::SetViewByMouse()
{
	if(m_bmouseView==false) //如果鼠标控制场景关闭时,返回
		return;
 
	float angleY = 0.0f;							
	float angleZ = 0.0f;							
	static float currentRotX = 0.0f;
	
	POINT mousePos;									
	GetCursorPos(&mousePos);//得到光标的位置，以屏幕坐标表示,存储到	mousePos变量里			
	
	//如果鼠标坐标没有变化,返回
	if( (mousePos.x == m_oldMousePos.x) && (mousePos.y == m_oldMousePos.y) )
		return;
	
   /* 对鼠标y坐标前后之差进行缩小(这里缩小500倍,这个值可根据实际地形设置),如果angleY值
	太大,表示即使鼠标y坐标前后之差很小,也会导致三维地形场景在Y方向上变化很大*/
	angleY = (float)( (m_oldMousePos.x - mousePos.x) ) / 500.0f;

	/* 对鼠标z坐标前后之差进行缩小(这里缩小4000倍,这个值可根据实际地形设置),如果angleZ值
	太大,表示即使鼠标z坐标前后之差很小,也会导致三维地形场景在Z方向上变化很大*/
	angleZ = (float)( (m_oldMousePos.y - mousePos.y) ) / 4000.0f;
	
	currentRotX -= angleZ;  
	
	CVector3 vAxis = CrossProduct(m_vView - m_vPosition, m_vUpVector); //叉积计算
	vAxis = Normalize(vAxis);	//vAxis归一化
	
	RotateView(angleZ, vAxis.x, vAxis.y, vAxis.z);//通过鼠标控制相机的旋转(旋转视角)
	RotateView(angleY, 0, 1, 0);//通过鼠标控制相机的旋转(旋转视角)

	m_oldMousePos.x=mousePos.x;		//记录当前鼠标x坐标
	m_oldMousePos.y=mousePos.y;		//记录当前鼠标y坐标
		
}

//通过鼠标控制相机的旋转(旋转视角)
void CT3DSystemView::RotateView(float angle, float x, float y, float z)
{
	CVector3 vNewView;
	
	CVector3 vView = m_vView - m_vPosition;	//相机视点与观察点三维坐标差值	
	
	float cosTheta = (float)cos(angle); //得到旋转视角的cos函数值
	float sinTheta = (float)sin(angle);//得到旋转视角的sin函数值
	
	vNewView.x  = (cosTheta + (1 - cosTheta) * x * x)		* vView.x;
	vNewView.x += ((1 - cosTheta) * x * y - z * sinTheta)	* vView.y;
	vNewView.x += ((1 - cosTheta) * x * z + y * sinTheta)	* vView.z;
	
	vNewView.y  = ((1 - cosTheta) * x * y + z * sinTheta)	* vView.x;
	vNewView.y += (cosTheta + (1 - cosTheta) * y * y)		* vView.y;
	vNewView.y += ((1 - cosTheta) * y * z - x * sinTheta)	* vView.z;
	
	vNewView.z  = ((1 - cosTheta) * x * z - y * sinTheta)	* vView.x;
	vNewView.z += ((1 - cosTheta) * y * z + x * sinTheta)	* vView.y;
	vNewView.z += (cosTheta + (1 - cosTheta) * z * z)		* vView.z;
	
	m_vView = m_vPosition + vNewView; //得到旋转后的相机视点坐标

	GetNorthPtangle(); //计算时钟指北针指向角度
}

//在Z轴方向上移动时设置相机观察点和视点坐标
void CT3DSystemView::MoveCamera(float speed)
{
	CVector3 vVector = m_vView - m_vPosition;//相机视点与观察点三维坐标差值
	vVector = Normalize(vVector); //相机视点与观察点三维坐标差值归一化
	m_vPosition.x += vVector.x * speed;	//相机视点x新坐标	
	m_vPosition.z += vVector.z * speed;	//相机视点z新坐标	
	m_vView.x += vVector.x * speed;		//相机观察点x新坐标
	m_vView.z += vVector.z * speed;		//相机观察点z新坐标
}

//根据键盘按键值来重新设置相机
void CT3DSystemView::CheckForMovement()
{
	if(m_keynumber ==1)	// ↑上箭头按键，向场景外移动(Z轴正方向)
	{		
		MoveCamera(m_Step_Z); //在Z轴方向上移动时设置相机观察点和视点坐标
	}
	
	if(m_keynumber ==2) 	// ↓下箭头按键，向场景外移动(Z轴负方向)
	{
		MoveCamera(-m_Step_Z);	//在Z轴方向上移动时设置相机观察点和视点坐标
	}
	
	if(m_keynumber ==3)	// ←左箭头按键，向左方向移动场景(X轴负方向)
	{
		StrafeCamera(-m_Step_X);	//在X轴方向上移动时设置相机观察点和视点坐标
	}

	if(m_keynumber ==4)	// →右箭头按键，向右方向移动场景(X轴正方向)
	{
		StrafeCamera(m_Step_X);	//在X轴方向上移动时设置相机观察点和视点坐标
	}

	Derx=m_vPosition.x-m_oldvPosition.x;//相机视点在X方向上的变化量差值
	Derz=m_vPosition.z-m_oldvPosition.z;//相机视点在Z方向上的变化量差值
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	
	//如果相机视点在X方向上或Z方向上的变化量差值>=DEM地形子块的总宽度时,调入新的地形块
	if(fabs(Derz)>=theApp.m_Dem_BlockWidth || fabs(Derx)>=theApp.m_Dem_BlockWidth)
	{
		if(m_loddem_StartRow!=1 && m_loddem_StartCol!=1 \
			&& m_loddem_EndRow!=theApp.m_BlockRows && m_loddem_EndCol!=theApp.m_BlockCols)
		{
// 			LoadNewData();
		}
	}
}

//在X轴方向上移动时设置相机观察点和视点坐标
void CT3DSystemView::StrafeCamera(float speed)
{
	m_vPosition.x += m_vStrafe.x * speed;	//相机视点x新坐标
	m_vPosition.z += m_vStrafe.z * speed;	//相机视点z新坐标
	m_vView.x += m_vStrafe.x * speed;	//相机观察点x新坐标
	m_vView.z += m_vStrafe.z * speed;	//相机观察点z新坐标
}

//键盘按下响应函数
void CT3DSystemView::OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
  m_keynumber=0;

   switch (nChar) //根据按键字符来进行相应设置
   {
   case VK_LEFT:  
	   m_keynumber=3;
	   m_xTrans-=m_Step_X;	//在X方向上移动的距离累计
	   OnDraw (GetDC());	//刷新三维场景
	   break;
   case VK_RIGHT:
	   m_keynumber=4;
	   m_xTrans+=m_Step_X;	//在X方向上移动的距离累计
	   OnDraw (GetDC());	//刷新三维场景
	   break;
   case VK_UP:
		m_zTrans -= m_Step_Z;	//在Z方向上移动的距离累计
	    m_keynumber=1;
	   OnDraw (GetDC());	//刷新三维场景
	   break;
   case VK_DOWN:
		m_zTrans += m_Step_Z;	//在Z方向上移动的距离累计
		m_keynumber=2;
	   OnDraw (GetDC());	//刷新三维场景
	   break;
	case 'F':     
		m_ViewWideNarrow += 1.0;	//飞行视野增大,看见场景范围增大了,相当于相机镜头向屏幕外方向移动	
		OnDraw (GetDC());//刷新三维场景
		break;
	case 'V':     
		m_ViewWideNarrow -= 1.0;    //飞行视野减小,看见场景范围减小了,相当于相机镜头向屏幕里方向移动
		OnDraw (GetDC());//刷新三维场景
		break;
	case 'H':     					   //视角下倾
		m_ViewUpDown -= 0.2;
		OnDraw (GetDC());//刷新三维场景
		break;
	case 'N':     					   //视角上倾
		m_ViewUpDown += 0.2;
		OnDraw (GetDC());//刷新三维场景
		break;
	case 'J':     					   //加速
		m_flyspeed -= 1;
		if(m_flyspeed<=1)
			m_flyspeed=1;
		SetFLyTimer();//设置飞行计时器
		break;
	case 'M':     					   //减速
		m_flyspeed += 1;
		SetFLyTimer();//设置飞行计时器
		break;
	case 'G':     
		m_StaticHeight=m_StaticHeight*1.1;
		OnDraw (GetDC());//刷新三维场景
		break;
	case 'B':     
		m_StaticHeight=m_StaticHeight/1.1; //减少固定飞行高度
		OnDraw (GetDC());//刷新三维场景
		break;
	case VK_F2:								 //显示漫游热键帮助
		DisplayHelp();					   
		break;
	case 'Z':								//沿方案线漫游
		OnFlyRoutinschemeline();					   
		break;
	case 'S':								//停止飞行
		OnFlyStop();					   
		break;
	case 'P':								//开始/暂停飞行
		OnFlyPlaypause();					   
		break;
   }
	mCamraUpdate();	//重新调整相机
	m_keynumber=0; //恢复按键0值

	CView::OnKeyDown(nChar, nRepCnt, nFlags);
}

//按下鼠标右键响应函数
void CT3DSystemView::OnRButtonDown(UINT nFlags, CPoint point) 
{
	m_QueryType=-1;//关闭三维空间查询,选线设计、飞行路径设置

	m_bmouseView=FALSE; //关闭鼠标控相机旋转
	m_OrthoZommPan=-1;	//关闭正射投影模式下场景控制
	
	CView::OnRButtonDown(nFlags, point);
}


//菜单"正射投影模式"响应函数
void CT3DSystemView::OnMenuProjecttionortho() 
{
 	m_stereo=FALSE; //关闭
	m_ViewType =GIS_VIEW_ORTHO; //正射投影模式
	SetProjection(); //重新设置
	OnDraw (GetDC());	//刷新三维场景
}

//"正射投影模式"设置/取消选中标志
void CT3DSystemView::OnUpdateMenuProjecttionortho(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ViewType ==GIS_VIEW_ORTHO);//根据m_ViewType值是否设置选中标志
	
}

void CT3DSystemView::OnMenuPerspective() 
{
	m_ViewType =GIS_VIEW_PERSPECTIVE;
	SetProjection();//重新设置
	OnDraw (GetDC());	//刷新三维场景
	
}

//"透视投影模式"设置/取消选中标志
void CT3DSystemView::OnUpdateMenuPerspective(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_ViewType ==GIS_VIEW_PERSPECTIVE);//根据m_ViewType值是否设置选中标志
		pCmdUI->Enable(TRUE);
}


//根据投影模式的不同,重新设置投影参数
void CT3DSystemView::SetProjection()
{
	glViewport(0, 0, WinViewX, WinViewY);//设置视口大小
    glMatrixMode(GL_PROJECTION);//将当前矩阵设置为投影矩阵
    glLoadIdentity();//将当前矩阵置换为单位阵
	
	if(m_ViewType==GIS_VIEW_ORTHO)	//如果投影模式为正射投影模式	
	{
		float MS=0.6; //初始正射投影窗口大小,这个值可根据地形范围自行调整
		if (WinViewX <= WinViewY) 
		{
			//设置正射投影视体
			glOrtho (-MS, MS, -MS*(GLfloat)WinViewY/(GLfloat)WinViewX, 
				MS*(GLfloat)WinViewY/(GLfloat)WinViewX, -1,1);
			
			m_ViewXYscale=(GLfloat)WinViewY/(GLfloat)WinViewX;//存储视口宽高比例
		}
		else 
		{
			//设置正射投影视体
			glOrtho (-MS*(GLfloat)WinViewX/(GLfloat)WinViewY, 
				MS*(GLfloat)WinViewX/(GLfloat)WinViewY, -MS, MS, -1, 1);
			
			m_ViewXYscale=(GLfloat)WinViewX/(GLfloat)WinViewY;//存储视口宽高比例
		}
	}
	else if(m_ViewType==GIS_VIEW_PERSPECTIVE) 	//如果投影模式为透视投影模式
	{
		//设置透视投影视体
		gluPerspective(50.0 + m_ViewWideNarrow , m_aspectRatio , m_near , m_far);	
	}

	glMatrixMode(GL_MODELVIEW); //将当前矩阵设置为模型矩阵		
	glLoadIdentity();   //将当前矩阵置换为单位阵       
	
}

//绘制正射投影栻上的三维地形
void CT3DSystemView::DrawTerrain_ORTHO()
{
	if(theApp.bLoginSucceed==FALSE || m_bLoadInitDemData==FALSE)
		return;
		

	glColor3f(1.0, 1.0, 1.0);
	glDisable(GL_TEXTURE_2D) ; // 关闭2D纹理映射功能
	glActiveTextureARB(GL_TEXTURE0_ARB) ; // 选择TEXTURE0为设置目标
	glEnable(GL_TEXTURE_2D) ; // 激活TEXTURE0单元
	
	glActiveTextureARB(GL_TEXTURE1_ARB) ; // 选择TEXTURE1为设置目标
	glEnable(GL_TEXTURE_2D) ; // 激活TEXTURE1单元 , 启用2D纹理映射
	glTexEnvi(GL_TEXTURE_ENV ,  GL_TEXTURE_ENV_MODE ,  GL_COMBINE_ARB);
	glTexEnvi(GL_TEXTURE_ENV ,  GL_RGB_SCALE_ARB ,  2);
	glMatrixMode(GL_TEXTURE); //定义矩阵为模型纹理矩阵
	glLoadIdentity() ; // 将当前矩阵置换为单位矩阵
	glDisable(GL_TEXTURE_2D) ; // 关闭纹理功能
	glActiveTextureARB(GL_TEXTURE0_ARB) ; // 选择TEXTURE0为设置目标
	SetDrawMode() ; // 
				
	glClearDepth(1.0f); 
	glEnable(GL_DEPTH_TEST); 
	glDepthFunc(GL_LESS); 
	
    glViewport(0, 0, WinViewX, WinViewY);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
	glOrtho (-m_OrthoViewSize*m_ViewXYscale, m_OrthoViewSize*m_ViewXYscale,-m_OrthoViewSize,m_OrthoViewSize,-1,1);
	glMatrixMode(GL_MODELVIEW); 
	glLoadIdentity();  
	
	glTranslatef(m_OrthotranslateX,m_OrthotranslateY,0);//移动

	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		
	int nCount=0;
		
	for(int j=0;j<m_LodDemblockNumber;j++)
	{
		memset(m_pbQuadMat[j],0,m_nMapSize*m_nMapSize);
	}

	//新建显示列表
	 glNewList(m_TerrainList,GL_COMPILE_AND_EXECUTE);

	 m_RenderDemblockNumber=0;
	 glColor3f(1,0,1);
	 for(int i=0;i<m_LodDemblockNumber;i++)
	 {
		   mCurrentTextID=i;
			m_CurrentDemArrayIndex=i;
			glBindTexture(GL_TEXTURE_2D, m_demTextureID[i]);//绑定纹理
			m_lodDemBlock[i][3]=0;
			m_RenderDemblockNumber++;
			m_lodDemBlock[i][3]=1;
			DrawBlockOrtho(i); //绘制地形块
	}

	glEndList();
	  
		
	CString strText;
	strText.Format("【内存/渲染块数】=%d/%d",m_LodDemblockNumber,m_RenderDemblockNumber);
		pMainFrame->Set_BarText(0,strText,0); 

	glActiveTextureARB(GL_TEXTURE1_ARB);
	glDisable(GL_TEXTURE_2D);
	
	glActiveTextureARB(GL_TEXTURE0_ARB);		
	glDisable(GL_TEXTURE_2D);
	glDisable(GL_DEPTH_TEST);
	glDisable(GL_CULL_FACE);
}

//绘制正射投影模式下地形块
void CT3DSystemView::DrawBlockOrtho(int DemBlockIndex)
{
	float mcolorR,mcolorG;
	
	//地形块中心x,y坐标
	double centerx=m_DemBlockCenterCoord[DemBlockIndex][0];
	double centery=fabs(m_DemBlockCenterCoord[DemBlockIndex][1]);
	
	
	double m_leftDownx,m_leftDowny,m_RightUpx,m_RightUpy;
	
	double Lx=(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);
	double Ly=(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y);
	
	m_leftDownx=centerx-theApp.m_Dem_BlockWidth/2-Lx/2;
	m_leftDowny=centery-theApp.m_Dem_BlockWidth/2-Ly/2;
	if((centerx+theApp.m_Dem_BlockWidth/2)>Lx)
		m_RightUpx=Lx-Lx/2;
	else
		m_RightUpx=centerx+theApp.m_Dem_BlockWidth/2-Lx/2;
	
	if((centery+theApp.m_Dem_BlockWidth/2)>Ly)
		m_RightUpy=Ly-Ly/2;
	else
		m_RightUpy=centery+theApp.m_Dem_BlockWidth/2-Ly/2;
	
	
	float m_scaleX=1.0/(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x);
	float m_scaleY=1.0/(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y);
	double x1,y1,x2,y2;
	x1=m_leftDownx*m_scaleX;
	y1=m_leftDowny*m_scaleX;
	x2=m_RightUpx*m_scaleX;
	y2=m_RightUpy*m_scaleX;
	
	glBegin(GL_QUADS);
	glNormal3f(0,0,1);
	glTexCoord2f(0.0f,0.0f);
	GetOrthoColor(x1,y1,&mcolorR,&mcolorG);
	glColor3f(mcolorR,mcolorG,0.5);
	glVertex2f(x1,y1);
	
	glTexCoord2f(1.0f,0.0f);
	GetOrthoColor(x2,y1,&mcolorR,&mcolorG);
	glColor3f(mcolorR,mcolorG,0.5);
	glVertex2f(x2,y1);
	
	glTexCoord2f(1.0f,1.0f);
	GetOrthoColor(x2,y2,&mcolorR,&mcolorG);
	glColor3f(mcolorR,mcolorG,0.5);
	glVertex2f(x2,y2);
	
	glTexCoord2f(0.0f,1.0f);
	GetOrthoColor(x1,y2,&mcolorR,&mcolorG);
	glColor3f(mcolorR,mcolorG,0.5);
	glVertex2f(x1,y2);
	glEnd();
	
}

void CT3DSystemView::GetOrthoColor(double x1, double y1, float *mColorR, float *mColorG)
{
	//return;
	double mx=(x1+m_ortho_CordinateOriginX)*(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x)+theApp.m_DemLeftDown_x;
	double my=(y1+m_ortho_CordinateOriginY)*(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)+theApp.m_DemLeftDown_y;
	double mz=m_demInsert.GetHeightValue(mx,my,2);
	//最高红色 255,0,0  最低绿色 0,255,0
	//Dr=255 DG=-255 DB=0
	*mColorR=(mz-m_minHeight)/(m_maxHeight-m_minHeight);
	*mColorG=1-(mz-m_minHeight)/(m_maxHeight-m_minHeight);
}

//放大影像
void CT3DSystemView::OnOrthoZoomIn() 
{
	m_OrthoZommPan=ORTHO_ZOOMIN;
	m_OrthoViewSize=m_OrthoViewSize/1.1;
	
	OnDraw(GetDC());
	
}
//缩小影像
void CT3DSystemView::OnOrthoZoomOut() 
{
	m_OrthoZommPan=ORTHO_ZOOMOUT;
	m_OrthoViewSize=m_OrthoViewSize*1.1;
	OnDraw(GetDC()); //刷新三维场景
	
}

//移动影像
void CT3DSystemView::OnOrthoPan() 
{
	m_OrthoZommPan=ORTHO_PAN;
	OnDraw(GetDC());//刷新三维场景
				
}

//恢复初始大小
void CT3DSystemView::OnOrthoZoomOrigin() 
{
	m_OrthoZommPan=ORTHO_ZOOMORIGIN;
	m_OrthoViewSize=0.3;
	OnDraw(GetDC());//刷新三维场景
	
}

//开窗缩放
void CT3DSystemView::OnOrthoZoomWindow() 
{
	m_OrthoZommPan=ORTHO_ZOOMWINDOW;
	OnDraw(GetDC());//刷新三维场景
	
}

void CT3DSystemView:: OnUpdateOnOrthoZoomIn(CCmdUI*  pCmdUI)   
{   
	pCmdUI->Enable(m_ViewType==GIS_VIEW_ORTHO);   
}

void CT3DSystemView:: OnUpdateOnOrthoZoomOut(CCmdUI*  pCmdUI)   
{   
	pCmdUI->Enable(m_ViewType==GIS_VIEW_ORTHO);   
}

void CT3DSystemView:: OnUpdateOnOrthoPan(CCmdUI*  pCmdUI)   
{   
	pCmdUI->Enable(m_ViewType==GIS_VIEW_ORTHO);   
}

void CT3DSystemView:: OnUpdateOnOrthoZoomOrigin(CCmdUI*  pCmdUI)   
{   
	pCmdUI->Enable(m_ViewType==GIS_VIEW_ORTHO);   
}

void CT3DSystemView:: OnUpdateOnOrthoZoomWindow(CCmdUI*  pCmdUI)   
{   
	pCmdUI->Enable(m_ViewType==GIS_VIEW_ORTHO);   
}

//响应鼠标左键抬起消息
void CT3DSystemView::OnLButtonUp(UINT nFlags, CPoint point) 
{
	m_bmouseView=false; //关闭鼠标旋转相机功能
	
	//如果是正射投影模式并且是移动控制模式下
	if(m_ViewType==GIS_VIEW_ORTHO && m_OrthoZommPan==ORTHO_PAN)	
	{
		int dx=point.x-m_preX;//在X方向移动距离 
		int dy=point.y-m_preY;//在Y方向移动距离
		m_OrthotranslateX+=dx*0.00007;//X距离变换到正射投影模式的移动值
		m_OrthotranslateY+=-dy*0.00007;//Y距离变换到正射投影模式的移动值
		OnDraw (GetDC());//刷新三维场景
	}
	
	CView::OnLButtonUp(nFlags, point);
}

//三维选线设计
void CT3DSystemView::OnMenuLinedesign() 
{
	m_OrthoZommPan=-1;
	
	if(m_QueryType==SELECTLINE) //如果当前已经三维选线状态，则关闭
		m_QueryType=-1;
	else // //如果当前不是三维选线状态，则打开
		m_QueryType=SELECTLINE;
}

//设置选中状态
void CT3DSystemView::OnUpdateMenuLinedesign(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_QueryType==SELECTLINE);
}


//绘制正射投影模式下的三维线路
void CT3DSystemView::DrawRailwaythesme_Ortho()
{
	CString tt,strSql,m_style;
// 	long i;

	//如果数据库没有加载成功或调入数据失败，返回
	if(theApp.bLoginSucceed==FALSE || m_bLoadInitDemData==FALSE)
		return;
	
	glLineWidth(4.0);//设置线宽
	SetDrawMode();//设置绘图模式

	glViewport(0, 0, WinViewX, WinViewY);//重新设置视口大小
	
	if(b_haveMadeRail3DwayList_Ortho==FALSE )   //如果还没有构建线路三维模型的显示列表
	{

		ReLoadCenterPt();//重新从数据库中读取线路中线坐标点数据
		
		glNewList(m_Rail3DwayList_Ortho,GL_COMPILE_AND_EXECUTE);//创建显示列表
				
			glColor3f(0.75,0.75,0.75);//设置颜色
			glLineWidth(1.0);//设置线宽
		
				//1. 绘制左侧路基边坡
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());//绑定路基边坡纹理
				for (long i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
				{
					m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
					if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点" &&m_style!="桥梁起点"  &&m_style!="桥梁中间点" &&m_style!="桥梁终点")
					{	
						DrawBP_Ortho(i,1);//绘制左侧路基边坡
					}
					
				}
		
				//4.绘制右侧路基边坡
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());//绑定路基边坡纹理
				
				for (i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
				{
					m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//交点类型
					//如果交点类型是非隧道和桥梁段的点，则绘制路基边坡		
					
					if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点" &&m_style!="桥梁起点"  &&m_style!="桥梁中间点" &&m_style!="桥梁终点")
					{
				
					    glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());//绑定路基边坡纹理
						DrawBP_Ortho(i,2);//绘制右侧路基边坡
						glBindTexture(GL_TEXTURE_2D, m_cTxtureSuigou.GetTxtID());//绑定水沟纹理
						DrawSuiGou_Ortho(i);//绘制水沟
						
					}
				}
		

		//5.绘制中心线		
		
		m_TempPts.RemoveAll();
		PCordinate pt;
		glLineWidth(8.0);
		glColor3f(1,0,1);


		for (i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			
			//如果未进行线路三维建模，则调用DrawCenterLine()函数绘制线路中线
			if(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetSize()<=0)
				DrawCenterLine(i,FALSE,m_currentSchemeIndexs);//绘制线路中线
			else
			{
			
				tt=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;

				if(tt!="隧道中间点" && i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x;
					pt->y=-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z; 
					pt->strJDStyle=tt;
					m_TempPts.Add(pt);
				}
				else //隧道部分的线路位于地面以下，不绘制
				{		
					if(m_TempPts.GetSize()>1)
					{	
				
						glBegin(GL_LINE_STRIP);

						for(int k=0;k<m_TempPts.GetSize();k++)
						{
							tt=m_TempPts.GetAt(k)->strJDStyle;
							if(tt=="交点坐标" || tt=="直缓点坐标" || tt=="缓直点坐标" || tt=="交点-直缓点坐标")
								glColor3f(1,0,0);
							else
								glColor3f(0,0,1);

							glVertex2f(GetOrthoX(m_TempPts.GetAt(k)->x),GetOrthoY(m_TempPts.GetAt(k)->y));
						}
						glEnd();
						m_TempPts.RemoveAll();
					}			
				}
			}
		}		


		//6.绘制轨道
		glBindTexture(GL_TEXTURE_2D, m_cTxtureRailway.GetTxtID());//绑定轨道纹理
		glColor3f(0.5,0,0.4);//设置颜色
		glLineWidth(2.0);//设置线宽
		
		CString tt1,tt2;
		for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			tt1=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//当前交点类型
			tt2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;//下一交点类型
			if(tt1!="隧道中间点"  &&  tt2!="隧道中间点")
			{
				//以矩形方式连接前后相临轨道断面
				glBegin(GL_POLYGON);
				glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前轨道断面左侧点)
				glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1),\
					GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1));
				
				glTexCoord2f(1.0f,0.0f); //设置纹理坐标(当前轨道断面右侧点)
				glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2),\
					GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2));
				
				glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一前轨道断面右侧点)
				glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2),\
					GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2));
				
				glTexCoord2f(0.0f,1.0f);//设置纹理坐标(下一前轨道断面左侧点)
				glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1),\
					GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1));
				
				glEnd();
			}
		
		}		


		//7.绘制道床边坡
		glBindTexture(GL_TEXTURE_2D, m_cTxtureGdToLJ.GetTxtID());//绑定道床边坡纹理		
		glColor3f(1,1,0);//设置颜
		for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			tt1=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//当前交点类型
			tt2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;//下一交点类型
			if(tt1!="隧道中间点"  &&  tt2!="隧道中间点")
			{
				
			//以矩形方式连接方式绘制左侧道床边坡
			glBegin(GL_POLYGON);
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(当前左侧道床边坡左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1),\
				GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1));
			
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(当前左侧道床边坡右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1),\
				GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1));
			
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(下一左侧道床边坡右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1));
			
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(下一左侧道床边坡左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1));			
			glEnd();
			
			//以矩形方式连接方式绘制右侧道床边坡
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前右侧道床边坡左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2),\
				GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2));
			
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(当前右侧道床边坡右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2),\
				GetOrthoY(-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2));
			
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一右侧道床边坡右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2));
			
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(下一右侧道床边坡左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2));
			
			glEnd();
			}
		}		
		


		//8.绘制路肩
		glBindTexture(GL_TEXTURE_2D, m_cTxtureLJ.GetTxtID());//绑定路肩纹理
		glColor3f(1,0.5,0.25);//设置颜色
		for (i=0;i<myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			tt1=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//当前交点类型
			tt2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;//下一交点类型
			if(tt1!="隧道中间点"  &&  tt2!="隧道中间点")
			{
				
			//以矩形方式连接方式绘制左侧路肩
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前左侧路肩断面左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1));
			
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(当前左侧路肩断面右侧点)	
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1));
			
			glTexCoord2f(1.0f,1.0f);;//设置纹理坐标(下一左侧路肩断面右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1));
			
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(下一左侧路肩断面左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1));
						
			glEnd();
			
			//以矩形方式连接方式绘制右侧路肩
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前右侧路肩断面左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2));
			
			
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(当前右侧路肩断面右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2));
			
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一右侧路肩断面右侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2));
			
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(下一右侧路肩断面左侧点)
			glVertex2f(GetOrthoX(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2),\
				GetOrthoY(-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2));
			
			glEnd();
			}
		}		

		glLineWidth(1.0);//恢复线宽
		glEndList();//结束显示列表
		b_haveMadeRail3DwayList_Ortho=TRUE;//标识已经创建显示列表
	}
	else  //如果已经构建线路三维模型的显示列表,则直接调用显示列表
	{ 
		glCallList(m_Rail3DwayList_Ortho); //调用线路三维模型显示列表
	}
	

}

//绘制中心线
//位于地面以下的部分不绘制
void CT3DSystemView::DrawCenterLine(long index, BOOL ifSelectLine, int SchemeIndex)
{
	CString tt;
	double x1,y1,z1,x2,y2,z2;
	double x0,y0,z0;
	float DerDistence=2.0;

	float Dh;
	
	m_TempPts.RemoveAll();
	
	if(ifSelectLine==TRUE) //在选线设计
	{
		Dh=5;
		x1=myDesingScheme.PtS_JD.GetAt(index)->x;
		y1=myDesingScheme.PtS_JD.GetAt(index)->y;
		z1=myDesingScheme.PtS_JD.GetAt(index)->z;
		
		x2=myDesingScheme.PtS_JD.GetAt(index+1)->x;
		y2=myDesingScheme.PtS_JD.GetAt(index+1)->y;
		z2=myDesingScheme.PtS_JD.GetAt(index+1)->z;
	}
	else  //打开项目时,绘制打开的方案
	{
		Dh=5;
		x1=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index)->x;
		y1=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index)->y;
		z1=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index)->z;
		
		x2=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index+1)->x;
		y2=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index+1)->y;
		z2=myDesingScheme.PtS_3DLineZX[SchemeIndex].GetAt(index+1)->z;
		
	}
	glColor3f(0,0,1);

	PCordinate pt;
	
	double L=myDesingScheme.GetDistenceXYZ(x1,y1,z1,x2,y2,z2);
	double L0=0;
	if(m_ViewType==GIS_VIEW_ORTHO) //如果是正射投影模式
	{		
		while(L0<=L)
		{
			
			x0=x1+L0/L*(x2-x1);
			y0=y1+L0/L*(y2-y1);//高计线高程
			z0=z1+L0/L*(z2-z1);

			//地面高程
			float dmh=m_demInsert.GetHeightValue(x0+theApp.m_DemLeftDown_x,-z0+theApp.m_DemLeftDown_y,2);
			if(y0>=dmh-Dh) //设计线点高程大于地面高程
			{
				pt=new Cordinate;
				pt->x =x0;
				pt->y=y0;
				pt->z =z0;
				m_TempPts.Add(pt);
			}
			else
			{
				if(m_TempPts.GetSize()>1) //临时点数大于1
				{	
					
					glBegin(GL_LINE_STRIP); //以折线形式绘制
					
					for(int k=0;k<m_TempPts.GetSize();k++)
					{
						glVertex2f(GetOrthoX(m_TempPts.GetAt(k)->x),GetOrthoY(-m_TempPts.GetAt(k)->z));
					}
					glEnd();
					m_TempPts.RemoveAll(); //清空
					L0-=DerDistence;
				}
			}

			L0+=DerDistence;
			if(L0>=L)
			{
				x0=x2;
				y0=y2;
				z0=z2;
				dmh=m_demInsert.GetHeightValue(x0+theApp.m_DemLeftDown_x,-z0+theApp.m_DemLeftDown_y,2);
				if(y0>=dmh-Dh ) //设计线点高程大于地面高程
				{
					pt=new Cordinate;
					pt->x =x0;
					pt->y=y0;
					pt->z =z0;
					m_TempPts.Add(pt);
					if(m_TempPts.GetSize()>1)
					{	
						
						glBegin(GL_LINE_STRIP); //以折线形式绘制
						
						for(int k=0;k<m_TempPts.GetSize();k++)
						{
							glVertex2f(GetOrthoX(m_TempPts.GetAt(k)->x),GetOrthoY(-m_TempPts.GetAt(k)->z));
						}
						glEnd();
						m_TempPts.RemoveAll();
						
					}
				}
				break;
			}
		
		}
		if(m_TempPts.GetSize()>1)//临时点数大于1
		{	
			
			glBegin(GL_LINE_STRIP);  //以折线形式绘制
			
			for(int k=0;k<m_TempPts.GetSize();k++)
			{
				glVertex2f(GetOrthoX(m_TempPts.GetAt(k)->x),GetOrthoY(-m_TempPts.GetAt(k)->z));
			}
			glEnd();
			m_TempPts.RemoveAll();
		}
	}
	else if (m_ViewType==GIS_VIEW_PERSPECTIVE) //如果是透视投影模式
	{
		while(L0<=L)
		{
			
			x0=x1+L0/L*(x2-x1);
			y0=y1+L0/L*(y2-y1);//计线高程
			z0=z1+L0/L*(z2-z1);
			
			//地面高程
			float dmh=m_demInsert.GetHeightValue(x0+theApp.m_DemLeftDown_x,-z0+theApp.m_DemLeftDown_y,2);
			if(y0>=dmh-Dh)  //设计线点高程大于地面高程
			{
				pt=new Cordinate;
				pt->x =x0;
				pt->y=y0;
				pt->z =z0;
				m_TempPts.Add(pt);
			}
			else
			{
				if(m_TempPts.GetSize()>1)
				{	
					
					glBegin(GL_LINE_STRIP);
					
					for(int k=0;k<m_TempPts.GetSize();k++)
					{
						glVertex3f(m_TempPts.GetAt(k)->x,m_TempPts.GetAt(k)->y,
							m_TempPts.GetAt(k)->z);
						
					}
					glEnd();
					m_TempPts.RemoveAll();
					L0-=DerDistence;
				}
			}
			
			L0+=DerDistence;
			if(L0>=L)
			{
				x0=x2;
				y0=y2;
				z0=z2;
				dmh=m_demInsert.GetHeightValue(x0+theApp.m_DemLeftDown_x,-z0+theApp.m_DemLeftDown_y,2);
				if(y0>=dmh-Dh )//设计线点高程大于地面高程
				{
					pt=new Cordinate;
					pt->x =x0;
					pt->y=y0;
					pt->z =z0;
					m_TempPts.Add(pt);
					if(m_TempPts.GetSize()>1)
					{	
						
						glBegin(GL_LINE_STRIP);
						
						for(int k=0;k<m_TempPts.GetSize();k++)
						{
							glVertex3f(m_TempPts.GetAt(k)->x,m_TempPts.GetAt(k)->y,
								m_TempPts.GetAt(k)->z);
						}
						glEnd();
						m_TempPts.RemoveAll();
						
					}
				}
				break;
			}
		}

		if(m_TempPts.GetSize()>1)
		{	
			
			glBegin(GL_LINE_STRIP);
			
			for(int k=0;k<m_TempPts.GetSize();k++)
			{
				glVertex3f(m_TempPts.GetAt(k)->x,m_TempPts.GetAt(k)->y,
					m_TempPts.GetAt(k)->z);
				
			}
			glEnd();
			m_TempPts.RemoveAll();
		}

	}
	
	
}

//计算正射投影模式下x坐标
float CT3DSystemView::GetOrthoX(double x)
{
	float xt=x/(theApp.m_DemRightUp_x-theApp.m_DemLeftDown_x)-m_ortho_CordinateOriginX;
	return xt;
	
}

//计算正射投影模式下y坐标
float CT3DSystemView::GetOrthoY(double y)
{
	float yt=y/(theApp.m_DemRightUp_y-theApp.m_DemLeftDown_y)*m_ortho_CordinateXYScale-m_ortho_CordinateOriginY;
	return yt;
	
}

//保存设计方案
void CT3DSystemView::OnMenuLinesavescheme() 
{
	int manswer=MessageBox("是否确认保存方案?","保存方案",MB_ICONQUESTION|MB_YESNO);
	if(manswer==7)
		return;
	
	b_haveMadeRail3DwayList=FALSE;
	b_haveMadeRail3DwayList_Ortho=FALSE;
	if(myDesingScheme.JDCurveElementss[m_currentSchemeIndexs].GetSize()>0)
	{
		myDesingScheme.m_bHaveSaveScheme=TRUE;
		myDesingScheme.SavePlaneCurveData();//保存曲线要素到数据库中	
	}	
	
}


//实现透视投影模式下的线路三维模型绘制
void CT3DSystemView::DrawRailwaythesme()
{

	//压入属性堆栈
	glPushAttrib(GL_DEPTH_BUFFER_BIT|GL_ENABLE_BIT|GL_POLYGON_BIT );
		glEnable(GL_DEPTH_TEST|GL_CULL_FACE);
		glCullFace(GL_BACK);

	//如果数据库没有加载成功或调入数据失败，返回
	if(theApp.bLoginSucceed==FALSE || m_bLoadInitDemData==FALSE)
		return;
	
	CString tt,strSql;
	CString m_style,m_stylePre,m_styleNext;
	PCordinate pt;
	

	SetDrawMode();//设置绘图模式
	glViewport(0, 0, WinViewX, WinViewY); //重新设置视口大小
	
	
	if(b_haveMadeRail3DwayList==FALSE )  //如果还没有构建线路三维模型的显示列表
	{

		
		ReLoadCenterPt();//重新从数据库中读取线路中线坐标点数据
			


		glNewList(m_Rail3DwayList,GL_COMPILE_AND_EXECUTE); //创建显示列表
		
		glColor3f(0.75,0.75,0.75);//设置颜色
		glLineWidth(2.0);//设置线宽
		

		//1. 绘制桥墩
		myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].RemoveAll();
		myDesingScheme.GetBirdgeQDcordinate(m_Bridge.m_Bridge_QDSpace);//根据桥墩间距计算设置桥墩坐标
		for (long i=0;i<myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetSize();i++)
		{
		 	DrawSceneQD(myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->y1,\
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->z1,
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->y2,\
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->z2,
				myDesingScheme.PtS_BridgeQD3D[m_currentSchemeIndexs].GetAt(i)->QDHeight);
		}		

		
		//2. 计算桥梁栏杆三维坐标
		myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].RemoveAll();
		myDesingScheme.GetBirdgeLGcordinate(m_Bridge.m_Bridge_HLSpace);//根据桥栏杆间距计算设置桥栏杆坐标
		
		//3. 绘制左侧路基边坡
		glColor3f(1,0,0);		
		int mBridgeIndex=0;//桥梁初始索引号
		glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID()); //绑定路基边坡纹理
		for (i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle; //交点类型
			m_styleNext=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;	
		
			//如果交点类型是非隧道和桥梁段的点，则绘制路基边坡
			if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点" \
				&&m_style!="桥梁中间点" &&m_style!="桥梁终点" && m_styleNext!="隧道起点" )
			{	
				DrawBP(i,1); //绘制左侧路基边坡
				
			}
			if(m_style=="桥梁起点" || m_style=="桥梁终点")	
			{
				
				glColor3f(0.5,0,1);
				if(i>0)
				{
					//下一点处的交点类型
					m_styleNext=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;
						//桥梁起终点处护坡
						//(规则如下:如果是桥梁直接相连,则不需要绘制护坡)
						int Innum;
						//绘制桥梁下方护坡面
						if(m_style=="桥梁起点")
							Innum=i;//i-1;
						else if(m_style=="桥梁终点")
							Innum=i+1;
						
						//绘制桥梁下方护坡面
						DrawQDHP(Innum,m_style,m_Bridge.m_Bridge_HPangle/180.0*PAI,m_styleNext,mBridgeIndex);
				
				}
				if(m_style=="桥梁终点")
					mBridgeIndex++;
			}
		}


		//4.绘制右侧路基边坡
		glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());//绑定路基边坡纹理
		for (i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//交点类型
			m_styleNext=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;//下一点的交点类型
			

			//如果交点类型是非隧道和桥梁段的点，则绘制路基边坡		
			if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点" \
				&&m_style!="桥梁中间点" &&m_style!="桥梁终点" && m_styleNext!="隧道起点")
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				DrawBP(i,2);//绘制右侧路基边坡
				
			}
			if(m_style!="隧道起点" && m_style!="隧道中间点"  \
				&& m_style!="桥梁起点" && m_style!="桥梁中间点" && m_style!="隧道中间点" )
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureSuigou.GetTxtID());//绑定水沟纹理
				DrawSuiGou(i);//绘制水沟
				
			}
		}
	


		//5.绘制线路中线
		glLineWidth(3.0);
		m_TempPts.RemoveAll();
		for ( i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;i++)
		{

			//如果未进行线路三维建模，则调用DrawCenterLine()函数绘制线路中线
			if(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetSize()<=0)
				DrawCenterLine(i,FALSE,m_currentSchemeIndexs); //绘制线路中线
			else
			{				
				tt=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;//交点类型
				
				if(tt!="隧道中间点" && i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x;
					pt->y=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y; 
					pt->z=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z; 
					pt->strJDStyle=tt;
					m_TempPts.Add(pt);
				}
				else //隧道部分的线路位于地面以下，不绘制
				{		
					if(m_TempPts.GetSize()>1)
					{	
						
						glBegin(GL_LINE_STRIP);
						
						for(int k=0;k<m_TempPts.GetSize();k++)
						{
							tt=m_TempPts.GetAt(k)->strJDStyle;
							if(tt=="交点坐标" || tt=="直缓点坐标" || tt=="缓直点坐标" || tt=="交点-直缓点坐标")
								glColor3f(1,0,0);
							else
								glColor3f(0,0,1);
							glVertex3f(m_TempPts.GetAt(k)->x,m_TempPts.GetAt(k)->y,m_TempPts.GetAt(k)->z);							
						}
						glEnd();
						m_TempPts.RemoveAll();
					}
				}
			}
		}	


	
		///////////////////////////////
       //6.绘制轨道
		glBindTexture(GL_TEXTURE_2D, m_cTxtureRailway.GetTxtID()); //绑定轨道纹理
	 	glLineWidth(2.0);//设置线宽
		for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			
			if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->Derh==0) //如果挖为0
				glColor3f(0,1,1); //设置颜色
			else
				glColor3f(1,0,1); //设置颜色
			float L=myDesingScheme.GetDistenceXY(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
		
			//以矩形方式连接前后相临轨道断面	
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f); //设置纹理坐标(当前轨道断面左侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1);
			glTexCoord2f(1.0f,0.0f); //设置纹理坐标(当前轨道断面右侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(1.0f,L/10);//设置纹理坐标(下一前轨道断面右侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glTexCoord2f(0.0f,L/10); //设置纹理坐标(下一前轨道断面左侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			
			glEnd();

		}		
	
		//7.绘制道床边坡
		glBindTexture(GL_TEXTURE_2D, m_cTxtureGdToLJ.GetTxtID());//绑定道床边坡纹理
		glColor3f(1,1,0);//设置颜色
		for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			//以矩形方式连接方式绘制左侧道床边坡	
			glBegin(GL_POLYGON);
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(当前左侧道床边坡左侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1);
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(当前左侧道床边坡右侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(下一左侧道床边坡右侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(下一左侧道床边坡左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1);
			
			glEnd();
			
			//以矩形方式连接方式绘制右侧道床边坡
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前右侧道床边坡左侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(当前右侧道床边坡右侧点)
			glVertex3f(myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2,
				myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一右侧道床边坡右侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(下一右侧道床边坡左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glEnd();
		}		
		
		//8.绘制路肩
		glBindTexture(GL_TEXTURE_2D, m_cTxtureLJ.GetTxtID());//绑定路肩纹理
		glColor3f(1,0.5,0.25);//设置颜色
		for (i=0;i<myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			//以矩形方式连接方式绘制左侧路肩	
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前左侧路肩断面左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y1,
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1);
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(当前左侧路肩断面右侧点)	
			glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y1,
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一左侧路肩断面右侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(下一左侧路肩断面左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1);
			
			glEnd();			

			//以矩形方式连接方式绘制右侧路肩	
			glBegin(GL_POLYGON);
			glTexCoord2f(0.0f,0.0f);//设置纹理坐标(当前右侧路肩断面左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(1.0f,0.0f);//设置纹理坐标(当前右侧路肩断面右侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y2,
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(1.0f,1.0f);//设置纹理坐标(下一右侧路肩断面右侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y2,
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glTexCoord2f(0.0f,1.0f);//设置纹理坐标(下一右侧路肩断面左侧点)
			glVertex3f(myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2,
				myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			
			glEnd();
		}		

		//9.绘制桥梁栏杆
		DrawBridgeHL();

		//10.绘制隧道	
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].RemoveAll();//隧道坐标数据数组清空
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].RemoveAll();//隧道信息数据数组清空
		//调用GetTunnelcordinate()函数重新计算隧道坐标
		myDesingScheme.GetTunnelcordinate(m_Tunnel.height,\
			m_Tunnel.Archeight,\
			m_Tunnel.WallHeight,\
			m_Tunnel.width/2.0,\
			m_Tunnel.ArcSegmentNumber);
		DrawTunnel(); //绘制隧道	
	
		glLineWidth(1.0);//恢复线宽
		glEndList();//结束显示列表
	
		b_haveMadeRail3DwayList=TRUE; //标识已经创建显示列表
	}
	else //如果已经构建线路三维模型的显示列表,则直接调用显示列表
	{
		glCallList(m_Rail3DwayList);  //调用线路三维模型显示列表
	}
	glPopAttrib();//弹出属性堆栈
}

void CT3DSystemView::DrawBP(long index, int BPside)
{


	float mNCDistence=4.0;
	long i=index;

	int j;


	if(BPside==1) //左边坡
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_L;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_L;

		if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->TW_left==0 ||\
			myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->TW_right==0)
				glColor3f(0,1,1);
		else
				glColor3f(1,0,0);
		
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				
				//如果路基类型相同(同为路堑或路堤)
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f); 
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z);
					glTexCoord2f(0.0f,0.0f); 
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f); 
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,1.0f); 
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z);
					glEnd();
				}
				else  //如果路基类型相同(路堑,路堤相连)
				{
					
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z);
					glEnd();
					


	
					glBegin(GL_POLYGON);
						glTexCoord2f(0.0f,0.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z);
						glTexCoord2f(0.5f,0.5f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].z);
						glTexCoord2f(0.5f,1.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z);
						glTexCoord2f(1.0f,0.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
						glEnd();
	
					

					
				}
				if(j>0) 
					{
						glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
						glBegin(GL_POLYGON);
						glTexCoord2f(0.0f,0.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
						glTexCoord2f(1.0f,0.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z);
						glTexCoord2f(1.0f,1.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z);
						glTexCoord2f(0.0f,1.0f);
						glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
						glEnd();
	
					
					}
	
			}
		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z);
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z);
					glEnd();
					
				
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z);
					glEnd();
					
				}
			

				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
					
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z);
					glEnd();
				}

			}
			
		}
		
	}
	else if(BPside==2) 
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_R;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_R;
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z);
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z);
					glEnd();
					

			
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glEnd();
			

					
				}
				
				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());

					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glEnd();
				}
			}
		


		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z);
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z);
					glEnd();
					
						
		
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].z);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z);
					glEnd();
	
				}
				
				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z);
					glTexCoord2f(1.0f,0.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z);
					glTexCoord2f(1.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z);
					glTexCoord2f(0.0f,1.0f);
					glVertex3f(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z);
					glEnd();
				}
			}

		}
		
	}
	
	glLineWidth(1.0);
	glColor3f(1,1,1);

	
	

}

void CT3DSystemView::DrawBP_Ortho(long index, int BPside)
{

	float mNCDistence=4.0;
	long i=index;

	int j;


	glLineWidth(2.0);
	glColor3f(1,0,0);

	if(BPside==1) 
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_L;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_L;
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				
				
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f); 
			
			
			
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z));
					
					glTexCoord2f(0.0f,0.0f); 
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f); 
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f); 
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z));
					
					glEnd();
				}
				else 
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z));
					
					glEnd();
					


					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z));
					
					glTexCoord2f(0.5f,0.5f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].z));
					
					glTexCoord2f(0.5f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glEnd();


					
				}
			
	
				if(j>0) 
					{
						glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
					
						
						glBegin(GL_POLYGON);
						glTexCoord2f(0.0f,0.0f);
 
 
 
						glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
							GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
						
						glTexCoord2f(1.0f,0.0f);
 
 
 
						glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x),\
							GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z));
						
						glTexCoord2f(1.0f,1.0f);
 
 
 
						glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x),\
							GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z));
						
						glTexCoord2f(0.0f,1.0f);
 
 
 
						glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
							GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
						
						glEnd();
	
					
					}
	
			}
		
			
		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z));
					
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z));
					
					glEnd();
					
				
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z));
					
					glEnd();
					
				}
			

				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
					
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z));
					
					glEnd();
				}

			}
			
		}
		
	}
	else if(BPside==2) 
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_R;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_R;
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z));
					
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z));
					
					glEnd();
					

			
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].z));
					
					glTexCoord2f(1.0f,1.0f);
	
	
	
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
						
					glEnd();
			

					
				}
				
				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());

					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glEnd();
				}
			}
		


		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z));
					
					glEnd();
				}
				else
				{
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z));
					
					glEnd();
					
						
		
					glBegin(GL_POLYGON);
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].z));
					
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z));
					
					glEnd();
	
				}
				
				if(j>0) 
				{
					glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
					glBegin(GL_POLYGON);
					glTexCoord2f(0.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z));
					
					glTexCoord2f(1.0f,0.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z));
					
					glTexCoord2f(1.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z));
					
					glTexCoord2f(0.0f,1.0f);
 
 
 
					glVertex2f(GetOrthoX(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x),\
						GetOrthoY(-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z));
					
					glEnd();
				}
			}

		}
		
	}
	
	glLineWidth(1.0);
	glColor3f(1,1,1);

}

void CT3DSystemView::DrawSuiGou(long index)
{
	glColor3f(1.0,0.4,1);
	glLineWidth(2.0);

 /*
 //4.求左右侧水沟交点坐标
	/*
	排水沟由4个点确定,分别为1-4号点,其中0号点为LjToBp点,已存储
		5____4  1____0
   (b4) \     /(b3)
		3\___/2

	其中:
	侧沟深度 SuiGou_h,
	侧沟顶宽度 SuiGou_b1,
	侧沟底宽度 SuiGou_b2,
	侧沟外边坡的宽度  SuiGou_b3
	侧沟内边坡[靠近路基一侧]斜率 SuiGou_m1,
	侧沟外边坡[远离路基一侧]斜率 SuiGou_m2,
	float SuiGou_h,SuiGou_b1,SuiGou_b2,SuiGou_b3,SuiGou_b4,SuiGou_m1,SuiGou_m2;
	SuiGou_b2=0.4;//侧沟底宽度[规范要求]
	SuiGou_h=0.6;//侧沟深度[规范要求]
	SuiGou_b3=0.3;
	SuiGou_b4=0.6;//侧沟外边坡的宽度[规范要求:路堑:0.8  路堤:0.6 ,这里为了简便,统一取为0.6]
	SuiGou_m1=SuiGou_m2=1;//内/外边坡斜率[规范要求]
    SuiGou_b1=SuiGou_h/SuiGou_m1+SuiGou_h/SuiGou_m2+SuiGou_b2;//侧沟顶宽度
	所以水沟总宽度=0.6+2*0.6+0.4+0.3=2.5
 */
 	float SGLL=2.5;//2.8;
//	float b=
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_L==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_L==TRUE)
	{
		glBegin(GL_QUAD_STRIP);
		for(int i=5;i>=0;i--)
		{

			switch(i)
			{
			case 5:
				glTexCoord2f(0.0f,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(0.0f,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;	
			case 4:
				glTexCoord2f(0.6f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(0.6f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;	
			case 3:
				glTexCoord2f(1.2f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(1.2f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;		
			case 2:
				glTexCoord2f(1.6f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(1.6f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;		
			case 1:
				glTexCoord2f(2.2f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(2.2f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;		
			case 0:
				glTexCoord2f(1.0f,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z);
				glTexCoord2f(1.0f,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z);
				break;		
			}

		}
		glEnd();
	}
	else if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_L==FALSE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_L==TRUE)
	{
		//当前点没有水沟,而下一点有水沟
		
		glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f(1.0f,0.0f); 
			glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->x1,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->y1,\
				myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->z1);
			glTexCoord2f(0.0f,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[5].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[5].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[5].z);
			glTexCoord2f(0.6f/SGLL,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[4].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[4].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[4].z);
			
			glTexCoord2f(1.2f/SGLL,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[3].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[3].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[3].z);
			glTexCoord2f(1.6f/SGLL,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[2].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[2].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[2].z);
			glTexCoord2f(2.2f/SGLL,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[1].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[1].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[1].z);
			glTexCoord2f(1.0f,1.0f); 
			glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[0].x,
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[0].y,	
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[0].z);
			
		glEnd();
	}
	else if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_L==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_L==FALSE)
	{
		//当前点有水沟,而下一点没有水沟
		glBegin(GL_TRIANGLE_FAN);
		glTexCoord2f(1.0f,1.0f); 
		glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->x1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->y1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->z1);
		glTexCoord2f(0.0f,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[5].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[5].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[5].z);
		glTexCoord2f(0.6f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[4].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[4].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[4].z);
		
		glTexCoord2f(1.2f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[3].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[3].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[3].z);
		glTexCoord2f(1.6f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[2].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[2].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[2].z);
		glTexCoord2f(2.2f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[1].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[1].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[1].z);
		glTexCoord2f(1.0f,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[0].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[0].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[0].z);
		
		glEnd();
		
		
	}
	
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_R==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_R==TRUE)
	{
		glBegin(GL_QUAD_STRIP);
		for(int i=5;i>=0;i--)
		{

			switch(i)
			{
			case 5:
				glTexCoord2f(1.0f,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(1.0f,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;	
			case 4:
				glTexCoord2f(1.9f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(1.9f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;	
			case 3:
				glTexCoord2f(1.3f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(1.3f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;		
			case 2:
				glTexCoord2f(0.9f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(0.9f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;		
			case 1:
				glTexCoord2f(0.3f/SGLL,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(0.3f/SGLL,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;		
			case 0:
				glTexCoord2f(0.0f,0.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z);
				glTexCoord2f(0.0f,1.0f); 
				glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,	
					myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z);
				break;		
			}

		}
		glEnd();
	}
	else if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_R==FALSE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_R==TRUE)
	{
		//当前点没有水沟,而下一点有水沟
		glBegin(GL_TRIANGLE_FAN);
		glTexCoord2f(0.0f,0.0f); 
		glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->x2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->y2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index)->z2);
		glTexCoord2f(1.0f,1.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[5].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[5].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[5].z);
		glTexCoord2f(1.9f/SGLL,1.0f);  
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[4].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[4].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[4].z);
		glTexCoord2f(1.3f/SGLL,1.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[3].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[3].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[3].z);
		glTexCoord2f(0.9f/SGLL,1.0f);  
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[2].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[2].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[2].z);
		glTexCoord2f(0.3f/SGLL,1.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[1].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[1].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[1].z);
		glTexCoord2f(0.0f,1.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[0].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[0].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[0].z);
		
		glEnd();
	}
	else if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_R==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_R==FALSE)
	{
		//当前点有水沟,而下一点没有水沟
		
		glBegin(GL_TRIANGLE_FAN);
		glTexCoord2f(1.0f,1.0f); 
		glVertex3f(myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->x2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->y2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(index+1)->z2);
		glTexCoord2f(1.0f,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[5].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[5].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[5].z);
		glTexCoord2f(1.9f/SGLL,0.0f);  
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[4].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[4].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[4].z);
		glTexCoord2f(1.3f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[3].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[3].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[3].z);
		glTexCoord2f(0.9f/SGLL,0.0f);  
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[2].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[2].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[2].z);
		glTexCoord2f(0.3f/SGLL,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[1].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[1].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[1].z);
		glTexCoord2f(0.0f,0.0f); 
		glVertex3f(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[0].x,
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[0].y,	
			myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[0].z);
		
		glEnd();
	}
	

	glLineWidth(1.0);
	glColor3f(1,1,1);
	
}

void CT3DSystemView::DrawSuiGou_Ortho(long index)
{
	glColor3f(1.0,0.4,1);
	glLineWidth(2.0);

	
	float SGLL=2.8;
	
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_L==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_L==TRUE)
	{
		glBegin(GL_QUAD_STRIP);
		for(int i=5;i>=0;i--)
		{
			switch(i)
			{
			case 5:
				glTexCoord2f(0.0f,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));

				glTexCoord2f(0.0f,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;	
			case 4:
				glTexCoord2f(0.5f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));
				
				glTexCoord2f(0.5f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;	
			case 3:
				glTexCoord2f(1.3f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));
				
				glTexCoord2f(1.3f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;		
			case 2:
				glTexCoord2f(1.7f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));
				
				glTexCoord2f(1.7f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;		
			case 1:
				glTexCoord2f(2.5f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));
				
				glTexCoord2f(2.5f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;		
			case 0:
				glTexCoord2f(1.0f,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z));
				
				glTexCoord2f(1.0f,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z));
				
				break;		
			}

		}
		glEnd();
	}
	
	
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_R==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_R==TRUE)
	{

		glBegin(GL_QUAD_STRIP);
		for(int i=5;i>=0;i--)
		{

			switch(i)
			{
			case 5:
				glTexCoord2f(0.0f,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(0.0f,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;	
			case 4:
				glTexCoord2f(0.5f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(0.5f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;	
			case 3:
				glTexCoord2f(1.3f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(1.3f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;		
			case 2:
				glTexCoord2f(1.7f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(1.7f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;		
			case 1:
				glTexCoord2f(2.5f/SGLL,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(2.5f/SGLL,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;		
			case 0:
				glTexCoord2f(1.0f,0.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z));
				
				glTexCoord2f(1.0f,1.0f); 
 
 
 
				glVertex2f(GetOrthoX(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x),\
					GetOrthoY(-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z));
				
				break;		
			}

		}
		glEnd();
	}

	glLineWidth(1.0);
	glColor3f(1,1,1);
}

//绘制桥梁下方护坡面
void CT3DSystemView::DrawQDHP(long Index, CString mCurrentPtstyle, float mHPangle, CString mNextPtstyle, int mBridgeIndex)
{
	//	如果是桥隧直接相连,则不需要绘制护坡)
	if(mNextPtstyle=="隧道起点")
		return;
	//	参与绘制护坡面的点

	//	左侧:护坡点HuPo_L+PtS_RailwayLj3D左侧点(x1,y1,z1)
	//	右侧:PtS_RailwayLj3D左侧点(x2,y2,z2)+护坡点HuPo_R
			
	//根据是桥梁起点(mCurrentPtstyle="桥梁起点")还是
	//桥梁终点(mCurrentPtstyle="桥梁终点")来分别进行绘制,二
	//者护坡面的方向是相反的
	double x1,y1,z1,x2,y2,z2;
	double tx,ty,tz;
	
	PCordinate pt;
	
	int i;
	double ax,ay,az;
	int N1,N2;

	
	N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->Huponums_L-1;//左侧边坡数
	N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->Huponums_R-1;//右侧边坡数

	if(N1<0 || N2<0)
		return;

	x1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[N1].Hp[2].x;
	y1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[N1].Hp[2].y;
	z1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[N1].Hp[2].z;
	x2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[N2].Hp[2].x;
	y2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[N2].Hp[2].y,
	z2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[N2].Hp[2].z;
	
		
	float dx=x2-x1;
	float dz=z2-z1;
	float mangle;
	
	if(mCurrentPtstyle=="桥梁起点")
	{
		if(fabs(dx)<=0.000001) 
		{
			mangle=0;
		}  
		else 
		{
			mangle=atan(fabs(dz/dx));
			if(dx>=0 && dz<=0)  //1象限
			{
				mangle=PAI/2+mangle;
			}
			else if(dx<=0 && dz<=0) //2象限 
			{
				mangle=PAI*3.0/2-mangle;
			}
			else if(dx<=0 && dz>=0) //3象限
			{
				mangle=2*PAI-mangle;
			}
			else if(dx>=0 && dz>=0) //4象限
			{
				mangle=PAI/2-mangle;
			}
			
		}
	}
	else if(mCurrentPtstyle=="桥梁终点")
	{
		if(fabs(dx)<=0.000001) 
		{
			mangle=PAI*3.0/2;
		}  
		else 
		{
			mangle=atan(fabs(dz/dx));
			if(dx>=0 && dz<=0)  //1象限
			{
				mangle=2*PAI-mangle;
			}
			else if(dx<=0 && dz<=0) //2象限 
			{
				mangle=PAI/2-mangle;
			}
			else if(dx<=0 && dz>=0) //3象限
			{
				mangle=PAI/2+mangle;
			}
			else if(dx>=0 && dz>=0) //4象限
			{
				mangle=PAI*3.0/2-mangle;
			}
			
		}
	}
	
		//	(1)//桥梁起点护坡面
		PtBridgeHPUp.RemoveAll();
		PtBridgeHPDown.RemoveAll();
	
		//依次求得按给定护坡面角度与地面的交点
		//(1)左侧护坡点
		for(i=N1;i>=0;i--)
		{
			for(int j=2;j>=0;j--)
			{
				tx=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[i].Hp[j].x;
				ty=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[i].Hp[j].y;
				tz=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_L[i].Hp[j].z;
				myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
				pt=new Cordinate;
				pt->x=ax;pt->y=ay;pt->z=az;
				PtBridgeHPDown.Add(pt);
				pt=new Cordinate;
				pt->x=tx;pt->y=ty;pt->z=tz;
				PtBridgeHPUp.Add(pt); //加入护坡面
			}
		}
	
		//(2) 左侧路肩TO边坡点
		tx=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->x1;
		ty=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->y1;
		tz=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->z1;
		myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
		pt=new Cordinate;
		pt->x=ax;pt->y=ay;pt->z=az;
		PtBridgeHPDown.Add(pt);
		pt=new Cordinate;
		pt->x=tx;pt->y=ty;pt->z=tz;
		PtBridgeHPUp.Add(pt);
		
		//(3) 左侧路肩点
		tx=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->x1;
		ty=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->y1;
		tz=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->z1;
		myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
		pt=new Cordinate;
		pt->x=ax;pt->y=ay;pt->z=az;
		PtBridgeHPDown.Add(pt);
		pt=new Cordinate;
		pt->x=tx;pt->y=ty;pt->z=tz;
		PtBridgeHPUp.Add(pt);
		
		//(4) 中心线
		tx=myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(Index)->x1;
		ty=myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(Index)->y1;
		tz=myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(Index)->z1;
		myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
		pt=new Cordinate;
		pt->x=ax;pt->y=ay;pt->z=az;
		PtBridgeHPDown.Add(pt);
		pt=new Cordinate;
		pt->x=tx;pt->y=ty;pt->z=tz;
		PtBridgeHPUp.Add(pt);
		
		//(5) 右侧路肩点
			tx=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->x2;
			ty=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->y2;
			tz=myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(Index)->z2;
			myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
			pt=new Cordinate;
			pt->x=ax;pt->y=ay;pt->z=az;
			PtBridgeHPDown.Add(pt);
			pt=new Cordinate;
			pt->x=tx;pt->y=ty;pt->z=tz;
			PtBridgeHPUp.Add(pt);
			
		//(6) 右侧路肩TO边坡点
			tx=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->x2;
			ty=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->y2;
			tz=myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(Index)->z2;
			myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
			pt=new Cordinate;
			pt->x=ax;pt->y=ay;pt->z=az;
			PtBridgeHPDown.Add(pt);
			pt=new Cordinate;
			pt->x=tx;pt->y=ty;pt->z=tz;
			PtBridgeHPUp.Add(pt);
			
		
			//(7)右侧护坡点
			for(i=0;i<=N2;i++)
			{
				for(int j=0;j<=2;j++)
				{
					
					tx=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[i].Hp[j].x;
					ty=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[i].Hp[j].y;
					tz=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(Index)->HuPo_R[i].Hp[j].z;
					myDesingScheme.GetHpD(tx,ty,tz,mangle,mHPangle,&ax,&ay,&az);
					pt=new Cordinate;
					pt->x=ax;pt->y=ay;pt->z=az;
					PtBridgeHPDown.Add(pt);
					pt=new Cordinate;
					pt->x=tx;pt->y=ty;pt->z=tz;
					PtBridgeHPUp.Add(pt);
				}
			}
	
		glLineWidth(4.0);
		glColor3f(1,0,1);

	//记录护坡下面的与地面交点
	if(mCurrentPtstyle=="桥梁起点")
	{
		myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeIndex)->m_ReginPt_Start.RemoveAll();
		for(int j=0;j<PtBridgeHPDown.GetSize();j++)
		{
			pt=new Cordinate;
			pt->x=PtBridgeHPDown.GetAt(j)->x;
			pt->y=PtBridgeHPDown.GetAt(j)->y;
			pt->z=PtBridgeHPDown.GetAt(j)->z;
			myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeIndex)->m_ReginPt_Start.Add(pt);
		}
	}
	else if(mCurrentPtstyle=="桥梁终点")
	{
		myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeIndex)->m_ReginPt_End.RemoveAll();
		for(int j=0;j<PtBridgeHPDown.GetSize();j++)
		{
			pt=new Cordinate;
			pt->x=PtBridgeHPDown.GetAt(j)->x;
			pt->y=PtBridgeHPDown.GetAt(j)->y;
			pt->z=PtBridgeHPDown.GetAt(j)->z;
			myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeIndex)->m_ReginPt_End.Add(pt);
		}
	}
	



		double mMin_X,mMin_Y,mMax_X,mMax_Y,Lx,Ly;
		GetMinMaxXY_bridgeHPm(&mMin_X,&mMin_Y,&mMax_X,&mMax_Y);
		Lx=mMax_X-mMin_X;
		Ly=mMax_Y-mMin_Y;

		glBindTexture(GL_TEXTURE_2D, m_cBridgeHpm.GetTxtID());
		glBegin(GL_TRIANGLE_STRIP);
		for(i=0;i<PtBridgeHPDown.GetSize();i++)
		{
			glTexCoord2f((PtBridgeHPUp.GetAt(i)->x-mMin_X)/Lx,(PtBridgeHPUp.GetAt(i)->y-mMin_Y)/Ly);
			glVertex3f(PtBridgeHPUp.GetAt(i)->x,PtBridgeHPUp.GetAt(i)->y,PtBridgeHPUp.GetAt(i)->z);
			glTexCoord2f((PtBridgeHPDown.GetAt(i)->x-mMin_X)/Lx,(PtBridgeHPDown.GetAt(i)->y-mMin_Y)/Ly);
			glVertex3f(PtBridgeHPDown.GetAt(i)->x,PtBridgeHPDown.GetAt(i)->y,PtBridgeHPDown.GetAt(i)->z);
		}
		glEnd();


		
		glColor3f(1,1,1);
		glLineWidth(0);
		

}

//绘制桥墩
void CT3DSystemView::DrawSceneQD(double x1, double y1, double z1, double x2, double y2, double z2, float QDheight)
{
	//桥墩应具有的宽度
	float Xwidth=m_Railway.m_Railway_width+2*m_Railway.m_Lj_width+2*m_Railway.m_GuiMianToLujianWidth;
	float mangle=myDesingScheme.GetANgle(x1,z1,x2,z2); //计算桥墩模型应旋转的角度(使之与线路垂直)
	if(mangle>180)
		mangle=mangle-180;
	
	float xscale=Xwidth/(m_QD_maxx-m_QD_minx); //计算桥墩模型在X方向上的缩放比例(宽度)
	float yscale=QDheight/(m_QD_maxy-m_QD_miny);//计算桥墩模型在Y方向上的缩放比例(高度)
	
	glPushMatrix(); //压入堆栈
	glScalef(xscale,yscale,1); //桥墩缩放
	glTranslatef(((x1+x2)/2-(m_QD_maxx+m_QD_minx)/2)/xscale,y2/yscale-m_QD_maxy,(z1+z2)/2); //平移
	glRotatef(mangle,0,1,0); //旋转
	glCallList(m_QDList); //调用桥墩显示列表绘制桥墩
	glPopMatrix(); //弹出堆栈
	
}


//绘制隧道
void CT3DSystemView::DrawTunnel()
{
//	m_Tunnel.H;//隧道圆弧顶端再向上的高度
//	m_Tunnel.L;//隧道洞门顶端左右两侧延伸的宽度 
		
	glColor3f(1,1,1);
	glLineWidth(2.0);
	int index=0;
	
	long m_StartIndex,m_EndIndex;//存储当前隧道起终坐标点在所有隧道数据坐标的索引号
	m_StartIndex=-1;
	
	//根据所有隧道的坐标(隧道之间以x=y=-1为分隔,)
	//如下示例数据说明,表示共有2个隧道
	/*
	12345,67,89
	12345,67,89
	12345,67,89
	.....
	12345,67,89
	-1,-1,-1
	12345,67,89
	12345,67,89
	12345,67,89
	.....
	12345,67,89
	-1,-1,-1
	*/
	//
	for (long i=0;i<myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetSize();i++)
	{
		if(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->x1!=-1 && \
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->y1!=-1)
		{
			if(m_StartIndex<0)
				m_StartIndex=i; //当前隧道起始坐标点在所有隧道的坐标的索引号
		}
		else
		{
			m_EndIndex=i-1;//当前隧道终止坐标点在所有隧道的坐标的索引号(m_EndIndex-m_StartIndex确定了当前隧道由多少个坐标点组成)
			DrawTunnelEach(m_StartIndex,m_EndIndex,index,m_Tunnel.H,m_Tunnel.L); //绘制隧道(index:表示当道隧道在所有隧道的索引,即当道隧道是线路中第几个隧道
			index++; //隧道的索引号+1
			m_StartIndex=-1;//恢复为-1
		}
	}
	
}

void CT3DSystemView::GetMinMaxXY_bridgeHPm(double *minx, double *miny, double *maxx, double *maxy)
{
	*minx=PtBridgeHPDown.GetAt(0)->x;
	*miny=PtBridgeHPDown.GetAt(0)->y;
	*maxx=PtBridgeHPUp.GetAt(0)->x;
	*maxy=PtBridgeHPUp.GetAt(0)->y;
	
	for(int i=0;i<PtBridgeHPDown.GetSize();i++)
	{
		if(*minx>PtBridgeHPDown.GetAt(i)->x) *minx=PtBridgeHPDown.GetAt(i)->x;
		if(*miny>PtBridgeHPDown.GetAt(i)->y) *miny=PtBridgeHPDown.GetAt(i)->y;
		if(*maxx<PtBridgeHPDown.GetAt(i)->x) *maxx=PtBridgeHPDown.GetAt(i)->x;
		if(*maxy<PtBridgeHPDown.GetAt(i)->y) *maxy=PtBridgeHPDown.GetAt(i)->y;
	}
	
	for(i=0;i<PtBridgeHPUp.GetSize();i++)
	{
		if(*minx>PtBridgeHPUp.GetAt(i)->x) *minx=PtBridgeHPUp.GetAt(i)->x;
		if(*miny>PtBridgeHPUp.GetAt(i)->y) *miny=PtBridgeHPUp.GetAt(i)->y;
		if(*maxx<PtBridgeHPUp.GetAt(i)->x) *maxx=PtBridgeHPUp.GetAt(i)->x;
		if(*maxy<PtBridgeHPUp.GetAt(i)->y) *maxy=PtBridgeHPUp.GetAt(i)->y;
	}
	
}

//绘制隧道
/*参数说明:
mStartindex:当前隧道起始坐标点在所有隧道的坐标数据的索引号
mEndIndex://当前隧道终止坐标点在所有隧道的坐标数据的索引号
mTunnelIndex:表示当道隧道在所有隧道的索引,即当道隧道是线路中第几个隧道(假设共4个隧道,则mTunnelIndex=0,1,2,3)
H:隧道圆弧顶端再向上的高度
L:隧道洞门顶端左右两侧延伸的宽度 
*/
void CT3DSystemView::DrawTunnelEach(long mStartindex, long mEndIndex, int mTunnelIndex, float H, float L)
{
	double minx,miny,Lx,Ly;
	
	//(1)绘制隧道出口洞门
	glBindTexture(GL_TEXTURE_2D, m_cTunnel_dm.GetTxtID()); //绑定隧道出口洞门纹理
	DrawTunnelDM(
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->x1,\
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->y1,\
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->z1,\
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->x2,\
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->y2,\
		myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mEndIndex)->z2,\
		H,L,mEndIndex,mTunnelIndex,FALSE,\
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelIndex)->mEndAngle\
		);
	
	/*	根据隧道出口洞门左右两侧立墙底下端坐标(x,y1,z1),(x2,y2,z2)	*/

	//(2)绘制隧道内部
	glBindTexture(GL_TEXTURE_2D, m_cTunnel.GetTxtID());//绑定隧道内部纹理
	for ( long i=mStartindex;i<mEndIndex;i++)
	{
	//	glBindTexture(GL_TEXTURE_2D, m_cTunnel.GetTxtID());//绑定隧道内部纹理
		
			//左侧立墙
			glBegin(GL_POLYGON ); 
				glTexCoord2f(0,0);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->y1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glTexCoord2f(0,1);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->y1+m_Tunnel.WallHeight,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glTexCoord2f(1,1);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->y1+m_Tunnel.WallHeight,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
				glTexCoord2f(1,0);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
			glEnd();

			//右侧立墙
			glBegin(GL_POLYGON ); 
			glTexCoord2f(1,0);
			glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->y2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(1,1);
			glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->x2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->y2+m_Tunnel.WallHeight,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->z2);
			glTexCoord2f(0,1);
			glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->y2+m_Tunnel.WallHeight,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glTexCoord2f(0,0);
			glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
				myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
			glEnd();

			
	//		glBindTexture(GL_TEXTURE_2D, m_cTunnel.GetTxtID());
			//顶端圆弧
			int mNums=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetSize();

			minx=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(mNums-1)->x;
			miny=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(mNums-1)->z;
			Lx=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(0)->x-minx;
			Ly=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(0)->z-miny;
			
			//绘制相临两断面的顶端圆弧,以连续多边形方式绘制(GL_QUAD_STRIP)
			glColor3f(1,0,0);
			glBegin(GL_QUAD_STRIP );
				glTexCoord2f(0,0);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(0)->x,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(0)->y,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(0)->z);
				
				glTexCoord2f(0,1);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(0)->x,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(0)->y,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(0)->z);
				
				glTexCoord2f(1.0/m_Tunnel.ArcSegmentNumber,0);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(1)->x,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(1)->y,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(1)->z);
			
				glTexCoord2f(1.0/m_Tunnel.ArcSegmentNumber,1);
				glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(1)->x,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(1)->y,\
					myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(1)->z);
				
			
				for(int n=2;n<myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetSize();n++)
				{
					
					glTexCoord2f(n*1.0/m_Tunnel.ArcSegmentNumber,0);
					glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(n)->x,\
						myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(n)->y,\
						myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i)->tunnelArc.GetAt(n)->z);
					
					glTexCoord2f(n*1.0/m_Tunnel.ArcSegmentNumber,1);
					glVertex3f(myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(n)->x,\
						myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(n)->y,\
						myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(i+1)->tunnelArc.GetAt(n)->z);
					
				}
			
			glEnd();

			glColor3f(1,1,1);
			
		}
		
		//绘制隧道进出口洞门
		glBindTexture(GL_TEXTURE_2D, m_cTunnel_dm.GetTxtID());//绑定隧道进口洞门纹理
		DrawTunnelDM(
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->x1,\
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->y1,\
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->z1,\
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->x2,\
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->y2,\
			myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(mStartindex)->z2,\
			H,L,mStartindex,mTunnelIndex,TRUE,\
			myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelIndex)->mStartangle\
			);
}

//绘制隧道洞门
/*
参数说明:
x1,y1,z1:隧道洞门左两侧立墙底下端坐标
x2,y2,z2:隧道洞门右两侧立墙底下端坐标
H;隧道圆弧顶端再向上的高度
L:隧道洞门顶端左右两侧延伸的宽度
index:隧道洞门的坐标点在所有隧道的数据坐标的索引号
tunnelIndex;当道隧道在所有隧道的索引,即当道隧道是线路中第几个隧道
bstartDM:表示是隧道进口洞门(bstartDM=TRUE)还是隧道出口洞门((bstartDM=FALSE)
mangle:洞门的走向角度
  */
void CT3DSystemView::DrawTunnelDM(double x1, double y1, double z1, double x2, double y2, double z2, float H, float L, int index, int tunnelIndex, BOOL bstartDM, float mangle)
{
//所传递的坐标P6(x1,y1,z1),P5(x2,y2,z2)
//H:从圆弧顶端向上的高度(也就是要在圆弧顶端再加一定的高度,作为洞门的总高度)
//L:从立墙正上方向,向右扩展的距离,构成端墙式洞门结构
//以GL_TRIANGLE绘制△P1p2p6  △P3p4p5 
//P2P3间分为 m_Tunnel.ArcSegmentNumber数量点,与圆弧点相对应,以GL_TRIANGLE_STRIP绘制
/*
隧道洞门示意图图(端墙式洞门)
	P1   L   P2          P3   L   P4
	--------- |-----------|----------
	 \        |      h_   |        /
	  \       |           |       /   
	   \      |  圆弧顶端 |      /
	    \     |           |     /
		 \    |           |    /
		  \   |           |   /
		   \  |           |  /
	     P7_\_|_P6______P5|_/P8
*/


	double P1[3],P2[3],P3[3],P4[3],P5[3],P6[3],P7[3],P8[3];
	double A1[3],A2[3],A3[3],A4[3],A5[3],A6[3];
	double Lx,Ly,minx,miny,maxx,maxy;
	

	P6[0]=x1;P6[1]=y1;P6[2]=z1;
	P5[0]=x2;P5[1]=y2;P5[2]=z2;
	
	long mIn;
	if(bstartDM==TRUE)//洞门进口
		mIn=myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->startIndex-1;//索引值
	else
		mIn=myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->endIndex-1;//索引值

	P7[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[1].x;
	P7[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[1].y;
	P7[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[1].z;

	P8[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[1].x;
	P8[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[1].y;
	P8[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[1].z;
	
		
	//(1)求出P2,P3坐标 P2,P3间距离=m_Tunnel.width
	P2[0]=x1;P2[1]=y1+m_Tunnel.height+H;P2[2]=z1;
	P3[0]=x2;P3[1]=y2+m_Tunnel.height+H;P3[2]=z2;
	
	//(2)求出P1,P4坐标
	P1[0]=P2[0]-L*cos(mangle);P1[1]=P2[1];P1[2]=P2[2]+L*sin(mangle);
	P4[0]=P3[0]+L*cos(mangle);P4[1]=P3[1];P4[2]=P3[2]-L*sin(mangle);
		
	
	//////////////////////////////////////////////////////////////////////////
	//桥墩应具有的宽度
	float Xwidth=fabs(P4[0]-P1[0]);
	float Yheight=fabs(P3[1]-P5[1]);
	mangle=myDesingScheme.GetANgle(x1,z1,x2,z2); //计算隧道洞门模型应旋转的角度(使之与线路垂直)
	
	float xscale=Xwidth/(m_TunnelDM_maxx-m_TunnelDM_minx); //计算隧道洞门模型在X方向上的缩放比例(宽度)
	float yscale=Yheight/(m_TunnelDM_maxy-m_TunnelDM_miny);//计算隧道洞门模型在Y方向上的缩放比例(高度)
	glPushMatrix(); //压入堆栈
	glScalef(xscale,yscale,1); //隧道洞门缩放
	glTranslatef(((x1+x2)/2-(m_TunnelDM_maxx+m_TunnelDM_minx)/2)/xscale,((y1+y2)/2-(m_TunnelDM_maxy+m_TunnelDM_miny)/2)/yscale,(z1+z2)/2); //平移
	glRotatef(mangle,0,1,0); //旋转
	glCallList(m_TunnelDMList); //调用隧道洞门显示列表绘制桥墩
	glPopMatrix(); //弹出堆栈
	
	//////////////////////////////////////////////////////////////////////////
	
//	return;

	

	PCordinate pt;

	if(bstartDM==TRUE)//洞门进口
	{
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_Start.RemoveAll();
		
		//计算隧道洞门进口各点坐标
		pt=new Cordinate;
		pt->x =P1[0];	pt->y =P1[1];	pt->z =P1[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_Start.Add(pt);
	
		pt=new Cordinate;
		pt->x =P2[0];	pt->y =P2[1];	pt->z =P2[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_Start.Add(pt);
	
		pt=new Cordinate;
		pt->x =P3[0];	pt->y =P3[1];	pt->z =P3[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_Start.Add(pt);
		
		pt=new Cordinate;
		pt->x =P4[0];	pt->y =P4[1];	pt->z =P4[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_Start.Add(pt);
		
	}
	else
	{
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_End.RemoveAll();
		
		//计算隧道洞门出口各点坐标
		pt=new Cordinate;
		pt->x =P1[0];	pt->y =P1[1];	pt->z =P1[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_End.Add(pt);
		
		pt=new Cordinate;
		pt->x =P2[0];	pt->y =P2[1];	pt->z =P2[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_End.Add(pt);
		
		pt=new Cordinate;
		pt->x =P3[0];	pt->y =P3[1];	pt->z =P3[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_End.Add(pt);
		
		pt=new Cordinate;
		pt->x =P4[0];	pt->y =P4[1];	pt->z =P4[2];
		myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(tunnelIndex)->m_ReginPt_End.Add(pt);
	}


		float m_L=0.8;//
		A1[0]=P1[0]-m_L*sin(mangle);A1[1]=P1[1];A1[2]=P1[2]-m_L*cos(mangle);
		A2[0]=P2[0]-m_L*sin(mangle);A2[1]=P2[1];A2[2]=P2[2]-m_L*cos(mangle);
		A3[0]=P3[0]-m_L*sin(mangle);A3[1]=P3[1];A3[2]=P3[2]-m_L*cos(mangle);
		A4[0]=P4[0]-m_L*sin(mangle);A4[1]=P4[1];A4[2]=P4[2]-m_L*cos(mangle);
		
		
		if(bstartDM==TRUE)//如果是隧道进口洞门
		{
			Lx=P4[0]-P1[0];
			Ly=P1[1]-P7[1];
			minx=P1[0];
			miny=P7[1];
		}
		else  //如果是隧道出口洞门
		{
			Lx=P1[0]-P4[0];
			Ly=P1[1]-P7[1];
			minx=P4[0];
			miny=P7[1];
		}

			glLineWidth(3.0);  //设置线宽

			glColor3f(1,0,1); //设置颜色
			glBegin(GL_TRIANGLE_STRIP); //以连续折线方式绘制
			glVertex3dv(A1);
			glVertex3dv(P1);
			glVertex3dv(A2);
			glVertex3dv(P2);
			glVertex3dv(A3);
			glVertex3dv(P3);
			glVertex3dv(A4);
			glVertex3dv(P4);
			glEnd();


			glColor3f(0.4,0.2,1);
			glBegin(GL_TRIANGLES); //以三角形形绘制(p1P2P7)
				glTexCoord2f((P1[0]-minx)/Lx,1);
				glVertex3dv(P1);
				glTexCoord2f((P2[0]-minx)/Lx,1);
				glVertex3dv(P2);
				glTexCoord2f((P7[0]-minx)/Lx,0);
				glVertex3dv(P7);
			glEnd();

			glBegin(GL_TRIANGLES);//以三角形形绘制(p2P7P6)
				glTexCoord2f((P2[0]-minx)/Lx,1);
				glVertex3dv(P2);
				glTexCoord2f((P7[0]-minx)/Lx,0);
				glVertex3dv(P7);
				glTexCoord2f((P6[0]-minx)/Lx,0);
				glVertex3dv(P6);
			glEnd();

			glBegin(GL_TRIANGLES);//以三角形形绘制(p3P5P8)
				glTexCoord2f((P3[0]-minx)/Lx,1);
				glVertex3dv(P3);
				glTexCoord2f((P5[0]-minx)/Lx,0);
				glVertex3dv(P5);
				glTexCoord2f((P8[0]-minx)/Lx,0);
				glVertex3dv(P8);
			glEnd();

			glBegin(GL_TRIANGLES);//以三角形形绘制(p3P8P4)
			glTexCoord2f((P3[0]-minx)/Lx,1);
			glVertex3dv(P3);
			glTexCoord2f((P8[0]-minx)/Lx,0);
			glVertex3dv(P8);
			glTexCoord2f((P4[0]-minx)/Lx,1);
			glVertex3dv(P4);
			glEnd();
			
			
			//绘制顶端圆弧
			int mNums=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(index)->tunnelArc.GetSize();
			double tx,ty,tz;
			double rx,ry,rz;
			glBegin(GL_TRIANGLE_STRIP);
			for(int i=0;i<mNums;i++)
			{
				tx=P3[0]+i*(P2[0]-P3[0])/(mNums-1);
				ty=P3[1];
				tz=P3[2]+i*(P2[2]-P3[2])/(mNums-1);
				glTexCoord2f((tx-minx)/Lx,(ty-miny)/Ly);
				glVertex3f(tx,ty,tz);
				rx=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(index)->tunnelArc.GetAt(i)->x;
				ry=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(index)->tunnelArc.GetAt(i)->y;
				rz=myDesingScheme.PtS_Tunnel3D[m_currentSchemeIndexs].GetAt(index)->tunnelArc.GetAt(i)->z;
				glTexCoord2f((rx-minx)/Lx,(ry-miny)/Ly);
				glVertex3f(rx,ry,rz);
			}
		glEnd();
		
//-----------封闭洞门与路基边界处
		//绘制洞门
		if(bstartDM==TRUE)//洞门进口
		{
			glBindTexture(GL_TEXTURE_2D, m_cTunnel_dmwcBp.GetTxtID());
			//(1)左侧
			if(mIn>=1)
			{
				A1[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[2].x;
				A1[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[2].y;
				A1[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[2].z;
				
				A2[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[1].x;
				A2[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[1].y;
				A2[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_L[0].Hp[1].z;
				
				A3[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].x;
				A3[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].y;
				A3[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].z;
				
				A4[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].x;
				A4[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].y;
				A4[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].z;
			
				A5[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].x;
				A5[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].y;
				A5[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].z;
			
				A6[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].x;
				A6[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].y;
				A6[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].z;
				
				//求 minx,maxx,miny,maxy;
				
				minx=miny=999999999;
				maxx=maxy=-999999999;
				if(minx>A1[0]) minx=A1[0];
				if(maxx<A1[0]) maxx=A1[0];
				if(miny>A1[1]) miny=A1[1];
				if(maxy<A1[1]) maxy=A1[1];
				
				if(minx>A2[0]) minx=A2[0];
				if(maxx<A2[0]) maxx=A2[0];
				if(miny>A2[1]) miny=A2[1];
				if(maxy<A2[1]) maxy=A2[1];
				
				if(minx>A3[0]) minx=A3[0];
				if(maxx<A3[0]) maxx=A3[0];
				if(miny>A3[1]) miny=A3[1];
				if(maxy<A3[1]) maxy=A3[1];
				
				if(minx>A4[0]) minx=A4[0];
				if(maxx<A4[0]) maxx=A4[0];
				if(miny>A4[1]) miny=A4[1];
				if(maxy<A4[1]) maxy=A4[1];
			
				if(minx>A5[0]) minx=A5[0];
				if(maxx<A5[0]) maxx=A5[0];
				if(miny>A5[1]) miny=A5[1];
				if(maxy<A5[1]) maxy=A5[1];
				
				if(minx>A6[0]) minx=A6[0];
				if(maxx<A6[0]) maxx=A6[0];
				if(miny>A6[1]) miny=A6[1];
				if(maxy<A6[1]) maxy=A6[1];
				
				if(minx>P1[0]) minx=P1[0];
				if(maxx<P1[0]) maxx=P1[0];
				if(miny>P1[1]) miny=P1[1];
				if(maxy<P1[1]) maxy=P1[1];
				
				if(minx>P7[0]) minx=P7[0];
				if(maxx<P7[0]) maxx=P7[0];
				if(miny>P7[1]) miny=P7[1];
				if(maxy<P7[1]) maxy=P7[1];
				
		
				
				float L1,L2;
				L1=myDesingScheme.GetDistenceXYZ(A1[0],A1[1],A1[2],P1[0],P1[1],P1[2]);
				L2=myDesingScheme.GetDistenceXYZ(A2[0],A2[1],A2[2],A3[0],A3[1],A3[2]);

				int bsign;
				if(A2[2]>=A3[2])
					bsign=-1;
				else
					bsign=1;
		
				minx=A1[0]+bsign*L1;
				maxx=P7[0];
			
				Lx=maxx-minx;
				Ly=maxy-miny;
				
				glColor3f(1,1,0);
				glBegin(GL_TRIANGLE_FAN);
					glTexCoord2f((P1[0]-minx)/Lx,(P1[1]-miny)/Ly);
					glVertex3dv(P1);
					glTexCoord2f((A1[0]-minx+bsign*L1)/Lx,(A1[1]-miny)/Ly);
					glVertex3dv(A1);
					glTexCoord2f((A2[0]-minx+bsign*L2)/Lx,(A2[1]-miny)/Ly);
					glVertex3dv(A2);
					glTexCoord2f((A3[0]-minx)/Lx,(A3[1]-miny)/Ly);
					glVertex3dv(A3);
					glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
					glVertex3dv(A4);
					glTexCoord2f((P7[0]-minx)/Lx,(P7[1]-miny)/Ly);
					glVertex3dv(P7);
				glEnd();
				
					
				glBegin(GL_TRIANGLE_FAN);
				glTexCoord2f((P7[0]-minx)/Lx,(P7[1]-miny)/Ly);
				glVertex3dv(P7);
				glTexCoord2f((A6[0]-minx)/Lx,(A6[1]-miny)/Ly);
				glVertex3dv(A6);
				glTexCoord2f((A5[0]-minx)/Lx,(A5[1]-miny)/Ly);
				glVertex3dv(A5);
				glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
				glVertex3dv(A4);
				glEnd();
			

			//(2)右侧
			A1[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[2].x;
			A1[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[2].y;
			A1[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[2].z;
			
			A2[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[1].x;
			A2[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[1].y;
			A2[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn-1)->HuPo_R[0].Hp[1].z;
			
			A3[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].x;
			A3[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].y;
			A3[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].z;
			
			A4[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].x;
			A4[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].y;
			A4[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].z;
			
			A5[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].x;
			A5[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].y;
			A5[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].z;
			
			A6[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].x;
			A6[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].y;
			A6[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].z;
			
			//求 minx,maxx,miny,maxy;
			
			minx=miny=999999999;
			maxx=maxy=-999999999;
			if(minx>A1[0]) minx=A1[0];
			if(maxx<A1[0]) maxx=A1[0];
			if(miny>A1[1]) miny=A1[1];
			if(maxy<A1[1]) maxy=A1[1];
			
			if(minx>A2[0]) minx=A2[0];
			if(maxx<A2[0]) maxx=A2[0];
			if(miny>A2[1]) miny=A2[1];
			if(maxy<A2[1]) maxy=A2[1];
			
			if(minx>A3[0]) minx=A3[0];
			if(maxx<A3[0]) maxx=A3[0];
			if(miny>A3[1]) miny=A3[1];
			if(maxy<A3[1]) maxy=A3[1];
			
			if(minx>A4[0]) minx=A4[0];
			if(maxx<A4[0]) maxx=A4[0];
			if(miny>A4[1]) miny=A4[1];
			if(maxy<A4[1]) maxy=A4[1];
			
			if(minx>A5[0]) minx=A5[0];
			if(maxx<A5[0]) maxx=A5[0];
			if(miny>A5[1]) miny=A5[1];
			if(maxy<A5[1]) maxy=A5[1];
			
			if(minx>A6[0]) minx=A6[0];
			if(maxx<A6[0]) maxx=A6[0];
			if(miny>A6[1]) miny=A6[1];
			if(maxy<A6[1]) maxy=A6[1];
			
			if(minx>P4[0]) minx=P4[0];
			if(maxx<P4[0]) maxx=P4[0];
			if(miny>P4[1]) miny=P4[1];
			if(maxy<P4[1]) maxy=P4[1];
			
			if(minx>P8[0]) minx=P8[0];
			if(maxx<P8[0]) maxx=P8[0];
			if(miny>P8[1]) miny=P8[1];
			if(maxy<P8[1]) maxy=P8[1];
			
		//	
			L1=myDesingScheme.GetDistenceXYZ(A1[0],A1[1],A1[2],P1[0],P1[1],P1[2]);
			L2=myDesingScheme.GetDistenceXYZ(A2[0],A2[1],A2[2],A3[0],A3[1],A3[2]);
			
			
			if(A2[2]>=A3[2])
				bsign=1;
			else
				bsign=-1;
			
			maxx=A1[0]+bsign*L1;
			minx=P8[0];
			
			Lx=maxx-minx;
			Ly=maxy-miny;
			
			
			glColor3f(1,1,0);
			glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f((P4[0]-minx)/Lx,(P4[1]-miny)/Ly);
			glVertex3dv(P4);
			glTexCoord2f((A1[0]-minx+bsign*L1)/Lx,(A1[1]-miny)/Ly);
			glVertex3dv(A1);
			glTexCoord2f((A2[0]-minx+bsign*L2)/Lx,(A2[1]-miny)/Ly);
			glVertex3dv(A2);
			glTexCoord2f((A3[0]-minx)/Lx,(A3[1]-miny)/Ly);
			glVertex3dv(A3);
			glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
			glVertex3dv(A4);
			glTexCoord2f((P8[0]-minx)/Lx,(P8[1]-miny)/Ly);
			glVertex3dv(P8);
			glEnd();
			
			
			
			glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f((P8[0]-minx)/Lx,(P8[1]-miny)/Ly);
			glVertex3dv(P8);
			glTexCoord2f((A6[0]-minx)/Lx,(A6[1]-miny)/Ly);
			glVertex3dv(A6);
			glTexCoord2f((A5[0]-minx)/Lx,(A5[1]-miny)/Ly);
			glVertex3dv(A5);
			glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
			glVertex3dv(A4);
			glEnd();
			}
		}
		else if(bstartDM==FALSE)//洞门出口
		{
			glBindTexture(GL_TEXTURE_2D, m_cTunnel_dmwcBp.GetTxtID());
			
			//(1)左侧
			A1[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[2].x;
			A1[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[2].y;
			A1[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[2].z;
			
			A2[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[1].x;
			A2[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[1].y;
			A2[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_L[0].Hp[1].z;
			
			A3[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].x;
			A3[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].y;
			A3[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[1].z;
			
			A4[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].x;
			A4[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].y;
			A4[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_L[0].Hp[0].z;
		
			A5[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].x;
			A5[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].y;
			A5[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[3].z;
		
			A6[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].x;
			A6[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].y;
			A6[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouL[2].z;
			
			//求 minx,maxx,miny,maxy;
			
			minx=miny=999999999;
			maxx=maxy=-999999999;
			if(minx>A1[0]) minx=A1[0];
			if(maxx<A1[0]) maxx=A1[0];
			if(miny>A1[1]) miny=A1[1];
			if(maxy<A1[1]) maxy=A1[1];
			
			if(minx>A2[0]) minx=A2[0];
			if(maxx<A2[0]) maxx=A2[0];
			if(miny>A2[1]) miny=A2[1];
			if(maxy<A2[1]) maxy=A2[1];
			
			if(minx>A3[0]) minx=A3[0];
			if(maxx<A3[0]) maxx=A3[0];
			if(miny>A3[1]) miny=A3[1];
			if(maxy<A3[1]) maxy=A3[1];
			
			if(minx>A4[0]) minx=A4[0];
			if(maxx<A4[0]) maxx=A4[0];
			if(miny>A4[1]) miny=A4[1];
			if(maxy<A4[1]) maxy=A4[1];
		
			if(minx>A5[0]) minx=A5[0];
			if(maxx<A5[0]) maxx=A5[0];
			if(miny>A5[1]) miny=A5[1];
			if(maxy<A5[1]) maxy=A5[1];
			
			if(minx>A6[0]) minx=A6[0];
			if(maxx<A6[0]) maxx=A6[0];
			if(miny>A6[1]) miny=A6[1];
			if(maxy<A6[1]) maxy=A6[1];
			
			if(minx>P1[0]) minx=P1[0];
			if(maxx<P1[0]) maxx=P1[0];
			if(miny>P1[1]) miny=P1[1];
			if(maxy<P1[1]) maxy=P1[1];
			
			if(minx>P7[0]) minx=P7[0];
			if(maxx<P7[0]) maxx=P7[0];
			if(miny>P7[1]) miny=P7[1];
			if(maxy<P7[1]) maxy=P7[1];
			
	
			
			float L1,L2;
			L1=myDesingScheme.GetDistenceXYZ(A1[0],A1[1],A1[2],P1[0],P1[1],P1[2]);
			L2=myDesingScheme.GetDistenceXYZ(A2[0],A2[1],A2[2],A3[0],A3[1],A3[2]);

			int bsign;
			if(A2[2]>=A3[2])
				bsign=-1;
			else
				bsign=1;
	
			minx=A1[0]+bsign*L1;
			maxx=P7[0];
		
			Lx=maxx-minx;
			Ly=maxy-miny;
			
			glColor3f(1,1,0);
			glBegin(GL_TRIANGLE_FAN);
				glTexCoord2f((P1[0]-minx)/Lx,(P1[1]-miny)/Ly);
				glVertex3dv(P1);
				glTexCoord2f((A1[0]-minx+bsign*L1)/Lx,(A1[1]-miny)/Ly);
				glVertex3dv(A1);
				glTexCoord2f((A2[0]-minx+bsign*L2)/Lx,(A2[1]-miny)/Ly);
				glVertex3dv(A2);
				glTexCoord2f((A3[0]-minx)/Lx,(A3[1]-miny)/Ly);
				glVertex3dv(A3);
				glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
				glVertex3dv(A4);
				glTexCoord2f((P7[0]-minx)/Lx,(P7[1]-miny)/Ly);
				glVertex3dv(P7);
			glEnd();
			
				
			glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f((P7[0]-minx)/Lx,(P7[1]-miny)/Ly);
			glVertex3dv(P7);
			glTexCoord2f((A6[0]-minx)/Lx,(A6[1]-miny)/Ly);
			glVertex3dv(A6);
			glTexCoord2f((A5[0]-minx)/Lx,(A5[1]-miny)/Ly);
			glVertex3dv(A5);
			glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
			glVertex3dv(A4);
			glEnd();
		

			//(2)右侧
			A1[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[2].x;
			A1[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[2].y;
			A1[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[2].z;
			
			A2[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[1].x;
			A2[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[1].y;
			A2[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn+1)->HuPo_R[0].Hp[1].z;
			
			A3[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].x;
			A3[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].y;
			A3[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[1].z;
			
			A4[0]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].x;
			A4[1]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].y;
			A4[2]=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(mIn)->HuPo_R[0].Hp[0].z;
			
			A5[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].x;
			A5[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].y;
			A5[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[3].z;
			
			A6[0]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].x;
			A6[1]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].y;
			A6[2]=myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(mIn)->SuiGouR[2].z;
			
			//求 minx,maxx,miny,maxy;
			
			minx=miny=999999999;
			maxx=maxy=-999999999;
			if(minx>A1[0]) minx=A1[0];
			if(maxx<A1[0]) maxx=A1[0];
			if(miny>A1[1]) miny=A1[1];
			if(maxy<A1[1]) maxy=A1[1];
			
			if(minx>A2[0]) minx=A2[0];
			if(maxx<A2[0]) maxx=A2[0];
			if(miny>A2[1]) miny=A2[1];
			if(maxy<A2[1]) maxy=A2[1];
			
			if(minx>A3[0]) minx=A3[0];
			if(maxx<A3[0]) maxx=A3[0];
			if(miny>A3[1]) miny=A3[1];
			if(maxy<A3[1]) maxy=A3[1];
			
			if(minx>A4[0]) minx=A4[0];
			if(maxx<A4[0]) maxx=A4[0];
			if(miny>A4[1]) miny=A4[1];
			if(maxy<A4[1]) maxy=A4[1];
			
			if(minx>A5[0]) minx=A5[0];
			if(maxx<A5[0]) maxx=A5[0];
			if(miny>A5[1]) miny=A5[1];
			if(maxy<A5[1]) maxy=A5[1];
			
			if(minx>A6[0]) minx=A6[0];
			if(maxx<A6[0]) maxx=A6[0];
			if(miny>A6[1]) miny=A6[1];
			if(maxy<A6[1]) maxy=A6[1];
			
			if(minx>P4[0]) minx=P4[0];
			if(maxx<P4[0]) maxx=P4[0];
			if(miny>P4[1]) miny=P4[1];
			if(maxy<P4[1]) maxy=P4[1];
			
			if(minx>P8[0]) minx=P8[0];
			if(maxx<P8[0]) maxx=P8[0];
			if(miny>P8[1]) miny=P8[1];
			if(maxy<P8[1]) maxy=P8[1];
			
		//	
			L1=myDesingScheme.GetDistenceXYZ(A1[0],A1[1],A1[2],P1[0],P1[1],P1[2]);
			L2=myDesingScheme.GetDistenceXYZ(A2[0],A2[1],A2[2],A3[0],A3[1],A3[2]);
			
			
			if(A2[2]>=A3[2])
				bsign=1;
			else
				bsign=-1;
			
			maxx=A1[0]+bsign*L1;
			minx=P8[0];
			
			Lx=maxx-minx;
			Ly=maxy-miny;
			
			
			glColor3f(1,1,0);
			glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f((P4[0]-minx)/Lx,(P4[1]-miny)/Ly);
			glVertex3dv(P4);
			glTexCoord2f((A1[0]-minx+bsign*L1)/Lx,(A1[1]-miny)/Ly);
			glVertex3dv(A1);
			glTexCoord2f((A2[0]-minx+bsign*L2)/Lx,(A2[1]-miny)/Ly);
			glVertex3dv(A2);
			glTexCoord2f((A3[0]-minx)/Lx,(A3[1]-miny)/Ly);
			glVertex3dv(A3);
			glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
			glVertex3dv(A4);
			glTexCoord2f((P8[0]-minx)/Lx,(P8[1]-miny)/Ly);
			glVertex3dv(P8);
			glEnd();
			
			
			
			glBegin(GL_TRIANGLE_FAN);
			glTexCoord2f((P8[0]-minx)/Lx,(P8[1]-miny)/Ly);
			glVertex3dv(P8);
			glTexCoord2f((A6[0]-minx)/Lx,(A6[1]-miny)/Ly);
			glVertex3dv(A6);
			glTexCoord2f((A5[0]-minx)/Lx,(A5[1]-miny)/Ly);
			glVertex3dv(A5);
			glTexCoord2f((A4[0]-minx)/Lx,(A4[1]-miny)/Ly);
			glVertex3dv(A4);
			glEnd();
		}

		glColor3f(1,1,1);
		glLineWidth(0);
}



//线路三维建模
void CT3DSystemView::OnMenuBuild3dlinemodle() 
{
	BeginWaitCursor();
	b_haveGetRegion=FALSE;//是否已获取封闭区域
	
	myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_RailwayBP3D[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].RemoveAll();
	myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].RemoveAll();
	

	if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()<2)
		return;
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	float iPer=100.0/(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1);
	CString tt;
	
	
	for (long i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;i++)
	{	
		
		if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->Lc<=
			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC)
		{
			myDesingScheme.Get3DLineModel(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->x,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->y,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->z,\
				m_Railway.m_Railway_width,m_Railway.m_Lj_width,m_Railway.m_Lj_Dh,\
				m_Railway.m_GuiMianToLujianWidth,45,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle,\
				i,\
				myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->Lc
				);
		}
		tt.Format("正在进行线路三维建模 , 请稍候... , 已完成%.2f%s" , (i+1)*iPer , "%");
		pMainFrame->Set_BarText(4 , tt , 0); 
		
	}
	

	if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)->Lc<=
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC)
	{
		myDesingScheme.Get3DLineModelLast(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2)->x,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2)->y,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2)->z,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)->x,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)->y,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)->z,\
			m_Railway.m_Railway_width,m_Railway.m_Lj_width,m_Railway.m_Lj_Dh,\
			m_Railway.m_GuiMianToLujianWidth,45,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2)->strJDStyle,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1)->strJDStyle,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-2)->Lc\
			);
	}

	
	pMainFrame->Set_BarText(4 , "加载地形与影像纹理数据完成!" , 0); 
	b_haveMadeRail3DwayList=FALSE;
	b_haveMadeRail3DwayList_Ortho=FALSE;
	OnDraw (GetDC()); //刷新三维场景
	
}

//沿方案线漫游,也就不将当前线路方案中心线作为漫游路径而已	
void CT3DSystemView::OnFlyRoutinschemeline() 
{
	m_R=3000;
	m_r=2000;
	
	if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()<=1)
	{
		if(m_FlayPath.GetSize()<=0) //表明当前是按路径漫游状态
			m_PathFlag=FALSE; //标识飞行路径无效
		else
			m_PathFlag=TRUE; //标识飞行路径有效
		return;
	}
	
	m_FlayPath.RemoveAll();//飞行路径数组清空
	PCordinate ppt=NULL;
	//将线路中线作为飞行路径
	for(int i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize();i++)
	{
		ppt = new Cordinate;
		ppt->x=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x;//x坐标
		ppt->y=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y;//y坐标
		ppt->z=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z;//z坐标
		ppt->Lc=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->Lc;//里程
		m_FlayPath.Add(ppt); //将线路中线坐标点加入飞行路径数组中
	}
	
	if(m_FlayPath.GetSize()>1) //如果飞行路径有效
	{
		m_bmouseView=FALSE; //停止鼠标旋转视角功能
		//	m_ShowFlyPath=TRUE;
// 		m_ShowFlyPath=FALSE;//线路三维漫游不需要显示路径,影响效果
		m_PathFlag=TRUE; //标识飞行路径有效
		m_ifZoomonRoad=TRUE; //标识沿线路方案漫游
		m_far=1500; //线路三维漫游时,让视口远距离变小一些,剪裁掉一些看不见
		//的元素,加快漫游速度,原来是10000,相当于10公里,线路三维漫游时不需
		//要这么大
		
	}
	else
	{
		m_ifZoomonRoad=FALSE; //标识沿线路方案漫游为FALSE
	}
	
	OnFlyRoutineheight();	//按相对高度飞行	
}





//根据漫游路径相临坐标点计算相机各参数
void CT3DSystemView::GetCameraCorrdinate(double x1, double y1, double z1, double x2, double y2, double z2)
{
	//(x1,y1,x1):飞行路径当前点坐标
	//(x2,y2,x2):飞行路径下一点坐标
	

	if(m_FlyHeightType == GIS_FLY_STATICHEIGHT)	//固定高度飞行方式
	{
		m_vView.x=x2;//观察点x坐标
		m_vView.y=m_StaticHeight;//观察点y坐标=y2+m_StaticHeight固定高度值
		m_vView.z=z2;//观察点z坐标

		m_vPosition.x=x1;//视点x坐标
		m_vPosition.y=m_vView.y;//视点y坐标=观察点y坐标
		m_vPosition.z=z1;//视点z坐标
	}
	//按相对高度(即沿路径)漫游时，需计算一个基本高度
	else if(m_FlyHeightType == GIS_FLY_PATHHEIGHT)
	{
		m_vView.x=x2; //观察点x坐标
		m_vView.y=y2+m_StaticHeight; //观察点y坐标=y2+m_StaticHeight固定高度值
		m_vView.z=z2;//观察点z坐标
		
		//求二点之间距离
		float distance = sqrt((x2-x1)*(x2-x1)+(z2-z1)*(z2-z1));
		//根据倾角计算高度差
		float dh = distance * tan(m_ViewUpDown * PAI_D180);
		
		m_vPosition.x=x1;//视点x坐标
		m_vPosition.y=m_vView.y+dh;//视点y坐标=观察点y坐标+高差
		m_vPosition.z=z1;//视点z坐标
	}
}

//计算漫游时的速度
void CT3DSystemView::CalZoomSpeed(float mCurZooomLC)
{
	static DWORD dwStart = 0;
	DWORD dwNow = ::GetTickCount();
	if(dwNow-dwStart>=100) //0.1秒
	{
		long fgf=dwNow-dwStart;
		CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
		CString strText;
		strText.Format("漫游速度= %.3f 米/秒 ",(mCurZooomLC-m_PreZooomLC)/(((dwNow-dwStart)/1000.0)),0);
		pMainFrame->Set_BarText(4,strText,0); 
		dwStart = dwNow;
		m_PreZooomLC=mCurZooomLC;
		
	}
}



void CT3DSystemView::BuildQDModelList()
{
	g_3dsModels=new C3DSModel;
	char *str = new char[256]; 
	
	CString str3DSfilename;
	
	strcpy(g_sMediaPath,m_3DSfilename_QD);
	GetFilePath(g_sMediaPath);
	g_3dsModels->LoadModelFromFile((LPTSTR)(LPCTSTR)m_3DSfilename_QD);
	
	glNewList(m_QDList,GL_COMPILE);
	g_3dsModels->Draw(2,&m_QD_minx,&m_QD_maxx,&m_QD_miny,&m_QD_maxy,&m_QD_minz,&m_QD_maxz);	
	glEndList();
	
}

//创建隧道洞门显示列表
void CT3DSystemView::BuildTunnelDMModelList()
{
	g_3dsModels=new C3DSModel;
	char *str = new char[256]; 
	
	CString str3DSfilename;
	
	m_3DSfilename_TunnelDM=".\\模型\\隧道洞门\\端墙式洞门\\模型文件.3DS";
	strcpy(g_sMediaPath,m_3DSfilename_TunnelDM);
	GetFilePath(g_sMediaPath);
	g_3dsModels->LoadModelFromFile((LPTSTR)(LPCTSTR)m_3DSfilename_TunnelDM);
	
	glNewList(m_TunnelDMList,GL_COMPILE);
	g_3dsModels->Draw(2,&m_TunnelDM_minx,&m_TunnelDM_maxx,&m_TunnelDM_miny,&m_TunnelDM_maxy,&m_TunnelDM_minz,&m_TunnelDM_maxz);	
	glEndList();

}


void CT3DSystemView::makeQLList()
{
	glNewList(m_QLanList,GL_COMPILE);
		DrawCylinder(m_Bridge.m_Bridge_HLWidth/100.0,m_Bridge.m_Bridge_HLHeight,500);
	glEndList();
	
}

//画圆柱
void CT3DSystemView::DrawCylinder(GLfloat radii, GLfloat Height, GLint segmentNum)
{
	if(radii==0   ||   Height==0)   
		return;   
	GLint j,k;   
	GLfloat x,y;   
		
	
	glBegin(GL_TRIANGLE_STRIP);
	for(j=0,k=0;j<=segmentNum;j++,k++)
	{
		x=cos(2.0*PI*j/(double)segmentNum);
		y=sin(2.0*PI*j/(double)segmentNum);
		if(k==segmentNum)
			k=0;
		
		glTexCoord2f(x/radii,0);
		glVertex3f(x*radii, 0,-y*radii);
		
		glTexCoord2f(x/radii,1);
		glVertex3f(x*radii,Height,-y*radii);
		
	}
	glEnd();
}

//隧道参数设置
void CT3DSystemView::OnMenuTunnelset() 
{
	CTunncelPropertySet dlg;
	dlg.m_tunnel_height=m_Tunnel.height;//隧道总高度
	dlg.m_tunnel_Archeight=m_Tunnel.Archeight;//隧道圆弧高度
	dlg.m_tunnel_WallHeight =m_Tunnel.height-m_Tunnel.Archeight;//隧道立墙高度
	dlg.m_tunnel_ArcSegmentNumber=m_Tunnel.ArcSegmentNumber;//隧道圆弧分段数
	dlg.m_tunnel_H=m_Tunnel.H ;//隧道圆弧顶端再向上的高度
	dlg.m_tunnel_L=m_Tunnel.L;//隧道洞门顶端左右两侧延伸的宽度 
	if(dlg.DoModal()==IDOK)
	{
		m_Tunnel.height=dlg.m_tunnel_height;//隧道总高度
		m_Tunnel.Archeight=dlg.m_tunnel_Archeight;//隧道圆弧高度
		m_Tunnel.WallHeight =m_Tunnel.height-m_Tunnel.Archeight;;//隧道立墙高度
		m_Tunnel.ArcSegmentNumber=dlg.m_tunnel_ArcSegmentNumber;//隧道圆弧分段数
		m_Tunnel.H= dlg.m_tunnel_H;//隧道圆弧顶端再向上的高度
		m_Tunnel.L=dlg.m_tunnel_L;//隧道洞门顶端左右两侧延伸的宽度 
		b_haveMadeRail3DwayList=FALSE; //当隧道参数改变时,需要重新构建显示列表(透视投影模式)
		b_haveMadeRail3DwayList_Ortho=FALSE;//当隧道参数改变时,需要重新构建显示列表(正射投影模式)
		OnDraw (GetDC()); //刷新三维场景
	}	
}

//设置桥梁参数
void CT3DSystemView::OnMenuBridgeset() 
{
	CBridgeSet dlg;
	dlg.m_Bridge_HLHeight=m_Bridge.m_Bridge_HLHeight;//桥梁栏杆高
	dlg.m_Bridge_HLSpace=m_Bridge.m_Bridge_HLSpace;//桥梁栏杆间距
	dlg.m_Bridge_HLWidth=m_Bridge.m_Bridge_HLWidth;//桥梁栏杆宽度
	dlg.m_Bridge_QDSpace=m_Bridge.m_Bridge_QDSpace;//桥墩间距
	dlg.m_Bridge_HPangle=m_Bridge.m_Bridge_HPangle;//桥头护坡倾角
	dlg.m_bridgeColorR=m_bridgeColorR;//桥梁栏杆颜色
	dlg.m_bridgeColorG=m_bridgeColorG;//桥梁栏杆颜色
	dlg.m_bridgeColorB=m_bridgeColorB;//桥梁栏杆颜色

	if(dlg.DoModal()==IDOK)
	{
		m_Bridge.m_Bridge_HLHeight=dlg.m_Bridge_HLHeight;//桥梁栏杆高
		m_Bridge.m_Bridge_HLSpace=dlg.m_Bridge_HLSpace;;//桥梁栏杆间距
		m_Bridge.m_Bridge_HLWidth=dlg.m_Bridge_HLWidth;//桥梁栏杆宽度
		m_Bridge.m_Bridge_QDSpace=dlg.m_Bridge_QDSpace;//桥墩间距
		m_Bridge.m_Bridge_HPangle=dlg.m_Bridge_HPangle;//桥头护坡倾角
		m_bridgeColorR=dlg.m_bridgeColorR;//桥梁栏杆颜色
		m_bridgeColorG=dlg.m_bridgeColorG;//桥梁栏杆颜色
		m_bridgeColorB=dlg.m_bridgeColorB;////桥梁栏杆颜色
		b_haveMadeRail3DwayList=FALSE;
		b_haveMadeRail3DwayList_Ortho=FALSE;
		makeQLList(); //重新生成桥梁栏杆显示列表
		OnDraw (GetDC()); //刷新三维场景
	}	
}

//绘制桥梁栏杆
void CT3DSystemView::DrawBridgeHL()
{
		//绘制桥梁栏杆
		glPushAttrib(GL_TEXTURE_BIT|GL_POLYGON_BIT);
			glDisable(GL_TEXTURE_2D); //关闭纹理
			glPolygonMode(GL_FRONT_AND_BACK ,GL_LINE);	//以线型绘制
			glColor3f(m_bridgeColorR/255.0,m_bridgeColorG/255.0,m_bridgeColorB/255.0);//设置桥梁栏杆颜色
			glLineWidth(m_Bridge.m_Bridge_HLWidth); //设置绘制栏杆的线宽
		for (long i=0;i<myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetSize()-1;i++)
		{
			
			//如果当前和下一桥梁栏杆坐标!=-1，则绘制桥梁栏杆
			if(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y1!=-1)
			{	
				
				//左侧桥梁栏杆中间横线
				glBegin(GL_LINES);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1+m_Bridge.m_Bridge_HLHeight/2.0,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y1+m_Bridge.m_Bridge_HLHeight/2.0,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
				glEnd();

				//左侧桥梁栏杆顶端横线
				glBegin(GL_LINES);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1+m_Bridge.m_Bridge_HLHeight,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y1+m_Bridge.m_Bridge_HLHeight,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->z1);
				glEnd();


				//右侧桥梁栏杆中间横线
				glBegin(GL_LINES);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y2+m_Bridge.m_Bridge_HLHeight/2.0,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z2);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y2+m_Bridge.m_Bridge_HLHeight/2.0,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
				glEnd();
				
				//右侧桥梁栏杆顶端横线
				glBegin(GL_LINES);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y2+m_Bridge.m_Bridge_HLHeight,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z2);
				glVertex3f(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y2+m_Bridge.m_Bridge_HLHeight,
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->z2);
				glEnd();

				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制左侧圆柱形栏杆立柱
				glPopMatrix();


		
				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z2);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制右侧圆柱形栏杆立柱
				glPopMatrix();
			
			}
			
			if(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1==-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1==-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->x1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->y1!=-1)
			{
				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->y1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->z1);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制左侧圆柱形栏杆立柱
				glPopMatrix();
				
				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->y2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i-1)->z2);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制右侧圆柱形栏杆立柱
				glPopMatrix();
				
			}
			if(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1!=-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->x1==-1 && \
				myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i+1)->y1==-1)
			{
				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y1,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z1);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制左侧圆柱形栏杆立柱
				glPopMatrix();
				
				glPushMatrix();
				glTranslatef(myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->x2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->y2,\
					myDesingScheme.PtS_BridgeLG3D[m_currentSchemeIndexs].GetAt(i)->z2);
				glCallList(m_QLanList);//调用桥梁栏杆显示列表绘制右侧圆柱形栏杆立柱
				glPopMatrix();
				
			}

		
		}
		glEnable(GL_TEXTURE_2D);
		glPolygonMode(GL_FRONT_AND_BACK ,GL_FILL);	
		glPopAttrib();
}

//重新从数据库中读取线路中线坐标点数据
void CT3DSystemView::ReLoadCenterPt()
{
	CString tt,strSql;
	PCordinate pt;
	
	//重新读取方案的的线路中线坐标数据
	//		if(myDesingScheme.SchemeDatass[m_currentSchemeIndexs].EndLC<=myDesingScheme.SchemeDatass[m_currentSchemeIndexs].StartLC)
	//			strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' ORDER BY 里程 ASC",\
	//			myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename);
	//		else	
	strSql.Format("SELECT *  FROM  T3DLineZxCorrdinate WHERE 方案名称='%s' \
		ORDER BY 里程 ASC",\
		myDesingScheme.SchemeDatass[m_currentSchemeIndexs].Schemename\
		);
	
	try
	{
		//			if(m_Recordset->State) //如果
		//				m_Recordset->Close();
		m_Recordset->Open((_bstr_t)strSql,(IDispatch*)(theApp.m_pConnection),adOpenDynamic,adLockOptimistic,adCmdText);   //打开数据表 
	}
	
	catch(_com_error& e)	//错误捕捉
	{
		CString errormessage;
		errormessage.Format("打开表失败!\r\n错误信息:%s",e.ErrorMessage());
		::MessageBox(NULL,errormessage,"打开数据表",MB_ICONSTOP);
		return;
	} 
	
	myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].RemoveAll(); //清空线路中线数组PtS_3DLineZX[]
	while (!m_Recordset->adoEOF)
	{
		pt=new Cordinate;
		
		Thevalue = m_Recordset->GetCollect("x坐标"); 
		pt->x=(double)Thevalue; //线路中线点的x坐标
		
		Thevalue = m_Recordset->GetCollect("y坐标"); 
		pt->y=(double)Thevalue;//线路中线点的y坐标
		
		Thevalue = m_Recordset->GetCollect("z坐标"); 
		pt->z=(double)Thevalue;//线路中线点的z坐标
		
		Thevalue = m_Recordset->GetCollect("坐标类型"); 
		if(Thevalue.vt!=VT_NULL) 
		{
			tt=Thevalue.bstrVal;
			pt->strJDStyle=tt; //线路中线点的x类型
		}
		
		Thevalue = m_Recordset->GetCollect("地面高程"); 
		pt->dmh=(double)Thevalue; //对应的地面高程
		
		Thevalue = m_Recordset->GetCollect("填挖高"); 
		pt->Derh=(double)Thevalue; //对应的填挖高
		
		Thevalue = m_Recordset->GetCollect("里程"); 
		pt->Lc=(double)Thevalue;//对应的里程
		
		myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].Add(pt); //存储线路中线坐标数据
		m_Recordset->MoveNext(); //指针下移
		
	}
	m_Recordset->Close(); //关闭记录集
	
}

//边坡纹理
void CT3DSystemView::OnMenuTexturebp() 
{
	CTextureBP	dlg;
	CString strTexturename;

	if(dlg.DoModal()==IDOK) //如果以IDOK方式关闭对话框
	{
		strTexturename="纹理\\边坡防护\\"+dlg.m_BPtextureName; //得到纹理文件名路径名
		m_cTxtureBP.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename); //加载边坡纹理
		b_haveMadeRail3DwayList=FALSE; //三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(透视投影模式下)
		b_haveMadeRail3DwayList_Ortho=FALSE;//三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(正射投影模式下)
		OnDraw (GetDC()); //刷新三维场景
	}	
}

//路肩纹理
void CT3DSystemView::OnMenuTexturelj() 
{
	CTextureLJ	dlg;
	CString strTexturename;
	if(dlg.DoModal()==IDOK)//如果以IDOK方式关闭对话框
	{
		strTexturename="纹理\\路肩\\"+dlg.m_LJtextureName;//得到纹理文件名路径名
		m_cTxtureLJ.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename);//加载路肩纹理
		b_haveMadeRail3DwayList=FALSE; //三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(透视投影模式下)
		b_haveMadeRail3DwayList_Ortho=FALSE;//三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(正射投影模式下)
		OnDraw (GetDC());//刷新三维场景
	}	
}

//桥梁护坡面纹理
void CT3DSystemView::OnMenuTextureqlhpm() 
{
	CTextureQLHpm	dlg;
	CString strTexturename;
	if(dlg.DoModal()==IDOK)//如果以IDOK方式关闭对话框
	{
		strTexturename="纹理\\桥下方护坡面\\"+dlg.m_HQMtextureName;//得到纹理文件名路径名
		m_cBridgeHpm.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename);//加载路肩纹理
		b_haveMadeRail3DwayList=FALSE; //三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(透视投影模式下)
		b_haveMadeRail3DwayList_Ortho=FALSE;//三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(正射投影模式下)
		OnDraw (GetDC());//刷新三维场景
	}	
}

//隧道内墙纹理
void CT3DSystemView::OnMenuTexturetunnel() 
{

	CTextureTunnel	dlg;
	CString strTexturename;
	if(dlg.DoModal()==IDOK)//如果以IDOK方式关闭对话框
	{
		strTexturename="纹理\\隧道内墙\\"+dlg.m_TunneltextureName;//得到纹理文件名路径名
		m_cTunnel.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename);//加载路肩纹理
		b_haveMadeRail3DwayList=FALSE; //三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(透视投影模式下)
		b_haveMadeRail3DwayList_Ortho=FALSE;//三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(正射投影模式下)
		OnDraw (GetDC());//刷新三维场景
	}		
}

//隧道洞门纹理
void CT3DSystemView::OnMenuTexturetunnelDm() 
{
	CTextureTunnelDm	dlg;
	CString strTexturename;
	if(dlg.DoModal()==IDOK)//如果以IDOK方式关闭对话框
	{
		strTexturename="纹理\\隧道洞门\\"+dlg.m_TunnelDmtextureName;//得到纹理文件名路径名
		m_cTunnel_dm.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename);//加载路肩纹理
		strTexturename="纹理\\隧道洞门外侧边坡\\"+dlg.m_TunnelDmtextureName;//得到纹理文件名路径名
		m_cTunnel_dmwcBp.LoadGLTextures((LPTSTR)(LPCTSTR)strTexturename);//隧道洞门外侧边坡
		
		b_haveMadeRail3DwayList=FALSE; //三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(透视投影模式下)
		b_haveMadeRail3DwayList_Ortho=FALSE;//三维线路显示列表标识为FALSE,表示需要重新构建线路三维模型列表(正射投影模式下)
		OnDraw (GetDC());//刷新三维场景
	}		
}

//时钟指北针的绘制
void CT3DSystemView::DrawClock()
{
//	if(m_bShowClocFlag==FALSE) return;
	
	glPushMatrix(); //压入矩阵堆栈
		glPolygonMode(GL_FRONT_AND_BACK,GL_LINE); //线绘制方式
		glDisable(GL_TEXTURE_2D);	//关闭纹理	
		SetClockProjectionNavigate(); //设置指北针的投影参数
		glCallList(m_clockList);//调用指北针时钟的显示列表
		DrawNorthPt();	//绘制指北针
		
		glPolygonMode(GL_FRONT_AND_BACK,GL_FILL); //填充绘制方式
		glEnable(GL_TEXTURE_2D);//打开纹理	
		glLineWidth(1.0); //设置线宽
		glColor3f(1.0, 1.0, 1.0); //设置颜色
	glPopMatrix();//弹出矩阵堆栈
}

void CT3DSystemView::SetClockProjectionNavigate()
{
	float wh=120; //设置时钟的高度
	glViewport(0, WinViewY-wh,wh, wh);//设置视口位置和大小
    glMatrixMode( GL_PROJECTION ); //设置当前矩阵为透视矩阵
    glLoadIdentity(); //将当前矩阵置换为单位阵 
	glOrtho (0.0f,1.0,0.0f, 1.0f, -1.0f, 1.0f);
	glMatrixMode( GL_MODELVIEW );//设置当前矩阵为模型矩阵
	glLoadIdentity ();//将当前矩阵置换为单位阵 
}

//创建时钟指北针显示列表
void CT3DSystemView::makeClockList()
{
	glNewList(m_clockList,GL_COMPILE); //创建显示列表
	float R=0.5,x,y;//时钟圆盘半径
	int i;

	glColor3f(0.0, 1.0, 1.0); //设置文字颜色

	x=R*cos((0)*PAI_D180)+0.37;//加上偏移量，准备写入字母"E"，表示刻度3
	y=R*sin((0)*PAI_D180)+0.48;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"E"); //在设置坐标位置写入E，表示方向“东”

	x=R*cos((90)*PAI_D180)+0.47;//加上偏移量，准备写入字母"N"，表示刻度12
	y=R*sin((90)*PAI_D180)+0.36;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"N");//在设置坐标位置写入N，表示方向“北”
	
	x=R*cos((180)*PAI_D180)+0.59;//加上偏移量，准备写入字母"W" ，表示刻度9
	y=R*sin((180)*PAI_D180)+0.48;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"W");//在设置坐标位置写入W，表示方向“西”

	x=R*cos((270)*PAI_D180)+0.48;//加上偏移量，准备写入字母"S" ，表示刻度6
	y=R*sin((270)*PAI_D180)+0.58;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"S");//在设置坐标位置写入S，表示方向“南”
	
	glColor3f(1.0, 1.0, 1.0); //设置时钟刻度数字颜色
	
	x=R*cos((30)*PAI_D180)+0.39; //设置坐标
	y=R*sin((30)*PAI_D180)+0.43;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"2"); //写入数字刻度
	
	
	x=R*cos((60)*PAI_D180)+0.42;
	y=R*sin((60)*PAI_D180)+0.40;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"1");//写入数字刻度1
	
	
	x=R*cos((120)*PAI_D180)+0.49;
	y=R*sin((120)*PAI_D180)+0.38;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"11");//写入数字刻度11
	
	x=R*cos((150)*PAI_D180)+0.55;
	y=R*sin((150)*PAI_D180)+0.42;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"10");//写入数字刻度10


	x=R*cos((210)*PAI_D180)+0.58;
	y=R*sin((210)*PAI_D180)+0.53;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"8");//写入数字刻度8

	x=R*cos((240)*PAI_D180)+0.54;
	y=R*sin((240)*PAI_D180)+0.58;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"7");//写入数字刻度7


	x=R*cos((300)*PAI_D180)+0.43;
	y=R*sin((300)*PAI_D180)+0.58;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"5");//写入数字刻度5

	x=R*cos((330)*PAI_D180)+0.40;
	y=R*sin((330)*PAI_D180)+0.52;
	PrintText(x,y,(LPTSTR)(LPCTSTR)"4");//写入数字刻度4
	
    //设置时钟圆内圆盘的颜色
	glColor3f(0.0, 1.0, 0.0);
	glLineWidth(2.0); //设置线宽
    //绘制时钟圆外圆盘
	glBegin(GL_LINE_STRIP);
		for ( i=0;i<=360;i++)
		{
			 x=R*cos(i*PAI_D180)+0.5;
			 y=R*sin(i*PAI_D180)+0.5;
			glVertex2f(x,y);
		}
	glEnd();
	
	//绘制时钟内表示小时之间的刻度,用圆点表示	
	float d;
	for (i=0;i<=360;i+=6)
	{

		switch(i)
		{
		case 0: //在N(12点)处
			glColor3f(0.0, 1.0, 1.0);//设置颜色
			glPointSize(4.0); //设置点的大小
			break;
		case 90://在W(9点)处
			glColor3f(0.0, 1.0, 1.0);//设置颜色
			glPointSize(4.0); //设置点的大小
			break;
		case 180://在S(6点)处
			glColor3f(0.0, 1.0, 1.0);//设置颜色
			glPointSize(4.0); //设置点的大小
			break;
		case 270://在E(3点)处
			glColor3f(0.0, 1.0, 1.0);//设置颜色
			glPointSize(4.0); //设置点的大小
			break;
		default:
			glColor3f(0.77, 0.67, 0.95);//设置颜色
			glPointSize(2.0); //设置点的大小
			break;
		}
	
		if(i%30==0 && i%90!=0)//在整时刻处(如7点,8点等)
		{	
			glColor3f(1.0, 0.0, 1.0);//设置颜色
			glPointSize(3.0);//设置点的大小
		}
		
		d=0.04;//偏移量
		x=R*cos(i*PAI_D180)+0.5;//计算x坐标
		y=R*sin(i*PAI_D180)+0.5;//计算y坐标
		
		//绘制点标志
		glBegin(GL_POINTS);
			x=x-d*cos(i*PAI_D180);
			y=y-d*sin(i*PAI_D180);
			glVertex2f(x,y);
		glEnd();
		}
	glLineWidth(1.0); //设置线宽
	glEndList();//结束显示列表
	
}

//绘制指北针
void CT3DSystemView::DrawNorthPt()
{
	glPolygonMode(GL_FRONT_AND_BACK,GL_FILL); //以填充方式绘制
	glDisable(GL_TEXTURE_2D); //关闭纹理		
	float R=0.5;
		
	float x1,y1,x2,y2,x3,y3;
	float mPtangle=25;
	float tempangle;
	float L,L1,L2;
	L1=0.3; 
	L2=0.2;
	x1=0.5;y1=0.5; //时钟圆心点坐标，指北针围绕该点进行指向旋转
	x3=x1+L1*cos((m_NorthPtangle)*PAI_D180);
	y3=y1+L1*sin((m_NorthPtangle)*PAI_D180);

	//如果指北针指向角位于第1象限
	if(m_NorthPtangle>=0 && m_NorthPtangle<=90)
	{	tempangle=m_NorthPtangle-mPtangle;
		L=0.1/cos(mPtangle*PAI_D180);
		x2=x1-L2*cos(tempangle*PAI_D180);
		y2=y1-L2*sin(tempangle*PAI_D180);
		
		glColor3f(1.0, 1.0, 0.0); //设置颜色
		glBegin(GL_TRIANGLES);//绘制左侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
		
		glColor3f(1.0, 0.0, 0.0); //设置颜色
		tempangle=m_NorthPtangle+mPtangle;
		x2=x1-L2*cos(tempangle*PAI_D180);
		y2=y1-L2*sin(tempangle*PAI_D180);
		glBegin(GL_TRIANGLES);//绘制右侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
	}

	//如果指北针指向角位于第2象限
	if(m_NorthPtangle>90 && m_NorthPtangle<=180)
	{	

	tempangle=180-m_NorthPtangle-mPtangle;
	x2=x1+L2*cos(tempangle*PAI_D180);
	y2=y1-L2*sin(tempangle*PAI_D180);
	
	glColor3f(1.0, 1.0, 0.0); //设置颜色
	glBegin(GL_TRIANGLES);//绘制左侧三角形
		glVertex2f(x1,y1);
		glVertex2f(x2,y2);
		glVertex2f(x3,y3);
	glEnd();
	
	glColor3f(1.0, 0.0, 0.0); //设置颜色
	tempangle=180-m_NorthPtangle+mPtangle;
	x2=x1+L2*cos(tempangle*PAI_D180);
	y2=y1-L2*sin(tempangle*PAI_D180);
	glBegin(GL_TRIANGLES);//绘制右侧三角形
		glVertex2f(x1,y1);
		glVertex2f(x2,y2);
		glVertex2f(x3,y3);
	glEnd();
	}

	//如果指北针指向角位于第3象限
	if(m_NorthPtangle>180 && m_NorthPtangle<=270)
	{	
		
		tempangle=m_NorthPtangle-180-mPtangle;
		x2=x1+L2*cos(tempangle*PAI_D180);
		y2=y1+L2*sin(tempangle*PAI_D180);
		
		glColor3f(1.0, 1.0, 0.0);//设置颜色
		glBegin(GL_TRIANGLES);//绘制左侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
		
		glColor3f(1.0, 0.0, 0.0);//设置颜色
		tempangle=m_NorthPtangle-180+mPtangle;
		x2=x1+L2*cos(tempangle*PAI_D180);
		y2=y1+L2*sin(tempangle*PAI_D180);
		glBegin(GL_TRIANGLES);//绘制右侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
	}

	//如果指北针指向角位于第4象限
	if(m_NorthPtangle>270 && m_NorthPtangle<=360)
	{	
		
		tempangle=360-m_NorthPtangle-mPtangle;
		x2=x1-L2*cos(tempangle*PAI_D180);
		y2=y1+L2*sin(tempangle*PAI_D180);
		
		glColor3f(1.0, 1.0, 0.0);//设置颜色
		glBegin(GL_TRIANGLES);//绘制左侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
		
		glColor3f(1.0, 0.0, 0.0);//设置颜色
		tempangle=360-m_NorthPtangle+mPtangle;
		x2=x1-L2*cos(tempangle*PAI_D180);
		y2=y1+L2*sin(tempangle*PAI_D180);
		glBegin(GL_TRIANGLES);//绘制右侧三角形
			glVertex2f(x1,y1);
			glVertex2f(x2,y2);
			glVertex2f(x3,y3);
		glEnd();
	}

	glColor3f(0.4, 0.47, 0.72);//设置颜色
	glLineWidth(2.0); //设置线宽
	glBegin(GL_LINES); //指北针下短直线
		glVertex2f(x1,y1);
		x2=x1-0.1*cos((m_NorthPtangle)*PAI_D180);
		y2=y1-0.1*sin((m_NorthPtangle)*PAI_D180);
		glVertex2f(x2,y2);
	glEnd();
	glPolygonMode(GL_FRONT_AND_BACK,GL_FILL); 
	glEnable(GL_TEXTURE_2D); //开启纹理		
	glLineWidth(1.0);//设置线宽
}

//在指定位置输出文本
void CT3DSystemView::PrintText(float x, float y, char *string)
{
	int length;
	length = (int) strlen(string); //字符串长度
	glRasterPos2f(x,y); //定位当前光标
	for (int m=0;m<length;m++)
	{
		glutBitmapCharacter(GLUT_BITMAP_HELVETICA_12, string[m]);//用位图方式按指定的字体绘制一个字符串
	}
	
}

 //根据相机的视点坐标和观察点坐标计算时钟指北针指向角度
void CT3DSystemView::GetNorthPtangle()
{
	if(theApp.bLoginSucceed==FALSE) return;
	
	float dx,dz,ar;
	dx=m_vPosition.x-m_vView.x;//相机视点与观察点x坐标之差
	dz=m_vPosition.z-m_vView.z;//相机视点与观察点z坐标之差
	
	if(dx==0) //如果dx==0
	{
		if(dz>=0) //如果dz>=0
			m_NorthPtangle=90; ////指北针初始指向角度=90，指向屏幕里面（Z轴负方向）
		else
			m_NorthPtangle=270;////指北针初始指向角度=270，指向屏幕外面（Z轴正方向）
	}
	else
	{
		if(dx>0) 
		{
			if(dz>0) //第2象限
			{
				ar=fabs(atan(dx/dz));
				m_NorthPtangle=90+ar*HDANGLE;//指北针初始指向角度
			}
			else    //第3象限
			{
				ar=fabs(atan(dx/dz));
				m_NorthPtangle=270-ar*HDANGLE;//指北针初始指向角度
			}
		}
		
		if(dx<0)
		{
			if(dz>0)  //第1象限
			{
				ar=fabs(atan(dx/dz));
				m_NorthPtangle=90-ar*HDANGLE;//指北针初始指向角度
			}
			else    //第4象限
			{
				ar=fabs(atan(dx/dz));
				m_NorthPtangle=270+ar*HDANGLE;//指北针初始指向角度
			}
		}
		
	}
}


//获取封闭区域
void CT3DSystemView::GetRoadCloseRegion()
{
	CString tt,m_style,m_style2;
	
	PCordinate pt=NULL;
	mRegions.RemoveAll();
	
	int m_reginNums=0; //封闭区域数
	int mBridgeindex=0;//桥梁索引初始值
	int mTunnelindex=0;//隧道索引初始值

	m_ReginPtsLeft.RemoveAll(); //左边界封闭区域数据点清空
	m_ReginPtsRight.RemoveAll();//右边界封闭区域数据点清空

	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	float fPer=100.0/(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1);
	for (long i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
	{

		tt.Format("正在构建线路左侧封闭区域,已完成 %.2f%s",(i+1)*fPer,"%");
		pMainFrame->Set_BarText(4 , tt , 0); 
	
		//线路当前断面交点类型
		m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
		//线路下一断面交点类型
		m_style2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;
		//如果交点类型非桥梁和隧道点
		if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点" \
			&&m_style!="桥梁起点"  &&m_style!="桥梁中间点" &&m_style!="桥梁终点" \
			&& m_style2!="隧道起点")
		{
			//线路当前断面左侧边坡数
			int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_L-1;
			//线路下一断面左侧边坡数
			int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_L-1;
			
			//如果相临两断面边坡填挖类型不同
			if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->TW_left!=\
				myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->TW_left)
			{
				if(N1>=0) //如果线路当前断面左侧边坡数>=1
				{
					for(int m=N1;m>=0;m--) //从最高一级边坡开始记录所有边坡点
					{
						for(int n=2;n>=0;n--)
						{
							pt=new Cordinate;
							pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].x;
							pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].y;
							pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].z;
							m_ReginPtsLeft.Add(pt);//存储点坐标数据
						}
						
					}
				}
				else //线路当前断面左侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y1;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
				}

				if(N2>=0) //如果线路下一断面左侧边坡数>=1
				{
					for(int m=0;m<=N2;m++) //从最低一级边坡开始记录所有边坡点
					{
						for(int n=0;n<=2;n++)
						{
							pt=new Cordinate;
							pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].x;
							pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].y;
							pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].z;
							m_ReginPtsLeft.Add(pt);//存储点坐标数据
						}
						
					}
				}
				else //线路下一断面左侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y1;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
				}
				
			}
			else //如果相临两断面边坡填挖类型相同,
			{
				//计算线路当前断面方兴未艾写入的边界坐标点数据
				if(N1>=0) //如果线路当前断面左侧边坡数>=1
				{
					if(N1<=N2) //如果线路当前断面左侧边坡数<=线路下一断面左侧边坡数,只记录当前断面最高一级边坡点坐标
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N1].Hp[2].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N1].Hp[2].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N1].Hp[2].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
					}
					else //如果线路当前断面左侧边坡数>线路下一断面左侧边坡数
					{
						for(int m=N2+1;m<=N1;m++) //需要从上至下记录比下一断面多出的边坡所有边坡点坐标
						{
							for(int n=2;n>=0;n--)
							{
								pt=new Cordinate;
								pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].x;
								pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].y;
								pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[m].Hp[n].z;
								m_ReginPtsLeft.Add(pt);//存储点坐标数据
							}
								
						}
						//只需要记录线路下一断面的最高一级边坡点坐标
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N2].Hp[1].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N2].Hp[1].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[N2].Hp[1].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
						
					}
				}
				else  //线路当前断面左侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y1;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
				}

				//计算线路下一断面方兴未艾写入的边界坐标点数据
				if(N2>=0) //如果线路下一断面左侧边坡数>=1
				{
					if(N2<=N1)//如果线路下一断面左侧边坡数<=线路当前面左侧边坡数,只记录下一断面最高一级边坡点坐标
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N2].Hp[2].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N2].Hp[2].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N2].Hp[2].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
					}
					else  //如果线路下一断面左侧边坡数>线路当前断面左侧边坡数
					{
						for(int m=N1+1;m<=N2;m++)//需要从下至上记录比当前断面多出的边坡所有边坡点坐标
						{
							for(int n=0;n<=2;n++)
							{
								pt=new Cordinate;
								pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].x;
								pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].y;
								pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[m].Hp[n].z;
								m_ReginPtsLeft.Add(pt);//存储点坐标数据
							}
							
						}
						//补充线路下一断面对应当前断面最高一级的边坡点坐标
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N1].Hp[1].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N1].Hp[1].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[N1].Hp[1].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
						
					}
				}
				else  //线路下一断面左侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y1;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
				}
			}	
				
		}
		else //如果交点类型是"隧道起点",表示遇到了隧道起始端
		{
			
			if(m_style=="隧道起点")
			{
				//线路前一交点左侧边坡数
				int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->Huponums_L-1;
				for(int j=N1;j>0;j--) //记录线路前一断面左侧所有边坡点
				{
					for(int k=2;k>=0;k--) //每一级边坡由3个点组成
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_L[j].Hp[k].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_L[j].Hp[k].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_L[j].Hp[k].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
						
					}
				}

				//得到隧道进口洞门处的所有坐标点数据,作为边界点数据
				int M=myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_Start.GetSize();
				for(int k=0;k<M;k++)
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_Start.GetAt(k)->x;
					pt->y =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_Start.GetAt(k)->y;
					pt->z =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_Start.GetAt(k)->z;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
				}

				WriteRegionPts(m_reginNums,TRUE,-1);//当前封闭区域结束,写入左侧边界点坐标数据
				m_ReginPtsLeft.RemoveAll();//数组清空
				m_reginNums++; //封闭区域数+1
							
			}
			else if(m_style=="桥梁起点") //如果交点类型是"桥梁起点"
			{
				//得到该桥梁起始点处下试试看护坡点数,,作为边界点数据
				int M=myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_Start.GetSize();
				for(int k=0;k<M;k++)
				{
					
					pt=new Cordinate;
					pt->x =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_Start.GetAt(k)->x;
					pt->y =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_Start.GetAt(k)->y;
					pt->z =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_Start.GetAt(k)->z;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
					
				}
				WriteRegionPts(m_reginNums,TRUE,-1);//当前封闭区域结束,写入左侧边界点坐标数据
				m_ReginPtsLeft.RemoveAll();//数组清空
				m_reginNums++;//封闭区域数+1
				
			}			
			
			else if (m_style=="隧道终点")//如果交点类型是"隧道终点",表示遇到了隧道终止端,表示新的封闭区域开始了
			{
				
				//得到隧道出口洞门处的所有坐标点数据,作为边界点数据
				int M=myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_End.GetSize();
				for(int k=M-1;k>=0;k--)
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_End.GetAt(k)->x;
					pt->y =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_End.GetAt(k)->y;
					pt->z =myDesingScheme.TunnelInfor[m_currentSchemeIndexs].GetAt(mTunnelindex)->m_ReginPt_End.GetAt(k)->z;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
					
				}

				//线路下一断面左侧边坡数
				int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_L-1;
                //补充线路下一断面左侧第1级连坡的最高点坐标
				pt=new Cordinate;
				pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[0].Hp[2].x;
				pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[0].Hp[2].y;
				pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[0].Hp[2].z;
				m_ReginPtsLeft.Add(pt);//存储点坐标数据
				
				//记录线路下一断面左侧边坡第2级到最调到级所有边坡点坐标,作为边界点数据
				for(int j=1;j<=N1;j++)
				{
					for(int k=0;k<=2;k++)
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[k].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[k].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[k].z;
						m_ReginPtsLeft.Add(pt);//存储点坐标数据
					}
				}
				mTunnelindex++; //到了隧道终止端,表示一座隧道结束了,隧道索引+1
			}
			
			else if (m_style=="桥梁终点") //如果交点类型是"桥梁终点",表示遇到了桥梁终止端,表示新的封闭区域开始了
			{
				
				//得到桥梁结束处下方的所有护坡点坐标数据,作为边界点数据
				int M=myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_End.GetSize();
				for(int k=M-1;k>=0;k--)
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_End.GetAt(k)->x;
					pt->y =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_End.GetAt(k)->y;
					pt->z =myDesingScheme.BridgeInfor[m_currentSchemeIndexs].GetAt(mBridgeindex)->m_ReginPt_End.GetAt(k)->z;
					m_ReginPtsLeft.Add(pt);//存储点坐标数据
					
				}

				mBridgeindex++; //到了桥梁终止端,表示一座桥梁结束了,桥梁索引号+1
			}
			
			
		}

	}

	m_reginNums=0;
	m_ReginPtsRight.RemoveAll(); //右侧边界点清空
	for ( i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		tt.Format("正在构建线路右侧封闭区域,已完成 %.2f%s",(i+1)*fPer,"%");
		pMainFrame->Set_BarText(4 , tt , 0); 

		//线路当前断面交点类型
		m_style=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
 		//线路下一断面交点类型
		m_style2=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->strJDStyle;
		
		//如果交点类型非桥梁和隧道点
		if(m_style!="隧道起点" && m_style!="隧道中间点" &&m_style!="隧道终点"\
			&&m_style!="桥梁起点"  &&m_style!="桥梁中间点" &&m_style!="桥梁终点" \
			&&m_style2!="隧道起点")
		{
			//线路当前断面右侧边坡数
			int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_R-1;
			//线路下一断面右侧边坡数
			int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_R-1;
			//如果相临两断面边坡填挖类型不同
			if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->TW_right!=\
				myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->TW_right)
			{
				if(N1>=0)  //如果线路当前断面右侧边坡数>=1
				{
					for(int m=N1;m>=0;m--) //从最高一级边坡开始记录所有边坡点
					{
						for(int n=2;n>=0;n--)
						{
							pt=new Cordinate;
							pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].x;
							pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].y;
							pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].z;
							m_ReginPtsRight.Add(pt);//存储点坐标数据
						}
						
					}
				}
				else//线路当前断面右侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y2;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2;
					m_ReginPtsRight.Add(pt);
				}
				
				if(N2>=0) //如果线路下一断面右侧边坡数>=1
				{
					for(int m=0;m<=N2;m++) //从最低一级边坡开始记录所有边坡点
					{
						for(int n=0;n<=2;n++)
						{
							pt=new Cordinate;
							pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].x;
							pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].y;
							pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].z;
							m_ReginPtsRight.Add(pt);//存储点坐标数据
						}
						
					}
				}
				else//线路下一断面右侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y2;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2;
					m_ReginPtsRight.Add(pt);//存储点坐标数据
				}
				
			}
			else//如果相临两断面边坡填挖类型相同
			{
				//计算线路当前断面左侧边界坐标点数据
				if(N1>=0) //如果线路当前断面右侧边坡数>=1
				{
					if(N1<=N2)//如果线路当前断面右侧边坡数<=线路下一断面右侧边坡数,只记录当前断面最高一级边坡点坐标
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N1].Hp[2].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N1].Hp[2].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N1].Hp[2].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
					}
					else //如果线路当前断面右侧边坡数>线路下一断面右侧边坡数
					{
						for(int m=N2+1;m<=N1;m++) //需要从上至下记录比下一断面多出的边坡所有边坡点坐标
						{
							for(int n=2;n>=0;n--)
							{
								pt=new Cordinate;
								pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].x;
								pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].y;
								pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[m].Hp[n].z;
								m_ReginPtsRight.Add(pt);//存储点坐标数据
							}
						}
						//只需要记录线路下一断面的最高一级边坡点坐标
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N2].Hp[1].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N2].Hp[1].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[N2].Hp[1].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
					}		
				}
				else //线路当前断面右侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y2;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2;
					m_ReginPtsRight.Add(pt);	//存储点坐标数据
				}
			
				//计算线路下一断面应该写入的边界坐标点数据
				if(N2>=0) //如果线路下一断面右侧边坡数>=1
				{				
					if(N2<=N1)//如果线路下一断面右侧边坡数<=线路当前面右侧边坡数,只记录下一断面最高一级边坡点坐标
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N2].Hp[2].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N2].Hp[2].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N2].Hp[2].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
					}
					else//如果线路下一断面右侧边坡数>线路当前断面右侧边坡数
					{
						for(int m=N1+1;m<=N2;m++)//需要从下至上记录比当前断面多出的边坡所有边坡点坐标
						{
							for(int n=0;n<=2;n++)
							{
								pt=new Cordinate;
								pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].x;
								pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].y;
								pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[m].Hp[n].z;
								m_ReginPtsRight.Add(pt);//存储点坐标数据
							}
						}
						//补充线路下一断面对应当前断面最高一级的边坡点坐标
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N1].Hp[1].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N1].Hp[1].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[N1].Hp[1].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
						
					}
				}
				else  //线路下一断面右侧刚好是填挖平衡点,则直接将路肩边坡点作为边界点
				{
					pt=new Cordinate;
					pt->x =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2;
					pt->y =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y2;
					pt->z =myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2;
					m_ReginPtsRight.Add(pt);//存储点坐标数据
				}
			}
		}
		else
		{
			if(m_style=="隧道起点") //如果交点类型是"隧道起点",表示遇到了隧道起始端
			{
				//线路前一交点右侧边坡数
				int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->Huponums_R-1;
				for(int j=N1;j>0;j--)//记录线路前一断面右侧所有边坡点
				{
					for(int k=2;k>=0;k--)//每一级边坡由3个点组成
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_R[j].Hp[k].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_R[j].Hp[k].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i-1)->HuPo_R[j].Hp[k].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
						
					}
				}

				WriteRegionPts(m_reginNums,FALSE,1); //当前封闭区域结束,写入右侧边界点坐标数据
				m_ReginPtsRight.RemoveAll();//数组清空
				m_reginNums++;//封闭区域数+1
				
			}
			else if(m_style=="桥梁起点")//如果交点类型是"桥梁起点"
			{
				WriteRegionPts(m_reginNums,FALSE,1);//当前封闭区域结束,写入右侧边界点坐标数据
				m_ReginPtsRight.RemoveAll();//数组清空
				m_reginNums++;//封闭区域数+1
				
			}			
			else if(m_style=="隧道终点")//如果交点类型是"隧道终点",表示遇到了隧道终止端,表示新的封闭区域开始了
			{

				//得到隧道出口洞门处的所有坐标点数据,作为边界点数据
				int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_R-1;
				for(int j=1;j<=N1;j++)
				{
					for(int k=0;k<=2;k++)
					{
						pt=new Cordinate;
						pt->x =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[k].x;
						pt->y =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[k].y;
						pt->z =myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[k].z;
						m_ReginPtsRight.Add(pt);//存储点坐标数据
					}
				}
				mTunnelindex++;//到了隧道终止端,表示一座隧道结束了,隧道索引+1
			}
			
			else if (m_style=="桥梁终点")//如果交点类型是"桥梁终点",表示遇到了桥梁终止端,表示新的封闭区域开始了
			{
				mBridgeindex++;//到了桥梁终止端,表示一座桥梁结束了,桥梁索引号+1
			}
		}
		
	}

	//对不合理的边蜀区域进行清理
	for(i=0;i<mRegions.GetSize();i++)
	{
		if(mRegions.GetAt(i)->ReginPts.GetSize()<=1) //如果封闭区域数据点<=1
		{
			mRegions.RemoveAt(i);//移除
		}
	}
}

//绘制经过线路建三维模后的三维地形TIN
void CT3DSystemView::DrawTerrainDelaunay()
{
	
	if(b_haveTerrainZoomroadList==TRUE)   //如果已成构建线路三维模型的显示列表
	{
		glCallList(m_TerrainZoomroadList);
		return;
	}


	if(m_currentSchemeIndexs<0) //如果当前线路方案索引<0，返回
		return;
	
	if(	b_haveGetRegion==TRUE) //如果已经获取线路封闭区域，返回
		return;
	//没有经过线路三维建模返回
	if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()<=1)
		return;
	if(myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()<=1)
		return;
	
	BeginWaitCursor();
	
	CString m_style,m_style2;
	long i;

	//创建线路三维建模后是否建立了地形TIN模型的显示列表
	glNewList(m_TerrainZoomroadList,GL_COMPILE_AND_EXECUTE);
	
	glPushAttrib(GL_TEXTURE_BIT|GL_POLYGON_BIT);
	glDisable(GL_TEXTURE_2D); //关闭纹理
	glLineWidth(2.0); //设置线宽
	glColor3f(0.2,0,1.0); //设置颜色
	
	GetRoadCloseRegion();//获取封闭区域
	BuildTerrainRegionInfo(); //计算每个地形块连同封闭区域的所有地形数据集
	m_BlockTriInfos.RemoveAll(); //地形块三角网信息清空
	
	CString tt;
	float fPer=100.0/m_BlockLSPts.GetSize();
	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	for( i=0;i<m_BlockLSPts.GetSize();i++)   
	{
		tt.Format("正在绘制地形TIN模型,已完成 %.2f%s",(i+1)*fPer,"%");
		pMainFrame->Set_BarText(4 , tt , 0); 
		
		DrawTerrainTIN(i); //以TIN方式绘制地形块i
		tempBlockTriInfos=new BlockTriInfos;
		for_each(m_Triangles.begin(), m_Triangles.end(), GetBlockTriangles(i));
		AddBlcokTri(i,m_BlockLSPts.GetAt(i)->mDemBlockRow,m_BlockLSPts.GetAt(i)->mDemBlockCol);//加入当前地形块有三角网数据(同时要剔除位于线路封闭区域的所有三角形)
	}
	glEndList();
	b_haveTerrainZoomroadList=TRUE;//线路三维建模后是否建立了地形TIN模型的显示列表
	EndWaitCursor();
	MessageBox("建模完成","线路三维建模",MB_ICONINFORMATION);
	pMainFrame->Set_BarText(4 , "加载地形与影像纹理数据完成!" , 0); 
}

//将边界点坐标数据写入封闭区域
void CT3DSystemView::WriteRegionPts(int index, BOOL bAddorAppend, int mLeftorRight)
{
	PRegions tempregion=new Regions;
	PCordinate pt=NULL;
	
	if(bAddorAppend==TRUE)  //如果是增加边界点，mRegions数组新增加元素
	{
		tempregion->index=index; //封闭区域索引
		for(long i=0;i<m_ReginPtsLeft.GetSize();i++)//所有左边界点数据
		{
			pt=new Cordinate;
			pt->x=m_ReginPtsLeft.GetAt(i)->x;
			pt->y=m_ReginPtsLeft.GetAt(i)->y;
			pt->z=m_ReginPtsLeft.GetAt(i)->z;
			tempregion->ReginPts.Add(pt);//存储到临时数组中
		}
		mRegions.Add(tempregion);//加入边界数据
	}
	else if(bAddorAppend==FALSE)  //如果不是新增数据，直接向mRegions数组索引位置写入数据
	{
		//所有右边界点数据
		for(long i=m_ReginPtsRight.GetSize()-1;i>=0;i--)
		{
			pt=new Cordinate;
			pt->x=m_ReginPtsRight.GetAt(i)->x;
			pt->y=m_ReginPtsRight.GetAt(i)->y;
			pt->z=m_ReginPtsRight.GetAt(i)->z;
			mRegions.GetAt(index)->ReginPts.Add(pt);//加入右边界点数据
		}
		m_ReginPtsRight.RemoveAll();//右边界点数组清空
		
		
	}
}

//根据封闭区域边界上的数据点，计算该封闭区域穿越的地形块ID号
//若封闭区域穿越多个地形块，则其记录的地形块ID号也有多个
void CT3DSystemView::GetRegionID(long index)
{
	double x,y;
	int ID;
	mRegions.GetAt(index)->GridID.RemoveAll();
	for(long i=0;i<mRegions.GetAt(index)->ReginPts.GetSize();i++)
	{
		
		x=mRegions.GetAt(index)->ReginPts.GetAt(i)->x;//封闭区域边界上的数据点的x坐标	
		y=-mRegions.GetAt(index)->ReginPts.GetAt(i)->z;//封闭区域边界上的数据点的y坐标
		
		ID=GetBlockID(x,y);	//根据x,y坐标求取所属于的地形块的ID号
		
		bool bhaveExit=FALSE; //初始标识为FALSE
		for(int k=0;k<mRegions.GetAt(index)->GridID.GetSize();k++)
		{
			int mid=mRegions.GetAt(index)->GridID.GetAt(k);
			if(ID==mid) //如果求得地形块ID号在记录中的ID号相同，则忽略，不再记录
			{
				bhaveExit=TRUE;
				break;
			}
		}
		if(bhaveExit==FALSE) ////如果求得地形块ID号与所有记录中的ID号不同，则记录
			mRegions.GetAt(index)->GridID.Add(ID);//记录ID号
	}
}

//计算每个地形块连同封闭区域的所有地形数据集
void CT3DSystemView::BuildTerrainRegionInfo()
{
	//如果没有地形数据
	if(theApp.m_BlockRows*theApp.m_BlockCols<=0)
		return;

	long i,j;
	//计算每个封闭子区域的最小最大x,y坐标
	for( i=0;i<mRegions.GetSize();i++)
	{
		GetRegionID(i);//得到封闭区域ID
		double minx,miny,maxx,maxy;
		minx=miny=999999999;
		maxx=maxy=-999999999;
		//计算封闭最大,最小坐标
		for( j=0;j<mRegions.GetAt(i)->ReginPts.GetSize();j++)
		{
			if(minx>mRegions.GetAt(i)->ReginPts.GetAt(j)->x)
				minx=mRegions.GetAt(i)->ReginPts.GetAt(j)->x;
			if(miny>-mRegions.GetAt(i)->ReginPts.GetAt(j)->z)
				miny=-mRegions.GetAt(i)->ReginPts.GetAt(j)->z;
			if(maxx<mRegions.GetAt(i)->ReginPts.GetAt(j)->x)
				maxx=mRegions.GetAt(i)->ReginPts.GetAt(j)->x;
			if(maxy<-mRegions.GetAt(i)->ReginPts.GetAt(j)->z)
				maxy=-mRegions.GetAt(i)->ReginPts.GetAt(j)->z;
			
		}
		mRegions.GetAt(i)->minx=minx;
		mRegions.GetAt(i)->miny=miny;
		mRegions.GetAt(i)->maxx=maxx;
		mRegions.GetAt(i)->maxy=maxy;
	}
	
	b_haveGetRegion=TRUE; //标识已计算封闭区域为TRUE

	//重新定义数组大小，并分配内存
	mBlockReginInforMe=new CArray<PBlockReginInforMe,PBlockReginInforMe>[theApp.m_BlockRows*theApp.m_BlockCols];
	m_BlockLSPts.RemoveAll();//清空

	CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
	float fper=100.0/(theApp.m_BlockRows*theApp.m_BlockCols);
	CString tt;
	for(i=1;i<=theApp.m_BlockRows;i++)
	{
		for( j=1;j<=theApp.m_BlockCols;j++)
		{
			tt.Format("正在计算每个地形块连同封闭区域的所有地形数据集,已完成 %.2f%s",i*j*fper,"%");
			pMainFrame->Set_BarText(4 , tt , 0); 
			
			GetBlockInregion(i,j); //得到地形块封闭区域信息
			WriteBlockLsPts(i,j);//将地形块封闭区域信息写入
		}
	}
}

//绘制地形块三角网地形
void CT3DSystemView::DrawTerrainTIN(long blockIndex)
{
	m_Vertices.clear(); //顶点清除
	m_Triangles.clear();//三角形清除
	double x,y,z;
	
	CDelaunay d; //定义CDelaunay变量
	//获取某个地形块内所有有效地形数据点,参与地形三角网的构建
	for(long j=0;j<m_BlockLSPts.GetAt(blockIndex)->ReginPts.GetSize();j++)
	{
		x=m_BlockLSPts.GetAt(blockIndex)->ReginPts.GetAt(j)->x; //x坐标
		y=-m_BlockLSPts.GetAt(blockIndex)->ReginPts.GetAt(j)->z;//y坐标
		z=m_BlockLSPts.GetAt(blockIndex)->ReginPts.GetAt(j)->y;//z坐标(高程)
		if(z!=theApp.m_DEM_IvalidValue) //如果高程是无效值,将不参与三角网构建
			m_Vertices.insert(vertex(x, y,z));
	}
	d.Triangulate(m_Vertices, m_Triangles);//根据地形数据点(m_Vertices中存储)构建地形三角网
}

//加入当前地形块有三角网数据(同时要剔除位于线路封闭区域的所有三角形)
void CT3DSystemView::AddBlcokTri(int blockIndex, int DemblcokRow, int DemblcokCol)
{
	for(long i=0;i<tempBlockTriInfos->TriPts.GetSize();i++)
	{
		//判断地形块TIN三角形是否位于地形块内封闭区域内
		BOOL bOk=GetTriOutRegion(\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt1.x,\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt1.y,\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt2.x,\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt2.y,\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt3.x,\
			tempBlockTriInfos->TriPts.GetAt(i)->Pt3.y,\
			blockIndex);
		if(bOk==TRUE) //如果三角形是位于地形块内封闭区域内,以x坐标=-9999来标识
			tempBlockTriInfos->TriPts.GetAt(i)->Pt1.x=-9999;
	}
	
	tempBlockTriInfos->mDemBlockRow=DemblcokRow;//当前地形块的行号
	tempBlockTriInfos->mDemBlockCol=DemblcokCol;//当前地形块的列号
	m_BlockTriInfos.Add(tempBlockTriInfos);//加入当前地形块的三角网信息
}

//判断地形块TIN三角形是否位于地形块内封闭区域内
BOOL CT3DSystemView::GetTriOutRegion(double x1, double y1, double x2, double y2, double x3, double y3, int blockIndex)
{
	Point *polygon;	
	Point pt;
	bool b1,b2,b3;	
	int m_a1,m_a2,m_a3;
	m_a1=m_a2=m_a3=0; //初始标识为0

	//对当前地形块内共有的封闭区域进行判断
	//mBlockReginInforMe[blockIndex].GetSize()：当前地形块内共有的封闭区域总数
	for(int i=0;i<mBlockReginInforMe[blockIndex].GetSize();i++)
	{
		//如果三角形的三个点全部位于封闭区域最大最小矩形边界之外
		//则该三角形必位于封闭区域外,不需用剔除
		if((x1<mBlockReginInforMe[blockIndex].GetAt(i)->minx && \
			x2<mBlockReginInforMe[blockIndex].GetAt(i)->minx && \
			x3<mBlockReginInforMe[blockIndex].GetAt(i)->minx)|| \
			(x1>mBlockReginInforMe[blockIndex].GetAt(i)->maxx && \
			x2>mBlockReginInforMe[blockIndex].GetAt(i)->maxx && \
			x3>mBlockReginInforMe[blockIndex].GetAt(i)->maxx)|| \
			(y1<mBlockReginInforMe[blockIndex].GetAt(i)->miny && \
			y2<mBlockReginInforMe[blockIndex].GetAt(i)->miny && \
			y3<mBlockReginInforMe[blockIndex].GetAt(i)->miny)|| \
			(y1>mBlockReginInforMe[blockIndex].GetAt(i)->maxy && \
			y2>mBlockReginInforMe[blockIndex].GetAt(i)->maxy && \
			y3>mBlockReginInforMe[blockIndex].GetAt(i)->maxy))
		{
			m_a1=m_a2=m_a3=0;//3点都位于封闭区域内，三点标识全为0
		}
		else
		{
			//读取封闭区域所有的边界点
			long M=mBlockReginInforMe[blockIndex].GetAt(i)->ReginPts.GetSize();
			polygon=new Point[M];
			for(long j=0;j<M;j++)
			{
				polygon[j].x=mBlockReginInforMe[blockIndex].GetAt(i)->ReginPts.GetAt(j)->x;
				polygon[j].y=-mBlockReginInforMe[blockIndex].GetAt(i)->ReginPts.GetAt(j)->z;
			}
			
			
			pt.x=x1; pt.y=y1;
			b1=mCalF.InPolygon(polygon,M,pt);//判断点(x1,y1)是否位于该封闭区域内
			if(b1==true) //如果位于
				m_a1=1;
			
			pt.x=x2; pt.y=y2;
			b2=mCalF.InPolygon(polygon,M,pt);//判断点(x2,y2)是否位于该封闭区域内
			if(b2==true)//如果位于
				m_a2=1;
			
			pt.x=x3; pt.y=y3;//判断点(x3,y3)是否位于该封闭区域内
			b3=mCalF.InPolygon(polygon,M,pt);
			if(b3==true)//如果位于
				m_a3=1;
			
			//如果3点都位于内部或在封闭区域边界上,需要进一步判断
			//因为如果3点都位于封闭区域边界上,该三角形实际上是位于封闭区域之外 ,不需要删除
			if(m_a1==1 && m_a2==1 && m_a3==1)
			{
				pt.x=(x1+x2)/2; pt.y=(y1+y2)/2; //(x1,y1)和(x2,y2)中点坐标
				b1=mCalF.InPolygon(polygon,M,pt);//判断中点否位于该封闭区域内
				if(b1==false)//如果不位于
					m_a1=0;
				
				if(m_a1==1) //如果(x1,y1)和(x2,y2)中点坐标位于该封闭区域内
				{
					pt.x=(x2+x3)/2; pt.y=(y2+y3)/2;//(x2,y2)和(x3,y3)中点坐标
					b1=mCalF.InPolygon(polygon,M,pt);//判断中点否位于该封闭区域内
					if(b2==false)//如果不位于
						m_a2=0;
				}
				
				if(m_a1==1 && m_a2==1) //如果(x1,y1)和(x2,y2)以及(x2,y2)和(x3,y3)的中点坐标位于该封闭区域内
				{
					pt.x=(x1+x3)/2; pt.y=(y1+y3)/2;//(x1,y1)和(x3,y3)中点坐标
					b3=mCalF.InPolygon(polygon,M,pt);
					if(b3==false)//如果不位于
						m_a3=0;
				}
			} 
			
			
			
			if(m_a1+m_a2+m_a3>=3) //如果三角形的3点以及三边中点都位于封闭区域内, 则该三维形位于封闭区域内
			{
				return TRUE; //返回TURE,表示三角形位于封闭区域内,需要剔除
				break;
				
			}
		}
	}
	
	return FALSE;//返回FALSE,表示三角形不位于封闭区域内,不需要剔除
}

//得到地形块封闭区域信息
void CT3DSystemView::GetBlockInregion(int blockRow, int blockCol)
{
	int bInCur,bInPre;
	double minx,miny,maxx,maxy;
	double block_minx,block_miny,block_maxx,block_maxy;

	/*
					-------------(block_maxx,block_maxy)
					|        	|
					|        	|
					|        	|
					|        	|
					|        	|
					-------------
				(block_minx,block_miny)
	*/

	block_minx=(blockCol-1)*theApp.m_Dem_BlockWidth;//地形块最小x坐标
	block_maxx=blockCol*theApp.m_Dem_BlockWidth;//地形块最大x坐标
	block_miny=(blockRow-1)*theApp.m_Dem_BlockWidth;//地形块最小y坐标
	block_maxy=blockRow*theApp.m_Dem_BlockWidth;//地形块最大y坐标
	
	int Mnus;
	PCordinate pt;
	Point  edgePts[4];
	
	long iNdex=(blockRow-1)*theApp.m_BlockCols+(blockCol-1);//根据行列号计算地形块在所有地形块中的索引
	PBlockReginInforMe tempReginInforMe;
	
	for(long i=0;i<mRegions.GetSize();i++)
	{
		tempReginInforMe=new BlockReginInforMe;
		//如果
		//(1)当前封闭区域的最小x坐标>=地形块最小x坐标
		//(2)当前封闭区域的最大x坐标<=地形块最大x坐标
		//(3)当前封闭区域的最小y坐标>=地形块最小y坐标
		//(4)当前封闭区域的最大y坐标<=地形块最大y坐标
		///四个条件全满足时,表示该封闭子区域完全位于当前地形块内
		if(mRegions.GetAt(i)->minx>=block_minx &&\
			mRegions.GetAt(i)->maxx<=block_maxx&&\
			mRegions.GetAt(i)->miny>=block_miny &&\
			mRegions.GetAt(i)->maxy<=block_maxy)
		{
			//拷贝封闭区域数据点
			tempReginInforMe->ReginPts.Copy(mRegions.GetAt(i)->ReginPts);
			tempReginInforMe->minx=mRegions.GetAt(i)->minx;
			tempReginInforMe->miny=mRegions.GetAt(i)->miny;
			tempReginInforMe->maxx=mRegions.GetAt(i)->maxx;
			tempReginInforMe->maxy=mRegions.GetAt(i)->maxy;
			mBlockReginInforMe[iNdex].Add(tempReginInforMe);//
		}
		else 
		{
			//如果
			//(1)当前封闭区域的最小x坐标>地形块最大x坐标
			//(2)当前封闭区域的最大x坐标<地形块最小x坐标
			//(3)当前封闭区域的最小y坐标>地形块最大y坐标
			//(4)当前封闭区域的最大y坐标<地形块最小y坐标
			//四个条件满足其中之一时,是封闭子区域必不位于地形块内
			if(mRegions.GetAt(i)->minx>block_maxx ||
				mRegions.GetAt(i)->maxx<block_minx||\
				mRegions.GetAt(i)->miny>block_maxy ||
				mRegions.GetAt(i)->maxy<=block_miny)
			{
			//	Sleep(0);
			}
			else
			{
				bInCur=bInPre=1;
				
				for(long j=0;j<mRegions.GetAt(i)->ReginPts.GetSize();j++)
				{
					double x=mRegions.GetAt(i)->ReginPts.GetAt(j)->x;
					double y=-mRegions.GetAt(i)->ReginPts.GetAt(j)->z;
					double z=mRegions.GetAt(i)->ReginPts.GetAt(j)->y;
				    //如果点(x,y,z)位于地形块内(包括在地形块边界上)
					if(x>=block_minx &&	x<=block_maxx&&\
						y>=block_miny && y<=block_maxy)
					{
						bInCur=1; //标识封闭区域边界点Pi(x,y,z)位于地形块内
						
						if(j>0 && bInCur*bInPre==-1)
						{
							//计算封闭子区域与当前地形块四条边界线的交点坐标,求得的交点坐标
							//也将作为该地形块的数据点参与地形绘制.
							GetBlockEdgeJd(block_minx,block_miny,block_maxx,block_maxy,i,j,&Mnus,edgePts);
							for(int k=0;k<Mnus;k++)
							{
								pt=new Cordinate;
								pt->x=edgePts[k].x;pt->y=edgePts[k].y;pt->z=edgePts[k].z;
								tempReginInforMe->ReginPts.Add(pt);//记录数据点
							}
						}
						
						pt=new Cordinate;
						pt->x=x;pt->y=z;pt->z=-y;//坐标变换
						tempReginInforMe->ReginPts.Add(pt);//记录数据点
						
					}
					else 
					{
						bInCur=-1;//标识封闭区域边界点Pi(x,y,z)位于地形块外 
						if(j>0 && bInCur*bInPre==-1)
						{
							//计算封闭子区域与当前地形块四条边界线的交点坐标,求得的交点坐标
							//也将作为该地形块的数据点参与地形绘制.
							GetBlockEdgeJd(block_minx,block_miny,block_maxx,block_maxy,i,j,&Mnus,edgePts);
							for(int k=0;k<Mnus;k++) //将位于地形块内的部分边界点存储
							{
								pt=new Cordinate;
								pt->x=edgePts[k].x;pt->y=edgePts[k].y;pt->z=edgePts[k].z;
								tempReginInforMe->ReginPts.Add(pt);//记录数据点
							}
						}
						
					}
					bInPre=bInCur;
				}

				//计算最小最大x,y坐标
				minx=miny=999999999;
				maxx=maxy=-999999999;
				for(long k=0;k<tempReginInforMe->ReginPts.GetSize();k++)
				{
					if(minx>tempReginInforMe->ReginPts.GetAt(k)->x)
						minx=tempReginInforMe->ReginPts.GetAt(k)->x;
					if(miny>-tempReginInforMe->ReginPts.GetAt(k)->z)
						miny=-tempReginInforMe->ReginPts.GetAt(k)->z;
					if(maxx<tempReginInforMe->ReginPts.GetAt(k)->x)
						maxx=tempReginInforMe->ReginPts.GetAt(k)->x;
					if(maxy<-tempReginInforMe->ReginPts.GetAt(k)->z)
						maxy=-tempReginInforMe->ReginPts.GetAt(k)->z;
					
				}
				
				tempReginInforMe->minx=minx;
				tempReginInforMe->miny=miny;
				tempReginInforMe->maxx=maxx;
				tempReginInforMe->maxy=maxy;
				mBlockReginInforMe[iNdex].Add(tempReginInforMe);//记录封闭区域信息
			}
		}
	}
}

//写入地形块的坐标数据
void CT3DSystemView::WriteBlockLsPts(long Blcokrow, long Blcokcol)
{
	PBlockLSPts tempBlockLSPts=new BlockLSPts;
	
	tempBlockLSPts->ReginPts.RemoveAll();
	
	PCordinate pt=new Cordinate;
	double x,y,z;
	long INX,mTerrainBlockID;
	mTerrainBlockID=(Blcokrow-1)*theApp.m_BlockCols+Blcokcol-1;//地形块的ID号

    //根据地形块的分块大小,依次将所有地形数据点写入地形块封闭区域数组中
	for(int i=0;i<theApp.m_Dem_BlockSize;i++)
	{
		for (int j=0;j<theApp.m_Dem_BlockSize;j++)
		{
			
			x=(Blcokcol-1)*theApp.m_Dem_BlockWidth+j*theApp.m_Cell_xwidth;//转换后的X坐标
			y=(Blcokrow-1)*theApp.m_Dem_BlockWidth+i*theApp.m_Cell_ywidth;//转换后的Y坐标
			
			INX=i*theApp.m_Dem_BlockSize+j;
			z=m_pHeight_My[mTerrainBlockID][INX];//求得对应的高程值
			
            pt=new Cordinate;
			pt->x =x;
			pt->y=z;
			pt->z =-y;
			tempBlockLSPts->ReginPts.Add(pt);//加入数据点
		}
	}
	
	//加入封闭区域位于地形块或与地形块边界相交的数据点
	long iNdex=(Blcokrow-1)*theApp.m_BlockCols+(Blcokcol-1);
	for(int k=0;k<mBlockReginInforMe[iNdex].GetSize();k++)
	{
		tempBlockLSPts->ReginPts.Append(mBlockReginInforMe[iNdex].GetAt(k)->ReginPts);
	}
	
	tempBlockLSPts->mDemBlockRow=Blcokrow; //当前地形块对应的行号
	tempBlockLSPts->mDemBlockCol=Blcokcol; //当前地形块对应的殒号
	m_BlockLSPts.Add(tempBlockLSPts);//当前地形块的所有数据点
	
}

//计算封闭子区域与当前地形块四条边界线的交点坐标,求得的交点坐标
//也将作为该地形块的数据点参与地形绘制.
void CT3DSystemView::GetBlockEdgeJd(double block_minx, double block_miny, double block_maxx, double block_maxy, long RegionsIndex, long ReginPtsIndex, int *jdNus, Point edgePts[])
{
	Point p1,p2,q1,q2,crossPoint;
	
	int m=0; //初始交点数为0
	bool bCross=false; //初始不相交
	
	//当前封闭区域相临两边界点
	q1.x=mRegions.GetAt(RegionsIndex)->ReginPts.GetAt(ReginPtsIndex)->x;
	q1.y=-mRegions.GetAt(RegionsIndex)->ReginPts.GetAt(ReginPtsIndex)->z;
	q2.x=mRegions.GetAt(RegionsIndex)->ReginPts.GetAt(ReginPtsIndex-1)->x;
	q2.y=-mRegions.GetAt(RegionsIndex)->ReginPts.GetAt(ReginPtsIndex-1)->z;
	
	
	//地形块上边界线
	p1.x =block_minx;p1.y=block_maxy;
	p2.x =block_maxx;p2.y=block_maxy;
	
	//计算是p1p2直线与q1q2直线是否相交
	crossPoint =mCalF.GetCrossPoint(p1,p2,q1,q2,&bCross);
	if(bCross==true)  //如果相交,表明与地形块上边界线相交
	{
		edgePts[m].x=crossPoint.x; //记录交点x坐标
		edgePts[m].z=-crossPoint.y;//记录交点z坐标
		//从DEM中内插出高程,求得交点y坐标
		edgePts[m].y=m_demInsert.GetHeightValue(edgePts[m].x+theApp.m_DemLeftDown_x,-edgePts[m].z+theApp.m_DemLeftDown_y,2);
		m++; //交点数加1
	}
	
	//地形块左边界线
	p1.x =block_minx;p1.y=block_maxy;
	p2.x =block_minx;p2.y=block_miny;
	
	//计算是p1p2直线与q1q2直线是否相交
	crossPoint =mCalF.GetCrossPoint(p1,p2,q1,q2,&bCross);
	if(bCross==true) //如果相交,表明与地形块左边界线相交
	{
		edgePts[m].x=crossPoint.x;//记录交点x坐标
		edgePts[m].z=-crossPoint.y;//记录交点z坐标
		//从DEM中内插出高程,求得交点y坐标
		edgePts[m].y=m_demInsert.GetHeightValue(edgePts[m].x+theApp.m_DemLeftDown_x,-edgePts[m].z+theApp.m_DemLeftDown_y,2);
		m++;//交点数加1
	}
	
	//地形块下边界线
	p1.x =block_minx;p1.y=block_miny;
	p2.x =block_maxx;p2.y=block_miny;
	
	//计算是p1p2直线与q1q2直线是否相交
	crossPoint =mCalF.GetCrossPoint(p1,p2,q1,q2,&bCross);
	if(bCross==true) //如果相交,表明与地形块下边界线相交
	{
		edgePts[m].x=crossPoint.x;//记录交点x坐标
		edgePts[m].z=-crossPoint.y;//记录交点z坐标
		//从DEM中内插出高程,求得交点y坐标
		edgePts[m].y=m_demInsert.GetHeightValue(edgePts[m].x+theApp.m_DemLeftDown_x,-edgePts[m].z+theApp.m_DemLeftDown_y,2);
		m++;//交点数加1
	}
	
	//地形块右边界线
	p1.x =block_maxx;p1.y=block_maxy;
	p2.x =block_maxx;p2.y=block_miny;

	//计算是p1p2直线与q1q2直线是否相交
	crossPoint =mCalF.GetCrossPoint(p1,p2,q1,q2,&bCross);
	if(bCross==true)//如果相交,表明与地形块右边界线相交
	{
		edgePts[m].x=crossPoint.x;
		edgePts[m].z=-crossPoint.y;
		//从DEM中内插出高程,求得交点y坐标
		edgePts[m].y=m_demInsert.GetHeightValue(edgePts[m].x+theApp.m_DemLeftDown_x,-edgePts[m].z+theApp.m_DemLeftDown_y,2);
		m++;//交点数加1
	}
	
	*jdNus=m; //返回求得交点数		
}



//根据地形点的x,y 人材计算该地形点所属的在形块ID号
int CT3DSystemView::GetBlockID(double x, double y)
{
	//	theApp.m_BlockCols//DEM块总列数
	//	theApp.m_BlockRows;//DEM块总行数
	//	theApp.m_Dem_BlockWidth//每个DEM块宽度
	//	所属于行,列计算方法
	
	int mrow,mcol;
	if((y/theApp.m_Dem_BlockWidth)==(int)(y/theApp.m_Dem_BlockWidth))
		mrow=y/theApp.m_Dem_BlockWidth;
	else
		mrow=(int)(y/theApp.m_Dem_BlockWidth)+1;
	if(mrow<=0) //如果行号<=0,行号=1
		mrow=1;
	
	if((x/theApp.m_Dem_BlockWidth)==(int)(x/theApp.m_Dem_BlockWidth))
		mcol=x/theApp.m_Dem_BlockWidth;
	else
		mcol=(int)(x/theApp.m_Dem_BlockWidth)+1;
	if(mcol<=0)//如果列号<=0,列号=1
		mcol=1;
	int ID=(mrow-1)*theApp.m_BlockCols+mcol;
	return ID; //返回ID号
}

//设置飞行路径
void CT3DSystemView::OnPathManuinput() 
{
	m_ShowFlyPath=TRUE;  //标识是否显示飞行路径
	m_QueryType=SELECTFLYPATH;//进行飞行路径选择
	m_FlayPath.RemoveAll();//存储进行飞行路径坐标数组清空
}

//绘制飞行路径
void CT3DSystemView::DrawFlyPath()
{
	//如果显示飞行路径并且飞行路径坐标点数>1,才绘制飞行路径
	if(m_ShowFlyPath==TRUE  && m_FlayPath.GetSize()>1)//进行飞行路径选择
	{
		glPushMatrix(); //压入矩阵堆栈
		glDisable(GL_TEXTURE_2D);//关闭纹理(即飞行路径不采用纹理)
		glLineWidth(3.0);//设置飞行路径线宽
		glColor3f(0,1,1); //设置飞行路径颜色
		if(m_ViewType==GIS_VIEW_ORTHO)	//绘制正射投影模式下的飞行路径
		{
			glBegin(GL_LINE_STRIP); //以折线方式绘制飞行路径
			for(int i=0;i<m_FlayPath.GetSize();i++)
				glVertex2f(GetOrthoX(m_FlayPath.GetAt(i)->x),GetOrthoY(-m_FlayPath.GetAt(i)->z));
			glEnd();
			
			//在飞行路径每个坐标点处绘制点圆加以标识每个坐标点
			for(i=0;i<m_FlayPath.GetSize();i++)
			{
				glColor3f(0,0.5,0.5); //点的颜色
				glPointSize(4.0); //点的大小
				glBegin(GL_POINTS);
					glVertex2f(GetOrthoX(m_FlayPath.GetAt(i)->x),GetOrthoY(-m_FlayPath.GetAt(i)->z));
				glEnd();
			}
			glPointSize(0); //恢复点的默认大小
			
		}
		else  if(m_ViewType==GIS_VIEW_PERSPECTIVE) //绘制透视投影模式下的飞行路径
		{
			glBegin(GL_LINE_STRIP);
			for(int i=0;i<m_FlayPath.GetSize();i++)
				glVertex3f(m_FlayPath.GetAt(i)->x, m_FlayPath.GetAt(i)->y, m_FlayPath.GetAt(i)->z);
			glEnd();
			
		}
		glEnable(GL_TEXTURE_2D); //开启纹理
		glLineWidth(1.0); //恢复线宽
		glColor3f(1,1,1); //恢复颜色
		glPopMatrix(); //弹出矩阵堆栈
	}
}

//路径坐标插值
void CT3DSystemView::OnFlppathInterpolation() 
{
	float m_InsertDdis=10; //插值间距 
	
	double x1,y1,z1,x2,y2,z2;
	PCordinate ppt ;  
	
	m_FlayPathTempPts.RemoveAll(); //临时存储飞行路径
	for(int i=0;i<m_FlayPath.GetSize()-1;i++)
	{
		x1=m_FlayPath.GetAt(i)->x; //获取飞行路径当前点的x坐标
		y1=m_FlayPath.GetAt(i)->y; //获取飞行路径当前点的y坐标
		z1=m_FlayPath.GetAt(i)->z; //获取飞行路径当前点的z坐标
		
		x2=m_FlayPath.GetAt(i+1)->x; //获取飞行路径下一点的x坐标
		y2=m_FlayPath.GetAt(i+1)->y;//获取飞行路径下一点的y坐标
		z2=m_FlayPath.GetAt(i+1)->z;//获取飞行路径下一点的z坐标
		
		if(i==0) //如果是飞行路径的起始点,则记录
		{
			ppt = new Cordinate;
			ppt->x=x1;
			ppt->y=y1;
			ppt->z=z1;
			m_FlayPathTempPts.Add(ppt);
		}
		
		//计算飞行路径当前点与下一点的距离
		double L=myDesingScheme.GetDistenceXYZ(x1,y1,z1,x2,y2,z2);
		int M=L/m_InsertDdis; //计算应内插的坐标点数
		for(int j=1;j<=M;j++)
		{
			//线性内插计算出新的内插点的三维坐标
			ppt = new Cordinate;
			ppt->x=x1+j*m_InsertDdis/L*(x2-x1);
			ppt->z=z1+j*m_InsertDdis/L*(z2-z1);
			ppt->y=m_demInsert.GetHeightValue(ppt->x+theApp.m_DemLeftDown_x,-ppt->z+theApp.m_DemLeftDown_y,2);
			m_FlayPathTempPts.Add(ppt); //记录内插点坐标
			
		}
		
		ppt = new Cordinate;
		ppt->x=x2;
		ppt->y=y2;
		ppt->z=z2;
		m_FlayPathTempPts.Add(ppt); //将飞行路径下一点的坐标也记录在内
		
	}
	
	m_FlayPath.RemoveAll(); //飞行路径数组清空
	for(i=0;i<m_FlayPathTempPts.GetSize();i++)
	{
		ppt = new Cordinate;
		ppt->x=m_FlayPathTempPts.GetAt(i)->x;
		ppt->y=m_FlayPathTempPts.GetAt(i)->y;
		ppt->z=m_FlayPathTempPts.GetAt(i)->z;
		m_FlayPath.Add(ppt); //重新填充飞行路径数组
		
	}
	OnDraw(GetDC()); //刷新三维场景
	MessageBox("路径坐标插值完成!","路径坐标插值",MB_ICONINFORMATION);	
}

//保存飞行路径
void CT3DSystemView::OnFlypathSave() 
{
	CString 	NeededFile;
	char 		FileFilter[] = "飞行路径(*.pth)|*.pth||";

	//设置文件对话框属性
	DWORD 		FileDialogFlag = OFN_EXPLORER | OFN_PATHMUSTEXIST | OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT;
	CFileDialog FileDialogBoxFile(FALSE, NULL, 0, FileDialogFlag, FileFilter, this);
	FileDialogBoxFile.m_ofn.lpstrTitle = "保存飞行路径文件";
	char		FileName[200];
	
	CString tt[3];
	if( FileDialogBoxFile.DoModal() == IDOK ) //如果对话框成果打开
	{	
		NeededFile = FileDialogBoxFile.GetPathName(); //得到文件名
		sprintf(FileName, "%s", NeededFile);
		if(strcmp(FileDialogBoxFile.GetFileExt(),"pth")!=0) 
			strcat(FileName,".pth"); //添加飞行路径文件扩展名
		
		if(FlyPathSave(FileName)) //如果飞行路径保存成功
			MessageBox("路径点保存完毕","保存飞行路径",MB_ICONWARNING);
		
	}			
}

//飞行路径保存
int CT3DSystemView::FlyPathSave(char *pathfile)
{
	FILE	*fpw;
	char	message[200];
	
	if((fpw = fopen(pathfile, "w")) == NULL)//如果写入文件失败
  	{ 
		sprintf(message, "文件 %s 创建无效", pathfile);
		MessageBox(message,"保存飞行路径坐标到文件",MB_ICONWARNING);
		return 0; //返回失败
  	}
	
	
	fprintf(fpw, "%d\n", m_FlayPath.GetSize());//写入飞行路径坐标点总数
	for(int i=0;i<m_FlayPath.GetSize();i++)
	{
		//向文件fpw国写入飞行路径坐标点的三维坐标
		fprintf(fpw, "%lf,%lf,%lf\n",m_FlayPath.GetAt(i)->x, m_FlayPath.GetAt(i)->y, m_FlayPath.GetAt(i)->z);
		
	}
	
	fclose(fpw); //关闭文件
	
	return 1;  //返回成功
}

//打开飞行路径
void CT3DSystemView::OnFlyOpenpath() 
{
	CString 	NeededFile;
	char 		FileFilter[] = "飞行路径(*.pth)|*.pth||";
	//设置文件对话框属性
	DWORD 		FileDialogFlag = OFN_EXPLORER | OFN_PATHMUSTEXIST | OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT;
	CFileDialog FileDialogBoxFile(TRUE, NULL, 0, FileDialogFlag, FileFilter, this);
	FileDialogBoxFile.m_ofn.lpstrTitle = "打开飞行路径文件";
	char		FileName[200];
	
	CString tt[3];
	if( FileDialogBoxFile.DoModal() == IDOK )//如果对话框成果打开
	{	
		NeededFile = FileDialogBoxFile.GetPathName();//得到文件名
		sprintf(FileName, "%s", NeededFile);
		if(strcmp(FileDialogBoxFile.GetFileExt(),"pth")!=0) 
			strcat(FileName,".pth");//添加飞行路径文件扩展名
		
		if(FlyPathRead(FileName)) //读取飞行路径文件数据动态数组中
			MessageBox("打开路径点完毕","提示",MB_ICONWARNING);
		if(m_FlayPath.GetSize()>1) //如果飞行路径数据坐标点数>1
		{
			m_ShowFlyPath=TRUE; //显示飞行路径
			m_PathFlag=TRUE; //标识飞行路径打开成功
		}
		else
			m_PathFlag=FALSE;//标识飞行路径打开失败
	}			
}

//读取飞行路径文件数据动态数组中
int CT3DSystemView::FlyPathRead(char *pathfile)
{
	CString tt,m_strszLine;
	PCordinate ppt = new Cordinate;  
	
	m_FlayPath.RemoveAll(); //飞行路径数组清空
	
	CStdioFile m_inFile;	
			
	if(m_inFile.Open (pathfile,CFile::modeRead)==FALSE) //打开文件
	{
		return 0; //返回失败标志
	}
	m_inFile.ReadString(m_strszLine); //读取飞行路径坐标点总数
	while( m_inFile.ReadString(m_strszLine))
	{
		ppt = new Cordinate;
		
		m_strszLine.TrimLeft(" ");
		m_strszLine.TrimRight("	");
		int nPos=m_strszLine.Find(",");
		tt=m_strszLine.Left(nPos);
		ppt->x=atof(tt);
		m_strszLine=m_strszLine.Right(m_strszLine.GetLength()-nPos-1);
		nPos=m_strszLine.Find(",");
		tt=m_strszLine.Left(nPos);
		ppt->y=atof(tt); 
		m_strszLine=m_strszLine.Right(m_strszLine.GetLength()-nPos-1);
		ppt->z=atof(m_strszLine);
		m_FlayPath.Add(ppt); //记录飞行路径坐标点
		
	}
	m_inFile.Close(); //关闭文件
	
	return 1; //返回成功标志
}

//显示或关闭飞行路径
void CT3DSystemView::OnFlyOnoffpath() 
{
	if(m_ShowFlyPath==TRUE) //如果当前是显示飞行路径
		m_ShowFlyPath=FALSE; //标识设置为FALSE
	else
		m_ShowFlyPath=TRUE; //反之,设置为TRUE	
	OnDraw(GetDC());  //刷新三维场景 
	
}

//根据m_ShowFlyPath值修改菜单文本
void CT3DSystemView::OnUpdateFlyOnoffpath(CCmdUI* pCmdUI) 
{
	if(m_ShowFlyPath==TRUE) //如果当前是显示飞行路径
		pCmdUI->SetText("关闭飞行路径"); //将菜单名称设置为"关闭飞行路径"
	else   //如果当前是关闭飞行路径
		pCmdUI->SetText("显示飞行路径");//将菜单名称设置为"显示飞行路径"
}

//按固定高度漫游
void CT3DSystemView::OnFlyStaticheight() 
{
	if(m_FlayPath.GetSize()<=0) //如果飞行路径坐标点数量<=0，即飞行路径为空
	{
		MessageBox("没有输入路径文件","飞行浏览",MB_ICONWARNING);
		return;
	}
	
	m_R=4000;
	m_r=3000;
	
	m_FlyHeightType = GIS_FLY_STATICHEIGHT; //设置漫游类型为固定高度漫游
	m_StaticHeight = (m_maxHeight+m_minHeight)/4.0;  //取固定高度值=地形最大和最小高度的1/4
	m_flypathPtIndex=0; //飞行路径坐标索引=0
	
	SetFLyTimer();	//设置三维漫游计时器
}

void CT3DSystemView::OnUpdateFlyStaticheight(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable(	m_PathFlag == TRUE); //根据是否具有有效飞行路径值设置菜单状态
	if (m_FlyHeightType==GIS_FLY_STATICHEIGHT) //如果当前是"沿固定高度漫游"方式
        pCmdUI->SetCheck(TRUE); //菜单前设置选中标志"√"
    else
		pCmdUI->SetCheck(FALSE); //否则不设置		
}

//设置飞行计时器
void CT3DSystemView::SetFLyTimer()
{
	SetTimer(1,m_flyspeed,0); //m_flyspeed:飞行计时器时间间隔,
	
}

//计时器
void CT3DSystemView::OnTimer(UINT nIDEvent) 
{
	switch(nIDEvent)
	{
	case 1:  //三维漫游
		if(m_flypathPtIndex<=m_FlayPath.GetSize()-2) //如果当前飞行路径坐标点索引<=路径坐标点总数-1
		{
			//根据漫游路径相临坐标点计算相机各参数
			GetCameraCorrdinate(
				m_FlayPath.GetAt(m_flypathPtIndex)->x,\
				m_FlayPath.GetAt(m_flypathPtIndex)->y,\
				m_FlayPath.GetAt(m_flypathPtIndex)->z,\
				m_FlayPath.GetAt(m_flypathPtIndex+1)->x,\
				m_FlayPath.GetAt(m_flypathPtIndex+1)->y,\
				m_FlayPath.GetAt(m_flypathPtIndex+1)->z
				);
			if(m_ifZoomonRoad==TRUE)//如果沿线路方案漫游
			{
				//计算当前里程
				CString tt,tt2;
				tt=myDesingScheme.GetLC(m_FlayPath.GetAt(m_flypathPtIndex)->Lc);
				tt2="当前里程="+tt;
				CMainFrame *pMainFrame=(CMainFrame*)AfxGetApp()->m_pMainWnd;
				pMainFrame->Set_BarText(5,tt2,0); 
				CalZoomSpeed(m_FlayPath.GetAt(m_flypathPtIndex)->Lc);
			}
			else
			{
				double L=myDesingScheme.GetDistenceXYZ(
					m_FlayPath.GetAt(m_flypathPtIndex)->x,\
					m_FlayPath.GetAt(m_flypathPtIndex)->y,\
					m_FlayPath.GetAt(m_flypathPtIndex)->z,\
					m_FlayPath.GetAt(m_flypathPtIndex+1)->x,\
					m_FlayPath.GetAt(m_flypathPtIndex+1)->y,\
					m_FlayPath.GetAt(m_flypathPtIndex+1)->z
					);
					m_CurZooomLC+=L;
					CalZoomSpeed(m_CurZooomLC);
			}
			OnDraw(GetDC()); //刷新三维场景
			m_flypathPtIndex++; //飞行路径当前坐标索引号+1
		}

		else
		{
			m_flypathPtIndex=0; //到了飞行尾,将飞行路径当前坐标索引号重置为0,即从飞行路径起始点开始漫游
		}
		break;
	case 2: //录制图像序列
		RecordPictures();
		break;
	case 3:
	//	m_snows.DrawSnow ();
		break;
	}
	
	CView::OnTimer(nIDEvent);
}

//按相对高度漫游方式
void CT3DSystemView::OnFlyRoutineheight() 
{
	if(m_FlayPath.GetSize()<=0)//如果飞行路径坐标点数量<=0，即飞行路径为空
	{
		MessageBox("没有输入路径文件","飞行浏览",MB_ICONWARNING);
		return;
	}
	
	m_FlyHeightType = GIS_FLY_PATHHEIGHT;//设置漫游类型为相对高度漫游
	m_R=3000;
	m_r=2000;
	
	if(m_ifZoomonRoad==TRUE) 
		m_StaticHeight =5;
	else
		m_StaticHeight =80; //设置相对高差值
	
	m_flypathPtIndex=0;//飞行路径坐标初始索引=0
	SetFLyTimer();	//设置三维漫游计时器
}

//设置菜单是否设置选中标识"√"
void CT3DSystemView::OnUpdateFlyRoutineheight(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable(	m_PathFlag == TRUE); //根据是否具有有效飞行路径值设置菜单状态
	if (m_FlyHeightType==GIS_FLY_PATHHEIGHT) //如果当前是"沿相对高度漫游"方式
        pCmdUI->SetCheck(TRUE); //菜单前设置选中标志"√"
    else
		pCmdUI->SetCheck(FALSE); //否则不设置	
}

//开始/暂停漫游
void CT3DSystemView::OnFlyPlaypause() 
{
	if(m_FlyPause==FALSE) //如果不是暂停漫游,即处于漫游状态
	{
		m_FlyPause=TRUE; //设置暂停标识m_FlyPause=TRUE
		KillTimer(1); //销毁定时器1
	}
	else //如果处于漫游状态
	{
		m_FlyPause=FALSE; //设置暂停标识m_FlyPause=FALSE
		SetFLyTimer();//设置飞行时间器,继续漫游
	}
}

//根据m_FlyPause值设置菜单ID_FLY_PLAYPAUSE文本
void CT3DSystemView::OnUpdateFlyPlaypause(CCmdUI* pCmdUI) 
{
	if(m_FlyPause==TRUE) //如果处于漫游状态
		pCmdUI->SetText("开始漫游"); //设置菜单ID_FLY_PLAYPAUSE文本
	else  //如果不是暂停漫游,即处于漫游状态
		pCmdUI->SetText("暂停漫游");//设置菜单ID_FLY_PLAYPAUSE文本
	
}

//停止漫游
void CT3DSystemView::OnFlyStop() 
{
	KillTimer(1); //销毁定时器1
	m_flypathPtIndex=0;//飞行路径坐标索引=0
	m_FlyPause=FALSE; //暂停标识为FALSE
	m_ifZoomonRoad=FALSE; //标识沿线路方案线漫游为FALSE
	m_far=10000;	//恢复gluPerspective()函数定义平截头体的远剪裁平面的距离
}

//单步前进
void CT3DSystemView::OnFlyOnestep() 
{
	//如果飞行路径坐标索引>=0并且<飞行路径坐标总数-1,该条件表示的是
	//只要飞行路径坐标索引没有到飞行路径尾,就可以执行单步前进
	if(m_flypathPtIndex>=0 && m_flypathPtIndex<m_FlayPath.GetSize()-1)
	{
		
		KillTimer(1);	//销毁定时器1		
		m_FlyPause=TRUE;//暂停标识为TRUE
		//根据漫游路径相临坐标点计算相机各参数
		GetCameraCorrdinate(
			m_FlayPath.GetAt(m_flypathPtIndex)->x,\
			m_FlayPath.GetAt(m_flypathPtIndex)->y,\
			m_FlayPath.GetAt(m_flypathPtIndex)->z,\
			m_FlayPath.GetAt(m_flypathPtIndex+1)->x,\
			m_FlayPath.GetAt(m_flypathPtIndex+1)->y,\
			m_FlayPath.GetAt(m_flypathPtIndex+1)->z
			);
		
		OnDraw(GetDC()); //刷新三维场景 
		m_flypathPtIndex++; //当前行路径坐标索引+1
	}	
}

//飞行视野扩大
void CT3DSystemView::OnFlyViewEnlarge() 
{
	m_ViewWideNarrow += 5.0; //m_ViewWideNarrow值增加
	OnDraw (GetDC()); //刷新三维场景
}

//飞行视野减小
void CT3DSystemView::OnFlyViewSmall() 
{
	m_ViewWideNarrow -= 5.0;//m_ViewWideNarrow值减小
	OnDraw (GetDC());//刷新三维场景
}

//飞行高度增加
void CT3DSystemView::OnFlyHeightUp() 
{
	m_StaticHeight += 8;// 高度值增加(步长=8,可任意设定)
	OnDraw (GetDC());//刷新三维场景	
}

//飞行高度降低
void CT3DSystemView::OnFlyHeightDown() 
{
	m_StaticHeight -= 8;// 高度值增加(步长=8,可任意设定)
	OnDraw (GetDC());//刷新三维场景	
	
}

//飞行视角上倾(仰视)
void CT3DSystemView::OnFlyViewUp() 
{
	m_ViewUpDown += 1.0;
	OnDraw (GetDC());//刷新三维场景	
}

//飞行视角下倾(俯视)
void CT3DSystemView::OnFlyViewDown() 
{
	m_ViewUpDown -= 1.0;
	OnDraw (GetDC());//刷新三维场景	
}

//加速
void CT3DSystemView::OnFlySpeedUp() 
{
	m_flyspeed-=2; //飞行时的计时器时间间隔减少 
	if(m_flyspeed<=1) //如果计时器时间间隔<=1,则有
		m_flyspeed=1;
	SetFLyTimer();	//设置飞行计时器
}

//减速
void CT3DSystemView::OnFlySpeedDown() 
{
	m_flyspeed+=2;//飞行时的计时器时间间隔增加 
	SetFLyTimer();//设置飞行计时器
}

//三维漫游调整热键帮助
void CT3DSystemView::DisplayHelp()
{
	char str[20][50],strdis[2000];
	
	strdis[0]='\0';
	
	strcpy(str[0]," 向↑键   往前方向移动\n");
	strcpy(str[1]," 向↓键   往后方向移动\n");
	strcpy(str[2]," 向→键   往左方向移动\n");
	strcpy(str[3]," 向←键   往右方向移动\n");
	strcpy(str[4],"  J 键    飞行加速\n");
	strcpy(str[5],"  M 键    飞行减速\n");
	strcpy(str[6],"  F 键    飞行视野增大\n");
	strcpy(str[7],"  V 键    飞行视野减小\n");
	strcpy(str[8],"  G 键     升高飞行高度\n");
	strcpy(str[9],"  B 键     降低飞行高度\n");
	strcpy(str[10],"  H 键    飞行观察上倾\n");
	strcpy(str[11],"  N 键    飞行观察下倾\n");
	strcpy(str[12],"  Z 键    沿方案线漫游\n");
	strcpy(str[13],"  P 键    开始/暂停漫游\n");
	strcpy(str[14],"  S 键    停止漫游");
	
	for(int i=0;i<15; i++)
		strcat(strdis,str[i]);
	
	MessageBox(strdis,"三维漫游热键说明",MB_OK);
}

//桥墩模型
void CT3DSystemView::OnMenuModleqd() 
{
	CModelMangerQD dlg;
	if(dlg.DoModal()==IDOK) //如果对话框以IDOK方式关闭
	{
		if(!dlg.m_3DSfilename_QD.IsEmpty()) //如果桥墩模型名称不为空
		{
			m_3DSfilename_QD=dlg.m_3DSfilename_QD; //得到桥墩模型名称
			BuildQDModelList(); //重新构建桥墩模型显示列表
			
		}
	}	
}

//输出线路三维模型到CAD
void CT3DSystemView::OnMenuOuptcad3dlinemodel() 
{
	CString tt,DxfFilename;
	
	CDXF mdxf;
	CStdioFile *Dxffile=new CStdioFile;
	CFileDialog fielDlg(FALSE,NULL,NULL,OFN_HIDEREADONLY|OFN_OVERWRITEPROMPT,"AutoCAD Dxf(*.dxf)|*dxf||",NULL);
	
	fielDlg.m_ofn.lpstrTitle = "输出线路三维模型到CAD";   
	
	if(fielDlg.DoModal()==IDCANCEL) //打开对话框
		return;
	
	DxfFilename=fielDlg.GetPathName(); //得到DXF文件名
	if(DxfFilename.IsEmpty()) //如果DXF文件名为空，返回
		return;

	//确保DXF文件以".dxf"扩展名结尾
	tt=DxfFilename.Right(4);
	tt.MakeUpper();
	if(strcmp(tt,".DXF")!=0)
		DxfFilename+=".dxf";
	
	//如果新创建的DXF文件已经打开，给出错信息	 
	if(Dxffile->Open (DxfFilename,CFile::modeCreate | CFile::modeWrite)==FALSE)
	{
		this->MessageBox("文件"+DxfFilename+"已被其它程序打开或占用,请关闭再试!","输出线路三维模型到CAD",MB_ICONINFORMATION);
		return;
	}

	mdxf.DxfHeader(Dxffile); //写入DXF文件头
	mdxf.DxfLineType(Dxffile);//写入DXF线型定义
	mdxf.DxfBeginDrawEnties(Dxffile); //开始写入图形实体

	//写入线路中心线部分
	for (long i=0;i<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		
		mdxf.DxfDraw_Line(Dxffile,"线路中心线",1,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x,\
			-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->z,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->y,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->x,\
			-myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->z,\
			myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i+1)->y);
	}	

	//写入两侧轨道间的连线部分
	for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		mdxf.DxfDraw_Line(Dxffile,"两侧轨道间的连线",6,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧轨道间的连线",6,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧轨道间的连线",6,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧轨道间的连线",6,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1);
	}		

	//写入两侧碴肩至碴脚间的连线部分
	for (i=0;i<myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		
	
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y1);
		
		
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴肩至碴脚间的连线",3,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_Railway3D[m_currentSchemeIndexs].GetAt(i)->y2);
		

	}
	
	//写入两侧碴脚至路肩间的连线部分
	for (i=0;i<myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetSize()-1;i++)
	{
	
		
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x1,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z1,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y1);
		
		
		
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i+1)->y2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2);
		mdxf.DxfDraw_Line(Dxffile,"两侧碴脚至路肩间的连线",4,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLj3D[m_currentSchemeIndexs].GetAt(i)->y2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->x2,\
			-myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->z2,\
			myDesingScheme.PtS_RailwayLjToBP3D[m_currentSchemeIndexs].GetAt(i)->y2);
		
	}		
	
	//写入左侧边坡部分(隧道和桥梁不写入)
	for (i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		tt=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
		if(tt!="隧道起点" && tt!="隧道中间点" &&tt!="隧道终点" &&tt!="桥梁起点"  &&tt!="桥梁中间点" &&tt!="桥梁终点")
		{	
			DrawBP_CAD(i,1,Dxffile);//左侧边坡
		
		}
	}
	
	//写入右侧边坡和两侧水沟部分(隧道和桥梁不写入)
	for (i=0;i<myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetSize()-1;i++)
	{
		tt=myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->strJDStyle;
		if(tt!="隧道起点" && tt!="隧道中间点" &&tt!="隧道终点" &&tt!="桥梁起点"  &&tt!="桥梁中间点" &&tt!="桥梁终点")
		{
			DrawBP_CAD(i,2,Dxffile); //右侧边坡
			DrawSuiGou_CAD(i,2,Dxffile);//两侧水沟
		
		}
	}
	
	mdxf.DxfSectionEnd(Dxffile); //写入文件结束标志
	tt.Format("dxf文件%s已输出完成!",DxfFilename);
	MessageBox(tt,"输出图形到AuotCAD",MB_ICONINFORMATION); //给出DXF文件输出完成信息
		
}

//写入两侧边坡部分到DXF文件中(隧道和桥梁不写入)
void CT3DSystemView::DrawBP_CAD(long index, int BPside, CStdioFile *Dxffile)
{
	long i=index;

	int j;


	if(BPside==1) 
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_L;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_L;
	
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				
				
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{

					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y);
				}
				else 
				{
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j+1].Hp[2].y);
				}
			
	
				if(j>0) 
					{
						glBindTexture(GL_TEXTURE_2D, m_cTxturePT.GetTxtID());
						mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y);
						mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y);
						mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
						mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
							-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
							myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
						
				}
	
			}
			
		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				glBindTexture(GL_TEXTURE_2D, m_cTxtureBP.GetTxtID());
				
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].style)
				{
 
 					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y);
					
				}
				else
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y);

 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j+1].Hp[2].y);
					
					
				}
			

				if(j>0) 
				{
 
 					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_L[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_L[j].Hp[1].y);
					
				}

			}
			
		}
		
	}
	else if(BPside==2) 
	{
		
		int N1=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->Huponums_R;
		int N2=myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->Huponums_R;
		if(N1<=N2 && N1>0 && N2>0)
		{
			for(j=0;j<N1;j++)
			{
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x<myDesingScheme.PtS_3DLineZX[m_currentSchemeIndexs].GetAt(i)->x)
					{
					}
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y);
					
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y);					
				}
				else
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y);
					
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j+1].Hp[2].y);
					
				}
				
				if(j>0) 
				{
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					
				}
			}
		}
		else 
		{
			for(j=0;j<N2;j++)
			{
				if(myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].style==\
					myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].style)
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y);
					
				}
				else
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y);
					

					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[2].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j+1].Hp[2].y);
					
				}
				
				if(j>0) 
				{
 
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[0].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y);
					mdxf.DxfDraw_Line(Dxffile,"路基边坡线",2,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i+1)->HuPo_R[j].Hp[1].y,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].x,\
						-myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].z,\
						myDesingScheme.PtS_HuPo[m_currentSchemeIndexs].GetAt(i)->HuPo_R[j].Hp[1].y);
					
				}
			}

		}
		
	}
}

void CT3DSystemView::DrawSuiGou_CAD(long index, int BPside, CStdioFile *Dxffile)
{
	float SGLL=2.8;
	int i;
	
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_L==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_L==TRUE)
	{
		
		for( i=0;i<=5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y);
		}
		for( i=0;i<5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i+1].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i+1].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouL[i+1].y);
		}
		
		for( i=0;i<5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i+1].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i+1].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouL[i+1].y);
		}
		
		
	}
	
	
	if(myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->bhaveSuiGou_R==TRUE\
		&& myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->bhaveSuiGou_R==TRUE)
	{
		for( i=0;i<=5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y);
		}
		for( i=0;i<5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i+1].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i+1].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index)->SuiGouR[i+1].y);
		}
		
		for( i=0;i<5;i++)
		{
			
			mdxf.DxfDraw_Line(Dxffile,"水沟",5,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i].y,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i+1].x,\
				-myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i+1].z,\
				myDesingScheme.PtS_PaiSuiGou[m_currentSchemeIndexs].GetAt(index+1)->SuiGouR[i+1].y);
		}
		
	}
}

//动画录制参数设置
void CT3DSystemView::OnAviParameter() 
{
	CRect rect;
	this->GetClientRect(&rect); //得到客户区大小不
	
	CAviParameter	 dlg;
	

	//确保AVI宽度和高度有效
    if(m_MovieWidth<=0 || m_MovieHeight<=0)
	{
		m_MovieWidth=rect.right; //AVI的宽度
		m_MovieHeight=rect.bottom;//AVI的高度
	}
	
	
	dlg.m_MoviemaxWidth=rect.right;   //AVI的最大宽度
	dlg.m_MoviemaxHeight=rect.bottom; //AVI的最大高度
	
	dlg.m_AviFilename=m_AviFilename;  //AVI文件名
	dlg.m_AVIFrame=m_AVIFrame;//AVI文件录制频率
	
	dlg.m_MovieWidth=m_MovieWidth; //AVI的宽度
    dlg.m_MovieHeight=m_MovieHeight; //AVI的高度 
	
	if(dlg.DoModal ()==IDOK)  
	{
		m_AviFilename=dlg.m_AviFilename; //AVI文件名
		m_AVIFrame=dlg.m_AVIFrame; //AVI文件录制频率
		m_MovieWidth=dlg.m_MovieWidth;  //AVI的宽度
		m_MovieHeight=dlg.m_MovieHeight; //AVI的高度 
	}	
}



//开始录制动画
void CT3DSystemView::OnAviStart() 
{
	if(m_Beginrecording==TRUE)  //如果开始录制动画标识m_Beginrecording=TRUE,表示已经正在录制动画,返回
	{
		recordBegin(); //记录开始录制动画
		return; //返回
	}
	
	//打开AVI保存文件
	CFileDialog fd(FALSE,"avi",0,OFN_OVERWRITEPROMPT|OFN_HIDEREADONLY|OFN_PATHMUSTEXIST,"Microsoft AVI (*.avi)|*.avi||");
	
	CDC *dc=GetDC();
	wglMakeCurrent( dc->m_hDC, m_hRC );//将RC与当前DC关联
	
	CRect rect;
	GetClientRect(&rect); //得到客户工大
	
	//确保AVI宽度和高度有效
	if(m_MovieWidth<=0 ||m_MovieHeight<=0) 
	{
		m_MovieWidth=rect.right;
		m_MovieHeight=rect.bottom;
	}
	
	BeginWaitCursor(); //显示沙漏光标
    bool success = false;

	//将捕捉到的数据写入AVI文件中
	success = BeginMovieCapture(m_AviFilename,m_MovieWidth,m_MovieHeight,m_AVIFrame);
	if (!success)
    {
        MessageBox("不能录制动画!", "录制动画", MB_OK | MB_ICONERROR);
		return;
    }
	
	if (m_movieCapture != NULL)
    {
        if (isRecording())
            OnAviPause(); //暂停录制动画
        else
            recordBegin(); //开始录制动画
    }
	
    EndWaitCursor();//结束沙漏光标,恢复正常光标
	
	wglMakeCurrent( 0, 0 );//释放DC和RC 
	ReleaseDC(dc);	//释放dc		
}

//开始捕捉
bool CT3DSystemView::BeginMovieCapture(CString filename, int width, int height, float framerate)
{
	m_movieCapture = new CAVICapture();
	//设置AVI文件名称、录制帧的大小、录制帧率、AVI文件压缩方式等信息
    bool success = m_movieCapture->start(filename, width, height, framerate);
    if (success) //如果设置成功
        initMovieCapture(m_movieCapture); //初始化m_movieCapture变量
    else
        delete m_movieCapture; //如果失败,删除m_movieCapture
    
    return success;
}

//初始化m_movieCapture变量
void CT3DSystemView::initMovieCapture(CAVICapture *mc)
{
	if (m_movieCapture == NULL)
        m_movieCapture = mc;
	
}

//获取是否在录制动画
bool CT3DSystemView::isRecording()
{
	return m_Recording;
	
}

//如果m_movieCapture不为NULL,初始化成功,则设置录制标识=true
void CT3DSystemView::recordBegin()
{
	if (m_movieCapture != NULL) //如果m_movieCapture不为NULL
        m_Recording = true;
	
}

//暂停录制动画
void CT3DSystemView::OnAviPause() 
{
	m_Recording = false;
}

//结束录制动画
void CT3DSystemView::OnAviEnd() 
{
	if (m_movieCapture != NULL) //如果m_movieCapture 不为空,表示已捕捉数据
	{
        recordEnd(); //结束录制动画		
	}		
}

//结束录制动画
void CT3DSystemView::recordEnd()
{
	if (m_movieCapture != NULL) //如果m_movieCapture 不为空,表示已捕捉数据
    {
        OnAviPause();//暂停,将捕捉的数据不再写入文件
        m_movieCapture->end();//结束录制
        delete m_movieCapture; //删除m_movieCapture(里面包含录制的数据)
        m_movieCapture = NULL; //重新设置m_movieCapture为空
    }
}

//设置图像采集频率
void CT3DSystemView::OnSavepictureSpeed() 
{
	CRecordPictureSpeed	 dlg;
	dlg.m_recordPictSpeed=m_recordPictSpeed;//当前所设置的采集图像频率
	if(dlg.DoModal ()==IDOK) //如果对话框以IDOK方式关闭
	{
		m_recordPictSpeed=dlg.m_recordPictSpeed; //得到新设置的采集图像频率
	}			
}


//录像(图像序列)
void CT3DSystemView::OnSavepictureContinue() 
{
	m_RecordPicture=TRUE;  //录像图像序列的标志为TRUE
	SetPictureName(); //设置录制图像时的初始文件名称
	SetTimer(2,m_recordPictSpeed,NULL);	 //设置录制图像计时器
	
}

//设置录制图像时的初始文件名称，后面采集的图像文件名均以此初始文件名+录制序号组成
void CT3DSystemView::SetPictureName()
{
	CFileDialog FileDialog( FALSE, _T("bmp"), _T("*.bmp"), OFN_HIDEREADONLY,
		_T("Windows Bitmap Files (*.bmp)|*.bmp|All Files (*.*)|*.*||"), NULL );
	
	if(FileDialog.DoModal() == IDOK) //打开文件对话框
	{
		m_RecordPicturename = FileDialog.GetPathName(); //得到图像文件名
		
	}	
}

//录制图像序列
void CT3DSystemView::RecordPictures()
{
	
	CString str,str1,fname;
	
	str.Format("%s",m_RecordPicturename);
	int n=str.GetLength ();
	str=str.Left (n-4);
	m_PictureNumbers++; //采集的图像数量+1
	
	str1.Format ("%d",m_PictureNumbers);
	switch(str1.GetLength ())
	{
		case 1:str1="0000"+str1;break;
		case 2:str1="000"+str1;break;
		case 3:str1="00"+str1;break;
		case 4:str1="0"+str1;break;
	}
	
	fname=str+str1+".bmp"; //得到要保存的位图文件名
	CRect rcDIB;
	GetClientRect(&rcDIB);
    
	OnDraw (GetDC());//刷新三维场景 
	CapturedImage.Capture(GetDC(), rcDIB);
	
	CapturedImage.WriteDIB(fname); //将采集的数据写入位图中文件中
	CapturedImage.Release();	//释放CapturedImage
}

//打印预览
void CT3DSystemView::OnFilePrintPreview() 
{
	CRect rcDIB;
	GetClientRect(&rcDIB); //得到客户区大小
	OnDraw (GetDC()); //刷新三维场景 
	CapturedImage.Capture(GetDC(), rcDIB);//将DDB图形转换为与设备无关的位图DIB
	CView::OnFilePrintPreview(); //调用	CView::OnFilePrintPreview()函数打印预览
}

//停止录像图像
void CT3DSystemView::OnSavepictureStop() 
{
	m_RecordPicture=FALSE; //标识录制图像为FALSE
	KillTimer(2);	//销毁计算器2	
	
}

//保存屏幕到位图
void CT3DSystemView::OnMenuSavescreentobmp() 
{
	CString strFilename;
	
	HDC   dc;   
	dc=::GetDC(NULL);   
	CRect rcDIB;
	GetClientRect(&rcDIB);
	rcDIB.right = rcDIB.Width();
	rcDIB.bottom = rcDIB.Height();
	HBITMAP mybitmap=GetSrcBit(dc,rcDIB.Width(),rcDIB.Height());
	
	CFileDialog FileDialog( FALSE, _T("bmp"), _T("*.bmp"), OFN_HIDEREADONLY,
		_T("Windows Bitmap Files (*.bmp)|*.bmp||"), NULL );
	if(FileDialog.DoModal()==IDOK)
	{
		strFilename=FileDialog.GetPathName(); //得到位图文件名
		if(!strFilename.IsEmpty()) //如果位图文件名为空
		{
			DeleteFile(strFilename); //删除原有文件
			BOOL bOK=SaveBmp(mybitmap,strFilename);  //保存到位图文件
			if(bOK==TRUE) //如果保存成功
				MessageBox("图像保存成功","保存屏幕到位图",MB_ICONINFORMATION);
			else
				MessageBox("图像保存失败","保存屏幕到位图",MB_ICONINFORMATION);
		}
		
	}	
}

//保存位图到文件
BOOL CT3DSystemView::SaveBmp(HBITMAP hBitmap, CString FileName)
{
	//设备描述表   
	HDC   hDC;   
	//当前分辨率下每象素所占字节数   
	int   iBits;   
	//位图中每象素所占字节数   
	WORD   wBitCount;   
	//定义调色板大小，   位图中像素字节大小   ，位图文件大小   ，   写入文件字节数     
	DWORD   dwPaletteSize=0,   dwBmBitsSize=0,   dwDIBSize=0,   dwWritten=0;     
	//位图属性结构     
	BITMAP   Bitmap;       
	//位图文件头结构   
	BITMAPFILEHEADER   bmfHdr;       
	//位图信息头结构     
	BITMAPINFOHEADER   bi;       
	//指向位图信息头结构       
	LPBITMAPINFOHEADER   lpbi;       
	//定义文件，分配内存句柄，调色板句柄     
	HANDLE   fh,   hDib,   hPal,hOldPal=NULL;     
    
	//计算位图文件每个像素所占字节数     
	hDC   =   CreateDC("DISPLAY",   NULL,   NULL,   NULL);   
	iBits   =   GetDeviceCaps(hDC,   BITSPIXEL)   *   GetDeviceCaps(hDC,   PLANES);     
	DeleteDC(hDC);     
	if   (iBits   <=   1)   wBitCount   =   1;     
	else   if   (iBits   <=   4)     wBitCount   =   4;     
	else   if   (iBits   <=   8)     wBitCount   =   8;     
	else       wBitCount   =   24;     
    
	GetObject(hBitmap,   sizeof(Bitmap),   (LPSTR)&Bitmap);    //保存位图文件前通过GetObject函数取得位图长度
	bi.biSize   =   sizeof(BITMAPINFOHEADER);   
	bi.biWidth   =   Bitmap.bmWidth;   
	bi.biHeight   =   Bitmap.bmHeight;   
	bi.biPlanes   =   1;   
	bi.biBitCount   =   wBitCount;   
	bi.biCompression   =   BI_RGB;   
	bi.biSizeImage   =   0;   
	bi.biXPelsPerMeter   =   0;   
	bi.biYPelsPerMeter   =   0;   
	bi.biClrImportant   =   0;   
	bi.biClrUsed   =   0;   
    
	dwBmBitsSize   =   ((Bitmap.bmWidth   *   wBitCount   +   31)   /   32)   *   4   *   Bitmap.bmHeight;   
    
	//为位图内容分配内存     
	hDib   =   GlobalAlloc(GHND,dwBmBitsSize   +   dwPaletteSize   +   sizeof(BITMAPINFOHEADER));     
	lpbi   =   (LPBITMAPINFOHEADER)GlobalLock(hDib);     
	*lpbi   =   bi;     
    
	//   处理调色板       
	hPal   =   GetStockObject(DEFAULT_PALETTE);     
	if   (hPal)     
	{     
		hDC   =   ::GetDC(NULL);     
		hOldPal   =   ::SelectPalette(hDC,   (HPALETTE)hPal,   FALSE);     
		RealizePalette(hDC);     
	}   
	
	//得到DC位图句柄 
	GetDIBits(hDC,   hBitmap,   0,   (UINT)   Bitmap.bmHeight,   (LPSTR)lpbi   +   sizeof(BITMAPINFOHEADER)     
		+dwPaletteSize,   (BITMAPINFO   *)lpbi,   DIB_RGB_COLORS);     
    
	//恢复调色板       
	if   (hOldPal)     
	{     
		::SelectPalette(hDC,   (HPALETTE)hOldPal,   TRUE);     
		RealizePalette(hDC);     
		::ReleaseDC(NULL,   hDC);     
	}     
    
	//创建位图文件       
	fh   =   CreateFile(FileName,   GENERIC_WRITE,0,   NULL,   CREATE_ALWAYS,     
		FILE_ATTRIBUTE_NORMAL   |   FILE_FLAG_SEQUENTIAL_SCAN,   NULL);     
    
	if   (fh   ==   INVALID_HANDLE_VALUE)     return   FALSE;     
    
	//   设置位图文件头     
	bmfHdr.bfType   =   0x4D42;   //   "BM"     
	dwDIBSize   =   sizeof(BITMAPFILEHEADER)   +   sizeof(BITMAPINFOHEADER)   +   dwPaletteSize   +   dwBmBitsSize;       
	bmfHdr.bfSize   =   dwDIBSize;     
	bmfHdr.bfReserved1   =   0;     
	bmfHdr.bfReserved2   =   0;     
	bmfHdr.bfOffBits   =   (DWORD)sizeof(BITMAPFILEHEADER)   +   (DWORD)sizeof(BITMAPINFOHEADER)   +   dwPaletteSize;     
	//   写入位图文件头     
	WriteFile(fh,   (LPSTR)&bmfHdr,   sizeof(BITMAPFILEHEADER),   &dwWritten,   NULL);     
	//   写入位图文件其余内容     
	WriteFile(fh,   (LPSTR)lpbi,   dwDIBSize,   &dwWritten,   NULL);     
	//清除       
	GlobalUnlock(hDib);     
	GlobalFree(hDib);     
	CloseHandle(fh);     
    
	return   TRUE;   
}

//得到DC位图句柄
HBITMAP CT3DSystemView::GetSrcBit(HDC hDC, DWORD BitWidth, DWORD BitHeight)
{
	HDC   hBufDC;   
	HBITMAP   hBitmap,   hBitTemp;   
    
	//创建设备上下文(HDC)   
	hBufDC   =   CreateCompatibleDC(hDC);   
    
	//创建HBITMAP   
	hBitmap   =   CreateCompatibleBitmap(hDC,   BitWidth,   BitHeight);   
	hBitTemp   =   (HBITMAP)   SelectObject(hBufDC,   hBitmap);   
    
	//得到位图缓冲区   
	StretchBlt(hBufDC,   0,   0,   BitWidth,   BitHeight,   
		hDC,   0,   0,   BitWidth,   BitHeight,   SRCCOPY);   
    
	//得到最终的位图信息   
	hBitmap   =   (HBITMAP)   SelectObject(hBufDC,   hBitTemp);   
    
	//释放内存   
    
	DeleteObject(hBitTemp);   
	::DeleteDC(hBufDC);   
    
	return   hBitmap;   
}




