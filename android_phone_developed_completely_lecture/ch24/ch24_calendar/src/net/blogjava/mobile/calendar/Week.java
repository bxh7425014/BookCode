package net.blogjava.mobile.calendar;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.View;

public class Week extends CalendarParent
{
	private String[] weekNames;
	private int weekNameColor;

	public Week(Activity activity, View view)
	{
		super(activity, view);
		weekNameColor = activity.getResources().getColor(R.color.weekname_color);
		weekNames = activity.getResources().getStringArray(R.array.week_name);
		paint.setTextSize(weekNameSize);
	}

	@Override
	public void draw(Canvas canvas)
	{

		float left = borderMargin;
		float top = borderMargin;
		float everyWeekWidth = (view.getMeasuredWidth() -  borderMargin * 2)/ 7;
		float everyWeekHeight = everyWeekWidth;
		
		paint.setFakeBoldText(true);
		for (int i = 0; i < weekNames.length; i++)
		{
			if(i == 0 || i == weekNames.length - 1)
				paint.setColor(sundaySaturdayColor);
			else
				paint.setColor(weekNameColor);

			left = borderMargin + everyWeekWidth * i
					+ (everyWeekWidth - paint.measureText(weekNames[i])) / 2;
			canvas.drawText(weekNames[i], left, top + paint.getTextSize()+weekNameMargin, paint);
		}

	}

}
