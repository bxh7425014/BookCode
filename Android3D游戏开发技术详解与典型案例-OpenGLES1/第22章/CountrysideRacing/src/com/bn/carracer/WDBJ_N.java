package com.bn.carracer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
//纹理矩形
public class WDBJ_N {
    int roadTexId;
    int lubiaoTexId;
    int jiantouTexId;
    
    Road road;
    DrawCirque wCirque;
    DrawCirque nCirque;  
    DrawCylinder cylinder; 
    
    float xoffset;//环偏移量
    float yoffset;
    float zoffset;
    
    float w_RR;//外环半径
    float n_RR;//内环半径
    float z_R;//弯道箭头圆柱半径
    float cr=9.6f;//环圆截面半径
    float Rstart=-90f;//路标环绘制的起始角度
    float Rover=0f;//路标环绘制的结束角度
    float Cstart=50f;//环截面起始角度
    float Cover=130f;//环截面结束角度
    
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public void setTexId(int roadTexId,int lubiaoTexId,int jiantouTexId)
	{
		this.roadTexId=roadTexId;
		this.lubiaoTexId=lubiaoTexId;
		this.jiantouTexId=jiantouTexId;
	}
    
    public WDBJ_N
    (
    		float partSize,//部件尺寸
    		float roadWidth//路宽
    )
    {     	
    	xoffset=-partSize/2;
    	yoffset=(float) (-cr*Math.sin(Math.toRadians(50)));
    	zoffset=partSize/2;
    	
    	w_RR=(float) (partSize/2+roadWidth/2+cr*Math.cos(Math.toRadians(50)));
    	n_RR=(float) (partSize/2-roadWidth/2-cr*Math.cos(Math.toRadians(50)));
    	z_R=(float) (partSize/2+roadWidth/2+2*cr*Math.cos(Math.toRadians(50)));
    	
    	road=new Road(partSize,roadWidth);
    	wCirque=new DrawCirque(4.5f,10,w_RR,cr,Rstart,Rover,Cstart,Cover,15);
    	nCirque=new DrawCirque(4.5f,10,n_RR,cr,Rstart,Rover,Cstart,Cover,4);
    	cylinder=new DrawCylinder(30f,z_R,9f,xoffset,zoffset);
    }
    
    public void drawSelf(GL10 gl)
    {
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
    	gl.glPushMatrix();
    	road.drawSelf(gl);
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();
    	gl.glTranslatef(xoffset, yoffset, zoffset);
    	wCirque.drawSelf(gl);
    	gl.glPopMatrix();
    	 
    	gl.glPushMatrix();
    	gl.glTranslatef(xoffset, yoffset, zoffset);
    	nCirque.drawSelf(gl);
    	gl.glPopMatrix();
    	
    	gl.glPushMatrix();
        cylinder.drawSelf(gl);
    	gl.glPopMatrix();
    } 
    
    
    
    
    
    //绘制圆环面的内部类
    private class Road
    {
    	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
        private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
        int vCount=0;
    	
    	public Road(float L,float W)
    	{	    	
	    	float x0=-L/2;
	    	float z0=L/2;
	    	
	    	//顶点坐标数据的初始化================begin============================
	        ArrayList<Float> alfv=new ArrayList<Float>();//顶点坐标
	        ArrayList<Float> alft=new ArrayList<Float>();//纹理坐标
	        float REPEAT=10.0f;        
	        float rn=(L-W)/2;//内圈半径
	        float rw=rn+W;//外圈半径
	        float alphaSpan=0.9f; 
	        float texCorSpan=REPEAT/(90/alphaSpan);
	        for(float alpha=0;alpha<90;alpha=alpha+alphaSpan)
	        {
	        	float c=alpha/alphaSpan;
	        	
	        	float xn1=(float)(x0+Math.sin(Math.toRadians(alpha))*rn);
	        	float zn1=(float)(z0-Math.cos(Math.toRadians(alpha))*rn);
	        	float yn1=0;
	        	float sn1=texCorSpan*c;
	        	float tn1=1.0f;
	        	
	        	float xw1=(float)(x0+Math.sin(Math.toRadians(alpha))*rw);
	        	float zw1=(float)(z0-Math.cos(Math.toRadians(alpha))*rw);
	        	float yw1=0;
	        	float sw1=texCorSpan*c;
	        	float tw1=0.0f;
	        	
	        	float xn2=(float)(x0+Math.sin(Math.toRadians(alpha+alphaSpan))*rn);
	        	float zn2=(float)(z0-Math.cos(Math.toRadians(alpha+alphaSpan))*rn);
	        	float yn2=0;
	        	float sn2=texCorSpan*(c+1);
	        	float tn2=1.0f;
	        	
	        	float xw2=(float)(x0+Math.sin(Math.toRadians(alpha+alphaSpan))*rw);
	        	float zw2=(float)(z0-Math.cos(Math.toRadians(alpha+alphaSpan))*rw);
	        	float yw2=0;
	        	float sw2=texCorSpan*(c+1);
	        	float tw2=0.0f;
	        	
	        	alfv.add(xn1);alfv.add(yn1);alfv.add(zn1);
	        	alfv.add(xn2);alfv.add(yn2);alfv.add(zn2);
	        	alfv.add(xw1);alfv.add(yw1);alfv.add(zw1);
	        	
	        	alfv.add(xw2);alfv.add(yw2);alfv.add(zw2);        	
	        	alfv.add(xw1);alfv.add(yw1);alfv.add(zw1);
	        	alfv.add(xn2);alfv.add(yn2);alfv.add(zn2);
	        	
	        	alft.add(sn1);alft.add(tn1);
	        	alft.add(sn2);alft.add(tn2);
	        	alft.add(sw1);alft.add(tw1);
	        	
	        	alft.add(sw2);alft.add(tw2);
	        	alft.add(sw1);alft.add(tw1);
	        	alft.add(sn2);alft.add(tn2);
	        }
	        
	        float vertices[]=new float[alfv.size()];
	        for(int i=0;i<alfv.size();i++)
	        {
	        	vertices[i]=alfv.get(i);
	        }
	        
	        vCount=alfv.size()/3;
			
	        //创建顶点坐标数据缓冲
	        //vertices.length*4是因为一个整数四个字节
	        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
	        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
	        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
	        mVertexBuffer.position(0);//设置缓冲区起始位置
	        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
	        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
	        //顶点坐标数据的初始化================end============================
	        
	        //纹理 坐标数据初始化
	        float[] texST=new float[alft.size()];
	        for(int i=0;i<alft.size();i++)
	        {
	        	texST[i]=alft.get(i);
	        }
	        	
	        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
	        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
	        mTextureBuffer = tbb.asFloatBuffer();//转换为int型缓冲
	        mTextureBuffer.put(texST);//向缓冲区中放入顶点着色数据
	        mTextureBuffer.position(0);//设置缓冲区起始位置         
        
	    }
	
