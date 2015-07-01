package wyf.jsc.rtb;


import javax.microedition.khronos.opengles.GL10;

import static wyf.jsc.rtb.Constant.*;

public class FloorGroup {

	Floor floor;
	public FloorGroup(int scale,int textureId)
	{
		floor=new Floor(scale,textureId);
	}
	public void drawSelf(GL10 gl)
	{
		int rows=MAP.length;
		int cols=MAP[0].length;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(MAP[i][j]==1)
				{
					gl.glPushMatrix();
					gl.glTranslatef(-tempFlag+j*UNIT_SIZE, 0, -tempFlag/2+i*UNIT_SIZE);
					floor.drawSelf(gl);
					gl.glPopMatrix();
				}
			}
		}
	}
}

