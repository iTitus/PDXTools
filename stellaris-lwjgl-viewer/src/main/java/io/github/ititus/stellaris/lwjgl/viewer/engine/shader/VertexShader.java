package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import org.lwjgl.opengl.GL32C;

public class VertexShader extends Shader {

    public static final String EXTENSION = ".vert";

    public VertexShader(ShaderSource shaderSource) {
        super(shaderSource);
    }

    @Override
    protected int create() {
        return GL32C.glCreateShader(GL32C.GL_VERTEX_SHADER);
    }
}
