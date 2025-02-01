//
// Created by DAT20 on 9/15/2024.
//

#ifndef MYGAMES_MODELLOADER_H
#define MYGAMES_MODELLOADER_H

#include "utils/AssetLoader.h"
#include <vector>
#include "types/Model.h"
#include "types/TextureType.h"

class ModelLoader {
public:
    static Model square(AAssetManager *assetManager);

    static Model cube(AAssetManager *assetManager);

private:
};

#endif //MYGAMES_MODELLOADER_H
