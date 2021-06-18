package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import static org.lwjgl.opengl.GL32C.GL_VERTEX_SHADER;

public class VertexShader extends Shader<VertexShader> {

    public static final String EXTENSION = ".vert";

    public VertexShader(ShaderSource shaderSource) {
        super(GL_VERTEX_SHADER, shaderSource);
    }
}
