//
// Created by DAT20 on 10/5/2024.
//
#include "Observer.h"

void Observer::update(float width, float height) {
    if (height == 0.0f)
        return;
    float ratio = width / height;
    projection = glm::perspective(glm::radians(45.0f), ratio, 0.1f, 50.0f);
}

