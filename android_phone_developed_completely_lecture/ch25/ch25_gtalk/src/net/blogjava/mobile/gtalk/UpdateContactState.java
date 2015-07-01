package net.blogjava.mobile.gtalk;

import android.os.Handler;

public class UpdateContactState implements Runnable
{
	public boolean flag = true;
	private OnContactStateListener mOnContactStateListener;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{

			if (mOnContactStateListener != null)
				mOnContactStateListener.contactStateChange();

			super.handleMessage(msg);
		}

	};

	@Override
	public void run()
	{
		// 
		while (flag)
		{
			try
			{
				
				Thread.sleep(5000);
				handler.sendEmptyMessage(0);

			}
			catch (Exception e)
			{
				
			}
		}

	}

	public void setOnContactStateListener(OnContactStateListener listener)
	{
		mOnContactStateListener = listener;
	}

}
