package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;

import static org.lwjgl.opengl.GL32C.*;

public abstract class Shader extends GlObject {

    private final ShaderSource shaderSource;

    protected Shader(ShaderSource shaderSource) {
        this.shaderSource = shaderSource;
    }

    @Override
    public void load() {
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
