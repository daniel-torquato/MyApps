#ifndef INFINITY_MATH
#define INFINITY_MATH
#include <object.h>
#include <vector>

class InfNumber : Object {
private:
    const static uint MAX_SIZE = 17;
    std::vector<u_char> slots;
public:
    explicit InfNumber(uint x);

    InfNumber(const InfNumber& v);

    explicit InfNumber(const std::vector<u_char> &x);

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
    InfNumber operator-();
    bool operator==(const InfNumber& v);

    [[nodiscard]]
    std::string toString() const override;

    [[nodiscard]]
    std::string toBuffer() const;
};

#endif
