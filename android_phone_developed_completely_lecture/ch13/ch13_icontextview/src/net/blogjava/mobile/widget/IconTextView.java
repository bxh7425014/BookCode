package net.blogjava.mobile.widget;

import net.blogjava.mobile.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconTextView extends TextView
{
	// 图像资源ID
	private int resourceId = 0;
	// icon位置 0：left 1:right
	private int iconPosition = 0;
	private Bitmap bitmap;

	public IconTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.IconTextView);
		
		resourceId = typedArray.getResourceId(R.styleable.IconTextView_iconSrc,
				0);
		if (resourceId > 0)
			bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
		iconPosition = typedArray.getInt(R.styleable.IconTextView_iconPosition,
				0);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		if (bitmap != null)
		{

			// 从原图上截取图像的区域，在本例中为整个图像
			Rect src = new Rect();
			// 将截取的图像复制到bitmap上的目标区域，在本例中与复制区域相同
			Rect target = new Rect();
			src.left = 0;
			src.top = 0;
			src.right = bitmap.getWidth();
			src.bottom = bitmap.getHeight();

			int textHeight = (int) getTextSize();
			int left = 0;
			if (iconPosition == 1)
			{
				left = (int) getPaint().measureText(getText().toString()) + 2;
			}
			target.left = left;
			// 计算图像复制到目录区域的纵坐标。由于TextView中文本内容并不是从最顶端开始绘制的，因此，需要重新计算绘制图像的纵坐标
			target.top = (int) ((getMeasuredHeight() - getTextSize()) / 2) + 1;
			target.bottom = target.top + textHeight;
			// 为了保证图像不变形，需要根据图像高度重新计算图像的宽度
			target.right = left
					+ (int) (textHeight * (bitmap.getWidth() / (float) bitmap
							.getHeight()));
			// 开始绘制图像
			canvas.drawBitmap(bitmap, src, target, getPaint());
			// 将TextView中的文本向右移动一定的距离（在本例中移动了图像宽度加2个象素点的位置）
			if (iconPosition == 0)
				canvas.translate(target.right + 2, 0);
		}
		super.onDraw(canvas);

	}

}
