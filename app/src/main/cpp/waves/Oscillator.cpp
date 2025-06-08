//
// Created by DAT20 on 16/12/2023.
//
#include "Oscillator.h"
#include <cmath>
#include <android/log.h>

#define TWO_PI ((double) 3.141592653589793238462643383279f * 2)

void Oscillator::setSampleRate(int32_t sampleRate) {
    sampleRate_ = sampleRate;
}

void Oscillator::setTone(double frequency, double amplitude) {
    frequency_ = frequency;
    amplitude_ = amplitude;
    phaseIncrement_ = (TWO_PI * frequency_) / (double) sampleRate_;
}

void Oscillator::addTone(double frequency, double amplitude) {
    phaseIncrement_ = (TWO_PI ) / (double) sampleRate_;
    __android_log_print(ANDROID_LOG_ERROR, "Mytag", "Add tone %f %f", frequency, amplitude);
    energy += amplitude;
            //sqrt(energy * energy + amplitude * amplitude);
    tones.emplace_back(Tone{frequency, amplitude});
}

void Oscillator::reset() {
    __android_log_print(ANDROID_LOG_ERROR, "Mytag", "Clean");
    tones.clear();
    energy = 0.0f;
}

void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {
    if (isWaveOn_.load()) {
        for (int i = 0; i < numFrames; ++i) {
            // Calculates the next sample value for the sine wave.
            float current = 0.0f;
            for (int j = 0; j < tones.size() && energy > 0.0f; ++j) {
                current += (float) (sin(phase_ * tones[j].frequency) * tones[j].amplitude / energy);
            }
            // current = (float) (sin(phase_) * amplitude_);
            audioData[i] = current;
           // __android_log_print(ANDROID_LOG_ERROR, "Mytag", "Render %f %f", phase_, phaseIncrement_);
            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
           // if (phase_ > TWO_PI) phase_ -= TWO_PI;
        }
    } else {
        //__android_log_print(ANDROID_LOG_ERROR, "Mytag", "Render reset");
        phase_ = 0;
        for (int i = 0; i < numFrames; ++i) {
            audioData[i] = 0;
        }
    }

}