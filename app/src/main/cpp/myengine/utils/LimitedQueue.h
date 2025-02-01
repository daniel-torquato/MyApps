//
// Created by DAT20 on 9/21/2024.
//

#ifndef MYGAMES_LIMITEDQUEUE_H
#define MYGAMES_LIMITEDQUEUE_H

#include <vector>
#include "third-party/glm/glm.hpp"

class LimitedQueue {
public:
    LimitedQueue();
    explicit LimitedQueue(int size);
    void add(glm::vec2 element);
    glm::vec2 get(int index);
    int size() const;
    void clean();
private:
    int max_size = 0;
    int current_size = 0;
    int head_index = 0;
    int tail_index = -1;
    std::vector<glm::vec2> buffer;
};



#endif //MYGAMES_LIMITEDQUEUE_H
