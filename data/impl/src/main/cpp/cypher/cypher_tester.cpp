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


#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_impl ## _CypherTester ## _ ## func

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

    InfNumber converter( StringUtil::toBuffer(str_token));
    InfNumber second( StringUtil::toBuffer(str_message));

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

    InfNumber converter( StringUtil::toBuffer(str_token));
    InfNumber second( StringUtil::toBuffer(str_message));

    InfNumber tmp = converter * second;

    return env->NewStringUTF(tmp.toBuffer().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(mod)(
        JNIEnv *env,
        jobject _this,
        jstring input,
        jstring divisor
) {

    std::string str_token = env->GetStringUTFChars(input, nullptr);
    std::string str_message = env->GetStringUTFChars(divisor, nullptr);

    InfNumber converter( StringUtil::toBuffer(str_token));
    InfNumber second( StringUtil::toBuffer(str_message));

    InfNumber tmp = converter % second;

    return env->NewStringUTF(tmp.toBuffer().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(neg)(
        JNIEnv *env,
        jobject _this,
        jstring input
) {

    std::string str_token = env->GetStringUTFChars(input, nullptr);

    InfNumber converter( StringUtil::toBuffer(str_token));

    InfNumber tmp = -converter;

    return env->NewStringUTF(tmp.toBuffer().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(converter)(
        JNIEnv *env,
        jobject _this,
        jstring token
) {

    std::string str_token = env->GetStringUTFChars(token, nullptr);

    std::vector<u_char> buffer = StringUtil::toBuffer(str_token);

    std::string output = StringUtil::toString(buffer);

    return env->NewStringUTF(output.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
jni_prefix(input)(
        JNIEnv *env,
        jobject _this,
        jstring token
) {

    std::string str_token = env->GetStringUTFChars(token, nullptr);

    std::vector<slot> buffer = StringUtil::toSlots(str_token);
    std::string output = StringUtil::toString(buffer);

    return env->NewStringUTF(output.c_str());
}