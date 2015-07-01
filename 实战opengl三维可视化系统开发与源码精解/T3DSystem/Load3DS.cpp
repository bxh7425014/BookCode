// Load3DS.cpp: implementation of the CLoad3DS class.
 
#include "stdafx.h"
#include "Load3DS.h"

static int gBuffer[50000] = {0};					
 

CLoad3DS::CLoad3DS()
{
	m_FilePointer = NULL;
}

CLoad3DS::~CLoad3DS()
{
	m_FilePointer = NULL;
}

 
 
//  打开一个3ds文件，读出其中的内容，并释放内存
bool CLoad3DS::Import3DS(CLoad3DS::t3DModel *pModel, char *strFileName)
{
	char strMessage[255] = {0};
	tChunk currentChunk = {0};
	int i=0;

	// 打开一个3ds文件
	m_FilePointer = fopen(strFileName, "rb");

	
	// 确保所获得的文件指针合法
	if(!m_FilePointer) 
	{
		sprintf(strMessage, "找不到文件: %s!", strFileName);
		MessageBox(NULL, strMessage, "Error", MB_OK);
		return false;
	}

	// 当文件打开之后，首先应该将文件最开始的数据块读出以判断是否是一个3ds文件
	// 如果是3ds文件的话，第一个块ID应该是PRIMARY
	
	// 将文件的第一块读出并判断是否是3ds文件
	ReadChunk(&currentChunk);

	// 确保是3ds文件
	if (currentChunk.ID != PRIMARY)
	{
		sprintf(strMessage, "Unable to load PRIMARY chuck from file: %s!", strFileName);
		MessageBox(NULL, strMessage, "Error", MB_OK);
		return false;
	}

	
	// 现在开始读入数据，ProcessNextChunk()是一个递归函数
	
	// 通过调用下面的递归函数，将对象读出
	ProcessNextChunk(pModel, &currentChunk);

	
	// 在读完整个3ds文件之后，计算顶点的法线
	ComputeNormals(pModel);

	// 释放内存空间
	CleanUp();

	return true;
}


//  下面的函数释放所有的内存空间，并关闭文件
void CLoad3DS::CleanUp()
{
	if (m_FilePointer) 
	{
		fclose(m_FilePointer);		// 关闭当前的文件指针				
		m_FilePointer = NULL;  //文件指针为空
	}
}

 
//  下面的函数读出3ds文件的主要部分(是个递归函数)
void CLoad3DS::ProcessNextChunk(CLoad3DS::t3DModel *pModel, tChunk *pPreviousChunk)
{
	t3DObject newObject = {0};			// 用来添加到对象链表		
	tMaterialInfo newTexture = {0};		// 用来添加到材质链表		

	tChunk currentChunk = {0};			// 用来添加到当前块链表		
	tChunk tempChunk = {0};			// 用来添加到临时块链表		

	//  下面每读一个新块，都要判断一下块的ID，如果该块是需要的读入的，则继续进行
	//  如果是不需要读入的块，则略过

	// 继续读入子块，直到达到预定的长度
	while (pPreviousChunk->bytesRead < pPreviousChunk->length)
	{
		
		ReadChunk(&currentChunk);	// 读入下一个块

		// 判断块的ID号
		switch (currentChunk.ID)
		{
		case VERSION:		// 文件版本号					
			// 在该块中有一个无符号短整型数保存了文件的版本
			
			// 读入文件的版本号，并将字节数添加到bytesRead变量中	
			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
		
			// 如果文件版本号大于3，给出一个警告信息
			if ((currentChunk.length - currentChunk.bytesRead == 4) && (gBuffer[0] > 0x03)) 
			{
				MessageBox(NULL, "3DS文件版本号大于3，有可能读取不正确！", "警告", MB_OK);
			}
			break;

		case OBJECTINFO:		 // 网格版本信息				
				
			ReadChunk(&tempChunk);// 读入下一个块
			
			// 获得网格的版本号
			tempChunk.bytesRead += fread(gBuffer, 1, tempChunk.length - tempChunk.bytesRead, m_FilePointer);

			// 增加读入的字节数
			currentChunk.bytesRead += tempChunk.bytesRead;

			// 进入下一个块
			ProcessNextChunk(pModel, &currentChunk);
			break;
		case MATERIAL:			// 材质信息				
	
			// 材质的数目递增
			pModel->numOfMaterials++;
			
			// 在纹理链表中添加一个空白纹理结构
			pModel->vctMaterials.push_back(newTexture);
		
			// 进入材质装入函数
			ProcessNextMaterialChunk(pModel, &currentChunk);
		
			break;

		case OBJECT:			// 对象的名称			
			// 该块是对象信息块的头部，保存了对象了名称
			
			pModel->numOfObjects++;// 对象数递增
		
			// 添加一个新的tObject节点到对象链表中
			pModel->vctObjects.push_back(newObject); 
		
			// 初始化对象和它的所有数据成员
			memset(&(pModel->vctObjects[pModel->numOfObjects - 1]), 0, sizeof(t3DObject));

			// 获得并保存对象的名称，然后增加读入的字节数
			currentChunk.bytesRead += GetString(pModel->vctObjects[pModel->numOfObjects - 1].strName);
			
			// 进入其余的对象信息的读入
			ProcessNextObjectChunk(pModel, &(pModel->vctObjects[pModel->numOfObjects - 1]), &currentChunk);
			break;

		case EDITKEYFRAME:
			// 跳过关键帧块的读入，增加需要读入的字节数
			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;

		default: 
			//  跳过所有忽略的块的内容的读入，增加需要读入的字节数
			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;
		}
	
		// 增加从最后块读入的字节数
		pPreviousChunk->bytesRead += currentChunk.bytesRead;
	}

}

