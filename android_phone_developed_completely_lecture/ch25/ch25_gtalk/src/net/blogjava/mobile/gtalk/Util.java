package net.blogjava.mobile.gtalk;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Util
{
	private final String PREFERENCE_NAME = "gtalk";
	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public Util(Context context)
	{
		mContext = context;
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	public void showMsg(String msg)
	{
		Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
	}

	public void saveString(String key, String value)
	{
		mSharedPreferences.edit().putString(key, value).commit();
	}

	public String getString(String key, String... defValue)
	{
		if (defValue.length > 0)
			return mSharedPreferences.getString(key, defValue[0]);
		else
			return mSharedPreferences.getString(key, "");

	}
	
	public static String getLeftString(String s, String separator)
	{
		int index = s.indexOf(separator);
		if (index > -1)
			return s.substring(0, index);
		else
			return s;
	}
}
