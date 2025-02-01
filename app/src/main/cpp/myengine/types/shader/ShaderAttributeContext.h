//
// Created by DAT20 on 9/29/2024.
//

#ifndef MYGAMES_SHADERATTRIBUTECONTEXT_H
#define MYGAMES_SHADERATTRIBUTECONTEXT_H

#include <vector>
#include "ShaderAttributeHolder.h"

struct ShaderAttributeContext {
    int data_size;
    std::vector<ShaderAttributeHolder> holders;
};

#endif //MYGAMES_SHADERATTRIBUTECONTEXT_H
