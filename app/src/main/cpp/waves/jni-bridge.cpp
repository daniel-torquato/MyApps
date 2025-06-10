#include <jni.h>
#include <string>
#include <android/input.h>
#include <android/log.h>
#include "api/AudioEngine.h"

#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_waves_SoundRepository ## _ ## func

static AudioEngine *audioEngine = new AudioEngine();

extern "C"
JNIEXPORT void JNICALL
jni_prefix(performControl)(JNIEnv *, jobject,  jboolean on) {
    audioEngine->setToneOn(on);
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(setTone)(JNIEnv *, jobject,  jfloat frequency, jfloat amplitude) {
    audioEngine->allocate(1);
    audioEngine->setTone(frequency, amplitude, 0);
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(setTones)(JNIEnv *env, jobject _this,  jobjectArray tones) {
    jsize length = env->GetArrayLength(tones);
    __android_log_print(ANDROID_LOG_ERROR, "MyTag", "Settting tones %d", length);

    if (length >= 0) {
        audioEngine->allocate(length);
        jclass toneClass = env->FindClass( "xyz/torquato/myapps/ui/mixer/model/Tone");
        jfieldID freqId = env->GetFieldID(toneClass, "frequency", "F");
        jfieldID amplId = env->GetFieldID(toneClass, "amplitude", "F");

        for (int i = 0; i < length; ++i) {
            jobject tone = env->GetObjectArrayElement(tones, 0);
            jfloat frequency = env->GetFloatField(tone, freqId);
            jfloat amplitude = env->GetFloatField(tone, amplId);

            audioEngine->setTone(frequency, amplitude, i);
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(clean)(JNIEnv *, jobject, jint size) {
    audioEngine->allocate(size);
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(startEngine)(JNIEnv *, jobject) {
    audioEngine->start();
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(stopEngine)(JNIEnv *, jobject) {
    audioEngine->stop();
}