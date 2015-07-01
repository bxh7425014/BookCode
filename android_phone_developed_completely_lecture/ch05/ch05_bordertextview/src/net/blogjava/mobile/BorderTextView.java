package net.blogjava.mobile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BorderTextView extends TextView
{

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(android.graphics.Color.BLACK);
		canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
		canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this
				.getHeight() - 1, paint);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this
				.getHeight() - 1, paint);
	}

	public BorderTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

}
