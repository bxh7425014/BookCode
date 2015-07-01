// OCIclass.cpp: implementation of the COCIclass class.
 
 

#include "stdafx.h"
 
#include "OCIclass.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

 
// Construction/Destruction
 

FILE *fp_OCI;	//FILE指针,向来打开外部文件
ub4 Filelength_OCI; //存储打开外部文件的长度

/*
为了不一次写入过多数据，首先定义一个缓冲区大小LOB_BUF_LEN，比如规定为8192。 
如果写入数据大小（size）在LOB_BUF_LEN范围内，则可一次写完。 
*/
#define LOB_BUF_LEN  131072  
 
#define MAXLENGTH    4294967296 //一个LOB字段最大存储的字节数

 
//以回调方式写入数据
sb4 callback_writes_OCI(dvoid *ctxp,dvoid *bufxp,ub4 *lenp, ub1 *piece)
{
	ub4 piecelen;	
	static ub4 len=LOB_BUF_LEN;
	
	if((Filelength_OCI-len)>LOB_BUF_LEN) //如果文件的长度-每次读写的长度>LOB_BUF_LEN
		piecelen=LOB_BUF_LEN;	//此时读写的长度=LOB_BUF_LEN
	else  //如文件的长度<每次读写的长度
		piecelen=Filelength_OCI-len; //此时读写的长度=文件的长度-LOB_BUF_LEN
	
	*lenp=piecelen;
	
	//开始读文件
	if(fread((void*)bufxp,(size_t)piecelen,1,fp_OCI)!=1)
	{
		AfxMessageBox("读数据文件发生错误!");
		*piece=OCI_LAST_PIECE;
		len=LOB_BUF_LEN;
		return OCI_CONTINUE;
		
	}
	len+=piecelen;//已读的文件长度累加
	
	if(len==Filelength_OCI)  //如果已读的文件长度=文件总长度,结束对文件的读操作
	{
		*piece=OCI_LAST_PIECE;//当前块为最后一块标志，表示写最后一批数据 
		len=LOB_BUF_LEN;
	}
	else
		*piece=OCI_NEXT_PIECE;//否则,当前块为下一块标志,表示继续写，但不写最后一批 
	
	return OCI_CONTINUE;
}

 
//以回调方式读入数据
sb4 callback_Read_OCI(dvoid *ctxp,CONST dvoid *bufxp,ub4 len,ub1 piece)
{
	static ub4 piece_count=0;
	piece_count++;
	switch(piece)
	{
	case OCI_LAST_PIECE://表示写最后一批数据 
		fwrite((void*)bufxp,(size_t)len,1,fp_OCI);
		piece_count=0;
		return OCI_CONTINUE;
	case OCI_FIRST_PIECE://表示写第一批
	case OCI_NEXT_PIECE://表示继续写，但不写最后一批
		fwrite((void*)bufxp,(size_t)len,1,fp_OCI);
		break;
	default:
		return OCI_ERROR;
	}
	
	return OCI_CONTINUE;
	
}

COCIclass::COCIclass()
{

}

COCIclass::~COCIclass()
{

}


