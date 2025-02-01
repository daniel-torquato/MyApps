//
// Created by DAT20 on 9/22/2024.
//

#ifndef MYGAMES_SCENELOADER_H
#define MYGAMES_SCENELOADER_H

#include "types/Scene.h"
#include "ModelLoader.h"
#include "third-party/glm/glm.hpp"
#include "third-party/glm/ext/matrix_transform.hpp"
#include "third-party/glm/gtc/type_ptr.hpp"
#include <android/asset_manager.h>
#include "types/Transformation.h"

class SceneLoader {
public:
    static Scene base(AAssetManager *assetManager);
    static Scene fixed(AAssetManager *assetManager);
private:
};

#endif //MYGAMES_SCENELOADER_H
