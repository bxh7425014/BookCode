package wyf.tzz.lta;
import javax.microedition.khronos.opengles.GL10;
import static wyf.tzz.lta.Constant.*;
//表示获得晶体数量的类
public class Score {						
	ScoreRect[] numbers=new ScoreRect[10];		//数字纹理矩形数组
	int goal=0;									//hero得分数
	
	public Score(int texId){					//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++){
			numbers[i]=new ScoreRect
            (
            	 texId,								//纹理图片ID
            	 ICON_WIDTH*0.7f/2,					//图片宽度
            	 ICON_HEIGHT*0.7f,					//图片高度
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
		String scoreStr=goal+"";						//将得分数转为String类型
		gl.glPushMatrix();								//保存当前矩阵
		for(int i=0;i<scoreStr.length();i++)
		{
			char c=scoreStr.charAt(i);					//将得分中的每个数字字符绘制
			gl.glTranslatef(ICON_WIDTH*0.7f, 0, 0);		//沿x方向平移
			numbers[c-'0'].drawSelf(gl);				//绘制每个数字
		}
		gl.glPopMatrix();								//回复之前矩阵
	}
}
