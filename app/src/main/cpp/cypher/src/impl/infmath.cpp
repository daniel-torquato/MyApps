#include <infmath.h>
#include <string>
#include <iostream>
#include <StringUtil.hpp>
#include <android/log.h>

InfNumber::InfNumber(unsigned int x) {
    slots = std::vector<unsigned char>(x, '\0');
}

InfNumber::InfNumber(const InfNumber& v) {
    slots = std::vector<u_char>(v.slots);
}

InfNumber::InfNumber(const std::vector<unsigned char> &x) {
    slots = x;
}

std::vector<unsigned char> InfNumber::get() {
    return slots;
}

void InfNumber::set(uint slot, u_char value) {
    for (uint i = slots.size(); i <= slot; ++i) {
        slots.emplace_back(0x00);
    }
    slots[slot] = value;
}

u_char InfNumber::get(uint slot) const {
    u_char ret = 0;
    if (slot < slots.size())
        ret = slots[slot];
    return ret;
}

void InfNumber::add(uint slot, u_char v) {
    u_char carry = v;
    for (uint i = slot; i < slots.size(); ++i) {
        u_char tmp = slots[i] + carry;
        slots[i] = tmp & 256;
        carry = tmp / 256;
    }
    if (carry != 0)
        slots.emplace_back(carry);
}

uint InfNumber::size() const {
    return slots.size();
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
    for (uint i = min_size; i < max_size; ++i) {
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

   // __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "OUT %s", buffer.toString().c_str());
   // __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "SIZE BIG %d=%d", big_slot->slots.size(),
   //                     max_size);
   // __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "SIZE SMALL %d=%d", small_slot->slots.size(),
   //                     min_size);

    return buffer;
}

InfNumber InfNumber::operator*(const InfNumber &v) {
    InfNumber buffer(v.slots.size() + slots.size());

    for (int i = 0; i < v.slots.size(); ++i) {
        uint8_t carry = 0;
        uint j = 0;
        for (; j < slots.size(); ++j) {
            int tmp = v.slots[i] * slots[j] + carry + buffer.slots[i + j];
            buffer.slots[i + j] = tmp % 256;
            carry = tmp / 256;
        }

        if (carry != 0) {
            if (i + j < buffer.slots.size())
                buffer.slots[i + j] += carry;
            else
                buffer.slots.emplace_back(carry);
        }
    }

    return buffer;
}

InfNumber InfNumber::operator%(const InfNumber &v) {
    InfNumber buffer(slots);
    return buffer;
}

std::string InfNumber::toString() const {
    std::string buffer(2 * slots.size(), '0');
    for (int i = 0; i < slots.size(); ++i) {
        std::string tmp = StringUtil::convertToChar(slots[i]);
        buffer[2 * i + 0] = tmp[0];
        buffer[2 * i + 1] = tmp[1];
    }
    return buffer;
}


void InfNumber::clean() {
    uint s;

    for (s = slots.size() - 1; s >= 0 && slots[s] == 0; ++s) {}

    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "CLEAN %d", s);

    slots.resize(s);
}

void InfNumber::clean(uint s) {
    __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "CLEAN %d", s);

    slots.resize(s);
}

bool InfNumber::operator==(const InfNumber &v) {
    bool ret = slots.size() == v.size();
    for (uint i = 0; i < slots.size() && ret; ++i) {
        ret = slots[i] == v.get(i);
    }
    return ret;
}

// TODO: Create shift-right to enable division.
InfNumber InfNumber::operator>>(uint n) {
    InfNumber ret(slots);

    if (0 < n && n < 8) {
        const uint8_t block = (1 << n);
        const uint last_id = ret.slots.size() - 1;
        uint8_t carry = 0;

        for (uint i = 0; i < ret.slots.size(); ++i) {
            uint8_t tmp = (ret.slots[last_id - i] >> n);
            if (carry != 0)
                tmp += (carry << (8 - n));

            carry = ret.slots[last_id - i] % block;
            ret.slots[last_id - i] = tmp;
        }
    }

    return ret;
}

