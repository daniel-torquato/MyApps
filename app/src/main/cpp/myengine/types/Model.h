#ifndef ANDROIDGLINVESTIGATIONS_MODEL_H
#define ANDROIDGLINVESTIGATIONS_MODEL_H

#include <utility>
#include <vector>
#include "Vertex.h"
#include "Index.h"
#include "Texture.h"

class Model {
public:
    inline Model(
            std::vector<Vertex> vertices,
            std::vector<Index> indices,
            std::vector<Texture> textures
    ) : vertices_(std::move(vertices)),
        indices_(std::move(indices)),
        textures_{std::move(textures)} {}

    [[nodiscard]]
    inline const Vertex *getVertexData() const {
        return vertices_.data();
    }

    [[nodiscard]]
    inline size_t getIndexCount() const {
        return indices_.size();
    }

    [[nodiscard]]
    inline const Index *getIndexData() const {
        return indices_.data();
    }

    [[maybe_unused]]
    [[nodiscard]]
    inline std::vector<Texture> getTextures() const {
        return textures_;
    }


private:
    std::vector<Vertex> vertices_;
    std::vector<Index> indices_;
    std::vector<Texture> textures_;
};

#endif //ANDROIDGLINVESTIGATIONS_MODEL_H