//初始化OCI
void COCIclass::Init_OCI()
{
	//创建OCI环境,即创建和初始化OCI工作环境，其他的OCI函数需要OCI环境才能执行。
	if(status=OCIEnvCreate(&envhp,OCI_DEFAULT,(dvoid *)0, 
		(dvoid* (*)(dvoid*,size_t))0,(dvoid* (*)(dvoid*,dvoid*,size_t))0,
		(void (*)(dvoid *, dvoid *)) 0, (size_t) 0,(dvoid **) 0 ))
	{
		MessageBox(NULL,"创建OCI环境失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	/*
	1．创建OCI环境 
	sword OCIEnvCreate(   
	OCIEnv **envhpp,  //OCI环境句柄指针 
	ub4 mode, //初始化模式：OCI_DEFAULT/OCI_THREADED 等 
	CONST dvoid *ctxp, 
	CONST dvoid *(*malicfp)(dvoid *ctxp,size_t size), 
	CONST dvoid *(ralocfp)(dvoid *ctxp,dvoid *memptr,size_t newsize), 
	CONST void *(*mfreefp)(dvoid *ctxp,dvoid *memptr), 
	Size_t xstramemsz, 
	Dvoid **usrmempp 
	) 

OCI函数设置的模式有： 
	OCI_DEFUALT:使用OCI默认的环境 
	OCI_THREADED：线程环境下使用OCI 
	OCI_OBJECT：对象模式 
	OCI_SHARED：共享模式 
	OCI_EVENTS 
	OCI_NO_UCB 
	OCI_ENV_NO_MUTEX：非互斥访问模式 
	其中模式可以用逻辑运算符进行迭加，将函数设置成多多种模式：如mode=OCI_SHREADED| OCI_OBJECT 

	*/

	//申请错误句柄
	if(status=OCIHandleAlloc((dvoid*)envhp,(dvoid**)&errhp,(ub4)OCI_HTYPE_ERROR,
		(size_t)0,(dvoid**)0))
	{
		MessageBox(NULL,"申请错误句柄失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}

	//申请服务器句柄
	if(status=OCIHandleAlloc((dvoid*)envhp,(dvoid**)&srvhp,
		(ub4)OCI_HTYPE_SERVER,(size_t)0,(dvoid**)0))
	{
		MessageBox(NULL,"申请服务器句柄失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	
	//申请服务环境句柄
	if(status=OCIHandleAlloc((dvoid*)envhp,(dvoid**)&svchp,
		(ub4)OCI_HTYPE_SVCCTX,(size_t)0,(dvoid**)0))
	{
		MessageBox(NULL,"申请服务环境句柄失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	
	//申请会话句柄
	if(status=OCIHandleAlloc((dvoid*)envhp,(dvoid**)&authp,
		(ub4)OCI_HTYPE_SESSION,(size_t)0,(dvoid**)0))
	{
		MessageBox(NULL,"申请会话句柄失败!","初始化OIC",MB_ICONINFORMATION);		
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	
	//申请语句句柄
	if(status=OCIHandleAlloc((dvoid*)envhp,(dvoid**)&stmthp,
		(ub4)OCI_HTYPE_STMT,(size_t)0,(dvoid**)0))
	{
		MessageBox(NULL,"申请语句句柄失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	
	/*
	2．申请/释放句柄 
	sword OCIHandleAlloc( 
		CONST dvoid *parenth,  //新申请句柄的父句柄，一般为OCI环境句柄 
		Dvoid **hndlpp,   //申请的新句柄 
		Ub4 type, type,  //句柄类型 
		Size_t xtramem_sz,   //申请的内存数 
		Dvoid **usrmempp  //申请到的内存块指针 
	) 
	注： 
	一般需要申请的句柄有： 
	服务器句柄OCIServer, 句柄类型OCI_HTYPE_SERVER 
	错误句柄OCIError，用于捕获OCI错误信息, 句柄类型OCI_HTYPE_ERROR 
	事务句柄OCISession, 句柄类型OCI_HTYPE_SESSION 
	上下文句柄OCISvcCtx, 句柄类型OCI_HTYPE_SVCCTX 
	SQL语句句柄OCIStmt, 句柄类型OCI_HTYPE_STMT 
	*/

	/*
	释放句柄 
	sword OCIHandleFree( 
	dvoid *hndlp,  //要释放的句柄 
	ub4 type   //句柄类型 
	) 
	*/

	//申请2个LOB占位符,用于对BLOB类型数据写入或读取操作。用参数OCI_DTYPE_LOB，获得一个LOB的句柄
	for(int i=0;i<2;i++)
	{
		if(status=OCIDescriptorAlloc((dvoid*)envhp,(dvoid**)&lob_loc[i],(ub4)OCI_DTYPE_LOB,
			(size_t)0,(dvoid**)0))
		{
			MessageBox(NULL,"申请LOB占位符失败!","初始化OIC",MB_ICONINFORMATION);
			Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
			return ;		
		}
 	}

	/*
	12． 结合占位符和指示器变量： 
	占位符：在程序中，一些SQL语句需要在程序运行时才能确定它的语句数据，在设计时
	可用一个占位符来代替，当程序运行时，在它准备好语句后，必须为每个占位符指定一个变量，
	即将占位符与程序变量地址结合，执行时，Oracle就从这些变量中读取数据，并将它们
	与SQL语句一起传递给Oracle服务器执行。OCI结合占位符时，它将占位符与程序
	变量关联起来，并同时要指出程序变量的数据类型和数据长度。 
	如：select * from test where name=:p1 and age>:p2 
	：p1和：p2为占位符 
	
	  指示器变量：由于在Oracle中，列值可以为NULL，但在C语言中没有NULL值，
	  为了能使OCI程序表达NULL列值，OCI函数允许程序为所执行语句中的结合变量同时
	  关联一个指示符变量或指示符变量数组，以说明所结合的占位符是否为NULL或所
	  读取的列值是否为NULL，以及所读取的列值是否被截取。 
	  除SQLT＿NTY（SQL Named DataType）外，指示符变量或指示符变量数组的数据类型为sb2,其值说明： 
	  作为输入变量时：（如insert ,update语句中） 
	  　=-1：OCI程序将NULL赋给Oracle表的列，忽略占位符结合的程序变量值 
	  >=0：应用程序将程序变量值赋给指定列 
	  作为输出变量时：（如select语句中） 
	  ＝-2：所读取的列数据长度大于程序变量的长度，则被截取。 
	  =-1：所读取的值为NULL,输出变量的值不会被改变。 
	  =0：数据被完整读入到指定的程序变量中 
	  ＞0：所读取的列数据长度大于程序变量的长度，则被截取，指示符变量值为所读取数据被截取前的实际长度 
	  
	*/
// 	theApp.m_servername="";//定义的全局oracle数据库服务器名

	//连接数据库
	if(status=OCIServerAttach(srvhp,errhp,(text*)(LPCSTR)theApp.m_servername,strlen(theApp.m_servername),OCI_DEFAULT))
		{
		MessageBox(NULL,"连接数据库失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
/*
	4．连接/断开服务器 
	多用户方式连接： 
	sword  OCIServerAttach( 
	OCIServer     *srvhp,//未初始化的服务器句柄 
	OCIError      *errhp, //错误句柄
	CONST text    *dblink,//服务器SID 
	sb4           dblink_len, //服务器名长度
	ub4           mode //=OCI_DEFAULT,系统环境将设为阻塞方式 
	); 

	  OCI连接有二种方式：Blocking(阻塞方式)和non_Blocking(非阻塞方式)，阻塞方式就是
	  当调用 OCI操作时，必须等到此OCI操作完成后服务器才返回客户端相应的信息，不管
	  是成功还是失败。非阻塞方式是当客户端提交OCI操作给服务器后，服务器立即
	  返回OCI_STILL_EXECUTING信息，而并不等待服务端的操作完成。 
	对于non-blocking方式，应用程序若收到一个OCI函数的返回值为 OCI_STILL_EXECUTING时
	必须再次对每一个OCI函数的返回值进行判断，判断其成功与否。 
	可通过设置服务器属性为OCI_ATTR_NONBLOCKING_MODE来实现。系统默认方式为阻塞模式. 
	  
*/


	//设置服务环境的服务器属性(即oracle数据库的服务器名)	
	if(status= OCIAttrSet ((dvoid*)svchp, (ub4) OCI_HTYPE_SVCCTX, 
		(dvoid*)srvhp, (ub4) 0,OCI_ATTR_SERVER, errhp))
	{
		MessageBox(NULL,"设置服务环境的服务器属性失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}

	//设置会话所使用的用户名称(即oracle数据库用户名)
	if(status= OCIAttrSet((dvoid*)authp,OCI_HTYPE_SESSION,
		(text*)(LPCSTR)theApp.m_username,(ub4)strlen(theApp.m_username),OCI_ATTR_USERNAME,errhp))
	{
		MessageBox(NULL,"设置会话所使用的用户帐号失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}	
	
	//设置会话所使用的用户密码(即oracle数据库密码)
	if(status= OCIAttrSet((dvoid*)authp,OCI_HTYPE_SESSION,
		(text*)(LPCSTR)theApp.m_userpassword,(ub4)strlen(theApp.m_userpassword),OCI_ATTR_PASSWORD,errhp))
	{
		MessageBox(NULL,"设置会话所使用的用户密码失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}

	/*
	5．读取/设置句柄属性 
	sword OCIAttrSet( 
	dvoid *trgthndlp,  //需设置的句柄名 
	ub4  trghndltyp, //句柄类型 
	dvoid *attributep, //设置的属性名 
	ub4 size, //属性值长度 
	ub4 attrtype,     //属性类型 
	OCIError *errhp   //错误句柄 
	) 
	注：一般要设置的属性有： 
	服务器实例： 
	句柄类型OCI_HTYPE_SVCCTX，属性类型OCI_ATTR_SERVER 
	连接数据的用户名： 
	句柄类型OCI_HTYPE_SESSION，属性类型OCI_ATTR_USERNAME 
	用户密码 
    句柄类型OCI_HTYPE_SESSION，属性类型OCI_ATTR_PASSWORD 
	事务：   
	句柄类型OCI_HTYPE_SVCCTX，属性类型OCI_ATTR_SESSION 
	
	
	*/
	
	//开始一个会话(即通过设置的oracle数据库用户名和密码\)
	if(status= OCISessionBegin(svchp,errhp,authp,OCI_CRED_RDBMS,OCI_DEFAULT))
	{
		MessageBox(NULL,"设置会话所使用的用户帐号失败!","初始化OIC",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
	/*
	6．开始/结束一个会话
	先认证用户再建立一个会话连接
    sword OCISessionBegin ( 
	OCISvcCtx     *svchp,   //服务环境句柄
	OCIError    *errhp,	//错误句柄
	OCISession *usrhp, //用户会话句柄
	ub4           credt, //认证类型
	ub4           mode //操作模式
	);
	
      *认证类型： 
	  OCI_CRED_RDBMS:用数据库用户名和密码进行认证，则先要设置OCI_ATTR_USERNAME和OCI_ATTR_PASSWORD属性
	  OCI_CRED_EXT:外部认证，不需要设置用户和密码
	  OCI_DEFAULT：用户会话环境只能被指定的服务器环境句柄所设置
	  OCI_SYSDBA：用户要具有sysdba权限
	  OCI_SYSOPER：用户要具有sysoper权限
	  
	*/
	
	if(status= OCIAttrSet(svchp, OCI_HTYPE_SVCCTX,(dvoid*)authp, 0, 
		OCI_ATTR_SESSION, errhp))
	{
		MessageBox(NULL,"设置会话所使用的用户帐号失败!","初始化OIC",MB_ICONINFORMATION);		
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return ;		
	}
}


/*
 OCI函数返回值： 
OCI_SUCCESS –函数执行成功 (=0) 
OCI_SUCCESS_WITH_INFO –执行成功，但有诊断消息返回，可能是警告信息 
OCI_NO_DATA—函数执行完成，但没有其他数据 
OCI_ERROR—函数执行错误 
OCI_INVALID_HANDLE—传递给函数的参数为无效句柄，或传回的句柄无效 
OCI_NEED_DATA—需要应用程序提供运行时刻的数据 
OCI_CONTINUE—回调函数返回代码，说明回调函数需要OCI库恢复其正常的处理操作 
OCI_STILL_EXECUTING—服务环境建立在非阻塞模式，OCI函数调用正在执行中。 

  */
//对COI函数返回值进行错误判断并给出提示信息
void COCIclass::Error_proc(dvoid *errhp, sword status)
{
	text errbuf[512];
	sb4 errcode;
	CString errinfor;
	
	switch(status)
	{
	case OCI_SUCCESS://函数执行成功
		break;
	case OCI_SUCCESS_WITH_INFO:
		MessageBox(NULL,"执行成功，但有诊断消息返回，可能是警告信息","错误处理",MB_ICONINFORMATION);
		break;
	case OCI_NEED_DATA:
		MessageBox(NULL,"需要应用程序提供运行时刻的数据","错误处理",MB_ICONINFORMATION);
		break;
	case OCI_NO_DATA:
		MessageBox(NULL,"函数执行完成，但没有其他数据","错误处理",MB_ICONINFORMATION);
		break;
	case OCI_ERROR://函数执行错误
		OCIErrorGet ((dvoid *) errhp, (ub4) 1, (text *) NULL, 
			&errcode, errbuf, (ub4) sizeof(errbuf), (ub4) OCI_HTYPE_ERROR);
		errinfor.Format ("OCI 错误号: %d\n错误信息:%s",errcode,errbuf);
		MessageBox(NULL,errinfor,"错误处理",MB_ICONINFORMATION);
		break;
	case OCI_INVALID_HANDLE: 
		MessageBox(NULL,"传递给函数的参数为无效句柄，或传回的句柄无效","错误处理",MB_ICONINFORMATION);
		break;
	case OCI_STILL_EXECUTING:
		MessageBox(NULL,"服务环境建立在非阻塞模式，OCI函数调用正在执行中","错误处理",MB_ICONINFORMATION);
		break;
	default:
		break;
	}
}
/*
sword OCIErrorGet ( 
	dvoid      *hndlp, //错误句柄 
	ub4        recordno,//从那里读取错误记录，从1开始 
	text       *sqlstate,//已取消，=NULL 
	sb4        *errcodep, //错误号 
	text       *bufp,  //错误内容 
	ub4        bufsiz,  //bufp长度 
	ub4        type //传递的错误句柄类型 
	=OCI_HTYPE_ERROR:错误句柄 
	=OCI_HTYPE_ENV：环境句柄 
); 

*/
 
//以回调方式将LOB数据写入Oracle数据库中
int COCIclass::CallbackWriteToLob()
{
	ub4 offset=1,loblength=0;
	ub4 bufp[LOB_BUF_LEN];
	
	ub4 piecelen;
	ub1 *piece=0;
	ub4 *lenp=0;
	
	CString tempstr;
	
	OCILobGetLength(svchp,errhp,lob_loc[0],&loblength);//获得LOB的数据长度,存储到loblength变量中
	
	fseek(fp_OCI,0,SEEK_SET);//打开fp_OCI指针指向的文件
	
	if(Filelength_OCI>LOB_BUF_LEN) //如果文件长度大于LOB_BUF_LEN
		piecelen=LOB_BUF_LEN;  //读文件块的长度=LOB_BUF_LEN
	else  //如果文件长度小于LOB_BUF_LEN
		piecelen=Filelength_OCI; //读文件块的长度就直接等于文件长度
	
	if(fread((dvoid*)bufp,(size_t)piecelen,1,fp_OCI)!=1)//如果读错误
	{
		MessageBox(NULL,"读入数据发生错误!","回调模式写入数据",MB_ICONINFORMATION);
		return -1;
	}
	if(Filelength_OCI<LOB_BUF_LEN)   //如果文件长度小于LOB_BUF_LEN
	{		
		if(status=OCILobWrite(svchp,errhp,lob_loc[0],&Filelength_OCI,offset,(dvoid*)bufp,(ub4)piecelen,
			OCI_ONE_PIECE,(dvoid*)0,(sb4(*)(dvoid*,dvoid*,ub4*,ub1*))0,(ub2)0,
			(ub1)SQLCS_IMPLICIT)!=OCI_SUCCESS)
		{
			Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
			return -1;
		}
		
	}
	else   //如果文件长度大于LOB_BUF_LEN
	{
		if(status=OCILobWrite(svchp,errhp,lob_loc[0],&Filelength_OCI,offset,(dvoid*)bufp,
			(ub4)piecelen,OCI_FIRST_PIECE,(dvoid*)0,callback_writes_OCI,(ub2)0,SQLCS_IMPLICIT))
		{
			Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
			return -1;			
		}
	}
	
	return 0;
}
/*
OCILobGetLength(
	handle_service,//服务环境句柄
	handle_error,//错误句柄
	p_blob, //LOB定位符
	 &size //存储LOB的数据长度
	 ) 

OCILobWrite()：覆盖的方式把数据写到指定偏移量处。返回OCI_NEED_DATA表示未
写完LOB数据的全部，还应当调用此函数继续写。（使用ORACLE的流机制，最快、使用更好的空间和REDO）

*/
 
BOOL COCIclass::AddNormalDataToDB(CString strSQL)
{	
	//准备SQL语句
	if (status =OCIStmtPrepare(stmthp, errhp,(const unsigned char*)(LPCSTR)strSQL, strlen((char*)(LPCSTR)strSQL),OCI_NTV_SYNTAX,OCI_DEFAULT))
	{
		Error_proc(errhp, status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;
	}
	/*
	7．准备SQL语句 
	sword OCIStmtPrepare ( 
		OCIStmt       *stmtp,//语句句柄   
		OCIError      *errhp, //错误句柄
		CONST text    *stmt,  //SQL语句 
		ub4           stmt_len,   //语句长度 
		ub4           language,  //语句的语法格式=OCI_NTV_SYNTAX 
		ub4           mode //=OCI_DEFAULT 
		); 
	*/
	
	if (status =OCIStmtExecute(svchp, stmthp, errhp, (ub4 )1,0, NULL, NULL, OCI_DEFAULT))
	{
		Error_proc(errhp, status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;
	}
	
	if (status =OCITransCommit(svchp, errhp, (ub4)0))
	{
		Error_proc(errhp, status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;
	}

	return  TRUE;
}

 /*
 lob 操作函数OCI函数简要列表：
 
           OCILobOpen()打开内部或外部LOB
           OCILobFileOpen()打开外部LOB的文件
           OCILobClose()关闭LOB
           OCILobFileClose()完毕外部LOB的文件
           OCILobFileCloseAll()关闭所有外部LOB的文件
           OCILobGetLength()获得LOB的数据长度
           OCILobRead()读取LOB数据，可指定读取指定偏移量除的给定大小的LOB数据到内存，
		              返回OCI_NEED_DATA表示未读完LOB数据的全部，还应当调用此函数继续读。
           OCILobWrite()以覆盖的方式把数据写到指定偏移量处。返回OCI_NEED_DATA
		              表示未写完LOB数据的全部，还应当调用此函数继续写。
           OCILobWriteAppend()以追加的方式写LOB数据
           OCILobTrim()把LOB的大小截断为指定值，只能操作内部LOB
           OCILobErase()删除LOB指定偏移量的指定大小的数据。
*/
 
//将BLOB类型数据类型写入oralce数据库中
BOOL COCIclass::AddBOLBDataToDB(CString strFilename, CString strSQL,int m_ID)
{
	text *selectlocator;

	fp_OCI=fopen((const char*)(LPCSTR)strFilename,(const char *)"rb");
	fseek(fp_OCI,0,SEEK_END);
	Filelength_OCI=ftell(fp_OCI);
	if(Filelength_OCI<=0)
	{
		MessageBox(NULL,"文件长度为0,不能写入!","写入数据",MB_ICONSTOP);
		return FALSE;
	}
	selectlocator=(text*)(LPCTSTR)strSQL;
	
	
	//准备SQL语句
	if(status= OCIStmtPrepare(stmthp,errhp,selectlocator,
		(ub4)strlen((char*)selectlocator),(ub4)OCI_NTV_SYNTAX,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"准备SQL语句失败!","写入LOB数据",MB_ICONSTOP);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return  FALSE;		
	}
	
	//按位置绑定,建议一般按此方式绑定
	if(status= OCIBindByPos(stmthp,&bndhp,errhp,(ub4)1,(dvoid*)&m_ID,
		(sb4)sizeof(m_ID),SQLT_INT,(dvoid*)0,(ub2*)0,(ub2*)0,(ub4)0,(ub4*)0,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"按位置绑定失败!","写入LOB数据",MB_ICONSTOP);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE ;		
	}
	/*
	sword OCIBindByPos ( 
	OCIStmt      *stmtp, //SQL语句句柄OCIStmt
	OCIBind      **bindpp, //结合句柄 
	OCIError     *errhp, //错误句柄
	ub4          position,// 绑定的位置 
	dvoid        *valuep, //绑定的变量名
	sb4          value_sz,  //绑定的变量名长度 	
	ub2          dty, //绑定的类型
	dvoid        *indp, //指示符变量指针(sb2类型),单条绑定时为NULL, 	
	ub2          *alenp, //说明执行前后被结合的数组变量中各元素数据实际的长度，单条绑定时为NULL 	
	ub2          *rcodep, //列级返回码数据指针，单条绑定时为NULL 	
	ub4          maxarr_len, //最多的记录数,如果是单条绑定，则为0 	
	ub4          *curelep, //列级返回码数据指针，单条绑定时为NULL 	
	ub4          mode	//=OCI_DEFAULT 
	); 
	*/


	//按位置绑定,建议一般按此方式绑定
	if(status= OCIDefineByPos(stmthp,&defhp,errhp,(ub4)1,(dvoid*)&lob_loc[0],
		(sb4)-1,(ub2)SQLT_BLOB,(dvoid*)0,(ub2*)0,(ub2*)0,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"按位置定义失败!","写入LOB数据",MB_ICONSTOP);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return  FALSE;		
	}
	
	//执行SQL语句 
	if(status= OCIStmtExecute(svchp,stmthp,errhp,(ub4)1,(ub4)0,
		(CONST OCISnapshot*)0,(OCISnapshot*)0,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"执行SQL语句失败!","写入LOB数据",MB_ICONSTOP);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return  FALSE;		
	}
	
	/*
	sword OCIStmtExecute ( 
	OCISvcCtx           *svchp,  //服务环境句柄 
	OCIStmt             *stmtp,  //语句句柄 
	OCIError            *errhp, //错误句柄
	ub4                 iters, // ** 
	ub4                 rowoff, //** 
	CONST OCISnapshot   *snap_in, 
	OCISnapshot         *snap_out, 
	ub4                 mode //** 
	); 
	**注： 
	1. iters:对于select语句，它说明一次执行读取到buffer中的记录行数，如果不能确定
	select语句所返回的行数，可将iters设置为0,而对于其他的语句，iters表示这些语句
	的执行次数，此时iters不能为0。 
	2. rowoff:在多行执行时，该参数表示从所结合的数据变量中的第几条记录开始执行(即记录偏移量)。 
	3. mode：=OCI_DEFAULT:default模式 
	=OCI_DESCRIBE_ONLY：描述模式，只返回选择列表的描述信息，而不执行语句 
	=OCI_COMMIT_ON_SUCCESS：自动提交模式，当执行成功后，自动提交。 
	=OCI_EXACT_FETCH:精确提取模式。 
	=OCI_BATCH_ERRORS：批错误执行模式：用于执行数组方式的操作，在此模式下，	批量insert ,
	update,delete时，执行过程中任何一条记录错误不会导致整个insert ,update,delete失败，
	系统自动会收集错误信息，而在非批错误方式下，其中的任何一条记录错误，将会导致整个操作失败。 
	例如: 
	执行一次 
	swResult = OCIStmtExecute(svchp, stmtp,  errhp, 1, 0, NULL, NULL, OCI_DEFAULT); 
	批量执行100次： 
	swResult = OCIStmtExecute(svchp, stmtp,  errhp,100, 0, NULL, NULL, OCI_DEFAULT); 
	
	*/


	CallbackWriteToLob(); 

	/*当应用进程与服务器断开连接时，程序没有使用OCITransCommit()进行事务的
	提交，则所有活动的事务会自动回滚。*/
	if (status =OCITransCommit(svchp,errhp, (ub4)0))
	{
		Error_proc(errhp, status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;
	}
	fclose(fp_OCI);

	return TRUE;
}

 
//从数据库中读取BLOB数据
BOOL COCIclass::ReadBOLBDataFromDB(CString strFilename, CString strSQL, int m_ID)
{
	text *cp=NULL;
	text *selectlocator;
	
	selectlocator=(text*)(LPCTSTR)strSQL;
	

	//准备SQL语句
	if(status= OCIStmtPrepare(stmthp,errhp,selectlocator,
		(ub4)strlen((char*)selectlocator),(ub4)OCI_NTV_SYNTAX,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"准备SQL语句失败!","读取数据",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return  FALSE;		
	}
		
	
	//按位置绑定,建议一般按此方式绑定
	if(status= OCIBindByPos(stmthp,&bndhp,errhp,(ub4)1,(dvoid*)&m_ID,
		(sb4)sizeof(m_ID),SQLT_INT,(dvoid*)0,(ub2*)0,(ub2*)0,(ub4)0,(ub4*)0,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"按位置绑定失败!","读取数据",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;		
	}
	
	
	//定义输出变量 
	if(status= OCIDefineByPos(stmthp,&defhp,errhp,(ub4)1,(dvoid*)&lob_loc[0],
		(sb4)-1,(ub2)SQLT_BLOB,(dvoid*)0,(ub2*)0,(ub2*)0,(ub4)OCI_DEFAULT))
	{
			MessageBox(NULL,"按位置定义失败!","读取数据",MB_ICONINFORMATION);
			Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;		
	}
	/*
	sword OCIDefineByPos ( 
		OCIStmt     *stmtp, //语句句柄 
		OCIDefine   **defnpp,//定义句柄—用于数组变量 
		OCIError    *errhp, //错误句柄
		ub4         position,//位置序号(从1 开始) 
		dvoid       *valuep, //输出的变量名 
		sb4         value_sz, //变量长度 
		ub2         dty,  //数据类型 
		dvoid       *indp, //指示器变量/指示器变量数组，如果此字段可能存在空值，则要指示器变量，否则单条处理时为NULL 
		ub2         *rlenp, //提取的数据长度 
		ub2         *rcodep, //列级返回码数组指针 
		ub4         mode //OCI_DEFAULT 
	); 
	*/
	
	//执行SQL语句
	if(status= OCIStmtExecute(svchp,stmthp,errhp,(ub4)1,(ub4)0,
		(CONST OCISnapshot*)0,(OCISnapshot*)0,(ub4)OCI_DEFAULT))
	{
		MessageBox(NULL,"执行SQL语句失败!","读取数据",MB_ICONINFORMATION);
		Error_proc(errhp,status);//对COI函数返回值进行错误判断并给出提示信息
		return FALSE;		
	}

	//当应用进程与服务器断开连接时,程序没有使用OCITransCommit()
	//进行事务的提交,则所有活动的事务会自动回滚
	OCITransCommit(svchp,errhp,(ub4)OCI_DEFAULT);//提交一个事务
	/*
	sword OCITransCommit ( 
	OCISvcCtx    *svchp,  //服务环境句柄 
	OCIError     *errhp, //错误句柄
	ub4          flags ); //OCI_DEFAULT 	
		*/


	fp_OCI=fopen(strFilename,"wb"); //打开外部文件
	BOOL tb=TRUE;
	if(fp_OCI)
	{
		tb=CallbackReadLob();
	} 
	fclose(fp_OCI);	//关闭文件
	return tb;

	
}

BOOL COCIclass::CallbackReadLob()
{
	ub4 offset=1,amtp=0;
	ub1 bufp[LOB_BUF_LEN];
	sword status;
	CString tt;
	ub4 loblengtht=0;	

	//获得LOB的数据长度,存储到loblength变量中
	OCILobGetLength(svchp,errhp,lob_loc[0],&loblengtht);

	if(loblengtht<=0) //如果LOB的数据长度<=0，返回错误，
		return FALSE;

	amtp=loblengtht;
	//读取LOB数据
	if(status=OCILobRead(svchp,errhp,lob_loc[0],&amtp,offset,(dvoid*)bufp,
		(ub4)LOB_BUF_LEN,(dvoid*)0,callback_Read_OCI,(ub2)0,(ub1)SQLCS_IMPLICIT))
	{
		return FALSE;
		MessageBox(NULL,"错误数据!","读取LOB数据",MB_ICONSTOP);
	}	
	return TRUE;
}
/*
OCILobRead(handle_service,handle_error,p_blob,&amt,1,read_buf+offset-1, (ub4)LOB_BUF_LEN,NULL,NULL …); 
//加亮的1表示第一次读的位移，在流模式下，此参数和amt一样，只有在第一次读时有效 
//加亮的NULL表示回调函数为空，这样流模式使用轮询（polling）方法而不是回调（callback）方法 
//轮询方法下，如果没有读到blob末尾，函数返回OCI_NEED_DATA，读取完毕，返回OCI_SUCCESS 
如果读取完毕（OCI_NEED_DATA），跳出循环 

*/