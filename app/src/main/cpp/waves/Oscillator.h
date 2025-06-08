//
// Created by DAT20 on 16/12/2023.
//

#ifndef MYWAVES_OSCILLATOR_H
#define MYWAVES_OSCILLATOR_H

#include <atomic>
#include <cstdint>
#include <vector>
#include "model/Tone.h"

class Oscillator {
public:
    void setWaveOn(bool isWaveOn);
    void setSampleRate(int32_t sampleRate);
    void render(float *audioData, int32_t numFrames);
    void setTone(double frequency, double amplitude);
    void addTone(double frequency, double amplitude);
    void reset();

private:
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double phaseIncrement_ = 0.0;
    double frequency_ = 440.0;
    double amplitude_ = 0.3;
    std::vector<Tone> tones;
    double energy = 0.0;
    double sampleRate_ = 1.0;
};

#endif //MYWAVES_OSCILLATOR_H
