package com.yarin.android.Examples_15_02;
import java.util.ArrayList;
import java.util.HashMap;
public class MemoConsumerImpl implements MemoConsumer
{
	ArrayList	arr		= null;
	HashMap		hash	= null;
	public void creatArray()
	{
		arr = new ArrayList(1000);
	}
	public void creatHashMap()
	{
		hash = new HashMap(1000);
	}
}
