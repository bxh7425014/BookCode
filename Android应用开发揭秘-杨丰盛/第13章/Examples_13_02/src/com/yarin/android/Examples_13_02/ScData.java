package com.yarin.android.Examples_13_02;

import java.util.ArrayList;
import java.util.List;
//VERTEX顶点结构
class VERTEX
{
	float x, y, z;// 3D 坐标
	float u, v;// 纹理坐标
	public VERTEX(float x,float y,float z,float u,float v)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.u = u;
		this.v = v;
	}
}
//TRIANGLE三角形结构
class TRIANGLE
{
	// VERTEX矢量数组，大小为3
	VERTEX[]	vertex	= new VERTEX[3];
}
//SECTOR区段结构
class SECTOR
{
	// Sector中的三角形个数
	int numtriangles;
	// 三角行的list
	List<TRIANGLE>	triangle	= new ArrayList<TRIANGLE>();
}
