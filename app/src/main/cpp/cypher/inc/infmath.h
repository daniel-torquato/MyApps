#ifndef __INFINITY_MATH__
#define __INFINITY_MATH__
#include <object.h>
#include <vector>

class InfNumber : Object {
private:
    std::vector<unsigned char> slots;
public:
    InfNumber(unsigned int x);

    InfNumber(const std::vector<unsigned char> &x);

    void set(unsigned int slot, unsigned char value);

    InfNumber operator+(const InfNumber& v);

    std::vector<unsigned char> get();

    std::string toString() override;
};

#endif
