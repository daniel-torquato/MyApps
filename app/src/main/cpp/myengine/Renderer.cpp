#include "Renderer.h"

void Renderer::render() {
    glClearColor(0.1f, 0.1f, 0.1f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    updateRenderArea();

    if (isBeginning) {

        setBeginningUniforms();

        isBeginning = false;
    }


    if (updateObserver_) {

        updateObserver();

        setObserverUniforms();

        updateObserver_ = false;
    }


    for (const Object &it: scene_.objects) {
        setObjectUniforms(it);

        enableAttributes(it);

        enableTexture(it);

        glDrawElements(GL_TRIANGLES, (int) it.model_.getIndexCount(), GL_UNSIGNED_SHORT, it.model_.getIndexData());

        disableAttributes();
    }

    auto swapResult = eglSwapBuffers(display_, surface_);
    assert(swapResult == EGL_TRUE);
}

void Renderer::enableAttributes(const Object &it) {
    for (const ShaderAttributeHolder &holder: attributeContext_.holders) {
        glVertexAttribPointer(
                holder.id,
                holder.size,
                GL_FLOAT,
                GL_FALSE,
                attributeContext_.data_size,
                ((uint8_t *) it.model_.getVertexData()) + holder.offset
        );
        glEnableVertexAttribArray(holder.id);
    }
}

void Renderer::enableTexture(const Object &it) {
    int index = 0;
    int diffuseIndex = 0;
    int specularIndex = 0;
    for (const Texture &texture: it.model_.getTextures()) {
        glActiveTexture(GL_TEXTURE0 + index);
        switch (texture.type) {
            case DIFFUSE: {
                auto diffuseId = int{index};
                std::string uniformName = "uMaterial.diffuse[" + std::to_string(diffuseIndex++) + "]";
                aout << "Texture: " << uniformName << std::endl;
                setUniform(uniformName, &diffuseId);
            }
                break;
            case SPECULAR: {
                auto specularId = int{index};
                std::string uniformName = "uMaterial.specular[" + std::to_string(specularIndex++) + "]";
                aout << "Texture: " << uniformName << std::endl;
                setUniform(uniformName, &specularId);
            }
                break;
        }

        glBindTexture(GL_TEXTURE_2D, textureMap_[texture.bitmap.id]);
        index++;
    }
}

void Renderer::setObjectUniforms(const Object &it) {
    auto transformation = it.transformation_.toMatrix();
    setUniform("uModel", (void *) glm::value_ptr(transformation));
    setUniform("uIsFixed", (void *) &(it.is_fixed_));
}

void Renderer::setObserverUniforms() {
    setUniform("uProjection", glm::value_ptr(observer.projection));
    auto view = observer.camera.getView();
    setUniform("uView", glm::value_ptr(view));
    setUniform("uViewPos", glm::value_ptr(observer.camera.position));
    setUniform("uLight[0].position", glm::value_ptr(observer.camera.position));
    setUniform("uLight[0].direction", glm::value_ptr(observer.camera.front));
}

void Renderer::disableAttributes() {
    for (const ShaderAttributeHolder &holder: attributeContext_.holders) {
        glDisableVertexAttribArray(holder.id);
    }
}

void Renderer::setBeginningUniforms() {


    auto shininess = 32.0f;
    setUniform("uMaterial.shininess", &shininess);

    auto lightSize = int{2};
    setUniform("uLightSize", &lightSize);

    // auto lightPosition = glm::vec3{0.0f, 0.0f, 10.0f};
    // setUniform("uLight[0].position", glm::value_ptr(lightPosition));
//
    // auto lightDirection = glm::vec3{0.0f, 0.0f, -1.0f};
    // setUniform("uLight[0].direction", glm::value_ptr(lightDirection));

    auto lightIsDirectional = false;
    setUniform("uLight[0].isDirectional", (void *) &lightIsDirectional);

    auto lightCutOff = glm::cos(glm::radians(5.5f));
    setUniform("uLight[0].cutOff", (void *) &lightCutOff);

    auto lightOuterCutOff = glm::cos(glm::radians(12.5f));
    setUniform("uLight[0].outerCutOff", (void *) &lightOuterCutOff);

    auto lightAttenuation = lightAttenuationTable.at(200);
    setUniform("uLight[0].attenuation", glm::value_ptr(lightAttenuation));

    auto lightAmbient = 0.01f * glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[0].ambient", glm::value_ptr(lightAmbient));

    auto lightDiffuse = 1.0f * glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[0].diffuse", glm::value_ptr(lightDiffuse));

    auto lightSpecular = 1.0f * glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[0].specular", glm::value_ptr(lightSpecular));

    auto lightPosition2 = glm::vec3{0.0f, 10.0f, 0.0f};
    setUniform("uLight[1].position", glm::value_ptr(lightPosition2));

    auto lightDirection2 = glm::vec3{0.0f, 0.0f, -1.0f};
    setUniform("uLight[1].direction", glm::value_ptr(lightDirection2));

    auto lightIsDirectional2 = false;
    setUniform("uLight[1].isDirectional", (void *) &lightIsDirectional2);

    auto lightCutOff2 = glm::cos(glm::radians(180.0f));
    setUniform("uLight[1].cutOff", (void *) &lightCutOff2);

    auto lightOuterCutOff2 = glm::cos(glm::radians(180.0f));
    setUniform("uLight[1].outerCutOff", (void *) &lightOuterCutOff2);

    auto lightAttenuation2 = lightAttenuationTable.at(3250);
    setUniform("uLight[1].attenuation", glm::value_ptr(lightAttenuation2));

    auto lightAmbient2 = 0.01f * glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[1].ambient", glm::value_ptr(lightAmbient2));

    auto lightDiffuse2 = glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[1].diffuse", glm::value_ptr(lightDiffuse2));

    auto lightSpecular2 = glm::vec3{1.0f, 1.0f, 1.0f};
    setUniform("uLight[1].specular", glm::value_ptr(lightSpecular2));

}

void Renderer::updateObserver() {
    auto screen = Screen {
            .width = (float) width_,
            .height = (float) height_
    };
    observer.update(screen.width, screen.height);
    observer.camera.update(control);
}

void Renderer::setUniform(const std::string& name, void *value) const {
    ShaderUniformHolder holder = uniformHolders_.at(name);
    switch (holder.type) {
        case BOOL_1:
            glUniform1i(holder.id, *((bool *) value));
            break;
        case INT_1:
            glUniform1iv(holder.id, 1, (int *) value);
            break;
        case FLOAT_1:
            glUniform1fv(holder.id, 1, (float *) value);
            break;
        case FLOAT_3:
            glUniform3fv(holder.id, 1, (float *) value);
            break;
        case FLOAT_4:
            glUniform4fv(holder.id, 1, (float *) value);
            break;
        case FLOAT_16:
            glUniformMatrix4fv(holder.id, 1, false, ((float *) value));
            break;
    }
}

void Renderer::init() {
    // Choose your render attributes
    constexpr EGLint attributes[] = {
            EGL_RENDERABLE_TYPE, EGL_OPENGL_ES3_BIT,
            EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
            EGL_BLUE_SIZE, 8,
            EGL_GREEN_SIZE, 8,
            EGL_RED_SIZE, 8,
            EGL_DEPTH_SIZE, 24,
            EGL_NONE
    };

    // The default display is probably what you want on Android
    auto display = eglGetDisplay(EGL_DEFAULT_DISPLAY);
    eglInitialize(display, nullptr, nullptr);

    // figure out how many configs there are
    EGLint numConfigs;
    eglChooseConfig(display, attributes, nullptr, 0, &numConfigs);

    // get the list of configurations
    std::unique_ptr<EGLConfig[]> supportedConfigs(new EGLConfig[numConfigs]);
    eglChooseConfig(display, attributes, supportedConfigs.get(), numConfigs, &numConfigs);

    // Find a config we like.
    // Could likely just grab the first if we don't care about anything else in the config.
    // Otherwise hook in your own heuristic
    auto config = *std::find_if(
            supportedConfigs.get(),
            supportedConfigs.get() + numConfigs,
            [&display](const EGLConfig &config) {
                EGLint red, green, blue, depth;
                if (eglGetConfigAttrib(display, config, EGL_RED_SIZE, &red)
                    && eglGetConfigAttrib(display, config, EGL_GREEN_SIZE, &green)
                    && eglGetConfigAttrib(display, config, EGL_BLUE_SIZE, &blue)
                    && eglGetConfigAttrib(display, config, EGL_DEPTH_SIZE, &depth)) {

                    aout << "Found config with " << red << ", " << green << ", " << blue << ", "
                         << depth << std::endl;
                    return red == 8 && green == 8 && blue == 8 && depth == 24;
                }
                return false;
            });

    aout << "Found " << numConfigs << " configs" << std::endl;
    aout << "Chose " << config << std::endl;

    // create the proper window surface
    EGLSurface surface = eglCreateWindowSurface(display, config, app_->window, nullptr);

    // Create a GLES 3 context
    EGLint contextAttributes[] = {EGL_CONTEXT_CLIENT_VERSION, 3, EGL_NONE};
    EGLContext context = eglCreateContext(display, config, nullptr, contextAttributes);

    // get some window metrics
    auto madeCurrent = eglMakeCurrent(display, surface, surface, context);
    assert(madeCurrent);

    display_ = display;
    surface_ = surface;
    context_ = context;

    // make width and height invalid so it gets updated the first frame in @a updateRenderArea()
    width_ = -1;
    height_ = -1;

    // PRINT_GL_STRING(GL_VENDOR)
    // PRINT_GL_STRING(GL_RENDERER)
    // PRINT_GL_STRING(GL_VERSION)
    // PRINT_GL_STRING_AS_LIST(GL_EXTENSIONS)

    glEnable(GL_DEPTH_TEST);

    activeShader();

    auto lightAttenuationTableLocal = std::unordered_map<int, glm::vec3>{
            {7,    {1.0f, 0.7f,    1.8f}},
            {13,   {1.0f, 0.35f,   0.44f}},
            {20,   {1.0f, 0.22f,   0.20f}},
            {32,   {1.0f, 0.14f,   0.07f}},
            {50,   {1.0f, 0.09f,   0.032f}},
            {65,   {1.0f, 0.07f,   0.017f}},
            {100,  {1.0f, 0.045f,  0.0075f}},
            {160,  {1.0f, 0.027f,  0.0028f}},
            {200,  {1.0f, 0.022f,  0.0019f}},
            {325,  {1.0f, 0.014f,  0.0007f}},
            {600,  {1.0f, 0.014f,  0.0002f}},
            {3250, {1.0f, 0.0014f, 0.000007f}},
    };
    lightAttenuationTable.merge(lightAttenuationTableLocal);

    // get some demo models into memory
    createModels();
}

void Renderer::updateRenderArea() {
    EGLint width;
    eglQuerySurface(display_, surface_, EGL_WIDTH, &width);

    EGLint height;
    eglQuerySurface(display_, surface_, EGL_HEIGHT, &height);

    if (width != width_ || height != height_) {
        width_ = width;
        height_ = height;
        glViewport(0, 0, width, height);

        // make sure that we lazily recreate the projection matrix before we render
        updateObserver_ = true;
    }
}

/**
 * @brief Create any demo models we want for this demo.
 */
void Renderer::createModels() {
    scene_ = SceneLoader::base(app_->activity->assetManager);

    for (const Object &object: scene_.objects) {
        for (const Texture &texture: object.model_.getTextures()) {
            if (textureMap_.find(texture.bitmap.id) == textureMap_.end())
                textureMap_[texture.bitmap.id] = GraphicalBackend::loadTexture(texture.bitmap);
        }
    }

    observer = Observer{
        .projection = glm::perspective(glm::radians(45.0f), 0.5f, 0.1f, 50.0f),
        .camera =  Camera{
            .position = {0.0f, 0.0f, 10.0f},
            .up = {0.0, 1.0f, 0.0f}
        }
    };
}

void Renderer::handleInput() {
    // handle all queued inputs
    auto *inputBuffer = android_app_swap_input_buffers(app_);
    if (!inputBuffer) {
        // no inputs yet.
        return;
    }

    // handle motion events (motionEventsCounts can be 0).
    for (auto i = 0; i < inputBuffer->motionEventsCount; i++) {
        auto &motionEvent = inputBuffer->motionEvents[i];
        auto action = motionEvent.action;

        // Find the pointer index, mask and bit-shift to turn it into a readable value.
        auto pointerIndex = (action & AMOTION_EVENT_ACTION_POINTER_INDEX_MASK)
                >> AMOTION_EVENT_ACTION_POINTER_INDEX_SHIFT;
        aout << "Pointer(s): ";

        // get the x and y position of this event if it is not ACTION_MOVE.
        auto &pointer = motionEvent.pointers[pointerIndex];
        auto x = GameActivityPointerAxes_getX(&pointer);
        auto y = GameActivityPointerAxes_getY(&pointer);

        // determine the action type and process the event accordingly.
        switch (action & AMOTION_EVENT_ACTION_MASK) {
            case AMOTION_EVENT_ACTION_DOWN:
            case AMOTION_EVENT_ACTION_POINTER_DOWN:
                aout << "(" << pointer.id << ", " << x << ", " << y << ") "
                     << "Pointer Down";
                control.createPosition(0);
                updateObserver_ = true;
                control.addEvent(0, glm::vec2{x, y});
                break;

            case AMOTION_EVENT_ACTION_CANCEL:
                // treat the CANCEL as an UP event: doing nothing in the app, except
                // removing the pointer from the cache if pointers are locally saved.
                // code pass through on purpose.
            case AMOTION_EVENT_ACTION_UP:
            case AMOTION_EVENT_ACTION_POINTER_UP:
                aout << "(" << pointer.id << ", " << x << ", " << y << ") "
                     << "Pointer Up";
                control.stop(0);
                break;

            case AMOTION_EVENT_ACTION_MOVE:
                // There is no pointer index for ACTION_MOVE, only a snapshot of
                // all active pointers; app needs to cache previous active pointers
                // to figure out which ones are actually moved.
                for (auto index = 0; index < motionEvent.pointerCount; index++) {
                    pointer = motionEvent.pointers[index];
                    x = GameActivityPointerAxes_getX(&pointer);
                    y = GameActivityPointerAxes_getY(&pointer);
                    updateObserver_ = true;
                    control.addEvent(index, glm::vec2 {x, y});
                    glm::vec2 move = control.getSpeed(index);
                    aout << "(" << pointer.id << ", " << move.x << ", " << move.y << ")";

                    if (index != (motionEvent.pointerCount - 1)) aout << ",";
                    aout << " ";
                }
                aout << "Pointer Move";
                break;
            default:
                aout << "Unknown MotionEvent Action: " << action;
        }
        aout << std::endl;
    }
    // clear the motion input count in this buffer for main thread to re-use.
    android_app_clear_motion_events(inputBuffer);

    // handle input key events.
    for (auto i = 0; i < inputBuffer->keyEventsCount; i++) {
        auto &keyEvent = inputBuffer->keyEvents[i];
        aout << "Key: " << keyEvent.keyCode <<" ";
        switch (keyEvent.action) {
            case AKEY_EVENT_ACTION_DOWN:
                aout << "Key Down";
                break;
            case AKEY_EVENT_ACTION_UP:
                aout << "Key Up";
                break;
            case AKEY_EVENT_ACTION_MULTIPLE:
                // Deprecated since Android API level 29.
                aout << "Multiple Key Actions";
                break;
            default:
                aout << "Unknown KeyEvent Action: " << keyEvent.action;
        }
        aout << std::endl;
    }
    // clear the key input count too.
    android_app_clear_key_events(inputBuffer);
}

void Renderer::activeShader() {
    ShaderInterface shaderInterface = ShaderInterface{
            .fragmentFile = "shaders/frag.glsl",
            .vertexFile = "shaders/vertex.glsl",
            .uniformList = {
                    {"uProjection",             FLOAT_16},
                    {"uView",                   FLOAT_16},
                    {"uModel",                  FLOAT_16},
                    {"uIsFixed",                BOOL_1},
                    {"uViewPos",                FLOAT_3},
                    {"uMaterial.specular[0]",   INT_1},
                    {"uMaterial.diffuse[0]",    INT_1},
                    {"uMaterial.shininess",     FLOAT_1},
                    {"uLightSize",              INT_1},
                    {"uLight[0].position",      FLOAT_3},
                    {"uLight[0].direction",     FLOAT_3},
                    {"uLight[0].isDirectional", BOOL_1},
                    {"uLight[0].attenuation",   FLOAT_3},
                    {"uLight[0].cutOff",        FLOAT_1},
                    {"uLight[0].outerCutOff",   FLOAT_1},
                    {"uLight[0].ambient",       FLOAT_3},
                    {"uLight[0].specular",      FLOAT_3},
                    {"uLight[0].diffuse",       FLOAT_3},
                    {"uLight[1].position",      FLOAT_3},
                    {"uLight[1].direction",     FLOAT_3},
                    {"uLight[1].isDirectional", BOOL_1},
                    {"uLight[1].attenuation",   FLOAT_3},
                    {"uLight[1].cutOff",        FLOAT_1},
                    {"uLight[1].outerCutOff",   FLOAT_1},
                    {"uLight[1].ambient",       FLOAT_3},
                    {"uLight[1].specular",      FLOAT_3},
                    {"uLight[1].diffuse",       FLOAT_3},
            },
            .attributeContainer = {
                    .data_size = sizeof(Vertex),
                    .references = std::vector<ShaderAttributeReference>{
                            {"inPosition", 3},
                            {"inUV",       2},
                            {"inNormal",   3}
                    }
            }
    };

    std::string fragSource = AssetLoader::loadFile(
            app_->activity->assetManager,
            shaderInterface.fragmentFile.c_str()
    );
    std::string vertexSource = AssetLoader::loadFile(
            app_->activity->assetManager,
            shaderInterface.vertexFile.c_str()
    );
    Shader *shader = Shader::loadShader(vertexSource, fragSource);

    getAttributeHolders(shaderInterface, *shader);

    getUniformHolders(shaderInterface, *shader);

    assert(shader);

    shader->activate();
}

void Renderer::getAttributeHolders(const  ShaderInterface &shaderInterface, const Shader &shader) {
    GLint status = 0;
    int offset = 0;
    attributeContext_ = {
            .data_size = shaderInterface.attributeContainer.data_size,
            .holders = std::vector<ShaderAttributeHolder>(shaderInterface.attributeContainer.references.size())
    };
    for (int i = 0; i < shaderInterface.attributeContainer.references.size(); ++i) {
        auto attributeReference = shaderInterface.attributeContainer.references[i];
        status = shader.findAttribute(attributeReference.name.c_str());
        if (status == -1)
            break;
        attributeContext_.holders[i] =  {
                .id = status,
                .size = attributeReference.size,
                .offset = offset
        };
        offset += attributeReference.size * (int) sizeof(glm::vec1);
    }
}

void Renderer::getUniformHolders(const  ShaderInterface &shaderInterface, const Shader &shader) {
    GLint status = 0;
    for (const ShaderUniformReference & uniformName : shaderInterface.uniformList) {
        status = shader.findUniform(uniformName.name.c_str());
        if (status == -1)
            break;
        uniformHolders_.insert({uniformName.name, {
                .id = status,
                .type = uniformName.type
        }});
    }
}

Renderer::~Renderer() {
    if (display_ != EGL_NO_DISPLAY) {
        eglMakeCurrent(display_, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
        if (context_ != EGL_NO_CONTEXT) {
            eglDestroyContext(display_, context_);
            context_ = EGL_NO_CONTEXT;
        }
        if (surface_ != EGL_NO_SURFACE) {
            eglDestroySurface(display_, surface_);
            surface_ = EGL_NO_SURFACE;
        }
        eglTerminate(display_);
        display_ = EGL_NO_DISPLAY;
    }

    for (const auto &id: textureMap_) {
        glDeleteTextures(1, &id.second);
    }
}
