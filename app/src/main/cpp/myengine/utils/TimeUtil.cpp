//
// Created by DAT20 on 10/6/2024.
//
#include "TimeUtil.h"

double TimeUtil::getTimeNanoSeconds() {
    struct timespec now{};
    clock_gettime(CLOCK_MONOTONIC, &now);
    return ((double) now.tv_sec) + ((double) now.tv_nsec / 1000'000'000LL);
}

