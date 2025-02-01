//
// Created by DAT20 on 9/22/2024.
//
#include "SceneLoader.h"


Scene SceneLoader::base(AAssetManager *assetManager) {
    Scene scene_;

    Model cube = ModelLoader::cube(assetManager);
    Model square = ModelLoader::square(assetManager);

    std::vector<glm::vec3> cubePositions = {
            glm::vec3(0.0f, 0.0f, 0.0f),
            glm::vec3(2.0f, 5.0f, -15.0f),
            glm::vec3(-1.5f, -2.2f, -2.5f),
            glm::vec3(-3.8f, -2.0f, -12.3f),
            glm::vec3(2.4f, -0.4f, -3.5f),
            glm::vec3(-1.7f, 3.0f, -7.5f),
            glm::vec3(1.3f, -2.0f, -2.5f),
            glm::vec3(1.5f, 2.0f, -2.5f),
            glm::vec3(1.5f, 0.2f, -1.5f),
            glm::vec3(-1.3f, 1.0f, -1.5f)
    };

    glm::vec3 rotation_axis = {1.0f, 0.3f, 0.5f};
    float rotation_factor = 20.0f;

    for(unsigned int i = 0; i < cubePositions.size(); ++i) {
        scene_.objects.emplace_back(
                Object{
                        .model_ = cube,
                        .is_fixed_ = false,
                        .transformation_ = Transformation{
                                .translation = cubePositions[i],
                                .rotation = {
                                        .angle = rotation_factor * (float) i,
                                        .axis = rotation_axis
                                },
                        }
                }
        );
    }

    scene_.objects.emplace_back(
            Object{
                    .model_ = square,
                    .is_fixed_ = true,
                    .transformation_ = Transformation{
                            .translation = {-0.0f, -0.185f, -0.5f},
                            .scale = {0.02f, 0.02f, 0.0f}
                    }
            }
    );

    return scene_;
}

Scene SceneLoader::fixed(AAssetManager *assetManager) {
    Scene scene_;

    Model cube = ModelLoader::cube(assetManager);
    Model square = ModelLoader::square(assetManager);

    // std::vector<glm::vec3> cubePositions = {
    //         {-2.2f,-6.6f, 0.0f},
    //         {-2.2f,-3.3f, 0.0f},
    //         {-2.2f, 0.0f, 0.0f},
    //         {-2.2f, 3.3f, 0.0f},
    //         {-2.2f, 6.6f, 0.0f},
    //         { 2.2f,-6.6f, 0.0f},
    //         { 2.2f,-3.3f, 0.0f},
    //         { 2.2f, 0.0f, 0.0f},
    //         { 2.2f, 3.3f, 0.0f},
    //         { 2.2f, 6.6f, 0.0f},
    // };

    std::vector<glm::vec3> cubePositions = {
            { 0.0f, 0.0f, 0.0f},
    };

    glm::vec3 rotation_axis = {1.0f, 0.3f, 0.5f};
    float rotation_factor = 0.0f;

    for(unsigned int i = 0; i < cubePositions.size(); ++i) {
        scene_.objects.emplace_back(
                Object{
                        .model_ = cube,
                        .is_fixed_ = false,
                        .transformation_ = Transformation{
                                .translation = cubePositions[i],
                                .rotation = {
                                        .angle = rotation_factor * (float) i,
                                        .axis = rotation_axis
                                },
                                .scale = {3.0f, 3.0f, 3.0f}
                        }
                }
        );
    }

    scene_.objects.emplace_back(
            Object{
                    .model_ = square,
                    .is_fixed_ = true,
                    .transformation_ = Transformation{
                            .translation = {-0.06f, -0.185f, -0.5f},
                            .scale = {0.02f, 0.02f, 0.0f}
                    }
            }
    );

    return scene_;
}
