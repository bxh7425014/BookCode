package com.guo.myball;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

import static com.guo.myball.Constant.*;
import static com.guo.myball.GameSurfaceView.*;

//表示墙的类
public class Wall {
	//顶点坐标数据缓冲
	private FloatBuffer   mVertexBuffer;
	//顶点纹理数据缓冲
	private FloatBuffer   mTextureBuffer;
	//顶点法向量数据缓冲
	private FloatBuffer   mNormalBuffer;
	//顶点数量
	int vCount;
	//用于记录当前点是否扫描过   1 表示此点不需要在扫描   0   表示此点需要扫描
	private int[][] indexFlag;
    //构造函数
    public Wall()
    {
    	//顶点坐标数据的初始化----------begin
        int rows=MAP.length;
        int cols=MAP[0].length;
        indexFlag=new int[rows][cols];
        //顶点数组，用于存放顶点
        ArrayList<Float> alVertex=new ArrayList<Float>();
        //法向量数组，用于存放法向量
        ArrayList<Float> alNormal=new ArrayList<Float>();
        //纹理数组，用于存放纹理数据
        ArrayList<Float> alTexture=new ArrayList<Float>();
        //行扫描
        for(int i=0;i<rows;i++)
        {
        	//列扫描
        	for(int j=0;j<cols;j++)
        	{//对地图中的每一块进行处理
        		if(MAP[i][j]==1)//当前点为墙
        		{
        			// area[0]表示起始点行 列 ， area[1]表示宽度和高度
        			int [][] area=returnMaxBlock(i,j);
        			//对区域内的每个点建造围墙
        			for(int k=area[0][0];k<area[0][0]+area[1][1];k++)
        			{
        				for(int t=area[0][1];t<area[0][1]+area[1][0];t++)
        				{
        					//建造顶层墙
        					float xx1=t*UNIT_SIZE;       //    1
                			float y=FLOOR_Y+WALL_HEIGHT;
                			float zz1=k*UNIT_SIZE;
                			
                			float xx2=t*UNIT_SIZE;       //     2
                			float zz2=(k+1)*UNIT_SIZE;
                			
                			float xx3=(t+1)* UNIT_SIZE;    //   3
                			float zz3=(k+1)*UNIT_SIZE;
                			
                			float xx4=(t+1)*UNIT_SIZE;       //    4
                			float zz4=k*UNIT_SIZE;
                			//构造三角形
                			alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);
            				alVertex.add(xx2);alVertex.add(y);alVertex.add(zz2);
            				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);

            				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);
            				alVertex.add(xx4);alVertex.add(y);alVertex.add(zz4);
            				alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);
            				
            				//添加纹理   整块平铺
            				alTexture.add((float)((float)t/cols));alTexture.add((float)k/rows);
            				alTexture.add((float)((float)t/cols));alTexture.add((float)((float)(k+1)/rows));        				
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)((float)(k+1)/rows));
            				
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)((float)(k+1)/rows));
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)k/rows);
            				alTexture.add((float)((float)t/cols));alTexture.add((float)k/rows);
            				
            			

            				//建造向量
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);

            				//建造墙的上面
            				if(k==0||MAP[k-1][t]==0)
            				{
            					float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y;
                				float z1=k*UNIT_SIZE;   //  1
                				
                				float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y+WALL_HEIGHT;
                				float z2=k*UNIT_SIZE;    // 2
                				
                				float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y+WALL_HEIGHT;
                				float z3=k*UNIT_SIZE;    //  3
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y;
                				float z4=k*UNIT_SIZE;    //  4
                				//建造三角形
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//建造纹理
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)((float)1/rows));        				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)1/rows);
                                //建造向量
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
            				}
            				//建造墙的下面
            				if(k==rows-1||MAP[k+1][t]==0)
            				{
            					float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y+WALL_HEIGHT;
                				float z1=(k+1)*UNIT_SIZE;
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y+WALL_HEIGHT;
                				float z4=(k+1)*UNIT_SIZE;
                				
                				float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y;
                				float z3=(k+1)*UNIT_SIZE;
                				//建造三角形
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//建造纹理
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)((float)1/rows));        				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)1/rows);
                				//建造向量
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
            				}
            				//建造墙的左面
            				if(t==0||MAP[k][t-1]==0)
            				{
            					float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x3=t*UNIT_SIZE;
                				float y3=FLOOR_Y+WALL_HEIGHT;
                				float z3=(k+1)*UNIT_SIZE;
                				
                				float x4=t*UNIT_SIZE;
                				float y4=FLOOR_Y+WALL_HEIGHT;
                				float z4=k*UNIT_SIZE;
                				
                				float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y;
                				float z1=k*UNIT_SIZE;
                				//建造三角形
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//建造纹理
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)((float)(k+1-area[0][0])/rows));        				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				alTexture.add((float)((float)1/cols));alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				//建造向量
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
            				}
            				//建造墙的右面
            				if(t==cols-1||MAP[k][t+1]==0)
            				{
            					float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y;
                				float z3=(k+1)*UNIT_SIZE;
                				
                				float x2=(t+1)*UNIT_SIZE;
                				float y2=FLOOR_Y+WALL_HEIGHT;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x1=(t+1)*UNIT_SIZE;
                				float y1=FLOOR_Y+WALL_HEIGHT;
                				float z1=k*UNIT_SIZE;
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y;
                				float z4=k*UNIT_SIZE;
                				//建造三角形
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//建造纹理
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)((float)(k+1-area[0][0])/rows));        				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				alTexture.add((float)((float)1/cols));alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				//建造向量
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
            				}
        				}
        			}
        		}
        	}
        }
    	vCount=alVertex.size()/3;        
        float vertices[]=new float[alVertex.size()];
        for(int i=0;i<alVertex.size();i++)
        {
        	vertices[i]=alVertex.get(i);
        }
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个float四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        //设置字节顺序
        vbb.order(ByteOrder.nativeOrder());
        //转换为float型缓冲
        mVertexBuffer = vbb.asFloatBuffer();
        //向缓冲区中放入顶点坐标数据
        mVertexBuffer.put(vertices);
        //设置缓冲区起始位置
        mVertexBuffer.position(0);
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化------------end
        
        //顶点法向量数据初始化================begin============================
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount*3;i++)
        {
        	normals[i]=alNormal.get(i);
        }
        //分配空间
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        //设置字节顺序
        nbb.order(ByteOrder.nativeOrder());
        //转换为float型缓冲
        mNormalBuffer = nbb.asFloatBuffer();
        //向缓冲区中放入顶点法向量数据
        mNormalBuffer.put(normals);
        //设置缓冲区起始位置
        mNormalBuffer.position(0);
        //顶点法向量数据初始化------------end
        
        //顶点纹理数据的初始化------------begin
        float textures[]=new float[alTexture.size()];
        for(int i=0;i<alTexture.size();i++)
        {
        	textures[i]=alTexture.get(i);
        }
        //创建顶点纹理数据缓冲
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        //设置字节顺序
        tbb.order(ByteOrder.nativeOrder());
        //转换为Float型缓冲
        mTextureBuffer= tbb.asFloatBuffer();
        //向缓冲区中放入顶点着色数据
        mTextureBuffer.put(textures);
        //设置缓冲区起始位置
        mTextureBuffer.position(0);
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理数据的初始化------------end
    }

    public void drawSelf(GL10 gl,int texId)
    {        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        
        //为画笔指定顶点法向量数据
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0,
        		vCount 
        );
    }
    //根据当前点 判断出此点周围最大的面积块数,当前点必须为墙   
    public int[][] returnMaxBlock(int rowIndex,int colIndex)
    {
    	int rowindex=rowIndex;
    	int colindex=colIndex;
    	int rowsize;//用于记录行的大小
    	int colsize;//用于记录列的大小
    
    	int [][] area=new int[2][2];//用于记录位置 ，area[0]表示起始点索引，  area[1]表示长度和宽度
    	area[0][0]=rowIndex;area[0][1]=colIndex;
    	//横向 长度为1
    	int tempRowSize=1;//宽度
    	int tempColSize=1;//长度
    	//从该索引点往右遍历，直到遇到0，得到长度
    	while(colindex+1<MAP[0].length&&MAP[rowindex][colindex+1]==1&&indexFlag[rowindex][colindex+1]==0)
    	{
    		tempColSize++;//长度加一
    		colindex++;//列索引加一
    	}
    	//存放最后索引点位置
    	rowsize=tempRowSize;colsize=tempColSize;
    	area[1][0]=colsize;area[1][1]=rowsize;
    	//将indexFlag扫描过的格子置为1
    	for(int i=area[0][0];i<area[0][0]+area[1][1];i++)
    	{
    		for(int j=area[0][1];j<area[0][1]+area[1][0];j++)
    		{
    			indexFlag[i][j]=1;//将其值设置为    1     表示不用在扫描
    		}
    	}
    	return area;
    }
}