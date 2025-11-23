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

    std::string str_token = env->GetStringUTFChars(token, nullptr);
    std::string str_message = env->GetStringUTFChars(message, nullptr);

    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "Hello Log %s %s", str_token.c_str(), str_message.c_str());

    InfNumber number("\x33\x33\x33");
    InfNumber r(16);

    return env->NewStringUTF((str_token + str_message + number.get()).c_str());
}

std::vector<unsigned char> format(
        std::string input
        ) {

    std::vector<unsigned char> another(input.size());

    for (int i = 0; i < input.size(); ++i) {
        another[i] = input[i];
    }

    return another;
}



