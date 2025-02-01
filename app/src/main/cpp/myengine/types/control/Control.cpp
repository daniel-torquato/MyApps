//
// Created by DAT20 on 9/16/2024.
//
#include "Control.h"

void Control::addEvent(int id, glm::vec2 point) {
    positions[id].add(point);
}

void Control::createPosition(int id) {
    if (positions.find(id) != positions.end())
        positions[id].clean();
    else
        positions.insert({{id, LimitedQueue(buffer_size)}});
}

void Control::stop(int id) {
    if (positions.find(id) != positions.end())
        positions[id].clean();
}

glm::vec2 Control::getSpeed(int id) {
    glm::vec2 speed = glm::vec2 {0.0f};
    if (positions[id].size() > 1)
        speed += positions[id].get(0) - positions[id].get(1);
    return speed;
}