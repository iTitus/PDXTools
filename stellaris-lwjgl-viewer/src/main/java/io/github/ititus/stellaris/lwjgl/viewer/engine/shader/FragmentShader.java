package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import static org.lwjgl.opengl.GL32C.GL_FRAGMENT_SHADER;

public class FragmentShader extends Shader<FragmentShader> {

    public static final String EXTENSION = ".frag";

    public FragmentShader(ShaderSource shaderSource) {
        super(GL_FRAGMENT_SHADER, shaderSource);
    }
}
