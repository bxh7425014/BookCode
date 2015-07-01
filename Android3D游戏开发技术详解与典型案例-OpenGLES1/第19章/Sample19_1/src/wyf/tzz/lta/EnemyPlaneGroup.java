package wyf.tzz.lta;

import static wyf.tzz.lta.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class EnemyPlaneGroup{
	EnemyPlane ep;						//敌机
	TextureRect trExplo[];				//爆炸组图
	SingleEnemyPlane[] seps=new SingleEnemyPlane[ENEMYPLANE_COUNT];//敌机数组
	
	public EnemyPlaneGroup(EnemyPlane ep ,TextureRect trExplo[]){
		this.ep=ep;
		this.trExplo=trExplo;
		for(int i=0;i<seps.length;i++)			//遍历敌机数组
		{
			seps[i]=new SingleEnemyPlane(this);//创建单个敌机
		}
	}
	
	public void drawSelf(GL10 gl){				//绘制方法	
		for(int i=0;i<seps.length;i++)			//遍历敌机数组
		{
			seps[i].drawSelf(gl);				//分别绘制每个敌机
		}
	}
}
