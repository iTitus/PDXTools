package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import static org.lwjgl.opengl.GL32C.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32C.glCreateShader;

public class VertexShader extends Shader {

    public static final String EXTENSION = ".vert";

    public VertexShader(ShaderSource shaderSource) {
        super(shaderSource);
    }

    @Override
    protected int create() {
        return glCreateShader(GL_VERTEX_SHADER);
    }
}
