#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "com_yarin_android_HelloNDK_Jni.h"
int add2()
{
	int x,y;
	x = 1000;
	y = 8989;
	x+=y;
	return x;
}
JNIEXPORT jint JNICALL Java_com_yarin_android_HelloNDK_Jni_getCInt
  (JNIEnv *env, jobject thiz)
{
	return add2();
}
JNIEXPORT jstring JNICALL Java_com_yarin_android_HelloNDK_Jni_getCString
  (JNIEnv *env, jobject thiz)
{
	return (*env)->NewStringUTF(env, "HelloNDK---->>");
}


