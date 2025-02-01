//
// Created by DAT20 on 9/15/2024.
//
#include "ModelLoader.h"
#include "types/Vertex.h"

Model ModelLoader::square(AAssetManager *assetManager) {
    /*
     * This is a square:
     *   0 --- 1
     *   | \   |
     *   |  \  |
     *   |   \ |
     *   3 --- 2
     */
    std::vector<glm::vec3> points = {
            /* 0 */  glm::vec3{-1, 1, 0},
            /* 1 */  glm::vec3{1, 1, 0},
            /* 2 */  glm::vec3{1, -1, 0},
            /* 3 */  glm::vec3{-1, -1, 0},
    };

    /*
     *    y x->
     *    |
     *    v
     *
     *    0 --- 1
     *    |     |
     *    |     |
     *    |     |
     *    3 --- 2
     */
    std::vector<glm::vec2> textPoints = {
            /* 0 */  glm::vec2{0, 0},
            /* 1 */  glm::vec2{1, 0},
            /* 2 */  glm::vec2{1, 1},
            /* 3 */  glm::vec2{0, 1}
    };

    std::vector<glm::vec3> normals = {
            {0.0f, 0.0f, -1.0f}
    };

    std::vector<Index> indices = {
            0, 1, 2,
            0, 2, 3
    };

    std::vector<Vertex> vertices = {
            Vertex(points[0], textPoints[0], normals[0]),
            Vertex(points[1], textPoints[1], normals[0]),
            Vertex(points[2], textPoints[2], normals[0]),
            Vertex(points[3], textPoints[3], normals[0])
    };

    // auto spAndroidRobotTexture = TextureAsset::loadAsset(assetManager, "textures/img.png");
    auto textures = std::vector<Texture>{
        Texture {
            .id = 0,
            .type = DIFFUSE,
            .bitmap = AssetLoader::loadAsset(assetManager, "textures/img.png")
        }
    };
    return Model{vertices, indices, textures};
}

Model ModelLoader::cube(AAssetManager *assetManager) {
      std::vector<glm::vec3> points3d = {
          /* 0 */ glm::vec3{-1.0f, -1.0f, -1.0f},
          /* 1 */ glm::vec3{-1.0f, -1.0f, 1.0f},
          /* 2 */ glm::vec3{-1.0f, 1.0f, -1.0f},
          /* 3 */ glm::vec3{-1.0f, 1.0f, 1.0f},
          /* 4 */ glm::vec3{1.0f, -1.0f, -1.0f},
          /* 5 */ glm::vec3{1.0f, -1.0f, 1.0f},
          /* 6 */ glm::vec3{1.0f, 1.0f, -1.0f},
          /* 7 */ glm::vec3{1.0f, 1.0f, 1.0f},
    };



    /*
     *    y x->
     *    |
     *    v
     *
     *    0 --- 1
     *    |     |
     *    |     |
     *    |     |
     *    3 --- 2
     */
    std::vector<glm::vec2> textPoints = {
            /* 0 */  glm::vec2{0, 0},
            /* 1 */  glm::vec2{1, 0},
            /* 2 */  glm::vec2{1, 1},
            /* 3 */  glm::vec2{0, 1}
    };

    std::vector<glm::vec3> normals = {
            /* +x */ {1.0f, 0.0f, 0.0f},
            /* +y */ {0.0f, 1.0f, 0.0f},
            /* +z */ {0.0f, 0.0f, 1.0f}
    };

    /*
     *      2------6
     *     /|     /| 0 --- 1    3-----7 7-----6 6-----2 2-----3  1-----5 2-----6
     *    3------7 | |     |    |    /| |    /| |    /| |    /|  |    /| |    /|
     *    | |    | | |     |    |  /  | |  /  | |  /  | |  /  |  |  /  | |  /  |
     *    | 0 ---|-4 |     |    |/    | |/    | |/    | |/    |  |/    | |/    |
     *    |/     |/  3 --- 2    1-----5 5-----4 4-----0 0-----1  0-----4 3-----7
     *    1------5
     */

    std::vector<Vertex> vertices3d = {
            /*
             * 01 37
             * 32 15
             */
            /* 00 30 */ Vertex(points3d[3], textPoints[0], normals[2]),
            /* 01 71 */ Vertex(points3d[7], textPoints[1], normals[2]),
            /* 02 52 */ Vertex(points3d[5], textPoints[2], normals[2]),
            /* 03 13 */ Vertex(points3d[1], textPoints[3], normals[2]),

            /*
             * 01 76
             * 32 54
             */
            /* 04 70 */ Vertex(points3d[7], textPoints[0], normals[0]),
            /* 05 61 */ Vertex(points3d[6], textPoints[1], normals[0]),
            /* 06 42 */ Vertex(points3d[4], textPoints[2], normals[0]),
            /* 07 53 */ Vertex(points3d[5], textPoints[3], normals[0]),

            /*
             * 01 62
             * 32 40
             */
            /* 08 60 */ Vertex(points3d[6], textPoints[0], -normals[2]),
            /* 09 21 */ Vertex(points3d[2], textPoints[1], -normals[2]),
            /* 10 02 */ Vertex(points3d[0], textPoints[2], -normals[2]),
            /* 11 43 */ Vertex(points3d[4], textPoints[3], -normals[2]),

            /*
             * 01 23
             * 32 01
             */
            /* 12 20 */ Vertex(points3d[2], textPoints[0], -normals[0]),
            /* 13 31 */ Vertex(points3d[3], textPoints[1], -normals[0]),
            /* 14 12 */ Vertex(points3d[1], textPoints[2], -normals[0]),
            /* 15 03 */ Vertex(points3d[0], textPoints[3], -normals[0]),

            /*
             * 01 15
             * 32 04
             */
            /* 16 10 */ Vertex(points3d[1], textPoints[0], -normals[1]),
            /* 17 51 */ Vertex(points3d[5], textPoints[1], -normals[1]),
            /* 18 42 */ Vertex(points3d[4], textPoints[2], -normals[1]),
            /* 19 03 */ Vertex(points3d[0], textPoints[3], -normals[1]),

            /*
             * 01 26
             * 32 37
             */
            /* 20 20 */ Vertex(points3d[2], textPoints[0], normals[1]),
            /* 21 61 */ Vertex(points3d[6], textPoints[1], normals[1]),
            /* 22 72 */ Vertex(points3d[7], textPoints[2], normals[1]),
            /* 23 33 */ Vertex(points3d[3], textPoints[3], normals[1]),
    };


    std::vector<Index> indices3d = {
              0,  1,  2,  0,  2,  3,
              4,  5,  6,  4,  6,  7,
              8,  9, 10,  8, 10, 11,
             12, 13, 14, 12, 14, 15,
             16, 17, 18, 16, 18, 19,
             20, 21, 22, 20, 22, 23
    };

    //         AssetLoader::loadAsset(assetManager, "textures/img.png"),
    auto textures = std::vector<Texture>{
            Texture{
                    .id = 0,
                    .type = DIFFUSE,
                    .bitmap = AssetLoader::loadAsset(assetManager, "textures/container2.png"),

            },
            Texture{
                    .id = 0,
                    .type = SPECULAR,
                    .bitmap = AssetLoader::loadAsset(assetManager,
                                                     "textures/container2_specular.png"),
            }
    };
    return Model{vertices3d, indices3d, textures};
}