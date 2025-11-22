#include <infmath.h>
#include <string.h>
#include <iostream>

InfNumber::InfNumber(unsigned int x) {
    slots = std::string (x, '\0');
}

InfNumber::InfNumber(const std::string &x) {
    slots = x;
    std::cout << "Build Infinity number: " << x << std::endl;
}

std::string InfNumber::get() {
    return slots;
}

void InfNumber::set(unsigned int slot, char value) {
    slots[slot] = value;
}
