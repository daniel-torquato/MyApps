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
jni_prefix(sum)(
        JNIEnv *env,
        jobject _this,
        jstring token,
        jstring message
) {

    std::string str_token = env->GetStringUTFChars(token, nullptr);
    std::string str_message = env->GetStringUTFChars(message, nullptr);

    InfNumber converter( StringUtil::fromString(str_token));
    InfNumber second( StringUtil::fromString(str_message));

    InfNumber tmp = converter + second;

    return env->NewStringUTF(tmp.toBuffer().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(times)(
        JNIEnv *env,
        jobject _this,
        jstring token,
        jstring message
) {

    std::string str_token = env->GetStringUTFChars(token, nullptr);
    std::string str_message = env->GetStringUTFChars(message, nullptr);

    InfNumber converter( StringUtil::fromString(str_token));
    InfNumber second( StringUtil::fromString(str_message));

    InfNumber tmp = converter * second;

    return env->NewStringUTF(tmp.toBuffer().c_str());
}