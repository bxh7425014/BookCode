package com.yarin.android.HelloNDK;
public class Jni
{
	/*声明本地方法*/
	//得到一个int型数据
	public native int getCInt();
	//得到一个字符串数据
	public native String getCString();
}

