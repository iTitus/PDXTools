package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import static org.lwjgl.opengl.GL32C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL32C.glCreateShader;

public class FragmentShader extends Shader {

    public static final String EXTENSION = ".frag";

    public FragmentShader(ShaderSource shaderSource) {
        super(shaderSource);
    }

    @Override
    protected int create() {
        return glCreateShader(GL_FRAGMENT_SHADER);
    }
}
