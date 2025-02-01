//
// Created by DAT20 on 16/12/2023.
//

#ifndef MYWAVES_AUDIOENGINE_H
#define MYWAVES_AUDIOENGINE_H

#include <aaudio/AAudio.h>
#include "Oscillator.h"

class AudioEngine {
public:
    bool start();
    void stop();
    void restart();
    void setToneOn(bool isToneOn);
    void setTone(float frequency, float amplitude);

private:
    Oscillator oscillator_;
    AAudioStream *stream_;
};

#endif //MYWAVES_AUDIOENGINE_H
