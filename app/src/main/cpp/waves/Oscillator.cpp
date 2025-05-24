//
// Created by DAT20 on 16/12/2023.
//
#include "Oscillator.h"
#include <cmath>

#define TWO_PI (3.14159 * 2)

void Oscillator::setSampleRate(int32_t sampleRate) {
    sampleRate_ = sampleRate;
}

void Oscillator::setTone(double frequency, double amplitude) {
    frequency_ = frequency;
    amplitude_ = amplitude;
    phaseIncrement_ = (TWO_PI * frequency_) / (double) sampleRate_;
}

void Oscillator::setWaveOn(bool isWaveOn) {

    isWaveOn_.store(isWaveOn);
}

void Oscillator::render(float *audioData, int32_t numFrames) {
    if (isWaveOn_.load()) {
        for (int i = 0; i < numFrames; ++i) {
            // Calculates the next sample value for the sine wave.
            auto current = (float) (sin(phase_) * amplitude_);
            audioData[i] = current;

            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;
        }
    } else {
        phase_ = 0;
        for (int i = 0; i < numFrames; ++i) {
            audioData[i] = 0;
        }
    }

}