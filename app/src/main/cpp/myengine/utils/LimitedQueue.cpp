//
// Created by DAT20 on 9/21/2024.
//
#include "LimitedQueue.h"

LimitedQueue::LimitedQueue(int size) {
    max_size = size;
    buffer = std::vector<glm::vec2>(size);
}

void LimitedQueue::add(glm::vec2 element) {

    if (current_size < max_size)
        ++current_size;

    ++tail_index;

    if (current_size == max_size) {
        ++head_index;
    }

    if (tail_index % max_size == max_size - 1) {
        tail_index = max_size - 1;
        head_index = 0;
    }

    buffer[tail_index % max_size] = element;
}

glm::vec2 LimitedQueue::get(int index) {
    return buffer[(tail_index - index + max_size) % max_size];
}

int LimitedQueue::size() const {
    return current_size;
}

void LimitedQueue::clean() {
    current_size = 0;
    head_index = -1;
}

LimitedQueue::LimitedQueue() {}
