package wyf.jsl.lb;

import javax.microedition.khronos.opengles.GL10;
import static wyf.jsl.lb.Constant.*;
//表示击中坦克数量的类
public class Score 
{
	GLGameView mv;
	DrawPanel[] numbers=new DrawPanel[10];
	
	public Score(int texId,GLGameView mv)
	{
		this.mv=mv;
		
		//生成0-9十个数字的纹理矩形
		for(int i=0;i<10;i++)
		{
			numbers[i]=new DrawPanel
            (
            	SCORE_NUMBER_SPAN_X,
            	SCORE_NUMBER_SPAN_Y,
            	 texId,
            	 new float[]
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*(i+1),0, 0.1f*i,1,  0.1f*(i+1),1
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=Count+"";
		for(int i=0;i<scoreStr.length();i++)
		{//将得分中的每个数字字符绘制
			char c=scoreStr.charAt(i);
			gl.glPushMatrix();
			gl.glTranslatef(i*SCORE_NUMBER_SPAN_X, 0, 0);
			numbers[c-'0'].drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}