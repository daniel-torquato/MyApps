//
// Created by DAT20 on 9/15/2024.
//

#ifndef MYGAMES_OBJECT_H
#define MYGAMES_OBJECT_H

#include "Model.h"
#include "Transformation.h"

class Object {
public:
    Model model_;
    bool is_fixed_{};
    Transformation transformation_;
};

#endif //MYGAMES_OBJECT_H
