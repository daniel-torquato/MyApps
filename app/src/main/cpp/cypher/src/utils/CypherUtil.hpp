#ifndef __POLY1305__
#define __POLY1305__
#include <infmath.h>
#include <android/log.h>

class CypherUtil {
private:
public:
    static InfNumber poly1305(const InfNumber &r, const InfNumber &m) {
        unsigned int j;
        unsigned int l = m.size();
        uint slot = 0;
        InfNumber h(0u);
        InfNumber tmp(0u);

        for (slot = 0; slot <= (m.size() + 1) / 16; ++slot) {
            InfNumber c(0);
            for (j = 0; j < 16 && j < m.size(); ++j) {
                c.set(j, m.get(slot * 16 + j));
            }
            c.set(j, 0x01);

            tmp = c;

            __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "PARTIAL1 %d %s", slot, tmp.toString().c_str());
            tmp = h + c;

            __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "PARTIAL2 %d %s", slot, tmp.toString().c_str());

            tmp = tmp * r;

            __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "PARTIAL3 %d %s", slot, tmp.toString().c_str());

            tmp = reduce(tmp);

            __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "PARTIAL4 %d %s", slot, tmp.toString().c_str());

            h = tmp;
        }

        return h;
    }

    static InfNumber reduce(const InfNumber& v) {
        InfNumber ret(v);
        InfNumber tmp(0u);
        static const InfNumber factor(std::vector<u_char>{0x05});

        if (v.size() > 16) {
            u_char block = ret.get(16);
            ret.set(16, block & 0x03);
            tmp.set(0, block  & 0xfc);
        }

        for (uint i = 17; i < v.size(); ++i) {
            u_char block = ret.get(i);
            tmp.set(i - 16, block);
        }

        __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "REDUCE1 %s %s %s",
                            ret.toString().c_str(),
                            v.toString().c_str(),
                            tmp.toString().c_str()
        );

        tmp = tmp >> 2;

        __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "REDUCE2 %s %s %s",
                            ret.toString().c_str(),
                            v.toString().c_str(),
                            tmp.toString().c_str()
        );

        ret.clean(17);

        __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "REDUCE3 %s %s %s",
                            ret.toString().c_str(),
                            v.toString().c_str(),
                            tmp.toString().c_str());

        tmp = tmp * factor;

        __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "REDUCE4 %s %s %s %s",
                            ret.toString().c_str(),
                            v.toString().c_str(),
                            tmp.toString().c_str(),
                            factor.toString().c_str());

        ret = ret + tmp;

        __android_log_print(ANDROID_LOG_DEBUG, "MyTag", "REDUCE5 %s %s %s",
                            ret.toString().c_str(),
                            v.toString().c_str(),
                            tmp.toString().c_str()
        );

        return ret;
    }
};


#endif
