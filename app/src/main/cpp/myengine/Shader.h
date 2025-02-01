#ifndef ANDROIDGLINVESTIGATIONS_SHADER_H
#define ANDROIDGLINVESTIGATIONS_SHADER_H

#include <string>
#include "AndroidOut.h"
#include <GLES3/gl3.h>
#include "utils/Utility.h"
#include "third-party/glm/gtc/type_ptr.hpp"
#include "types/shader/ShaderAttributeContext.h"
#include "types/shader/ShaderAttributeReference.h"
#include "types/shader/ShaderAttributeContainer.h"
#include "types/shader/ShaderUniformHolder.h"
#include "types/shader/ShaderUniformReference.h"
#include "types/shader/ShaderUniformType.h"


/*!
 * A class representing a simple shader program. It consists of vertex and fragment components. The
 * input attributes are a position (as a glm::vec3) and a uv (as a glm::vec2). It also takes a uniform
 * to be used as the entire model/view/projection matrix. The shader expects a single texture for
 * fragment shading, and does no other lighting calculations (thus no uniforms for lights or normal
 * attributes).
 */
class Shader {
public:
    /*!
     * Loads a shader given the full sourcecode and names for necessary attributes and uniforms to
     * link to. Returns a valid shader on success or null on failure. Shader resources are
     * automatically cleaned up on destruction.
     * @param vertexSource The full source code for your vertex program
     * @param fragmentSource The full source code of your fragment program
     * @param positionAttributeName The name of the position attribute in your vertex program
     * @param uvAttributeName The name of the uv coordinate attribute in your vertex program
     * @param projectionMatrixUniformName The name of your model/view/projection matrix uniform
     * @return a valid Shader on success, otherwise null.
     */
    static Shader *loadShader(
            const std::string &vertexSource,
            const std::string &fragmentSource
    );

    inline ~Shader() {
        if (program_) {
            glDeleteProgram(program_);
            program_ = 0;
        }
    }

    /*!
     * Prepares the shader for use, call this before executing any draw commands
     */
    void activate() const;

    /*!
     * Cleans up the shader after use, call this after executing any draw commands
     */
    static void deactivate() ;

    int findAttribute(const char *Id) const;

    int findUniform(const char *Id) const;


private:
    /*!
     * Helper function to cube a shader of a given type
     * @param shaderType The OpenGL shader type. Should either be GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
     * @param shaderSource The full source of the shader
     * @return the id of the shader, as returned by glCreateShader, or 0 in the case of an error
     */
    static GLuint loadShader(GLenum shaderType, const std::string &shaderSource);

    /*!
     * Constructs a new instance of a shader. Use @a activeShader
     * @param program the GL program id of the shader
     * @param position the attribute location of the position
     * @param uv the attribute location of the uv coordinates
     * @param projectionMatrix the uniform location of the projection matrix
     */
    explicit Shader(
            GLuint program
    ) : program_(program) {}

    GLuint program_;
};

#endif //ANDROIDGLINVESTIGATIONS_SHADER_H