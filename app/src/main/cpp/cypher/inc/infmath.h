#ifndef _INFINITY_MATH_
#define _INFINITY_MATH_
#include <object.h>
#include <vector>

class InfNumber : Object {
private:
    std::vector<u_char> slots;
public:
    InfNumber(uint x);

    InfNumber(const InfNumber& v);

    InfNumber(const std::vector<u_char> &x);

    void set(uint slot, u_char value);

    [[nodiscard]]
    u_char get(uint slot) const;

    [[nodiscard]]
    std::vector<u_char> get();

    void add(uint slot, u_char v);

    void clean();

    void clean(uint s);

    [[nodiscard]]
    uint size() const;

    InfNumber operator+(const InfNumber& v);
    InfNumber operator*(const InfNumber& v);
    InfNumber operator%(const InfNumber& v);
    InfNumber operator>>(uint n);
    bool operator==(const InfNumber& v);

    [[nodiscard]]
    std::string toString() const override;
};

#endif
