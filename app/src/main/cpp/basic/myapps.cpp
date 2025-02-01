// Write C++ code here.


#include <jni.h>
#include <android/log.h>

#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_basic_BasicRepository ## _ ## func

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(entry)(JNIEnv *env, jobject ) {
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "Hello Log");
    return env->NewStringUTF("Android XYZ");
}