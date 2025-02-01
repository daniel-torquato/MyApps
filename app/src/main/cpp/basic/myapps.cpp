// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("myapps");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("myapps")
//      }
//    }

#include <jni.h>
#include <android/log.h>

extern "C" {
JNIEXPORT jstring JNICALL
Java_xyz_torquato_myapps_MainActivity_entry(JNIEnv *env, jobject /* this */) {
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "Hello Log");
    return env->NewStringUTF("Android XYZ");
}
}