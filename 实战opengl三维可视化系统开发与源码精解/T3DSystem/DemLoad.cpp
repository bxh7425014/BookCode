// DemLoad.cpp : implementation file
 

#include "stdafx.h"
 
#include "DemLoad.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


 
// CDemLoad dialog

CDemLoad::CDemLoad(CWnd* pParent /*=NULL*/)
	: CDialog(CDemLoad::IDD, pParent)
{
	//{{AFX_DATA_INIT(CDemLoad)
	m_DemFileName = _T("");
	m_strblockinfo = _T("");
	m_strsubblockinfo = _T("");
	//}}AFX_DATA_INIT
}


void CDemLoad::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CDemLoad)
	DDX_Control(pDX, IDC_COMBO_SUBBLOCKSIZE, m_subCombolblockSize);
	DDX_Control(pDX, IDC_PROGRESS, m_progress);
	DDX_Text(pDX, IDC_EDIT_DEMFILE, m_DemFileName);
	DDX_Text(pDX, IDC_EDIT_BLOCKINFOR, m_strblockinfo);
	DDX_Text(pDX, IDC_EDIT_SUBBLOCKINFOR, m_strsubblockinfo);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CDemLoad, CDialog)
	//{{AFX_MSG_MAP(CDemLoad)
	ON_BN_CLICKED(IDC_BUTTON_BROWSE, OnButtonBrowse)
	ON_BN_CLICKED(IDC_BUTTON_SEPERATE, OnButtonSeperate)
	ON_CBN_SELCHANGE(IDC_COMBO_SUBBLOCKSIZE, OnSelchangeComboSubblocksize)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

 
// CDemLoad message handlers

//将DEM子块数据写入数据库中
void CDemLoad::AddDemBlockDataToDB(int RowIndex, int ColIndex, CString strfilenaem, long ID)
{
	CString tt;
	double centerx,centery;

	//DEM子块的中心坐标
	centerx=theApp.m_DemLeftDown_x+((ColIndex-1)+1.0/2)*theApp.m_Dem_BlockWidth;
	centery=theApp.m_DemLeftDown_y+((RowIndex-1)+1.0/2)*theApp.m_Dem_BlockWidth;

	tt.Format("INSERT INTO dem_block VALUES(%d,%d,EMPTY_BLOB(),%ld,%.3f,%.3f)",\
		RowIndex,ColIndex,ID,centerx,centery);

	myOci.AddNormalDataToDB(tt);//将常规数据类型写入oralce数据库中

	tt.Format("SELECT DEM数据 FROM dem_block WHERE 行号=%d AND 列号=%d AND 编号= :%d FOR UPDATE",RowIndex,ColIndex,ID)  ;
	myOci.AddBOLBDataToDB(strfilenaem,tt,ID);//将DEM分块文件作为BLOB类型数据类型写入oralce数据库中
	
}


