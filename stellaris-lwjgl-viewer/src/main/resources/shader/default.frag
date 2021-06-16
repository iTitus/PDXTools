#version 150 core

in vec2 textureCoord;

layout(location = 0) out vec4 fragColor;

uniform sampler2D tex;

void main() {
    fragColor = texture2D(tex, textureCoord);
}
