package guo.supermario.shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {
    /**敌人存活状态**/
   public static final int ENEMY_ALIVE_STATE = 0;
    /**敌人死亡状态**/
   public static final int ENEMY_DEATH_STATE = 1;  
    /**敌人行走的Y轴速度**/
    static final int ENEMY_STEP_Y = 5;  
    /**子弹图片的宽度**/
    static final int BULLET_WIDTH = 40;    
    /** 子弹的XY坐标 **/
    public int m_posX = 0;
    public int m_posY = 0; 
    /** 敌人行走的动画 **/
    private Animation mAnimation0 = null;
    /** 敌人死亡的动画 **/
    private Animation mAnimation1 = null;   
    /**播放动画状态**/
    public int mAnimState = 0;
    
    /**是否更新绘制敌人**/
    boolean mFacus = false; 
    /**敌人状态**/
    int mState =0;
    Context mContext = null;
    public Enemy(Context context, Bitmap[] frameBitmap,Bitmap[] deadBitmap) {
	mContext = context;
	mAnimation0 = new Animation(mContext, frameBitmap, true);
	mAnimation1 = new Animation(mContext, deadBitmap, false);
    }    
    /**初始化坐标**/
    public void init(int x, int y) {
	m_posX = x;
	m_posY = y;
	mFacus = true;
	mAnimState = ENEMY_ALIVE_STATE;
	mState = ENEMY_ALIVE_STATE;
	mAnimation0.reset();
	mAnimation1.reset();
    }   
    /**绘制敌人动画**/
    public void DrawEnemy(Canvas Canvas, Paint paint) {
	if (mFacus) {
	    if(mAnimState == ENEMY_ALIVE_STATE) {
		 mAnimation0.DrawAnimation(Canvas, paint, m_posX, m_posY);
	    }else if(mAnimState == ENEMY_DEATH_STATE) {
		mAnimation1.DrawAnimation(Canvas, paint, m_posX, m_posY);
	    }
	   
	}
    }
    /**更新敌人状态**/
    public void UpdateEnemy() {
	if (mFacus) {
	    m_posY += ENEMY_STEP_Y;
	    //当敌人状态为死亡并且死亡动画播放完毕 不在绘制敌人
	    if(mAnimState == ENEMY_DEATH_STATE) {
			if(mAnimation1.mIsend) {
			    mFacus = false; 
			    mState = ENEMY_DEATH_STATE;
			}
	    }
	}
    }
}