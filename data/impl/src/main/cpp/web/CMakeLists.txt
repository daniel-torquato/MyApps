cmake_minimum_required(VERSION 3.22.1)


get_filename_component(ProjectId ${CMAKE_CURRENT_SOURCE_DIR} NAME)
string(REPLACE " " "_" ProjectId ${ProjectId})
project("${ProjectId}")

add_library(${PROJECT_NAME} SHARED search.cpp)
#add_library(${PROJECT_NAME}_test SHARED  "test/search.cpp")

find_package(openssl REQUIRED CONFIG)
find_package(curl    REQUIRED CONFIG)

target_link_libraries(${PROJECT_NAME}
        curl::curl
        android
        log)

#target_link_libraries(${PROJECT_NAME}_test
#        curl::curl
#        openssl::openssl
#        android
#        log)
