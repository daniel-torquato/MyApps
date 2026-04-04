//
// Created by torquato on 03/01/26.
//
#ifndef SLOT_H
#define SLOT_H
#include <tuple>

class slot {
private:
    u_int value;
public:
    explicit slot();
    explicit slot(u_int input);
    std::tuple<slot, slot> operator+(const slot& v) const;

    void set(u_int pos, u_char input);
    u_char get(u_int pos) const;

    const static size_t size_of = sizeof(value);
};



#endif