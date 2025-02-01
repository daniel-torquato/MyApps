//
// Created by DAT20 on 10/11/2024.
//

#ifndef MYGAMES_TEXTUREBITMAP_H
#define MYGAMES_TEXTUREBITMAP_H

#include <memory>
#include <string>
#include <vector>

class TextureBitmap {
public:
    std::string id;
    int width = 0;
    int height = 0;
    std::shared_ptr<std::vector<uint8_t>> data;
};

#endif //MYGAMES_TEXTUREBITMAP_H
