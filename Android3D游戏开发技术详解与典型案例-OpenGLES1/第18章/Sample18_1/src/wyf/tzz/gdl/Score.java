package wyf.tzz.gdl;
import javax.microedition.khronos.opengles.GL10;
import static wyf.tzz.gdl.Constant.*;
//表示获得晶体数量的类
public class Score 
{	
	TextureRect[] numbers=new TextureRect[10];		//数字纹理矩形数组
	
	MySurfaceView msv;								//主界面
	
	public Score(int texId,MySurfaceView msv)
	{
		this.msv=msv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            	 texId,								//纹理图片ID
            	 ICON_WIDTH*0.7f/2,					//图标宽度
            	 ICON_HEIGHT*0.7f/2,				//图标高度
           		 new float[]						//纹理坐标数组
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*i,1, 0.1f*(i+1),1,  0.1f*(i+1),0
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=msv.objectCount+"";				//得分数
		
		gl.glPushMatrix();								//保存当前矩阵
		
		for(int i=0;i<scoreStr.length();i++)
		{
			char c=scoreStr.charAt(i);					//将得分中的每个数字字符绘制
			
			gl.glTranslatef(ICON_WIDTH*0.7f, 0, 0);		//x方向平移
			
			numbers[c-'0'].drawSelf(gl);				//绘制每个数字
			
		}
		
		gl.glPopMatrix();								//回复之前矩阵
	}
}
