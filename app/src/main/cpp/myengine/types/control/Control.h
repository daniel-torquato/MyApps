//
// Created by DAT20 on 9/16/2024.
//

#ifndef MYGAMES_CONTROL_H
#define MYGAMES_CONTROL_H

#include "third-party/glm/glm.hpp"
#include "utils/LimitedQueue.h"
#include <unordered_map>

class Control {
public:
    void addEvent(int id, glm::vec2 point);
    void createPosition(int id);
    void stop(int id);
    glm::vec2 getSpeed(int id);
private:
    std::unordered_map<int, LimitedQueue> positions;
    const int buffer_size = 3;
};

#endif //MYGAMES_CONTROL_H
