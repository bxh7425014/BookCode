package com.yarin.android.Examples_15_02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.util.Log;

public class Handler implements InvocationHandler
{
	private Object	obj;
	public Handler(Object obj)
	{
		this.obj = obj;
	}
	public static Object newInstance(Object obj)
	{
		Object result = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new Handler(obj));
		return (result);
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		Object result;
		try
		{
			Log.i("Handler", "begin method " + method.getName());
			
			long start = Memory.used();
			result = method.invoke(obj, args);
			long end = Memory.used();
			
			Log.i("Handler", "memory increased by " + (end - start) + "bytes");
		}
		catch (InvocationTargetException e)
		{
			throw e.getTargetException();
		}
		catch (Exception e)
		{
			throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
		}
		finally
		{
			Log.i("Handler", "end method " + method.getName());
		}
		return result;
	}
}

