//
// Created by DAT20 on 9/16/2024.
//

#ifndef MYGAMES_CAMERA_H
#define MYGAMES_CAMERA_H

#include "third-party/glm/glm.hpp"
#include "third-party/glm/ext/matrix_transform.hpp"
#include "types/control/Control.h"

class Camera {
public:
    glm::vec3 position;
    glm::vec3 up;
    glm::vec3 front;
    glm::vec3 right;
    void update(Control control);
    glm::mat4 getView() const;
};

#endif //MYGAMES_CAMERA_H
