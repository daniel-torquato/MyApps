//
// Created by torquato on 23/11/25.
//

#ifndef __OBJECT_H__
#define __OBJECT_H__
#include <string>
#include <android/log.h>

class Object {
public:
    virtual std::string toString() const = 0;
};

#endif // __OBJECT_H__
