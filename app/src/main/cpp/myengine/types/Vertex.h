//
// Created by DAT20 on 9/29/2024.
//

#ifndef MYGAMES_VERTEX_H
#define MYGAMES_VERTEX_H

#include <third-party/glm/glm.hpp>

struct Vertex {
    constexpr Vertex(
            const glm::vec3 &inPosition,
            const glm::vec2 &inUV,
            const glm::vec3 &inNormal
    ) : position(inPosition),
        uv(inUV),
        normal(inNormal) {}

    glm::vec3 position;
    glm::vec2 uv;
    glm::vec3 normal;
};

#endif //MYGAMES_VERTEX_H
