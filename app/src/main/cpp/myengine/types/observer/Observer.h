//
// Created by DAT20 on 10/5/2024.
//

#ifndef MYGAMES_OBSERVER_H
#define MYGAMES_OBSERVER_H

#include "Camera.h"
#include <third-party/glm/glm.hpp>
#include <third-party/glm/ext/matrix_clip_space.hpp>

class Observer {
public:
    glm::mat4 projection;
    Camera camera;
    void update(float width, float height);
};


#endif //MYGAMES_OBSERVER_H
