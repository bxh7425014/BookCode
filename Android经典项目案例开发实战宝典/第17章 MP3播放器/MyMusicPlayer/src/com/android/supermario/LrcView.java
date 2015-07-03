package com.android.supermario;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.supermario.LrcProcess.LrcContent;
/**
 * 自定义绘画歌词，产生滚动效果
 */
public class LrcView extends TextView {
	//视图的宽度
	private float width;
	//视图的高度
	private float high;
	//当前播放歌词的画笔
	private Paint CurrentPaint;
	//非当前播放歌词的画笔
	private Paint NotCurrentPaint;
	//每行文字的高度
	private float TextHigh = 25;
	//非当前播放歌词的子图大小
	private float TextSize = 18;
	//歌词显示的位置
	private int Index = 0;
	//歌词语句列表
	private List<LrcContent> mSentenceEntities = new ArrayList<LrcContent>();
	//初始化歌词语句列表
	public void setSentenceEntities(List<LrcContent> mSentenceEntities) {
		this.mSentenceEntities = mSentenceEntities;
	}
	//三种不同参数的初始化
	public LrcView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public LrcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	public LrcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	//初始化函数
	private void init() {
		// TODO Auto-generated method stub
		setFocusable(true);
		// 高亮部分
		CurrentPaint = new Paint();
		CurrentPaint.setAntiAlias(true);
		CurrentPaint.setTextAlign(Paint.Align.CENTER);
		// 非高亮部分
		NotCurrentPaint = new Paint();
		NotCurrentPaint.setAntiAlias(true);
		NotCurrentPaint.setTextAlign(Paint.Align.CENTER);
	}
	//绘制视图
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (canvas == null) {
			return;
		}
		//设置歌词字体的颜色
		CurrentPaint.setColor(Color.argb(210, 251, 248, 29));
		NotCurrentPaint.setColor(Color.argb(140, 255, 255, 255));
		//设置当前歌词字体和为大小
		CurrentPaint.setTextSize(24);
		CurrentPaint.setTypeface(Typeface.SERIF);
		//设置非当前歌词字体和为大小
		NotCurrentPaint.setTextSize(TextSize);
		NotCurrentPaint.setTypeface(Typeface.DEFAULT);
		try {
			setText("");
			//显示当前播放的歌词
			canvas.drawText(mSentenceEntities.get(Index).getLrc(), width / 2,
					high / 2, CurrentPaint);
			float tempY = high / 2;
			// 画出本句之前的句子
			for (int i = Index - 1; i >= 0; i--) {
				// 向上推移
				tempY = tempY - TextHigh;
				//显示歌词
				canvas.drawText(mSentenceEntities.get(i).getLrc(), width / 2,
						tempY, NotCurrentPaint);
			}
			tempY = high / 2;
			// 画出本句之后的句子
			for (int i = Index + 1; i < mSentenceEntities.size(); i++) {
				// 往下推移
				tempY = tempY + TextHigh;
				//显示歌词
				canvas.drawText(mSentenceEntities.get(i).getLrc(), width / 2,
						tempY, NotCurrentPaint);
			}
		} catch (Exception e) {
			setText("未找到歌词文件");
		}
	}
	//视图大小改变时
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.width = w;
		this.high = h;
	}
	//设置歌词显示的位置
	public void SetIndex(int index) {
		this.Index = index;
	}
}