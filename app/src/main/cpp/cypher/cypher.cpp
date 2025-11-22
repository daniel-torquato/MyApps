// Write C++ code here.


#include <jni.h>
#include <android/log.h>
#include <string>
#include <vector>
#include <infmath.h>

#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_cypher_CypherRepository ## _ ## func

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(entry)(
        JNIEnv *env,
        jobject _this,
        jstring token,
        jstring message
) {
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "Hello Log");

    std::string str_token = env->GetStringUTFChars(token, nullptr);
    std::string str_message = env->GetStringUTFChars(message, nullptr);

    InfNumber number("\x33\x33\x33");
    InfNumber r(16);

    return env->NewStringUTF((str_token + str_message).c_str());
}
