package wyf.zs;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public class Spheroid {
	private IntBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private IntBuffer   mColorBuffer;//顶点着色数据缓冲
    private ByteBuffer  mIndexBuffer;//顶点构建索引数据缓冲  
    public float moveX=3.01f;
    
    int vCount=0;
    int iCount=0;
    
    public Spheroid(int scale)
    {
    	//顶点坐标数据的初始化================begin============================
    	final double a=2;
    	final double b=1.5;
    	final double c=1.5;
    	final int UNIT_SIZE=10000;
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//存放顶点坐标的ArrayList
    	final int angleSpan=18;//将球进行单位切分的角度
        for(int vAngle=-90;vAngle<=90;vAngle=vAngle+angleSpan)//垂直方向angleSpan度一份
        {
        	for(int hAngle=0;hAngle<360;hAngle=hAngle+angleSpan)//水平方向angleSpan度一份,0与360为同一点
        	{//纵向横向各到一个角度后计算对应的此点在球面上的坐标    		
        		int x=(int)(a*scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle)));
        		int y=(int)(b*scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle)));
        		int z=(int)(c*scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		//将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
        		alVertix.add(x);alVertix.add(y);alVertix.add(z);
        	}
        } 	
        vCount=alVertix.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
    	
        //将alVertix中的坐标值转存到一个int数组中
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asIntBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================
        
        //顶点着色数据的初始化================begin============================
        final int one = 65535;
        int colors[]=new int[vCount*4];//顶点颜色值数组，每个顶点4个色彩值RGBA
        for(int i=0;i<vCount;i++)
        {//随机生成每个顶点的颜色
        	colors[i*4]=(int) (one*Math.random());//红色通道
        	colors[i*4+1]=(int) (one*Math.random());//绿色通道
        	colors[i*4+2]=(int) (one*Math.random());//蓝色通道
        	colors[i*4+3]=0;//alpha色彩通道
        }
        
        //创建顶点着色数据缓冲
        //vertices.length*4是因为一个int型整数四个字节
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mColorBuffer = cbb.asIntBuffer();//转换为int型缓冲
        mColorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        mColorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点着色数据的初始化================end============================
        
        //三角形构造索引数据初始化==========begin==========================
        ArrayList<Integer> alIndex=new ArrayList<Integer>();
        int row=(180/angleSpan)+1;//球面切分的行数
        int col=360/angleSpan;//球面切分的列数
        for(int i=0;i<row;i++)//对每一行循环
        {
        	if(i>0&&i<row-1)
        	{//中间行
        		for(int j=-1;j<col;j++)
				{//中间行的两个相邻点与下一行的对应点构成三角形
					int k=i*col+j;
					alIndex.add(k+col);
					alIndex.add(k+1);
					alIndex.add(k);		
				}
        		for(int j=0;j<col+1;j++)
				{//中间行的两个相邻点与上一行的对应点构成三角形				
					int k=i*col+j;
					alIndex.add(k-col);
					alIndex.add(k-1);
					alIndex.add(k);	
				}
        	}
        }
        iCount=alIndex.size();
        byte indices[]=new byte[alIndex.size()];
        for(int i=0;i<alIndex.size();i++)
        {
        	indices[i]=alIndex.get(i).byteValue();
        } 
        //创建三角形构造索引数据缓冲
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);//向缓冲区中放入三角形构造索引数据
        mIndexBuffer.position(0);//设置缓冲区起始位置
      //三角形构造索引数据初始化==========end==============================
    }

    public void drawSelf(GL10 gl)
    {    	       
        gl.glTranslatef(moveX, 0, 0);      
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FIXED,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
		
        //为画笔指定顶点着色数据
        gl.glColorPointer
        (
        		4, 				//设置颜色的组成成分，必须为4—RGBA
        		GL10.GL_FIXED, 	//顶点颜色值的类型为 GL_FIXED
        		0, 				//连续顶点着色数据之间的间隔
        		mColorBuffer	//顶点着色数据
        );
		
        //绘制图形
        gl.glDrawElements
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		iCount, 			 	//一共icount/3个三角形，iCount个顶点
        		GL10.GL_UNSIGNED_BYTE, 	//索引值的尺寸
        		mIndexBuffer			//索引值数据
        ); 
    }
}

