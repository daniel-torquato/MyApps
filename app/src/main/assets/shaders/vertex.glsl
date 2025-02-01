#version 300 es
in vec3 inPosition;
in vec2 inUV;
in vec3 inNormal;

out vec2 fragUV;
out vec3 fragNormal;
out vec3 fragPos;
out float isFixed;

uniform bool uIsFixed;
uniform mat4 uProjection;
uniform mat4 uModel;
uniform mat4 uView;

void main() {
    fragUV = inUV;
    fragNormal = mat3(transpose(inverse(uModel))) * inNormal;
    fragPos = vec3(uModel * vec4(inPosition, 1.0));
    if (uIsFixed) {
        isFixed = 1.0f;
        gl_Position = uProjection * vec4(fragPos, 1.0);
    } else {
        isFixed = 0.0f;
        gl_Position = uProjection * uView * vec4(fragPos, 1.0);
    }
}