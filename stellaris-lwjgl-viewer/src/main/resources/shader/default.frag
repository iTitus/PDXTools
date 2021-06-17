#version 330 core

in vertex_data {
    vec2 textureCoord;
};

layout(location = 0) out vec4 fragColor;

uniform sampler2D tex;

void main() {
    fragColor = texture(tex, textureCoord);
}
