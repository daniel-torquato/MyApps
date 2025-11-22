#ifndef __INFINITY_MATH__
#define __INFINITY_MATH__
#include <string>

class InfNumber {
	private:
		std::string slots;
	public:
		InfNumber(unsigned int x);
		InfNumber(const std::string &x);
        void set(unsigned int slot, char value);
        std::string get();
};

//std::ostream& operator<<(std::ostream &os, const InfNumber &word) {
//	return os;
//}

#endif
