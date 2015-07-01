// TextureLoad.cpp : implementation file
 

#include "stdafx.h"
 
#include "TextureLoad.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

 
// CTextureLoad dialog

#define   BUFFERLEN   102400

CTextureLoad::CTextureLoad(CWnd* pParent /*=NULL*/)
	: CDialog(CTextureLoad::IDD, pParent)
{
	//{{AFX_DATA_INIT(CTextureLoad)
	//}}AFX_DATA_INIT
}


void CTextureLoad::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CTextureLoad)
	DDX_Control(pDX, IDC_LIST_FILES, m_listfiles);
	DDX_Control(pDX, IDC_PROGRESS, m_progress);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CTextureLoad, CDialog)
	//{{AFX_MSG_MAP(CTextureLoad)
	ON_BN_CLICKED(IDC_BUTTON_BROWSE, OnButtonBrowse)
	ON_WM_DESTROY()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CTextureLoad message handlers


//浏览外部纹理文件
void CTextureLoad::OnButtonBrowse() 
{
	CString  strFile,strFilter="Tif files(*.Tif)|*.Tif|\
							 BMP files(*.BMP)|*.BMP|\
							 Jpeg files(*.JPG)|*.JPG|\
							 GIF files(*.GIF)|*.GIF|\
							 PCX files(*.PCX)|*.PCX|\
							 Targa files(*.TGA)|*.TGA||";

	//定制打开文件对话框，使支持多项选择(OFN_ALLOWMULTISELECT)
	CFileDialog   fdlg(TRUE,NULL,NULL,OFN_HIDEREADONLY|OFN_ALLOWMULTISELECT,strFilter);     

	char  *pBuf= new  char[BUFFERLEN];//申请缓冲区
	
	fdlg.m_ofn.lpstrFile=pBuf;    //让pBuf代替CFileDialog缓冲区	
	fdlg.m_ofn.lpstrFile[0]='\0';     
	fdlg.m_ofn.nMaxFile=BUFFERLEN; 


	int nCount=0;//初始选择的文件数为0
	
	//如果成功，获取对话框多项选择的文件，并依次添加到列表框里
	if(fdlg.DoModal()==IDOK)     
	{     
        //GetStartPosition():返回指示遍历映射起始位置的POSITION位置，如果映射为空，则返回NULL
		POSITION  pos=fdlg.GetStartPosition();  
		m_listfiles.ResetContent();//清空列表框
	
		while(pos)   //如果映射不为空
		{   
			nCount++; 
			strFile=fdlg.GetNextPathName(pos);  //得到文件名
			m_listfiles.AddString(strFile);//将文件名加入到列表框
		}     
	}  
	
	delete[]  pBuf;   //回收缓冲区
}

//初始化对话框
BOOL CTextureLoad::OnInitDialog() 
{
	CDialog::OnInitDialog();
//	myOci.Init_OCI();	//初始化OCI
	return TRUE;  
}

//对各级纹理影像逐个分块处理,并写入oracle数据库中
void CTextureLoad::OnOK() 
{

	CString mfilename,stt,tt;

	BeginWaitCursor();

	CString tempDirctory="c:\\tempRailwayBmp";//存放纹理影像子块的临时文件夹
	DWORD  dwAttr=GetFileAttributes(tempDirctory);   //获取文件夹属性
	if(dwAttr==0xFFFFFFFF)  //如果该文件夹不存在就创建一个   
        CreateDirectory(tempDirctory,NULL);  //创建临时文件夹

	m_listfiles.SetCurSel(0);
	m_listfiles.GetText(m_listfiles.GetCurSel(),mfilename);
	if(GetTextureRange(mfilename)==FALSE)
		return;
	
	//对各级纹理影像逐个分块处理,并写入oracle数据库中
	for(int i=0;i<m_listfiles.GetCount();i++)
	{
		m_listfiles.SetCurSel(i); //设置列表框当前索引
		m_listfiles.GetText(m_listfiles.GetCurSel(),mfilename);//得到当前包含全路径的影像文件名
		int nPos=mfilename.ReverseFind('\\');
		tt=mfilename.Mid(nPos+1,mfilename.GetLength()-nPos);//得到影像文件名
		
		stt.Format("正在处理：%s",tt);
		this->SetWindowText(stt);	//设置对话框标题
		SeperateImage(mfilename,i,tempDirctory);//对影像纹理进行分块处理,并写入oracle数据库中	
	}	

	//将影像纹理总体信息写入影像信息表中
	tt.Format("INSERT INTO IMAGERECT_INFO VALUES(%.3f,%.3f,%.3f,%.3f,%d,\
		%.2f,%.2f,%.2f,%.2f,%.2f)",theApp.m_TexturLeftDown_x,theApp.m_TexturLeftDown_y,\
		theApp.m_TexturRightUp_x,theApp.m_TexturRightUp_y,m_listfiles.GetCount(),\
		theApp.m_ImageResolution[1],theApp.m_ImageResolution[2],theApp.m_ImageResolution[3],\
		theApp.m_ImageResolution[4],theApp.m_ImageResolution[5]);
	theApp.m_pConnection->Execute (_bstr_t(tt),NULL,adCmdText);//执行SQL语句
	
	EndWaitCursor(); //将光标切换为默认光标,结束等待
	m_progress.ShowWindow(SW_HIDE);//隐藏进度条
	m_progress.SetPos(0);//恢复初始位置0值
	MessageBox("影像纹理文件写入完成!","影像纹理入库",MB_OK);
	RemoveDirectory(tempDirctory); //删除临时文件夹
	theApp.bLoadImage=TRUE;//加载影像成功
		
	CDialog::OnOK();
}

