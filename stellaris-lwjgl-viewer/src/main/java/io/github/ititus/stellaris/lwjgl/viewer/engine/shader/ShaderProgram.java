package io.github.ititus.stellaris.lwjgl.viewer.engine.shader;

import io.github.ititus.math.matrix.Mat2f;
import io.github.ititus.math.matrix.Mat3f;
import io.github.ititus.math.matrix.Mat4f;
import io.github.ititus.math.vector.Vec2f;
import io.github.ititus.math.vector.Vec3f;
import io.github.ititus.math.vector.Vec4f;
import io.github.ititus.stellaris.lwjgl.viewer.engine.GlObject;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ShaderProgram extends GlObject {

    private final List<Shader> shaders;

    protected ShaderProgram(Shader... shaders) {
        this.shaders = new ArrayList<>(Arrays.asList(shaders));
    }

    public void use() {
        GL32C.glUseProgram(id);
    }

    @Override
    protected int create() {
        return GL32C.glCreateProgram();
    }

    @Override
    public void load() {
        link();
        findLocations();
    }

    @Override
    protected void delete() {
        freeShaders();
        GL32C.glDeleteProgram(id);
    }

    protected abstract void findLocations();

    public int getUniformLocation(CharSequence name) {
        return GL32C.glGetUniformLocation(id, name);
    }

    public int getAttributeLocation(CharSequence name) {
        return GL32C.glGetAttribLocation(id, name);
    }

    public void setUniform(int location, int value) {
        GL32C.glUniform1i(location, value);
    }

    public void setUniform(int location, float value) {
        GL32C.glUniform1f(location, value);
    }

    public void setUniform(int location, Vec2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(2);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Vec3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(3);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Vec4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Mat2f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Mat3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(9);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    public void setUniform(int location, Mat4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.write(buffer);
            buffer.flip();
            GL32C.glUniform2fv(location, buffer);
        }
    }

    private void link() {
        for (Shader shader : shaders) {
            shader.load();
            GL32C.glAttachShader(id, shader.id());
        }

        GL32C.glLinkProgram(id);
        if (GL32C.glGetProgrami(id, GL32C.GL_LINK_STATUS) != GL32C.GL_TRUE) {
            String infoLog = GL32C.glGetProgramInfoLog(id);
            free();
            throw new RuntimeException("Shader program linking failed: " + infoLog);
        }

        freeShaders();
    }

    private void freeShaders() {
        for (Shader shader : shaders) {
            GL32C.glDetachShader(id, shader.id());
            shader.free();
        }

        shaders.clear();
    }
}
