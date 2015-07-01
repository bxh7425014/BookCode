package net.blogjava.mobile;

import android.view.animation.Interpolator;

public class MyInterceptor implements Interpolator
{

	@Override
	public float getInterpolation(float input)
	{

		if (input <= 0.5)
			return input * input;
		else
			return (1 - input) * (1 - input) ;

	}

}
