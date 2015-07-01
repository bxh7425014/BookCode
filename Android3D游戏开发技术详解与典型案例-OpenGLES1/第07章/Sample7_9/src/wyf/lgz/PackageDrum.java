package wyf.lgz;

import javax.microedition.khronos.opengles.GL10;

import wyf.lgz.DrawCircle;
import wyf.lgz.DrawHyperboloid;

public class PackageDrum
{
	MyGLSurfaceView surface;
	DrawHyperboloid hyperboloid;
	DrawCircle circle;
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	 
	public PackageDrum(MyGLSurfaceView surface)
	{
		this.surface=surface;
		
		hyperboloid=new DrawHyperboloid(10f,2f,2f,6f,10,18,surface.mRenderer.bodyTextureId);//创建圆柱体
        circle=new DrawCircle(hyperboloid.R,12f,surface.mRenderer.circleTextureId);
	}
	
	public void onDraw(GL10 gl)
	{
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
        gl.glPushMatrix();
        initMaterial(gl);//初始化纹理
        hyperboloid.drawSelf(gl);//绘制
        gl.glPopMatrix();//恢复变换矩阵现场
        
        gl.glPushMatrix();
        initMaterial(gl);
        gl.glTranslatef(0,hyperboloid.height*0.5f, 0);
        gl.glRotatef(90, 1, 0, 0);
        gl.glRotatef(180, 0, 0, 1);
        circle.drawSelf(gl);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        initMaterial(gl);
        gl.glTranslatef(0, -hyperboloid.height*0.5f, 0);
        gl.glRotatef(-90, 1, 0, 0);
        circle.drawSelf(gl);
        gl.glPopMatrix();
	}
	
	//初始化材质
	private void initMaterial(GL10 gl)
	{
        //环境光
        float ambientMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {248f/255f, 242f/255f, 144f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
}