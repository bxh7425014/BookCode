package net.blogjava.mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MotionEvent;

public class DateDialog extends AlertDialog
{

	public DateDialog(Context context)
	{
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		dismiss();
		return super.onTouchEvent(event);
	}

}
