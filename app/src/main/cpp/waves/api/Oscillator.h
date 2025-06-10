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
    void setTone(double frequency, double amplitude, int index);
    void allocate(int size);

private:
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double phaseStep_ = 0.0;
    std::vector<Tone> tones_{100};
    unsigned toneSize = 0;
    double energy = 0.0;
    double sampleRate_ = 1.0;
};

#endif //MYWAVES_OSCILLATOR_H
