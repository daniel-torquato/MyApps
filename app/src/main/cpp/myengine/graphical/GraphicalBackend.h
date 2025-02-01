//
// Created by DAT20 on 10/11/2024.
//

#ifndef MYGAMES_GRAPHICALBACKEND_H
#define MYGAMES_GRAPHICALBACKEND_H

#include <GLES3/gl3.h>
#include "types/TextureBitmap.h"

class GraphicalBackend {
public:
    static unsigned int loadTexture(const TextureBitmap &texture);
};

#endif //MYGAMES_GRAPHICALBACKEND_H
