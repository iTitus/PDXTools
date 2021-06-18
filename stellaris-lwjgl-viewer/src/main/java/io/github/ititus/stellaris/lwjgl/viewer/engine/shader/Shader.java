package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;

import static org.lwjgl.opengl.GL32C.*;

public abstract class Shader<T extends Shader<T>> extends GlObject<T> {

    private final int type;
    private final ShaderSource shaderSource;

    protected Shader(int type, ShaderSource shaderSource) {
        this.type = type;
        this.shaderSource = shaderSource;
    }

    @Override
    protected int create() {
        return glCreateShader(type);
    }

    @Override
    public void init() {
        glShaderSource(id, shaderSource.getShaderSource());
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE) {
            String infoLog = glGetShaderInfoLog(id);
            delete();
            throw new RuntimeException("Shader compilation failed: " + infoLog);
        }
    }

    @Override
    public void delete() {
        glDeleteShader(id);
    }
}
