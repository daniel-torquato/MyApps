// Write C++ code here.


#include <jni.h>
#include <android/log.h>

#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_math_MathRepository ## _ ## func

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(sum)(
        JNIEnv *env,
        jobject _this,
        jstring input1,
        jstring input2
) {
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "Hello Log");


    return env->NewStringUTF("Android XYZ");
}