//将纹理影像进行分块处理,并写入oracle数据库中
void CTextureLoad::SeperateImage(CString mfilename, int m_phramidLayer,CString tempDirctory)
{
	
		CString stt,strfile;
        m_pImageObject=new CImageObject;
		
		m_pImageObject->Load(mfilename,NULL,-1,-1);//加载影像纹理
		long m_height=m_pImageObject->GetHeight();//得到纹理高度
		long m_width=m_pImageObject->GetWidth();//得到纹理宽度

		//当前LOD级别的纹理分辨率
		theApp.m_ImageResolution[m_phramidLayer]=(theApp.m_TexturRightUp_x-theApp.m_TexturLeftDown_x)/m_width;
	

		//纹理影像子块的宽度
		int m_fg_width=theApp.m_Dem_BlockWidth/theApp.m_ImageResolution[m_phramidLayer]; 
		//纹理影像子块的高度
		int m_fg_height=theApp.m_Dem_BlockWidth/theApp.m_ImageResolution[m_phramidLayer];

		//计算当前LOD级的纹理影像分块的总行数
		if(m_height%m_fg_height==0)
			m_totalRows=m_height/m_fg_height;
		else
		{
			m_totalRows=m_height/m_fg_height+1;
			
		}
		
		//计算当前LOD级的纹理影像分块的总列数
		if(m_width%m_fg_width==0)
			m_totalCols=m_width/m_fg_width;
		else
		{
			m_totalCols=m_width/m_fg_width+1;
			
		}		
		
		int nPos=mfilename.ReverseFind('\\');
		strfile=mfilename.Mid(nPos+1,mfilename.GetLength()-nPos-5);

		for(int i=0;i<m_totalRows;i++)
		{
			for(int j=0;j<m_totalCols;j++)
			{
			
			//设置进度条值
			m_progress.SetPos((i*m_totalCols+j+1)*100.0/(m_totalRows*m_totalCols));
			int mleftx=(j-0)*m_fg_width;	    //影像子块左下角x坐标
			int mlefty=(m_totalRows-i-1)*m_fg_height;	//影像子块左下角y坐标
			int mrightx=mleftx+m_fg_width-1;	//影像子块右上角x坐标
			int mrigty=mlefty+m_fg_height-1;	//影像子块右上角y坐标
			m_pImageObject->Crop( mleftx, mlefty, mrightx, mrigty);	//读取由mleftx、mlefty、mrightx和mrigty所确定的影像子块
			stt.Format("%s\\%s@%d_%d.bmp",tempDirctory,strfile,i,j);
			m_pImageObject->Save( stt, 1);//将读取的影像子块存储到临时文件中
			int m_subImageWidth=m_pImageObject->GetWidth();//得到影像子块的宽度
			int m_subImageHeight=m_pImageObject->GetHeight();//得到影像子块的高度
		    //将影像子块以BLOB数据类型写入oracle数据库中
			WriteImageToDB(stt,i+1,j+1,m_subImageHeight,m_subImageWidth,m_phramidLayer,m_fg_width,m_fg_height);
			DeleteFile(stt);
			m_pImageObject->Load(mfilename,NULL,-1,-1);//重新加载原始影像
			}
		}
}

