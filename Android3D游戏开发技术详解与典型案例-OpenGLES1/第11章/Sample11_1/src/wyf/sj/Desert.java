package wyf.sj;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static wyf.sj.Constant.*;
import javax.microedition.khronos.opengles.GL10;

public class Desert {

	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
    int vCount=0;//顶点数量
    float yAngle;//y轴旋转角度
    int xOffset;//x偏移量
    int zOffset;//z平移量
    int texId;//纹理ID
    int width;//地板横向width个单位
    int height;//地板纵向height个单位
    
    public Desert(int xOffset,int zOffset,float scale,float yAngle,int texId,int width,int height)
    {
    	this.xOffset=xOffset;//获取引用
    	this.zOffset=zOffset;//获取引用
    	this.yAngle=yAngle;//获取引用
    	this.texId=texId;//获取引用
    	this.width=width;//获取引用
    	this.height=height;//获取引用
    	
    	//顶点坐标数据的初始化================begin============================
        vCount=width*height*6;//每个地板块6个顶点
       
        float vertices[]=new float[vCount*3];
        int k=0;                           
        for(int i=0;i<width;i++)
        	for(int j=0;j<height;j++)
	        {//每个地板块由两个三角形6个顶点构成	        	
	        	vertices[k++]=i*UNIT_SIZE*scale;//第一个三角形的第一个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        	vertices[k++]=i*UNIT_SIZE*scale;//第一个三角形的第二个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;//第一个三角形的第三个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;//第二个三角形的第一个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;//第二个三角形的第二个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        	vertices[k++]=i*UNIT_SIZE*scale;//第二个三角形的第三个点坐标
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        };
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个Float四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================        
        
        
        //纹理 坐标数据初始化
        float[] texST=new float[vCount*2];
        for(int i=0;i<vCount*2/12;i++)
        {
        	texST[i*12]=0;//第一个三角形的第一个点的st坐标
        	texST[i*12+1]=0;
        	
        	texST[i*12+2]=0;//第一个三角形的第二个点的st坐标
        	texST[i*12+3]=1;
        	
        	texST[i*12+4]=1;//第一个三角形的第三个点的st坐标
        	texST[i*12+5]=1;
        	
        	texST[i*12+6]=1;//第二个三角形的第一个点的st坐标
        	texST[i*12+7]=1;
        	
        	texST[i*12+8]=1;//第二个三角形的第二个点的st坐标
        	texST[i*12+9]=0;
        	
        	texST[i*12+10]=0;//第二个三角形的第三个点的st坐标
        	texST[i*12+11]=0;
        };
        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTextureBuffer = tbb.asFloatBuffer();//转换为int型缓冲
        mTextureBuffer.put(texST);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置         
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        
        gl.glPushMatrix();//保护现场
        gl.glTranslatef(xOffset*UNIT_SIZE, 0, 0);
        gl.glTranslatef(0, 0, zOffset*UNIT_SIZE);
        gl.glRotatef(yAngle, 0, 1, 0);
        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        
        //开启纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //允许使用纹理ST坐标缓冲
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );
        
        gl.glPopMatrix();//恢复现场
    }
}