//  下面的函数处理所有的文件中对象的信息
void CLoad3DS::ProcessNextObjectChunk(CLoad3DS::t3DModel *pModel, CLoad3DS::t3DObject *pObject, CLoad3DS::tChunk *pPreviousChunk)
{
	
	tChunk currentChunk = {0};// 用来添加到当前块链表		
	vector<tMatREF*> vctMatIDS;
	
	// 继续读入块的内容直至本子块结束
	while (pPreviousChunk->bytesRead < pPreviousChunk->length)
	{
		// 读入下一个块
		ReadChunk(&currentChunk);

		// 区别读入是哪种块
		switch (currentChunk.ID)
		{
		case OBJECT_MESH:	 	// 正读入的是一个新块				
		
			// 使用递归函数调用，处理该新块
			ProcessNextObjectChunk(pModel, pObject, &currentChunk);
			break;

		case OBJECT_VERTICES:		// 读入是对象顶点		
			ReadVertices(pObject, &currentChunk);// 读对象顶点
			break;

		case OBJECT_FACES:		// 读入的是对象的面			
			ReadVertexIndices(pObject, &currentChunk);// 读入对象的面
			break;

		case OBJECT_MATERIAL:			// 读入的是对象的材质名称		
			// 该块保存了对象材质的名称，可能是一个颜色，也可能是一个纹理映射。同时在该块中也保存了
			// 纹理对象所赋予的面
			
			// 下面读入对象的材质名称
			ReadObjectMaterial(pModel, pObject, &currentChunk,&vctMatIDS);			
			break;

		case OBJECT_UV:				// 读入对象的UV纹理坐标			

			// 读入对象的UV纹理坐标
			ReadUVCoordinates(pObject, &currentChunk);
			break;

//		case 0x4111: 
//			
//			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
//			break;
		default:   	// 略过不需要读入的块
			
			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;
		}

		// 添加从最后块中读入的字节数到前面的读入的字节中
		pPreviousChunk->bytesRead += currentChunk.bytesRead;

	}
	if(pPreviousChunk->ID!=OBJECT_MESH) return;
	
	int size=vctMatIDS.size();
	if(size)
	{
		pObject->numOfMaterials=size; //对象的材质数量
		pObject->pMaterialREFS=new tMatREF[size];
		for(int i=0;i<size;i++)
		{
			pObject->pMaterialREFS[i]=*(vctMatIDS[i]);
		}
		vctMatIDS.clear();
	}
}


