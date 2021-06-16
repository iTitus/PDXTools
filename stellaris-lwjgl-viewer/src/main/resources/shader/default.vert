#version 150 core

in vec3 pos;
in vec2 texpos;

out vec4 vertexColor;
out vec2 textureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
    // vertexColor = color;
    textureCoord = texpos;
    gl_Position = projection * view * model * vec4(pos, 1.0f);
}
