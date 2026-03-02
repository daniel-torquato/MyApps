//
// Created by torquato on 03/01/26.
//
#include <slot.h>
#include <android/log.h>

std::tuple<slot, slot> slot::operator+(const slot &v) const {
    u_long tmp = value + v.value;
    slot low = slot(tmp & UINT_MAX);
    slot high = slot(tmp >> sizeof(UINT_MAX));
    return {low, high};
}

slot::slot(u_int input) {
    value = input;
}

slot::slot() {
    value = 0;
}

void slot::set(u_int pos, u_char input) {
    value = (value & ~(0xff << (pos * 8)) | (input << (pos * 8)));
}

u_char slot::get(u_int pos) const {
    return (value >> (pos * 8)) & 0xff;
}
