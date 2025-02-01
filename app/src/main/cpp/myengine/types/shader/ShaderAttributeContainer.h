//
// Created by DAT20 on 9/29/2024.
//

#ifndef MYGAMES_SHADERATTRIBUTECONTAINER_H
#define MYGAMES_SHADERATTRIBUTECONTAINER_H

#include <vector>
#include "ShaderAttributeReference.h"

struct ShaderAttributeContainer {
    int data_size;
    std::vector<ShaderAttributeReference> references;
};

#endif //MYGAMES_SHADERATTRIBUTECONTAINER_H
