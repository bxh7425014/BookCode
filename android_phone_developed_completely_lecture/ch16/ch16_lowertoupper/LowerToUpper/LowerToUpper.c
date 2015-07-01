#include <stdio.h>
#include <jni.h>

void lowercase_to_uppercase(const char *filename1, const char * filename2)
{
    FILE *fp1 = fopen(filename1, "rt");
    FILE *fp2 = fopen(filename2, "wt");
    char ch=fgetc(fp1);
    
   while(!feof(fp1))
   { 
        if(ch >= 97 && ch <= 122)
            ch -= 32;                    
        fputc(ch, fp2);
        ch = fgetc(fp1);

    } 
    fclose(fp1);
    fclose(fp2);    
}


JNIEXPORT void JNICALL Java_net_blogjava_mobile_jni_LowerToUpper_convert
  (JNIEnv *env, jobject obj, jstring filename1, jstring filename2)
{
	const char *c_str1 = (*env)->GetStringUTFChars(env, filename1, NULL);
	const char *c_str2 = (*env)->GetStringUTFChars(env, filename2, NULL);
	lowercase_to_uppercase(c_str1, c_str2);
	(*env)->ReleaseStringUTFChars(env, filename1, c_str1);
	(*env)->ReleaseStringUTFChars(env, filename2, c_str2);
   return;
}
