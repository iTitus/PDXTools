package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import org.lwjgl.opengl.GL32C;

public class FragmentShader extends Shader {

    public static final String EXTENSION = ".frag";

    public FragmentShader(ShaderSource shaderSource) {
        super(shaderSource);
    }

    @Override
    protected int create() {
        return GL32C.glCreateShader(GL32C.GL_FRAGMENT_SHADER);
    }
}
