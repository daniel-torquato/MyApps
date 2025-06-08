#include <jni.h>
#include <string>
#include <android/input.h>
#include "AudioEngine.h"

#define jni_prefix(func) Java ## _xyz_torquato_myapps ## _data_waves_SoundRepository ## _ ## func

static AudioEngine *audioEngine = new AudioEngine();

extern "C"
JNIEXPORT void JNICALL
jni_prefix(touchEvent)(JNIEnv *, jobject, jint action,
                       jfloat frequency, jfloat amplitude) {
    switch (action) {
        case AMOTION_EVENT_ACTION_DOWN:
            audioEngine->setTone(frequency, amplitude);
            audioEngine->setToneOn(true);
            break;
        case AMOTION_EVENT_ACTION_UP:
            audioEngine->setToneOn(false);
            break;
        default:
            break;
    }
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(performControl)(JNIEnv *, jobject,  jboolean on) {
    audioEngine->setToneOn(on);
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(addTone)(JNIEnv *, jobject,  jfloat frequency, jfloat amplitude) {
    audioEngine->addTone(frequency, amplitude);
}

extern "C"
JNIEXPORT void JNICALL
jni_prefix(clean)(JNIEnv *, jobject) {
    audioEngine->clean();
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