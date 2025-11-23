#include <infmath.h>
#include <string.h>
#include <iostream>
#include <StringUtil.hpp>
#include <android/log.h>

InfNumber::InfNumber(unsigned int x) {
    slots = std::vector<unsigned char>(x, '\0');
}

InfNumber::InfNumber(const std::vector<unsigned char> &x) {
    slots = x;
}

std::vector<unsigned char> InfNumber::get() {
    return slots;
}

void InfNumber::set(unsigned int slot, unsigned char value) {
    slots[slot] = value;
}

InfNumber InfNumber::operator+(const InfNumber &v) {
    InfNumber buffer(v.slots);
    InfNumber *big_slot = &buffer;
    InfNumber *small_slot = this;

    if (buffer.slots.size() < slots.size()) {
        small_slot = &buffer;
        big_slot = this;
    }
    unsigned int max_size = big_slot->slots.size();
    unsigned int min_size = small_slot->slots.size();

    int carry = 0;
    for (int i = 0; i < min_size; ++i) {
        int tmp = big_slot->slots[i] + small_slot->slots[i] + carry;
        buffer.slots[i] = tmp % 256;
        carry = tmp / 256;
    }
    for (int i = min_size; i < max_size; ++i) {
        int tmp = big_slot->slots[i] + carry;
        if (buffer.slots.size() >= big_slot->slots.size()) {
            buffer.slots[i] = tmp % 256;
        } else {
            buffer.slots.emplace_back(tmp % 256);
        }
        carry = tmp / 256;
    }
    if (carry != 0)
        buffer.slots.emplace_back(carry);

    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "OUT %s", buffer.toString().c_str());
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "SIZE BIG %d=%d", big_slot->slots.size(),
                        max_size);
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "SIZE SMALL %d=%d", small_slot->slots.size(),
                        min_size);

    return buffer;
}

std::string InfNumber::toString() {
    std::string buffer(2 * slots.size(), '0');
    for (int i = 0; i < slots.size(); ++i) {
        std::string tmp = StringUtil::convertToChar(slots[i]);
        buffer[2 * i + 0] = tmp[0];
        buffer[2 * i + 1] = tmp[1];
    }
    return buffer;
}


