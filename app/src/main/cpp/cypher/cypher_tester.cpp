//
// Created by torquato on 28/12/25.
//

#include <jni.h>
#include <android/log.h>
#include <string>
#include <vector>
#include <infmath.h>
#include <StringUtil.hpp>
#include <CypherUtil.hpp>


#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _CypherTester ## _ ## func

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(entry)(
        JNIEnv *env,
        jobject _this,
        jstring token,
        jstring message
) {

    std::string str_token = env->GetStringUTFChars(token, nullptr);
    std::string str_message = env->GetStringUTFChars(message, nullptr);

    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "INPUT %s %s", str_token.c_str(), str_message.c_str());

    InfNumber converter( StringUtil::fromString(str_token));
    InfNumber second( StringUtil::fromString(str_message));

    InfNumber tmp = CypherUtil::poly1305(converter, second);

    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "CONVERTER %s", converter.toString().c_str());
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "SUM %s", (converter + second).toString().c_str());
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "MULT %s", (converter * second).toString().c_str());
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "CYPHER %s", tmp.toString().c_str());


    return env->NewStringUTF(tmp.toBuffer().c_str());
}