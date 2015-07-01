package wyf.tzz.gdl;

import javax.microedition.khronos.opengles.GL10;

import static wyf.tzz.gdl.Constant.*;

public class Tree
{
	Ball ball; //树冠引用
	Column column;//树干引用
	
	public Tree(float scall,int leafId, int trunkId)
	{
		 ball=new Ball(scall*UNIT_SIZE,leafId);//为树冠指定纹理
		 column=new Column(2*scall*UNIT_SIZE,0.2f*scall*UNIT_SIZE,trunkId);//为树干指定纹理
	}
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//保护当前矩阵
		gl.glTranslatef(0, column.height*UNIT_SIZE/2, 0);//平移
		column.drawSelf(gl);//绘制树干
		gl.glTranslatef(0, column.height*UNIT_SIZE/2, 0);//平移
		gl.glTranslatef(0,ball.radius*UNIT_SIZE/2-0.2f, 0);//平移
		ball.drawSelf(gl);//绘制树冠
		gl.glPopMatrix();//恢复之前保护的矩阵
		
	}
}