//初始化DEM分块大小
void CDemLoad::Init_BlockSize()
{
	long mvalue=1;
	int m;
	CString tt;
	m_subCombolblockSize.ResetContent ();
	for(int i=1;i<=9;i++)
	{
		m=1;
		for(int j=1;j<=i;j++)
			m=m*2;
		mvalue=m*16+1;
		tt.Format("%d",mvalue);
		tt=tt+"×"+tt;
		m_subCombolblockSize.AddString (tt);
	}
	m_subCombolblockSize.SetCurSel (0);
	OnSelchangeComboSubblocksize();
}

 
void CDemLoad::OnButtonBrowse() 
{


	CString tt,stt;
	FILE *fp;

	CFileDialog FileDialog(TRUE,"DEM数据文件",NULL,OFN_HIDEREADONLY \
		| OFN_OVERWRITEPROMPT,\
		"DEM数据文件(*.dem)|*.dem|\
		文本格式(*.txt)|*.txt||",NULL);
	
	FileDialog.m_ofn.lpstrTitle="打开DEM数据文件";	


	if(FileDialog.DoModal() == IDOK)
		m_DemFileName = FileDialog.GetPathName();
	else
		return;	
	this->UpdateData(FALSE);
	

	
    if((fp=fopen(m_DemFileName,"r"))==NULL)
		
    {
		MessageBox("地面模型文件不存在!","初始化地面模型",MB_ICONINFORMATION+MB_OK);
		exit(-1);
	}

}

 
void CDemLoad::OnButtonSeperate() 
{
	CString stt;
	
	BeginWaitCursor();
    m_progress.ShowWindow(SW_SHOW);

	DWORD   dw1=GetTickCount();   
	tempDemDirctory="c:\\tempRailwayDem";
	DWORD  dwAttr=GetFileAttributes(tempDemDirctory);   
	if(dwAttr==0xFFFFFFFF)     
        CreateDirectory(tempDemDirctory,NULL);  //创建临时文件夹
	
	SeperateDem(m_DemFileName,m_subBlockSize);//分块处理

	DWORD   dw2=GetTickCount();   
	DWORD   dw3=dw2-dw1; 
	stt.Format("%.3f秒!",dw3/1000.0);
	EndWaitCursor(); //将光标切换为默认光标,结束等待
	RemoveDirectory(tempDemDirctory);//删除临时文件夹
	
	SetWindowText("数模读取与分块处理");	
	m_progress.ShowWindow(SW_HIDE);//隐藏进度条
	m_progress.SetPos(0);
	MessageBox("数模读取与分块处理完成，共用时"+stt,"数模读取与分块处理",MB_ICONINFORMATION|MB_OK);
	EndDialog(IDOK);//以IDOK模式关闭对话框
	
}

//初始化信息
BOOL CDemLoad::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	m_progress.SetRange(0,100);//设置进度条控件范围
	
	Init_BlockSize();//初始化DEM分块大小
	
	myOci.Init_OCI();//初始化OCI

	return TRUE;  
}


