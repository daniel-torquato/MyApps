//
// Created by DAT20 on 9/16/2024.
//
#include "Camera.h"

glm::mat4 Camera::getView() const {
    glm::vec3 newUp = glm::normalize(glm::cross(right, position));
    return  glm::lookAt(position, glm::normalize(position), newUp);
}

void Camera::update(Control control) {
    right = glm::normalize(glm::cross(position, up));
    glm::vec3 newUp = glm::normalize(glm::cross(right, position));
    glm::vec2 move = control.getSpeed(0);
    position += 0.01f * (right * move.x + newUp * move.y);
    front = -position;
}