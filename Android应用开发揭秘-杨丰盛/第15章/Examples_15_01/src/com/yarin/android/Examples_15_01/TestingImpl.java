package com.yarin.android.Examples_15_01;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class TestingImpl implements Testing
{
	private List	link	= new LinkedList();
	private List	array	= new ArrayList();
	public TestingImpl()
	{
		for (int i = 0; i < 10000; i++)
		{
			array.add(new Integer(i));
			link.add(new Integer(i));
		}
	}
	public void testArrayList()
	{
		for (int i = 0; i < 10000; i++)
			array.get(i);
	}
	public void testLinkedList()
	{
		for (int i = 0; i < 10000; i++)
			link.get(i);
	}
}