//分块选项改变时,计算DEM子块分块大小
void CDemLoad::OnSelchangeComboSubblocksize() 
{
	int mIndex=	m_subCombolblockSize.GetCurSel();
	CString tt;
	m_subCombolblockSize.GetLBText(mIndex,tt);
	int mPos=tt.Find("×",1);
	tt=tt.Left(mPos);
	m_subBlockSize=atoi(tt);	
}

 
//根据所设置的分块大小，对DEM文件进行分块并写入Oralce数据库中
void CDemLoad::SeperateDem(CString strfilename, int BlockSize)
{
	long i,j;
	GLint m_DEM_X_neednumber,m_DEM_Y_neednumber;
	CString *strfiles;
	CStdioFile *Sfiles;
	
	float hh;	
	CString tt,stt;
	CString m_TerrainFileName;

	FILE *fp=fopen(strfilename,"r");//打开DEM文件

	fscanf(fp,"%s",tt);theApp.m_DemLeftDown_x=atof(tt);//得到DEM数据的左下角大地x坐标
    fscanf(fp,"%s",tt);theApp.m_DemLeftDown_y=atof(tt);//得到DEM数据的左下角大地y坐标
    fscanf(fp,"%s",tt);theApp.m_Cell_xwidth=atoi(tt);//得到DEM数据在x方向上数据点间距
    fscanf(fp,"%s",tt);theApp.m_Cell_ywidth=atoi(tt);//得到DEM数据在y方向上数据点间距
    fscanf(fp,"%s",tt);theApp.m_Dem_cols=atoi(tt);//得到DEM数据总列数
    fscanf(fp,"%s",tt);theApp.m_Dem_Rows=atoi(tt);//得到DEM数据总行数
    fscanf(fp,"%s\n",tt);theApp.m_DEM_IvalidValue=atoi(tt);//得到DEM数据无效数据值
    
    theApp.m_Dem_BlockSize=m_subBlockSize;//DEM分块大小
	theApp.m_DemRightUp_x=theApp.m_DemLeftDown_x+theApp.m_Cell_xwidth*(theApp.m_Dem_cols-1);//DEM右上角x坐标
	theApp.m_DemRightUp_y=theApp.m_DemLeftDown_y+theApp.m_Cell_ywidth*(theApp.m_Dem_Rows-1);//DEM右上角y坐标
	theApp.m_Dem_BlockWidth=theApp.m_Cell_xwidth*(theApp.m_Dem_BlockSize-1);//DEM子块总宽度

	
	SetWindowText("正在进行数模分块处理....");	

	if(theApp.m_Dem_Rows<=BlockSize)//如果DEM总行数小于所设置的分块大小,则DEM子块总行数=1
	{
		theApp.m_BlockRows=1;
		m_DEM_Y_neednumber=BlockSize-theApp.m_Dem_Rows;//在Y方向(行)需要添加无效数据的行数
	}
	else  //如果DEM总行数大于所设置的分块大小,计算DEM子块总行数
	{
		if((theApp.m_Dem_Rows-BlockSize) % (BlockSize-1)==0)//如果DEM总行数是分块大小的整数倍
		{
			theApp.m_BlockRows=(theApp.m_Dem_Rows-BlockSize) /(BlockSize-1)+1;
			m_DEM_Y_neednumber=0;//在Y方向上不需要添加无效数据
		}
		else
		{
			theApp.m_BlockRows=(theApp.m_Dem_Rows-BlockSize) /(BlockSize-1)+2;//分块总行数
			m_DEM_Y_neednumber=theApp.m_BlockRows*(BlockSize-1)+1-theApp.m_Dem_Rows;//在Y方向(行)需要添加无效数据的行数
		}
	}
		
	if(theApp.m_Dem_cols<=BlockSize)//如果DEM总列数小于所设置的分块大小,则DEM子块总列数=1
	{
		theApp.m_BlockCols=1;//则DEM子块总列数=1
		m_DEM_X_neednumber=BlockSize-theApp.m_Dem_cols;//需要在x方向(列)添加无效数据的列数
	}
	else //如果DEM总列数大于所设置的分块大小,计算DEM子块总列数
	{
		if((theApp.m_Dem_cols-BlockSize) % (BlockSize-1)==0)//如果DEM总列数是分块大小的整数倍
		{
			theApp.m_BlockCols=(theApp.m_Dem_cols-BlockSize) /(BlockSize-1)+1;
			m_DEM_X_neednumber=0;//在X方向上不需要添加无效数据
		}
		else
		{
			theApp.m_BlockCols=(theApp.m_Dem_cols-BlockSize) /(BlockSize-1)+2;//分块总列数
			m_DEM_X_neednumber=theApp.m_BlockCols*(BlockSize-1)+1-theApp.m_Dem_cols;//在X方向(列)需要添加无效数据的列数
		}
	}
	
	
        //重新定义m_pHeight数组大小
		m_pHeight= new float[BlockSize*BlockSize];

		//根据分块的总行数和总列数重新定义strfiles和Sfiles大小,用来存储每个子块数据的文件名
		strfiles =new CString [theApp.m_BlockRows*theApp.m_BlockCols];		
		Sfiles =new CStdioFile [theApp.m_BlockRows*theApp.m_BlockCols];
		
		//根据分块的总行数和总列数,建立临时文件,用来存储子块数据
		for ( i=0;i<theApp.m_BlockRows;i++)
		{
			for ( j=0;j<theApp.m_BlockCols;j++)
			{
				//获得文件名
				tt.Format("%d.txt",j+i*theApp.m_BlockCols+1);
				strfiles[j+i*theApp.m_BlockCols]=tempDemDirctory+"\\block_"+tt;
				//创建文件
				Sfiles[j+i*theApp.m_BlockCols].Open (strfiles[j+i*theApp.m_BlockCols],CFile::modeCreate | CFile::modeWrite);
				Sfiles[j+i*theApp.m_BlockCols].Close();//关闭文件
			}
		}
	

	long mto=theApp.m_Dem_Rows*theApp.m_Dem_cols;
	
	int mColsdatanum=0;
	int mCurrentRow,mCurrentCol;
	int m_oldcurrentRow=-1;

	CString *strSaveUpText;
	strSaveUpText= new CString [theApp.m_BlockCols];

	//
	theApp.m_DemHeight= new float[theApp.m_Dem_Rows*theApp.m_Dem_cols];
	
	//根据分块的总行数和总列数\和分块大小,对DEM数据进行了分块处理,将数据写入对应的DEM子块数据文件中
	for (i=1;i<=theApp.m_Dem_Rows;i++)
	{	
		m_progress.SetPos (i*(100.0/theApp.m_Dem_Rows));		
		if(i<=BlockSize)
			mCurrentRow=1;
		else
		{
			if((i-1)%(BlockSize-1)==0)
				mCurrentRow=(i-1)/(BlockSize-1);
            else
				mCurrentRow=(i-1)/(BlockSize-1)+1;
		}

        if(m_oldcurrentRow!=mCurrentRow)
		{
			if(m_oldcurrentRow>0)
			{
				for ( int k=0;k<theApp.m_BlockCols;k++)
				{
					Sfiles[k+(m_oldcurrentRow-1)*theApp.m_BlockCols].Close();
				}
			}

			for (  int k=0;k<theApp.m_BlockCols;k++)
			{
				Sfiles[k+(mCurrentRow-1)*theApp.m_BlockCols].Open (strfiles[k+(mCurrentRow-1)*theApp.m_BlockCols],CFile::modeCreate |CFile::modeWrite);
			}
		}
	
		stt="";	

		mColsdatanum=0;
		for (j=1;j<=theApp.m_Dem_cols;j++)
		{
			if(j<=BlockSize)
				mCurrentCol=1;
			else
			{
				if((j-1)%(BlockSize-1)==0)
					mCurrentCol=(j-1)/(BlockSize-1);
				else
					mCurrentCol=(j-1)/(BlockSize-1)+1;
			}

			fscanf(fp,"%f ",&hh);  //读取高程
			tt.Format("%.3f	",hh);//高程精度取小数点后3位
			theApp.m_DemHeight[(i-1)*theApp.m_Dem_cols+(j-1)]=hh;
			stt=stt+tt;
			mColsdatanum++;
			if(mColsdatanum % BlockSize==0 || j==theApp.m_Dem_cols )
				{
				if(j==theApp.m_Dem_cols)
				{
				
					//对于不足分块大小的子块数据,以无效数据补充
					int pos=BlockSize-mColsdatanum;
					for(int k=1;k<=pos;k++)
					{
					
						tt.Format("%d",theApp.m_DEM_IvalidValue);
						stt=stt+tt;
					}
				}
				stt=stt+"\n";
			    if(mCurrentRow>1 && m_oldcurrentRow!=mCurrentRow)
				{
					
					Sfiles[(mCurrentRow-1)*theApp.m_BlockCols+mCurrentCol-1].WriteString(strSaveUpText[mCurrentCol-1]);

				}
				Sfiles[(mCurrentRow-1)*theApp.m_BlockCols+mCurrentCol-1].WriteString (stt);
		
                if((i-1)% (BlockSize-1)==0 && (i>1 && i<theApp.m_Dem_Rows)) 
				{
					int ms=(i-1)/(BlockSize-1)*theApp.m_BlockCols+mCurrentCol-1;
					Sfiles[ms].Open(strfiles[ms],CFile::modeCreate |CFile::modeWrite);
				
					Sfiles[ms].WriteString (stt);//将数据写入文件
					Sfiles[ms].Close();
					strSaveUpText[mCurrentCol-1]=stt;
				}
			
				if(mColsdatanum % BlockSize==0) 
				{
					stt=tt;
					mColsdatanum=1;
				}
				else
				{
					stt="";
					if(j<theApp.m_Dem_cols) mColsdatanum=0;
				}
				if(i>=theApp.m_Dem_Rows && j>=theApp.m_Dem_cols)
				{
				
					int pos=m_DEM_Y_neednumber;
					for(int p=1;p<=theApp.m_BlockCols;p++)
					{
						for(int k=1;k<=pos;k++)
						{
							stt="";
							//对于不足分块大小的子块数据,以无效数据补充
							for(int m=1;m<=BlockSize+1;m++)
							{
							
								tt.Format("	%d",theApp.m_DEM_IvalidValue);
								stt=stt+tt;
							}
							stt=stt+"\n";
							Sfiles[theApp.m_BlockCols*theApp.m_BlockRows-(theApp.m_BlockCols-p+1)].WriteString (stt);
						}
					}
					
				}
							
			}
			
		}
		fscanf(fp,"\n");  
		m_oldcurrentRow=mCurrentRow;
	
	}

	//关闭所打开的临时文件
	for ( int k=0;k<theApp.m_BlockCols;k++)
	{
		Sfiles[k+(theApp.m_BlockRows-1)*theApp.m_BlockCols].Close();
	}

	//写入数据库之前,先删除dem_block(DEM数据表中的原来所有数据)
	//是为了防止对同一数据文件多次写入数据库中的重复写入错误
	CString strSql;
	strSql="DELETE FROM dem_block";
	HRESULT hr=theApp.m_pConnection->Execute (_bstr_t(strSql),NULL,adCmdText);
	if(!SUCCEEDED(hr))
	{
		MessageBox("删除失败!","写入DEM数据到数据库",MB_ICONSTOP);
		return;
	}
	//依次将分块捕捞DEM子块数据文件写入oracle数据库中
    SetWindowText("将数模分块数据写入数据库...");	
	for ( i=0;i<theApp.m_BlockRows;i++)
	{
			for ( j=0;j<theApp.m_BlockCols;j++)
			{
				m_progress.SetPos ((i*theApp.m_BlockCols+j+1)*(100.0/(theApp.m_BlockRows*theApp.m_BlockCols)));
				AddDemBlockDataToDB(i+1,j+1,strfiles[i*theApp.m_BlockCols+j],i*theApp.m_BlockCols+j+1);
			}
	}
	
	fclose(fp);  //关闭文件
	
	WriteTotalDemToBlob(strfilename);//将DEM数据写入oralce数据库中

}



