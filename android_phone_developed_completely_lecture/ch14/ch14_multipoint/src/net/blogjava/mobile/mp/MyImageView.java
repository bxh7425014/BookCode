package net.blogjava.mobile.mp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

public class MyImageView extends ImageView
{
	private GestureDetector gestureDetector;
	private Context context;

	public MyImageView(final Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;

		gestureDetector = new GestureDetector(context,
				new OnGestureListener()
				{

					@Override
					public boolean onDown(MotionEvent e)
					{
						Toast.makeText(context,
								String.valueOf(e.getHistorySize()),
								Toast.LENGTH_SHORT).show();
						return false;
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY)
					{
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY)
					{
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onSingleTapUp(MotionEvent e)
					{
						// TODO Auto-generated method stub
						return false;
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		if (gestureDetector.onTouchEvent(event))
		       return true;
		    else
		        return false; 
		
	}
}
