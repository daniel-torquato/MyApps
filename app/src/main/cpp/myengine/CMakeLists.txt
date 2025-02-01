# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

cmake_minimum_required(VERSION 3.22.1)

get_filename_component(ProjectId ${CMAKE_CURRENT_SOURCE_DIR} NAME)
string(REPLACE " " "_" ProjectId ${ProjectId})
project("${ProjectId}")

add_subdirectory(third-party/glm)

# Creates your game shared library. The name must be the same as the
# one used for loading in your Kotlin/Java or AndroidManifest.txt files.
include_directories(utils types loader graphical)

add_library(${PROJECT_NAME} SHARED
        utils/LimitedQueue.cpp
        utils/TimeUtil.cpp
        utils/AssetLoader.cpp
        graphical/GraphicalBackend.cpp
        main.cpp
        AndroidOut.cpp
        Renderer.cpp
        loader/ModelLoader.cpp
        loader/SceneLoader.cpp
        Shader.cpp
        types/observer/Camera.cpp
        types/observer/Observer.cpp
        types/control/Control.cpp
        utils/Utility.cpp
)

# Searches for a package provided by the game activity dependency
find_package(game-activity REQUIRED CONFIG)

# Configure libraries CMake uses to link your target library.
target_link_libraries(
        ${PROJECT_NAME}
        # The game activity
        game-activity::game-activity

        # EGL and other dependent libraries required for drawing
        # and interacting with Android system
        EGL
        GLESv3
        glm::glm
        jnigraphics
        android
        log)