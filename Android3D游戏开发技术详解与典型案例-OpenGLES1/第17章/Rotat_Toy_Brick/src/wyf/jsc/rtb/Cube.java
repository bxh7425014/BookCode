package wyf.jsc.rtb;
import javax.microedition.khronos.opengles.GL10;
import static wyf.jsc.rtb.Constant.*;
public class Cube 
{
	TextureRect trSmall;//小面纹理ID
	TextureRect trBig;//大面纹理ID
	float scale;//尺寸缩放系数
	float rSmall;//小面旋转半径
	float rBig;//大面旋转半径
	float unitLocalSize;//实际单位尺寸——小面的半边长
	float angleZ=0;//实时旋转扰动角度   绕Z轴
	float angleX=0;//实时旋转扰动角度   绕X轴
	float angleY=0;//实时旋转扰动角度   绕Y轴
    int state=POSTURE_ONE;//当前姿态
    float xOffset=0;//x位置
    float zOffset=0;//Z位置
    float tempCenterX=0;//旋转临时X扰动值
    float tempCenterY=0;//旋转临时Y扰动值
    float tempCenterZ=0;//旋转临时Z扰动值
    int i1=INIT_I;
	int j1=INIT_J;
	int i2=INIT_I;
	int j2=INIT_J;//初始化坐标，确定积木的位置	
	public Cube(float scale,int cubeSmallTexId,int cubeBigTexId,float[] texTS)
	{
		this.scale=scale;
		trSmall=new TextureRect(scale,scale,cubeSmallTexId,texTS);
		trBig=new TextureRect(scale,2*scale,cubeBigTexId,texTS);	
		unitLocalSize=UNIT_SIZE*scale;
		rSmall=(float)Math.sqrt(2)*unitLocalSize;
		rBig=(float)Math.sqrt(5)*unitLocalSize;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		//移动到指定的XZ位置绘制Cube
		gl.glTranslatef(xOffset, 0, zOffset);
		//按指定的姿态绘制Cube
		switch(state)
		{
		  case POSTURE_ZERO: 
		  break;
		  case POSTURE_ONE:
			gl.glTranslatef(0, unitLocalSize, 0);
			gl.glRotatef(90, 1, 0, 0);
	      break;
		  case POSTURE_TWO:
			gl.glRotatef(90, 0, 1, 0);
		  break;
		}
		//旋转中的扰动
		gl.glTranslatef(tempCenterX, tempCenterY, tempCenterZ);
		gl.glRotatef(angleX, 1, 0, 0);
		gl.glRotatef(angleY, 0, 1, 0);
		gl.glRotatef(angleZ, 0, 0, 1);
		//绘制前小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 2*UNIT_SIZE*scale);
		trSmall.drawSelf(gl);		
		gl.glPopMatrix();
		//绘制后小面
		gl.glPushMatrix();		
		gl.glTranslatef(0, 0, -2*UNIT_SIZE*scale);
		gl.glRotatef(180, 0, 1, 0);
		trSmall.drawSelf(gl);		
		gl.glPopMatrix();
		//绘制上大面
		gl.glPushMatrix();			
		gl.glTranslatef(0,UNIT_SIZE*scale,0);
		gl.glRotatef(-90, 1, 0, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();
		//绘制下大面
		gl.glPushMatrix();			
		gl.glTranslatef(0,-UNIT_SIZE*scale,0);
		gl.glRotatef(90, 1, 0, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();
		//绘制左大面
		gl.glPushMatrix();			
		gl.glTranslatef(UNIT_SIZE*scale,0,0);		
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();
		//绘制右大面
		gl.glPushMatrix();			
		gl.glTranslatef(-UNIT_SIZE*scale,0,0);		
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		trBig.drawSelf(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
	}
	
	public void keyPress(int keyNumber)
	{
		boolean flag=false;
		int afterState=POSTURE_CHANGE[state][keyNumber];
		flag=checkOnOff(afterState,keyNumber);
		if(flag)
		{
			float afterXOffset=xOffset+X_OFFSET_CHANGE[state][keyNumber]*unitLocalSize*2;
			float afterZOffset=zOffset+Z_OFFSET_CHANGE[state][keyNumber]*unitLocalSize*2;
			int anmiNumber=ROTATE_ANMI_ID[state][keyNumber];
			new RotateThread
			(
					anmiNumber,
					this,
					afterState,
					afterXOffset,
					afterZOffset
			).start();
			if(MAP[j1][i1]==2&&MAP[j2][i2]==2)
			{
				winSound=true;
				new WinDrop(this).start();//掉下来的动画。
				level++;
				winAndLose=0;
				winFlag=true;
			}
		}else
		{
			//放掉下来的动画。
			dropFlag=true;
			int anmiNumber=ROTATE_ANMI_ID[state][keyNumber];
			new DropOff
			(
				anmiNumber,
				this
			).start();
			winAndLose=1;
			loseFlag=true;
		}
		
	}
	public boolean checkOnOff(int afterState,int keyNumber)//碰撞检测方法
	{
		switch(afterState)
		{
		case 0:
				i1=i1+afterStateIsZero[keyNumber][0];			
				j1=j1+afterStateIsZero[keyNumber][1];
				i2=i2+afterStateIsZero[keyNumber][2];
				j2=j2+afterStateIsZero[keyNumber][3];
				if(MAP[j1][i1]==1&&MAP[j2][i2]==1||MAP[j1][i1]==2||MAP[j2][i2]==2)
				{
					targetX=i1;
					targetZ=j1;
					int tempi=0;
					int tempj=0;
					if(j1<j2)
					{
						tempi=i1;
						tempj=j1;
						i1=i2;
						j1=j2;
						i2=tempi;
						j2=tempj;
						
					}
					return true;
				}else
				{
					return false;
				}
			
		case 1:
				i1=i1+afterStateIsOne[keyNumber][0];
				j1=j1+afterStateIsOne[keyNumber][1];
				i2=i2+afterStateIsOne[keyNumber][2];
				j2=j2+afterStateIsOne[keyNumber][3];
				if(MAP[j1][i1]==1&&MAP[j2][i2]==1||MAP[j1][i1]==2||MAP[j2][i2]==2)
				{					
					targetX=i1;
					targetZ=j1;
					return true;
				}
				else
				{
					return false;
				}
		case 2:
				i1=i1+afterStateIsTwo[keyNumber][0];
				j1=j1+afterStateIsTwo[keyNumber][1];
				i2=i2+afterStateIsTwo[keyNumber][2];
				j2=j2+afterStateIsTwo[keyNumber][3];
				if(MAP[j1][i1]==1&&MAP[j2][i2]==1||MAP[j1][i1]==2||MAP[j2][i2]==2)
				{
					targetX=i1;
					targetZ=j1;
					int tempi=0;
					int tempj=0;
					if(i1<i2)
					{
						tempi=i1;
						tempj=j1;
						i1=i2;
						j1=j2;
						i2=tempi;
						j2=tempj;
						
					}
					return true;
				}
				else
				{
					return false;
				}
		}
		return false;
	}
}


