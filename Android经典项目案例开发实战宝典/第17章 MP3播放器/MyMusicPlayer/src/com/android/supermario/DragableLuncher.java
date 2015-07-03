package com.android.supermario;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Scroller;

/**
 * 自定义View，模仿android 桌面Luncher
 */
public class DragableLuncher extends ViewGroup {
	// 按钮背景色
	int choseColor, defaultColor;
	// 底部按钮数组
	ImageButton[] bottomBar;
	// 负责得到滚动属性的对象
	private Scroller mScroller;
	// 负责触摸的功能类
	private VelocityTracker mVelocityTracker;
	// 滚动的起始X坐标
	private int mScrollX = 0;
	// 默认显示第几屏
	private int mCurrentScreen = 0;
	// 滚动结束X坐标
	private float mLastMotionX;

	private static final int SNAP_VELOCITY = 1000;

	private final static int TOUCH_STATE_REST = 0;
	private final static int TOUCH_STATE_SCROLLING = 1;

	private int mTouchState = TOUCH_STATE_REST;
	//用户滑动的距离最小值
	private int mTouchSlop = 0;

	public DragableLuncher(Context context) {
		super(context);
		mScroller = new Scroller(context);
		//得到用于判定用户是否滑动的距离的临界值
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT));
	}

	public DragableLuncher(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);

		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,

				ViewGroup.LayoutParams.FILL_PARENT));

		/* 获得xml中设置的属性值，这里是指代默认显示第几屏幕 */
		// mCurrentScreen =
		// attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.luncher.demo",
		// "default_screen", 0);
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.DragableLuncher);
		mCurrentScreen = a.getInteger(
				R.styleable.DragableLuncher_default_screen, 0);
	}
	//拦截touch事件，返回true继续执行ontouch回调函数
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}
		//取得当前的x坐标
		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_MOVE:
			// 取绝对值
			final int xDiff = (int) Math.abs(x - mLastMotionX);
			//若移动的距离小于最小距离则将移动的标志位置为true，否则置为false
			boolean xMoved = xDiff > mTouchSlop;
			if (xMoved) {
				//如果用户沿着x轴滑动足够的距离就滚动
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
			//触摸到touch瞬间记录下x坐标
		case MotionEvent.ACTION_DOWN:
			// 记录滑动的初始位置
			mLastMotionX = x;
			//如果停止拖动
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;
			break;

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// 停止拖动
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	/**
	 * 设置是否打开触摸滑动
	 */
	public boolean isOpenTouchAnima(boolean b) {
		isOpen = b;
		return isOpen;
	}
	//默认触摸滑动打开
	public boolean isOpen = true;
	//响应滑动时间
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isOpen) {
			if (mVelocityTracker == null) {
				//获得速率探测器
				mVelocityTracker = VelocityTracker.obtain();
			}
			//将touch事件添加进探测器中
			mVelocityTracker.addMovement(event);
			//取得touch事件的类型
			final int action = event.getAction();
			//取得x坐标
			final float x = event.getX();
			//处理各种touch事件
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				// 记录下初始位置
				mLastMotionX = x;
				break;
			case MotionEvent.ACTION_MOVE:
				final int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				if (deltaX < 0) {
					if (mScrollX > 0) {
						scrollBy(Math.max(-mScrollX, deltaX), 0);
					}
				} else if (deltaX > 0) {
					//取得可滚动的最大距离
					final int availableToScroll = getChildAt(
							getChildCount() - 1).getRight()
							- mScrollX - getWidth();
					if (availableToScroll > 0) {
						scrollBy(Math.min(availableToScroll, deltaX), 0);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				//计算当前速率
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);
				int velocityX = (int) velocityTracker.getXVelocity();

				if (velocityX > SNAP_VELOCITY && mCurrentScreen > 0) {
					// 滑动到左边的界面
					snapToScreen(mCurrentScreen - 1);
				} else if (velocityX < -SNAP_VELOCITY
						&& mCurrentScreen < getChildCount() - 1) {
					// 滑动到右边的界面
					snapToScreen(mCurrentScreen + 1);
				} else {
					//滑动到判定的界面
					snapToDestination();
				}

				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				mTouchState = TOUCH_STATE_REST;
				break;
			case MotionEvent.ACTION_CANCEL:
				mTouchState = TOUCH_STATE_REST;
			}
			mScrollX = this.getScrollX();
		} else {
			return false;
		}
		if (bottomBar != null) {
			for (int k = 0; k < bottomBar.length; k++) {
				if (k == mCurrentScreen) {
					bottomBar[k].setBackgroundColor(choseColor);
				} else {
					bottomBar[k].setBackgroundColor(defaultColor);
				}
			}
		}

		return true;
	}

	public void setBottomBarBg(ImageButton[] ib, int choseColor,
			int defaultColor) {
		this.bottomBar = ib;
		this.choseColor = choseColor;
		this.defaultColor = defaultColor;
	}
	//滑动到判定的界面
	private void snapToDestination() {
		final int screenWidth = getWidth();
		final int whichScreen = (mScrollX + (screenWidth / 2)) / screenWidth;
		snapToScreen(whichScreen);
	}

	/**
	 * 带动画效果显示界面
	 */
	public void snapToScreen(int whichScreen) {
		mCurrentScreen = whichScreen;
		final int newX = whichScreen * getWidth();
		final int delta = newX - mScrollX;
		mScroller.startScroll(mScrollX, 0, delta, 0, Math.abs(delta) * 2);
		invalidate();
	}

	/**
	 * 不带动画效果显示界面
	 */
	public void setToScreen(int whichScreen) {
		// Log.i(LOG_TAG, "set To Screen " + whichScreen);
		mCurrentScreen = whichScreen;
		final int newX = whichScreen * getWidth();
		mScroller.startScroll(newX, 0, 0, 0, 10);
		invalidate();
	}
	//获得当前屏幕是第几屏
	public int getCurrentScreen() {
		return mCurrentScreen;
	}
	//当主界面布局改变时调用
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		//获得子元素的个数
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}
	//取得测量得到的高宽
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//提取出宽度
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		//提取出宽度的模式
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}
		//提取高度的模式
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}

		// 子元素将被分配给同样的高和宽
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		//滚动到指定的屏幕
		scrollTo(mCurrentScreen * width, 0);
	}
	//计算滚动的坐标
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			mScrollX = mScroller.getCurrX();
			scrollTo(mScrollX, 0);
			postInvalidate();
		}
	}
}
