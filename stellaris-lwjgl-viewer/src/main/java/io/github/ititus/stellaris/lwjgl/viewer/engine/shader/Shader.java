package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;
import org.lwjgl.opengl.GL32C;

public abstract class Shader extends GlObject {

    private final ShaderSource shaderSource;

    protected Shader(ShaderSource shaderSource) {
        this.shaderSource = shaderSource;
    }

    @Override
    public void load() {
        GL32C.glShaderSource(id, shaderSource.getShaderSource());
        GL32C.glCompileShader(id);
        if (GL32C.glGetShaderi(id, GL32C.GL_COMPILE_STATUS) != GL32C.GL_TRUE) {
            String infoLog = GL32C.glGetShaderInfoLog(id);
            delete();
            throw new RuntimeException("Shader compilation failed: " + infoLog);
        }
    }

    @Override
    public void delete() {
        GL32C.glDeleteShader(id);
    }
}