//  下面的函数处理所有的材质信息
void CLoad3DS::ProcessNextMaterialChunk(CLoad3DS::t3DModel *pModel, CLoad3DS::tChunk *pPreviousChunk)
{
	
	tChunk currentChunk = {0};// 用来添加到当前块链表	

	// 继续读入这些块，直到该子块结束
	while (pPreviousChunk->bytesRead < pPreviousChunk->length)
	{
		// 读入下一块
		ReadChunk(&currentChunk);

		// 判断读入的是什么块
		switch (currentChunk.ID)
		{
		case MATNAME:					// 材质的名称			
			
			// 读入材质的名称
			currentChunk.bytesRead += fread(pModel->vctMaterials[pModel->numOfMaterials - 1].strName, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;
			
		case MAT_AMBIENT:		//材质的环境光
			ReadColorChunk(&(pModel->vctMaterials[pModel->numOfMaterials - 1]), &currentChunk,MAT_AMBIENT);
			break;
		case MAT_SPECULAR:		//材质的镜面光
			ReadColorChunk(&(pModel->vctMaterials[pModel->numOfMaterials - 1]), &currentChunk,MAT_SPECULAR);
			break;
		case MAT_EMISSIVE:		 //材质的出射光
			ReadColorChunk(&(pModel->vctMaterials[pModel->numOfMaterials - 1]), &currentChunk,MAT_EMISSIVE);
			break;
		case MATDIFFUSE:		//材质的漫反射光				
			ReadColorChunk(&(pModel->vctMaterials[pModel->numOfMaterials - 1]), &currentChunk,MATDIFFUSE);
			break;
		
		case MATMAP:			// 纹理信息的头部					
			
			// 进入下一个材质块信息
			ProcessNextMaterialChunk(pModel, &currentChunk);
			break;

		case MATMAPFILE:	// 材质文件的名称					

			// 读入材质的文件名称
			currentChunk.bytesRead += fread(pModel->vctMaterials[pModel->numOfMaterials - 1].strFile, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;
		
		default:  

			// 掠过不需要读入的块
			currentChunk.bytesRead += fread(gBuffer, 1, currentChunk.length - currentChunk.bytesRead, m_FilePointer);
			break;
		}

		// 添加从最后块中读入的字节数
		pPreviousChunk->bytesRead += currentChunk.bytesRead;
	}
}

//  下面函数读入块的ID号和它的字节长度
void CLoad3DS::ReadChunk(CLoad3DS::tChunk *pChunk)
{
	// 读入块的ID号，占用了2个字节。块的ID号象OBJECT或MATERIAL一样，说明了在块中所包含的内容
	pChunk->bytesRead = fread(&pChunk->ID, 1, 2, m_FilePointer);

	// 然后读入块占用的长度，包含了四个字节
	pChunk->bytesRead += fread(&pChunk->length, 1, 4, m_FilePointer);
}


//  下面的函数读入一个字符串
int CLoad3DS::GetString(char *pBuffer)
{
	int index = 0;

	
	// 读入一个字节的数据
	fread(pBuffer, 1, 1, m_FilePointer);

	// 直到结束
	while (*(pBuffer + index++) != 0) 
	{
		// 读入一个字符直到NULL
		fread(pBuffer + index, 1, 1, m_FilePointer);
	}

	// 返回字符串的长度
	return strlen(pBuffer) + 1;
}


//  下面的函数读入RGB颜色
void CLoad3DS::ReadColorChunk(CLoad3DS::tMaterialInfo *pMaterial, CLoad3DS::tChunk *pChunk,USHORT typeFlag)
{
	tChunk tempChunk = {0};
	BYTE btmp[3];

	// 读入颜色块信息
	ReadChunk(&tempChunk);
	switch(typeFlag)
	{
	case MAT_AMBIENT://材质的环境光颜色
		tempChunk.bytesRead += fread(btmp, 1, tempChunk.length - tempChunk.bytesRead, m_FilePointer);
		Bytes2Floats(btmp,pMaterial->ambient,3,1.0f/256.0f);
		pMaterial->ambient[3]=1.0f;
		break;		
	case MAT_SPECULAR://材质的镜面光颜色
		tempChunk.bytesRead += fread(btmp, 1, tempChunk.length - tempChunk.bytesRead, m_FilePointer);
		Bytes2Floats(btmp,pMaterial->specular,3,1.0f/256.0f);
		pMaterial->specular[3]=1.0f;
		break;
	case MAT_EMISSIVE://材质的出射光颜色
		tempChunk.bytesRead += fread(btmp, 1, tempChunk.length - tempChunk.bytesRead, m_FilePointer);
		
		memset(btmp,0,3);
		
		Bytes2Floats(btmp,pMaterial->emissive,3,1.0f/256.0f);
		pMaterial->emissive[3]=1.0f;
		break;
	case MATDIFFUSE://材质的漫反射光颜色
	default:
		
		tempChunk.bytesRead += fread(pMaterial->color, 1, tempChunk.length - tempChunk.bytesRead, m_FilePointer);
		Bytes2Floats(pMaterial->color,pMaterial->diffuse,3,1.0f/256.0f);
		pMaterial->diffuse[3]=1.0f;
		break;
	}

	// 增加读入的字节数
	pChunk->bytesRead += tempChunk.bytesRead;
}

//  下面的函数读入顶点索引
void CLoad3DS::ReadVertexIndices(CLoad3DS::t3DObject *pObject, CLoad3DS::tChunk *pPreviousChunk)
{
	unsigned short index = 0;		// 用于读入当前面的索引				

	// 读入该对象中面的数目
	pPreviousChunk->bytesRead += fread(&pObject->numOfFaces, 1, 2, m_FilePointer);

	// 分配所有面的存储空间，并初始化结构
	pObject->pFaces = new tFace [pObject->numOfFaces];
	memset(pObject->pFaces, 0, sizeof(tFace) * pObject->numOfFaces);

	
	// 遍历对象中所有的面
	for(int i = 0; i < pObject->numOfFaces; i++)
	{
		
		
		for(int j = 0; j < 4; j++)
		{
			// 读入当前面的第一个点
			pPreviousChunk->bytesRead += fread(&index, 1, sizeof(index), m_FilePointer);

			if(j < 3)
			{
				// 将索引保存在面的结构中
				pObject->pFaces[i].vertIndex[j] = index;
			}
		}
	}
}


 
//  下面的函数读入对象的UV坐标
void CLoad3DS::ReadUVCoordinates(t3DObject *pObject, tChunk *pPreviousChunk)
{
	
	// 为了读入对象的UV坐标，首先需要读入UV坐标的数量，然后才读入具体的数据
	
	// 读入UV坐标的数量
	pPreviousChunk->bytesRead += fread(&pObject->numTexVertex, 1, 2, m_FilePointer);

	// 分配保存UV坐标的内存空间
	pObject->pTexVerts = new CVector2 [pObject->numTexVertex];

	// 读入纹理坐标
	pPreviousChunk->bytesRead += fread(pObject->pTexVerts, 1, pPreviousChunk->length - pPreviousChunk->bytesRead, m_FilePointer);
}


//  读入对象的顶点坐标
void CLoad3DS::ReadVertices(CLoad3DS::t3DObject *pObject, CLoad3DS::tChunk *pPreviousChunk)
{
	
	
	// 在读入实际的顶点之前，首先必须确定需要读入多少个顶点。
	
	// 读入顶点的数目
	pPreviousChunk->bytesRead += fread(&(pObject->numOfVerts), 1, 2, m_FilePointer);

	// 分配顶点的存储空间，然后初始化结构体
	pObject->pVerts = new CVector3 [pObject->numOfVerts];
	memset(pObject->pVerts, 0, sizeof(CVector3) * pObject->numOfVerts);

	
	// 读入顶点序列
	pPreviousChunk->bytesRead += fread(pObject->pVerts, 1, pPreviousChunk->length - pPreviousChunk->bytesRead, m_FilePointer);

	
	
   //初始化对象的最小和最大x,y,z坐标
	pObject->m_minX=999999; pObject->m_maxX=-99999;
	pObject->m_minY=999999; pObject->m_maxY=-99999;
	pObject->m_minZ=999999; pObject->m_maxZ=-99999;

	// 现在已经读入了所有的顶点。
	// 因为3D Studio Max的模型的Z轴是指向上的，因此需要将y轴和z轴翻转过来。
	// 具体的做法是将Y轴和Z轴交换，然后将Z轴反向。
	
	// 遍历所有的顶点
	for(int i = 0; i < pObject->numOfVerts; i++)
	{
		
		// 保存Y轴的值
		float fTempY = pObject->pVerts[i].y;

		
		// 设置Y轴的值等于Z轴的值
		pObject->pVerts[i].y = pObject->pVerts[i].z;

		// 设置Z轴的值等于-Y轴的值
		pObject->pVerts[i].z = -fTempY;

		//计算对象的最小和最大x,y,z坐标
		if(pObject->m_minX>pObject->pVerts[i].x) pObject->m_minX=pObject->pVerts[i].x;
		if(pObject->m_maxX<pObject->pVerts[i].x) pObject->m_maxX=pObject->pVerts[i].x;
		if(pObject->m_minY>pObject->pVerts[i].y) pObject->m_minY=pObject->pVerts[i].y;
		if(pObject->m_maxY<pObject->pVerts[i].y) pObject->m_maxY=pObject->pVerts[i].y;
		if(pObject->m_minZ>pObject->pVerts[i].z) pObject->m_minZ=pObject->pVerts[i].z;
		if(pObject->m_maxZ<pObject->pVerts[i].z) pObject->m_maxZ=pObject->pVerts[i].z;
		
	}
}

//  下面的函数读入对象的材质名称
void CLoad3DS::ReadObjectMaterial(CLoad3DS::t3DModel *pModel,CLoad3DS::t3DObject *pObject, CLoad3DS::tChunk *pPreviousChunk,vector<tMatREF*> *pvmatids)
{
	char strMaterial[255] = {0};	// 用来保存对象的材质名称		
	bool bmaterror=true;

	tMatREF *pMatref;
	pMatref=new tMatREF;
	
	// 材质或者是颜色，或者是对象的纹理，也可能保存了象明亮度、发光度等信息。
	
	// 下面读入赋予当前对象的材质名称	
	pPreviousChunk->bytesRead += GetString(strMaterial);


	// 遍历所有的纹理
	for(int i = 0; i < pModel->numOfMaterials; i++)
	{
		//如果读入的纹理与当前的纹理名称匹配
		if(strcmp(strMaterial, pModel->vctMaterials[i].strName) == 0)
		{
			// 设置材质ID
			pObject->materialID = i;
			pMatref->nMaterialID=i;
			
			// 判断是否是纹理映射，如果strFile是一个长度大于1的字符串，则是纹理
			if(strlen(pModel->vctMaterials[i].strFile) > 0) 
			{
				// 设置对象的纹理映射标志
				pObject->bHasTexture = true;
				pMatref->bHasTexture=true;
			}	
			bmaterror=false;
			break;
		}
		else
		{
			// 如果该对象没有材质，则设置ID为-1
			pObject->materialID = -1;
			pMatref->nMaterialID=-1;
			bmaterror=true;
		}
	}
 	
	pPreviousChunk->bytesRead += fread(gBuffer, 1, pPreviousChunk->length - pPreviousChunk->bytesRead, m_FilePointer);
	if(!bmaterror)
	{
		pMatref->nFaceNum=gBuffer[0]&0x0000FFFF;
		pMatref->pFaceIndexs=new USHORT[pMatref->nFaceNum];
		memcpy(pMatref->pFaceIndexs,2+(BYTE*)gBuffer,pMatref->nFaceNum*sizeof(USHORT));
		
		pvmatids->push_back(pMatref);
	}
 
}			

 
 
//  下面的函数用于计算对象的法向量
void CLoad3DS::ComputeNormals(CLoad3DS::t3DModel *pModel)
{
	CVector3 vVector1, vVector2, vNormal, vPoly[3];

	// 如果模型中没有对象，则返回	
	if(pModel->numOfObjects <= 0)
		return;

	
	// 遍历模型中所有的对象
	for(int index = 0; index < pModel->numOfObjects; index++)
	{
		
		// 获得当前的对象
		t3DObject *pObject = &(pModel->vctObjects[index]);

		// 分配需要的存储空间
		CVector3 *pNormals		= new CVector3 [pObject->numOfFaces];
		CVector3 *pTempNormals	= new CVector3 [pObject->numOfFaces];
		pObject->pNormals		= new CVector3 [pObject->numOfVerts];

		// 遍历对象的所有面
		for(int i=0; i < pObject->numOfFaces; i++)
		{												
			
			vPoly[0] = pObject->pVerts[pObject->pFaces[i].vertIndex[0]];
			vPoly[1] = pObject->pVerts[pObject->pFaces[i].vertIndex[1]];
			vPoly[2] = pObject->pVerts[pObject->pFaces[i].vertIndex[2]];

			
			// 计算面的法向量
			vVector1 = Vector(vPoly[0], vPoly[2]);	// 获得多边形的矢量	
			vVector2 = Vector(vPoly[2], vPoly[1]);// 获得多边形的第二个矢量		

			vNormal  = Cross(vVector1, vVector2);		// 获得两个矢量的叉积	
			pTempNormals[i] = vNormal;				// 保存非规范化法向量	
			vNormal  = Normalize(vNormal);		// 规范化获得的叉积		

			pNormals[i] = vNormal;				// 将法向量添加到法向量列表中			
		}

		
		//  下面求顶点法向量
		CVector3 vSum (0.0, 0.0, 0.0);
		CVector3 vZero = vSum;
		int shared=0;

		// 遍历所有的顶点
		for (i = 0; i < pObject->numOfVerts; i++)			
		{
			for (int j = 0; j < pObject->numOfFaces; j++)	// 遍历所有的三角形面
			{												
				// 判断该点是否与其它的面共享
				if (pObject->pFaces[j].vertIndex[0] == i || 
					pObject->pFaces[j].vertIndex[1] == i || 
					pObject->pFaces[j].vertIndex[2] == i)
				{
					vSum =vSum+pTempNormals[j];
					shared++;								
				}
			}      
			
			
			pObject->pNormals[i] = vSum/float(-shared);

			// 规范化最后的顶点法向
			pObject->pNormals[i] = Normalize(pObject->pNormals[i]);	

			vSum = vZero;									
			shared = 0;										
		}
	
		// 释放存储空间，开始下一个对象
		delete [] pTempNormals;
		delete [] pNormals;
	}
}

//字节数组到浮点转化 
void CLoad3DS::Bytes2Floats(BYTE *pbs, float *pfs, int num, float fsk)
{
	if(num==0||num>100)return;
	for(int i=0;i<num;i++)
	{
		pfs[i]=float(pbs[i])*fsk;
	}
}


/*
值得注意的是OpenGL的坐标系和3DS Max的坐标系是不同的，3D Studio Max中的模型的Z轴是指向上的，
而OpenGL中模型的Z轴是垂直屏幕指向用户的，因此需要将顶点的坐标的y和z翻转过来。
什么是块？
块ID是标识该块中数据类型的独一无二的代码，同时也标识是否存在子块。它占用了两个字节。
块的长度表示的是紧跟在该块后续的数据的长度。它占用了四个字节。

*/