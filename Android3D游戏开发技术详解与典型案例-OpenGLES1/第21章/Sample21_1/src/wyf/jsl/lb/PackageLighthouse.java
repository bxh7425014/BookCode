package wyf.jsl.lb;

import static wyf.jsl.lb.Constant.*;
import javax.microedition.khronos.opengles.GL10;

public class PackageLighthouse
{
	DrawLightCylinder pedestal;	//灯塔底座
	DrawLightCylinder body;		//灯塔躯干
	DrawLightCylinder flatform;	//灯塔瞭望台
	DrawTaper overhead;		//灯塔顶
	DrawTaper upswell1;		//瞭望台凸起部分 
	DrawTaper upswell2;		//底座凸起部分
	
	public float mAngleX;	//旋转
	public float mAngleY;
	public float mAngleZ;
	
	public PackageLighthouse()
	{
		pedestal=new DrawLightCylinder(PEDESTAL_LENGHT,PEDESTAL_R,18,10);
		body=new DrawLightCylinder(BODY_LENGHT,BODY_R,18,10);
		flatform=new DrawLightCylinder(FLOTFORM_LENGHT,FLOTFORM_R,18,10);
		overhead=new DrawTaper(OVERHEAD_HEIGHT,OVERHEAD_R,18,10);
		upswell1=new DrawTaper(UPSWELL_HEIGHT,UPSWELL_R,18,10);
		upswell2=new DrawTaper(UPSWELL_HEIGHT,PEDESTAL_R,18,10);
	}
	
	public void onDraw(GL10 gl)
	{
		gl.glRotatef(mAngleX, 1, 0, 0);//旋转
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glPushMatrix();//绘制灯塔底座
		initMaterial2(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length*0.5f, 0);
		gl.glRotatef(90, 0, 0, 1);
		pedestal.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔底座上部凸起
		initMaterial2(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length, 0);
		upswell2.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔躯干
		initMaterial2(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length+body.length*0.5f, 0);
		gl.glRotatef(90, 0, 0, 1);
		body.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔瞭望台
		initMaterial3(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length+body.length*FLOTFORM_SCALE, 0);
		gl.glRotatef(90, 0, 0, 1);
		flatform.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔顶
		initMaterial1(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length+body.length, 0);
		overhead.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔瞭望台上部凸起
		initMaterial1(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length+body.length*FLOTFORM_SCALE+flatform.length*0.5f, 0);
		upswell1.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();//绘制灯塔瞭望台下部凸起
		initMaterial1(gl);							//初始化材质
		gl.glTranslatef(0, pedestal.length+body.length*FLOTFORM_SCALE-flatform.length*0.5f, 0);
		gl.glRotatef(180, 0, 0, 1);
		upswell1.drawSelf(gl);
		gl.glPopMatrix();
	}
	
	//初始化红色材质
	private void initMaterial1(GL10 gl)
	{
        //环境光为
        float ambientMaterial[] = {144f/255f, 80f/255f, 42f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {144f/255f, 80f/255f, 42f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {144f/255f, 80f/255f, 42f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
	 
	//初始化白色材质
	private void initMaterial2(GL10 gl)
	{
        //环境光
        float ambientMaterial[] = {196f/255f, 205f/255f, 204f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //散射光
        float diffuseMaterial[] = {196f/255f, 205f/255f, 204f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //高光材质
        float specularMaterial[] = {196f/255f, 205f/255f, 204f/255f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 100.0f);
	}
	
	//初始化黄色材质
	private void initMaterial3(GL10 gl)
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