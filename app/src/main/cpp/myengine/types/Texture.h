//
// Created by DAT20 on 10/16/2024.
//

#ifndef MYGAMES_TEXTURE_H
#define MYGAMES_TEXTURE_H

#include "TextureType.h"
#include "TextureBitmap.h"

struct Texture {
    unsigned int id;
    TextureType type;
    TextureBitmap bitmap;
};

#endif //MYGAMES_TEXTURE_H
