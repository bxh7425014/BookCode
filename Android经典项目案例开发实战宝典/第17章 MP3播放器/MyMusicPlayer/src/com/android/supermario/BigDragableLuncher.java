package com.android.supermario;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Scroller;

/**
 * 自定义View，模仿android 桌面Luncher
 */
public class BigDragableLuncher extends ViewGroup {

	// 按钮背景色
	int choseColor, defaultColor;
	// 底部按钮数组
	ImageButton[] bottomBar;
	// 负责得到滚动属性的对象
	private Scroller mScroller;
	// 滚动的起始X坐标
	private int mScrollX = 0;
	// 默认显示第几屏
	private int mCurrentScreen = 0;

	public int mTouchSlop = 0;

	public BigDragableLuncher(Context context) {
		super(context);
		mScroller = new Scroller(context);
		// 得到状态位
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		//设置布局参数
		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT));
	}

	public BigDragableLuncher(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);

		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		//设置布局参数
		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT));

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.DragableLuncher);
		mCurrentScreen = a.getInteger(
				R.styleable.DragableLuncher_default_screen, 0);
	}
	//设置底部按钮颜色
	public void setBottomBarBg(ImageButton[] ib, int choseColor,
			int defaultColor) {
		this.bottomBar = ib;
		this.choseColor = choseColor;
		this.defaultColor = defaultColor;
	}
	//屏幕滚动
	public void snapToDestination() {
		final int screenWidth = getWidth();
		//滑动距离超过1/2屏幕时，进入下一个界面
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
		mCurrentScreen = whichScreen;
		final int newX = whichScreen * getWidth();
		mScroller.startScroll(newX, 0, 0, 0, 10);
		invalidate();
	}
	//取得当前屏幕位置
	public int getCurrentScreen() {
		return mCurrentScreen;
	}
	//改变布局时调用
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			//从左到右依次排列子元素
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}

	}
	//传递高宽信息
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//取得宽度值
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}
		//将高宽信息传递给子元素
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurrentScreen * width, 0);
	}
	//计算滚动距离
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			mScrollX = mScroller.getCurrX();
			scrollTo(mScrollX, 0);
			postInvalidate();
		}
	}
}
