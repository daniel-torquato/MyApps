//
// Created by DAT20 on 10/6/2024.
//

#ifndef MYGAMES_SHADERINTERFACE_H
#define MYGAMES_SHADERINTERFACE_H

#include <string>
#include "ShaderUniformReference.h"
#include "ShaderAttributeContainer.h"

class ShaderInterface {
public:
    std::string fragmentFile;
    std::string vertexFile;
    std::vector<ShaderUniformReference> uniformList;
    ShaderAttributeContainer attributeContainer;
};

#endif //MYGAMES_SHADERINTERFACE_H
