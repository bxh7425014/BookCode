package guo.supermario.shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {
    /**子弹的X轴速度**/
    static final int BULLET_STEP_X = 3;   
    /**子弹的Y轴速度**/
    static final int BULLET_STEP_Y = 15;     
    /**子弹图片的宽度**/
    static final int BULLET_WIDTH = 40;   
    /** 子弹的XY坐标 **/
    public int m_posX = 0;
    public int m_posY = 0;   
    /** 子弹的动画 **/
    private Animation mAnimation = null;    
    /**是否更新绘制子弹**/
    boolean mFacus = false;   
    Context mContext = null;
    public Bullet(Context context, Bitmap[] frameBitmap) {
		mContext = context;
		mAnimation = new Animation(mContext, frameBitmap, true);
    }    
    /**初始化坐标**/
    public void init(int x, int y) {
		m_posX = x;
		m_posY = y;
		mFacus = true;
    }    
    /**绘制子弹**/
    public void DrawBullet(Canvas Canvas, Paint paint) {
		if (mFacus) {
		    mAnimation.DrawAnimation(Canvas, paint, m_posX, m_posY);
		}
    }
    /**更新子弹的坐标点**/
    public void UpdateBullet() {
		if (mFacus) {
		    m_posY -= BULLET_STEP_Y;
		}
    }   
}