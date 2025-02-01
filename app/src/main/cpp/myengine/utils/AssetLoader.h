//
// Created by DAT20 on 9/22/2024.
//

#ifndef MYGAMES_ASSETLOADER_H
#define MYGAMES_ASSETLOADER_H

#include <string>
#include <android/asset_manager.h>
#include <android/imagedecoder.h>
#include "types/TextureBitmap.h"
#include <cassert>

class AssetLoader {
public:
    static std::string loadFile(AAssetManager *mAssetManager, const char *filename);
    static TextureBitmap loadAsset(AAssetManager *assetManager, const std::string &assetPath);
private:
};

#endif //MYGAMES_ASSETLOADER_H
