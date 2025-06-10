//
// Created by DAT20 on 16/12/2023.
//
#include "../api/Oscillator.h"
#include <cmath>
#include <android/log.h>

#define TWO_PI ((double) 3.141592653589793238462643383279f * 2)

void Oscillator::setSampleRate(int32_t sampleRate) {
    sampleRate_ = sampleRate;
    phaseStep_ = (TWO_PI ) / (double) sampleRate_;
}

void Oscillator::setTone(double frequency, double amplitude, int index) {
    if (0 <= index && index < toneSize) {
        energy += amplitude;
        tones_[index].frequency = frequency;
        tones_[index].amplitude = amplitude;
    }
}

void Oscillator::allocate(int size) {
    __android_log_print(ANDROID_LOG_ERROR, "Mytag", "Clean");
    toneSize = size <= tones_.size() ? size : tones_.size();
    energy = 0.0f;
    phase_ = 0.0f;
}

void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {
    if (isWaveOn_.load()) {
        for (int i = 0; i < numFrames; ++i) {
            // Calculates the next sample value for the sine wave.
            float current = 0.0f;
            for (int j = 0; j < toneSize && energy > 0.0f; ++j) {
                current += (float) (sin(phase_ * tones_[j].frequency) * tones_[j].amplitude / energy);
            }
            audioData[i] = current;
            phase_ += phaseStep_;
        }
    } else {
        phase_ = 0;
        for (int i = 0; i < numFrames; ++i) {
            audioData[i] = 0;
        }
    }
}