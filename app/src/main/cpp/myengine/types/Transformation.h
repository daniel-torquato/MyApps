//
// Created by DAT20 on 10/3/2024.
//

#ifndef MYGAMES_TRANSFORMATION_H
#define MYGAMES_TRANSFORMATION_H

#include <third-party/glm/glm.hpp>
#include "third-party/glm/ext/matrix_transform.hpp"

class Transformation {
public:
    glm::vec3 translation = DEFAULT_TRANSLATION;
    class Rotation {
    public:
        float angle;
        glm::vec3 axis;
    } rotation = DEFAULT_ROTATION;
    glm::vec3 scale = DEFAULT_SCALE;

    [[nodiscard]]
    glm::mat4 toMatrix() const {
        auto transformation = glm::identity<glm::mat4>();
        transformation = glm::translate(transformation, translation);
        transformation = glm::rotate(transformation, rotation.angle, rotation.axis);
        transformation = glm::scale(transformation, scale);
        return transformation;
    }
    constexpr static const glm::vec3 DEFAULT_TRANSLATION = glm::vec3{0.0f, 0.0f, 0.0f};
    constexpr static const Rotation DEFAULT_ROTATION = {0.0f, {1.0f, 0.0f, 0.0f}};
    constexpr static const glm::vec3 DEFAULT_SCALE = glm::vec3{1.0f, 1.0f, 1.0f};
};


#endif //MYGAMES_TRANSFORMATION_H
