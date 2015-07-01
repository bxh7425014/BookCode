package net.blogjava.mobile;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class MyImageView extends ImageView
{
	public AnimationDrawable animationDrawable;
	public Field field;
    
	@Override
	protected void onDraw(Canvas canvas)
	{
		try
		{

			field = AnimationDrawable.class.getDeclaredField("mCurFrame");
			field.setAccessible(true);
			int curFrame = field.getInt(animationDrawable);
			if (curFrame == 2)
			{
				field.setInt(animationDrawable, 0);
				Toast.makeText(this.getContext(), "重新设为第一个图像.", Toast.LENGTH_SHORT).show();
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
