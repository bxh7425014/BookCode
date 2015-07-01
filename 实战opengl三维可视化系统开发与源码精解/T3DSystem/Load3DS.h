 
 

#if !defined(AFX_LOAD3DS_H__69E7E7AC_7A07_4479_9687_AC19CED0E3CF__INCLUDED_)
#define AFX_LOAD3DS_H__69E7E7AC_7A07_4479_9687_AC19CED0E3CF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "vector.h"
 
#include <vector>
using namespace std;


 
//  基本块(Primary Chunk)，位于文件的开始
#define PRIMARY       0x4D4D

//  主块(Main Chunks)
#define OBJECTINFO    0x3D3D				// 网格对象的版本号
#define VERSION       0x0002				// .3ds文件的版本
#define EDITKEYFRAME  0xB000				// 所有关键帧信息的头部

 
//  对象的次级定义(包括对象的材质和对象）
#define MATERIAL	  0xAFFF				// 保存纹理信息
#define OBJECT		  0x4000				// 保存对象的面、顶点等信息

 
//  材质的次级定义
#define MATNAME       0xA000				// 保存材质名称
#define MATDIFFUSE    0xA020				// 对象/材质的颜色
#define MATMAP        0xA200				// 新材质的头部
#define MATMAPFILE    0xA300				// 保存纹理的文件名
 
//  材质的光照定义
#define  MAT_AMBIENT  0xA010
#define  MAT_SPECULAR 0xA030
#define  MAT_EMISSIVE 0xA040
 
#define OBJECT_MESH   0x4100			// 新的网格对象	
 
//  OBJECT_MESH的次级定义
#define OBJECT_VERTICES     0x4110			// 对象顶点
#define OBJECT_FACES		0x4120			// 对象的面
#define OBJECT_MATERIAL		0x4130			// 对象的材质
#define OBJECT_UV			0x4140			// 对象的UV纹理坐标


 

// CLoad3DS类处理所有的装入代码
class CLoad3DS
{
 
public:
	
	
	struct tIndices {							

		unsigned short a, b, c, bVisible;		
	};

	// 保存块信息的结构
	struct tChunk
	{
		unsigned short int ID;					// 块的ID		
		unsigned int length;					// 块的长度
		unsigned int bytesRead;					// 需要读的块数据的字节数
	};

	// 保存面信息的结构
	struct tFace
	{
		int vertIndex[3];			// 顶点索引
		int coordIndex[3];			// 纹理坐标索引
	};

	// 保存材质信息的结构体
	struct tMaterialInfo
	{
		char  strName[255];		// 纹理名称	
		char  strFile[255];		// 如果存在纹理映射，则表示纹理文件名称	
		
		BYTE  color[3];			// 对象的RGB颜色	
		
		float  ambient[4];		//材质的环境光		
		float  diffuse[4];		//材质的出射光		
		float  specular[4];		//材质的镜面光		
		float  emissive[4];		//材质的漫反射光		
		
		int   texureId;			// 纹理ID	
		float uTile;			// u 重复	
		float vTile;			// v 重复	
		float uOffset;			// u 纹理偏移   
		float vOffset;			// v 纹理偏移	
	} ;
						
	
	class tMatREF
	{
	public:
		int nMaterialID;
		USHORT *pFaceIndexs;
		int nFaceNum;
		bool bHasTexture;
	public:
		tMatREF()
		{
			nMaterialID=-1;
			nFaceNum=0;
			pFaceIndexs=NULL;
			bHasTexture=false;
		}
	};

	
	
	// 对象信息的结构
	struct t3DObject 
	{
		int  numOfVerts;			// 模型中顶点的数目
		int  numOfFaces;			// 模型中面的数目
		int  numTexVertex;			// 模型中纹理坐标的数目
		int  numOfMaterials;         // 模型中材质的数目
		int  materialID;			// 纹理ID
		bool bHasTexture;			// 是否具有纹理映射
		char strName[255];			// 对象的名称
		CVector3  *pVerts;			// 对象的顶点
		CVector3  *pNormals;		// 对象的法向量
		CVector2  *pTexVerts;		// 纹理UV坐标
		tFace *pFaces;				// 对象的面信息
		tMatREF	      *pMaterialREFS;
		double m_minX;  //对象的最小x坐标
		double m_maxX;	//对象的最大x坐标
		double m_minY;	//对象的最小y坐标
		double m_maxY;	//对象的最大y坐标
		double m_minZ;	//对象的最小z坐标
		double m_maxZ;	//对象的最大z坐标
	};

	//  模型信息结构体
	struct t3DModel 
	{
		int numOfObjects;			// 模型中对象的数目		
		int numOfMaterials;			// 模型中材质的数目		
		vector<tMaterialInfo> vctMaterials;	// 材质链表信息
		vector<t3DObject> vctObjects;		// 模型中对象链表信息
	};

public:
	void Bytes2Floats(BYTE *pbs,float *pfs,int num,float fsk);//字节数组到浮点转化 	
	
	CLoad3DS();			// 初始化数据成员						
	
	~CLoad3DS();
	bool Import3DS(t3DModel *pModel, char *strFileName);// 装入3ds文件到模型结构中
private:
	int GetString(char *);//读入一个字符串
	void ReadChunk(tChunk *);//读入块的ID号和它的字节长度
	void ProcessNextChunk(t3DModel *pModel, tChunk *);//读出3ds文件的主要部分(是个递归函数)
	void ProcessNextObjectChunk(t3DModel *pModel, t3DObject *pObject, tChunk *);//处理所有的文件中对象的信息
	void ProcessNextMaterialChunk(t3DModel *pModel, tChunk *);//处理所有的材质信息
	void ReadColorChunk(tMaterialInfo *pMaterial, tChunk *pChunk,USHORT typeFlag=0);// 读对象颜色的RGB值
	void ReadVertices(t3DObject *pObject, tChunk *);	// 读对象的顶点
	void ReadVertexIndices(t3DObject *pObject, tChunk *);// 读对象的面信息
	void ReadUVCoordinates(t3DObject *pObject, tChunk *);	// 读对象的纹理坐标
	void ReadObjectMaterial(t3DModel *pModel, t3DObject *pObject, tChunk *pPreviousChunk,vector<tMatREF*> *pvmatids);	// 读赋予对象的材质名称
	void ComputeNormals(t3DModel *pModel);// 计算对象顶点的法向量
	void CleanUp();// 关闭文件，释放内存空间
	
	FILE *m_FilePointer;// 文件指针
};


#endif // !defined(AFX_LOAD3DS_H__69E7E7AC_7A07_4479_9687_AC19CED0E3CF__INCLUDED_)