	    public void drawSelf(GL10 gl)
	    {        
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
	
	        
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
	        gl.glBindTexture(GL10.GL_TEXTURE_2D, roadTexId);
			
	        //绘制图形
	        gl.glDrawArrays
	        (
	        		GL10.GL_TRIANGLES, 		//以三角形方式填充
	        		0, 			 			//开始点编号
	        		vCount					//顶点的数量
	        );
	        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	        gl.glDisable(GL10.GL_TEXTURE_2D);
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	    }
    }
    
    
    //绘制圆环体的内部类
    private class DrawCirque
    {
    	private FloatBuffer myVertex;//顶点缓冲
    	private FloatBuffer myTexture;//纹理缓冲
    	
    	int vcount;
    	
    	int repeat;
    	
    	public DrawCirque    	
    	(
    			float ring_Span,float circle_Span,float ring_Radius,float circle_Radius,
    			float RstartAngle,float RoverAngle,float CstartAngle,float CoverAngle,
    			int repeat
    	)
    	{     
    		//ring_Span表示环每一份多少度；circle_Span表示圆截环每一份多少度;ring_Radius表示环半径；circle_Radius圆截面半径。
    		this.repeat=repeat;
    		
    		ArrayList<Float> val=new ArrayList<Float>();
    		
    		for(float circle_Degree=CstartAngle;circle_Degree<CoverAngle;circle_Degree+=circle_Span)
    		{
    			for(float ring_Degree=RstartAngle;ring_Degree<RoverAngle;ring_Degree+=ring_Span)
    			{
    				float x1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree)));
    				float y1=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
    				float z1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree)));
    				
    				float x2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
    				float y2=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
    				float z2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
    				
    				float x3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
    				float y3=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
    				float z3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
    				
    				float x4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree)));
    				float y4=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
    				float z4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree)));
    				
    				val.add(x1);val.add(y1);val.add(z1);
    				val.add(x4);val.add(y4);val.add(z4);
    				val.add(x2);val.add(y2);val.add(z2);
    							
    				val.add(x2);val.add(y2);val.add(z2);
    				val.add(x4);val.add(y4);val.add(z4);
    				val.add(x3);val.add(y3);val.add(z3); 
    			}
    		}
    		vcount=val.size()/3;
    		float[] vertexs=new float[vcount*3];
    		for(int i=0;i<vcount*3;i++)
    		{
    			vertexs[i]=val.get(i);
    		}
    		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
    		vbb.order(ByteOrder.nativeOrder());
    		myVertex=vbb.asFloatBuffer();
    		myVertex.put(vertexs);
    		myVertex.position(0);
    		
    		//纹理
    		
    		int row=(int) ((CoverAngle-CstartAngle)/circle_Span);
    		int col=(int) ((RoverAngle-RstartAngle)/ring_Span);
    		float[] textures=generateTexCoor(col,row);
    		
    		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
    		tbb.order(ByteOrder.nativeOrder());
    		myTexture=tbb.asFloatBuffer();
    		myTexture.put(textures);
    		myTexture.position(0);
    	}
    	
    	public void drawSelf(GL10 gl)
    	{	
    		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
    		
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
    		gl.glBindTexture(GL10.GL_TEXTURE_2D, lubiaoTexId);
    		
    		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
    		
    		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    	}
    	
        //自动切分纹理产生纹理数组的方法
        public float[] generateTexCoor(int bw,int bh)
        {
        	float[] result=new float[bw*bh*6*2]; 
        	float REPEAT=repeat;
        	float sizew=REPEAT/bw;//列数
        	float sizeh=1.0f/bh;//行数
        	int c=0;
        	for(int i=0;i<bh;i++)
        	{
        		for(int j=0;j<bw;j++)
        		{
        			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
        			float s=j*sizew;
        			float t=i*sizeh;
        			
        			result[c++]=s;
        			result[c++]=t;
        		
        			result[c++]=s;
        			result[c++]=t+sizeh;
        			
        			result[c++]=s+sizew;
        			result[c++]=t;
        			
        			result[c++]=s+sizew;
        			result[c++]=t;
        			   			
        			result[c++]=s;
        			result[c++]=t+sizeh;
        			
        			result[c++]=s+sizew;
        			result[c++]=t+sizeh;     			
        		}
        	}
        	return result;
        }        
    }
    
    
    
    
    
    
    //绘制圆柱体的内部类
    private class DrawCylinder
    {
    	private FloatBuffer myVertexBuffer;//顶点坐标缓冲 
    	private FloatBuffer myTexture;//纹理缓冲
    	
    	int vCount;//顶点数量
    	
    	public DrawCylinder(float height,float circle_radius,float degreespan,float xoffset,float zoffset)
    	{		    		
    		ArrayList<Float> val=new ArrayList<Float>();//顶点存放列表
    		ArrayList<Float> tal=new ArrayList<Float>();//纹理存放列表
    		
    		degreespan=3;

    		for(float circle_degree=0;circle_degree<90;circle_degree+=degreespan)//循环行
    		{
    			if((circle_degree/degreespan)%2==1){continue;}
    			
    			float x1=(float) (xoffset+circle_radius*Math.sin(Math.toRadians(circle_degree)));
    			float y1=height;
    			float z1=(float) (zoffset-circle_radius*Math.cos(Math.toRadians(circle_degree)));
    			
    			float x2=(float) (xoffset+circle_radius*Math.sin(Math.toRadians(circle_degree)));
    			float y2=0;
    			float z2=(float) (zoffset-circle_radius*Math.cos(Math.toRadians(circle_degree)));
    			
    			float x3=(float) (xoffset+circle_radius*Math.sin(Math.toRadians(circle_degree+degreespan)));
    			float y3=0;
    			float z3=(float) (zoffset-circle_radius*Math.cos(Math.toRadians(circle_degree+degreespan)));
    			
    			float x4=(float) (xoffset+circle_radius*Math.sin(Math.toRadians(circle_degree+degreespan)));
    			float y4=height;
    			float z4=(float) (zoffset-circle_radius*Math.cos(Math.toRadians(circle_degree+degreespan)));
    			
    			float s1=0;
    			float t1=0;
    			
    			float s2=0;
    			float t2=1;
    			
    			float s3=1;
    			float t3=1;
    			
    			float s4=1;
    			float t4=0;
    				
    				val.add(x1);val.add(y1);val.add(z1);//两个三角形，共6个顶点的坐标
    				val.add(x2);val.add(y2);val.add(z2);
    				val.add(x4);val.add(y4);val.add(z4);
    				
    				val.add(x4);val.add(y4);val.add(z4);
    				val.add(x2);val.add(y2);val.add(z2);
    				val.add(x3);val.add(y3);val.add(z3);
	
    				tal.add(s1);tal.add(t1);
    				tal.add(s2);tal.add(t2);
    				tal.add(s4);tal.add(t4);

    				tal.add(s4);tal.add(t4);
    				tal.add(s2);tal.add(t2);
    				tal.add(s3);tal.add(t3);
    		}
    		 
    		vCount=val.size()/3;//确定顶点数量
    		
    		//顶点
    		float[] vertexs=new float[vCount*3];
    		for(int i=0;i<vCount*3;i++)
    		{
    			vertexs[i]=val.get(i);
    		}
    		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
    		vbb.order(ByteOrder.nativeOrder());
    		myVertexBuffer=vbb.asFloatBuffer(); 
    		myVertexBuffer.put(vertexs);
    		myVertexBuffer.position(0);
    		
    		//纹理
    		float[] textures=new float[vCount*2];
    		for(int i=0;i<vCount*2;i++)
    		{
    			textures[i]=tal.get(i);
    		}
    		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
    		tbb.order(ByteOrder.nativeOrder());
    		myTexture=tbb.asFloatBuffer();
    		myTexture.put(textures);
    		myTexture.position(0);
    	}
    	
    	public void drawSelf(GL10 gl)
    	{	
    		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//打开顶点缓冲
    		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//指定顶点缓冲
    		
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
    		gl.glBindTexture(GL10.GL_TEXTURE_2D, jiantouTexId);
    		
    		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//绘制图像
    		 
    		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//关闭缓冲
    		gl.glEnable(GL10.GL_TEXTURE_2D);
    		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    	}
    }
}
