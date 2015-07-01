package com.yarin.android.Examples_15_02;

public class Memory
{
	public static long used()
	{
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		return (total - free);
	}
}
