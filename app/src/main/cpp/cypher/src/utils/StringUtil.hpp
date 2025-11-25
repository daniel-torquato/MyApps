//
// Created by torquato on 23/11/25.
//
#include <string>
#include <vector>

class StringUtil {
public:
    static std::vector<unsigned char> fromString(std::string input) {
        std::vector<unsigned char> buffer((input.size() + 1) / 2);
        for (int i = 0; i < buffer.size(); ++i) {
            buffer[i] = convertToChar(input[2 * i]) * 0x10;
            if (2 * i + 1 < input.size()) {
                buffer[i] += convertToChar(input[2 * i + 1]);
            }
        }
        return buffer;
    }

    static std::string convertToChar(unsigned char value) {
        std::string buffer(2, 0x00);
        unsigned char low = (value & 0xf0) >> 4;
        unsigned char high = value & 0x0f;

       // __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "PRE %x %x %x", low, high, value);

        buffer[0] = fromUByte(low);
        buffer[1] = fromUByte(high);

      //  __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "POS %x %x %x", value, buffer[0], buffer[1]);

        return buffer;
    }
private:
    static unsigned char convertToChar(char low) {
        unsigned char buffer = 0;
        if ('a' <= low && low <= 'f') {
            buffer += (low - 'a') + 10;
        } else if ('0' <= low && low <= '9') {
            buffer += low - '0';
        }
        return buffer;
    }
    
    static char fromUByte(unsigned char value) {
        char buffer = 0;
        if (0x0a <= value && value <= 0x0f) {
            buffer += (value - 0x0a) + 'a';
        } else {
            buffer += value + '0';
        }
        return buffer;
    }
};