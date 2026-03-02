cmake_minimum_required(VERSION 3.22.1)


get_filename_component(ProjectId ${CMAKE_CURRENT_SOURCE_DIR} NAME)
string(REPLACE " " "_" ProjectId ${ProjectId})
project("${ProjectId}")

add_library(${PROJECT_NAME} SHARED calculator.cpp)

target_link_libraries(${PROJECT_NAME}
    android
    log)