//得到分块大小
int CDemLoad::GetBlcokSize(int currentSel)
{
	int m=1;
	for(int i=0;i<currentSel;i++)
		m=m*2;
	return m*32+1;
}

//从DEM分块文件中读取数据 
void CDemLoad::ReadDemDataFromFiles(CString strfiles, int Index)
{
	float hh; 
	int i,j;
    int mCount=theApp.m_Dem_BlockSize;
    FILE *fp=fopen(strfiles,"r");//打开文件
	
	for( i=0;i<mCount;i++) //等比例DEM子块的行、列数年是相同的(如33×33,65×65等等)
	{
		for( j=0;j<mCount;j++)
		{
			fscanf(fp,"%f ",&hh);  
			
			m_pHeight[i*mCount+j]=hh;
			
		}
	}
	fclose(fp);//关闭文件
	DeleteFile(strfiles);//删除临时分块文件
}

 
//根据行、列索引值计算对应DEM数据点的高程
float CDemLoad::HH(int mRows, int mCols)
{
	return m_pHeight[mRows*theApp.m_Dem_BlockSize+mCols];
}


//将DEM数据写入oralce数据库中
void CDemLoad::WriteTotalDemToBlob(CString strfile)
{
	CString tt;

	
	tt.Format("INSERT INTO DEM_INFO VALUES(%.3f,%.3f,%d,%d,%d,%d,%d,%d,%d,EMPTY_BLOB(),%d,%d)",\
		theApp.m_DemLeftDown_x,theApp.m_DemLeftDown_y,theApp.m_Cell_xwidth,\
		theApp.m_Cell_ywidth,theApp.m_Dem_BlockSize,theApp.m_BlockRows,\
		theApp.m_BlockCols,theApp.m_Dem_Rows,theApp.m_Dem_cols,1,theApp.m_DEM_IvalidValue);

	myOci.AddNormalDataToDB(tt);//将常规数据类型写入oralce数据库中
	
	tt.Format("SELECT DEM数据 FROM DEM_INFO WHERE 编号= :%d FOR UPDATE",1)  ;
	myOci.AddBOLBDataToDB(strfile,tt,1);//将DEM分块文件作为BLOB类型数据类型写入oralce数据库中

	
}





















