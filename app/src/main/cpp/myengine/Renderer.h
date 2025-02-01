#ifndef ANDROIDGLINVESTIGATIONS_RENDERER_H
#define ANDROIDGLINVESTIGATIONS_RENDERER_H

#include <android/asset_manager.h>
#include <android/imagedecoder.h>
#include <game-activity/native_app_glue/android_native_app_glue.h>
#include <EGL/egl.h>
#include <GLES3/gl3.h>
#include <memory>
#include "types/Model.h"
#include "types/Scene.h"
#include "types/shader/ShaderAttributeReference.h"
#include "types/shader/ShaderInterface.h"
#include "Shader.h"
#include "loader/ModelLoader.h"
#include "graphical/GraphicalBackend.h"
#include "types/observer/Observer.h"
#include <memory>
#include <vector>
#include "AndroidOut.h"
#include "Shader.h"
#include "utils/Utility.h"
#include "utils/TimeUtil.h"
#include "utils/AssetLoader.h"
#include "third-party/glm/glm.hpp"
#include "third-party/glm/ext/matrix_transform.hpp"
#include "third-party/glm/gtc/type_ptr.hpp"
#include "loader/SceneLoader.h"
#include "types/Screen.h"

struct android_app;

class Renderer {
public:
    /*!
     * @param pApp the android_app this Renderer belongs to, needed to configure GL
     */
    inline explicit Renderer(android_app *pApp) :
            app_(pApp),
            display_(EGL_NO_DISPLAY),
            surface_(EGL_NO_SURFACE),
            context_(EGL_NO_CONTEXT),
            width_(0),
            height_(0),
            updateObserver_(true),
            isBeginning(true) {
        init();
    }

    virtual ~Renderer();

    /*!
     * Handles input from the android_app.
     *
     * Note: this will clear the input queue
     */
    void handleInput();

    /*!
     * Renders all the models in the renderer
     */
    void render();

private:
    /*!
     * Performs necessary OpenGL initialization. Customize this if you want to change your EGL
     * context or application-wide settings.
     */
    void init();

    /*!
     * @brief we have to check every frame to see if the framebuffer has changed in size. If it has,
     * update the viewport accordingly
     */
    void updateRenderArea();

    /*!
     * Creates the models for this sample. You'd likely cube a scene configuration from a file or
     * use some other setup logic in your full game.
     */
    void createModels();

    /**
     *
     */
    void activeShader();

    void enableAttributes(const Object &object);

    void enableTexture(const Object &it);

    void setObjectUniforms(const Object &it);

    void setUniform(const std::string& name, void *value) const;

    void setObserverUniforms();

    void updateObserver();

    void setBeginningUniforms();

    void disableAttributes();

    void getAttributeHolders(const  ShaderInterface &shaderInterface, const Shader &shader);

    void getUniformHolders(const  ShaderInterface &shaderInterface, const Shader &shader);

    android_app *app_;
    EGLDisplay display_;
    EGLSurface surface_;
    EGLContext context_;
    EGLint width_;
    EGLint height_;

    bool updateObserver_;
    bool isBeginning;

    std::unique_ptr<Shader> shader_;
    Scene scene_;

    ShaderAttributeContext attributeContext_;
    std::unordered_map<std::string, unsigned int> textureMap_;
    std::unordered_map<std::string, ShaderUniformHolder> uniformHolders_;

    Observer observer{};
    Control control;
    std::unordered_map<int, glm::vec3> lightAttenuationTable;
};

#endif //ANDROIDGLINVESTIGATIONS_RENDERER_H