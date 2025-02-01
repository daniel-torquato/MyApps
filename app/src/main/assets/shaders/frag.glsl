#version 300 es
precision mediump float;

#define MAX_TEXTURE 4
struct Material {
    sampler2D diffuse[MAX_TEXTURE];
    sampler2D specular[MAX_TEXTURE];
    float shininess;
};

struct Light {
    vec3 position;
    vec3 direction;
    bool isDirectional;
    vec3 attenuation;
    float cutOff;
    float outerCutOff;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

#define MAX_LIGHT 4

in vec2 fragUV;
in vec3 fragNormal;
in vec3 fragPos;
in float isFixed;

uniform vec3 uViewPos;
uniform Material uMaterial;
uniform int uLightSize;
uniform Light uLight[MAX_LIGHT];

out vec4 outColor;

vec3 calcLight(Light light, vec3 norm, vec3 fragPos, vec2 fragUV, vec3 viewPos) {
    vec3 result = vec3(0.0f, 0.0f, 0.0f);

    if (light.isDirectional == true) {
        vec3 lightDirNorm = normalize(-light.direction);

        // Textures
        vec3 diffuseTexture = texture(uMaterial.diffuse[0], fragUV).rgb;
        vec3 specularTexture = texture(uMaterial.specular[0], fragUV).rgb;

        // Ambient
        vec3 ambient = diffuseTexture;

        // Specular
        vec3 viewDir = normalize(viewPos - fragPos);
        vec3 reflectDir = reflect(-lightDirNorm, norm);
        vec3 specular = pow(max(dot(viewDir, reflectDir), 0.0f), uMaterial.shininess) * specularTexture;

        // Diffuse
        vec3 diffuse = max(dot(norm, lightDirNorm), 0.0f) * diffuseTexture;

        result = light.ambient * ambient + light.specular * specular + light.diffuse * diffuse;
    } else {
        vec3 lightDir = light.position - fragPos;
        vec3 lightDirNorm = normalize(lightDir);

        float theta = dot(lightDirNorm, normalize(-light.direction));
        float epsilon = light.cutOff - light.outerCutOff;
        float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);

        // Textures
        vec3 diffuseTexture = texture(uMaterial.diffuse[0], fragUV).rgb;
        vec3 specularTexture = texture(uMaterial.specular[0], fragUV).rgb;

        float distance = length(lightDir);
        vec3 distanceVector = vec3(1.0f, distance, (distance * distance));
        float attenuation = 1.0f / (dot(light.attenuation, distanceVector));

        // Ambient
        vec3 ambient = diffuseTexture;

        // Specular
        vec3 viewDir = normalize(viewPos - fragPos);
        vec3 reflectDir = reflect(-lightDirNorm, norm);
        vec3 specular = pow(max(dot(viewDir, reflectDir), 0.0f), uMaterial.shininess) * specularTexture;

        // Diffuse
        vec3 diffuse = max(dot(norm, lightDirNorm), 0.0f) * diffuseTexture;

        result = attenuation * light.ambient * ambient + intensity * attenuation * light.specular * specular +  intensity * attenuation * light.diffuse * diffuse;
    }

    // Result
    return result;
}

void main() {
    if (isFixed > 0.0f) {
        outColor = texture(uMaterial.diffuse[0], fragUV);
    } else {
        vec3 result = vec3(0.0f, 0.0f, 0.0f);
        for (int i = 0; i < uLightSize; ++i) {
            result += calcLight(uLight[i], normalize(fragNormal), fragPos, fragUV, uViewPos);
        }
        outColor = vec4(result, 1.0f);
    }
}