//将影像子块以BLOB数据类型写入oracle数据库中
BOOL CTextureLoad::WriteImageToDB(CString strFile, int m_RowIndex, int m_ColIndex, int m_height, int m_width, int m_phramidLayer,int m_fg_width,int m_fg_height)
{
	
	CString tt;
	
	double m_leftdownx,m_leftdowny,m_rightUpx,m_rightUpy;

	//计算将影像子块的左下和左上角x,y大地坐标
	m_leftdownx=(m_ColIndex-1)*m_fg_width*theApp.m_ImageResolution[m_phramidLayer]+theApp.m_TexturLeftDown_x;
	m_leftdowny=(m_RowIndex-1)*m_fg_height*theApp.m_ImageResolution[m_phramidLayer]+theApp.m_TexturLeftDown_y;
	m_rightUpx=m_leftdownx+m_width*theApp.m_ImageResolution[m_phramidLayer];
	m_rightUpy=m_leftdowny+m_height*theApp.m_ImageResolution[m_phramidLayer];

	int m_ID=(m_RowIndex-1)*m_totalCols+m_ColIndex;//影像子块的ID号

	tt.Format("INSERT INTO texture VALUES(%d,%d,%d,%d,%d,EMPTY_BLOB(),\
		%ld,%.3f,%.3f,%.3f,%.3f)",\
		m_RowIndex,m_ColIndex,m_height,m_width,m_phramidLayer,m_ID,\
		m_leftdownx,m_leftdowny,m_rightUpx,m_rightUpy);
	
	//调用OCI公共类的AddNormalDataToDB函数，将常规数据类型的数据写入oracle数据库中
	myOci.AddNormalDataToDB(tt);

	//调用OCI公共类的AddBOLBDataToDB函数，将BLOB类型的数据写入oracle数据库中
	tt.Format("SELECT 纹理数据 FROM texture WHERE 行号=%d AND 列号=%d AND 纹理金子塔层号=%d AND 编号= :%d FOR UPDATE",m_RowIndex,m_ColIndex,m_phramidLayer,m_ID)  ;
	myOci.AddBOLBDataToDB(strFile,tt,m_ID);
	
	return TRUE;
}

//销毁对话框
void CTextureLoad::OnDestroy() 
{
	CDialog::OnDestroy();
	
	m_pImageObject=NULL;
	delete m_pImageObject;
	
}

 
//将最后一级纹理影像不进行分块直接写入数据库中,将其作为导航图纹理
BOOL CTextureLoad::WriteLittleLodImageToDB(CString strFile)
{
	
	CString tt;
	int m_ID=1;
	
	tt.Format("INSERT INTO texture_LitLOD VALUES(EMPTY_BLOB(),%d)",m_ID);
	//调用OCI公共类的AddNormalDataToDB函数，将常规数据类型的数据写入oracle数据库中
	myOci.AddNormalDataToDB(tt);
	
	tt.Format("SELECT 纹理数据 FROM texture_LitLOD WHERE  编号= :%d FOR UPDATE",m_ID)  ;
	//调用OCI公共类的AddBOLBDataToDB函数，将BLOB类型的数据写入oracle数据库中
	myOci.AddBOLBDataToDB(strFile,tt,m_ID);
	return TRUE;
}


//得到影像纹理的左下和右上角 x,y坐标
BOOL CTextureLoad::GetTextureRange(CString tcrPathname)
{
	/*
	
	影像纹理坐标文件是以项目名称同名的，扩展名为".tod"的文件中存储的，其格式为：
	lb: 781395.000	1869975.000
	rt: 797995.000	1876275.000
	其中	第一行的lb:表示影像纹理的左下角x,y坐标
			第二行的rt:表示影像纹理的右上角x,y坐标
	*/
	CString tt,strpath;
	int	pos=tcrPathname.ReverseFind('\\');
	strpath=tcrPathname.Left(pos);
	pos=strpath.ReverseFind('\\');
	tt=strpath.Right(strpath.GetLength()-pos-1);
    FILE *fp;
	tt=strpath+"\\"+tt+".tod";//得到影像范围文件名
	
	if((fp=fopen(tt,"r"))==NULL)//如果文件打开失败
    {
		MessageBox("影像范围文件"+tt+"不存在!","读取影像范围文件",MB_ICONINFORMATION+MB_OK);
		fclose(fp);	//关闭文件
		return FALSE;//返回FALSE
	}
	else
	{
		fscanf(fp,"%s",tt);//得到lb:字符串
		fscanf(fp,"%s",tt);theApp.m_TexturLeftDown_x=atof(tt);//纹理的左下角x坐标
		fscanf(fp,"%s\n",tt);theApp.m_TexturLeftDown_y=atof(tt);//纹理的左x,y坐标
		fscanf(fp,"%s",tt);//得到rt:字符串
		fscanf(fp,"%s",tt);theApp.m_TexturRightUp_x=atof(tt);//纹理的右上角x坐标
		fscanf(fp,"%s\n",tt);theApp.m_TexturRightUp_y=atof(tt);//纹理的右上角y坐标
		fclose(fp);//关闭文件
		return TRUE;//返回TRUE
		
	}
	
}
