package net.blogjava.mobile;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;


public class MyImageView extends ImageView
{
	public AnimationDrawable animationDrawable;
    public ImageView ivMissile;
	public Field field;
    
	@Override
	protected void onDraw(Canvas canvas)
	{
		try
		{

			field = AnimationDrawable.class.getDeclaredField("mCurFrame");
			field.setAccessible(true);
			int curFrame = field.getInt(animationDrawable);
			if (curFrame == animationDrawable.getNumberOfFrames() - 1)
			{
				setVisibility(View.INVISIBLE);
				ivMissile.setVisibility(View.VISIBLE);
			}

		}
		catch (Exception e)
		{	
			
		}
		super.onDraw(canvas);

	}

	public MyImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

	}

}
