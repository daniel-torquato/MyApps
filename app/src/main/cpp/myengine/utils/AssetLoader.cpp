//
// Created by DAT20 on 9/22/2024.
//

#include "AssetLoader.h"


std::string AssetLoader::loadFile(
        AAssetManager *mAssetManager,
        const char *filename
) {

    AAsset *vertexShader = AAssetManager_open(mAssetManager, filename, AASSET_MODE_UNKNOWN);
    std::string buffer;
    if (vertexShader != nullptr) {
        off_t length = AAsset_getLength(vertexShader);
        off_t offset = 0;
        buffer.resize(length, 0);
        AAsset_read(vertexShader, buffer.data(), length);
    }
    AAsset_close(vertexShader);
    return buffer;
}

TextureBitmap AssetLoader::loadAsset(AAssetManager *assetManager, const std::string &assetPath) {
    // Get the image from asset manager
    auto pAndroidRobotPng = AAssetManager_open(
            assetManager,
            assetPath.c_str(),
            AASSET_MODE_BUFFER);

    // Make a decoder to turn it into a texture
    AImageDecoder *pAndroidDecoder = nullptr;
    auto result = AImageDecoder_createFromAAsset(pAndroidRobotPng, &pAndroidDecoder);
    assert(result == ANDROID_IMAGE_DECODER_SUCCESS);

    // make sure we get 8 bits per channel out. RGBA order.
    AImageDecoder_setAndroidBitmapFormat(pAndroidDecoder, ANDROID_BITMAP_FORMAT_RGBA_8888);

    // Get the image header, to help set everything up
    const AImageDecoderHeaderInfo *pAndroidHeader = nullptr;
    pAndroidHeader = AImageDecoder_getHeaderInfo(pAndroidDecoder);

    // important metrics for sending to GL
    auto width = AImageDecoderHeaderInfo_getWidth(pAndroidHeader);
    auto height = AImageDecoderHeaderInfo_getHeight(pAndroidHeader);
    auto stride = AImageDecoder_getMinimumStride(pAndroidDecoder);

    // Get the bitmap data of the image
    auto textureBitmap = TextureBitmap {
            .id = assetPath,
            .width = width,
            .height = height,
            .data = std::make_unique<std::vector<uint8_t>>(height * stride)
    };
    auto decodeResult = AImageDecoder_decodeImage(
            pAndroidDecoder,
            textureBitmap.data->data(),
            stride,
            textureBitmap.data->size());
    assert(decodeResult == ANDROID_IMAGE_DECODER_SUCCESS);

    // cleanup helpers
    AImageDecoder_delete(pAndroidDecoder);
    AAsset_close(pAndroidRobotPng);

    // Create a shared pointer so it can be cleaned up easily/automatically
    return textureBitmap;
}